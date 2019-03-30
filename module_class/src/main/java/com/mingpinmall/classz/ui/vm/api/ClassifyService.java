package com.mingpinmall.classz.ui.vm.api;

import com.mingpinmall.classz.ResultBean;
import com.mingpinmall.classz.ui.vm.bean.BrandListInfo;
import com.mingpinmall.classz.ui.vm.bean.ClassificationBean;
import com.mingpinmall.classz.ui.vm.bean.ClassificationRighitBean;
import com.mingpinmall.classz.ui.vm.bean.GoodsListInfo;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ClassifyService {

    String CLASSIFYLEFT = "/mobile/index.php";
    String SHAPPINGLIST = "/mobile/index.php?app=goods&wwi=goods_list";

    /*品牌*/
    String BRAND = "/mo_bile/ index.php?app=member_index&wwi=index";

    /*添加收藏*/
    String ADDCOLLECTION = "/mo_bile/index.php?app=member_favorites&wwi=favorites_add";
    /*删除收藏*/
    String DELCOLLECTION = "/mo_bile/index.php?app=member_favorites&wwi=favorites_del";

    /*左边*/
    @FormUrlEncoded
    @POST(CLASSIFYLEFT)
    Flowable<ClassificationBean> getLeft(@Field("app") String app,
                                         @Field("wwi") String wwi);

    /*右边*/
    @GET(CLASSIFYLEFT)
    Flowable<ClassificationRighitBean> getRight(@Query("app") String app,
                                                @Query("wwi") String wwi,
                                                @Query("gc_id") String id);

    /*品牌*/
    @GET(BRAND)
    Flowable<BrandListInfo> getRightByBrand();

    /*商品分类*/
    @GET(SHAPPINGLIST)
    Flowable<GoodsListInfo> getShappingList(@QueryMap Map<String, Object> map);

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

    /*封装通用*/
    @FormUrlEncoded
    @GET("{path}")
    Flowable<ResultBean> execute(@Path("path") String url,
                                 @FieldMap Map<String, String> map);


}
