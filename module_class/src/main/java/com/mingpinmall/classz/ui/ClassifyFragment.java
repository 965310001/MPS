package com.mingpinmall.classz.ui;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.goldze.common.dmvvm.BuildConfig;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.mingpinmall.classz.R;
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

/**
 * 分类
 */
public class ClassifyFragment extends AbsLifecycleFragment<FragmentClassifyBinding, ClassifyViewModel> implements OnItemClickListener {

    //    private TRecyclerView rvLeftRecyclerView;
    TRecyclerView rvRightRecyclerView;
    //    private DelegateAdapter leftAdapter;
    DelegateAdapter rightAdapter;

//    private final ItemData leftData = new ItemData();

    private final ItemData rightData = new ItemData();

    /***************/
//    private RecyclerView rvLeft, rvRight;
//    private BaseItemAdapter leftBaseAdapter, rightBaseAdapter;

    /***************/


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
    public void initView(Bundle state) {
        super.initView(state);
//        leftAdapter = AdapterPool.newInstance().getLeftAdapter(getActivity())
//                .setOnItemClickListener(this)
//                .build();
        rightAdapter = AdapterPool.newInstance().getRightAdapter(getActivity())
                .build();
//        leftAdapter.setDatas(leftData);
        rightAdapter.setDatas(rightData);
        TRecyclerView rvLeftRecyclerView = binding.trvLeft;
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

        /********/
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if (rightData.get(i) instanceof ClassificationRighitBean.DatasBean.ClassListBean) {
                    return 3;
                } else if (rightData.get(i) instanceof ClassificationRighitBean.DatasBean.ClassListBean.ChildBean) {
                    return 3;
                } else {
                    return 1;
                }
            }
        });
        rvRightRecyclerView.setLayoutManager(gridLayoutManager);
        /********/

//        rvRightRecyclerView.setLayoutManager(linearLayoutManager);

        /*******************************/
//        rvLeft = binding.rvLeft;
//        rvRight = binding.rvRight;
//        linearLayoutManager = new LinearLayoutManager(getActivity());
//        rvLeft.setLayoutManager(linearLayoutManager);
//        linearLayoutManager = new LinearLayoutManager(getActivity());
//        rvRight.setLayoutManager(linearLayoutManager);
//        rvLeft.addItemDecoration(new DividerItemDecoration(getContext(),
//                DividerItemDecoration.VERTICAL));
//        rvRight.addItemDecoration(new DividerItemDecoration(getContext(),
//                DividerItemDecoration.VERTICAL));
//        leftBaseAdapter = new BaseItemAdapter();
//        rightBaseAdapter = new BaseItemAdapter();
//        rvLeft.setAdapter(leftBaseAdapter);
//        rvRight.setAdapter(rightBaseAdapter);
//
//        leftBaseAdapter.register(ClassificationBean.DatasBean.ClassListBean.class,
//                new DataBindViewHolderManager(R.layout.classify_item_of_list, BR.data));

//        rightBaseAdapter.register(ClassificationRighitBean.DatasBean.ClassListBean.class,
//                new DataBindViewHolderManager(R.layout.item_products, BR.data));

        /*******************************/

        mViewModel.getLeft();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        lazyLoad();
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();

        LiveBus.getDefault()
                .subscribe(Constants.EVENT_KEY_CLASSIFY_MORE, ClassificationBean.class)
                .observeForever(new Observer<ClassificationBean>() {
                    @Override
                    public void onChanged(@Nullable ClassificationBean response) {
//                        leftData.clear();
                        List<ClassificationBean.DatasBean.ClassListBean> class_list = response.getDatas().getClass_list();
                        data = new ClassificationBean.DatasBean.ClassListBean("-1",
                                "品牌推荐", BuildConfig.APP_URL + "/wap/images/degault.png", true);
                        class_list.add(0, data);
//                        leftData.addAll(class_list);
//                        leftAdapter.notifyDataSetChanged();
//                        data = response.getDatas().getClass_list().get(0);
//                        data.setSelect(true);
//                        mViewModel.getRight(data.getGc_id());

                        binding.setData(class_list);
                        mViewModel.getRightByBrand();

                        /**************************/
//                        leftBaseAdapter.addDataItems(response.getDatas().getClass_list());
                        /**************************/
                    }
                });

//        LiveBus.getDefault()
//                .subscribe(Constants.EVENT_KEY_CLASSIFY_MORE_RIGHT, ClassificationRighitBean.class)
//                .observeForever(new Observer<ClassificationRighitBean>() {
//                    @Override
//                    public void onChanged(@Nullable ClassificationRighitBean response) {
//                        rightData.clear();
//                        rightData.addAll(response.getDatas().getClass_list());
//                        rightAdapter.notifyDataSetChanged();
//                        /**************************/
//                        /**************************/
//                    }
//                });

        LiveBus.getDefault()
                .subscribe(Constants.EVENT_KEY_CLASSIFY_MORE_RIGHT, Object.class)
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
                        rightAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.EVENT_KEY_CLASSIFY_MORE_STATE;
    }

    int leftPostion = 0;
    ClassificationBean.DatasBean.ClassListBean data;

    @Override
    public void onItemClick(View view, int postion, Object object) {
        if (object instanceof ClassificationBean.DatasBean.ClassListBean) {
            if (leftPostion != postion) {

                data.setSelect(false);
//                leftAdapter.notifyItemChanged(leftPostion);
                data = (ClassificationBean.DatasBean.ClassListBean) object;

                data.setSelect(true);
                if (data.getGc_id().equals("-1")) {
                    mViewModel.getRightByBrand();
                } else {
                    mViewModel.getRight(data.getGc_id());
                }

                leftPostion = postion;
//                leftAdapter.notifyItemChanged(leftPostion);
            }
        } else if (object instanceof BrandListInfo.DatasBean.BrandListBean) {
//            leftAdapter.notifyItemChanged(leftPostion);
//            data = (ClassificationBean.DatasBean.ClassListBean) object;
//
//            data.setSelect(true);
//            mViewModel.getRightByBrand();
//
//            leftPostion = postion;
//            leftAdapter.notifyItemChanged(leftPostion);
        }

    }


}