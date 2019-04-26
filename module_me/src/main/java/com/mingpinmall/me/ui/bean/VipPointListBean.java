package com.mingpinmall.me.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：会员积分记录
 * 创建人：小斌
 * 创建时间: 2019/4/20
 **/
public class VipPointListBean implements Serializable {

    private List<LogListBean> log_list;

    public List<LogListBean> getLog_list() {
        return log_list;
    }

    public void setLog_list(List<LogListBean> log_list) {
        this.log_list = log_list;
    }

    public static class LogListBean {

        private String pl_id;
        private String pl_memberid;
        private String pl_membername;
        private Object pl_adminid;
        private Object pl_adminname;
        private String pl_points;
        private String pl_addtime;
        private String pl_desc;
        private String pl_stage;
        private String date;

        public String getPl_id() {
            return pl_id;
        }

        public void setPl_id(String pl_id) {
            this.pl_id = pl_id;
        }

        public String getPl_memberid() {
            return pl_memberid;
        }

        public void setPl_memberid(String pl_memberid) {
            this.pl_memberid = pl_memberid;
        }

        public String getPl_membername() {
            return pl_membername;
        }

        public void setPl_membername(String pl_membername) {
            this.pl_membername = pl_membername;
        }

        public Object getPl_adminid() {
            return pl_adminid;
        }

        public void setPl_adminid(Object pl_adminid) {
            this.pl_adminid = pl_adminid;
        }

        public Object getPl_adminname() {
            return pl_adminname;
        }

        public void setPl_adminname(Object pl_adminname) {
            this.pl_adminname = pl_adminname;
        }

        public String getPl_points() {
            return pl_points;
        }

        public void setPl_points(String pl_points) {
            this.pl_points = pl_points;
        }

        public String getPl_addtime() {
            return pl_addtime;
        }

        public void setPl_addtime(String pl_addtime) {
            this.pl_addtime = pl_addtime;
        }

        public String getPl_desc() {
            return pl_desc;
        }

        public void setPl_desc(String pl_desc) {
            this.pl_desc = pl_desc;
        }

        public String getPl_stage() {
            return pl_stage;
        }

        public void setPl_stage(String pl_stage) {
            this.pl_stage = pl_stage;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
