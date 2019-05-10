package com.mingpinmall.me.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：充值卡充值记录
 * @author 小斌
 * @date 2019/4/20
 **/
public class RCardLogBean implements Serializable {

    private List<LogListBean> log_list;

    public List<LogListBean> getLog_list() {
        return log_list;
    }

    public void setLog_list(List<LogListBean> log_list) {
        this.log_list = log_list;
    }

    public static class LogListBean {

        private String id;
        private String member_id;
        private String member_name;
        private String type;
        private String add_time;
        private String available_amount;
        private String freeze_amount;
        private String description;
        private String add_time_text;
        private String order_desc;
        private String order_no;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getAvailable_amount() {
            return available_amount;
        }

        public void setAvailable_amount(String available_amount) {
            this.available_amount = available_amount;
        }

        public String getFreeze_amount() {
            return freeze_amount;
        }

        public void setFreeze_amount(String freeze_amount) {
            this.freeze_amount = freeze_amount;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAdd_time_text() {
            return add_time_text;
        }

        public void setAdd_time_text(String add_time_text) {
            this.add_time_text = add_time_text;
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
