package com.mingpinmall.classz.ui.vm.bean;

import android.support.annotation.NonNull;

/**
 * @author GuoFeng
 * @date : 2019/1/24 18:02
 * @description:
 */
public class TypeInfo {
    public @NonNull
    String title;

    public String type;

    public TypeInfo(@NonNull final String title) {
        this.title = title;
    }

    public TypeInfo(@NonNull String title, String type) {
        this.title = title;
        this.type = type;
    }
}
