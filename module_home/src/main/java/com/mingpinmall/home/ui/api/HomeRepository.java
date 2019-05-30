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

/**
 * 功能描述：
 *
 * @author 小斌
 * @date 2019/4/3
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
                                if (homeItemBean.isSuccess()) {
                                    sendData(Constants.GET_STORE_CLASS, homeItemBean.getData());
                                } else {
                                    sendData(Constants.GET_STORE_CLASS, homeItemBean.getMessage());
                                }
                            }

                            @Override
                            public void onFailure(String msg) {
                                sendData(Constants.GET_STORE_CLASS, msg == null ? "获取失败" : msg);
                            }

//                    @Override
//                    protected void onNoNetWork() {
//                    }
                        })
        );
    }

    /**
     * 获取店铺列表
     */
    protected void getStoreStreet(String keyword, String areaInfo, String scId, int curPage) {
        addDisposable(apiService.getStoreStreet(keyword, areaInfo, scId, getUserKey(), 10, curPage)
                        .compose(RxSchedulers.io_main())
                        .subscribeWith(new RxSubscriber<BaseResponse<ShopStreetBean>>() {
                            @Override
                            public void onSuccess(BaseResponse<ShopStreetBean> homeItemBean) {
                                if (homeItemBean.isSuccess()) {
                                    sendData(Constants.GET_STORE_LIST, homeItemBean);
                                } else {
                                    sendData(Constants.GET_STORE_LIST, homeItemBean.getMessage());
                                }
                            }

                            @Override
                            public void onFailure(String msg) {
                                sendData(Constants.GET_STORE_LIST, msg == null ? "获取失败" : msg);
                            }

//                    @Override
//                    protected void onNoNetWork() {
//                    }
                        })
        );
    }

    /**
     * 描述：获取首页数据
     **/
    protected void getHomeDataList() {
        addDisposable(apiService.getHomeDataList()
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<HomeItemBean>() {
                    @Override
                    public void onSuccess(HomeItemBean data) {
                        sendData(Constants.HOME_DATA_JSON, data);
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.HOME_DATA_JSON, msg == null ? "获取失败" : msg);
                    }

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        sendData(Constants.HOME_DATA_JSON, "");
                    }
                })
        );
    }

    /**
     * 获取专题页面
     */
    protected void getSpecialList(String specialId) {
        addDisposable(apiService.getSpecialList(specialId)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<SpecialPageBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<SpecialPageBean> data) {
                        if (data.isSuccess()) {
                            sendData(Constants.SPECIAL_LIST, specialId, data.getData());
                        } else {
                            sendData(Constants.SPECIAL_LIST, specialId, data.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(Constants.SPECIAL_LIST, specialId, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

}
