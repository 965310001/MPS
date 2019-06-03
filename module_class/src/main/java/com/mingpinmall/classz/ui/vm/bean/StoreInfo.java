package com.mingpinmall.classz.ui.vm.bean;

import android.databinding.Bindable;
import android.text.TextUtils;

import com.goldze.common.dmvvm.base.bean.BaseBean;
import com.goldze.common.dmvvm.utils.log.QLog;
import com.mingpinmall.classz.BR;


import java.util.List;

/**
 * 店铺信息
 */
public class StoreInfo extends BaseBean {

    private StoreInfoBean store_info;
    //    private int rec_goods_list_count;
    private List<GoodsInfo> rec_goods_list;
    /*收藏 销量*/
    private List<GoodsInfo> collectdesc_goods_list;
    private List<GoodsInfo> salenumdesc_goods_list;

    public StoreInfoBean getStore_info() {
        return store_info;
    }

    public void setStore_info(StoreInfoBean store_info) {
        this.store_info = store_info;
    }

    public List<GoodsInfo> getRec_goods_list() {
        return rec_goods_list;
    }

    public void setRec_goods_list(List<GoodsInfo> rec_goods_list) {
        this.rec_goods_list = rec_goods_list;
    }

    public List<GoodsInfo> getCollectdesc_goods_list() {
        return collectdesc_goods_list;
    }

    public void setCollectdesc_goods_list(List<GoodsInfo> collectdesc_goods_list) {
        this.collectdesc_goods_list = collectdesc_goods_list;
    }

    public List<GoodsInfo> getSalenumdesc_goods_list() {
        return salenumdesc_goods_list;
    }

    public void setSalenumdesc_goods_list(List<GoodsInfo> salenumdesc_goods_list) {
        this.salenumdesc_goods_list = salenumdesc_goods_list;
    }

    public static class StoreInfoBean extends BaseBean {
        /**
         * store_id : 7
         * store_name : 瑞士邦顿（bestdon）手表旗舰店
         * member_id : 30361
         * store_avatar : http://192.168.0.44/data/upload/mall/store/05262125366598649_sm.jpg
         * goods_count : 6
         * store_collect : 236
         * is_favorate : false
         * is_own_mall : false
         * store_credit_text : 描述: 5.0, 服务: 5.0, 物流: 5.0
         * mb_title_img :
         * mb_sliders : []
         */

        private String store_id;
        private String store_name;
        private String member_id;
        private String store_avatar;
        private int store_collect;
        private boolean is_favorate;
        private boolean is_own_mall;

        /**
         * store_company_name : 桃桃
         * province_id : 4
         * area_info : 山西 大同市 城区
         * store_address : 111
         */

        private String store_company_name;
        private String province_id;
        private String area_info;
        //        private String store_address;
        private String store_time_text;
        /**
         * store_keywords : 瑞士邦顿手表，手表，男表，女表，邦顿手表，bestdon，情侣手表
         */

//        private String store_keywords;
        private String store_zy;

        private String store_workingtime;
        /**
         * sc_name : 品牌手表/流行手表
         */

        private String sc_name;
        private List<StoreCreditNewBean> store_credit_new;

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getStore_avatar() {
            return store_avatar;
        }

        public void setStore_avatar(String store_avatar) {
            this.store_avatar = store_avatar;
        }

        @Bindable
        public int getStore_collect() {
            return store_collect;
        }

        public void setStore_collect(int store_collect) {
            this.store_collect = store_collect;
            notifyPropertyChanged(BR.store_collect);
        }

        @Bindable
        public boolean isIs_favorate() {
            return is_favorate;
        }

        public void setIs_favorate(boolean is_favorate) {
            this.is_favorate = is_favorate;
            notifyPropertyChanged(BR.is_favorate);
        }

        public boolean isIs_own_mall() {
            return is_own_mall;
        }

        public void setIs_own_mall(boolean is_own_mall) {
            this.is_own_mall = is_own_mall;
        }

        public String getStore_company_name() {
            return store_company_name;
        }

        public void setStore_company_name(String store_company_name) {
            this.store_company_name = store_company_name;
        }

        public String getProvince_id() {
            return province_id;
        }

        public void setProvince_id(String province_id) {
            this.province_id = province_id;
        }

        public String getArea_info() {
            return area_info;
        }

        public String getStore_time_text() {
            return store_time_text;
        }

        public void setStore_time_text(String store_time_text) {
            this.store_time_text = store_time_text;
        }

        public void setArea_info(String area_info) {
            this.area_info = area_info;
        }

        public String getStore_zy() {
            QLog.i(store_zy);
            return store_zy;
        }

        public void setStore_zy(String store_zy) {
            this.store_zy = store_zy;
        }

        public String getStore_workingtime() {
            return store_workingtime;
        }

        public void setStore_workingtime(String store_workingtime) {
            this.store_workingtime = store_workingtime;
        }

        public String getSc_name() {
            if (TextUtils.isEmpty(sc_name)) {
                return "";
            }
            return sc_name;
        }

        public void setSc_name(String sc_name) {
            this.sc_name = sc_name;
        }

        public List<StoreCreditNewBean> getStore_credit_new() {
            return store_credit_new;
        }

        public void setStore_credit_new(List<StoreCreditNewBean> store_credit_new) {
            this.store_credit_new = store_credit_new;
        }

        public static class StoreCreditNewBean extends BaseBean {
            /**
             * text : 描述
             * credit : 5.0
             * percent : ----
             * percent_class : equal
             * percent_text : 持平
             */

            private String text;
            private String credit;
            private String percent;
            private String percent_class;
            private String percent_text;

            private String context;

            @Bindable
            public String getContext() {
                if (TextUtils.isEmpty(context)) {
                    context = getCredit() + " 与同行业" + getPercent_text() + " " + getPercent();
                }
                return context;
            }

            public void setContext(String context) {
                this.context = context;
                notifyPropertyChanged(BR.context);
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getCredit() {
                if (TextUtils.isEmpty(credit)) {
                    return "";
                }
                return credit;
            }

            public void setCredit(String credit) {
                this.credit = credit;
            }

            public String getPercent() {
                if (TextUtils.isEmpty(percent)) {
                    return "";
                }
                return percent;
            }

            public void setPercent(String percent) {
                this.percent = percent;
            }

            public String getPercent_class() {
                return percent_class;
            }

            public void setPercent_class(String percent_class) {
                this.percent_class = percent_class;
            }

            public String getPercent_text() {
                if (TextUtils.isEmpty(percent_text)) {
                    return "";
                }
                return percent_text;
            }

            public void setPercent_text(String percent_text) {
                this.percent_text = percent_text;
            }
        }
    }
}
