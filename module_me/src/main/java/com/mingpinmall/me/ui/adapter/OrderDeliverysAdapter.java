package com.mingpinmall.me.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mingpinmall.me.R;

import java.util.ArrayList;

/**
 * 功能描述：物流信息
 * *@author 小斌
 * @date 2019/4/27
 **/
public class OrderDeliverysAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public OrderDeliverysAdapter() {
        super(R.layout.item_orderdelivery, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        int position = helper.getLayoutPosition();
        int lastItemIndex = getItemCount() - 1;
        helper.setText(R.id.tv_information, item)
                .setVisible(R.id.line_bottom, position < lastItemIndex)
                .setBackgroundRes(R.id.line_bottom, position < 1 ? R.drawable.shape_green_stroke_1dp : R.drawable.shape_gray_stroke_1dp)
                .setBackgroundRes(R.id.line_top, position == 1 ? R.drawable.shape_green_stroke_1dp : R.drawable.shape_gray_stroke_1dp)
                .setVisible(R.id.iv_point, position == 0)
                .setVisible(R.id.iv_point2, position != 0)
                .setVisible(R.id.line_top, position > 0)
        ;
    }
}
