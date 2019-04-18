package com.mingpinmall.me.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：虚拟订单详情
 * 创建人：小斌
 * 创建时间: 2019/4/18
 **/
public class VirtualInformationBean implements Serializable {

    private OrderInfoBean order_info;

    public OrderInfoBean getOrder_info() {
        return order_info;
    }

    public void setOrder_info(OrderInfoBean order_info) {
        this.order_info = order_info;
    }

    public static class OrderInfoBean {

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
        private Object trade_no;
        private String close_time;
        private Object close_reason;
        private String finnshed_time;
        private String order_amount;
        private String refund_amount;
        private String rcb_amount;
        private String pd_amount;
        private String order_state;
        private String refund_state;
        private String buyer_msg;
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
        private String state_desc;
        private String payment_name;
        private boolean if_cancel;
        private boolean if_evaluation;
        private boolean if_refund;
        private String goods_image_url;
        private boolean ownmall;
        private boolean if_resend;
        private List<CodeListBean> code_list;

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

        public Object getTrade_no() {
            return trade_no;
        }

        public void setTrade_no(Object trade_no) {
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

        public String getBuyer_msg() {
            return buyer_msg;
        }

        public void setBuyer_msg(String buyer_msg) {
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

        public boolean isIf_evaluation() {
            return if_evaluation;
        }

        public void setIf_evaluation(boolean if_evaluation) {
            this.if_evaluation = if_evaluation;
        }

        public boolean isIf_refund() {
            return if_refund;
        }

        public void setIf_refund(boolean if_refund) {
            this.if_refund = if_refund;
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

        public boolean isIf_resend() {
            return if_resend;
        }

        public void setIf_resend(boolean if_resend) {
            this.if_resend = if_resend;
        }

        public List<CodeListBean> getCode_list() {
            return code_list;
        }

        public void setCode_list(List<CodeListBean> code_list) {
            this.code_list = code_list;
        }

        public static class CodeListBean {
            /**
             * rec_id : 1
             * order_id : 1
             * store_id : 3
             * buyer_id : 6
             * vr_code : 460001806239046667
             * vr_state : 0
             * vr_usetime : null
             * pay_price : 0.01
             * vr_indate : 1443628799
             * commis_rate : 0
             * refund_lock : 0
             * vr_invalid_refund : 1
             * vr_code_desc : 已过期，过期时间 2015-09-30
             * vr_code_valid_count : 1
             */

            private String rec_id;
            private String order_id;
            private String store_id;
            private String buyer_id;
            private String vr_code;
            private String vr_state;
            private Object vr_usetime;
            private String pay_price;
            private String vr_indate;
            private String commis_rate;
            private String refund_lock;
            private String vr_invalid_refund;
            private String vr_code_desc;
            private int vr_code_valid_count;

            public String getRec_id() {
                return rec_id;
            }

            public void setRec_id(String rec_id) {
                this.rec_id = rec_id;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getStore_id() {
                return store_id;
            }

            public void setStore_id(String store_id) {
                this.store_id = store_id;
            }

            public String getBuyer_id() {
                return buyer_id;
            }

            public void setBuyer_id(String buyer_id) {
                this.buyer_id = buyer_id;
            }

            public String getVr_code() {
                return vr_code;
            }

            public void setVr_code(String vr_code) {
                this.vr_code = vr_code;
            }

            public String getVr_state() {
                return vr_state;
            }

            public void setVr_state(String vr_state) {
                this.vr_state = vr_state;
            }

            public Object getVr_usetime() {
                return vr_usetime;
            }

            public void setVr_usetime(Object vr_usetime) {
                this.vr_usetime = vr_usetime;
            }

            public String getPay_price() {
                return pay_price;
            }

            public void setPay_price(String pay_price) {
                this.pay_price = pay_price;
            }

            public String getVr_indate() {
                return vr_indate;
            }

            public void setVr_indate(String vr_indate) {
                this.vr_indate = vr_indate;
            }

            public String getCommis_rate() {
                return commis_rate;
            }

            public void setCommis_rate(String commis_rate) {
                this.commis_rate = commis_rate;
            }

            public String getRefund_lock() {
                return refund_lock;
            }

            public void setRefund_lock(String refund_lock) {
                this.refund_lock = refund_lock;
            }

            public String getVr_invalid_refund() {
                return vr_invalid_refund;
            }

            public void setVr_invalid_refund(String vr_invalid_refund) {
                this.vr_invalid_refund = vr_invalid_refund;
            }

            public String getVr_code_desc() {
                return vr_code_desc;
            }

            public void setVr_code_desc(String vr_code_desc) {
                this.vr_code_desc = vr_code_desc;
            }

            public int getVr_code_valid_count() {
                return vr_code_valid_count;
            }

            public void setVr_code_valid_count(int vr_code_valid_count) {
                this.vr_code_valid_count = vr_code_valid_count;
            }
        }
    }
}
