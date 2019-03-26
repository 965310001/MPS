package com.mingpinmall.classz.ui.vm;

import com.mingpinmall.classz.ui.vm.bean.ClassificationBean;
import com.mingpinmall.classz.ui.vm.bean.ClassificationRighitBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ClassifyService {

    String CLASSIFYLEFT = "/mobile/index.php";
//    String CLASSIFYRIGHT = "/mobile/index.php";

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

    /*商品分类*/
    @GET(CLASSIFYLEFT)
    Flowable<ClassificationRighitBean> getProductsList(@Query("app") String app,
                                                       @Query("wwi") String wwi,
                                                       @Query("gc_id") String id);
}
