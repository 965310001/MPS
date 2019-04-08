package com.mingpinmall.me.ui.api;

import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.bean.CheckSmsCodeBean;
import com.mingpinmall.me.ui.bean.CodeKeyMode;
import com.mingpinmall.me.ui.bean.SmsBean;
import com.goldze.common.dmvvm.base.bean.UserBean;

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
    /*短信验证码*/
    String SENDSMS = "/mo_bile/index.php?app=connect&wwi=get_sms_captcha";
    /*短信验证码 验证*/
    String CheckCode = "/mo_bile/index.php?app=member_account&wwi=modify_password_step3";

    /*短信验证码 验证*/
    @FormUrlEncoded
    @POST(CheckCode)
    Flowable<CheckSmsCodeBean> checkCode(@Field("auth_code") String code,
                                         @Field("key") String key
    );

    /*登录*/
    @FormUrlEncoded
    @POST(UserApiService.LOGIN)
    Flowable<BaseResponse<UserBean>> login(@Field("username") String userName,
                                           @Field("password") String password,
                                           @Field("login_type") int loginType,//1:表示手机验证码登录 2:表示密码登录
                                           @Field("client") String Client//android/ios
    );

    /*发送短信验证码*/
    @GET(UserApiService.SENDSMS)
    Flowable<BaseResponse<SmsBean>> sendSMS(@Query("type") int type,//短信类型:1为注册、登录,3为找回密码
                                            @Query("phone") String phone
    );

    /*获取和生成图形验证码钥匙*/
    @GET(UserApiService.MAKECODEKEY)
    Flowable<BaseResponse<CodeKeyMode>> makeCodeKey();

}
