package com.mingpinmall.me.ui.adapter;

import android.support.v7.widget.AppCompatImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.ReturnBean;

import java.util.ArrayList;

/**
 * 功能描述：退货列表
 * 创建人：小斌
 * 创建时间: 2019/4/23
 **/
public class ReturnOrderListAdapter extends BaseQuickAdapter<ReturnBean.ReturnListBean, BaseViewHolder> {
    public ReturnOrderListAdapter() {
        super(R.layout.item_return_order, new ArrayList<ReturnBean.ReturnListBean>());
    }

    @Override
    protected void convert(BaseViewHolder helper, ReturnBean.ReturnListBean item) {
        helper.setText(R.id.tv_storeName, item.getStore_name())
                .setText(R.id.tv_time, item.getAdd_time())
                .setText(R.id.tv_label, item.getGoods_name())
                .setText(R.id.tv_money, item.getRefund_amount())
                .setText(R.id.tv_returnCount, item.getGoods_num())
                .addOnClickListener(R.id.ll_shopContent)
                .addOnClickListener(R.id.bt_refundInformation)
                .addOnClickListener(R.id.tv_removeOrder);
        ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_image), item.getGoods_img_360());
    }
}
