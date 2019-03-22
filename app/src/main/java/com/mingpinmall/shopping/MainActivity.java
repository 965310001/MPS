package com.mingpinmall.shopping;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mingpinmall.shopping.bean.UserBean;
import com.mingpinmall.shopping.databinding.ActivityMainBinding;

import me.goldze.common.base.mvvm.base.test.BaseActivity2;

/**
 * @Author: guofeng
 * @CreateDate:
 * @Description:
 */
public class MainActivity extends BaseActivity2<ActivityMainBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("主页");
    }

    @Override
    protected void initData() {
        super.initData();
//        UserBean user = new UserBean();
//        user.setName("guofeng");
//        binding.setUser(user);
    }
}
