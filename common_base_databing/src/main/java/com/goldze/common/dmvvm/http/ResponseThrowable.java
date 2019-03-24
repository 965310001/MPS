package com.goldze.common.dmvvm.http;

/**
 * @author GuoFeng
 * @date :2019/1/16 11:10
 * @description: 异常类封装
 */
public class ResponseThrowable extends Exception {
    public int code;
    public String message;

    public ResponseThrowable(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}