package com.mingpinmall.me.ui.api;

import com.mingpinmall.me.ui.bean.UserBean;
import com.socks.library.KLog;

import me.goldze.common.base.mvvm.base.BaseRepository;
import me.goldze.common.http.RetrofitClient;
import me.goldze.common.http.rx.RxSchedulers;
import me.goldze.common.http.rx.RxSubscriber;

public class UserRepository extends BaseRepository {

    private UserApiService apiService = RetrofitClient.getInstance().create(UserApiService.class);

    protected void login(String phone, String password) {
        addDisposable(apiService.login(phone, password)
                .compose(RxSchedulers.<UserBean>io_main())
                .subscribeWith(new RxSubscriber<UserBean>() {
                    @Override
                    public void onSuccess(UserBean result) {
                        sendData("EVENT_KEY_WORK_MORE", result);
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                    }
                })
        );
    }


}
