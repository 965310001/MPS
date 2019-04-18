package com.mingpinmall.home.ui.api;

import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.base.BaseRepository;
import com.goldze.common.dmvvm.http.RetrofitClient;
import com.goldze.common.dmvvm.http.rx.RxSchedulers;
import com.goldze.common.dmvvm.http.rx.RxSubscriber;
import com.mingpinmall.home.ui.bean.HomeItemBean;
import com.mingpinmall.home.ui.bean.ShopClassBean;
import com.mingpinmall.home.ui.bean.ShopStreetBean;
import com.socks.library.KLog;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/3
 **/
public class HomeRepository extends BaseRepository {
    private HomeApiService apiService = RetrofitClient.getInstance().create(HomeApiService.class);

    /**
     * 获取店铺 分类列表
     */
    protected void getStoreClass() {
        addDisposable(apiService.getStoreClass(getUserKey())
                .compose(RxSchedulers.<BaseResponse<ShopClassBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<ShopClassBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<ShopClassBean> homeItemBean) {
                        if (homeItemBean.isSuccess())
                            sendData("GET_STORE_CLASS", "success", homeItemBean.getData());
                        else
                            sendData("GET_STORE_CLASS", "err", homeItemBean.getMessage());
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("GET_STORE_CLASS", "err", msg == null ? "获取失败" : msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                    }
                })
        );
    }

    /**
     * 获取店铺列表
     *
     * @param curPage
     */
    protected void getStoreStreet(String keyword, String area_info, String sc_id, int curPage) {
        addDisposable(apiService.getStoreStreet(keyword, area_info, sc_id, getUserKey(), 10, curPage)
                .compose(RxSchedulers.<BaseResponse<ShopStreetBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<ShopStreetBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<ShopStreetBean> homeItemBean) {
                        if (homeItemBean.isSuccess())
                            sendData("GET_STORE_LIST", "success", homeItemBean);
                        else
                            sendData("GET_STORE_LIST", "err", homeItemBean.getMessage());
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData("GET_STORE_LIST", "err", msg == null ? "获取失败" : msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                    }
                })
        );
    }

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
