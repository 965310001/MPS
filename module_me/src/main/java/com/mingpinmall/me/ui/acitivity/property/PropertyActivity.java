package com.mingpinmall.me.ui.acitivity.property;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityPropertyBinding;
import com.mingpinmall.me.ui.adapter.PropertyItemAdapter;
import com.mingpinmall.me.ui.bean.BaseItemBean;
import com.mingpinmall.me.ui.widget.SettingItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：我的财产
 * 创建人：小斌
 * 创建时间: 2019/3/27
 **/
@Route(path = ARouterConfig.PROPERTYACTIVITY)
public class PropertyActivity extends BaseActivity<ActivityPropertyBinding> {

    private PropertyItemAdapter itemAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_property;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle(getString(R.string.title_propertyActivity));
        itemAdapter = new PropertyItemAdapter();
        binding.setAdapter(itemAdapter);

        initItemData();
        setItemClickListener();
    }

    private void setItemClickListener() {
        itemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        ActivityToActivity.toActivity(ARouterConfig.ACCOUNTSURPLUSACTIVITY);
                        break;
                    case 1:
                        ActivityToActivity.toActivity(ARouterConfig.CARDSURPLUSACTIVITY);
                        break;
                    case 2:
                        ActivityToActivity.toActivity(ARouterConfig.COUPONACTIVITY);
                        break;
                    case 3:
                        ActivityToActivity.toActivity(ARouterConfig.STOREPACKETACTIVITY);
                        break;
                    case 4:
                        ActivityToActivity.toActivity(ARouterConfig.VIPINTERGRALACTIVITY);
                        break;
                }
            }
        });
    }

    /**
     * 初始化Item
     */
    private void initItemData() {
        int[] titleIds = new int[]{
                R.string.item_text_item1,
                R.string.item_text_item2,
                R.string.item_text_item3,
                R.string.item_text_item4,
                R.string.item_text_item5,
        };
        int[] descIds = new int[]{
                R.string.item_subtext_item1,
                R.string.item_subtext_item2,
                R.string.item_subtext_item3,
                R.string.item_subtext_item4,
                R.string.item_subtext_item5,
        };
        String[] subTitleIds = new String[]{
                "0.00 元",
                "0.00 元",
                "1 张",
                "0 个",
                "50 分",
        };
        List<BaseItemBean> itemBeanList = new ArrayList<>();
        for (int i = 0; i < titleIds.length; i++) {
            BaseItemBean itemBean = new BaseItemBean();
            itemBean.setItemType(1);
            itemBean.setItemMode(SettingItemView.ThemeMode.Default);
            itemBean.setTitle(getString(titleIds[i]));
            itemBean.setSubTitle(subTitleIds[i]);
            itemBean.setDescription(getString(descIds[i]));
            itemBean.setDrawable(R.drawable.icon_cartfill_press);
            itemBeanList.add(itemBean);
        }
        itemAdapter.setNewData(itemBeanList);
    }
}
