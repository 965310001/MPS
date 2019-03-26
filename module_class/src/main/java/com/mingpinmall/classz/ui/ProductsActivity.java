package com.mingpinmall.classz.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.activity.HorizontalTabActivity;
import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类list
 */
@Route(path = ARouterConfig.classify.PRODUCTSACTIVITY)
public class ProductsActivity extends HorizontalTabActivity {

    @Autowired
    String id;

    @Override
    protected String[] getTabTitles() {
        return Constants.PRODUCTS_TITLE;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);
    }

    @Override
    protected List<BaseFragment> getTabFragments() {
        List<BaseFragment> list = new ArrayList<>();
        int length = Constants.PRODUCTS_TITLE.length;
        KLog.i(id);
        for (int i = 0; i < length; i++) {
            list.add(ProductsItemFragment.newInstance(id));
        }
        return list;
    }
}