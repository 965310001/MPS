package com.mingpinmall.me.ui.acitivity.property;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.mvvm.base.BaseTabsActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.acitivity.property.storePacket.PacketGetFragment;
import com.mingpinmall.me.ui.acitivity.property.storePacket.PacketListFragment;

/**
 * 功能描述：店铺红包
 * 创建人：小斌
 * 创建时间: 2019/3/28
 **/
@Route(path = ARouterConfig.Me.STOREPACKETACTIVITY)
public class StorePacketActivity extends BaseTabsActivity {

    @Override
    protected String[] initTabs() {
        return new String[]{getString(R.string.tabs_text_storePacket1), getString(R.string.tabs_text_storePacket2)};
    }

    @Override
    protected Class[] initFragments() {
        return new Class[]{PacketListFragment.class, PacketGetFragment.class};
    }

}
