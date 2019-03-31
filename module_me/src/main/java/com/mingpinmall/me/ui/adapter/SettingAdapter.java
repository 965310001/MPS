package com.mingpinmall.me.ui.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.BaseItemBean;
import com.mingpinmall.me.ui.widget.SettingItemView;

import java.util.ArrayList;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/3/25
 **/
public class SettingAdapter extends BaseMultiItemQuickAdapter<BaseItemBean, BaseViewHolder> {

    public SettingAdapter() {
        super(new ArrayList<BaseItemBean>());
        addItemType(0, R.layout.item_default_space);
        addItemType(1, R.layout.item_base_setting);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseItemBean item) {
        switch (item.getItemType()) {
            case 0:
                /**
                 * 空白
                 */
                break;
            case 1:
                SettingItemView settingItemView = (SettingItemView) helper.itemView;
                settingItemView.setThemeMode(item.getItemMode());
                settingItemView.setTitle(item.getTitle());
                settingItemView.setSubTitle(item.getSubTitle() == null ? "" : item.getSubTitle());
                settingItemView.setDescription(item.getDescription() == null ? "" : item.getDescription());
                break;
        }

    }
}
