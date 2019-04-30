package com.mingpinmall.me.ui.bean;

import java.util.List;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/30
 **/
public class OrderEvaluateInfoBean {

    private List<String> picPath;
    private boolean isAnonymity;
    private String evaluateText;
    private int stars = 0;

    public List<String> getPicPath() {
        return picPath;
    }

    public void setPicPath(List<String> picPath) {
        this.picPath = picPath;
    }

    public boolean isAnonymity() {
        return isAnonymity;
    }

    public void setAnonymity(boolean anonymity) {
        isAnonymity = anonymity;
    }

    public String getEvaluateText() {
        return evaluateText == null ? "" : evaluateText;
    }

    public void setEvaluateText(String evaluateText) {
        this.evaluateText = evaluateText == null ? "" : evaluateText;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
