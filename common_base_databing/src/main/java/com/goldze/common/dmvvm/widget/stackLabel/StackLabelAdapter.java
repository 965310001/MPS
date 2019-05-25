package com.goldze.common.dmvvm.widget.stackLabel;

import android.view.View;

import java.util.List;

/**
 * @author 小斌
 * @data 2019/5/24
 **/
public class StackLabelAdapter<T> {

    private List<T> labels;
    private StackLabel stackLabel;
    private CovertData<T> covertData;

    public StackLabelAdapter(CovertData<T> tCovertData) {
        covertData = tCovertData;
    }

    public void setCovertData(CovertData<T> covertData) {
        this.covertData = covertData;
    }

    public void setStackLabel(StackLabel stackLabel) {
        this.stackLabel = stackLabel;
    }

    public void setLabels(List<T> labels) {
        this.labels = labels;
        notifyDataSetChanged();
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
        return covertData.covert(labels.get(position), position);
    }

    public T getItem(int position) {
        return labels.get(position);
    }

    public interface CovertData<T> {
        /**
         *
         * @param data
         * @param position
         * @return
         */
        String covert(T data, int position);
    }

    public interface OnLabelClickListener<T> {
        void onClick(int index, View v, T s);
    }

}
