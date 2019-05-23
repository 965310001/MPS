package com.mingpinmall.me.ui.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bigkoo.convenientbanner.utils.ScreenUtil;
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
                //移除
//                .setGone(R.id.tv_removeOrder, TextUtils.equals("0", state) || TextUtils.equals("40", state))
                .setGone(R.id.tv_removeOrder, false)
                .addOnClickListener(R.id.ll_shopContent, R.id.tv_removeOrder);
        ImageUtils.loadImage(helper.getView(R.id.iv_image), item.getGoods_image_url());

        //生成底部按钮
        initBottomBtns(helper.itemView.getContext(), helper, item);
    }

    private void initBottomBtns(Context context, BaseViewHolder helper, VirtualOrderBean.OrderListBean item) {
        LinearLayout buttonContent = helper.getView(R.id.ll_buttonContent);
        buttonContent.removeAllViews();
        if (item.isIf_evaluation()) {
            //评价订单
            AppCompatTextView evaluationBtn;
            if (helper.getView(R.id.order_evaluation) != null) {
                evaluationBtn = helper.getView(R.id.order_evaluation);
            } else {
                evaluationBtn = (AppCompatTextView) View.inflate(context, R.layout.button_layout_border_red, null);
            }
            evaluationBtn.setId(R.id.order_evaluation);
            evaluationBtn.setText("评价订单");
            addButton(context, helper, buttonContent, evaluationBtn);
        }
        if (item.isIf_cancel()) {
            //取消订单
            AppCompatTextView cancelBtn;
            if (helper.getView(R.id.order_buyer_cancel) != null) {
                cancelBtn = helper.getView(R.id.order_buyer_cancel);
            } else {
                cancelBtn = (AppCompatTextView) View.inflate(context, R.layout.button_layout_default, null);
            }
            cancelBtn.setId(R.id.order_buyer_cancel);
            cancelBtn.setText("取消订单");
            addButton(context, helper, buttonContent, cancelBtn);
        }
        if (item.isIf_pay()) {
            //订单支付
            AppCompatTextView payBtn;
            if (helper.getView(R.id.order_pay) != null) {
                payBtn = helper.getView(R.id.order_pay);
            } else {
                payBtn = (AppCompatTextView) View.inflate(context, R.layout.button_layout_solid_red, null);
            }
            payBtn.setId(R.id.order_pay);
            payBtn.setText("订单支付");
            addButton(context, helper, buttonContent, payBtn);
        }
    }

    private void addButton(Context context, BaseViewHolder helper, LinearLayout groupView, AppCompatTextView tvBtn) {
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = ScreenUtil.dip2px(context, 6);
        if (tvBtn == null) {
            return;
        }
        tvBtn.setLayoutParams(params);
        groupView.addView(tvBtn);
        helper.addOnClickListener(tvBtn.getId());
    }
}
