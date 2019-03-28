package com.mingpinmall.classz;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.BaseAdapter;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.listener.OnLoadMoreListener;
import com.goldze.common.dmvvm.R;
import com.goldze.common.dmvvm.base.core.banner.BannerList;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.base.mvvm.AbsViewModel;
import com.goldze.common.dmvvm.databinding.FragmentList2Binding;
import com.goldze.common.dmvvm.databinding.FragmentListBinding;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.socks.library.KLog;
import com.trecyclerview.TRecyclerView;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.adapter.ItemData;
import com.trecyclerview.listener.OnRefreshListener;
import com.trecyclerview.listener.OnScrollStateListener;

import java.util.Collection;
import java.util.List;

/**
 * @author GuoFeng
 * @date :2019/1/17 17:06
 * @description:
 */
public abstract class BaseListFragment2<T extends AbsViewModel> extends AbsLifecycleFragment<FragmentList2Binding, T>
        implements OnRefreshListener {

    protected TRecyclerView mRecyclerView;

    protected FloatingActionButton floatBtn;

//    protected RelativeLayout mTitleBar;
//    protected TextView mTitle;

    protected RecyclerView.LayoutManager layoutManager;

    protected DelegateAdapter adapter;

    protected String lastId = null;

    protected long page = 1;

    protected boolean isLoadMore = true;

    protected boolean isLoading = true;

    protected boolean isRefresh = false;

    protected ItemData oldItems;

    protected ItemData newItems;

    private int lastItemPosition;

    //是否向上滑动
    private boolean isSlidingUpward;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_list2;
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
        newItems = new ItemData();
        adapter = createAdapter();
        adapter.setDatas(oldItems);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(createLayoutManager());
        if (isItemDecoration()) {
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL));
        }
        mRecyclerView.addOnRefreshListener(this);
        mRecyclerView.addOnScrollStateListener(new OnScrollStateListener() {
            @Override
            public void onScrollStateChanged(int state1) {
                if (state1 == RecyclerView.SCROLL_STATE_IDLE) {
                    if (activity != null) {
//                        Glide.with(activity).resumeRequests();
                    }
                } else {
                    if (activity != null) {
//                        Glide.with(activity).pauseRequests();
                    }
                }
            }
        });

        /*滑动显示到顶部*/
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
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
                    if (lastItemPosition + 1 == adapter.getItemCount()
                            && isLoadMore) {
                        /*加载更多数据*/
                        KLog.i("加载更多数据");
                        if (isSlidingUpward) onLoadMore();
                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!mRecyclerView.canScrollVertically(1)) {
                    if (dy > 0 && floatBtn.getVisibility() == View.GONE) {
                        floatBtn.show();//滑动到底部
                    }
                } else if (!mRecyclerView.canScrollVertically(-1)) {
                    floatBtn.hide();//滑动到顶部
                } else if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_SETTLING) {//滚动状态
                    if (dy < 0 && floatBtn.getVisibility() == View.GONE) {
                        floatBtn.show();//向下滚动状态
                    }
//                    else {
//                        floatBtn.hide();
//                    }
                }
                isSlidingUpward = dy > 0;
                KLog.i("" + isSlidingUpward + dy);
            }
        });
        floatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.smoothScrollToPosition(0);
            }
        });


        /**************************** start *****************************/
        adapter1 = new BaseItemAdapter();
        recyclerView1 = binding.recyclerView;
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter1.register(TextBean.class, new TextViewManager());
        adapter1.register(GoodsInfo.class, new GoodsInfoManager());
        adapter1.addHeadItem(new TextBean("我是Head View"));
        adapter1.addFootItem(new TextBean("我是Foot View"));
        recyclerView1.setAdapter(adapter1);
        adapter1.enableLoadMore(new LoadMoreHolderManager(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                ++page;
                isLoadMore = true;
                getLoadMoreData();
            }
        }));
        swipeRefreshLayout = binding.swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                currPage = 1;
                adapter1.clearData();
                adapter1.notifyDataSetChanged();

                page = 1;
                lastId = null;
                isRefresh = true;
                isLoadMore = false;
                getRemoteData();
            }
        });

    }

    BaseItemAdapter adapter1;
    RecyclerView recyclerView1;
    int currPage;
    SwipeRefreshLayout swipeRefreshLayout;

//    protected void loadData() {
//        if (currPage < 3) {
////            fillData();
//            adapter1.setLoadCompleted(false);
//            currPage++;
//        } else if (currPage == 3) {
//            adapter1.setLoadFailed();
////            failTimes++;
//        } else {
////            fillData();
//            adapter1.setLoadCompleted(true);
//        }
//        swipeRefreshLayout.setRefreshing(false);
//    }

    /**************************** end *****************************/


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
        getRemoteData();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        onRefresh();
    }

    protected void setData(List<?> collection) {
        if (isLoadMore) {
            onLoadMoreSuccess(collection);
        } else {
            onRefreshSuccess(collection);
        }
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
    public void onLoadMore() {
        ++page;
        isLoadMore = true;
        getLoadMoreData();
    }

    protected void setBannerData(BannerList headAdList) {
        newItems.add(headAdList);
    }

    protected void onRefreshSuccess(Collection<?> collection) {
//        newItems.clear();
//        newItems.addAll(collection);
//        oldItems.clear();
//        oldItems.addAll(newItems);
        if (null != collection && collection.size() < 10) {
            mRecyclerView.refreshComplete(oldItems, true);
        } else {
            mRecyclerView.refreshComplete(oldItems, false);
        }
//        isRefresh = false;
//        if (null != collection && collection.size() < 10) {
//            adapter1.setLoadCompleted(false);
//        }
//        swipeRefreshLayout.setRefreshing(false);

        oldItems.clear();
        for (Object o : collection) {
            oldItems.add(o);
            adapter.notifyItemChanged(oldItems.size()-1);
        }

    }

    protected void onLoadMoreSuccess(List<?> collection) {
//        if (null != collection && collection.size() < 10) {
//            adapter1.setLoadCompleted(true);
//        }
//        swipeRefreshLayout.setRefreshing(false);
//
//
//        for (Object o : collection) {
//            adapter1.addDataItem(o);
////            adapter1.notifyDataSetChanged();
//        }

        isLoading = true;
        isLoadMore = false;
//        oldItems.add(collection);
//        if (null != collection && collection.size() < 10) {
//            mRecyclerView.loadMoreComplete(oldItems, true);
//        } else {
//            mRecyclerView.loadMoreComplete(oldItems, false);
//        }

        if (null != collection && collection.size() < 10) {
//            mRecyclerView.loadMoreComplete(oldItems, true);
            mRecyclerView.setLoadingMoreEnabled(true);
        } else {
            mRecyclerView.setLoadingMoreEnabled(false);
//            mRecyclerView.loadMoreComplete(oldItems, false);
        }

        for (Object o : collection) {
            oldItems.add(o);
            adapter.notifyItemChanged(oldItems.size()-1);
        }
//        if (null != collection && collection.size() < 10){
//            mRecyclerView.setLoadingMoreEnabled(true);
//        }else{
//            mRecyclerView.setLoadingMoreEnabled(false);
//        }
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
        layoutManager = new LinearLayoutManager(getContext());
        return layoutManager;
    }

    /**
     * 获取更多网络数据t
     */
    protected void getLoadMoreData() {
        getRemoteData();
    }

}
