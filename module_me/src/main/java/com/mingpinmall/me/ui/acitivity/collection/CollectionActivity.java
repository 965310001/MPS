package com.mingpinmall.me.ui.acitivity.collection;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.mvvm.base.BaseTabsActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.mingpinmall.me.R;

/**
 * 功能描述：商品or店铺收藏 页面
 * 创建人：小斌
 * 创建时间: 2019/3/27
 **/
@Route(path = ARouterConfig.Me.COLLECTIONACTIVITY)
public class CollectionActivity extends BaseTabsActivity {

    @Autowired
    int pageIndex;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        setIndex(pageIndex);
        super.initViews(savedInstanceState);
    }

    @Override
    protected String[] initTabs() {
        return new String[]{getString(R.string.tabs_text_product), getString(R.string.tabs_text_shops)};
    }

    @Override
    protected Class[] initFragments() {
        return new Class[]{ProductCollectionFragment.class, ShopsCollectionFragment.class};
    }

}
