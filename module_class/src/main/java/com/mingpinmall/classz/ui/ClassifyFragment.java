package com.mingpinmall.classz.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mingpinmall.classz.R;
import com.mingpinmall.classz.R2;

import butterknife.BindView;
import butterknife.OnClick;
import me.goldze.common.base.mvvm.base.BaseFragment;
import me.goldze.common.utils.ToastUtils;

/**
 * 分类
 */
public class ClassifyFragment extends BaseFragment {

    @BindView(R2.id.tv_classif)
    TextView tvClassz;


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
        tvClassz.setText("tvClassz");
    }


    @OnClick(R2.id.tv_classif)
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_classif) {
            ToastUtils.showLong("tv_classif");
        }
    }
}
