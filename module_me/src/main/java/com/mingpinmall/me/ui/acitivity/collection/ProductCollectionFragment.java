package com.mingpinmall.me.ui.acitivity.collection;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.dialog.TextDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentDefaultRecyclerviewBinding;
import com.mingpinmall.me.ui.adapter.ProductCollectionAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.ProductCollectionBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * 功能描述：商品收藏页面
 * 创建人：小斌
 * 创建时间: 2019/3/26
 **/
public class ProductCollectionFragment extends AbsLifecycleFragment<FragmentDefaultRecyclerviewBinding, MeViewModel> {

    private int pageIndex = 1;
    private boolean isLoadmore = false;

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
                isLoadmore = true;
                mViewModel.getProductCollectList(pageIndex + 1);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isLoadmore = false;
                mViewModel.getProductCollectList(1);
            }
        });
        collectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //点击事件，跳转商品详情
                ProductCollectionBean.FavoritesListBean data = collectionAdapter.getItem(position);
                ActivityToActivity.toActivity(ARouterConfig.home.SHOPPINGDETAILSACTIVITY, "id", data.getGoods_id());
            }
        });
        collectionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //子控件点击事件
                final ProductCollectionBean.FavoritesListBean itemData = collectionAdapter.getItem(position);
                if (view.getId() == R.id.iv_delete) {
                    //删除这条收藏
                    TextDialog.showBaseDialog(activity, "取消店铺收藏", "确定取消收藏吗？", new TextDialog.SingleButtonCallback() {
                        @Override
                        public void onClick() {
                            mViewModel.deleGoodsCollect(itemData.getGoods_id());
                        }
                    }).show();
                }
            }
        });
    }

    @Override
    protected void dataObserver() {
        registerObserver("DEL_GOODS_COLLECT", "success")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        lazyLoad();
                    }
                });
        registerObserver("DEL_GOODS_COLLECT", "err", String.class)
                .observeForever(new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String msg) {
                        ToastUtils.showShort(msg);
                    }
                });
        registerObserver("PRODUCT_COLLECT_LIST", "success")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        BaseResponse<ProductCollectionBean> data = (BaseResponse<ProductCollectionBean>) result;
                        pageIndex = 1;
                        binding.refreshLayout.finishRefresh();
                        collectionAdapter.setNewData(data.getData().getFavorites_list());
                        if (data.getData().getFavorites_list().size() == 0) {
                            showSuccess();
                        }
                        if (!data.isHasmore())
                            binding.refreshLayout.finishLoadMoreWithNoMoreData();
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
