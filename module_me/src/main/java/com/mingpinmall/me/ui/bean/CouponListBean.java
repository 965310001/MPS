package com.mingpinmall.me.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：店铺代金券
 * 创建人：小斌
 * 创建时间: 2019/4/20
 **/
public class CouponListBean implements Serializable {

    private int type;
    private List<ListBean> list;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {

        private String voucher_id;
        private String voucher_code;
        private String voucher_title;
        private String voucher_desc;
        private String voucher_start_date;
        private String voucher_end_date;
        private String voucher_price;
        private String voucher_limit;
        private String voucher_state;
        private String voucher_order_id;
        private String voucher_store_id;
        private String store_name;
        private String store_id;
        private String store_domain;
        private String voucher_t_customimg;
        private String voucher_state_text;
        private String dates;

        public String getVoucher_id() {
            return voucher_id;
        }

        public void setVoucher_id(String voucher_id) {
            this.voucher_id = voucher_id;
        }

        public String getVoucher_code() {
            return voucher_code;
        }

        public void setVoucher_code(String voucher_code) {
            this.voucher_code = voucher_code;
        }

        public String getVoucher_title() {
            return voucher_title;
        }

        public void setVoucher_title(String voucher_title) {
            this.voucher_title = voucher_title;
        }

        public String getVoucher_desc() {
            return voucher_desc;
        }

        public void setVoucher_desc(String voucher_desc) {
            this.voucher_desc = voucher_desc;
        }

        public String getVoucher_start_date() {
            return voucher_start_date;
        }

        public void setVoucher_start_date(String voucher_start_date) {
            this.voucher_start_date = voucher_start_date;
        }

        public String getVoucher_end_date() {
            return voucher_end_date;
        }

        public void setVoucher_end_date(String voucher_end_date) {
            this.voucher_end_date = voucher_end_date;
        }

        public String getVoucher_price() {
            return voucher_price;
        }

        public void setVoucher_price(String voucher_price) {
            this.voucher_price = voucher_price;
        }

        public String getVoucher_limit() {
            return voucher_limit;
        }

        public void setVoucher_limit(String voucher_limit) {
            this.voucher_limit = voucher_limit;
        }

        public String getVoucher_state() {
            return voucher_state;
        }

        public void setVoucher_state(String voucher_state) {
            this.voucher_state = voucher_state;
        }

        public String getVoucher_order_id() {
            return voucher_order_id;
        }

        public void setVoucher_order_id(String voucher_order_id) {
            this.voucher_order_id = voucher_order_id;
        }

        public String getVoucher_store_id() {
            return voucher_store_id;
        }

        public void setVoucher_store_id(String voucher_store_id) {
            this.voucher_store_id = voucher_store_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getStore_domain() {
            return store_domain;
        }

        public void setStore_domain(String store_domain) {
            this.store_domain = store_domain;
        }

        public String getVoucher_t_customimg() {
            return voucher_t_customimg;
        }

        public void setVoucher_t_customimg(String voucher_t_customimg) {
            this.voucher_t_customimg = voucher_t_customimg;
        }

        public String getVoucher_state_text() {
            return voucher_state_text;
        }

        public void setVoucher_state_text(String voucher_state_text) {
            this.voucher_state_text = voucher_state_text;
        }

        public String getDates() {
            return dates;
        }

        public void setDates(String dates) {
            this.dates = dates;
        }
    }
}
