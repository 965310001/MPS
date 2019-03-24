package com.mingpinmall.classz.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.FragmentClassifyBinding;


/**
 * 分类
 */
public class ClassifyFragment extends BaseFragment<FragmentClassifyBinding> {

    //    @BindView(R2.id.tv_classif)
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
        binding.tvClassif.setText("tvClassz");
    }


    //    @OnClick(R2.id.tv_classif)
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_classif) {
            ToastUtils.showLong("tv_classif");
        }
    }
}
