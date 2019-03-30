package com.mingpinmall.classz.ui.vm.bean;


import android.databinding.Bindable;

import com.mingpinmall.classz.BR;
import com.goldze.common.dmvvm.base.bean.BaseBean;

import java.util.List;

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

    public class DatasBean extends BaseBean {
        private List<GoodsInfo> goods_list;

        @Bindable
        public List<GoodsInfo> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsInfo> goods_list) {
            this.goods_list = goods_list;
            notifyPropertyChanged(BR.goods_list);
        }
    }
}
