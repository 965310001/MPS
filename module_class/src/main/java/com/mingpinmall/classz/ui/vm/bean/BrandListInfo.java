package com.mingpinmall.classz.ui.vm.bean;

import android.view.View;

import com.goldze.common.dmvvm.base.bean.BaseBean;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 品牌
 */
public class BrandListInfo extends BaseBean {

    private DatasBean datas;

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean extends BaseBean {
        private List<BrandListBean> brand_list;

        public List<BrandListBean> getBrand_list() {
            return brand_list;
        }


        public void setBrand_list(List<BrandListBean> brand_list) {
            this.brand_list = brand_list;
        }

        public static class BrandListBean extends BaseBean {
            /**
             * brand_id : 366
             * brand_name : bestdon/邦顿
             * brand_pic : http://39.108.254.185/data/upload/mall/brand/05175109678434826_small.jpg
             */

            private String brand_id;
            private String brand_name;
            private String brand_pic;

            public String getBrand_id() {
                return brand_id;
            }

            public void setBrand_id(String brand_id) {
                this.brand_id = brand_id;
            }

            public String getBrand_name() {
                return brand_name;
            }

            public void setBrand_name(String brand_name) {
                this.brand_name = brand_name;
            }

            public String getBrand_pic() {
                return brand_pic;
            }

            public void setBrand_pic(String brand_pic) {
                this.brand_pic = brand_pic;
            }

            public void click(View view) {
                Map<String, Object> map = new HashMap<>();
                map.put("type", 1);
                map.put("id", brand_id);
                ActivityToActivity.toActivity(ARouterConfig.classify.PRODUCTSACTIVITY, map);
            }
        }
    }
}
