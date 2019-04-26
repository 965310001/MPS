package com.mingpinmall.me.ui.acitivity.collection;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
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
import com.mingpinmall.me.ui.adapter.ShopsCollectionAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.ShopsCollectionBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * 功能描述：店铺收藏页面
 * 创建人：小斌
 * 创建时间: 2019/3/26
 **/
public class ShopsCollectionFragment extends AbsLifecycleFragment<FragmentDefaultRecyclerviewBinding, MeViewModel> {

    private int pageIndex = 1;
    private ShopsCollectionAdapter collectionAdapter;

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
        collectionAdapter = new ShopsCollectionAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerView.setAdapter(collectionAdapter);

        binding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getShopsCollectList(pageIndex + 1);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getShopsCollectList(1);
            }
        });
        collectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //点击事件，跳转到店铺
                ShopsCollectionBean.FavoritesListBean bean = collectionAdapter.getItem(position);
                ActivityToActivity.toActivity(ARouterConfig.classify.STOREACTIVITY, "storeId", bean.getStore_id());
            }
        });
        collectionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //子控件点击事件
                final ShopsCollectionBean.FavoritesListBean itemData = collectionAdapter.getItem(position);
                if (view.getId() == R.id.iv_delete) {
                    //删除这条收藏
                    TextDialog.showBaseDialog(activity, "取消收藏", "确定取消收藏吗？", new TextDialog.SingleButtonCallback() {
                        @Override
                        public void onClick() {
                            mViewModel.deleShopsCollect(itemData.getStore_id());
                        }
                    }).show();
                }
            }
        });
    }

    @Override
    protected void dataObserver() {
        registerObserver("DEL_SHOP_COLLECT", "success")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        lazyLoad();
                    }
                });
        registerObserver("DEL_SHOP_COLLECT", "err", String.class)
                .observeForever(new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String msg) {
                        ToastUtils.showShort(msg);
                    }
                });
        registerObserver("SHOPS_COLLECT_LIST", "success")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        pageIndex = 1;
                        binding.refreshLayout.finishRefresh();
                        BaseResponse<ShopsCollectionBean> data = (BaseResponse<ShopsCollectionBean>) result;
                        collectionAdapter.setNewData(data.getData().getFavorites_list());
                    }
                });
        registerObserver("SHOPS_COLLECT_LIST", "loadmore")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        pageIndex++;
                        binding.refreshLayout.finishLoadMore();
                        BaseResponse<ShopsCollectionBean> data = (BaseResponse<ShopsCollectionBean>) result;
                        collectionAdapter.addData(data.getData().getFavorites_list());
                    }
                });
        registerObserver("SHOPS_COLLECT_LIST", "err")
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
        mViewModel.getShopsCollectList(1);
    }

    @Override
    protected Object getStateEventKey() {
        return "SHOPS_COLLECTION";
    }
}
