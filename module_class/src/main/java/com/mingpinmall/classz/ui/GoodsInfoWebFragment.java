package com.mingpinmall.classz.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.FragmentGoodsInfoWebBinding;
import com.socks.library.KLog;

/**
 * 商品详情 - 图文详情 Fragment
 */
public class GoodsInfoWebFragment extends BaseFragment<FragmentGoodsInfoWebBinding> {
    private String url;
    //    @BindView(R.id.hwv_detail)
    public WebView webView;
    private WebSettings webSettings;

    public GoodsInfoWebFragment() {
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
        /*showSuccess();*/
        webView = binding.hwvDetail;
        initWebView();
    }

    private void initWebView() {
        url = getArguments().getString("url");
        KLog.i("initWebView:" + url);

        webView.setFocusable(false);
        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setBlockNetworkImage(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebViewClient(new GoodsDetailWebViewClient());
    }

    private class GoodsDetailWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webSettings.setBlockNetworkImage(false);
            showSuccess();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showLoadingState();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }
    }


}
