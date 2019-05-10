package com.mingpinmall.cart.ui.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * 功能描述：处理过后的购物车
 * *@author 小斌
 * @date 2019/4/24
 **/
public class AvailableCartBean implements MultiItemEntity {

    private int itemType = 0;
    private boolean isCheck = false;
    private int childCount = 0;
    private int checkedCount = 0;
    private boolean isExpanded = false;//满即送列表是否展开

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public int getCheckedCount() {
        return checkedCount;
    }

    public void setCheckedCount(int checkedCount) {
        this.checkedCount = checkedCount;
    }

    public void changeCheckedCount(boolean check) {
        if (check) {
            this.checkedCount++;
        } else {
            this.checkedCount--;
        }
    }

    public boolean isCheck() {
        if (getItemType() == 0) {
            return checkedCount == childCount;
        }
        return isCheck;
    }

    public void setCheck(boolean check) {
        if (getItemType() == 0) {
            setCheckedCount(check ? getChildCount() : 0);
        }
        isCheck = check;
    }

    public void setItemType(int itemType) {
        if (itemType == 1) {
            setChildCount(0);
            setCheckedCount(0);
        }
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    private String store_id;
    private String store_name;
    private String free_freight;
    private List<ShopCartBean.CartListBean.VoucherBean> voucher;
    private List<ShopCartBean.CartListBean.MansongBean> mansong;

    public String getFree_freight() {
        return free_freight == null ? "" : free_freight;
    }

    public void setFree_freight(String free_freight) {
        this.free_freight = free_freight == null ? "" : free_freight;
    }

    public List<ShopCartBean.CartListBean.VoucherBean> getVoucher() {
        return voucher;
    }

    public void setVoucher(List<ShopCartBean.CartListBean.VoucherBean> voucher) {
        this.voucher = voucher;
    }

    public List<ShopCartBean.CartListBean.MansongBean> getMansong() {
        return mansong;
    }

    public void setMansong(List<ShopCartBean.CartListBean.MansongBean> mansong) {
        this.mansong = mansong;
    }

    private ShopCartBean.CartListBean.GoodsBean goods;

    public ShopCartBean.CartListBean.GoodsBean getGoods() {
        return goods;
    }

    public void setGoods(ShopCartBean.CartListBean.GoodsBean goods) {
        this.goods = goods;
    }

    public String getStore_id() {
        return store_id == null ? "" : store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id == null ? "" : store_id;
    }

    public String getStore_name() {
        return store_name == null ? "" : store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name == null ? "" : store_name;
    }

    public static class GoodsBean {

        private String cart_id;
        private String buyer_id;
        private String store_id;
        private String store_name;
        private String goods_id;
        private String goods_name;
        private String goods_price;
        private String goods_num;
        private String goods_image;
        private String bl_id;
        private boolean state;
        private boolean storage_state;
        private String goods_commonid;
        private String gc_id;
        private String transport_id;
        private String goods_freight;
        private String goods_vat;
        private String goods_storage;
        private String goods_storage_alarm;
        private String is_fcode;
        private String have_gift;
        private String is_book;
        private String book_down_payment;
        private String book_final_payment;
        private String book_down_time;
        private String is_chain;
        private String goods_spec;
        private String goods_image_url;
        private String goods_total;
        private List<?> groupbuy_info;
        private List<?> xianshi_info;
        private List<?> sole_info;
        private List<?> contractlist;

        public String getCart_id() {
            return cart_id == null ? "" : cart_id;
        }

        public void setCart_id(String cart_id) {
            this.cart_id = cart_id == null ? "" : cart_id;
        }

        public String getBuyer_id() {
            return buyer_id == null ? "" : buyer_id;
        }

        public void setBuyer_id(String buyer_id) {
            this.buyer_id = buyer_id == null ? "" : buyer_id;
        }

        public String getStore_id() {
            return store_id == null ? "" : store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id == null ? "" : store_id;
        }

        public String getStore_name() {
            return store_name == null ? "" : store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name == null ? "" : store_name;
        }

        public String getGoods_id() {
            return goods_id == null ? "" : goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id == null ? "" : goods_id;
        }

        public String getGoods_name() {
            return goods_name == null ? "" : goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name == null ? "" : goods_name;
        }

        public String getGoods_price() {
            return goods_price == null ? "" : goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price == null ? "" : goods_price;
        }

        public String getGoods_num() {
            return goods_num == null ? "" : goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num == null ? "" : goods_num;
        }

        public String getGoods_image() {
            return goods_image == null ? "" : goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image == null ? "" : goods_image;
        }

        public String getBl_id() {
            return bl_id == null ? "" : bl_id;
        }

        public void setBl_id(String bl_id) {
            this.bl_id = bl_id == null ? "" : bl_id;
        }

        public boolean isState() {
            return state;
        }

        public void setState(boolean state) {
            this.state = state;
        }

        public boolean isStorage_state() {
            return storage_state;
        }

        public void setStorage_state(boolean storage_state) {
            this.storage_state = storage_state;
        }

        public String getGoods_commonid() {
            return goods_commonid == null ? "" : goods_commonid;
        }

        public void setGoods_commonid(String goods_commonid) {
            this.goods_commonid = goods_commonid == null ? "" : goods_commonid;
        }

        public String getGc_id() {
            return gc_id == null ? "" : gc_id;
        }

        public void setGc_id(String gc_id) {
            this.gc_id = gc_id == null ? "" : gc_id;
        }

        public String getTransport_id() {
            return transport_id == null ? "" : transport_id;
        }

        public void setTransport_id(String transport_id) {
            this.transport_id = transport_id == null ? "" : transport_id;
        }

        public String getGoods_freight() {
            return goods_freight == null ? "" : goods_freight;
        }

        public void setGoods_freight(String goods_freight) {
            this.goods_freight = goods_freight == null ? "" : goods_freight;
        }

        public String getGoods_vat() {
            return goods_vat == null ? "" : goods_vat;
        }

        public void setGoods_vat(String goods_vat) {
            this.goods_vat = goods_vat == null ? "" : goods_vat;
        }

        public String getGoods_storage() {
            return goods_storage == null ? "" : goods_storage;
        }

        public void setGoods_storage(String goods_storage) {
            this.goods_storage = goods_storage == null ? "" : goods_storage;
        }

        public String getGoods_storage_alarm() {
            return goods_storage_alarm == null ? "" : goods_storage_alarm;
        }

        public void setGoods_storage_alarm(String goods_storage_alarm) {
            this.goods_storage_alarm = goods_storage_alarm == null ? "" : goods_storage_alarm;
        }

        public String getIs_fcode() {
            return is_fcode == null ? "" : is_fcode;
        }

        public void setIs_fcode(String is_fcode) {
            this.is_fcode = is_fcode == null ? "" : is_fcode;
        }

        public String getHave_gift() {
            return have_gift == null ? "" : have_gift;
        }

        public void setHave_gift(String have_gift) {
            this.have_gift = have_gift == null ? "" : have_gift;
        }

        public String getIs_book() {
            return is_book == null ? "" : is_book;
        }

        public void setIs_book(String is_book) {
            this.is_book = is_book == null ? "" : is_book;
        }

        public String getBook_down_payment() {
            return book_down_payment == null ? "" : book_down_payment;
        }

        public void setBook_down_payment(String book_down_payment) {
            this.book_down_payment = book_down_payment == null ? "" : book_down_payment;
        }

        public String getBook_final_payment() {
            return book_final_payment == null ? "" : book_final_payment;
        }

        public void setBook_final_payment(String book_final_payment) {
            this.book_final_payment = book_final_payment == null ? "" : book_final_payment;
        }

        public String getBook_down_time() {
            return book_down_time == null ? "" : book_down_time;
        }

        public void setBook_down_time(String book_down_time) {
            this.book_down_time = book_down_time == null ? "" : book_down_time;
        }

        public String getIs_chain() {
            return is_chain == null ? "" : is_chain;
        }

        public void setIs_chain(String is_chain) {
            this.is_chain = is_chain == null ? "" : is_chain;
        }

        public String getGoods_spec() {
            return goods_spec == null ? "" : goods_spec;
        }

        public void setGoods_spec(String goods_spec) {
            this.goods_spec = goods_spec == null ? "" : goods_spec;
        }

        public String getGoods_image_url() {
            return goods_image_url == null ? "" : goods_image_url;
        }

        public void setGoods_image_url(String goods_image_url) {
            this.goods_image_url = goods_image_url == null ? "" : goods_image_url;
        }

        public String getGoods_total() {
            return goods_total == null ? "" : goods_total;
        }

        public void setGoods_total(String goods_total) {
            this.goods_total = goods_total == null ? "" : goods_total;
        }

        public List<?> getGroupbuy_info() {
            return groupbuy_info;
        }

        public void setGroupbuy_info(List<?> groupbuy_info) {
            this.groupbuy_info = groupbuy_info;
        }

        public List<?> getXianshi_info() {
            return xianshi_info;
        }

        public void setXianshi_info(List<?> xianshi_info) {
            this.xianshi_info = xianshi_info;
        }

        public List<?> getSole_info() {
            return sole_info;
        }

        public void setSole_info(List<?> sole_info) {
            this.sole_info = sole_info;
        }

        public List<?> getContractlist() {
            return contractlist;
        }

        public void setContractlist(List<?> contractlist) {
            this.contractlist = contractlist;
        }
    }
}
