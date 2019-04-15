package com.mingpinmall.classz.ui.api;

import android.app.Application;
import android.support.annotation.NonNull;

import com.goldze.common.dmvvm.base.mvvm.AbsViewModel;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

public class ClassifyViewModel extends AbsViewModel<ClassifyRepository> {

    public ClassifyViewModel(@NonNull Application application) {
        super(application);
    }

    /*商品分类*/
    public void getLeft() {
        mRepository.getLeft();
    }

    /*右边分类*/
    public void getRight(String gcId) {
        mRepository.getRight(gcId);
    }

    /*品牌列表*/
    public void getRightByBrand() {
        mRepository.getRightByBrand();
    }

    /*商品列表*/
    public void getShappingList(String bId, String curpage, String keyword, String typeId,
                                String areaId, String priceFrom, String priceTo,
                                String key, String order, String ci, String st) {
        mRepository.getShappingList(bId, curpage, keyword, typeId, areaId, priceFrom, priceTo, key, order, ci, st);
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
        mRepository.execute(ClassifyService.ADD_CART_APP, ClassifyService.ADD_CART_WWI, eventKey, map);
    }

    /*添加，删除收藏*/
    // TODO: 2019/4/2 收藏
    public void favorites(String goodsId, boolean isLike, Object eventKey) {
        Map<String, Object> map = new HashMap<>();
        map.put(isLike ? "fav_id" : "goods_id", goodsId);
        map.put("_client", "android");
        KLog.i(isLike ? "取消收藏" : "添加收藏");
        mRepository.execute(ClassifyService.ADDCOLLECTION_APP,
                isLike ? ClassifyService.DELCOLLECTION_WWI : ClassifyService.ADDCOLLECTION_WWI, eventKey, map);
    }

    /*评价列表*/
    public void getEvaluate(String gId, String type, String curpage) {
        mRepository.getEvaluate(gId, type, curpage);
    }

    /*获取订单信息*/
    public void getOrderInfo(String cartId, String addressId, String ifcart, Object eventKey) {
        mRepository.getOrderInfo(cartId, addressId, ifcart, eventKey);
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
    public void addInvoice(final Object eventKey, Map<String, Object> map) {
        mRepository.addInvoice(eventKey, map);
    }

    /*删除发票列表*/
    public void invoiceDel(final Object eventKey, String invId) {
        mRepository.invoiceDel(eventKey, invId);
    }

    /************************************* 店铺 ******************************/
    /*店铺信息*/
    public void getStoreInfo(String storeId, String key, Object eventKey) {
        mRepository.getStoreInfo(storeId, key, eventKey);
    }

    /*收藏排行*/
    public void getStoreGoodsRank(String storeId, String orderType, String num, Object eventKey) {
        mRepository.getStoreGoodsRank(storeId, orderType, num, eventKey);
    }
    /************************************* end ******************************/


}
