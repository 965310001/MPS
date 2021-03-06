//package com.mingpinmall.classz.ui.activity.classiflist;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.view.View;
//
//import com.goldze.common.dmvvm.constants.ARouterConfig;
//import com.goldze.common.dmvvm.utils.ActivityToActivity;
//import com.mingpinmall.classz.R;
//import com.mingpinmall.classz.base.BaseProductsFragment;
//import com.mingpinmall.classz.ui.api.ClassifyViewModel;
//import com.mingpinmall.classz.ui.constants.Constants;
//
///**
// * 商品分类list
// */
//public class ProductsFragment extends BaseProductsFragment<ClassifyViewModel> {
//
//    public static ProductsFragment newInstance(String id, String gcIdType, int type, String keyword) {
//        ProductsFragment fragment = new ProductsFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("id", id);
//        bundle.putString("gcIdType", gcIdType);
//        bundle.putInt("type", type);
//        bundle.putString("keyword", keyword);
//        fragment.setArguments(bundle);
//        return fragment;
//    }
//
//    @Override
//    public void setArguments(@Nullable Bundle args) {
//        super.setArguments(args);
//        type = args.getInt("type");
//        keyword = args.getString("keyword");
//    }
//
//    @Override
//    protected void getRemoteData() {
//        super.getRemoteData();
//
////        mViewModel.getShappingList(getArguments().getString("gcIdType"), getArguments().getString("id"),
////                String.valueOf(page), keyword, String.valueOf(type), key, order, st,
////                areaId, priceFrom, priceTo,
////                "", "", "", "", "", ci, "", "", "", "");
//    }
//
//    @Override
//    protected Object getStoreGoodsRankKey() {
//        return Constants.PRODUCTS_EVENT_KEY[0];
//    }
//
//    @Override
//    protected Object getCustompopWindowKey() {
//        return Constants.CUSTOMPOPWINDOW_KEY[1];
//    }
//
//    @Override
//    protected Object getStateEventKey() {
//        return Constants.PRODUCTS_EVENT_KEY[1];
//    }
//
//    @Override
//    protected void changFilterTab1() {
//        key = "1";
//        order = "2";
//    }
//
//    @Override
//    protected void changeKeyAndOrder(int position) {
//        switch (position) {
//            case 0:/*综合排序*/
//                key = "";
//                order = "";
//                break;
//            case 1:/*价格从高到低*/
//                key = "3";
//                order = "2";
//                break;
//            case 2:/*价格从低到高*/
//                key = "3";
//                order = "1";
//                break;
//            case 3:/*综合排序*/
//                key = "2";
//                order = "2";
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        super.onClick(v);
//        int i = v.getId();
//        if (i == R.id.ed_search) {
//            ActivityToActivity.toActivity(ARouterConfig.home.SEARCHACTIVITY);
//        }
//    }
////    @Autowired
////    String id;
////
////    @Autowired
////    int type;
////
////    @Autowired
////    String keyword;
////
////    View curressView;
////
////    /*店铺服务*/
////    String ci = "", st = "";
////
////    private String areaId = "", priceFrom = "", priceTo = "";//地区 价格区间最低范围 价格区间最高范围
////
////    private String key, order;/*排序条件*/
////
////    private FilterTab filterTab0;
////
////    ImageView imageView;
////
////    public static StoreProductsFragment newInstance(String id) {
////        StoreProductsFragment fragment = new StoreProductsFragment();
////        Bundle bundle = new Bundle();
////        bundle.putString("id", id);
////        fragment.setArguments(bundle);
////        return fragment;
////    }
////
////
////    @Override
////    public void initView(Bundle state) {
////        super.initView(state);
////        ItemTabsegmentBinding itemTabsegmentBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.item_tabsegment, null, false);
////        binding.llRecyclerview.addView(itemTabsegmentBinding.getRoot(), 0);
////        filterTab0 = itemTabsegmentBinding.filterTab0;
////        filterTab0.setFilterTabSelected(true);
////        curressView = filterTab0;
////
////        imageView = itemTabsegmentBinding.filterTab4;
////        imageView.setOnClickListener(this);
////
////        for (FilterTab tab :
////                Arrays.asList(itemTabsegmentBinding.filterTab0, itemTabsegmentBinding.filterTab1, itemTabsegmentBinding.filterTab2, itemTabsegmentBinding.filterTab3)) {
////            tab.setOnClickListener(this);
////        }
////    }
////
////
////    /*获取更多数据*/
////    @Override
////    protected void getRemoteData() {
////        super.getRemoteData();
////        id = ((StoreActivity) activity).getStoreId();
////        QLog.i(id + " " + page + " " + keyword + " " + key + " " + order);
////        mViewModel.getStoreGoods(getArguments().getString("id"), page, key, areaId, priceFrom, priceTo, order, ci, st, Constants.STORE_GOODS_RANK_KEY[2]);
////    }
////
////    @Override
////    protected void dataObserver() {
////        super.dataObserver();
////
////        registerObserver(Constants.STORE_GOODS_RANK_KEY[2], GoodsListInfo.class)
////                .observe(this, new Observer<GoodsListInfo>() {
////                    @Override
////                    public void onChanged(@Nullable GoodsListInfo response) {
////                        QLog.i("" + type);
////                        setData(response.getDatas().getGoods_list());
////                    }
////                });
////
////        /*筛选*/
////        registerObserver(Constants.CUSTOMPOPWINDOW_KEY[1], ScreenInfo.class)
////                .observe(this, new Observer<ScreenInfo>() {
////                    @Override
////                    public void onChanged(@Nullable ScreenInfo reponse) {
////                        areaId = reponse.areaId;
////                        priceFrom = reponse.priceFrom;
////                        priceTo = reponse.priceTo;
////                        for (String s : reponse.shoppingServer) {
////                            ci = ci.concat(s).concat("_");
////                        }
////                        for (String s : reponse.goodsType) {
////                            st = st.concat(s).concat("_");
////                        }
////                        keyword = "";
//////                        areaId="";
////                        order = "";
////                        key = "";
////                        id = "";
////                        onRefresh();
////                    }
////                });
////    }
////
////    @Override
////    protected DelegateAdapter createAdapter() {
////        return AdapterPool.newInstance()
////                .getProductsAdapter(getActivity())
////                .build();
////    }
////
////    @Override
////    protected Object getStateEventKey() {
////        return Constants.STORE_GOODS_RANK_KEY[1];
////    }
////
////    @Override
////    public void onClick(View v) {
////        int i = v.getId();
////        if (v instanceof FilterTab) {
////            if (v != curressView) {
////                ((FilterTab) v).setFilterTabSelected(!v.isSelected());
////                curressView = v;
////            }
////            if (i == R.id.filter_tab0) {
////                if (null == customPopWindow) {
////                    customPopWindow =
////                            new CustomPopWindow.Builder(getActivity())//.setColumnCount(3)//设置列数，测试2.3.4.5没问题
////                                    .setDataSource(Arrays.asList("综合排序", "价格从高到低", "价格从低到高", "人气排序"))
////                                    .setListener(this)
////                                    .setColorBg(R.color.color_f8f8f8).build().createPop();
////                }
////                customPopWindow.showAsDropDown(filterTab0);
////            } else if (i == R.id.filter_tab1) {
////                key = "3";
////                order = "2";
////                onRefresh();
////            } else if (i == R.id.filter_tab3) {
////                if (null == screeningPopWindow) {
////                    screeningPopWindow = new ScreeningPopWindow.Builder(getActivity())
////                            .setEventKey(Constants.CUSTOMPOPWINDOW_KEY[1])
////                            .setColorBg(R.color.color_f8f8f8).build().createPop();
////                }
////                screeningPopWindow.showAtLocation(filterTab0,
////                        Gravity.TOP, 100, DisplayUtil.getStatusBarHeight(getActivity()));
////            }
////        } else if (i == R.id.filter_tab4) {
////            if (!isGrid) {
////                if (null == gridLayoutManager) {
////                    gridLayoutManager = new GridLayoutManager(getContext(), 2);
////                    gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
////                        @Override
////                        public int getSpanSize(int i) {
////                            if (adapter.getItems().get(i) instanceof GoodsInfo) {
////                                return 1;
////                            }
////                            return 2;
////                        }
////                    });
////                    gridAdapter = AdapterPool.newInstance()
////                            .getProductsGridAdapter(getContext())
////                            .build();
////                }
////                mRecyclerView.setLayoutManager(gridLayoutManager);
////                isGrid = !isGrid;
////                imageView.setImageResource(R.drawable.icon_grid_32);
////                gridAdapter.setDatas(adapter.getItems());
////                mRecyclerView.setAdapter(gridAdapter);
////            } else {
////                isGrid = !isGrid;
////                mRecyclerView.setLayoutManager(layoutManager);
////                imageView.setImageResource(R.drawable.icon_list_32);
////                adapter.setDatas(gridAdapter.getItems());
////                mRecyclerView.setAdapter(adapter);
////            }
////            adapter.notifyDataSetChanged();
////        }
////
////    }
////
////    @Override
////    protected boolean isItemDecoration() {
////        return false;
////    }
////
////    GridLayoutManager gridLayoutManager;
////    DelegateAdapter gridAdapter;
////    boolean isGrid;
////    ScreeningPopWindow screeningPopWindow;
////    CustomPopWindow customPopWindow;
////
////    @Override
////    public void onClick(PopupWindow dialog, View itemView, int position, String content) {
////        filterTab0.setText(content);
////        switch (position) {
////            case 0:/*综合排序*/
////                key = "";
////                order = "";
////                break;
////            case 1:/*价格从高到低*/
////                key = "2";
////                order = "2";
////                break;
////            case 2:/*价格从低到高*/
////                key = "2";
////                order = "1";
////                break;
////            case 3:/*综合排序*/
////                key = "5";
////                order = "2";
////                break;
////        }
////        onRefresh();
////        dialog.dismiss();
////    }
//}
