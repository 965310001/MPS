package com.mingpinmall.me.ui.bean;

import java.io.Serializable;

/**
 * 功能描述：我的推广码
 * 创建人：小斌
 * 创建时间: 2019/4/29
 **/
public class ReduceCashBean implements Serializable {

    private String available_predeposit;

    public String getAvailable_predeposit() {
        return available_predeposit == null ? "" : available_predeposit;
    }

    public void setAvailable_predeposit(String available_predeposit) {
        this.available_predeposit = available_predeposit == null ? "" : available_predeposit;
    }
}
