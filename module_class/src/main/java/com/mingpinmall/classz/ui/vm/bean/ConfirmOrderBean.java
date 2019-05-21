package com.mingpinmall.classz.ui.vm.bean;

import android.text.TextUtils;

import com.goldze.common.dmvvm.base.bean.AddressDataBean;
import com.goldze.common.dmvvm.base.bean.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author 小斌
 * @data 2019/5/20
 **/
public class ConfirmOrderBean extends BaseBean {

    private AddressDataBean.AddressListBean address_info;
    private AddressApiBean address_api;
    private InvInfoBean inv_info;
    private String order_amount;
    private String vat_hash;
    private List<StoreCartListNewsBean> store_cart_list_news;

    public String getVat_hash() {
        return vat_hash == null ? "" : vat_hash;
    }

    public void setVat_hash(String vat_hash) {
        this.vat_hash = vat_hash == null ? "" : vat_hash;
    }

    public AddressApiBean getAddress_api() {
        return address_api;
    }

    public void setAddress_api(AddressApiBean address_api) {
        this.address_api = address_api;
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
        return order_amount == null ? "" : order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount == null ? "" : order_amount;
    }

    public List<StoreCartListNewsBean> getStore_cart_list_news() {
        return store_cart_list_news;
    }

    public void setStore_cart_list_news(List<StoreCartListNewsBean> store_cart_list_news) {
        this.store_cart_list_news = store_cart_list_news;
    }

    public static class AddressApiBean {

        private String state;
        private String allow_offpay;
        private String offpay_hash;
        private String offpay_hash_batch;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
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

    public static class InvInfoBean {

        private String inv_reg_phone;
        private String inv_reg_bname;
        private String inv_reg_baccount;
        private String inv_rec_name;
        private String inv_rec_mobphone;
        private String inv_rec_province;
        private String inv_goto_addr;
        private String inv_username;
        private String inv_jgcode;
        private String inv_idcode;
        private String content;

        public String getInv_reg_phone() {
            return inv_reg_phone == null ? "" : inv_reg_phone;
        }

        public void setInv_reg_phone(String inv_reg_phone) {
            this.inv_reg_phone = inv_reg_phone == null ? "" : inv_reg_phone;
        }

        public String getInv_reg_bname() {
            return inv_reg_bname == null ? "" : inv_reg_bname;
        }

        public void setInv_reg_bname(String inv_reg_bname) {
            this.inv_reg_bname = inv_reg_bname == null ? "" : inv_reg_bname;
        }

        public String getInv_reg_baccount() {
            return inv_reg_baccount == null ? "" : inv_reg_baccount;
        }

        public void setInv_reg_baccount(String inv_reg_baccount) {
            this.inv_reg_baccount = inv_reg_baccount == null ? "" : inv_reg_baccount;
        }

        public String getInv_rec_name() {
            return inv_rec_name == null ? "" : inv_rec_name;
        }

        public void setInv_rec_name(String inv_rec_name) {
            this.inv_rec_name = inv_rec_name == null ? "" : inv_rec_name;
        }

        public String getInv_rec_mobphone() {
            return inv_rec_mobphone == null ? "" : inv_rec_mobphone;
        }

        public void setInv_rec_mobphone(String inv_rec_mobphone) {
            this.inv_rec_mobphone = inv_rec_mobphone == null ? "" : inv_rec_mobphone;
        }

        public String getInv_rec_province() {
            return inv_rec_province == null ? "" : inv_rec_province;
        }

        public void setInv_rec_province(String inv_rec_province) {
            this.inv_rec_province = inv_rec_province == null ? "" : inv_rec_province;
        }

        public String getInv_goto_addr() {
            return inv_goto_addr == null ? "" : inv_goto_addr;
        }

        public void setInv_goto_addr(String inv_goto_addr) {
            this.inv_goto_addr = inv_goto_addr == null ? "" : inv_goto_addr;
        }

        public String getInv_username() {
            return inv_username == null ? "" : inv_username;
        }

        public void setInv_username(String inv_username) {
            this.inv_username = inv_username == null ? "" : inv_username;
        }

        public String getInv_jgcode() {
            return inv_jgcode == null ? "" : inv_jgcode;
        }

        public void setInv_jgcode(String inv_jgcode) {
            this.inv_jgcode = inv_jgcode == null ? "" : inv_jgcode;
        }

        public String getInv_idcode() {
            return inv_idcode == null ? "" : inv_idcode;
        }

        public void setInv_idcode(String inv_idcode) {
            this.inv_idcode = inv_idcode == null ? "" : inv_idcode;
        }

        public String getContent() {
            return content == null ? "" : content;
        }

        public void setContent(String content) {
            this.content = content == null ? "" : content;
        }
    }

    public static class StoreCartListNewsBean {

        private String store_goods_total;
        private StoreMansongRuleListBean store_mansong_rule_list;
        private String freight;
        private String freight_message;
        private String store_name;
        private String yf_price;
        private String store_id;
        private List<GoodsListBean> goods_list;

        public String getYf_price() {
            return yf_price == null ? "" : yf_price;
        }

        public void setYf_price(String yf_price) {
            this.yf_price = yf_price == null ? "" : yf_price;
        }

        public String getStore_goods_total() {
            return store_goods_total == null ? "" : store_goods_total;
        }

        public void setStore_goods_total(String store_goods_total) {
            this.store_goods_total = store_goods_total == null ? "" : store_goods_total;
        }

        public StoreMansongRuleListBean getStore_mansong_rule_list() {
            return store_mansong_rule_list;
        }

        public void setStore_mansong_rule_list(StoreMansongRuleListBean store_mansong_rule_list) {
            this.store_mansong_rule_list = store_mansong_rule_list;
        }

        public String getFreight() {
            return freight == null ? "" : freight;
        }

        public void setFreight(String freight) {
            this.freight = freight == null ? "" : freight;
        }

        public String getFreight_message() {
            return freight_message == null ? "" : freight_message;
        }

        public void setFreight_message(String freight_message) {
            this.freight_message = freight_message == null ? "" : freight_message;
        }

        public String getStore_name() {
            return store_name == null ? "" : store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name == null ? "" : store_name;
        }

        public String getStore_id() {
            return store_id == null ? "" : store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id == null ? "" : store_id;
        }

        public List<GoodsListBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsListBean> goods_list) {
            this.goods_list = goods_list;
        }

        public static class StoreMansongRuleListBean {

            private String rule_id;
            private String mansong_id;
            private String price;
            private String discount;
            private String mansong_goods_name;
            private String goods_id;
            private String mansong_name;
            private String start_time;
            private String end_time;
            private DescBean desc;

            public String getRule_id() {
                return rule_id == null ? "" : rule_id;
            }

            public void setRule_id(String rule_id) {
                this.rule_id = rule_id == null ? "" : rule_id;
            }

            public String getMansong_id() {
                return mansong_id == null ? "" : mansong_id;
            }

            public void setMansong_id(String mansong_id) {
                this.mansong_id = mansong_id == null ? "" : mansong_id;
            }

            public String getPrice() {
                return price == null ? "" : price;
            }

            public void setPrice(String price) {
                this.price = price == null ? "" : price;
            }

            public String getDiscount() {
                return discount == null ? "" : discount;
            }

            public void setDiscount(String discount) {
                this.discount = discount == null ? "" : discount;
            }

            public String getMansong_goods_name() {
                return mansong_goods_name == null ? "" : mansong_goods_name;
            }

            public void setMansong_goods_name(String mansong_goods_name) {
                this.mansong_goods_name = mansong_goods_name == null ? "" : mansong_goods_name;
            }

            public String getGoods_id() {
                return goods_id == null ? "" : goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id == null ? "" : goods_id;
            }

            public String getMansong_name() {
                return mansong_name == null ? "" : mansong_name;
            }

            public void setMansong_name(String mansong_name) {
                this.mansong_name = mansong_name == null ? "" : mansong_name;
            }

            public String getStart_time() {
                return start_time == null ? "" : start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time == null ? "" : start_time;
            }

            public String getEnd_time() {
                return end_time == null ? "" : end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time == null ? "" : end_time;
            }

            public DescBean getDesc() {
                return desc;
            }

            public void setDesc(DescBean desc) {
                this.desc = desc;
            }

            public static class DescBean {

                private String desc;

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }
            }
        }

        public static class GoodsListBean {

            private String cart_id;
            private String buyer_id;
            private String store_id;
            private String store_name;
            private String goods_id;
            private String goods_name;
            private String goods_price;
            private String goods_num;
            private String goods_commonid;
            private String gc_id;
            private String transport_id;
            private String goods_freight;
            private String have_gift;
            private String is_fcode;
            private Object xianshi_info;
            private String is_book;
            private String goods_spec;
            private boolean ifsole;
            private String goods_total;
            private String goods_image_url;
            private String upper_limit;
            private String groupbuy_id;
            private String promotions_id;
            private boolean ifgroupbuy;
            private boolean ifxianshi;
            private List<GiftBean> gift_list;

            public boolean isIfxianshi() {
                return ifxianshi;
            }

            public void setIfxianshi(boolean ifxianshi) {
                this.ifxianshi = ifxianshi;
            }

            public List<GiftBean> getGift_list() {
                return gift_list;
            }

            public void setGift_list(List<GiftBean> gift_list) {
                this.gift_list = gift_list;
            }

            public String getHave_gift() {
                return have_gift == null ? "" : have_gift;
            }

            public boolean isHaveGifts() {
                return TextUtils.equals("1", have_gift);
            }

            public void setHave_gift(String have_gift) {
                this.have_gift = have_gift == null ? "" : have_gift;
            }

            public String getCart_id() {
                return cart_id == null ? "" : cart_id;
            }

            public void setCart_id(String cart_id) {
                this.cart_id = cart_id == null ? "" : cart_id;
            }

            public String getBuyer_id() {
                return buyer_id == null ? "" : buyer_id;
            }

            public void setBuyer_id(String buyer_id) {
                this.buyer_id = buyer_id == null ? "" : buyer_id;
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

            public String getGoods_commonid() {
                return goods_commonid == null ? "" : goods_commonid;
            }

            public void setGoods_commonid(String goods_commonid) {
                this.goods_commonid = goods_commonid == null ? "" : goods_commonid;
            }

            public String getGc_id() {
                return gc_id == null ? "" : gc_id;
            }

            public void setGc_id(String gc_id) {
                this.gc_id = gc_id == null ? "" : gc_id;
            }

            public String getTransport_id() {
                return transport_id == null ? "" : transport_id;
            }

            public void setTransport_id(String transport_id) {
                this.transport_id = transport_id == null ? "" : transport_id;
            }

            public String getGoods_freight() {
                return goods_freight == null ? "" : goods_freight;
            }

            public void setGoods_freight(String goods_freight) {
                this.goods_freight = goods_freight == null ? "" : goods_freight;
            }

            public String getIs_fcode() {
                return is_fcode == null ? "" : is_fcode;
            }

            public boolean isFCode() {
                if (is_fcode == null) {
                    return false;
                }
                return "1".equals(is_fcode);
            }

            public void setIs_fcode(String is_fcode) {
                this.is_fcode = is_fcode == null ? "" : is_fcode;
            }

            public Object getXianshi_info() {
                return xianshi_info;
            }

            public void setXianshi_info(Object xianshi_info) {
                this.xianshi_info = xianshi_info;
            }

            public String getIs_book() {
                return is_book == null ? "" : is_book;
            }

            public void setIs_book(String is_book) {
                this.is_book = is_book == null ? "" : is_book;
            }

            public String getGoods_spec() {
                return goods_spec == null ? "" : goods_spec;
            }

            public void setGoods_spec(String goods_spec) {
                this.goods_spec = goods_spec == null ? "" : goods_spec;
            }

            public boolean isIfsole() {
                return ifsole;
            }

            public void setIfsole(boolean ifsole) {
                this.ifsole = ifsole;
            }

            public String getGoods_total() {
                return goods_total == null ? "" : goods_total;
            }

            public void setGoods_total(String goods_total) {
                this.goods_total = goods_total == null ? "" : goods_total;
            }

            public String getGoods_image_url() {
                return goods_image_url == null ? "" : goods_image_url;
            }

            public void setGoods_image_url(String goods_image_url) {
                this.goods_image_url = goods_image_url == null ? "" : goods_image_url;
            }

            public String getUpper_limit() {
                return upper_limit == null ? "" : upper_limit;
            }

            public void setUpper_limit(String upper_limit) {
                this.upper_limit = upper_limit == null ? "" : upper_limit;
            }

            public String getGroupbuy_id() {
                return groupbuy_id == null ? "" : groupbuy_id;
            }

            public void setGroupbuy_id(String groupbuy_id) {
                this.groupbuy_id = groupbuy_id == null ? "" : groupbuy_id;
            }

            public String getPromotions_id() {
                return promotions_id == null ? "" : promotions_id;
            }

            public void setPromotions_id(String promotions_id) {
                this.promotions_id = promotions_id == null ? "" : promotions_id;
            }

            public boolean isIfgroupbuy() {
                return ifgroupbuy;
            }

            public void setIfgroupbuy(boolean ifgroupbuy) {
                this.ifgroupbuy = ifgroupbuy;
            }

            public static class GiftBean {

                private String gift_id;
                private String goods_id;
                private String goods_commonid;
                private String gift_goodsid;
                private String gift_goodsname;
                private String gift_goodsimage;
                private String gift_amount;
                private String goods_storage;

                public String getGift_id() {
                    return gift_id == null ? "" : gift_id;
                }

                public void setGift_id(String gift_id) {
                    this.gift_id = gift_id == null ? "" : gift_id;
                }

                public String getGoods_id() {
                    return goods_id == null ? "" : goods_id;
                }

                public void setGoods_id(String goods_id) {
                    this.goods_id = goods_id == null ? "" : goods_id;
                }

                public String getGoods_commonid() {
                    return goods_commonid == null ? "" : goods_commonid;
                }

                public void setGoods_commonid(String goods_commonid) {
                    this.goods_commonid = goods_commonid == null ? "" : goods_commonid;
                }

                public String getGift_goodsid() {
                    return gift_goodsid == null ? "" : gift_goodsid;
                }

                public void setGift_goodsid(String gift_goodsid) {
                    this.gift_goodsid = gift_goodsid == null ? "" : gift_goodsid;
                }

                public String getGift_goodsname() {
                    return gift_goodsname == null ? "" : gift_goodsname;
                }

                public void setGift_goodsname(String gift_goodsname) {
                    this.gift_goodsname = gift_goodsname == null ? "" : gift_goodsname;
                }

                public String getGift_goodsimage() {
                    return gift_goodsimage == null ? "" : gift_goodsimage;
                }

                public void setGift_goodsimage(String gift_goodsimage) {
                    this.gift_goodsimage = gift_goodsimage == null ? "" : gift_goodsimage;
                }

                public String getGift_amount() {
                    return gift_amount == null ? "" : gift_amount;
                }

                public void setGift_amount(String gift_amount) {
                    this.gift_amount = gift_amount == null ? "" : gift_amount;
                }

                public String getGoods_storage() {
                    return goods_storage == null ? "" : goods_storage;
                }

                public void setGoods_storage(String goods_storage) {
                    this.goods_storage = goods_storage == null ? "" : goods_storage;
                }
            }
        }
    }

}
