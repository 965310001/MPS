package com.mingpinmall.classz.ui.activity.holo;

import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.just.agentweb.WebViewClient;
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
//    private AgentWeb mAgentWeb;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);

        setTitle("试戴");
        KLog.i(url);
        webview = binding.wv;
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setBuiltInZoomControls(true);
        settings.setAppCacheEnabled(true);// 设置缓存
        webview.setWebChromeClient(new WebChromeClient());
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //设置允许js弹出alert对话框。
        settings.setGeolocationEnabled(true); //设置可以定位
        settings.setUserAgentString(String.format("%s %s", webview.getSettings().getUserAgentString(), "GZDZWL"));
        KLog.i(webview.getSettings().getUserAgentString());
//        webview.setWebViewClient(new MyWebClient());
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
//        webview.loadUrl(url);        // 打印结果
        KLog.i(url);
//        webview.loadUrl("https://www.feeai.cn/wxapi/h5/fitting/demo.php?goods_id=37&cat_id=7");

//        webview.loadUrl("https://www.feeai.cn/fitting/?shop_id=1097e32594e07daf671d50ad93fca1a9&shop_secret=daa98398d09cf846869c0fd2094d08df#/");
//        webview.loadUrl("https://www.feeai.cn/fitting/?shop_id=1097e32594e07daf671d50ad93fca1a9&shop_secret=daa98398d09cf846869c0fd2094d08df#/");
//        webview.loadUrl("http://www.feeai.cn/fitting/");
        webview.loadUrl("https://www.feeai.cn/fitting?version=2");
        ToastUtils.showLong("测试");
        webview.addJavascriptInterface(new AndroidToJs(), "app");
    }


    class AndroidToJs extends Object {
        @JavascriptInterface
        public boolean customizeCamera() {
            return true;
        }

        @JavascriptInterface
        public boolean chooseModelImage() {
            KLog.i("处理摄像头");
            return true;
        }
    }

    private class MyWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            KLog.i("shouldOverrideUrlLoading");

//            Map<String, String> extraHeaders = new HashMap<String, String>();
//            extraHeaders.put("Referer", "http://wxpay.wxutil.com");
            view.loadUrl(url);
//            webview.loadUrl(url);
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
//            tvTitle.setText(view.getTitle());
            showSuccess();
        }
    }

//    private WebViewClient client = new WebViewClient() {
//        /**
//         * 防止加载网页时调起系统浏览器
//         */
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//            return true;
//        }
//    };


    @Override
    protected Object getStateEventKey() {
        return "";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_holo;
    }
}
