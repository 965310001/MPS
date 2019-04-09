package com.goldze.common.dmvvm.base.bean;

import com.google.gson.annotations.SerializedName;

public class CheckSmsCodeBean extends BaseBean {

    private int code;
    @SerializedName("error")
    private String message;
    private int datas;

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message == null ? "" : message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getDatas() {
        return datas;
    }

    public void setDatas(int datas) {
        this.datas = datas;
    }
}
