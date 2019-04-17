package com.mingpinmall.home.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/16
 **/
public class ShopClassBean implements Serializable {

    private List<ClassListBean> class_list;

    public List<ClassListBean> getClass_list() {
        return class_list;
    }

    public void setClass_list(List<ClassListBean> class_list) {
        this.class_list = class_list;
    }

    public static class ClassListBean {
        /**
         * sc_id : 19
         * sc_name : 电脑硬件/显示器/电脑周边
         * sc_bail : 0
         * sc_sort : 1
         */

        private String sc_id;
        private String sc_name;
        private String sc_bail;
        private String sc_sort;

        public String getSc_id() {
            return sc_id;
        }

        public void setSc_id(String sc_id) {
            this.sc_id = sc_id;
        }

        public String getSc_name() {
            return sc_name;
        }

        public void setSc_name(String sc_name) {
            this.sc_name = sc_name;
        }

        public String getSc_bail() {
            return sc_bail;
        }

        public void setSc_bail(String sc_bail) {
            this.sc_bail = sc_bail;
        }

        public String getSc_sort() {
            return sc_sort;
        }

        public void setSc_sort(String sc_sort) {
            this.sc_sort = sc_sort;
        }
    }
}
