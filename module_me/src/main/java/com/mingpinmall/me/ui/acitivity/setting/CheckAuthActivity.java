package com.mingpinmall.me.ui.acitivity.setting;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.dialog.TextDialog;
import com.goldze.common.dmvvm.widget.loading.CustomProgressDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityResetCheckBinding;
import com.mingpinmall.me.ui.api.UserViewModel;
import com.mingpinmall.me.ui.bean.SmsBean;
import com.mingpinmall.me.ui.constants.Constants;
import com.xuexiang.xui.utils.CountDownButtonHelper;

import static com.goldze.common.dmvvm.constants.ARouterConfig.SUCCESS;

/**
 * 功能描述：重设密码
 *
 * @author 小斌
 * @date 2019/4/2
 **/
@Route(path = ARouterConfig.Me.CHECKAUTHACTIVITY)
public class CheckAuthActivity extends AbsLifecycleActivity<ActivityResetCheckBinding, UserViewModel> {

    public final static int RESET_PHONE = 0;
    public final static int RESET_PASSWORD = 1;
    public final static int RESET_PAY_PASSWORD = 2;

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
        switch (type) {
            case 0:
                setTitle(R.string.title_resetPhoneActivity);
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

    /**
     * 设置监听
     */
    private void setListener() {
        CountDownButtonHelper buttonHelper = new CountDownButtonHelper(binding.tvGetPsdCode, 60);
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
        registerObserver(Constants.CHECK_PAY_PASSWORD, String.class).observeForever(result -> {
            CustomProgressDialog.stop();
            if (result.equals(SUCCESS)) {
                //  设置了支付密码，跳转到使用支付密码设置手机
            } else if ("获取设置失败".equals(result)) {
                CustomProgressDialog.stop();
            } else {
                //没设置支付密码，跳转到设置支付密码
                TextDialog.showBaseDialog(activity,
                        "提示",
                        getString(R.string.dialog_text_tips),
                        () -> {
                            //跳转操作放在这
                            //这里直接销毁这个界面，返回个人设置界面
                            finish();
                        }
                ).show();
            }

        });
        registerObserver(Constants.GET_SMS_CODE, Object.class).observeForever(result -> {
            if (result instanceof SmsBean) {
                ToastUtils.showShort("动态码已发送");
            } else {
                ToastUtils.showShort(result.toString());
            }
        });
        registerObserver(Constants.CHECK_CODE, String.class).observeForever(result -> {
            if (result.equals(SUCCESS)) {
                //验证成功
                switch (type) {
                    case 0:
                        ActivityToActivity.toActivity(ARouterConfig.Me.BINDPHONEACTIVITY);
                        break;
                    case 1:
                        ActivityToActivity.toActivity(ARouterConfig.Me.RESETPASSWORDACTIVITY, "type", 0);
                        break;
                    case 2:
                        ActivityToActivity.toActivity(ARouterConfig.Me.RESETPASSWORDACTIVITY, "type", 1);
                        break;
                    default:
                        break;
                }
                finish();
            } else {
                ToastUtils.showShort(result);
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
            CustomProgressDialog.show(activity);
            mViewModel.checkPayPassword();
        } else if (viewId == R.id.btn_sublimt) {
            //点击下一步
            binding.btnSublimt.setEnabled(false);
            final String smsCode = binding.edMsgCode.getText().toString().trim();
            switch (type) {
                case 0:
                    TextDialog.showBaseDialog(activity,
                            "提示",
                            "继续将解除当前已绑定的手机，是否继续？",
                            () -> mViewModel.unBindPhone(smsCode)).show();
                    break;
                case 1:
                    mViewModel.checkLoginPsdSmsCode(smsCode);
                    break;
                case 2:
                    mViewModel.checkPayPsdSmsCode(smsCode);
                    break;
                default:
                    break;
            }
        }
    }
}
