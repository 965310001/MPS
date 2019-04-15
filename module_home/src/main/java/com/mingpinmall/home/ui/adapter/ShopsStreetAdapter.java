package com.mingpinmall.home.ui.adapter;

import android.support.v7.widget.AppCompatImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.home.R;
import com.mingpinmall.home.ui.bean.ShopStreetBean;

import java.util.ArrayList;

/**
 * 功能描述：店铺街  店铺列表
 * 创建人：小斌
 * 创建时间: 2019/4/8
 **/
public class ShopsStreetAdapter extends BaseQuickAdapter<ShopStreetBean.StoreListBean, BaseViewHolder> {
    public ShopsStreetAdapter() {
        super(R.layout.item_shops, new ArrayList<ShopStreetBean.StoreListBean>());
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopStreetBean.StoreListBean item) {
        ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_image), item.getStore_avatar());
        helper.setText(R.id.tv_label, item.getStore_name())
                .setText(R.id.tv_fans, item.getStore_collect())
                .setText(R.id.tv_count, item.getGoods_count() + "");
    }
}
