package com.mingpinmall.me.ui.acitivity.order;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityCollectionBinding;
import com.mingpinmall.me.ui.acitivity.order.RefundOrder.RefunMoneyFragment;
import com.mingpinmall.me.ui.acitivity.order.RefundOrder.RefundFragment;
import com.mingpinmall.me.ui.adapter.BasePagerAdapter;
import com.xuexiang.xui.widget.tabbar.TabControlView;

/**
 * 功能描述：我的订单 > 退款/退货
 * 创建人：小斌
 * 创建时间: 2019/3/27
 **/
@Route(path = ARouterConfig.REFUNDACTIVITY)
public class RefundActivity extends BaseActivity<ActivityCollectionBinding> {

    @Autowired
    int pageIndex = 0;

    private RefundFragment refundFragment;
    private RefunMoneyFragment refunMoneyFragment;

    private BasePagerAdapter pagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    protected boolean isTabsBar() {
        return true;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);

        refundFragment = new RefundFragment();
        refunMoneyFragment = new RefunMoneyFragment();
        pagerAdapter = new BasePagerAdapter(getSupportFragmentManager(), this);
        pagerAdapter.addFragment(refunMoneyFragment, getString(R.string.tabs_text_refundMoney));
        pagerAdapter.addFragment(refundFragment, getString(R.string.tabs_text_refund));
        binding.viewPager.setAdapter(pagerAdapter);
        binding.viewPager.setNoScroll(true);
        tabControlView.setOnTabSelectionChangedListener(new TabControlView.OnTabSelectionChangedListener() {
            @Override
            public void newSelection(String title, String value) {
                binding.viewPager.setCurrentItem(value.equals("1") ? 0 : 1, false);
            }
        });
        try {
            tabControlView.setItems(new String[]{getString(R.string.tabs_text_refundMoney), getString(R.string.tabs_text_refund)}, new String[]{"1", "2"});
            tabControlView.setDefaultSelection(pageIndex);
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
