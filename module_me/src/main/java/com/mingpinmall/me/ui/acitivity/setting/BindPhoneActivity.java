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
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.progress.ProgressDialog;
import com.google.gson.Gson;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityResetPasswordBinding;
import com.mingpinmall.me.databinding.ActivityResetPhoneBinding;
import com.mingpinmall.me.ui.api.UserViewModel;
import com.mingpinmall.me.ui.bean.MyInfoBean;
import com.mingpinmall.me.ui.bean.SmsBean;
import com.mingpinmall.me.ui.constants.Constants;
import com.xuexiang.xui.utils.CountDownButtonHelper;

import org.w3c.dom.Text;

/**
 * 功能描述：重设手机
 * 创建人：小斌
 * 创建时间: 2019/4/2
 **/
@Route(path = ARouterConfig.Me.BindPhoneActivity)
public class BindPhoneActivity extends AbsLifecycleActivity<ActivityResetPhoneBinding, UserViewModel> implements TextWatcher {

    @Autowired
    String phoneNumber;

    private ProgressDialog progressDialog;
    private CountDownButtonHelper buttonHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_phone;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);
        setTitle(R.string.title_resetPhoneActivity);

        progressDialog = ProgressDialog.initNewDialog(getSupportFragmentManager());

        binding.edPhone.addTextChangedListener(this);
        binding.edMsgCode.addTextChangedListener(this);

        binding.tvGetPsdCode.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);

        buttonHelper = new CountDownButtonHelper(binding.tvGetPsdCode, 60);
        buttonHelper.setOnCountDownListener(new CountDownButtonHelper.OnCountDownListener() {
            @Override
            public void onCountDown(int time) {
                binding.tvGetPsdCode.setEnabled(false);
                binding.tvGetPsdCode.setText(time + "s后可重新获取");
            }

            @Override
            public void onFinished() {
                binding.tvGetPsdCode.setEnabled(true);
                binding.tvGetPsdCode.setText("重新获取");
            }
        });
    }

    @Override
    protected Object getStateEventKey() {
        return "RESET_PHONE";
    }

    @Override
    protected void dataObserver() {
        registerObserver(Constants.GET_SMS_CODE, Object.class).observeForever(result -> {
            if (result instanceof SmsBean) {
                ToastUtils.showShort("动态码已发送");
                buttonHelper.start();
            } else {
                ToastUtils.showShort(result.toString());
            }
        });
        registerObserver(Constants.BIND_PHONE, String.class).observeForever(result -> {
            if (result.equals("success")) {
                progressDialog.onComplete("", () -> {
                    LiveBus.getDefault().postEvent(ARouterConfig.REFRESH_DATA, "SettingActivity", "");
                    finish();
                });
            } else {
                progressDialog.onFail(result);
            }
        });
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.tv_getPsdCode) {
            //获取短信验证码
            mViewModel.getSmsCode(4, binding.edPhone.getText().toString());
        } else if (viewId == R.id.btn_submit) {
            //提交
            progressDialog.onLoading("");
            mViewModel.bindPhone(binding.edMsgCode.getText().toString());
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        binding.tvGetPsdCode.setVisibility(binding.edPhone.length() >= 11 ? View.VISIBLE : View.INVISIBLE);
        binding.btnSubmit.setEnabled(binding.edMsgCode.length() >= 4 && binding.edPhone.length() >= 11);
    }
}
