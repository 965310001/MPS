package com.mingpinmall.me.ui.bean;

import java.util.List;

/**
 * 功能描述：
 * @author 小斌
 * @date 2019/4/8
 **/
public class ProductCollectionBean {


    private List<FavoritesListBean> favorites_list;

    public List<FavoritesListBean> getFavorites_list() {
        return favorites_list;
    }

    public void setFavorites_list(List<FavoritesListBean> favorites_list) {
        this.favorites_list = favorites_list;
    }

    public static class FavoritesListBean {

        private String goods_id;
        private String goods_name;
        private String goods_image;
        private String store_id;
        private String fav_id;
        private String goods_image_url;
        private String goods_price;

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

        public String getFav_id() {
            return fav_id;
        }

        public void setFav_id(String fav_id) {
            this.fav_id = fav_id;
        }

        public String getGoods_image_url() {
            return goods_image_url;
        }

        public void setGoods_image_url(String goods_image_url) {
            this.goods_image_url = goods_image_url;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }
    }
}
