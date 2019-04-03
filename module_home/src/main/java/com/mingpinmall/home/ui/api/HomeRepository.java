package com.mingpinmall.home.ui.api;

import com.goldze.common.dmvvm.base.mvvm.base.BaseRepository;
import com.goldze.common.dmvvm.http.RetrofitClient;
import com.goldze.common.dmvvm.http.rx.RxSchedulers;
import com.goldze.common.dmvvm.http.rx.RxSubscriber;
import com.mingpinmall.home.ui.bean.HomeItemBean;
import com.socks.library.KLog;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/3
 **/
public class HomeRepository extends BaseRepository {
    private HomeApiService apiService = RetrofitClient.getInstance().create(HomeApiService.class);

    /**
     * @date 创建时间： 2019/4/3
     * @author 创建人：小斌
     * @Description 描述：获取首页数据
     * @Version
     **/
    protected void getHomeDataList() {
        addDisposable(apiService.getHomeDataList()
                .compose(RxSchedulers.<HomeItemBean>io_main())
                .subscribeWith(new RxSubscriber<HomeItemBean>() {
                                   @Override
                                   public void onSuccess(HomeItemBean data) {
                                       KLog.i(data);
                                       sendData("HOME_DATA_JSON", data);
                                   }

                                   @Override
                                   public void onError(Throwable e) {
                                       super.onError(e);
                                       KLog.i(e.toString());
                                   }

                                   @Override
                                   public void onFailure(String msg) {
                                       KLog.i(msg);
                                       sendData("Err_HOME_DATA_JSON", msg);
                                   }
                               }
                )
        );
    }

}
