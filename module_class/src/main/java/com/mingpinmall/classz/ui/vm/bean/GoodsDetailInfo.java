package com.mingpinmall.classz.ui.vm.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GoodsDetailInfo extends BaseBean {

    /**
     * code : 200
     * datas : {"goods_info":{"goods_name":"正品瑞士邦顿(Bestdon)手表BD7108G男士大表盘全自动针机械表精钢军表夜光防水户外运动腕表","goods_jingle":"型号BD7108G    100米防水 终身保修 全国包邮 邦顿正品  专柜同款","gc_id_1":"530","gc_id_2":"540","gc_id_3":"587","spec_name":null,"spec_value":null,"goods_attr":null,"goods_custom":null,"mobile_body":"","goods_price":"599.00","goods_marketprice":"599.00","goods_costprice":"0.00","goods_discount":"100","goods_serial":"BD7108G","goods_storage_alarm":"0","goods_barcode":"","transport_id":"0","transport_title":"","goods_freight":"0.00","goods_vat":"0","areaid_1":"19","areaid_2":"289","goods_stcids":"","plateid_top":"0","plateid_bottom":"0","is_virtual":"0","virtual_indate":"0","virtual_limit":"0","virtual_invalid_refund":"0","sup_id":"0","is_own_mall":"0","goods_id":"109928","goods_promotion_price":"599.00","goods_promotion_type":"0","goods_click":85,"goods_salenum":"0","goods_collect":"1","goods_spec":null,"goods_storage":"1597","color_id":"0","evaluation_good_star":"5","evaluation_count":"0","is_fcode":"0","is_presell":"0","presell_deliverdate":"0","is_book":"0","book_down_payment":"0.00","book_final_payment":"0.00","book_down_time":"0","book_buyers":"0","have_gift":"0","contract_1":"0","contract_2":"0","contract_3":"0","contract_4":"0","contract_5":"0","contract_6":"0","contract_7":"0","contract_8":"0","contract_9":"0","contract_10":"0","is_chain":"0","invite_rate":"0.00","sole_info":[],"groupbuy_info":null,"xianshi_info":null,"jjg_info":null,"cart":1,"buynow":1,"contractlist":[],"goods_url":"http://www.mingpinmall.cn/pinpai/index.php?app=goods&wwi=index&goods_id=109928"},"spec_image":["http://www.mingpinmall.cn/data/upload/mall/common/default_goods_image_60.gif"],"mansong_info":null,"gift_array":[],"goods_commend_list":[{"goods_id":"109927","goods_name":"正品瑞士邦顿（bestdon）手表BD7768L陶瓷女表全自动机械贝壳面镶钻蓝宝石商务时尚白领腕表","goods_price":null,"goods_promotion_price":"1299.00","goods_image_url":"http://www.mingpinmall.cn/data/upload/mall/common/default_goods_image_240.gif"},{"goods_id":"109898","goods_name":"正品邦顿（bestdon）超薄手表女款正品时尚潮流简约休闲防水真皮带女表情侣石英表","goods_price":null,"goods_promotion_price":"258.00","goods_image_url":"http://www.mingpinmall.cn/data/upload/mall/common/default_goods_image_240.gif"},{"goods_id":"109925","goods_name":"邦顿（Bestdon）手表男士机械表 全自动夜光时尚多功能精钢防水皮带男表 镂空大表盘腕表 银壳黑面","goods_price":null,"goods_promotion_price":"499.00","goods_image_url":"http://www.mingpinmall.cn/data/upload/mall/common/default_goods_image_240.gif"},{"goods_id":"109928","goods_name":"正品瑞士邦顿(Bestdon)手表BD7108G男士大表盘全自动针机械表精钢军表夜光防水户外运动腕表","goods_price":null,"goods_promotion_price":"599.00","goods_image_url":"http://www.mingpinmall.cn/data/upload/mall/common/default_goods_image_240.gif"},{"goods_id":"109926","goods_name":"正品 瑞士邦顿（bestdon）飞轮全自动机械表男表精钢男士手表真皮带夜光防水镂空大表盘","goods_price":null,"goods_promotion_price":"428.00","goods_image_url":"http://www.mingpinmall.cn/data/upload/mall/common/default_goods_image_240.gif"},{"goods_id":"109924","goods_name":"正品瑞士邦顿（bestdon）情侣手表 石英表超薄防水情侣手表真皮表带","goods_price":null,"goods_promotion_price":"388.00","goods_image_url":"http://www.mingpinmall.cn/data/upload/mall/common/default_goods_image_240.gif"}],"store_info":{"store_id":"7","store_name":"瑞士邦顿（bestdon）手表旗舰店","member_id":"30361","member_name":"puppet","is_own_mall":"0","goods_count":"6","store_credit":{"store_desccredit":{"text":"描述","credit":"5.0","percent":"----","percent_class":"equal","percent_text":"平"},"store_servicecredit":{"text":"服务","credit":"5.0","percent":"----","percent_class":"equal","percent_text":"平"},"store_deliverycredit":{"text":"物流","credit":"5.0","percent":"----","percent_class":"equal","percent_text":"平"}},"new_storeCredit":[{"text":"描述","credit":"5.0","percent":"----","percent_class":"equal","percent_text":"平"},{"text":"服务","credit":"5.0","percent":"----","percent_class":"equal","percent_text":"平"},{"text":"物流","credit":"5.0","percent":"----","percent_class":"equal","percent_text":"平"}]},"spec_list":{"":"109928"},"goods_image":"http://www.mingpinmall.cn/data/upload/mall/common/default_goods_image_360.gif,http://www.mingpinmall.cn/data/upload/mall/common/default_goods_image_360.gif,http://www.mingpinmall.cn/data/upload/mall/common/default_goods_image_360.gif,http://www.mingpinmall.cn/data/upload/mall/common/default_goods_image_360.gif,http://www.mingpinmall.cn/data/upload/mall/common/default_goods_image_360.gif","goods_eval_list":[],"goods_evaluate_info":{"good":0,"normal":0,"bad":0,"all":0,"img":0,"good_percent":100,"normal_percent":0,"bad_percent":0,"good_star":5,"star_average":5},"goods_hair_info":{"content":"免运费","if_store_cn":"有货","if_store":true,"area_name":"全国"}}
     */

//    private int code;
    private DatasBean datas;

    public boolean isSuccess() {
        return super.getCode() == 200;
    }

    public int getCode() {
        return super.getCode();
    }

//    public void setCode(int code) {
//        this.code = code;
//    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * goods_info : {"goods_name":"正品瑞士邦顿(Bestdon)手表BD7108G男士大表盘全自动针机械表精钢军表夜光防水户外运动腕表","goods_jingle":"型号BD7108G    100米防水 终身保修 全国包邮 邦顿正品  专柜同款","gc_id_1":"530","gc_id_2":"540","gc_id_3":"587","spec_name":null,"spec_value":null,"goods_attr":null,"goods_custom":null,"mobile_body":"","goods_price":"599.00","goods_marketprice":"599.00","goods_costprice":"0.00","goods_discount":"100","goods_serial":"BD7108G","goods_storage_alarm":"0","goods_barcode":"","transport_id":"0","transport_title":"","goods_freight":"0.00","goods_vat":"0","areaid_1":"19","areaid_2":"289","goods_stcids":"","plateid_top":"0","plateid_bottom":"0","is_virtual":"0","virtual_indate":"0","virtual_limit":"0","virtual_invalid_refund":"0","sup_id":"0","is_own_mall":"0","goods_id":"109928","goods_promotion_price":"599.00","goods_promotion_type":"0","goods_click":85,"goods_salenum":"0","goods_collect":"1","goods_spec":null,"goods_storage":"1597","color_id":"0","evaluation_good_star":"5","evaluation_count":"0","is_fcode":"0","is_presell":"0","presell_deliverdate":"0","is_book":"0","book_down_payment":"0.00","book_final_payment":"0.00","book_down_time":"0","book_buyers":"0","have_gift":"0","contract_1":"0","contract_2":"0","contract_3":"0","contract_4":"0","contract_5":"0","contract_6":"0","contract_7":"0","contract_8":"0","contract_9":"0","contract_10":"0","is_chain":"0","invite_rate":"0.00","sole_info":[],"groupbuy_info":null,"xianshi_info":null,"jjg_info":null,"cart":1,"buynow":1,"contractlist":[],"goods_url":"http://www.mingpinmall.cn/pinpai/index.php?app=goods&wwi=index&goods_id=109928"}
         * spec_image : ["http://www.mingpinmall.cn/data/upload/mall/common/default_goods_image_60.gif"]
         * mansong_info : null
         * gift_array : []
         * goods_commend_list : [{"goods_id":"109927","goods_name":"正品瑞士邦顿（bestdon）手表BD7768L陶瓷女表全自动机械贝壳面镶钻蓝宝石商务时尚白领腕表","goods_price":null,"goods_promotion_price":"1299.00","goods_image_url":"http://www.mingpinmall.cn/data/upload/mall/common/default_goods_image_240.gif"},{"goods_id":"109898","goods_name":"正品邦顿（bestdon）超薄手表女款正品时尚潮流简约休闲防水真皮带女表情侣石英表","goods_price":null,"goods_promotion_price":"258.00","goods_image_url":"http://www.mingpinmall.cn/data/upload/mall/common/default_goods_image_240.gif"},{"goods_id":"109925","goods_name":"邦顿（Bestdon）手表男士机械表 全自动夜光时尚多功能精钢防水皮带男表 镂空大表盘腕表 银壳黑面","goods_price":null,"goods_promotion_price":"499.00","goods_image_url":"http://www.mingpinmall.cn/data/upload/mall/common/default_goods_image_240.gif"},{"goods_id":"109928","goods_name":"正品瑞士邦顿(Bestdon)手表BD7108G男士大表盘全自动针机械表精钢军表夜光防水户外运动腕表","goods_price":null,"goods_promotion_price":"599.00","goods_image_url":"http://www.mingpinmall.cn/data/upload/mall/common/default_goods_image_240.gif"},{"goods_id":"109926","goods_name":"正品 瑞士邦顿（bestdon）飞轮全自动机械表男表精钢男士手表真皮带夜光防水镂空大表盘","goods_price":null,"goods_promotion_price":"428.00","goods_image_url":"http://www.mingpinmall.cn/data/upload/mall/common/default_goods_image_240.gif"},{"goods_id":"109924","goods_name":"正品瑞士邦顿（bestdon）情侣手表 石英表超薄防水情侣手表真皮表带","goods_price":null,"goods_promotion_price":"388.00","goods_image_url":"http://www.mingpinmall.cn/data/upload/mall/common/default_goods_image_240.gif"}]
         * store_info : {"store_id":"7","store_name":"瑞士邦顿（bestdon）手表旗舰店","member_id":"30361","member_name":"puppet","is_own_mall":"0","goods_count":"6","store_credit":{"store_desccredit":{"text":"描述","credit":"5.0","percent":"----","percent_class":"equal","percent_text":"平"},"store_servicecredit":{"text":"服务","credit":"5.0","percent":"----","percent_class":"equal","percent_text":"平"},"store_deliverycredit":{"text":"物流","credit":"5.0","percent":"----","percent_class":"equal","percent_text":"平"}},"new_storeCredit":[{"text":"描述","credit":"5.0","percent":"----","percent_class":"equal","percent_text":"平"},{"text":"服务","credit":"5.0","percent":"----","percent_class":"equal","percent_text":"平"},{"text":"物流","credit":"5.0","percent":"----","percent_class":"equal","percent_text":"平"}]}
         * spec_list : {"":"109928"}
         * goods_image : http://www.mingpinmall.cn/data/upload/mall/common/default_goods_image_360.gif,http://www.mingpinmall.cn/data/upload/mall/common/default_goods_image_360.gif,http://www.mingpinmall.cn/data/upload/mall/common/default_goods_image_360.gif,http://www.mingpinmall.cn/data/upload/mall/common/default_goods_image_360.gif,http://www.mingpinmall.cn/data/upload/mall/common/default_goods_image_360.gif
         * goods_eval_list : []
         * goods_evaluate_info : {"good":0,"normal":0,"bad":0,"all":0,"img":0,"good_percent":100,"normal_percent":0,"bad_percent":0,"good_star":5,"star_average":5}
         * goods_hair_info : {"content":"免运费","if_store_cn":"有货","if_store":true,"area_name":"全国"}
         */

        private GoodsInfo goods_info;
        private Object mansong_info;
        private StoreInfoBean store_info;
        private SpecListBean spec_list;
        private String goods_image;
        private GoodsEvaluateInfoBean goods_evaluate_info;
        private GoodsHairInfoBean goods_hair_info;
        private List<String> spec_image;
        private List<?> gift_array;
        private List<GoodsInfo> goods_commend_list;
        public List<GoodsComment> goods_eval_list;

        public GoodsInfo getGoods_info() {
            return goods_info;
        }

        public void setGoods_info(GoodsInfo goods_info) {
            this.goods_info = goods_info;
        }

        public Object getMansong_info() {
            return mansong_info;
        }

        public void setMansong_info(Object mansong_info) {
            this.mansong_info = mansong_info;
        }

        public StoreInfoBean getStore_info() {
            return store_info;
        }

        public void setStore_info(StoreInfoBean store_info) {
            this.store_info = store_info;
        }

        public SpecListBean getSpec_list() {
            return spec_list;
        }

        public void setSpec_list(SpecListBean spec_list) {
            this.spec_list = spec_list;
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

        public List<String> getSpec_image() {
            return spec_image;
        }

        public void setSpec_image(List<String> spec_image) {
            this.spec_image = spec_image;
        }

        public List<?> getGift_array() {
            return gift_array;
        }

        public void setGift_array(List<?> gift_array) {
            this.gift_array = gift_array;
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

        public static class StoreInfoBean {
            /**
             * store_id : 7
             * store_name : 瑞士邦顿（bestdon）手表旗舰店
             * member_id : 30361
             * member_name : puppet
             * is_own_mall : 0
             * goods_count : 6
             * store_credit : {"store_desccredit":{"text":"描述","credit":"5.0","percent":"----","percent_class":"equal","percent_text":"平"},"store_servicecredit":{"text":"服务","credit":"5.0","percent":"----","percent_class":"equal","percent_text":"平"},"store_deliverycredit":{"text":"物流","credit":"5.0","percent":"----","percent_class":"equal","percent_text":"平"}}
             * new_storeCredit : [{"text":"描述","credit":"5.0","percent":"----","percent_class":"equal","percent_text":"平"},{"text":"服务","credit":"5.0","percent":"----","percent_class":"equal","percent_text":"平"},{"text":"物流","credit":"5.0","percent":"----","percent_class":"equal","percent_text":"平"}]
             */

            private String store_id;
            private String store_name;
            private String member_id;
            private String member_name;
            private String is_own_mall;
            private String goods_count;
            private List<NewStoreCreditBean> new_storeCredit;

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

            public static class NewStoreCreditBean {
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

        public static class SpecListBean {
            /**
             * : 109928
             */

            @SerializedName("")
            public String _$101; // FIXME check this code

            public String get_$101() {
                return _$101;
            }

            public void set_$101(String _$101) {
                this._$101 = _$101;
            }
        }

        public static class GoodsEvaluateInfoBean {
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

            public int good;
            public int normal;
            public int bad;
            public int all;
            public int img;
            public int good_percent;
            public int normal_percent;
            public int bad_percent;
            public int good_star;
            public int star_average;

            public int getGood() {
                return good;
            }

            public void setGood(int good) {
                this.good = good;
            }

            public int getNormal() {
                return normal;
            }

            public void setNormal(int normal) {
                this.normal = normal;
            }

            public int getBad() {
                return bad;
            }

            public void setBad(int bad) {
                this.bad = bad;
            }

            public int getAll() {
                return all;
            }

            public void setAll(int all) {
                this.all = all;
            }

            public int getImg() {
                return img;
            }

            public void setImg(int img) {
                this.img = img;
            }

            public int getGood_percent() {
                return good_percent;
            }

            public void setGood_percent(int good_percent) {
                this.good_percent = good_percent;
            }

            public int getNormal_percent() {
                return normal_percent;
            }

            public void setNormal_percent(int normal_percent) {
                this.normal_percent = normal_percent;
            }

            public int getBad_percent() {
                return bad_percent;
            }

            public void setBad_percent(int bad_percent) {
                this.bad_percent = bad_percent;
            }

            public int getGood_star() {
                return good_star;
            }

            public void setGood_star(int good_star) {
                this.good_star = good_star;
            }

            public int getStar_average() {
                return star_average;
            }

            public void setStar_average(int star_average) {
                this.star_average = star_average;
            }
        }

        public static class GoodsHairInfoBean {
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