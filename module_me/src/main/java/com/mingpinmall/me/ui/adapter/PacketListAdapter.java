package com.mingpinmall.me.ui.adapter;

import android.graphics.Color;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.PacketListBean;
import com.goldze.common.dmvvm.widget.CouponDisplayView;

import java.util.ArrayList;

/**
 * 功能描述：我的红包
 *
 * @author 小斌
 * @date 2019/4/20
 **/
public class PacketListAdapter extends BaseMultiItemQuickAdapter<PacketListBean.RedpacketListBean, BaseViewHolder> {
    public PacketListAdapter() {
        super(new ArrayList<>());
        //分隔item
        addItemType(0, R.layout.item_coupon);
        //未使用
        addItemType(1, R.layout.item_coupon_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, PacketListBean.RedpacketListBean item) {
        switch (item.getItemType()) {
            case 0:
                helper.setText(R.id.tv_label, item.getRpacket_state_text());
                break;
            case 1:
                helper.setText(R.id.tv_storeName, item.getRpacket_code())
                        .setText(R.id.tv_time, String.format("有效期至:\n%s", item.getDates()))
                        .setText(R.id.tv_money, String.format("¥%s", item.getRpacket_price()))
                        .setText(R.id.tv_use, String.format("满%s可用", item.getRpacket_limit()))
                        .setImageResource(R.id.iv_subImage, R.drawable.ic_mcc_09_w)
                        .setText(R.id.tv_desc, item.getRpacket_desc())
                        .setGone(R.id.tv_type, false)
                        .setGone(R.id.tv_desc, item.isDisplay())
                        .setGone(R.id.iv_stateImage, !TextUtils.equals("1", item.getRpacket_state()))
                        .setBackgroundColor(R.id.cl_right, TextUtils.equals("1", item.getRpacket_state()) ?
                                Color.parseColor("#ed5564") : Color.parseColor("#aab2bd"))
                        .setImageResource(R.id.iv_stateImage, TextUtils.equals("2", item.getRpacket_state()) ? R.drawable.ticket_ysy : R.drawable.ticket_ysx);
                CouponDisplayView displayView = helper.getView(R.id.cdv_view);
                displayView.setState(TextUtils.equals("1", item.getRpacket_state()));
                break;
            default:
                break;
        }
    }
}
