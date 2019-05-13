package com.mingpinmall.me.ui.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.PhysicalOrderBean;
import com.mingpinmall.me.ui.bean.RefundBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：实物订单适配器
 * *@author 小斌
 * @date 2019/4/13
 **/
public class RefundOrderListAdapter extends BaseQuickAdapter<RefundBean.RefundListBean, BaseViewHolder> {

    public RefundOrderListAdapter() {
        super(R.layout.item_refund_order_group, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, RefundBean.RefundListBean item) {
        helper.setText(R.id.tv_storeName, item.getStore_name())
                .setText(R.id.tv_time, item.getAdd_time())
                .setText(R.id.tv_money, item.getRefund_amount())
                .addOnClickListener(R.id.ll_shopContent)
                .addOnClickListener(R.id.bt_refundInformation)
                .addOnClickListener(R.id.tv_removeOrder);

        //生成商品列表
        initGoodsList(helper.itemView.getContext(), helper, item.getGoods_list());
    }

    private void initGoodsList(Context context, BaseViewHolder helper, List<RefundBean.RefundListBean.GoodsListBean> item) {
        LinearLayout shopsList = helper.getView(R.id.ll_shopList);
        shopsList.removeAllViews();
        for (int i = 0; i < item.size(); i++) {
            RefundBean.RefundListBean.GoodsListBean shops = item.get(i);
            View childView = View.inflate(context, R.layout.item_refund_order_child, null);
            ((AppCompatTextView) childView.findViewById(R.id.tv_label)).setText(shops.getGoods_name());
            ((AppCompatTextView) childView.findViewById(R.id.tv_spec)).setText(shops.getGoods_spec());
            ImageUtils.loadImageCorners(childView.findViewById(R.id.iv_image),
                    ScreenUtil.dip2px(context, 4), shops.getGoods_img_360());
            shopsList.addView(childView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            View line = new View(context);
            line.setBackgroundResource(R.color.line_color);
            shopsList.addView(line, LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtil.px2dip(context, 1));
        }
    }
}
