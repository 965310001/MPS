package com.mingpinmall.me.ui.acitivity.setting;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivitySettingBinding;
import com.mingpinmall.me.ui.adapter.SettingAdapter;
import com.mingpinmall.me.ui.bean.BaseItemBean;
import com.mingpinmall.me.ui.widget.SettingItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/3/25
 **/
@Route(path = ARouterConfig.SETTINGACTIVITY)
public class SettingActivity extends BaseActivity<ActivitySettingBinding> {

    @Autowired
    String name;

    SettingAdapter settingAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle(getString(R.string.title_settingActivity));
        ARouter.getInstance().inject(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        settingAdapter = new SettingAdapter();
        settingAdapter.bindToRecyclerView(binding.recyclerView);

        initItems();
    }

    /**
     * 初始化item
     */
    private void initItems() {
        String[] titles = new String[]{
                getString(R.string.set_item_item1),
                getString(R.string.set_item_item2),
                getString(R.string.set_item_item3),
                getString(R.string.set_item_item4),
                getString(R.string.set_item_item5),
        };
        String[] descs = new String[]{
                getString(R.string.set_desc_item1),
                getString(R.string.set_desc_item2),
                getString(R.string.set_desc_item3),
                getString(R.string.set_desc_item4),
                "",
        };
        List<BaseItemBean> itemBeanList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            BaseItemBean itemBean = new BaseItemBean();
            itemBean.setDescription(descs[i]);
            itemBean.setItemMode(SettingItemView.ThemeMode.NoLeftImage);
            itemBean.setTitle(titles[i]);
            itemBean.setItemType(1);
            itemBeanList.add(itemBean);
            if (i == 2 || i == 3 || i == 4) {
                BaseItemBean spaceItem = new BaseItemBean();
                spaceItem.setItemType(0);
                itemBeanList.add(spaceItem);
            }
        }
        itemBeanList.get(2).setSubTitle(getString(R.string.set_subitem_item4));
        itemBeanList.get(6).setItemMode(SettingItemView.ThemeMode.NoLeftImage_NoMoreIcon_NoDescription);
        settingAdapter.setNewData(itemBeanList);

        settingAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String itemName = "";
                switch (position) {
                    case 0:
                        itemName = "登录密码";
                        break;
                    case 1:
                        itemName = "手机验证";
                        break;
                    case 2:
                        itemName = "支付密码";
                        break;
                    case 4:
                        itemName = "用户反馈";
                        break;
                    case 5:
                        itemName = "安全退出";
                        break;
                }
                Toast.makeText(SettingActivity.this, "点击了 " + itemName + " 条目", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
