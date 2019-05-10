package com.mingpinmall.home.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：专题页面
 * *@author 小斌
 * @date 2019/5/5
 **/
public class SpecialPageBean implements Serializable {

    private String item_id;
    private String special_id;
    private String item_type;
    private String item_usable;
    private String item_sort;
    private List<HomeItemBean.DatasBean> list;

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getSpecial_id() {
        return special_id;
    }

    public void setSpecial_id(String special_id) {
        this.special_id = special_id;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getItem_usable() {
        return item_usable;
    }

    public void setItem_usable(String item_usable) {
        this.item_usable = item_usable;
    }

    public String getItem_sort() {
        return item_sort;
    }

    public void setItem_sort(String item_sort) {
        this.item_sort = item_sort;
    }

    public List<HomeItemBean.DatasBean> getList() {
        return list;
    }

    public void setList(List<HomeItemBean.DatasBean> list) {
        this.list = list;
    }

    public static class ItemDataBean {

        private String title;
        private List<ItemBean> item;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ItemBean> getItem() {
            return item;
        }

        public void setItem(List<ItemBean> item) {
            this.item = item;
        }

        public static class ItemBean {

            private String goods_id;
            private String goods_name;
            private String goods_promotion_price;
            private String goods_image;

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

            public String getGoods_image() {
                return goods_image;
            }

            public void setGoods_image(String goods_image) {
                this.goods_image = goods_image;
            }
        }
    }
}
