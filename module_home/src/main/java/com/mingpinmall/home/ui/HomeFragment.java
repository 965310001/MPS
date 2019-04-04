package com.mingpinmall.home.ui;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.google.gson.Gson;
import com.mingpinmall.home.R;
import com.mingpinmall.home.databinding.FragmentHomeBinding;
import com.mingpinmall.home.ui.adapter.HomeListAdapter;
import com.mingpinmall.home.ui.api.HomeViewModel;
import com.mingpinmall.home.ui.bean.HomeItemBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 */
public class HomeFragment extends AbsLifecycleFragment<FragmentHomeBinding, HomeViewModel> {

    private final String TAG = "首页";

    HomeListAdapter homeListAdapter;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        homeListAdapter = new HomeListAdapter();
        binding.setAdapter(homeListAdapter);
        //不使用上拉加载更多
        binding.refreshLayout.setEnableLoadMore(false);
        //下拉刷新监听
        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getHomeDataList();
            }
        });
        homeListAdapter.setBannerClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //轮播图点击事件
            }
        });
        homeListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //子Item点击事件
            }
        });
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                //当前条目索引
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int position = layoutManager.findFirstVisibleItemPosition();
                if (position == 0) {
                    View firstView = layoutManager.findViewByPosition(position);
                    float scrollY = Math.abs(firstView.getTop());
                    float firstViewHeight = firstView.getHeight();
                    float alpha = scrollY / firstViewHeight;
                    if (alpha > 0) {
                        binding.clTitleBar.setVisibility(View.VISIBLE);
                        binding.clTitleBar.setAlpha(alpha);
                    } else {
                        binding.clTitleBar.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    protected void lazyLoad() {
        Log.i(TAG, "lazyLoad: 第一次加载数据");
        mViewModel.getHomeDataList();
    }

    @Override
    protected void dataObserver() {
        LiveBus.getDefault().subscribe("HOME_DATA_JSON", HomeItemBean.class)
                .observeForever(new Observer<HomeItemBean>() {
                    @Override
                    public void onChanged(@Nullable HomeItemBean data) {
                        homeListAdapter.setNewData(formatJsonData(data));
                    }
                });
        LiveBus.getDefault().subscribe("Err_HOME_DATA_JSON", String.class)
                .observeForever(new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String msg) {
                        binding.refreshLayout.finishRefresh(false);
                        ToastUtils.showShort(msg);
                    }
                });
    }

    /**
     * @date 创建时间： 2019/4/3
     * @author 创建人：小斌
     * @Description 描述：处理数据
     * @Version
     **/
    private List<HomeItemBean.DatasBean> formatJsonData(HomeItemBean data) {
        List<HomeItemBean.DatasBean> datasBeans = data.getDatas();
        for (HomeItemBean.DatasBean bean : datasBeans) {
            if (bean.getAdv_list() != null) {
                //轮播图
                bean.setItemType(0);
            } else if (bean.getGoods() != null) {
                //未知
                bean.setItemType(10);
            } else if (bean.getGoods1() != null) {
                //限购
                bean.setItemType(11);
            } else if (bean.getGoods2() != null) {
                //团购
                bean.setItemType(12);
            } else if (bean.getHome1() != null) {
                //板块A
                bean.setItemType(1);
            } else if (bean.getHome2() != null) {
                //超值购  品牌街  有利可图
                bean.setItemType(2);
            } else if (bean.getHome3() != null) {
                //布局c
                bean.setItemType(3);
            } else if (bean.getHome4() != null) {
                //热门活动  特色市场
                bean.setItemType(4);
            } else if (bean.getHome5() != null) {
                //手机通讯  自营超市
                bean.setItemType(5);
            } else if (bean.getHome6() != null) {
                //导航
                bean.setItemType(6);
            }
        }
        return datasBeans;
    }

    @Override
    protected Object getStateEventKey() {
        return "HOME_PAGE";
    }

}
