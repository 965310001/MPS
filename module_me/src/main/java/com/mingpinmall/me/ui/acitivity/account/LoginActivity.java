package com.mingpinmall.me.ui.acitivity.account;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.goldze.common.dmvvm.base.bean.UserBean;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.loading.CustomProgressDialog;
import com.goldze.common.dmvvm.widget.progress.ProgressDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityLoginBinding;
import com.mingpinmall.me.ui.api.UserViewModel;
import com.mingpinmall.me.ui.bean.SmsBean;
import com.mingpinmall.me.ui.constants.Constants;
import com.xuexiang.xui.utils.CountDownButtonHelper;

import java.util.ArrayList;

/**
 * 功能描述：登陆界面
 *
 * @author 小斌
 * @date 2019/3/25
 */
@Route(path = ARouterConfig.LOGINACTIVITY)
public class LoginActivity extends AbsLifecycleActivity<ActivityLoginBinding, UserViewModel> implements TextWatcher {

    private CountDownButtonHelper buttonHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTitle(R.string.title_loginActivity);

        ArrayList<CustomTabEntity> tabEntityList = new ArrayList<>(2);
        CustomTabEntity tabs1 = new CustomTabEntity() {
            @Override
            public String getTabTitle() {
                return getString(R.string.tabs_login_puk);
            }
            @Override
            public int getTabSelectedIcon() {
                return R.drawable.ic_login_tabs1_sel;
            }
            @Override
            public int getTabUnselectedIcon() {
                return R.drawable.ic_login_tabs1_unsel;
            }
        };
        CustomTabEntity tabs2 = new CustomTabEntity() {
            @Override
            public String getTabTitle() {
                return getString(R.string.tabs_login_phone);
            }
            @Override
            public int getTabSelectedIcon() {
                return R.drawable.ic_login_tabs2_sel;
            }
            @Override
            public int getTabUnselectedIcon() {
                return R.drawable.ic_login_tabs2_unsel;
            }
        };
        tabEntityList.add(tabs1);
        tabEntityList.add(tabs2);
        binding.tabs.setTabData(tabEntityList);
        setListener();
    }

    private void setListener() {
        binding.tabs.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (position) {
                    case 0:
                        binding.llPanel0.setVisibility(View.VISIBLE);
                        binding.llPanel1.setVisibility(View.GONE);
                        break;
                    case 1:
                        binding.llPanel0.setVisibility(View.GONE);
                        binding.llPanel1.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
                setEnabled();
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        binding.edPassword.addTextChangedListener(this);
        binding.edPhone.addTextChangedListener(this);
        binding.edMsgCode.addTextChangedListener(this);

        binding.tvProtocol.setOnClickListener(this);
        binding.tvGetPsdCode.setOnClickListener(this);
        binding.btnSublimt.setOnClickListener(this);

        binding.cbAgree.setOnCheckedChangeListener((buttonView, isChecked) -> setEnabled());

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
        return "";
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.btn_sublimt) {
            /*登陆*/
            CustomProgressDialog.show(activity, "请稍后...");
            mViewModel.login(
                    //用户名
                    binding.edPhone.getText().toString().trim(),
                    //密码或手机验证码
                    binding.tabs.getCurrentTab() == 0 ? binding.edMsgCode.getText().toString().trim()
                            : binding.edPassword.getText().toString().trim(),
                    //1:表示手机验证码登录 2:表示密码登录
                    binding.tabs.getCurrentTab() == 0 ? 1 : 2
            );
        } else if (viewId == R.id.tv_getPsdCode) {
            /*获取登陆短信动态码*/
            mViewModel.getSmsCode(1, binding.edPhone.getText().toString().trim());
        } else if (viewId == R.id.tv_protocol) {
            /*阅读用户注册协议*/
            ActivityToActivity.toWebView("https://www.mingpinmall.cn/wap/tmpl/member/document.html");
        }

    }

    @Override
    protected void dataObserver() {
        registerObserver(Constants.LOGIN, Object.class).observeForever(result -> {
            if (result instanceof UserBean) {
                SharePreferenceUtil.saveUser(result);
                ToastUtils.showShort("登陆成功");
                LiveBus.getDefault().postEvent(ARouterConfig.LOGIN_SUCCESS, true);
                CustomProgressDialog.stop();
                finish();
            } else {
                CustomProgressDialog.stop();
                ToastUtils.showShort(result.toString());
            }
        });
        registerObserver(Constants.GET_SMS_CODE, Object.class).observeForever(result -> {
            if (result instanceof SmsBean) {
                ToastUtils.showShort("验证码已发送");
                buttonHelper.start();
            } else {
                ToastUtils.showShort(result.toString());
            }

        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        setEnabled();
    }

    private void setEnabled() {
        int phoneCount = binding.edPhone.getText().length();

        binding.tvGetPsdCode.setVisibility(phoneCount >= 11 ? View.VISIBLE : View.INVISIBLE);
        boolean editTextAllOK;
        if (binding.tabs.getCurrentTab() == 0) {
            editTextAllOK = phoneCount >= 11 && binding.edMsgCode.getText().length() >= 4;
        } else {
            editTextAllOK = phoneCount >= 4 && binding.edPassword.getText().length() >= 6;
        }
        if (binding.cbAgree.isChecked() && editTextAllOK) {
            binding.btnSublimt.setEnabled(true);
        } else {
            binding.btnSublimt.setEnabled(false);
        }
    }
}
