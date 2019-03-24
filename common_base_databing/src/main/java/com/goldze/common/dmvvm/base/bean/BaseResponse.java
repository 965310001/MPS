package com.goldze.common.dmvvm.base.bean;

/**
 * @author GuoFeng
 * @date :2019/1/15 20:53
 * @description: 类仅供参考，实际业务返回的固定字段, 根据需求来定义，
 */

public class BaseResponse<T> extends BaseBean {

    private int code;
    private int errorCode;
    private String message;
    private String errorMsg;
    private T data; //result


    public String getErrorMsg() {
        return errorMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isOk() {
        return code == 0;
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

    public boolean isSuccess() {
        return errorCode == 0;
    }

    public int getErrorCode() {
        return errorCode;
    }
}


