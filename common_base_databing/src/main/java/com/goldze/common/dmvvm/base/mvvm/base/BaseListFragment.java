package com.goldze.common.dmvvm.base.mvvm.base;

import android.os.Bundle;
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
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.base.mvvm.AbsViewModel;
import com.goldze.common.dmvvm.databinding.FragmentListBinding;
import com.socks.library.KLog;
import com.trecyclerview.TRecyclerView;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.adapter.ItemData;
import com.trecyclerview.listener.OnRefreshListener;


import java.util.Collection;
import java.util.List;

/**
 * @author GuoFeng
 * @date :2019/1/17 17:06
 * @description:
 */
public abstract class BaseListFragment<T extends AbsViewModel> extends AbsLifecycleFragment<FragmentListBinding, T>
        implements OnRefreshListener {

    protected TRecyclerView mRecyclerView;

    protected FloatingActionButton floatBtn;

    protected RecyclerView.LayoutManager layoutManager;

    protected DelegateAdapter adapter;

    protected String lastId = null;

    protected long page = 1;

    protected boolean isLoadMore = true, isLoading = true, isRefresh = false;

    protected ItemData oldItems;

    private int lastItemPosition;

    //是否向上滑动
    private boolean isSlidingUpward;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_list;
    }

    @Override
    public int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        mRecyclerView = getViewById(R.id.recycler_view);
        floatBtn = getViewById(R.id.float_btn);
        oldItems = new ItemData();
        adapter = createAdapter();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(layoutManager = createLayoutManager());
        if (isItemDecoration()) {
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL));
        }
        mRecyclerView.addOnRefreshListener(this);
//        mRecyclerView.addOnScrollStateListener(new OnScrollStateListener() {
//            @Override
//            public void onScrollStateChanged(int state1) {
//                if (state1 == RecyclerView.SCROLL_STATE_IDLE) {
//                    if (activity != null) {
//                        Glide.with(activity).resumeRequests();
//                    }
//                } else {
//                    if (activity != null) {
//                        Glide.with(activity).pauseRequests();
//                    }
//                }
//            }
//        });
        /*滑动显示到顶部*/
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (activity != null) {
                        Glide.with(activity).resumeRequests();
                    }
                } else {
                    if (activity != null) {
                        Glide.with(activity).pauseRequests();
                    }
                }
                // TODO: 2019/3/31 滑动的状态的待修改 
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (layoutManager instanceof GridLayoutManager) {
                        lastItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof LinearLayoutManager) {
                        lastItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                        lastItemPosition = findMax(lastPositions);
                    }
                    if (lastItemPosition + 4 == adapter.getItemCount()
                            && isLoadMore) {
                        /*加载更多数据*/
                        KLog.i("加载更多数据");
                        if (isSlidingUpward) onLoadMore();
                    }
                }
//                if (lastItemPosition == 0) {
//                    KLog.i("顶部");
//                } else if (lastItemPosition == layoutManager.getItemCount() - 1) {
//                    KLog.i("底部");
//                }
//                /*KLog.i("是否到顶部" + isSlideToBottom(recyclerView));*/
//                if (lastItemPosition == 0) {
//                    KLog.i("顶部");
//                } else if (lastItemPosition == layoutManager.getItemCount() - 1) {
//                    KLog.i("底部");
//                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

//                KLog.i("" + (dy > 0) + dy);
                if (!mRecyclerView.canScrollVertically(1)) {
                    if (dy > 0 && floatBtn.getVisibility() == View.GONE) {
                        floatBtn.show();//滑动到底部
                    } else {
                        floatBtn.hide();
                    }
                } else if (!mRecyclerView.canScrollVertically(-1)) {
                    if (dy > 0 && floatBtn.getVisibility() == View.VISIBLE) {
                        floatBtn.hide();//滑动到顶部
                    }
                } else if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_SETTLING) {//滚动状态
                    if (dy < 0 && floatBtn.getVisibility() == View.VISIBLE) {
                        floatBtn.hide();
                    } else if (dy < 0 && floatBtn.getVisibility() == View.GONE) {
                        floatBtn.show();//向下滚动状态
                    }
                }
//                isSlidingUpward = dy > 0;
//                KLog.i("" + isSlidingUpward + dy);

//                if (lastItemPosition == 0) {
//                    KLog.i("顶部");
//                } else if (lastItemPosition == layoutManager.getItemCount() - 1) {
//                    KLog.i("底部");
//                }
//                isSlidingUpward = dy > 0;
//                KLog.i("" + isSlidingUpward + dy);
//                KLog.i("是否到顶部" + isSlideToBottom(recyclerView));
            }
        });
        floatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.smoothScrollToPosition(0);
//                floatBtn.hide();
            }
        });
    }

    /*是否到顶部*/
    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
//        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
//                >= recyclerView.computeVerticalScrollRange())
//            return true;
//        return false;
        return recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange();
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
    protected void lazyLoad() {
        isLoadMore = false;
        onRefresh();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        onRefresh();
    }

    protected void setData(List<?> collection) {
        isLoadMore = collection.size() >= 10;
//        if (isLoadMore) {
//            onLoadMoreSuccess(collection);
//        } else {
//            onRefreshSuccess(collection);
//        }
        if (!isRefresh) onLoadMoreSuccess(collection);
        else onRefreshSuccess(collection);
    }

    @Override
    public void onRefresh() {
        KLog.i("onRefresh");
        page = 1;
        lastId = null;
        isRefresh = true;
        isLoadMore = false;

        getRemoteData();
    }

    @Override
    public void onLoadMore() {
        ++page;
        isLoadMore = true;
        getLoadMoreData();
    }

    protected void setBannerData(BannerList headAdList) {
        oldItems.add(headAdList);
    }

    protected void onRefreshSuccess(Collection<?> collection) {
        oldItems.clear();
        oldItems.addAll(collection);
//        if (null != collection && collection.size() < 10) {
//            mRecyclerView.refreshComplete(oldItems, true);
//        } else {
//            mRecyclerView.refreshComplete(oldItems, false);
//        }
        mRecyclerView.refreshComplete(oldItems, null != collection && collection.size() < 10);
        isRefresh = false;

    }

    protected void onLoadMoreSuccess(List<?> collection) {
        isLoading = true;
//        isLoadMore = false;
        oldItems.addAll(collection);
//        if (null != collection && collection.size() < 10) {
//            mRecyclerView.loadMoreComplete(collection, true);
//        } else {
//        }
        mRecyclerView.loadMoreComplete(collection, null != collection && collection.size() < 10);
    }

    /**
     * adapter
     *
     * @return DelegateAdapter
     */
    protected abstract DelegateAdapter createAdapter();

    /**
     * LayoutManager
     *
     * @return LayoutManager
     */
    protected RecyclerView.LayoutManager createLayoutManager() {
//        layoutManager = new LinearLayoutManager(getContext());
        return new LinearLayoutManager(getContext());
    }

    /**
     * 获取更多网络数据t
     */
    protected void getLoadMoreData() {
        getRemoteData();
    }

}
