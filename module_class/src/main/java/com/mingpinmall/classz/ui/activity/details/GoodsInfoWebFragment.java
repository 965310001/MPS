package com.mingpinmall.classz.ui.activity.details;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.goldze.common.dmvvm.utils.StatusBarUtils;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.FragmentGoodsInfoWebBinding;

/**
 * 商品详情 - 图文详情 Fragment
 */
public class GoodsInfoWebFragment extends BaseFragment<FragmentGoodsInfoWebBinding> {

    //    private WebView webView;
//    private WebSettings webSettings;
    private AgentWeb mAgentWeb;

    public GoodsInfoWebFragment() {
    }

    @Override
    protected void onInVisible() {
        super.onInVisible();
        mAgentWeb.getWebCreator().getWebView().scrollTo(0, 0);
    }

    public static GoodsInfoWebFragment newInstance(String url) {
        GoodsInfoWebFragment fragment = new GoodsInfoWebFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_goods_info_web;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {
        setTitlePadding(binding.rlTopPanel);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(binding.contentLayout, new FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(getArguments().getString("url"));
        mAgentWeb.getWebCreator().getWebView().setOverScrollMode(View.OVER_SCROLL_NEVER);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mAgentWeb.getWebCreator().getWebView().setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
//        initWebView();
    }

//    private void initWebView() {
//        webView.setFocusable(false);
//        webView.loadUrl(getArguments().getString("url"));
//        webSettings = webView.getSettings();
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setLoadsImagesAutomatically(true);
//        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        webSettings.setBlockNetworkImage(true);
//        webSettings.setUseWideViewPort(true);
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        webView.setWebViewClient(new GoodsDetailWebViewClient());
//    }

//    private class GoodsDetailWebViewClient extends WebViewClient {
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//            webSettings.setBlockNetworkImage(false);
//            showSuccess();
//        }
//
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            super.onPageStarted(view, url, favicon);
//            showLoadingState();
//        }
//    }

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
