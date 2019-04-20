package com.mingpinmall.me.ui.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.PredepoitLogBean;
import com.mingpinmall.me.ui.bean.RCardLogBean;

import java.util.ArrayList;

/**
 * 功能描述：充值卡充值记录
 * 创建人：小斌
 * 创建时间: 2019/4/19
 **/
public class RCardLogAdapter extends BaseQuickAdapter<RCardLogBean.LogListBean, BaseViewHolder> {
    public RCardLogAdapter() {
        super(R.layout.item_predepositlog, new ArrayList<RCardLogBean.LogListBean>());
    }

    @Override
    protected void convert(BaseViewHolder helper, RCardLogBean.LogListBean item) {
        String[] strings = item.getDescription().split(": ");
        String label = strings[0] + ": ";
        String code = strings[1];
        helper.setText(R.id.tv_label, label)
                .setText(R.id.tv_code, code)
                .setText(R.id.tv_money,
                        Double.parseDouble(item.getAvailable_amount()) > 0 ?
                                "+" + item.getAvailable_amount() :
                                item.getAvailable_amount()
                )
                .setText(R.id.tv_time, item.getAdd_time_text())
                .setTextColor(R.id.tv_money,
                        Double.parseDouble(item.getAvailable_amount()) > 0 ?
                                Color.parseColor("#ed5564") :
                                Color.parseColor("#36bc9b"));
    }
}
