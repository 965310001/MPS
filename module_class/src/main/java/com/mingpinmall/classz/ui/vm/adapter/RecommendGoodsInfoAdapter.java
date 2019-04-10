package com.mingpinmall.classz.ui.vm.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.goldze.common.dmvvm.adapter.BaseRecyclerAdapter;
import com.goldze.common.dmvvm.base.mvvm.base.BaseViewHolder;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.socks.library.KLog;

import java.util.List;

public class RecommendGoodsInfoAdapter extends BaseRecyclerAdapter<GoodsInfo> {

    @Override
    protected void convert(BaseViewHolder holder, final GoodsInfo data, int position, List<Object> payloads) {
        holder.setText(R.id.tv_goods_name, data.getGoods_name());
        holder.setText(R.id.tv_goods_price, String.format("¥%s", data.getGoods_promotion_price()));
        ImageUtils.loadImage(((ImageView) holder.getView(R.id.iv_goods)), data.getGoods_image_url());
    }

    public RecommendGoodsInfoAdapter(Context context, @Nullable List<GoodsInfo> list) {
        super(context, list, R.layout.market_item_of_goods_recommend_list);
    }
}