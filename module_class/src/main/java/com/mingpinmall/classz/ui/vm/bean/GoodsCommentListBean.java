package com.mingpinmall.classz.ui.vm.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;

import java.util.List;

public class GoodsCommentListBean extends BaseBean {

    /**
     * code : 200
     * hasmore : true
     * page_total : 2
     * datas : {"goods_eval_list":[{"geval_scores":"5","geval_content":"好评，发货快，东西适合！","geval_addtime":"1433494597","geval_frommemberid":"156","geval_frommembername":"z***7","geval_explain":null,"geval_content_again":"","geval_addtime_again":"0","geval_explain_again":"","member_avatar":"http://192.168.0.44/data/upload/mall/common/05234670838512007.gif","geval_addtime_date":"2015-06-05","geval_image_240":[],"geval_image_1024":[],"geval_addtime_again_date":"1970-01-01","geval_image_again_240":[],"geval_image_again_1024":[]},{"geval_scores":"4","geval_content":"质量好，价格便宜","geval_addtime":"1433494696","geval_frommemberid":"6870","geval_frommembername":"l***o","geval_explain":null,"geval_content_again":"","geval_addtime_again":"0","geval_explain_again":"","member_avatar":"http://192.168.0.44/data/upload/mall/common/05234670838512007.gif","geval_addtime_date":"2015-06-05","geval_image_240":[],"geval_image_1024":[],"geval_addtime_again_date":"1970-01-01","geval_image_again_240":[],"geval_image_again_1024":[]},{"geval_scores":"5","geval_content":"很显胖和显肚子。。","geval_addtime":"1433063565","geval_frommemberid":"220","geval_frommembername":"h***0","geval_explain":null,"geval_content_again":"","geval_addtime_again":"0","geval_explain_again":"","member_avatar":"http://192.168.0.44/data/upload/mall/common/05234670838512007.gif","geval_addtime_date":"2015-05-31","geval_image_240":[],"geval_image_1024":[],"geval_addtime_again_date":"1970-01-01","geval_image_again_240":[],"geval_image_again_1024":[]},{"geval_scores":"5","geval_content":"衣衣非常好，还想买一件，可是涨价了，以后还会来买，好评！","geval_addtime":"1433496916","geval_frommemberid":"16697","geval_frommembername":"无***9","geval_explain":null,"geval_content_again":"","geval_addtime_again":"0","geval_explain_again":"","member_avatar":"http://192.168.0.44/data/upload/mall/common/05234670838512007.gif","geval_addtime_date":"2015-06-05","geval_image_240":[],"geval_image_1024":[],"geval_addtime_again_date":"1970-01-01","geval_image_again_240":[],"geval_image_again_1024":[]},{"geval_scores":"4","geval_content":"质量不错","geval_addtime":"1433497376","geval_frommemberid":"16696","geval_frommembername":"灵***5","geval_explain":null,"geval_content_again":"","geval_addtime_again":"0","geval_explain_again":"","member_avatar":"http://192.168.0.44/data/upload/mall/common/05234670838512007.gif","geval_addtime_date":"2015-06-05","geval_image_240":[],"geval_image_1024":[],"geval_addtime_again_date":"1970-01-01","geval_image_again_240":[],"geval_image_again_1024":[]},{"geval_scores":"5","geval_content":"大小正好，款式大方，好搭配衣服，特别是这个钟表图案，很有意义，一定珍惜时间，质量也不错，价格实惠。","geval_addtime":"1431790744","geval_frommemberid":"46","geval_frommembername":"t***1","geval_explain":null,"geval_content_again":"","geval_addtime_again":"0","geval_explain_again":"","member_avatar":"http://192.168.0.44/data/upload/mall/common/05234670838512007.gif","geval_addtime_date":"2015-05-16","geval_image_240":[],"geval_image_1024":[],"geval_addtime_again_date":"1970-01-01","geval_image_again_240":[],"geval_image_again_1024":[]},{"geval_scores":"4","geval_content":"非常好，质量也很好！","geval_addtime":"1433498679","geval_frommemberid":"16694","geval_frommembername":"鲍***珍","geval_explain":null,"geval_content_again":"","geval_addtime_again":"0","geval_explain_again":"","member_avatar":"http://192.168.0.44/data/upload/mall/common/05234670838512007.gif","geval_addtime_date":"2015-06-05","geval_image_240":[],"geval_image_1024":[],"geval_addtime_again_date":"1970-01-01","geval_image_again_240":[],"geval_image_again_1024":[]},{"geval_scores":"5","geval_content":"还不错，物有所值","geval_addtime":"1433499893","geval_frommemberid":"115","geval_frommembername":"风***1","geval_explain":null,"geval_content_again":"","geval_addtime_again":"0","geval_explain_again":"","member_avatar":"http://192.168.0.44/data/upload/mall/avatar/avatar_115.jpg","geval_addtime_date":"2015-06-05","geval_image_240":[],"geval_image_1024":[],"geval_addtime_again_date":"1970-01-01","geval_image_again_240":[],"geval_image_again_1024":[]},{"geval_scores":"5","geval_content":"挺好的","geval_addtime":"1431867713","geval_frommemberid":"315","geval_frommembername":"t***6","geval_explain":null,"geval_content_again":"","geval_addtime_again":"0","geval_explain_again":"","member_avatar":"http://192.168.0.44/data/upload/mall/common/05234670838512007.gif","geval_addtime_date":"2015-05-17","geval_image_240":[],"geval_image_1024":[],"geval_addtime_again_date":"1970-01-01","geval_image_again_240":[],"geval_image_again_1024":[]},{"geval_scores":"5","geval_content":"质量很好，值","geval_addtime":"1433502169","geval_frommemberid":"12461","geval_frommembername":"飘***j","geval_explain":null,"geval_content_again":"","geval_addtime_again":"0","geval_explain_again":"","member_avatar":"http://192.168.0.44/data/upload/mall/common/05234670838512007.gif","geval_addtime_date":"2015-06-05","geval_image_240":[],"geval_image_1024":[],"geval_addtime_again_date":"1970-01-01","geval_image_again_240":[],"geval_image_again_1024":[]}]}
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

    public static class DatasBean {
        private List<GoodsComment> goods_eval_list;

        public List<GoodsComment> getGoods_eval_list() {
            return goods_eval_list;
        }

        public void setGoods_eval_list(List<GoodsComment> goods_eval_list) {
            this.goods_eval_list = goods_eval_list;
        }
    }
}
