package com.mingpinmall.me.ui.api;

import com.goldze.common.dmvvm.base.bean.BaseBean;
import com.goldze.common.dmvvm.base.bean.BaseNothingBean;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.bean.UserBean;
import com.mingpinmall.me.ui.bean.DefaultCheckBean;
import com.mingpinmall.me.ui.bean.SmsBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author 小斌
 */
public interface UserApiService {

    /**
     * 登录
     */
    String LOGIN = "/mo_bile/index.php?app=login";

    /**
     * 短信验证码
     */
    String SENDSMS = "/mo_bile/index.php?app=connect&wwi=get_sms_captcha";

    /**
     * 验证 登录密码短信验证码
     */
    String CHECKLOGINPSDSMSCODE = "/mo_bile/index.php?app=member_account&wwi=modify_password_step3";
    /**
     * 验证 支付密码手机短信验证码
     */
    String CHECKPAYPSDSMSCODE = "/mo_bile/index.php?app=member_account&wwi=modify_paypwd_step3";

    /**
     * 验证 更改绑定手机短信验证码
     */
    String UNBINDPHONE = "/mo_bile/index.php?app=member_account&wwi=modify_mobile_step3";
    /**
     * 验证 更改绑定手机短信验证码
     */
    String BINDPHONE = "/mo_bile/index.php?app=member_account&wwi=bind_mobile_step2";

    /**
     * 检测是否设置了支付密码
     */
    String CHECKPAYPASSWORD = "/mo_bile/index.php?app=member_account&wwi=get_paypwd_info";
    /**
     * 修改登陆密码
     */
    String RESTPASSWORD = "/mo_bile/index.php?app=member_account&wwi=modify_password_step5";
    /**
     * 修改支付密码
     */
    String RESTPAYPASSWORD = "/mo_bile/index.php?app=member_account&wwi=modify_paypwd_step5";

    @FormUrlEncoded
    @POST(UNBINDPHONE)
    Flowable<BaseNothingBean> unBindPhone(
            @Field("auth_code") String authCode,
            @Field("key") String key
    );

    /**
     * 绑定手机
     * 描述：绑定手机短信验证码验证
     * 请求地址：https://www.mingpinmall.cn/mo_bile/index.php?app=member_account&wwi=bind_mobile_step2
     * 请求方式：post
     * 请求参数：
     * auth_code：短信验证码
     * key:用户key
     **/
    @FormUrlEncoded
    @POST(BINDPHONE)
    Flowable<BaseNothingBean> bindPhone(
            @Field("auth_code") String authCode,
            @Field("_client") String client,
            @Field("key") String key
    );

    /**
     * 修改登陆密码
     * @param password
     * @param password1
     * @param client
     * @param key
     * @return
     */
    @FormUrlEncoded
    @POST(RESTPASSWORD)
    Flowable<BaseResponse<BaseBean>> resetPassword(
            @Field("password") String password,
            @Field("password1") String password1,
            @Field("_client") String client,
            @Field("key") String key
    );

    /**
     * 修改支付密码
     * @param password
     * @param password1
     * @param client
     * @param key
     * @return
     */
    @FormUrlEncoded
    @POST(RESTPAYPASSWORD)
    Flowable<BaseResponse<BaseBean>> resetPayPassword(
            @Field("password") String password,
            @Field("password1") String password1,
            @Field("_client") String client,
            @Field("key") String key
    );

    /**
     * 验证 短信验证码 登陆密码修改
     * @param code
     * @param key
     * @param client
     * @param _client
     * @return
     */
    @FormUrlEncoded
    @POST(CHECKLOGINPSDSMSCODE)
    Flowable<BaseNothingBean> checkLoginPsdSmsCode(
            @Field("auth_code") String code,
            @Field("key") String key,
            @Field("client") String client,
            @Field("_client") String device
    );

    /**
     * 验证 短信验证码 支付密码修改
     * @param code
     * @param key
     * @param client
     * @param _client
     * @return
     */
    @FormUrlEncoded
    @POST(CHECKPAYPSDSMSCODE)
    Flowable<BaseNothingBean> checkPayPsdSmsCode(
            @Field("auth_code") String code,
            @Field("key") String key,
            @Field("client") String client,
            @Field("_client") String device
    );

    /**
     * 登录
     * @param userName
     * @param password
     * @param loginType
     * @param Client
     * @return
     */
    @FormUrlEncoded
    @POST(LOGIN)
    Flowable<BaseResponse<UserBean>> login(
            @Field("username") String userName,
            @Field("password") String password,
            @Field("login_type") int loginType,//1:表示手机验证码登录 2:表示密码登录
            @Field("client") String Client//android/ios
    );

    /**
     * 发送短信验证码
     * @param type 1为注册、登录 3为找回密码 4 绑定手机 5 支付密码、修改密码
     * @param phone
     * @param client
     * @return
     */
    @GET(SENDSMS)
    Flowable<BaseResponse<SmsBean>> sendSMS(
            @Query("type") int type,
            @Query("phone") String phone,
            @Query("client") String client
    );

    /**
     * 检查是否设置了 支付密码
     * @param key
     * @return
     */
    @GET(CHECKPAYPASSWORD)
    Flowable<BaseResponse<DefaultCheckBean>> checkPayPassword(
            @Query("key") String key
    );

}
