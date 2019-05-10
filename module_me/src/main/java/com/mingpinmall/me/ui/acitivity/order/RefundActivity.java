package com.mingpinmall.me.ui.acitivity.order;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.mvvm.base.BaseTabsActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.acitivity.order.refundorder.RefundFragment;
import com.mingpinmall.me.ui.acitivity.order.refundorder.ReturnFragment;

/**
 * 功能描述：我的订单 > 退款/退货
 * @author 小斌
 * @date 2019/3/27
 **/
@Route(path = ARouterConfig.Me.REFUNDACTIVITY)
public class RefundActivity extends BaseTabsActivity {

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
        return new String[]{getString(R.string.tabs_text_refundMoney), getString(R.string.tabs_text_refund)};
    }

    @Override
    protected Class[] initFragments() {
        return new Class[]{RefundFragment.class, ReturnFragment.class};
    }

}
