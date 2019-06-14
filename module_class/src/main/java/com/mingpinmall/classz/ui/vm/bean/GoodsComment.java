package com.mingpinmall.classz.ui.vm.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;

import java.util.List;

/**
 * 商品评价
 */
public class GoodsComment extends BaseBean {

    private String geval_scores;
    private String geval_content;
    private String geval_frommembername;
    private String geval_explain;
    private String member_avatar;
    private String geval_addtime_date;
    private String geval_addtime_again_date;
    private List<String> geval_image_240;
    private List<String> geval_image_1024;
    private List<String> geval_image_again_240;
    private List<String> geval_image_again_1024;

    public String getGeval_scores() {
        return geval_scores;
    }

    public void setGeval_scores(String geval_scores) {
        this.geval_scores = geval_scores;
    }

    public String getGeval_content() {
        return geval_content;
    }

    public void setGeval_content(String geval_content) {
        this.geval_content = geval_content;
    }

    public String getGeval_frommembername() {
        return geval_frommembername;
    }

    public void setGeval_frommembername(String geval_frommembername) {
        this.geval_frommembername = geval_frommembername;
    }

    public String getGeval_explain() {
        return geval_explain;
    }

    public void setGeval_explain(String geval_explain) {
        this.geval_explain = geval_explain;
    }

    public String getMember_avatar() {
        return member_avatar;
    }

    public void setMember_avatar(String member_avatar) {
        this.member_avatar = member_avatar;
    }

    public String getGeval_addtime_date() {
        return geval_addtime_date;
    }

    public void setGeval_addtime_date(String geval_addtime_date) {
        this.geval_addtime_date = geval_addtime_date;
    }

    public String getGeval_addtime_again_date() {
        return geval_addtime_again_date;
    }

    public void setGeval_addtime_again_date(String geval_addtime_again_date) {
        this.geval_addtime_again_date = geval_addtime_again_date;
    }

    public List<String> getGeval_image_240() {
        return geval_image_240;
    }

    public void setGeval_image_240(List<String> geval_image_240) {
        this.geval_image_240 = geval_image_240;
    }

    public List<String> getGeval_image_1024() {
        return geval_image_1024;
    }

    public void setGeval_image_1024(List<String> geval_image_1024) {
        this.geval_image_1024 = geval_image_1024;
    }

    public List<String> getGeval_image_again_240() {
        return geval_image_again_240;
    }

    public void setGeval_image_again_240(List<String> geval_image_again_240) {
        this.geval_image_again_240 = geval_image_again_240;
    }

    public List<String> getGeval_image_again_1024() {
        return geval_image_again_1024;
    }

    public void setGeval_image_again_1024(List<String> geval_image_again_1024) {
        this.geval_image_again_1024 = geval_image_again_1024;
    }
}