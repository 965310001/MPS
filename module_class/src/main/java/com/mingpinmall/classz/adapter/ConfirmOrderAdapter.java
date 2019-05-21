package com.mingpinmall.classz.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ui.vm.bean.ConfirmOrderBean;

import java.util.ArrayList;

/**
 * 提交订单
 * @author 小斌
 * @data 2019/5/20
 **/
public class ConfirmOrderAdapter extends BaseQuickAdapter<ConfirmOrderBean.StoreCartListNewsBean, BaseViewHolder> {

    public ConfirmOrderAdapter() {
        super(R.layout.item_confirm_order_group, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, ConfirmOrderBean.StoreCartListNewsBean item) {
        Context context = helper.itemView.getContext();
        helper.setText(R.id.tv_storeName, item.getStore_name())
                //运费
                .setText(R.id.tv_tips0, "运费" + item.getYf_price() + "元")
                //总价
                .setText(R.id.tv_money, item.getStore_goods_total())
                .addOnClickListener(R.id.ll_shopContent);

        LinearLayout shopsList = helper.getView(R.id.ll_shopList);
        shopsList.removeAllViews();
        for (int i = 0; i < item.getGoods_list().size(); i++) {
            //add 商品
            Log.d("提交订单", "convert: 商品" + i);
            ConfirmOrderBean.StoreCartListNewsBean.GoodsListBean shops = item.getGoods_list().get(i);
            View childView = View.inflate(context, R.layout.item_confirm_order_child, null);
            childView.setTag(i);
            childView.setOnClickListener(v -> {
                //TODO 商品点击事件
            });
            ((AppCompatTextView) childView.findViewById(R.id.tv_label)).setText(shops.getGoods_name());
            ((AppCompatTextView) childView.findViewById(R.id.tv_money)).setText(shops.getGoods_price());
            ((AppCompatTextView) childView.findViewById(R.id.tv_count)).setText(String.format("%s 件", shops.getGoods_num()));
            ((AppCompatTextView) childView.findViewById(R.id.tv_spec)).setText(shops.getGoods_spec());

            String state = "";
            if (shops.isIfgroupbuy()) {
                //团购
                state = "团购";
            } else if (shops.isIfxianshi()) {
                //限时折扣
                state = "限时折扣";
            } else if (shops.isIfsole()) {
                //手机专享
                state = "手机专享";
                childView.findViewById(R.id.iv_tips).setVisibility(View.VISIBLE);
                ((AppCompatImageView)childView.findViewById(R.id.iv_tips)).setImageResource(R.drawable.ic_mobile_white);
            } else {
                childView.findViewById(R.id.ll_tips).setVisibility(View.GONE);
            }
            ((AppCompatTextView)childView.findViewById(R.id.tv_state)).setText(state);

            ImageUtils.loadImageCorners(childView.findViewById(R.id.iv_image),
                    ScreenUtil.dip2px(context, 4), shops.getGoods_image_url());

            shopsList.addView(childView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            View line = new View(context);
            line.setBackgroundResource(R.color.line_color);
            shopsList.addView(line, LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtil.px2dip(context, 1));

            LinearLayout llList = childView.findViewById(R.id.ll_giftList);
            llList.removeAllViews();
            if (shops.isHaveGifts()) {
                Log.d("提交订单", "convert: 商品 " + i +" 有赠品");
                //add 商品 附带的 赠品
                llList.setVisibility(View.VISIBLE);
                if (shops.getGift_list() != null) {
                    for (int j = 0; j < shops.getGift_list().size(); j++) {
                        Log.d("提交订单", "convert: 商品 " + i +", 赠品 " + j);
                        ConfirmOrderBean.StoreCartListNewsBean.GoodsListBean.GiftBean giftListBean = shops.getGift_list().get(j);
                        View view = View.inflate(context, R.layout.item_tips_textview_14sp, null);
                        TextView textView = view.findViewById(R.id.tv_label);
                        textView.setText(giftListBean.getGift_goodsname());
                        llList.addView(view);
                        if (i < shops.getGift_list().size() - 1) {
                            Log.d("提交订单", "convert: 商品 " + i +", 分割线 " + j);
                            View line2 = new View(context);
                            line2.setBackgroundResource(R.color.line_color);
                            llList.addView(line2, LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtil.px2dip(context, 1));
                        }
                    }
                }

            } else {
                llList.setVisibility(View.GONE);
            }

        }
    }
}
