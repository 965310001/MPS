//package com.mingpinmall.classz.ui.vm.model;
//
//import android.databinding.BaseObservable;
//import android.databinding.Bindable;
//
//import com.mingpinmall.classz.BR;
//import com.mingpinmall.classz.ui.vm.bean.GoodsListInfo;
//
//public class GoodsListInfoViewModel extends BaseObservable {
//
//    private String goods_name;
//    private String goods_image_url;
//
//    private GoodsListInfo info;
//
//    public GoodsListInfoViewModel() {
//    }
//
//    public GoodsListInfoViewModel(GoodsListInfo info) {
//        this.info = info;
//    }
//
//    @Bindable
//    public String getName() {
//        return goods_name;
//    }
//
//    @Bindable
//    public String getGoods_image_url() {
//        return goods_image_url;
//    }
//
//    public void setGoods_image_url(String goods_image_url) {
//        this.goods_image_url = goods_image_url;
//    }
//
//    public void setName(String name) {
//        this.goods_name = name;
//        notifyPropertyChanged(BR.data);
//    }
//
//}
