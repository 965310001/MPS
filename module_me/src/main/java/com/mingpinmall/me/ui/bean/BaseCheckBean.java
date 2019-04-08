package com.mingpinmall.me.ui.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/2
 **/
public class BaseCheckBean {

    private boolean state;
    private String mobile;

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
