package com.mingpinmall.me.ui.bean;

import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：我的红包列表
 *
 * @author 小斌
 * @date 2019/4/20
 **/
public class PacketListBean implements Serializable {

    private List<RedpacketListBean> redpacket_list;

    public List<RedpacketListBean> getRedpacket_list() {
        return redpacket_list;
    }

    public void setRedpacket_list(List<RedpacketListBean> redpacket_list) {
        this.redpacket_list = redpacket_list;
    }

    public static class RedpacketListBean implements MultiItemEntity {

        private String rpacket_id;
        private String rpacket_code;
        private String rpacket_t_id;
        private String rpacket_title;
        private String rpacket_desc;
        private String rpacket_start_date;
        private String rpacket_end_date;
        private String rpacket_price;
        private String rpacket_limit;
        private String rpacket_state;
        private String rpacket_active_date;
        private String rpacket_owner_id;
        private String rpacket_owner_name;
        private String rpacket_order_id;
        private String rpacket_pwd;
        private Object rpacket_pwd2;
        private String rpacket_customimg;
        private String rpacket_customimg_url;
        private String rpacket_state_text;
        private String rpacket_state_key;
        private String dates;

        public String getRpacket_id() {
            return rpacket_id == null ? "" : rpacket_id;
        }

        public void setRpacket_id(String rpacket_id) {
            this.rpacket_id = rpacket_id == null ? "" : rpacket_id;
        }

        public String getRpacket_code() {
            return rpacket_code == null ? "" : rpacket_code;
        }

        public void setRpacket_code(String rpacket_code) {
            this.rpacket_code = rpacket_code == null ? "" : rpacket_code;
        }

        public String getRpacket_t_id() {
            return rpacket_t_id == null ? "" : rpacket_t_id;
        }

        public void setRpacket_t_id(String rpacket_t_id) {
            this.rpacket_t_id = rpacket_t_id == null ? "" : rpacket_t_id;
        }

        public String getRpacket_title() {
            return rpacket_title == null ? "" : rpacket_title;
        }

        public void setRpacket_title(String rpacket_title) {
            this.rpacket_title = rpacket_title == null ? "" : rpacket_title;
        }

        public String getRpacket_desc() {
            return rpacket_desc == null ? "" : rpacket_desc;
        }

        public void setRpacket_desc(String rpacket_desc) {
            this.rpacket_desc = rpacket_desc == null ? "" : rpacket_desc;
        }

        public String getRpacket_start_date() {
            return rpacket_start_date == null ? "" : rpacket_start_date;
        }

        public void setRpacket_start_date(String rpacket_start_date) {
            this.rpacket_start_date = rpacket_start_date == null ? "" : rpacket_start_date;
        }

        public String getRpacket_end_date() {
            return rpacket_end_date == null ? "" : rpacket_end_date;
        }

        public void setRpacket_end_date(String rpacket_end_date) {
            this.rpacket_end_date = rpacket_end_date == null ? "" : rpacket_end_date;
        }

        public String getRpacket_price() {
            return rpacket_price == null ? "" : rpacket_price;
        }

        public void setRpacket_price(String rpacket_price) {
            this.rpacket_price = rpacket_price == null ? "" : rpacket_price;
        }

        public String getRpacket_limit() {
            return rpacket_limit == null ? "" : rpacket_limit;
        }

        public void setRpacket_limit(String rpacket_limit) {
            this.rpacket_limit = rpacket_limit == null ? "" : rpacket_limit;
        }

        public String getRpacket_state() {
            return rpacket_state == null ? "" : rpacket_state;
        }

        public void setRpacket_state(String rpacket_state) {
            this.rpacket_state = rpacket_state == null ? "" : rpacket_state;
        }

        public String getRpacket_active_date() {
            return rpacket_active_date == null ? "" : rpacket_active_date;
        }

        public void setRpacket_active_date(String rpacket_active_date) {
            this.rpacket_active_date = rpacket_active_date == null ? "" : rpacket_active_date;
        }

        public String getRpacket_owner_id() {
            return rpacket_owner_id == null ? "" : rpacket_owner_id;
        }

        public void setRpacket_owner_id(String rpacket_owner_id) {
            this.rpacket_owner_id = rpacket_owner_id == null ? "" : rpacket_owner_id;
        }

        public String getRpacket_owner_name() {
            return rpacket_owner_name == null ? "" : rpacket_owner_name;
        }

        public void setRpacket_owner_name(String rpacket_owner_name) {
            this.rpacket_owner_name = rpacket_owner_name == null ? "" : rpacket_owner_name;
        }

        public String getRpacket_order_id() {
            return rpacket_order_id == null ? "" : rpacket_order_id;
        }

        public void setRpacket_order_id(String rpacket_order_id) {
            this.rpacket_order_id = rpacket_order_id == null ? "" : rpacket_order_id;
        }

        public String getRpacket_pwd() {
            return rpacket_pwd == null ? "" : rpacket_pwd;
        }

        public void setRpacket_pwd(String rpacket_pwd) {
            this.rpacket_pwd = rpacket_pwd == null ? "" : rpacket_pwd;
        }

        public Object getRpacket_pwd2() {
            return rpacket_pwd2;
        }

        public void setRpacket_pwd2(Object rpacket_pwd2) {
            this.rpacket_pwd2 = rpacket_pwd2;
        }

        public String getRpacket_customimg() {
            return rpacket_customimg == null ? "" : rpacket_customimg;
        }

        public void setRpacket_customimg(String rpacket_customimg) {
            this.rpacket_customimg = rpacket_customimg == null ? "" : rpacket_customimg;
        }

        public String getRpacket_customimg_url() {
            return rpacket_customimg_url == null ? "" : rpacket_customimg_url;
        }

        public void setRpacket_customimg_url(String rpacket_customimg_url) {
            this.rpacket_customimg_url = rpacket_customimg_url == null ? "" : rpacket_customimg_url;
        }

        public String getRpacket_state_text() {
            return rpacket_state_text == null ? "" : rpacket_state_text;
        }

        public void setRpacket_state_text(String rpacket_state_text) {
            this.rpacket_state_text = rpacket_state_text == null ? "" : rpacket_state_text;
        }

        public String getRpacket_state_key() {
            return rpacket_state_key == null ? "" : rpacket_state_key;
        }

        public void setRpacket_state_key(String rpacket_state_key) {
            this.rpacket_state_key = rpacket_state_key == null ? "" : rpacket_state_key;
        }

        public String getDates() {
            return dates == null ? "" : dates;
        }

        public void setDates(String dates) {
            this.dates = dates == null ? "" : dates;
        }

        @Override
        public int getItemType() {
            return TextUtils.equals("0", getRpacket_state()) ? 0 : 1;
        }
    }
}
