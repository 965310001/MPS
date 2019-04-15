package com.mingpinmall.home.ui.api;

import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.mingpinmall.home.ui.bean.HomeItemBean;
import com.mingpinmall.home.ui.bean.ShopStreetBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/3
 **/
public interface HomeApiService {

    /**
     * 描述：首页数据调用
     * 请求地址：http://www.mingpinmall.cn/mo_bile/index.php?app=index
     * 请求方式：get
     * 请求参数：无
     */
    String HOME_PAGE = "/mo_bile/index.php?app=index";

    /*我的商城中用户信息获取*/
    @GET(HOME_PAGE)
    Flowable<HomeItemBean> getHomeDataList();

    /**
     * 描述：店铺街
     * 请求地址：https://www.mingpinmall.cn/mo_bile/index.php?app=store&wwi=store_list&page=10&curpage=1
     * 请求方式：get
     * 请求参数：无
     */
    String STORE_STREET = "/mo_bile/index.php?app=store&wwi=store_list";

    /*店铺街*/
    @GET(STORE_STREET)
    Flowable<BaseResponse<ShopStreetBean>> getStoreStreet(
            @Query("keyword") String keyword,
            @Query("area_info") String area_info,
            @Query("sc_id") int sc_id,
            @Query("key") String key,
            @Query("page") int page,
            @Query("curpage") int curPage
    );

}
