package com.mingpinmall.me.ui.api;

import com.goldze.common.dmvvm.base.bean.AddressDataBean;
import com.goldze.common.dmvvm.base.bean.BaseNothingBean;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.mingpinmall.me.ui.bean.BaseCheckBean;
import com.mingpinmall.me.ui.bean.BaseIntDatasBean;
import com.mingpinmall.me.ui.bean.CityBean;
import com.mingpinmall.me.ui.bean.FootprintBean;
import com.mingpinmall.me.ui.bean.MyInfoBean;
import com.mingpinmall.me.ui.bean.ProductCollectionBean;
import com.mingpinmall.me.ui.bean.PropertyBean;
import com.mingpinmall.me.ui.bean.ShopsCollectionBean;

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
     * feedback:反馈内容
     */
    String FeedBack = "mo_bile/index.php?app=member_feedback&wwi=feedback_add";

    @FormUrlEncoded
    @POST(FeedBack)
    Flowable<BaseResponse<BaseIntDatasBean>> sendFeedBack(@Field("key") String key, @Field("feedback") String feedback);

    /**
     * 描述：店铺收藏
     * 请求地址：
     * http://www.mingpinmall.cn/mo_bile/index.php?app=member_favorites_store&wwi=favorites_list &curpage=1&page=10
     * 请求方式：GET
     * 请求参数：无
     */
    String ShopsCollectList = "/mo_bile/index.php?app=member_favorites_store&wwi=favorites_list";

    /*店铺收藏*/
    @GET(ShopsCollectList)
    Flowable<BaseResponse<ShopsCollectionBean>> getShopsCollectList(@Query("key") String key, @Query("curpage") int curpage, @Query("page") int page);

    /**
     * 描述：产品收藏
     * 请求地址：http://www.mingpinmall.cn/mo_bile/index.php?app=member_favorites&wwi=favorites_list &curpage=1&page=10
     * 请求方式：GET
     * 请求参数：无
     */
    String ProductCollectList = "/mo_bile/index.php?app=member_favorites&wwi=favorites_list";

    /*产品收藏*/
    @GET(ProductCollectList)
    Flowable<BaseResponse<ProductCollectionBean>> getProductCollectList(@Query("key") String key, @Query("curpage") int curpage, @Query("page") int page);

    /**
     * 描述：根据条件查询订单
     * 请求地址：
     * http://www.mingpinmall.cn/mo_bile/index.php?app=member_order&wwi=order_list&page=10&curpage=1
     * 请求方式：POST
     * 请求参数：
     * State_type:
     * [state_new:待付款]
     * [state_send:待收货]
     * [state_notakes:待自提]
     * [state_noeval:待评价]
     * Order_key:搜索内容，产品标题或订单号
     */
    String getOrder = "/mo_bile/index.php?app=member_order&wwi=order_list";

    @FormUrlEncoded
    @POST(getOrder)
    Flowable<BaseIntDatasBean> getOrder(
            @Field("key") String key,
            @Field("state_type") String state_type,
            @Field("order_key") String order_key,
            @Field("page") int page,
            @Field("curpage") int curpage
    );

    /**
     * 描述：浏览记录
     * 请求地址：
     * http://www.mingpinmall.cn/mo_bile/index.php?app=member_goodsbrowse&wwi=browse_list &curpage=1&page=10
     * 请求方式：get
     * 请求参数：无
     */
    String MyFootprint = "/mo_bile/index.php?app=member_goodsbrowse&wwi=browse_list";

    @GET(MyFootprint)
    Flowable<BaseResponse<FootprintBean>> getFootprint(@Query("key") String key, @Query("page") int page, @Query("curpage") int curPage);

    /**
     * 描述：清空浏览记录
     * 请求地址:http://www.mingpinmall.cn/mo_bile/index.php?app=member_ goodsbrowse&wwi=browse_clearall
     * 请求方式：get
     * 请求参数：无
     */
    String ClearnFootprint = "/mo_bile/index.php?app=member_ goodsbrowse&wwi=browse_clearall";

    @GET(ClearnFootprint)
    Flowable<BaseResponse<FootprintBean>> clearnFootprint(@Query("key") String key);

    /**
     * 描述：我的财产
     * 请求地址：http://www.mingpinmall.cn/mo_bile/index.php?app=member_index&wwi=my_asset
     * 请求方式：get
     * 请求参数：无
     */
    String myAsset = "/mo_bile/index.php?app=member_index&wwi=my_asset";

    @GET(myAsset)
    Flowable<BaseResponse<PropertyBean>> getMyAsset(@Query("key") String key);

    /**
     * 描述：各级地区
     * 请求地址：
     * http://www.mingpinmall.cn/mo_bile/index.php?app=area&wwi=area_list
     * 请求方式：get
     * 请求参数：
     * key:用户key
     * area_id:地区id
     */
    String GetCity = "/mo_bile/index.php?app=area&wwi=area_list";

    @GET(GetCity)
    Flowable<BaseResponse<CityBean>> getCityList(@Query("key") String key, @Query("area_id") String area_id);

    /**
     * 描述：收货地址管理
     * 请求地址：http://www.mingpinmall.cn/mo_bile/index.php?app=member_address&wwi=address_list
     * 请求方式：get
     * 请求参数：无
     */
    String AddressList = "/mo_bile/index.php?app=member_address&wwi=address_list";

    @GET(AddressList)
    Flowable<BaseResponse<AddressDataBean>> getAddressList(@Query("key") String key);

    /**
     * 描述：收货地址详细信息
     * 请求地址：http://www.mingpinmall.cn/mo_bile/index.php?app=member_address&wwi=address_info
     * 请求方式：post
     * 请求参数：
     * 	address_id:地址id
     */
    String GetAddress = "/mo_bile/index.php?app=member_address&wwi=address_info";

    @FormUrlEncoded
    @POST(GetAddress)
    Flowable<BaseResponse<AddressDataBean.AddressListBean>> getAddress(@Field("key") String key, @Field("address_id") String address_id);

    /**
     * 描述：删除收货地址
     * 请求地址：http://www.mingpinmall.cn/mo_bile/index.php?app=member_address&wwi=address_del
     * 请求方式：post
     * 请求参数：
     * 	address_id:地址id
     */
    String DelAddress = "/mo_bile/index.php?app=member_address&wwi=address_del";

    @FormUrlEncoded
    @POST(DelAddress)
    Flowable<BaseNothingBean> delAddress(@Field("key") String key, @Field("address_id") String address_id);

    /**
     * 描述：新增收货地址
     * 请求地址：http://www.mingpinmall.cn/mo_bile/index.php?app=member_address&wwi=address_add
     * 请求方式：post
     * 请求参数：
     * id_default:设置默认地址  0否，1是
     * true_name:收货人姓名
     * area_id:地区id   3级 如果三级没有id，则使用二级的
     * city_id:市级id   2级
     * area_info:地区 名字
     * address:详细地址 名字
     * mob_phone:联系手机
     */
    String AddAddress = "/mo_bile/index.php?app=member_address&wwi=address_add";

    @FormUrlEncoded
    @POST(AddAddress)
    Flowable<BaseNothingBean> addAddress(@Field("key") String key,
                                         @Field("is_default") int id_default,
                                         @Field("true_name") String name,
                                         @Field("city_id") String city_id,
                                         @Field("area_id") String area_id,
                                         @Field("area_info") String area_info,
                                         @Field("address") String address,
                                         @Field("mob_phone") String phone
    );

    /**
     * 描述：收货地址编辑
     * 请求地址：http://www.mingpinmall.cn/mo_bile/index.php?app=member_address&wwi=address_edit
     * 请求方式：post
     * 请求参数：
     * address_id:地址id
     * id_default:设置默认地址  0否，1是
     * true_name:收货人姓名
     * area_id:地区id   3级 如果三级没有id，则使用二级的
     * city_id:市级id   2级
     * area_info:地区 名字
     * address:详细地址 名字
     * mob_phone:联系手机
     */
    String EditAddress = "/mo_bile/index.php?app=member_address&wwi=address_edit";

    @FormUrlEncoded
    @POST(EditAddress)
    Flowable<BaseNothingBean> editAddress(@Field("key") String key,
                                                     @Field("address_id") String address_id,
                                                     @Field("is_default") int id_default,
                                                     @Field("true_name") String name,
                                                     @Field("city_id") String city_id,
                                                     @Field("area_id") String area_id,
                                                     @Field("area_info") String area_info,
                                                     @Field("address") String address,
                                                     @Field("mob_phone") String phone
    );

    /*我的商城中用户信息获取*/
    @GET(GETUSERINFO)
    Flowable<BaseResponse<MyInfoBean>> getUserInfo(@Query("key") String key);

    /*检测是否设置了支付密码*/
    @GET(PAYPWD_INFO)
    Flowable<BaseResponse<BaseCheckBean>> getPayPwdInfo(@Query("key") String key);

    /*检测是否绑定手机*/
    @GET(PHONENUMBER_INFO)
    Flowable<BaseResponse<BaseCheckBean>> getPhoneInfo(@Query("key") String key);


}
