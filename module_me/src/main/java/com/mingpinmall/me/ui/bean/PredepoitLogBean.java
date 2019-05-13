package com.mingpinmall.me.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：账户余额列表
 * @author 小斌
 * @date 2019/4/19
 **/
public class PredepoitLogBean implements Serializable {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {

        private String lg_id;
        private String lg_member_id;
        private String lg_member_name;
        private String lg_admin_name;
        private String lg_type;
        private String lg_av_amount;
        private String lg_freeze_amount;
        private String lg_add_time;
        private String lg_desc;
        private String lg_invite_member_id;
        private String lg_add_time_text;
        private String order_desc;
        private String order_no;

        public String getLg_id() {
            return lg_id;
        }

        public void setLg_id(String lg_id) {
            this.lg_id = lg_id;
        }

        public String getLg_member_id() {
            return lg_member_id;
        }

        public void setLg_member_id(String lg_member_id) {
            this.lg_member_id = lg_member_id;
        }

        public String getLg_member_name() {
            return lg_member_name;
        }

        public void setLg_member_name(String lg_member_name) {
            this.lg_member_name = lg_member_name;
        }

        public String getLg_admin_name() {
            return lg_admin_name;
        }

        public void setLg_admin_name(String lg_admin_name) {
            this.lg_admin_name = lg_admin_name;
        }

        public String getLg_type() {
            return lg_type;
        }

        public void setLg_type(String lg_type) {
            this.lg_type = lg_type;
        }

        public String getLg_av_amount() {
            return lg_av_amount;
        }

        public void setLg_av_amount(String lg_av_amount) {
            this.lg_av_amount = lg_av_amount;
        }

        public String getLg_freeze_amount() {
            return lg_freeze_amount;
        }

        public void setLg_freeze_amount(String lg_freeze_amount) {
            this.lg_freeze_amount = lg_freeze_amount;
        }

        public String getLg_add_time() {
            return lg_add_time;
        }

        public void setLg_add_time(String lg_add_time) {
            this.lg_add_time = lg_add_time;
        }

        public String getLg_desc() {
            return lg_desc;
        }

        public void setLg_desc(String lg_desc) {
            this.lg_desc = lg_desc;
        }

        public String getLg_invite_member_id() {
            return lg_invite_member_id;
        }

        public void setLg_invite_member_id(String lg_invite_member_id) {
            this.lg_invite_member_id = lg_invite_member_id;
        }

        public String getLg_add_time_text() {
            return lg_add_time_text;
        }

        public void setLg_add_time_text(String lg_add_time_text) {
            this.lg_add_time_text = lg_add_time_text;
        }

        public String getOrder_desc() {
            return order_desc;
        }

        public void setOrder_desc(String order_desc) {
            this.order_desc = order_desc;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }
    }
}
