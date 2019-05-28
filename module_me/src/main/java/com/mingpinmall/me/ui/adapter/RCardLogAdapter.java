package com.mingpinmall.me.ui.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.RCardLogBean;

import java.util.ArrayList;

/**
 * 功能描述：充值卡充值记录
 * @author 小斌
 * @date 2019/4/19
 **/
public class RCardLogAdapter extends BaseQuickAdapter<RCardLogBean.LogListBean, BaseViewHolder> {
    public RCardLogAdapter() {
        super(R.layout.item_predepositlog, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, RCardLogBean.LogListBean item) {
        String label = item.getDescription();
        String code = "";
        if (item.getDescription().contains(":")) {
            label = item.getDescription().split(":")[0] + ":";
            code = item.getDescription().split(":")[1];
        }
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
