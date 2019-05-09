package com.mingpinmall.me.ui.acitivity.order;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityRefundInformationBinding;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.RefundInformation;
import com.mingpinmall.me.ui.constants.Constants;

/**
 * 功能描述：退款单详情
 * 创建人：小斌
 * 创建时间: 2019/4/16
 **/
@Route(path = ARouterConfig.Me.REFUNDORDERINFORMATION)
public class RefundOrderInformationActivity extends AbsLifecycleActivity<ActivityRefundInformationBinding, MeViewModel> {

    @Autowired
    String refundId;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        ARouter.getInstance().inject(this);
        setTitle(R.string.text_refundInformation);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.REFUND_INFORMATION, Object.class).observeForever(result -> {
            if (result instanceof RefundInformation) {
                RefundInformation data = (RefundInformation) result;
                binding.setData(data);
            } else {
                ToastUtils.showShort(result.toString());
            }
        });
    }

    @Override
    protected void initData() {
        mViewModel.getRefundInformation(refundId);
    }

    @Override
    protected Object getStateEventKey() {
        return "RefundOrderInformationActivity";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refund_information;
    }
}
