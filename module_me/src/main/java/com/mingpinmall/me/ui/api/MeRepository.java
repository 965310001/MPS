package com.mingpinmall.me.ui.api;

import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.base.BaseRepository;
import com.goldze.common.dmvvm.http.RetrofitClient;
import com.goldze.common.dmvvm.http.rx.RxSchedulers;
import com.goldze.common.dmvvm.http.rx.RxSubscriber;
import com.mingpinmall.me.ui.bean.BaseCheckBean;
import com.mingpinmall.me.ui.bean.BaseIntDatasBean;
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

}
