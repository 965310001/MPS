package com.mingpinmall.me.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.VirtualOrderBean;

import java.util.ArrayList;

/**
 * 功能描述：虚拟订单适配器
 *
 * @author 小斌
 * @date 2019/4/13
 **/
public class VirtualOrderListAdapter extends BaseQuickAdapter<VirtualOrderBean.OrderListBean, BaseViewHolder> {

    public VirtualOrderListAdapter() {
        super(R.layout.item_virtual_order_group, new ArrayList<>());
    }

    @Override
    protected void convert(final BaseViewHolder helper, VirtualOrderBean.OrderListBean item) {
        String state = item.getOrder_state();

        helper.setText(R.id.tv_storeName, item.getStore_name())
                .setText(R.id.tv_orderState, item.getState_desc())
                .setText(R.id.tv_label, item.getGoods_name())
                .setText(R.id.tv_payMoney, String.format("¥%s", item.getGoods_price()))
                .setText(R.id.tv_count, String.format("x%s", item.getGoods_num()))
                //评价
                .setGone(R.id.bt_appraiseOrder, TextUtils.equals("40", state))
                //收货
                .setGone(R.id.bt_harvestOrder, TextUtils.equals("30", state))
                //支付
                .setGone(R.id.bt_payOrder, TextUtils.equals("10", state))
                //取消
                .setGone(R.id.bt_cancelOrder, TextUtils.equals("10", state))
                //移除
                .setGone(R.id.tv_removeOrder, TextUtils.equals("0", state) || TextUtils.equals("40", state))
                .addOnClickListener(R.id.ll_shopContent)
                .addOnClickListener(R.id.bt_appraiseOrder)
                .addOnClickListener(R.id.bt_harvestOrder)
                .addOnClickListener(R.id.bt_payOrder)
                .addOnClickListener(R.id.bt_cancelOrder)
                .addOnClickListener(R.id.tv_removeOrder);

        ImageUtils.loadImage(helper.getView(R.id.iv_image), item.getGoods_image_url());
    }
}
