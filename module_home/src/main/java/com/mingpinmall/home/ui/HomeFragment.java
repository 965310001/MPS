package com.mingpinmall.home.ui;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 */
public class HomeFragment extends AbsLifecycleFragment<FragmentHomeBinding, HomeViewModel> {

    private final String TAG = "首页";

    private boolean darkMode = false;

    private HomeListAdapter homeListAdapter;
    private GridLayoutManager gridLayoutManager;
    //列表滑动时用到的变量
    private float scrollY;
    private float firstViewHeight;
    private float alpha;
    private int position;

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
        gridLayoutManager = new GridLayoutManager(activity, 4);
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        homeListAdapter = new HomeListAdapter();
        homeListAdapter.bindToRecyclerView(binding.recyclerView);
        homeListAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                switch (homeListAdapter.getData().get(position).getItemType()) {
                    case 3://布局c
                        return 2;
                    case 6://导航
                        return 1;
                    case 10://商品
                        return 2;
                    case 12://团购
                        return 2;
                    default:
                        return 4;
                }
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
        /**
         * 列表上点击事件
         */
        setItemCLickListener();
        /**
         * 除列表外，其他按钮点击事件
         */
        setClickListener();
        //列表滑动监听
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                //当前条目索引
                position = gridLayoutManager.findFirstVisibleItemPosition();
                if (position == 0) {
                    View firstView = gridLayoutManager.findViewByPosition(position);
                    scrollY = Math.abs(firstView.getTop());
                    firstViewHeight = firstView.getHeight();
                    alpha = scrollY / firstViewHeight;
                    if (alpha > 0) {
                        binding.clTitleBar.setVisibility(View.VISIBLE);
                        binding.clTitleBar.setAlpha(alpha);
                        binding.fab2top.show();
                        darkMode = true;
                        setDarkMode(true);
                    } else {
                        binding.clTitleBar.setVisibility(View.GONE);
                        binding.fab2top.hide();
                        darkMode = false;
                        setDarkMode(false);
                    }
                }
            }
        });
    }

    /**
     * 除列表外，其他按钮点击事件
     */
    private void setClickListener() {
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
                ActivityToActivity.toActivity(ARouterConfig.home.SEARCHACTIVITY);
            }
        });
        binding.llMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击消息
                ActivityToActivity.toActivity(ARouterConfig.Me.MESSAGEACTIVITY);
            }
        });
        binding.llQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击扫一扫
                IntentIntegrator.forSupportFragment(HomeFragment.this).setCaptureActivity(ScanQRCodeActivity.class).initiateScan();
            }
        });
    }

    /**
     * 列表上点击事件
     */
    private void setItemCLickListener() {
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
                    ActivityToActivity.toActivity(
                            ARouterConfig.home.SHOPPINGDETAILSACTIVITY,
                            "id",
                            goods1Bean.getItem().get(index).getGoods_id()
                    );
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
                    ActivityToActivity.toActivity(ARouterConfig.Me.MESSAGEACTIVITY);
                    return;
                } else if (id == R.id.sv_banner_search) {
                    //点击搜索
                    ActivityToActivity.toActivity(ARouterConfig.home.SEARCHACTIVITY);
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
                        navigationRouter(datasBean);
                        break;
                    case 10:
                        //商品列表
                        HomeItemBean.DatasBean.GoodsBean.ItemBean goodsBean = datasBean.getGoodsItemBean();
                        ActivityToActivity.toActivity(
                                ARouterConfig.home.SHOPPINGDETAILSACTIVITY,
                                "id",
                                goodsBean.getGoods_id()
                        );
                        break;
                    case 12:
                        //团购
                        HomeItemBean.DatasBean.Goods2Bean.Goods2BeanItem goods2Bean = datasBean.getGoods2ItemBean();
                        ActivityToActivity.toActivity(
                                ARouterConfig.home.SHOPPINGDETAILSACTIVITY,
                                "id",
                                goods2Bean.getGoods_id()
                        );
                        break;
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
    private void navigationRouter(HomeItemBean.DatasBean datasBean) {
        switch (datasBean.getData()) {
            case "tmpl/mall.html":
                //店铺街
                ActivityToActivity.toActivity(ARouterConfig.home.SHOPSTREETACTIVITY);
                break;
            case "tmpl/member/member_asset.html":
                //资产管理
                ActivityToActivity.toActivity(ARouterConfig.Me.PROPERTYACTIVITY);
                break;
            case "tmpl/member/views_list.html":
                //我的足迹
                ActivityToActivity.toActivity(ARouterConfig.Me.FOOTPRINTACTIVITY);
                break;
            case "tmpl/member/member_invite.html":
                //我的名师  分销管理 DISRTIBUTIONACTIVITY
                ActivityToActivity.toActivity(ARouterConfig.Me.DISRTIBUTIONACTIVITY);
                break;
            case "tmpl/product_first_categroy.html":
                //分类 跳转底部导航
                LiveBus.getDefault().postEvent("Main", "tab", 1);
                break;
            case "tmpl/cart_list.html":
                //购物车 跳转底部导航
                LiveBus.getDefault().postEvent("Main", "tab", 3);
                break;
            case "tmpl/member/member.html":
                //我的 跳转底部导航
                LiveBus.getDefault().postEvent("Main", "tab", 4);
                break;
            case "tmpl/member/signin.html":
                //签到
                ToastUtils.showShort("签到");
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
        registerObserver("HOME_DATA_JSON", Object.class)
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable final Object result) {
                        if (result instanceof HomeItemBean) {
                            HomeItemBean data = (HomeItemBean) result;
                            if (data.getCode() == 200) {
                                binding.refreshLayout.finishRefresh();
                                homeListAdapter.setNewData(formatDatas(data.getDatas()));
                            } else {
                                binding.refreshLayout.finishRefresh(false);
                            }
                        } else {
                            binding.refreshLayout.finishRefresh(false);
                            ToastUtils.showShort(result.toString());
                        }
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
                datasBean.setItemType(22);
                datasBean.setLabel(datasBean.getGoods().getTitle());
                datasBean.setSubLabel("小编向您推荐以下商品");
                listData.add(datasBean);
                HomeItemBean.DatasBean bean0;
                for (HomeItemBean.DatasBean.GoodsBean.ItemBean goodsBean : datasBean.getGoods().getItem()) {
                    bean0 = new HomeItemBean.DatasBean();
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
                datasBean.setLabel(datasBean.getGoods2().getTitle().equals("") ? "团购商品" : datasBean.getGoods2().getTitle());
                datasBean.setSubLabel("精品抢购 有你所选");
                listData.add(datasBean);
                //添加内容
                HomeItemBean.DatasBean bean2;
                for (HomeItemBean.DatasBean.Goods2Bean.Goods2BeanItem good2Bean : datasBean.getGoods2().getItem()) {
                    bean2 = new HomeItemBean.DatasBean();
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
                HomeItemBean.DatasBean bean3;
                for (HomeItemBean.DatasBean.Home3Bean.ItemBean home3Bean : datasBean.getHome3().getItem()) {
                    bean3 = new HomeItemBean.DatasBean();
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
                HomeItemBean.DatasBean bean6;
                for (HomeItemBean.DatasBean.Home6Bean.ItemBeanX home6Bean : datasBean.getHome6().getItem()) {
                    bean6 = new HomeItemBean.DatasBean();
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
