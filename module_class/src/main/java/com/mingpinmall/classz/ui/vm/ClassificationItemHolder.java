package com.mingpinmall.classz.ui.vm;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.ClassifyItemOfListBinding;
import com.mingpinmall.classz.ui.vm.bean.ClassificationBean;
import com.trecyclerview.holder.AbsHolder;
import com.trecyclerview.holder.AbsItemHolder;

public class ClassificationItemHolder extends AbsItemHolder<ClassificationBean.DatasBean.ClassListBean,
        ClassificationItemHolder.ViewHolder> {

    public ClassificationItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.classify_item_of_list;
    }

    ClassifyItemOfListBinding binding;

    @Override
    public ViewHolder createViewHolder(View view) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), getLayoutResId(), null, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ClassificationBean.DatasBean.ClassListBean dataBean) {
        binding = DataBindingUtil.getBinding(holder.itemView);
        binding.setData(dataBean);
    }

    @Override
    protected long getItemId(@NonNull ClassificationBean.DatasBean.ClassListBean data) {
        return Long.parseLong(data.getGc_id());
    }

    @BindingAdapter({"bind:image"})
    public static void imageLoader(ImageView imageView, String url) {
        ImageUtils.loadImage(imageView, url);
    }

    static class ViewHolder extends AbsHolder {
        private ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
