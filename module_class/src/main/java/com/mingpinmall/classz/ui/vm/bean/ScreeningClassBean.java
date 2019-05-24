package com.mingpinmall.classz.ui.vm.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;

import java.util.List;

/**
 * @author 小斌
 * @data 2019/5/24
 **/
public class ScreeningClassBean extends BaseBean {

    private List<GcListChildBean> gc_list_child;
    private List<GoodsAttrListBean> goods_attr_list;

    public List<GcListChildBean> getGc_list_child() {
        return gc_list_child;
    }

    public void setGc_list_child(List<GcListChildBean> gc_list_child) {
        this.gc_list_child = gc_list_child;
    }

    public List<GoodsAttrListBean> getGoods_attr_list() {
        return goods_attr_list;
    }

    public void setGoods_attr_list(List<GoodsAttrListBean> goods_attr_list) {
        this.goods_attr_list = goods_attr_list;
    }

    public static class GcListChildBean {
        /**
         * gc_id : 439
         * gc_name : 汤姆福特
         * type_id : 0
         * type_name :
         * gc_parent_id : 427
         * commis_rate : 0
         * gc_sort : 0
         * gc_virtual : 0
         * gc_title :
         * gc_keywords :
         * gc_description :
         * show_type : 1
         */

        private String gc_id;
        private String gc_name;
        private String type_id;
        private String type_name;
        private String gc_parent_id;
        private String commis_rate;
        private String gc_sort;
        private String gc_virtual;
        private String gc_title;
        private String gc_keywords;
        private String gc_description;
        private String show_type;

        public String getGc_id() {
            return gc_id == null ? "" : gc_id;
        }

        public void setGc_id(String gc_id) {
            this.gc_id = gc_id == null ? "" : gc_id;
        }

        public String getGc_name() {
            return gc_name == null ? "" : gc_name;
        }

        public void setGc_name(String gc_name) {
            this.gc_name = gc_name == null ? "" : gc_name;
        }

        public String getType_id() {
            return type_id == null ? "" : type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id == null ? "" : type_id;
        }

        public String getType_name() {
            return type_name == null ? "" : type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name == null ? "" : type_name;
        }

        public String getGc_parent_id() {
            return gc_parent_id == null ? "" : gc_parent_id;
        }

        public void setGc_parent_id(String gc_parent_id) {
            this.gc_parent_id = gc_parent_id == null ? "" : gc_parent_id;
        }

        public String getCommis_rate() {
            return commis_rate == null ? "" : commis_rate;
        }

        public void setCommis_rate(String commis_rate) {
            this.commis_rate = commis_rate == null ? "" : commis_rate;
        }

        public String getGc_sort() {
            return gc_sort == null ? "" : gc_sort;
        }

        public void setGc_sort(String gc_sort) {
            this.gc_sort = gc_sort == null ? "" : gc_sort;
        }

        public String getGc_virtual() {
            return gc_virtual == null ? "" : gc_virtual;
        }

        public void setGc_virtual(String gc_virtual) {
            this.gc_virtual = gc_virtual == null ? "" : gc_virtual;
        }

        public String getGc_title() {
            return gc_title == null ? "" : gc_title;
        }

        public void setGc_title(String gc_title) {
            this.gc_title = gc_title == null ? "" : gc_title;
        }

        public String getGc_keywords() {
            return gc_keywords == null ? "" : gc_keywords;
        }

        public void setGc_keywords(String gc_keywords) {
            this.gc_keywords = gc_keywords == null ? "" : gc_keywords;
        }

        public String getGc_description() {
            return gc_description == null ? "" : gc_description;
        }

        public void setGc_description(String gc_description) {
            this.gc_description = gc_description == null ? "" : gc_description;
        }

        public String getShow_type() {
            return show_type == null ? "" : show_type;
        }

        public void setShow_type(String show_type) {
            this.show_type = show_type == null ? "" : show_type;
        }
    }

    public static class GoodsAttrListBean {
        /**
         * name : 适用人群
         * value : [{"attr_value_id":"77","attr_value_name":"通用","attr_id":"15"},{"attr_value_id":"78","attr_value_name":"男士","attr_id":"15"},{"attr_value_id":"79","attr_value_name":"女士","attr_id":"15"},{"attr_value_id":"80","attr_value_name":"儿童","attr_id":"15"}]
         * attr_id : 15
         */

        private String name;
        private int attr_id;
        private List<ValueBean> value;

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name == null ? "" : name;
        }

        public int getAttr_id() {
            return attr_id;
        }

        public void setAttr_id(int attr_id) {
            this.attr_id = attr_id;
        }

        public List<ValueBean> getValue() {
            return value;
        }

        public void setValue(List<ValueBean> value) {
            this.value = value;
        }

        public static class ValueBean {
            /**
             * attr_value_id : 77
             * attr_value_name : 通用
             * attr_id : 15
             */

            private String attr_value_id;
            private String attr_value_name;
            private String attr_id;

            public String getAttr_value_id() {
                return attr_value_id == null ? "" : attr_value_id;
            }

            public void setAttr_value_id(String attr_value_id) {
                this.attr_value_id = attr_value_id == null ? "" : attr_value_id;
            }

            public String getAttr_value_name() {
                return attr_value_name == null ? "" : attr_value_name;
            }

            public void setAttr_value_name(String attr_value_name) {
                this.attr_value_name = attr_value_name == null ? "" : attr_value_name;
            }

            public String getAttr_id() {
                return attr_id == null ? "" : attr_id;
            }

            public void setAttr_id(String attr_id) {
                this.attr_id = attr_id == null ? "" : attr_id;
            }
        }

    }
}
