package com.mingpinmall.classz.ui.activity.holo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.Img2Base64Util;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.utils.log.QLog;
import com.goldze.common.dmvvm.widget.dialog.TextDialog;
import com.goldze.common.dmvvm.widget.loading.CustomProgressDialog;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.ActivityHoloBinding;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;

import java.io.File;
import java.util.List;

/**
 * 试戴
 */
@Route(path = ARouterConfig.classify.HOLO2ACTIVITY)
public class Holo2Activity extends AbsLifecycleActivity<ActivityHoloBinding, ClassifyViewModel> {

    @Autowired
    String url;

    private final String holoSavePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator + "MPS" + File.separator + "holo";
    private AgentWeb mAgentWeb;
    private BridgeWebView mBridgeWebView;
    private String picPath = "";

    private ValueCallback<Uri[]> mFilePathCallback;

    @Override
    protected boolean isActionBar() {
        return false;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);
//        setTitle("试戴");
        setTitlePadding(baseBinding.flContent);
        QLog.i("试戴URL：" + url);
//        url = "https://www.feeai.cn/fitting/?shop_id=1097e32594e07daf671d50ad93fca1a9&shop_secret=daa98398d09cf846869c0fd2094d08df&cat_id=7&goods_id=361&version=189";
        mBridgeWebView = new BridgeWebView(activity);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(findViewById(R.id.fl_content), new FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebViewClient(getWebViewClient())
                .setWebChromeClient(getWebChromeClient())
                .setWebView(mBridgeWebView)
                .createAgentWeb()
                .ready()
                .get();
        mAgentWeb.getAgentWebSettings().getWebSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mAgentWeb.getAgentWebSettings().getWebSettings().setAllowFileAccess(true);
        mAgentWeb.getAgentWebSettings().getWebSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mAgentWeb.getWebCreator().getWebView().setOverScrollMode(View.OVER_SCROLL_NEVER);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mAgentWeb.getWebCreator().getWebView().setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        mAgentWeb.getJsInterfaceHolder().addJavaObject("app", new AndroidToJs());
        mAgentWeb.getAgentWebSettings().getWebSettings()
                .setUserAgentString(String.format("%s %s", mAgentWeb.getWebCreator().getWebView().getSettings().getUserAgentString(), "GZDZWL"));
        mAgentWeb.getUrlLoader().loadUrl(url);
    }

    private WebViewClient getWebViewClient() {
        return new WebViewClient() {
            BridgeWebViewClient mBridgeWebViewClient = new BridgeWebViewClient(mBridgeWebView);

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return mBridgeWebViewClient.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mBridgeWebViewClient.onPageFinished(view, url);
            }
        };
    }

    private WebChromeClient getWebChromeClient() {
        return new WebChromeClient() {
            // For Android 5.0
            @Override
            public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> filePath,
                                             android.webkit.WebChromeClient.FileChooserParams fileChooserParams) {
                // Double check that we don't have any existing callbacks
                if (mFilePathCallback != null) {
                    mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = filePath;
                showFileChooser();
                return true;
            }

            //openFileChooser for Android versions >= 4.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                openFileChooser(uploadMsg, acceptType);
            }
//
//            @Override
//            public void onConsoleMessage(String message, int lineNumber, String sourceID) {
//                Log.i("console", message + "(" + sourceID + ":" + lineNumber + ")");
//                super.onConsoleMessage(message, lineNumber, sourceID);
//            }
//
//            @Override
//            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
//                Log.i("console", "[" + consoleMessage.messageLevel() + "] " + consoleMessage.message() + "(" + consoleMessage.sourceId() + ":" + consoleMessage.lineNumber() + ")");
//                return super.onConsoleMessage(consoleMessage);
//            }
        };
    }

    /**
     * 打开系统文件选择器
     */
    private void showFileChooser() {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                // 每行显示个数 int
                .imageSpanCount(3)
                // 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .selectionMode(PictureConfig.SINGLE)
                // glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .sizeMultiplier(0.5f)
                // 是否压缩 true or false
                .compress(true)
                // 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .previewEggs(true)
                // 小于100kb的图片不压缩
                .minimumCompressSize(100)
                //结果回调onActivityResult code
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    class AndroidToJs extends Object {
        @JavascriptInterface
        public boolean backHost() {
            Log.d(TAG, "AndroidToJs: backHost 首页");
            runOnUiThread(Holo2Activity.this::finish);
            return true;
        }

        @JavascriptInterface
        public boolean goProduct(String sku_id) {
            Log.d(TAG, "AndroidToJs: goProduct 查看商品详情");
            ActivityToActivity.toActivity(ARouterConfig.home.SHOPPINGDETAILSACTIVITY, "id", sku_id);
            return true;
        }

        @JavascriptInterface
        public boolean customizePhotoSaving(String base64img) {
            Log.d(TAG, "AndroidToJs: customizePhotoSaving 保存图片");
            CustomProgressDialog.show(activity);
            String defType = "jpeg";
            if (base64img.contains(";base64,")) {
                String[] strs = base64img.split(";base64,");
                base64img = strs[1];

                defType = "." + strs[0].split("/")[1];
            }
            //保存到相册,查询路径是否存在，如果不存在则创建
            File basePath = new File(holoSavePath);
            if (!basePath.exists()) {
                basePath.mkdirs();
            }
            String path = holoSavePath + File.separator + "mps_save_holo_" + System.currentTimeMillis() + defType;
            Log.d(TAG, "AndroidToJs: customizePhotoSaving 图片路径 -> (" + path + ")");
            boolean isOK = Img2Base64Util.base64ToFile(base64img, path);

            if (isOK) {
                Uri contentUri = Uri.fromFile(new File(path));
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri);
                sendBroadcast(mediaScanIntent);
                CustomProgressDialog.stop();
                TextDialog.showBaseDialog(activity, "", "已保存到系统相册").show();
            } else {
                TextDialog.showBaseDialog(activity, "", "保存失败").show();
            }
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    for (LocalMedia lm : selectList) {
                        if (lm.isCompressed()) {
                            picPath = lm.getCompressPath();
                        } else if (lm.isCut()) {
                            picPath = lm.getCutPath();
                        } else {
                            picPath = lm.getPath();
                        }
                    }
                    File file = new File(picPath);
                    if (!file.exists()) {
                        ToastUtils.showShort("图片未找到！");
                        return;
                    }
                    Uri[] results = new Uri[]{Uri.fromFile(file)};
                    mFilePathCallback.onReceiveValue(results);
                    mFilePathCallback = null;
                    break;
                default:
                    break;
            }
        }
        if (mFilePathCallback != null) {
            mFilePathCallback.onReceiveValue(null);
            mFilePathCallback = null;
        }
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    protected Object getStateEventKey() {
        return "Holo2Activity";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_holo;
    }
}
