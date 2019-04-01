package com.mingpinmall.me.ui.api;

import com.goldze.common.dmvvm.base.mvvm.base.BaseRepository;
import com.goldze.common.dmvvm.http.RetrofitClient;
import com.goldze.common.dmvvm.http.rx.RxSchedulers;
import com.goldze.common.dmvvm.http.rx.RxSubscriber;
import com.mingpinmall.me.ui.bean.CodeKeyMode;
import com.mingpinmall.me.ui.bean.UserBean;
import com.mingpinmall.me.ui.constants.Constants;
import com.socks.library.KLog;

public class UserRepository extends BaseRepository {

    private UserApiService apiService = RetrofitClient.getInstance().create(UserApiService.class);

    protected void login(String phone, String password, int login_type, String captcha, String codekey) {
        addDisposable(apiService.login(phone, password, login_type, captcha, codekey, "android")
                .compose(RxSchedulers.<UserBean>io_main())
                .subscribeWith(new RxSubscriber<UserBean>() {
                    @Override
                    public void onSuccess(UserBean result) {
                        sendData(Constants.EVENT_KEY_USER_GETUSER, result);
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData(Constants.Err_EVENT_KEY_USER_GETUSER, msg);
                    }
                })
        );
    }

    /*获取创建验证码图片的key*/
    protected void makeCodeKey() {
        addDisposable(apiService.makeCodeKey()
                .compose(RxSchedulers.<CodeKeyMode>io_main())
                .subscribeWith(new RxSubscriber<CodeKeyMode>() {
                    @Override
                    public void onSuccess(CodeKeyMode result) {
                        sendData(Constants.EVENT_KEY_GETCODEKEY, result);
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData(Constants.Err_EVENT_KEY_GETCODEKEY, msg);
                    }

                })
        );
    }

}
