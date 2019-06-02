package com.mingpinmall.classz.ui.vm.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;

/**
 * @author 小斌
 * @data 2019/6/2
 **/
public class HolosBean extends BaseBean {

    private String goods_id;
    private String goods_storage;
    private String goods_image;

    public String getGoods_id() {
        return goods_id == null ? "" : goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id == null ? "" : goods_id;
    }

    public String getGoods_storage() {
        return goods_storage == null ? "" : goods_storage;
    }

    public void setGoods_storage(String goods_storage) {
        this.goods_storage = goods_storage == null ? "" : goods_storage;
    }

    public String getGoods_image() {
        return goods_image == null ? "" : goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image == null ? "" : goods_image;
    }
}
