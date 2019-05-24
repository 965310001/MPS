package com.mingpinmall.classz.ui.activity.holo;

import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.ActivityHoloBinding;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.socks.library.KLog;

/**
 * 试戴
 */
@Route(path = ARouterConfig.classify.HOLOACTIVITY)
public class HoloActivity extends AbsLifecycleActivity<ActivityHoloBinding, ClassifyViewModel> {

    @Autowired
    String url;
    WebView webview;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);

        setTitle("试戴");
        KLog.i(url);
        webview = binding.wv;
        WebSettings settings = webview.getSettings();
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setUserAgentString(String.format("%s %s", webview.getSettings().getUserAgentString(), "GZDZWL"));
        settings.setBuiltInZoomControls(false);
        KLog.i(webview.getSettings().getUserAgentString());
        webview.setWebViewClient(new MyWebClient());
        webview.loadUrl(url);        // 打印结果
    }

    private class MyWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            KLog.i("shouldOverrideUrlLoading");
            webview.loadUrl(url);
            return false;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            KLog.i("onReceivedSslError");
            handler.proceed(); // 接受所有网站的证书
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            tvTitle.setText(view.getTitle());
            showSuccess();
        }
    }


    @Override
    protected Object getStateEventKey() {
        return "";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_holo;
    }
}
