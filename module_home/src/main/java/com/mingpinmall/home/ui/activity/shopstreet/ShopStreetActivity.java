package com.mingpinmall.home.ui.activity.shopstreet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
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
import com.mingpinmall.home.ui.constants.Constants;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * 功能描述：
 * @author 小斌
 * @date 2019/4/15
 **/
@Route(path = ARouterConfig.home.SHOPSTREETACTIVITY)
public class ShopStreetActivity extends AbsLifecycleActivity<ActivityShopstreetBinding, HomeViewModel> {

    private ShopsStreetAdapter shopsStreetAdapter;
    private int pageIndex = 1;
    private boolean isLoadmore = false;
    private String sc_id = "";

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        AndroidBug5497Workaround.assistActivity(findViewById(android.R.id.content));
        setTitle(R.string.title_ShopStreetActivity);
        binding.ivClass.setOnClickListener(this);
        binding.tvSelectAddress.setOnClickListener(this);
        binding.tvClearnAddress.setOnClickListener(this);
        binding.tvSearch.setOnClickListener(this);
        binding.fab.setOnClickListener(this);

        shopsStreetAdapter = new ShopsStreetAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(shopsStreetAdapter);

        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> initData());
        shopsStreetAdapter.setOnLoadMoreListener(() -> {
            isLoadmore = true;
            String text = binding.tvSelectAddress.getText().toString();
            mViewModel.getStoreStreet(
                    binding.edSearch.getText().toString(),
                    text.equals(getString(R.string.text_selectShopAddress)) ? "" : text,
                    sc_id,
                    pageIndex + 1
            );
        }, binding.recyclerView);

        /*监听变化显示和隐藏按钮*/
        binding.tvSelectAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.tvClearnAddress.setVisibility(
                        s.toString().equals(getString(R.string.text_selectShopAddress))
                                ? View.INVISIBLE
                                : View.VISIBLE
                );
            }
        });
        shopsStreetAdapter.setOnItemClickListener((adapter, view, position) -> {
            //列表点击事件
            ShopStreetBean.StoreListBean storeListBean = shopsStreetAdapter.getItem(position);
            ActivityToActivity.toActivity(ARouterConfig.classify.STOREACTIVITY, "storeId", storeListBean.getStore_id());
        });
    }

    @Override
    protected void initData() {
        isLoadmore = false;
        String text = binding.tvSelectAddress.getText().toString();
        mViewModel.getStoreStreet(
                binding.edSearch.getText().toString(),
                text.equals(getString(R.string.text_selectShopAddress)) ? "" : text,
                sc_id,
                1
        );
    }

    @Override
    protected void dataObserver() {
        registerObserver(Constants.GET_STORE_LIST, Object.class).observeForever(result -> {
            if (result instanceof String) {
                ToastUtils.showShort(result.toString());
                if (!isLoadmore) {
                    binding.refreshLayout.finishRefresh(false);
                } else {
                    shopsStreetAdapter.loadMoreFail();
                }
            } else {
                BaseResponse<ShopStreetBean> data = (BaseResponse<ShopStreetBean>) result;
                if (!isLoadmore) {
                    pageIndex = 1;
                    binding.refreshLayout.finishRefresh();
                    shopsStreetAdapter.setNewData(data.getData().getStore_list());
                } else {
                    pageIndex++;
                    shopsStreetAdapter.loadMoreComplete();
                    shopsStreetAdapter.addData(data.getData().getStore_list());
                }
                if (!data.isHasmore()) {
                    shopsStreetAdapter.loadMoreEnd();
                }
            }
        });
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.tv_search) {
            //搜索
            initData();
        }
        if (viewId == R.id.tv_selectAddress) {
            //选择店铺地区
            ARouter.getInstance().build(ARouterConfig.Me.SELECTCITYACTIVITY).navigation(this, 1);
        } else if (viewId == R.id.iv_class) {
            //分类
            ARouter.getInstance().build(ARouterConfig.home.SHOPCLASSACTIVITY).navigation(this, 2);
        } else if (viewId == R.id.tv_clearnAddress) {
            //重置选择的地址
            binding.tvSelectAddress.setText(getString(R.string.text_selectShopAddress));
            initData();
        } else if (viewId == R.id.fab) {
            //我的足迹
            ActivityToActivity.toActivity(ARouterConfig.Me.FOOTPRINTACTIVITY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                //选择地址
                if (resultCode == RESULT_OK) {
                    //选择地区回来，更新列表
                    binding.tvSelectAddress.setText(data.getStringExtra("address"));
                    initData();
                }
                break;
            case 2:
                //选择分类
                if (resultCode == RESULT_OK) {
                    //选择地区回来，更新列表
                    sc_id = data.getStringExtra("sc_id");
                    initData();
                }
                break;
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
