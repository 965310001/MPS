package com.mingpinmall.me.ui.api;

import com.goldze.common.dmvvm.base.mvvm.base.BaseRepository;
import com.goldze.common.dmvvm.http.RetrofitClient;
import com.goldze.common.dmvvm.http.rx.RxSchedulers;
import com.goldze.common.dmvvm.http.rx.RxSubscriber;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
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

    /**
     * 我的商城页面 获取用户信息
     **/
    protected void getUserInfo() {
        UserBean userBean = (UserBean) SharePreferenceUtil.getUser(UserBean.class);
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

}
