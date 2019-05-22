package com.goldze.common.dmvvm.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goldze.common.dmvvm.base.mvvm.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GuoFeng
 * @date : 2019/1/20 16:19
 * @description:
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private OnItemClickListener onItemClickListener;

    private OnItemLongClickListener onItemLongClickListener;

    private List<T> dataList;

    private LayoutInflater layoutInflater;

    private int mItemLayoutId;

    private Context context;

    protected Context getContext() {
        return context;
    }

    public List<T> getList() {
        return dataList;
    }

    public void setList(List<T> dataList) {
        this.dataList = dataList;
    }

    /**
     * @param context
     * @param list
     * @param itemLayoutId
     */
    public BaseRecyclerAdapter(Context context, @Nullable List<T> list, int itemLayoutId) {
        this.context = context;
        this.dataList = list;
        this.mItemLayoutId = itemLayoutId;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(mItemLayoutId, parent, false);
        return new BaseViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            convert(holder, dataList.get(position), position, payloads);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        List list = new ArrayList();
        convert(holder, dataList.get(position), position, list);
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClickListener(v, position);
            }
        });
        holder.itemView.setOnLongClickListener(v -> {
            if (onItemLongClickListener != null) {
                onItemLongClickListener.onItemLongClickListener(v, position);
            }
            return true;
        });
    }

    protected abstract void convert(BaseViewHolder holder, T t, int position, List<Object> payloads);

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(View v, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClickListener(View v, int position);
    }

    public T getData(int position) {
        return dataList.get(position);
    }
}
