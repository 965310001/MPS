package com.mingpinmall.classz.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.ItemOfGoodsCommentListBinding;
import com.mingpinmall.classz.ui.vm.bean.GoodsComment;
import com.trecyclerview.holder.AbsHolder;
import com.trecyclerview.holder.AbsItemHolder;

public class ItemOfGoodsCommentList extends AbsItemHolder<GoodsComment, ItemOfGoodsCommentList.ViewHolder> {

    private ItemOfGoodsCommentListBinding binding;

    public ItemOfGoodsCommentList(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_of_goods_comment_list;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GoodsComment goodsComment) {
        binding = holder.getBinding();
        binding.setData(goodsComment);

        // TODO: 2019/6/14 这里需要添加 trecyclerview 的分割线
        binding.setAdapter(AdapterPool.newInstance().getItemImage(mContext).build());
        binding.setLayout(new GridLayoutManager(mContext, 6));

        if (null != goodsComment.getGeval_image_240() && goodsComment.getGeval_image_240().size() > 0) {
            binding.setList(goodsComment.getGeval_image_240());
            binding.setIsGone(true);
        } else {
            binding.setIsGone(false);
        }
        binding.executePendingBindings();
    }

    class ViewHolder extends AbsHolder {
        ItemOfGoodsCommentListBinding binding;

        private ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public ItemOfGoodsCommentListBinding getBinding() {
            return binding;
        }
    }
}

