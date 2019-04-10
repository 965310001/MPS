package com.mingpinmall.home.ui;


import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
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
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.StatusBarUtils;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.mingpinmall.home.R;
import com.mingpinmall.home.databinding.FragmentHomeBinding;
import com.mingpinmall.home.ui.activity.qrCode.ScanQRCodeActivity;
import com.mingpinmall.home.ui.adapter.HomeListAdapter;
import com.mingpinmall.home.ui.adapter.ListBannerItemClickListener;
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

    private boolean darkMode = false;

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
        setTitlePadding(binding.clTitleBar);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 4);

        binding.recyclerView.setLayoutManager(gridLayoutManager);
        homeListAdapter = new HomeListAdapter();
        homeListAdapter.bindToRecyclerView(binding.recyclerView);
        homeListAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                int span;
                switch (homeListAdapter.getData().get(position).getItemType()) {
                    case 3://布局c
                        span = 2;
                        break;
                    case 6://导航
                        span = 1;
                        break;
                    case 10://商品
                        span = 2;
                        break;
                    case 12://团购
                        span = 2;
                        break;
                    default:
                        span = 4;
                        break;
                }
                return span;
            }
        });

        binding.fab2top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回顶部
                binding.recyclerView.smoothScrollToPosition(0);
            }
        });
        binding.svSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击搜索框
            }
        });
        binding.llMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击消息
                ActivityToActivity.toActivity(ARouterConfig.MESSAGEACTIVITY);
            }
        });
        binding.llQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击扫一扫
                IntentIntegrator.forSupportFragment(HomeFragment.this).setCaptureActivity(ScanQRCodeActivity.class).initiateScan();
            }
        });
        //不使用上拉加载更多
        binding.refreshLayout.setEnableLoadMore(false);
        //下拉刷新监听
        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getHomeDataList();
            }
        });
        //列表上的轮播图
        homeListAdapter.setBannerClickListener(new ListBannerItemClickListener() {
            @Override
            public void onItemClick(int position, int index) {
                //轮播图点击事件
                HomeItemBean.DatasBean bannerDatas = homeListAdapter.getItem(position);
                if (bannerDatas.getAdv_list() != null) {
                    //顶部单张图片式轮播图
                    HomeItemBean.DatasBean.AdvListBean advListBean = bannerDatas.getAdv_list();
                    ToastUtils.showShort("事件: " + advListBean.getItem().get(index).getData());
                } else {
                    //其他轮播图
                    HomeItemBean.DatasBean.Goods1Bean goods1Bean = bannerDatas.getGoods1();
                    ToastUtils.showShort("事件: " + goods1Bean.getItem().get(index).getGoods_name());
                }
            }
        });
        //item内部子View点击
        homeListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //item上的View点击事件
                if (!SharePreferenceUtil.isLogin()) {
                    ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
                    return;
                }
                int id = view.getId();
                if (id == R.id.ll_banner_msg) {
                    //点击消息
                    ActivityToActivity.toActivity(ARouterConfig.MESSAGEACTIVITY);
                    return;
                } else if (id == R.id.sv_banner_search) {
                    //点击搜索
                    return;
                }
                // 2 4 5
                HomeItemBean.DatasBean datasBean = homeListAdapter.getItem(position);
                int type = datasBean.getItemType();
                switch (type) {
                    case 5:
                        //手机通讯  自营超市
                        HomeItemBean.DatasBean.Home5Bean home5Bean = datasBean.getHome5();
                        if (id == R.id.iv_square) {
                            ToastUtils.showShort("事件: " + home5Bean.getSquare_data());
                        } else if (id == R.id.iv_rectangle1) {
                            ToastUtils.showShort("事件: " + home5Bean.getRectangle1_data());
                        } else if (id == R.id.iv_rectangle2) {
                            ToastUtils.showShort("事件: " + home5Bean.getRectangle2_data());
                        } else if (id == R.id.iv_rectangle3) {
                            ToastUtils.showShort("事件: " + home5Bean.getRectangle3_data());
                        }
                        break;
                    case 4:
                        //热门活动  特色市场
                        HomeItemBean.DatasBean.Home4Bean home4Bean = datasBean.getHome4();
                        if (id == R.id.iv_square) {
                            ToastUtils.showShort("事件: " + home4Bean.getSquare_data());
                        } else if (id == R.id.iv_rectangle1) {
                            ToastUtils.showShort("事件: " + home4Bean.getRectangle1_data());
                        } else if (id == R.id.iv_rectangle2) {
                            ToastUtils.showShort("事件: " + home4Bean.getRectangle2_data());
                        }
                        break;
                    case 2:
                        //超值购  品牌街  有利可图
                        HomeItemBean.DatasBean.Home2Bean home2Bean = datasBean.getHome2();
                        if (id == R.id.iv_square) {
                            ToastUtils.showShort("事件: " + home2Bean.getSquare_data());
                        } else if (id == R.id.iv_rectangle1) {
                            ToastUtils.showShort("事件: " + home2Bean.getRectangle1_data());
                        } else if (id == R.id.iv_rectangle2) {
                            ToastUtils.showShort("事件: " + home2Bean.getRectangle2_data());
                        }
                        break;
                }

            }
        });
        //item点击
        homeListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //item点击事件
                if (!SharePreferenceUtil.isLogin()) {
                    ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
                    return;
                }
                HomeItemBean.DatasBean datasBean = homeListAdapter.getItem(position);
                int type = datasBean.getItemType();
                switch (type) {
                    case 1:
                        //模型板块布局A
                        HomeItemBean.DatasBean.Home1Bean datasBean0 = datasBean.getHome1();
                        ToastUtils.showShort("事件: " + datasBean0.getData());
                        break;
                    case 3:
                        //布局c ITEM
                        ToastUtils.showShort("事件: " + datasBean.getData());
                        break;
                    case 6:
                        //导航
                        navARouter(datasBean);
                        break;
                    case 10:
                        //商品列表
                        HomeItemBean.DatasBean.GoodsBean.ItemBean goodsBean = datasBean.getGoodsItemBean();
                        ToastUtils.showShort("事件: " + goodsBean.getGoods_name());
                        break;
                    case 12:
                        //团购
                        HomeItemBean.DatasBean.Goods2Bean.Goods2BeanItem goods2Bean = datasBean.getGoods2ItemBean();
                        ToastUtils.showShort("事件: " + goods2Bean.getGoods_name());
                        break;
                }
            }
        });
        //列表滑动监听
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @SuppressLint("RestrictedApi")
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
                        binding.fab2top.setVisibility(View.VISIBLE);
                        binding.clTitleBar.setAlpha(alpha);
                        darkMode = true;
                        setDarkMode(darkMode);
                    } else {
                        binding.clTitleBar.setVisibility(View.GONE);
                        binding.fab2top.setVisibility(View.GONE);
                        darkMode = false;
                        setDarkMode(false);
                    }
                }
            }
        });
    }

    @Override
    protected void onVisible() {
        setDarkMode(darkMode);
    }

    /**
     * @date 创建时间： 2019/4/4
     * @author 创建人：小斌
     * @Description 描述：列表上的导航功能
     * @Version
     **/
    private void navARouter(HomeItemBean.DatasBean datasBean) {
        switch (datasBean.getData()) {
            case "tmpl/mall.html":
                //店铺街
                break;
            case "tmpl/member/member_asset.html":
                //资产管理
                ActivityToActivity.toActivity(ARouterConfig.PROPERTYACTIVITY);
                break;
            case "tmpl/member/views_list.html":
                //我的足迹
                ActivityToActivity.toActivity(ARouterConfig.FOOTPRINTACTIVITY);
                break;
            case "tmpl/member/member_invite.html":
                //我的名师  分销管理 DISRTIBUTIONACTIVITY
                ActivityToActivity.toActivity(ARouterConfig.DISRTIBUTIONACTIVITY);
                break;
        }
    }

    @Override
    protected void lazyLoad() {
        Log.i(TAG, "lazyLoad: 第一次加载数据");
        mViewModel.getHomeDataList();
    }

    @Override
    protected void dataObserver() {
        registerObserver("HOME_DATA_JSON", HomeItemBean.class)
                .observeForever(new Observer<HomeItemBean>() {
                    @Override
                    public void onChanged(@Nullable HomeItemBean data) {
                        if (data.getCode() == 200) {
                            binding.refreshLayout.finishRefresh();
                            homeListAdapter.setNewData(formatDatas(data.getDatas()));
                        } else {
                            binding.refreshLayout.finishRefresh(false);
                        }
                    }
                });
        registerObserver("Err_HOME_DATA_JSON", String.class)
                .observeForever(new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String msg) {
                        binding.refreshLayout.finishRefresh(false);
                        ToastUtils.showShort(msg);
                    }
                });
    }

    private List<HomeItemBean.DatasBean> formatDatas(List<HomeItemBean.DatasBean> datas) {
        List<HomeItemBean.DatasBean> listData = new ArrayList<>();
        for (HomeItemBean.DatasBean datasBean : datas) {
            if (datasBean.getAdv_list() != null) {
                //轮播图
                datasBean.setItemType(0);
                listData.add(datasBean);
            } else if (datasBean.getGoods() != null) {
                //商品
                for (HomeItemBean.DatasBean.GoodsBean.ItemBean goodsBean : datasBean.getGoods().getItem()) {
                    HomeItemBean.DatasBean bean0 = new HomeItemBean.DatasBean();
                    bean0.setItemType(10);
                    bean0.setGoodsItemBean(goodsBean);
                    listData.add(bean0);
                }
            } else if (datasBean.getGoods1() != null) {
                //限购
                datasBean.setItemType(11);
                listData.add(datasBean);
            } else if (datasBean.getGoods2() != null) {
                //团购
                //添加一个标题
                datasBean.setItemType(22);
                listData.add(datasBean);
                //添加内容
                for (HomeItemBean.DatasBean.Goods2Bean.Goods2BeanItem good2Bean : datasBean.getGoods2().getItem()) {
                    HomeItemBean.DatasBean bean2 = new HomeItemBean.DatasBean();
                    bean2.setItemType(12);
                    bean2.setGoods2ItemBean(good2Bean);
                    listData.add(bean2);
                }
            } else if (datasBean.getHome1() != null) {
                //板块A
                datasBean.setItemType(1);
                listData.add(datasBean);
            } else if (datasBean.getHome2() != null) {
                //超值购  品牌街  有利可图
                datasBean.setItemType(2);
                listData.add(datasBean);
            } else if (datasBean.getHome3() != null) {
                //布局c
                //添加一个标题条
                datasBean.setItemType(33);
                listData.add(datasBean);
                //添加内容
                for (HomeItemBean.DatasBean.Home3Bean.ItemBean home3Bean : datasBean.getHome3().getItem()) {
                    HomeItemBean.DatasBean bean3 = new HomeItemBean.DatasBean();
                    bean3.setItemType(3);
                    bean3.setData(home3Bean.getData());
                    bean3.setType(home3Bean.getType());
                    bean3.setImage(home3Bean.getImage());
                    listData.add(bean3);
                }
            } else if (datasBean.getHome4() != null) {
                //热门活动  特色市场
                datasBean.setItemType(4);
                listData.add(datasBean);
            } else if (datasBean.getHome5() != null) {
                //手机通讯  自营超市
                datasBean.setItemType(5);
                listData.add(datasBean);
            } else if (datasBean.getHome6() != null) {
                //导航
                for (HomeItemBean.DatasBean.Home6Bean.ItemBeanX home6Bean : datasBean.getHome6().getItem()) {
                    HomeItemBean.DatasBean bean6 = new HomeItemBean.DatasBean();
                    bean6.setItemType(6);
                    bean6.setData(home6Bean.getData());
                    bean6.setType(home6Bean.getType());
                    bean6.setImage(home6Bean.getImage());
                    listData.add(bean6);
                }
            }
        }
        return listData;
    }

    @Override
    protected Object getStateEventKey() {
        return "HOME_PAGE";
    }

}
