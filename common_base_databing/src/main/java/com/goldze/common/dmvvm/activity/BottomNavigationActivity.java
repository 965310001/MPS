package com.goldze.common.dmvvm.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.goldze.common.dmvvm.R;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.databinding.ActivityBottomNavigationBinding;
import com.heima.tabview.library.TabView;
import com.heima.tabview.library.TabViewChild;

import java.util.List;


/**
 * @author GuoFeng
 * @date :2019/1/16 14:40
 * @description: 底部导航Activity
 */
public abstract class BottomNavigationActivity extends BaseActivity<ActivityBottomNavigationBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bottom_navigation;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        showSuccess();
    }

    //init Bottom tabBar
    protected void initNavBar(@NonNull List<TabViewChild> tabViewChildren,
                              @NonNull FragmentManager fragmentManager,
                              @Nullable TabView.OnTabChildClickListener listener) {

//        TabView tabView = binding.tabView;
        binding.tabView.setTabViewChild(tabViewChildren, fragmentManager);
        if (null != listener) {
            binding.tabView.setOnTabChildClickListener(listener);
        }
    }
}
