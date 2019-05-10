package com.mingpinmall.me.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.FootprintBean;

import java.util.ArrayList;

/**
 * 功能描述：
 * @author 小斌
 * @date 2019/4/11
 **/
public class FootprintListAdapter extends BaseQuickAdapter<FootprintBean.GoodsbrowseListBean, BaseViewHolder> {

    public FootprintListAdapter() {
        super(R.layout.item_footprint, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, FootprintBean.GoodsbrowseListBean item) {
        helper.setText(R.id.tv_label, item.getGoods_name())
                .setText(R.id.tv_pay, "¥ " + item.getGoods_promotion_price());
        ImageUtils.loadImage(helper.getView(R.id.iv_image), item.getGoods_image_url());
    }
}
