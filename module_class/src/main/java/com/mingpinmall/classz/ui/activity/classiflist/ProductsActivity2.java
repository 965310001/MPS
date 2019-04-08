package com.mingpinmall.classz.ui.activity.classiflist;

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.mvvm.base.BaseListActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;

import com.goldze.common.dmvvm.utils.DisplayUtil;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.databinding.ItemTabsegmentBinding;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.GoodsListInfo;

import com.mingpinmall.classz.widget.CustomPopWindow;
import com.mingpinmall.classz.widget.FilterBar;
import com.mingpinmall.classz.widget.FilterTab;

import com.mingpinmall.classz.widget.ScreeningPopWindow;
import com.socks.library.KLog;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.listener.OnItemClickListener;

import java.util.Arrays;

/**
 * 商品分类list
 */
@Route(path = ARouterConfig.classify.PRODUCTSACTIVITY)
public class ProductsActivity2 extends BaseListActivity<ClassifyViewModel> {

    @Autowired
    String id;

    @Autowired
    int type;

    @Autowired
    String keyword;

    View curressView;

    private String areaId = "", priceFrom = "", priceTo = "";//地区 价格区间最低范围 价格区间最高范围

    private String key, order;/*排序条件*/

    private FilterTab filterTab0;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);

        ItemTabsegmentBinding itemTabsegmentBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_tabsegment, null, false);
        binding.llRecyclerview.addView(itemTabsegmentBinding.getRoot(), 0);
        filterTab0 = itemTabsegmentBinding.filterTab0;
        filterTab0.setFilterTabSelected(true);
        curressView = filterTab0;

        for (FilterTab tab :
                Arrays.asList(itemTabsegmentBinding.filterTab0, itemTabsegmentBinding.filterTab1, itemTabsegmentBinding.filterTab2, itemTabsegmentBinding.filterTab3)) {
            tab.setOnClickListener(this);
        }
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
                .observe(this, new Observer<GoodsListInfo>() {
                    @Override
                    public void onChanged(@Nullable GoodsListInfo response) {
                        KLog.i("" + type);
                        setData(response.getDatas().getGoods_list());
                    }
                });
        /*综合排序*/
        registerObserver(Constants.CUSTOMPOPWINDOW_KEY[0], String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        filterTab0.setText(s);
                        if (s.equals("综合排序")) {
                            key = "";
                            order = "";
                        } else if (s.equals("人气排序")) {
                            key = "2";
                            order = "2";
                        } else if (s.equals("价格从高到低")) {
                            key = "3";
                            order = "2";
                        } else if (s.equals("价格从低到高")) {
                            key = "3";
                            order = "1";
                        }
                        page = 1;
                        getRemoteData();
                    }
                });

        /*筛选*/
        registerObserver(Constants.CUSTOMPOPWINDOW_KEY[1], String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        KLog.i("选择的内容" + s);
                    }
                });
    }

    /*获取更多数据*/
    @Override
    protected void getRemoteData() {
        super.getRemoteData();

        KLog.i(id + " " + page + " " + keyword + " " + key + " " + order);

        mViewModel.getShappingList(id, String.valueOf(page), keyword, String.valueOf(type), areaId, priceFrom, priceTo, key, order);
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.PRODUCTS_EVENT_KEY[1];
    }

    @Override
    public void onClick(View v) {
        if (v instanceof FilterTab) {
            if (v != curressView) {
                ((FilterTab) v).setFilterTabSelected(!v.isSelected());
                curressView = v;
            }
            int i = v.getId();
            if (i == R.id.filter_tab0) {
                new CustomPopWindow.Builder(ProductsActivity2.this).setColumnCount(3)//设置列数，测试2.3.4.5没问题
                        .setDataSource(Arrays.asList("综合排序", "价格从高到低", "价格从低到高", "人气排序"))
                        .setColorBg(R.color.color_f8f8f8).build().createPop().showAsDropDown(filterTab0);
                adapter = createAdapter();

            } else if (i == R.id.filter_tab1) {
                key = "1";
                order = "2";
                onRefresh();

            } else if (i == R.id.filter_tab3) {
                new ScreeningPopWindow.Builder(ProductsActivity2.this)
                        .setDataSource(Arrays.asList("综合排序", "价格从高到低", "价格从低到高", "人气排序"))
                        .setColorBg(R.color.color_f8f8f8).build().createPop()
                        .showAtLocation(filterTab0,
                                Gravity.TOP, 100, DisplayUtil.getStatusBarHeight(ProductsActivity2.this));

            }

        }

    }
}