package com.mingpinmall.me.ui.acitivity.account;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.progress.ProgressDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityLoginBinding;
import com.mingpinmall.me.ui.api.UserApiService;
import com.mingpinmall.me.ui.api.UserViewModel;
import com.mingpinmall.me.ui.bean.CodeKeyMode;
import com.mingpinmall.me.ui.bean.UserBean;
import com.mingpinmall.me.ui.constants.Constants;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.banner.widget.banner.base.ImageLoader;
import com.xuexiang.xui.widget.dialog.LoadingDialog;

import java.util.Random;


/**
 * 功能描述：登陆界面
 * 创建人：小斌
 * 创建时间: 2019/3/25
 */
@Route(path = ARouterConfig.LOGINACTIVITY)
public class LoginActivity extends AbsLifecycleActivity<ActivityLoginBinding, UserViewModel> implements TextWatcher {

    private String codeKey = "";
    private boolean lock = false;

    private ProgressDialog progressDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTitle(R.string.title_loginActivity);
        mViewModel.makeCodeKey();
        progressDialog = ProgressDialog.initNewDialog(getSupportFragmentManager());

        TabLayout.Tab tab = binding.tabs.newTab();
        tab.setText(R.string.tabs_login_puk);
        tab.setTag("puk");

        TabLayout.Tab tab2 = binding.tabs.newTab();
        tab2.setText(R.string.tabs_login_phone);
        tab2.setTag("psd");

        binding.tabs.addTab(tab);
        binding.tabs.addTab(tab2);

        binding.edAuthCode.addTextChangedListener(this);
        binding.edPassword.addTextChangedListener(this);
        binding.edPhone.addTextChangedListener(this);
        binding.edMsgCode.addTextChangedListener(this);

        binding.tvProtocol.setOnClickListener(this);
        binding.btnGetPsdCode.setOnClickListener(this);
        binding.ivAuthCode.setOnClickListener(this);
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
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.btn_sublimt) {
            /*登陆*/
            mViewModel.login(
                    binding.edPhone.getText().toString().trim(),//用户名
                    binding.tabs.getSelectedTabPosition() == 0 ? binding.edMsgCode.getText().toString().trim()//密码或手机验证码
                            : binding.edPassword.getText().toString().trim(),
                    binding.tabs.getSelectedTabPosition() == 0 ? 1 : 2,//1:表示手机验证码登录 2:表示密码登录
                    binding.edAuthCode.getText().toString().trim(),//用户输入的验证码(密码登录使用)
                    codeKey//获取验证码的密匙(密码登录使用)
            );
            progressDialog.onLoading("登录中");
        } else if (viewId == R.id.btn_getPsdCode) {
            /*获取登陆短信动态码*/
        } else if (viewId == R.id.iv_authCode) {
            /*获取登陆短信动态码*/
            if (codeKey == null || codeKey.isEmpty()) {
                lock = false;
                mViewModel.makeCodeKey();
            } else {
                lock = true;
                setAuthCodeImage();
            }
        } else if (viewId == R.id.tv_protocol) {
            /*阅读用户注册协议*/
            ActivityToActivity.toWebView("http://39.108.254.185/wap/tmpl/member/document.html");
        }

    }

    @Override
    protected void dataObserver() {
        LiveBus.getDefault()
                .subscribe(Constants.EVENT_KEY_USER_GETUSER, UserBean.class)
                .observeForever(new Observer<UserBean>() {
                    @Override
                    public void onChanged(@Nullable UserBean userBean) {
                        switch (userBean.getCode()) {
                            case 400:
                                progressDialog.onFail(userBean.getDatas().getError(), 1500);
                                break;
                            case 200:
                                SharePreferenceUtil.saveUser(userBean);
                                progressDialog.onComplete("登录成功", 1500, new ProgressDialog.OnDismissListener() {
                                    @Override
                                    public void onDismiss() {
                                        finish();
                                    }
                                });
                                break;
                        }
                    }
                });
        LiveBus.getDefault()
                .subscribe(Constants.Err_EVENT_KEY_USER_GETUSER, String.class)
                .observeForever(new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String msg) {
                        progressDialog.onFail(msg, 1500);
                    }
                });
        LiveBus.getDefault()
                .subscribe(Constants.EVENT_KEY_GETCODEKEY, CodeKeyMode.class)
                .observeForever(new Observer<CodeKeyMode>() {
                    @Override
                    public void onChanged(@Nullable CodeKeyMode userBean) {
                        codeKey = userBean.getDatas().getCodekey();
                        Log.i("验证码KEY", "onChanged: " + codeKey);
                        if (!lock) {
                            setAuthCodeImage();
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        LiveBus.getDefault().clear(Constants.EVENT_KEY_USER_GETUSER);
        LiveBus.getDefault().clear(Constants.Err_EVENT_KEY_USER_GETUSER);
        LiveBus.getDefault().clear(Constants.EVENT_KEY_GETCODEKEY);
        super.onDestroy();
    }

    private void setAuthCodeImage() {
        ImageUtils.loadImage(binding.ivAuthCode, UserApiService.MAKECODE + "&k=" + codeKey + "&t=" + new Random().nextInt());
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
        int psdCount = binding.edPassword.getText().length();
        int authCodeCount = binding.edAuthCode.getText().length();
        switch (binding.tabs.getSelectedTabPosition()) {
            case 0:
                editTextAllOK = phoneCount >= 11 && msgCodeCount > 4;
                break;
            case 1:
                editTextAllOK = phoneCount >= 11 && psdCount >= 4 && authCodeCount >= 4;
                break;
        }
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
