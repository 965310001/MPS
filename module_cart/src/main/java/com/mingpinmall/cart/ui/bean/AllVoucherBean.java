package com.mingpinmall.cart.ui.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;

import java.util.List;

/**
 * @author 小斌
 * @data 2019/5/29
 **/
public class AllVoucherBean extends BaseBean {

    private List<VoucherListBean> voucher_list;

    public List<VoucherListBean> getVoucher_list() {
        return voucher_list;
    }

    public void setVoucher_list(List<VoucherListBean> voucher_list) {
        this.voucher_list = voucher_list;
    }

    public static class VoucherListBean {

        private String voucher_t_id;
        private String voucher_t_title;
        private String voucher_t_start_date;
        private String voucher_t_end_date;
        private String voucher_t_price;
        private String voucher_t_state;
        private String voucher_t_limit;
        private String voucher_t_store_id;
        private String voucher_t_storename;
        private String voucher_t_total;
        private String voucher_t_giveout;
        private String voucher_t_customimg;
        private String voucher_t_sc_name;
        private String voucher_t_state_text;
        private String voucher_t_mgradelimittext;
        private String voucher_t_end_date_text;

        public String getVoucher_t_id() {
            return voucher_t_id == null ? "" : voucher_t_id;
        }

        public void setVoucher_t_id(String voucher_t_id) {
            this.voucher_t_id = voucher_t_id == null ? "" : voucher_t_id;
        }

        public String getVoucher_t_title() {
            return voucher_t_title == null ? "" : voucher_t_title;
        }

        public void setVoucher_t_title(String voucher_t_title) {
            this.voucher_t_title = voucher_t_title == null ? "" : voucher_t_title;
        }

        public String getVoucher_t_start_date() {
            return voucher_t_start_date == null ? "" : voucher_t_start_date;
        }

        public void setVoucher_t_start_date(String voucher_t_start_date) {
            this.voucher_t_start_date = voucher_t_start_date == null ? "" : voucher_t_start_date;
        }

        public String getVoucher_t_end_date() {
            return voucher_t_end_date == null ? "" : voucher_t_end_date;
        }

        public void setVoucher_t_end_date(String voucher_t_end_date) {
            this.voucher_t_end_date = voucher_t_end_date == null ? "" : voucher_t_end_date;
        }

        public String getVoucher_t_price() {
            return voucher_t_price == null ? "" : voucher_t_price;
        }

        public void setVoucher_t_price(String voucher_t_price) {
            this.voucher_t_price = voucher_t_price == null ? "" : voucher_t_price;
        }

        public String getVoucher_t_state() {
            return voucher_t_state == null ? "" : voucher_t_state;
        }

        public void setVoucher_t_state(String voucher_t_state) {
            this.voucher_t_state = voucher_t_state == null ? "" : voucher_t_state;
        }

        public String getVoucher_t_limit() {
            return voucher_t_limit == null ? "" : voucher_t_limit;
        }

        public void setVoucher_t_limit(String voucher_t_limit) {
            this.voucher_t_limit = voucher_t_limit == null ? "" : voucher_t_limit;
        }

        public String getVoucher_t_store_id() {
            return voucher_t_store_id == null ? "" : voucher_t_store_id;
        }

        public void setVoucher_t_store_id(String voucher_t_store_id) {
            this.voucher_t_store_id = voucher_t_store_id == null ? "" : voucher_t_store_id;
        }

        public String getVoucher_t_storename() {
            return voucher_t_storename == null ? "" : voucher_t_storename;
        }

        public void setVoucher_t_storename(String voucher_t_storename) {
            this.voucher_t_storename = voucher_t_storename == null ? "" : voucher_t_storename;
        }

        public String getVoucher_t_total() {
            return voucher_t_total == null ? "" : voucher_t_total;
        }

        public void setVoucher_t_total(String voucher_t_total) {
            this.voucher_t_total = voucher_t_total == null ? "" : voucher_t_total;
        }

        public String getVoucher_t_giveout() {
            return voucher_t_giveout == null ? "" : voucher_t_giveout;
        }

        public void setVoucher_t_giveout(String voucher_t_giveout) {
            this.voucher_t_giveout = voucher_t_giveout == null ? "" : voucher_t_giveout;
        }

        public String getVoucher_t_customimg() {
            return voucher_t_customimg == null ? "" : voucher_t_customimg;
        }

        public void setVoucher_t_customimg(String voucher_t_customimg) {
            this.voucher_t_customimg = voucher_t_customimg == null ? "" : voucher_t_customimg;
        }

        public String getVoucher_t_sc_name() {
            return voucher_t_sc_name == null ? "" : voucher_t_sc_name;
        }

        public void setVoucher_t_sc_name(String voucher_t_sc_name) {
            this.voucher_t_sc_name = voucher_t_sc_name == null ? "" : voucher_t_sc_name;
        }

        public String getVoucher_t_state_text() {
            return voucher_t_state_text == null ? "" : voucher_t_state_text;
        }

        public void setVoucher_t_state_text(String voucher_t_state_text) {
            this.voucher_t_state_text = voucher_t_state_text == null ? "" : voucher_t_state_text;
        }

        public String getVoucher_t_mgradelimittext() {
            return voucher_t_mgradelimittext == null ? "" : voucher_t_mgradelimittext;
        }

        public void setVoucher_t_mgradelimittext(String voucher_t_mgradelimittext) {
            this.voucher_t_mgradelimittext = voucher_t_mgradelimittext == null ? "" : voucher_t_mgradelimittext;
        }

        public String getVoucher_t_end_date_text() {
            return voucher_t_end_date_text == null ? "" : voucher_t_end_date_text;
        }

        public void setVoucher_t_end_date_text(String voucher_t_end_date_text) {
            this.voucher_t_end_date_text = voucher_t_end_date_text == null ? "" : voucher_t_end_date_text;
        }
    }
}
