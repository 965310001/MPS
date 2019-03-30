package com.mingpinmall.me.ui.acitivity.account;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityLoginBinding;


/**
 * 功能描述：登陆界面
 * 创建人：小斌
 * 创建时间: 2019/3/25
 */
@Route(path = ARouterConfig.LOGINACTIVITY)
public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        setTitle(R.string.title_loginActivity);

        TabLayout.Tab tab = binding.tabs.newTab();
        tab.setText(R.string.tabs_login_puk);
        tab.setTag("puk");

        TabLayout.Tab tab2 = binding.tabs.newTab();
        tab2.setText(R.string.tabs_login_phone);
        tab2.setTag("psd");

        binding.tabs.addTab(tab);
        binding.tabs.addTab(tab2);

        binding.tvProtocol.setOnClickListener(this);

        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getTag().toString()) {
                    case "puk":
                        binding.llPanel0.setVisibility(View.VISIBLE);
                        binding.llPanel1.setVisibility(View.GONE);
                        break;
                    case "psd":
                        binding.llPanel0.setVisibility(View.GONE);
                        binding.llPanel1.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.btn_sublimt) {
            ToastUtils.showLong("点击了登录");
        } else if (viewId == R.id.tv_protocol) {
            ActivityToActivity.toWebView("http://39.108.254.185/wap/tmpl/member/document.html");
        }

    }

}
