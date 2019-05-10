package com.mingpinmall.me.ui.acitivity.property;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityAccountSurplusBinding;
import com.mingpinmall.me.ui.acitivity.property.accountsurplus.PdcashFragment;
import com.mingpinmall.me.ui.acitivity.property.accountsurplus.PdrechargeFragment;
import com.mingpinmall.me.ui.acitivity.property.accountsurplus.PredepositLogFragment;
import com.mingpinmall.me.ui.adapter.BasePagerAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.Predepoit;
import com.mingpinmall.me.ui.constants.Constants;

/**
 * 功能描述：我的财产-账户余额
 * @author 小斌
 * @date 2019/3/28
 **/
@Route(path = ARouterConfig.Me.ACCOUNTSURPLUSACTIVITY)
public class AccountSurplusActivity extends AbsLifecycleActivity<ActivityAccountSurplusBinding, MeViewModel> {

    private PredepositLogFragment predepositLogFragment;
    private PdrechargeFragment pdrechargeFragment;
    private PdcashFragment pdcashFragment;

    private BasePagerAdapter pagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_surplus;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTitle(R.string.title_accountSurplusActivity);
        pagerAdapter = new BasePagerAdapter(getSupportFragmentManager(), this);

        predepositLogFragment = new PredepositLogFragment();
        pdrechargeFragment = new PdrechargeFragment();
        pdcashFragment = new PdcashFragment();

        pagerAdapter.addFragment(predepositLogFragment, R.string.tabs_text_surplus1);
        pagerAdapter.addFragment(pdrechargeFragment, R.string.tabs_text_surplus2);
        pagerAdapter.addFragment(pdcashFragment, R.string.tabs_text_surplus3);

        binding.viewPager.setAdapter(pagerAdapter);
        binding.tabs.setupWithViewPager(binding.viewPager);
    }

    @Override
    protected void initData() {
        super.initData();
        mViewModel.getPredepoit();
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver("REFRESH_PDC", String.class).observeForever(s -> {
            //需要更新数据
            initData();
        });
        registerObserver(Constants.PREDEPOIT, Object.class).observeForever(result -> {
            if (result instanceof Predepoit) {
                Predepoit predepoit = (Predepoit) result;
                //获取到账户余额
                binding.tvSurplus.setText(predepoit.getPredepoit());
            } else {
                ToastUtils.showShort(result.toString());
            }
        });
    }

    @Override
    protected Object getStateEventKey() {
        return "AccountSurplusActivity";
    }
}
