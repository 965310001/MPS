package com.mingpinmall.me.ui.api;

import android.util.Log;

import com.goldze.common.dmvvm.base.bean.AddressDataBean;
import com.goldze.common.dmvvm.base.bean.BaseNothingBean;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.base.BaseRepository;
import com.goldze.common.dmvvm.base.mvvm.stateview.StateConstants;
import com.goldze.common.dmvvm.http.RetrofitClient;
import com.goldze.common.dmvvm.http.rx.RxSchedulers;
import com.goldze.common.dmvvm.http.rx.RxSubscriber;
import com.mingpinmall.me.ui.bean.*;
import com.socks.library.KLog;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/1
 **/
public class MeRepository extends BaseRepository {

    private MeApiService apiService = RetrofitClient.getInstance().create(MeApiService.class);

    /*会员积分*/
    protected void getVipPoint() {
        addDisposable(apiService.getVipPoint(getUserKey())
                .compose(RxSchedulers.<BaseResponse<VipPointBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<VipPointBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<VipPointBean> result) {
                        if (result.isSuccess()) {
                            sendData("VIP_POINT", "success", result.getData());
                        } else {
                            sendData("VIP_POINT", "err", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("VIP_POINT", "err", msg == null ? "获取失败" : msg);
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
                            sendData("PACKET_LIST", "success", result);
                        } else {
                            sendData("PACKET_LIST", "err", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("PACKET_LIST", "err", msg == null ? "获取失败" : msg);
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
                            sendData("VIP_POINT_LOG", "success", result);
                        } else {
                            sendData("VIP_POINT_LOG", "err", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("VIP_POINT_LOG", "err", msg == null ? "获取失败" : msg);
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
                            sendData("COUPON_CHARGE", "success", result);
                        } else {
                            sendData("COUPON_CHARGE", "err", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("COUPON_CHARGE", "err", msg == null ? "获取失败" : msg);
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
                            sendData("RCARD_CHARGE", "success", result);
                        } else {
                            sendData("RCARD_CHARGE", "err", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("RCARD_CHARGE", "err", msg == null ? "获取失败" : msg);
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
                            sendData("PACKET_CHARGE", "success", result);
                        } else {
                            sendData("PACKET_CHARGE", "err", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("PACKET_CHARGE", "err", msg == null ? "获取失败" : msg);
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
                            sendData("RCCHARGE_LIST", "success", result);
                        } else {
                            sendData("RCCHARGE_LIST", "err", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("RCCHARGE_LIST", "err", msg == null ? "获取失败" : msg);
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
                            sendData("COUPONLISTBEAN", "success", result);
                        } else {
                            sendData("COUPONLISTBEAN", "err", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("COUPONLISTBEAN", "err", msg == null ? "获取失败" : msg);
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
                            sendData("RCBALANCE", "success", result.getData());
                        } else {
                            sendData("RCBALANCE", "err", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("RCBALANCE", "err", msg == null ? "获取失败" : msg);
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
                            sendData("PREDEPOIT", "success", result.getData());
                        } else {
                            sendData("PREDEPOIT", "err", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("PREDEPOIT", "err", msg == null ? "获取失败" : msg);
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
                            sendData("PREDPOSITLOG", "success", result);
                        } else {
                            sendData("PREDPOSITLOG", "err", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("PREDPOSITLOG", "err", msg == null ? "获取失败" : msg);
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
                            sendData("PDRECHARGE", "success", result);
                        } else {
                            sendData("PDRECHARGE", "err", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("PDRECHARGE", "err", msg == null ? "获取失败" : msg);
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
                            sendData("PDCASH", "success", result);
                        } else {
                            sendData("PDCASH", "err", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("PDCASH", "err", msg == null ? "获取失败" : msg);
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
                            sendData("PDCASH_INFORMATION", "success", result.getData());
                        } else {
                            sendData("PDCASH_INFORMATION", "err", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("PDCASH_INFORMATION", "err", msg == null ? "获取失败" : msg);
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
                    public void onSuccess(BaseResponse<RefundBean> virtualStoreAddrsBeanBaseResponse) {

                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                })
        );
    }

    /**
     * 获取虚拟订单详细内容
     *
     * @param orderId
     */
    protected void getVitrualOrderInformation(String orderId) {
        addDisposable(apiService.getVitrualOrderInformation(getUserKey(), orderId)
                .compose(RxSchedulers.<BaseResponse<VirtualInformationBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<VirtualInformationBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<VirtualInformationBean> result) {
                        if (result.isSuccess()) {
                            sendData("VIRTUAL_ORDER_INFORMATION", "success", result.getData());
                        } else {
                            sendData("VIRTUAL_ORDER_INFORMATION", "err", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("VIRTUAL_ORDER_INFORMATION", "err", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /**
     * 获取虚拟订单详细内容 中的店铺地址
     *
     * @param orderId
     */
    protected void getVitrualOrderStoreAddrs(String orderId) {
        addDisposable(apiService.getVitrualOrderStoreAddrs(getUserKey(), orderId)
                .compose(RxSchedulers.<BaseResponse<VirtualStoreAddrsBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<VirtualStoreAddrsBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<VirtualStoreAddrsBean> result) {
                        if (result.isSuccess()) {
                            sendData("VIRTUAL_ORDER_ADDRS", "success", result.getData());
                        } else {
                            sendData("VIRTUAL_ORDER_ADDRS", "err", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("VIRTUAL_ORDER_ADDRS", "err", msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /**
     * 取消订单
     *
     * @param orderId
     */
    protected void cancelOrder(final String eventKey, String orderId) {
        addDisposable(apiService.cancelOrder(getUserKey(), orderId)
                .compose(RxSchedulers.<BaseNothingBean>io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {

                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess())
                            sendData(eventKey, "REFRESH_ORDER_LIST", "");
                        else
                            sendData(eventKey, "DO_SOMETHING_ERR", result.getMessage());
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData(eventKey, "DO_SOMETHING_ERR", msg == null ? "取消订单失败" : msg);
                    }
                })
        );
    }

    /**
     * 取消虚拟订单
     *
     * @param orderId
     */
    protected void cancelVirtualOrder(final String eventKey, String orderId) {
        addDisposable(apiService.cancelVirtualOrder(getUserKey(), orderId)
                .compose(RxSchedulers.<BaseNothingBean>io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {

                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess())
                            sendData(eventKey, "REFRESH_ORDER_LIST", "");
                        else
                            sendData(eventKey, "DO_SOMETHING_ERR", result.getMessage());
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData(eventKey, "DO_SOMETHING_ERR", msg == null ? "取消订单失败" : msg);
                    }
                })
        );
    }

    /**
     * 分销管理
     *
     * @param wwi
     * @param curpage
     */
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

    /**
     * 获取收货地址详细内容
     *
     * @param addressId
     */
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

    /**
     * 删除收货地址
     *
     * @param addressId
     */
    protected void delAddress(String addressId) {
        addDisposable(apiService.delAddress(getUserKey(), addressId)
                .compose(RxSchedulers.<BaseNothingBean>io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {

                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.getCode() == 200) {
                            sendData("DEL_ADDRESS", "success", "删除成功");
                        } else {
                            sendData("DEL_ADDRESS", "err", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("DEL_ADDRESS", "err", msg == null ? "删除失败" : msg);
                    }
                })

        );
    }

    /**
     * 获取收货地址列表
     */
    protected void getAddressList() {
        addDisposable(apiService.getAddressList(getUserKey())
                .compose(RxSchedulers.<BaseResponse<AddressDataBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<AddressDataBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<AddressDataBean> response) {
                        if (response.isSuccess()) {
                            sendData("GET_ADDRESS_LIST", "success", response.getData());
                        } else {
                            sendData("GET_ADDRESS_LIST", "err", response.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("GET_ADDRESS_LIST", "err", msg == null ? "获取失败" : msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        showPageState("ADDRESS_ACTIVITY", StateConstants.NET_WORK_STATE);
                    }
                })
        );
    }

    /**
     * 新增收货地址
     *
     * @param id_default
     * @param name
     * @param city_id
     * @param area_id
     * @param area_info
     * @param address
     * @param phone
     */
    protected void addAddress(int id_default, String name, String city_id, String area_id, String area_info,
                              String address, String phone) {
        addDisposable(apiService.addAddress(getUserKey(), id_default, name, city_id, area_id, area_info, address, phone)
                .compose(RxSchedulers.<BaseNothingBean>io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {

                    @Override
                    public void onSuccess(BaseNothingBean baseNothingBean) {
                        if (baseNothingBean.getCode() == 200) {
                            sendData("EDIT_ADDRESS", "success", "200");
                        } else {
                            sendData("EDIT_ADDRESS", "err", baseNothingBean.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("EDIT_ADDRESS", "err", msg == null ? "保存失败" : msg);
                    }

                    @Override
                    protected void onNoNetWork() {

                    }
                })
        );
    }

    /**
     * 编辑收货地址
     *
     * @param address_id 收货地址ID
     * @param id_default
     * @param name
     * @param city_id
     * @param area_id
     * @param area_info
     * @param address
     * @param phone
     */
    protected void editAddress(String address_id, int id_default, String name, String city_id, String area_id, String area_info,
                               String address, String phone) {
        addDisposable(apiService.editAddress(getUserKey(), address_id, id_default, name, city_id, area_id, area_info, address, phone)
                .compose(RxSchedulers.<BaseNothingBean>io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {

                    @Override
                    public void onSuccess(BaseNothingBean baseNothingBean) {
                        if (baseNothingBean.getCode() == 200) {
                            sendData("EDIT_ADDRESS", "success", "200");
                        } else {
                            sendData("EDIT_ADDRESS", "err", baseNothingBean.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("EDIT_ADDRESS", "err", msg == null ? "保存失败" : msg);
                    }

                    @Override
                    protected void onNoNetWork() {

                    }
                })
        );
    }

    /**
     * 获取城市列表
     *
     * @param areaId
     */
    protected void getCityList(String areaId) {
        addDisposable(apiService.getCityList(getUserKey(), areaId)
                .compose(RxSchedulers.<BaseResponse<CityBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<CityBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<CityBean> response) {
                        if (response.isSuccess())
                            sendData("GET_CITY_LIST", "success", response.getData());
                        else
                            sendData("GET_CITY_LIST", "err", response.getMessage());
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("GET_CITY_LIST", "err", msg == null ? "获取失败" : msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        showPageState("SelectCityActivity", StateConstants.NET_WORK_STATE);
                    }
                })
        );
    }

    /**
     * 我的商城页面 清空我的足迹
     **/
    protected void clearnMyFootprint() {
        addDisposable(apiService.clearnFootprint(getUserKey())
                .compose(RxSchedulers.<BaseResponse<FootprintBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<FootprintBean>>() {
                                   @Override
                                   public void onSuccess(BaseResponse<FootprintBean> result) {
                                       if (result.isSuccess())
                                           sendData("CLEAR_FOOTPRINT", "success loadmore", "");
                                       else
                                           sendData("CLEAR_FOOTPRINT", "err", result.getMessage());
                                   }

                                   @Override
                                   public void onFailure(String msg) {
                                       KLog.i(msg);
                                       sendData("CLEAR_FOOTPRINT", "err", msg == null ? "清空失败" : msg);
                                   }
                               }
                )
        );
    }

    /**
     * 我的商城页面 获取我的足迹
     **/
    protected void getMyFootprint(final int curPage) {
        addDisposable(apiService.getFootprint(getUserKey(), 10, curPage)
                .compose(RxSchedulers.<BaseResponse<FootprintBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<FootprintBean>>() {
                                   @Override
                                   public void onSuccess(BaseResponse<FootprintBean> result) {
                                       if (result.isSuccess())
                                           if (curPage > 1) {
                                               sendData("GET_FOOTPRINT", "success loadmore", result);
                                           } else {
                                               sendData("GET_FOOTPRINT", "success refresh", result);
                                           }
                                       else
                                           sendData("GET_FOOTPRINT", "err", result.getMessage());
                                   }

                                   @Override
                                   public void onFailure(String msg) {
                                       KLog.i(msg);
                                       sendData("GET_FOOTPRINT", "err", msg);
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
                                           sendData("GET_USER_INFO", "success", result.getData());
                                       else
                                           sendData("GET_USER_INFO", "fail", result);
                                   }

                                   @Override
                                   public void onFailure(String msg) {
                                       KLog.i(msg);
                                       sendData("GET_USER_INFO", "err", msg);
                                   }
                               }
                )
        );
    }

    public void getPayPwdInfo() {
        addDisposable(apiService.getPayPwdInfo(getUserKey())
                .compose(RxSchedulers.<BaseResponse<BaseCheckBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<BaseCheckBean>>() {
                                   @Override
                                   public void onSuccess(BaseResponse<BaseCheckBean> result) {
                                       sendData("USER_PAYPWD_INFO", result.getData());
                                   }

                                   @Override
                                   public void onFailure(String msg) {
                                       KLog.i(msg);
                                       sendData("Err_USER_PAYPWD_INFO", msg);
                                   }
                               }
                )
        );
    }

    /**
     * 获取我的财产
     */
    public void getProperty() {
        addDisposable(apiService.getMyAsset(getUserKey())
                .compose(RxSchedulers.<BaseResponse<PropertyBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<PropertyBean>>() {
                                   @Override
                                   public void onSuccess(BaseResponse<PropertyBean> result) {
                                       sendData("MY_ASSET", result.getData());
                                   }

                                   @Override
                                   public void onFailure(String msg) {
                                       KLog.i(msg);
                                       sendData("Err_MY_ASSET", msg);
                                   }
                               }
                )
        );
    }

    public void getPhoneInfo() {
        addDisposable(apiService.getPhoneInfo(getUserKey())
                .compose(RxSchedulers.<BaseResponse<BaseCheckBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<BaseCheckBean>>() {
                                   @Override
                                   public void onSuccess(BaseResponse<BaseCheckBean> result) {
                                       sendData("USER_PHONE_INFO", result.getData());
                                   }

                                   @Override
                                   public void onFailure(String msg) {
                                       KLog.i(msg);
                                       sendData("Err_USER_PHONE_INFO", msg);
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
                        sendData("SEND_FEEDBACK", result.getData());
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("Err_SEND_FEEDBACK", msg);
                    }

                })
        );
    }

    /**
     * 获取店铺收藏列表
     *
     * @param
     */
    protected void getShopsCollectList(final int curpage) {
        addDisposable(apiService.getShopsCollectList(getUserKey(), curpage, 10)
                .compose(RxSchedulers.<BaseResponse<ShopsCollectionBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<ShopsCollectionBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<ShopsCollectionBean> result) {
                        if (curpage > 1) {
                            sendData("SHOPS_COLLECT_LIST", "loadmore", result);
                        } else {
                            sendData("SHOPS_COLLECT_LIST", "success", result);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("SHOPS_COLLECT_LIST", "err", msg);
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
                            sendData("DEL_SHOP_COLLECT", "success", result);
                        } else {
                            sendData("DEL_SHOP_COLLECT", "err", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("DEL_SHOP_COLLECT", "err", msg == null ? "删除失败" : msg);
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
                            sendData("DEL_GOODS_COLLECT", "success", result);
                        } else {
                            sendData("DEL_GOODS_COLLECT", "err", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("DEL_GOODS_COLLECT", "err", msg == null ? "删除失败" : msg);
                    }
                })
        );
    }

    /**
     * 获取商品收藏列表
     *
     * @param
     */
    protected void getProductCollectList(final int curpage) {
        addDisposable(apiService.getProductCollectList(getUserKey(), curpage, 10)
                .compose(RxSchedulers.<BaseResponse<ProductCollectionBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<ProductCollectionBean>>() {
                                   @Override
                                   public void onSuccess(BaseResponse<ProductCollectionBean> result) {
                                       if (curpage > 1) {
                                           sendData("PRODUCT_COLLECT_LIST", "loadmore", result);
                                       } else {
                                           sendData("PRODUCT_COLLECT_LIST", "success", result);
                                       }
                                   }

                                   @Override
                                   public void onFailure(String msg) {
                                       KLog.i(msg);
                                       sendData("PRODUCT_COLLECT_LIST", "err", msg);
                                   }
                               }
                )
        );
    }

    /**
     * 获取实物订单列表（包括搜索）
     *
     * @param state_type [state_new:待付款]
     *                   [state_send:待收货]
     *                   [state_notakes:待自提]
     *                   [state_noeval:待评价]
     * @param order_key  搜索内容，产品标题或订单号
     * @param page       每页数量
     * @param curpage    页码
     */
    protected void getPhysicalOrderList(final String event_key, String state_type, String order_key, int page, final int curpage) {
        addDisposable(apiService.getPhysicalOrderList(getUserKey(), state_type, order_key, page, curpage)
                .compose(RxSchedulers.<BaseResponse<PhysicalOrderBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<PhysicalOrderBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<PhysicalOrderBean> result) {
                        if (!result.isSuccess()) {
                            Log.i("数据", "onChanged: 发送err " + event_key + "err");
                            sendData(event_key, event_key + "err", result.getMessage());
                            return;
                        }
                        Log.i("数据", "onChanged: 发送sucess " + event_key + "success");
                        sendData(event_key, event_key + "success", result);
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        Log.i("数据", "onChanged: 发送err " + event_key);
                        sendData(event_key, event_key + "err", msg == null ? "获取失败" : msg);
                    }
                })
        );

    }

    /**
     * 获取订单详情
     *
     * @param order_id
     */
    protected void getOrderInformation(String order_id) {
        addDisposable(apiService.getOrderInformation(getUserKey(), order_id)
                .compose(RxSchedulers.<BaseResponse<OrderInformationBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<OrderInformationBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<OrderInformationBean> result) {
                        if (result.isSuccess()) {
                            sendData("ORDER_INFORMATION", "success", result.getData());
                        } else {
                            sendData("ORDER_INFORMATION", "err", result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData("ORDER_INFORMATION", "err", msg == null ? "获取失败" : msg);
                    }
                })
        );

    }

    /**
     * 获取虚拟订单列表（包括搜索）
     *
     * @param state_type [state_new:待付款]
     *                   [state_send:待收货]
     *                   [state_notakes:待自提]
     *                   [state_noeval:待评价]
     * @param order_key  搜索内容，产品标题或订单号
     * @param page       每页数量
     * @param curpage    页码
     */
    protected void getVirtualOrderList(final String event_key, String state_type, String order_key, int page, final int curpage) {
        addDisposable(apiService.getVirtualOrderList(getUserKey(), state_type, order_key, page, curpage)
                .compose(RxSchedulers.<BaseResponse<VirtualOrderBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<VirtualOrderBean>>() {

                    @Override
                    public void onSuccess(BaseResponse<VirtualOrderBean> result) {
                        Log.e("网络请求", "onSuccess: " + event_key);
                        if (!result.isSuccess()) {
                            sendData(event_key, event_key + "err", result.getMessage());
                            return;
                        }
                        sendData(event_key, event_key + "success", result);
                    }

                    @Override
                    public void onFailure(String msg) {
                        KLog.i(msg);
                        sendData(event_key, event_key + "err", msg == null ? "获取失败" : msg);
                    }
                })
        );

    }

}
