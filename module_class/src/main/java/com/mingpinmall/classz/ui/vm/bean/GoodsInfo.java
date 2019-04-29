package com.mingpinmall.classz.ui.vm.bean;

import android.databinding.Bindable;
import android.view.View;

import com.goldze.common.dmvvm.base.bean.BaseBean;
import com.goldze.common.dmvvm.base.bean.UserBean;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.DateUtils;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.mingpinmall.classz.BR;
import com.socks.library.KLog;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;

@Entity
public class GoodsInfo extends BaseBean {

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
    private String is_virtual;/*是否是虚拟码*/
    private String is_presell;/*预*/
    private String is_fcode;/*F码*/
    private String have_gift;/*赠*/
    private String store_name;/*商家名字*/
    @Transient
    private boolean isStoreName = false;
    private String is_own_mall;
    //            private boolean sole_flag;
    @Transient
    private boolean group_flag;/*降*/
    @Transient
    private boolean xianshi_flag;/*降*/
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

    @Transient
    public String virtual_indate;/*有效期*/

    @Transient
    private int cart;

    private int num;//购买的数量

    /*属性*/
    @Transient
    public List<String> news_goods_spec;/*默认选中*/
    @Transient
    public List<String> news_goods_spec_name;
    @Transient
    public List<List<String>> news_goods_spec_value;
    @Transient
    public List<NewsSpecDataBean> news_spec_data;
    @Transient
    public List<NewsSpecListDataBean> news_spec_list_data;

    @Transient
    public String member_id;/*是否是自己*/

    public boolean isShop() {
        UserBean userBean = (UserBean) SharePreferenceUtil.getUser(UserBean.class);
        if (userBean == null) {
            return false;
        }
        return userBean.getUserid().equals(member_id);
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

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

    public void setCart(int cart) {
        this.cart = cart;
        notifyPropertyChanged(BR.cart);
    }

    @Bindable
    public boolean isCart() {
        return cart == 1;
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

    public String getGoods_image_url() {
        return goods_image_url;
    }

    public void setGoods_image_url(String goods_image_url) {
        this.goods_image_url = goods_image_url;
    }

    public void click(View view) {
        LiveBus.getDefault().postEvent("GOODSSPECIFICATIONPOP_VAL", "GOODSSPECIFICATIONPOP_VAL",
                goods_id);
        ActivityToActivity.goShoppingDetails(goods_id);
    }

    public boolean getGroup_flag() {
        return this.group_flag;
    }

    public boolean getXianshi_flag() {
        return this.xianshi_flag;
    }


    /*有效期*/
    public String getValidity() {
        return String.format("即日起 到 %s", DateUtils.getFormatDate(Long.parseLong(virtual_indate), "yyyy-MM-dd HH:mm:ss"));
    }


    public static class NewsSpecDataBean extends BaseBean {

        private int spec_id;
        private String spec_name;
        private List<SpecValueBean> spec_value;

        public int getSpec_id() {
            return spec_id;
        }

        public void setSpec_id(int spec_id) {
            this.spec_id = spec_id;
        }

        public String getSpec_name() {
            return spec_name;
        }

        public void setSpec_name(String spec_name) {
            this.spec_name = spec_name;
        }

        public List<SpecValueBean> getSpec_value() {
            return spec_value;
        }

        public void setSpec_value(List<SpecValueBean> spec_value) {
            this.spec_value = spec_value;
        }

        public static class SpecValueBean extends BaseBean {
            /**
             * spe_id : 496
             * spe_value : 大
             */

            private int spe_id;
            private String spe_value;

            public int getSpe_id() {
                return spe_id;
            }

            public void setSpe_id(int spe_id) {
                this.spe_id = spe_id;
            }

            public String getSpe_value() {
                return spe_value;
            }

            public void setSpe_value(String spe_value) {
                this.spe_value = spe_value;
            }
        }
    }

    public static class NewsSpecListDataBean extends BaseBean {
        /**
         * key : 496|510|513
         * val : 109964
         */

        private String key;
        private String val;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }
    }
}