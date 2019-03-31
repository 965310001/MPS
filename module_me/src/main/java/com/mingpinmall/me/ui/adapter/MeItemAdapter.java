package com.mingpinmall.me.ui.adapter;

import android.util.Log;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.MeItemBean;
import com.mingpinmall.me.ui.widget.AutoColorView;
import com.mingpinmall.me.ui.widget.SettingItemView;

import java.util.ArrayList;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/3/25
 **/
public class MeItemAdapter extends BaseMultiItemQuickAdapter<MeItemBean, BaseViewHolder> {

    public MeItemAdapter() {
        super(new ArrayList<MeItemBean>());
        addItemType(0, R.layout.view_me_horizontal_itemview);
        addItemType(1, R.layout.item_base_setting);
        addItemType(2, R.layout.item_default_space);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeItemBean item) {
        switch (helper.getItemViewType()) {
            case 0:
                //横向五个按钮
                helper.setText(R.id.tv_item1, item.getSubLabel()[0])
                        .setText(R.id.tv_item2, item.getSubLabel()[1])
                        .setText(R.id.tv_item3, item.getSubLabel()[2])
                        .setText(R.id.tv_item4, item.getSubLabel()[3])
                        .setText(R.id.tv_item5, item.getSubLabel()[4])
                        .setImageResource(R.id.iv_item1, item.getSubImage()[0])
                        .setImageResource(R.id.iv_item2, item.getSubImage()[1])
                        .setImageResource(R.id.iv_item3, item.getSubImage()[2])
                        .setImageResource(R.id.iv_item4, item.getSubImage()[3])
                        .setImageResource(R.id.iv_item5, item.getSubImage()[4])
                        .addOnClickListener(R.id.ll_item1)
                        .addOnClickListener(R.id.ll_item2)
                        .addOnClickListener(R.id.ll_item3)
                        .addOnClickListener(R.id.ll_item4)
                        .addOnClickListener(R.id.ll_item5);
                break;
            case 1:
                //正常按钮
                SettingItemView settingItemView = (SettingItemView) helper.itemView;
                settingItemView.setThemeMode(SettingItemView.ThemeMode.NoDescription);
                settingItemView.setImageIcon(item.getDrawable());
                settingItemView.setTitle(item.getTitle());
                settingItemView.setSubTitle(item.getTitle2());
                settingItemView.showRedPoint(item.getPoint());
                break;
            default:
                break;
        }
    }

}
