package com.mingpinmall.classz;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.listener.OnItemClickListener;
import com.freelib.multiitem.listener.OnLoadMoreListener;
import com.goldze.common.dmvvm.R;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.base.mvvm.AbsViewModel;
import com.goldze.common.dmvvm.databinding.FragmentList2Binding;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.socks.library.KLog;
import com.trecyclerview.listener.OnRefreshListener;

import java.util.List;

/**
 * @author GuoFeng
 * @date :2019/1/17 17:06
 * @description:
 */
public abstract class BaseListFragment2<T extends AbsViewModel> extends AbsLifecycleFragment<FragmentList2Binding, T>
        implements OnRefreshListener {

    protected BaseItemAdapter adapter;

    protected RecyclerView mRecyclerView;

    protected SwipeRefreshLayout swipeRefreshLayout;

    protected FloatingActionButton floatBtn;

    protected RecyclerView.LayoutManager layoutManager;

    protected String lastId = null;

    protected long page = 1;

    protected boolean isLoadMore = true;

    protected boolean isLoading = true;

    protected boolean isRefresh = false;

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
        mRecyclerView = binding.recyclerView;
        swipeRefreshLayout = binding.swipeRefreshLayout;
        floatBtn = binding.floatBtn;

        adapter = createAdapter();
        register(adapter);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(createLayoutManager());
        if (isItemDecoration()) {
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL));
        }
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int state) {
                if (state == RecyclerView.SCROLL_STATE_IDLE) {
                    if (activity != null) {
                        KLog.i("resumeRequests ");
//                        Glide.with(activity).resumeRequests();
                    }
                } else {
                    if (activity != null) {
                        KLog.i("pauseRequests ");
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
        adapter.enableLoadMore(new LoadMoreHolderManager(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                ++page;
                isLoadMore = true;
                getLoadMoreData();
            }
        }));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(true);
                adapter.clearData();
                adapter.notifyDataSetChanged();

                page = 1;
                lastId = null;
                isRefresh = true;
                isLoadMore = false;
                getRemoteData();
            }
        });

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder baseViewHolder) {
                ToastUtils.showLong(String.format("你点击了第%s位置的数据：%s",
                        baseViewHolder.getItemPosition(), baseViewHolder.getItemData()));
            }
        });
    }

    protected abstract void register(BaseItemAdapter adapter);

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
        swipeRefreshLayout.setRefreshing(false);
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

    protected void addHeadItem(Object headItem) {
        adapter.addHeadItem(headItem);
    }

    protected void addFootItem(Object footItem) {
        adapter.addFootItem(footItem);
    }

    protected void onRefreshSuccess(List<?> collection) {
        if (null != collection && collection.size() < 10) {
            adapter.setLoadCompleted(false);
        }
        adapter.addDataItems(collection);
        adapter.notifyDataSetChanged();


    }

    protected void onLoadMoreSuccess(List<?> collection) {
        isLoading = true;
        isLoadMore = false;
        if (null != collection && collection.size() < 10) {
            adapter.setLoadCompleted(false);
        }
        adapter.addDataItems(collection);
        adapter.notifyDataSetChanged();

//        for (Object o : collection) {
//            adapter.addHeadItem(o);
//        }
    }

    /**
     * adapter
     *
     * @return BaseItemAdapter
     */
    protected BaseItemAdapter createAdapter() {
        return new BaseItemAdapter();
    }

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