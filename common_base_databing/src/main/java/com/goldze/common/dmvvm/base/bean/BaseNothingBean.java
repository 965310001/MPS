package com.goldze.common.dmvvm.base.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/12
 **/
public class BaseNothingBean extends BaseBean {

    private int code;
    @SerializedName("error")
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message == null ? "" : message;
    }
}
