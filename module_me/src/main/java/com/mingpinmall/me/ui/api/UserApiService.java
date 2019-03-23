package com.mingpinmall.me.ui.api;

import com.mingpinmall.me.ui.bean.UserBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserApiService {

    /*登录*/
    String LOGIN = "/v2/ecapi.auth.signin";

    /*登录*/
    @FormUrlEncoded
    @POST(UserApiService.LOGIN)
    Flowable<UserBean> login(@Field("username") String userName,
                             @Field("password") String password);

    /*注册*/
    /*重置密码*/
}
