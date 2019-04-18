package com.mingpinmall.me.ui.acitivity.property;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityVipintergralBinding;

/**
 * 功能描述：会员积分
 * 创建人：小斌
 * 创建时间: 2019/3/28
 **/
@Route(path = ARouterConfig.Me.VIPINTERGRALACTIVITY)
public class VipIntegralActivity extends BaseActivity<ActivityVipintergralBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_vipintergral;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle(R.string.title_vipIntegralActivity);
    }
}
