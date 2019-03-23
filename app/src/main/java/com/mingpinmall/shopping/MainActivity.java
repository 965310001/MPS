package com.mingpinmall.shopping;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mingpinmall.shopping.bean.UserBean;
import com.mingpinmall.shopping.databinding.ActivityMainBinding;
import com.socks.library.KLog;

import me.goldze.common.base.mvvm.base.test.BaseActivity2;
import me.goldze.common.utils.ToastUtils;

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
        UserBean user = new UserBean();
        user.setName("dage");
        binding.setUser(user);
        binding.setClick(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Toast.makeText(v.getContext(), "you clicked the view", Toast.LENGTH_LONG).show();
    }

    public void doSubmit(View view) {
        Toast.makeText(view.getContext(), "you clicked the view", Toast.LENGTH_LONG).show();
        KLog.i("doSubmit");
    }

    public void doSubmit(UserBean userBean) {
        Toast.makeText(this, "you clicked the view" + userBean.getName(), Toast.LENGTH_LONG).show();
        KLog.i("doSubmit");
    }
}
