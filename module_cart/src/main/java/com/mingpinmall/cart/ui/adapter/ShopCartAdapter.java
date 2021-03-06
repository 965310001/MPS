package com.mingpinmall.cart.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.goldze.common.dmvvm.widget.SmoothCheckBox;
import com.mingpinmall.cart.R;
import com.mingpinmall.cart.ui.bean.AvailableCartBean;
import com.mingpinmall.cart.ui.bean.ShopCartBean;

import java.util.ArrayList;

/**
 * 功能描述：购物车列表适配器
 * *@author 小斌
 *
 * @date 2019/4/24
 **/
public class ShopCartAdapter extends BaseMultiItemQuickAdapter<AvailableCartBean, BaseViewHolder> {

    public ShopCartAdapter() {
        super(new ArrayList<>());
        addItemType(0, R.layout.item_cart_group);
        addItemType(1, R.layout.item_cart_child);
        addItemType(11, R.layout.item_default_space);
    }

    @Override
    protected void convert(final BaseViewHolder helper, AvailableCartBean item) {
        if (item.getItemType() == 11) {
            return;
        }
        Context context = helper.itemView.getContext();
        SmoothCheckBox checkBox = helper.getView(R.id.cb_single);
        checkBox.setChecked(item.isCheck(), false);
        helper.addOnClickListener(R.id.cb_single);
        switch (item.getItemType()) {
            case 0:
                //店铺
                initItemType0(context, helper, item);
                break;
            case 1:
                //商品
                initItemType1(context, helper, item);
                break;
            default:
                break;
        }
    }

    private void initItemType1(Context context, BaseViewHolder helper, AvailableCartBean item) {
        ImageUtils.loadImageCorners(helper.getView(R.id.iv_image),
                ScreenUtil.dip2px(context, 4), item.getGoods().getGoods_image_url());
        helper.setText(R.id.tv_label, item.getGoods().getGoods_name())
                .setText(R.id.tv_spec, item.getGoods().getGoods_spec())
                .setText(R.id.tv_money, "¥" + item.getGoods().getGoods_price())
                .setText(R.id.tv_count, item.getGoods().getGoods_num())
                .setEnabled(R.id.iv_jian, !TextUtils.equals("1", item.getGoods().getGoods_num()))
                .setEnabled(R.id.iv_jia, true)
                .setImageResource(R.id.iv_jian, helper.getView(R.id.iv_jian).isEnabled() ? R.drawable.ic_jian : R.drawable.ic_jian_un)
                .setImageResource(R.id.iv_jia, helper.getView(R.id.iv_jia).isEnabled() ? R.drawable.ic_jia : R.drawable.ic_jia_un)
                .setVisible(R.id.tv_state, true)
                .setGone(R.id.ll_giftList, item.getGoods().getGift_list() != null)
                .addOnClickListener(R.id.iv_delete, R.id.iv_jia, R.id.iv_jian);
        if (item.getGoods().getGift_list() != null) {
            LinearLayout llList = helper.getView(R.id.ll_giftList);
            llList.removeAllViews();
            for (int i = 0; i < item.getGoods().getGift_list().size(); i++) {
                ShopCartBean.CartListBean.GoodsBean.GiftListBean giftListBean = item.getGoods().getGift_list().get(i);
                View view = View.inflate(context, R.layout.item_tips_textview_14sp, null);
                TextView textView = view.findViewById(R.id.tv_label);
                textView.setText(giftListBean.getGift_goodsname() + "    x" + giftListBean.getGift_amount());
                llList.addView(view);
                if (i < item.getGoods().getGift_list().size() - 1) {
                    View line = new View(context);
                    line.setBackgroundColor(ContextCompat.getColor(context, R.color.line_color));
                    llList.addView(line, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                }
            }
        }
        helper.setVisible(R.id.ll_tips, true);
        helper.setGone(R.id.iv_tips, false);
        String state = "";
        if (item.getGoods().isIfgroupbuy()) {
            //团购
            state = "团购";
        } else if (item.getGoods().isIfxianshi()) {
            //限时折扣
            state = "限时折扣";
        } else if (item.getGoods().isIfsole()) {
            //手机专享
            state = "手机专享";
            helper.setGone(R.id.iv_tips, true);
            helper.setImageResource(R.id.iv_tips, R.drawable.ic_mobile_white);
        } else {
            helper.setVisible(R.id.ll_tips, false);
        }
        helper.setText(R.id.tv_state, state);
    }

    private void initItemType0(Context context, BaseViewHolder helper, AvailableCartBean item) {
        helper.setText(R.id.tv_storeName, item.getStore_name())
                .setGone(R.id.tv_coupon, item.getVoucher() != null)
                .setText(R.id.tv_free_freight, item.getFree_freight())
                .setGone(R.id.cl_free_freight, !item.getFree_freight().isEmpty())
                .setGone(R.id.cl_gift_list, item.getMansong() != null)
                .setImageResource(R.id.iv_arrow, item.isExpanded()
                        ? R.drawable.ic_collapse
                        : R.drawable.ic_expanded
                )
                .addOnClickListener(R.id.tv_coupon, R.id.ll_listContent, R.id.iv_arrow);
        if (item.getMansong() != null) {
            LinearLayout llList = helper.getView(R.id.ll_listContent);
            llList.removeAllViews();
            for (int i = 0; i < item.getMansong().size(); i++) {
                View view = View.inflate(context, R.layout.item_img_textview_12sp, null);
                TextView textView = view.findViewById(R.id.tv_label);
                textView.setText(item.getMansong().get(i).getDesc());
                if (!item.getMansong().get(i).getUrl().isEmpty()) {
                    AppCompatImageView imageView = view.findViewById(R.id.iv_image);
                    ImageUtils.loadImage(imageView, item.getMansong().get(i).getUrl());
                }
                llList.addView(view);
                if (item.isExpanded()) {
                    continue;
                }
                break;
            }
        }
    }

}
