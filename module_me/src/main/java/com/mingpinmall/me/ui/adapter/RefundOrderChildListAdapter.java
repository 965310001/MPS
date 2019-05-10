package com.mingpinmall.me.ui.adapter;

import android.support.v7.widget.AppCompatImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.RefundBean;

import java.util.List;

/**
 * 功能描述：退款列表 中 店铺商品 适配器
 * *@author 小斌
 * @date 2019/4/13
 **/
public class RefundOrderChildListAdapter extends BaseQuickAdapter<RefundBean.RefundListBean.GoodsListBean, BaseViewHolder> {

    public RefundOrderChildListAdapter(List<RefundBean.RefundListBean.GoodsListBean> dataList) {
        super(R.layout.item_refund_order_child, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, RefundBean.RefundListBean.GoodsListBean item) {
        helper.setText(R.id.tv_label, item.getGoods_name());
        ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_image), item.getGoods_img_360());
    }
}
