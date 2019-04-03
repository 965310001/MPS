package com.mingpinmall.home.ui.api;

import com.mingpinmall.home.ui.bean.HomeItemBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;

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

}
