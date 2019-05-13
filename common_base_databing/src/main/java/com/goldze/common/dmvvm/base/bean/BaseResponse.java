package com.goldze.common.dmvvm.base.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    @SerializedName("error")
    private String message;
    @SerializedName("datas")
    private T data;

    /**
     * 补充
     * {
     *     "code": 400,
     *     "login": "0",
     *     "error": "请登录",
     *     "datas": {
     *         "error": "请登录"
     *     }
     * }
     */
    private String login;

    /**
     * 是否有更多分页
     */
    private boolean hasmore;
    /**
     * 分页页码
     */
    private int page_total;

    public String getLogin() {
        return login == null ? "" : login;
    }

    public void setLogin(String login) {
        this.login = login == null ? "" : login;
    }

    public boolean isHasmore() {
        return hasmore;
    }

    public void setHasmore(boolean hasmore) {
        this.hasmore = hasmore;
    }

    public int getPage_total() {
        return page_total;
    }

    public void setPage_total(int page_total) {
        this.page_total = page_total;
    }

    public boolean isSuccess() {
        return code == SUCCESS;
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

    public boolean isData() {
        return null != data;
    }

    public void setData(T data) {
        this.data = data;
    }

//    public int getErrorCode() {
//        return errorCode;
//    }

}