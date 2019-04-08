package com.mingpinmall.me.ui.acitivity.setting;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.google.gson.Gson;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityResetCheckBinding;
import com.mingpinmall.me.databinding.ActivityResetPasswordBinding;
import com.mingpinmall.me.ui.api.UserViewModel;
import com.mingpinmall.me.ui.bean.MyInfoBean;

/**
 * 功能描述：重设密码
 * 创建人：小斌
 * 创建时间: 2019/4/2
 **/
@Route(path = ARouterConfig.CheckAuthActivity)
public class CheckAuthActivity extends AbsLifecycleActivity<ActivityResetCheckBinding, UserViewModel> {

    public final static int RESET_PHONE = 0;
    public final static int RESET_PASSWORD = 1;
    public final static int RESET_PAY_PASSWORD = 2;

    @Autowired
    String phoneNumber;

    @Autowired
    int type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_check;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);
        switch (type) {
            case 0:
                setTitle(R.string.title_resetPhoneActivity);
                binding.tvChange.setVisibility(View.VISIBLE);
                break;
            case 1:
                setTitle(R.string.title_resetPasswordActivity);
                break;
            case 2:
                setTitle(R.string.title_resetPayPasswordActivity);
                break;
            default:
                finish();
                return;
        }
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
        binding.btnSublimt.setOnClickListener(this);
    }

    @Override
    protected Object getStateEventKey() {
        return "RESET_CHECK";
    }

    @Override
    protected void dataObserver() {
        registerObserver("GET_SMS_CODE", "success")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        ToastUtils.showShort("验证码发送成功");
                    }
                });
        registerObserver("GET_SMS_CODE", "err")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        ToastUtils.showShort(result.toString());
                    }
                });
        registerObserver("CHECK_CODE", "success")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        //验证成功
                        switch (type) {
                            case 0:
                                ActivityToActivity.toActivity(ARouterConfig.ResetPhoneActivity);
                                break;
                            case 1:
                                ActivityToActivity.toActivity(ARouterConfig.ResetPasswordActivity);
                                break;
                            case 2:
                                ActivityToActivity.toActivity(ARouterConfig.ResetPayPwdActivity);
                                break;
                        }
                    }
                });
        registerObserver("CHECK_CODE", "err")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        //验证码验证失败
                        ToastUtils.showShort(result.toString());
                        binding.btnSublimt.setEnabled(true);
                    }
                });
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.btn_getPsdCode) {
            MyInfoBean.MemberInfoBean infoBean = new Gson().fromJson(SharePreferenceUtil.getKeyValue("USER_INFO"), MyInfoBean.MemberInfoBean.class);
            mViewModel.getSmsCode(3, infoBean.getMember_mobile());
        } else if (viewId == R.id.btn_sublimt) {
            //点击下一步
            binding.btnSublimt.setEnabled(false);
            mViewModel.checkCode(binding.edMsgCode.getText().toString().trim());
        }
    }
}
