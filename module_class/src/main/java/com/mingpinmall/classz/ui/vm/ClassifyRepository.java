package com.mingpinmall.classz.ui.vm;

import com.goldze.common.dmvvm.base.mvvm.base.BaseRepository;
import com.goldze.common.dmvvm.base.mvvm.stateview.StateConstants;
import com.goldze.common.dmvvm.http.RetrofitClient;
import com.goldze.common.dmvvm.http.rx.RxSchedulers;
import com.goldze.common.dmvvm.http.rx.RxSubscriber;
import com.mingpinmall.classz.constants.Constants;
import com.mingpinmall.classz.ui.vm.api.ClassifyService;
import com.mingpinmall.classz.ui.vm.bean.BrandListInfo;
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
                        showPageState(Constants.EVENT_KEY_CLASSIFY_MORE_STATE, StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    protected void onStart() {
                        super.onStart();
                        showPageState(Constants.EVENT_KEY_CLASSIFY_MORE_STATE, StateConstants.LOADING_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        showPageState(Constants.EVENT_KEY_CLASSIFY_MORE_STATE, StateConstants.ERROR_STATE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        KLog.i(e.toString());
                        showPageState(Constants.EVENT_KEY_CLASSIFY_MORE_STATE, StateConstants.ERROR_STATE);
                    }
                })
        );

    }

    /*获取右边的数据*/
    public void getRight(String gcId) {
        addDisposable(apiService.getRight("goods_class", "get_child_all", gcId)
                .compose(RxSchedulers.<ClassificationRighitBean>io_main())
                .subscribeWith(new RxSubscriber<ClassificationRighitBean>() {
                    @Override
                    public void onSuccess(ClassificationRighitBean result) {
                        sendData(Constants.EVENT_KEY_CLASSIFY_MORE_RIGHT, result);
                    }

                    @Override
                    protected void onStart() {
                        super.onStart();
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

    /*获取品牌的数据*/
    public void getRightByBrand() {
        addDisposable(apiService.getRightByBrand()
                .compose(RxSchedulers.<BrandListInfo>io_main())
                .subscribeWith(new RxSubscriber<BrandListInfo>() {
                    @Override
                    public void onSuccess(BrandListInfo result) {
                        sendData(Constants.EVENT_KEY_CLASSIFY_MORE_RIGHT, result);
                        showPageState(Constants.EVENT_KEY_CLASSIFY_MORE_STATE, StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    protected void onStart() {
                        super.onStart();
                        showPageState(Constants.EVENT_KEY_CLASSIFY_MORE_STATE, StateConstants.LOADING_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        showPageState(Constants.EVENT_KEY_CLASSIFY_MORE_STATE, StateConstants.ERROR_STATE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        KLog.i(e.toString());
                        showPageState(Constants.EVENT_KEY_CLASSIFY_MORE_STATE, StateConstants.ERROR_STATE);
                    }
                })
        );

    }


    /*商品列表*/
    public void getShappingList(String bId, String curpage, String keyword, final String typeId) {
        Map<String, Object> map = new HashMap<>();
        map.put("gc_id", bId);
        map.put("Key", 1);
        map.put("keyword", keyword);
        map.put("page", Constants.PAGE_RN);
        map.put("curpage", curpage);
        addDisposable(apiService.getShappingList(map)
                .compose(RxSchedulers.<GoodsListInfo>io_main())
                .subscribeWith(new RxSubscriber<GoodsListInfo>() {
                    @Override
                    public void onSuccess(GoodsListInfo result) {
                        sendData(Constants.PRODUCTS_EVENT_KEY, typeId, result);
                        showPageState(Constants.PRODUCTS_EVENT_KEY_LIST_STATE, typeId, StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        showPageState(Constants.PRODUCTS_EVENT_KEY_LIST_STATE, typeId, StateConstants.ERROR_STATE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        showPageState(Constants.PRODUCTS_EVENT_KEY_LIST_STATE, typeId, StateConstants.ERROR_STATE);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.PRODUCTS_EVENT_KEY_LIST_STATE, typeId, StateConstants.NET_WORK_STATE);
                    }
                })
        );

    }


}
