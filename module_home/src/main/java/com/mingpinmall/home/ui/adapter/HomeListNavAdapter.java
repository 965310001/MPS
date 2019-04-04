package com.mingpinmall.home.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.home.R;
import com.mingpinmall.home.ui.bean.HomeItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/3
 **/
public class HomeListNavAdapter extends BaseQuickAdapter<HomeItemBean.DatasBean.Home6Bean.ItemBeanX, BaseViewHolder> {


    public HomeListNavAdapter() {
        super(R.layout.item_base_image, new ArrayList<HomeItemBean.DatasBean.Home6Bean.ItemBeanX>());
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeItemBean.DatasBean.Home6Bean.ItemBeanX item) {
        ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.image), item.getImage());
    }
}
