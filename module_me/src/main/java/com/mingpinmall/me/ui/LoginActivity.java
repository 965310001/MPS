package com.mingpinmall.me.ui;

import android.os.Bundle;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mingpinmall.me.R;

import butterknife.BindView;
import me.goldze.common.base.mvvm.base.test.BaseActivity;
import me.goldze.common.constants.ARouterConfig;

/**
 * @Author: guofeng
 * @CreateDate:
 * @Description: 登录
 */
@Route(path = ARouterConfig.LOGINACTIVITY)
public class LoginActivity extends BaseActivity {

    @BindView(R2.id.ed_phone)
    EditText edPhone;

//    @Override
//    public void initViews(Bundle savedInstanceState) {
//        ARouter.getInstance().inject(this);
//        super.initViews(savedInstanceState);
//
//        edPhone.setText("1594962");
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        edPhone.setText("1594962");
    }

}
