package com.mingpinmall.me.ui.adapter;

import android.util.Log;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.CouponListBean;
import com.goldze.common.dmvvm.widget.CouponDisplayView;

import java.util.ArrayList;

/**
 * 功能描述：
 * @author 小斌
 * @date 2019/4/20
 **/
public class CouponListAdapter extends BaseMultiItemQuickAdapter<CouponListBean.VoucherListBean, BaseViewHolder> {
    public CouponListAdapter() {
        super(new ArrayList<>());
        //分隔item
        addItemType(0, R.layout.item_coupon);
        //正常内容item
        addItemType(1, R.layout.item_coupon_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponListBean.VoucherListBean item) {
        switch (item.getItemType()) {
            case 0:
                helper.setText(R.id.tv_label, item.getVoucher_state_text());
                break;
            default:
                ImageUtils.loadImage(helper.getView(R.id.iv_image), item.getVoucher_t_customimg());
                helper.setText(R.id.tv_storeName, item.getStore_name())
                        .setText(R.id.tv_time, "有效期至:\n" + item.getVoucher_end_date_text())
                        .setText(R.id.tv_money, "¥" + item.getVoucher_price())
                        .setText(R.id.tv_use, "满" + item.getVoucher_limit() + "可用")
                        .setGone(R.id.iv_stateImage, item.getType() > 1)
                        .setImageResource(R.id.iv_stateImage, item.getType() == 2 ? R.drawable.ticket_ysy : R.drawable.ticket_ysx);
                CouponDisplayView displayView = helper.getView(R.id.cdv_view);
                displayView.setState(item.getType() < 2);
                Log.d(TAG, "convert: " + item.getType() + " - " + helper.getAdapterPosition());
                break;
        }
    }
}
