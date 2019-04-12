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
import com.mingpinmall.me.ui.api.UserViewModel;
import com.mingpinmall.me.ui.bean.MyInfoBean;
import com.mingpinmall.me.ui.bean.SmsBean;

/**
 * 功能描述：重设密码
 * 创建人：小斌
 * 创建时间: 2019/4/2
 **/
@Route(path = ARouterConfig.ResetPasswordActivity)
public class ResetPasswordActivity extends AbsLifecycleActivity<ActivityResetPasswordBinding, UserViewModel> implements TextWatcher {

    private ProgressDialog progressDialog;

    @Autowired
    String phoneNumber;

    @Autowired
    int type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);
        progressDialog = ProgressDialog.initNewDialog(getSupportFragmentManager());
        switch (type) {
            case 0:
                //重设登陆密码
                setTitle(R.string.title_resetPasswordActivity);
                break;
            case 1:
                //重设支付密码
                setTitle(R.string.title_resetPayPasswordActivity);
                break;
            default:
                finish();
                return;
        }
        binding.edPassword.addTextChangedListener(this);
        binding.edPassword2.addTextChangedListener(this);
        binding.btnSublimt.setOnClickListener(this);
    }

    @Override
    protected Object getStateEventKey() {
        return "RESET_LOGIN_PASSWORD";
    }

    @Override
    protected void dataObserver() {
        registerObserver("RESET_PASSWORD", "success")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object o) {
                        //重设密码成功
                        progressDialog.onComplete("", new ProgressDialog.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                finish();
                            }
                        });
                    }
                });
        registerObserver("RESET_PASSWORD", "fail")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object o) {
                        //重设密码失败
                        progressDialog.onFail("重设密码失败");
                    }
                });
        registerObserver("RESET_PASSWORD", "err")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object o) {
                        //重设密码成功错误
                        progressDialog.onFail(o.toString());
                    }
                });
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.btn_sublimt) {
            //点击下一步
            if (binding.edPassword.getText().toString().length() < 6 ||
                    !binding.edPassword.getText().toString().equals(binding.edPassword2.getText().toString())) {
                binding.edPassword.setError("两次密码不一致，或少于6位。");
                return;
            }
            binding.btnSublimt.setEnabled(false);
            progressDialog.onLoading("");
            String password0 = binding.edPassword.getText().toString();
            String password1 = binding.edPassword2.getText().toString();

            switch (type) {
                case 0:
                    mViewModel.resetPassword(password0, password1);
                    break;
                case 1:
                    mViewModel.resetPayPassword(password0, password1);
                    break;
            }
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
        if (binding.edPassword.length() >= 6 && binding.edPassword2.length() >= 6 ) {
            binding.btnSublimt.setEnabled(true);
        } else {
            binding.btnSublimt.setEnabled(false);
        }
    }
}
