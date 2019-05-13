package com.mingpinmall.me.ui.acitivity.order;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.base.CustomTabsActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityOrderBinding;
import com.mingpinmall.me.ui.acitivity.order.physicalorder.PhysicalOrderFragment;
import com.mingpinmall.me.ui.acitivity.order.virtualorder.VirtualOrderFragment;

/**
 * 功能描述：我的订单
 * @author 小斌
 * @date 2019/3/26
 **/
@Route(path = ARouterConfig.Me.ORDERACTIVITY)
public class OrderActivity extends CustomTabsActivity<ActivityOrderBinding> {

    @Autowired
    int pageIndex;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);
        ((PhysicalOrderFragment) getFragments(0)).setPageIndex(pageIndex);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    protected String[] initTabs() {
        binding.ivSearch.setOnClickListener(v -> LiveBus.getDefault().postEvent("SEARCH", "ORDER", binding.edSearch.getText().toString()));
        return new String[]{getString(R.string.tabs_text_physical), getString(R.string.tabs_text_virtual)};
    }

    @Override
    protected Class[] initFragments() {
        return new Class[]{PhysicalOrderFragment.class, VirtualOrderFragment.class};
    }

    @Override
    protected int getFrameId() {
        return R.id.fl_fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LiveBus.getDefault().clear("SEARCH", "ORDER");
    }
}
