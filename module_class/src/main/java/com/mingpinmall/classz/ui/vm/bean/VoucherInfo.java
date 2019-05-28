package com.mingpinmall.classz.ui.vm.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;

import java.util.List;

public class VoucherInfo extends BaseBean {

    private List<VoucherListBean> voucher_list;

    public List<VoucherListBean> getVoucher_list() {
        return voucher_list;
    }

    public void setVoucher_list(List<VoucherListBean> voucher_list) {
        this.voucher_list = voucher_list;
    }

    public static class VoucherListBean extends BaseBean {
        /**
         * voucher_t_id : 4
         * voucher_t_title : 测试
         * voucher_t_desc : 111
         * voucher_t_start_date : 1555394155
         * voucher_t_end_date : 1559232000
         * voucher_t_price : 50
         * voucher_t_limit : 100.00
         * voucher_t_store_id : 10
         * voucher_t_storename : qqqqqq
         * voucher_t_sc_id : 2
         * voucher_t_creator_id : 16
         * voucher_t_state : 1
         * voucher_t_total : 11
         * voucher_t_giveout : 0
         * voucher_t_used : 0
         * voucher_t_add_date : 1555470664
         * voucher_t_quotaid : 0
         * voucher_t_points : 0
         * voucher_t_eachlimit : 0
         * voucher_t_styleimg : null
         * voucher_t_customimg : http://192.168.0.44/data/upload/mall/voucher/10/06087381550762632.jpg
         * voucher_t_recommend : 0
         * voucher_t_gettype : 3
         * voucher_t_isbuild : 0
         * voucher_t_mgradelimit : 0
         * voucher_t_sc_name : 服装鞋包
         * voucher_t_gettype_key : free
         * voucher_t_gettype_text : 免费领取
         * voucher_t_state_text : 有效
         * voucher_t_mgradelimittext : V0
         * voucher_t_end_date_text : 2019年05月31日
         */

        private String voucher_t_id;
        private String voucher_t_title;
        private String voucher_t_desc;
        private String voucher_t_start_date;
        private String voucher_t_end_date;
        private String voucher_t_price;
        private String voucher_t_limit;
        private String voucher_t_store_id;
        private String voucher_t_storename;
        private String voucher_t_sc_id;
        private String voucher_t_creator_id;
        private String voucher_t_state;
        private String voucher_t_total;
        private String voucher_t_giveout;
        private String voucher_t_used;
        private String voucher_t_add_date;
        private String voucher_t_quotaid;
        private String voucher_t_points;
        private String voucher_t_eachlimit;
        private Object voucher_t_styleimg;
        private String voucher_t_customimg;
        private String voucher_t_recommend;
        private String voucher_t_gettype;
        private String voucher_t_isbuild;
        private String voucher_t_mgradelimit;
        private String voucher_t_sc_name;
        private String voucher_t_gettype_key;
        private String voucher_t_gettype_text;
        private String voucher_t_state_text;
        private String voucher_t_mgradelimittext;
        private String voucher_t_end_date_text;

        //面额50元


        public String getVoucher_t_id() {
            return voucher_t_id;
        }

        public void setVoucher_t_id(String voucher_t_id) {
            this.voucher_t_id = voucher_t_id;
        }

        public String getVoucher_t_title() {
            return voucher_t_title;
        }

        public void setVoucher_t_title(String voucher_t_title) {
            this.voucher_t_title = voucher_t_title;
        }

        public String getVoucher_t_desc() {
            return voucher_t_desc;
        }

        public void setVoucher_t_desc(String voucher_t_desc) {
            this.voucher_t_desc = voucher_t_desc;
        }

        public String getVoucher_t_start_date() {
            return voucher_t_start_date;
        }

        public void setVoucher_t_start_date(String voucher_t_start_date) {
            this.voucher_t_start_date = voucher_t_start_date;
        }

        public String getVoucher_t_end_date() {
            return voucher_t_end_date;
        }

        public void setVoucher_t_end_date(String voucher_t_end_date) {
            this.voucher_t_end_date = voucher_t_end_date;
        }

        public String getVoucher_t_price() {
            return String.format("面额<font size=\"10\">%s</font>元",voucher_t_price);
        }

        public void setVoucher_t_price(String voucher_t_price) {
            this.voucher_t_price = voucher_t_price;
        }

        public String getVoucher_t_limit() {
            return String.format("需消费%s元使用", voucher_t_limit);
        }

        public void setVoucher_t_limit(String voucher_t_limit) {
            this.voucher_t_limit = voucher_t_limit;
        }

        public String getVoucher_t_store_id() {
            return voucher_t_store_id;
        }

        public void setVoucher_t_store_id(String voucher_t_store_id) {
            this.voucher_t_store_id = voucher_t_store_id;
        }

        public String getVoucher_t_storename() {
            return voucher_t_storename;
        }

        public void setVoucher_t_storename(String voucher_t_storename) {
            this.voucher_t_storename = voucher_t_storename;
        }

        public String getVoucher_t_sc_id() {
            return voucher_t_sc_id;
        }

        public void setVoucher_t_sc_id(String voucher_t_sc_id) {
            this.voucher_t_sc_id = voucher_t_sc_id;
        }

        public String getVoucher_t_creator_id() {
            return voucher_t_creator_id;
        }

        public void setVoucher_t_creator_id(String voucher_t_creator_id) {
            this.voucher_t_creator_id = voucher_t_creator_id;
        }

        public String getVoucher_t_state() {
            return voucher_t_state;
        }

        public void setVoucher_t_state(String voucher_t_state) {
            this.voucher_t_state = voucher_t_state;
        }

        public String getVoucher_t_total() {
            return voucher_t_total;
        }

        public void setVoucher_t_total(String voucher_t_total) {
            this.voucher_t_total = voucher_t_total;
        }

        public String getVoucher_t_giveout() {
            return voucher_t_giveout;
        }

        public void setVoucher_t_giveout(String voucher_t_giveout) {
            this.voucher_t_giveout = voucher_t_giveout;
        }

        public String getVoucher_t_used() {
            return voucher_t_used;
        }

        public void setVoucher_t_used(String voucher_t_used) {
            this.voucher_t_used = voucher_t_used;
        }

        public String getVoucher_t_add_date() {
            return voucher_t_add_date;
        }

        public void setVoucher_t_add_date(String voucher_t_add_date) {
            this.voucher_t_add_date = voucher_t_add_date;
        }

        public String getVoucher_t_quotaid() {
            return voucher_t_quotaid;
        }

        public void setVoucher_t_quotaid(String voucher_t_quotaid) {
            this.voucher_t_quotaid = voucher_t_quotaid;
        }

        public String getVoucher_t_points() {
            return voucher_t_points;
        }

        public void setVoucher_t_points(String voucher_t_points) {
            this.voucher_t_points = voucher_t_points;
        }

        public String getVoucher_t_eachlimit() {
            return voucher_t_eachlimit;
        }

        public void setVoucher_t_eachlimit(String voucher_t_eachlimit) {
            this.voucher_t_eachlimit = voucher_t_eachlimit;
        }

        public Object getVoucher_t_styleimg() {
            return voucher_t_styleimg;
        }

        public void setVoucher_t_styleimg(Object voucher_t_styleimg) {
            this.voucher_t_styleimg = voucher_t_styleimg;
        }

        public String getVoucher_t_customimg() {
            return voucher_t_customimg;
        }

        public void setVoucher_t_customimg(String voucher_t_customimg) {
            this.voucher_t_customimg = voucher_t_customimg;
        }

        public String getVoucher_t_recommend() {
            return voucher_t_recommend;
        }

        public void setVoucher_t_recommend(String voucher_t_recommend) {
            this.voucher_t_recommend = voucher_t_recommend;
        }

        public String getVoucher_t_gettype() {
            return voucher_t_gettype;
        }

        public void setVoucher_t_gettype(String voucher_t_gettype) {
            this.voucher_t_gettype = voucher_t_gettype;
        }

        public String getVoucher_t_isbuild() {
            return voucher_t_isbuild;
        }

        public void setVoucher_t_isbuild(String voucher_t_isbuild) {
            this.voucher_t_isbuild = voucher_t_isbuild;
        }

        public String getVoucher_t_mgradelimit() {
            return voucher_t_mgradelimit;
        }

        public void setVoucher_t_mgradelimit(String voucher_t_mgradelimit) {
            this.voucher_t_mgradelimit = voucher_t_mgradelimit;
        }

        public String getVoucher_t_sc_name() {
            return voucher_t_sc_name;
        }

        public void setVoucher_t_sc_name(String voucher_t_sc_name) {
            this.voucher_t_sc_name = voucher_t_sc_name;
        }

        public String getVoucher_t_gettype_key() {
            return voucher_t_gettype_key;
        }

        public void setVoucher_t_gettype_key(String voucher_t_gettype_key) {
            this.voucher_t_gettype_key = voucher_t_gettype_key;
        }

        public String getVoucher_t_gettype_text() {
            return voucher_t_gettype_text;
        }

        public void setVoucher_t_gettype_text(String voucher_t_gettype_text) {
            this.voucher_t_gettype_text = voucher_t_gettype_text;
        }

        public String getVoucher_t_state_text() {
            return voucher_t_state_text;
        }

        public void setVoucher_t_state_text(String voucher_t_state_text) {
            this.voucher_t_state_text = voucher_t_state_text;
        }

        public String getVoucher_t_mgradelimittext() {
            return voucher_t_mgradelimittext;
        }

        public void setVoucher_t_mgradelimittext(String voucher_t_mgradelimittext) {
            this.voucher_t_mgradelimittext = voucher_t_mgradelimittext;
        }

        public String getVoucher_t_end_date_text() {
            return String.format("至%s前使用", voucher_t_end_date_text);
        }

        public void setVoucher_t_end_date_text(String voucher_t_end_date_text) {
            this.voucher_t_end_date_text = voucher_t_end_date_text;
        }
    }
}
