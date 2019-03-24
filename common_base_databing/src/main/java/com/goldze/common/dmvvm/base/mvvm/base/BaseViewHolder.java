package com.goldze.common.dmvvm.base.mvvm.base;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * @author GuoFeng
 * @date :2019/1/17 17:06
 * @description:
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> views;
    private final LinkedHashSet<Integer> childClickViewIds;

    public View convertView;

    public BaseViewHolder(final View view) {
        super(view);
        this.views = new SparseArray<>();
        this.childClickViewIds = new LinkedHashSet<>();

        this.convertView = view;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public BaseViewHolder setText(@IdRes int viewId, CharSequence value) {
        TextView view = getView(viewId);
        view.setText(value);
        return this;
    }

    public HashSet<Integer> getChildClickViewIds() {
        return childClickViewIds;
    }

    public void addOnClickListener(@IdRes final int viewId) {
        childClickViewIds.add(viewId);
        final View view = getView(viewId);
        if (view != null) {
            if (!view.isClickable()) {
                view.setClickable(true);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
