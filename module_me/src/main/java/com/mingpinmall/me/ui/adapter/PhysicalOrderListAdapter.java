package com.mingpinmall.me.ui.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
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
    protected void convert(BaseViewHolder helper, PhysicalOrderBean item) {
        Context context = helper.itemView.getContext();
        helper.setText(R.id.tv_storeName, item.getStore_name())
                .setText(R.id.tv_tips0, "共" + item.getExtend_order_goods().size() + "件商品，合计")
                .setText(R.id.tv_tips1, "¥" + item.getOrder_amount())
                .setText(R.id.tv_tips2, "(含运费¥" + item.getShipping_fee() + ")")
                .setText(R.id.tv_orderState, item.getState_desc())
                .setGone(R.id.tv_removeOrder, item.isIf_delete())// 移除订单 按钮
                .setGone(R.id.tv_tips, item.isIf_lock())//退货退款状态
                .setGone(R.id.ll_gifts, item.getZengpin_list() != null && item.getZengpin_list().size() > 0)//赠品列表
                .addOnClickListener(R.id.ll_shopContent, R.id.tv_removeOrder);
        LinearLayout buttonContent = helper.getView(R.id.ll_buttonContent);
        buttonContent.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = ScreenUtil.dip2px(context, 6);

        if (item.isIf_deliver()) {
            //查看物流
            AppCompatTextView tvBtn;
            if (helper.getView(R.id.order_deliver) != null)
                tvBtn = helper.getView(R.id.order_deliver);
            else
                tvBtn = (AppCompatTextView) View.inflate(context, R.layout.button_layout_default, null);
            tvBtn.setId(R.id.order_deliver);
            tvBtn.setText("查看物流");
            tvBtn.setLayoutParams(params);
            buttonContent.addView(tvBtn);
            helper.addOnClickListener(R.id.order_deliver);
        }
        if (item.isIf_evaluation_again()) {
            //追加评价
            AppCompatTextView tvBtn;
            if (helper.getView(R.id.order_evaluation_again) != null)
                tvBtn = helper.getView(R.id.order_evaluation_again);
            else
                tvBtn = (AppCompatTextView) View.inflate(context, R.layout.button_layout_border_red, null);
            tvBtn.setId(R.id.order_evaluation_again);
            tvBtn.setText("追加评价");
            tvBtn.setLayoutParams(params);
            buttonContent.addView(tvBtn);
            helper.addOnClickListener(R.id.order_evaluation_again);
        }
        if (item.isIf_evaluation()) {
            //评价订单
            AppCompatTextView tvBtn;
            if (helper.getView(R.id.order_evaluation) != null)
                tvBtn = helper.getView(R.id.order_evaluation);
            else
                tvBtn = (AppCompatTextView) View.inflate(context, R.layout.button_layout_border_red, null);
            tvBtn.setId(R.id.order_evaluation);
            tvBtn.setText("评价订单");
            tvBtn.setLayoutParams(params);
            buttonContent.addView(tvBtn);
            helper.addOnClickListener(R.id.order_evaluation);
        }
        if (item.isIf_receive()) {
            //确认收货
            AppCompatTextView tvBtn;
            if (helper.getView(R.id.order_sure) != null)
                tvBtn = helper.getView(R.id.order_sure);
            else
                tvBtn = (AppCompatTextView) View.inflate(context, R.layout.button_layout_border_red, null);
            tvBtn.setId(R.id.order_sure);
            tvBtn.setText("确认收货");
            tvBtn.setLayoutParams(params);
            buttonContent.addView(tvBtn);
            helper.addOnClickListener(R.id.order_sure);
        }
        if (item.isIf_cancel()) {
            //取消订单 & 立即支付
            AppCompatTextView orderCancel;
            AppCompatTextView orderPay;
            if (helper.getView(R.id.order_cancel) != null)
                orderCancel = helper.getView(R.id.order_cancel);
            else
                orderCancel = (AppCompatTextView) View.inflate(context, R.layout.button_layout_default, null);

            orderCancel.setId(R.id.order_cancel);
            orderCancel.setText("取消订单");
            orderCancel.setLayoutParams(params);
            buttonContent.addView(orderCancel);
            helper.addOnClickListener(R.id.order_cancel);

            if (helper.getView(R.id.order_pay) != null)
                orderPay = helper.getView(R.id.order_pay);
            else
                orderPay = (AppCompatTextView) View.inflate(context, R.layout.button_layout_solid_red, null);

            orderPay.setId(R.id.order_pay);
            orderPay.setText("立即支付");
            orderPay.setLayoutParams(params);
            buttonContent.addView(orderPay);
            helper.addOnClickListener(R.id.order_pay);
        }

        LinearLayout ll_List = helper.getView(R.id.ll_gifts);
        ll_List.removeAllViews();
        if (item.getZengpin_list() != null) {
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

        LinearLayout shopsList = helper.getView(R.id.ll_shopList);
        shopsList.removeAllViews();
        for (int i = 0; i < item.getExtend_order_goods().size(); i++) {
            PhysicalOrderBean.ExtendOrderGoodsBean shops = item.getExtend_order_goods().get(i);
            View childView = View.inflate(context, R.layout.item_physical_order_child, null);
            ((AppCompatTextView) childView.findViewById(R.id.tv_label)).setText(shops.getGoods_name());
            ((AppCompatTextView) childView.findViewById(R.id.tv_payMoney)).setText(shops.getGoods_price());
            ((AppCompatTextView) childView.findViewById(R.id.tv_count)).setText(shops.getGoods_num());
            ((AppCompatTextView) childView.findViewById(R.id.tv_spec)).setText(shops.getGoods_spec());
            ImageUtils.loadImage((AppCompatImageView) childView.findViewById(R.id.iv_image), shops.getGoods_image_url());
            shopsList.addView(childView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i < item.getExtend_order_goods().size() - 1) {
                View line = new View(context);
                line.setBackgroundResource(R.color.line_color);
                shopsList.addView(line, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            }
        }
    }
}
