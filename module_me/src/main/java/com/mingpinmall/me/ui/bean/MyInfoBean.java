package com.mingpinmall.me.ui.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;
import com.google.gson.annotations.SerializedName;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/1
 **/
public class MyInfoBean extends BaseBean {

    private DatasBean datas;

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {

        private MemberInfoBean member_info;

        public MemberInfoBean getMember_info() {
            return member_info;
        }

        public void setMember_info(MemberInfoBean member_info) {
            this.member_info = member_info;
        }

        public static class MemberInfoBean {

            private String user_name;
            private String id;
            private String avatar;
            private String level_name;
            private String favorites_store;
            private String favorites_goods;
            private String member_mobile;
            /**
             * 待付款订单
             */
            private String order_nopay_count;
            /**
             * 待收款
             */
            private String order_noreceipt_count;
            /**
             * 退款/退货
             */
            private String order_notakes_count;
            /**
             * 待评价
             */
            private String order_noeval_count;

            @SerializedName("return")
            private String returnX;

            public String getMember_mobile() {
                return member_mobile == null ? "" : member_mobile;
            }

            public void setMember_mobile(String member_mobile) {
                this.member_mobile = member_mobile == null ? "" : member_mobile;
            }

            public String getUser_name() {
                return user_name == null ? "" : user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name == null ? "" : user_name;
            }

            public String getId() {
                return id == null ? "" : id;
            }

            public void setId(String id) {
                this.id = id == null ? "" : id;
            }

            public String getAvatar() {
                return avatar == null ? "" : avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar == null ? "" : avatar;
            }

            public String getLevel_name() {
                return level_name == null ? "" : level_name;
            }

            public void setLevel_name(String level_name) {
                this.level_name = level_name == null ? "" : level_name;
            }

            public String getFavorites_store() {
                return favorites_store == null ? "" : favorites_store;
            }

            public void setFavorites_store(String favorites_store) {
                this.favorites_store = favorites_store == null ? "" : favorites_store;
            }

            public String getFavorites_goods() {
                return favorites_goods == null ? "" : favorites_goods;
            }

            public void setFavorites_goods(String favorites_goods) {
                this.favorites_goods = favorites_goods == null ? "" : favorites_goods;
            }

            public int getOrder_nopay_count() {
                return order_nopay_count == null ? 0 : Integer.parseInt(order_nopay_count);
            }

            public void setOrder_nopay_count(String order_nopay_count) {
                this.order_nopay_count = order_nopay_count == null ? "" : order_nopay_count;
            }

            public int getOrder_noreceipt_count() {
                return order_noreceipt_count == null ? 0 : Integer.parseInt(order_noreceipt_count);
            }

            public void setOrder_noreceipt_count(String order_noreceipt_count) {
                this.order_noreceipt_count = order_noreceipt_count == null ? "" : order_noreceipt_count;
            }

            public int getOrder_notakes_count() {
                return order_notakes_count == null ? 0 : Integer.parseInt(order_notakes_count);
            }

            public void setOrder_notakes_count(String order_notakes_count) {
                this.order_notakes_count = order_notakes_count == null ? "" : order_notakes_count;
            }

            public int getOrder_noeval_count() {
                return order_noeval_count == null ? 0 : Integer.parseInt(order_noeval_count);
            }

            public void setOrder_noeval_count(String order_noeval_count) {
                this.order_noeval_count = order_noeval_count == null ? "" : order_noeval_count;
            }

            public int getReturnX() {
                return returnX == null ? 0 : Integer.parseInt(returnX);
            }

            public void setReturnX(String returnX) {
                this.returnX = returnX == null ? "" : returnX;
            }
        }
    }
}
