package com.mingpinmall.me.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：退款详情
 * 创建人：小斌
 * 创建时间: 2019/4/23
 **/
public class RefundInformation implements Serializable {

        private RefundBean refund;
        private DetailArrayBean detail_array;
        private List<?> pic_list;

        public RefundBean getRefund() {
            return refund;
        }

        public void setRefund(RefundBean refund) {
            this.refund = refund;
        }

        public DetailArrayBean getDetail_array() {
            return detail_array;
        }

        public void setDetail_array(DetailArrayBean detail_array) {
            this.detail_array = detail_array;
        }

        public List<?> getPic_list() {
            return pic_list;
        }

        public void setPic_list(List<?> pic_list) {
            this.pic_list = pic_list;
        }

        public static class RefundBean {

            private String refund_id;
            private String goods_id;
            private String goods_name;
            private String order_id;
            private String refund_amount;
            private String refund_sn;
            private String order_sn;
            private String add_time;
            private String goods_img_360;
            private String seller_state;
            private String admin_state;
            private String store_id;
            private String store_name;
            private String reason_info;
            private String buyer_message;
            private String seller_message;
            private String admin_message;

            public String getRefund_id() {
                return refund_id;
            }

            public void setRefund_id(String refund_id) {
                this.refund_id = refund_id;
            }

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

            public String getGoods_img_360() {
                return goods_img_360;
            }

            public void setGoods_img_360(String goods_img_360) {
                this.goods_img_360 = goods_img_360;
            }

            public String getSeller_state() {
                return seller_state;
            }

            public void setSeller_state(String seller_state) {
                this.seller_state = seller_state;
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

            public String getReason_info() {
                return reason_info;
            }

            public void setReason_info(String reason_info) {
                this.reason_info = reason_info;
            }

            public String getBuyer_message() {
                return buyer_message;
            }

            public void setBuyer_message(String buyer_message) {
                this.buyer_message = buyer_message;
            }

            public String getSeller_message() {
                return seller_message;
            }

            public void setSeller_message(String seller_message) {
                this.seller_message = seller_message;
            }

            public String getAdmin_message() {
                return admin_message;
            }

            public void setAdmin_message(String admin_message) {
                this.admin_message = admin_message;
            }
        }

        public static class DetailArrayBean {

            private String refund_code;
            private String pay_amount;
            private String pd_amount;
            private String rcb_amount;

            public String getRefund_code() {
                return refund_code;
            }

            public void setRefund_code(String refund_code) {
                this.refund_code = refund_code;
            }

            public String getPay_amount() {
                return pay_amount;
            }

            public void setPay_amount(String pay_amount) {
                this.pay_amount = pay_amount;
            }

            public String getPd_amount() {
                return pd_amount;
            }

            public void setPd_amount(String pd_amount) {
                this.pd_amount = pd_amount;
            }

            public String getRcb_amount() {
                return rcb_amount;
            }

            public void setRcb_amount(String rcb_amount) {
                this.rcb_amount = rcb_amount;
            }
        }
}
