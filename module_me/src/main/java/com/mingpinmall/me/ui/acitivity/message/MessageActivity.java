package com.mingpinmall.me.ui.acitivity.message;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityMessageBinding;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/3/27
 **/
@Route(path = ARouterConfig.MESSAGEACTIVITY)
public class MessageActivity extends BaseActivity<ActivityMessageBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle(R.string.title_messageActivity);
    }
}
