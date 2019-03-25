package com.mingpinmall.classz.ui.vm;

import com.mingpinmall.classz.ui.vm.bean.ClassificationBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ClassifyService {

    String CLASSIFYLEFT = "/mobile/index.php?app=goods_class&wwi=index";
    String CLASSIFYRIGHT = "/mobile/index.php?app=goods_class&wwi=index";

    /*左边*/
    @POST(CLASSIFYLEFT)
    Flowable<ClassificationBean> getLeft();

    /*右边*/
    @FormUrlEncoded
    @POST(CLASSIFYRIGHT)
    Flowable<ClassificationBean> getRight(@Field("gc_id") String id);
}
