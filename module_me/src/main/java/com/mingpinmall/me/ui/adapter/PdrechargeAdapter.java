package com.mingpinmall.me.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.PdrechargeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：充值明细
 * 创建人：小斌
 * 创建时间: 2019/4/19
 **/
public class PdrechargeAdapter extends BaseQuickAdapter<PdrechargeBean.ListBean, BaseViewHolder> {
    public PdrechargeAdapter() {
        super(R.layout.item_pdrecharge, new ArrayList<PdrechargeBean.ListBean>());
    }

    @Override
    protected void convert(BaseViewHolder helper, PdrechargeBean.ListBean item) {
        helper.setText(R.id.tv_label, "充值状态：" + item.getPdr_payment_state_text())
                .setText(R.id.tv_code, "充值单号：" + item.getPdr_sn())
                .setText(R.id.tv_money, item.getPdr_amount())
                .setText(R.id.tv_time, item.getPdr_add_time_text());
    }
}