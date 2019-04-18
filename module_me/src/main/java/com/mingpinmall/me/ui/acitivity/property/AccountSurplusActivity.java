package com.mingpinmall.me.ui.acitivity.property;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityAccountSurplusBinding;
import com.mingpinmall.me.ui.acitivity.property.accountSurplusFragment.SurplusFragment1;
import com.mingpinmall.me.ui.acitivity.property.accountSurplusFragment.SurplusFragment2;
import com.mingpinmall.me.ui.acitivity.property.accountSurplusFragment.SurplusFragment3;
import com.mingpinmall.me.ui.adapter.BasePagerAdapter;

/**
 * 功能描述：我的财产-账户余额
 * 创建人：小斌
 * 创建时间: 2019/3/28
 **/
@Route(path = ARouterConfig.Me.ACCOUNTSURPLUSACTIVITY)
public class AccountSurplusActivity extends BaseActivity<ActivityAccountSurplusBinding> {

    private SurplusFragment1 surplusFragment1;
    private SurplusFragment2 surplusFragment2;
    private SurplusFragment3 surplusFragment3;

    private BasePagerAdapter pagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_surplus;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle(R.string.title_accountSurplusActivity);
        pagerAdapter = new BasePagerAdapter(getSupportFragmentManager(), this);

        surplusFragment1 = new SurplusFragment1();
        surplusFragment2 = new SurplusFragment2();
        surplusFragment3 = new SurplusFragment3();

        pagerAdapter.addFragment(surplusFragment1, R.string.tabs_text_surplus1);
        pagerAdapter.addFragment(surplusFragment2, R.string.tabs_text_surplus2);
        pagerAdapter.addFragment(surplusFragment3, R.string.tabs_text_surplus3);

        binding.viewPager.setAdapter(pagerAdapter);
        binding.tabs.setupWithViewPager(binding.viewPager);
    }
}
