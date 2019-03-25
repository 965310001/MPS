package com.mingpinmall.classz.ui.vm;

import com.goldze.common.dmvvm.base.mvvm.base.BaseRepository;
import com.goldze.common.dmvvm.http.RetrofitClient;
import com.goldze.common.dmvvm.http.rx.RxSchedulers;
import com.goldze.common.dmvvm.http.rx.RxSubscriber;
import com.mingpinmall.classz.ui.Constants;
import com.mingpinmall.classz.ui.vm.bean.ClassificationBean;
import com.socks.library.KLog;

public class ClassifyRepository extends BaseRepository {

    ClassifyService apiService = RetrofitClient.getInstance().create(ClassifyService.class);

    /*获取左边的数据*/
    public void getLeft() {
        addDisposable(apiService.getLeft()
                .compose(RxSchedulers.<ClassificationBean>io_main())
                .subscribeWith(new RxSubscriber<ClassificationBean>() {
                    @Override
                    public void onSuccess(ClassificationBean result) {
                        sendData(Constants.EVENT_KEY_CLASSIFY_MORE, result);
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

    public void getRight(String gcId) {
        addDisposable(apiService.getRight(gcId)
                .compose(RxSchedulers.<ClassificationBean>io_main())
                .subscribeWith(new RxSubscriber<ClassificationBean>() {
                    @Override
                    public void onSuccess(ClassificationBean result) {
//                        sendData(Constants.EVENT_KEY_CLASSIFY_MORE, result);
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
