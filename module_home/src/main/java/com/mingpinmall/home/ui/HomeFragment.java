package com.mingpinmall.home.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.mingpinmall.home.R;
import com.mingpinmall.home.databinding.FragmentHomeBinding;
import com.goldze.common.dmvvm.activity.qrcode.ScanQrCodeActivity;
import com.mingpinmall.home.ui.adapter.HomeListAdapter;
import com.mingpinmall.home.ui.api.HomeViewModel;
import com.mingpinmall.home.ui.bean.HomeItemBean;
import com.mingpinmall.home.ui.constants.Constants;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页
 * @author 小斌
 * @date 2019/4/3
 */
public class HomeFragment extends AbsLifecycleFragment<FragmentHomeBinding, HomeViewModel> {

    private final String TAG = "首页";

    private boolean darkMode = false;

    private HomeListAdapter homeListAdapter;
    private GridLayoutManager gridLayoutManager;

    /**
     * 列表滑动时用到的变量
     */
    private float scrollY;
    private float firstViewHeight;
    private int alpha;
    private int position;
//    private boolean sChange;

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
        homeListAdapter.setSpanSizeLookup((gridLayoutManager, position) -> {
            switch (homeListAdapter.getData().get(position).getItemType()) {
                case 3:
                    //布局c
                    return 2;
                case 6:
                    //导航
                    return 1;
                case 10:
                    //商品
                    return 2;
                case 12:
                    //团购
                    return 2;
                default:
                    return 4;
            }
        });

        //不使用上拉加载更多
        binding.refreshLayout.setEnableLoadMore(false);
        //下拉刷新监听
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> mViewModel.getHomeDataList());
        //列表上点击事件
        setItemCLickListener();
        //除列表外，其他按钮点击事件
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
                    alpha = (int) (scrollY / firstViewHeight * 200);
                    if (alpha > 0) {
//                        if (sChange) {
//                            sChange = false;
//                            binding.ivScan.setImageResource(R.drawable.ic_scans_new_black);
//                            binding.ivMsg.setImageResource(R.drawable.ic_msg_new_black);
//                        }
                        binding.lineTop.setBackgroundColor(Color.argb(alpha, 232, 232, 232));
                        binding.clTitleBar.setBackgroundColor(Color.argb(alpha, 255, 255, 255));
                        binding.fab2top.show();
                        darkMode = true;
                        setDarkMode(true);
                    } else {
//                        sChange = true;
//                        binding.ivScan.setImageResource(R.drawable.ic_scans_new_white);
//                        binding.ivMsg.setImageResource(R.drawable.ic_msg_new_white);
                        binding.lineTop.setBackgroundColor(Color.argb(0, 232, 232, 232));
                        binding.clTitleBar.setBackgroundColor(Color.argb(0, 255, 255, 255));
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
        binding.fab2top.setOnClickListener(v -> {
            //返回顶部
            binding.recyclerView.smoothScrollToPosition(0);
        });
        binding.svSearch.setOnClickListener(v -> {
            //点击搜索框
            ActivityToActivity.toActivity(ARouterConfig.home.SEARCHACTIVITY);
        });
        binding.llMsg.setOnClickListener(v -> {
            //点击消息
            ActivityToActivity.toActivity(ARouterConfig.Me.MESSAGEACTIVITY);
        });
        binding.llQRCode.setOnClickListener(v -> {
            //点击扫一扫
            IntentIntegrator.forSupportFragment(HomeFragment.this).setCaptureActivity(ScanQrCodeActivity.class).initiateScan();
        });
    }

    /**
     * 列表上点击事件
     */
    private void setItemCLickListener() {
        //列表上的轮播图
        homeListAdapter.setBannerClickListener((position, index) -> {
            //轮播图点击事件
            HomeItemBean.DatasBean bannerDatas = homeListAdapter.getItem(position);
            if (bannerDatas.getAdv_list() != null) {
                //顶部单张图片式轮播图
                HomeItemBean.DatasBean.AdvListBean.ItemBean advListBean = bannerDatas.getAdv_list().getItem().get(index);
                navigationRouter(advListBean.getData(), advListBean.getType());
            } else {
                //其他轮播图
                HomeItemBean.DatasBean.Goods1Bean goods1Bean = bannerDatas.getGoods1();
                ActivityToActivity.toActivity(
                        ARouterConfig.home.SHOPPINGDETAILSACTIVITY,
                        "id",
                        goods1Bean.getItem().get(index).getGoods_id()
                );
            }
        });
        //item内部子View点击
        homeListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            //item上的View点击事件
            // 2 4 5
            HomeItemBean.DatasBean datasBean = homeListAdapter.getItem(position);
            routerBus(datasBean, datasBean.getItemType(), view.getId());
        });
        //item点击
        homeListAdapter.setOnItemClickListener((adapter, view, position) -> {
            //item点击事件
            HomeItemBean.DatasBean datasBean = homeListAdapter.getItem(position);
            routerBus(datasBean, datasBean.getItemType(), view.getId());
        });
    }

    /**
     * 动作路由
     * @param datasBean
     * @param type
     * @param id
     */
    private void routerBus(HomeItemBean.DatasBean datasBean, int type, int id) {
        switch (type) {
            case 1:
                //布局：带标题单张图片
                HomeItemBean.DatasBean.Home1Bean datasBean0 = datasBean.getHome1();
                navigationRouter(datasBean0.getData(), datasBean0.getType());
                break;
            case 3:
                //布局：无标题单张图片
                navigationRouter(datasBean.getData(), datasBean.getType());
                break;
            case 5:
                //布局：左矩形  右上矩形  右下俩矩形  左1右3
                HomeItemBean.DatasBean.Home5Bean home5Bean = datasBean.getHome5();
                if (id == R.id.iv_square) {
                    navigationRouter(home5Bean.getSquare_data(), home5Bean.getSquare_type());
                } else if (id == R.id.iv_rectangle1) {
                    navigationRouter(home5Bean.getRectangle1_data(), home5Bean.getRectangle1_type());
                } else if (id == R.id.iv_rectangle2) {
                    navigationRouter(home5Bean.getRectangle2_data(), home5Bean.getRectangle2_type());
                } else if (id == R.id.iv_rectangle3) {
                    navigationRouter(home5Bean.getRectangle3_data(), home5Bean.getRectangle3_type());
                }
                break;
            case 4:
                //布局：左上矩形  左下矩形  右矩形  左2右1
                HomeItemBean.DatasBean.Home4Bean home4Bean = datasBean.getHome4();
                if (id == R.id.iv_square) {
                    navigationRouter(home4Bean.getSquare_data(), home4Bean.getSquare_type());
                } else if (id == R.id.iv_rectangle1) {
                    navigationRouter(home4Bean.getRectangle1_data(), home4Bean.getRectangle1_type());
                } else if (id == R.id.iv_rectangle2) {
                    navigationRouter(home4Bean.getRectangle2_data(), home4Bean.getRectangle2_type());
                }
                break;
            case 2:
                //布局：左矩形  右上矩形  右下矩形  左1右2
                HomeItemBean.DatasBean.Home2Bean home2Bean = datasBean.getHome2();
                if (id == R.id.iv_square) {
                    navigationRouter(home2Bean.getSquare_data(), home2Bean.getSquare_type());
                } else if (id == R.id.iv_rectangle1) {
                    navigationRouter(home2Bean.getRectangle1_data(), home2Bean.getRectangle1_type());
                } else if (id == R.id.iv_rectangle2) {
                    navigationRouter(home2Bean.getRectangle2_data(), home2Bean.getRectangle2_type());
                }
                break;
            case 6:
                //布局：无标题单张图片导航
                navigationRouter(datasBean.getData(), datasBean.getType());
                break;
            case 10:
                //布局：商品
                HomeItemBean.DatasBean.GoodsBean.ItemBean goodsBean = datasBean.getGoodsItemBean();
                navigationRouter(goodsBean.getGoods_id(), "goods");
                break;
            case 12:
                //布局：商品2
                HomeItemBean.DatasBean.Goods2Bean.Goods2BeanItem goods2Bean = datasBean.getGoods2ItemBean();
                navigationRouter(goods2Bean.getGoods_id(), "goods");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onVisible() {
        setDarkMode(darkMode);
    }

    /**
     * 描述：列表上的导航功能
     **/
    private void navigationRouter(String data, String type) {
        switch (type) {
            case "url":
                //跳转到指定页面
                route2Url(data);
                break;
            case "keyword":
                //通过关键字搜索产品
                ActivityToActivity.toActivity(ARouterConfig.classify.PRODUCTSACTIVITY, "keyword", data);
                break;
            case "special":
                //前往指定专题页面
                ActivityToActivity.toActivity(ARouterConfig.home.SPECIALACTIVITY, "id", data);
                break;
            case "goods":
                //前往产品详细页面
                ActivityToActivity.toActivity(ARouterConfig.home.SHOPPINGDETAILSACTIVITY, "id", data);
                break;
            default:
                break;
        }
    }

    /**
     * 跳转到指定页面
     *
     * @param url 地址
     */
    private void route2Url(String url) {
        if (url.contains("mall.html")) {
            //店铺街
            ActivityToActivity.toActivity(ARouterConfig.home.SHOPSTREETACTIVITY);
        } else if (url.contains("member_asset.html")) {
            //资产管理
            ActivityToActivity.toActivity(ARouterConfig.Me.PROPERTYACTIVITY);
        } else if (url.contains("views_list.html")) {
            //我的足迹
            ActivityToActivity.toActivity(ARouterConfig.Me.FOOTPRINTACTIVITY);
        } else if (url.contains("member_invite.html")) {
            //分销管理 DISRTIBUTIONACTIVITY
            ActivityToActivity.toActivity(ARouterConfig.Me.DISRTIBUTIONACTIVITY);
        } else if (url.contains("product_first_categroy.html")) {
            //分类 跳转底部导航
            LiveBus.getDefault().postEvent("Main", "tab", 1);
        } else if (url.contains("famous_teacher.html")) {
            //名师 跳转底部导航
            LiveBus.getDefault().postEvent("Main", "tab", 2);
        } else if (url.contains("cart_list.html")) {
            //购物车 跳转底部导航
            LiveBus.getDefault().postEvent("Main", "tab", 3);
        } else if (url.contains("member.html")) {
            //我的 跳转底部导航
            LiveBus.getDefault().postEvent("Main", "tab", 4);
        } else if (url.contains("signin.html")) {
            //签到
            ToastUtils.showShort("签到");
        } else if (url.contains("product_list.html?b_id")) {
            //跳转到搜索指定id的商品
            String id = url.split("b_id=")[1];
            Map<String, Object> params = new HashMap<>(2);
            params.put("gcId", id);
            params.put("type", 0);
            ActivityToActivity.toActivity(ARouterConfig.classify.PRODUCTSACTIVITY, params);
        }
    }

    @Override
    protected void lazyLoad() {
        Log.i(TAG, "lazyLoad: 第一次加载数据");
        mViewModel.getHomeDataList();
    }

    @Override
    protected void dataObserver() {
        registerObserver("banner_w_h", Float.class).observeForever(result -> {
            Log.d(TAG, "banner_w_h: 设置高度咯~");
            if (homeListAdapter.getItemCount() == 0) {
                return;
            }
            BaseViewHolder baseViewHolder = (BaseViewHolder) binding.recyclerView.findViewHolderForAdapterPosition(0);
            View itemView = baseViewHolder.itemView;
            ViewGroup.LayoutParams params =  itemView.getLayoutParams();
            int maxHei = (int) (ScreenUtil.getScreenWidth(activity) / result);
            if (params.height < maxHei) {
                params.height = (int) (ScreenUtil.getScreenWidth(activity) / result);
                itemView.setLayoutParams(params);
            }
        });
        registerObserver(Constants.HOME_DATA_JSON, Object.class).observeForever(result -> {
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
                if (!datasBean.getGoods().getTitle().isEmpty()) {
                    datasBean.setItemType(22);
                    datasBean.setLabel(datasBean.getGoods().getTitle());
                    datasBean.setSubLabel("小编向您推荐以下商品");
                    listData.add(datasBean);
                }
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
                if (!datasBean.getGoods2().getTitle().isEmpty()) {
                    datasBean.setItemType(22);
                    datasBean.setLabel(datasBean.getGoods2().getTitle());
                    datasBean.setSubLabel("精品抢购 有你所选");
                    listData.add(datasBean);
                }
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
