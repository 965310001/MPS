package com.mingpinmall.classz.ui.vm.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;

import java.util.List;

/**
 * 商品详情-店主推荐-商品滑动适配器 配合ConvenientBanner 使用
 */
public class RecommendGoodsAdapter implements CBViewHolderCreator {

    private Context mContext;

    public RecommendGoodsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Holder createHolder(View itemView) {
        return new Holder<List<GoodsInfo>>(itemView) {
            RecyclerView recyclerView;

            @Override
            protected void initView(View itemView) {
                recyclerView = itemView.findViewById(R.id.recycler_view);
            }

            @Override
            public void updateUI(List<GoodsInfo> data) {
                recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
                recyclerView.setAdapter(new RecommendGoodsInfoAdapter(mContext, data));
            }
        };
    }

    @Override
    public int getLayoutId() {
        return R.layout.recyclerview_base;
    }

}