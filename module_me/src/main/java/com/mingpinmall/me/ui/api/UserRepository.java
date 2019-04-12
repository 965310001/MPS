package com.mingpinmall.me.ui.api;

import com.goldze.common.dmvvm.base.bean.BaseBean;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.bean.CheckSmsCodeBean;
import com.goldze.common.dmvvm.base.mvvm.base.BaseRepository;
import com.goldze.common.dmvvm.http.RetrofitClient;
import com.goldze.common.dmvvm.http.rx.RxSchedulers;
import com.goldze.common.dmvvm.http.rx.RxSubscriber;
import com.mingpinmall.me.ui.bean.CodeKeyMode;
import com.mingpinmall.me.ui.bean.DefaultCheckBean;
import com.mingpinmall.me.ui.bean.SmsBean;
import com.goldze.common.dmvvm.base.bean.UserBean;
import com.mingpinmall.me.ui.constants.Constants;
import com.socks.library.KLog;

public class UserRepository extends BaseRepository {

    private UserApiService apiService = RetrofitClient.getInstance().create(UserApiService.class);

    /**
     * 重设登陆密码
     *
     * @param password
     * @param password1
     */
    protected void resetPassword(String password, String password1) {
        addDisposable(apiService.resetPassword(password, password1, "android", getUserKey())
                .compose(RxSchedulers.<BaseResponse<BaseBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<BaseBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<BaseBean> baseBeanBaseResponse) {
                        if (baseBeanBaseResponse.isSuccess()) {
                            sendData("RESET_PASSWORD", "success", "");
                        } else {
                            sendData("RESET_PASSWORD", "fail", "");
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("RESET_PASSWORD", "err", msg);
                    }
                })
        );
    }

    /**
     * 重设支付密码
     *
     * @param password
     * @param password1
     */
    protected void resetPayPassword(String password, String password1) {
        addDisposable(apiService.resetPayPassword(password, password1, "android", getUserKey())
                .compose(RxSchedulers.<BaseResponse<BaseBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<BaseBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<BaseBean> baseBeanBaseResponse) {
                        if (baseBeanBaseResponse.isSuccess()) {
                            sendData("RESET_PASSWORD", "success", "");
                        } else {
                            sendData("RESET_PASSWORD", "fail", "");
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("RESET_PASSWORD", "err", msg);
                    }
                })
        );
    }

    protected void login(String phone, String password, int login_type) {
        addDisposable(apiService.login(phone, password, login_type, "android")
                .compose(RxSchedulers.<BaseResponse<UserBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<UserBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<UserBean> result) {
                        if (result.isSuccess())
                            sendData(Constants.EVENT_KEY_USER_GETUSER, result.getData());
                        else
                            sendData(Constants.Err_EVENT_KEY_USER_GETUSER, result.getMessage());
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData(Constants.Err_EVENT_KEY_USER_GETUSER, msg);
                    }
                })
        );
    }

    /*验证 修改支付密码前，手机短信验证码是否正确*/
    protected void checkPayPsdSmsCode(String code) {
        addDisposable(apiService.checkPayPsdSmsCode(code, getUserKey(), "android", "android")
                .compose(RxSchedulers.<CheckSmsCodeBean>io_main())
                .subscribeWith(new RxSubscriber<CheckSmsCodeBean>() {

                    @Override
                    public void onSuccess(CheckSmsCodeBean result) {
                        sendData("CHECK_CODE", "success", result);
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("CHECK_CODE", "err", msg);
                    }
                })
        );
    }

    /*验证 修改登录密码前，短信验证码是否正确*/
    protected void checkLoginPsdSmsCode(String code) {
        addDisposable(apiService.checkLoginPsdSmsCode(code, getUserKey(), "android", "android")
                .compose(RxSchedulers.<CheckSmsCodeBean>io_main())
                .subscribeWith(new RxSubscriber<CheckSmsCodeBean>() {

                    @Override
                    public void onSuccess(CheckSmsCodeBean result) {
                        sendData("CHECK_CODE", "success", result);
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("CHECK_CODE", "err", msg);
                    }
                })
        );
    }

    /*验证 修改手机前，短信验证码是否正确*/
    protected void checkPhonePsdSmsCode(String code) {
        addDisposable(apiService.checkBindPhoneSmsCode(code, getUserKey(), "android", "android")
                .compose(RxSchedulers.<CheckSmsCodeBean>io_main())
                .subscribeWith(new RxSubscriber<CheckSmsCodeBean>() {

                    @Override
                    public void onSuccess(CheckSmsCodeBean result) {
                        sendData("CHECK_CODE", "success", result);
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("CHECK_CODE", "err", msg);
                    }
                })
        );
    }

    /*验证是否设置了支付密码*/
    protected void checkPayPassword() {
        addDisposable(apiService.checkPayPassword(getUserKey())
                .compose(RxSchedulers.<BaseResponse<DefaultCheckBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<DefaultCheckBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<DefaultCheckBean> result) {
                        if (result.getData().isState())
                            sendData("CHECK_PAY_PASSWORD", "success", "");
                        else
                            sendData("CHECK_PAY_PASSWORD", "fail", result.getMessage());
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("CHECK_PAY_PASSWORD", "err", msg);
                    }
                })
        );
    }

    /*获取创建验证码图片的key*/
    protected void makeCodeKey() {
        addDisposable(apiService.makeCodeKey()
                .compose(RxSchedulers.<BaseResponse<CodeKeyMode>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<CodeKeyMode>>() {
                    @Override
                    public void onSuccess(BaseResponse<CodeKeyMode> result) {
                        sendData(Constants.EVENT_KEY_GETCODEKEY, result.getData());
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData(Constants.Err_EVENT_KEY_GETCODEKEY, msg);
                    }

                })
        );
    }

    /*获取 登录，注册，找回密码 短信验证码*/
    protected void getSmsCode(int type, String phone) {
        addDisposable(apiService.sendSMS(type, phone)
                .compose(RxSchedulers.<BaseResponse<SmsBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<SmsBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<SmsBean> result) {
                        if (result.isSuccess())
                            sendData("GET_SMS_CODE", "success", result.getData());
                        else
                            sendData("GET_SMS_CODE", "err", result.getMessage());
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("GET_SMS_CODE", "err", msg);
                    }

                })
        );
    }

    /*获取 修改密码，修改手机，修改支付密码 短信验证码 */
    protected void getResetSmsCode() {
        addDisposable(apiService.sendResetSMS("android", getUserKey())
                .compose(RxSchedulers.<BaseResponse<SmsBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<SmsBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<SmsBean> result) {
                        if (result.isSuccess())
                            sendData("GET_RESET_SMS_CODE", "success", "");
                        else
                            sendData("GET_RESET_SMS_CODE", "err", result.getMessage());
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("GET_RESET_SMS_CODE", "err", msg == null ? "" : msg);
                    }

                })
        );
    }

}
