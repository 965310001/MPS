package com.mingpinmall.me.ui.api;

import com.goldze.common.dmvvm.base.bean.BaseBean;
import com.goldze.common.dmvvm.base.bean.BaseNothingBean;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.base.BaseRepository;
import com.goldze.common.dmvvm.http.RetrofitClient;
import com.goldze.common.dmvvm.http.rx.RxSchedulers;
import com.goldze.common.dmvvm.http.rx.RxSubscriber;
import com.mingpinmall.me.ui.bean.DefaultCheckBean;
import com.mingpinmall.me.ui.bean.SmsBean;
import com.goldze.common.dmvvm.base.bean.UserBean;
import com.mingpinmall.me.ui.constants.Constants;
import com.socks.library.KLog;

import static com.goldze.common.dmvvm.constants.ARouterConfig.SUCCESS;

/**
 * @author 小斌
 */
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
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<BaseBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<BaseBean> baseBeanBaseResponse) {
                        if (baseBeanBaseResponse.isSuccess()) {
                            sendData(Constants.RESET_PASSWORD, SUCCESS);
                        } else {
                            sendData(Constants.RESET_PASSWORD, baseBeanBaseResponse.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData(Constants.RESET_PASSWORD, msg == null ? "重设密码失败" : msg);
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
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<BaseBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<BaseBean> baseBeanBaseResponse) {
                        if (baseBeanBaseResponse.isSuccess()) {
                            sendData(Constants.RESET_PASSWORD, SUCCESS);
                        } else {
                            sendData(Constants.RESET_PASSWORD, baseBeanBaseResponse.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.RESET_PASSWORD, msg == null ? "重设支付密码失败" : msg);
                    }
                })
        );
    }

    protected void login(String phone, String password, int login_type) {
        addDisposable(apiService.login(phone, password, login_type, "android")
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<UserBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<UserBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.LOGIN, result.getData());
                        } else {
                            sendData(Constants.LOGIN, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData(Constants.LOGIN, msg == null ? "获取用户信息失败" : msg);
                    }
                })
        );
    }

    /*验证 修改支付密码前，手机短信验证码是否正确*/
    protected void checkPayPsdSmsCode(String code) {
        addDisposable(apiService.checkPayPsdSmsCode(code, getUserKey(), "android", "android")
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {

                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData(Constants.CHECK_CODE, SUCCESS);
                        } else {
                            sendData(Constants.CHECK_CODE, result.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.CHECK_CODE, msg);
                    }
                })
        );
    }

    /*验证 修改登录密码前，短信验证码是否正确*/
    protected void checkLoginPsdSmsCode(String code) {
        addDisposable(apiService.checkLoginPsdSmsCode(code, getUserKey(), "android", "android")
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {
                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData(Constants.CHECK_CODE, SUCCESS);
                        } else {
                            sendData(Constants.CHECK_CODE, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.CHECK_CODE, msg == null ? "验证码错误" : msg);
                    }
                })
        );
    }

    /*验证是否设置了支付密码*/
    protected void checkPayPassword() {
        addDisposable(apiService.checkPayPassword(getUserKey())
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<DefaultCheckBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<DefaultCheckBean> result) {
                        if (result.getData().isState()) {
                            sendData(Constants.CHECK_PAY_PASSWORD, SUCCESS);
                        } else {
                            sendData(Constants.CHECK_PAY_PASSWORD, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.CHECK_PAY_PASSWORD, msg == null ? "获取设置失败" : msg);
                    }
                })
        );
    }

    /*获取 登录，注册，找回密码 短信验证码*/
    protected void getSmsCode(int type, String phone) {
        addDisposable(apiService.sendSMS(type, phone, "android")
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<SmsBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<SmsBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.GET_SMS_CODE, result.getData());
                        } else {
                            sendData(Constants.GET_SMS_CODE, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData(Constants.GET_SMS_CODE, msg == null ? "短信验证码发送失败" : msg);
                    }

                })
        );
    }

    /* 解除手机绑定 */
    protected void unBindPhone(String authCode) {
        addDisposable(apiService.unBindPhone(authCode, getUserKey())
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {
                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData(Constants.CHECK_CODE, SUCCESS);
                        } else {
                            sendData(Constants.CHECK_CODE, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData(Constants.CHECK_CODE, msg == null ? "" : msg);
                    }

                })
        );
    }

    /* 绑定手机 */
    protected void bindPhone(String authCode) {
        addDisposable(apiService.bindPhone(authCode, "android", getUserKey())
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {
                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData(Constants.BIND_PHONE, SUCCESS);
                        } else {
                            sendData(Constants.BIND_PHONE, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData(Constants.BIND_PHONE, msg == null ? "" : msg);
                    }

                })
        );
    }

}
