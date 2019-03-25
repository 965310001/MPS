package com.mingpinmall.classz.ui.vm;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ui.vm.bean.ClassificationBean;
import com.mingpinmall.classz.databinding.ClassifyItemOfListBinding;
import com.trecyclerview.holder.AbsHolder;
import com.trecyclerview.holder.AbsItemHolder;

public class ClassificationItemHolder extends AbsItemHolder<ClassificationBean.DatasBean.ClassListBean,
        ClassificationItemHolder.ViewHolder> {

//    private int selectIndex;

    public ClassificationItemHolder(Context context) {
        super(context);

//        LiveBus.getDefault().subscribe(Constants.Listview.POSTION_LEF_EVENT_KEY[0],
//                Constants.Listview.POSTION_LEF_EVENT_KEY[1]).observeForever(new Observer<Object>() {
//            @Override
//            public void onChanged(@Nullable Object o) {
//                selectIndex = (int) o;
//            }
//        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.classify_item_of_list;
    }

    ClassifyItemOfListBinding binding;

    @Override
    public ViewHolder createViewHolder(View view) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), view.getId(), null, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ClassificationBean.DatasBean.ClassListBean dataBean) {
        binding = DataBindingUtil.getBinding(holder.itemView);
        binding.setData(dataBean);
    }

    static class ViewHolder extends AbsHolder {
        private ViewHolder(View itemView) {
            super(itemView);

//            tvText = getViewById(R.id.tv_text);
        }
    }
}
