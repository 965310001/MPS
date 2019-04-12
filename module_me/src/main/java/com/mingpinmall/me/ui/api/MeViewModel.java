package com.mingpinmall.me.ui.api;

import android.app.Application;
import android.support.annotation.NonNull;

import com.goldze.common.dmvvm.base.mvvm.AbsViewModel;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/1
 **/
public class MeViewModel extends AbsViewModel<MeRepository> {

    public MeViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 获取收货地址列表
     */
    public void getAddressList() {
        mRepository.getAddressList();
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
    public void addAddress(int id_default, String name, String city_id, String area_id, String area_info,
                           String address, String phone) {
        mRepository.addAddress(id_default, name, city_id, area_id, area_info, address, phone);
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
    public void editAddress(int address_id, int id_default, String name, String city_id, String area_id, String area_info,
                            String address, String phone) {
        mRepository.editAddress(address_id, id_default, name, city_id, area_id, area_info, address, phone);
    }

    /**
     * 获取城市列表
     */
    public void getCityList(String areaId) {
        mRepository.getCityList(areaId);
    }

    public void getUserInfo() {
        mRepository.getUserInfo();
    }

    /**
     * 清空我的足迹
     */
    public void clearnMyFootprint() {
        mRepository.clearnMyFootprint();
    }

    /**
     * 获取我的足迹
     */
    public void getMyFootprint(int curPage) {
        mRepository.getMyFootprint(curPage);
    }

    /**
     * 获取我的财产
     */
    public void getProperty() {
        mRepository.getProperty();
    }

    /**
     * 获取支付密码状态，是否设置
     */
    public void getPayPwdInfo() {
        mRepository.getPayPwdInfo();
    }

    /**
     * 获取手机号是否验证状态
     */
    public void getPhoneInfo() {
        mRepository.getPhoneInfo();
    }

    /**
     * 发送用户反馈
     *
     * @param feedback
     */
    public void sendFeedBack(String feedback) {
        mRepository.sendFeedBack(feedback);
    }

    /**
     * 获取店铺收藏列表
     *
     * @param
     */
    public void getShopsCollectList(int curpage) {
        mRepository.getShopsCollectList(curpage);
    }

    /**
     * 获取商品收藏列表
     *
     * @param
     */
    public void getProductCollectList(int curpage) {
        mRepository.getProductCollectList(curpage);
    }

    /**
     * 获取订单列表
     *
     * @param
     */
    public void getOrderList(String event_key, String state_type, String order_key, int curpage) {
        mRepository.getOrderList(event_key, state_type, order_key, 10, curpage);
    }
}
