package com.mingpinmall.classz.ui.vm.bean;

import com.goldze.common.dmvvm.base.bean.AddressDataBean;
import com.goldze.common.dmvvm.base.bean.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 订单信息
 */
public class OrderInfo extends BaseBean {

    private StoreCartListBean store_cart_list;
    private AddressDataBean.AddressListBean address_info;
    private Object ifshow_offpay;
    private String vat_hash;
    private InvInfoBean inv_info;
    private String order_amount;
    private AddressApiBean address_api;
    private StoreFinalTotalListBean store_final_total_list;

    public StoreCartListBean getStore_cart_list() {
        return store_cart_list;
    }

    public void setStore_cart_list(StoreCartListBean store_cart_list) {
        this.store_cart_list = store_cart_list;
    }

    public AddressDataBean.AddressListBean getAddress_info() {
        return address_info;
    }

    public void setAddress_info(AddressDataBean.AddressListBean address_info) {
        this.address_info = address_info;
    }

    public Object getIfshow_offpay() {
        return ifshow_offpay;
    }

    public void setIfshow_offpay(Object ifshow_offpay) {
        this.ifshow_offpay = ifshow_offpay;
    }

    public String getVat_hash() {
        return vat_hash;
    }

    public void setVat_hash(String vat_hash) {
        this.vat_hash = vat_hash;
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

    public StoreFinalTotalListBean getStore_final_total_list() {
        return store_final_total_list;
    }

    public void setStore_final_total_list(StoreFinalTotalListBean store_final_total_list) {
        this.store_final_total_list = store_final_total_list;
    }

    public static class StoreCartListBean {
        /**
         * 10 : {"goods_list":[{"goods_num":1,"goods_id":109938,"goods_commonid":"108808","gc_id":"12","store_id":"10","goods_name":"3333333333333","goods_price":"333.00","store_name":"qqqqqq","goods_image":"10_06082251459466849.png","transport_id":"0","goods_freight":"0.00","goods_vat":"0","goods_storage":"332","goods_storage_alarm":"0","is_fcode":"0","have_gift":"0","state":true,"storage_state":true,"groupbuy_info":null,"xianshi_info":null,"is_chain":"0","is_book":"0","book_down_payment":"0.00","book_final_payment":"0.00","book_down_time":"0","cart_id":109938,"bl_id":0,"sole_info":[],"contractlist":[],"goods_total":"333.00","goods_image_url":"http://192.168.0.44/data/upload/mall/store/goods/10/10_06082251459466849_240.png"}],"store_goods_total":"333.00","store_mansong_rule_list":null,"store_voucher_info":[],"store_voucher_list":[],"freight":"1","store_name":"qqqqqq"}
         */

        @SerializedName("10")
        private _$10Bean _$10;

        public _$10Bean get_$10() {
            return _$10;
        }

        public void set_$10(_$10Bean _$10) {
            this._$10 = _$10;
        }

        public static class _$10Bean {
            /**
             * goods_list : [{"goods_num":1,"goods_id":109938,"goods_commonid":"108808","gc_id":"12","store_id":"10","goods_name":"3333333333333","goods_price":"333.00","store_name":"qqqqqq","goods_image":"10_06082251459466849.png","transport_id":"0","goods_freight":"0.00","goods_vat":"0","goods_storage":"332","goods_storage_alarm":"0","is_fcode":"0","have_gift":"0","state":true,"storage_state":true,"groupbuy_info":null,"xianshi_info":null,"is_chain":"0","is_book":"0","book_down_payment":"0.00","book_final_payment":"0.00","book_down_time":"0","cart_id":109938,"bl_id":0,"sole_info":[],"contractlist":[],"goods_total":"333.00","goods_image_url":"http://192.168.0.44/data/upload/mall/store/goods/10/10_06082251459466849_240.png"}]
             * store_goods_total : 333.00
             * store_mansong_rule_list : null
             * store_voucher_info : []
             * store_voucher_list : []
             * freight : 1
             * store_name : qqqqqq
             */

            private String store_goods_total;
            private Object store_mansong_rule_list;
            private String freight;
            private String store_name;
            private List<GoodsInfo> goods_list;
//            private List<?> store_voucher_info;
//            private List<?> store_voucher_list;

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

//            public List<?> getStore_voucher_info() {
//                return store_voucher_info;
//            }
//
//            public void setStore_voucher_info(List<?> store_voucher_info) {
//                this.store_voucher_info = store_voucher_info;
//            }
//
//            public List<?> getStore_voucher_list() {
//                return store_voucher_list;
//            }
//
//            public void setStore_voucher_list(List<?> store_voucher_list) {
//                this.store_voucher_list = store_voucher_list;
//            }

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

    public static class AddressApiBean {
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
        private ContentBean content;
        private String allow_offpay;
        private String offpay_hash;
        private String offpay_hash_batch;
//        private List<?> no_send_tpl_ids;
//        private List<?> allow_offpay_batch;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public ContentBean getContent() {
            return content;
        }

        public void setContent(ContentBean content) {
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

        public static class ContentBean {
            /**
             * 10 : 0.00
             */

            @SerializedName("10")
            private String _$10;

            public String get_$10() {
                return _$10;
            }

            public void set_$10(String _$10) {
                this._$10 = _$10;
            }
        }
    }

    public static class StoreFinalTotalListBean {
        /**
         * 10 : 333.00
         */

        @SerializedName("10")
        private String _$10;

        public String get_$10() {
            return _$10;
        }

        public void set_$10(String _$10) {
            this._$10 = _$10;
        }
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "store_cart_list=" + store_cart_list +
                ", address_info=" + address_info +
                ", ifshow_offpay=" + ifshow_offpay +
                ", vat_hash='" + vat_hash + '\'' +
                ", inv_info=" + inv_info +
                ", order_amount='" + order_amount + '\'' +
                ", address_api=" + address_api +
                ", store_final_total_list=" + store_final_total_list +
                '}';
    }
}
