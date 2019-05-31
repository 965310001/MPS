package com.mingpinmall.classz.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.ItemListInfoBinding;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsListInfo;
import com.trecyclerview.holder.AbsHolder;
import com.trecyclerview.holder.AbsItemHolder;

import java.util.List;

public class GoodsListInfoAdapter extends AbsItemHolder<GoodsListInfo, GoodsListInfoAdapter.ViewHolder> {

    public GoodsListInfoAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_list_info;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GoodsListInfo goodsListInfo) {
        ItemListInfoBinding bind = holder.getBind();
        List<GoodsInfo> list = goodsListInfo.getList();

        ImageView[] imageViews = {bind.ivImage, bind.ivImage2, bind.ivImage3};
        GoodsInfo goodsInfo;
        bind.flItem.setVisibility(View.GONE);
        bind.flItem2.setVisibility(View.GONE);
        bind.flItem3.setVisibility(View.GONE);
        for (int i = 0; i < list.size(); i++) {
            goodsInfo = list.get(i);
            ImageUtils.loadImage(imageViews[i], goodsInfo.getGoods_image_url());
            if (0 == i) {
                bind.flItem.setVisibility(View.VISIBLE);
                GoodsInfo finalGoodsInfo = goodsInfo;
                bind.flItem.setOnClickListener(v -> ActivityToActivity.goShoppingDetails(finalGoodsInfo.getGoods_id()));
                bind.tvSalenum.setText(String.format("已售%s", goodsInfo.getGoods_salenum()));
                bind.tvPrice.setText(String.format(mContext.getResources().getString(R.string.price_holder), goodsInfo.getGoods_price()));
            } else if (1 == i) {
                bind.flItem2.setVisibility(View.VISIBLE);
                GoodsInfo finalGoodsInfo1 = goodsInfo;
                bind.flItem2.setOnClickListener(v -> ActivityToActivity.goShoppingDetails(finalGoodsInfo1.getGoods_id()));
                bind.tvSalenum2.setText(String.format("已售%s", goodsInfo.getGoods_salenum()));
            } else if (2 == i) {
                bind.flItem3.setVisibility(View.VISIBLE);
                GoodsInfo finalGoodsInfo2 = goodsInfo;
                bind.flItem3.setOnClickListener(v -> ActivityToActivity.goShoppingDetails(finalGoodsInfo2.getGoods_id()));
                bind.tvSalenum3.setText(String.format("已售%s", goodsInfo.getGoods_salenum()));
            }
        }
    }

    class ViewHolder extends AbsHolder {

        private ItemListInfoBinding bind;

        private ViewHolder(View itemView) {
            super(itemView);
            bind = DataBindingUtil.bind(itemView);
        }

        public ItemListInfoBinding getBind() {
            return bind;
        }
    }
}