package com.mingpinmall.me.ui.api;

import com.goldze.common.dmvvm.base.bean.AddressDataBean;
import com.goldze.common.dmvvm.base.bean.BaseNothingBean;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.bean.NewDatasResponse;
import com.goldze.common.dmvvm.base.mvvm.base.BaseRepository;
import com.goldze.common.dmvvm.base.mvvm.stateview.StateConstants;
import com.goldze.common.dmvvm.http.RetrofitClient;
import com.goldze.common.dmvvm.http.rx.RxSchedulers;
import com.goldze.common.dmvvm.http.rx.RxSubscriber;
import com.goldze.common.dmvvm.utils.log.QLog;
import com.mingpinmall.me.ui.bean.BaseCheckBean;
import com.mingpinmall.me.ui.bean.BaseIntDatasBean;
import com.mingpinmall.me.ui.bean.CityBean;
import com.mingpinmall.me.ui.bean.CouponListBean;
import com.mingpinmall.me.ui.bean.FootprintBean;
import com.mingpinmall.me.ui.bean.MerchandiseBean;
import com.mingpinmall.me.ui.bean.MessageListBean;
import com.mingpinmall.me.ui.bean.MyInfoBean;
import com.mingpinmall.me.ui.bean.OrderApplyRefundBean;
import com.mingpinmall.me.ui.bean.OrderDeliverBean;
import com.mingpinmall.me.ui.bean.OrderDeliverListBean;
import com.mingpinmall.me.ui.bean.OrderEvaluateBean;
import com.mingpinmall.me.ui.bean.OrderInformationBean;
import com.mingpinmall.me.ui.bean.PacketListBean;
import com.mingpinmall.apppay.pay.PayLayoutBean;
import com.mingpinmall.me.ui.bean.PayMessageInfo;
import com.mingpinmall.me.ui.bean.PdcashBean;
import com.mingpinmall.me.ui.bean.PdcashInfoBean;
import com.mingpinmall.me.ui.bean.PdrechargeBean;
import com.mingpinmall.me.ui.bean.PhysicalOrderBean;
import com.mingpinmall.me.ui.bean.Predepoit;
import com.mingpinmall.me.ui.bean.PredepoitLogBean;
import com.mingpinmall.me.ui.bean.ProductCollectionBean;
import com.mingpinmall.me.ui.bean.PropertyBean;
import com.mingpinmall.me.ui.bean.RCardBalanceBean;
import com.mingpinmall.me.ui.bean.RCardLogBean;
import com.mingpinmall.me.ui.bean.ReduceCashBean;
import com.mingpinmall.me.ui.bean.RefundBean;
import com.mingpinmall.me.ui.bean.RefundInformationBean;
import com.mingpinmall.me.ui.bean.ReturnBean;
import com.mingpinmall.me.ui.bean.ReturnInformationBean;
import com.mingpinmall.me.ui.bean.ShopsApplyRefundBean;
import com.mingpinmall.me.ui.bean.ShopsCollectionBean;
import com.mingpinmall.me.ui.bean.UploadFilesBean;
import com.mingpinmall.me.ui.bean.VipPointBean;
import com.mingpinmall.me.ui.bean.VipPointListBean;
import com.mingpinmall.me.ui.bean.VirtualInformationBean;
import com.mingpinmall.me.ui.bean.VirtualOrderBean;
import com.mingpinmall.me.ui.bean.VirtualStoreAddrsBean;
import com.mingpinmall.me.ui.constants.Constants;


import java.util.Map;

import okhttp3.RequestBody;

import static com.goldze.common.dmvvm.constants.ARouterConfig.SUCCESS;

/**
 * 功能描述：
 *
 * @author 小斌
 * @date 2019/4/1
 **/
public class MeRepository extends BaseRepository {

    private MeApiService apiService = RetrofitClient.getInstance().create(MeApiService.class);

    /*上传文件*/
    protected void uploadFiles(RequestBody file) {
        addDisposable(apiService.uploadFiles(getUserKey(), file)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<UploadFilesBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<UploadFilesBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.UPLOAD_FILES, result.getData());
                        } else {
                            sendData(Constants.UPLOAD_FILES, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.UPLOAD_FILES, msg == null ? "上传失败" : msg);
                    }
                })
        );
    }

    /*重新发送虚拟产品兑换码短信*/
    protected void sendVirtualCode(String buyerPhone, String orderId) {
        addDisposable(apiService.sendVirtualCode(getUserKey(), buyerPhone, orderId)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {
                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData(Constants.SEND_VIRTUALCODE, "success");
                        } else {
                            sendData(Constants.SEND_VIRTUALCODE, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.SEND_VIRTUALCODE, msg == null ? "上传失败" : msg);
                    }
                })
        );
    }

    /*获取支付信息*/
    protected void getPayInfo(String paySn, Object eventKey, String tag) {
        addDisposable(apiService.getPayInfo(getUserKey(), paySn)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<PayLayoutBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<PayLayoutBean> result) {
                        if (result.isSuccess()) {
                            sendData(eventKey, tag, result.getData());
                        } else {
                            sendData(eventKey, tag, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(eventKey, tag, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*获取支付信息*/
    protected void getPayInfo2(String paySn, String rcb_pay, String pd_pay, String password, String payment_code, Object eventKey, String tag) {
        addDisposable(apiService.getPayInfo2(getUserKey(), paySn, rcb_pay, pd_pay, password, payment_code, "android")
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<PayMessageInfo>() {
                    @Override
                    public void onSuccess(PayMessageInfo result) {
                        if (result.isSuccess()) {
                            sendData(eventKey, tag, result);
                        } else {
                            sendData(eventKey, tag, result.getErr_code_des());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(eventKey, tag, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*提交 订单 退款*/
    protected void postOrderRefund(Map<String, RequestBody> params) {
        addDisposable(apiService.postOrderRefund(params)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {
                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData(Constants.POST_REFUND_ALL, SUCCESS);
                        } else {
                            sendData(Constants.POST_REFUND_ALL, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.POST_REFUND_ALL, msg == null ? "提交失败" : msg);
                    }
                })
        );
    }

    /*提交 商品 退款    &退货*/
    protected void postRefund(Map<String, RequestBody> params) {
        addDisposable(apiService.postRefund(params)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {
                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData(Constants.POST_REFUND, SUCCESS);
                        } else {
                            sendData(Constants.POST_REFUND, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.POST_REFUND, msg == null ? "提交失败" : msg);
                    }
                })
        );
    }

    /*获取 商品退款&退货*/
    protected void getRefundShopsInfo(String order_id, String order_goods_id) {
        addDisposable(apiService.getRefundShopsInfo(getUserKey(), order_id, order_goods_id)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<ShopsApplyRefundBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<ShopsApplyRefundBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.REFUND_SHOPS_INFO, result.getData());
                        } else {
                            sendData(Constants.REFUND_SHOPS_INFO, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.REFUND_SHOPS_INFO, msg == null ? "评价失败" : msg);
                    }
                })
        );
    }

    /*获取 订单退款*/
    protected void getRefundOrderInfo(String order_id) {
        addDisposable(apiService.getRefundOrderInfo(getUserKey(), order_id)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<OrderApplyRefundBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<OrderApplyRefundBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.REFUND_ORDER_INFO, result.getData());
                        } else {
                            sendData(Constants.REFUND_ORDER_INFO, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.REFUND_ORDER_INFO, msg == null ? "评价失败" : msg);
                    }
                })
        );
    }

    /*获取该订单下可评价商品列表*/
    protected void sendEvaluate(String json, Map<String, RequestBody> files) {
        addDisposable(apiService.sendEvaluate(json, getUserKey(), files)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {
                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData(Constants.SEND_EVALUATE, SUCCESS);
                        } else {
                            sendData(Constants.SEND_EVALUATE, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.SEND_EVALUATE, msg == null ? "评价失败" : msg);
                    }
                })
        );
    }

    /*获取该订单下可评价商品列表*/
    protected void getOrderEvaluate(String order_id) {
        addDisposable(apiService.getOrderEvaluate(getUserKey(), order_id)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<OrderEvaluateBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<OrderEvaluateBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.ORDER_EVALUATE_LIST, result.getData());
                        } else {
                            sendData(Constants.SEND_EVALUATE, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.SEND_EVALUATE, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*我的推广码子功能2:提现申请*/
    protected void addPdCash(String pdc_bank_user, String pdc_bank_no,
                             String pdc_bank_name, String pdc_amount, String password) {
        addDisposable(apiService.addPdCash(getUserKey(), "phone", pdc_bank_user, pdc_bank_no,
                pdc_bank_name, pdc_amount, password, "ok", 1)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {
                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData(Constants.ADD_PDCASH, "您的提现申请已成功提交，请等待系统处理");
                        } else {
                            sendData(Constants.ADD_PDCASH, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.ADD_PDCASH, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*我的推广码子功能1:绑定邀请码*/
    protected void bindUserCode(String parent_id) {
        addDisposable(apiService.bindUserCode(getUserKey(), parent_id, 1)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {
                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData(Constants.BIND_USER_CODE, "绑定推荐码成功");
                        } else {
                            sendData(Constants.BIND_USER_CODE, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.BIND_USER_CODE, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*我的推广码*/
    protected void getReduceCash() {
        addDisposable(apiService.getReduceCash(getUserKey())
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<ReduceCashBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<ReduceCashBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.REDUCE_CASH, result.getData());
                        } else {
                            sendData(Constants.REDUCE_CASH, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.REDUCE_CASH, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*消息列表*/
    protected void getMsgList() {
        addDisposable(apiService.getMsgList(getUserKey(), 1)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<MessageListBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<MessageListBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.MESSAGE_LIST, result.getData());
                        } else {
                            sendData(Constants.MESSAGE_LIST, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.MESSAGE_LIST, msg == null ? "获取失败" : msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        sendData(Constants.MESSAGE_LIST, "");
                    }
                })
        );
    }

    /*消息列表*/
    protected void delMsg(String id) {
        addDisposable(apiService.delMsg(getUserKey(), id)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {
                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData(Constants.DELETE_MESSAGE, SUCCESS);
                        } else {
                            sendData(Constants.DELETE_MESSAGE, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.DELETE_MESSAGE, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*退货详情*/
    protected void getReturnInformation(String returnId) {
        addDisposable(apiService.getReturnInformation(getUserKey(), returnId)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<NewDatasResponse<ReturnInformationBean>>() {

                    @Override
                    public void onSuccess(NewDatasResponse<ReturnInformationBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.RETURN_INFORMATION, result.getData());
                        } else {
                            sendData(Constants.RETURN_INFORMATION, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.RETURN_INFORMATION, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*退款详情*/
    protected void getRefundInformation(String refundId) {
        addDisposable(apiService.getRefundInformation(getUserKey(), refundId)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<NewDatasResponse<RefundInformationBean>>() {

                    @Override
                    public void onSuccess(NewDatasResponse<RefundInformationBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.REFUND_INFORMATION, result.getData());
                        } else {
                            sendData(Constants.REFUND_INFORMATION, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.REFUND_INFORMATION, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*会员积分*/
    protected void getVipPoint() {
        addDisposable(apiService.getVipPoint(getUserKey())
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<VipPointBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<VipPointBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.VIP_POINT, result.getData());
                        } else {
                            sendData(Constants.VIP_POINT, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.VIP_POINT, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*会员积分历史记录*/
    protected void getVipPointLog(int curPage) {
        addDisposable(apiService.getVipPointLog(getUserKey(), 15, curPage)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<VipPointListBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<VipPointListBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.VIP_POINT_LOG, result);
                        } else {
                            sendData(Constants.VIP_POINT_LOG, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.VIP_POINT_LOG, msg == null ? "获取失败" : msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        sendData(Constants.VIP_POINT_LOG, "没有网络");
                    }
                })
        );
    }

    /*获取我的红包记录*/
    protected void getPacketList(int curPage) {
        addDisposable(apiService.getPacketList(getUserKey(), 10, curPage)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<PacketListBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<PacketListBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.PACKET_LIST, result);
                        } else {
                            sendData(Constants.PACKET_LIST, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.PACKET_LIST, msg == null ? "获取失败" : msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        sendData(Constants.PACKET_LIST, "没有网络");
                    }
                })
        );
    }

    /*代金券确认领取*/
    protected void cpCharge(String rc_sn) {
        addDisposable(apiService.cpCharge(getUserKey(), rc_sn, "android")
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {

                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData(Constants.COUPON_CHARGE, SUCCESS);
                        } else {
                            sendData(Constants.COUPON_CHARGE, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.COUPON_CHARGE, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*充值卡确认充值*/
    protected void rcCharge(String rc_sn) {
        addDisposable(apiService.rcCharge(getUserKey(), rc_sn, "android")
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {
                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData(Constants.RCARD_CHARGE, SUCCESS);
                        } else {
                            sendData(Constants.RCARD_CHARGE, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.RCARD_CHARGE, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*确认领取红包*/
    protected void packetCharge(String pwd_code) {
        addDisposable(apiService.packetCharge(getUserKey(), pwd_code, "android")
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {
                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData(Constants.PACKET_CHARGE, SUCCESS);
                        } else {
                            sendData(Constants.PACKET_CHARGE, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.PACKET_CHARGE, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*充值卡充值记录*/
    protected void getRCBLog(int curPage) {
        addDisposable(apiService.getRCBLog(getUserKey(), 10, curPage)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<RCardLogBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<RCardLogBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.RCCHARGE_LIST, result);
                        } else {
                            sendData(Constants.RCCHARGE_LIST, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.RCCHARGE_LIST, msg == null ? "获取失败" : msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        sendData(Constants.RCCHARGE_LIST, "没有网络");
                    }
                })
        );
    }

    /*获取充值卡余额*/
    protected void getRCBalance() {
        addDisposable(apiService.getRCBalance(getUserKey(), "available_rc_balance")
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<RCardBalanceBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<RCardBalanceBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.RCBALANCE, result.getData());
                        } else {
                            sendData(Constants.RCBALANCE, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.RCBALANCE, msg == null ? "获取失败" : msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        sendData(Constants.RCBALANCE, "没有网络");
                    }
                })
        );
    }

    /*代金券列表*/
    protected void getCouponList(int curPage) {
        addDisposable(apiService.getCouponList(getUserKey(), 10, curPage)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<CouponListBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<CouponListBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.COUPONLISTBEAN, result);
                        } else {
                            sendData(Constants.COUPONLISTBEAN, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.COUPONLISTBEAN, msg == null ? "获取失败" : msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        sendData(Constants.COUPONLISTBEAN, "没有网络");
                    }
                })
        );
    }

    /*获取账户余额*/
    protected void getPredepoit() {
        addDisposable(apiService.getPredepoit(getUserKey(), "predepoit")
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<Predepoit>>() {
                    @Override
                    public void onSuccess(BaseResponse<Predepoit> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.PREDEPOIT, result.getData());
                        } else {
                            sendData(Constants.PREDEPOIT, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.PREDEPOIT, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*获取账户余额列表*/
    protected void getPredepoitLog(int curPage) {
        addDisposable(apiService.getPredepoitLog(getUserKey(), 10, curPage)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<PredepoitLogBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<PredepoitLogBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.PREDPOSITLOG, result);
                        } else {
                            sendData(Constants.PREDPOSITLOG, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.PREDPOSITLOG, msg == null ? "获取失败" : msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        sendData(Constants.PREDPOSITLOG, "没有网络");
                    }
                })
        );
    }

    /*获取账户充值明细*/
    protected void getPdreChargeList(int curPage) {
        addDisposable(apiService.getPdreChargeList(getUserKey(), 10, curPage)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<PdrechargeBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<PdrechargeBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.PDRECHARGE, result);
                        } else {
                            sendData(Constants.PDRECHARGE, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.PDRECHARGE, msg == null ? "获取失败" : msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        sendData(Constants.PDRECHARGE, "没有网络");
                    }
                })
        );
    }

    /*获取账户余额提现列表*/
    protected void getPdcashList(int curPage) {
        addDisposable(apiService.getPdcashList(getUserKey(), 10, curPage)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<PdcashBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<PdcashBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.PDCASH, result);
                        } else {
                            sendData(Constants.PDCASH, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.PDCASH, msg == null ? "获取失败" : msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        sendData(Constants.PDCASH, "没有网络");
                    }
                })
        );
    }

    /*获取账户提现详情*/
    protected void getPdcashList(String pdcId) {
        addDisposable(apiService.getPdCashInfo(getUserKey(), pdcId)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<PdcashInfoBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<PdcashInfoBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.PDCASH_INFORMATION, result.getData());
                        } else {
                            sendData(Constants.PDCASH_INFORMATION, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.PDCASH_INFORMATION, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*获取退款列表*/
    protected void getRefundList(int curPage) {
        addDisposable(apiService.getRefundList(getUserKey(), 10, curPage)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<RefundBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<RefundBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.REFUND, result);
                        } else {
                            sendData(Constants.REFUND, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.REFUND, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*获取退货列表*/
    protected void getReturnList(int curPage) {
        addDisposable(apiService.getRetrunList(getUserKey(), 10, curPage)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<ReturnBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<ReturnBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.RETURN, result);
                        } else {
                            sendData(Constants.RETURN, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.RETURN, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*获取虚拟订单详细内容*/
    protected void getVitrualOrderInformation(String orderId) {
        addDisposable(apiService.getVitrualOrderInformation(getUserKey(), orderId)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<VirtualInformationBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<VirtualInformationBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.VIRTUAL_ORDER_INFORMATION, result.getData());
                        } else {
                            sendData(Constants.VIRTUAL_ORDER_INFORMATION, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        QLog.i(msg);
                        sendData(Constants.VIRTUAL_ORDER_INFORMATION, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*获取虚拟订单详细内容 中的店铺地址*/
    protected void getVitrualOrderStoreAddrs(String orderId) {
        addDisposable(apiService.getVitrualOrderStoreAddrs(getUserKey(), orderId)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<VirtualStoreAddrsBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<VirtualStoreAddrsBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.VIRTUAL_ORDER_ADDRS, result.getData());
                        } else {
                            sendData(Constants.VIRTUAL_ORDER_ADDRS, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        QLog.i(msg);
                        sendData(Constants.VIRTUAL_ORDER_ADDRS, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*确认收货*/
    protected void recevieOrder(final String eventKey, String orderId) {
        addDisposable(apiService.recevieOrder(getUserKey(), orderId)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {

                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData(eventKey, Constants.RECEVIE_ORDER, SUCCESS);
                        } else {
                            sendData(eventKey, Constants.RECEVIE_ORDER, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        QLog.i(msg);
                        sendData(eventKey, Constants.RECEVIE_ORDER, msg == null ? "确认收货失败" : msg);
                    }
                })
        );
    }

    /*删除订单*/
    protected void removeOrder(final String eventKey, String orderId) {
        addDisposable(apiService.removeOrder(getUserKey(), orderId)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {

                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData(eventKey, Constants.REMOVE_ORDER, SUCCESS);
                        } else {
                            sendData(eventKey, Constants.REMOVE_ORDER, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        QLog.i(msg);
                        sendData(eventKey, Constants.REMOVE_ORDER, msg == null ? "删除订单失败" : msg);
                    }
                })
        );
    }

    /*取消订单*/
    protected void cancelOrder(final String eventKey, String orderId) {
        addDisposable(apiService.cancelOrder(getUserKey(), orderId)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {

                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData(eventKey, Constants.CANCEL_ORDER, SUCCESS);
                        } else {
                            sendData(eventKey, Constants.CANCEL_ORDER, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        QLog.i(msg);
                        sendData(eventKey, Constants.CANCEL_ORDER, msg == null ? "取消订单失败" : msg);
                    }
                })
        );
    }

    /*取消虚拟订单*/
    protected void cancelVirtualOrder(final String eventKey, String orderId) {
        addDisposable(apiService.cancelVirtualOrder(getUserKey(), orderId)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {

                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData(eventKey, Constants.CANCEL_ORDER, SUCCESS);
                        } else {
                            sendData(eventKey, Constants.CANCEL_ORDER, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        QLog.i(msg);
                        sendData(eventKey, Constants.CANCEL_ORDER, msg == null ? "取消订单失败" : msg);
                    }
                })
        );
    }

    /*分销管理*/
    protected void getInviteList(String wwi, int curpage) {
        addDisposable(apiService.getInviteList(getUserKey(), wwi, 10, curpage)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {

                    @Override
                    public void onSuccess(BaseNothingBean baseNothingBeanBaseResponse) {

                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                })
        );
    }

    /*获取收货地址详细内容*/
    protected void getAddress(String addressId) {
        addDisposable(apiService.getAddress(getUserKey(), addressId)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<AddressDataBean.AddressListBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<AddressDataBean.AddressListBean> addressListBeanBaseResponse) {

                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                })
        );
    }

    /*删除收货地址*/
    protected void delAddress(String addressId) {
        addDisposable(apiService.delAddress(getUserKey(), addressId)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {

                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.getCode() == 200) {
                            sendData(Constants.DEL_ADDRESS, SUCCESS);
                        } else {
                            sendData(Constants.DEL_ADDRESS, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        QLog.i(msg);
                        sendData(Constants.DEL_ADDRESS, msg == null ? "删除失败" : msg);
                    }
                })

        );
    }

    /*获取收货地址列表*/
    protected void getAddressList() {
        addDisposable(apiService.getAddressList(getUserKey())
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<AddressDataBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<AddressDataBean> response) {
                        if (response.isSuccess()) {
                            sendData(Constants.ADDRESS_LIST, response.getData());
                        } else {
                            sendData(Constants.ADDRESS_LIST, response.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        QLog.i(msg);
                        sendData(Constants.ADDRESS_LIST, msg == null ? "获取失败" : msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        sendData(Constants.ADDRESS_LIST, "没有网络");
                    }
                })
        );
    }

    /*新增收货地址*/
    protected void addAddress(int id_default, String name, String city_id, String area_id, String area_info,
                              String address, String phone) {
        addDisposable(apiService.addAddress(getUserKey(), id_default, name, city_id, area_id, area_info, address, phone)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {

                    @Override
                    public void onSuccess(BaseNothingBean baseNothingBean) {
                        if (baseNothingBean.getCode() == 200) {
                            sendData(Constants.EDIT_ADDRESS, SUCCESS);
                        } else {
                            sendData(Constants.EDIT_ADDRESS, baseNothingBean.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        QLog.i(msg);
                        sendData(Constants.EDIT_ADDRESS, msg == null ? "保存失败" : msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }
                })
        );
    }

    /*编辑收货地址*/
    protected void editAddress(String address_id, int id_default, String name, String city_id, String area_id, String area_info,
                               String address, String phone) {
        addDisposable(apiService.editAddress(getUserKey(), address_id, id_default, name, city_id, area_id, area_info, address, phone)
                        .compose(RxSchedulers.io_main())
                        .subscribeWith(new RxSubscriber<BaseNothingBean>() {

                            @Override
                            public void onSuccess(BaseNothingBean baseNothingBean) {
                                if (baseNothingBean.getCode() == 200) {
                                    sendData(Constants.EDIT_ADDRESS, SUCCESS);
                                } else {
                                    sendData(Constants.EDIT_ADDRESS, baseNothingBean.getMessage());
                                }
                            }

                            @Override
                            public void onFailure(String msg) {
                                QLog.i(msg);
                                sendData(Constants.EDIT_ADDRESS, msg == null ? "保存失败" : msg);
                            }

//                    @Override
//                    protected void onNoNetWork() {
//
//                    }
                        })
        );
    }

    /*获取城市列表*/
    protected void getCityList(String areaId) {
        addDisposable(apiService.getCityList(getUserKey(), areaId)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<CityBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<CityBean> response) {
                        if (response.isSuccess()) {
                            sendData(Constants.CITY_LIST, response.getData());
                        } else {
                            sendData(Constants.CITY_LIST, response.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        QLog.i(msg);
                        sendData(Constants.CITY_LIST, msg == null ? "获取失败" : msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        showPageState("SelectCityActivity", StateConstants.NET_WORK_STATE);
                    }
                })
        );
    }

    /*我的商城页面 清空我的足迹*/
    protected void clearnMyFootprint() {
        addDisposable(apiService.clearnFootprint(getUserKey(), "android")
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {
                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData(Constants.CLEAR_FOOTPRINT, SUCCESS);
                        } else {
                            sendData(Constants.CLEAR_FOOTPRINT, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        QLog.i(msg);
                        sendData(Constants.CLEAR_FOOTPRINT, msg == null ? "清空失败" : msg);
                    }
                })
        );
    }

    /*我的商城页面 获取我的足迹*/
    protected void getMyFootprint(int curPage) {
        addDisposable(apiService.getFootprint(getUserKey(), 10, curPage)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<FootprintBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<FootprintBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.GET_FOOTPRINT, result);
                        } else {
                            sendData(Constants.GET_FOOTPRINT, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        QLog.i(msg);
                        sendData(Constants.GET_FOOTPRINT, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }


    /**
     * 我的商城页面 获取用户信息
     **/
    protected void getUserInfo() {
        addDisposable(apiService.getUserInfo(getUserKey())
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<MyInfoBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<MyInfoBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.GET_USER_INFO, result.getData());
                        } else {
                            sendData(Constants.GET_USER_INFO, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        QLog.i(msg);
                        sendData(Constants.GET_USER_INFO, msg == null ? "获取失败" : msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        sendData(Constants.GET_USER_INFO, "没有网络");
                    }
                })
        );
    }

    /*获取支付密码信息*/
    public void getPayPwdInfo() {
        addDisposable(apiService.getPayPwdInfo(getUserKey())
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<BaseCheckBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<BaseCheckBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.USER_PAYPWD_INFO, result.getData());
                        } else {
                            sendData(Constants.GET_USER_INFO, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        QLog.i(msg);
                        sendData(Constants.GET_USER_INFO, msg);
                    }
                })
        );
    }

    /*获取绑定手机状态*/
    public void getPhoneInfo() {
        addDisposable(apiService.getPhoneInfo(getUserKey())
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<BaseCheckBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<BaseCheckBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.USER_PHONE_INFO, result.getData());
                        } else {
                            sendData(Constants.ERR_USER_INFO, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.GET_USER_INFO, msg);
                    }
                })
        );
    }

    /*获取我的财产*/
    public void getProperty() {
        addDisposable(apiService.getMyAsset(getUserKey())
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<PropertyBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<PropertyBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.MY_ASSET, result.getData());
                        } else {
                            sendData(Constants.MY_ASSET, result.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(String msg) {
                        QLog.i(msg);
                        sendData(Constants.MY_ASSET, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*发送用户反馈*/
    protected void sendFeedBack(String feedBack) {
        addDisposable(apiService.sendFeedBack(getUserKey(), feedBack)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<BaseIntDatasBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<BaseIntDatasBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.SEND_FEEDBACK, SUCCESS);
                        } else {
                            sendData(Constants.SEND_FEEDBACK, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.SEND_FEEDBACK, msg == null ? "反馈失败" : msg);
                    }

                })
        );
    }

    /*获取店铺收藏列表*/
    protected void getShopsCollectList(int curpage) {
        addDisposable(apiService.getShopsCollectList(getUserKey(), curpage, 10)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<ShopsCollectionBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<ShopsCollectionBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.SHOPS_COLLECT_LIST, result);
                        } else {
                            sendData(Constants.SHOPS_COLLECT_LIST, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        QLog.i(msg);
                        sendData(Constants.SHOPS_COLLECT_LIST, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*店铺删除收藏动作*/
    protected void deleShopsCollect(String storeId) {
        addDisposable(apiService.deleShopsCollect(getUserKey(), "android", storeId)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {
                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData(Constants.DEL_SHOP_COLLECT, SUCCESS);
                        } else {
                            sendData(Constants.DEL_SHOP_COLLECT, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        QLog.i(msg);
                        sendData(Constants.DEL_SHOP_COLLECT, msg == null ? "删除失败" : msg);
                    }
                })
        );
    }

    /*商品删除收藏动作*/
    protected void deleGoodsCollect(String favId) {
        addDisposable(apiService.deleGoodsCollect(getUserKey(), "android", favId)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {
                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData(Constants.DEL_GOODS_COLLECT, SUCCESS);
                        } else {
                            sendData(Constants.DEL_GOODS_COLLECT, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        QLog.i(msg);
                        sendData(Constants.DEL_GOODS_COLLECT, msg == null ? "删除失败" : msg);
                    }
                })
        );
    }

    /*获取商品收藏列表*/
    protected void getProductCollectList(int curpage) {
        addDisposable(apiService.getProductCollectList(getUserKey(), curpage, 10)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<ProductCollectionBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<ProductCollectionBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.PRODUCT_COLLECT_LIST, result);
                        } else {
                            sendData(Constants.PRODUCT_COLLECT_LIST, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        QLog.i(msg);
                        sendData(Constants.PRODUCT_COLLECT_LIST, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*获取实物订单列表（包括搜索）*/
    protected void getPhysicalOrderList(final String event_key, String state_type, String order_key, int page, final int curpage) {
        addDisposable(apiService.getPhysicalOrderList(getUserKey(), state_type, order_key, page, curpage)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<NewDatasResponse<PhysicalOrderBean>>() {

                    @Override
                    public void onSuccess(NewDatasResponse<PhysicalOrderBean> result) {
                        if (result.isSuccess()) {
                            sendData(event_key, result);
                        } else {
                            sendData(event_key, result.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(event_key, msg == null ? "获取失败" : msg);
                    }
                })
        );

    }

    /*获取实物订单详情*/
    protected void getOrderInformation(String order_id) {
        addDisposable(apiService.getOrderInformation(getUserKey(), order_id)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<OrderInformationBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<OrderInformationBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.PHYSICAL_ORDER_INFORMATION, result.getData());
                        } else {
                            sendData(Constants.PHYSICAL_ORDER_INFORMATION, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.PHYSICAL_ORDER_INFORMATION, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*获取实物订单最新一条物流信息*/
    protected void getOrderDeliverInformation(String order_id) {
        addDisposable(apiService.getOrderDeliverInformation(getUserKey(), order_id)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<OrderDeliverBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<OrderDeliverBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.ORDER_DELIVER_INFORMATION, result.getData());
                        } else {
                            sendData(Constants.ORDER_DELIVER_INFORMATION, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.ORDER_DELIVER_INFORMATION, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*获取实物订单物流详情*/
    protected void getOrderDeliverList(String order_id) {
        addDisposable(apiService.getOrderDeliverList(getUserKey(), order_id)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<OrderDeliverListBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<OrderDeliverListBean> result) {
                        if (result.isSuccess()) {
                            sendData(Constants.ORDER_DELIVER_LIST, result.getData());
                        } else {
                            sendData(Constants.ORDER_DELIVER_LIST, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        QLog.i(msg);
                        sendData(Constants.ORDER_DELIVER_LIST, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*获取虚拟订单列表（包括搜索）*/
    protected void getVirtualOrderList(final String event_key, String state_type, String order_key, int page, final int curpage) {
        addDisposable(apiService.getVirtualOrderList(getUserKey(), state_type, order_key, page, curpage)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<VirtualOrderBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<VirtualOrderBean> result) {
                        if (!result.isSuccess()) {
                            sendData(event_key, result.getMessage());
                            return;
                        }
                        sendData(event_key, result);
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(event_key, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*退货发货-页面信息*/
    public void getMemberReturn(String returnId) {
        addDisposable(apiService.getMemberReturn(getUserKey(), returnId)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<MerchandiseBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<MerchandiseBean> result) {
                        if (!result.isSuccess()) {
                            sendData("MERCHANDISEBEAN_EVENT_KEY", result.getMessage());
                            return;
                        }
                        sendData("MERCHANDISEBEAN_EVENT_KEY", result.getData());
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("MERCHANDISEBEAN_EVENT_KEY", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*退货发货-确认发货*/
    public void getMemberReturn(String returnId, String expressId, String invoiceNo) {
        addDisposable(apiService.getMemberReturn(getUserKey(), returnId, expressId, invoiceNo)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse>() {

                    @Override
                    public void onSuccess(BaseResponse result) {
                        if (!result.isSuccess()) {
                            sendData("MERCHANDISEBEAN_EVENT_KEY", result.getMessage());
                            return;
                        }
                        sendData("MERCHANDISEBEAN_EVENT_KEY", "成功");
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("MERCHANDISEBEAN_EVENT_KEY", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

}
