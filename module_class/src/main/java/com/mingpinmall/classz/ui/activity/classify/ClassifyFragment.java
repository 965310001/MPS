package com.mingpinmall.classz.ui.activity.classify;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.goldze.common.dmvvm.BuildConfig;
import com.goldze.common.dmvvm.activity.qrcode.ScanQrCodeActivity;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.databinding.FragmentClassifyBinding;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.BrandListInfo;
import com.mingpinmall.classz.ui.vm.bean.ClassificationBean;
import com.mingpinmall.classz.ui.vm.bean.ClassificationRighitBean;
import com.socks.library.KLog;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.adapter.ItemData;
import com.trecyclerview.listener.OnItemClickListener;

import java.util.List;

/**
 * 分类
 */
public class ClassifyFragment extends AbsLifecycleFragment<FragmentClassifyBinding, ClassifyViewModel>
        implements OnItemClickListener, View.OnClickListener {

    private int leftPosition = 0;
    private DelegateAdapter rightAdapter;
    private ClassificationBean.ClassListBean data;
    private ItemData rightData;

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

    @Override
    public void initView(Bundle state) {
        super.initView(state);

        rightData = new ItemData();

        getViewById(R.id.sv_search).setOnClickListener(this);
        getViewById(R.id.iv_scan).setOnClickListener(this);
        getViewById(R.id.iv_msg).setOnClickListener(this);

        //在这里设置沉浸式状态栏
        setTitlePadding(getViewById(R.id.rl_title_content));
        //并且设置状态栏字体颜色为黑色
        setDarkMode(true);

        binding.setLeftAdapter(AdapterPool.newInstance().getLeftAdapter(getActivity())
                .setOnItemClickListener(this)
                .build());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                return rightAdapter.getItems().get(i) instanceof String ? 3 : 1;
            }
        });
        rightAdapter = AdapterPool.newInstance()
                .getRightAdapter(getActivity())
                .build();
        binding.setRightLayout(gridLayoutManager);
        binding.setRightAdapter(rightAdapter);
    }

    @Override
    protected void getRemoteData() {
        super.getRemoteData();
        mViewModel.getLeft();
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.EVENT_KEY_CLASSIFY_MORE[0], BaseResponse.class)
                .observeForever(response -> {
                    BaseResponse<ClassificationBean> baseResponse = response;
                    if (baseResponse.isSuccess()) {
                        try {
                            List<ClassificationBean.ClassListBean> class_list = baseResponse.getData().getClass_list();
                            data = new ClassificationBean.ClassListBean("-1",
                                    "品牌推荐", BuildConfig.APP_URL + "/wap/images/degault.png", true);
                            class_list.add(0, data);
                            binding.setData(class_list);
                            mViewModel.getRightByBrand();
                        } catch (Exception e) {
                            KLog.i(e.toString());
                            ToastUtils.showLong("服务器出现问题，请稍后再试");
                        }
                    } else {
                        ToastUtils.showLong(baseResponse.getMessage());
                    }

                });

        registerObserver(Constants.EVENT_KEY_CLASSIFY_MORE[2], Object.class)
                .observeForever(object -> {
                    rightData.clear();
                    if (object instanceof ClassificationRighitBean) {
                        ClassificationRighitBean data = (ClassificationRighitBean) object;
                        rightData.add("热门推荐");
                        rightData.addAll(data.getDatas().getClass_list());
                    } else if (object instanceof BrandListInfo) {
                        BrandListInfo data = (BrandListInfo) object;
                        rightData.add("热门品牌");
                        rightData.addAll(data.getDatas().getBrand_list());
                    }
                    binding.setRightdata(rightData);
                });
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.EVENT_KEY_CLASSIFY_MORE[1];
    }

    @Override
    public void onItemClick(View view, int position, Object object) {
        if (object instanceof ClassificationBean.ClassListBean) {
            if (leftPosition != position) {
                data.setSelect(false);
                data = (ClassificationBean.ClassListBean) object;
                data.setSelect(true);
                if (data.getGc_id().equals("-1")) {
                    mViewModel.getRightByBrand();
                } else {
                    mViewModel.getRight(data.getGc_id());
                }
                leftPosition = position;
            }
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sv_search) {
            ActivityToActivity.toActivity(ARouterConfig.home.SEARCHACTIVITY);
        } else if (i == R.id.iv_scan) {
            IntentIntegrator.forSupportFragment(ClassifyFragment.this).setCaptureActivity(ScanQrCodeActivity.class).initiateScan();
        } else if (i == R.id.iv_msg) {
            ActivityToActivity.toActivity(ARouterConfig.Me.MESSAGEACTIVITY);
        }
    }

    @Override
    protected void onVisible() {
        //当这个fragment用户可见时，重新设置为黑色状态栏字体
        setDarkMode(true);
    }
}