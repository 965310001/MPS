package com.mingpinmall.classz.ui.vm.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;

import java.util.ArrayList;

public class GoodsListInfo extends BaseBean {

    /**
     * code : 200
     * hasmore : false
     * page_total : 1
     * datas : {"goods_list":[{"goods_id":"109928","store_id":"7","goods_name":"正品瑞士邦顿(Bestdon)手表BD7108G男士大表盘全自动针机械表精钢军表夜光防水户外运动腕表","goods_jingle":"型号BD7108G    100米防水 终身保修 全国包邮 邦顿正品  专柜同款","goods_price":"599.00","goods_marketprice":"599.00","goods_image":"7_05175802468129745.jpg","goods_salenum":"0","evaluation_good_star":"5","evaluation_count":"0","is_virtual":"0","is_presell":"0","is_fcode":"0","have_gift":"0","store_name":"瑞士邦顿（bestdon）手表旗舰店","is_own_mall":"0","sole_flag":false,"group_flag":false,"xianshi_flag":false,"goods_image_url":"http://39.108.254.185/data/upload/mall/common/default_goods_image_360.gif"},{"goods_id":"109927","store_id":"7","goods_name":"正品瑞士邦顿（bestdon）手表BD7768L陶瓷女表全自动机械贝壳面镶钻蓝宝石商务时尚白领腕表","goods_jingle":"全国联保，专柜正品，瑞士品牌手表，无忧售后，超精准时间，耐磨防摔蓝宝石镜面，天然珍珠母贝贝壳表面，真钻石镶钻水钻表，IP电镀奢华女表，时尚刻花点缀，316L精钢表壳，精钢间陶瓷表带。50米防水，再也不怕洗衣服进水了。","goods_price":"1299.00","goods_marketprice":"1299.00","goods_image":"7_05175142296406902.jpg","goods_salenum":"0","evaluation_good_star":"5","evaluation_count":"0","is_virtual":"0","is_presell":"0","is_fcode":"0","have_gift":"0","store_name":"瑞士邦顿（bestdon）手表旗舰店","is_own_mall":"0","sole_flag":false,"group_flag":false,"xianshi_flag":false,"goods_image_url":"http://39.108.254.185/data/upload/mall/common/default_goods_image_360.gif"},{"goods_id":"109926","store_id":"7","goods_name":"正品 瑞士邦顿（bestdon）飞轮全自动机械表男表精钢男士手表真皮带夜光防水镂空大表盘","goods_jingle":"专柜正品 厂家直销 镂空机械表","goods_price":"428.00","goods_marketprice":"1280.00","goods_image":"7_05176160208432776.jpg","goods_salenum":"0","evaluation_good_star":"5","evaluation_count":"0","is_virtual":"0","is_presell":"0","is_fcode":"0","have_gift":"0","store_name":"瑞士邦顿（bestdon）手表旗舰店","is_own_mall":"0","sole_flag":false,"group_flag":false,"xianshi_flag":false,"goods_image_url":"http://39.108.254.185/data/upload/mall/common/default_goods_image_360.gif"},{"goods_id":"109925","store_id":"7","goods_name":"邦顿（Bestdon）手表男士机械表 全自动夜光时尚多功能精钢防水皮带男表 镂空大表盘腕表 银壳黑面","goods_jingle":"品牌直营！厂家直销！货到付款！7天无理由退换货！","goods_price":"499.00","goods_marketprice":"580.00","goods_image":"7_05261646423780923.jpg","goods_salenum":"0","evaluation_good_star":"5","evaluation_count":"0","is_virtual":"0","is_presell":"0","is_fcode":"0","have_gift":"0","store_name":"瑞士邦顿（bestdon）手表旗舰店","is_own_mall":"0","sole_flag":false,"group_flag":false,"xianshi_flag":false,"goods_image_url":"http://39.108.254.185/data/upload/mall/common/default_goods_image_360.gif"},{"goods_id":"109924","store_id":"7","goods_name":"正品瑞士邦顿（bestdon）情侣手表 石英表超薄防水情侣手表真皮表带","goods_jingle":"全国包邮  专柜同款  正品保证  假一赔十  型号BD9920","goods_price":"388.00","goods_marketprice":"588.00","goods_image":"7_05177797788906979.jpg","goods_salenum":"0","evaluation_good_star":"5","evaluation_count":"0","is_virtual":"0","is_presell":"0","is_fcode":"0","have_gift":"0","store_name":"瑞士邦顿（bestdon）手表旗舰店","is_own_mall":"0","sole_flag":false,"group_flag":false,"xianshi_flag":false,"goods_image_url":"http://39.108.254.185/data/upload/mall/common/default_goods_image_360.gif"},{"goods_id":"109898","store_id":"7","goods_name":"正品邦顿（bestdon）超薄手表女款正品时尚潮流简约休闲防水真皮带女表情侣石英表","goods_jingle":"厂家直销 正品保证","goods_price":"258.00","goods_marketprice":"860.00","goods_image":"7_05176148641405253.jpg","goods_salenum":"0","evaluation_good_star":"5","evaluation_count":"0","is_virtual":"0","is_presell":"0","is_fcode":"0","have_gift":"0","store_name":"瑞士邦顿（bestdon）手表旗舰店","is_own_mall":"0","sole_flag":false,"group_flag":false,"xianshi_flag":false,"goods_image_url":"http://39.108.254.185/data/upload/mall/common/default_goods_image_360.gif"}]}
     */

    private int code;
    private boolean hasmore;
    private int page_total;
    private DatasBean datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

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

    public static class DatasBean extends BaseBean {
        private ArrayList<GoodsInfo> goods_list;

        public ArrayList<GoodsInfo> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(ArrayList<GoodsInfo> goods_list) {
            this.goods_list = goods_list;
        }

//        public static class GoodsListBean extends BaseBean {
//            /**
//             * goods_id : 109928
//             * store_id : 7
//             * goods_name : 正品瑞士邦顿(Bestdon)手表BD7108G男士大表盘全自动针机械表精钢军表夜光防水户外运动腕表
//             * goods_jingle : 型号BD7108G    100米防水 终身保修 全国包邮 邦顿正品  专柜同款
//             * goods_price : 599.00
//             * goods_marketprice : 599.00
//             * goods_image : 7_05175802468129745.jpg
//             * goods_salenum : 0
//             * evaluation_good_star : 5
//             * evaluation_count : 0
//             * is_virtual : 0
//             * is_presell : 0
//             * is_fcode : 0
//             * have_gift : 0
//             * store_name : 瑞士邦顿（bestdon）手表旗舰店
//             * is_own_mall : 0
//             * sole_flag : false
//             * group_flag : false
//             * xianshi_flag : false
//             * goods_image_url : http://39.108.254.185/data/upload/mall/common/default_goods_image_360.gif
//             */
//
//            private String goods_id;
//            private String store_id;
//            private String goods_name;
//            private String goods_jingle;
//            private String goods_price;
//            private String goods_marketprice;
//            private String goods_image;
//            private String goods_salenum;
//            private String evaluation_good_star;
//            private String evaluation_count;
//            private String is_virtual;
//            private String is_presell;
//            private String is_fcode;
//            private String have_gift;
//            private String store_name;
//            private String is_own_mall;
//            //            private boolean sole_flag;
////            private boolean group_flag;
////            private boolean xianshi_flag;
//            private String goods_image_url;
//
//            public String getGoods_id() {
//                return goods_id;
//            }
//
//            public void setGoods_id(String goods_id) {
//                this.goods_id = goods_id;
//            }
//
//            public String getStore_id() {
//                return store_id;
//            }
//
//            public void setStore_id(String store_id) {
//                this.store_id = store_id;
//            }
//
//            public String getGoods_name() {
//                return goods_name;
//            }
//
//            public void setGoods_name(String goods_name) {
//                this.goods_name = goods_name;
//            }
//
//            public String getGoods_jingle() {
//                return goods_jingle;
//            }
//
//            public void setGoods_jingle(String goods_jingle) {
//                this.goods_jingle = goods_jingle;
//            }
//
//            public String getGoods_price() {
//                return goods_price;
//            }
//
//            public void setGoods_price(String goods_price) {
//                this.goods_price = goods_price;
//            }
//
//            public String getGoods_marketprice() {
//                return goods_marketprice;
//            }
//
//            public void setGoods_marketprice(String goods_marketprice) {
//                this.goods_marketprice = goods_marketprice;
//            }
//
//            public String getGoods_image() {
//                return goods_image;
//            }
//
//            public void setGoods_image(String goods_image) {
//                this.goods_image = goods_image;
//            }
//
//            public String getGoods_salenum() {
//                return goods_salenum;
//            }
//
//            public void setGoods_salenum(String goods_salenum) {
//                this.goods_salenum = goods_salenum;
//            }
//
//            public String getEvaluation_good_star() {
//                return evaluation_good_star;
//            }
//
//            public void setEvaluation_good_star(String evaluation_good_star) {
//                this.evaluation_good_star = evaluation_good_star;
//            }
//
//            public String getEvaluation_count() {
//                return evaluation_count;
//            }
//
//            public void setEvaluation_count(String evaluation_count) {
//                this.evaluation_count = evaluation_count;
//            }
//
//            public String getIs_virtual() {
//                return is_virtual;
//            }
//
//            public void setIs_virtual(String is_virtual) {
//                this.is_virtual = is_virtual;
//            }
//
//            public String getIs_presell() {
//                return is_presell;
//            }
//
//            public void setIs_presell(String is_presell) {
//                this.is_presell = is_presell;
//            }
//
//            public String getIs_fcode() {
//                return is_fcode;
//            }
//
//            public void setIs_fcode(String is_fcode) {
//                this.is_fcode = is_fcode;
//            }
//
//            public String getHave_gift() {
//                return have_gift;
//            }
//
//            public void setHave_gift(String have_gift) {
//                this.have_gift = have_gift;
//            }
//
//            public String getStore_name() {
//                return store_name;
//            }
//
//            public void setStore_name(String store_name) {
//                this.store_name = store_name;
//            }
//
//            public String getIs_own_mall() {
//                return is_own_mall;
//            }
//
//            public void setIs_own_mall(String is_own_mall) {
//                this.is_own_mall = is_own_mall;
//            }
//
////            public boolean isSole_flag() {
////                return sole_flag;
////            }
////
////            public void setSole_flag(boolean sole_flag) {
////                this.sole_flag = sole_flag;
////            }
////
////            public boolean isGroup_flag() {
////                return group_flag;
////            }
////
////            public void setGroup_flag(boolean group_flag) {
////                this.group_flag = group_flag;
////            }
////
////            public boolean isXianshi_flag() {
////                return xianshi_flag;
////            }
////
////            public void setXianshi_flag(boolean xianshi_flag) {
////                this.xianshi_flag = xianshi_flag;
////            }
//
//            public String getGoods_image_url() {
//                return goods_image_url;
//            }
//
//            public void setGoods_image_url(String goods_image_url) {
//                this.goods_image_url = goods_image_url;
//            }
//        }
    }
}
