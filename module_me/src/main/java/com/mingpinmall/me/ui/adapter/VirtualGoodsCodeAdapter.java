package com.mingpinmall.me.ui.adapter;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.VirtualInformationBean;

import java.util.ArrayList;

/**
 * 功能描述：虚拟订单 虚拟兑换码列表
 * *@author 小斌
 * @date 2019/4/18
 **/
public class VirtualGoodsCodeAdapter extends BaseQuickAdapter<VirtualInformationBean.OrderInfoBean.CodeListBean, BaseViewHolder> {

    public VirtualGoodsCodeAdapter() {
        super(R.layout.item_virtual_code, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, VirtualInformationBean.OrderInfoBean.CodeListBean item) {
        boolean isSafe = TextUtils.equals("0", item.getVr_state()) || !TextUtils.equals("2", item.getRefund_lock());
        helper.setText(R.id.tv_code, item.getVr_code())
                .setText(R.id.tv_type, isSafe ? "有效" : "失效")
                .setBackgroundRes(R.id.tv_type, isSafe ? R.drawable.bg_shape_green : R.drawable.bg_shape_b0b0b0)
                .setTextColor(R.id.tv_code, isSafe ?
                        ContextCompat.getColor(helper.itemView.getContext(), R.color.bg_color_9) :
                        ContextCompat.getColor(helper.itemView.getContext(), R.color.bg_color_10)
                );
    }
}
