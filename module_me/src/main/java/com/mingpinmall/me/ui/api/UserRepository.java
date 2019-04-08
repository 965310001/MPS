package com.mingpinmall.me.ui.api;

import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.bean.CheckSmsCodeBean;
import com.goldze.common.dmvvm.base.mvvm.base.BaseRepository;
import com.goldze.common.dmvvm.http.RetrofitClient;
import com.goldze.common.dmvvm.http.rx.RxSchedulers;
import com.goldze.common.dmvvm.http.rx.RxSubscriber;
import com.mingpinmall.me.ui.bean.CodeKeyMode;
import com.mingpinmall.me.ui.bean.SmsBean;
import com.goldze.common.dmvvm.base.bean.UserBean;
import com.mingpinmall.me.ui.constants.Constants;
import com.socks.library.KLog;

public class UserRepository extends BaseRepository {

    private UserApiService apiService = RetrofitClient.getInstance().create(UserApiService.class);

    protected void login(String phone, String password, int login_type) {
        addDisposable(apiService.login(phone, password, login_type, "android")
                .compose(RxSchedulers.<BaseResponse<UserBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<UserBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<UserBean> result) {
                        sendData(Constants.EVENT_KEY_USER_GETUSER, result.getData());
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData(Constants.Err_EVENT_KEY_USER_GETUSER, msg);
                    }
                })
        );
    }

    /*验证短信验证码是否正确*/
    protected void checkCode(String code) {
        addDisposable(apiService.checkCode(code, getUserKey())
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

    /*获取短信验证码*/
    protected void getSmsCode(int type, String phone) {
        addDisposable(apiService.sendSMS(type, phone)
                .compose(RxSchedulers.<BaseResponse<SmsBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<SmsBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<SmsBean> result) {
                        sendData("GET_SMS_CODE", "success", result.getData());
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("GET_SMS_CODE", "err", msg);
                    }

                })
        );
    }

}
