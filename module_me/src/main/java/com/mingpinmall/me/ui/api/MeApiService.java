package com.mingpinmall.me.ui.api;

import com.goldze.common.dmvvm.base.bean.AddressDataBean;
import com.goldze.common.dmvvm.base.bean.BaseNothingBean;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.bean.NewDatasResponse;
import com.mingpinmall.apppay.pay.PayLayoutBean;
import com.mingpinmall.me.ui.bean.*;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * 功能描述：
 * @author 小斌
 * @date 2019/4/1
 **/
public interface MeApiService {

    /**
     * 描述：上传文件
     * 请求 URL: http://192.168.0.44/mo_bile/index.php?app=sns_album&wwi=file_upload
     * 请求方式：POST
     * 参数
     */
    String UPLOAD_FILES = "/mo_bile/index.php?app=sns_album&wwi=file_upload";

    @Multipart
    @POST(UPLOAD_FILES)
    Flowable<BaseResponse<UploadFilesBean>> uploadFiles(
            @Part("key") String key,
            @Part RequestBody file
    );

    /**
     * 描述：重新发送虚拟产品兑换码短信
     * 请求 URL: https://www.mingpinmall.cn/mo_bile/index.php?app=member_vr_order&wwi=resend
     * 请求方式：POST
     * 参数
     */
    String SEND_VIRTUALCODE = "/mo_bile/index.php?app=member_vr_order&wwi=resend";

    @FormUrlEncoded
    @POST(SEND_VIRTUALCODE)
    Flowable<BaseNothingBean> sendVirtualCode(
            @Field("key") String key,
            @Field("buyer_phone") String buyerPhone,
            @Field("order_id") String orderId
    );

    /**
     * 描述：获取支付信息2
     * 请求 URL: http://192.168.0.44/mo_bile/index.php?app=member_payment&wwi=pay_new
     * 请求方式：POST
     * 参数
     * pay_sn:
     * rcb_pay:
     * pd_pay:
     * payment_code:
     * client: android
     */
    String PAY_INFO2 = "/mo_bile/index.php?app=member_payment&wwi=pay_new";

    @FormUrlEncoded
    @POST(PAY_INFO2)
    Flowable<PayMessageInfo> getPayInfo2(
            @Field("key") String key,
            @Field("pay_sn") String paySn,
            @Field("rcb_pay") String rcb_pay,
            @Field("pd_pay") String pd_pay,
            @Field("password") String password,
            @Field("payment_code") String payment_code,
            @Field("client") String client
    );

    /**
     * 描述：获取支付信息
     * 请求 URL: http://192.168.0.44/mo_bile/index.php?app=member_buy&wwi=pay
     * 请求方式：POST
     * 参数
     * pay_sn:
     */
    String PAY_INFO = "/mo_bile/index.php?app=member_buy&wwi=pay";

    @FormUrlEncoded
    @POST(PAY_INFO)
    Flowable<BaseResponse<PayLayoutBean>> getPayInfo(
            @Field("key") String key,
            @Field("pay_sn") String paySn
    );

    /**
     * 描述：提交商品评价
     * 请求 URL: https://www.mingpinmall.cn/mo_bile/index.php?app=member_evaluate&wwi=save
     * 请求方式：GET
     * 参数
     * order_id
     */
    String SENDEVALUATE = "/mo_bile/index.php?app=member_evaluate&wwi=save";

    @Multipart
    @POST(SENDEVALUATE)
    Flowable<BaseNothingBean> sendEvaluate(
            @Part("jsonData") String json,
            @PartMap() Map<String, RequestBody> files
    );

    /**
     * 描述：提交 商品 退款&退货
     * 请求 URL: http://192.168.0.44/mo_bile/index.php?app=member_refund&wwi=refund_post
     * 请求方式：POST
     * 参数
     * order_id
     * order_goods_id
     */
    String SHOPS_REFUND_UPLOAD = "/mo_bile/index.php?app=member_refund&wwi=refund_post";

    @Multipart
    @POST(SHOPS_REFUND_UPLOAD)
    Flowable<BaseNothingBean> postRefund(
            @PartMap Map<String, RequestBody> images
    );

    /**
     * 描述：提交 订单 退款
     * 请求 URL: https://www.mingpinmall.cn/mo_bile/index.php?app=member_refund&wwi=refund_all_post
     * 请求方式：POST
     * 参数
     * order_id
     * order_goods_id
     */
    String ORDER_REFUND_UPLOAD = "/mo_bile/index.php?app=member_refund&wwi=refund_all_post";

    @Multipart
    @POST(ORDER_REFUND_UPLOAD)
    Flowable<BaseNothingBean> postOrderRefund(
            @PartMap Map<String, RequestBody> images
    );

    /**
     * 描述：获取 商品退款&退货
     * 请求 URL: http://192.168.0.44/mo_bile/index.php?app=member_refund&wwi=refund_form&key=&order_id=&order_goods_id=
     * 请求方式：GET
     * 参数
     * order_id
     * order_goods_id
     */
    String SHOPS_REFUND_INFO = "/mo_bile/index.php?app=member_refund&wwi=refund_form";

    @GET(SHOPS_REFUND_INFO)
    Flowable<BaseResponse<ShopsApplyRefundBean>> getRefundShopsInfo(
            @Query("key") String key,
            @Query("order_id") String order_id,
            @Query("order_goods_id") String order_goods_id
    );

    /**
     * 描述：获取 订单退款
     * 请求 URL: https://www.mingpinmall.cn/mo_bile/index.php?app=member_refund&wwi=refund_all_form&key=&order_id=
     * 请求方式：GET
     * 参数
     * order_id
     */
    String ORDER_REFUND_INFO = "/mo_bile/index.php?app=member_refund&wwi=refund_all_form";

    @GET(ORDER_REFUND_INFO)
    Flowable<BaseResponse<OrderApplyRefundBean>> getRefundOrderInfo(
            @Query("key") String key,
            @Query("order_id") String order_id
    );

    /**
     * 描述：获取该订单下可评价商品列表
     * 请求 URL: https://www.mingpinmall.cn/mo_bile/index.php?app=member_evaluate&wwi=index
     * 请求方式：GET
     * 参数
     * order_id
     */
    String ORDEREVALUATE = "/mo_bile/index.php?app=member_evaluate&wwi=index";

    @GET(ORDEREVALUATE)
    Flowable<BaseResponse<OrderEvaluateBean>> getOrderEvaluate(
            @Query("key") String key,
            @Query("order_id") String order_id
    );

    /**
     * 描述：我的推广码子功能2:提现申请
     * 请求 URL: https://www.mingpinmall.cn/member/index.php?app=predeposit&wwi=pd_cash_add&inajax=1
     * 请求方式：POST
     * 参数
     * <p>
     * type: phone
     * pdc_bank_user: 44
     * pdc_bank_no: 33
     * pdc_bank_name: 22
     * pdc_amount: 11
     * password: 55
     * form_submit: ok
     */
    String PD_CASH_ADD = "/mo_bile/index.php?app=predeposit&wwi=pd_cash_add";

    @FormUrlEncoded
    @POST(PD_CASH_ADD)
    Flowable<BaseNothingBean> addPdCash(
            @Field("key") String key,
            @Field("type") String type,
            @Field("pdc_bank_user") String pdc_bank_user,
            @Field("pdc_bank_no") String pdc_bank_no,
            @Field("pdc_bank_name") String pdc_bank_name,
            @Field("pdc_amount") String pdc_amount,
            @Field("password") String password,
            @Field("form_submit") String form_submit,
            @Field("inajax") int inajax
    );

    /**
     * 描述：我的推广码子功能1:绑定邀请码
     * 请求 URL: https://www.mingpinmall.cn/mo_bile/index.php?app=member_buy&wwi=BindUserCode
     * 请求方式：POST
     * 参数
     * <p>
     * parent_id: 1111
     * type: 1
     */
    String BIND_USER_CODE = "/mo_bile/index.php?app=member_buy&wwi=BindUserCode";

    @FormUrlEncoded
    @POST(BIND_USER_CODE)
    Flowable<BaseNothingBean> bindUserCode(
            @Field("key") String key,
            @Field("parent_id") String parent_id,
            @Field("type") int type
    );

    /**
     * 描述：消息记录
     * 请求 URL: https://www.mingpinmall.cn/mo_bile/index.php?app=member_chat&wwi=get_user_list
     * 请求方式：POST
     * 参数
     * recent: 1
     */
    String MESSAGE = "/mo_bile/index.php?app=member_chat&wwi=get_user_list";

    @FormUrlEncoded
    @POST(MESSAGE)
    Flowable<BaseResponse<MessageListBean>> getMsgList(
            @Field("key") String key,
            @Field("recent") int recent
    );

    /**
     * 描述：我的推广码
     * 请求 URL: https://www.mingpinmall.cn/mo_bile/index.php?app=member_buy&wwi=reduceCash
     * 请求方式：POST
     * 参数
     * recent: 1
     */
    String REDUCECASH = "/mo_bile/index.php?app=member_buy&wwi=reduceCash";

    @FormUrlEncoded
    @POST(REDUCECASH)
    Flowable<BaseResponse<ReduceCashBean>> getReduceCash(
            @Field("key") String key
    );

    /**
     * 描述：删除一条消息记录
     * 请求 URL: https://www.mingpinmall.cn/mo_bile/index.php?app=member_chat&wwi=del_msg
     * 请求方式：POST
     * 参数
     * t_id: 30407
     */
    String DEL_MESSAGE = "/mo_bile/index.php?app=member_chat&wwi=del_msg";

    @FormUrlEncoded
    @POST(DEL_MESSAGE)
    Flowable<BaseNothingBean> delMsg(
            @Field("key") String key,
            @Field("t_id") String t_id
    );

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
     * 描述：实物订单确认收货
     * 请求 URL: http://192.168.0.44/mo_bile/index.php?app=member_order&wwi=order_receive
     * 请求方式：post
     * 请求参数：
     * order_id:订单id
     */
    String RECEIVE_ORDER = "/mo_bile/index.php?app=member_order&wwi=order_receive";

    @POST(RECEIVE_ORDER)
    @FormUrlEncoded
    Flowable<BaseNothingBean> recevieOrder(
            @Field("key") String key,
            @Field("order_id") String order_id
    );

    /**
     * 描述：实物订单确认收货
     * 请求 URL: https://www.mingpinmall.cn/mo_bile/index.php?app=member_order&wwi=order_delete
     * 请求方式：post
     * 请求参数：
     * order_id:订单id
     */
    String REMOVE_ORDER = "/mo_bile/index.php?app=member_order&wwi=order_delete";

    @POST(REMOVE_ORDER)
    @FormUrlEncoded
    Flowable<BaseNothingBean> removeOrder(
            @Field("key") String key,
            @Field("order_id") String order_id
    );

    /**
     * 描述：取消实物订单
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
     * 描述：取消虚拟订单
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
     * 描述：虚拟订单详情 店铺地点
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
    Flowable<NewDatasResponse<RefundInformationBean>> getRefundInformation(
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
    Flowable<NewDatasResponse<ReturnInformationBean>> getReturnInformation(
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
    String FEED_BACK = "mo_bile/index.php?app=member_feedback&wwi=feedback_add";

    @FormUrlEncoded
    @POST(FEED_BACK)
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
    String SHOPS_COLLECT_LIST = "/mo_bile/index.php?app=member_favorites_store&wwi=favorites_list";

    @GET(SHOPS_COLLECT_LIST)
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
     * store_id:产品id
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
     * fav_id:产品id
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
    String PRODUCT_COLLECT_LIST = "/mo_bile/index.php?app=member_favorites&wwi=favorites_list";

    @GET(PRODUCT_COLLECT_LIST)
    Flowable<BaseResponse<ProductCollectionBean>> getProductCollectList(
            @Query("key") String key,
            @Query("curpage") int curpage,
            @Query("page") int page
    );

    /**
     * 描述：获取订单最新物流信息
     * 请求 URL: https://www.mingpinmall.cn/mo_bile/index.php?app=member_order&wwi=get_current_deliver
     * 请求方式：post
     * 请求参数：
     * order_id:订单id
     */
    String ORDERDELIVERINFORMATION = "/mo_bile/index.php?app=member_order&wwi=get_current_deliver";

    @FormUrlEncoded
    @POST(ORDERDELIVERINFORMATION)
    Flowable<BaseResponse<OrderDeliverBean>> getOrderDeliverInformation(
            @Field("key") String key,
            @Field("order_id") String order_id
    );

    /**
     * 描述：查看订单物流详情
     * 请求 URL: https://www.mingpinmall.cn/mo_bile/index.php?app=member_order&wwi=search_deliver
     * 请求方式：get
     * 请求参数：
     * order_id:订单id
     */
    String ORDERDELIVERYLIST = "/mo_bile/index.php?app=member_order&wwi=search_deliver";

    @FormUrlEncoded
    @POST(ORDERDELIVERYLIST)
    Flowable<BaseResponse<OrderDeliverListBean>> getOrderDeliverList(
            @Field("key") String key,
            @Field("order_id") String order_id
    );

    /**
     * 描述：查看订单详情
     * 请求地址：https://www.mingpinmall.cn/mo_bile/index.php?app=member_order&wwi=order_info&order_id=71
     * 请求方式：get
     * 请求参数：
     * order_id:订单id
     */
    String ORDER_INFORMATION = "/mo_bile/index.php?app=member_order&wwi=order_info";

    @GET(ORDER_INFORMATION)
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
    String GET_PHYSICAL_ORDER = "/mo_bile/index.php?app=member_order&wwi=order_list";

    @FormUrlEncoded
    @POST(GET_PHYSICAL_ORDER)
    Flowable<NewDatasResponse<PhysicalOrderBean>> getPhysicalOrderList(
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
    String GET_VIRTUAL_ORDER = "/mo_bile/index.php?app=member_vr_order&wwi=order_list";

    @FormUrlEncoded
    @POST(GET_VIRTUAL_ORDER)
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
    String MY_FOOTPRINT = "/mo_bile/index.php?app=member_goodsbrowse&wwi=browse_list";

    @GET(MY_FOOTPRINT)
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
    String CLEARN_FOOTPRINT = "/mo_bile/index.php?app=member_goodsbrowse&wwi=browse_clearall";

    @GET(CLEARN_FOOTPRINT)
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
    String MYASSET = "/mo_bile/index.php?app=member_index&wwi=my_asset";

    @GET(MYASSET)
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
    String GET_CITY = "/mo_bile/index.php?app=area&wwi=area_list";

    @GET(GET_CITY)
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
    String ADDRESS_LIST = "/mo_bile/index.php?app=member_address&wwi=address_list";

    @GET(ADDRESS_LIST)
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
    String GET_ADDRESS = "/mo_bile/index.php?app=member_address&wwi=address_info";

    @FormUrlEncoded
    @POST(GET_ADDRESS)
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
    String DEL_ADDRESS = "/mo_bile/index.php?app=member_address&wwi=address_del";

    @FormUrlEncoded
    @POST(DEL_ADDRESS)
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
    String ADD_ADDRESS = "/mo_bile/index.php?app=member_address&wwi=address_add";

    @FormUrlEncoded
    @POST(ADD_ADDRESS)
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
    String EDIT_ADDRESS = "/mo_bile/index.php?app=member_address&wwi=address_edit";

    @FormUrlEncoded
    @POST(EDIT_ADDRESS)
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
