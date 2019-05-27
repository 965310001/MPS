package com.goldze.common.dmvvm.constants;

/**
 * @author GuoFeng
 * @date :2019/1/17 13:23
 * @description: 路由页面常量配置 注意：路径至少需要两级 {/xx/xx}
 */
public interface ARouterConfig {

    Object REFRESH_DATA = "REFRESH_DATA";
    Object LOGIN_SUCCESS = "LOGIN_SUCCESS";
    Object LOGIN_OUT = "LOGIN_OUT";
    String SUCCESS = "RESULT_SUCCESS";

    enum ARouterType {
        //登录
        LOGINACTIVITY(ARouterConfig.LOGINACTIVITY),
        //过渡
        SPLASHACTIVITY(ARouterConfig.SPLASHACTIVITY),
        //主界面
        MAINACTIVITY(ARouterConfig.MAINACTIVITY),
        SHOPPINGACTIVITY(ARouterConfig.Shopping.SHOPPINGACTIVITY),
        //地址
        ADDRESSLISTACTIVITY(ARouterConfig.Me.ADDRESSLISTACTIVITY),
        //web
        WEBVIEWACTIVITY(ARouterConfig.WEBVIEWACTIVITY);

        public String url;

        ARouterType(String url) {
            this.url = url;
        }
    }

    int LOGIN_NEEDED = 200;

    /**
     * webViewActivity
     */
    String WEBVIEWACTIVITY = "/commom_base/WebViewActivity";

    /**
     * 过渡界面
     */
    String SPLASHACTIVITY = "/main/SplashActivity";

    /**
     * 主界面
     */
    String MAINACTIVITY = "/main/MainActivity";

    /**
     * 登录
     */
    String LOGINACTIVITY = "/me/LoginActivity";


    /***************************************************************************首页***********************************************************************/
    interface cart {
        /**
         * 购物车
         */
        String SHOPCARTACTIVITY = "/cart/ShopCartActivity";
        /**
         * 提交订单
         */
        String PAYSTEPONEACTIVITY = "/cart/PayStepOneActivity";
    }

    interface home {
        /**
         * 店铺街
         */
        String SHOPSTREETACTIVITY = "/home1/ShopStreetActivity";
        /**
         * 店铺分类
         */
        String SHOPCLASSACTIVITY = "/home1/ShopClassActivity";
        /**
         * 专题页面
         */
        String SPECIALACTIVITY = "/home1/SpecialActivity";

        /**
         * 搜索
         */
        String SEARCHACTIVITY = "/home/SearchActivity";

        /**
         * 商品详情
         */
        String SHOPPINGDETAILSACTIVITY = "/home/ShoppingDetailsActivity";

    }


    /***************************************************************************分类***********************************************************************/
    interface classify {
        /*购买*/
        String BUYACTIVITY = "/clasz/BuyActivity";

        /*商品分类list*/
        String PRODUCTSACTIVITY = "/clasz/ProductsActivity";
        /*商品分类list*/
        String SCREENINGACTIVITY = "/clasz/ScreeningActivity";

        /*提交订单*/
        String CONFIRMORDERACTIVITY = "/clasz/ConfirmOrderActivity";
        /*提交订单*/
        String CONFIRMORDERACTIVITY2 = "/clasz/ConfirmOrderActivity2";
        /*管理发票信息*/
        String INVOICEACTIVITY = "/clasz/InvoiceActivity";
        /*店铺*/
        String STOREACTIVITY = "/clasz/StoreActivity";
        /*店铺介绍*/
        String STOREINTROACTIVITY = "/clasz/StoreIntroActivity";
        /*店铺分类列表*/
        String PRODUCTSACTIVITY2 = "/clasz/ProductsActivity2'";
        /*聊天*/
        String CHATACTIVITY = "/clasz/ChatActivity";

        /*试戴*/
        String HOLOACTIVITY = "/clasz/HoloActivity";
//        String DISTRIBUTIONACTIVITY = "/clasz/DistributionActivity";
    }

    /***************************************************************************购物车***********************************************************************/
    interface Shopping {
        /**
         * 购物车
         */
        String SHOPPINGACTIVITY = "/clasz/ShoppingActivity";

        /**
         * 查看物流
         */
        String DELIVERYACTIVITY = "/clasz/DeliveryActivity";

        /**
         * 订单详情
         */
        String ORDERDETAILSACTIVITY = "/shopping/OrderDetailsActivity";

        /**
         * 订单详情
         */
        String EVALUATIONACTIVITY = "/shopping/EvaluationActivity";
    }

    /***************************************************************************我的***********************************************************************/
    interface Me {
        /**
         * 设置
         */
        String SETTINGACTIVITY = "/me/SettingActivity";

        /**
         * 我的订单
         */
        String ORDERACTIVITY = "/me/OrderActivity";

        /**
         * 实物订单详情
         */
        String PHYSICALORDERINFORMATIONACTIVITY = "/me/PhysicalOrderInformationActivity";

        /**
         * 虚拟订单详情
         */
        String VIRTUALORDERINFORMATIONACTIVITY = "/me/VirtualOrderInformationActivity";

        /**
         * 退换货
         */
        String REFUNDACTIVITY = "/me/RefundActivity";

        /**
         * 订单退款
         */
        String ORDERREFUNDACTIVITY = "/me/OrderRefundActivity";

        /**
         * 商品退款
         */
        String APPLYREFUNDACTIVITY = "/me/ApplyRefundActivity";

        /**
         * 商品退货
         */
        String APPLYRETURNACTIVITY = "/me/ApplyReturnActivity";

        /**
         * 退款详情
         */
        String REFUNDORDERINFORMATION = "/me/RefundOrderInformationActivity";

        /**
         * 退货详情
         */
        String RETRUNORDERINFORMATION = "/me/RetrunOrderInformationActivity";

        /**
         * 我的消息
         */
        String MESSAGEACTIVITY = "/me/MessageActivity";

        /**
         * 我的财产
         */
        String PROPERTYACTIVITY = "/me/PropertyActivity";

        /**
         * 充值卡余额
         */
        String CARDSURPLUSACTIVITY = "/me/CardSurplusActivity";

        /**
         * 账户余额
         */
        String ACCOUNTSURPLUSACTIVITY = "/me/AccountSurplusActivity";

        /**
         * 账户余额提现详情
         */
        String PDCASHINFORMATIONACTIVITY = "/me/PdcashInformationActivity";

        /**
         * 店铺代金券
         */
        String COUPONACTIVITY = "/me/CouponActivity";

        /**
         * 店铺红包
         */
        String STOREPACKETACTIVITY = "/me/StorePacketActivity";

        /**
         * 会员积分
         */
        String VIPINTERGRALACTIVITY = "/me/VipIntegralActivity";

        /**
         * 我的收藏
         */
        String COLLECTIONACTIVITY = "/me/CollectionActivity";

        /**
         * 我的足迹
         */
        String FOOTPRINTACTIVITY = "/me/FootprintActivity";

        /**
         * 我的分销管理
         */
        String DISRTIBUTIONACTIVITY = "/me/DistributionActivity";

        /**
         * 我的推广码
         */
        String REDUCECASHACTIVITY = "/me/ReduceCashActivity";

        /**
         * 收货地址管理
         */
        String ADDRESSMANAGERACTIVITY = "/me/AddressManagerActivity";

        /**
         * 编辑 or 新增 收货地址
         */
        String EDITADDRESSACTIVITY = "/me/EditAddressActivity";

        /**
         * 重设登录密码 重设支付密码
         */
        String RESETPASSWORDACTIVITY = "/me/ResetPasswordActivity";

        /**
         * 修改 密码，手机，支付密码 前，检查验证码是否正确
         */
        String CHECKAUTHACTIVITY = "/me/CheckAuthActivity";

        /**
         * 用户反馈
         */
        String FEEDBACKACTIVITY = "/me/FeedBackActivity";

        /**
         * 重设手机
         */
        String BINDPHONEACTIVITY = "/me/BindPhoneActivity";

        /**
         * 选择城市
         */
        String SELECTCITYACTIVITY = "/me/SelectCityActivity";

        /**
         * 管理收货地址
         */
        String ADDRESSLISTACTIVITY = "/me/AddressListActivity";

        /**
         * 物流信息
         */
        String ORDERDELIVERYACTIVITY = "/me/OrderDeliveryActivity";

        /**
         * 物流信息
         */
        String ORDEREVALUATEACTIVITY = "/me/OrderEvaluateActivity";

    }


}
