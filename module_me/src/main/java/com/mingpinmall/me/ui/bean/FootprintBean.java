package com.mingpinmall.me.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：
 * @author 小斌
 * @date 2019/4/11
 **/
public class FootprintBean implements Serializable {

    private List<GoodsbrowseListBean> goodsbrowse_list;

    public List<GoodsbrowseListBean> getGoodsbrowse_list() {
        return goodsbrowse_list;
    }

    public void setGoodsbrowse_list(List<GoodsbrowseListBean> goodsbrowse_list) {
        this.goodsbrowse_list = goodsbrowse_list;
    }

    public static class GoodsbrowseListBean {

        private String goods_id;
        private String goods_name;
        private String goods_promotion_price;
        private String goods_promotion_type;
        private String goods_marketprice;
        private String goods_image;
        private String store_id;
        private String gc_id;
        private String gc_id_1;
        private String gc_id_2;
        private String gc_id_3;
        private String browsetime;
        private String goods_image_url;
        private String browsetime_day;
        private String browsetime_text;

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_promotion_price() {
            return goods_promotion_price;
        }

        public void setGoods_promotion_price(String goods_promotion_price) {
            this.goods_promotion_price = goods_promotion_price;
        }

        public String getGoods_promotion_type() {
            return goods_promotion_type;
        }

        public void setGoods_promotion_type(String goods_promotion_type) {
            this.goods_promotion_type = goods_promotion_type;
        }

        public String getGoods_marketprice() {
            return goods_marketprice;
        }

        public void setGoods_marketprice(String goods_marketprice) {
            this.goods_marketprice = goods_marketprice;
        }

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getGc_id() {
            return gc_id;
        }

        public void setGc_id(String gc_id) {
            this.gc_id = gc_id;
        }

        public String getGc_id_1() {
            return gc_id_1;
        }

        public void setGc_id_1(String gc_id_1) {
            this.gc_id_1 = gc_id_1;
        }

        public String getGc_id_2() {
            return gc_id_2;
        }

        public void setGc_id_2(String gc_id_2) {
            this.gc_id_2 = gc_id_2;
        }

        public String getGc_id_3() {
            return gc_id_3;
        }

        public void setGc_id_3(String gc_id_3) {
            this.gc_id_3 = gc_id_3;
        }

        public String getBrowsetime() {
            return browsetime;
        }

        public void setBrowsetime(String browsetime) {
            this.browsetime = browsetime;
        }

        public String getGoods_image_url() {
            return goods_image_url;
        }

        public void setGoods_image_url(String goods_image_url) {
            this.goods_image_url = goods_image_url;
        }

        public String getBrowsetime_day() {
            return browsetime_day;
        }

        public void setBrowsetime_day(String browsetime_day) {
            this.browsetime_day = browsetime_day;
        }

        public String getBrowsetime_text() {
            return browsetime_text;
        }

        public void setBrowsetime_text(String browsetime_text) {
            this.browsetime_text = browsetime_text;
        }
    }
}
