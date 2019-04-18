package com.mingpinmall.me.ui.acitivity.setting;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.dialog.MaterialDialogUtils;
import com.goldze.common.dmvvm.widget.progress.ProgressDialog;
import com.google.gson.Gson;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityResetCheckBinding;
import com.mingpinmall.me.databinding.ActivityResetPasswordBinding;
import com.mingpinmall.me.ui.api.UserViewModel;
import com.mingpinmall.me.ui.bean.MyInfoBean;
import com.xuexiang.xui.utils.CountDownButtonHelper;

/**
 * 功能描述：重设密码
 * 创建人：小斌
 * 创建时间: 2019/4/2
 **/
@Route(path = ARouterConfig.Me.CheckAuthActivity)
public class CheckAuthActivity extends AbsLifecycleActivity<ActivityResetCheckBinding, UserViewModel> {

    public final static int RESET_PHONE = 0;
    public final static int RESET_PASSWORD = 1;
    public final static int RESET_PAY_PASSWORD = 2;

    private ProgressDialog progressDialog;
    private CountDownButtonHelper buttonHelper;

    @Autowired
    String phoneNumber;
    @Autowired
    String phoneNumber2;

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
        progressDialog = ProgressDialog.initNewDialog(getSupportFragmentManager());
        switch (type) {
            case 0:
                setTitle(R.string.title_resetPhoneActivity);
//                binding.tvChange.setVisibility(View.VISIBLE); //通过支付密码验证 按钮
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
        binding.tvPhone.setText(phoneNumber2);

        setListener();
    }

    /*设置监听*/
    private void setListener() {
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
        binding.tvGetPsdCode.setOnClickListener(this);
        binding.btnSublimt.setOnClickListener(this);
        binding.tvChange.setOnClickListener(this);
    }

    @Override
    protected Object getStateEventKey() {
        return "RESET_CHECK";
    }

    @Override
    protected void dataObserver() {
        registerObserver("CHECK_PAY_PASSWORD", "success")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        //设置了支付密码，跳转到使用支付密码设置手机
                        progressDialog.close();
                    }
                });

        registerObserver("CHECK_PAY_PASSWORD", "fail")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        //没设置支付密码，跳转到设置支付密码
                        progressDialog.close();
                        MaterialDialogUtils.showBasicDialog(CheckAuthActivity.this, getString(R.string.dialog_text_tips))
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        //跳转操作放在这
                                        //这里直接销毁这个界面，返回个人设置界面
                                        finish();
                                    }
                                })
                                .show();
                    }
                });

        registerObserver("CHECK_PAY_PASSWORD", "err")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        //支付密码 查询失败
                        progressDialog.onFail("网络错误！");
                    }
                });
        registerObserver("GET_RESET_SMS_CODE", "success")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        ToastUtils.showShort("验证码发送成功");
                    }
                });
        registerObserver("GET_RESET_SMS_CODE", "err")
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
                                ActivityToActivity.toActivity(ARouterConfig.Me.BindPhoneActivity);
                                break;
                            case 1:
                                ActivityToActivity.toActivity(ARouterConfig.Me.ResetPasswordActivity, "type", 0);
                                break;
                            case 2:
                                ActivityToActivity.toActivity(ARouterConfig.Me.ResetPasswordActivity, "type", 1);
                                break;
                        }
                        finish();
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
        if (viewId == R.id.tv_getPsdCode) {
            if (type == 0) {
                mViewModel.getSmsCode(4, phoneNumber2);
            } else {
                mViewModel.getSmsCode(5, phoneNumber2);
            }
        } else if (viewId == R.id.tv_change) {
            //通过支付密码修改手机
            //检查是否设置了支付密码
            progressDialog.onLoading("");
            mViewModel.checkPayPassword();
        } else if (viewId == R.id.btn_sublimt) {
            //点击下一步
            binding.btnSublimt.setEnabled(false);
            final String smsCode = binding.edMsgCode.getText().toString().trim();
            switch (type) {
                case 0:
                    MaterialDialogUtils.showBasicDialog(CheckAuthActivity.this, "继续将解除当前已绑定的手机，是否继续？")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    mViewModel.unBindPhone(smsCode);
                                }
                            })
                            .show();
                    break;
                case 1:
                    mViewModel.checkLoginPsdSmsCode(smsCode);
                    break;
                case 2:
                    mViewModel.checkPayPsdSmsCode(smsCode);
                    break;
            }
        }
    }
}
