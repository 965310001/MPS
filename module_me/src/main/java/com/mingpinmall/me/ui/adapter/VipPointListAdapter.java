package com.mingpinmall.me.ui.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.VipPointListBean;

import java.util.ArrayList;

/**
 * 功能描述：会员积分记录
 * 创建人：小斌
 * 创建时间: 2019/4/20
 **/
public class VipPointListAdapter extends BaseQuickAdapter<VipPointListBean.LogListBean, BaseViewHolder> {
    public VipPointListAdapter() {
        super(R.layout.item_vip_points, new ArrayList<VipPointListBean.LogListBean>());
    }

    @Override
    protected void convert(BaseViewHolder helper, VipPointListBean.LogListBean item) {
        helper.setText(R.id.tv_label, item.getPl_desc())
                .setText(R.id.tv_point, Integer.parseInt(item.getPl_points()) > 0 ?
                        "+" + item.getPl_points() :
                        "-" + item.getPl_points()
                )
                .setTextColor(R.id.tv_point, Integer.parseInt(item.getPl_points()) > 0 ?
                        Color.parseColor("#ed5564") :
                        Color.parseColor("#36bc9b")
                );
    }
}
