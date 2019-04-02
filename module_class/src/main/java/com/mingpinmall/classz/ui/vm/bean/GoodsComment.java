package com.mingpinmall.classz.ui.vm.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;

import java.util.List;
import java.util.Objects;

/**
 * 商品评价
 */
public class GoodsComment extends BaseBean {

    /**
     * geval_scores : 5
     * geval_content : 好评，发货快，东西适合！
     * geval_addtime : 1433494597
     * geval_frommemberid : 156
     * geval_frommembername : z***7
     * geval_explain : null
     * geval_content_again :
     * geval_addtime_again : 0
     * geval_explain_again :
     * member_avatar : http://www.mingpinmall.cn/data/upload/mall/common/05234670838512007.gif
     * geval_addtime_date : 2015-06-05
     * geval_image_240 : []
     * geval_image_1024 : []
     * geval_addtime_again_date : 1970-01-01
     * geval_image_again_240 : []
     * geval_image_again_1024 : []
     */

    private String geval_scores;
    private String geval_content;
    private String geval_addtime;
    private String geval_frommemberid;
    private String geval_frommembername;
    private Object geval_explain;
    private String geval_content_again;
    private String geval_addtime_again;
    private String geval_explain_again;
    private String member_avatar;
    private String geval_addtime_date;
    private String geval_addtime_again_date;
//    private List<?> geval_image_240;
//    private List<?> geval_image_1024;
//    private List<?> geval_image_again_240;
//    private List<?> geval_image_again_1024;

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

    public String getGeval_addtime() {
        return geval_addtime;
    }

    public void setGeval_addtime(String geval_addtime) {
        this.geval_addtime = geval_addtime;
    }

    public String getGeval_frommemberid() {
        return geval_frommemberid;
    }

    public void setGeval_frommemberid(String geval_frommemberid) {
        this.geval_frommemberid = geval_frommemberid;
    }

    public String getGeval_frommembername() {
        return geval_frommembername;
    }

    public void setGeval_frommembername(String geval_frommembername) {
        this.geval_frommembername = geval_frommembername;
    }

    public Object getGeval_explain() {
        return geval_explain;
    }

    public void setGeval_explain(Object geval_explain) {
        this.geval_explain = geval_explain;
    }

    public String getGeval_content_again() {
        return geval_content_again;
    }

    public void setGeval_content_again(String geval_content_again) {
        this.geval_content_again = geval_content_again;
    }

    public String getGeval_addtime_again() {
        return geval_addtime_again;
    }

    public void setGeval_addtime_again(String geval_addtime_again) {
        this.geval_addtime_again = geval_addtime_again;
    }

    public String getGeval_explain_again() {
        return geval_explain_again;
    }

    public void setGeval_explain_again(String geval_explain_again) {
        this.geval_explain_again = geval_explain_again;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoodsComment that = (GoodsComment) o;
        return Objects.equals(geval_scores, that.geval_scores) &&
                Objects.equals(geval_content, that.geval_content) &&
                Objects.equals(geval_addtime, that.geval_addtime) &&
                Objects.equals(geval_frommemberid, that.geval_frommemberid) &&
                Objects.equals(geval_frommembername, that.geval_frommembername) &&
                Objects.equals(geval_explain, that.geval_explain) &&
                Objects.equals(geval_content_again, that.geval_content_again) &&
                Objects.equals(geval_addtime_again, that.geval_addtime_again) &&
                Objects.equals(geval_explain_again, that.geval_explain_again) &&
                Objects.equals(member_avatar, that.member_avatar) &&
                Objects.equals(geval_addtime_date, that.geval_addtime_date) &&
                Objects.equals(geval_addtime_again_date, that.geval_addtime_again_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(geval_scores, geval_content, geval_addtime, geval_frommemberid, geval_frommembername, geval_explain, geval_content_again, geval_addtime_again, geval_explain_again, member_avatar, geval_addtime_date, geval_addtime_again_date);
    }
}
