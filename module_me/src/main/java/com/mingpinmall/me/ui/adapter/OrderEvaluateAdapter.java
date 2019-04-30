package com.mingpinmall.me.ui.adapter;

import android.support.v7.widget.AppCompatImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.OrderEvaluateBean;

import java.util.ArrayList;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 功能描述：评价页面，商品列表
 * 创建人：小斌
 * 创建时间: 2019/4/30
 **/
public class OrderEvaluateAdapter extends BaseQuickAdapter<OrderEvaluateBean.OrderGoodsBean, BaseViewHolder> {
    public OrderEvaluateAdapter() {
        super(R.layout.item_order_evaluate, new ArrayList<OrderEvaluateBean.OrderGoodsBean>());
    }

    @Override
    protected void convert(BaseViewHolder helper, final OrderEvaluateBean.OrderGoodsBean item) {
        ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_image), item.getGoods_image_url());
        MaterialRatingBar ratingBar = helper.getView(R.id.rb_evaluate);
        ratingBar.setNumStars(5);
        helper.setText(R.id.tv_goodsName, item.getGoods_name());

    }
}
