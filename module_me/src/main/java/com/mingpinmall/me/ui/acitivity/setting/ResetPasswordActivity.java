package com.mingpinmall.me.ui.acitivity.setting;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.loading.CustomProgressDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityResetPasswordBinding;
import com.mingpinmall.me.ui.api.UserViewModel;
import com.mingpinmall.me.ui.constants.Constants;

import static com.goldze.common.dmvvm.constants.ARouterConfig.SUCCESS;

/**
 * 功能描述：重设密码
 *
 * @author 小斌
 * @date 2019/4/2
 **/
@Route(path = ARouterConfig.Me.RESETPASSWORDACTIVITY)
public class ResetPasswordActivity extends AbsLifecycleActivity<ActivityResetPasswordBinding, UserViewModel> implements TextWatcher {

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
        switch (type) {
            case 0:
                //重设登陆密码
                setTitle(R.string.title_resetPasswordActivity);
                break;
            case 1:
                //重设支付密码
                setTitle(R.string.title_resetPayPasswordActivity);
                binding.edPassword.setHint("请输入支付密码");
                binding.edPassword2.setHint("在次输入支付密码");
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
        registerObserver(Constants.RESET_PASSWORD, String.class).observeForever(o -> {
            //重设密码成功
            switch (o) {
                case SUCCESS:
                    ToastUtils.showShort("密码已重设");
                    CustomProgressDialog.stop();
                    //发起更新数据
                    LiveBus.getDefault().postEvent(ARouterConfig.REFRESH_DATA, "SettingActivity", "");
                    finish();
                    break;
                default:
                    ToastUtils.showShort(o);
                    CustomProgressDialog.stop();
                    break;
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
            CustomProgressDialog.show(activity);
            String password0 = binding.edPassword.getText().toString();
            String password1 = binding.edPassword2.getText().toString();

            switch (type) {
                case 0:
                    mViewModel.resetPassword(password0, password1);
                    break;
                case 1:
                    mViewModel.resetPayPassword(password0, password1);
                    break;
                default:
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
        if (binding.edPassword.length() >= 6 && binding.edPassword2.length() >= 6) {
            binding.btnSublimt.setEnabled(true);
        } else {
            binding.btnSublimt.setEnabled(false);
        }
    }
}
