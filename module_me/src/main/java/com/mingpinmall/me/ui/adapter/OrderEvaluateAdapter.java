package com.mingpinmall.me.ui.adapter;

import android.app.Activity;

import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.me.R;
import com.goldze.common.dmvvm.base.bean.BaseSelectPhotos;
import com.mingpinmall.me.ui.bean.OrderEvaluateBean;
import com.goldze.common.dmvvm.utils.SelectPhotosTools;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 功能描述：评价页面，商品列表
 * *@author 小斌
 * @date 2019/4/30
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
    protected void convert(BaseViewHolder helper, OrderEvaluateBean.OrderGoodsBean item) {
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
        photosTools.setOnCallListener(photosTools1 ->
                this.callTool = photosTools1
        );
        ImageUtils.loadImageCorners(helper.getView(R.id.iv_image),
                ScreenUtil.dip2px(helper.itemView.getContext(), 4), item.getGoods_image_url());
        MaterialRatingBar ratingBar = helper.getView(R.id.rb_evaluate);
        ratingBar.setNumStars(5);
        ratingBar.setOnRatingChangeListener((ratingBar1, rating) -> item.setScore(String.valueOf((int) rating)));
        helper.setText(R.id.tv_goodsName, item.getGoods_name());
    }

}
