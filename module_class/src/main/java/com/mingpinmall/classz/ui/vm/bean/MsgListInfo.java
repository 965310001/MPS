package com.mingpinmall.classz.ui.vm.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;

import java.util.List;

public class MsgListInfo extends BaseBean {

    private boolean node_chat;
    private String node_site_url;
    private String resource_site_url;
    private MemberInfoBean member_info;
    private UserInfoBean user_info;
    private GoodsInfo chat_goods;

    /*消息历史*/
    private List<MsgInfo.MsgBean> list;

    public boolean isNode_chat() {
        return node_chat;
    }

    public void setNode_chat(boolean node_chat) {
        this.node_chat = node_chat;
    }

    public String getNode_site_url() {
        return node_site_url;
    }

    public void setNode_site_url(String node_site_url) {
        this.node_site_url = node_site_url;
    }

    public String getResource_site_url() {
        return resource_site_url;
    }

    public void setResource_site_url(String resource_site_url) {
        this.resource_site_url = resource_site_url;
    }

    public MemberInfoBean getMember_info() {
        return member_info;
    }

    public void setMember_info(MemberInfoBean member_info) {
        this.member_info = member_info;
    }

    public UserInfoBean getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfoBean user_info) {
        this.user_info = user_info;
    }

    public GoodsInfo getChat_goods() {
        return chat_goods;
    }

    public void setChat_goods(GoodsInfo chat_goods) {
        this.chat_goods = chat_goods;
    }

    public List<MsgInfo.MsgBean> getList() {
        return list;
    }

    public void setList(List<MsgInfo.MsgBean> list) {
        this.list = list;
    }

    public static class MemberInfoBean extends BaseBean{
        /**
         * member_id : 16
         * member_name : 15013070796
         * member_avatar : http://192.168.0.44/data/upload/mall/common/05234670838512007.gif
         * store_id : 10
         * store_name : qqqqqq
         * store_avatar : http://192.168.0.44/data/upload/mall/common/default_store_avatar.gif
         * grade_id : 3
         * seller_name : 15013070796
         */

        private String member_id;
        private String member_name;
        private String member_avatar;
        private String store_id;
        private String store_name;
        private String store_avatar;
        private String grade_id;
        private String seller_name;

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public String getMember_avatar() {
            return member_avatar;
        }

        public void setMember_avatar(String member_avatar) {
            this.member_avatar = member_avatar;
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

        public String getStore_avatar() {
            return store_avatar;
        }

        public void setStore_avatar(String store_avatar) {
            this.store_avatar = store_avatar;
        }

        public String getGrade_id() {
            return grade_id;
        }

        public void setGrade_id(String grade_id) {
            this.grade_id = grade_id;
        }

        public String getSeller_name() {
            return seller_name;
        }

        public void setSeller_name(String seller_name) {
            this.seller_name = seller_name;
        }
    }

    public static class UserInfoBean {
        /**
         * store_id :
         * store_name :
         * store_avatar :
         * grade_id :
         * member_avatar : http://192.168.0.44/data/upload/mall/common/05234670838512007.gif
         */

        private String store_id;
        private String member_id;
        private String store_name;
        private String member_name;
        private String store_avatar;
        private String grade_id;
        private String member_avatar;

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
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

        public String getStore_avatar() {
            return store_avatar;
        }

        public void setStore_avatar(String store_avatar) {
            this.store_avatar = store_avatar;
        }

        public String getGrade_id() {
            return grade_id;
        }

        public void setGrade_id(String grade_id) {
            this.grade_id = grade_id;
        }

        public String getMember_avatar() {
            return member_avatar;
        }

        public void setMember_avatar(String member_avatar) {
            this.member_avatar = member_avatar;
        }
    }

    public static class ChatGoodsBean {
        /**
         * goods_id : 109928
         * goods_commonid : 108788
         * goods_name : 正品瑞士邦顿(Bestdon)手表BD7108G男士大表盘全自动针机械表精钢军表夜光防水户外运动腕表
         * goods_jingle : 型号BD7108G    100米防水 终身保修 全国包邮 邦顿正品  专柜同款
         * store_id : 7
         * store_name : 瑞士邦顿（bestdon）手表旗舰店
         * gc_id : 587
         * gc_id_1 : 530
         * gc_id_2 : 540
         * gc_id_3 : 587
         * brand_id : 366
         * goods_price : 599.00
         * goods_promotion_price : 599.00
         * goods_promotion_type : 0
         * goods_marketprice : 599.00
         * goods_serial : BD7108G
         * goods_storage_alarm : 0
         * goods_barcode :
         * goods_click : 656
         * goods_salenum : 75
         * goods_collect : 1
         * spec_name : N;
         * goods_spec : N;
         * goods_storage : 1522
         * goods_image : 7_05175802468129745.jpg
         * goods_body :
         * mobile_body :
         * goods_state : 1
         * goods_verify : 1
         * goods_addtime : 1472822300
         * goods_edittime : 1556614867
         * areaid_1 : 19
         * areaid_2 : 289
         * color_id : 0
         * transport_id : 0
         * goods_freight : 0.00
         * goods_vat : 0
         * goods_commend : 1
         * goods_stcids :
         * evaluation_good_star : 5
         * evaluation_count : 0
         * is_virtual : 0
         * virtual_indate : 0
         * virtual_limit : 0
         * virtual_invalid_refund : 0
         * is_fcode : 0
         * is_presell : 0
         * presell_deliverdate : 0
         * is_book : 0
         * book_down_payment : 0.00
         * book_final_payment : 0.00
         * book_down_time : 0
         * book_buyers : 0
         * have_gift : 0
         * is_own_mall : 0
         * contract_1 : 0
         * contract_2 : 0
         * contract_3 : 0
         * contract_4 : 0
         * contract_5 : 0
         * contract_6 : 0
         * contract_7 : 0
         * contract_8 : 0
         * contract_9 : 0
         * contract_10 : 0
         * is_chain : 0
         * invite_rate : 0.00
         * url : http://192.168.0.44/pinpai/index.php?app=goods&wwi=index&goods_id=109928
         * pic : http://192.168.0.44/data/upload/mall/common/default_goods_image_60.gif
         * pic24 : http://192.168.0.44/data/upload/mall/common/default_goods_image_240.gif
         * pic36 : http://192.168.0.44/data/upload/mall/common/default_goods_image_360.gif
         */

        private String goods_id;
        private String goods_commonid;
        private String goods_name;
        private String goods_jingle;
        private String store_id;
        private String store_name;
        private String goods_price;
        //        private String gc_id;
        //        private String gc_id_1;
        //        private String gc_id_2;
        //        private String gc_id_3;
        //        private String brand_id;
//        private String goods_promotion_price;
//        private String goods_promotion_type;
//        private String goods_marketprice;
//        private String goods_serial;
//        private String goods_storage_alarm;
//        private String goods_barcode;
        //        private String goods_collect;
//        private String spec_name;
//        private String goods_spec;
//        private String goods_storage;
        private String goods_image;
        //        private String goods_click;
        private String goods_salenum;
//        private String goods_body;
//        private String mobile_body;
//        private String goods_state;
//        private String goods_verify;
//        private String goods_addtime;
//        private String goods_edittime;
//        private String areaid_1;
//        private String areaid_2;
//        private String color_id;
//        private String transport_id;
//        private String goods_freight;
//        private String goods_vat;
//        private String goods_commend;
//        private String goods_stcids;
//        private String evaluation_good_star;
//        private String evaluation_count;
//        private String is_virtual;
//        private String virtual_indate;
//        private String virtual_limit;
//        private String virtual_invalid_refund;
//        private String is_fcode;
//        private String is_presell;
//        private String presell_deliverdate;
//        private String is_book;
//        private String book_down_payment;
//        private String book_final_payment;
//        private String book_down_time;
//        private String book_buyers;
//        private String have_gift;
//        private String is_own_mall;
//        private String contract_1;
//        private String contract_2;
//        private String contract_3;
//        private String contract_4;
//        private String contract_5;
//        private String contract_6;
//        private String contract_7;
//        private String contract_8;
//        private String contract_9;
//        private String contract_10;
//        private String is_chain;
//        private String invite_rate;
//        private String url;
//        private String pic;
//        private String pic24;
//        private String pic36;

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_commonid() {
            return goods_commonid;
        }

        public void setGoods_commonid(String goods_commonid) {
            this.goods_commonid = goods_commonid;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_jingle() {
            return goods_jingle;
        }

        public void setGoods_jingle(String goods_jingle) {
            this.goods_jingle = goods_jingle;
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
//
//        public String getGc_id() {
//            return gc_id;
//        }
//
//        public void setGc_id(String gc_id) {
//            this.gc_id = gc_id;
//        }
//
//        public String getGc_id_1() {
//            return gc_id_1;
//        }
//
//        public void setGc_id_1(String gc_id_1) {
//            this.gc_id_1 = gc_id_1;
//        }
//
//        public String getGc_id_2() {
//            return gc_id_2;
//        }
//
//        public void setGc_id_2(String gc_id_2) {
//            this.gc_id_2 = gc_id_2;
//        }
//
//        public String getGc_id_3() {
//            return gc_id_3;
//        }
//
//        public void setGc_id_3(String gc_id_3) {
//            this.gc_id_3 = gc_id_3;
//        }
//
//        public String getBrand_id() {
//            return brand_id;
//        }
//
//        public void setBrand_id(String brand_id) {
//            this.brand_id = brand_id;
//        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

//        public String getGoods_promotion_price() {
//            return goods_promotion_price;
//        }
//
//        public void setGoods_promotion_price(String goods_promotion_price) {
//            this.goods_promotion_price = goods_promotion_price;
//        }
//
//        public String getGoods_promotion_type() {
//            return goods_promotion_type;
//        }
//
//        public void setGoods_promotion_type(String goods_promotion_type) {
//            this.goods_promotion_type = goods_promotion_type;
//        }
//
//        public String getGoods_marketprice() {
//            return goods_marketprice;
//        }
//
//        public void setGoods_marketprice(String goods_marketprice) {
//            this.goods_marketprice = goods_marketprice;
//        }
//
//        public String getGoods_serial() {
//            return goods_serial;
//        }
//
//        public void setGoods_serial(String goods_serial) {
//            this.goods_serial = goods_serial;
//        }
//
//        public String getGoods_storage_alarm() {
//            return goods_storage_alarm;
//        }

//        public void setGoods_storage_alarm(String goods_storage_alarm) {
//            this.goods_storage_alarm = goods_storage_alarm;
//        }
//
//        public String getGoods_barcode() {
//            return goods_barcode;
//        }
//
//        public void setGoods_barcode(String goods_barcode) {
//            this.goods_barcode = goods_barcode;
//        }
//
//        public String getGoods_click() {
//            return goods_click;
//        }
//
//        public void setGoods_click(String goods_click) {
//            this.goods_click = goods_click;
//        }

        public String getGoods_salenum() {
            return goods_salenum;
        }

        public void setGoods_salenum(String goods_salenum) {
            this.goods_salenum = goods_salenum;
        }

//        public String getGoods_collect() {
//            return goods_collect;
//        }
//
//        public void setGoods_collect(String goods_collect) {
//            this.goods_collect = goods_collect;
//        }
//
//        public String getSpec_name() {
//            return spec_name;
//        }
//
//        public void setSpec_name(String spec_name) {
//            this.spec_name = spec_name;
//        }
//
//        public String getGoods_spec() {
//            return goods_spec;
//        }
//
//        public void setGoods_spec(String goods_spec) {
//            this.goods_spec = goods_spec;
//        }
//
//        public String getGoods_storage() {
//            return goods_storage;
//        }
//
//        public void setGoods_storage(String goods_storage) {
//            this.goods_storage = goods_storage;
//        }

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

//        public String getGoods_body() {
//            return goods_body;
//        }
//
//        public void setGoods_body(String goods_body) {
//            this.goods_body = goods_body;
//        }
//
//        public String getMobile_body() {
//            return mobile_body;
//        }
//
//        public void setMobile_body(String mobile_body) {
//            this.mobile_body = mobile_body;
//        }
//
//        public String getGoods_state() {
//            return goods_state;
//        }
//
//        public void setGoods_state(String goods_state) {
//            this.goods_state = goods_state;
//        }
//
//        public String getGoods_verify() {
//            return goods_verify;
//        }
//
//        public void setGoods_verify(String goods_verify) {
//            this.goods_verify = goods_verify;
//        }
//
//        public String getGoods_addtime() {
//            return goods_addtime;
//        }
//
//        public void setGoods_addtime(String goods_addtime) {
//            this.goods_addtime = goods_addtime;
//        }
//
//        public String getGoods_edittime() {
//            return goods_edittime;
//        }
//
//        public void setGoods_edittime(String goods_edittime) {
//            this.goods_edittime = goods_edittime;
//        }

//        public String getAreaid_1() {
//            return areaid_1;
//        }
//
//        public void setAreaid_1(String areaid_1) {
//            this.areaid_1 = areaid_1;
//        }
//
//        public String getAreaid_2() {
//            return areaid_2;
//        }
//
//        public void setAreaid_2(String areaid_2) {
//            this.areaid_2 = areaid_2;
//        }
//
//        public String getColor_id() {
//            return color_id;
//        }
//
//        public void setColor_id(String color_id) {
//            this.color_id = color_id;
//        }
//
//        public String getTransport_id() {
//            return transport_id;
//        }
//
//        public void setTransport_id(String transport_id) {
//            this.transport_id = transport_id;
//        }
//
//        public String getGoods_freight() {
//            return goods_freight;
//        }
//
//        public void setGoods_freight(String goods_freight) {
//            this.goods_freight = goods_freight;
//        }

//        public String getGoods_vat() {
//            return goods_vat;
//        }
//
//        public void setGoods_vat(String goods_vat) {
//            this.goods_vat = goods_vat;
//        }
//
//        public String getGoods_commend() {
//            return goods_commend;
//        }
//
//        public void setGoods_commend(String goods_commend) {
//            this.goods_commend = goods_commend;
//        }
//
//        public String getGoods_stcids() {
//            return goods_stcids;
//        }
//
//        public void setGoods_stcids(String goods_stcids) {
//            this.goods_stcids = goods_stcids;
//        }
//
//        public String getEvaluation_good_star() {
//            return evaluation_good_star;
//        }
//
//        public void setEvaluation_good_star(String evaluation_good_star) {
//            this.evaluation_good_star = evaluation_good_star;
//        }
//
//        public String getEvaluation_count() {
//            return evaluation_count;
//        }
//
//        public void setEvaluation_count(String evaluation_count) {
//            this.evaluation_count = evaluation_count;
//        }
//        public String getIs_virtual() {
//            return is_virtual;
//        }
//
//        public void setIs_virtual(String is_virtual) {
//            this.is_virtual = is_virtual;
//        }
//        public String getVirtual_indate() {
//            return virtual_indate;
//        }
//
//        public void setVirtual_indate(String virtual_indate) {
//            this.virtual_indate = virtual_indate;
//        }
//
//        public String getVirtual_limit() {
//            return virtual_limit;
//        }
//
//        public void setVirtual_limit(String virtual_limit) {
//            this.virtual_limit = virtual_limit;
//        }
//
//        public String getVirtual_invalid_refund() {
//            return virtual_invalid_refund;
//        }
//
//        public void setVirtual_invalid_refund(String virtual_invalid_refund) {
//            this.virtual_invalid_refund = virtual_invalid_refund;
//        }
//        public String getIs_fcode() {
//            return is_fcode;
//        }
//
//        public void setIs_fcode(String is_fcode) {
//            this.is_fcode = is_fcode;
//        }
//
//        public String getIs_presell() {
//            return is_presell;
//        }
//
//        public void setIs_presell(String is_presell) {
//            this.is_presell = is_presell;
//        }
//
//        public String getPresell_deliverdate() {
//            return presell_deliverdate;
//        }
//
//        public void setPresell_deliverdate(String presell_deliverdate) {
//            this.presell_deliverdate = presell_deliverdate;
//        }
//
//        public String getIs_book() {
//            return is_book;
//        }
//
//        public void setIs_book(String is_book) {
//            this.is_book = is_book;
//        }
//
//        public String getBook_down_payment() {
//            return book_down_payment;
//        }
//
//        public void setBook_down_payment(String book_down_payment) {
//            this.book_down_payment = book_down_payment;
//        }
//
//        public String getBook_final_payment() {
//            return book_final_payment;
//        }
//
//        public void setBook_final_payment(String book_final_payment) {
//            this.book_final_payment = book_final_payment;
//        }
//
//        public String getBook_down_time() {
//            return book_down_time;
//        }
//
//        public void setBook_down_time(String book_down_time) {
//            this.book_down_time = book_down_time;
//        }
//
//        public String getBook_buyers() {
//            return book_buyers;
//        }
//
//        public void setBook_buyers(String book_buyers) {
//            this.book_buyers = book_buyers;
//        }
//
//        public String getHave_gift() {
//            return have_gift;
//        }
//
//        public void setHave_gift(String have_gift) {
//            this.have_gift = have_gift;
//        }

//        public String getIs_own_mall() {
//            return is_own_mall;
//        }
//
//        public void setIs_own_mall(String is_own_mall) {
//            this.is_own_mall = is_own_mall;
//        }
//
//        public String getContract_1() {
//            return contract_1;
//        }
//
//        public void setContract_1(String contract_1) {
//            this.contract_1 = contract_1;
//        }
//
//        public String getContract_2() {
//            return contract_2;
//        }
//
//        public void setContract_2(String contract_2) {
//            this.contract_2 = contract_2;
//        }
//
//        public String getContract_3() {
//            return contract_3;
//        }
//
//        public void setContract_3(String contract_3) {
//            this.contract_3 = contract_3;
//        }
//
//        public String getContract_4() {
//            return contract_4;
//        }
//
//        public void setContract_4(String contract_4) {
//            this.contract_4 = contract_4;
//        }
//
//        public String getContract_5() {
//            return contract_5;
//        }
//
//        public void setContract_5(String contract_5) {
//            this.contract_5 = contract_5;
//        }
//
//        public String getContract_6() {
//            return contract_6;
//        }

//        public void setContract_6(String contract_6) {
//            this.contract_6 = contract_6;
//        }
//
//        public String getContract_7() {
//            return contract_7;
//        }
//
//        public void setContract_7(String contract_7) {
//            this.contract_7 = contract_7;
//        }
//
//        public String getContract_8() {
//            return contract_8;
//        }
//
//        public void setContract_8(String contract_8) {
//            this.contract_8 = contract_8;
//        }
//
//        public String getContract_9() {
//            return contract_9;
//        }
//
//        public void setContract_9(String contract_9) {
//            this.contract_9 = contract_9;
//        }
//
//        public String getContract_10() {
//            return contract_10;
//        }
//
//        public void setContract_10(String contract_10) {
//            this.contract_10 = contract_10;
//        }
//
//        public String getIs_chain() {
//            return is_chain;
//        }
//
//        public void setIs_chain(String is_chain) {
//            this.is_chain = is_chain;
//        }
//
//        public String getInvite_rate() {
//            return invite_rate;
//        }
//
//        public void setInvite_rate(String invite_rate) {
//            this.invite_rate = invite_rate;
//        }
//        public String getUrl() {
//            return url;
//        }
//
//        public void setUrl(String url) {
//            this.url = url;
//        }
//
//        public String getPic() {
//            return pic;
//        }
//
//        public void setPic(String pic) {
//            this.pic = pic;
//        }
//
//        public String getPic24() {
//            return pic24;
//        }
//
//        public void setPic24(String pic24) {
//            this.pic24 = pic24;
//        }
//
//        public String getPic36() {
//            return pic36;
//        }
//
//        public void setPic36(String pic36) {
//            this.pic36 = pic36;
//        }
    }
}