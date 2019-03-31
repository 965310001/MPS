package com.mingpinmall.me.ui.acitivity.collection;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityCollectionBinding;
import com.mingpinmall.me.ui.adapter.BasePagerAdapter;
import com.xuexiang.xui.widget.tabbar.TabControlView;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/3/27
 **/
@Route(path = ARouterConfig.COLLECTIONACTIVITY)
public class CollectionActivity extends BaseActivity<ActivityCollectionBinding> {

    @Autowired
    int pageIndex = 0;

    private ProductCollectionFragment productFragment;
    private ShopsCollectionFragment shopsFragment;

    private BasePagerAdapter pagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    protected boolean isBack() {
        return false;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        findViewById(R.id.rl_title_bar).setVisibility(View.GONE);
        binding.ivBack2.setOnClickListener(this);

        productFragment = new ProductCollectionFragment();
        shopsFragment = new ShopsCollectionFragment();
        pagerAdapter = new BasePagerAdapter(getSupportFragmentManager(), this);
        pagerAdapter.addFragment(productFragment, getString(R.string.me_headitem_item1));
        pagerAdapter.addFragment(shopsFragment, getString(R.string.me_headitem_item2));
        binding.viewPager.setAdapter(pagerAdapter);
        binding.viewPager.setNoScroll(true);
        binding.tabControl.setOnTabSelectionChangedListener(new TabControlView.OnTabSelectionChangedListener() {
            @Override
            public void newSelection(String title, String value) {
                binding.viewPager.setCurrentItem(value.equals("1") ? 0 : 1, false);
            }
        });
        try {
            binding.tabControl.setItems(new String[]{getString(R.string.tabs_text_product), getString(R.string.tabs_text_shops)}, new String[]{"1", "2"});
            binding.tabControl.setDefaultSelection(pageIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.iv_back2) {
            onBackPressed();
        }
    }
}
