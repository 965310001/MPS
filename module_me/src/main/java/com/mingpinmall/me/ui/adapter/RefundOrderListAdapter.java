package com.mingpinmall.me.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.RefundBean;

import java.util.ArrayList;

/**
 * 功能描述：实物订单适配器
 * *@author 小斌
 * @date 2019/4/13
 **/
public class RefundOrderListAdapter extends BaseQuickAdapter<RefundBean.RefundListBean, BaseViewHolder> {

    public RefundOrderListAdapter() {
        super(R.layout.item_refund_order_group, new ArrayList<RefundBean.RefundListBean>());
    }

    @Override
    protected void convert(final BaseViewHolder helper, RefundBean.RefundListBean item) {
        helper.setText(R.id.tv_storeName, item.getStore_name())
                .setText(R.id.tv_time, item.getAdd_time())
                .setText(R.id.tv_money, item.getRefund_amount())
                .addOnClickListener(R.id.ll_shopContent)
                .addOnClickListener(R.id.bt_refundInformation)
                .addOnClickListener(R.id.tv_removeOrder);

        RefundOrderChildListAdapter physicalOrderChildListAdapter = new RefundOrderChildListAdapter(item.getGoods_list());
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(helper.itemView.getContext()));
        recyclerView.setAdapter(physicalOrderChildListAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        physicalOrderChildListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                helper.itemView.callOnClick();
            }
        });
    }
}
