package com.mingpinmall.me.ui.bean;

import java.io.Serializable;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/2
 **/
public class BaseCheckBean implements Serializable {

    private boolean state;
    private String mobile;
    private String mobile_full;

    public String getMobile_full() {
        return mobile_full == null ? "" : mobile_full;
    }

    public void setMobile_full(String mobile_full) {
        this.mobile_full = mobile_full == null ? "" : mobile_full;
    }

    public String getMobile() {
        return mobile == null ? "" : mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? "" : mobile;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

}
