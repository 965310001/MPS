package com.mingpinmall.classz.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.HtmlFromUtils;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.goldze.common.dmvvm.utils.log.QLog;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ui.vm.bean.ConfirmOrderBean;
import com.xuexiang.xui.widget.button.switchbutton.SwitchButton;

import java.util.ArrayList;

/**
 * 提交订单
 *
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
                //满即送
//                .setGone(R.id.cl_gift_list, item.isManSong())
                .setGone(R.id.cl_free_freight, item.isManSong())
                .addOnClickListener(R.id.ll_shopContent);

        if (item.isManSong()) {
            try {
                helper.setText(R.id.tv_tips2, "满级送")
                        .setText(R.id.tv_free_freight, item.getStore_mansong_rule_list().getDesc().getDesc() + "，送 ");
                ImageUtils.loadImage(helper.getView(R.id.iv_tiv3), item.getStore_mansong_rule_list().getDesc().getUrl());
                /*if (!TextUtils.isEmpty(item.getStore_mansong_rule_list().getDesc().getUrl())) {
                    HtmlFromUtils.setImageFromNetWork(context, helper.getView(R.id.tv_free_freight),
                            String.format(" 送[%s]", item.getStore_mansong_rule_list().getDesc().getUrl()), true);
                }*/
//                    LinearLayout llList = helper.getView(R.id.ll_listContent);
//                    llList.removeAllViews();
//                    for (int i = 0; i < item.getMansong().size(); i++) {
//                        View view = View.inflate(context, R.layout.item_img_textview_12sp, null);
//                        TextView textView = view.findViewById(R.id.tv_label);
//                        textView.setText(item.getMansong().get(i).getDesc());
//                        if (!item.getMansong().get(i).getUrl().isEmpty()) {
//                            AppCompatImageView imageView = view.findViewById(R.id.iv_image);
//                            ImageUtils.loadImage(imageView, item.getMansong().get(i).getUrl());
//                        }
//                        llList.addView(view);
//                        if (item.isExpanded()) {
//                            continue;
//                        }
//                        break;

//            }
            } catch (Exception e) {
                QLog.i(e.toString());
            }
        }
        /*店铺代金卷*/
        ConfirmOrderBean.StoreCartListNewsBean.StoreVoucherInfoBean storeVoucherInfo = item.getStore_voucher_info();
        if (null != storeVoucherInfo) {
            if (!TextUtils.isEmpty(storeVoucherInfo.getVoucher_price())) {
                helper.setText(R.id.tv_tips1, String.format("节省%s元", storeVoucherInfo.getVoucher_price()));
                helper.setGone(R.id.ll_voucherPrice, true);
            } else {
                helper.setGone(R.id.ll_voucherPrice, false);
            }
        } else {
            helper.setGone(R.id.ll_voucherPrice, false);
        }

        LinearLayout shopsList = helper.getView(R.id.ll_shopList);
        shopsList.removeAllViews();
        for (
                int i = 0; i < item.getGoods_list().size(); i++) {
            //add 商品
            Log.d("提交订单", "convert: 商品" + i);
            ConfirmOrderBean.StoreCartListNewsBean.GoodsListBean shops = item.getGoods_list().get(i);
            View childView = View.inflate(context, R.layout.item_confirm_order_child, null);
            childView.setTag(i);
            childView.setOnClickListener(v -> {
                //TODO 商品点击事件
                ActivityToActivity.toActivity(ARouterConfig.home.SHOPPINGDETAILSACTIVITY, "id", shops.getGoods_id());
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
                ((AppCompatImageView) childView.findViewById(R.id.iv_tips)).setImageResource(R.drawable.ic_mobile_white);
            } else {
                childView.findViewById(R.id.ll_tips).setVisibility(View.GONE);
            }
            ((AppCompatTextView) childView.findViewById(R.id.tv_state)).setText(state);

            ImageUtils.loadImageCorners(childView.findViewById(R.id.iv_image),
                    ScreenUtil.dip2px(context, 4), shops.getGoods_image_url());

            shopsList.addView(childView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            View line = new View(context);
            line.setBackgroundResource(R.color.line_color);
            shopsList.addView(line, LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtil.px2dip(context, 1));

            LinearLayout llList = childView.findViewById(R.id.ll_giftList);
            llList.removeAllViews();
            if (shops.isHaveGifts()) {
                Log.d("提交订单", "convert: 商品 " + i + " 有赠品");
                //add 商品 附带的 赠品
                llList.setVisibility(View.VISIBLE);
                if (shops.getGift_list() != null) {
                    for (int j = 0; j < shops.getGift_list().size(); j++) {
                        Log.d("提交订单", "convert: 商品 " + i + ", 赠品 " + j);
                        ConfirmOrderBean.StoreCartListNewsBean.GoodsListBean.GiftBean giftListBean = shops.getGift_list().get(j);
                        View view = View.inflate(context, R.layout.item_tips_textview_14sp, null);
                        TextView textView = view.findViewById(R.id.tv_label);
                        textView.setText(giftListBean.getGift_goodsname());
                        llList.addView(view);
                        if (i < shops.getGift_list().size() - 1) {
                            Log.d("提交订单", "convert: 商品 " + i + ", 分割线 " + j);
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

        if (null != item.getJoin_store_info()) {
            helper.setVisible(R.id.ll_discount, true);
            helper.setText(R.id.tv_discount, String.valueOf(item.getJoin_store_info().getZk()));
        }
    }


}
