package com.mingpinmall.me.ui.acitivity.setting;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.google.gson.Gson;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityResetPasswordBinding;
import com.mingpinmall.me.ui.api.UserViewModel;
import com.mingpinmall.me.ui.bean.MyInfoBean;
import com.mingpinmall.me.ui.bean.SmsBean;

/**
 * 功能描述：重设密码
 * 创建人：小斌
 * 创建时间: 2019/4/2
 **/
@Route(path = ARouterConfig.ResetPasswordActivity)
public class ResetPasswordActivity extends AbsLifecycleActivity<ActivityResetPasswordBinding, UserViewModel> {

    @Autowired
    String phoneNumber;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);
        setTitle(R.string.title_resetPasswordActivity);
        binding.tvPhone.setText(phoneNumber);
        binding.edMsgCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.btnSublimt.setEnabled(s.length() >= 4);
            }
        });
        binding.btnGetPsdCode.setOnClickListener(this);
    }

    @Override
    protected Object getStateEventKey() {
        return null;
    }

    @Override
    protected void dataObserver() {
        LiveBus.getDefault()
                .subscribe("GET_SMS_CODE", SmsBean.class)
                .observeForever(new Observer<SmsBean>() {
                    @Override
                    public void onChanged(@Nullable SmsBean smsBean) {

                    }
                });
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.btn_getPsdCode) {
            MyInfoBean.DatasBean.MemberInfoBean infoBean = new Gson().fromJson(SharePreferenceUtil.getKeyValue("USER_INFO"), MyInfoBean.DatasBean.MemberInfoBean.class);
            mViewModel.getSmsCode(3, infoBean.getMember_mobile());
        }
    }
}
