package com.mingpinmall.me.ui.bean;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：退货详情
 * *@author 小斌
 *
 * @date 2019/4/23
 **/
public class ReturnInformationBean implements Serializable {

    private ReturnInfoBean return_info;
    private List<String> pic_list;
    private DetailArrayBean detail_array;

    public ReturnInfoBean getReturn_info() {
        return return_info;
    }

    public void setReturn_info(ReturnInfoBean return_info) {
        this.return_info = return_info;
    }

    public List<String> getPic_list() {
        return pic_list;
    }

    public void setPic_list(List<String> pic_list) {
        this.pic_list = pic_list;
    }

    public DetailArrayBean getDetail_array() {
        return detail_array;
    }

    public void setDetail_array(DetailArrayBean detail_array) {
        this.detail_array = detail_array;
    }

    public static class ReturnInfoBean {

        private String refund_id;
        private String goods_id;
        private String goods_name;
        private String goods_num;
        private String goods_state_v;
        private String ship_state;
        private String delay_state;
        private String order_id;
        private String refund_amount;
        private String refund_sn;
        private String return_type;
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
            return refund_id == null ? "" : refund_id;
        }

        public void setRefund_id(String refund_id) {
            this.refund_id = refund_id == null ? "" : refund_id;
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

        public String getGoods_num() {
            return goods_num == null ? "" : goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num == null ? "" : goods_num;
        }

        public String getGoods_state_v() {
            return goods_state_v == null ? "" : goods_state_v;
        }

        public void setGoods_state_v(String goods_state_v) {
            this.goods_state_v = goods_state_v == null ? "" : goods_state_v;
        }

        public String getShip_state() {
            return ship_state == null ? "" : ship_state;
        }

        public void setShip_state(String ship_state) {
            this.ship_state = ship_state == null ? "" : ship_state;
        }

        public String getDelay_state() {
            return delay_state == null ? "" : delay_state;
        }

        public void setDelay_state(String delay_state) {
            this.delay_state = delay_state == null ? "" : delay_state;
        }

        public String getOrder_id() {
            return order_id == null ? "" : order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id == null ? "" : order_id;
        }

        public String getRefund_amount() {
            return refund_amount == null ? "" : refund_amount;
        }

        public void setRefund_amount(String refund_amount) {
            this.refund_amount = refund_amount == null ? "" : refund_amount;
        }

        public String getRefund_sn() {
            return refund_sn == null ? "" : refund_sn;
        }

        public void setRefund_sn(String refund_sn) {
            this.refund_sn = refund_sn == null ? "" : refund_sn;
        }

        public String getReturn_type() {
            return return_type == null ? "" : return_type;
        }

        public void setReturn_type(String return_type) {
            this.return_type = return_type == null ? "" : return_type;
        }

        public String getOrder_sn() {
            return order_sn == null ? "" : order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn == null ? "" : order_sn;
        }

        public String getAdd_time() {
            return add_time == null ? "" : add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time == null ? "" : add_time;
        }

        public String getGoods_img_360() {
            return goods_img_360 == null ? "" : goods_img_360;
        }

        public void setGoods_img_360(String goods_img_360) {
            this.goods_img_360 = goods_img_360 == null ? "" : goods_img_360;
        }

        public String getSeller_state() {
            return seller_state == null ? "" : seller_state;
        }

        public void setSeller_state(String seller_state) {
            this.seller_state = seller_state == null ? "" : seller_state;
        }

        public String getAdmin_state() {
            return admin_state == null ? "" : admin_state;
        }

        public void setAdmin_state(String admin_state) {
            this.admin_state = admin_state == null ? "" : admin_state;
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

        public String getReason_info() {
            return reason_info == null ? "" : reason_info;
        }

        public void setReason_info(String reason_info) {
            this.reason_info = reason_info == null ? "" : reason_info;
        }

        public String getBuyer_message() {
            return buyer_message == null ? "" : buyer_message;
        }

        public void setBuyer_message(String buyer_message) {
            this.buyer_message = buyer_message == null ? "" : buyer_message;
        }

        public String getSeller_message() {
            return seller_message == null ? "" : seller_message;
        }

        public void setSeller_message(String seller_message) {
            this.seller_message = seller_message == null ? "" : seller_message;
        }

        public String getAdmin_message() {
            return admin_message == null ? "" : admin_message;
        }

        public void setAdmin_message(String admin_message) {
            this.admin_message = admin_message == null ? "" : admin_message;
        }
    }

    public static class DetailArrayBean {

        private String refund_code;
        private String pay_amount;
        private String pd_amount;
        private String rcb_amount;

        public boolean isNull() {
            if (TextUtils.isEmpty(refund_code) && TextUtils.isEmpty(pay_amount) &&
                    TextUtils.isEmpty(pd_amount) && TextUtils.isEmpty(rcb_amount)) {
                return true;
            }
            return false;
        }

        public String getRefund_code() {
            return refund_code == null ? "" : refund_code;
        }

        public void setRefund_code(String refund_code) {
            this.refund_code = refund_code == null ? "" : refund_code;
        }

        public String getPay_amount() {
            return pay_amount == null ? "" : pay_amount;
        }

        public void setPay_amount(String pay_amount) {
            this.pay_amount = pay_amount == null ? "" : pay_amount;
        }

        public String getPd_amount() {
            return pd_amount == null ? "" : pd_amount;
        }

        public void setPd_amount(String pd_amount) {
            this.pd_amount = pd_amount == null ? "" : pd_amount;
        }

        public String getRcb_amount() {
            return rcb_amount == null ? "" : rcb_amount;
        }

        public void setRcb_amount(String rcb_amount) {
            this.rcb_amount = rcb_amount == null ? "" : rcb_amount;
        }
    }
}
