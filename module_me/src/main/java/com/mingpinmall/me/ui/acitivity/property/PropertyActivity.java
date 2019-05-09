package com.mingpinmall.me.ui.acitivity.property;

import android.arch.lifecycle.Observer;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.bean.BaseBean;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ResourcesUtils;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityPropertyBinding;
import com.mingpinmall.me.ui.adapter.PropertyItemAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.BaseItemBean;
import com.mingpinmall.me.ui.bean.PropertyBean;
import com.mingpinmall.me.ui.constants.Constants;
import com.mingpinmall.me.ui.widget.SettingItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：我的财产
 * 创建人：小斌
 * 创建时间: 2019/3/27
 **/
@Route(path = ARouterConfig.Me.PROPERTYACTIVITY)
public class PropertyActivity extends AbsLifecycleActivity<ActivityPropertyBinding, MeViewModel> {

    private PropertyItemAdapter itemAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_property;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTitle(getString(R.string.title_propertyActivity));
        itemAdapter = new PropertyItemAdapter();
        binding.setAdapter(itemAdapter);

        initItemData();
        setItemClickListener();

        mViewModel.getProperty();
    }

    @Override
    protected Object getStateEventKey() {
        return "PROPERTY_ACTIVITY";
    }

    @Override
    protected void dataObserver() {
        registerObserver(Constants.MY_ASSET, Object.class).observeForever(result -> {
            if (result instanceof PropertyBean) {
                setData((PropertyBean) result);
            } else {
                ToastUtils.showShort(result.toString());
            }
        });
    }

    private void setData(PropertyBean propertyBean) {
        itemAdapter.getData().get(0).setSubTitle(propertyBean.getPredepoit() + " 元");
        itemAdapter.getData().get(1).setSubTitle(propertyBean.getAvailable_rc_balance() + " 元");
        itemAdapter.getData().get(2).setSubTitle(propertyBean.getVoucher() + " 张");
        itemAdapter.getData().get(3).setSubTitle(propertyBean.getRedpacket() + " 个");
        itemAdapter.getData().get(4).setSubTitle(propertyBean.getPoint() + " 分");
        itemAdapter.notifyDataSetChanged();
    }

    private void setItemClickListener() {
        itemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        ActivityToActivity.toActivity(ARouterConfig.Me.ACCOUNTSURPLUSACTIVITY);
                        break;
                    case 1:
                        ActivityToActivity.toActivity(ARouterConfig.Me.CARDSURPLUSACTIVITY);
                        break;
                    case 2:
                        ActivityToActivity.toActivity(ARouterConfig.Me.COUPONACTIVITY);
                        break;
                    case 3:
                        ActivityToActivity.toActivity(ARouterConfig.Me.STOREPACKETACTIVITY);
                        break;
                    case 4:
                        ActivityToActivity.toActivity(ARouterConfig.Me.VIPINTERGRALACTIVITY);
                        break;
                }
            }
        });
    }

    /**
     * 初始化Item
     */
    private void initItemData() {
        String[] titles = getResources().getStringArray(R.array.titles_property);
        String[] desc = getResources().getStringArray(R.array.desc_property);
        TypedArray subImage1 = ResourcesUtils.getInstance().obtainTypedArray(R.array.home_me_estates_image);
        List<BaseItemBean> itemBeanList = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            BaseItemBean itemBean = new BaseItemBean();
            itemBean.setItemType(1);
            itemBean.setItemMode(SettingItemView.ThemeMode.Default);
            itemBean.setTitle(titles[i]);
            itemBean.setSubTitle("");
            itemBean.setDescription(desc[i]);
            itemBean.setDrawable(subImage1.getResourceId(i, 0));
            itemBeanList.add(itemBean);
        }
        itemAdapter.setNewData(itemBeanList);
    }
}
