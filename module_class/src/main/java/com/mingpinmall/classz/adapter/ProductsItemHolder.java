package com.mingpinmall.classz.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;

import android.view.View;

import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.ItemProductsBinding;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;

import com.trecyclerview.holder.AbsHolder;
import com.trecyclerview.holder.AbsItemHolder;

public class ProductsItemHolder extends AbsItemHolder<GoodsInfo, ProductsItemHolder.ViewHolder> {

//    private ItemProductsBinding binding;

    public ProductsItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_products;
    }

    @Override
    public ProductsItemHolder.ViewHolder createViewHolder(View view) {
        return new ViewHolder(DataBindingUtil.bind(view).getRoot());
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductsItemHolder.ViewHolder holder, @NonNull GoodsInfo data) {
//        binding = DataBindingUtil.getBinding(holder.itemView);
//        binding.setData(data);

        holder.setData(data);
    }

    class ViewHolder extends AbsHolder {

        private ItemProductsBinding binding;

        private ViewHolder(View itemView) {
            super(itemView);
        }

        ItemProductsBinding getBinding() {
            binding = DataBindingUtil.getBinding(itemView);
            return binding;
        }

        protected void setData(GoodsInfo data) {
            getBinding().setData(data);
        }

    }

}