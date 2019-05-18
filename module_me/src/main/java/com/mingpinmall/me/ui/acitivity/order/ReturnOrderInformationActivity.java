package com.mingpinmall.me.ui.acitivity.order;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityReturnInformationBinding;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.ReturnInformationBean;
import com.mingpinmall.me.ui.constants.Constants;

import java.util.List;

/**
 * 功能描述：退货单详情
 * *@author 小斌
 * @date 2019/4/16
 **/
@Route(path = ARouterConfig.Me.RETRUNORDERINFORMATION)
public class ReturnOrderInformationActivity extends AbsLifecycleActivity<ActivityReturnInformationBinding, MeViewModel> {

    @Autowired
    String returnId;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);
        setTitle(R.string.text_returnInformation);
        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> initData());
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.RETURN_INFORMATION, Object.class).observeForever(result -> {
            if (result instanceof String) {
                ToastUtils.showShort(result.toString());
            } else {
                formatData((List<ReturnInformationBean>) result);
            }
        });
    }

    private void formatData(List<ReturnInformationBean> dataList) {
        ReturnInformationBean data;
        if (dataList.size() > 0) {
            data = dataList.get(0);
        } else {
            return;
        }
        binding.setData(data);
        if (data.getDetail_array() == null || data.getDetail_array().isNull()) {
            binding.llContent.setVisibility(View.GONE);
        } else {
            binding.llContent.setVisibility(View.VISIBLE);
        }

        AppCompatImageView imageView;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ScreenUtil.dip2px(activity, 36),
                ScreenUtil.dip2px(activity, 36)
        );
        params.leftMargin = ScreenUtil.dip2px(activity, 6);
        for (String imageUrl : data.getPic_list()) {
            imageView = new AppCompatImageView(activity);
            imageView.setLayoutParams(params);
            binding.llImageList.addView(imageView);
            ImageUtils.loadImage(imageView, imageUrl,
                    ScreenUtil.dip2px(activity, 36),
                    ScreenUtil.dip2px(activity, 36)
            );
        }
    }

    @Override
    protected void initData() {
        mViewModel.getReturnInformation(returnId);
    }

    @Override
    protected Object getStateEventKey() {
        return "ReturnOrderInformationActivity";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_return_information;
    }
}
