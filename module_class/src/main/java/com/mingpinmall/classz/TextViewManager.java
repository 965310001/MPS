package com.mingpinmall.classz;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;

/**
 * @author free46000  2017/03/17
 * @version v1.0
 */
public class TextViewManager<T extends TextBean> extends BaseViewHolderManager<T> {



    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, @NonNull T data) {
        TextView textView = getView(holder, R.id.text);
        textView.setText(data.getText());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_text;
    }

}
