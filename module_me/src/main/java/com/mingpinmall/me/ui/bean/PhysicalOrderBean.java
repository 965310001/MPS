package com.mingpinmall.me.ui.bean;

import java.util.List;

/**
 * 功能描述：订单
 * @author 小斌
 * @date 2019/4/11
 **/
public class PhysicalOrderBean {

    private String order_id;
    private String order_sn;
    private String pay_sn;
    private String store_id;
    private String store_name;
    private String buyer_id;
    private String buyer_name;
    private String buyer_email;
    private String buyer_phone;
    private String add_time;
    private String payment_code;
    private String payment_time;
    private String finnshed_time;
    private String goods_amount;
    private String order_amount;
    private String rcb_amount;
    private String pd_amount;
    private String shipping_fee;
    private String evaluation_state;
    private String evaluation_again_state;
    private String order_state;
    private String refund_state;
    private String lock_state;
    private String delete_state;
    private String refund_amount;
    private String delay_time;
    private String order_from;
    private String shipping_code;
    private String order_type;
    private String api_pay_time;
    private String chain_id;
    private String chain_code;
    private String rpt_amount;
    private String trade_no;
    private String state_desc;
    private String payment_name;
    private boolean if_cancel;
    private boolean if_show_pay;
    private boolean if_refund_cancel;
    private boolean if_receive;
    private boolean if_lock;
    private boolean if_deliver;
    private boolean if_evaluation;
    private boolean if_evaluation_again;
    private boolean if_delete;
    private boolean ownmall;
    private List<ExtendOrderGoodsBean> extend_order_goods;
    private List<ZengpinListBean> zengpin_list;

    public boolean isIf_show_pay() {
        return if_show_pay;
    }

    public void setIf_show_pay(boolean if_show_pay) {
        this.if_show_pay = if_show_pay;
    }

    public String getOrder_id() {
        return order_id == null ? "" : order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id == null ? "" : order_id;
    }

    public String getOrder_sn() {
        return order_sn == null ? "" : order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn == null ? "" : order_sn;
    }

    public String getPay_sn() {
        return pay_sn == null ? "" : pay_sn;
    }

    public void setPay_sn(String pay_sn) {
        this.pay_sn = pay_sn == null ? "" : pay_sn;
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

    public String getBuyer_id() {
        return buyer_id == null ? "" : buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id == null ? "" : buyer_id;
    }

    public String getBuyer_name() {
        return buyer_name == null ? "" : buyer_name;
    }

    public void setBuyer_name(String buyer_name) {
        this.buyer_name = buyer_name == null ? "" : buyer_name;
    }

    public String getBuyer_email() {
        return buyer_email == null ? "" : buyer_email;
    }

    public void setBuyer_email(String buyer_email) {
        this.buyer_email = buyer_email == null ? "" : buyer_email;
    }

    public String getBuyer_phone() {
        return buyer_phone == null ? "" : buyer_phone;
    }

    public void setBuyer_phone(String buyer_phone) {
        this.buyer_phone = buyer_phone == null ? "" : buyer_phone;
    }

    public String getAdd_time() {
        return add_time == null ? "" : add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time == null ? "" : add_time;
    }

    public String getPayment_code() {
        return payment_code == null ? "" : payment_code;
    }

    public void setPayment_code(String payment_code) {
        this.payment_code = payment_code == null ? "" : payment_code;
    }

    public String getPayment_time() {
        return payment_time == null ? "" : payment_time;
    }

    public void setPayment_time(String payment_time) {
        this.payment_time = payment_time == null ? "" : payment_time;
    }

    public String getFinnshed_time() {
        return finnshed_time == null ? "" : finnshed_time;
    }

    public void setFinnshed_time(String finnshed_time) {
        this.finnshed_time = finnshed_time == null ? "" : finnshed_time;
    }

    public String getGoods_amount() {
        return goods_amount == null ? "" : goods_amount;
    }

    public void setGoods_amount(String goods_amount) {
        this.goods_amount = goods_amount == null ? "" : goods_amount;
    }

    public String getOrder_amount() {
        return order_amount == null ? "" : order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount == null ? "" : order_amount;
    }

    public String getRcb_amount() {
        return rcb_amount == null ? "" : rcb_amount;
    }

    public void setRcb_amount(String rcb_amount) {
        this.rcb_amount = rcb_amount == null ? "" : rcb_amount;
    }

    public String getPd_amount() {
        return pd_amount == null ? "" : pd_amount;
    }

    public void setPd_amount(String pd_amount) {
        this.pd_amount = pd_amount == null ? "" : pd_amount;
    }

    public String getShipping_fee() {
        return shipping_fee == null ? "" : shipping_fee;
    }

    public void setShipping_fee(String shipping_fee) {
        this.shipping_fee = shipping_fee == null ? "" : shipping_fee;
    }

    public String getEvaluation_state() {
        return evaluation_state == null ? "" : evaluation_state;
    }

    public void setEvaluation_state(String evaluation_state) {
        this.evaluation_state = evaluation_state == null ? "" : evaluation_state;
    }

    public String getEvaluation_again_state() {
        return evaluation_again_state == null ? "" : evaluation_again_state;
    }

    public void setEvaluation_again_state(String evaluation_again_state) {
        this.evaluation_again_state = evaluation_again_state == null ? "" : evaluation_again_state;
    }

    public String getOrder_state() {
        return order_state == null ? "" : order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state == null ? "" : order_state;
    }

    public String getRefund_state() {
        return refund_state == null ? "" : refund_state;
    }

    public void setRefund_state(String refund_state) {
        this.refund_state = refund_state == null ? "" : refund_state;
    }

    public String getLock_state() {
        return lock_state == null ? "" : lock_state;
    }

    public void setLock_state(String lock_state) {
        this.lock_state = lock_state == null ? "" : lock_state;
    }

    public String getDelete_state() {
        return delete_state == null ? "" : delete_state;
    }

    public void setDelete_state(String delete_state) {
        this.delete_state = delete_state == null ? "" : delete_state;
    }

    public String getRefund_amount() {
        return refund_amount == null ? "" : refund_amount;
    }

    public void setRefund_amount(String refund_amount) {
        this.refund_amount = refund_amount == null ? "" : refund_amount;
    }

    public String getDelay_time() {
        return delay_time == null ? "" : delay_time;
    }

    public void setDelay_time(String delay_time) {
        this.delay_time = delay_time == null ? "" : delay_time;
    }

    public String getOrder_from() {
        return order_from == null ? "" : order_from;
    }

    public void setOrder_from(String order_from) {
        this.order_from = order_from == null ? "" : order_from;
    }

    public String getShipping_code() {
        return shipping_code == null ? "" : shipping_code;
    }

    public void setShipping_code(String shipping_code) {
        this.shipping_code = shipping_code == null ? "" : shipping_code;
    }

    public String getOrder_type() {
        return order_type == null ? "" : order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type == null ? "" : order_type;
    }

    public String getApi_pay_time() {
        return api_pay_time == null ? "" : api_pay_time;
    }

    public void setApi_pay_time(String api_pay_time) {
        this.api_pay_time = api_pay_time == null ? "" : api_pay_time;
    }

    public String getChain_id() {
        return chain_id == null ? "" : chain_id;
    }

    public void setChain_id(String chain_id) {
        this.chain_id = chain_id == null ? "" : chain_id;
    }

    public String getChain_code() {
        return chain_code == null ? "" : chain_code;
    }

    public void setChain_code(String chain_code) {
        this.chain_code = chain_code == null ? "" : chain_code;
    }

    public String getRpt_amount() {
        return rpt_amount == null ? "" : rpt_amount;
    }

    public void setRpt_amount(String rpt_amount) {
        this.rpt_amount = rpt_amount == null ? "" : rpt_amount;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getState_desc() {
        return state_desc == null ? "" : state_desc;
    }

    public void setState_desc(String state_desc) {
        this.state_desc = state_desc == null ? "" : state_desc;
    }

    public String getPayment_name() {
        return payment_name == null ? "" : payment_name;
    }

    public void setPayment_name(String payment_name) {
        this.payment_name = payment_name == null ? "" : payment_name;
    }

    public boolean isIf_cancel() {
        return if_cancel;
    }

    public void setIf_cancel(boolean if_cancel) {
        this.if_cancel = if_cancel;
    }

    public boolean isIf_refund_cancel() {
        return if_refund_cancel;
    }

    public void setIf_refund_cancel(boolean if_refund_cancel) {
        this.if_refund_cancel = if_refund_cancel;
    }

    public boolean isIf_receive() {
        return if_receive;
    }

    public void setIf_receive(boolean if_receive) {
        this.if_receive = if_receive;
    }

    public boolean isIf_lock() {
        return if_lock;
    }

    public void setIf_lock(boolean if_lock) {
        this.if_lock = if_lock;
    }

    public boolean isIf_deliver() {
        return if_deliver;
    }

    public void setIf_deliver(boolean if_deliver) {
        this.if_deliver = if_deliver;
    }

    public boolean isIf_evaluation() {
        return if_evaluation;
    }

    public void setIf_evaluation(boolean if_evaluation) {
        this.if_evaluation = if_evaluation;
    }

    public boolean isIf_evaluation_again() {
        return if_evaluation_again;
    }

    public void setIf_evaluation_again(boolean if_evaluation_again) {
        this.if_evaluation_again = if_evaluation_again;
    }

    public boolean isIf_delete() {
        return if_delete;
    }

    public void setIf_delete(boolean if_delete) {
        this.if_delete = if_delete;
    }

    public boolean isOwnmall() {
        return ownmall;
    }

    public void setOwnmall(boolean ownmall) {
        this.ownmall = ownmall;
    }

    public List<ExtendOrderGoodsBean> getExtend_order_goods() {
        return extend_order_goods;
    }

    public void setExtend_order_goods(List<ExtendOrderGoodsBean> extend_order_goods) {
        this.extend_order_goods = extend_order_goods;
    }

    public List<ZengpinListBean> getZengpin_list() {
        return zengpin_list;
    }

    public void setZengpin_list(List<ZengpinListBean> zengpin_list) {
        this.zengpin_list = zengpin_list;
    }

    public static class ExtendOrderGoodsBean {

        private String goods_id;
        private String goods_name;
        private String goods_price;
        private String goods_num;
        private String goods_type;
        private String goods_spec;
        private String invite_rates;
        private String goods_image_url;
        private boolean refund;

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

        public String getGoods_type() {
            return goods_type == null ? "" : goods_type;
        }

        public void setGoods_type(String goods_type) {
            this.goods_type = goods_type == null ? "" : goods_type;
        }

        public String getGoods_spec() {
            return goods_spec == null ? "" : goods_spec;
        }

        public void setGoods_spec(String goods_spec) {
            this.goods_spec = goods_spec == null ? "" : goods_spec;
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

        public boolean isRefund() {
            return refund;
        }

        public void setRefund(boolean refund) {
            this.refund = refund;
        }
    }

    public static class ZengpinListBean {

        private String goods_id;
        private String goods_name;
        private String goods_price;
        private String goods_num;
        private String goods_type;
        private String goods_spec;
        private String invite_rates;
        private String goods_image_url;
        private boolean refund;

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

        public String getGoods_type() {
            return goods_type == null ? "" : goods_type;
        }

        public void setGoods_type(String goods_type) {
            this.goods_type = goods_type == null ? "" : goods_type;
        }

        public String getGoods_spec() {
            return goods_spec == null ? "" : goods_spec;
        }

        public void setGoods_spec(String goods_spec) {
            this.goods_spec = goods_spec == null ? "" : goods_spec;
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

        public boolean isRefund() {
            return refund;
        }

        public void setRefund(boolean refund) {
            this.refund = refund;
        }
    }
}
