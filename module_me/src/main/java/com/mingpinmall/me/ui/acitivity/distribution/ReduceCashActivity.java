package com.mingpinmall.me.ui.acitivity.distribution;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.mvvm.base.BaseTabsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityReducecashBinding;
import com.mingpinmall.me.ui.acitivity.distribution.reducecash.ActionOneFragment;
import com.mingpinmall.me.ui.acitivity.distribution.reducecash.ActionTwoFragment;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.ReduceCashBean;
import com.mingpinmall.me.ui.constants.Constants;

/**
 * 功能描述：我的推广码
 * *@author 小斌
 * @date 2019/4/29
 **/
@Route(path = ARouterConfig.Me.REDUCECASHACTIVITY)
public class ReduceCashActivity extends BaseTabsLifecycleActivity<ActivityReducecashBinding, MeViewModel> {

    @Override
    protected String[] initTabs() {
        return new String[]{getString(R.string.tabs_text_reduce1), getString(R.string.tabs_text_reduce2)};
    }

    @Override
    protected Class[] initFragments() {
        return new Class[]{ActionOneFragment.class, ActionTwoFragment.class};
    }

    @Override
    protected void initData() {
        super.initData();
        mViewModel.getReduceCash();
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.REDUCE_CASH, Object.class).observeForever(result -> {
            if (result instanceof ReduceCashBean) {
                ReduceCashBean data = (ReduceCashBean) result;
                binding.setData(data);
            } else {
                ToastUtils.showShort(result.toString());
            }
        });
    }

    @Override
    protected Object getStateEventKey() {
        return "ReduceCashActivity";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reducecash;
    }

    @Override
    protected int getFrameId() {
        return R.id.fl_view;
    }
}
