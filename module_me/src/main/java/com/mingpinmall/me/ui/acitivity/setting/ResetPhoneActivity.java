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
import com.mingpinmall.me.databinding.ActivityResetPhoneBinding;
import com.mingpinmall.me.ui.api.UserViewModel;
import com.mingpinmall.me.ui.bean.MyInfoBean;
import com.mingpinmall.me.ui.bean.SmsBean;

/**
 * 功能描述：重设手机
 * 创建人：小斌
 * 创建时间: 2019/4/2
 **/
@Route(path = ARouterConfig.ResetPhoneActivity)
public class ResetPhoneActivity extends AbsLifecycleActivity<ActivityResetPhoneBinding, UserViewModel> {

    @Autowired
    String phoneNumber;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_phone;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);
        setTitle(R.string.title_resetPhoneActivity);
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
        binding.tvChange.setOnClickListener(this);
    }

    @Override
    protected Object getStateEventKey() {
        return null;
    }

    @Override
    protected void dataObserver() {
        registerObserver("GET_SMS_CODE", SmsBean.class)
                .observeForever(new Observer<SmsBean>() {
                    @Override
                    public void onChanged(@Nullable SmsBean smsBean) {

                    }
                });
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.btn_getPsdCode) {
            //获取短信验证码
            MyInfoBean.MemberInfoBean infoBean = new Gson().fromJson(SharePreferenceUtil.getKeyValue("USER_INFO"), MyInfoBean.MemberInfoBean.class);
            mViewModel.getSmsCode(3, infoBean.getMember_mobile());
        } else if (viewId == R.id.tv_change) {
            //使用支付密码来验证
        }
    }
}
