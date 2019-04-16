package com.mingpinmall.classz.ui.vm.bean;


import android.databinding.Bindable;

import com.goldze.common.dmvvm.base.bean.BaseBean;
import com.mingpinmall.classz.BR;

import java.util.List;

public class GoodsListInfo extends BaseBean {

    private boolean hasmore;
    private int page_total;
    private DatasBean datas;
    /*收藏 销量*/
    private List<GoodsInfo> collectdesc_goods_list;
    private List<GoodsInfo> salenumdesc_goods_list;

    public boolean isHasmore() {
        return hasmore;
    }

    public void setHasmore(boolean hasmore) {
        this.hasmore = hasmore;
    }

    public int getPage_total() {
        return page_total;
    }

    public void setPage_total(int page_total) {
        this.page_total = page_total;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public class DatasBean extends BaseBean {
        private List<GoodsInfo> goods_list;

        @Bindable
        public List<GoodsInfo> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsInfo> goods_list) {
            this.goods_list = goods_list;
            notifyPropertyChanged(BR.goods_list);
        }
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

    @Override
    public String toString() {
        return "GoodsListInfo{" +
                "hasmore=" + hasmore +
                ", page_total=" + page_total +
                ", datas=" + datas +
                '}';
    }
}
