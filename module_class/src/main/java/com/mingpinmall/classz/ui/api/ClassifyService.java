package com.mingpinmall.classz.ui.api;

import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.mingpinmall.classz.ResultBean;
import com.mingpinmall.classz.ui.vm.bean.AreaListInfo;
import com.mingpinmall.classz.ui.vm.bean.BrandListInfo;
import com.mingpinmall.classz.ui.vm.bean.ClassificationBean;
import com.mingpinmall.classz.ui.vm.bean.ClassificationRighitBean;
import com.mingpinmall.classz.ui.vm.bean.GoodsCommentListBean;
import com.mingpinmall.classz.ui.vm.bean.GoodsDetailInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsListInfo;
import com.mingpinmall.classz.ui.vm.bean.HotKeyInfo;
import com.mingpinmall.classz.ui.vm.bean.OrderInfo;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ClassifyService {

    String BASEURL = "/mo_bile/index.php";

    //    String CLASSIFYLEFT = "/mobile/index.php";
    String SHAPPINGLIST = "/mo_bile/index.php?app=goods&wwi=goods_list";

    /*品牌*/
    String BRAND = "/mo_bile/index.php?app=brand&wwi=recommend_list";
    /*商品详情*/
    String GOODSDETAIL = "/mo_bile/index.php?app=goods&wwi=goods_detail";

    /*加入购物车*/
    String ADD_CART_APP = "member_cart";
    String ADD_CART_WWI = "cart_add";

    /*商品评价*/
    String EVALUATE = "/mo_bile/index.php?app=goods&wwi=goods_evaluate";

    /*搜索列表*/
    String[] HOTKEY = {"index", "search_key_list"};

    /*添加收藏*/
    String ADDCOLLECTION_APP = "member_favorites";
    String ADDCOLLECTION_WWI = "favorites_add";
    /*删除收藏*/
    String DELCOLLECTION_WWI = "favorites_del";

    /*左边*/
    @GET(BASEURL)
    Flowable<ClassificationBean> getLeft(@Query("app") String app,
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

    /*商品评价*/
    @GET(EVALUATE)
    Flowable<BaseResponse<AreaListInfo>> getArea(@QueryMap Map<String, Object> map);


    /*商品详情*/
    @GET(GOODSDETAIL)
    Flowable<GoodsDetailInfo> getGoodsDetail(@Query("goods_id") String goodsId);

    /*商品列表*/
    @GET(BASEURL)
    Flowable<BaseResponse<HotKeyInfo>> getHotKeys(@QueryMap Map<String, Object> map);

    /*添加收藏*/
//    @GET(ADDCOLLECTION)
//    Flowable<GoodsListInfo> getAddCollection(@Query("Goods_id") String goodsId);
//
//    /*删除收藏*/
//    @GET(DELCOLLECTION)
//    Flowable<GoodsListInfo> getDelCollection(@Query("Goods_id") String goodsId);

    /*添加 删除 收藏*/
    @FormUrlEncoded
    @POST("{path}")
    Flowable<ResultBean> execute(@Path("path") String url,
                                 @Query("Goods_id") String goodsId);

    /*获取订单信息*/
    @FormUrlEncoded
//    @Multipart
    @POST(BASEURL)
//    Flowable<BaseResponse<OrderInfo>> getOrderInfo(@QueryMap Map<String, Object> map);
    Flowable<BaseResponse<OrderInfo>> getOrderInfo(@FieldMap Map<String, Object> map);


    /*封装通用*/
    @FormUrlEncoded
    @POST("mo_bile/index.php")
    Flowable<ResultBean> execute(@Field("app") String app,
                                 @Field("wwi") String wwi,
                                 @FieldMap Map<String, Object> map);


}
