package com.mingpinmall.home.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.home.R;
import com.mingpinmall.home.databinding.ActivitySpecialBinding;
import com.mingpinmall.home.ui.adapter.HomeListAdapter;
import com.mingpinmall.home.ui.api.HomeViewModel;
import com.mingpinmall.home.ui.bean.HomeItemBean;
import com.mingpinmall.home.ui.bean.SpecialPageBean;
import com.mingpinmall.home.ui.constants.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：专题页面
 * *@author 小斌
 *
 * @date 2019/5/5
 **/
@Route(path = ARouterConfig.home.SPECIALACTIVITY)
public class SpecialActivity extends AbsLifecycleActivity<ActivitySpecialBinding, HomeViewModel> {

    @Autowired
    String id;

    private HomeListAdapter listAdapter;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);
        setTitle("");
        listAdapter = new HomeListAdapter();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 4);
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        listAdapter = new HomeListAdapter();
        listAdapter.bindToRecyclerView(binding.recyclerView);
        listAdapter.setSpanSizeLookup((gridLayoutManager1, position) -> {
            switch (listAdapter.getData().get(position).getItemType()) {
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

        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> initData());
        setListener();
    }

    private void setListener() {
        //列表上的轮播图
        listAdapter.setBannerClickListener((position, index) -> {
            //轮播图点击事件
            HomeItemBean.DatasBean bannerDatas = listAdapter.getItem(position);
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
        });
        //item内部子View点击
        listAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            //item上的View点击事件
            if (!SharePreferenceUtil.isLogin()) {
                ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
                return;
            }
            // 2 4 5
            HomeItemBean.DatasBean datasBean = listAdapter.getItem(position);
            routerBus(datasBean, datasBean.getItemType(), view.getId());
        });
        //item点击
        listAdapter.setOnItemClickListener((adapter, view, position) -> {
            //item点击事件
            if (!SharePreferenceUtil.isLogin()) {
                ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
                return;
            }
            HomeItemBean.DatasBean datasBean = listAdapter.getItem(position);
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

    /**
     * 列表上的导航功能
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
        if (url.contains("product_list.html?b_id")) {
            //跳转到搜索
            String id = url.split("b_id=")[1];
            ActivityToActivity.toActivity(ARouterConfig.classify.PRODUCTSACTIVITY, "id", id);
        } else if (url.contains("signin.html")) {
            //签到
            ToastUtils.showShort("签到");
        }
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.SPECIAL_LIST, id, Object.class).observeForever(o -> {
            if (o instanceof SpecialPageBean) {
                binding.refreshLayout.finishRefresh();
                SpecialPageBean result = (SpecialPageBean) o;
                listAdapter.setNewData(formatDatas(result.getList()));
            } else {
                ToastUtils.showShort(o.toString());
                binding.refreshLayout.finishRefresh(false);
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
                datasBean.setLabel(datasBean.getGoods2().getTitle().isEmpty() ? "团购商品" : datasBean.getGoods2().getTitle());
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
    protected void initData() {
        super.initData();
        mViewModel.getSpecialList(id);
    }

    @Override
    protected Object getStateEventKey() {
        return "SpecialActivity";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_special;
    }
}
