package com.mingpinmall.me.ui.adapter;

import android.support.v7.widget.AppCompatImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.PhysicalOrderBean;

import java.util.List;

/**
 * 功能描述：订单列表 中 店铺商品 适配器
 * 创建人：小斌
 * 创建时间: 2019/4/13
 **/
public class PhysicalOrderChildListAdapter extends BaseQuickAdapter<PhysicalOrderBean.ExtendOrderGoodsBean, BaseViewHolder> {

    public PhysicalOrderChildListAdapter(List<PhysicalOrderBean.ExtendOrderGoodsBean> dataList) {
        super(R.layout.item_physical_order_child, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, PhysicalOrderBean.ExtendOrderGoodsBean item) {
        helper.setText(R.id.tv_label, item.getGoods_name())
                .setText(R.id.tv_payMoney, "¥" + item.getGoods_price())
                .setText(R.id.tv_count, "x" + item.getGoods_num())
                .setText(R.id.tv_spec, item.getGoods_spec());
        ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_image), item.getGoods_image_url());

    }
}
