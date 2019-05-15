package com.mingpinmall.cart;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;

import com.trecyclerview.holder.AbsHolder;
import com.trecyclerview.holder.AbsItemHolder;

public class DataBindItemViewHolderManager extends AbsItemHolder<Object, DataBindItemViewHolderManager.ViewHolder> {

    @LayoutRes
    private int itemLayoutId;

    int bindVariableId;

    private View.OnClickListener onClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

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
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull Object t) {
        viewHolder.getBinding().setVariable(bindVariableId, t);
        viewHolder.getBinding().executePendingBindings();
        viewHolder.getBinding().getRoot().findViewById(R.id.btn_receive).setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onClick(v);
            }
        });
    }

    class ViewHolder extends AbsHolder {
        ViewDataBinding binding;

        private ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }
}
