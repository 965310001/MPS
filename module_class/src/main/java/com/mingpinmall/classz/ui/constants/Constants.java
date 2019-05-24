package com.mingpinmall.classz.ui.constants;

/**
 * 常量
 */
public interface Constants {

    /*左边 右边*/
    Object[] EVENT_KEY_CLASSIFY_MORE = {"EVENT_KEY_CLASSIFY_MORE", "EVENT_KEY_CLASSIFY_MORE_STATE", "EVENT_KEY_CLASSIFY_MORE_RIGHT"};

    /*商品分类*/
//    String[] PRODUCTS_TITLE = {"综合排序", "销量优先", "筛选"};
    Object[] PRODUCTS_EVENT_KEY = {"PRODUCTS_EVENT_KEY", "PRODUCTS_EVENT_KEY_LIST_STATE"};

    /*商品详情*/
    Object[] GOODSDETAIL_EVENT_KEY = {"GOODSDETAIL_EVENT_KEY", "GOODSDETAIL_EVENT_KEY_STATE"};

    /*购物车KEY*/
    Object CART_EVENT_KEY = "CART_EVENT_KEY";

    /*收藏*/
    Object FAVORITES = "FAVORITES";

    String PAGE_RN = "10";

    /*评价列表*/
    Object[] EVALUATE_EVENT_KEY = {"EVALUATE_EVENT_KEY", "EVALUATE_EVENT_KEY_LIST_STATE"};
    /*省 市 区*/
//    Object AREA_LIST_EVENT_KEY = "AREA_LIST_EVENT_KEY";

    /*搜索*/
    Object[] SEARCH_EVENT_KEY = {"SEARCH_EVENT_KEY", "SEARCH_EVENT_KEY_STATE",
            "SEARCH_EVENT_KEY_SSEARCH", "SEARCH_EVENT_KEY_DELETE"};/*2 3 */
    /*搜索传值*/
//    String KEYWORD = "KEYWORD";

    /*筛选框*/
    Object[] CUSTOMPOPWINDOW_KEY = {"CUSTOMPOPWINDOW_KEY", "CUSTOMPOPWINDOW_KEY_SCREENING"};

    /*筛选内容*/
    Object[] SCREENING_KEY = {"SCREENING_FIRST_KEY", "SCREENING_CLASS_ONE_KEY", "SCREENING_CLASS_TWO_KEY", "SCREENING_CLASS_THREE_KEY"};

    /*提交订单*/
    Object[] CONFIRMORDER_KEY = {"CONFIRMORDER_KEY", "CONFIRMORDER_STATE",
            "CONFIRMORDER_PAY_KEY", "CONFIRMORDER_WEIXIN_PAY_KEY", "CONFIRMORDER_FCODE"};/*提交订单 支付*/

    /*发票*/
    Object[] INVOICECONTENT_KEY = {"INVOICECONTENT_KEY", "INVOICECONTENT_KEY_STATE", "ADD_INVOICECONTENT_KEY", "INVOICE_LIST_STATE",
            "INVOICEDEL_KEY"};/*4*/

    /*收藏排行*/
    Object[] STORE_GOODS_RANK_KEY = {"STORE_GOODS_RANK_KEY", "STORE_GOODS_RANK_STATE",
            "STORE_INFO_KEY",//2
            "STORE_NEW_GOODS_KEY", "STOREPROMOTION_KEY",//商品上新3   活动店铺4
            "STORE_GOODS_KEY",//全部商品5
    };
    /*店铺收藏 取消*/
    Object STORE_FAVORITES = "STORE_FAVORITES";

    /*店铺介绍*/
    Object[] STOREINTRO = {"STOREPROMOTION_KEY", "STOREPROMOTION_KEY_STATE"};/*店铺介绍*/

    /*店铺列表*/
    Object[] STOREINTRO_LIST = {"STOREINTRO_LIST_KEY", "STOREINTRO_LIST_STATE", "STOREINTRO_POPWINDOW_STATE"};/*店铺介绍*/

    /**/
    Object[] VOUCHER = {"VOUCHER_KEY", "VOUCHER_LIST_STATE",
            "VOUCHER_FREEEX_KEY"};/*代金券 2*/

    /*聊天*/
    Object[] CHAT = {"CHAT_KEY", "CHAT_LIST_STATE", "NODEINFO_KEY", "CHAT_LOG_KEY", "UPLOAD_IMAGE_KEY"};


    /*Socker URL*/
//    String CHAT_SERVER_URL = "https://www.mingpinmall.cn:8091";

    /*微信APPID*/
    String WEIXIN_APP_ID = "wxc18a7a67aae81510";
    int GET_TOKEN = 1;
    int CHECK_TOKEN = 2;
    int REFRESH_TOKEN = 3;
    int GET_INFO = 4;
    int GET_IMG = 5;
    /*支付宝APPID*/
    CharSequence AI_APP_ID = "2019050964432158";
}