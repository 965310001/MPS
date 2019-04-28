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

    /*消息列表*/
    public void delMsg(String id) {
        mRepository.delMsg(id);
    }

    /*消息列表*/
    public void getMsgList() {
        mRepository.getMsgList();
    }

    /*退货详情*/
    public void getReturnInformation(String returnId) {
        mRepository.getReturnInformation(returnId);
    }

    /*退款详情*/
    public void getRefundInformation(String refundId) {
        mRepository.getRefundInformation(refundId);
    }

    /*确认领取红包*/
    public void packetCharge(String pwd_code) {
        mRepository.packetCharge(pwd_code);
    }

    /*获取我的红包记录*/
    public void getPacketList(int curPage) {
        mRepository.getPacketList(curPage);
    }

    /*会员积分*/
    public void getVipPoint() {
        mRepository.getVipPoint();
    }

    /*会员积分历史记录*/
    public void getVipPointLog(int curPage) {
        mRepository.getVipPointLog(curPage);
    }

    /*代金券列表*/
    public void getCouponList(int curPage) {
        mRepository.getCouponList(curPage);
    }

    /*代金券确认领取*/
    public void cpCharge(String rc_sn) {
        mRepository.cpCharge(rc_sn);
    }

    /*充值卡确认充值*/
    public void rcCharge(String rc_sn) {
        mRepository.rcCharge(rc_sn);
    }

    /*充值卡使用记录*/
    public void getRCBLog(int curPage) {
        mRepository.getRCBLog(curPage);
    }

    /*账户余额*/
    public void getRCBalance() {
        mRepository.getRCBalance();
    }

    /*获取账户余额*/
    public void getPredepoit() {
        mRepository.getPredepoit();
    }

    /*获取账户余额列表*/
    public void getPredepoitLog(int curPage) {
        mRepository.getPredepoitLog(curPage);
    }

    /*获取账户充值明细*/
    public void getPdreChargeList(int curPage) {
        mRepository.getPdreChargeList(curPage);
    }

    /*获取账户充值明细*/
    public void getPdcashList(int curPage) {
        mRepository.getPdcashList(curPage);
    }

    /*获取账户提现详情*/
    public void getPdcashList(String pdcId) {
        mRepository.getPdcashList(pdcId);
    }

    /*获取退款列表*/
    public void getRefundList(int curPage) {
        mRepository.getRefundList(curPage);
    }

    /*获取退货列表*/
    public void getReturnList(int curPage) {
        mRepository.getReturnList(curPage);
    }

    /*获取虚拟订单详细内容 中的店铺地址*/
    public void getVitrualOrderStoreAddrs(String orderId) {
        mRepository.getVitrualOrderStoreAddrs(orderId);
    }

    /*获取虚拟订单详细内容*/
    public void getVitrualOrderInformation(String orderId) {
        mRepository.getVitrualOrderInformation(orderId);
    }

    /*取消虚拟订单*/
    public void cancelVirtualOrder(String eventKey, String orderId) {
        mRepository.cancelVirtualOrder(eventKey, orderId);
    }

    /*取消订单*/
    public void cancelOrder(String eventKey, String orderId) {
        mRepository.cancelOrder(eventKey, orderId);
    }

    /*确认收货*/
    public void recevieOrder(final String eventKey, String orderId) {
        mRepository.recevieOrder(eventKey, orderId);
    }

    /*删除订单*/
    public void removeOrder(final String eventKey, String orderId) {
        mRepository.removeOrder(eventKey, orderId);
    }

    /*分销管理*/
    public void getInviteList(String wwi, int curpage){
        mRepository.getInviteList(wwi, curpage);
    }

    /*获取收货地址列表*/
    public void getAddressList() {
        mRepository.getAddressList();
    }
    /*删除收货地址*/
    public void delAddress(String addressId) {
        mRepository.delAddress(addressId);
    }

    /*新增收货地址*/
    public void addAddress(int id_default, String name, String city_id, String area_id, String area_info,
                           String address, String phone) {
        mRepository.addAddress(id_default, name, city_id, area_id, area_info, address, phone);
    }

    /*编辑收货地址*/
    public void editAddress(String address_id, int id_default, String name, String city_id, String area_id, String area_info,
                            String address, String phone) {
        mRepository.editAddress(address_id, id_default, name, city_id, area_id, area_info, address, phone);
    }

    /*获取城市列表*/
    public void getCityList(String areaId) {
        mRepository.getCityList(areaId);
    }

    /*获取用户信息*/
    public void getUserInfo() {
        mRepository.getUserInfo();
    }

    /*清空我的足迹*/
    public void clearnMyFootprint() {
        mRepository.clearnMyFootprint();
    }

    /*获取我的足迹*/
    public void getMyFootprint(int curPage) {
        mRepository.getMyFootprint(curPage);
    }

    /*获取我的财产*/
    public void getProperty() {
        mRepository.getProperty();
    }

    /*获取支付密码状态，是否设置*/
    public void getPayPwdInfo() {
        mRepository.getPayPwdInfo();
    }

    /*获取手机号是否验证状态*/
    public void getPhoneInfo() {
        mRepository.getPhoneInfo();
    }

    /*发送用户反馈*/
    public void sendFeedBack(String feedback) {
        mRepository.sendFeedBack(feedback);
    }

    /*获取店铺收藏列表*/
    public void getShopsCollectList(int curpage) {
        mRepository.getShopsCollectList(curpage);
    }

    /*店铺删除收藏动作*/
    public void deleShopsCollect(String storeId) {
        mRepository.deleShopsCollect(storeId);
    }

    /*商品删除收藏动作*/
    public void deleGoodsCollect(String favId) {
        mRepository.deleGoodsCollect(favId);
    }

    /*获取商品收藏列表*/
    public void getProductCollectList(int curpage) {
        mRepository.getProductCollectList(curpage);
    }

    /*获取订单列表*/
    public void getPhysicalOrderList(String event_key, String state_type, String order_key, int curpage) {
        mRepository.getPhysicalOrderList(event_key, state_type, order_key, 10, curpage);
    }

    /*获取订单详情*/
    public void getOrderInformation(String order_id) {
        mRepository.getOrderInformation(order_id);
    }

    /*获取订单最新物流信息*/
    public void getOrderDeliverInformation(String order_id) {
        mRepository.getOrderDeliverInformation(order_id);
    }

    /*获取订单物流详情*/
    public void getOrderDeliverList(String order_id) {
        mRepository.getOrderDeliverList(order_id);
    }

    /*获取订单列表*/
    public void getVirtualOrderList(String event_key, String state_type, String order_key, int curpage) {
        mRepository.getVirtualOrderList(event_key, state_type, order_key, 10, curpage);
    }

    /*获取收货地址详细内容*/
    public void getAddress(String addressId) {
        mRepository.getAddress(addressId);
    }
}
