package me.goldze.common.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.heima.tabview.library.TabView;
import com.heima.tabview.library.TabViewChild;

import java.util.List;

import me.goldze.common.R;
import me.goldze.common.base.mvvm.base.BaseActivity;

/**
 * @author GuoFeng
 * @date :2019/1/16 14:40
 * @description: 底部导航Activity
 */
public abstract class BottomNavigationActivity extends BaseActivity {

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

        TabView tabView = findViewById(R.id.tabView);
        tabView.setTabViewChild(tabViewChildren, fragmentManager);
        if (null != listener) {
            tabView.setOnTabChildClickListener(listener);
        }
    }
}
