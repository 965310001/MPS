package com.mingpinmall.me.ui.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;

import java.util.List;

/**
 * 追加评价订单 商品列表
 * @author 小斌
 * @data 2019/6/15
 **/
public class EvaluateAgainListBean extends BaseBean {

    private StoreInfoBean store_info;
    private List<EvaluateGoodsBean> evaluate_goods;

    public StoreInfoBean getStore_info() {
        return store_info;
    }

    public void setStore_info(StoreInfoBean store_info) {
        this.store_info = store_info;
    }

    public List<EvaluateGoodsBean> getEvaluate_goods() {
        return evaluate_goods;
    }

    public void setEvaluate_goods(List<EvaluateGoodsBean> evaluate_goods) {
        this.evaluate_goods = evaluate_goods;
    }

    public static class StoreInfoBean {

        private String store_id;
        private String store_name;
        private String is_own_mall;

        public String getStore_id() {
            return store_id == null ? "" : store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id == null ? "" : store_id;
        }

        public String getStore_name() {
            return store_name == null ? "" : store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name == null ? "" : store_name;
        }

        public String getIs_own_mall() {
            return is_own_mall == null ? "" : is_own_mall;
        }

        public void setIs_own_mall(String is_own_mall) {
            this.is_own_mall = is_own_mall == null ? "" : is_own_mall;
        }
    }

    public static class EvaluateGoodsBean {

        private String geval_id;
        private String geval_orderid;
        private String geval_orderno;
        private String geval_ordergoodsid;
        private String geval_goodsid;
        private String geval_goodsname;
        private String geval_goodsprice;
        private String geval_goodsimage;
        private String geval_scores;
        private String geval_content;
        private String geval_isanonymous;
        private String geval_addtime;
        private String geval_storeid;
        private String geval_storename;
        private String geval_frommemberid;
        private String geval_frommembername;
        private Object geval_explain;
        private String geval_image;
        private String geval_content_again;
        private String geval_addtime_again;
        private String geval_image_again;
        private String geval_explain_again;

        public String getGeval_id() {
            return geval_id == null ? "" : geval_id;
        }

        public void setGeval_id(String geval_id) {
            this.geval_id = geval_id == null ? "" : geval_id;
        }

        public String getGeval_orderid() {
            return geval_orderid == null ? "" : geval_orderid;
        }

        public void setGeval_orderid(String geval_orderid) {
            this.geval_orderid = geval_orderid == null ? "" : geval_orderid;
        }

        public String getGeval_orderno() {
            return geval_orderno == null ? "" : geval_orderno;
        }

        public void setGeval_orderno(String geval_orderno) {
            this.geval_orderno = geval_orderno == null ? "" : geval_orderno;
        }

        public String getGeval_ordergoodsid() {
            return geval_ordergoodsid == null ? "" : geval_ordergoodsid;
        }

        public void setGeval_ordergoodsid(String geval_ordergoodsid) {
            this.geval_ordergoodsid = geval_ordergoodsid == null ? "" : geval_ordergoodsid;
        }

        public String getGeval_goodsid() {
            return geval_goodsid == null ? "" : geval_goodsid;
        }

        public void setGeval_goodsid(String geval_goodsid) {
            this.geval_goodsid = geval_goodsid == null ? "" : geval_goodsid;
        }

        public String getGeval_goodsname() {
            return geval_goodsname == null ? "" : geval_goodsname;
        }

        public void setGeval_goodsname(String geval_goodsname) {
            this.geval_goodsname = geval_goodsname == null ? "" : geval_goodsname;
        }

        public String getGeval_goodsprice() {
            return geval_goodsprice == null ? "" : geval_goodsprice;
        }

        public void setGeval_goodsprice(String geval_goodsprice) {
            this.geval_goodsprice = geval_goodsprice == null ? "" : geval_goodsprice;
        }

        public String getGeval_goodsimage() {
            return geval_goodsimage == null ? "" : geval_goodsimage;
        }

        public void setGeval_goodsimage(String geval_goodsimage) {
            this.geval_goodsimage = geval_goodsimage == null ? "" : geval_goodsimage;
        }

        public String getGeval_scores() {
            return geval_scores == null ? "" : geval_scores;
        }

        public void setGeval_scores(String geval_scores) {
            this.geval_scores = geval_scores == null ? "" : geval_scores;
        }

        public String getGeval_content() {
            return geval_content == null ? "" : geval_content;
        }

        public void setGeval_content(String geval_content) {
            this.geval_content = geval_content == null ? "" : geval_content;
        }

        public String getGeval_isanonymous() {
            return geval_isanonymous == null ? "" : geval_isanonymous;
        }

        public void setGeval_isanonymous(String geval_isanonymous) {
            this.geval_isanonymous = geval_isanonymous == null ? "" : geval_isanonymous;
        }

        public String getGeval_addtime() {
            return geval_addtime == null ? "" : geval_addtime;
        }

        public void setGeval_addtime(String geval_addtime) {
            this.geval_addtime = geval_addtime == null ? "" : geval_addtime;
        }

        public String getGeval_storeid() {
            return geval_storeid == null ? "" : geval_storeid;
        }

        public void setGeval_storeid(String geval_storeid) {
            this.geval_storeid = geval_storeid == null ? "" : geval_storeid;
        }

        public String getGeval_storename() {
            return geval_storename == null ? "" : geval_storename;
        }

        public void setGeval_storename(String geval_storename) {
            this.geval_storename = geval_storename == null ? "" : geval_storename;
        }

        public String getGeval_frommemberid() {
            return geval_frommemberid == null ? "" : geval_frommemberid;
        }

        public void setGeval_frommemberid(String geval_frommemberid) {
            this.geval_frommemberid = geval_frommemberid == null ? "" : geval_frommemberid;
        }

        public String getGeval_frommembername() {
            return geval_frommembername == null ? "" : geval_frommembername;
        }

        public void setGeval_frommembername(String geval_frommembername) {
            this.geval_frommembername = geval_frommembername == null ? "" : geval_frommembername;
        }

        public Object getGeval_explain() {
            return geval_explain;
        }

        public void setGeval_explain(Object geval_explain) {
            this.geval_explain = geval_explain;
        }

        public String getGeval_image() {
            return geval_image == null ? "" : geval_image;
        }

        public void setGeval_image(String geval_image) {
            this.geval_image = geval_image == null ? "" : geval_image;
        }

        public String getGeval_content_again() {
            return geval_content_again == null ? "" : geval_content_again;
        }

        public void setGeval_content_again(String geval_content_again) {
            this.geval_content_again = geval_content_again == null ? "" : geval_content_again;
        }

        public String getGeval_addtime_again() {
            return geval_addtime_again == null ? "" : geval_addtime_again;
        }

        public void setGeval_addtime_again(String geval_addtime_again) {
            this.geval_addtime_again = geval_addtime_again == null ? "" : geval_addtime_again;
        }

        public String getGeval_image_again() {
            return geval_image_again == null ? "" : geval_image_again;
        }

        public void setGeval_image_again(String geval_image_again) {
            this.geval_image_again = geval_image_again == null ? "" : geval_image_again;
        }

        public String getGeval_explain_again() {
            return geval_explain_again == null ? "" : geval_explain_again;
        }

        public void setGeval_explain_again(String geval_explain_again) {
            this.geval_explain_again = geval_explain_again == null ? "" : geval_explain_again;
        }
    }
}
