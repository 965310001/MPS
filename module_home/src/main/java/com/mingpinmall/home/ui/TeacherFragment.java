package com.mingpinmall.home.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebViewClient;
import com.mingpinmall.home.R;
import com.mingpinmall.home.databinding.FragmentTeacherBinding;

/**
 * 功能描述：名师预约
 * *@author 小斌
 * @date 2019/4/27
 **/
public class TeacherFragment extends BaseFragment<FragmentTeacherBinding> {

//    private final String URL = "https://www.feeai.cn/demo/demo.php#/";
    private final String URL = "http://c85of8h57pslid5r.mikecrm.com/D1HWDMm";

    private AgentWeb mAgentWeb;

    public TeacherFragment() {
    }

    public static TeacherFragment newInstance() {
        return new TeacherFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_teacher;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {
        getViewById(R.id.rl_title_bar).setVisibility(View.GONE);
        ((TextView) getViewById(R.id.tv_title)).setText("名师预约");
        //在这里设置沉浸式状态栏
        setTitlePadding(getViewById(R.id.rl_title_content));
        binding.refreshLayout.setEnableLoadMore(false);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(binding.refreshLayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebViewClient(new MyWebClient())
                .createAgentWeb()
                .ready()
                .go(URL);
        mAgentWeb.getWebCreator().getWebView().setOverScrollMode(View.OVER_SCROLL_NEVER);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mAgentWeb.getWebCreator().getWebView().setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> mAgentWeb.getUrlLoader().loadUrl(URL));
    }

    private class MyWebClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            binding.refreshLayout.finishRefresh();
        }
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        setDarkMode(true);
    }

    @Override
    protected void onInVisible() {
        super.onInVisible();
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
    public void onDestroyView() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroyView();
    }

}
