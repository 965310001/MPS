package com.mingpinmall.me.ui.adapter;

import android.app.Activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.OrderEvaluateBean;
import com.mingpinmall.me.ui.utils.SelectPhotosTools;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 功能描述：评价页面，商品列表
 * 创建人：小斌
 * 创建时间: 2019/4/30
 **/
public class OrderEvaluateAdapter extends BaseQuickAdapter<OrderEvaluateBean.OrderGoodsBean, BaseViewHolder> {

    private List<SelectPhotosTools> tools;
    private SelectPhotosTools callTool;

    public SelectPhotosTools getCallTool() {
        return callTool;
    }

    public void removeCallTool() {
        this.callTool = null;
    }

    public List<SelectPhotosTools> getTools() {
        return tools;
    }

    public OrderEvaluateAdapter() {
        super(R.layout.item_order_evaluate, new ArrayList<>());
        tools = new ArrayList<>();
    }

    @Override
    protected void convert(BaseViewHolder helper, final OrderEvaluateBean.OrderGoodsBean item) {
        SelectPhotosTools photosTools;
        if (tools.size() >= helper.getAdapterPosition() + 1) {
            photosTools = tools.get(helper.getAdapterPosition());
            photosTools.reSetRecyclerView(helper.getView(R.id.rv_picList));
        } else {
            photosTools = new SelectPhotosTools((Activity) helper.itemView.getContext(), helper.getView(R.id.rv_picList));
            photosTools.setMaxSize(5);
            photosTools.setSpanCount(4);
            tools.add(photosTools);
        }
        photosTools.setOnCallListener(photosTools1 -> {
            this.callTool = photosTools1;
        });
        ImageUtils.loadImage(helper.getView(R.id.iv_image), item.getGoods_image_url());
        MaterialRatingBar ratingBar = helper.getView(R.id.rb_evaluate);
        ratingBar.setNumStars(5);
        helper.setText(R.id.tv_goodsName, item.getGoods_name());
    }

}
