package com.mingpinmall.me.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.PhysicalOrderBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：实物订单适配器
 * 创建人：小斌
 * 创建时间: 2019/4/13
 **/
public class PhysicalOrderListAdapter extends BaseQuickAdapter<PhysicalOrderBean, BaseViewHolder> {

    public PhysicalOrderListAdapter() {
        super(R.layout.item_physical_order_group, new ArrayList<PhysicalOrderBean>());
    }

    @Override
    protected void convert(final BaseViewHolder helper, PhysicalOrderBean item) {
        String state = item.getOrder_state();
        Context context = helper.itemView.getContext();
        helper.setText(R.id.tv_storeName, item.getStore_name())
                .setText(R.id.tv_orderState, item.getState_desc())
                .setGone(R.id.bt_appraiseOrder, state.equals("40"))//评价
                .setGone(R.id.bt_harvestOrder, state.equals("30"))//收货
                .setGone(R.id.bt_payOrder, state.equals("10"))//支付
                .setGone(R.id.bt_cancelOrder, state.equals("10"))//取消
                .setGone(R.id.tv_removeOrder, state.equals("0") || state.equals("40"))//移除
                .setGone(R.id.ll_gifts, item.getZengpin_list() != null)//赠品列表
                .addOnClickListener(R.id.ll_shopContent)
                .addOnClickListener(R.id.bt_appraiseOrder)
                .addOnClickListener(R.id.bt_harvestOrder)
                .addOnClickListener(R.id.bt_payOrder)
                .addOnClickListener(R.id.bt_cancelOrder)
                .addOnClickListener(R.id.tv_removeOrder);

        if (item.getZengpin_list() != null) {
            LinearLayout ll_List = helper.getView(R.id.ll_gifts);
            ll_List.removeAllViews();
            for (int i = 0; i < item.getZengpin_list().size(); i++) {
                PhysicalOrderBean.ZengpinListBean giftListBean = item.getZengpin_list().get(i);
                View view = View.inflate(context, R.layout.item_tips_textview_14sp, null);
                TextView textView = view.findViewById(R.id.tv_label);
                textView.setText(giftListBean.getGoods_name() + "    x" + giftListBean.getGoods_num());
                ll_List.addView(view);
                if (i < item.getZengpin_list().size() - 1) {
                    View line = new View(context);
                    line.setBackgroundResource(R.color.line_color);
                    ll_List.addView(line, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                }
            }
        }

        PhysicalOrderChildListAdapter physicalOrderChildListAdapter = new PhysicalOrderChildListAdapter(item.getExtend_order_goods());
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
