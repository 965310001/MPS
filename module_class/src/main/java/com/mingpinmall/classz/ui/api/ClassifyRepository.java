package com.mingpinmall.classz.ui.api;

import android.text.TextUtils;

import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.base.BaseRepository;
import com.goldze.common.dmvvm.base.mvvm.stateview.StateConstants;
import com.goldze.common.dmvvm.http.RetrofitClient;
import com.goldze.common.dmvvm.http.rx.RxSchedulers;
import com.goldze.common.dmvvm.http.rx.RxSubscriber;
import com.mingpinmall.classz.ResultBean;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.AreaListInfo;
import com.mingpinmall.classz.ui.vm.bean.BrandListInfo;
import com.mingpinmall.classz.ui.vm.bean.ClassificationBean;
import com.mingpinmall.classz.ui.vm.bean.ClassificationRighitBean;
import com.mingpinmall.classz.ui.vm.bean.GoodsCommentListBean;
import com.mingpinmall.classz.ui.vm.bean.GoodsDetailInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsListInfo;
import com.mingpinmall.classz.ui.vm.bean.HotKeyInfo;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class ClassifyRepository extends BaseRepository {

    ClassifyService apiService = RetrofitClient.getInstance().create(ClassifyService.class);

    /*获取左边的数据*/
    public void getLeft() {
        addDisposable(apiService.getLeft("goods_class", "index")
                .compose(RxSchedulers.<ClassificationBean>io_main())
                .subscribeWith(new RxSubscriber<ClassificationBean>() {
                    @Override
                    public void onSuccess(ClassificationBean result) {
                        sendData(Constants.EVENT_KEY_CLASSIFY_MORE[0], result);
                        showPageState(Constants.EVENT_KEY_CLASSIFY_MORE[1], StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    protected void onStart() {
                        super.onStart();
                        showPageState(Constants.EVENT_KEY_CLASSIFY_MORE[1], StateConstants.LOADING_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        showPageState(Constants.EVENT_KEY_CLASSIFY_MORE[1], StateConstants.ERROR_STATE);
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
                        sendData(Constants.EVENT_KEY_CLASSIFY_MORE[2], result);
                    }

                    @Override
                    protected void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
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
                                sendData(Constants.EVENT_KEY_CLASSIFY_MORE[2], result);
//                        showPageState(Constants.EVENT_KEY_CLASSIFY_MORE[1], StateConstants.SUCCESS_STATE);
                            }

                            @Override
                            protected void onStart() {
                                super.onStart();
//                        showPageState(Constants.EVENT_KEY_CLASSIFY_MORE[1], StateConstants.LOADING_STATE);
                            }

                            @Override
                            public void onFailure(String msg) {
                                KLog.i(msg);
//                        showPageState(Constants.EVENT_KEY_CLASSIFY_MORE[1], StateConstants.ERROR_STATE);
                            }
                        })
        );

    }

    /*商品列表*/
    public void getShappingList(String bId, String curpage, String keyword, final String typeId,
                                String areaId, String priceFrom, String priceTo,
                                String key, String order, String ci, String st) {
        Map<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(bId)) {
            map.put("0".equals(typeId) ? "gc_id" : "b_id", bId);
        } else if (!TextUtils.isEmpty(keyword)) {
            map.put("keyword", keyword);
        }
        if (!TextUtils.isEmpty(ci)) map.put("ci", ci);
        if (!TextUtils.isEmpty(st)) map.put("st", st);
        if (!TextUtils.isEmpty(areaId)) map.put("area_id", areaId);//地区
        map.put("price_from", priceFrom);//价格区间最低范围
        map.put("price_to", priceTo);// 价格区间最高范围
        if (!TextUtils.isEmpty(key)) map.put("key", key);/*使用排序*/
        if (!TextUtils.isEmpty(order)) map.put("order", order);/*使用排序*/
        for (String s : st.split("_")) map.put(s, "1");
        map.put("page", Constants.PAGE_RN);
        map.put("curpage", curpage);
        addDisposable(apiService.getShappingList(map)
                .compose(RxSchedulers.<GoodsListInfo>io_main())
                .subscribeWith(new RxSubscriber<GoodsListInfo>() {
                    @Override
                    public void onSuccess(GoodsListInfo result) {
                        sendData(Constants.PRODUCTS_EVENT_KEY[0], typeId, result);
                        showPageState(Constants.PRODUCTS_EVENT_KEY[1], typeId, StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        showPageState(Constants.PRODUCTS_EVENT_KEY[1], typeId, StateConstants.ERROR_STATE);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.PRODUCTS_EVENT_KEY[1], typeId, StateConstants.NET_WORK_STATE);
                    }
                })
        );

    }

    /*商品详情*/
    public void getGoodsDetail(String goodsId) {
        addDisposable(apiService.getGoodsDetail(goodsId)
                .compose(RxSchedulers.<GoodsDetailInfo>io_main())
                .subscribeWith(new RxSubscriber<GoodsDetailInfo>() {
                    @Override
                    public void onSuccess(GoodsDetailInfo result) {
                        sendData(Constants.GOODSDETAIL_EVENT_KEY[0], result);
                        showPageState(Constants.GOODSDETAIL_EVENT_KEY[1], StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        showPageState(Constants.GOODSDETAIL_EVENT_KEY[1], StateConstants.ERROR_STATE);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.GOODSDETAIL_EVENT_KEY[1], StateConstants.NET_WORK_STATE);
                    }
                })
        );

    }

    /*搜索列表*/
    public void getHotKeys() {
        addDisposable(apiService.getHotKeys(parames(ClassifyService.HOTKEY[0], ClassifyService.HOTKEY[1]))
                        .compose(RxSchedulers.<BaseResponse<HotKeyInfo>>io_main())
                        .subscribeWith(new RxSubscriber<BaseResponse<HotKeyInfo>>() {
                            @Override
                            public void onSuccess(BaseResponse<HotKeyInfo> result) {
                                sendData(Constants.SEARCH_EVENT_KEY[0], result);
//                        showPageState(Constants.SEARCH_EVENT_KEY_STATE, StateConstants.SUCCESS_STATE);
                            }

                            @Override
                            public void onFailure(String msg) {
                                showPageState(Constants.SEARCH_EVENT_KEY[1], StateConstants.ERROR_STATE);
                            }

                            @Override
                            protected void onNoNetWork() {
                                super.onNoNetWork();
                                showPageState(Constants.SEARCH_EVENT_KEY[1], StateConstants.NET_WORK_STATE);
                            }
                        })
        );

    }

    /*评价列表*/
    public void getEvaluate(String gId, String type, String curpage) {
        Map<String, Object> map = new HashMap<>();
        map.put("goods_id", gId);
        map.put("type", type);
        map.put("page", Integer.parseInt(Constants.PAGE_RN) * 2);
        map.put("curpage", curpage);
        addDisposable(apiService.getEvaluate(map)
                .compose(RxSchedulers.<GoodsCommentListBean>io_main())
                .subscribeWith(new RxSubscriber<GoodsCommentListBean>() {
                    @Override
                    public void onSuccess(GoodsCommentListBean result) {
                        sendData(Constants.EVALUATE_EVENT_KEY[0], result);
                        showPageState(Constants.EVALUATE_EVENT_KEY[1], StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        showPageState(Constants.EVALUATE_EVENT_KEY[1], StateConstants.ERROR_STATE);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.EVALUATE_EVENT_KEY[1], StateConstants.NET_WORK_STATE);
                    }
                })
        );

    }

    /*获取订单信息*/
    public void getOrderInfo(String cartId, final Object eventKey) {
        Map<String, Object> map = parames("", "");
        map.put("", cartId);
        addDisposable(apiService.getOrderInfo(map)
                .compose(RxSchedulers.<BaseResponse<HotKeyInfo>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<HotKeyInfo>>() {
                    @Override
                    public void onSuccess(BaseResponse<HotKeyInfo> result) {
                        sendData(eventKey, result);
                        showPageState(Constants.CONFIRMORDER_KEY[1], StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        showPageState(Constants.CONFIRMORDER_KEY[1], StateConstants.ERROR_STATE);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.CONFIRMORDER_KEY[1], StateConstants.NET_WORK_STATE);
                    }
                }));
    }

    /*省 市 区*/
    public void getArea(String areaId) {
        Map<String, Object> map = parames("area_list", "area_list");
        map.put("area_id", areaId);
        addDisposable(apiService.getArea(map)
                        .compose(RxSchedulers.<BaseResponse<AreaListInfo>>io_main())
                        .subscribeWith(new RxSubscriber<BaseResponse<AreaListInfo>>() {
                            @Override
                            public void onSuccess(BaseResponse<AreaListInfo> result) {
                                sendData(Constants.AREA_LIST_EVENT_KEY, result);
//                        showPageState(Constants.EVALUATE_EVENT_KEY[1], StateConstants.SUCCESS_STATE);
                            }

                            @Override
                            public void onFailure(String msg) {
                                KLog.i(msg);
//                        showPageState(Constants.EVALUATE_EVENT_KEY[1], StateConstants.ERROR_STATE);
                            }

                            @Override
                            protected void onNoNetWork() {
                                super.onNoNetWork();
//                                showPageState(Constants.EVALUATE_EVENT_KEY[1], StateConstants.NET_WORK_STATE);
                            }
                        })
        );

    }


    /*通用*/
    public void execute(String app, String wwi, final Object eventKey, Map<String, Object> map) {
        addDisposable(apiService.execute(app, wwi, map)
                        .compose(RxSchedulers.<ResultBean>io_main())
                        .subscribeWith(new RxSubscriber<ResultBean>() {
                            @Override
                            public void onSuccess(ResultBean result) {
                                sendData(eventKey, result);
//                        showPageState(eventStateKey, StateConstants.SUCCESS_STATE);
                            }

                            @Override
                            public void onFailure(String msg) {
                                KLog.i(msg);
//                        showPageState(eventStateKey, StateConstants.ERROR_STATE);
                            }

                            @Override
                            protected void onNoNetWork() {
                                super.onNoNetWork();
                                KLog.i("onNoNetWork");
//                        showPageState(eventStateKey, StateConstants.NET_WORK_STATE);
                            }
                        })
        );
    }


    Map<String, Object> map = new HashMap<>();

    Map<String, Object> parames(Object app, Object wwi) {
        map.put("app", app);
        map.put("wwi", wwi);
        return map;
    }

}
