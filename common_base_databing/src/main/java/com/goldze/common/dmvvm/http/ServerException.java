package com.goldze.common.dmvvm.http;

/**
 * @author GuoFeng
 * @date : 2019/1/19 17:02
 * @description: 服务器异常
 */
public class ServerException extends RuntimeException {
    public String message;
    public int code;

    public ServerException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
