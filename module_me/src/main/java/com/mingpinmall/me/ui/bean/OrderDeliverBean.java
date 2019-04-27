package com.mingpinmall.me.ui.bean;

import java.io.Serializable;

/**
 * 功能描述：订单物流信息
 * 创建人：小斌
 * 创建时间: 2019/4/27
 **/
public class OrderDeliverBean implements Serializable {

    private DeliverInfoBean deliver_info;

    public DeliverInfoBean getDeliver_info() {
        return deliver_info;
    }

    public void setDeliver_info(DeliverInfoBean deliver_info) {
        this.deliver_info = deliver_info;
    }

    public static class DeliverInfoBean {

        private String time;
        private String ftime;
        private String context;
        private String location;

        public String getTime() {
            return time == null ? "" : time;
        }

        public void setTime(String time) {
            this.time = time == null ? "" : time;
        }

        public String getFtime() {
            return ftime == null ? "" : ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime == null ? "" : ftime;
        }

        public String getContext() {
            return context == null ? "" : context;
        }

        public void setContext(String context) {
            this.context = context == null ? "" : context;
        }

        public String getLocation() {
            return location == null ? "" : location;
        }

        public void setLocation(String location) {
            this.location = location == null ? "" : location;
        }
    }
}
