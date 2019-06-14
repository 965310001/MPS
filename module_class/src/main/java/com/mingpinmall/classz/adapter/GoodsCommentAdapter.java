
package com.mingpinmall.classz.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.goldze.common.dmvvm.adapter.BaseRecyclerAdapter;
import com.goldze.common.dmvvm.base.mvvm.base.BaseViewHolder;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ui.vm.bean.GoodsComment;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class GoodsCommentAdapter extends BaseRecyclerAdapter<GoodsComment> {

    public GoodsCommentAdapter(Context context, @Nullable List<GoodsComment> list) {
        super(context, list, R.layout.item_of_goods_comment_list_detail);
    }

    @Override
    protected void convert(BaseViewHolder holder, GoodsComment data, int position, List<Object> payloads) {
        holder.setText(R.id.tv_name, data.getGeval_frommembername())
                .setText(R.id.tv_comment, data.getGeval_content())
                .setText(R.id.tv_time, data.getGeval_addtime_date());

        ((MaterialRatingBar) holder.getView(R.id.rb_evaluate)).setRating(Float.parseFloat(data.getGeval_scores()));
    }
}