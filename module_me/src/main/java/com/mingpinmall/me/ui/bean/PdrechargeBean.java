package com.mingpinmall.me.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：充值明细
 * 创建人：小斌
 * 创建时间: 2019/4/19
 **/
public class PdrechargeBean implements Serializable {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {

        private String pdr_id;
        private String pdr_sn;
        private String pdr_member_id;
        private String pdr_member_name;
        private String pdr_amount;
        private String pdr_payment_code;
        private String pdr_payment_name;
        private String pdr_trade_sn;
        private String pdr_add_time;
        private String pdr_payment_state;
        private String pdr_payment_time;
        private String pdr_admin;
        private String pdr_add_time_text;
        private String pdr_payment_state_text;

        public String getPdr_id() {
            return pdr_id;
        }

        public void setPdr_id(String pdr_id) {
            this.pdr_id = pdr_id;
        }

        public String getPdr_sn() {
            return pdr_sn;
        }

        public void setPdr_sn(String pdr_sn) {
            this.pdr_sn = pdr_sn;
        }

        public String getPdr_member_id() {
            return pdr_member_id;
        }

        public void setPdr_member_id(String pdr_member_id) {
            this.pdr_member_id = pdr_member_id;
        }

        public String getPdr_member_name() {
            return pdr_member_name;
        }

        public void setPdr_member_name(String pdr_member_name) {
            this.pdr_member_name = pdr_member_name;
        }

        public String getPdr_amount() {
            return pdr_amount;
        }

        public void setPdr_amount(String pdr_amount) {
            this.pdr_amount = pdr_amount;
        }

        public String getPdr_payment_code() {
            return pdr_payment_code;
        }

        public void setPdr_payment_code(String pdr_payment_code) {
            this.pdr_payment_code = pdr_payment_code;
        }

        public String getPdr_payment_name() {
            return pdr_payment_name;
        }

        public void setPdr_payment_name(String pdr_payment_name) {
            this.pdr_payment_name = pdr_payment_name;
        }

        public String getPdr_trade_sn() {
            return pdr_trade_sn;
        }

        public void setPdr_trade_sn(String pdr_trade_sn) {
            this.pdr_trade_sn = pdr_trade_sn;
        }

        public String getPdr_add_time() {
            return pdr_add_time;
        }

        public void setPdr_add_time(String pdr_add_time) {
            this.pdr_add_time = pdr_add_time;
        }

        public String getPdr_payment_state() {
            return pdr_payment_state;
        }

        public void setPdr_payment_state(String pdr_payment_state) {
            this.pdr_payment_state = pdr_payment_state;
        }

        public String getPdr_payment_time() {
            return pdr_payment_time;
        }

        public void setPdr_payment_time(String pdr_payment_time) {
            this.pdr_payment_time = pdr_payment_time;
        }

        public String getPdr_admin() {
            return pdr_admin;
        }

        public void setPdr_admin(String pdr_admin) {
            this.pdr_admin = pdr_admin;
        }

        public String getPdr_add_time_text() {
            return pdr_add_time_text;
        }

        public void setPdr_add_time_text(String pdr_add_time_text) {
            this.pdr_add_time_text = pdr_add_time_text;
        }

        public String getPdr_payment_state_text() {
            return pdr_payment_state_text;
        }

        public void setPdr_payment_state_text(String pdr_payment_state_text) {
            this.pdr_payment_state_text = pdr_payment_state_text;
        }
    }
}
