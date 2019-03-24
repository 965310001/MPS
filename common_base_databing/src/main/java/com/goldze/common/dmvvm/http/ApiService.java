package com.goldze.common.dmvvm.http;//package me.goldze.common.http;
//
//
//import io.reactivex.Flowable;
//
//import me.goldze.common.BuildConfig;
//import me.goldze.common.base.bean.BaseResponse;
//import me.goldze.common.base.core.banner.Banner;
//import retrofit2.http.Field;
//import retrofit2.http.FormUrlEncoded;
//import retrofit2.http.POST;
//
///**
// * @author GuoFeng
// * @date : 2019/1/19 14:55
// * @description: api
// */
//public interface ApiService {
//
//    interface Api {
//
//        /*http://api.biandanwang.cc/v2/*/
//        /*http://wanandroid.com/*/
//        String Base_URL = BuildConfig.APP_URL;
//
//        /*-------------------------------------------------------------------首页---------------------------------------------------*/
//        /*登录*/
//        String LOGIN = "/v2/ecapi.auth.signin";
//        /*注册*/
//        String REGISTER = "/v2/ecapi.auth.mobile.signup";
//        /*重置用户密码*/
//        String RESET = "/v2/ecapi.auth.mobile.reset";
//        /*发送验证码*/
//        String SENDCODE = "/v2/ecapi.auth.mobile.send";
//
//        /*检查更新*/
//        String CHECKUPDATE = "";
//
//        interface home {
////            String BANNER = "/banner/json";
////            String HOME_LIST = "/banner/json";
//
//            /*轮播图*/
//            String BANNER = "/v2/ecapi.banner.list";
//            /*首页数据*/
//            String HOME_LIST = "/v2/ecapi.home.product.list";
//            /*菜单*/
//            String HOME_MENU = "/v2/ecapi.site.menu";
//
//            /*订单*/
//            String ORDER_LIST = "/v2/ecapi.order.list";
//            /*红包*/
//            String RED_LIST = "/v2/ecapi.cashgift.list";
//
//            /*热搜*/
//            String HOTKEY_LIST = "/v2/ecapi.search.keyword.list";
//
//            /*搜索list*/
//            String SEARCH_LIST = "/article/query/0/json";
//        }
//
//        /*-------------------------------------------------------------------分类---------------------------------------------------*/
//        interface classify {
//            //双listview
//            String TREE = "/v2/ecapi.category.list";
//        }
//
//        /*-------------------------------------------------------------------购物车-------------------------------------------------*/
//        interface shopping {
//            String PRODUCT = "/v2/ecapi.product.get";
//            /*商品列表*/
//            String PRODUCTLIST = "/v2/ecapi.product.list";
//            /*购物车数量*/
//            String CARTNUM = "/v2/ecapi.cart.quantity";
//            /*购物车商品列表*/
//            String CARTLIST = "/v2/ecapi.cart.get";
//            /*删除购物车商品*/
//            String DELETECART = "/v2/ecapi.cart.delete";
//            /*修改购物车商品数量*/
//            String UPDATECART = "/v2/ecapi.cart.update";
//            /*添加商品到购物车*/
//            String ADDCART = "/v2/ecapi.cart.add";
//            /*购物车--结算*/
//            String CHECKOUTCART = "/v2/ecapi.order.checkout";
//            /*商品评价*/
//            String REVIEWLIST = "/v2/ecapi.review.product.list";
//        }
//        /*-------------------------------------------------------------------我的---------------------------------------------------*/
//
//        interface me {
//
//            /*地址列表*/
//            String ADDRESSLIST = "//";
//            /*地址的增加，修改*/
//            String UPDATEADDRESS = "//";
//            /*站点信息*/
//            String SITE = "/v2/ecapi.site.get";
//            /*获取红包列表*/
//            String REDLIST = "/v2/ecapi.cashgift.list";
//            /*我的收藏*/
//            String LIKEDLIST = "/v2/ecapi.product.liked.list";
//            /*取消收藏*/
//            String UNLIKE = "/v2/ecapi.product.unlike";
//            /*订单详情*/
//            String ORDER = "/v2/ecapi.order.get";
//        }
//    }
//
//
//    /*-------------------------------------------------------------------首页---------------------------------------------------*/
//    /*登录*/
//    //Observable Flowable
//    @FormUrlEncoded
//    @POST(Api.LOGIN)
//    Flowable<UserInfo> login(@Field("username") String userName,
//                             @Field("password") String password);
//
//    /**
//     * 注册
//     *
//     * @param mobile     手机号
//     * @param code       验证码
//     * @param password   密码
//     * @param inviteCode 邀请码
//     * @return
//     */
//    @FormUrlEncoded
//    @POST(Api.REGISTER)
//    Flowable<UserInfo> register(@Field("mobile") String mobile,
//                                @Field("code") String code,
//                                @Field("password") String password,
//                                @Field("invite_code") String inviteCode);
//
//    /*注册第二步*/
//    @FormUrlEncoded
//    @POST(Api.REGISTER)
//    Flowable<UserInfo> register2(@Field("mobile") String mobile,
//                                 @Field("code") String code,
//                                 @Field("password") String password,
//                                 @Field("invite_code") String inviteCode,
//                                 @Field("region") String region,
//                                 @Field("address") String address,
//                                 @Field("contacts") String contacts,
//                                 @Field("company") String company,
//                                 @Field("ywyno") String ywyno);
//
//    /**
//     * 获取验证码
//     *
//     * @param mobile
//     * @param code   区号
//     * @return
//     */
//    @FormUrlEncoded
//    @POST(Api.SENDCODE)
//    Flowable<UserInfo> senCode(@Field("mobile") String mobile,
//                               @Field("code") String code);
//
//
//    /*重置密码*/
//    @FormUrlEncoded
//    @POST(Api.RESET)
//    Flowable<UserInfo> reset(@Field("mobile") String mobile,
//                             @Field("code") String code,
//                             @Field("password") String password);
//
//
//    /*菜单*/
//    @POST(Api.home.HOME_MENU)
//    Flowable<CatagoryInfo> getMenu();
//
//
//    /*检查更新*/
//    @POST(Api.CHECKUPDATE)
//    Observable<AppInfo> checkUpdate();
//
//    /*轮播图*/
//    @POST(Api.home.BANNER)
//    @FormUrlEncoded
//    Flowable<Banner> getBannerData(@Field("page") int page,
//                                   @Field("per_page") int per_page);
//
//    /*首页数据*/
//    @POST(Api.home.HOME_LIST)
//    Flowable<HomeList> getHomeData();
//
//    /*订单*/
//    @FormUrlEncoded
//    @POST(Api.home.ORDER_LIST)
//    Flowable<OrderInfo> getOrderListData(@Field("page") long page,
//                                         @Field("per_page") String per_page,
//                                         @Field("status") String status);
//
//    /*订单详情*/
//    @FormUrlEncoded
//    @POST(Api.me.ORDER)
//    Flowable<OrderDetailsInfo> getOrderData(@Field("order") int order);
//
//    /*红包*/
//    @FormUrlEncoded
//    @POST(Api.home.RED_LIST)
//    Flowable<RedItemInfo> getRedData(@Field("page") int page,
//                                     @Field("per_page") String per_page,
//                                     @Field("status") String status);
//
//    /*热搜*/
//    @POST(Api.home.HOTKEY_LIST)
//    Flowable<HotKeyInfo> getHotkeyData();
//
//    /*搜索list*/
//    @FormUrlEncoded
//    @POST(Api.home.SEARCH_LIST)
//    Flowable<SearchInfo> getSearchListData(@Field("k") String k);
//
//    /*双listview*/
//    @FormUrlEncoded
//    @POST(Api.classify.TREE)
//    Flowable<ClassificationInfo> getTreeData(@Field("page") int page,
//                                             @Field("per_page") int per_page,
//                                             @Field("top_level") int top_level);
//
//    /*-------------------------------------------------------------------分类---------------------------------------------------*/
//
//    /*双listview右边*/
////    @POST(Api.classify.TREE)
////    Flowable<ClassificationInfo> getTreeRightData(@Field("category") int category);
//
//    /*商品评价*/
//    @FormUrlEncoded
//    @POST(Api.shopping.REVIEWLIST)
//    Flowable<ReviewListInfo> getReviewList(@Field("product") long id,
//                                           @Field("page") int page,
//                                           @Field("per_page") int perPage);
//
//    /*-------------------------------------------------------------------购物车-------------------------------------------------*/
//    /*商品详情*/
//    @FormUrlEncoded
//    @POST(Api.shopping.PRODUCT)
//    Flowable<GoodsListInfo> getProduct(@Field("product") long id);
//
//    /*购物车商品数量*/
//    @POST(Api.shopping.CARTNUM)
//    Flowable<UserInfo> getCartNum(); /*购物车商品数量*/
//
//    /*购物车商品列表*/
//    @POST(Api.shopping.CARTLIST)
//    Flowable<VendorInfo> getCartList();
//
//    /*删除购物车商品*/
//    @FormUrlEncoded
//    @POST(Api.shopping.DELETECART)
//    Flowable<UserInfo> deleteCart(@Field("good") String ids);
//
//
//    /*修改购物车商品数量*/
//    @FormUrlEncoded
//    @POST(Api.shopping.UPDATECART)
//    Flowable<UserInfo> updateCart(@Field("good") String id,
//                                  @Field("amount") int amount);
//
//
//    /*添加商品到购物车*/
//    @FormUrlEncoded
//    @POST(Api.shopping.ADDCART)
//    Flowable<GoodsListInfo> addCart(@Field("product") String id,
//                                    @Field("amount") int amount,//数量
//                                    @Field("property") int property);//用户选择的类型
//
//    /*添加商品到购物车*/
//    @FormUrlEncoded
//    @POST(Api.shopping.ADDCART)
//    Flowable<GoodsListInfo> addCart(@Field("product") String id,
//                                    @Field("amount") int amount);
//
//    /*购物车--结算*/
//    @POST(Api.shopping.CHECKOUTCART)
//    Flowable<UserInfo> checkoutCart();
//
//    /*购物车--结算1*/
////    @FormUrlEncoded
////    @POST(Api.shopping.UPDATECART)
////    Flowable<UserInfo> checkoutCart(@Field("good") String id,
////                                  @Field("amount") int amount);
//
//    /*商品列表*/
//    @FormUrlEncoded
//    @POST(Api.shopping.PRODUCTLIST)
//    Flowable<GoodsListInfo> getProductList(@Field("category") long id,
//                                           @Field("page") long page,
//                                           @Field("per_page") int per_page,
//                                           @Field("is_hot") int is_hot,
//                                           @Field("sort_key") int sort_key,
//                                           @Field("sort_value") int sort_value);
//
//
//    /*-------------------------------------------------------------------我的---------------------------------------------------*/
//
//    @FormUrlEncoded
//    @POST(Api.me.ADDRESSLIST)
//    Flowable<BaseResponse<AddressInfo>> getAddressList(@Field("id") String id);
//
//    @FormUrlEncoded
//    @POST(Api.me.UPDATEADDRESS)
//    Flowable<BaseResponse<String>> updateAddress(@Field("id") String id,
//                                                 @Field("status") String status,
//                                                 @Field("name") String name,
//                                                 @Field("phone") String phone,
//                                                 @Field("address") String address,
//                                                 @Field("detailed") String detailed);
//
//    /*站点信息*/
//    @POST(Api.me.SITE)
//    Flowable<SiteInfo> getSite();
//
//
//    /*获取红包列表*/
//    @FormUrlEncoded
//    @POST(Api.me.REDLIST)
//    Flowable<RedItemInfo> getRedList(@Field("status") String status,
//                                     @Field("page") long page,
//                                     @Field("per_page") String perPage);
//
//    /*收藏列表*/
//    @FormUrlEncoded
//    @POST(Api.me.LIKEDLIST)
//    Flowable<GoodsListInfo> getLikedList(@Field("page") long page,
//                                         @Field("per_page") String perPage);
//
//    /*取消收藏*/
//    @FormUrlEncoded
//    @POST(Api.me.UNLIKE)
//    Flowable<UserInfo> getUnlike(@Field("product") long product);
//
//}
