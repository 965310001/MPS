package com.mingpinmall.me.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.base.bean.AddressDataBean;
import com.mingpinmall.me.R;

import java.util.ArrayList;

/**
 * 功能描述：收货地址列表适配器
 *
 * @author 小斌
 * @date 2019/3/29
 **/
public class AddressListAdapter extends BaseQuickAdapter<AddressDataBean.AddressListBean, BaseViewHolder> {

    public AddressListAdapter() {
        super(R.layout.item_address, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressDataBean.AddressListBean item) {
        helper.addOnClickListener(R.id.tv_edit)
                .addOnClickListener(R.id.tv_delete)
                .setText(R.id.tv_name, item.getTrue_name())
                .setText(R.id.tv_phone, item.getMob_phone())
                .setText(R.id.tv_address, item.getArea_info() + " " + item.getAddress())
                .setGone(R.id.tv_isDefault, TextUtils.equals("1", item.getIs_default()));
    }
}
