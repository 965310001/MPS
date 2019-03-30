package com.mingpinmall.me.ui.acitivity.address;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityEditaddressBinding;

/**
 * 功能描述：编辑 / 新增 收货地址
 * 创建人：小斌
 * 创建时间: 2019/3/29
 **/
@Route(path = ARouterConfig.EDITADDRESSACTIVITY)
public class EditAddressActivity extends BaseActivity<ActivityEditaddressBinding> {

    @Autowired
    boolean isAdd = true;//是否是新增， false则是编辑模式

    @Override
    protected int getLayoutId() {
        return R.layout.activity_editaddress;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        setTitle(isAdd ? R.string.title_addAddressActivity : R.string.title_editAddressActivity);
    }
}
