package com.mingpinmall.classz.ui.vm.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;

import java.util.List;

/**
 * @author 小斌
 * @data 2019/5/24
 **/
public class ScreeningBean extends BaseBean {

    private List<AreaListBean> area_list;
    private List<ContractListBean> contract_list;
    private List<GcListBean> gc_list;
    private List<GoodsTypeBean> goods_type;
    private List<StoreTypeBean> store_type;

    public List<AreaListBean> getArea_list() {
        return area_list;
    }

    public void setArea_list(List<AreaListBean> area_list) {
        this.area_list = area_list;
    }

    public List<ContractListBean> getContract_list() {
        return contract_list;
    }

    public void setContract_list(List<ContractListBean> contract_list) {
        this.contract_list = contract_list;
    }

    public List<GcListBean> getGc_list() {
        return gc_list;
    }

    public void setGc_list(List<GcListBean> gc_list) {
        this.gc_list = gc_list;
    }

    public List<GoodsTypeBean> getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(List<GoodsTypeBean> goods_type) {
        this.goods_type = goods_type;
    }

    public List<StoreTypeBean> getStore_type() {
        return store_type;
    }

    public void setStore_type(List<StoreTypeBean> store_type) {
        this.store_type = store_type;
    }

    public static class AreaListBean {
        /**
         * area_id : 1
         * area_name : 北京
         */

        private String area_id;
        private String area_name;

        public String getArea_id() {
            return area_id == null ? "" : area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id == null ? "" : area_id;
        }

        public String getArea_name() {
            return area_name == null ? "" : area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name == null ? "" : area_name;
        }
    }

    public static class ContractListBean {
        /**
         * id : 1
         * name : 7天退货
         */

        private String id;
        private String name;

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id == null ? "" : id;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name == null ? "" : name;
        }
    }

    public static class GcListBean {
        /**
         * gc_id : 427
         * gc_name : 光学镜架
         * type_id : 7
         * type_name : 光学镜架
         * gc_parent_id : 0
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

    public static class GoodsTypeBean {
        /**
         * id : gift
         * name : 赠品
         */

        private String id;
        private String name;

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id == null ? "" : id;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name == null ? "" : name;
        }
    }

    public static class StoreTypeBean {
        /**
         * id : own_mall
         * name : 平台自营
         */

        private String id;
        private String name;

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id == null ? "" : id;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name == null ? "" : name;
        }
    }
}
