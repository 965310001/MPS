package com.mingpinmall.me.ui.acitivity.account;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.progress.ProgressDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityLoginBinding;
import com.mingpinmall.me.ui.api.UserViewModel;
import com.mingpinmall.me.ui.bean.SmsBean;
import com.goldze.common.dmvvm.base.bean.UserBean;
import com.mingpinmall.me.ui.constants.Constants;
import com.xuexiang.xui.utils.CountDownButtonHelper;


/**
 * 功能描述：登陆界面
 * 创建人：小斌
 * 创建时间: 2019/3/25
 */
@Route(path = ARouterConfig.LOGINACTIVITY)
public class LoginActivity extends AbsLifecycleActivity<ActivityLoginBinding, UserViewModel> implements TextWatcher {

    private ProgressDialog progressDialog;
    private CountDownButtonHelper buttonHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTitle(R.string.title_loginActivity);
        progressDialog = ProgressDialog.initNewDialog(getSupportFragmentManager());

        TabLayout.Tab tab = binding.tabs.newTab();
        tab.setText(R.string.tabs_login_puk);
        tab.setTag("puk");

        TabLayout.Tab tab2 = binding.tabs.newTab();
        tab2.setText(R.string.tabs_login_phone);
        tab2.setTag("psd");

        binding.tabs.addTab(tab);
        binding.tabs.addTab(tab2);

        binding.edPassword.addTextChangedListener(this);
        binding.edPhone.addTextChangedListener(this);
        binding.edMsgCode.addTextChangedListener(this);

        binding.tvProtocol.setOnClickListener(this);
        binding.tvGetPsdCode.setOnClickListener(this);

        binding.cbAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setEnabled();
            }
        });
        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getTag().toString()) {
                    case "puk":
                        binding.llPanel0.setVisibility(View.VISIBLE);
                        binding.llPanel1.setVisibility(View.GONE);
                        break;
                    case "psd":
                        binding.llPanel0.setVisibility(View.GONE);
                        binding.llPanel1.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        buttonHelper = new CountDownButtonHelper(binding.tvGetPsdCode, 60);
        buttonHelper.setOnCountDownListener(new CountDownButtonHelper.OnCountDownListener() {
            @Override
            public void onCountDown(int time) {
                binding.tvGetPsdCode.setEnabled(false);
                binding.tvGetPsdCode.setText(time + "s");
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
            progressDialog.onLoading("");
            mViewModel.login(
                    binding.edPhone.getText().toString().trim(),//用户名
                    binding.tabs.getSelectedTabPosition() == 0 ? binding.edMsgCode.getText().toString().trim()//密码或手机验证码
                            : binding.edPassword.getText().toString().trim(),
                    binding.tabs.getSelectedTabPosition() == 0 ? 1 : 2//1:表示手机验证码登录 2:表示密码登录
            );
        } else if (viewId == R.id.tv_getPsdCode) {
            /*获取登陆短信动态码*/
            mViewModel.getSmsCode(1, binding.edPhone.getText().toString().trim());
        } else if (viewId == R.id.tv_protocol) {
            /*阅读用户注册协议*/
            ActivityToActivity.toWebView("http://39.108.254.185/wap/tmpl/member/document.html");
        }

    }

    @Override
    protected void dataObserver() {
        registerObserver(Constants.EVENT_KEY_USER_GETUSER, UserBean.class)
                .observeForever(new Observer<UserBean>() {
                    @Override
                    public void onChanged(@Nullable UserBean userBean) {
                        SharePreferenceUtil.saveUser(userBean);
                        progressDialog.onComplete("", new ProgressDialog.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                LiveBus.getDefault().postEvent("LoginSuccess", true);
                                finish();
                            }
                        });
                    }
                });
        registerObserver(Constants.Err_EVENT_KEY_USER_GETUSER, String.class)
                .observeForever(new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String msg) {
                        progressDialog.onFail(msg, 1500);
                    }
                });
        registerObserver("GET_SMS_CODE", "success")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        ToastUtils.showShort("验证码已发送");
                        buttonHelper.start();
                    }
                });
        registerObserver("GET_SMS_CODE", "err")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object msg) {
                        ToastUtils.showShort(msg.toString());
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
        int phoneCount = binding.edPhone.getText().length();
        int msgCodeCount = binding.edMsgCode.getText().length();
        int passwordCount = binding.edPassword.getText().length();

        binding.tvGetPsdCode.setVisibility(phoneCount >= 11 ? View.VISIBLE : View.INVISIBLE);
        editTextAllOK = phoneCount >= 11 && binding.tabs.getSelectedTabPosition() == 0 ? msgCodeCount >= 4 : passwordCount >= 6;
        setEnabled();
    }

    private boolean editTextAllOK = false;

    private void setEnabled() {
        if (binding.cbAgree.isChecked() && editTextAllOK) {
            binding.btnSublimt.setEnabled(true);
        } else {
            binding.btnSublimt.setEnabled(false);
        }
    }
}
