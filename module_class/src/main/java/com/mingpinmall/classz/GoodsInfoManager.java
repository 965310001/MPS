package com.mingpinmall.classz;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;

public class GoodsInfoManager<T extends GoodsInfo> extends BaseViewHolderManager<T> {

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, @NonNull T data) {
        TextView textView = getView(holder, R.id.text);
        textView.setText(data.getGoods_name());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_text;
    }
}