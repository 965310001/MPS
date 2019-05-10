package com.mingpinmall.me.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.PacketListBean;
import com.mingpinmall.me.ui.widget.CouponDisplayView;

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
                ImageUtils.loadImage(helper.getView(R.id.iv_image), item.getRpacket_customimg_url());
                helper.setText(R.id.tv_storeName, item.getRpacket_owner_name())
                        .setText(R.id.tv_time, "有效期至:\n" + item.getDates())
                        .setText(R.id.tv_money, item.getRpacket_price())
                        .setText(R.id.tv_use, "满" + item.getRpacket_limit() + "可用")
                        .setImageResource(R.id.iv_subImage, R.drawable.ic_mcc_09_w)
                        .setGone(R.id.iv_stateImage, !TextUtils.equals("1", item.getRpacket_state()))
                        .setImageResource(R.id.iv_stateImage, TextUtils.equals("2", item.getRpacket_state()) ? R.drawable.ticket_ysy : R.drawable.ticket_ysx);
                CouponDisplayView displayView = helper.getView(R.id.cdv_view);
                displayView.setState(TextUtils.equals("1", item.getRpacket_state()));
                break;
            default:
                break;
        }
    }
}
