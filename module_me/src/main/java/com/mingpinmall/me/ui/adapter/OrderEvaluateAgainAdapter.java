package com.mingpinmall.me.ui.adapter;

import android.app.Activity;

import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.goldze.common.dmvvm.utils.SelectPhotosTools;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.EvaluateAgainListBean;
import com.mingpinmall.me.ui.bean.OrderEvaluateBean;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 功能描述：追加评价页面，商品列表
 * *@author 小斌
 * @date 2019/4/30
 **/
public class OrderEvaluateAgainAdapter extends BaseQuickAdapter<EvaluateAgainListBean.EvaluateGoodsBean, BaseViewHolder> {

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

    public OrderEvaluateAgainAdapter() {
        super(R.layout.item_order_evaluate_again, new ArrayList<>());
        tools = new ArrayList<>();
    }

    @Override
    protected void convert(BaseViewHolder helper, EvaluateAgainListBean.EvaluateGoodsBean item) {
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
                ScreenUtil.dip2px(helper.itemView.getContext(), 4), item.getGeval_goodsimage());
        helper.setText(R.id.tv_goodsName, item.getGeval_goodsname())
        .setText(R.id.tv_tips, item.getGeval_content());
    }

}
