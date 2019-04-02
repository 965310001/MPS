package com.mingpinmall.me.ui.api;

import android.app.Application;
import android.support.annotation.NonNull;

import com.goldze.common.dmvvm.base.mvvm.AbsViewModel;


public class UserViewModel extends AbsViewModel<UserRepository> {

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public void login(String phone, String password, int login_type) {
        mRepository.login(phone, password, login_type);
    }

    public void makeCodeKey() {
        mRepository.makeCodeKey();
    }

    public void getSmsCode(int type, String phone) {
        mRepository.getSmsCode(type, phone);
    }

}