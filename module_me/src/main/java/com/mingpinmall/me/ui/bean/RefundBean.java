package com.mingpinmall.me.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：退款列表
 * *@author 小斌
 * @date 2019/4/22
 **/
public class RefundBean implements Serializable {

    private List<RefundListBean> refund_list;

    public List<RefundListBean> getRefund_list() {
        return refund_list;
    }

    public void setRefund_list(List<RefundListBean> refund_list) {
        this.refund_list = refund_list;
    }

    public static class RefundListBean {

        private String refund_id;
        private String order_id;
        private String refund_amount;
        private String refund_sn;
        private String order_sn;
        private String add_time;
        private String seller_state_v;
        private String seller_state;
        private String admin_state_v;
        private String admin_state;
        private String store_id;
        private String store_name;
        private List<GoodsListBean> goods_list;

        public String getRefund_id() {
            return refund_id;
        }

        public void setRefund_id(String refund_id) {
            this.refund_id = refund_id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getRefund_amount() {
            return refund_amount;
        }

        public void setRefund_amount(String refund_amount) {
            this.refund_amount = refund_amount;
        }

        public String getRefund_sn() {
            return refund_sn;
        }

        public void setRefund_sn(String refund_sn) {
            this.refund_sn = refund_sn;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getSeller_state_v() {
            return seller_state_v;
        }

        public void setSeller_state_v(String seller_state_v) {
            this.seller_state_v = seller_state_v;
        }

        public String getSeller_state() {
            return seller_state;
        }

        public void setSeller_state(String seller_state) {
            this.seller_state = seller_state;
        }

        public String getAdmin_state_v() {
            return admin_state_v;
        }

        public void setAdmin_state_v(String admin_state_v) {
            this.admin_state_v = admin_state_v;
        }

        public String getAdmin_state() {
            return admin_state;
        }

        public void setAdmin_state(String admin_state) {
            this.admin_state = admin_state;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public List<GoodsListBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsListBean> goods_list) {
            this.goods_list = goods_list;
        }

        public static class GoodsListBean {

            private String goods_id;
            private String goods_name;
            private Object goods_spec;
            private String goods_img_360;

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

            public Object getGoods_spec() {
                return goods_spec;
            }

            public void setGoods_spec(Object goods_spec) {
                this.goods_spec = goods_spec;
            }

            public String getGoods_img_360() {
                return goods_img_360;
            }

            public void setGoods_img_360(String goods_img_360) {
                this.goods_img_360 = goods_img_360;
            }
        }
    }
}
