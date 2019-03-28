package com.mingpinmall.classz;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * 文本bean
 * Created by free46000 on 2017/3/19.
 */
public class TextBean extends BaseObservable {
    private String text;

    public TextBean(String text) {
        this.text = text;
    }

    @Bindable
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(BR.text);
    }

    @Override
    public String toString() {
        return text;
    }
}
