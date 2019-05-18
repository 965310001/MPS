package com.mingpinmall.classz.ui.vm.bean;

import com.goldze.common.dmvvm.base.bean.AddressDataBean;
import com.goldze.common.dmvvm.base.bean.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 订单信息
 */
public class OrderInfo extends BaseBean {

    @SerializedName("store_cart_list_news")
    private List<StoreCartListBean> store_cart_list;
    private AddressDataBean.AddressListBean address_info;
    private InvInfoBean inv_info;
    private String order_amount;
    private AddressApiBean address_api;
    @SerializedName("new_store_final_total_list")
    private List<ContentBean> store_final_total_list;
    private String vat_hash;

    /**
     * member_info : {"member_mobile":"15013070796","available_predeposit":"0.00","available_rc_balance":"0.00"}
     */


    public String getVat_hash() {
        return vat_hash;
    }

    public void setVat_hash(String vat_hash) {
        this.vat_hash = vat_hash;
    }

    public List<StoreCartListBean> getStore_cart_list() {
        return store_cart_list;
    }

    public void setStore_cart_list(List<StoreCartListBean> store_cart_list) {
        this.store_cart_list = store_cart_list;
    }

    public AddressDataBean.AddressListBean getAddress_info() {
        return address_info;
    }

    public void setAddress_info(AddressDataBean.AddressListBean address_info) {
        this.address_info = address_info;
    }

    public InvInfoBean getInv_info() {
        return inv_info;
    }

    public void setInv_info(InvInfoBean inv_info) {
        this.inv_info = inv_info;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public AddressApiBean getAddress_api() {
        return address_api;
    }

    public void setAddress_api(AddressApiBean address_api) {
        this.address_api = address_api;
    }

    public List<ContentBean> getStore_final_total_list() {
        return store_final_total_list;
    }

    public void setStore_final_total_list(List<ContentBean> store_final_total_list) {
        this.store_final_total_list = store_final_total_list;
    }


    public static class StoreCartListBean {
        /**
         * 10 : {"goods_list":[{"goods_num":1,"goods_id":109938,"goods_commonid":"108808","gc_id":"12","store_id":"10","goods_name":"3333333333333","goods_price":"333.00","store_name":"qqqqqq","goods_image":"10_06082251459466849.png","transport_id":"0","goods_freight":"0.00","goods_vat":"0","goods_storage":"332","goods_storage_alarm":"0","is_fcode":"0","have_gift":"0","state":true,"storage_state":true,"groupbuy_info":null,"xianshi_info":null,"is_chain":"0","is_book":"0","book_down_payment":"0.00","book_final_payment":"0.00","book_down_time":"0","cart_id":109938,"bl_id":0,"sole_info":[],"contractlist":[],"goods_total":"333.00","goods_image_url":"http://192.168.0.44/data/upload/mall/store/goods/10/10_06082251459466849_240.png"}],"store_goods_total":"333.00","store_mansong_rule_list":null,"store_voucher_info":[],"store_voucher_list":[],"freight":"1","store_name":"qqqqqq"}
         */

        private String store_goods_total;
        private Object store_mansong_rule_list;
        private String freight;
        private String store_name;
        private List<GoodsInfo> goods_list;

        public String getStore_goods_total() {
            return store_goods_total;
        }

        public void setStore_goods_total(String store_goods_total) {
            this.store_goods_total = store_goods_total;
        }

        public Object getStore_mansong_rule_list() {
            return store_mansong_rule_list;
        }

        public void setStore_mansong_rule_list(Object store_mansong_rule_list) {
            this.store_mansong_rule_list = store_mansong_rule_list;
        }

        public String getFreight() {
            return freight;
        }

        public void setFreight(String freight) {
            this.freight = freight;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public List<GoodsInfo> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsInfo> goods_list) {
            this.goods_list = goods_list;
        }
    }

    public static class InvInfoBean {
        /**
         * content : 不需要发票
         */

        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class AddressApiBean extends BaseBean {
        /**
         * state : success
         * content : {"10":"0.00"}
         * no_send_tpl_ids : []
         * allow_offpay : 0
         * allow_offpay_batch : []
         * offpay_hash : 9Ec1oTKVqP3dlBaBDeXB4aoeGJBQrweKqReH6xB
         * offpay_hash_batch : yNJcD3ddzn8laB7yRHL6yvOczp1SG05
         */

        private String state;
        @SerializedName("new_content")
        private List<ContentBean> content;
        private String allow_offpay;
        private String offpay_hash;
        private String offpay_hash_batch;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public String getAllow_offpay() {
            return allow_offpay;
        }

        public void setAllow_offpay(String allow_offpay) {
            this.allow_offpay = allow_offpay;
        }

        public String getOffpay_hash() {
            return offpay_hash;
        }

        public void setOffpay_hash(String offpay_hash) {
            this.offpay_hash = offpay_hash;
        }

        public String getOffpay_hash_batch() {
            return offpay_hash_batch;
        }

        public void setOffpay_hash_batch(String offpay_hash_batch) {
            this.offpay_hash_batch = offpay_hash_batch;
        }

    }

    public static class ContentBean {

        /**
         * key : 7
         * value : 0.00
         */

        private int key;
        private String value;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "store_cart_list=" + store_cart_list +
                ", address_info=" + address_info +
                ", inv_info=" + inv_info +
                ", order_amount='" + order_amount + '\'' +
                ", store_final_total_list=" + store_final_total_list +
                '}';
    }

    /*虚拟产品*/
    @SerializedName("goods_info")
    private GoodsInfo goodsInfo;
    private MemberInfoBean member_info;
    /*private StoreInfo.StoreInfoBean store_info;*/

   /* public StoreInfo.StoreInfoBean getStore_info() {
        return store_info;
    }

    public void setStore_info(StoreInfo.StoreInfoBean store_info) {
        this.store_info = store_info;
    }*/

    public GoodsInfo getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public MemberInfoBean getMember_info() {
        return member_info;
    }

    public void setMember_info(MemberInfoBean member_info) {
        this.member_info = member_info;
    }

    public static class MemberInfoBean extends BaseBean {
        /**
         * member_mobile : 15013070796
         * available_predeposit : 0.00
         * available_rc_balance : 0.00
         */

        private String member_mobile;
        private String available_predeposit;
        private String available_rc_balance;

        public String getMember_mobile() {
            return member_mobile;
        }

        public void setMember_mobile(String member_mobile) {
            this.member_mobile = member_mobile;
        }

        public String getAvailable_predeposit() {
            return available_predeposit;
        }

        public void setAvailable_predeposit(String available_predeposit) {
            this.available_predeposit = available_predeposit;
        }

        public String getAvailable_rc_balance() {
            return available_rc_balance;
        }

        public void setAvailable_rc_balance(String available_rc_balance) {
            this.available_rc_balance = available_rc_balance;
        }
    }
}
