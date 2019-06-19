package com.mingpinmall.me.ui.acitivity.distribution.reducecash;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;

import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.loading.CustomProgressDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentReducecashAction1Binding;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.ReduceCashBean;
import com.mingpinmall.me.ui.constants.Constants;

/**
 * 功能描述：提现申请
 * *@author 小斌
 *
 * @date 2019/4/29
 **/
public class ActionOneFragment extends AbsLifecycleFragment<FragmentReducecashAction1Binding, MeViewModel> {

    public static ActionOneFragment getInstance() {
        return new ActionOneFragment();
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        binding.btnSubmit1.setOnClickListener(v -> {
            if (binding.edFriendsCode.length() == 0) {
                ToastUtils.showShort("请输入邀请码");
                return;
            }
            CustomProgressDialog.show(activity, "正在绑定");
            mViewModel.bindUserCode(binding.edFriendsCode.getText().toString().trim());
        });

    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.BIND_USER_CODE, String.class).observeForever(s -> {
            CustomProgressDialog.stop();
            ToastUtils.showShort(s);
        });

        registerObserver(Constants.REDUCE_CASH, Object.class).observeForever(result -> {
            if (result instanceof ReduceCashBean) {
                ReduceCashBean data = (ReduceCashBean) result;
                AppCompatEditText edFriendsCode = getViewById(R.id.ed_friendsCode);
                edFriendsCode.setText(data.getRecommend());
            } else {
                ToastUtils.showShort(result.toString());
            }
        });
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
    }

    @Override
    protected Object getStateEventKey() {
        return "ActionOneFragment";
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_reducecash_action1;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }
}
