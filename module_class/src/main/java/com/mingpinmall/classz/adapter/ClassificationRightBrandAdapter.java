package com.mingpinmall.classz.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.View;

import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.ItemClassifyBinding;
import com.mingpinmall.classz.databinding.ItemClassifyBrandBinding;
import com.mingpinmall.classz.ui.vm.bean.BrandListInfo;
import com.trecyclerview.holder.AbsHolder;
import com.trecyclerview.holder.AbsItemHolder;

public class ClassificationRightBrandAdapter extends AbsItemHolder<BrandListInfo.DatasBean.BrandListBean,
        ClassificationRightBrandAdapter.ViewHolder> {

    ItemClassifyBrandBinding binding;

    public ClassificationRightBrandAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_classify_brand;
    }


    @Override
    public ViewHolder createViewHolder(View view) {
        binding = DataBindingUtil.bind(view);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull BrandListInfo.DatasBean.BrandListBean data) {
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
