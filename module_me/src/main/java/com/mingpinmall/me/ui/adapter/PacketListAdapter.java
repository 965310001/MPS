package com.mingpinmall.me.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.PacketListBean;

import java.util.ArrayList;

/**
 * 功能描述：我的红包
 * 创建人：小斌
 * 创建时间: 2019/4/20
 **/
public class PacketListAdapter extends BaseQuickAdapter<PacketListBean.ListBean, BaseViewHolder> {
    public PacketListAdapter() {
        super(R.layout.item_predepositlog, new ArrayList<PacketListBean.ListBean>());
    }

    @Override
    protected void convert(BaseViewHolder helper, PacketListBean.ListBean item) {

    }
}
