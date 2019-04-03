package com.mingpinmall.classz.ui;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.goldze.common.dmvvm.BuildConfig;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.http.rx.ApiObserver;
import com.goldze.common.dmvvm.http.rx.BaseObserver;
import com.goldze.common.dmvvm.http.rx.RxBus;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.RxUtils;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ResultBean;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.constants.Constants;
import com.mingpinmall.classz.databinding.FragmentClassifyBinding;
import com.mingpinmall.classz.ui.vm.ClassifyViewModel;
import com.mingpinmall.classz.ui.vm.bean.BrandListInfo;
import com.mingpinmall.classz.ui.vm.bean.ClassificationBean;
import com.mingpinmall.classz.ui.vm.bean.ClassificationRighitBean;
import com.socks.library.KLog;
import com.trecyclerview.TRecyclerView;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.adapter.ItemData;
import com.trecyclerview.listener.OnItemClickListener;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 分类
 */
public class ClassifyFragment extends AbsLifecycleFragment<FragmentClassifyBinding, ClassifyViewModel>
        implements OnItemClickListener, View.OnClickListener {

    TRecyclerView rvRightRecyclerView;
    DelegateAdapter rightAdapter;

    private final ItemData rightData = new ItemData();

    public ClassifyFragment() {
    }

    public static ClassifyFragment newInstance() {
        return new ClassifyFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_classify;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    LinearLayoutManager linearLayoutManager;

    @Override
    protected <T extends View> T getViewById(int id) {
        return super.getViewById(id);
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);

        getViewById(R.id.rl_title_bar).setVisibility(View.VISIBLE);
        getViewById(R.id.iv_search).setVisibility(View.VISIBLE);
        getViewById(R.id.iv_search).setOnClickListener(this);
        ((TextView) getViewById(R.id.tv_title)).setText("分类");

        rightAdapter = AdapterPool.newInstance().getRightAdapter(getActivity())
                .build();
        rightAdapter.setDatas(rightData);
        final TRecyclerView rvLeftRecyclerView = binding.trvLeft;
        rvRightRecyclerView = binding.trvRight;

        rvLeftRecyclerView.setAdapter(AdapterPool.newInstance().getLeftAdapter(getActivity())
                .setOnItemClickListener(this)
                .build());
        rvRightRecyclerView.setAdapter(rightAdapter);

        rvLeftRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvLeftRecyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if (rightAdapter.getItems().get(i) instanceof ClassificationRighitBean.DatasBean.ClassListBean) {
                    return 3;
                } else if (rightAdapter.getItems().get(i) instanceof ClassificationRighitBean.DatasBean.ClassListBean.ChildBean) {
                    return 3;
                } else {
                    return 1;
                }
            }
        });
        rvRightRecyclerView.setLayoutManager(gridLayoutManager);
        mViewModel.getLeft();
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        LiveBus.getDefault()
                .subscribe(Constants.EVENT_KEY_CLASSIFY_MORE[0], ClassificationBean.class)
                .observeForever(new Observer<ClassificationBean>() {
                    @Override
                    public void onChanged(@Nullable ClassificationBean response) {
                        List<ClassificationBean.DatasBean.ClassListBean> class_list = response.getDatas().getClass_list();
                        data = new ClassificationBean.DatasBean.ClassListBean("-1",
                                "品牌推荐", BuildConfig.APP_URL + "/wap/images/degault.png", true);
                        class_list.add(0, data);
                        binding.setData(class_list);
                        mViewModel.getRightByBrand();
                    }
                });

        LiveBus.getDefault()
                .subscribe(Constants.EVENT_KEY_CLASSIFY_MORE[2], Object.class)
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object object) {
                        rightData.clear();
                        if (object instanceof ClassificationRighitBean) {
                            ClassificationRighitBean data = (ClassificationRighitBean) object;
                            rightData.addAll(data.getDatas().getClass_list());
                        } else if (object instanceof BrandListInfo) {
                            BrandListInfo data = (BrandListInfo) object;
                            rightData.addAll(data.getDatas().getBrand_list());
                        }
                        binding.setRightdata(rightData);
                    }
                });
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.EVENT_KEY_CLASSIFY_MORE[1];
    }

    int leftPostion = 0;
    ClassificationBean.DatasBean.ClassListBean data;

    @Override
    public void onItemClick(View view, int postion, Object object) {
        if (object instanceof ClassificationBean.DatasBean.ClassListBean) {
            if (leftPostion != postion) {
                data.setSelect(false);
                data = (ClassificationBean.DatasBean.ClassListBean) object;
                data.setSelect(true);
                if (data.getGc_id().equals("-1")) {
                    mViewModel.getRightByBrand();
                } else {
                    mViewModel.getRight(data.getGc_id());
                }
                leftPostion = postion;
            }
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_search) {
            ActivityToActivity.toActivity(ARouterConfig.home.SEARCHACTIVITY);
        }
    }
}