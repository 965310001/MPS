package com.goldze.common.dmvvm.base.mvvm.base;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goldze.common.dmvvm.R;
import com.goldze.common.dmvvm.base.core.banner.BannerList;
import com.goldze.common.dmvvm.databinding.FragmentListBinding;
import com.goldze.common.dmvvm.utils.Utils;
import com.socks.library.KLog;
import com.trecyclerview.TRecyclerView;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.adapter.ItemData;
import com.trecyclerview.listener.OnRefreshListener;
import com.trecyclerview.listener.OnScrollStateListener;

import java.util.Collection;
import java.util.List;


import static android.view.View.VISIBLE;

/**
 * @author GuoFeng
 * @date : 2019/1/26 13:56
 * @description:
 */
public abstract class BaseListActivity extends BaseActivity<FragmentListBinding> implements OnRefreshListener {

    protected TRecyclerView mRecyclerView;

//    protected RelativeLayout mTitleBar;
//    protected TextView mTitle;
//    protected ImageView ivBack;

    protected FloatingActionButton floatBtn;

    protected RecyclerView.LayoutManager layoutManager;

    protected DelegateAdapter adapter;

    protected long page = 1;

    protected String lastId = null;

    protected boolean isLoadMore = true;

    protected boolean isLoading = true;

    protected boolean isRefresh = false;

    protected ItemData oldItems;

    protected ItemData newItems;

    int lastItemPosition;

    //是否向上滑动
    private boolean isSlidingUpward;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_list;
    }


    @Override
    protected void initViews(Bundle savedInstanceState) {
        mRecyclerView = findViewById(R.id.recycler_view);
//        mTitleBar = findViewById(R.id.rl_title_bar);
//        mTitle = findViewById(R.id.tv_title);
//        ivBack = findViewById(R.id.iv_back);
//        floatBtn = findViewById(R.id.float_btn);
        floatBtn = binding.floatBtn;

//        ivBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        oldItems = new ItemData();
        newItems = new ItemData();
        adapter = createAdapter();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(createLayoutManager());
        mRecyclerView.addOnRefreshListener(this);
        mRecyclerView.addOnScrollStateListener(new OnScrollStateListener() {
            @Override
            public void onScrollStateChanged(int state1) {
                if (state1 == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(Utils.getApplication()).resumeRequests();
                } else {
                    Glide.with(Utils.getApplication()).pauseRequests();
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
                    floatBtn.show();//滑动到底部
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
            }
        });
        floatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.smoothScrollToPosition(0);
            }
        });

        if (isItemDecoration()) {
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration
                    .VERTICAL));
        }
        onRefresh();
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
        getLoadMoreData();
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
        if (isLoadMore) {
            onLoadMoreSuccess(collection);
        } else {
            onRefreshSuccess(collection);
        }
    }

    protected void setBannerData(BannerList headAdList) {
        newItems.add(headAdList);
    }

    protected void onRefreshSuccess(Collection<?> collection) {
        newItems.clear();
        newItems.addAll(collection);
        oldItems.clear();
        oldItems.addAll(newItems);
        if (collection.size() < 20) {
            mRecyclerView.refreshComplete(oldItems, true);
        } else {
            mRecyclerView.refreshComplete(oldItems, false);
        }
        isRefresh = false;
    }

    protected void onLoadMoreSuccess(List<?> collection) {
        isLoading = true;
        isLoadMore = false;
        oldItems.addAll(collection);
        if (collection.size() < 20) {
            mRecyclerView.loadMoreComplete(collection, true);
        } else {
            mRecyclerView.loadMoreComplete(collection, false);
        }
    }

    protected abstract DelegateAdapter createAdapter();

    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(this);
    }

//    protected void setTitle(String titleName) {
//        mTitleBar.setVisibility(VISIBLE);
//        mTitle.setText(titleName);
//    }
//
//    protected void showBack() {
//        ivBack.setVisibility(VISIBLE);
//    }


    /**
     * 获取更多网络数据t
     */
    protected void getLoadMoreData() {
    }

    /**
     * 获取网络数据
     */
    protected void getRemoteData() {
    }

    //    @OnClick({R2.id.iv_back})
//    public void onClick(View v) {
//        finish();
//    }
}
