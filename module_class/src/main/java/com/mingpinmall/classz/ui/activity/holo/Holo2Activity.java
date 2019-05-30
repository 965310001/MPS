package com.mingpinmall.classz.ui.activity.holo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.FileUtils;
import com.goldze.common.dmvvm.utils.Img2Base64Util;
import com.goldze.common.dmvvm.utils.ToastUtils;
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
import com.socks.library.KLog;

import org.w3c.dom.Text;

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

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);
        setTitle("试戴");
        KLog.i(url);
        mBridgeWebView = new BridgeWebView(activity);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(findViewById(R.id.fl_content), new FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebViewClient(getWebViewClient())
                .setWebChromeClient(new WebChromeClient() {
                    @Override
                    public void onConsoleMessage(String message, int lineNumber, String sourceID) {
                        Log.i("console", message + "(" + sourceID + ":" + lineNumber + ")");
                        super.onConsoleMessage(message, lineNumber, sourceID);
                    }

                    @Override
                    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                        Log.i("console", "[" + consoleMessage.messageLevel() + "] " + consoleMessage.message() + "(" + consoleMessage.sourceId() + ":" + consoleMessage.lineNumber() + ")");
                        return super.onConsoleMessage(consoleMessage);
                    }
                })
                .setWebView(mBridgeWebView)
                .createAgentWeb()
                .ready()
                .get();
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


    class AndroidToJs extends Object {
        @JavascriptInterface
        public boolean backHost() {
            Log.d(TAG, "AndroidToJs: backHost 首页");
            runOnUiThread(() -> mAgentWeb.getUrlLoader().loadUrl(url));
            return true;
        }

        @JavascriptInterface
        public boolean customizeCamera() {
            Log.d(TAG, "AndroidToJs: customizeCamera 这是什么？");
            return true;
        }

        @JavascriptInterface
        public boolean goProduct(String sku_id) {
            Log.d(TAG, "AndroidToJs: goProduct 查看商品详情");
            ActivityToActivity.toActivity(ARouterConfig.home.SHOPPINGDETAILSACTIVITY, "id", sku_id);
            return true;
        }

        @JavascriptInterface
        public boolean chooseModelImage() {
            Log.d(TAG, "AndroidToJs: chooseModelImage 选择照片/拍照");
            runOnUiThread(() -> PictureSelector.create(activity)
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
                    .forResult(PictureConfig.CHOOSE_REQUEST));
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
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
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
                    String imageSrc = "data:image/png;base64," + Img2Base64Util.imageToBase64(picPath);

                    mAgentWeb.getJsAccessEntrace().quickCallJs("previewImage", imageSrc);
                    break;
                default:
                    break;
            }
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
