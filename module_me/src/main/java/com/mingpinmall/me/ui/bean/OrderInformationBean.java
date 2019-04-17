package com.mingpinmall.me.ui.bean;

import java.util.List;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/16
 **/
public class OrderInformationBean {

    private OrderInfoBean order_info;

    public OrderInfoBean getOrder_info() {
        return order_info;
    }

    public void setOrder_info(OrderInfoBean order_info) {
        this.order_info = order_info;
    }

    public static class OrderInfoBean {
        /**
         * order_id : 161
         * order_state : 10
         * order_type : 1
         * order_sn : 2000000000055801
         * store_id : 3
         * store_name : 名品网自营
         * add_time : 2019-04-16 14:48:11
         * payment_time :
         * shipping_time :
         * finnshed_time :
         * order_amount : 2686.00
         * shipping_fee : 0.00
         * real_pay_amount : 2686.00
         * state_desc : 待付款
         * payment_name : 在线付款
         * order_message : test001
         * reciver_phone : 111332
         * reciver_name : 名字
         * reciver_addr : 河北 秦皇岛市 山海关区 啊啊啊啊啊
         * store_member_id : 3
         * store_phone :
         * order_tips : 请于1小时内完成付款，逾期未付订单自动关闭
         * promotion : [["店铺代金券","使用50.00元代金券 编码：960150510148390560"]]
         * invoice : 类型：普通发票  抬头：com 内容：明细
         * if_deliver : false
         * if_buyer_cancel : true
         * if_refund_cancel : false
         * if_receive : false
         * if_evaluation : false
         * if_lock : false
         * goods_list : [{"rec_id":"29782","goods_id":"109998","goods_name":"Silhouette诗乐2019新款男女款SPX全框黑色商务休闲简约2891 Black（黑）605","goods_price":"1368.00","goods_num":"2","goods_spec":"颜色：Black（黑）6050","image_url":"https://www.mingpinmall.cn/data/upload/mall/store/goods/3/3_06085743415988043_240.jpg","refund":0}]
         * zengpin_list : []
         * ownmall : true
         */

        private String order_id;
        private String order_state;
        private String order_type;
        private String order_sn;
        private String store_id;
        private String store_name;
        private String add_time;
        private String payment_time;
        private String shipping_time;
        private String finnshed_time;
        private String order_amount;
        private String shipping_fee;
        private String real_pay_amount;
        private String state_desc;
        private String payment_name;
        private String order_message;
        private String reciver_phone;
        private String reciver_name;
        private String reciver_addr;
        private String store_member_id;
        private String store_phone;
        private String order_tips;
        private String invoice;
        private boolean if_deliver;
        private boolean if_buyer_cancel;
        private boolean if_refund_cancel;
        private boolean if_receive;
        private boolean if_evaluation;
        private boolean if_lock;
        private boolean ownmall;
        private List<List<String>> promotion;
        private List<GoodsListBean> goods_list;
        private List<?> zengpin_list;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_state() {
            return order_state;
        }

        public void setOrder_state(String order_state) {
            this.order_state = order_state;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
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

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getPayment_time() {
            return payment_time;
        }

        public void setPayment_time(String payment_time) {
            this.payment_time = payment_time;
        }

        public String getShipping_time() {
            return shipping_time;
        }

        public void setShipping_time(String shipping_time) {
            this.shipping_time = shipping_time;
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

        public String getShipping_fee() {
            return shipping_fee;
        }

        public void setShipping_fee(String shipping_fee) {
            this.shipping_fee = shipping_fee;
        }

        public String getReal_pay_amount() {
            return real_pay_amount;
        }

        public void setReal_pay_amount(String real_pay_amount) {
            this.real_pay_amount = real_pay_amount;
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

        public String getOrder_message() {
            return order_message;
        }

        public void setOrder_message(String order_message) {
            this.order_message = order_message;
        }

        public String getReciver_phone() {
            return reciver_phone;
        }

        public void setReciver_phone(String reciver_phone) {
            this.reciver_phone = reciver_phone;
        }

        public String getReciver_name() {
            return reciver_name;
        }

        public void setReciver_name(String reciver_name) {
            this.reciver_name = reciver_name;
        }

        public String getReciver_addr() {
            return reciver_addr;
        }

        public void setReciver_addr(String reciver_addr) {
            this.reciver_addr = reciver_addr;
        }

        public String getStore_member_id() {
            return store_member_id;
        }

        public void setStore_member_id(String store_member_id) {
            this.store_member_id = store_member_id;
        }

        public String getStore_phone() {
            return store_phone;
        }

        public void setStore_phone(String store_phone) {
            this.store_phone = store_phone;
        }

        public String getOrder_tips() {
            return order_tips;
        }

        public void setOrder_tips(String order_tips) {
            this.order_tips = order_tips;
        }

        public String getInvoice() {
            return invoice;
        }

        public void setInvoice(String invoice) {
            this.invoice = invoice;
        }

        public boolean isIf_deliver() {
            return if_deliver;
        }

        public void setIf_deliver(boolean if_deliver) {
            this.if_deliver = if_deliver;
        }

        public boolean isIf_buyer_cancel() {
            return if_buyer_cancel;
        }

        public void setIf_buyer_cancel(boolean if_buyer_cancel) {
            this.if_buyer_cancel = if_buyer_cancel;
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

        public boolean isIf_evaluation() {
            return if_evaluation;
        }

        public void setIf_evaluation(boolean if_evaluation) {
            this.if_evaluation = if_evaluation;
        }

        public boolean isIf_lock() {
            return if_lock;
        }

        public void setIf_lock(boolean if_lock) {
            this.if_lock = if_lock;
        }

        public boolean isOwnmall() {
            return ownmall;
        }

        public void setOwnmall(boolean ownmall) {
            this.ownmall = ownmall;
        }

        public List<List<String>> getPromotion() {
            return promotion;
        }

        public void setPromotion(List<List<String>> promotion) {
            this.promotion = promotion;
        }

        public List<GoodsListBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsListBean> goods_list) {
            this.goods_list = goods_list;
        }

        public List<?> getZengpin_list() {
            return zengpin_list;
        }

        public void setZengpin_list(List<?> zengpin_list) {
            this.zengpin_list = zengpin_list;
        }

        public static class GoodsListBean {
            /**
             * rec_id : 29782
             * goods_id : 109998
             * goods_name : Silhouette诗乐2019新款男女款SPX全框黑色商务休闲简约2891 Black（黑）605
             * goods_price : 1368.00
             * goods_num : 2
             * goods_spec : 颜色：Black（黑）6050
             * image_url : https://www.mingpinmall.cn/data/upload/mall/store/goods/3/3_06085743415988043_240.jpg
             * refund : 0
             */

            private String rec_id;
            private String goods_id;
            private String goods_name;
            private String goods_price;
            private String goods_num;
            private String goods_spec;
            private String image_url;
            private int refund;

            public String getRec_id() {
                return rec_id;
            }

            public void setRec_id(String rec_id) {
                this.rec_id = rec_id;
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

            public String getGoods_spec() {
                return goods_spec;
            }

            public void setGoods_spec(String goods_spec) {
                this.goods_spec = goods_spec;
            }

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }

            public int getRefund() {
                return refund;
            }

            public void setRefund(int refund) {
                this.refund = refund;
            }
        }
    }
}
