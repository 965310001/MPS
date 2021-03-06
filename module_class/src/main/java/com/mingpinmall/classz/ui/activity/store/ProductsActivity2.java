//package com.mingpinmall.classz.ui.activity.store;
//
//import android.arch.lifecycle.Observer;
//import android.databinding.DataBindingUtil;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.GridLayoutManager;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.PopupWindow;
//
//import com.alibaba.android.arouter.facade.annotation.Autowired;
//import com.alibaba.android.arouter.facade.annotation.Route;
//import com.alibaba.android.arouter.launcher.ARouter;
//import com.goldze.common.dmvvm.base.mvvm.base.BaseListActivity;
//import com.goldze.common.dmvvm.constants.ARouterConfig;
//import com.goldze.common.dmvvm.utils.DisplayUtil;
//import com.mingpinmall.classz.R;
//import com.mingpinmall.classz.adapter.AdapterPool;
//import com.mingpinmall.classz.databinding.ItemTabsegmentBinding;
//import com.mingpinmall.classz.ui.api.ClassifyViewModel;
//import com.mingpinmall.classz.ui.constants.Constants;
//import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
//import com.mingpinmall.classz.ui.vm.bean.GoodsListInfo;
//import com.mingpinmall.classz.ui.vm.bean.ScreenInfo;
//import com.mingpinmall.classz.widget.CustomPopWindow;
//import com.mingpinmall.classz.widget.FilterTab;
//import com.mingpinmall.classz.widget.ScreeningPopWindow;
//
//import com.trecyclerview.adapter.DelegateAdapter;
//
//import java.util.Arrays;
//
///**
// * 商品分类list
// */
//@Route(path = ARouterConfig.classify.PRODUCTSACTIVITY2)
//public class ProductsActivity2 extends BaseListActivity<ClassifyViewModel>
//        implements CustomPopWindow.Builder.OnCustomPopWindowClickListener, ScreeningPopWindow.Builder.ScreeningPopWindowClickListener {
//
//    @Autowired
//    String id;
//
//    @Autowired
//    int type;
//
//    @Autowired
//    String keyword;
//
//    View curressView;
//
//    /*店铺服务*/
//    String ci = "", st = "";
//
//    private String areaId = "", priceFrom = "", priceTo = "";//地区 价格区间最低范围 价格区间最高范围
//
//    private String key, order;/*排序条件*/
//
//    private FilterTab filterTab0;
//
//    ImageView imageView;
//
//    GridLayoutManager gridLayoutManager;
//    DelegateAdapter gridAdapter;
//    boolean isGrid;
//
//    ScreeningPopWindow screeningPopWindow;
//    CustomPopWindow customPopWindow;
//
//    @Override
//    protected void initViews(Bundle savedInstanceState) {
//        ARouter.getInstance().inject(this);
//        super.initViews(savedInstanceState);
//
//        ItemTabsegmentBinding itemTabsegmentBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_tabsegment, null, false);
//        binding.llRecyclerview.addView(itemTabsegmentBinding.getRoot(), 0);
//        filterTab0 = itemTabsegmentBinding.filterTab0;
//        filterTab0.setFilterTabSelected(true);
//        curressView = filterTab0;
//
//        clSearch.setVisibility(View.VISIBLE);
//        edSearch.setFocusable(false);
//        edSearch.setFocusableInTouchMode(false);
//        edSearch.setOnClickListener(this);
//        edSearch.setText(keyword);
//        ivSearch.setVisibility(View.GONE);
//        tvTitle.setVisibility(View.GONE);
//
//        imageView = itemTabsegmentBinding.filterTab4;
//        imageView.setOnClickListener(this);
//
//        for (FilterTab tab :
//                Arrays.asList(itemTabsegmentBinding.filterTab0, itemTabsegmentBinding.filterTab1, itemTabsegmentBinding.filterTab2, itemTabsegmentBinding.filterTab3)) {
//            tab.setOnClickListener(this);
//        }
//    }
//
//    /*获取更多数据*/
//    @Override
//    protected void getRemoteData() {
//        super.getRemoteData();
//
//        QLog.i(id + " " + page + " " + keyword + " " + key + " " + order);
//
//        mViewModel.getStoreShappingList(id, String.valueOf(page),
//                keyword, String.valueOf(type), areaId, priceFrom, priceTo, key, order, ci, st);
//    }
//
//
//    @Override
//    protected void dataObserver() {
//        super.dataObserver();
//
//        registerObserver(Constants.STOREINTRO_LIST[0], String.valueOf(type), GoodsListInfo.class)
//                .observe(this, new Observer<GoodsListInfo>() {
//                    @Override
//                    public void onChanged(@Nullable GoodsListInfo response) {
//                        try {
//                            setData(response.getDatas().getGoods_list());
//                        } catch (Exception e) {
//                            QLog.i(e.toString());
//                        }
//                    }
//                });
//    }
//
//    @Override
//    protected DelegateAdapter createAdapter() {
//        return AdapterPool.newInstance()
//                .getProductsAdapter(this)
//                .build();
//    }
//
//    @Override
//    protected Object getStateEventKey() {
//        return Constants.STOREINTRO_LIST[1];
//    }
//
//    @Override
//    public void onClick(View v) {
//        int i = v.getId();
//        if (v instanceof FilterTab) {
//            if (v != curressView) {
//                ((FilterTab) v).setFilterTabSelected(!v.isSelected());
//                curressView = v;
//            }
//            if (i == R.id.filter_tab0) {
//                if (null == customPopWindow) {
//                    customPopWindow =
//                            new CustomPopWindow.Builder(ProductsActivity2.this)//.setColumnCount(3)//设置列数，测试2.3.4.5没问题
//                                    .setDataSource(Arrays.asList("综合排序", "价格从高到低", "价格从低到高", "人气排序"))
//                                    .setListener(this)
//                                    .setColorBg(R.color.color_f8f8f8).build().createPop();
//                }
//                customPopWindow.showAsDropDown(filterTab0);
////                adapter = createAdapter();
//
//            } else if (i == R.id.filter_tab1) {
//                key = "3";
//                order = "2";
//                onRefresh();
//            } else if (i == R.id.filter_tab3) {
//                if (null == screeningPopWindow) {
//                    screeningPopWindow = new ScreeningPopWindow
//                            .Builder(ProductsActivity2.this)
//                            .setListener(this)
//                            .setColorBg(R.color.color_f8f8f8).build().createPop();
//                }
//                screeningPopWindow.showAtLocation(filterTab0,
//                        Gravity.TOP, 100, DisplayUtil.getStatusBarHeight(ProductsActivity2.this));
//            }
//
//        } else if (i == R.id.filter_tab4) {
//            QLog.i("===");
//            if (!isGrid) {
//                if (null == gridLayoutManager) {
//                    gridLayoutManager = new GridLayoutManager(this, 2);
//                    gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                        @Override
//                        public int getSpanSize(int i) {
//                            if (adapter.getItems().get(i) instanceof GoodsInfo) {
//                                return 1;
//                            }
//                            return 2;
//                        }
//                    });
//                    gridAdapter = AdapterPool.newInstance()
//                            .getProductsGridAdapter(this)
//                            .build();
//                }
//                mRecyclerView.setLayoutManager(gridLayoutManager);
//                isGrid = !isGrid;
//                imageView.setImageResource(R.drawable.icon_grid_32);
//                gridAdapter.setDatas(adapter.getItems());
//                mRecyclerView.setAdapter(gridAdapter);
//            } else {
//                isGrid = !isGrid;
//                mRecyclerView.setLayoutManager(layoutManager);
//                imageView.setImageResource(R.drawable.icon_list_32);
//                adapter.setDatas(gridAdapter.getItems());
//                mRecyclerView.setAdapter(adapter);
//            }
//            adapter.notifyDataSetChanged();
//        }
//
//    }
//
//    @Override
//    public void onClick(PopupWindow dialog, View itemView, int position, String content) {
//        dialog.dismiss();
//        filterTab0.setText(content);
//        switch (position) {
//            case 0:/*综合排序*/
//                key = "0";
//                order = "0";
//                break;
//            case 1:/*价格从高到低*/
//                key = "2";
//                order = "2";
//                break;
//            case 2:/*价格从低到高*/
//                key = "2";
//                order = "1";
//                break;
//            case 3:/*综合排序*/
//                key = "5";
//                order = "2";
//                break;
//        }
//        onRefresh();
//    }
//
//    @Override
//    public void onClick(PopupWindow dialog, ScreenInfo reponse) {
//        areaId = reponse.areaId;
//        priceFrom = reponse.priceFrom;
//        priceTo = reponse.priceTo;
//        for (String s : reponse.shoppingServer) {
//            ci = ci.concat(s).concat("_");
//        }
//        for (String s : reponse.goodsType) {
//            st = st.concat(s).concat("_");
//        }
////        order = "";
////        key = "";
////        id = "";
//        onRefresh();
//        dialog.dismiss();
//    }
//}