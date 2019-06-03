package com.mingpinmall.classz.ui.api;

import android.app.Application;
import android.support.annotation.NonNull;

import com.goldze.common.dmvvm.base.mvvm.AbsViewModel;
import com.goldze.common.dmvvm.utils.log.QLog;


import java.util.HashMap;
import java.util.Map;

public class ClassifyViewModel extends AbsViewModel<ClassifyRepository> {

    public ClassifyViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 获取试戴图片内容
     * @param eventKey
     */
    public void getHoloImages(String goodsId, Object eventKey) {
        mRepository.getHoloImages(goodsId, eventKey);
    }

    /**
     * 获取筛选内容
     *
     * @param eventKey
     */
    public void getScreeningInfo(Object eventKey) {
        mRepository.getScreeningInfo(eventKey);
    }

    /**
     * 获取筛选内容
     *
     * @param eventKey
     * @param childId  ID
     */
    public void getScreeningClassInfo(Object eventKey, String childId) {
        mRepository.getScreeningClassInfo(eventKey, childId);
    }

    /*检查F码*/
    public void checkFCode(String fcode, String goods_id) {
        mRepository.checkFCode(fcode, goods_id);
    }

    /*商品分类*/
    public void getLeft() {
        mRepository.getLeft();
    }

    /*品牌列表*/
    public void getRightByBrand() {
        mRepository.getRightByBrand();
    }

    /*右边分类*/
    public void getRight(String gcId) {
        mRepository.getRight(gcId);
    }

    /*获得主分类ID*/
    public void getGcParentId(String gcId) {
        mRepository.getGcParentId(gcId);
    }

    /*商品列表*/
    public void getShappingList(Map<String, Object> params, long curpage) {
        mRepository.getShappingList(params, curpage);
    }

    /*商品详情*/
    public void getGoodsDetail(String goodsId) {
        mRepository.getGoodsDetail(goodsId);
    }

    /**
     * 添加到购物车
     *
     * @param goodsId  商品ID
     * @param num      数量
     * @param eventKey 有数据的Key
     */
    public void addCart(String goodsId, long num, Object eventKey) {
        Map<String, Object> map = new HashMap<>();
        map.put("goods_id", goodsId);
        map.put("quantity", num);
        map.put("_client", "android");
//        mRepository.execute(ClassifyService.ADD_CART_APP, ClassifyService.ADD_CART_WWI, eventKey, map);
        mRepository.execute("member_cart", "cart_add", eventKey, map);
    }

    /*添加，删除收藏*/
    // TODO: 2019/4/2 收藏
    public void favorites(String goodsId, boolean isLike, Object eventKey) {
        Map<String, Object> map = new HashMap<>();
        map.put(isLike ? "fav_id" : "goods_id", goodsId);
        map.put("_client", "android");
        QLog.i(isLike ? "取消收藏" : "添加收藏");
//        mRepository.execute(ClassifyService.ADDCOLLECTION_APP,
//                isLike ? ClassifyService.DELCOLLECTION_WWI : ClassifyService.ADDCOLLECTION_WWI, eventKey, map);
        mRepository.execute("member_favorites",
                isLike ? "favorites_del" : "favorites_add", eventKey, map);
    }

    /*评价列表*/
    public void getEvaluate(String gId, String type, String curpage) {
        mRepository.getEvaluate(gId, type, curpage);
    }

    /*获取订单信息*/
    public void getOrderInfo(String cartId, String addressId, String ifcart, Object eventKey) {
        mRepository.getOrderInfo(cartId, addressId, ifcart, eventKey);
    }

    /*获取订单信息*/
    public void getOrderInfo2(String cartId, String addressId, String ifcart, Object eventKey) {
        mRepository.getOrderInfo2(cartId, addressId, ifcart, eventKey);
    }

    /*搜索列表*/
    public void getHotKeys() {
        mRepository.getHotKeys();
    }

    /*获取发票内容*/
    public void getInvoiceContentList(final Object eventKey) {
        mRepository.getInvoiceContentList(eventKey);
    }

    /*获取发票列表*/
    public void getInvoiceList(final Object eventKey) {
        mRepository.getInvoiceList(eventKey);
    }

    /*确定发票内容*/
    public void addInvoice(Map<String, Object> map, final Object eventKey) {
        mRepository.addInvoice(map, eventKey);
    }

    /*删除发票列表*/
    public void invoiceDel(final Object eventKey, String invId) {
        mRepository.invoiceDel(eventKey, invId);
    }

    /*店铺介绍*/
    public void getStoreIntro(String storeId, Object eventKey) {
        mRepository.getStoreIntro(storeId, eventKey);
    }

    /*店铺列表*/
    public void getStoreShappingList(String bId, String curpage, String keyword, String typeId,
                                     String areaId, String priceFrom, String priceTo,
                                     String key, String order, String ci, String st) {
        mRepository.getStoreShappingList(bId, curpage, keyword, typeId, areaId, priceFrom, priceTo, key, order, ci, st);
    }

    /*提交订单*/
    public void getBuyStep2(Map<String, Object> map, Object eventKey) {
        mRepository.getBuyStep2(map, eventKey);
    }

    /************************************* 店铺 ******************************/
    /*店铺信息*/
    public void getStoreInfo(String storeId, String key, Object eventKey) {
        mRepository.getStoreInfo(storeId, key, eventKey);
    }

    public void getStoreGoods(Map<String, Object> map, final Object eventKey) {
        mRepository.getStoreGoods(map, eventKey);
    }

    public void getStoreGoods(String storeId, long page, String keyword,
                              String areaId, String priceFrom, String priceTo,
                              String order, String ci, String st, Object eventKey) {
        mRepository.getStoreGoods(storeId, page, keyword, areaId, priceFrom, priceTo, order, ci, st, eventKey);
    }

    /*商品上新*/
    public void getStoreNewGoods(String storeId, int page, Object eventKey) {
        mRepository.getStoreNewGoods(storeId, page, eventKey);
    }

    /*活动店铺*/
    public void getStorePromotion(String storeId, int page, Object eventKey) {
        mRepository.getStorePromotion(storeId, page, eventKey);
    }

    /*店铺收藏 取消*/
    public void getStoreFavorites(String storeId, boolean isDel, final Object eventKey) {
        mRepository.getStoreFavorites(storeId, isDel, eventKey);
    }

    /*店铺代金券*/
    public void getVoucherTplList(String storeId, final Object eventKey) {
        mRepository.getVoucherTplList(storeId, "free", eventKey);
    }

    /*领取代金券*/
    public void getVoucherFreeex(String storeId, final Object eventKey) {
        mRepository.getVoucherFreeex(storeId, eventKey);
    }

    /*聊天历史纪录*/
    public void getChatLog(String tId, String t, final Object eventKey) {
        mRepository.getChatLog(tId, t, eventKey);
    }

    /*获取聊天列表*/
    public void getNodeInfo(String goodsId, String tId, Object eventKey) {
        mRepository.getNodeInfo(goodsId, tId, eventKey);
    }

    /*发送聊天内容*/
    public void sendMsg(String goodsId, String fId, String tId, String tName, String msg, Object eventKey) {
        mRepository.sendMsg(goodsId, fId, tId, tName, msg, eventKey);
    }

    /*发送图片*/
    public void picUpload(String goodsId, String fId, String tId, String tName, String image, Object eventKey) {
        mRepository.picUpload(goodsId, fId, tId, tName, image, eventKey);
    }

    public void getPayNew(String paySn, String paymentCode, String pdPay, Object eventKey) {
        mRepository.getPayNew(paySn, paymentCode, pdPay, eventKey);
    }

    /*获取支付信息*/
    public void getPayInfoNew(String paySn, String rcb_pay, String pd_pay, String password, String payment_code, Object eventKey) {
        mRepository.getPayInfoNew(paySn, rcb_pay, pd_pay, password, payment_code, eventKey);
    }

    /*获取购物车数量*/
    public void getMemberCart(Object eventKey) {
        mRepository.getMemberCart(eventKey);
    }

    /************************************* end ******************************/

    //    public void picUpload(String goodsId, String fId, String tId, String tName, File file, Object eventKey) {
//        mRepository.picUpload(goodsId, fId, tId, tName, file, eventKey);
//    }
//    /*阿里支付*/
//    public void getAli(Map<String, Object> map, Object eventKey) {
//        mRepository.getAli(map, eventKey);
//    }
//
//    /*微信支付*/
//    public void getWeiXin(Map<String, Object> map, Object eventKey) {
//        mRepository.getWeiXin(map, eventKey);
//    }
//     /*收藏排行*/
//    public void getStoreGoodsRank(String storeId, String orderType, String num, Object eventKey) {
//        mRepository.getStoreGoodsRank(storeId, orderType, num, eventKey);
//    }
//
//    /*全部商品*/
//    public void getStoreGoods(String storeId, long page, Object eventKey) {
//        mRepository.getStoreGoods(storeId, page, eventKey);
//    }
}
