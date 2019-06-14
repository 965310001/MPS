//package com.mingpinmall.classz.adapter;
//
//import android.content.Context;
//import android.databinding.DataBindingUtil;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.GridLayoutManager;
//import android.view.View;
//
//import com.mingpinmall.classz.R;
//import com.mingpinmall.classz.databinding.TrecyclerviewBaseBinding;
//import com.trecyclerview.holder.AbsHolder;
//import com.trecyclerview.holder.AbsItemHolder;
//
//import java.util.List;
//
//public class ImageGridAdapter extends AbsItemHolder<List, ImageGridAdapter.ViewHolder> {
//
//    public ImageGridAdapter(Context context) {
//        super(context);
//    }
//
//    @Override
//    public int getLayoutResId() {
//        return R.layout.trecyclerview_base;
//    }
//
//    @Override
//    public ViewHolder createViewHolder(View view) {
//        return new ViewHolder(view);
//    }
//
//    TrecyclerviewBaseBinding bind;
//
//    @Override
//    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull List list) {
//        bind = holder.getBind();
//        bind.setData(list);
//        bind.setTag("3");
//        bind.setLayout(new GridLayoutManager(mContext, 3));
//        bind.setAdapter(AdapterPool.newInstance().getItemImage(mContext).build());
//        bind.executePendingBindings();
//    }
//
//    class ViewHolder extends AbsHolder {
//
//        TrecyclerviewBaseBinding bind;
//
//        private ViewHolder(View itemView) {
//
//            super(itemView);
//            bind = DataBindingUtil.bind(itemView);
//        }
//
//        public TrecyclerviewBaseBinding getBind() {
//            return bind;
//        }
//    }
//}
