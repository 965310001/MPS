package com.mingpinmall.me.ui.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.MeItemBean;
import com.goldze.common.dmvvm.widget.SettingItemView;

import java.util.ArrayList;

/**
 * 功能描述：
 * @author 小斌
 * @date 2019/3/25
 **/
public class MeItemAdapter extends BaseMultiItemQuickAdapter<MeItemBean, BaseViewHolder> {

    public MeItemAdapter() {
        super(new ArrayList<>());
        addItemType(0, R.layout.view_me_horizontal_itemview);
        addItemType(1, R.layout.item_base_setting);
        addItemType(2, R.layout.item_default_space);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeItemBean item) {
        switch (helper.getItemViewType()) {
            case 0:
                //横向五个按钮
                int[] textView = new int[]{R.id.tv_item1, R.id.tv_item2, R.id.tv_item3, R.id.tv_item4, R.id.tv_item5};
                int[] corner = new int[]{R.id.tv_corner1, R.id.tv_corner2, R.id.tv_corner3, R.id.tv_corner4, R.id.tv_corner5};
                int[] imageView = new int[]{R.id.iv_item1, R.id.iv_item2, R.id.iv_item3, R.id.iv_item4, R.id.iv_item5};
                int[] linearLayout = new int[]{R.id.ll_item1, R.id.ll_item2, R.id.ll_item3, R.id.ll_item4, R.id.ll_item5};
                for (int i = 0; i < 5; i++) {
                    helper.setText(textView[i], item.getSubLabel()[i])
//                            .setText(corner[i], String.valueOf(item.getSubCorner()[i]))
                            .setGone(corner[i], item.getSubCorner()[i] > 0)
                            .setImageResource(imageView[i], item.getSubImage().getResourceId(i, 0))
                            .addOnClickListener(linearLayout[i]);
                }
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
