package com.mingpinmall.me.ui.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.PhysicalOrderBean;

import java.util.ArrayList;

/**
 * 功能描述：实物订单适配器
 *
 * @author 小斌
 * @date 2019/4/13
 **/
public class PhysicalOrderListAdapter extends BaseQuickAdapter<PhysicalOrderBean, BaseViewHolder> {

    public PhysicalOrderListAdapter() {
        super(R.layout.item_physical_order_group, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, PhysicalOrderBean item) {
        Context context = helper.itemView.getContext();
        helper.setText(R.id.tv_storeName, item.getStore_name())
                .setText(R.id.tv_tips0, "共" + item.getExtend_order_goods().size() + "件商品，合计")
                .setText(R.id.tv_tips1, "¥" + item.getOrder_amount())
                .setText(R.id.tv_tips2, "(含运费¥" + item.getShipping_fee() + ")")
                .setText(R.id.tv_orderState, item.getState_desc())
                // 移除订单 按钮
                .setGone(R.id.tv_removeOrder, item.isIf_delete())
                //退货退款状态
                .setGone(R.id.tv_tips, item.isIf_lock())
                //赠品列表
                .setGone(R.id.ll_gifts, item.getZengpin_list() != null && item.getZengpin_list().size() > 0)
                .addOnClickListener(R.id.ll_shopContent, R.id.tv_removeOrder, R.id.ll_gifts);
        //生成底部按钮
        initBottomBtns(context, helper, item);
        //生成礼物列表
        initGiftsList(context, helper, item);
        //生成商品列表
        initGoodsList(context, helper, item);
    }

    private void initGoodsList(Context context, BaseViewHolder helper, PhysicalOrderBean item) {
        LinearLayout shopsList = helper.getView(R.id.ll_shopList);
        shopsList.removeAllViews();
        for (int i = 0; i < item.getExtend_order_goods().size(); i++) {
            PhysicalOrderBean.ExtendOrderGoodsBean shops = item.getExtend_order_goods().get(i);
            View childView = View.inflate(context, R.layout.item_physical_order_child, null);
            ((AppCompatTextView) childView.findViewById(R.id.tv_label)).setText(shops.getGoods_name());
            ((AppCompatTextView) childView.findViewById(R.id.tv_payMoney)).setText(shops.getGoods_price());
            ((AppCompatTextView) childView.findViewById(R.id.tv_count)).setText(shops.getGoods_num());
            ((AppCompatTextView) childView.findViewById(R.id.tv_spec)).setText(shops.getGoods_spec());
            ImageUtils.loadImageCorners(childView.findViewById(R.id.iv_image),
                    ScreenUtil.dip2px(context, 4), shops.getGoods_image_url());
            shopsList.addView(childView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            View line = new View(context);
            line.setBackgroundResource(R.color.line_color);
            shopsList.addView(line, LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtil.px2dip(context, 1));
        }
    }

    private void initGiftsList(Context context, BaseViewHolder helper, PhysicalOrderBean item) {
        LinearLayout llList = helper.getView(R.id.ll_gifts);
        llList.removeAllViews();
        if (item.getZengpin_list() != null) {
            helper.setVisible(R.id.line2, item.getZengpin_list().size() > 0);
            PhysicalOrderBean.ZengpinListBean giftListBean;
            for (int i = 0; i < item.getZengpin_list().size(); i++) {
                giftListBean = item.getZengpin_list().get(i);
                View view = View.inflate(context, R.layout.item_tips_textview_14sp, null);
                PhysicalOrderBean.ZengpinListBean finalGiftListBean = giftListBean;
                view.setOnClickListener(v -> ActivityToActivity.toActivity(ARouterConfig.home.SHOPPINGDETAILSACTIVITY, "id", finalGiftListBean.getGoods_id()));
                TextView textView = view.findViewById(R.id.tv_label);
                textView.setText(String.format("%s    x%s", giftListBean.getGoods_name(), giftListBean.getGoods_num()));
                llList.addView(view);
                if (i < item.getZengpin_list().size() - 1) {
                    View line = new View(context);
                    line.setBackgroundResource(R.color.line_color);
                    llList.addView(line, LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtil.px2dip(context, 1));
                }
            }
        }
    }

    private void initBottomBtns(Context context, BaseViewHolder helper, PhysicalOrderBean item) {
        LinearLayout buttonContent = helper.getView(R.id.ll_buttonContent);
        buttonContent.removeAllViews();
        if (item.isIf_deliver()) {
            //查看物流
            AppCompatTextView deliverBtn;
            if (helper.getView(R.id.order_deliver) != null) {
                deliverBtn = helper.getView(R.id.order_deliver);
            } else {
                deliverBtn = (AppCompatTextView) View.inflate(context, R.layout.button_layout_default, null);
            }
            deliverBtn.setId(R.id.order_deliver);
            deliverBtn.setText("查看物流");
            addButton(context, helper, buttonContent, deliverBtn);
        }
        if (item.isIf_evaluation_again()) {
            //追加评价
            AppCompatTextView againBtn;
            if (helper.getView(R.id.order_evaluation_again) != null) {
                againBtn = helper.getView(R.id.order_evaluation_again);
            } else {
                againBtn = (AppCompatTextView) View.inflate(context, R.layout.button_layout_border_red, null);
            }
            againBtn.setId(R.id.order_evaluation_again);
            againBtn.setText("追加评价");
            addButton(context, helper, buttonContent, againBtn);
        }
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
        if (item.isIf_receive()) {
            //确认收货
            AppCompatTextView receiveBtn;
            if (helper.getView(R.id.order_sure) != null) {
                receiveBtn = helper.getView(R.id.order_sure);
            } else {
                receiveBtn = (AppCompatTextView) View.inflate(context, R.layout.button_layout_border_red, null);
            }
            receiveBtn.setId(R.id.order_sure);
            receiveBtn.setText("确认收货");
            addButton(context, helper, buttonContent, receiveBtn);
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
        if (item.isIf_show_pay()) {
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
