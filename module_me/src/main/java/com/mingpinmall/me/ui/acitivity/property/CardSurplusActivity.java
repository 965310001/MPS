package com.mingpinmall.me.ui.acitivity.property;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityBaseTabsViewpagerBinding;
import com.xuexiang.xui.widget.tabbar.TabControlView;

/**
 * 功能描述：我的财产-充值卡余额
 * 创建人：小斌
 * 创建时间: 2019/3/28
 **/
@Route(path = ARouterConfig.CARDSURPLUSACTIVITY)
public class CardSurplusActivity extends BaseActivity<ActivityBaseTabsViewpagerBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_tabs_viewpager;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle(R.string.title_cardSurplusActivity);
        try {
            tabControlView.setItems(new String[]{getString(R.string.tabs_text_cardsurplus1), getString(R.string.tabs_text_cardsurplus2)}, new String[]{"1", "2"});
        } catch (Exception e) {
            e.printStackTrace();
        }
        tabControlView.setOnTabSelectionChangedListener(new TabControlView.OnTabSelectionChangedListener() {
            @Override
            public void newSelection(String title, String value) {
            }
        });
    }

    @Override
    protected boolean isTabsBar() {
        return true;
    }
}
