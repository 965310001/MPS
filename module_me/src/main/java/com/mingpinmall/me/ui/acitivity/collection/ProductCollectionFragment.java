package com.mingpinmall.me.ui.acitivity.collection;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentDefaultRecyclerviewBinding;
import com.mingpinmall.me.ui.adapter.ProductCollectionAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.ProductCollectionBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

/**
 * 功能描述：商品收藏页面
 * 创建人：小斌
 * 创建时间: 2019/3/26
 **/
public class ProductCollectionFragment extends AbsLifecycleFragment<FragmentDefaultRecyclerviewBinding, MeViewModel> {

    private int pageIndex = 1;
    private ProductCollectionAdapter collectionAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_default_recyclerview;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        collectionAdapter = new ProductCollectionAdapter();
        binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        binding.recyclerView.setAdapter(collectionAdapter);
        binding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getProductCollectList(pageIndex + 1);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getProductCollectList(1);
            }
        });
        collectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        collectionAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

                return true;
            }
        });
    }

    @Override
    protected void dataObserver() {
        registerObserver("PRODUCT_COLLECT_LIST", "success")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        pageIndex = 1;
                        binding.refreshLayout.finishRefresh();
                        BaseResponse<ProductCollectionBean> data = (BaseResponse<ProductCollectionBean>) result;
                        collectionAdapter.setNewData(data.getData().getFavorites_list());
                        if (data.getData().getFavorites_list().size() == 0) {
                            showSuccess();
                        }
                    }
                });
        registerObserver("PRODUCT_COLLECT_LIST", "loadmore")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        BaseResponse<ProductCollectionBean> data = (BaseResponse<ProductCollectionBean>) result;
                        pageIndex++;
                        if (data.isHasmore())
                            binding.refreshLayout.finishLoadMore();
                        else
                            binding.refreshLayout.finishLoadMoreWithNoMoreData();
                        collectionAdapter.addData(data.getData().getFavorites_list());
                    }
                });
        registerObserver("PRODUCT_COLLECT_LIST", "err")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        ToastUtils.showShort(result.toString());
                        binding.refreshLayout.finishRefresh(false);
                        binding.refreshLayout.finishLoadMore(false);
                    }
                });
    }

    @Override
    protected void lazyLoad() {
        mViewModel.getProductCollectList(1);
    }

    @Override
    protected Object getStateEventKey() {
        return "PRODUCT_COLLECTION";
    }
}
