package com.mingpinmall.classz.ui.vm;

import com.goldze.common.dmvvm.base.mvvm.base.BaseRepository;
import com.goldze.common.dmvvm.http.RetrofitClient;
import com.goldze.common.dmvvm.http.rx.RxSchedulers;
import com.goldze.common.dmvvm.http.rx.RxSubscriber;
import com.mingpinmall.classz.ui.Constants;
import com.mingpinmall.classz.ui.vm.api.ClassifyService;
import com.mingpinmall.classz.ui.vm.bean.ClassificationBean;
import com.mingpinmall.classz.ui.vm.bean.ClassificationRighitBean;
import com.mingpinmall.classz.ui.vm.bean.GoodsListInfo;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

public class ClassifyRepository extends BaseRepository {

    ClassifyService apiService = RetrofitClient.getInstance().create(ClassifyService.class);

    /*获取左边的数据*/
    public void getLeft() {
        addDisposable(apiService.getLeft("goods_class", "index")
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
        addDisposable(apiService.getRight("goods_class", "get_child_all", gcId)
                .compose(RxSchedulers.<ClassificationRighitBean>io_main())
                .subscribeWith(new RxSubscriber<ClassificationRighitBean>() {
                    @Override
                    public void onSuccess(ClassificationRighitBean result) {
                        sendData(Constants.EVENT_KEY_CLASSIFY_MORE_RIGHT, result);
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        KLog.i(e.toString());
                    }
                })
        );

    }

    /*商品列表*/
    public void getShappingList(String bId, String curpage, String keyword) {
        Map<String, Object> map = new HashMap<>();
        map.put("gc_id", bId);
        map.put("Key", 1);
        map.put("keyword", keyword);
        map.put("page", 10);
        map.put("curpage", curpage);
        addDisposable(apiService.getShappingList(map)
                .compose(RxSchedulers.<GoodsListInfo>io_main())
                .subscribeWith(new RxSubscriber<GoodsListInfo>() {
                    @Override
                    public void onSuccess(GoodsListInfo result) {
                        sendData(Constants.PRODUCTS_EVENT_KEY, result);
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        KLog.i(e.toString());
                    }
                })
        );

    }


}
