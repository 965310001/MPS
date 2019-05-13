package com.mingpinmall.classz.ui.vm.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GoodsDetailInfo extends BaseBean {

    @SerializedName("newdata")
    private DatasBean datas;
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return code == 200;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public class DatasBean extends BaseBean {

        private GoodsInfo goods_info;
        private StoreInfoBean store_info;
        private String goods_image;
        private GoodsEvaluateInfoBean goods_evaluate_info;
        private GoodsHairInfoBean goods_hair_info;
        private List<GoodsInfo> goods_commend_list;
        public List<GoodsComment> goods_eval_list;
        private boolean is_favorate;
        private List<GoodsInfo.NewsSpecListDataBean> news_spec_list_data;
        /**
         * mansong_info : {"list":{"mansong_id":"6","mansong_name":"满减大促销","quota_id":"0","start_time":"1555918680","end_time":"1651248000","member_id":"16","store_id":"10","member_name":"15013070796","store_name":"qqqqqq","state":"1","remark":"满减大促销","mansong_state_text":"正常","editable":true,"rules":[{"rule_id":"7","mansong_id":"6","price":"500","discount":"10","mansong_goods_name":"","goods_id":"0","goods_image":"10_06089974189764454.png","goods_image_url":"http://192.168.0.44/data/upload/mall/store/goods/10/10_06089974189764454_240.png","goods_storage":"2988","goods_url":"http://192.168.0.44/pinpai/index.php?app=goods&wwi=index&goods_id=109973"},{"rule_id":"8","mansong_id":"6","price":"1000","discount":"30","mansong_goods_name":"好看的电影001","goods_id":109973,"goods_image":"10_06089974189764454.png","goods_image_url":"http://192.168.0.44/data/upload/mall/store/goods/10/10_06089974189764454_240.png","goods_storage":"2988","goods_url":"http://192.168.0.44/pinpai/index.php?app=goods&wwi=index&goods_id=109973"}]}}
         */

        private MansongInfoBean mansong_info;
        private List<GiftArrayBean> gift_array;

        public boolean isIs_favorate() {
            return is_favorate;
        }

        public void setIs_favorate(boolean is_favorate) {
            this.is_favorate = is_favorate;
        }

        public GoodsInfo getGoods_info() {
            return goods_info;
        }

        public void setGoods_info(GoodsInfo goods_info) {
            this.goods_info = goods_info;
        }

        public StoreInfoBean getStore_info() {
            return store_info;
        }

        public void setStore_info(StoreInfoBean store_info) {
            this.store_info = store_info;
        }

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public GoodsEvaluateInfoBean getGoods_evaluate_info() {
            return goods_evaluate_info;
        }

        public void setGoods_evaluate_info(GoodsEvaluateInfoBean goods_evaluate_info) {
            this.goods_evaluate_info = goods_evaluate_info;
        }

        public GoodsHairInfoBean getGoods_hair_info() {
            return goods_hair_info;
        }

        public void setGoods_hair_info(GoodsHairInfoBean goods_hair_info) {
            this.goods_hair_info = goods_hair_info;
        }

        public List<GoodsInfo> getGoods_commend_list() {
            return goods_commend_list;
        }

        public void setGoods_commend_list(List<GoodsInfo> goods_commend_list) {
            this.goods_commend_list = goods_commend_list;
        }

        public List<GoodsComment> getGoods_eval_list() {
            return goods_eval_list;
        }

        public void setGoods_eval_list(List<GoodsComment> goods_eval_list) {
            this.goods_eval_list = goods_eval_list;
        }

        public List<GoodsInfo.NewsSpecListDataBean> getNews_spec_list_data() {
            return news_spec_list_data;
        }

        public void setNews_spec_list_data(List<GoodsInfo.NewsSpecListDataBean> news_spec_list_data) {
            this.news_spec_list_data = news_spec_list_data;
        }

        public MansongInfoBean getMansong_info() {
            return mansong_info;
        }

        public void setMansong_info(MansongInfoBean mansong_info) {
            this.mansong_info = mansong_info;
        }

        public List<GiftArrayBean> getGift_array() {
            return gift_array;
        }

        public void setGift_array(List<GiftArrayBean> gift_array) {
            this.gift_array = gift_array;
        }

        public class GiftArrayBean {
            /**
             * gift_id : 2
             * goods_id : 109964
             * goods_commonid : 108823
             * gift_goodsid : 109973
             * gift_goodsname : 好看的电影001
             * gift_goodsimage : 10_06089974189764454.png
             * gift_amount : 1
             */

            private String gift_goodsid;
            private String gift_goodsname;
            private String gift_goodsimage;
            private String gift_amount;

            public String getGift_goodsid() {
                return gift_goodsid;
            }

            public void setGift_goodsid(String gift_goodsid) {
                this.gift_goodsid = gift_goodsid;
            }

            public String getGift_goodsname() {
                return gift_goodsname;
            }

            public void setGift_goodsname(String gift_goodsname) {
                this.gift_goodsname = gift_goodsname;
            }

            public String getGift_goodsimage() {
                return gift_goodsimage;
            }

            public void setGift_goodsimage(String gift_goodsimage) {
                this.gift_goodsimage = gift_goodsimage;
            }

            public String getGift_amount() {
                return gift_amount;
            }

            public void setGift_amount(String gift_amount) {
                this.gift_amount = gift_amount;
            }
        }

        public class MansongInfoBean extends BaseBean {

            private String store_name;
            private String state;
            private List<RulesBean> rules;

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

            public List<RulesBean> getRules() {
                return rules;
            }

            public void setRules(List<RulesBean> rules) {
                this.rules = rules;
            }

            public class RulesBean extends BaseBean {
                /**
                 * rule_id : 7
                 * mansong_id : 6
                 * price : 500
                 * discount : 10
                 * mansong_goods_name :
                 * goods_id : 0
                 * goods_image : 10_06089974189764454.png
                 * goods_image_url : http://192.168.0.44/data/upload/mall/store/goods/10/10_06089974189764454_240.png
                 * goods_storage : 2988
                 * goods_url : http://192.168.0.44/pinpai/index.php?app=goods&wwi=index&goods_id=109973
                 */
                private String price;
                private String discount;
                private String mansong_goods_name;
                private String goods_id;
                @SerializedName("goods_image")
                private String goods_imageX;
                private String goods_image_url;

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

//                public String getGoods_imageX() {
//                    return goods_imageX;
//                }
//
//                public void setGoods_imageX(String goods_imageX) {
//                    this.goods_imageX = goods_imageX;
//                }

                public String getGoods_image_url() {
                    return goods_image_url;
                }

                public void setGoods_image_url(String goods_image_url) {
                    this.goods_image_url = goods_image_url;
                }
            }
        }

        public class StoreInfoBean extends BaseBean {
            private String store_id;
            private String store_name;
            private String member_id;
            private String member_name;
            private String is_own_mall;
            private String goods_count;
            private List<NewStoreCreditBean> new_storeCredit;

            private StoreCreditBean store_credit;


            public String getStore_id() {
                return store_id;
            }

            public void setStore_id(String store_id) {
                this.store_id = store_id;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public String getMember_id() {
                return member_id;
            }

            public void setMember_id(String member_id) {
                this.member_id = member_id;
            }

            public String getMember_name() {
                return member_name;
            }

            public void setMember_name(String member_name) {
                this.member_name = member_name;
            }

            public String getIs_own_mall() {
                return is_own_mall;
            }

            public void setIs_own_mall(String is_own_mall) {
                this.is_own_mall = is_own_mall;
            }

            public String getGoods_count() {
                return goods_count;
            }

            public void setGoods_count(String goods_count) {
                this.goods_count = goods_count;
            }


            public List<NewStoreCreditBean> getNew_storeCredit() {
                return new_storeCredit;
            }

            public void setNew_storeCredit(List<NewStoreCreditBean> new_storeCredit) {
                this.new_storeCredit = new_storeCredit;
            }

            public StoreCreditBean getStore_credit() {
                return store_credit;
            }

            public void setStore_credit(StoreCreditBean store_credit) {
                this.store_credit = store_credit;
            }

            public class StoreCreditBean extends BaseBean {
                /**
                 * store_desccredit : {"text":"描述","credit":"5.0","percent":"----","percent_class":"equal","percent_text":"平"}
                 * store_servicecredit : {"text":"服务","credit":"5.0","percent":"----","percent_class":"equal","percent_text":"平"}
                 * store_deliverycredit : {"text":"物流","credit":"5.0","percent":"----","percent_class":"equal","percent_text":"平"}
                 */

                private NewStoreCreditBean store_desccredit;
                private NewStoreCreditBean store_servicecredit;
                private NewStoreCreditBean store_deliverycredit;

                private List<NewStoreCreditBean> list;

                public List<NewStoreCreditBean> getList() {
                    list = new ArrayList<>();
                    if (null != store_desccredit) {
                        store_desccredit.setText("描述相符");
                    }
                    list.add(store_desccredit);
                    list.add(store_servicecredit);
                    list.add(store_deliverycredit);
                    return list;
                }

                public void setList(List<NewStoreCreditBean> list) {
                    this.list = list;
                }

                public NewStoreCreditBean getStore_desccredit() {
                    return store_desccredit;
                }

                public void setStore_desccredit(NewStoreCreditBean store_desccredit) {
                    this.store_desccredit = store_desccredit;
                }

                public NewStoreCreditBean getStore_servicecredit() {
                    return store_servicecredit;
                }

                public void setStore_servicecredit(NewStoreCreditBean store_servicecredit) {
                    this.store_servicecredit = store_servicecredit;
                }

                public NewStoreCreditBean getStore_deliverycredit() {
                    return store_deliverycredit;
                }

                public void setStore_deliverycredit(NewStoreCreditBean store_deliverycredit) {
                    this.store_deliverycredit = store_deliverycredit;
                }
            }

            public class NewStoreCreditBean extends BaseBean {
                /**
                 * text : 描述
                 * credit : 5.0
                 * percent : ----
                 * percent_class : equal
                 * percent_text : 平
                 */

                public String text;
                public String credit;
                public String percent;
                public String percent_class;
                public String percent_text;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public String getCredit() {
                    return credit;
                }

                public void setCredit(String credit) {
                    this.credit = credit;
                }

                public String getPercent() {
                    return percent;
                }

                public void setPercent(String percent) {
                    this.percent = percent;
                }

                public String getPercent_class() {
                    return percent_class;
                }

                public void setPercent_class(String percent_class) {
                    this.percent_class = percent_class;
                }

                public String getPercent_text() {
                    return percent_text;
                }

                public void setPercent_text(String percent_text) {
                    this.percent_text = percent_text;
                }
            }
        }

        public class GoodsEvaluateInfoBean extends BaseBean {
            /**
             * good : 0
             * normal : 0
             * bad : 0
             * all : 0
             * img : 0
             * good_percent : 100
             * normal_percent : 0
             * bad_percent : 0
             * good_star : 5
             * star_average : 5
             */

//            public int good;
//            public int normal;
//            public int bad;
            public int all;
//            public int img;
//            public int good_percent;
//            public int normal_percent;
//            public int bad_percent;
//            public int good_star;
//            public int star_average;

//            public int getGood() {
//                return good;
//            }
//
//            public void setGood(int good) {
//                this.good = good;
//            }
//
//            public int getNormal() {
//                return normal;
//            }
//
//            public void setNormal(int normal) {
//                this.normal = normal;
//            }
//
//            public int getBad() {
//                return bad;
//            }
//
//            public void setBad(int bad) {
//                this.bad = bad;
//            }

            public int getAll() {
                return all;
            }

            public void setAll(int all) {
                this.all = all;
            }

//            public int getImg() {
//                return img;
//            }
//
//            public void setImg(int img) {
//                this.img = img;
//            }
//
//            public int getGood_percent() {
//                return good_percent;
//            }
//
//            public void setGood_percent(int good_percent) {
//                this.good_percent = good_percent;
//            }
//
//            public int getNormal_percent() {
//                return normal_percent;
//            }
//
//            public void setNormal_percent(int normal_percent) {
//                this.normal_percent = normal_percent;
//            }

//            public int getBad_percent() {
//                return bad_percent;
//            }
//
//            public void setBad_percent(int bad_percent) {
//                this.bad_percent = bad_percent;
//            }
//
//            public int getGood_star() {
//                return good_star;
//            }
//
//            public void setGood_star(int good_star) {
//                this.good_star = good_star;
//            }

//            public int getStar_average() {
//                return star_average;
//            }
//
//            public void setStar_average(int star_average) {
//                this.star_average = star_average;
//            }
        }

        public class GoodsHairInfoBean extends BaseBean {
            /**
             * content : 免运费
             * if_store_cn : 有货
             * if_store : true
             * area_name : 全国
             */

            public String content;
            public String if_store_cn;
            public boolean if_store;
            public String area_name;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getIf_store_cn() {
                return if_store_cn;
            }

            public void setIf_store_cn(String if_store_cn) {
                this.if_store_cn = if_store_cn;
            }

            public boolean isIf_store() {
                return if_store;
            }

            public void setIf_store(boolean if_store) {
                this.if_store = if_store;
            }

            public String getArea_name() {
                return area_name;
            }

            public void setArea_name(String area_name) {
                this.area_name = area_name;
            }
        }
    }
}