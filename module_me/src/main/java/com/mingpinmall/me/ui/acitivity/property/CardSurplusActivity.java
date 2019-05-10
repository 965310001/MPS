package com.mingpinmall.me.ui.acitivity.property;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.mvvm.base.BaseTabsActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.acitivity.property.cardsurplus.CardSurplusChargeFragment;
import com.mingpinmall.me.ui.acitivity.property.cardsurplus.CardSurplusLogFragment;

/**
 * 功能描述：我的财产-充值卡余额
 * @author 小斌
 * @date 2019/3/28
 **/
@Route(path = ARouterConfig.Me.CARDSURPLUSACTIVITY)
public class CardSurplusActivity extends BaseTabsActivity {

    @Override
    protected String[] initTabs() {
        return new String[]{getString(R.string.tabs_text_cardsurplus1),
                getString(R.string.tabs_text_cardsurplus2)};
    }

    @Override
    protected Class[] initFragments() {
        return new Class[]{CardSurplusLogFragment.class, CardSurplusChargeFragment.class};
    }

    @Override
    protected boolean isTabsBar() {
        return true;
    }
}
