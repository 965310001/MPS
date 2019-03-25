package com.mingpinmall.shopping.common;

import com.goldze.common.dmvvm.base.BaseApplication;
import com.xuexiang.xui.XUI;

public class MyApplication extends BaseApplication {

    @Override
    public void run() {
        XUI.init(this);
    }
}
