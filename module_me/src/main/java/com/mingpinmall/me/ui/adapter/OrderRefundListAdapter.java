package com.mingpinmall.me.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.OrderApplyRefundBean;
import com.mingpinmall.me.ui.bean.OrderInformationBean;

import java.util.ArrayList;

/**
 * 功能描述：订单退款 店铺商品 适配器
 * 创建人：小斌
 * 创建时间: 2019/5/9
 **/
public class OrderRefundListAdapter extends BaseQuickAdapter<OrderApplyRefundBean.GoodsListBean, BaseViewHolder> {

    public OrderRefundListAdapter() {
        super(R.layout.item_physical_order_child, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderApplyRefundBean.GoodsListBean item) {
        helper.setText(R.id.tv_label, item.getGoods_name())
                .setText(R.id.tv_payMoney, "¥" + item.getGoods_price())
                .setText(R.id.tv_count, "x" + item.getGoods_num())
                .addOnClickListener(R.id.tv_refund, R.id.tv_return);
        ImageUtils.loadImage(helper.getView(R.id.iv_image), item.getGoods_img_360());
    }
}
