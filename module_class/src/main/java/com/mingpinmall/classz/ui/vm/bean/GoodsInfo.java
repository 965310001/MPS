package com.mingpinmall.classz.ui.vm.bean;

import android.databinding.Bindable;
import android.view.View;

import com.goldze.common.dmvvm.base.bean.BaseBean;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.mingpinmall.classz.BR;
import com.socks.library.KLog;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;


import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

@Entity
public class GoodsInfo extends BaseBean {
    /**
     * goods_id : 109928
     * store_id : 7
     * goods_name : 正品瑞士邦顿(Bestdon)手表BD7108G男士大表盘全自动针机械表精钢军表夜光防水户外运动腕表
     * goods_jingle : 型号BD7108G    100米防水 终身保修 全国包邮 邦顿正品  专柜同款
     * goods_price : 599.00
     * goods_marketprice : 599.00
     * goods_image : 7_05175802468129745.jpg
     * goods_salenum : 0
     * evaluation_good_star : 5
     * evaluation_count : 0
     * is_virtual : 0
     * is_presell : 0
     * is_fcode : 0
     * have_gift : 0
     * store_name : 瑞士邦顿（bestdon）手表旗舰店
     * is_own_mall : 0
     * sole_flag : false
     * group_flag : false
     * xianshi_flag : false
     * goods_image_url : http://39.108.254.185/data/upload/mall/common/default_goods_image_360.gif
     */

    @Id
    private String goods_id;
    private String store_id;
    private String goods_name;
    private String goods_jingle;
    private String goods_price;
    private String goods_promotion_price;
    private String goods_marketprice;
    private String goods_image;
    private String goods_salenum;
    private String evaluation_good_star;
    private String evaluation_count;
    private String is_virtual;
    private String is_presell;
    private String is_fcode;
    private String have_gift;
    private String store_name;/*商家名字*/
    @Transient
    private boolean isStoreName = false;
    private String is_own_mall;
    //            private boolean sole_flag;
//            private boolean group_flag;
//            private boolean xianshi_flag;
    private String goods_image_url;
    @Transient
    private boolean is_favorate;/*是否已经收藏*/

    @Transient
    private String goods_storage;/*库存*/
    @Transient
    private boolean checked = true;//是否选择

    @Transient
    private String goods_num;/*购物车的数量*/

    @Transient
    private String goods_addtime_text;/*添加时间*/


    /*购买的数量*/
    private int num;//数量

    @Keep
    public GoodsInfo() {
    }

    @Keep
    public GoodsInfo(String goods_id, String store_id, String goods_name, String goods_jingle, String goods_price, String goods_marketprice, String goods_image, String goods_salenum, String evaluation_good_star, String evaluation_count, String is_virtual, String is_presell, String is_fcode, String have_gift, String store_name, String is_own_mall, String goods_image_url) {
        this.goods_id = goods_id;
        this.store_id = store_id;
        this.goods_name = goods_name;
        this.goods_jingle = goods_jingle;
        this.goods_price = goods_price;
        this.goods_marketprice = goods_marketprice;
        this.goods_image = goods_image;
        this.goods_salenum = goods_salenum;
        this.evaluation_good_star = evaluation_good_star;
        this.evaluation_count = evaluation_count;
        this.is_virtual = is_virtual;
        this.is_presell = is_presell;
        this.is_fcode = is_fcode;
        this.have_gift = have_gift;
        this.store_name = store_name;
        this.is_own_mall = is_own_mall;
        this.goods_image_url = goods_image_url;
    }

    @Generated(hash = 1392708661)
    public GoodsInfo(String goods_id, String store_id, String goods_name, String goods_jingle, String goods_price, String goods_promotion_price, String goods_marketprice, String goods_image, String goods_salenum, String evaluation_good_star, String evaluation_count, String is_virtual, String is_presell, String is_fcode, String have_gift, String store_name,
                     String is_own_mall, String goods_image_url, int num) {
        this.goods_id = goods_id;
        this.store_id = store_id;
        this.goods_name = goods_name;
        this.goods_jingle = goods_jingle;
        this.goods_price = goods_price;
        this.goods_promotion_price = goods_promotion_price;
        this.goods_marketprice = goods_marketprice;
        this.goods_image = goods_image;
        this.goods_salenum = goods_salenum;
        this.evaluation_good_star = evaluation_good_star;
        this.evaluation_count = evaluation_count;
        this.is_virtual = is_virtual;
        this.is_presell = is_presell;
        this.is_fcode = is_fcode;
        this.have_gift = have_gift;
        this.store_name = store_name;
        this.is_own_mall = is_own_mall;
        this.goods_image_url = goods_image_url;
        this.num = num;
    }

    public String getGoods_addtime_text() {
        return goods_addtime_text;
    }

    public void setGoods_addtime_text(String goods_addtime_text) {
        this.goods_addtime_text = goods_addtime_text;
    }

    @Bindable
    public boolean isStoreName() {
        KLog.i(isStoreName + "==");
        return isStoreName;
    }

    public void setStoreName(boolean storeName) {
        isStoreName = storeName;
        notifyPropertyChanged(BR.storeName);
    }

    public boolean isIs_favorate() {
        return is_favorate;
    }

    public void setIs_favorate(boolean is_favorate) {
        this.is_favorate = is_favorate;
    }

    public String getGoods_storage() {
        return goods_storage;
    }

    public void setGoods_storage(String goods_storage) {
        this.goods_storage = goods_storage;
    }

    public String getGoods_promotion_price() {
        return goods_promotion_price;
    }

    public void setGoods_promotion_price(String goods_promotion_price) {
        this.goods_promotion_price = goods_promotion_price;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    @Bindable
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        notifyPropertyChanged(BR.num);
    }


    @Bindable
    public boolean isfavorate() {
        return is_favorate;
    }

    public void setfavorate(boolean is_favorate) {
        this.is_favorate = is_favorate;
        notifyPropertyChanged(BR.is_favorate);
    }


    @Bindable
    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
        notifyPropertyChanged(BR.goods_name);
    }

    public String getGoods_jingle() {
        return goods_jingle;
    }

    public void setGoods_jingle(String goods_jingle) {
        this.goods_jingle = goods_jingle;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_marketprice() {
        return goods_marketprice;
    }

    public void setGoods_marketprice(String goods_marketprice) {
        this.goods_marketprice = goods_marketprice;
    }

    @Bindable
    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
        notifyPropertyChanged(BR.goods_image);
    }

    @Bindable
    public String getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(String goods_num) {
        this.goods_num = goods_num;
        notifyPropertyChanged(BR.goods_num);
    }

    @Bindable
    public String getGoods_salenum() {
        return goods_salenum;
    }

    public void setGoods_salenum(String goods_salenum) {
        this.goods_salenum = goods_salenum;
        notifyPropertyChanged(BR.goods_salenum);
    }

    public String getEvaluation_good_star() {
        return evaluation_good_star;
    }

    public void setEvaluation_good_star(String evaluation_good_star) {
        this.evaluation_good_star = evaluation_good_star;
    }

    public String getEvaluation_count() {
        return evaluation_count;
    }

    public void setEvaluation_count(String evaluation_count) {
        this.evaluation_count = evaluation_count;
    }

    public String getIs_virtual() {
        return is_virtual;
    }

    public void setIs_virtual(String is_virtual) {
        this.is_virtual = is_virtual;
    }

    public String getIs_presell() {
        return is_presell;
    }

    public void setIs_presell(String is_presell) {
        this.is_presell = is_presell;
    }

    public String getIs_fcode() {
        return is_fcode;
    }

    public void setIs_fcode(String is_fcode) {
        this.is_fcode = is_fcode;
    }

    public String getHave_gift() {
        return have_gift;
    }

    public void setHave_gift(String have_gift) {
        this.have_gift = have_gift;
    }

    @Bindable
    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
        notifyPropertyChanged(BR.store_name);
    }

    public String getIs_own_mall() {
        return is_own_mall;
    }

    public void setIs_own_mall(String is_own_mall) {
        this.is_own_mall = is_own_mall;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


//            public boolean isSole_flag() {
//                return sole_flag;
//            }
//
//            public void setSole_flag(boolean sole_flag) {
//                this.sole_flag = sole_flag;
//            }
//
//            public boolean isGroup_flag() {
//                return group_flag;
//            }
//
//            public void setGroup_flag(boolean group_flag) {
//                this.group_flag = group_flag;
//            }
//
//            public boolean isXianshi_flag() {
//                return xianshi_flag;
//            }
//
//            public void setXianshi_flag(boolean xianshi_flag) {
//                this.xianshi_flag = xianshi_flag;
//            }

    public String getGoods_image_url() {
        return goods_image_url;
    }

    public void setGoods_image_url(String goods_image_url) {
        this.goods_image_url = goods_image_url;
    }

    public void click(View view) {
        KLog.i("click");
//        ActivityToActivity.toActivity(ARouterConfig.home.SHOPPINGDETAILSACTIVITY, "id", goods_id);
        ActivityToActivity.goShoppingDetails(goods_id);
    }

    public boolean getIsStoreName() {
        return this.isStoreName;
    }

    public void setIsStoreName(boolean isStoreName) {
        this.isStoreName = isStoreName;
    }

//    public void onclick(View view, GoodsInfo data) {
//        KLog.i("onclick");
//        ToastUtils.showLong(view.getId() + " " + data.toString() + getGoods_id());
//    }
}