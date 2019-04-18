package com.mingpinmall.me.ui.adapter;

import android.support.v7.widget.AppCompatImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.OrderInformationBean;
import com.mingpinmall.me.ui.bean.PhysicalOrderBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：订单详情 中 店铺商品 适配器
 * 创建人：小斌
 * 创建时间: 2019/4/13
 **/
public class PhysicalOrderInformationListAdapter extends BaseQuickAdapter<OrderInformationBean.OrderInfoBean.GoodsListBean, BaseViewHolder> {

    public PhysicalOrderInformationListAdapter() {
        super(R.layout.item_physical_order_child, new ArrayList<OrderInformationBean.OrderInfoBean.GoodsListBean>());
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderInformationBean.OrderInfoBean.GoodsListBean item) {
        helper.setText(R.id.tv_label, item.getGoods_name())
                .setText(R.id.tv_payMoney, "¥" + item.getGoods_price())
                .setText(R.id.tv_count, "x" + item.getGoods_num());
        ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_image), item.getImage_url());
    }
}
