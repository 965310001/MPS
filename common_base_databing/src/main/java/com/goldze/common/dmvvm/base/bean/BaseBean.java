package com.goldze.common.dmvvm.base.bean;

import android.databinding.BaseObservable;

import java.io.Serializable;

public class BaseBean extends BaseObservable implements Serializable {

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}