package com.mingpinmall.me.ui.adapter;

import android.support.v7.widget.AppCompatImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.CouponListBean;
import com.mingpinmall.me.ui.widget.CouponDisplayView;

import java.util.ArrayList;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/20
 **/
public class CouponListAdapter extends BaseMultiItemQuickAdapter<CouponListBean.VoucherListBean, BaseViewHolder> {
    public CouponListAdapter() {
        super(new ArrayList<CouponListBean.VoucherListBean>());
        addItemType(0, R.layout.item_coupon);//分隔item
        addItemType(1, R.layout.item_coupon_list);//正常内容item
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponListBean.VoucherListBean item) {
        switch (item.getItemType()) {
            case 0:
                helper.setText(R.id.tv_label, item.getVoucher_state_text());
                break;
            default:
                ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_image), item.getVoucher_t_customimg());
                helper.setText(R.id.tv_storeName, item.getStore_name())
                        .setText(R.id.tv_time, "有效期至:" + item.getVoucher_end_date_text())
                        .setText(R.id.tv_money, "¥" + item.getVoucher_price())
                        .setText(R.id.tv_use, "满" + item.getVoucher_limit() + "可用")
                        .setGone(R.id.iv_stateImage, item.getType() > 1)
                        .setImageResource(R.id.iv_stateImage, item.getType() == 2 ? R.drawable.ticket_ysy : R.drawable.ticket_ysx);
                CouponDisplayView displayView = helper.getView(R.id.cdv_view);
                displayView.setState(item.getType() == 1);
                break;
        }
    }
}
