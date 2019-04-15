package com.mingpinmall.classz.ui.vm.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.goldze.common.dmvvm.base.bean.BaseBean;

import java.util.List;

/**
 * 店铺首页
 */
public class StoreHomeInfo extends BaseBean {

    /**
     * code : 200
     * datas : {"store_info":{"store_id":"7","store_name":"瑞士邦顿（bestdon）手表旗舰店","member_id":"30361","store_avatar":"http://192.168.0.44/data/upload/mall/store/05262125366598649_sm.jpg","goods_count":6,"store_collect":236,"is_favorate":false,"is_own_mall":false,"store_credit_text":"描述: 5.0, 服务: 5.0, 物流: 5.0","mb_title_img":"","mb_sliders":[]},"rec_goods_list_count":6,"rec_goods_list":[{"goods_id":"109928","store_id":"7","store_name":"瑞士邦顿（bestdon）手表旗舰店","goods_name":"正品瑞士邦顿(Bestdon)手表BD7108G男士大表盘全自动针机械表精钢军表夜光防水户外运动腕表","goods_price":"599.00","goods_marketprice":"599.00","goods_image":"7_05175802468129745.jpg","goods_salenum":"2","evaluation_good_star":"5","evaluation_count":"0","is_virtual":"0","is_presell":"0","is_fcode":"0","have_gift":"0","goods_addtime":"1472822300","sole_flag":false,"group_flag":false,"xianshi_flag":false,"goods_image_url":"http://192.168.0.44/data/upload/mall/common/default_goods_image_360.gif"},{"goods_id":"109927","store_id":"7","store_name":"瑞士邦顿（bestdon）手表旗舰店","goods_name":"正品瑞士邦顿（bestdon）手表BD7768L陶瓷女表全自动机械贝壳面镶钻蓝宝石商务时尚白领腕表","goods_price":"1299.00","goods_marketprice":"1299.00","goods_image":"7_05175142296406902.jpg","goods_salenum":"0","evaluation_good_star":"5","evaluation_count":"0","is_virtual":"0","is_presell":"0","is_fcode":"0","have_gift":"0","goods_addtime":"1472822204","sole_flag":false,"group_flag":false,"xianshi_flag":false,"goods_image_url":"http://192.168.0.44/data/upload/mall/common/default_goods_image_360.gif"},{"goods_id":"109926","store_id":"7","store_name":"瑞士邦顿（bestdon）手表旗舰店","goods_name":"正品 瑞士邦顿（bestdon）飞轮全自动机械表男表精钢男士手表真皮带夜光防水镂空大表盘","goods_price":"428.00","goods_marketprice":"1280.00","goods_image":"7_05176160208432776.jpg","goods_salenum":"0","evaluation_good_star":"5","evaluation_count":"0","is_virtual":"0","is_presell":"0","is_fcode":"0","have_gift":"0","goods_addtime":"1472822168","sole_flag":false,"group_flag":false,"xianshi_flag":false,"goods_image_url":"http://192.168.0.44/data/upload/mall/common/default_goods_image_360.gif"},{"goods_id":"109925","store_id":"7","store_name":"瑞士邦顿（bestdon）手表旗舰店","goods_name":"邦顿（Bestdon）手表男士机械表 全自动夜光时尚多功能精钢防水皮带男表 镂空大表盘腕表 银壳黑面","goods_price":"499.00","goods_marketprice":"580.00","goods_image":"7_05261646423780923.jpg","goods_salenum":"0","evaluation_good_star":"5","evaluation_count":"0","is_virtual":"0","is_presell":"0","is_fcode":"0","have_gift":"0","goods_addtime":"1472820756","sole_flag":false,"group_flag":false,"xianshi_flag":false,"goods_image_url":"http://192.168.0.44/data/upload/mall/common/default_goods_image_360.gif"},{"goods_id":"109924","store_id":"7","store_name":"瑞士邦顿（bestdon）手表旗舰店","goods_name":"正品瑞士邦顿（bestdon）情侣手表 石英表超薄防水情侣手表真皮表带","goods_price":"388.00","goods_marketprice":"588.00","goods_image":"7_05177797788906979.jpg","goods_salenum":"0","evaluation_good_star":"5","evaluation_count":"0","is_virtual":"0","is_presell":"0","is_fcode":"0","have_gift":"0","goods_addtime":"1472819882","sole_flag":false,"group_flag":false,"xianshi_flag":false,"goods_image_url":"http://192.168.0.44/data/upload/mall/common/default_goods_image_360.gif"},{"goods_id":"109898","store_id":"7","store_name":"瑞士邦顿（bestdon）手表旗舰店","goods_name":"正品邦顿（bestdon）超薄手表女款正品时尚潮流简约休闲防水真皮带女表情侣石英表","goods_price":"258.00","goods_marketprice":"860.00","goods_image":"7_05176148641405253.jpg","goods_salenum":"0","evaluation_good_star":"5","evaluation_count":"0","is_virtual":"0","is_presell":"0","is_fcode":"0","have_gift":"0","goods_addtime":"1464271164","sole_flag":false,"group_flag":false,"xianshi_flag":false,"goods_image_url":"http://192.168.0.44/data/upload/mall/common/default_goods_image_360.gif"}]}
     */

    private int code;
    private DatasBean datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean extends BaseBean implements MultiItemEntity {
        /**
         * store_info : {"store_id":"7","store_name":"瑞士邦顿（bestdon）手表旗舰店","member_id":"30361","store_avatar":"http://192.168.0.44/data/upload/mall/store/05262125366598649_sm.jpg","goods_count":6,"store_collect":236,"is_favorate":false,"is_own_mall":false,"store_credit_text":"描述: 5.0, 服务: 5.0, 物流: 5.0","mb_title_img":"","mb_sliders":[]}
         * rec_goods_list_count : 6
         * rec_goods_list : [{"goods_id":"109928","store_id":"7","store_name":"瑞士邦顿（bestdon）手表旗舰店","goods_name":"正品瑞士邦顿(Bestdon)手表BD7108G男士大表盘全自动针机械表精钢军表夜光防水户外运动腕表","goods_price":"599.00","goods_marketprice":"599.00","goods_image":"7_05175802468129745.jpg","goods_salenum":"2","evaluation_good_star":"5","evaluation_count":"0","is_virtual":"0","is_presell":"0","is_fcode":"0","have_gift":"0","goods_addtime":"1472822300","sole_flag":false,"group_flag":false,"xianshi_flag":false,"goods_image_url":"http://192.168.0.44/data/upload/mall/common/default_goods_image_360.gif"},{"goods_id":"109927","store_id":"7","store_name":"瑞士邦顿（bestdon）手表旗舰店","goods_name":"正品瑞士邦顿（bestdon）手表BD7768L陶瓷女表全自动机械贝壳面镶钻蓝宝石商务时尚白领腕表","goods_price":"1299.00","goods_marketprice":"1299.00","goods_image":"7_05175142296406902.jpg","goods_salenum":"0","evaluation_good_star":"5","evaluation_count":"0","is_virtual":"0","is_presell":"0","is_fcode":"0","have_gift":"0","goods_addtime":"1472822204","sole_flag":false,"group_flag":false,"xianshi_flag":false,"goods_image_url":"http://192.168.0.44/data/upload/mall/common/default_goods_image_360.gif"},{"goods_id":"109926","store_id":"7","store_name":"瑞士邦顿（bestdon）手表旗舰店","goods_name":"正品 瑞士邦顿（bestdon）飞轮全自动机械表男表精钢男士手表真皮带夜光防水镂空大表盘","goods_price":"428.00","goods_marketprice":"1280.00","goods_image":"7_05176160208432776.jpg","goods_salenum":"0","evaluation_good_star":"5","evaluation_count":"0","is_virtual":"0","is_presell":"0","is_fcode":"0","have_gift":"0","goods_addtime":"1472822168","sole_flag":false,"group_flag":false,"xianshi_flag":false,"goods_image_url":"http://192.168.0.44/data/upload/mall/common/default_goods_image_360.gif"},{"goods_id":"109925","store_id":"7","store_name":"瑞士邦顿（bestdon）手表旗舰店","goods_name":"邦顿（Bestdon）手表男士机械表 全自动夜光时尚多功能精钢防水皮带男表 镂空大表盘腕表 银壳黑面","goods_price":"499.00","goods_marketprice":"580.00","goods_image":"7_05261646423780923.jpg","goods_salenum":"0","evaluation_good_star":"5","evaluation_count":"0","is_virtual":"0","is_presell":"0","is_fcode":"0","have_gift":"0","goods_addtime":"1472820756","sole_flag":false,"group_flag":false,"xianshi_flag":false,"goods_image_url":"http://192.168.0.44/data/upload/mall/common/default_goods_image_360.gif"},{"goods_id":"109924","store_id":"7","store_name":"瑞士邦顿（bestdon）手表旗舰店","goods_name":"正品瑞士邦顿（bestdon）情侣手表 石英表超薄防水情侣手表真皮表带","goods_price":"388.00","goods_marketprice":"588.00","goods_image":"7_05177797788906979.jpg","goods_salenum":"0","evaluation_good_star":"5","evaluation_count":"0","is_virtual":"0","is_presell":"0","is_fcode":"0","have_gift":"0","goods_addtime":"1472819882","sole_flag":false,"group_flag":false,"xianshi_flag":false,"goods_image_url":"http://192.168.0.44/data/upload/mall/common/default_goods_image_360.gif"},{"goods_id":"109898","store_id":"7","store_name":"瑞士邦顿（bestdon）手表旗舰店","goods_name":"正品邦顿（bestdon）超薄手表女款正品时尚潮流简约休闲防水真皮带女表情侣石英表","goods_price":"258.00","goods_marketprice":"860.00","goods_image":"7_05176148641405253.jpg","goods_salenum":"0","evaluation_good_star":"5","evaluation_count":"0","is_virtual":"0","is_presell":"0","is_fcode":"0","have_gift":"0","goods_addtime":"1464271164","sole_flag":false,"group_flag":false,"xianshi_flag":false,"goods_image_url":"http://192.168.0.44/data/upload/mall/common/default_goods_image_360.gif"}]
         */

        private int itemType;
        private String title;

        /*首页数据*/
        private StoreInfoBean store_info;
        private int rec_goods_list_count;
        private List<GoodsInfo> rec_goods_list;

        /*收藏排行 销量排行*/
        private List<GoodsInfo> goods_list;

        public StoreInfoBean getStore_info() {
            return store_info;
        }

        public void setStore_info(StoreInfoBean store_info) {
            this.store_info = store_info;
        }

        public int getRec_goods_list_count() {
            return rec_goods_list_count;
        }

        public void setRec_goods_list_count(int rec_goods_list_count) {
            this.rec_goods_list_count = rec_goods_list_count;
        }

        public List<GoodsInfo> getRec_goods_list() {
            return rec_goods_list;
        }

        public List<GoodsInfo> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsInfo> goods_list) {
            this.goods_list = goods_list;
        }

        public void setRec_goods_list(List<GoodsInfo> rec_goods_list) {
            this.rec_goods_list = rec_goods_list;
        }

        @Override
        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public static class StoreInfoBean {
            /**
             * store_id : 7
             * store_name : 瑞士邦顿（bestdon）手表旗舰店
             * member_id : 30361
             * store_avatar : http://192.168.0.44/data/upload/mall/store/05262125366598649_sm.jpg
             * goods_count : 6
             * store_collect : 236
             * is_favorate : false
             * is_own_mall : false
             * store_credit_text : 描述: 5.0, 服务: 5.0, 物流: 5.0
             * mb_title_img :
             * mb_sliders : []
             */

            private String store_id;
            private String store_name;
            private String member_id;
            private String store_avatar;
            private int goods_count;
            private int store_collect;
            private boolean is_favorate;
            private boolean is_own_mall;
            private String store_credit_text;
            private String mb_title_img;
            private List<?> mb_sliders;

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

            public String getStore_avatar() {
                return store_avatar;
            }

            public void setStore_avatar(String store_avatar) {
                this.store_avatar = store_avatar;
            }

            public int getGoods_count() {
                return goods_count;
            }

            public void setGoods_count(int goods_count) {
                this.goods_count = goods_count;
            }

            public int getStore_collect() {
                return store_collect;
            }

            public void setStore_collect(int store_collect) {
                this.store_collect = store_collect;
            }

            public boolean isIs_favorate() {
                return is_favorate;
            }

            public void setIs_favorate(boolean is_favorate) {
                this.is_favorate = is_favorate;
            }

            public boolean isIs_own_mall() {
                return is_own_mall;
            }

            public void setIs_own_mall(boolean is_own_mall) {
                this.is_own_mall = is_own_mall;
            }

            public String getStore_credit_text() {
                return store_credit_text;
            }

            public void setStore_credit_text(String store_credit_text) {
                this.store_credit_text = store_credit_text;
            }

            public String getMb_title_img() {
                return mb_title_img;
            }

            public void setMb_title_img(String mb_title_img) {
                this.mb_title_img = mb_title_img;
            }

            public List<?> getMb_sliders() {
                return mb_sliders;
            }

            public void setMb_sliders(List<?> mb_sliders) {
                this.mb_sliders = mb_sliders;
            }
        }

//        public static class RecGoodsListBean {
//            /**
//             * goods_id : 109928
//             * store_id : 7
//             * store_name : 瑞士邦顿（bestdon）手表旗舰店
//             * goods_name : 正品瑞士邦顿(Bestdon)手表BD7108G男士大表盘全自动针机械表精钢军表夜光防水户外运动腕表
//             * goods_price : 599.00
//             * goods_marketprice : 599.00
//             * goods_image : 7_05175802468129745.jpg
//             * goods_salenum : 2
//             * evaluation_good_star : 5
//             * evaluation_count : 0
//             * is_virtual : 0
//             * is_presell : 0
//             * is_fcode : 0
//             * have_gift : 0
//             * goods_addtime : 1472822300
//             * sole_flag : false
//             * group_flag : false
//             * xianshi_flag : false
//             * goods_image_url : http://192.168.0.44/data/upload/mall/common/default_goods_image_360.gif
//             */
//
//            private String goods_id;
//            private String store_id;
//            private String store_name;
//            private String goods_name;
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
//            private String goods_addtime;
//            private boolean sole_flag;
//            private boolean group_flag;
//            private boolean xianshi_flag;
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
//            public String getStore_name() {
//                return store_name;
//            }
//
//            public void setStore_name(String store_name) {
//                this.store_name = store_name;
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
//            public String getGoods_addtime() {
//                return goods_addtime;
//            }
//
//            public void setGoods_addtime(String goods_addtime) {
//                this.goods_addtime = goods_addtime;
//            }
//
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
