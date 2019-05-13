package com.mingpinmall.me.ui.bean;

import java.io.Serializable;

/**
 * 功能描述：提现详情
 * *@author 小斌
 * @date 2019/4/19
 **/
public class PdcashInfoBean implements Serializable {

    private InfoBean info;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {

        private String pdc_id;
        private String pdc_sn;
        private String pdc_member_id;
        private String pdc_member_name;
        private String pdc_amount;
        private String pdc_bank_name;
        private String pdc_bank_no;
        private String pdc_bank_user;
        private String pdc_add_time;
        private String pdc_payment_time;
        private String pdc_payment_state;
        private String pdc_payment_admin;
        private String pdc_add_time_text;
        private String pdc_payment_time_text;
        private String pdc_payment_state_text;

        public String getPdc_id() {
            return pdc_id;
        }

        public void setPdc_id(String pdc_id) {
            this.pdc_id = pdc_id;
        }

        public String getPdc_sn() {
            return pdc_sn;
        }

        public void setPdc_sn(String pdc_sn) {
            this.pdc_sn = pdc_sn;
        }

        public String getPdc_member_id() {
            return pdc_member_id;
        }

        public void setPdc_member_id(String pdc_member_id) {
            this.pdc_member_id = pdc_member_id;
        }

        public String getPdc_member_name() {
            return pdc_member_name;
        }

        public void setPdc_member_name(String pdc_member_name) {
            this.pdc_member_name = pdc_member_name;
        }

        public String getPdc_amount() {
            return pdc_amount;
        }

        public void setPdc_amount(String pdc_amount) {
            this.pdc_amount = pdc_amount;
        }

        public String getPdc_bank_name() {
            return pdc_bank_name;
        }

        public void setPdc_bank_name(String pdc_bank_name) {
            this.pdc_bank_name = pdc_bank_name;
        }

        public String getPdc_bank_no() {
            return pdc_bank_no;
        }

        public void setPdc_bank_no(String pdc_bank_no) {
            this.pdc_bank_no = pdc_bank_no;
        }

        public String getPdc_bank_user() {
            return pdc_bank_user;
        }

        public void setPdc_bank_user(String pdc_bank_user) {
            this.pdc_bank_user = pdc_bank_user;
        }

        public String getPdc_add_time() {
            return pdc_add_time;
        }

        public void setPdc_add_time(String pdc_add_time) {
            this.pdc_add_time = pdc_add_time;
        }

        public String getPdc_payment_time() {
            return pdc_payment_time;
        }

        public void setPdc_payment_time(String pdc_payment_time) {
            this.pdc_payment_time = pdc_payment_time;
        }

        public String getPdc_payment_state() {
            return pdc_payment_state;
        }

        public void setPdc_payment_state(String pdc_payment_state) {
            this.pdc_payment_state = pdc_payment_state;
        }

        public String getPdc_payment_admin() {
            return pdc_payment_admin;
        }

        public void setPdc_payment_admin(String pdc_payment_admin) {
            this.pdc_payment_admin = pdc_payment_admin;
        }

        public String getPdc_add_time_text() {
            return pdc_add_time_text;
        }

        public void setPdc_add_time_text(String pdc_add_time_text) {
            this.pdc_add_time_text = pdc_add_time_text;
        }

        public String getPdc_payment_time_text() {
            return pdc_payment_time_text;
        }

        public void setPdc_payment_time_text(String pdc_payment_time_text) {
            this.pdc_payment_time_text = pdc_payment_time_text;
        }

        public String getPdc_payment_state_text() {
            return pdc_payment_state_text;
        }

        public void setPdc_payment_state_text(String pdc_payment_state_text) {
            this.pdc_payment_state_text = pdc_payment_state_text;
        }
    }
}
