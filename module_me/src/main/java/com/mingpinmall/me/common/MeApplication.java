package com.mingpinmall.me.common;


import android.content.Context;
import android.support.annotation.NonNull;

import com.goldze.common.dmvvm.base.BaseApplication;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.xuexiang.xui.XUI;

/**
 * @Author: guofeng
 * @CreateDate: 2019/3/22 16:25
 * @Description:
 */
public class MeApplication extends BaseApplication {

    {
        /*全局初始化刷新控件 非必须*/
        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
            @Override
            public void initialize(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setEnableLoadMoreWhenContentNotFull(false)
                        .setEnableOverScrollDrag(true)
                        .setEnableOverScrollBounce(true);
            }
        });
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                ClassicsHeader header = new ClassicsHeader(context);
                header.setSpinnerStyle(SpinnerStyle.Translate);
//                header.setAccentColor(ContextCompat.getColor(context, R.color.text_black));
//                header.setPrimaryColor(ContextCompat.getColor(context, R.color.background_hui));
                return header;
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                ClassicsFooter footer = new ClassicsFooter(context);
                footer.setSpinnerStyle(SpinnerStyle.Translate);
//                        footer.setAccentColor(ContextCompat.getColor(context, R.color.text_black));
//                        footer.setPrimaryColor(ContextCompat.getColor(context, R.color.background_hui));
                return footer;
            }
        });
    }

    @Override
    public void run() {
        XUI.init(this);

    }

//    @Override
//    public void onTerminate() {
//        super.onTerminate();
//    }
}
