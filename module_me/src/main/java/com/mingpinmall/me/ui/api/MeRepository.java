package com.mingpinmall.me.ui.api;

import com.goldze.common.dmvvm.base.bean.AddressDataBean;
import com.goldze.common.dmvvm.base.bean.BaseNothingBean;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.base.BaseRepository;
import com.goldze.common.dmvvm.base.mvvm.stateview.StateConstants;
import com.goldze.common.dmvvm.http.RetrofitClient;
import com.goldze.common.dmvvm.http.rx.RxSchedulers;
import com.goldze.common.dmvvm.http.rx.RxSubscriber;
import com.mingpinmall.me.ui.bean.BaseCheckBean;
import com.mingpinmall.me.ui.bean.BaseIntDatasBean;
import com.mingpinmall.me.ui.bean.CityBean;
import com.mingpinmall.me.ui.bean.CouponListBean;
import com.mingpinmall.me.ui.bean.FootprintBean;
import com.mingpinmall.me.ui.bean.MessageListBean;
import com.mingpinmall.me.ui.bean.MyInfoBean;
import com.mingpinmall.me.ui.bean.OrderDeliverBean;
import com.mingpinmall.me.ui.bean.OrderDeliverListBean;
import com.mingpinmall.me.ui.bean.OrderEvaluateBean;
import com.mingpinmall.me.ui.bean.OrderInformationBean;
import com.mingpinmall.me.ui.bean.PacketListBean;
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
import com.mingpinmall.me.ui.bean.RefundInformation;
import com.mingpinmall.me.ui.bean.ReturnBean;
import com.mingpinmall.me.ui.bean.ReturnInformation;
import com.mingpinmall.me.ui.bean.ShopsCollectionBean;
import com.mingpinmall.me.ui.bean.VipPointBean;
import com.mingpinmall.me.ui.bean.VipPointListBean;
import com.mingpinmall.me.ui.bean.VirtualInformationBean;
import com.mingpinmall.me.ui.bean.VirtualOrderBean;
import com.mingpinmall.me.ui.bean.VirtualStoreAddrsBean;
import com.socks.library.KLog;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/1
 **/
public class MeRepository extends BaseRepository {

    private MeApiService apiService = RetrofitClient.getInstance().create(MeApiService.class);

    /*获取该订单下可评价商品列表*/
    protected void sendEvaluate(String json, Map<String, RequestBody> files) {
        addDisposable(apiService.sendEvaluate(json, files)
                .compose(RxSchedulers.<BaseResponse<OrderEvaluateBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<OrderEvaluateBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<OrderEvaluateBean> result) {
                    }

                    @Override
                    public void onFailure(String msg) {
                    }
                })
        );
    }

    /*获取该订单下可评价商品列表*/
    protected void getOrderEvaluate(String order_id) {
        addDisposable(apiService.getOrderEvaluate(getUserKey(), order_id)
                .compose(RxSchedulers.<BaseResponse<OrderEvaluateBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<OrderEvaluateBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<OrderEvaluateBean> result) {
                        if (result.isSuccess()) {
                            sendData("ORDER_EVALUATE_LIST", result.getData());
                        } else {
                            sendData("ORDER_EVALUATE_LIST", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("ORDER_EVALUATE_LIST", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*我的推广码子功能2:提现申请*/
    protected void addPdCash(String pdc_bank_user, String pdc_bank_no,
                             String pdc_bank_name, String pdc_amount, String password) {
        addDisposable(apiService.addPdCash(getUserKey(), "phone", pdc_bank_user, pdc_bank_no,
                pdc_bank_name, pdc_amount, password, "ok", 1)
                .compose(RxSchedulers.<BaseNothingBean>io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {
                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData("addPdCash", "success");
                        } else {
                            sendData("addPdCash", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("addPdCash", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*我的推广码子功能1:绑定邀请码*/
    protected void bindUserCode(String parent_id) {
        addDisposable(apiService.bindUserCode(getUserKey(), parent_id, 1)
                .compose(RxSchedulers.<BaseNothingBean>io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {
                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData("bindUserCode", "success");
                        } else {
                            sendData("bindUserCode", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("bindUserCode", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*我的推广码*/
    protected void getReduceCash() {
        addDisposable(apiService.getReduceCash(getUserKey())
                .compose(RxSchedulers.<BaseResponse<ReduceCashBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<ReduceCashBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<ReduceCashBean> result) {
                        if (result.isSuccess()) {
                            sendData("REDUCE_CASH", result.getData());
                        } else {
                            sendData("REDUCE_CASH", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("REDUCE_CASH", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*消息列表*/
    protected void getMsgList() {
        addDisposable(apiService.getMsgList(getUserKey(), 1)
                .compose(RxSchedulers.<BaseResponse<MessageListBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<MessageListBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<MessageListBean> result) {
                        if (result.isSuccess()) {
                            sendData("MESSAGE_LIST", result.getData());
                        } else {
                            sendData("MESSAGE_LIST", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("MESSAGE_LIST", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*消息列表*/
    protected void delMsg(String id) {
        addDisposable(apiService.delMsg(getUserKey(), id)
                .compose(RxSchedulers.<BaseNothingBean>io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {
                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData("DELETE_MESSAGE", "success");
                        } else {
                            sendData("DELETE_MESSAGE", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("DELETE_MESSAGE", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*退货详情*/
    protected void getReturnInformation(String returnId) {
        addDisposable(apiService.getReturnInformation(getUserKey(), returnId)
                .compose(RxSchedulers.<BaseResponse<ReturnInformation>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<ReturnInformation>>() {

                    @Override
                    public void onSuccess(BaseResponse<ReturnInformation> result) {
                        if (result.isSuccess()) {
                            sendData("RETURN_INFORMATION", result.getData());
                        } else {
                            sendData("RETURN_INFORMATION", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("RETURN_INFORMATION", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*退款详情*/
    protected void getRefundInformation(String refundId) {
        addDisposable(apiService.getRefundInformation(getUserKey(), refundId)
                .compose(RxSchedulers.<BaseResponse<RefundInformation>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<RefundInformation>>() {

                    @Override
                    public void onSuccess(BaseResponse<RefundInformation> result) {
                        if (result.isSuccess()) {
                            sendData("REFUND_INFORMATION", result.getData());
                        } else {
                            sendData("REFUND_INFORMATION", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("REFUND_INFORMATION", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*会员积分*/
    protected void getVipPoint() {
        addDisposable(apiService.getVipPoint(getUserKey())
                .compose(RxSchedulers.<BaseResponse<VipPointBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<VipPointBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<VipPointBean> result) {
                        if (result.isSuccess()) {
                            sendData("VIP_POINT", result.getData());
                        } else {
                            sendData("VIP_POINT", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("VIP_POINT", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*会员积分历史记录*/
    protected void getVipPointLog(int curPage) {
        addDisposable(apiService.getVipPointLog(getUserKey(), 10, curPage)
                .compose(RxSchedulers.<BaseResponse<VipPointListBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<VipPointListBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<VipPointListBean> result) {
                        if (result.isSuccess()) {
                            sendData("VIP_POINT_LOG", result);
                        } else {
                            sendData("VIP_POINT_LOG", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("VIP_POINT_LOG", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*获取我的红包记录*/
    protected void getPacketList(int curPage) {
        addDisposable(apiService.getPacketList(getUserKey(), 10, curPage)
                .compose(RxSchedulers.<BaseResponse<PacketListBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<PacketListBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<PacketListBean> result) {
                        if (result.isSuccess()) {
                            sendData("PACKET_LIST", result);
                        } else {
                            sendData("PACKET_LIST", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("PACKET_LIST", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*代金券确认领取*/
    protected void cpCharge(String rc_sn) {
        addDisposable(apiService.cpCharge(getUserKey(), rc_sn, "android")
                .compose(RxSchedulers.<BaseNothingBean>io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {

                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData("COUPON_CHARGE", "success");
                        } else {
                            sendData("COUPON_CHARGE", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("COUPON_CHARGE", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*充值卡确认充值*/
    protected void rcCharge(String rc_sn) {
        addDisposable(apiService.rcCharge(getUserKey(), rc_sn, "android")
                .compose(RxSchedulers.<BaseNothingBean>io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {
                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData("RCARD_CHARGE", "success");
                        } else {
                            sendData("RCARD_CHARGE", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("RCARD_CHARGE", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*确认领取红包*/
    protected void packetCharge(String pwd_code) {
        addDisposable(apiService.packetCharge(getUserKey(), pwd_code, "android")
                .compose(RxSchedulers.<BaseNothingBean>io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {
                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData("PACKET_CHARGE", "success");
                        } else {
                            sendData("PACKET_CHARGE", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("PACKET_CHARGE", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*充值卡充值记录*/
    protected void getRCBLog(int curPage) {
        addDisposable(apiService.getRCBLog(getUserKey(), 10, curPage)
                .compose(RxSchedulers.<BaseResponse<RCardLogBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<RCardLogBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<RCardLogBean> result) {
                        if (result.isSuccess()) {
                            sendData("RCCHARGE_LIST", result);
                        } else {
                            sendData("RCCHARGE_LIST", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("RCCHARGE_LIST", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*获取充值卡余额*/
    protected void getRCBalance() {
        addDisposable(apiService.getRCBalance(getUserKey(), "available_rc_balance")
                .compose(RxSchedulers.<BaseResponse<RCardBalanceBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<RCardBalanceBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<RCardBalanceBean> result) {
                        if (result.isSuccess()) {
                            sendData("RCBALANCE", result.getData());
                        } else {
                            sendData("RCBALANCE", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("RCBALANCE", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*代金券列表*/
    protected void getCouponList(int curPage) {
        addDisposable(apiService.getCouponList(getUserKey(), 10, curPage)
                .compose(RxSchedulers.<BaseResponse<CouponListBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<CouponListBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<CouponListBean> result) {
                        if (result.isSuccess()) {
                            sendData("COUPONLISTBEAN", result);
                        } else {
                            sendData("COUPONLISTBEAN", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("COUPONLISTBEAN", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*获取账户余额*/
    protected void getPredepoit() {
        addDisposable(apiService.getPredepoit(getUserKey(), "predepoit")
                .compose(RxSchedulers.<BaseResponse<Predepoit>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<Predepoit>>() {
                    @Override
                    public void onSuccess(BaseResponse<Predepoit> result) {
                        if (result.isSuccess()) {
                            sendData("PREDEPOIT", result.getData());
                        } else {
                            sendData("PREDEPOIT", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("PREDEPOIT", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*获取账户余额列表*/
    protected void getPredepoitLog(int curPage) {
        addDisposable(apiService.getPredepoitLog(getUserKey(), 10, curPage)
                .compose(RxSchedulers.<BaseResponse<PredepoitLogBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<PredepoitLogBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<PredepoitLogBean> result) {
                        if (result.isSuccess()) {
                            sendData("PREDPOSITLOG", result);
                        } else {
                            sendData("PREDPOSITLOG", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("PREDPOSITLOG", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*获取账户充值明细*/
    protected void getPdreChargeList(int curPage) {
        addDisposable(apiService.getPdreChargeList(getUserKey(), 10, curPage)
                .compose(RxSchedulers.<BaseResponse<PdrechargeBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<PdrechargeBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<PdrechargeBean> result) {
                        if (result.isSuccess()) {
                            sendData("PDRECHARGE", result);
                        } else {
                            sendData("PDRECHARGE", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("PDRECHARGE", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*获取账户余额提现列表*/
    protected void getPdcashList(int curPage) {
        addDisposable(apiService.getPdcashList(getUserKey(), 10, curPage)
                .compose(RxSchedulers.<BaseResponse<PdcashBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<PdcashBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<PdcashBean> result) {
                        if (result.isSuccess()) {
                            sendData("PDCASH", result);
                        } else {
                            sendData("PDCASH", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("PDCASH", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*获取账户提现详情*/
    protected void getPdcashList(String pdcId) {
        addDisposable(apiService.getPdCashInfo(getUserKey(), pdcId)
                .compose(RxSchedulers.<BaseResponse<PdcashInfoBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<PdcashInfoBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<PdcashInfoBean> result) {
                        if (result.isSuccess()) {
                            sendData("PDCASH_INFORMATION", result.getData());
                        } else {
                            sendData("PDCASH_INFORMATION", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("PDCASH_INFORMATION", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*获取退款列表*/
    protected void getRefundList(int curPage) {
        addDisposable(apiService.getRefundList(getUserKey(), 10, curPage)
                .compose(RxSchedulers.<BaseResponse<RefundBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<RefundBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<RefundBean> result) {
                        if (result.isSuccess()) {
                            sendData("REFUND", result);
                        } else {
                            sendData("REFUND", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("REFUND", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*获取退货列表*/
    protected void getReturnList(int curPage) {
        addDisposable(apiService.getRetrunList(getUserKey(), 10, curPage)
                .compose(RxSchedulers.<BaseResponse<ReturnBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<ReturnBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<ReturnBean> result) {
                        if (result.isSuccess()) {
                            sendData("RETURN", result);
                        } else {
                            sendData("RETURN", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("RETURN", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*获取虚拟订单详细内容*/
    protected void getVitrualOrderInformation(String orderId) {
        addDisposable(apiService.getVitrualOrderInformation(getUserKey(), orderId)
                .compose(RxSchedulers.<BaseResponse<VirtualInformationBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<VirtualInformationBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<VirtualInformationBean> result) {
                        if (result.isSuccess()) {
                            sendData("VIRTUAL_ORDER_INFORMATION", result.getData());
                        } else {
                            sendData("VIRTUAL_ORDER_INFORMATION", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("VIRTUAL_ORDER_INFORMATION", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*获取虚拟订单详细内容 中的店铺地址*/
    protected void getVitrualOrderStoreAddrs(String orderId) {
        addDisposable(apiService.getVitrualOrderStoreAddrs(getUserKey(), orderId)
                .compose(RxSchedulers.<BaseResponse<VirtualStoreAddrsBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<VirtualStoreAddrsBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<VirtualStoreAddrsBean> result) {
                        if (result.isSuccess()) {
                            sendData("VIRTUAL_ORDER_ADDRS", result.getData());
                        } else {
                            sendData("VIRTUAL_ORDER_ADDRS", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("VIRTUAL_ORDER_ADDRS", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*确认收货*/
    protected void recevieOrder(final String eventKey, String orderId) {
        addDisposable(apiService.recevieOrder(getUserKey(), orderId)
                .compose(RxSchedulers.<BaseNothingBean>io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {

                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess())
                            sendData(eventKey, "RECEVIE_ORDER", "success");
                        else
                            sendData(eventKey, "RECEVIE_ORDER", result.getMessage());
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData(eventKey, "RECEVIE_ORDER", msg == null ? "确认收货失败" : msg);
                    }
                })
        );
    }

    /*删除订单*/
    protected void removeOrder(final String eventKey, String orderId) {
        addDisposable(apiService.removeOrder(getUserKey(), orderId)
                .compose(RxSchedulers.<BaseNothingBean>io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {

                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess())
                            sendData(eventKey, "REMOVE_ORDER", "success");
                        else
                            sendData(eventKey, "REMOVE_ORDER", result.getMessage());
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData(eventKey, "REMOVE_ORDER", msg == null ? "删除订单失败" : msg);
                    }
                })
        );
    }

    /*取消订单*/
    protected void cancelOrder(final String eventKey, String orderId) {
        addDisposable(apiService.cancelOrder(getUserKey(), orderId)
                .compose(RxSchedulers.<BaseNothingBean>io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {

                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess())
                            sendData(eventKey, "REMOVE_ORDER", "success");
                        else
                            sendData(eventKey, "REMOVE_ORDER", result.getMessage());
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData(eventKey, "REMOVE_ORDER", msg == null ? "取消订单失败" : msg);
                    }
                })
        );
    }

    /*取消虚拟订单*/
    protected void cancelVirtualOrder(final String eventKey, String orderId) {
        addDisposable(apiService.cancelVirtualOrder(getUserKey(), orderId)
                .compose(RxSchedulers.<BaseNothingBean>io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {

                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess())
                            sendData(eventKey, "REMOVE_ORDER", "success");
                        else
                            sendData(eventKey, "REMOVE_ORDER", result.getMessage());
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData(eventKey, "REMOVE_ORDER", msg == null ? "取消订单失败" : msg);
                    }
                })
        );
    }

    /*分销管理*/
    protected void getInviteList(String wwi, int curpage) {
        addDisposable(apiService.getInviteList(getUserKey(), wwi, 10, curpage)
                .compose(RxSchedulers.<BaseNothingBean>io_main())
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
                .compose(RxSchedulers.<BaseResponse<AddressDataBean.AddressListBean>>io_main())
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
                .compose(RxSchedulers.<BaseNothingBean>io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {

                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.getCode() == 200) {
                            sendData("DEL_ADDRESS", "success");
                        } else {
                            sendData("DEL_ADDRESS", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("DEL_ADDRESS", msg == null ? "删除失败" : msg);
                    }
                })

        );
    }

    /*获取收货地址列表*/
    protected void getAddressList() {
        addDisposable(apiService.getAddressList(getUserKey())
                .compose(RxSchedulers.<BaseResponse<AddressDataBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<AddressDataBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<AddressDataBean> response) {
                        if (response.isSuccess()) {
                            sendData("GET_ADDRESS_LIST", response.getData());
                        } else {
                            sendData("GET_ADDRESS_LIST", response.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("GET_ADDRESS_LIST", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*新增收货地址*/
    protected void addAddress(int id_default, String name, String city_id, String area_id, String area_info,
                              String address, String phone) {
        addDisposable(apiService.addAddress(getUserKey(), id_default, name, city_id, area_id, area_info, address, phone)
                .compose(RxSchedulers.<BaseNothingBean>io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {

                    @Override
                    public void onSuccess(BaseNothingBean baseNothingBean) {
                        if (baseNothingBean.getCode() == 200) {
                            sendData("EDIT_ADDRESS", "success");
                        } else {
                            sendData("EDIT_ADDRESS", baseNothingBean.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("EDIT_ADDRESS", msg == null ? "保存失败" : msg);
                    }

                    @Override
                    protected void onNoNetWork() {

                    }
                })
        );
    }

    /*编辑收货地址*/
    protected void editAddress(String address_id, int id_default, String name, String city_id, String area_id, String area_info,
                               String address, String phone) {
        addDisposable(apiService.editAddress(getUserKey(), address_id, id_default, name, city_id, area_id, area_info, address, phone)
                .compose(RxSchedulers.<BaseNothingBean>io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {

                    @Override
                    public void onSuccess(BaseNothingBean baseNothingBean) {
                        if (baseNothingBean.getCode() == 200) {
                            sendData("EDIT_ADDRESS", "success");
                        } else {
                            sendData("EDIT_ADDRESS", baseNothingBean.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("EDIT_ADDRESS", msg == null ? "保存失败" : msg);
                    }

                    @Override
                    protected void onNoNetWork() {

                    }
                })
        );
    }

    /*获取城市列表*/
    protected void getCityList(String areaId) {
        addDisposable(apiService.getCityList(getUserKey(), areaId)
                .compose(RxSchedulers.<BaseResponse<CityBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<CityBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<CityBean> response) {
                        if (response.isSuccess())
                            sendData("GET_CITY_LIST", response.getData());
                        else
                            sendData("GET_CITY_LIST", response.getMessage());
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("GET_CITY_LIST", msg == null ? "获取失败" : msg);
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
                .compose(RxSchedulers.<BaseNothingBean>io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {
                                   @Override
                                   public void onSuccess(BaseNothingBean result) {
                                       if (result.isSuccess())
                                           sendData("CLEAR_FOOTPRINT", "success");
                                       else
                                           sendData("CLEAR_FOOTPRINT", result.getMessage());
                                   }

                                   @Override
                                   public void onFailure(String msg) {
                                       KLog.i(msg);
                                       sendData("CLEAR_FOOTPRINT", msg == null ? "清空失败" : msg);
                                   }
                               }
                )
        );
    }

    /*我的商城页面 获取我的足迹*/
    protected void getMyFootprint(int curPage) {
        addDisposable(apiService.getFootprint(getUserKey(), 10, curPage)
                .compose(RxSchedulers.<BaseResponse<FootprintBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<FootprintBean>>() {
                                   @Override
                                   public void onSuccess(BaseResponse<FootprintBean> result) {
                                       if (result.isSuccess())
                                           sendData("GET_FOOTPRINT", result);
                                       else
                                           sendData("GET_FOOTPRINT", result.getMessage());
                                   }

                                   @Override
                                   public void onFailure(String msg) {
                                       KLog.i(msg);
                                       sendData("GET_FOOTPRINT", msg == null ? "获取失败" : msg);
                                   }
                               }
                )
        );
    }


    /**
     * 我的商城页面 获取用户信息
     **/
    protected void getUserInfo() {
        addDisposable(apiService.getUserInfo(getUserKey())
                .compose(RxSchedulers.<BaseResponse<MyInfoBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<MyInfoBean>>() {
                                   @Override
                                   public void onSuccess(BaseResponse<MyInfoBean> result) {
                                       if (result.isSuccess())
                                           sendData("GET_USER_INFO", result.getData());
                                       else
                                           sendData("GET_USER_INFO", result.getMessage());
                                   }

                                   @Override
                                   public void onFailure(String msg) {
                                       KLog.i(msg);
                                       sendData("GET_USER_INFO", msg == null ? "获取失败" : msg);
                                   }
                               }
                )
        );
    }

    /*获取支付密码信息*/
    public void getPayPwdInfo() {
        addDisposable(apiService.getPayPwdInfo(getUserKey())
                .compose(RxSchedulers.<BaseResponse<BaseCheckBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<BaseCheckBean>>() {
                                   @Override
                                   public void onSuccess(BaseResponse<BaseCheckBean> result) {
                                       if (result.isSuccess()) {
                                           sendData("USER_PAYPWD_INFO", result.getData());
                                       } else {
                                           sendData("Err_USER_INFO", result.getMessage());
                                       }
                                   }

                                   @Override
                                   public void onFailure(String msg) {
                                       KLog.i(msg);
                                       sendData("Err_USER_INFO", msg);
                                   }
                               }
                )
        );
    }

    /*获取绑定手机状态*/
    public void getPhoneInfo() {
        addDisposable(apiService.getPhoneInfo(getUserKey())
                .compose(RxSchedulers.<BaseResponse<BaseCheckBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<BaseCheckBean>>() {
                                   @Override
                                   public void onSuccess(BaseResponse<BaseCheckBean> result) {
                                       if (result.isSuccess()) {
                                           sendData("USER_PHONE_INFO", result.getData());
                                       } else {
                                           sendData("Err_USER_INFO", result.getMessage());
                                       }
                                   }

                                   @Override
                                   public void onFailure(String msg) {
                                       sendData("Err_USER_INFO", msg);
                                   }
                               }
                )
        );
    }

    /*获取我的财产*/
    public void getProperty() {
        addDisposable(apiService.getMyAsset(getUserKey())
                .compose(RxSchedulers.<BaseResponse<PropertyBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<PropertyBean>>() {
                                   @Override
                                   public void onSuccess(BaseResponse<PropertyBean> result) {
                                       if (result.isSuccess()) {
                                           sendData("MY_ASSET", result.getData());
                                       } else {
                                           sendData("MY_ASSET", result.getMessage());
                                       }

                                   }

                                   @Override
                                   public void onFailure(String msg) {
                                       KLog.i(msg);
                                       sendData("MY_ASSET", msg == null ? "获取失败" : msg);
                                   }
                               }
                )
        );
    }

    /*发送用户反馈*/
    protected void sendFeedBack(String feedBack) {
        addDisposable(apiService.sendFeedBack(getUserKey(), feedBack)
                .compose(RxSchedulers.<BaseResponse<BaseIntDatasBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<BaseIntDatasBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<BaseIntDatasBean> result) {
                        if (result.isSuccess()) {
                            sendData("SEND_FEEDBACK", "success");
                        } else {
                            sendData("SEND_FEEDBACK", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("SEND_FEEDBACK", msg == null ? "反馈失败" : msg);
                    }

                })
        );
    }

    /*获取店铺收藏列表*/
    protected void getShopsCollectList(int curpage) {
        addDisposable(apiService.getShopsCollectList(getUserKey(), curpage, 10)
                .compose(RxSchedulers.<BaseResponse<ShopsCollectionBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<ShopsCollectionBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<ShopsCollectionBean> result) {
                        if (result.isSuccess()) {
                            sendData("SHOPS_COLLECT_LIST", result);
                        } else {
                            sendData("SHOPS_COLLECT_LIST", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("SHOPS_COLLECT_LIST", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*店铺删除收藏动作*/
    protected void deleShopsCollect(String storeId) {
        addDisposable(apiService.deleShopsCollect(getUserKey(), "android", storeId)
                .compose(RxSchedulers.<BaseNothingBean>io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {
                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData("DEL_SHOP_COLLECT", "success");
                        } else {
                            sendData("DEL_SHOP_COLLECT", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("DEL_SHOP_COLLECT", msg == null ? "删除失败" : msg);
                    }
                })
        );
    }

    /*商品删除收藏动作*/
    protected void deleGoodsCollect(String favId) {
        addDisposable(apiService.deleGoodsCollect(getUserKey(), "android", favId)
                .compose(RxSchedulers.<BaseNothingBean>io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {
                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            sendData("DEL_GOODS_COLLECT", "success");
                        } else {
                            sendData("DEL_GOODS_COLLECT", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("DEL_GOODS_COLLECT", msg == null ? "删除失败" : msg);
                    }
                })
        );
    }

    /*获取商品收藏列表*/
    protected void getProductCollectList(int curpage) {
        addDisposable(apiService.getProductCollectList(getUserKey(), curpage, 10)
                .compose(RxSchedulers.<BaseResponse<ProductCollectionBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<ProductCollectionBean>>() {
                                   @Override
                                   public void onSuccess(BaseResponse<ProductCollectionBean> result) {
                                       if (result.isSuccess()) {
                                           sendData("PRODUCT_COLLECT_LIST", result);
                                       } else {
                                           sendData("PRODUCT_COLLECT_LIST", result.getMessage());
                                       }
                                   }

                                   @Override
                                   public void onFailure(String msg) {
                                       KLog.i(msg);
                                       sendData("PRODUCT_COLLECT_LIST", msg == null ? "获取失败" : msg);
                                   }
                               }
                )
        );
    }

    /*获取实物订单列表（包括搜索）*/
    protected void getPhysicalOrderList(final String event_key, String state_type, String order_key, int page, final int curpage) {
        addDisposable(apiService.getPhysicalOrderList(getUserKey(), state_type, order_key, page, curpage)
                .compose(RxSchedulers.<BaseResponse<PhysicalOrderBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<PhysicalOrderBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<PhysicalOrderBean> result) {
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
                .compose(RxSchedulers.<BaseResponse<OrderInformationBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<OrderInformationBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<OrderInformationBean> result) {
                        if (result.isSuccess()) {
                            sendData("PHYSICAL_ORDER_INFORMATION", result.getData());
                        } else {
                            sendData("PHYSICAL_ORDER_INFORMATION", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("PHYSICAL_ORDER_INFORMATION", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*获取实物订单最新一条物流信息*/
    protected void getOrderDeliverInformation(String order_id) {
        addDisposable(apiService.getOrderDeliverInformation(getUserKey(), order_id)
                .compose(RxSchedulers.<BaseResponse<OrderDeliverBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<OrderDeliverBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<OrderDeliverBean> result) {
                        if (result.isSuccess()) {
                            sendData("ORDER_DELIVER_INFORMATION", result.getData());
                        } else {
                            sendData("ORDER_DELIVER_INFORMATION", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("ORDER_DELIVER_INFORMATION", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*获取实物订单物流详情*/
    protected void getOrderDeliverList(String order_id) {
        addDisposable(apiService.getOrderDeliverList(getUserKey(), order_id)
                .compose(RxSchedulers.<BaseResponse<OrderDeliverListBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<OrderDeliverListBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<OrderDeliverListBean> result) {
                        if (result.isSuccess()) {
                            sendData("ORDER_DELIVER_LIST", result.getData());
                        } else {
                            sendData("ORDER_DELIVER_LIST", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("ORDER_DELIVER_LIST", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*获取虚拟订单列表（包括搜索）*/
    protected void getVirtualOrderList(final String event_key, String state_type, String order_key, int page, final int curpage) {
        addDisposable(apiService.getVirtualOrderList(getUserKey(), state_type, order_key, page, curpage)
                .compose(RxSchedulers.<BaseResponse<VirtualOrderBean>>io_main())
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

}
