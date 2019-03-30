package com.mingpinmall.classz.ui.vm;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.View;

import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.ClassifyItemOfBrandRighitListBinding;
import com.mingpinmall.classz.ui.vm.bean.BrandListInfo;
import com.trecyclerview.holder.AbsHolder;
import com.trecyclerview.holder.AbsItemHolder;

public class ClassificationRightBrandItemHolder extends AbsItemHolder<BrandListInfo.DatasBean,
        ClassificationRightBrandItemHolder.ViewHolder> {

    public ClassificationRightBrandItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.classify_item_of_brand_righit_list;
    }


    ClassifyItemOfBrandRighitListBinding binding;

    @Override
    public ViewHolder createViewHolder(View view) {
        binding = DataBindingUtil.bind(view);
        return new ViewHolder(binding.getRoot());
    }

//    GridLayoutManager layout;
//    @Override
//    protected void onBindViewHolder(@NonNull ClassificationRightItemHolder.ViewHolder holder, @NonNull final BrandListInfo.DatasBean dataBean) {
//        binding = DataBindingUtil.getBinding(holder.itemView);
//
//        binding.setData(dataBean);
//
//        binding.executePendingBindings();
//    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull BrandListInfo.DatasBean dataBean) {
        binding = DataBindingUtil.getBinding(holder.itemView);
        binding.setData(dataBean);
        binding.executePendingBindings();
    }

    static class ViewHolder extends AbsHolder {
        private ViewHolder(View itemView) {
            super(itemView);
        }
    }
}

