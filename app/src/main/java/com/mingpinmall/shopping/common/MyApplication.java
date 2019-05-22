package com.mingpinmall.shopping.common;

import android.support.v4.content.ContextCompat;

import com.goldze.common.dmvvm.base.BaseApplication;
import com.goldze.bugly.BuglyUtils;
import com.mingpinmall.shopping.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.xuexiang.xui.XUI;

public class MyApplication extends BaseApplication {

    {
        /*全局初始化刷新控件 非必须*/
        SmartRefreshLayout.setDefaultRefreshInitializer((context, layout) -> layout.setEnableLoadMoreWhenContentNotFull(true)
                .setEnableAutoLoadMore(true)
                .setEnableOverScrollDrag(true)
                .setEnableOverScrollBounce(true));
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            ClassicsHeader header = new ClassicsHeader(context);
            header.setSpinnerStyle(SpinnerStyle.Translate);
            header.setAccentColor(ContextCompat.getColor(context, R.color.black));
//                header.setPrimaryColor(ContextCompat.getColor(context, R.color.color_666666));
            return header;
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            ClassicsFooter footer = new ClassicsFooter(context);
            footer.setSpinnerStyle(SpinnerStyle.Translate);
            footer.setAccentColor(ContextCompat.getColor(context, R.color.black));
//                footer.setPrimaryColor(ContextCompat.getColor(context, R.color.color_666666));
            return footer;
        });
    }

    @Override
    public void run() {
        XUI.init(this);
        BuglyUtils.init(getApplicationContext());
    }
}
