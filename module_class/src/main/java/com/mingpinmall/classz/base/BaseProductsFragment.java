package com.mingpinmall.classz.base;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.goldze.common.dmvvm.base.mvvm.base.BaseListFragment;
import com.goldze.common.dmvvm.utils.DisplayUtil;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.databinding.ItemTabsegmentBinding;
import com.mingpinmall.classz.ui.activity.classiflist.ScreeningActivity;
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

/**
 * 封装分类
 */
public abstract class BaseProductsFragment<T extends ClassifyViewModel> extends BaseListFragment<T>
        implements View.OnClickListener,
        CustomPopWindow.Builder.OnCustomPopWindowClickListener {

    @Autowired
    String id;

    @Autowired
    protected int type;

    @Autowired
    protected String keyword;

    View curressView;

    /*店铺服务*/
    protected String ci = "", st = "";

    protected String areaId = "", priceFrom = "", priceTo = "";//地区 价格区间最低范围 价格区间最高范围

    protected String key, order;/*排序条件*/

    private FilterTab filterTab0;

    protected ImageView imageView;

    GridLayoutManager gridLayoutManager;
    DelegateAdapter gridAdapter;
    boolean isGrid;
    ScreeningPopWindow screeningPopWindow;
    CustomPopWindow customPopWindow;


    @Override
    public void initView(Bundle state) {
        super.initView(state);
        ItemTabsegmentBinding itemTabsegmentBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.item_tabsegment, null, false);
        binding.llRecyclerview.addView(itemTabsegmentBinding.getRoot(), 0);
        filterTab0 = itemTabsegmentBinding.filterTab0;
        filterTab0.setFilterTabSelected(true);
        curressView = filterTab0;

        imageView = itemTabsegmentBinding.filterTab4;
        imageView.setOnClickListener(this);

        for (FilterTab tab :
                Arrays.asList(itemTabsegmentBinding.filterTab0,
                        itemTabsegmentBinding.filterTab1, itemTabsegmentBinding.filterTab2, itemTabsegmentBinding.filterTab3)) {
            tab.setOnClickListener(this);
        }

    }

    @Override
    protected void dataObserver() {
        super.dataObserver();

        registerObserver(getStoreGoodsRankKey(), GoodsListInfo.class)
                .observe(this, new Observer<GoodsListInfo>() {
                    @Override
                    public void onChanged(@Nullable GoodsListInfo response) {
                        /*KLog.i("77777" + type);*/
                        setData(response.getDatas().getGoods_list());
                    }
                });

        /*筛选*/
//        registerObserver(getCustompopWindowKey(), ScreenInfo.class)
//                .observe(this, reponse -> {
//                    areaId = reponse.areaId;
//                    priceFrom = reponse.priceFrom;
//                    priceTo = reponse.priceTo;
//                    for (String s : reponse.shoppingServer) {
//                        ci = ci.concat(s).concat("_");
//                    }
//                    for (String s : reponse.goodsType) {
//                        st = st.concat(s).concat("_");
//                    }
//                    keyword = "";
////                        areaId="";
//                    order = "";
//                    key = "";
//                    id = "";
//                    onRefresh();
//                });
    }

    /*筛选*/
    protected Object getStoreGoodsRankKey() {
        return Constants.STORE_GOODS_RANK_KEY[2];
    }

    /*筛选*/
    protected Object getCustompopWindowKey() {
        return Constants.CUSTOMPOPWINDOW_KEY[1];
    }

    @Override
    protected DelegateAdapter createAdapter() {
        return AdapterPool.newInstance()
                .getProductsAdapter(getActivity())
                .build();
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.STORE_GOODS_RANK_KEY[1];
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (v instanceof FilterTab) {
            if (v != curressView) {
                ((FilterTab) v).setFilterTabSelected(!v.isSelected());
                curressView = v;
            }
            if (i == R.id.filter_tab0) {
                if (null == customPopWindow) {
                    customPopWindow =
                            new CustomPopWindow.Builder(getActivity())//.setColumnCount(3)//设置列数，测试2.3.4.5没问题
                                    .setDataSource(Arrays.asList("综合排序", "价格从高到低", "价格从低到高", "人气排序"))
                                    .setListener(this)
                                    .setColorBg(R.color.color_f8f8f8).build().createPop();
                }
                customPopWindow.showAsDropDown(filterTab0);
            } else if (i == R.id.filter_tab1) {
                changFilterTab1();
                onRefresh();
            } else if (i == R.id.filter_tab3) {
                if (null == screeningPopWindow) {
                    screeningPopWindow = new ScreeningPopWindow.Builder(getActivity())
                            .setEventKey(Constants.CUSTOMPOPWINDOW_KEY[1])
                            .setColorBg(R.color.color_f8f8f8).build().createPop();
                }
                screeningPopWindow.showAtLocation(filterTab0,
                        Gravity.TOP, 100, DisplayUtil.getStatusBarHeight(getActivity()));
            }
        } else if (i == R.id.filter_tab4) {
            if (!isGrid) {
                if (null == gridLayoutManager) {
                    gridLayoutManager = new GridLayoutManager(getContext(), 2);
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
                            .getProductsGridAdapter(getContext())
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
    protected boolean isItemDecoration() {
        return false;
    }

    @Override
    public void onClick(PopupWindow dialog, View itemView, int position, String content) {
        filterTab0.setText(content);
        changeKeyAndOrder(position);
        onRefresh();
        dialog.dismiss();
    }

    /*获取更多数据*/
//    @Override
//    protected void getRemoteData() {
//        super.getRemoteData();
//        KLog.i(id + " " + page + " " + keyword + " " + key + " " + order);
//        mViewModel.getStoreGoods(getArguments().getString("id"), page, key, areaId, priceFrom, priceTo, order, ci, st, Constants.STORE_GOODS_RANK_KEY[2]);
//    }

    protected abstract void changFilterTab1();

    protected abstract void changeKeyAndOrder(int position);
}