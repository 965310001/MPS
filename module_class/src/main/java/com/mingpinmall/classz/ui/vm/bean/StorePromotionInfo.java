package com.mingpinmall.classz.ui.vm.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;

import java.util.List;

/**
 * 店铺活动
 */
public class StorePromotionInfo extends BaseBean {

    private DatasBean datas;

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        public static class PromotionBean {
            /**
             * mansong : {"mansong_id":"4","mansong_name":"满1000元送10元","quota_id":"0","start_time":"1555309200","end_time":"1573833600","member_id":"16","store_id":"10","member_name":"15013070796","store_name":"qqqqqq","state":"1","remark":"111","mansong_state_text":"正常","editable":true,"rules":[{"rule_id":"5","mansong_id":"4","price":"1000.00","discount":"10.00","mansong_goods_name":"","goods_id":"0","goods_image_url":"http://192.168.0.44/data/upload/mall/common/default_goods_image_60.gif"}],"start_time_text":"2019-04-15","end_time_text":"2019-11-16"}
             * xianshi : {"xianshi_id":"14","xianshi_name":"涛涛涛涛涛涛","xianshi_title":"涛涛涛涛他","xianshi_explain":"涛涛涛涛涛涛涛涛","quota_id":"0","start_time":"1555344000","end_time":"1576771200","member_id":"16","store_id":"10","member_name":"15013070796","store_name":"qqqqqq","lower_limit":"1","state":"1","xianshi_image":"","xianshi_image1":"","class_id":"6","xianshi_intro":"啊撒旦飞洒发生阿斯蒂芬","xianshi_image2":"","recommended":"0","xianshi_state_text":"正常","editable":true,"start_time_text":"2019-04-16","end_time_text":"2019-12-20"}
             */

            private MansongBean mansong;
            private XianshiBean xianshi;

            public MansongBean getMansong() {
                return mansong;
            }

            public void setMansong(MansongBean mansong) {
                this.mansong = mansong;
            }

            public XianshiBean getXianshi() {
                return xianshi;
            }

            public void setXianshi(XianshiBean xianshi) {
                this.xianshi = xianshi;
            }

            public static class MansongBean {
                /**
                 * mansong_id : 4
                 * mansong_name : 满1000元送10元
                 * quota_id : 0
                 * start_time : 1555309200
                 * end_time : 1573833600
                 * member_id : 16
                 * store_id : 10
                 * member_name : 15013070796
                 * store_name : qqqqqq
                 * state : 1
                 * remark : 111
                 * mansong_state_text : 正常
                 * editable : true
                 * rules : [{"rule_id":"5","mansong_id":"4","price":"1000.00","discount":"10.00","mansong_goods_name":"","goods_id":"0","goods_image_url":"http://192.168.0.44/data/upload/mall/common/default_goods_image_60.gif"}]
                 * start_time_text : 2019-04-15
                 * end_time_text : 2019-11-16
                 */

                private String mansong_id;
                private String mansong_name;
                private String quota_id;
                private String start_time;
                private String end_time;
                private String member_id;
                private String store_id;
                private String member_name;
                private String store_name;
                private String state;
                private String remark;
                private String mansong_state_text;
                private boolean editable;
                private String start_time_text;
                private String end_time_text;
                private List<RulesBean> rules;

                public String getMansong_id() {
                    return mansong_id;
                }

                public void setMansong_id(String mansong_id) {
                    this.mansong_id = mansong_id;
                }

                public String getMansong_name() {
                    return mansong_name;
                }

                public void setMansong_name(String mansong_name) {
                    this.mansong_name = mansong_name;
                }

                public String getQuota_id() {
                    return quota_id;
                }

                public void setQuota_id(String quota_id) {
                    this.quota_id = quota_id;
                }

                public String getStart_time() {
                    return start_time;
                }

                public void setStart_time(String start_time) {
                    this.start_time = start_time;
                }

                public String getEnd_time() {
                    return end_time;
                }

                public void setEnd_time(String end_time) {
                    this.end_time = end_time;
                }

                public String getMember_id() {
                    return member_id;
                }

                public void setMember_id(String member_id) {
                    this.member_id = member_id;
                }

                public String getStore_id() {
                    return store_id;
                }

                public void setStore_id(String store_id) {
                    this.store_id = store_id;
                }

                public String getMember_name() {
                    return member_name;
                }

                public void setMember_name(String member_name) {
                    this.member_name = member_name;
                }

                public String getStore_name() {
                    return store_name;
                }

                public void setStore_name(String store_name) {
                    this.store_name = store_name;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }

                public String getRemark() {
                    return remark;
                }

                public void setRemark(String remark) {
                    this.remark = remark;
                }

                public String getMansong_state_text() {
                    return mansong_state_text;
                }

                public void setMansong_state_text(String mansong_state_text) {
                    this.mansong_state_text = mansong_state_text;
                }

                public boolean isEditable() {
                    return editable;
                }

                public void setEditable(boolean editable) {
                    this.editable = editable;
                }

                public String getStart_time_text() {
                    return start_time_text;
                }

                public void setStart_time_text(String start_time_text) {
                    this.start_time_text = start_time_text;
                }

                public String getEnd_time_text() {
                    return end_time_text;
                }

                public void setEnd_time_text(String end_time_text) {
                    this.end_time_text = end_time_text;
                }

                public List<RulesBean> getRules() {
                    return rules;
                }

                public void setRules(List<RulesBean> rules) {
                    this.rules = rules;
                }

                public static class RulesBean {
                    /**
                     * rule_id : 5
                     * mansong_id : 4
                     * price : 1000.00
                     * discount : 10.00
                     * mansong_goods_name :
                     * goods_id : 0
                     * goods_image_url : http://192.168.0.44/data/upload/mall/common/default_goods_image_60.gif
                     */

                    private String rule_id;
                    private String mansong_id;
                    private String price;
                    private String discount;
                    private String mansong_goods_name;
                    private String goods_id;
                    private String goods_image_url;

                    public String getRule_id() {
                        return rule_id;
                    }

                    public void setRule_id(String rule_id) {
                        this.rule_id = rule_id;
                    }

                    public String getMansong_id() {
                        return mansong_id;
                    }

                    public void setMansong_id(String mansong_id) {
                        this.mansong_id = mansong_id;
                    }

                    public String getPrice() {
                        return price;
                    }

                    public void setPrice(String price) {
                        this.price = price;
                    }

                    public String getDiscount() {
                        return discount;
                    }

                    public void setDiscount(String discount) {
                        this.discount = discount;
                    }

                    public String getMansong_goods_name() {
                        return mansong_goods_name;
                    }

                    public void setMansong_goods_name(String mansong_goods_name) {
                        this.mansong_goods_name = mansong_goods_name;
                    }

                    public String getGoods_id() {
                        return goods_id;
                    }

                    public void setGoods_id(String goods_id) {
                        this.goods_id = goods_id;
                    }

                    public String getGoods_image_url() {
                        return goods_image_url;
                    }

                    public void setGoods_image_url(String goods_image_url) {
                        this.goods_image_url = goods_image_url;
                    }
                }
            }

            public static class XianshiBean {
                /**
                 * xianshi_id : 14
                 * xianshi_name : 涛涛涛涛涛涛
                 * xianshi_title : 涛涛涛涛他
                 * xianshi_explain : 涛涛涛涛涛涛涛涛
                 * quota_id : 0
                 * start_time : 1555344000
                 * end_time : 1576771200
                 * member_id : 16
                 * store_id : 10
                 * member_name : 15013070796
                 * store_name : qqqqqq
                 * lower_limit : 1
                 * state : 1
                 * xianshi_image :
                 * xianshi_image1 :
                 * class_id : 6
                 * xianshi_intro : 啊撒旦飞洒发生阿斯蒂芬
                 * xianshi_image2 :
                 * recommended : 0
                 * xianshi_state_text : 正常
                 * editable : true
                 * start_time_text : 2019-04-16
                 * end_time_text : 2019-12-20
                 */

                private String xianshi_id;
                private String xianshi_name;
                private String xianshi_title;
                private String xianshi_explain;
                private String quota_id;
                private String start_time;
                private String end_time;
                private String member_id;
                private String store_id;
                private String member_name;
                private String store_name;
                private String lower_limit;
                private String state;
                private String xianshi_image;
                private String xianshi_image1;
                private String class_id;
                private String xianshi_intro;
                private String xianshi_image2;
                private String recommended;
                private String xianshi_state_text;
                private boolean editable;
                private String start_time_text;
                private String end_time_text;

                public String getXianshi_id() {
                    return xianshi_id;
                }

                public void setXianshi_id(String xianshi_id) {
                    this.xianshi_id = xianshi_id;
                }

                public String getXianshi_name() {
                    return xianshi_name;
                }

                public void setXianshi_name(String xianshi_name) {
                    this.xianshi_name = xianshi_name;
                }

                public String getXianshi_title() {
                    return xianshi_title;
                }

                public void setXianshi_title(String xianshi_title) {
                    this.xianshi_title = xianshi_title;
                }

                public String getXianshi_explain() {
                    return xianshi_explain;
                }

                public void setXianshi_explain(String xianshi_explain) {
                    this.xianshi_explain = xianshi_explain;
                }

                public String getQuota_id() {
                    return quota_id;
                }

                public void setQuota_id(String quota_id) {
                    this.quota_id = quota_id;
                }

                public String getStart_time() {
                    return start_time;
                }

                public void setStart_time(String start_time) {
                    this.start_time = start_time;
                }

                public String getEnd_time() {
                    return end_time;
                }

                public void setEnd_time(String end_time) {
                    this.end_time = end_time;
                }

                public String getMember_id() {
                    return member_id;
                }

                public void setMember_id(String member_id) {
                    this.member_id = member_id;
                }

                public String getStore_id() {
                    return store_id;
                }

                public void setStore_id(String store_id) {
                    this.store_id = store_id;
                }

                public String getMember_name() {
                    return member_name;
                }

                public void setMember_name(String member_name) {
                    this.member_name = member_name;
                }

                public String getStore_name() {
                    return store_name;
                }

                public void setStore_name(String store_name) {
                    this.store_name = store_name;
                }

                public String getLower_limit() {
                    return lower_limit;
                }

                public void setLower_limit(String lower_limit) {
                    this.lower_limit = lower_limit;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }

                public String getXianshi_image() {
                    return xianshi_image;
                }

                public void setXianshi_image(String xianshi_image) {
                    this.xianshi_image = xianshi_image;
                }

                public String getXianshi_image1() {
                    return xianshi_image1;
                }

                public void setXianshi_image1(String xianshi_image1) {
                    this.xianshi_image1 = xianshi_image1;
                }

                public String getClass_id() {
                    return class_id;
                }

                public void setClass_id(String class_id) {
                    this.class_id = class_id;
                }

                public String getXianshi_intro() {
                    return xianshi_intro;
                }

                public void setXianshi_intro(String xianshi_intro) {
                    this.xianshi_intro = xianshi_intro;
                }

                public String getXianshi_image2() {
                    return xianshi_image2;
                }

                public void setXianshi_image2(String xianshi_image2) {
                    this.xianshi_image2 = xianshi_image2;
                }

                public String getRecommended() {
                    return recommended;
                }

                public void setRecommended(String recommended) {
                    this.recommended = recommended;
                }

                public String getXianshi_state_text() {
                    return xianshi_state_text;
                }

                public void setXianshi_state_text(String xianshi_state_text) {
                    this.xianshi_state_text = xianshi_state_text;
                }

                public boolean isEditable() {
                    return editable;
                }

                public void setEditable(boolean editable) {
                    this.editable = editable;
                }

                public String getStart_time_text() {
                    return start_time_text;
                }

                public void setStart_time_text(String start_time_text) {
                    this.start_time_text = start_time_text;
                }

                public String getEnd_time_text() {
                    return end_time_text;
                }

                public void setEnd_time_text(String end_time_text) {
                    this.end_time_text = end_time_text;
                }
            }
        }
    }
}
