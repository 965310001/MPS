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
import com.mingpinmall.classz.ui.vm.bean.BrandListInfo;
import com.mingpinmall.classz.ui.vm.bean.BuyStepInfo;
import com.mingpinmall.classz.ui.vm.bean.ClassificationBean;
import com.mingpinmall.classz.ui.vm.bean.ClassificationRighitBean;
import com.mingpinmall.classz.ui.vm.bean.GoodsCommentListBean;
import com.mingpinmall.classz.ui.vm.bean.GoodsDetailInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsListInfo;
import com.mingpinmall.classz.ui.vm.bean.HotKeyInfo;
import com.mingpinmall.classz.ui.vm.bean.InvoiceListInfo;
import com.mingpinmall.classz.ui.vm.bean.MsgInfo;
import com.mingpinmall.classz.ui.vm.bean.MsgListInfo;
import com.mingpinmall.classz.ui.vm.bean.OrderInfo;
import com.mingpinmall.classz.ui.vm.bean.PayMessageInfo;
import com.mingpinmall.classz.ui.vm.bean.StoreInfo;
import com.mingpinmall.classz.ui.vm.bean.StorePromotionInfo;
import com.mingpinmall.classz.ui.vm.bean.VoucherInfo;
import com.socks.library.KLog;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ClassifyRepository extends BaseRepository {

    private final ClassifyService apiService = RetrofitClient.getInstance().create(ClassifyService.class);

    /*获取左边的数据*/
    public void getLeft() {
        addDisposable(apiService.getLeft("goods_class", "index")
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<ClassificationBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<ClassificationBean> result) {
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
                .compose(RxSchedulers.io_main())
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
                        .compose(RxSchedulers.io_main())
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
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<GoodsListInfo>() {
                    @Override
                    public void onSuccess(GoodsListInfo result) {
                        sendData(Constants.PRODUCTS_EVENT_KEY[0], typeId, result);
                        showPageState(Constants.PRODUCTS_EVENT_KEY[1], typeId, StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
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
        addDisposable(apiService.getGoodsDetail(goodsId, getUserKey())
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<GoodsDetailInfo>() {
                    @Override
                    public void onSuccess(GoodsDetailInfo result) {
                        sendData(Constants.GOODSDETAIL_EVENT_KEY[0] + goodsId, result);
                        showPageState(Constants.GOODSDETAIL_EVENT_KEY[1] + goodsId, StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    protected void onStart() {
                        super.onStart();
                        sendData(Constants.GOODSDETAIL_EVENT_KEY[0] + goodsId + "LOADING", true);
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        showPageState(Constants.GOODSDETAIL_EVENT_KEY[1] + goodsId, StateConstants.ERROR_STATE);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.GOODSDETAIL_EVENT_KEY[1] + goodsId, StateConstants.NET_WORK_STATE);
                    }
                })
        );

    }

    /*搜索列表*/
    public void getHotKeys() {
//        addDisposable(apiService.getHotKeys(parames(ClassifyService.HOTKEY[0], ClassifyService.HOTKEY[1]))
        addDisposable(apiService.getHotKeys(parames("index", "search_key_list"))
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<HotKeyInfo>>() {
                    @Override
                    public void onSuccess(BaseResponse<HotKeyInfo> result) {
                        sendData(Constants.SEARCH_EVENT_KEY[0], result);
                        showPageState(Constants.SEARCH_EVENT_KEY[1], StateConstants.SUCCESS_STATE);
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
                .compose(RxSchedulers.io_main())
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
    public void getOrderInfo(String cartId, String addressId, String ifcart, final Object eventKey) {
        Map<String, Object> map = parames("member_buy", "buy_step1");
        map.put("cart_id", cartId);
        if (!TextUtils.isEmpty(addressId)) {
            map.put("address_id", ifcart);
        }
        if (!TextUtils.isEmpty(ifcart)) {
            map.put("ifcart", ifcart);
        }
        map.put("key", getUserKey());
        addDisposable(apiService.getOrderInfo(map)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<OrderInfo>>() {
                    @Override
                    public void onSuccess(BaseResponse<OrderInfo> result) {
                        if (result.isData() && result.isSuccess()) {
                            sendData(eventKey, result.getData());
                            showPageState(Constants.CONFIRMORDER_KEY[1], StateConstants.SUCCESS_STATE);
                        } else {
                            sendData(Constants.CONFIRMORDER_KEY[1] + "Error", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        showPageState(Constants.CONFIRMORDER_KEY[1], StateConstants.ERROR_STATE);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.CONFIRMORDER_KEY[1], StateConstants.NET_WORK_STATE);
                    }
                }));
    }

    /*获取虚拟订单信息*/
    public void getMemberVrBuy(String goodsId, String quantity, Object eventKey) {
        Map<String, Object> map = parames("member_vr_buy", "buy_step2");
        map.put("goods_id", goodsId);
        map.put("quantity", quantity);
        map.put("key", getUserKey());
        addDisposable(apiService.getOrderInfo(map)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<OrderInfo>>() {
                    @Override
                    public void onSuccess(BaseResponse<OrderInfo> result) {
                        if (result.isData() && result.isSuccess()) {
                            sendData(eventKey, result.getData());
                            showPageState(Constants.CONFIRMORDER_KEY[1], StateConstants.SUCCESS_STATE);
                        } else {
                            sendData(Constants.CONFIRMORDER_KEY[1] + "Error", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        showPageState(Constants.CONFIRMORDER_KEY[1], StateConstants.ERROR_STATE);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.CONFIRMORDER_KEY[1], StateConstants.NET_WORK_STATE);
                    }
                }));
    }

    /*获取发票内容*/
    public void getInvoiceContentList(final Object eventKey) {
        Map<String, Object> map = parames("member_invoice", "invoice_content_list");
        map.put("key", getUserKey());
        addDisposable(apiService.getInvoiceContentList(map)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<InvoiceListInfo>>() {
                    @Override
                    public void onSuccess(BaseResponse<InvoiceListInfo> result) {
                        sendData(eventKey, result);
                        showPageState(Constants.INVOICECONTENT_KEY[1], StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        showPageState(Constants.INVOICECONTENT_KEY[1], StateConstants.ERROR_STATE);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.INVOICECONTENT_KEY[1], StateConstants.NET_WORK_STATE);
                    }
                }));
    }

    /*确定发票内容*/
    public void getInvoiceList(final Object eventKey) {
        Map<String, Object> map = parames("member_invoice",
                "invoice_list");
        map.put("key", getUserKey());
        addDisposable(apiService.getInvoiceContentList(map)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<InvoiceListInfo>>() {
                    @Override
                    public void onSuccess(BaseResponse<InvoiceListInfo> result) {
                        sendData(eventKey, result);
                        showPageState(Constants.INVOICECONTENT_KEY[1], StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        showPageState(Constants.INVOICECONTENT_KEY[1], StateConstants.ERROR_STATE);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.INVOICECONTENT_KEY[1], StateConstants.NET_WORK_STATE);
                    }
                }));
    }

    /*获取发票列表*/
    public void addInvoice(Map<String, Object> map, final Object eventKey) {
        map = parames(map, "member_invoice",
                "invoice_add");
        map.put("key", getUserKey());
        addDisposable(apiService.getInvoiceContentList(map)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<InvoiceListInfo>>() {
                    @Override
                    public void onSuccess(BaseResponse<InvoiceListInfo> result) {
                        sendData(eventKey, result);
                        showPageState(Constants.INVOICECONTENT_KEY[1], StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        showPageState(Constants.INVOICECONTENT_KEY[1], StateConstants.ERROR_STATE);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.INVOICECONTENT_KEY[1], StateConstants.NET_WORK_STATE);
                    }
                }));
    }

    /*删除发票列表*/
    public void invoiceDel(final Object eventKey, String invId) {
        Map<String, Object> map = parames("member_invoice",
                "invoice_del");
        map.put("_client", "android");
        map.put("key", getUserKey());
        map.put("inv_id", invId);
        execute(eventKey, map);
    }

    /************************************* 店铺 start ******************************/
    /*店铺信息*/
    public void getStoreInfo(String storeId, String key, final Object eventKey) {
        Map<String, Object> map = parames("store", "store_info");
        map.put("store_id", storeId);
        map.put("key", key);
        map.put("return_full", "1");
        map.put("num", "3");
        addDisposable(apiService.getStoreInfo(map)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<StoreInfo>>() {
                    @Override
                    public void onSuccess(BaseResponse<StoreInfo> result) {
                        sendData(eventKey, result);
                        showPageState(Constants.STORE_GOODS_RANK_KEY[1], StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
//                        showPageState(Constants.INVOICECONTENT_KEY[1], StateConstants.ERROR_STATE);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
//                        showPageState(Constants.INVOICECONTENT_KEY[1], StateConstants.NET_WORK_STATE);
                    }
                }));

    }

    /*收藏排行*/
    public void getStoreGoodsRank(String storeId, String orderType, String num, final Object eventKey) {
        Map<String, Object> map = parames("store", "store_goods_rank");
        map.put("store_id", storeId);
        map.put("ordertype", orderType);
        map.put("num", num);
        addDisposable(apiService.getStoreGoodsRank(map)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<GoodsListInfo>() {
                    @Override
                    public void onSuccess(GoodsListInfo result) {
                        sendData(eventKey, result);
//                        showPageState(Constants.INVOICECONTENT_KEY[1], StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
//                        showPageState(Constants.INVOICECONTENT_KEY[1], StateConstants.ERROR_STATE);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
//                        showPageState(Constants.INVOICECONTENT_KEY[1], StateConstants.NET_WORK_STATE);
                    }
                }));

    }

    /*全部商品*/
    public void getStoreGoods(String storeId, long page, final Object eventKey) {
        Map<String, Object> map = parames("store", "store_goods");
        map.put("store_id", storeId);
        map.put("curpage", page);
        map.put("page", Constants.PAGE_RN);
        addDisposable(apiService.getStoreGoods(map)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<GoodsListInfo>() {
                    @Override
                    public void onSuccess(GoodsListInfo result) {
                        sendData(eventKey, result);
                    }

                    @Override
                    public void onFailure(String msg) {
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }
                }));
    }

    public void getStoreGoods(String storeId, long page, String key, final String areaId, String priceFrom, String priceTo,
                              String order, String ci, String st, final Object eventKey) {
        Map<String, Object> map = parames("store", "store_goods");
        map.put("store_id", storeId);
        map.put("curpage", page);
        map.put("page", Constants.PAGE_RN);
        if (!TextUtils.isEmpty(ci)) map.put("ci", ci);
        if (!TextUtils.isEmpty(st)) map.put("st", st);
        if (!TextUtils.isEmpty(areaId)) map.put("area_id", areaId);//地区
        map.put("price_from", priceFrom);//价格区间最低范围
        map.put("price_to", priceTo);// 价格区间最高范围
        if (!TextUtils.isEmpty(key)) map.put("key", key);/*使用排序*/
        if (!TextUtils.isEmpty(order)) map.put("order", order);/*使用排序*/
        for (String s : st.split("_")) map.put(s, "1");
        map.put("page", Constants.PAGE_RN);

        addDisposable(apiService.getStoreGoods(map)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<GoodsListInfo>() {
                    @Override
                    public void onSuccess(GoodsListInfo result) {
                        sendData(eventKey, result);
                        showPageState(Constants.STORE_GOODS_RANK_KEY[1], StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        showPageState(Constants.STORE_GOODS_RANK_KEY[1], StateConstants.ERROR_STATE);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.STORE_GOODS_RANK_KEY[1], StateConstants.NET_WORK_STATE);
                    }
                }));
    }

    /*商品上新*/
    public void getStoreNewGoods(String storeId, int page, final Object eventKey) {
        Map<String, Object> map = parames("store", "store_new_goods");
        map.put("store_id", storeId);
        map.put("curpage", page);
        map.put("page", Constants.PAGE_RN);
        addDisposable(apiService.getStoreGoodsRank(map)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<GoodsListInfo>() {
                    @Override
                    public void onSuccess(GoodsListInfo result) {
                        sendData(eventKey, result);
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }
                }));
    }

    /*活动店铺*/
    public void getStorePromotion(String storeId, int page, final Object eventKey) {
        Map<String, Object> map = parames("store", "store_promotion");
        map.put("store_id", storeId);
        map.put("curpage", page);
        map.put("page", Constants.PAGE_RN);
        addDisposable(apiService.getStorePromotionInfo(map)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<StorePromotionInfo>() {
                    @Override
                    public void onSuccess(StorePromotionInfo result) {
                        sendData(eventKey, result);
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }
                }));
    }

    /*店铺取消 收藏*/
    public void getStoreFavorites(String storeId, boolean isDel, final Object eventKey) {
        Map<String, Object> map = parames("member_favorites_store",
                isDel ? "favorites_del" : "favorites_add");
        map.put("store_id", storeId);
        map.put("_client", "android");
        map.put("key", getUserKey());
        addDisposable(apiService.getStoreFavorites(map)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<ResultBean>() {
                    @Override
                    public void onSuccess(ResultBean result) {
                        sendData(eventKey, result);
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }
                }));
    }

    /*店铺介绍*/
    public void getStoreIntro(String storeId, final Object eventKey) {
        Map<String, Object> map = parames("store", "store_intro");
        map.put("store_id", storeId);
        addDisposable(apiService.getStoreIntro(map)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<StoreInfo>>() {
                    @Override
                    public void onSuccess(BaseResponse<StoreInfo> result) {
                        sendData(eventKey, result);
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();

                    }
                }));
    }

    /*店铺列表*/
    public void getStoreShappingList(String bId, String curpage, String keyword, final String typeId,
                                     String areaId, String priceFrom, String priceTo,
                                     String key, String order, String ci, String st) {
        Map<String, Object> map = parames("store", "store_goods");
        if (!TextUtils.isEmpty(bId)) {
            map.put("store_id", bId);
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
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<GoodsListInfo>() {
                    @Override
                    public void onSuccess(GoodsListInfo result) {
                        sendData(Constants.STOREINTRO_LIST[0], typeId, result);
                        showPageState(Constants.STOREINTRO_LIST[1], typeId, StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        showPageState(Constants.STOREINTRO_LIST[1], typeId, StateConstants.ERROR_STATE);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.STOREINTRO_LIST[1], typeId, StateConstants.NET_WORK_STATE);
                    }
                })
        );

    }

    /*店铺代金券*/
    public void getVoucherTplList(String storeId, String gettype, final Object eventKey) {
        Map<String, Object> map = parames("voucher", "voucher_tpl_list");
        map.put("store_id", storeId);
        map.put("gettype", gettype);
        addDisposable(apiService.getVoucherTplList(map)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<VoucherInfo>>() {
                    @Override
                    public void onSuccess(BaseResponse<VoucherInfo> result) {
                        sendData(eventKey, result);
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }
                })
        );

    }

    /*店铺代金券*/
    public void getVoucherFreeex(String storeId, final Object eventKey) {
        Map<String, Object> map = parames("member_voucher", "voucher_freeex");
        map.put("tid", storeId);
        addDisposable(apiService.execute(map)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<ResultBean>() {
                    @Override
                    public void onSuccess(ResultBean result) {
                        sendData(eventKey, result);
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }
                })
        );

    }

    /*提交订单*/
    public void getBuyStep2(Map<String, Object> map, final Object eventKey) {
        map = parames(map, "member_buy", "buy_step2");
        map.put("key", getUserKey());
        addDisposable(apiService.getBuyStep2(map)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<BuyStepInfo>>() {
                    @Override
                    public void onSuccess(BaseResponse<BuyStepInfo> result) {
                        sendData(eventKey, result);
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }
                })
        );

    }

    public void getBuyStep3(Map<String, Object> map, final Object eventKey) {
        map = parames(map, "member_vr_buy", "buy_step3");
        map.put("key", getUserKey());
        addDisposable(apiService.getBuyStep2(map)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<BuyStepInfo>>() {
                    @Override
                    public void onSuccess(BaseResponse<BuyStepInfo> result) {
                        sendData(eventKey, result);
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }
                })
        );

    }

    /*获取聊天历史列表*/
    public void getChatLog(String tId, String t, final Object eventKey) {
        Map<String, Object> map = parames("member_chat", "get_chat_log");
        map.put("key", getUserKey());
        map.put("t_id", tId);
        map.put("t", t);
        map.put("page", "50");
        addDisposable(apiService.getChatLog(map)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<MsgListInfo>>() {
                    @Override
                    public void onSuccess(BaseResponse<MsgListInfo> result) {
                        if (result.isSuccess()) {
                            sendData(eventKey + "Success", result.getData());
                        } else {
                            sendData(Constants.CHAT[0] + "Error", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }
                })
        );

    }

    /*获取聊天列表*/
    public void getNodeInfo(String goodsId, String tId, final Object eventKey) {
        Map<String, Object> map = parames("member_chat", "get_node_info");
        map.put("key", getUserKey());
        if (!TextUtils.isEmpty(goodsId)) {
            map.put("chat_goods_id", goodsId);
        }
        map.put("u_id", tId);
        addDisposable(apiService.getNodeInfo(map)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<MsgListInfo>>() {
                    @Override
                    public void onSuccess(BaseResponse<MsgListInfo> result) {
                        if (result.isSuccess()) {
                            sendData(eventKey + "Success", result.getData());
                        } else {
                            sendData(Constants.CHAT[0] + "Error", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData(Constants.CHAT[0] + "Error", msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }
                })
        );

    }

    /*发送聊天信息*/
    public void sendMsg(String goodsId, String fId, String tId, String tName, String msg, final Object eventKey) {
        Map<String, Object> map = parames("member_chat", "send_msg");
        map.put("key", getUserKey());
        map.put("chat_goods_id", goodsId);
        map.put("t_id", tId);
        map.put("f_id", fId);
        if (!TextUtils.isEmpty(tName)) {
            map.put("t_name", tName);
        }
        map.put("t_msg", msg);
        addDisposable(apiService.sendMsg(map)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<MsgInfo>>() {
                    @Override
                    public void onSuccess(BaseResponse<MsgInfo> result) {
                        if (result.isSuccess()) {
                            sendData(eventKey + "Success", result.getData());
                        } else {
                            sendData(Constants.CHAT[0] + "Error", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData(Constants.CHAT[0] + "Error", msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }
                })
        );

    }

    /*发送图片*/
    public void picUpload(String goodsId, String fId, String tId, String tName, String image, final Object eventKey) {
        Map<String, Object> map = parames("member_chat", "pic_upload");
        map.put("key", getUserKey());
        map.put("chat_goods_id", goodsId);
        map.put("t_id", tId);
        map.put("f_id", fId);
        map.put("t_name", tName);
        File file = new File(image);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
        addDisposable(apiService.picUpload(map, requestBody)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<MsgInfo>>() {
                    @Override
                    public void onSuccess(BaseResponse<MsgInfo> result) {
                        if (result.isSuccess()) {
                            KLog.i("发送图片");
                            sendData(eventKey + "Success", result.getData());
                        } else {
                            sendData(Constants.CHAT[0] + "Error", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData(Constants.CHAT[0] + "Error", msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }
                })
        );

    }

    //    public void picUpload(String goodsId, String fId, String tId, String tName, File file, final Object eventKey) {
//        Map<String, Object> map = parames("member_chat", "pic_upload");
//        map.put("key", getUserKey());
//        map.put("chat_goods_id", goodsId);
//        map.put("t_id", tId);
//        map.put("f_id", fId);
//        map.put("t_name", tName);
////        File file = new File(image);
//        KLog.i(file.exists() + "文件存在" + file.getPath());
//        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
//        addDisposable(apiService.picUpload(map, requestBody)
//                .compose(RxSchedulers.<BaseResponse<MsgInfo>>io_main())
//                .subscribeWith(new RxSubscriber<BaseResponse<MsgInfo>>() {
//                    @Override
//                    public void onSuccess(BaseResponse<MsgInfo> result) {
//                        if (result.isSuccess()) {
//                            KLog.i("发送图片");
//                            sendData(eventKey + "Success", result.getData());
//                        } else {
//                            sendData(Constants.CHAT[0] + "Error", result.getMessage());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(String msg) {
//                        KLog.i(msg);
//                    }
//
//                    @Override
//                    protected void onNoNetWork() {
//                        super.onNoNetWork();
//                    }
//                })
//        );
//
//    }


//    /*支付宝*/
//    public void getAli(Map<String, Object> map, Object eventKey) {
////        map = parames(map, "member_chat", "send_msg");
////        map.put("key", getUserKey());
////        map.put("chat_goods_id", goodsId);
////        map.put("t_id", tId);
////        map.put("f_id", fId);
////        map.put("t_name", tName);
////        map.put("t_msg", msg);
////        addDisposable(apiService.sendMsg(map)
////                .compose(RxSchedulers.<BaseResponse<MsgInfo>>io_main())
////                .subscribeWith(new RxSubscriber<BaseResponse<MsgInfo>>() {
////                    @Override
////                    public void onSuccess(BaseResponse<MsgInfo> result) {
////                        if (result.isSuccess()) {
////                            sendData(eventKey + "Success", result.getData());
////                        } else {
////                            sendData(Constants.CHAT[0] + "Error", result.getMessage());
////                        }
////                    }
////
////                    @Override
////                    public void onFailure(String msg) {
////                        KLog.i(msg);
////                    }
////
////                    @Override
////                    protected void onNoNetWork() {
////                        super.onNoNetWork();
////                    }
////                })
////        );
//    }
//    /*微信*/
//    public void getWeiXin(Map<String, Object> map, Object eventKey) {
//    }

    /*获取参数*/
    public void getPayNew(String paySn, String paymentCode, String pdPay, final Object eventKey) {
        Map<String, Object> map = parames("member_payment", "pay_new");
        map.put("key", getUserKey());
        map.put("pay_sn", paySn);
        map.put("rcb_pay", "0");
        map.put("pd_pay", "0");
        map.put("payment_code", paymentCode);
        map.put("client", "android");
        addDisposable(apiService.getPayNew(map)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<PayMessageInfo>() {
                    @Override
                    public void onSuccess(PayMessageInfo result) {
                        if (result.isSuccess()) {
                            sendData(eventKey + "Success", result);
                        } else {
                            sendData(Constants.CHAT[0] + "Error", result.getErr_code_des());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }
                })
        );
    }

    /************************************* 店铺 end ******************************/
    /*通用*/
    public void execute(String app, String wwi, final Object eventKey, Map<String, Object> map) {
        map.put("key", getUserKey());
        map.put("app", app);
        map.put("wwi", wwi);
        execute(eventKey, map);
//        addDisposable(apiService.execute(app, wwi, map)
//                        .compose(RxSchedulers.<ResultBean>io_main())
//                        .subscribeWith(new RxSubscriber<ResultBean>() {
//                            @Override
//                            public void onSuccess(ResultBean result) {
//                                sendData(eventKey, result);
////                        showPageState(eventStateKey, StateConstants.SUCCESS_STATE);
//                            }
//
//                            @Override
//                            public void onFailure(String msg) {
//                                KLog.i(msg);
////                        showPageState(eventStateKey, StateConstants.ERROR_STATE);
//                            }
//
//                            @Override
//                            protected void onNoNetWork() {
//                                super.onNoNetWork();
//                                KLog.i("onNoNetWork");
////                        showPageState(eventStateKey, StateConstants.NET_WORK_STATE);
//                            }
//                        })
//        );
    }

//    public void execute(String app, String wwi, final Object eventKey, Map<String, Object> map) {
//        map.put("key", getUserKey());
//        map.put("app",app);
//        map.put("wwi",wwi);
//        addDisposable(apiService.execute(app, wwi, map)
//                        .compose(RxSchedulers.<ResultBean>io_main())
//                        .subscribeWith(new RxSubscriber<ResultBean>() {
//                            @Override
//                            public void onSuccess(ResultBean result) {
//                                sendData(eventKey, result);
////                        showPageState(eventStateKey, StateConstants.SUCCESS_STATE);
//                            }
//
//                            @Override
//                            public void onFailure(String msg) {
//                                KLog.i(msg);
////                        showPageState(eventStateKey, StateConstants.ERROR_STATE);
//                            }
//
//                            @Override
//                            protected void onNoNetWork() {
//                                super.onNoNetWork();
//                                KLog.i("onNoNetWork");
////                        showPageState(eventStateKey, StateConstants.NET_WORK_STATE);
//                            }
//                        })
//        );
//    }

    private void execute(final Object eventKey, Map<String, Object> map) {
        addDisposable(apiService.execute(map)
                        .compose(RxSchedulers.io_main())
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

//        Map<String, Object> parames(Object app, Object wwi) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("app", app);
//        map.put("wwi", wwi);
//        return map;
//    }

    private Map<String, Object> parames(Object app, Object wwi) {
        Map<String, Object> map = new HashMap<>();
        map = parames(map, app, wwi);
        return map;
    }

    private Map<String, Object> parames(Map<String, Object> map, Object app, Object wwi) {
        map.put("app", app);
        map.put("wwi", wwi);
        return map;
    }


//    /*省 市 区*/
//    public void getArea(String areaId) {
//        Map<String, Object> map = parames("area_list", "area_list");
//        map.put("area_id", areaId);
//        addDisposable(apiService.getArea(map)
//                        .compose(RxSchedulers.<BaseResponse<AreaListInfo>>io_main())
//                        .subscribeWith(new RxSubscriber<BaseResponse<AreaListInfo>>() {
//                            @Override
//                            public void onSuccess(BaseResponse<AreaListInfo> result) {
//                                sendData(Constants.AREA_LIST_EVENT_KEY, result);
////                        showPageState(Constants.EVALUATE_EVENT_KEY[1], StateConstants.SUCCESS_STATE);
//                            }
//
//                            @Override
//                            public void onFailure(String msg) {
//                                KLog.i(msg);
////                        showPageState(Constants.EVALUATE_EVENT_KEY[1], StateConstants.ERROR_STATE);
//                            }
//
//                            @Override
//                            protected void onNoNetWork() {
//                                super.onNoNetWork();
////                                showPageState(Constants.EVALUATE_EVENT_KEY[1], StateConstants.NET_WORK_STATE);
//                            }
//                        })
//        );
//
//    }
//    Map<String, RequestBody> generateRequestBody(Map<String, Object> requestDataMap) {
//        Map<String, RequestBody> requestBodyMap = new HashMap<>();
//        for (String key : requestDataMap.keySet()) {
//            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
//                    requestDataMap.get(key) == null ? "" : (String) requestDataMap.get(key));
//            requestBodyMap.put(key, requestBody);
//        }
//        return requestBodyMap;
//    }
}
