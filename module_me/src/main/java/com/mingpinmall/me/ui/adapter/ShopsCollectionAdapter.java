package com.mingpinmall.me.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.ShopsCollectionBean;

import java.util.ArrayList;

/**
 * 功能描述：
 * @author 小斌
 * @date 2019/4/8
 **/
public class ShopsCollectionAdapter extends BaseQuickAdapter<ShopsCollectionBean.FavoritesListBean, BaseViewHolder> {
    public ShopsCollectionAdapter() {
        super(R.layout.item_collection_shops, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopsCollectionBean.FavoritesListBean item) {
        ImageUtils.loadImage(helper.getView(R.id.iv_image), item.getStore_avatar_url());
        helper.setText(R.id.tv_label, item.getStore_name())
                .setText(R.id.tv_fans, item.getStore_collect())
                .setText(R.id.tv_count, item.getGoods_count() + "")
                .addOnClickListener(R.id.iv_delete);
    }
}
