package com.mingpinmall.cart.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：购物车
 * 创建人：小斌
 * 创建时间: 2019/4/24
 **/
public class ShopCartBean implements Serializable {

        private String sum;
        private int cart_count;
        private List<CartListBean> cart_list;

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public int getCart_count() {
            return cart_count;
        }

        public void setCart_count(int cart_count) {
            this.cart_count = cart_count;
        }

        public List<CartListBean> getCart_list() {
            return cart_list;
        }

        public void setCart_list(List<CartListBean> cart_list) {
            this.cart_list = cart_list;
        }

        public static class CartListBean {

            private String store_id;
            private String store_name;
            private String free_freight;
            private List<GoodsBean> goods;
            private List<VoucherBean> voucher;
            private List<MansongBean> mansong;

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

            public String getFree_freight() {
                return free_freight == null ? "" : free_freight;
            }

            public void setFree_freight(String free_freight) {
                this.free_freight = free_freight == null ? "" : free_freight;
            }

            public List<GoodsBean> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBean> goods) {
                this.goods = goods;
            }

            public List<VoucherBean> getVoucher() {
                return voucher;
            }

            public void setVoucher(List<VoucherBean> voucher) {
                this.voucher = voucher;
            }

            public List<MansongBean> getMansong() {
                return mansong;
            }

            public void setMansong(List<MansongBean> mansong) {
                this.mansong = mansong;
            }

            public static class GoodsBean {

                private String cart_id;
                private String buyer_id;
                private String store_id;
                private String store_name;
                private String goods_id;
                private String goods_name;
                private String goods_price;
                private String goods_num;
                private String goods_image;
                private String bl_id;
                private boolean state;
                private boolean storage_state;
                private String goods_commonid;
                private String gc_id;
                private String transport_id;
                private String goods_freight;
                private String goods_vat;
                private String goods_storage;
                private String goods_storage_alarm;
                private String is_fcode;
                private String have_gift;
                private String is_book;
                private String book_down_payment;
                private String book_final_payment;
                private String book_down_time;
                private String is_chain;
                private String goods_spec;
                private String goods_image_url;
                private String goods_total;
                private String upper_limit;
                private String groupbuy_id;
                private String promotions_id;
                private boolean ifgroupbuy;
                private boolean ifsole;
                private String goods_yprice;
                private boolean ifxianshi;
                private List<GiftListBean> gift_list;

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

                public String getGoods_image() {
                    return goods_image == null ? "" : goods_image;
                }

                public void setGoods_image(String goods_image) {
                    this.goods_image = goods_image == null ? "" : goods_image;
                }

                public String getBl_id() {
                    return bl_id == null ? "" : bl_id;
                }

                public void setBl_id(String bl_id) {
                    this.bl_id = bl_id == null ? "" : bl_id;
                }

                public boolean isState() {
                    return state;
                }

                public void setState(boolean state) {
                    this.state = state;
                }

                public boolean isStorage_state() {
                    return storage_state;
                }

                public void setStorage_state(boolean storage_state) {
                    this.storage_state = storage_state;
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

                public String getGoods_vat() {
                    return goods_vat == null ? "" : goods_vat;
                }

                public void setGoods_vat(String goods_vat) {
                    this.goods_vat = goods_vat == null ? "" : goods_vat;
                }

                public String getGoods_storage() {
                    return goods_storage == null ? "" : goods_storage;
                }

                public void setGoods_storage(String goods_storage) {
                    this.goods_storage = goods_storage == null ? "" : goods_storage;
                }

                public String getGoods_storage_alarm() {
                    return goods_storage_alarm == null ? "" : goods_storage_alarm;
                }

                public void setGoods_storage_alarm(String goods_storage_alarm) {
                    this.goods_storage_alarm = goods_storage_alarm == null ? "" : goods_storage_alarm;
                }

                public String getIs_fcode() {
                    return is_fcode == null ? "" : is_fcode;
                }

                public void setIs_fcode(String is_fcode) {
                    this.is_fcode = is_fcode == null ? "" : is_fcode;
                }

                public String getHave_gift() {
                    return have_gift == null ? "" : have_gift;
                }

                public void setHave_gift(String have_gift) {
                    this.have_gift = have_gift == null ? "" : have_gift;
                }

                public String getIs_book() {
                    return is_book == null ? "" : is_book;
                }

                public void setIs_book(String is_book) {
                    this.is_book = is_book == null ? "" : is_book;
                }

                public String getBook_down_payment() {
                    return book_down_payment == null ? "" : book_down_payment;
                }

                public void setBook_down_payment(String book_down_payment) {
                    this.book_down_payment = book_down_payment == null ? "" : book_down_payment;
                }

                public String getBook_final_payment() {
                    return book_final_payment == null ? "" : book_final_payment;
                }

                public void setBook_final_payment(String book_final_payment) {
                    this.book_final_payment = book_final_payment == null ? "" : book_final_payment;
                }

                public String getBook_down_time() {
                    return book_down_time == null ? "" : book_down_time;
                }

                public void setBook_down_time(String book_down_time) {
                    this.book_down_time = book_down_time == null ? "" : book_down_time;
                }

                public String getIs_chain() {
                    return is_chain == null ? "" : is_chain;
                }

                public void setIs_chain(String is_chain) {
                    this.is_chain = is_chain == null ? "" : is_chain;
                }

                public String getGoods_spec() {
                    return goods_spec == null ? "" : goods_spec;
                }

                public void setGoods_spec(String goods_spec) {
                    this.goods_spec = goods_spec == null ? "" : goods_spec;
                }

                public String getGoods_image_url() {
                    return goods_image_url == null ? "" : goods_image_url;
                }

                public void setGoods_image_url(String goods_image_url) {
                    this.goods_image_url = goods_image_url == null ? "" : goods_image_url;
                }

                public String getGoods_total() {
                    return goods_total == null ? "" : goods_total;
                }

                public void setGoods_total(String goods_total) {
                    this.goods_total = goods_total == null ? "" : goods_total;
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

                public boolean isIfsole() {
                    return ifsole;
                }

                public void setIfsole(boolean ifsole) {
                    this.ifsole = ifsole;
                }

                public String getGoods_yprice() {
                    return goods_yprice == null ? "" : goods_yprice;
                }

                public void setGoods_yprice(String goods_yprice) {
                    this.goods_yprice = goods_yprice == null ? "" : goods_yprice;
                }

                public boolean isIfxianshi() {
                    return ifxianshi;
                }

                public void setIfxianshi(boolean ifxianshi) {
                    this.ifxianshi = ifxianshi;
                }

                public List<GiftListBean> getGift_list() {
                    return gift_list;
                }

                public void setGift_list(List<GiftListBean> gift_list) {
                    this.gift_list = gift_list;
                }

                public static class GiftListBean {

                    private String gift_id;
                    private String goods_id;
                    private String goods_commonid;
                    private String gift_goodsid;
                    private String gift_goodsname;
                    private String gift_goodsimage;
                    private String gift_amount;
                    private String goods_storage;
                    private String goods_image_url;

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

                    public String getGoods_image_url() {
                        return goods_image_url == null ? "" : goods_image_url;
                    }

                    public void setGoods_image_url(String goods_image_url) {
                        this.goods_image_url = goods_image_url == null ? "" : goods_image_url;
                    }
                }
            }

            public static class VoucherBean {

                private String voucher_t_id;
                private String voucher_t_price;
                private String voucher_t_limit;
                private String voucher_t_end_date;

                public String getVoucher_t_id() {
                    return voucher_t_id == null ? "" : voucher_t_id;
                }

                public void setVoucher_t_id(String voucher_t_id) {
                    this.voucher_t_id = voucher_t_id == null ? "" : voucher_t_id;
                }

                public String getVoucher_t_price() {
                    return voucher_t_price == null ? "" : voucher_t_price;
                }

                public void setVoucher_t_price(String voucher_t_price) {
                    this.voucher_t_price = voucher_t_price == null ? "" : voucher_t_price;
                }

                public String getVoucher_t_limit() {
                    return voucher_t_limit == null ? "" : voucher_t_limit;
                }

                public void setVoucher_t_limit(String voucher_t_limit) {
                    this.voucher_t_limit = voucher_t_limit == null ? "" : voucher_t_limit;
                }

                public String getVoucher_t_end_date() {
                    return voucher_t_end_date == null ? "" : voucher_t_end_date;
                }

                public void setVoucher_t_end_date(String voucher_t_end_date) {
                    this.voucher_t_end_date = voucher_t_end_date == null ? "" : voucher_t_end_date;
                }
            }

            public static class MansongBean {

                private String desc;
                private String goods_name;
                private String url;

                public String getDesc() {
                    return desc == null ? "" : desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc == null ? "" : desc;
                }

                public String getGoods_name() {
                    return goods_name == null ? "" : goods_name;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name == null ? "" : goods_name;
                }

                public String getUrl() {
                    return url == null ? "" : url;
                }

                public void setUrl(String url) {
                    this.url = url == null ? "" : url;
                }
            }
    }
}
