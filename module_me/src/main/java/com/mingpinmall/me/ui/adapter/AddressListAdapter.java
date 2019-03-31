package com.mingpinmall.me.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.BaseItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：收货地址列表适配器
 * 创建人：小斌
 * 创建时间: 2019/3/29
 **/
public class AddressListAdapter extends BaseQuickAdapter<BaseItemBean, BaseViewHolder> {

    public AddressListAdapter() {
        super(R.layout.item_address, new ArrayList<BaseItemBean>());
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseItemBean item) {
        helper.addOnClickListener(R.id.tv_edit)
                .addOnClickListener(R.id.tv_delete);
    }
}
