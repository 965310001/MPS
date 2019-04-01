package com.mingpinmall.me.ui.api;

import com.mingpinmall.me.ui.bean.MyInfoBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/1
 **/
public interface MeApiService {

    /**
     * 描述：我的商城中用户信息获取
     * 请求地址：/mo_bile/index.php?app=member_index&wwi=index
     * 请求方式：GET
     * 请求参数：无
     */
    String GETUSERINFO = "/mo_bile/index.php?app=member_index&wwi=index";

    /*我的商城中用户信息获取*/
    @GET(GETUSERINFO)
    Flowable<MyInfoBean> getUserInfo(@Query("key") String key);



}
