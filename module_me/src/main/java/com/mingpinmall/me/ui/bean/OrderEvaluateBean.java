package com.mingpinmall.me.ui.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：订单可评价商品
 * *@author 小斌
 * @date 2019/4/30
 **/
public class OrderEvaluateBean implements Serializable {

    private StoreInfoBean store_info;
    private List<OrderGoodsBean> order_goods;

    public StoreInfoBean getStore_info() {
        return store_info;
    }

    public void setStore_info(StoreInfoBean store_info) {
        this.store_info = store_info;
    }

    public List<OrderGoodsBean> getOrder_goods() {
        return order_goods;
    }

    public void setOrder_goods(List<OrderGoodsBean> order_goods) {
        this.order_goods = order_goods;
    }

    public static class StoreInfoBean {

        private String store_id;
        private String store_name;
        private String is_own_mall;

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

        public String getIs_own_mall() {
            return is_own_mall == null ? "" : is_own_mall;
        }

        public void setIs_own_mall(String is_own_mall) {
            this.is_own_mall = is_own_mall == null ? "" : is_own_mall;
        }
    }

    public static class OrderGoodsBean {

        private String score;

        public String getScore() {
            return score == null ? "" : score;
        }

        public void setScore(String score) {
            this.score = score == null ? "" : score;
        }

        private String rec_id;
        private String order_id;
        private String goods_id;
        private String goods_name;
        private String goods_price;
        private String goods_num;
        private String goods_image;
        private String goods_pay_price;
        private String store_id;
        private String buyer_id;
        private String goods_type;
        private String promotions_id;
        private String commis_rate;
        private String gc_id;
        private String goods_spec;
        private String goods_contractid;
        private String invite_rates;
        private String goods_image_url;

        public String getRec_id() {
            return rec_id == null ? "" : rec_id;
        }

        public void setRec_id(String rec_id) {
            this.rec_id = rec_id == null ? "" : rec_id;
        }

        public String getOrder_id() {
            return order_id == null ? "" : order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id == null ? "" : order_id;
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

        public String getGoods_pay_price() {
            return goods_pay_price == null ? "" : goods_pay_price;
        }

        public void setGoods_pay_price(String goods_pay_price) {
            this.goods_pay_price = goods_pay_price == null ? "" : goods_pay_price;
        }

        public String getStore_id() {
            return store_id == null ? "" : store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id == null ? "" : store_id;
        }

        public String getBuyer_id() {
            return buyer_id == null ? "" : buyer_id;
        }

        public void setBuyer_id(String buyer_id) {
            this.buyer_id = buyer_id == null ? "" : buyer_id;
        }

        public String getGoods_type() {
            return goods_type == null ? "" : goods_type;
        }

        public void setGoods_type(String goods_type) {
            this.goods_type = goods_type == null ? "" : goods_type;
        }

        public String getPromotions_id() {
            return promotions_id == null ? "" : promotions_id;
        }

        public void setPromotions_id(String promotions_id) {
            this.promotions_id = promotions_id == null ? "" : promotions_id;
        }

        public String getCommis_rate() {
            return commis_rate == null ? "" : commis_rate;
        }

        public void setCommis_rate(String commis_rate) {
            this.commis_rate = commis_rate == null ? "" : commis_rate;
        }

        public String getGc_id() {
            return gc_id == null ? "" : gc_id;
        }

        public void setGc_id(String gc_id) {
            this.gc_id = gc_id == null ? "" : gc_id;
        }

        public String getGoods_spec() {
            return goods_spec == null ? "" : goods_spec;
        }

        public void setGoods_spec(String goods_spec) {
            this.goods_spec = goods_spec == null ? "" : goods_spec;
        }

        public String getGoods_contractid() {
            return goods_contractid == null ? "" : goods_contractid;
        }

        public void setGoods_contractid(String goods_contractid) {
            this.goods_contractid = goods_contractid == null ? "" : goods_contractid;
        }

        public String getInvite_rates() {
            return invite_rates == null ? "" : invite_rates;
        }

        public void setInvite_rates(String invite_rates) {
            this.invite_rates = invite_rates == null ? "" : invite_rates;
        }

        public String getGoods_image_url() {
            return goods_image_url == null ? "" : goods_image_url;
        }

        public void setGoods_image_url(String goods_image_url) {
            this.goods_image_url = goods_image_url == null ? "" : goods_image_url;
        }
    }
}
