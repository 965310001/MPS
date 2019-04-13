package com.mingpinmall.me.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/13
 **/
public class VirtualOrderBean implements Serializable {

    private List<OrderListBean> order_list;

    public List<OrderListBean> getOrder_list() {
        return order_list;
    }

    public void setOrder_list(List<OrderListBean> order_list) {
        this.order_list = order_list;
    }

    public static class OrderListBean implements Serializable {
        /**
         * order_id : 2
         * order_sn : 450494763052315006
         * store_id : 6
         * store_name : 智联
         * buyer_id : 30538
         * buyer_name : 2396665785
         * buyer_phone : 13798152960
         * add_time : 1441419052
         * payment_code : alipay
         * payment_time : 1441421147
         * trade_no : 2015090500001000260061225323
         * close_time : 0
         * close_reason : null
         * finnshed_time : 1469871538
         * order_amount : 100.01
         * refund_amount : 0.00
         * rcb_amount : 0.00
         * pd_amount : 0.00
         * order_state : 10
         * refund_state : 0
         * buyer_msg : null
         * delete_state : 0
         * goods_id : 109892
         * goods_name : 测试123
         * goods_price : 100.99
         * goods_num : 1
         * goods_image : 3_04864688672341055.jpg
         * commis_rate : 0
         * gc_id : 1044
         * vr_indate : 1443628799
         * vr_send_times : 0
         * vr_invalid_refund : 1
         * order_promotion_type : 0
         * promotions_id : 0
         * order_from : 2
         * evaluation_state : 0
         * evaluation_time : 0
         * use_state : 0
         * api_pay_time : 0
         * goods_contractid : null
         * goods_spec : null
         * order_state_text : 待付款
         * state_desc : 待付款
         * payment_name : 支付宝
         * if_cancel : true
         * if_pay : true
         * if_evaluation : false
         * goods_image_url : http://192.168.0.11/data/upload/mall/common/default_goods_image_240.gif
         * ownmall : false
         */

        private String order_id;
        private String order_sn;
        private String store_id;
        private String store_name;
        private String buyer_id;
        private String buyer_name;
        private String buyer_phone;
        private String add_time;
        private String payment_code;
        private String payment_time;
        private String trade_no;
        private String close_time;
        private Object close_reason;
        private String finnshed_time;
        private String order_amount;
        private String refund_amount;
        private String rcb_amount;
        private String pd_amount;
        private String order_state;
        private String refund_state;
        private Object buyer_msg;
        private String delete_state;
        private String goods_id;
        private String goods_name;
        private String goods_price;
        private String goods_num;
        private String goods_image;
        private String commis_rate;
        private String gc_id;
        private String vr_indate;
        private String vr_send_times;
        private String vr_invalid_refund;
        private String order_promotion_type;
        private String promotions_id;
        private String order_from;
        private String evaluation_state;
        private String evaluation_time;
        private String use_state;
        private String api_pay_time;
        private Object goods_contractid;
        private Object goods_spec;
        private String order_state_text;
        private String state_desc;
        private String payment_name;
        private boolean if_cancel;
        private boolean if_pay;
        private boolean if_evaluation;
        private String goods_image_url;
        private boolean ownmall;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
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

        public String getBuyer_id() {
            return buyer_id;
        }

        public void setBuyer_id(String buyer_id) {
            this.buyer_id = buyer_id;
        }

        public String getBuyer_name() {
            return buyer_name;
        }

        public void setBuyer_name(String buyer_name) {
            this.buyer_name = buyer_name;
        }

        public String getBuyer_phone() {
            return buyer_phone;
        }

        public void setBuyer_phone(String buyer_phone) {
            this.buyer_phone = buyer_phone;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getPayment_code() {
            return payment_code;
        }

        public void setPayment_code(String payment_code) {
            this.payment_code = payment_code;
        }

        public String getPayment_time() {
            return payment_time;
        }

        public void setPayment_time(String payment_time) {
            this.payment_time = payment_time;
        }

        public String getTrade_no() {
            return trade_no;
        }

        public void setTrade_no(String trade_no) {
            this.trade_no = trade_no;
        }

        public String getClose_time() {
            return close_time;
        }

        public void setClose_time(String close_time) {
            this.close_time = close_time;
        }

        public Object getClose_reason() {
            return close_reason;
        }

        public void setClose_reason(Object close_reason) {
            this.close_reason = close_reason;
        }

        public String getFinnshed_time() {
            return finnshed_time;
        }

        public void setFinnshed_time(String finnshed_time) {
            this.finnshed_time = finnshed_time;
        }

        public String getOrder_amount() {
            return order_amount;
        }

        public void setOrder_amount(String order_amount) {
            this.order_amount = order_amount;
        }

        public String getRefund_amount() {
            return refund_amount;
        }

        public void setRefund_amount(String refund_amount) {
            this.refund_amount = refund_amount;
        }

        public String getRcb_amount() {
            return rcb_amount;
        }

        public void setRcb_amount(String rcb_amount) {
            this.rcb_amount = rcb_amount;
        }

        public String getPd_amount() {
            return pd_amount;
        }

        public void setPd_amount(String pd_amount) {
            this.pd_amount = pd_amount;
        }

        public String getOrder_state() {
            return order_state;
        }

        public void setOrder_state(String order_state) {
            this.order_state = order_state;
        }

        public String getRefund_state() {
            return refund_state;
        }

        public void setRefund_state(String refund_state) {
            this.refund_state = refund_state;
        }

        public Object getBuyer_msg() {
            return buyer_msg;
        }

        public void setBuyer_msg(Object buyer_msg) {
            this.buyer_msg = buyer_msg;
        }

        public String getDelete_state() {
            return delete_state;
        }

        public void setDelete_state(String delete_state) {
            this.delete_state = delete_state;
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

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public String getCommis_rate() {
            return commis_rate;
        }

        public void setCommis_rate(String commis_rate) {
            this.commis_rate = commis_rate;
        }

        public String getGc_id() {
            return gc_id;
        }

        public void setGc_id(String gc_id) {
            this.gc_id = gc_id;
        }

        public String getVr_indate() {
            return vr_indate;
        }

        public void setVr_indate(String vr_indate) {
            this.vr_indate = vr_indate;
        }

        public String getVr_send_times() {
            return vr_send_times;
        }

        public void setVr_send_times(String vr_send_times) {
            this.vr_send_times = vr_send_times;
        }

        public String getVr_invalid_refund() {
            return vr_invalid_refund;
        }

        public void setVr_invalid_refund(String vr_invalid_refund) {
            this.vr_invalid_refund = vr_invalid_refund;
        }

        public String getOrder_promotion_type() {
            return order_promotion_type;
        }

        public void setOrder_promotion_type(String order_promotion_type) {
            this.order_promotion_type = order_promotion_type;
        }

        public String getPromotions_id() {
            return promotions_id;
        }

        public void setPromotions_id(String promotions_id) {
            this.promotions_id = promotions_id;
        }

        public String getOrder_from() {
            return order_from;
        }

        public void setOrder_from(String order_from) {
            this.order_from = order_from;
        }

        public String getEvaluation_state() {
            return evaluation_state;
        }

        public void setEvaluation_state(String evaluation_state) {
            this.evaluation_state = evaluation_state;
        }

        public String getEvaluation_time() {
            return evaluation_time;
        }

        public void setEvaluation_time(String evaluation_time) {
            this.evaluation_time = evaluation_time;
        }

        public String getUse_state() {
            return use_state;
        }

        public void setUse_state(String use_state) {
            this.use_state = use_state;
        }

        public String getApi_pay_time() {
            return api_pay_time;
        }

        public void setApi_pay_time(String api_pay_time) {
            this.api_pay_time = api_pay_time;
        }

        public Object getGoods_contractid() {
            return goods_contractid;
        }

        public void setGoods_contractid(Object goods_contractid) {
            this.goods_contractid = goods_contractid;
        }

        public Object getGoods_spec() {
            return goods_spec;
        }

        public void setGoods_spec(Object goods_spec) {
            this.goods_spec = goods_spec;
        }

        public String getOrder_state_text() {
            return order_state_text;
        }

        public void setOrder_state_text(String order_state_text) {
            this.order_state_text = order_state_text;
        }

        public String getState_desc() {
            return state_desc;
        }

        public void setState_desc(String state_desc) {
            this.state_desc = state_desc;
        }

        public String getPayment_name() {
            return payment_name;
        }

        public void setPayment_name(String payment_name) {
            this.payment_name = payment_name;
        }

        public boolean isIf_cancel() {
            return if_cancel;
        }

        public void setIf_cancel(boolean if_cancel) {
            this.if_cancel = if_cancel;
        }

        public boolean isIf_pay() {
            return if_pay;
        }

        public void setIf_pay(boolean if_pay) {
            this.if_pay = if_pay;
        }

        public boolean isIf_evaluation() {
            return if_evaluation;
        }

        public void setIf_evaluation(boolean if_evaluation) {
            this.if_evaluation = if_evaluation;
        }

        public String getGoods_image_url() {
            return goods_image_url;
        }

        public void setGoods_image_url(String goods_image_url) {
            this.goods_image_url = goods_image_url;
        }

        public boolean isOwnmall() {
            return ownmall;
        }

        public void setOwnmall(boolean ownmall) {
            this.ownmall = ownmall;
        }
    }
}
