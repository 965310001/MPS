package com.mingpinmall.home.ui.api;

import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.base.BaseRepository;
import com.goldze.common.dmvvm.http.RetrofitClient;
import com.goldze.common.dmvvm.http.rx.RxSchedulers;
import com.goldze.common.dmvvm.http.rx.RxSubscriber;
import com.mingpinmall.home.ui.bean.HomeItemBean;
import com.mingpinmall.home.ui.bean.ShopClassBean;
import com.mingpinmall.home.ui.bean.ShopStreetBean;
import com.mingpinmall.home.ui.bean.SpecialPageBean;
import com.mingpinmall.home.ui.constants.Constants;
import com.socks.library.KLog;

import retrofit2.http.Query;

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
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<ShopClassBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<ShopClassBean> homeItemBean) {
                        if (homeItemBean.isSuccess())
                            sendData(Constants.GET_STORE_CLASS, homeItemBean.getData());
                        else
                            sendData(Constants.GET_STORE_CLASS, homeItemBean.getMessage());
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.GET_STORE_CLASS, msg == null ? "获取失败" : msg);
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
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<ShopStreetBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<ShopStreetBean> homeItemBean) {
                        if (homeItemBean.isSuccess())
                            sendData(Constants.GET_STORE_LIST, homeItemBean);
                        else
                            sendData(Constants.GET_STORE_LIST, homeItemBean.getMessage());
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.GET_STORE_LIST, msg == null ? "获取失败" : msg);
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
                        sendData(Constants.HOME_DATA_JSON, data);
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.HOME_DATA_JSON, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*获取专题页面*/
    protected void getSpecialList(String specialId) {
        addDisposable(apiService.getSpecialList(specialId)
                .compose(RxSchedulers.<BaseResponse<SpecialPageBean>>io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<SpecialPageBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<SpecialPageBean> data) {
                        if (data.isSuccess())
                            sendData(Constants.SPECIAL_LIST, data.getData());
                        else
                            sendData(Constants.SPECIAL_LIST, data.getMessage());
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.SPECIAL_LIST, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

}
