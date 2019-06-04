package com.goldze.common.dmvvm.activity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.R;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.databinding.ActivitySmartwebBinding;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebViewClient;

/**
 * @author 小斌
 * @data 2019/5/31
 **/
@Route(path = ARouterConfig.SMARTWEBACTIVITY)
public class SmartWebActivity extends BaseActivity<ActivitySmartwebBinding> {

    @Autowired
    String url;

    private AgentWeb mAgentWeb;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_smartweb;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        setTitle("");
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((ViewGroup) binding.getRoot(), new FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebViewClient(new MyWebClient())
                .createAgentWeb()
                .ready()
                .go(url);
    }

    @Override
    public void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    private class MyWebClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            setTitle(view.getTitle());
        }
    }
}
