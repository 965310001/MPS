package com.mingpinmall.classz.ui.vm.bean;

import android.databinding.Bindable;
import com.goldze.common.dmvvm.base.bean.BaseBean;
import com.mingpinmall.classz.BR;

import java.util.List;

public class ClassificationBean extends BaseBean {

//    private int code;
    private DatasBean datas;

//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean extends BaseBean {
        private List<ClassListBean> class_list;

        public List<ClassListBean> getClass_list() {
            return class_list;
        }

        public void setClass_list(List<ClassListBean> class_list) {
            this.class_list = class_list;
        }

        public static class ClassListBean extends BaseBean  {
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
            //            private String type_id;
//            private String type_name;
//            private String gc_parent_id;
//            private String commis_rate;
//            private String gc_sort;
//            private String gc_virtual;
//            private String gc_title;
//            private String gc_keywords;
//            private String gc_description;
//            private String show_type;
//            private String text;
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
//            public String getType_id() {
//                return type_id;
//            }
//
//            public void setType_id(String type_id) {
//                this.type_id = type_id;
//            }
//
//            public String getType_name() {
//                return type_name;
//            }
//
//            public void setType_name(String type_name) {
//                this.type_name = type_name;
//            }
//
//            public String getGc_parent_id() {
//                return gc_parent_id;
//            }
//
//            public void setGc_parent_id(String gc_parent_id) {
//                this.gc_parent_id = gc_parent_id;
//            }
//
//            public String getCommis_rate() {
//                return commis_rate;
//            }
//
//            public void setCommis_rate(String commis_rate) {
//                this.commis_rate = commis_rate;
//            }
//
//            public String getGc_sort() {
//                return gc_sort;
//            }
//
//            public void setGc_sort(String gc_sort) {
//                this.gc_sort = gc_sort;
//            }
//
//            public String getGc_virtual() {
//                return gc_virtual;
//            }
//
//            public void setGc_virtual(String gc_virtual) {
//                this.gc_virtual = gc_virtual;
//            }
//
//            public String getGc_title() {
//                return gc_title;
//            }
//
//            public void setGc_title(String gc_title) {
//                this.gc_title = gc_title;
//            }

//            public String getGc_keywords() {
//                return gc_keywords;
//            }
//
//            public void setGc_keywords(String gc_keywords) {
//                this.gc_keywords = gc_keywords;
//            }
//
//            public String getGc_description() {
//                return gc_description;
//            }
//
//            public void setGc_description(String gc_description) {
//                this.gc_description = gc_description;
//            }

//            public String getShow_type() {
//                return show_type;
//            }
//
//            public void setShow_type(String show_type) {
//                this.show_type = show_type;
//            }


//            public String getText() {
//                return text;
//            }
//
//            public void setText(String text) {
//                this.text = text;
//            }
        }
    }


}
