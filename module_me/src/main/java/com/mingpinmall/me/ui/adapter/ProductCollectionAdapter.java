package com.mingpinmall.me.ui.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.ProductCollectionBean;

import java.util.ArrayList;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/8
 **/
public class ProductCollectionAdapter extends BaseQuickAdapter<ProductCollectionBean.FavoritesListBean, BaseViewHolder> {
    public ProductCollectionAdapter() {
        super(R.layout.item_collection_product, new ArrayList<ProductCollectionBean.FavoritesListBean>());
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductCollectionBean.FavoritesListBean item) {
        ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_image), item.getGoods_image_url());
        helper.setText(R.id.tv_label, item.getGoods_name())
                .setText(R.id.tv_money, item.getGoods_price())
                .addOnClickListener(R.id.iv_delete);
    }
}
