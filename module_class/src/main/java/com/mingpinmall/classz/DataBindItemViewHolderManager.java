package com.mingpinmall.classz;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;

import com.freelib.multiitem.adapter.holder.DataBindViewHolderManager;
import com.goldze.common.dmvvm.base.bean.BaseBean;
import com.mingpinmall.classz.adapter.ProductsItemHolder;
import com.trecyclerview.holder.AbsHolder;
import com.trecyclerview.holder.AbsItemHolder;

public class DataBindItemViewHolderManager extends AbsItemHolder<Object, DataBindItemViewHolderManager.ViewHolder> {

    @LayoutRes
    private int itemLayoutId;

    int bindVariableId;

    public DataBindItemViewHolderManager(Context context, int itemLayoutId, int bindVariableId) {
        super(context);
        this.itemLayoutId = itemLayoutId;
        this.bindVariableId = bindVariableId;
    }

    @Override
    public int getLayoutResId() {
        return itemLayoutId;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(DataBindingUtil.bind(view).getRoot());
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull Object t) {
        DataBindingUtil.getBinding(viewHolder.itemView).setVariable(bindVariableId, t);
        DataBindingUtil.getBinding(viewHolder.itemView).executePendingBindings();
    }

    class ViewHolder extends AbsHolder {
        private ViewHolder(View itemView) {
            super(itemView);
        }

    }
}
