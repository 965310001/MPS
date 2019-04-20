package com.mingpinmall.me.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.CouponListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/20
 **/
public class CouponListAdapter extends BaseQuickAdapter<CouponListBean.ListBean, BaseViewHolder> {
    public CouponListAdapter() {
        super(R.layout.item_predepositlog, new ArrayList<CouponListBean.ListBean>());
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponListBean.ListBean item) {

    }
}
