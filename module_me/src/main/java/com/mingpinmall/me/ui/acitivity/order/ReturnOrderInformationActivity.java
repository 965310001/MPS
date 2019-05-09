package com.mingpinmall.me.ui.acitivity.order;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityReturnInformationBinding;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.ReturnInformation;
import com.mingpinmall.me.ui.constants.Constants;

/**
 * 功能描述：退货单详情
 * 创建人：小斌
 * 创建时间: 2019/4/16
 **/
@Route(path = ARouterConfig.Me.RETRUNORDERINFORMATION)
public class ReturnOrderInformationActivity extends AbsLifecycleActivity<ActivityReturnInformationBinding, MeViewModel> {

    @Autowired
    String returnId;

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.RETURN_INFORMATION, Object.class).observeForever(result -> {
            if (result instanceof ReturnInformation) {
                ReturnInformation data = (ReturnInformation) result;
                binding.setData(data);
            } else {
                ToastUtils.showShort(result.toString());
            }
        });
    }

    @Override
    protected void initData() {
        ARouter.getInstance().inject(this);
        setTitle(R.string.text_returnInformation);
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
