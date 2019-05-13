package com.mingpinmall.me.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.PdcashBean;

import java.util.ArrayList;

/**
 * 功能描述：余额提现
 * *@author 小斌
 * @date 2019/4/19
 **/
public class PdcashAdapter extends BaseQuickAdapter<PdcashBean.ListBean, BaseViewHolder> {
    public PdcashAdapter() {
        super(R.layout.item_pdcash, new ArrayList<PdcashBean.ListBean>());
    }

    @Override
    protected void convert(BaseViewHolder helper, PdcashBean.ListBean item) {
        helper.setText(R.id.tv_label, "提现审核：" + item.getPdc_payment_state_text())
                .setText(R.id.tv_code, "充值单号：" + item.getPdc_sn())
                .setText(R.id.tv_money, item.getPdc_amount())
                .setText(R.id.tv_time, item.getPdc_add_time_text());
    }
}
