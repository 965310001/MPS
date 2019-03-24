package com.goldze.common.dmvvm.constants;

/**
 * @author GuoFeng
 * @date :2019/1/17 13:23
 * @description: 路由页面常量配置 注意：路径至少需要两级 {/xx/xx}
 */
public interface ARouterConfig {


    enum ARouterType {
        LOGINACTIVITY(ARouterConfig.LOGINACTIVITY),//登录
        SPLASHACTIVITY(ARouterConfig.SPLASHACTIVITY),//过渡
        MAINACTIVITY(ARouterConfig.MAINACTIVITY),//主界面
        REGISTERACTIVITY(ARouterConfig.REGISTERACTIVITY),//注册
        SHOPPINGACTIVITY(ARouterConfig.Shopping.SHOPPINGACTIVITY),//
        ADDRESSLISTACTIVITY(ARouterConfig.Me.ADDRESSLISTACTIVITY),//地址
        WEBVIEWACTIVITY(ARouterConfig.WEBVIEWACTIVITY);//web

        public String url;

        ARouterType(String url) {
            this.url = url;
        }
    }

    int LOGIN_NEEDED = 200;

    /*webViewActivity*/
    String WEBVIEWACTIVITY = "/commom_base/WebViewActivity";

    /*过渡界面*/
    String SPLASHACTIVITY = "/app/SplashActivity";

    /*主界面*/
    String MAINACTIVITY = "/app/MainActivity";

    //登录
    String LOGINACTIVITY = "/me/LoginActivity";

    //注册
    String REGISTERACTIVITY = "/me/RegisterActivity";

    //第二部注册
    String REGISTERACTIVITY2 = "/me/RegisterActivity2";


    /***************************************************************************首页***********************************************************************/
    interface home {
        /*搜索*/
        String SEARCHACTIVITY = "/home/SearchActivity";
        /*搜索列表*/
        String SEARCHLISTACTIVITY = "/home/SearchListActivity";
        /*商品详情*/
        String SHOPPINGDETAILSACTIVITY = "/home/ShoppingDetailsActivity";

        //四个menu的
        /*优惠活动*/
        String PREFERENTIALACTIVITY = "/menu/PreferentialActivity";
        /*积分商城*/
        String INTEGRALACTIVITY = "/menu/IntegralActivity";
        /*订单*/
        String ORDERACTIVITY = "/menu/OrderActivity";
        /*红包*/
        String REDACTIVITY = "/menu/RedActivity";

        String[] MAIN_MENU_ITEM = {PREFERENTIALACTIVITY, INTEGRALACTIVITY, ORDERACTIVITY, REDACTIVITY};

        /*忘记密码*/
        String RETRIEVEACTIVITY = "/home/RetrieveActivity";
    }


    /***************************************************************************分类***********************************************************************/
    interface classify {
        /*购买*/
        String BUYACTIVITY = "/clasz/BuyActivity";

        /*商品分类list*/
        String PRODUCTSACTIVITY = "/clasz/ProductsActivity";


//        String DISTRIBUTIONACTIVITY = "/clasz/DistributionActivity";
    }

    /***************************************************************************购物车***********************************************************************/
    interface Shopping {
        //购物车
        String SHOPPINGACTIVITY = "/clasz/ShoppingActivity";

        //查看物流
        String DELIVERYACTIVITY = "/clasz/DeliveryActivity";

        //订单详情
        String ORDERDETAILSACTIVITY = "/shopping/OrderDetailsActivity";

        //评价晒单
        String EVALUATIONACTIVITY = "/shopping/EvaluationActivity";
    }

    /***************************************************************************我的***********************************************************************/
    interface Me {
        //个人信息
        String PERSONINFOACTIVITY = "/me/PersonInfoActivity";

        //设置
        String SETTINGACTIVITY = "/me/SettingActivity";

        //消息
        String MESSAGEACTIVITY = "/me/MessageActivity";

        //积分、积分记录
        String INTEGRALRECORDACTIVITY = "/me/IntegralRecordActivity";

        //管理收货地址
        String ADDRESSLISTACTIVITY = "/me/AddressListActivity";

        //收货地址的增删改查
        String MODIFYACTIVITY = "/me/ModifyActivity";

        //我的资金
        String FUNDSACTIVITY = "/me/FundsActivity";

        //提取记录
        String EXTRACTLISTACTIVITY = "/me/ExtractListActivity";

        //提现
        String EXTRACTACTIVITY = "/me/ExtractActivity";

        /*资金明细*/
        String DEFINITEACTIVITY = "/me/DefiniteActivity";

        /*使用帮助*/
        String HELPACTIVITY = "/me/HelpActivity";

        /*我的收藏*/
        String COLLECTIONACTIVITY = "/me/CollectionActivity";

        /*我的红包*/
        String REDENVELOPEACTIVITY = "/me/RedEnvelopeActivity";

        //我的推荐
        String RECOMMENDATIONACTIVITY = "/me/RecommendationActivity";

        //我的推广二维码
        String DIMENSIONALCODESACTIVITY = "/me/DimensionalCodesActivity";

        //售后列表
        String AFTERSALESLISTACTIVITY = "/me/AfterSalesListActivity";

        //售后详情
        String AFTERSALESDETAILSDCTIVITY = "/me/AfterSalesDetailsDctivity";

        //可售后商品
        String AFTERSALESACTIVITY = "/me/AfterSalesActivity";

        //提交售后
        String ASKACTIVITY = "/me/AskActivity";

        //关于我们
        String ABOUTUSACTIVITY = "/me/AboutUsActivity";

        //客服中心
        String CUSTOMERSERVICEACTIVITY = "/me/CustomerServiceActivity";

        //站内公告
        String STATIONACTIVITY = "/me/StationActivity";

        //资讯
        String INFORMATIONACTIVITY = "/me/InformationActivity";

        //系统分类
        String PHYCLASSACTIVITY = "/me/PhyClassActivity";

    }


}
