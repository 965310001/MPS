package com.mingpinmall.classz.ui.activity;

import android.os.Bundle;

import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ui.activity.classify.ClassifyFragment;


public class ClasszActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_classz;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("分类");
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, ClassifyFragment.newInstance()).commit();

//        ActivityToActivity.toActivity(ARouterConfig.home.SHOPPINGDETAILSACTIVITY, "id", "109928");
    }

    @Override
    protected boolean isActionBar() {
        return false;
    }

    @Override
    protected boolean isBack() {
        return false;
    }
}