package com.mingpinmall.me.ui.api;

import com.mingpinmall.me.ui.bean.BaseCheckBean;
import com.mingpinmall.me.ui.bean.BaseIntDatasBean;
import com.mingpinmall.me.ui.bean.BasePageBean;
import com.mingpinmall.me.ui.bean.MyInfoBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    /**
     * 描述：检测是否设置了支付密码
     * 请求地址:
     * http://www.mingpinmall.cn/mo_bile/index.php?app=member_account&wwi=get_paypwd_info
     * 请求方式：GET
     * 请求参数：无
     */
    String PAYPWD_INFO = "/mo_bile/index.php?app=member_account&wwi=get_paypwd_info";

    /**
     * 描述：检测是否绑定手机
     * 请求地址:
     * http://www.mingpinmall.cn/mo_bile/index.php?app=member_account&wwi=get_mobile_info
     * 请求方式：GET
     * 请求参数：无
     */
    String PHONENUMBER_INFO = "/mo_bile/index.php?app=member_account&wwi=get_mobile_info";

    /**
     * 描述：用户反馈
     * 请求地址:
     * /mo_bile/index.php?app=member_feedback&wwi=feedback_add
     * 请求方式：POST
     * 请求参数：
     * 	feedback:反馈内容
     */
    String FeedBack = "mo_bile/index.php?app=member_feedback&wwi=feedback_add";

    @FormUrlEncoded
    @POST(FeedBack)
    Flowable<BaseIntDatasBean> sendFeedBack(@Field("key") String key, @Field("feedback") String feedback);

    /**
     * 描述：店铺收藏
     * 请求地址：
     * http://www.mingpinmall.cn/mo_bile/index.php?app=member_favorites_store&wwi=favorites_list &curpage=1&page=10
     * 请求方式：GET
     * 请求参数：无
     */
    String ShopsCollectList = "/mo_bile/index.php?app=member_favorites_store&wwi=favorites_list";

    /*我的商城中用户信息获取*/
    @GET(ShopsCollectList)
    Flowable<BasePageBean> getShopsCollectList(@Query("key") String key, @Query("curpage") int curpage, @Query("page") int page);

    /**
     * 描述：产品收藏
     * 请求地址：http://www.mingpinmall.cn/mo_bile/index.php?app=member_favorites&wwi=favorites_list &curpage=1&page=10
     * 请求方式：GET
     * 请求参数：无
     */
    String ProductCollectList = "/mo_bile/index.php?app=member_favorites&wwi=favorites_list";

    /*我的商城中用户信息获取*/
    @GET(ProductCollectList)
    Flowable<BasePageBean> getProductCollectList(@Query("key") String key, @Query("curpage") int curpage, @Query("page") int page);

    /*我的商城中用户信息获取*/
    @GET(GETUSERINFO)
    Flowable<MyInfoBean> getUserInfo(@Query("key") String key);

    /*检测是否设置了支付密码*/
    @GET(PAYPWD_INFO)
    Flowable<BaseCheckBean> getPayPwdInfo(@Query("key") String key);

    /*检测是否绑定手机*/
    @GET(PHONENUMBER_INFO)
    Flowable<BaseCheckBean> getPhoneInfo(@Query("key") String key);



}
