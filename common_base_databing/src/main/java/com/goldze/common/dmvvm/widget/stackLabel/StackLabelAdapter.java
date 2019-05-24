package com.goldze.common.dmvvm.widget.stackLabel;

import android.view.View;

import java.util.List;

/**
 * @author 小斌
 * @data 2019/5/24
 **/
public abstract class StackLabelAdapter<T> {

    private List<T> labels;
    private StackLabel stackLabel;

    public StackLabelAdapter(){
    }

    public void setStackLabel(StackLabel stackLabel) {
        this.stackLabel = stackLabel;
    }

    public void setLabels(List<T> labels) {
        this.labels = labels;
        notifyDataSetChanged();
    }

    public List<T> getLabels() {
        return labels;
    }

    public int getItemCount() {
        return labels == null ? 0 : labels.size();
    }

    public void notifyDataSetChanged() {
        if (stackLabel != null) {
            stackLabel.notifyDataSetChanged();
        }
    }

    public String getText(int position) {
        return covert(labels.get(position), position);
    }

    public T getItem(int position) {
        return labels.get(position);
    }

    public abstract String covert(T data, int position);

    public interface OnLabelClickListener<T> {
        void onClick(int index, View v, T s);
    }

}
