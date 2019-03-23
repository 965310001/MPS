package com.mingpinmall.me.ui;


import android.os.Bundle;

import com.mingpinmall.me.R;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;

import butterknife.BindView;
import me.goldze.common.base.mvvm.base.BaseFragment;

/**
 * 我的
 */
public class MeFragment extends BaseFragment {

    @BindView(R.id.ed_phone)
    MaterialEditText edPhone;

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
//        edPhone.setText("1594962952");
    }

}
