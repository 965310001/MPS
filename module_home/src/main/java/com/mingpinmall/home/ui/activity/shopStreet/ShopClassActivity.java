package com.mingpinmall.home.ui.activity.shopStreet;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.kongzue.stacklabelview.interfaces.OnLabelClickListener;
import com.mingpinmall.home.R;
import com.mingpinmall.home.databinding.ActivityShopclassBinding;
import com.mingpinmall.home.ui.api.HomeViewModel;
import com.mingpinmall.home.ui.bean.ShopClassBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/15
 **/
@Route(path = ARouterConfig.home.SHOPCLASSACTIVITY)
public class ShopClassActivity extends AbsLifecycleActivity<ActivityShopclassBinding, HomeViewModel> {

    private List<String> stringList;
    private ShopClassBean shopClassBean;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTitle(R.string.title_ShopClassActivity);
        stringList = new ArrayList<>();

        binding.stackLabelView.setOnLabelClickListener(new OnLabelClickListener() {
            @Override
            public void onClick(int index, View v, String s) {
                Intent intent = new Intent();
                intent.putExtra("sc_id", index == 0 ? "0" : shopClassBean.getClass_list().get(index - 1).getSc_id());
                setResult(100, intent);
                finish();
            }
        });
    }

    @Override
    protected void dataObserver() {
        registerObserver("GET_STORE_CLASS", "success", ShopClassBean.class)
                .observeForever(new Observer<ShopClassBean>() {
                    @Override
                    public void onChanged(@Nullable ShopClassBean result) {
                        shopClassBean = result;
                        stringList.clear();
                        stringList.add("全部");
                        for (ShopClassBean.ClassListBean classListBean : result.getClass_list()) {
                            stringList.add(classListBean.getSc_name());
                        }
                        binding.stackLabelView.setLabels(stringList);
                    }
                });
        registerObserver("GET_STORE_CLASS", "err")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object o) {
                        ToastUtils.showShort(o.toString());
                    }
                });
    }

    @Override
    protected void initData() {
        mViewModel.getStoreClass();
    }

    @Override
    protected Object getStateEventKey() {
        return "SHOP_CLASS_ACTIVITY";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopclass;
    }
}