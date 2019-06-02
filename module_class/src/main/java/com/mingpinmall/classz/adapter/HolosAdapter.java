package com.mingpinmall.classz.adapter;

import android.support.v7.widget.CardView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ui.vm.bean.HolosBean;

import java.util.ArrayList;

/**
 * @author 小斌
 * @data 2019/6/2
 **/
public class HolosAdapter extends BaseQuickAdapter<HolosBean, BaseViewHolder> {

    private int selIndex = 0;

    public void setSelIndex(int selIndex) {
        this.selIndex = selIndex;
        notifyDataSetChanged();
    }

    public HolosAdapter() {
        super(R.layout.item_holo_image, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, HolosBean item) {
        ImageUtils.loadImage(helper.getView(R.id.iv_image), item.getGoods_image());
        CardView cardView = helper.getView(R.id.cv_card);
        if (helper.getAdapterPosition() == selIndex) {
            cardView.setCardElevation(4f);
        } else {
            cardView.setCardElevation(0f);
        }
    }
}
