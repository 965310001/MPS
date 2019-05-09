package com.mingpinmall.me.ui.api;

import android.app.Application;
import android.support.annotation.NonNull;

import com.goldze.common.dmvvm.base.mvvm.AbsViewModel;


public class UserViewModel extends AbsViewModel<UserRepository> {

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public void resetPassword(String password, String password1) {
        mRepository.resetPassword(password, password1);
    }

    public void resetPayPassword(String password, String password1) {
        mRepository.resetPayPassword(password, password1);
    }

    public void login(String phone, String password, int login_type) {
        mRepository.login(phone, password, login_type);
    }

    /*支付密码修改时验证短信验证码*/
    public void checkPayPsdSmsCode(String code) {
        mRepository.checkPayPsdSmsCode(code);
    }

    /*登陆密码修改时验证短信验证码*/
    public void checkLoginPsdSmsCode(String code) {
        mRepository.checkLoginPsdSmsCode(code);
    }

    /*检查是否设置了支付密码*/
    public void checkPayPassword() {
        mRepository.checkPayPassword();
    }

    public void getSmsCode(int type, String phone) {
        mRepository.getSmsCode(type, phone);
    }

    /*解除手机绑定*/
    public void unBindPhone(String authCode) {
        mRepository.unBindPhone(authCode);
    }

    /*绑定手机*/
    public void bindPhone(String authCode) {
        mRepository.bindPhone(authCode);
    }

}