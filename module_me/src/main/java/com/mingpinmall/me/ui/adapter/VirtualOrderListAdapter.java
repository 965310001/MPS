package com.mingpinmall.me.ui.adapter;

import android.support.v7.widget.AppCompatImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.VirtualOrderBean;

import java.util.ArrayList;

/**
 * 功能描述：虚拟订单适配器
 * 创建人：小斌
 * 创建时间: 2019/4/13
 **/
public class VirtualOrderListAdapter extends BaseQuickAdapter<VirtualOrderBean.OrderListBean, BaseViewHolder> {

    public VirtualOrderListAdapter() {
        super(R.layout.item_virtual_order_group, new ArrayList<VirtualOrderBean.OrderListBean>());
    }

    @Override
    protected void convert(final BaseViewHolder helper, VirtualOrderBean.OrderListBean item) {
        String state = item.getOrder_state();

        helper.setText(R.id.tv_storeName, item.getStore_name())
                .setText(R.id.tv_orderState, item.getState_desc())
                .setText(R.id.tv_label, item.getGoods_name())
                .setText(R.id.tv_payMoney, "¥" + item.getGoods_price())
                .setText(R.id.tv_count, "x" + item.getGoods_num())
                .setGone(R.id.bt_appraiseOrder, state.equals("40"))//评价
                .setGone(R.id.bt_harvestOrder, state.equals("30"))//收货
                .setGone(R.id.bt_payOrder, state.equals("10"))//支付
                .setGone(R.id.bt_cancelOrder, state.equals("10"))//取消
                .setGone(R.id.tv_removeOrder, state.equals("0") || state.equals("40"))//移除
                .addOnClickListener(R.id.ll_shopContent)
                .addOnClickListener(R.id.bt_appraiseOrder)
                .addOnClickListener(R.id.bt_harvestOrder)
                .addOnClickListener(R.id.bt_payOrder)
                .addOnClickListener(R.id.bt_cancelOrder)
                .addOnClickListener(R.id.tv_removeOrder);

        ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_image), item.getGoods_image_url());
    }
}
