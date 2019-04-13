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
import com.mingpinmall.me.ui.bean.FootprintBean;
import com.mingpinmall.me.ui.bean.MyInfoBean;
import com.mingpinmall.me.ui.bean.ProductCollectionBean;
import com.mingpinmall.me.ui.bean.PropertyBean;
import com.mingpinmall.me.ui.bean.ShopsCollectionBean;
import com.mingpinmall.me.ui.constants.Constants;
import com.socks.library.KLog;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/1
 **/
public class MeRepository extends BaseRepository {

    private MeApiService apiService = RetrofitClient.getInstance().create(MeApiService.class);

    /**
     * 获取收货地址详细内容
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
                               }
                )
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
     * 获取订单列表（包括搜索）
     *
     * @param state_type [state_new:待付款]
     *                   [state_send:待收货]
     *                   [state_notakes:待自提]
     *                   [state_noeval:待评价]
     * @param order_key  搜索内容，产品标题或订单号
     * @param page       每页数量
     * @param curpage    页码
     */
    protected void getOrderList(final String event_key, String state_type, String order_key, int page, final int curpage) {
        addDisposable(apiService.getOrder(getUserKey(), state_type, order_key, page, curpage)
                .compose(RxSchedulers.<BaseIntDatasBean>io_main())
                .subscribeWith(new RxSubscriber<BaseIntDatasBean>() {

                    @Override
                    public void onSuccess(BaseIntDatasBean baseIntDatasBean) {
                        if (curpage == 1) {
                            sendData(event_key, "refresh success", baseIntDatasBean);
                        } else {
                            sendData(event_key, "loadmore success", baseIntDatasBean);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        if (curpage == 1) {
                            sendData(event_key, "refresh err", msg);
                        } else {
                            sendData(event_key, "loadmore err", msg);
                        }
                    }
                })
        );

    }

}
