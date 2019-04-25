package com.mingpinmall.me.ui.api;

import com.goldze.common.dmvvm.base.bean.AddressDataBean;
import com.goldze.common.dmvvm.base.bean.BaseNothingBean;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.mingpinmall.me.ui.bean.*;

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
     * 描述：会员积分记录
     * 请求地址
     * https://www.mingpinmall.cn/mo_bile/index.php?app=member_points&wwi=pointslog&key=&curpage=1&page=10
     * 请求方式：get
     * 参数 无
     */
    String VIPPOINTLOG = "/mo_bile/index.php?app=member_points&wwi=pointslog";

    @GET(VIPPOINTLOG)
    Flowable<BaseResponse<VipPointListBean>> getVipPointLog(
            @Query("key") String key,
            @Query("page") int page,
            @Query("curpage") int curpage
    );

    /**
     * 描述：会员积分
     * 请求地址
     * https://www.mingpinmall.cn/mo_bile/index.php?app=member_index&wwi=my_asset&key=&fields=point
     * 请求方式：get
     * 参数 无
     */
    String VIPPOINT = "/mo_bile/index.php?app=member_index&wwi=my_asset&fields=point";

    @GET(VIPPOINT)
    Flowable<BaseResponse<VipPointBean>> getVipPoint(
            @Query("key") String key
    );

    /**
     * 描述：确认领取
     * 请求地址
     * https://www.mingpinmall.cn/mo_bile/index.php?app=member_voucher&wwi=voucher_pwex
     * 请求方式：post
     * 参数
     * key:用户key
     * pwd_code：代金券卡密
     */

    String CP_CHARGE = "/mo_bile/index.php?app=member_voucher&wwi=voucher_pwex";

    @FormUrlEncoded
    @POST(CP_CHARGE)
    Flowable<BaseNothingBean> cpCharge(
            @Field("key") String key,
            @Field("pwd_code") String rc_sn,
            @Field("_client") String client
    );

    /**
     * 描述：代金券列表
     * 请求地址
     * https://www.mingpinmall.cn/mo_bile/index.php?app=member_voucher&wwi=voucher_list&key=&curpage=1&page=10
     * 请求方式：get
     * 请求参数：key
     */

    String CP_LIST = "/mo_bile/index.php?app=member_voucher&wwi=voucher_list";

    @GET(CP_LIST)
    Flowable<BaseResponse<CouponListBean>> getCouponList(
            @Query("key") String key,
            @Query("page") int page,
            @Query("curpage") int curpage
    );

    /**
     * 描述：请求自己红包列表
     * 请求地址：
     * https://www.mingpinmall.cn/mo_bile/index.php?app=member_redpacket&wwi=redpacket_list&key=&curpage=1&page=10
     * 参数
     * key：用户key
     */

    String PACKETLIST = "/mo_bile/index.php?app=member_redpacket&wwi=redpacket_list";

    @GET(PACKETLIST)
    Flowable<BaseResponse<PacketListBean>> getPacketList(
            @Query("key") String key,
            @Query("page") int page,
            @Query("curpage") int curpage
    );

    /**
     * 描述：红包提交领取
     * 请求地址
     * https://www.mingpinmall.cn/mo_bile/index.php?app=member_redpacket&wwi=rp_pwex
     * 请求参数
     * key:用户key
     * pwd_code：红包卡密
     */

    String PACKETCHARGE = "/mo_bile/index.php?app=member_redpacket&wwi=rp_pwex";

    @FormUrlEncoded
    @POST(PACKETCHARGE)
    Flowable<BaseNothingBean> packetCharge(
            @Field("key") String key,
            @Field("pwd_code") String pwd_code,
            @Field("_client") String client
    );

    /**
     * 描述：确认充值
     * 请求地址
     * https://www.mingpinmall.cn/mo_bile/index.php?app=member_fund&wwi=rechargecard_add
     * 请求方式：post
     * 参数
     * key:用户key
     * rc_sn:充值卡号
     */

    String RC_CHARGE = "/mo_bile/index.php?app=member_fund&wwi=rechargecard_add";

    @FormUrlEncoded
    @POST(RC_CHARGE)
    Flowable<BaseNothingBean> rcCharge(
            @Field("key") String key,
            @Field("rc_sn") String rc_sn,
            @Field("_client") String client
    );

    /**
     * 描述：充值卡使用记录
     * 请求地址
     * https://www.mingpinmall.cn/mo_bile/index.php?app=member_fund&wwi=rcblog&key=&curpage=1&page=10
     * 请求方式：get
     * 参数
     * key:用户key
     */

    String RCBLOG = "/mo_bile/index.php?app=member_fund&wwi=rcblog";

    @GET(RCBLOG)
    Flowable<BaseResponse<RCardLogBean>> getRCBLog(
            @Query("key") String key,
            @Query("page") int page,
            @Query("curpage") int curpage
    );

    /**
     * 描述：账户余额
     * 请求地址
     * https://www.mingpinmall.cn/mo_bile/index.php?app=member_index&wwi=my_asset&key=&fields=available_rc_balance
     * 参数
     * key:用户key
     */
    String AVAILABLE_RC_BALANCE = "/mo_bile/index.php?app=member_index&wwi=my_asset";

    @GET(AVAILABLE_RC_BALANCE)
    Flowable<BaseResponse<RCardBalanceBean>> getRCBalance(
            @Query("key") String key,
            @Query("fields") String fields
    );

    /**
     * 描述：退款列表
     * 请求地址
     * https://www.mingpinmall.cn/mo_bile/index.php?app=member_refund&wwi=get_refund_list&key=&curpage=1&page=10
     * 请求方式：get
     */
    String REFUND_LIST = "/mo_bile/index.php?app=member_refund&wwi=get_refund_list";

    @GET(REFUND_LIST)
    Flowable<BaseResponse<RefundBean>> getRefundList(
            @Query("key") String key,
            @Query("page") int page,
            @Query("curpage") int curpage
    );

    /**
     * 描述：退货列表
     * 请求地址
     * https://www.mingpinmall.cn/mo_bile/index.php?app=member_return&wwi=get_return_list&key=&curpage=1&page=10
     * 请求方式get
     */
    String RETURN_LIST = "/mo_bile/index.php?app=member_return&wwi=get_return_list";

    @GET(RETURN_LIST)
    Flowable<BaseResponse<ReturnBean>> getRetrunList(
            @Query("key") String key,
            @Query("page") int page,
            @Query("curpage") int curpage
    );

    /**
     * 描述：取消订单
     * 请求地址：https://www.mingpinmall.cn/mo_bile/index.php?app=member_order&wwi=order_cancel
     * 请求方式：post
     * 请求参数：
     * order_id:订单id
     */
    String CANCEL_ORDER = "/mo_bile/index.php?app=member_order&wwi=order_cancel";

    @POST(CANCEL_ORDER)
    @FormUrlEncoded
    Flowable<BaseNothingBean> cancelOrder(
            @Field("key") String key,
            @Field("order_id") String order_id
    );

    /**
     * 描述：取消订单
     * 请求地址：
     * https://www.mingpinmall.cn/mo_bile/index.php?app=member_vr_order&wwi=order_cancel
     * 请求方式：post
     * 请求参数：
     * Order_id:订单id
     * Key:用户key
     */
    String CANCEL_VR_ORDER = "/mo_bile/index.php?app=member_vr_order&wwi=order_cancel";

    @POST(CANCEL_VR_ORDER)
    @FormUrlEncoded
    Flowable<BaseNothingBean> cancelVirtualOrder(
            @Field("key") String key,
            @Field("order_id") String order_id
    );

    /**
     * 描述：虚拟订单详情
     * 请求地址：https://www.mingpinmall.cn/mo_bile/index.php?app=goods&wwi=store_o2o_addr
     * 请求方式：get
     * 请求参数：
     * store_id:店铺id
     */
    String V_ORDER_ADDRS = "/mo_bile/index.php?app=goods&wwi=store_o2o_addr";

    @GET(V_ORDER_ADDRS)
    Flowable<BaseResponse<VirtualStoreAddrsBean>> getVitrualOrderStoreAddrs(
            @Query("key") String key,
            @Query("store_id") String order_id
    );

    /**
     * 描述：虚拟订单详情
     * 请求地址：https://www.mingpinmall.cn/mo_bile/index.php?app=member_vr_order&wwi=order_info
     * 请求方式：get
     * 请求参数：
     * order_id:订单id
     */
    String V_ORDER_IMFORMATION = "/mo_bile/index.php?app=member_vr_order&wwi=order_info";

    @GET(V_ORDER_IMFORMATION)
    Flowable<BaseResponse<VirtualInformationBean>> getVitrualOrderInformation(
            @Query("key") String key,
            @Query("order_id") String order_id
    );

    /**
     * 描述：退款详情
     * 请求地址
     * https://www.mingpinmall.cn/mo_bile/index.php?app=member_refund&wwi=get_refund_info&key=&refund_id=1
     * 请求方式：get
     */
    String REFUND_INFORMATION = "/mo_bile/index.php?app=member_refund&wwi=get_refund_info";

    @GET(REFUND_INFORMATION)
    Flowable<BaseResponse<RefundInformation>> getRefundInformation(
            @Query("key") String key,
            @Query("refund_id") String refundId
    );

    /**
     * 描述：退货详情
     * 请求地址
     * https://www.mingpinmall.cn/mo_bile/index.php?app=member_return&wwi=get_return_info&key=&return_id=1
     * 请求方式：get
     */
    String RETURN_INFORMATION = "/mo_bile/index.php?app=member_return&wwi=get_return_info";

    @GET(RETURN_INFORMATION)
    Flowable<BaseResponse<ReturnInformation>> getReturnInformation(
            @Query("key") String key,
            @Query("return_id") String returnId
    );

    /**
     * 描述：账户余额
     * 请求地址：
     * http://www.mingpinmall.cn/mo_bile/index.php?app=member_index&wwi=my_asset&key=9d818aeef0	3b5778b2a3d1b3eb0de5bb&fields=predepoit
     * 请求方式：post
     * 参数
     * key：用户key
     * fields：predepoit（固定值）
     */
    String PREDEPOIT = "/mo_bile/index.php?app=member_index&wwi=my_asset";

    @GET(PREDEPOIT)
    Flowable<BaseResponse<Predepoit>> getPredepoit(
            @Query("key") String key,
            @Query("fields") String fields
    );

    /**
     * 描述：账户余额列表
     * 请求地址:
     * https://www.mingpinmall.cn/mo_bile/index.php?app=member_fund&wwi=predepositlog&key=&curpage=1&page=10
     * 请求方式：get
     */
    String PREDEPOITLOG = "/mo_bile/index.php?app=member_fund&wwi=predepositlog";

    @GET(PREDEPOITLOG)
    Flowable<BaseResponse<PredepoitLogBean>> getPredepoitLog(
            @Query("key") String key,
            @Query("page") int page,
            @Query("curpage") int curpage
    );

    /**
     * 描述：充值明细
     * https://www.mingpinmall.cn/mo_bile/index.php?app=member_fund&wwi=pdrechargelist&key=&curpage=1&page=10
     * 请求方式：get
     */
    String PDRECHARGELIST = "/mo_bile/index.php?app=member_fund&wwi=pdrechargelist";

    @GET(PDRECHARGELIST)
    Flowable<BaseResponse<PdrechargeBean>> getPdreChargeList(
            @Query("key") String key,
            @Query("page") int page,
            @Query("curpage") int curpage
    );

    /**
     * 描述：余额提现
     * 请求地址：
     * https://www.mingpinmall.cn/mo_bile/index.php?app=member_fund&wwi=pdcashlist&key=9d818aeef03b5778b2a3d1b3eb0de5bb&curpage=1&page=10
     * 请求方式：get
     */
    String PDCASHLIST = "/mo_bile/index.php?app=member_fund&wwi=pdcashlist";

    @GET(PDCASHLIST)
    Flowable<BaseResponse<PdcashBean>> getPdcashList(
            @Query("key") String key,
            @Query("page") int page,
            @Query("curpage") int curpage
    );

    /**
     * 描述：提现明细
     * 请求地址
     * https://www.mingpinmall.cn/mo_bile/index.php?app=member_fund&wwi=pdcashinfo&key=0511294120a9978607165b939a578d79&pdc_id=1
     * 请求方式：get
     * 参数
     * key：用户key
     * pdc_id:单子id
     */
    String PDCASHINFO = "/mo_bile/index.php?app=member_fund&wwi=pdcashinfo";

    @GET(PDCASHINFO)
    Flowable<BaseResponse<PdcashInfoBean>> getPdCashInfo(
            @Query("key") String key,
            @Query("pdc_id") String pdcId
    );

    /**
     * 描述：分销管理
     * 请求地址：
     * http://www.mingpinmall.cn/mo_bile/index.php?app=member_invite&wwi=inviteone&key=644b675d88d2c167b7513d0bdc7dadb9&curpage=1&page=10
     * 请求方式：get
     * 请求参数：
     * wwi:
     * inviteone:一级列表
     * invitetwo：二级列表
     * invitethir：三级列表
     * curpage:当前页
     * page:显示数量
     */
    String GETINVITE = "/mo_bile/index.php?app=member_invite";

    @GET(GETINVITE)
    Flowable<BaseNothingBean> getInviteList(
            @Query("key") String key,
            @Query("wwi") String wwi,
            @Query("page") int page,
            @Query("curpage") int curpage
    );

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
    Flowable<BaseResponse<BaseIntDatasBean>> sendFeedBack(
            @Field("key") String key,
            @Field("feedback") String feedback
    );

    /**
     * 描述：店铺收藏列表
     * 请求地址：
     * http://www.mingpinmall.cn/mo_bile/index.php?app=member_favorites_store&wwi=favorites_list &curpage=1&page=10
     * 请求方式：GET
     * 请求参数：无
     */
    String ShopsCollectList = "/mo_bile/index.php?app=member_favorites_store&wwi=favorites_list";

    /*店铺收藏*/
    @GET(ShopsCollectList)
    Flowable<BaseResponse<ShopsCollectionBean>> getShopsCollectList(
            @Query("key") String key,
            @Query("curpage") int curpage,
            @Query("page") int page
    );

    /**
     * 描述：店铺删除收藏动作
     * 请求地址：https://www.mingpinmall.cn/mo_bile/index.php?app=member_favorites_store &wwi=favorites_del
     * 请求方式：post
     * 请求参数：
     * key:用户key
     * 	store_id:产品id
     */
    String DELSHOPSCOLLECT = "/mo_bile/index.php?app=member_favorites_store&wwi=favorites_del";

    /*店铺收藏*/
    @FormUrlEncoded
    @POST(DELSHOPSCOLLECT)
    Flowable<BaseNothingBean> deleShopsCollect(
            @Field("key") String key,
            @Field("_client") String clinet,
            @Field("store_id") String storeId
    );

    /**
     * 描述：产品删除收藏动作
     * 请求地址：https://www.mingpinmall.cn/mo_bile/index.php?app=member_favorites&wwi=favorites_del
     * 请求方式：post
     * 请求参数：
     * 	fav_id:产品id
     */
    String DELGOODSCOLLECT = "/mo_bile/index.php?app=member_favorites&wwi=favorites_del";

    /*店铺收藏*/
    @FormUrlEncoded
    @POST(DELGOODSCOLLECT)
    Flowable<BaseNothingBean> deleGoodsCollect(
            @Field("key") String key,
            @Field("_client") String clinet,
            @Field("fav_id") String favId
    );

    /**
     * 描述：产品收藏
     * 请求地址：http://www.mingpinmall.cn/mo_bile/index.php?app=member_favorites&wwi=favorites_list &curpage=1&page=10
     * 请求方式：GET
     * 请求参数：无
     */
    String ProductCollectList = "/mo_bile/index.php?app=member_favorites&wwi=favorites_list";

    /*产品收藏*/
    @GET(ProductCollectList)
    Flowable<BaseResponse<ProductCollectionBean>> getProductCollectList(
            @Query("key") String key,
            @Query("curpage") int curpage,
            @Query("page") int page
    );

    /**
     * 描述：查看订单详情
     * 请求地址：https://www.mingpinmall.cn/mo_bile/index.php?app=member_order&wwi=order_info&order_id=71
     * 请求方式：get
     * 请求参数：
     * order_id:订单id
     */
    String OrderInformation = "/mo_bile/index.php?app=member_order&wwi=order_info";

    @GET(OrderInformation)
    Flowable<BaseResponse<OrderInformationBean>> getOrderInformation(
            @Query("key") String key,
            @Query("order_id") String order_id
    );

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
    String GetPhysicalOrder = "/mo_bile/index.php?app=member_order&wwi=order_list";

    @FormUrlEncoded
    @POST(GetPhysicalOrder)
    Flowable<BaseResponse<PhysicalOrderBean>> getPhysicalOrderList(
            @Field("key") String key,
            @Field("state_type") String state_type,
            @Field("order_key") String order_key,
            @Field("page") int page,
            @Field("curpage") int curpage
    );

    /**
     * 描述：请求虚拟订单列表
     * 请求地址：
     * https://www.mingpinmall.cn/mo_bile/index.php?app=member_vr_order&wwi=order_list&page=10&curpage=1
     * 请求方式：POST
     * 请求参数：
     * State_type:
     * [state_new:待付款]
     * [state_pay:待使用]
     * Order_key:搜索内容，产品标题或订单号
     */
    String GetVirtualOrder = "/mo_bile/index.php?app=member_vr_order&wwi=order_list";

    @FormUrlEncoded
    @POST(GetVirtualOrder)
    Flowable<BaseResponse<VirtualOrderBean>> getVirtualOrderList(
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
    Flowable<BaseResponse<FootprintBean>> getFootprint(
            @Query("key") String key,
            @Query("page") int page,
            @Query("curpage") int curPage
    );

    /**
     * 描述：清空浏览记录
     * 请求地址:https://www.mingpinmall.cn/mo_bile/index.php?app=member_goodsbrowse&wwi=browse_clearall
     * 请求方式：get
     * 请求参数：无
     */
    String ClearnFootprint = "/mo_bile/index.php?app=member_goodsbrowse&wwi=browse_clearall";

    @GET(ClearnFootprint)
    Flowable<BaseNothingBean> clearnFootprint(
            @Query("key") String key,
            @Query("_client") String client
    );

    /**
     * 描述：我的财产
     * 请求地址：http://www.mingpinmall.cn/mo_bile/index.php?app=member_index&wwi=my_asset
     * 请求方式：get
     * 请求参数：无
     */
    String myAsset = "/mo_bile/index.php?app=member_index&wwi=my_asset";

    @GET(myAsset)
    Flowable<BaseResponse<PropertyBean>> getMyAsset(
            @Query("key") String key
    );

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
    Flowable<BaseResponse<CityBean>> getCityList(
            @Query("key") String key,
            @Query("area_id") String area_id
    );

    /**
     * 描述：收货地址管理
     * 请求地址：http://www.mingpinmall.cn/mo_bile/index.php?app=member_address&wwi=address_list
     * 请求方式：get
     * 请求参数：无
     */
    String AddressList = "/mo_bile/index.php?app=member_address&wwi=address_list";

    @GET(AddressList)
    Flowable<BaseResponse<AddressDataBean>> getAddressList(
            @Query("key") String key
    );

    /**
     * 描述：收货地址详细信息
     * 请求地址：http://www.mingpinmall.cn/mo_bile/index.php?app=member_address&wwi=address_info
     * 请求方式：post
     * 请求参数：
     * address_id:地址id
     */
    String GetAddress = "/mo_bile/index.php?app=member_address&wwi=address_info";

    @FormUrlEncoded
    @POST(GetAddress)
    Flowable<BaseResponse<AddressDataBean.AddressListBean>> getAddress(
            @Field("key") String key,
            @Field("address_id") String address_id
    );

    /**
     * 描述：删除收货地址
     * 请求地址：http://www.mingpinmall.cn/mo_bile/index.php?app=member_address&wwi=address_del
     * 请求方式：post
     * 请求参数：
     * address_id:地址id
     */
    String DelAddress = "/mo_bile/index.php?app=member_address&wwi=address_del";

    @FormUrlEncoded
    @POST(DelAddress)
    Flowable<BaseNothingBean> delAddress(
            @Field("key") String key,
            @Field("address_id") String address_id
    );

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
    Flowable<BaseNothingBean> addAddress(
            @Field("key") String key,
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
    Flowable<BaseNothingBean> editAddress(
            @Field("key") String key,
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
    Flowable<BaseResponse<MyInfoBean>> getUserInfo(
            @Query("key") String key
    );

    /*检测是否设置了支付密码*/
    @GET(PAYPWD_INFO)
    Flowable<BaseResponse<BaseCheckBean>> getPayPwdInfo(
            @Query("key") String key
    );

    /*检测是否绑定手机*/
    @GET(PHONENUMBER_INFO)
    Flowable<BaseResponse<BaseCheckBean>> getPhoneInfo(
            @Query("key") String key
    );


}
