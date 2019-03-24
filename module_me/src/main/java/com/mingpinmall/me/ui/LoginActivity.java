package com.mingpinmall.me.ui;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mingpinmall.me.R;
import com.mingpinmall.me.R2;
import com.mingpinmall.me.ui.api.UserViewModel;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.OnClick;
import me.goldze.common.base.mvvm.AbsLifecycleActivity2;
import me.goldze.common.constants.ARouterConfig;
import me.goldze.common.utils.ToastUtils;

/**
 * @Author: guofeng
 * @CreateDate:
 * @Description: 登录
 */
@Route(path = ARouterConfig.LOGINACTIVITY)
public class LoginActivity extends AbsLifecycleActivity2<UserViewModel> {

    @BindView(R2.id.ed_phone)
    MaterialEditText edPhone;

    @BindView(R2.id.ed_password)
    MaterialEditText edPassword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTitle("登录");
        edPhone.setText("15949629529");
        edPassword.setText("15949629529");
    }

    @Override
    protected boolean isBack() {
        return false;
    }


    @OnClick({R2.id.btn_sublimt})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_sublimt) {
            ToastUtils.showLong("这是错误吗");
        }

    }

}
