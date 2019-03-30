package com.mingpinmall.me.ui.api;

import com.mingpinmall.me.ui.bean.CodeKeyMode;
import com.mingpinmall.me.ui.bean.UserBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApiService {

    /*登录*/
    String LOGIN = "/mo_bile/index.php?app=login";
    /*获取和生成图形验证码钥匙*/
    String MAKECODEKEY = "/mo_bile/index.php?app=seccode&wwi=makecodekey";
    /*生成图形验证码*/
    String MAKECODE = "http://39.108.254.185/mo_bile/index.php?app=seccode&wwi=makecode";

    /*登录*/
    @FormUrlEncoded
    @POST(UserApiService.LOGIN)
    Flowable<UserBean> login(@Field("username") String userName,
                             @Field("password") String password,
                             @Field("login_type") int loginType,//1:表示手机验证码登录 2:表示密码登录
                             @Field("captcha") String Captcha,//用户输入的验证码(密码登录使用)
                             @Field("codekey") String Codekey,//获取验证码的密匙(密码登录使用)
                             @Field("client") String Client//android/ios

    );
    /*获取和生成图形验证码钥匙*/
    @GET(UserApiService.MAKECODEKEY)
    Flowable<CodeKeyMode> makeCodeKey();

}
