package com.goldze.common.dmvvm.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：通用的UltraViewPager轮播图适配器
 * 创建人：小斌
 * 创建时间: 2019/5/5
 **/
public abstract class BaseBannerAdapter<T, D extends ViewDataBinding> extends PagerAdapter {

    private List<T> data;
    private OnPagerClickListener onPagerClickListener;

    public void setOnPagerClickListener(OnPagerClickListener onPagerClickListener) {
        this.onPagerClickListener = onPagerClickListener;
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        if (data == null) {
            data = new ArrayList<>();
        }
        return data;
    }

    abstract protected int getLayoutId();
    abstract protected void convert(D binding, T item, int position);

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        D view = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), getLayoutId(), container, false);
        convert(view, data.get(position), position);
        view.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPagerClickListener != null) {
                    onPagerClickListener.OnPagerClick(position);
                }
            }
        });
        container.addView(view.getRoot());
        return view.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    public interface OnPagerClickListener {
        void OnPagerClick(int position);
    }
}
