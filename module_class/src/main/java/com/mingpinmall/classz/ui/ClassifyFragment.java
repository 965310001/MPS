package com.mingpinmall.classz.ui;


import android.os.Bundle;

import com.mingpinmall.classz.R;

import me.goldze.common.base.mvvm.base.BaseFragment;

/**
 * 分类
 */
public class ClassifyFragment extends BaseFragment {

    public ClassifyFragment() {
    }

    public static ClassifyFragment newInstance() {
        return new ClassifyFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_classify;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {
        showSuccess();
    }

}
