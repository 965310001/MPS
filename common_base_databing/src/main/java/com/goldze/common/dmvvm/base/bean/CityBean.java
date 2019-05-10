package com.goldze.common.dmvvm.base.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：城市列表Item
 * @author 小斌
 * @date 2019/4/12
 **/
public class CityBean implements Serializable {

    private List<AreaListBean> area_list;

    public List<AreaListBean> getArea_list() {
        return area_list;
    }

    public void setArea_list(List<AreaListBean> area_list) {
        this.area_list = area_list;
    }

    public static class AreaListBean implements Serializable {

        private String area_id;
        private String area_name;

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }
    }
}
