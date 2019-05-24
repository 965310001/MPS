package com.mingpinmall.me.ui.adapter;

import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.VirtualInformationBean;

import java.util.ArrayList;

/**
 * 功能描述：虚拟订单 虚拟兑换码列表
 * *@author 小斌
 *
 * @date 2019/4/18
 **/
public class VirtualGoodsCodeAdapter extends BaseQuickAdapter<VirtualInformationBean.OrderInfoBean.CodeListBean, BaseViewHolder> {

    public VirtualGoodsCodeAdapter() {
        super(R.layout.item_virtual_code, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, VirtualInformationBean.OrderInfoBean.CodeListBean item) {
        AppCompatTextView textView = helper.getView(R.id.tv_code);
        textView.setText(item.getVr_code());
        if (!"0".equals(item.getVr_state()) || "2".equals(item.getRefund_lock())) {
            //失效
            helper.setText(R.id.tv_type, "失效");
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            textView.setTextColor(ContextCompat.getColor(helper.itemView.getContext(), R.color.bg_color_10));
            helper.setBackgroundRes(R.id.tv_type, R.drawable.bg_shape_b0b0b0);
        } else {
            //有效
            helper.setText(R.id.tv_type, "有效");
            textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            textView.setTextColor(ContextCompat.getColor(helper.itemView.getContext(), R.color.bg_color_9));
            helper.setBackgroundRes(R.id.tv_type, R.drawable.bg_shape_green);
        }
    }
}
