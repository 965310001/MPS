package com.mingpinmall.cart.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.cart.R;
import com.mingpinmall.cart.ui.bean.AvailableCartBean;
import com.mingpinmall.cart.ui.bean.ShopCartBean;
import com.mingpinmall.cart.ui.utils.SmoothCheckBox;

import java.util.ArrayList;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/24
 **/
public class ShopCartAdapter extends BaseMultiItemQuickAdapter<AvailableCartBean, BaseViewHolder> {

    public ShopCartAdapter() {
        super(new ArrayList<AvailableCartBean>());
        addItemType(0, R.layout.item_cart_group);
        addItemType(1, R.layout.item_cart_child);
        addItemType(11, R.layout.item_default_space);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final AvailableCartBean item) {
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
                helper.setText(R.id.tv_storeName, item.getStore_name())
                        .setGone(R.id.tv_coupon, item.getVoucher() != null)
                        .setText(R.id.tv_free_freight, item.getFree_freight())
                        .setGone(R.id.cl_free_freight, !item.getFree_freight().isEmpty())
                        .setGone(R.id.cl_gift_list, item.getMansong() != null)
                        .setImageDrawable(R.id.iv_arrow, item.isExpanded()
                                ? ContextCompat.getDrawable(context, R.drawable.ic_collapse)
                                : ContextCompat.getDrawable(context, R.drawable.ic_expanded)
                        )
                        .addOnClickListener(R.id.tv_coupon)
                        .addOnClickListener(R.id.ll_listContent)
                        .addOnClickListener(R.id.iv_arrow);
                if (item.getMansong() != null) {
                    LinearLayout ll_List = helper.getView(R.id.ll_listContent);
                    ll_List.removeAllViews();
                    for (int i = 0; i < item.getMansong().size(); i++) {
                        View view = View.inflate(context, R.layout.item_img_textview_14sp, null);
                        TextView textView = view.findViewById(R.id.tv_label);
                        textView.setText(item.getMansong().get(0).getDesc());
                        if (!item.getMansong().get(i).getUrl().isEmpty()) {
                            AppCompatImageView imageView = view.findViewById(R.id.iv_image);
                            ImageUtils.loadImage(imageView, item.getMansong().get(i).getUrl());
                        }
                        ll_List.addView(view);
                        if (item.isExpanded()) {
                            continue;
                        }
                        break;
                    }
                }
                break;
            case 1:
                //商品
                helper.setText(R.id.tv_label, item.getGoods().getGoods_name())
                        .setText(R.id.tv_spec, item.getGoods().getGoods_spec())
                        .setText(R.id.tv_money, "¥" + item.getGoods().getGoods_price())
                        .setText(R.id.tv_count, item.getGoods().getGoods_num())
                        .setEnabled(R.id.iv_jian, !item.getGoods().getGoods_num().equals("1"))
                        .setEnabled(R.id.iv_jia, true)
                        .setVisible(R.id.tv_state, true)
                        .setGone(R.id.ll_giftList, item.getGoods().getGift_list() != null)
                        .addOnClickListener(R.id.iv_delete)
                        .addOnClickListener(R.id.iv_jia)
                        .addOnClickListener(R.id.iv_jian);
                ((AppCompatTextView) helper.getView(R.id.tv_state)).setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

                if (item.getGoods().getGift_list() != null) {
                    LinearLayout ll_List = helper.getView(R.id.ll_giftList);
                    ll_List.removeAllViews();
                    for (int i = 0; i < item.getGoods().getGift_list().size(); i++) {
                        ShopCartBean.CartListBean.GoodsBean.GiftListBean giftListBean = item.getGoods().getGift_list().get(i);
                        View view = View.inflate(context, R.layout.item_tips_textview_14sp, null);
                        TextView textView = view.findViewById(R.id.tv_label);
                        textView.setText(giftListBean.getGift_goodsname() + "    x" + giftListBean.getGift_amount());
                        ll_List.addView(view);
                        if (i < item.getGoods().getGift_list().size() - 1) {
                            View line = new View(context);
                            line.setBackgroundResource(R.color.line_color);
                            ll_List.addView(line, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                        }
                    }
                }

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
                    Drawable phone = ContextCompat.getDrawable(context, R.drawable.ic_mobile_white);
                    ((AppCompatTextView) helper.getView(R.id.tv_state)).setCompoundDrawablesWithIntrinsicBounds(phone, null, null, null);
                } else {
                    helper.setVisible(R.id.tv_state, false);
                }
                helper.setText(R.id.tv_state, state);
                ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_image), item.getGoods().getGoods_image_url());
                break;
        }
    }

}