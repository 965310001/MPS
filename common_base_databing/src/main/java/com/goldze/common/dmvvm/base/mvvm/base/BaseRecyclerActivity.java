package com.goldze.common.dmvvm.base.mvvm.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.bumptech.glide.Glide;
import com.goldze.common.dmvvm.R;
import com.goldze.common.dmvvm.base.core.banner.BannerList;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.base.mvvm.AbsViewModel;
import com.goldze.common.dmvvm.databinding.FragmentListBinding;
import com.goldze.common.dmvvm.utils.ShareCacheUtil;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.utils.Utils;
import com.socks.library.KLog;
import com.trecyclerview.TRecyclerView;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.adapter.ItemData;
import com.trecyclerview.listener.OnRefreshListener;

import java.util.Collection;
import java.util.List;

/**
 * @author xiaobin copy from GuoFeng
 * @date : 2019/1/26 13:56
 * @description: 拓展版，支持列表，宫格，瀑布流
 */
public abstract class BaseRecyclerActivity<T extends AbsViewModel> extends AbsLifecycleActivity<FragmentListBinding, T> implements OnRefreshListener {

    protected TRecyclerView mRecyclerView;

    protected FloatingActionButton floatBtn;

    protected LinearLayoutManager linearManager;
    protected GridLayoutManager gridManager;
    protected StaggeredGridLayoutManager staggerManager;

    protected DelegateAdapter linearAdapter;
    protected DelegateAdapter gridAdapter;
    protected DelegateAdapter staggerAdapter;

    private AdapterType adapterType = AdapterType.None;

    protected long page = 1;

    protected String lastId = null;

    protected boolean isLoadMore = true, isLoading = true, isRefresh = false;

    protected ItemData oldItems;//, newItems;

    int lastItemPosition;

    //是否向上滑动
    private boolean isSlidingUpward;

    protected enum AdapterType {
        //线性
        Linear,
        //宫格
        Grid,
        //瀑布
        Stagger,
        //未设置
        None
    }

    /**
     * 设置默认的适配器
     * @return
     */
    protected abstract AdapterType getDefaultAdapterType();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        AdapterType getType = (AdapterType) ShareCacheUtil.get(activity).getAsObject(this.getClass().getSimpleName() + "_Type");
        if (getType != null) {
            adapterType = getType;
        } else {
            adapterType = getDefaultAdapterType();
        }
        mRecyclerView = findViewById(R.id.recycler_view);
        floatBtn = binding.floatBtn;
        oldItems = new ItemData();
        switch (adapterType) {
            case Linear:
                mRecyclerView.setAdapter(linearAdapter = createLinearAdapter());
                mRecyclerView.setLayoutManager(linearManager = (LinearLayoutManager) createLinearManager());
                break;
            case Grid:
                mRecyclerView.setAdapter(gridAdapter = createGridAdapter());
                mRecyclerView.setLayoutManager(gridManager = (GridLayoutManager) createGridManager());
                gridManager.setSpanSizeLookup(getSpanSizeLookup());
                break;
            case Stagger:
                mRecyclerView.setAdapter(staggerAdapter = createStaggerAdapter());
                mRecyclerView.setLayoutManager(staggerManager = (StaggeredGridLayoutManager) createStaggerManager());
                break;
            default:
                adapterType = AdapterType.Linear;
                mRecyclerView.setAdapter(linearAdapter = createLinearAdapter());
                mRecyclerView.setLayoutManager(linearManager = (LinearLayoutManager) createLinearManager());
                break;
        }


        mRecyclerView.addOnRefreshListener(this);
        mRecyclerView.addOnScrollStateListener(state1 -> {
            if (state1 == RecyclerView.SCROLL_STATE_IDLE) {
                Glide.with(Utils.getApplication()).resumeRequests();
            } else {
                Glide.with(Utils.getApplication()).pauseRequests();
            }
        });

        /*滑动显示到顶部*/
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = getListManager();
                    if (layoutManager instanceof GridLayoutManager) {
                        lastItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof LinearLayoutManager) {
                        lastItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                        lastItemPosition = findMax(lastPositions);
                    }
                    /*KLog.i((lastItemPosition + 5) + " =" + adapter.getItemCount() + "=" + isLoadMore);*/
                    if (lastItemPosition + 5 == mRecyclerView.getAdapter().getItemCount()
                            && isLoadMore) {
                        /*加载更多数据*/
                        KLog.i("加载更多数据");
                        if (isSlidingUpward) {
                            onLoadMore();
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!mRecyclerView.canScrollVertically(1)) {
                    floatBtn.show();//滑动到底部
                } else if (!mRecyclerView.canScrollVertically(-1)) {
                    floatBtn.hide();//滑动到顶部
                } else if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_SETTLING) {
                    //滚动状态
                    if (dy < 0 && floatBtn.getVisibility() == View.GONE) {
                        floatBtn.show();//向下滚动状态
                    }
                }
                isSlidingUpward = dy > 0;
            }
        });
        floatBtn.setOnClickListener(view -> mRecyclerView.smoothScrollToPosition(0));

        if (isItemDecoration()) {
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration
                    .VERTICAL));
        }
        onRefresh();
    }

    protected void changerManager(AdapterType adapterType) {
        switch (adapterType) {
            case Linear:
                if (linearAdapter == null) {
                    linearAdapter = createLinearAdapter();
                    linearManager = (LinearLayoutManager) createLinearManager();
                }
                linearAdapter.setDatas(oldItems);
                mRecyclerView.setLayoutManager(linearManager);
                mRecyclerView.setAdapter(linearAdapter);
                linearAdapter.notifyDataSetChanged();
                break;
            case Grid:
                if (gridAdapter == null) {
                    gridAdapter = createGridAdapter();
                    gridManager = (GridLayoutManager) createGridManager();
                    gridManager.setSpanSizeLookup(getSpanSizeLookup());
                }
                gridAdapter.setDatas(oldItems);
                mRecyclerView.setLayoutManager(gridManager);
                mRecyclerView.setAdapter(gridAdapter);
                gridAdapter.notifyDataSetChanged();
                break;
            case Stagger:
                if (staggerAdapter == null) {
                    staggerAdapter = createStaggerAdapter();
                    staggerManager = (StaggeredGridLayoutManager) createStaggerManager();
                }
                staggerAdapter.setDatas(oldItems);
                mRecyclerView.setLayoutManager(staggerManager);
                mRecyclerView.setAdapter(staggerAdapter);
                staggerAdapter.notifyDataSetChanged();
                break;
            default:
                if (linearAdapter == null) {
                    linearAdapter = createLinearAdapter();
                    linearManager = (LinearLayoutManager) createLinearManager();
                }
                linearAdapter.setDatas(oldItems);
                mRecyclerView.setLayoutManager(linearManager);
                mRecyclerView.setAdapter(linearAdapter);
                linearAdapter.notifyDataSetChanged();
                return;
        }
        this.adapterType = adapterType;
        if (mRecyclerView.getAdapter() == null) {
            ToastUtils.showShort("好像忘记初始化这个Adapter了哦！");
            return;
        }
        ShareCacheUtil.get(activity).put(this.getClass().getSimpleName() + "_Type", adapterType);
    }

    protected GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
        return new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                return 1;
            }
        };
    }

    protected AdapterType getAdapterType() {
        return adapterType;
    }

    protected DelegateAdapter getListAdapter() {
        switch (getAdapterType()) {
            case Linear:
                return linearAdapter;
            case Grid:
                return gridAdapter;
            case Stagger:
                return staggerAdapter;
            default:
                return linearAdapter;
        }
    }

    protected RecyclerView.LayoutManager getListManager() {
        switch (getAdapterType()) {
            case Linear:
                return linearManager;
            case Grid:
                return gridManager;
            case Stagger:
                return staggerManager;
            default:
                return linearManager;
        }
    }

    protected boolean isItemDecoration() {
        return true;
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    @Override
    public void onLoadMore() {
        ++page;
        isLoadMore = true;
        getRemoteData();
    }

    @Override
    public void onRefresh() {
        page = 1;
        lastId = null;
        isRefresh = true;
        isLoadMore = false;
        getRemoteData();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        onRefresh();
    }

    protected void setData(List<?> collection) {
        isLoadMore = collection.size() >= 10;
        if (!isRefresh) {
            onLoadMoreSuccess(collection);
        } else {
            onRefreshSuccess(collection);
        }
    }

    protected void setBannerData(BannerList headAdList) {
        oldItems.add(headAdList);
    }

    protected void onRefreshSuccess(Collection<?> collection) {
        oldItems.clear();
        oldItems.addAll(collection);
        mRecyclerView.refreshComplete(oldItems, !isLoadMore);
        isRefresh = false;
    }

    protected void onLoadMoreSuccess(List<?> collection) {
        isLoading = true;
        oldItems.addAll(collection);
        mRecyclerView.loadMoreComplete(collection, !isLoadMore);
    }

    @Nullable
    protected abstract DelegateAdapter createLinearAdapter();

    @Nullable
    protected abstract DelegateAdapter createGridAdapter();

    @Nullable
    protected abstract DelegateAdapter createStaggerAdapter();

    protected RecyclerView.LayoutManager createLinearManager() {
        return new LinearLayoutManager(this);
    }

    protected RecyclerView.LayoutManager createGridManager() {
        return new GridLayoutManager(this, 2);
    }

    protected RecyclerView.LayoutManager createStaggerManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    /**
     * 获取网络数据
     */
    protected void getRemoteData() {
    }
}