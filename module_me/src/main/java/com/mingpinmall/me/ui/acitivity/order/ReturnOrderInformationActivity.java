package com.mingpinmall.me.ui.acitivity.order;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityPhysicalOrderInformationBinding;
import com.mingpinmall.me.databinding.ActivityReturnInformationBinding;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.PdcashInfoBean;
import com.mingpinmall.me.ui.bean.ReturnInformation;

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
        registerObserver("RETURN_INFORMATION", "success", ReturnInformation.class)
                .observeForever(new Observer<ReturnInformation>() {
                    @Override
                    public void onChanged(@Nullable ReturnInformation result) {
                        binding.setData(result);
                    }
                });
        registerObserver("RETURN_INFORMATION", "err", String.class)
                .observeForever(new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String msg) {
                        ToastUtils.showShort(msg);
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
