package com.mingpinmall.classz.ui.api;

import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.mingpinmall.classz.ResultBean;
import com.mingpinmall.classz.ui.vm.bean.BrandListInfo;
import com.mingpinmall.classz.ui.vm.bean.BuyStepInfo;
import com.mingpinmall.classz.ui.vm.bean.ClassificationBean;
import com.mingpinmall.classz.ui.vm.bean.ClassificationRighitBean;
import com.mingpinmall.classz.ui.vm.bean.ConfirmOrderBean;
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

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ClassifyService {
    String BASEURL = "/mo_bile/index.php";
    
    String SHAPPINGLIST = "/mo_bile/index.php?app=goods&wwi=goods_list";

    /*品牌*/
    String BRAND = "/mo_bile/index.php?app=brand&wwi=recommend_list";
    /*商品详情*/
    String GOODSDETAIL = "/mo_bile/index.php?app=goods&wwi=goods_detail";

    //    加入购物车
//    String ADD_CART_APP = "member_cart";
//    String ADD_CART_WWI = "cart_add";
    //    搜索列表
//    String[] HOTKEY = {"index", "search_key_list"};
//    添加收藏
//    String ADDCOLLECTION_APP = "member_favorites";
//    String ADDCOLLECTION_WWI = "favorites_add";
    /*删除收藏*/
//    String DELCOLLECTION_WWI = "favorites_del";


    /*商品评价*/
    String EVALUATE = "/mo_bile/index.php?app=goods&wwi=goods_evaluate";


    /*左边*/
    @GET(BASEURL)
    Flowable<BaseResponse<ClassificationBean>> getLeft(@Query("app") String app,
                                                       @Query("wwi") String wwi);

    /*右边*/
    @GET(BASEURL)
    Flowable<ClassificationRighitBean> getRight(@Query("app") String app,
                                                @Query("wwi") String wwi,
                                                @Query("gc_id") String id);

    /*品牌*/
    @GET(BRAND)
    Flowable<BrandListInfo> getRightByBrand();

    /*商品分类*/
    @GET(SHAPPINGLIST)
    Flowable<GoodsListInfo> getShappingList(@QueryMap Map<String, Object> map);

    /*商品评价*/
    @GET(EVALUATE)
    Flowable<GoodsCommentListBean> getEvaluate(@QueryMap Map<String, Object> map);

//    商品评价
//    @GET(EVALUATE)
//    Flowable<BaseResponse<AreaListInfo>> getArea(@QueryMap Map<String, Object> map);
//    商品详情
//    @GET(GOODSDETAIL)
//    Flowable<GoodsDetailInfo> getGoodsDetail(@Query("goods_id") String goodsId);

    @GET(GOODSDETAIL)
    Flowable<GoodsDetailInfo> getGoodsDetail(@Query("goods_id") String goodsId, @Query("key") String key);

    /*商品列表*/
    @GET(BASEURL)
    Flowable<BaseResponse<HotKeyInfo>> getHotKeys(@QueryMap Map<String, Object> map);

    /*添加 删除 收藏*/
//    @FormUrlEncoded
//    @POST("{path}")
//    Flowable<ResultBean> execute(@Path("path") String url,
//                                 @Query("Goods_id") String goodsId);

    /*获取订单信息*/
    @FormUrlEncoded
    @POST(BASEURL)
    Flowable<BaseResponse<OrderInfo>> getOrderInfo(@FieldMap Map<String, Object> map);

    /*获取订单信息*/
    @FormUrlEncoded
    @POST(BASEURL)
    Flowable<BaseResponse<ConfirmOrderBean>> getOrderInfo2(@FieldMap Map<String, Object> map);

    /*获取发票内容*/
    @FormUrlEncoded
    @POST(BASEURL)
    Flowable<BaseResponse<InvoiceListInfo>> getInvoiceContentList(@FieldMap Map<String, Object> map);


    /*店铺信息*/
    @FormUrlEncoded
    @POST(BASEURL)
    Flowable<BaseResponse<StoreInfo>> getStoreInfo(@FieldMap Map<String, Object> map);

    /*全部商品*/
    @FormUrlEncoded
    @POST(BASEURL)
    Flowable<GoodsListInfo> getStoreGoods(@FieldMap Map<String, Object> map);

    /*收藏排行*/
    @FormUrlEncoded
    @POST(BASEURL)
    Flowable<GoodsListInfo> getStoreGoodsRank(@FieldMap Map<String, Object> map);

    /*店铺活动*/
    @FormUrlEncoded
    @POST(BASEURL)
    Flowable<StorePromotionInfo> getStorePromotionInfo(@FieldMap Map<String, Object> map);


    /*店铺收藏 取消*/
    @FormUrlEncoded
    @POST(BASEURL)
    Flowable<ResultBean> getStoreFavorites(@FieldMap Map<String, Object> map);

    /*店铺介绍*/
    @FormUrlEncoded
    @POST(BASEURL)
    Flowable<BaseResponse<StoreInfo>> getStoreIntro(@FieldMap Map<String, Object> map);

    /*店铺代金券*/
    @FormUrlEncoded
    @POST(BASEURL)
    Flowable<BaseResponse<VoucherInfo>> getVoucherTplList(@FieldMap Map<String, Object> map);

    /*提交订单*/
    @FormUrlEncoded
    @POST(BASEURL)
    Flowable<BaseResponse<BuyStepInfo>> getBuyStep2(@FieldMap Map<String, Object> map);

    /*消息列表*/
    @GET(BASEURL)
    Flowable<BaseResponse<MsgListInfo>> getNodeInfo(@QueryMap Map<String, Object> map);

    /*历史消息列表*/
    @FormUrlEncoded
    @POST(BASEURL)
    Flowable<BaseResponse<MsgListInfo>> getChatLog(@FieldMap Map<String, Object> map);

    /*发送信息*/
    @FormUrlEncoded
    @POST(BASEURL)
    Flowable<BaseResponse<MsgInfo>> sendMsg(@FieldMap Map<String, Object> map);

    /*发送图片*/
    @Multipart
    @POST(BASEURL)
    Flowable<BaseResponse<MsgInfo>> picUpload(@QueryMap Map<String, Object> map,
                                              @Part("up_img_kf\"; filename=\"test1.png") RequestBody images);

    /*getPayNew*/
    @GET(BASEURL)
    Flowable<PayMessageInfo> getPayNew(@QueryMap Map<String, Object> map);

    /***************************************封装通用******************************/
    /*封装通用*/
    @FormUrlEncoded
    @POST(BASEURL)
    Flowable<ResultBean> execute(@Field("app") String app,
                                 @Field("wwi") String wwi,
                                 @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(BASEURL)
    Flowable<ResultBean> execute(@FieldMap Map<String, Object> map);
}