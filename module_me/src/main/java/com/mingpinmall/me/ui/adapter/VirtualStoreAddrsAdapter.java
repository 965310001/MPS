package com.mingpinmall.me.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.VirtualStoreAddrsBean;

import java.util.ArrayList;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/18
 **/
public class VirtualStoreAddrsAdapter extends BaseQuickAdapter<VirtualStoreAddrsBean.AddrListBean, BaseViewHolder> {
    public VirtualStoreAddrsAdapter() {
        super(R.layout.item_virtual_store_addrs, new ArrayList<VirtualStoreAddrsBean.AddrListBean>());
    }

    @Override
    protected void convert(BaseViewHolder helper, VirtualStoreAddrsBean.AddrListBean item) {
        helper.setText(R.id.tv_storeName, item.getName_info())
                .setText(R.id.tv_storeAddr, item.getAddress_info());
    }
}
