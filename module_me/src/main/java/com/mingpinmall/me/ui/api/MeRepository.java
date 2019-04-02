package com.mingpinmall.me.ui.api;

import com.goldze.common.dmvvm.base.mvvm.base.BaseRepository;
import com.goldze.common.dmvvm.http.RetrofitClient;
import com.goldze.common.dmvvm.http.rx.RxSchedulers;
import com.goldze.common.dmvvm.http.rx.RxSubscriber;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.mingpinmall.me.ui.bean.BaseCheckBean;
import com.mingpinmall.me.ui.bean.BaseIntDatasBean;
import com.mingpinmall.me.ui.bean.BasePageBean;
import com.mingpinmall.me.ui.bean.MyInfoBean;
import com.mingpinmall.me.ui.bean.UserBean;
import com.mingpinmall.me.ui.constants.Constants;
import com.socks.library.KLog;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/1
 **/
public class MeRepository extends BaseRepository {

    private MeApiService apiService = RetrofitClient.getInstance().create(MeApiService.class);
    UserBean userBean = (UserBean) SharePreferenceUtil.getUser(UserBean.class);

    /**
     * 我的商城页面 获取用户信息
     **/
    protected void getUserInfo() {
        addDisposable(apiService.getUserInfo(userBean.getDatas().getKey())
                .compose(RxSchedulers.<MyInfoBean>io_main())
                .subscribeWith(new RxSubscriber<MyInfoBean>() {
                                   @Override
                                   public void onSuccess(MyInfoBean result) {
                                       sendData(Constants.EVENT_KEY_ME_GETUSERINFO, result);
                                   }

                                   @Override
                                   public void onFailure(String msg) {
                                       KLog.i(msg);
                                       sendData(Constants.Err_EVENT_KEY_ME_GETUSERINFO, msg);
                                   }
                               }
                )
        );
    }

    public void getPayPwdInfo() {
        addDisposable(apiService.getPayPwdInfo(userBean.getDatas().getKey())
                .compose(RxSchedulers.<BaseCheckBean>io_main())
                .subscribeWith(new RxSubscriber<BaseCheckBean>() {
                                   @Override
                                   public void onSuccess(BaseCheckBean result) {
                                       sendData("USER_PAYPWD_INFO", result);
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

    public void getPhoneInfo() {
        addDisposable(apiService.getPhoneInfo(userBean.getDatas().getKey())
                .compose(RxSchedulers.<BaseCheckBean>io_main())
                .subscribeWith(new RxSubscriber<BaseCheckBean>() {
                                   @Override
                                   public void onSuccess(BaseCheckBean result) {
                                       sendData("USER_PHONE_INFO", result);
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
        addDisposable(apiService.sendFeedBack(userBean.getDatas().getKey(), feedBack)
                .compose(RxSchedulers.<BaseIntDatasBean>io_main())
                .subscribeWith(new RxSubscriber<BaseIntDatasBean>() {
                    @Override
                    public void onSuccess(BaseIntDatasBean result) {
                        sendData("SEND_FEEDBACK", result);
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
     * @param
     */
    protected void getShopsCollectList(int curpage) {
        addDisposable(apiService.getShopsCollectList(userBean.getDatas().getKey(), curpage, 10)
                .compose(RxSchedulers.<BasePageBean>io_main())
                .subscribeWith(new RxSubscriber<BasePageBean>() {
                                   @Override
                                   public void onSuccess(BasePageBean result) {
                                       sendData("SHOPS_COLLECT_LIST", result);
                                   }

                                   @Override
                                   public void onFailure(String msg) {
                                       KLog.i(msg);
                                       sendData("Err_SHOPS_COLLECT_LIST", msg);
                                   }
                               }
                )
        );
    }

    /**
     * 获取商品收藏列表
     * @param
     */
    protected void getProductCollectList(int curpage){
        addDisposable(apiService.getProductCollectList(userBean.getDatas().getKey(), curpage, 10)
                .compose(RxSchedulers.<BasePageBean>io_main())
                .subscribeWith(new RxSubscriber<BasePageBean>() {
                                   @Override
                                   public void onSuccess(BasePageBean result) {
                                       sendData("PRODUCT_COLLECT_LIST", result);
                                   }

                                   @Override
                                   public void onFailure(String msg) {
                                       KLog.i(msg);
                                       sendData("Err_PRODUCT_COLLECT_LIST", msg);
                                   }
                               }
                )
        );
    }

}
