package com.mingpinmall.classz.ui.vm.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;

/**
 * @author 小斌
 * @data 2019/5/25
 **/
public class ClassGoodsBean extends BaseBean {

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
