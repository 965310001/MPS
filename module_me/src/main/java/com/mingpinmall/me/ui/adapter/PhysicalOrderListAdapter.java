package com.mingpinmall.me.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.PhysicalOrderBean;

import java.util.ArrayList;

/**
 * 功能描述：实物订单适配器
 * 创建人：小斌
 * 创建时间: 2019/4/13
 **/
public class PhysicalOrderListAdapter extends BaseQuickAdapter<PhysicalOrderBean.NewdataBean, BaseViewHolder> {

    public PhysicalOrderListAdapter() {
        super(R.layout.item_physical_order_group, new ArrayList<PhysicalOrderBean.NewdataBean>());
    }

    @Override
    protected void convert(final BaseViewHolder helper, PhysicalOrderBean.NewdataBean item) {
        String state = item.getOrder_state();

        helper.setText(R.id.tv_storeName, item.getStore_name())
                .setText(R.id.tv_orderState, item.getState_desc())
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

        PhysicalOrderChildListAdapter physicalOrderChildListAdapter = new PhysicalOrderChildListAdapter(item.getExtend_order_goods());
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(helper.itemView.getContext()));
        recyclerView.setAdapter(physicalOrderChildListAdapter);
    }
}
