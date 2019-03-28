package com.mingpinmall.classz.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;

import android.support.annotation.NonNull;

import android.view.View;
import android.widget.TextView;

import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.ItemProductsBinding;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;

import com.trecyclerview.holder.AbsHolder;
import com.trecyclerview.holder.AbsItemHolder;

public class ProductsItemHolder extends AbsItemHolder<GoodsInfo, ProductsItemHolder.ViewHolder> {

    private ItemProductsBinding binding;

    public ProductsItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_products;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        binding = DataBindingUtil.bind(view);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GoodsInfo data) {
        binding = DataBindingUtil.getBinding(holder.itemView);
        binding.setData(data);
        binding.executePendingBindings();
    }

    class ViewHolder extends AbsHolder {
        private ViewHolder(View itemView) {
            super(itemView);
        }
    }

}