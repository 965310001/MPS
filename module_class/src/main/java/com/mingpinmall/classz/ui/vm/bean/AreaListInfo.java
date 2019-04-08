package com.mingpinmall.classz.ui.vm.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 省 市 区
 */
public class AreaListInfo extends BaseBean {

    private List<AreaListBean> area_list;

    public List<AreaListBean> getArea_list() {
        return area_list;
    }

    public void setArea_list(List<AreaListBean> area_list) {
        this.area_list = area_list;
    }

    public static class AreaListBean {
        /**
         * area_id : 1
         * area_name : 北京
         */
        @SerializedName("area_id")
        public String areaId;
        @SerializedName("area_name")
        public String areaName;
    }
}
