package com.mingpinmall.me.ui;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityLoginBinding;


/**
 * @Author: guofeng
 * @CreateDate:
 * @Description: 登录
 */
@Route(path = ARouterConfig.LOGINACTIVITY)
public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
//        super.initViews(savedInstanceState);
        setTitle("登录");
        binding.edPhone.setText("15949629529");
        binding.edPassword.setText("15949629529");
    }

    @Override
    protected boolean isBack() {
        return false;
    }


    //    @OnClick({R2.id.btn_sublimt})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_sublimt) {
            ToastUtils.showLong("这是错误吗");
        }

    }

}
