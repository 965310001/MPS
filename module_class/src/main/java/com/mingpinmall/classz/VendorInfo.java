package com.mingpinmall.classz;

import com.goldze.common.dmvvm.base.bean.BaseBean;
import com.google.gson.annotations.SerializedName;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;

import java.util.List;


/**
 * @author GuoFeng
 * @date : 2019/1/24 10:46
 * @description: 供应商
 */
public class VendorInfo extends BaseBean {

    private String vendorId;//供应商ID
    private String vendorName;//供应商名称
    public List<GoodsInfo> goodsInfos;
    public boolean checked=true;

    @SerializedName("goods_groups")
    private List<GoodsGroups> data;

    public VendorInfo() {
    }

    public VendorInfo(String vendorName, List<GoodsInfo> goodsInfos) {
        this.vendorName = vendorName;
        this.goodsInfos = goodsInfos;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public List<GoodsInfo> getGoodsInfos() {
        return goodsInfos;
    }

    public void setGoodsInfos(List<GoodsInfo> goodsInfos) {
        this.goodsInfos = goodsInfos;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<GoodsGroups> getData() {
        return data;
    }

    public void setData(List<GoodsGroups> data) {
        this.data = data;
    }

    public static class GoodsGroups extends BaseBean {
        @SerializedName("goods")
        private List<GoodsInfo> data;

        @SerializedName("shop")
        private Shop shop;

        @SerializedName("total_price")
        private double totalPrice;

        @SerializedName("total_amount")
        private double totalAmount;

        public List<GoodsInfo> getData() {
            return data;
        }

        public void setData(List<GoodsInfo> data) {
            this.data = data;
        }

        public Shop getShop() {
            return shop;
        }

        public void setShop(Shop shop) {
            this.shop = shop;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public class Shop extends BaseBean {
            private String name;
            private long id;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }
        }
    }

}
