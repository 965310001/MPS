package com.mingpinmall.classz.ui.activity.details;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.FragmentGoodsInfoWebBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情 - 图文详情 Fragment
 */
public class GoodsInfoWebFragment extends BaseFragment<FragmentGoodsInfoWebBinding> {

    //    private WebView webView;
//    private WebSettings webSettings;
    private AgentWeb mAgentWeb;

    public GoodsInfoWebFragment() {
    }

    public void needScroll2Top() {
        if (mAgentWeb.getWebCreator().getWebView() != null) {
            mAgentWeb.getWebCreator().getWebView().scrollTo(0, 0);
        }
    }

    @Override
    protected void onInVisible() {
        super.onInVisible();
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
    protected void onVisible() {
        super.onVisible();
        WebView view = mAgentWeb.getWebCreator().getWebView();
        if (view.getWidth() == 0 || view.getHeight() == 0) {
            return;
        }
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        ((ShoppingDetailsActivity) activity).setDrawerImage(bitmap);
    }

    @Override
    public void initView(Bundle state) {
        setTitlePadding(binding.rlTopPanel);
        //imgDisplay
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(binding.contentLayout, new FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(new WebChromeClient() {
                    @Override
                    public void onProgressChanged(WebView view, int newProgress) {
                        super.onProgressChanged(view, newProgress);
                    }
                })
                .setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        addImageClickListner();
                    }
                })
                .createAgentWeb()
                .ready()
                .go(getArguments().getString("url"));
        mAgentWeb.getWebCreator().getWebView().setOverScrollMode(View.OVER_SCROLL_NEVER);
        mAgentWeb.getJsInterfaceHolder().addJavaObject("imagelistner", new AndroidToJs());
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mAgentWeb.getWebCreator().getWebView().setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
//        initWebView();
    }

    class AndroidToJs extends Object {
        @JavascriptInterface
        public boolean openImage(String img) {
            Log.d(TAG, "AndroidToJs: openImage 查看大图" + img);
            if (img != null) {
                List<LocalMedia> selectList = new ArrayList<>();
                LocalMedia localMedia = new LocalMedia();
                localMedia.setPath(img);
                selectList.add(localMedia);
                PictureSelector.create(activity).themeStyle(R.style.picture_default_style).openExternalPreview(0, selectList);
            }
            return true;
        }

    }

    // 注入js函数监听
    private void addImageClickListner() {
        Log.d(TAG, "addImageClickListner: 注入代码");

        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，在还是执行的时候调用本地接口传递url过去
        mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
//        mAgentWeb.getWebCreator().getWebView().loadUrl("javascript:(function(){"
//                + "var objs = document.getElementsByTagName(\"img\");"
//                + "var imgurl=''; "
//                + "for(var i=0;i<objs.length;i++)  "
//                + "{"
//                + "imgurl+=objs[i].src+',';"
//                + "    objs[i].onclick=function()  "
//                + "    {  "
//                + "        imagelistner.openImage(imgurl);  "
//                + "    }  " + "}" + "})()");
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
