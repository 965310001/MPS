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

/**
 * 功能描述：退货单详情
 * 创建人：小斌
 * 创建时间: 2019/4/16
 **/
@Route(path = ARouterConfig.Me.RETRUNORDERINFORMATION)
public class ReturnOrderInformationActivity extends AbsLifecycleActivity<ActivityReturnInformationBinding, MeViewModel> {

    @Autowired
    String pdcId;

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver("PDCASH_INFORMATION", "success", PdcashInfoBean.class)
                .observeForever(new Observer<PdcashInfoBean>() {
                    @Override
                    public void onChanged(@Nullable PdcashInfoBean result) {
                        Log.i("哇哇哇", "onChanged: 进来了？");
                        binding.setData(result);
                    }
                });
        registerObserver("PDCASH_INFORMATION", "err", String.class)
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
        setTitle(R.string.title_PdcashInformationActivity);
        mViewModel.getPdcashList(pdcId);
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
