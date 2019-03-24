package com.mingpinmall.me.ui;

import android.os.Bundle;

import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentMeBinding;

/**
 * 我的
 */
public class MeFragment extends BaseFragment<FragmentMeBinding> {

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
        binding.tvMe.setText("tvMe");
    }


    //    @OnClick(R2.id.tv_me)
//    public void onViewClicked(View view) {
//        int id = view.getId();
//        if (id == R.id.tv_me) {
//            ToastUtils.showLong("tvMe");
//        }
//    }
}
