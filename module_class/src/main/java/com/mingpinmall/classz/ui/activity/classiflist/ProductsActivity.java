package com.mingpinmall.classz.ui.activity.classiflist;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.activity.HorizontalTabActivity;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.DisplayUtil;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.widget.CustomPopWindow;
import com.mingpinmall.classz.widget.ScreeningPopWindow;
import com.socks.library.KLog;
import com.xuexiang.xui.widget.tabbar.EasyIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 商品分类list
 */
@Route(path = ARouterConfig.classify.PRODUCTSACTIVITY)
public class ProductsActivity extends HorizontalTabActivity {

    @Autowired
    String id;

    @Autowired
    int type;

    @Autowired
    String keyword;

    @Override
    protected String[] getTabTitles() {
        return Constants.PRODUCTS_TITLE;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);

        KLog.i("搜索" + keyword);

        mEasyIndicator.setOnTabClickListener(new EasyIndicator.onTabClickListener() {
            @Override
            public void onTabClick(String title, int position) {
                if (position == 10) {
                    new CustomPopWindow.Builder(ProductsActivity.this).setColumnCount(3)//设置列数，测试2.3.4.5没问题
                            .setDataSource(Arrays.asList("综合排序", "价格从高到低", "价格从低到高", "人气排序"))
                            .setColorBg(R.color.color_f8f8f8).build().createPop().showAsDropDown(mEasyIndicator);
                }
                new ScreeningPopWindow.Builder(ProductsActivity.this)
//                        .setColumnCount(3)//设置列数，测试2.3.4.5没问题
                        .setDataSource(Arrays.asList("综合排序", "价格从高到低", "价格从低到高", "人气排序"))
                        .setColorBg(R.color.color_f8f8f8).build().createPop()
//                        .showAsDropDown(mEasyIndicator);
                        .showAtLocation(mEasyIndicator,
                                Gravity.TOP, 100, DisplayUtil.getStatusBarHeight(ProductsActivity.this));


            }
        });

        /*综合排序*/
        LiveBus.getDefault()
                .subscribe(Constants.CUSTOMPOPWINDOW_KEY[0], String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        KLog.i("选择的内容" + s);
                    }
                });
        /*筛选*/
        LiveBus.getDefault()
                .subscribe(Constants.CUSTOMPOPWINDOW_KEY[1], String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        KLog.i("选择的内容" + s);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        for (Object object : Constants.CUSTOMPOPWINDOW_KEY) {
            LiveBus.getDefault().clear(object);
        }
    }

    @Override
    protected List<BaseFragment> getTabFragments() {
        List<BaseFragment> list = new ArrayList<>();
        int length = Constants.PRODUCTS_TITLE.length;
        KLog.i(id + keyword);
        for (int i = 0; i < length; i++) {
            list.add(ProductsItemFragment.newInstance(id, type + "", keyword));
        }
        return list;
    }
}