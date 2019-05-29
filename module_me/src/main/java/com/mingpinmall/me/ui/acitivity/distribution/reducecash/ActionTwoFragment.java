package com.mingpinmall.me.ui.acitivity.distribution.reducecash;

import android.os.Bundle;

import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.loading.CustomProgressDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentReducecashAction2Binding;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.constants.Constants;

/**
 * 功能描述：提现申请
 * *@author 小斌
 * @date 2019/4/29
 **/
public class ActionTwoFragment extends AbsLifecycleFragment<FragmentReducecashAction2Binding, MeViewModel> {

    public static ActionTwoFragment getInstance() {
        return new ActionTwoFragment();
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        binding.btnSubmit2.setOnClickListener(v -> {
            CustomProgressDialog.show(activity);
            mViewModel.addPdCash(
                    binding.edUserName.getText().toString(),
                    binding.edShouKuanAccount.getText().toString(),
                    binding.edShouKuan.getText().toString(),
                    binding.edMoney.getText().toString(),
                    binding.edPayPassword.getText().toString()
            );
        });
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.ADD_PDCASH, String.class).observeForever(s -> {
            CustomProgressDialog.stop();
            ToastUtils.showShort(s);
        });
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
    }

    @Override
    protected Object getStateEventKey() {
        return "ActionTwoFragment";
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_reducecash_action2;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }
}
