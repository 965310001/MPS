package com.mingpinmall.me.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mingpinmall.me.R;
import com.mingpinmall.me.R2;

import butterknife.BindView;
import butterknife.OnClick;
import me.goldze.common.base.mvvm.base.BaseFragment;
import me.goldze.common.utils.ToastUtils;

/**
 * 我的
 */
public class MeFragment extends BaseFragment {

    @BindView(R2.id.tv_me)
    TextView tvMe;

    public MeFragment() {
    }

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_me;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {
        showSuccess();
        tvMe.setText("tvMe");
    }




    @OnClick(R2.id.tv_me)
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id==R.id.tv_me) {
            ToastUtils.showLong("tvMe");
        }
    }
}
