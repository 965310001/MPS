package com.mingpinmall.classz.ui.activity.classiflist;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.goldze.common.dmvvm.base.mvvm.base.BaseListActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.DisplayUtil;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.databinding.ItemTabsegmentBinding;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsListInfo;
import com.mingpinmall.classz.ui.vm.bean.ScreenInfo;
import com.mingpinmall.classz.widget.CustomPopWindow;
import com.mingpinmall.classz.widget.FilterTab;
import com.mingpinmall.classz.widget.ScreeningPopWindow;
import com.socks.library.KLog;
import com.trecyclerview.adapter.DelegateAdapter;

import java.util.Arrays;
import java.util.Collection;

/**
 * 商品分类list
 */
@Route(path = ARouterConfig.classify.PRODUCTSACTIVITY)
public class ProductsActivity extends BaseListActivity<ClassifyViewModel>
        implements CustomPopWindow.Builder.OnCustomPopWindowClickListener {
    @Autowired
    String id;

    @Autowired
    String gcIdType;

    @Autowired
    int type;

    @Autowired
    String keyword;

    private View currentView;
    /*店铺服务*/
    private String ci = "", st = "";
    private String areaId = "", priceFrom = "", priceTo = "";//地区 价格区间最低范围 价格区间最高范围
    private String key, order;/*排序条件*/
    private FilterTab filterTab0;
    private PopupWindow screeningPopWindow, customPopWindow;
    private ImageView imageView;
    private String picPath = "";
    private GridLayoutManager gridLayoutManager;
    private DelegateAdapter gridAdapter;
    private boolean isGrid;


    @Override
    protected void initViews(Bundle savedInstanceState) {
//        XUI.initTheme(this);
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);
        clSearch.setVisibility(View.VISIBLE);
        edSearch.setFocusable(false);
        edSearch.setFocusableInTouchMode(false);
        edSearch.setInputType(InputType.TYPE_NULL);
        edSearch.setCursorVisible(false);
        edSearch.setTextIsSelectable(false);
        edSearch.setOnClickListener(this);
        edSearch.setText("".equals(keyword) ? "请输入搜索内容" : keyword);
        ivSearch.setVisibility(View.GONE);
        tvTitle.setVisibility(View.GONE);

        ItemTabsegmentBinding itemTabsegmentBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_tabsegment, null, false);
        binding.llRecyclerview.addView(itemTabsegmentBinding.getRoot(), 0);
        //分割线
        View view = new View(activity);
        view.setBackgroundResource(R.color.line_color);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.px2dip(activity, 1));
        binding.llRecyclerview.addView(view, 1, layoutParams);

        filterTab0 = itemTabsegmentBinding.filterTab0;
        imageView = itemTabsegmentBinding.filterTab4;
        imageView.setOnClickListener(this);
        filterTab0.setFilterTabSelected(true);
        currentView = filterTab0;

        for (FilterTab tab :
                Arrays.asList(itemTabsegmentBinding.filterTab0,
                        itemTabsegmentBinding.filterTab1,
                        itemTabsegmentBinding.filterTab2,
                        itemTabsegmentBinding.filterTab3)) {
            tab.setOnClickListener(this);
        }
    }


    @Override
    protected void onRefreshSuccess(Collection<?> collection) {
        oldItems.clear();
        if (!picPath.isEmpty()) {
            oldItems.add(picPath);
        }
        oldItems.addAll(collection);
        mRecyclerView.refreshComplete(oldItems, !isLoadMore);
        isRefresh = false;
    }

    @Override
    protected DelegateAdapter createAdapter() {
        return AdapterPool.newInstance()
                .getProductsAdapter(this)
                .build();
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();

        registerObserver(Constants.PRODUCTS_EVENT_KEY[0], String.valueOf(type), GoodsListInfo.class)
                .observe(this, response -> {
                    picPath = response.getDatas().getBrand_bgpic();
                    setData(response.getDatas().getGoods_list());
                });

        /*筛选*/
        registerObserver(Constants.CUSTOMPOPWINDOW_KEY[1], ScreenInfo.class)
                .observe(this, response -> {
                    areaId = response.areaId;
                    priceFrom = response.priceFrom;
                    priceTo = response.priceTo;
                    for (String s : response.shoppingServer) {
                        ci = ci.concat(s).concat("_");
                    }
                    for (String s : response.goodsType) {
                        st = st.concat(s).concat("_");
                    }
//                        keyword = "";
                    order = "";
                    key = "";
                    id = "";
                    onRefresh();
                });
    }

    /*获取更多数据*/
    @Override
    protected void getRemoteData() {
        super.getRemoteData();

        KLog.i(id + " " + page + " " + keyword + " " + key + " " + order);

        mViewModel.getShappingList(gcIdType, id, String.valueOf(page), keyword, String.valueOf(type), areaId, priceFrom, priceTo, key, order, ci, st);
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.PRODUCTS_EVENT_KEY[1];
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (v instanceof FilterTab) {
            if (v != currentView) {
                ((FilterTab) v).setFilterTabSelected(!v.isSelected());
                currentView = v;
            }
            if (i == R.id.filter_tab0) {
                if (null == customPopWindow) {
                    customPopWindow =
                            new CustomPopWindow.Builder(ProductsActivity.this)
                                    .setDataSource(Arrays.asList("综合排序", "价格从高到低", "价格从低到高", "人气排序"))
                                    .setListener(this)
                                    .setColorBg(R.color.white).build().createPop();
                }
                customPopWindow.showAsDropDown(filterTab0);
            } else if (i == R.id.filter_tab1) {
                key = "1";
                order = "2";
                onRefresh();
            } else if (i == R.id.filter_tab3) {
                ActivityToActivity.toActivity(ARouterConfig.classify.SCREENINGACTIVITY);
//                if (null == screeningPopWindow) {
//                    screeningPopWindow = new ScreeningPopWindow
//                            .Builder(ProductsActivity.this)
//                            .setEventKey(Constants.CUSTOMPOPWINDOW_KEY[1])
//                            .setColorBg(R.color.color_f8f8f8).build().createPop();
//                }
//                screeningPopWindow.showAtLocation(filterTab0,
//                        Gravity.TOP, 100, DisplayUtil.getStatusBarHeight(ProductsActivity.this));
            }
        } else if (i == R.id.ed_search) {
            ActivityToActivity.toActivity(ARouterConfig.home.SEARCHACTIVITY);
        } else if (i == R.id.filter_tab4) {
            if (!isGrid) {
                if (null == gridLayoutManager) {
                    gridLayoutManager = new GridLayoutManager(this, 2);
                    gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int i) {
                            if (adapter.getItems().get(i) instanceof GoodsInfo) {
                                return 1;
                            }
                            return 2;
                        }
                    });
                    gridAdapter = AdapterPool.newInstance()
                            .getProductsGridAdapter(this)
                            .build();
                }
                mRecyclerView.setLayoutManager(gridLayoutManager);
                isGrid = !isGrid;
                imageView.setImageResource(R.drawable.icon_grid_32);
                gridAdapter.setDatas(adapter.getItems());
                mRecyclerView.setAdapter(gridAdapter);
            } else {
                isGrid = !isGrid;
                mRecyclerView.setLayoutManager(layoutManager);
                imageView.setImageResource(R.drawable.icon_list_32);
                adapter.setDatas(gridAdapter.getItems());
                mRecyclerView.setAdapter(adapter);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(PopupWindow dialog, View itemView, int position, String content) {
        dialog.dismiss();
        filterTab0.setText(content);
        switch (position) {
            case 0:/*综合排序*/
                key = "";
                order = "";
                break;
            case 1:/*价格从高到低*/
                key = "3";
                order = "2";
                break;
            case 2:/*价格从低到高*/
                key = "3";
                order = "1";
                break;
            case 3:/*综合排序*/
                key = "2";
                order = "2";
                break;
            default:
                break;
        }
//        keyword = "";
        onRefresh();
    }
}