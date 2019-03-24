package com.goldze.common.dmvvm.http.rx;


import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.goldze.common.dmvvm.base.bean.BaseResponse;


/**
 * @author GuoFeng
 * @date : 2019/1/23 11:22
 * @description: Observer封装
 */
public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {

    @Override
    public void onChanged(@Nullable BaseResponse<T> response) {
        if (response != null) {
            T data = response.getData();
            if (data != null) {
                callback(data);
            } else {
                if (response.getErrorCode() != 0) {
                    onError(response.getErrorMsg());
                }
            }
        }
    }

    public void onError(String msg) {

    }

    public void onError(Throwable throwable) {
    }

    public abstract void callback(T response);
}
