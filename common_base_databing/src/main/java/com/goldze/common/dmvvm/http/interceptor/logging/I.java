package com.goldze.common.dmvvm.http.interceptor.logging;

import java.util.logging.Level;

import okhttp3.internal.platform.Platform;

/**
 * @author GuoFeng
 * @date :2019/1/16 10:15
 * @description:
 */
class I {

    protected I() {
        throw new UnsupportedOperationException();
    }

    static void log(int type, String tag, String msg) {
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(tag);
        switch (type) {
            case Platform.INFO:
                logger.log(Level.INFO, msg);
                break;
            default:
                logger.log(Level.WARNING, msg);
                break;
        }
    }
}
