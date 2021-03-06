package com.mingpinmall.cart.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.mingpinmall.cart.R;
import com.mingpinmall.cart.ui.CartFragment;

/**
 * 功能描述：购物车页面
 * *@author 小斌
 *
 * @date 2019/4/29
 **/
@Route(path = ARouterConfig.cart.SHOPCARTACTIVITY)
public class ShopCartActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_base);
        findViewById(R.id.rl_title_bar).setVisibility(View.VISIBLE);
        findViewById(R.id.iv_back).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.tv_title)).setText(R.string.title_ShopCartActivity);
        findViewById(R.id.iv_back).setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.remove(getSupportFragmentManager().findFragmentByTag("CART"));
            onBackPressed();
        });

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_content, CartFragment.newInstance(String.valueOf(System.currentTimeMillis())), "CART");
        fragmentTransaction.commitNowAllowingStateLoss();
    }

}
