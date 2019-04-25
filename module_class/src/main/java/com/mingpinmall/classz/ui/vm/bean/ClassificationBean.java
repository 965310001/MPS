package com.mingpinmall.classz.ui.vm.bean;

import android.databinding.Bindable;

import com.goldze.common.dmvvm.base.bean.BaseBean;
import com.mingpinmall.classz.BR;

import java.util.List;

public class ClassificationBean extends BaseBean {

//    private DatasBean datas;
//
//    public DatasBean getDatas() {
//        return datas;
//    }
//
//    public void setDatas(DatasBean datas) {
//        this.datas = datas;
//    }

    //    public static class DatasBean extends BaseBean {
    private List<ClassListBean> class_list;

    public List<ClassListBean> getClass_list() {
        return class_list;
    }

    public void setClass_list(List<ClassListBean> class_list) {
        this.class_list = class_list;
    }

    public static class ClassListBean extends BaseBean {
        /**
         * gc_id : 1
         * gc_name : 服饰鞋帽
         * type_id : 49
         * type_name : 服饰鞋帽
         * gc_parent_id : 0
         * commis_rate : 1
         * gc_sort : 255
         * gc_virtual : 1
         * gc_title : {name} - {sitename}
         * gc_keywords : {name},{sitename}
         * gc_description : {name},{sitename}
         * show_type : 1
         * image : http://39.108.254.185/data/upload/mobile/category/05015381809285330.png
         * text : 女装/男装/内衣/运动/女鞋/男鞋/配饰/童装
         */

        private String gc_id;
        private String gc_name;
        private String image;
        private boolean isSelect;

        public ClassListBean() {
        }

        public ClassListBean(String gc_id, String gc_name, String image, boolean isSelect) {
            this.gc_id = gc_id;
            this.gc_name = gc_name;
            this.image = image;
            this.isSelect = isSelect;
        }

        @Bindable
        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
            notifyPropertyChanged(BR.select);
        }

        public String getGc_id() {
            return gc_id;
        }

        public void setGc_id(String gc_id) {
            this.gc_id = gc_id;
        }

        @Bindable
        public String getGc_name() {
            return gc_name;
        }

        public void setGc_name(String gc_name) {
            this.gc_name = gc_name;
            notifyPropertyChanged(BR.gc_name);
        }

        @Bindable
        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
            notifyPropertyChanged(BR.image);
        }
    }
}