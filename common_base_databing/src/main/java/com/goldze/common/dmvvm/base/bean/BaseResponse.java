package com.goldze.common.dmvvm.base.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author GuoFeng
 * @date :2019/1/15 20:53
 * @description: 类仅供参考，实际业务返回的固定字段, 根据需求来定义，
 */

public class BaseResponse<T> extends BaseBean {

    private final int SUCCESS = 200;
    private final int FAIL = 500;
    private final int ERROR = 400;
    private final int NO_LOGIN = 300;

    private int code;
    private int errorCode;
    private String message;
    private String errorMsg;
    @SerializedName("datas")
    private T data; //result

    public boolean isSuccess() {
        return errorCode == SUCCESS;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

}