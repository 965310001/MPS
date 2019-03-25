package com.mingpinmall.classz.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.ItemClassifyBinding;
import com.mingpinmall.classz.ui.vm.bean.ClassificationRighitBean;
import com.trecyclerview.holder.AbsHolder;
import com.trecyclerview.holder.AbsItemHolder;

public class ClassificationRightAdapter extends AbsItemHolder<ClassificationRighitBean.DatasBean.ClassListBean.ChildBean,
        ClassificationRightAdapter.ViewHolder> {

    ItemClassifyBinding binding;

    public ClassificationRightAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_classify;
    }


    @Override
    public ViewHolder createViewHolder(View view) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), getLayoutResId(), null, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ClassificationRighitBean.DatasBean.ClassListBean.ChildBean data) {
        binding = DataBindingUtil.getBinding(holder.itemView);
        binding.setData(data);
    }

    class ViewHolder extends AbsHolder {

        private ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
