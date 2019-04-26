package com.mingpinmall.me.ui.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.PredepoitLogBean;

import java.util.ArrayList;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/19
 **/
public class PredepositLogAdapter extends BaseQuickAdapter<PredepoitLogBean.ListBean, BaseViewHolder> {
    public PredepositLogAdapter() {
        super(R.layout.item_predepositlog, new ArrayList<PredepoitLogBean.ListBean>());
    }

    @Override
    protected void convert(BaseViewHolder helper, PredepoitLogBean.ListBean item) {
        String label = item.getLg_desc();
        String code = "";
        if (item.getLg_desc().contains(":")) {
            label = item.getLg_desc().split(":")[0] + ":";
            code = item.getLg_desc().split(":")[1];
        }
        helper.setText(R.id.tv_label, label)
                .setText(R.id.tv_code, code)
                .setText(R.id.tv_money, Double.parseDouble(item.getLg_av_amount()) > 0 ? "+" + item.getLg_av_amount() : item.getLg_av_amount())
                .setText(R.id.tv_time, item.getLg_add_time_text())
                .setTextColor(R.id.tv_money, Double.parseDouble(item.getLg_av_amount()) < 0 ?
                        Color.parseColor("#36bc9b") :
                        Color.parseColor("#ed5564"));
    }
}
