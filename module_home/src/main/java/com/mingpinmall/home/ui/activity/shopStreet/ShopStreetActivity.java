package com.mingpinmall.home.ui.activity.shopStreet;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.AndroidBug5497Workaround;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.home.R;
import com.mingpinmall.home.databinding.ActivityShopstreetBinding;
import com.mingpinmall.home.ui.adapter.ShopsStreetAdapter;
import com.mingpinmall.home.ui.api.HomeViewModel;
import com.mingpinmall.home.ui.bean.ShopStreetBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/15
 **/
@Route(path = ARouterConfig.home.SHOPSTREETACTIVITY)
public class ShopStreetActivity extends AbsLifecycleActivity<ActivityShopstreetBinding, HomeViewModel> {

    private ShopsStreetAdapter shopsStreetAdapter;
    private int pageIndex = 1;
    private int cityId;
    private int areaId;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        AndroidBug5497Workaround.assistActivity(findViewById(android.R.id.content));
        setTitle(R.string.title_ShopStreetActivity);
        binding.tvSelectAddress.setOnClickListener(this);
        binding.fab.setOnClickListener(this);

        shopsStreetAdapter = new ShopsStreetAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.recyclerView.setAdapter(shopsStreetAdapter);

        binding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getStoreStreet(binding.edSearch.getText().toString(), "0", 0, pageIndex + 1);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
            }
        });
    }

    @Override
    protected void initData() {
        mViewModel.getStoreStreet(binding.edSearch.getText().toString(), "0", 0, 1);
    }

    @Override
    protected void dataObserver() {
        registerObserver("GET_STORE_LIST", "success")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        BaseResponse<ShopStreetBean> data = (BaseResponse<ShopStreetBean>) result;
                        if (!data.isHasmore()) {
                            binding.refreshLayout.finishLoadMoreWithNoMoreData();
                        }
                        pageIndex = data.getPage_total();
                        if (data.getPage_total() == 1) {
                            binding.refreshLayout.finishRefresh();
                            shopsStreetAdapter.setNewData(data.getData().getStore_list());
                        } else {
                            binding.refreshLayout.finishLoadMore();
                            shopsStreetAdapter.addData(data.getData().getStore_list());
                        }
                    }
                });
        registerObserver("GET_STORE_LIST", "err")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object o) {
                        ToastUtils.showShort(o.toString());
                        binding.refreshLayout.finishRefresh(false);
                        binding.refreshLayout.finishLoadMore(false);
                    }
                });
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.tv_selectAddress) {
            //选择店铺地区
            ARouter.getInstance().build(ARouterConfig.Me.SelectCityActivity).navigation(this, 1);
        } else if (viewId == R.id.iv_class) {
            //分类

        } else if (viewId == R.id.fab) {
            //我的足迹
            ActivityToActivity.toActivity(ARouterConfig.Me.FOOTPRINTACTIVITY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 100) {
//                intent.putExtra("address", addressOne + addressTwo);
//                intent.putExtra("cityId", cityId);
//                intent.putExtra("areaId", cityId);
                //选择地区回来，更新列表
                cityId = data.getIntExtra("cityId", 0);
                areaId = data.getIntExtra("areaId", 0);
                binding.tvSelectAddress.setText(data.getStringExtra("address"));
                initData();
            }
        }
    }

    @Override
    protected Object getStateEventKey() {
        return "ShopStreetActivity";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopstreet;
    }
}
