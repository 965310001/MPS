package com.mingpinmall.classz.ui.activity.holo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.log.QLog;
import com.just.agentweb.WebViewClient;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.ActivityHoloBinding;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 试戴
 */
@Route(path = ARouterConfig.classify.HOLOACTIVITY)
public class HoloActivity extends AbsLifecycleActivity<ActivityHoloBinding, ClassifyViewModel> {

    @Autowired
    String url;
    private WebView webview;
    //    private AgentWeb mAgentWeb;
    private static final int INPUT_FILE_REQUEST_CODE = 1;
    private static final int FILECHOOSER_RESULTCODE = 1;
    private ValueCallback<Uri[]> mFilePathCallback;
    private Uri mCapturedImageURI = null;
    private String mCameraPhotoPath;
    private ValueCallback<Uri> mUploadMessage;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                }
            }
        }
        super.initViews(savedInstanceState);

        setTitle("试戴");
        QLog.i(url);
        webview = binding.wv;
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setUserAgentString(settings.getUserAgentString() + "GZDZWL");
        settings.setAllowFileAccess(true);
        settings.setDomStorageEnabled(true);//打开DOM存储API
        settings.setBlockNetworkImage(false);//同步请求图片
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 设置缓存模式
        webview.addJavascriptInterface(new AndroidToJs(), "app");

        webview.setWebChromeClient(new WebChromeClient());
        settings.setAppCacheEnabled(true);// 设置缓存

//        settings.setBuiltInZoomControls(true);
//        settings.setDefaultTextEncodingName("UTF-8");
//        settings.setUseWideViewPort(true);
//        settings.setLoadWithOverviewMode(true);
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
//        settings.setJavaScriptCanOpenWindowsAutomatically(true); //设置允许js弹出alert对话框。
//        settings.setGeolocationEnabled(true); //设置可以定位
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
                super.onReceivedError(webView, webResourceRequest, webResourceError);
            }
        });

        webview.setWebChromeClient(getWebChromeClient());

//        webview.setWebViewClient(new MyWebClient());

//        int version = Build.VERSION.SDK_INT;
//        if (version < Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            webview.loadUrl("javascript:callJS()");
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webview.getSettings().setMixedContentMode(
//                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
//        } else {
//
//        }
//        webview.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
//            @Override
//            public void onReceiveValue(String value) {
//                //此处为 js 返回的结果
//            }
//        });

//        QLog.i(url);
//        url = "https://www.feeai.cn/fitting/?shop_id=1097e32594e07daf671d50ad93fca1a9&shop_secret=daa98398d09cf846869c0fd2094d08df#/";
//        url = "https://www.feeai.cn/fitting/?shop_id=1097e32594e07daf671d50ad93fca1a9&shop_secret=daa98398d09cf846869c0fd2094d08df#/";
//        url = "https://www.feeai.cn/fitting/?shop_id=1097e32594e07daf671d50ad93fca1a9&shop_secret=daa98398d09cf846869c0fd2094d08df#/";
        //TODO 6/4 测试
        url = "https://www.feeai.cn/fitting/?shop_id=1097e32594e07daf671d50ad93fca1a9&shop_secret=daa98398d09cf846869c0fd2094d08df&cat_id=7&goods_id=361&version=189";

//        url = "https://www.feeai.cn/fitting/?shop_id=1097e32594e07daf671d50ad93fca1a9&shop_secret=daa98398d09cf846869c0fd2094d08df#/";
//        url="https://www.feeai.cn/wxapi/h5/fitting/demo.php?goods_id=37&cat_id=7";
        webview.loadUrl(url);

        QLog.i(webview.getSettings().getUserAgentString() + url);

//        webview.loadUrl("https://www.feeai.cn/wxapi/h5/fitting/demo.php?goods_id=37&cat_id=7");
//        webview.loadUrl("https://www.feeai.cn/fitting/?shop_id=1097e32594e07daf671d50ad93fca1a9&shop_secret=daa98398d09cf846869c0fd2094d08df#/");
//        webview.loadUrl("https://www.feeai.cn/fitting/?shop_id=1097e32594e07daf671d50ad93fca1a9&shop_secret=daa98398d09cf846869c0fd2094d08df#/");
//        webview.loadUrl("http://www.feeai.cn/fitting/");
//        webview.loadUrl("https://www.feeai.cn/fitting?version=2");
//        ToastUtils.showLong("测试");

    }

    private WebChromeClient getWebChromeClient() {
        return new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            // For Android 5.0
            public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> filePath, FileChooserParams fileChooserParams) {
                // Double check that we don't have any existing callbacks
                if (mFilePathCallback != null) {
                    mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = filePath;
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                        takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        Log.e("Holo", "Unable to create Image File", ex);
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                    } else {
                        takePictureIntent = null;
                    }
                }
                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("image/*");
                Intent[] intentArray;
                if (takePictureIntent != null) {
                    intentArray = new Intent[]{takePictureIntent};
                } else {
                    intentArray = new Intent[0];
                }
                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);
                return true;
            }

            // openFileChooser for Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;
                // Create AndroidExampleFolder at sdcard
                // Create AndroidExampleFolder at sdcard
                File imageStorageDir = new File(
                        Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES)
                        , "AndroidExampleFolder");
                if (!imageStorageDir.exists()) {
                    // Create AndroidExampleFolder at sdcard
                    imageStorageDir.mkdirs();
                }
                // Create camera captured image file path and name
                File file = new File(
                        imageStorageDir + File.separator + "IMG_"
                                + String.valueOf(System.currentTimeMillis())
                                + ".jpg");
                mCapturedImageURI = Uri.fromFile(file);
                // Camera capture image intent
                final Intent captureIntent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                // Create file chooser intent
                Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
                // Set camera intent to file chooser
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS
                        , new Parcelable[]{captureIntent});
                // On select image call onActivityResult method of activity
                startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
            }

            // openFileChooser for Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                openFileChooser(uploadMsg, "");
            }

            //openFileChooser for other Android versions
            public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                        String acceptType,
                                        String capture) {
                openFileChooser(uploadMsg, acceptType);
            }
        };
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode != INPUT_FILE_REQUEST_CODE || mFilePathCallback == null) {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }
            Uri[] results = null;
            // Check that the response is a good one
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    // If there is not data, then we may have taken a photo
                    if (mCameraPhotoPath != null) {
                        results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                    }
                } else {
                    String dataString = data.getDataString();
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                }
            }
            mFilePathCallback.onReceiveValue(results);
            mFilePathCallback = null;
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            if (requestCode != FILECHOOSER_RESULTCODE || mUploadMessage == null) {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }
            if (requestCode == FILECHOOSER_RESULTCODE) {
                if (null == this.mUploadMessage) {
                    return;
                }
                Uri result = null;
                try {
                    if (resultCode != RESULT_OK) {
                        result = null;
                    } else {
                        // retrieve from the private variable if the intent is null
                        result = data == null ? mCapturedImageURI : data.getData();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "activity :" + e,
                            Toast.LENGTH_LONG).show();
                }
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }
        return;
    }

    class AndroidToJs extends Object {
        @JavascriptInterface
        public boolean customizeCamera() {
            QLog.i("处理 customizeCamera");
            return true;
        }

        @JavascriptInterface
        public boolean chooseModelImage() {
            QLog.i("处理 chooseModelImage");
            return true;
        }

        @JavascriptInterface
        public void customizePhotoSaving(String imgSrc) {
            QLog.i("自定义保存图像");
            Log.i("Holo", "自定义保存");
        }

        @JavascriptInterface
        public void backHost() {
            QLog.i("返回App");
        }

    }

//    private class MyWebClient extends WebViewClient {
////        @Override
////        public boolean shouldOverrideUrlLoading(WebView view, String url) {
////            QLog.i("shouldOverrideUrlLoading");
////
//////            Map<String, String> extraHeaders = new HashMap<String, String>();
//////            webview.loadUrl(url);
//////            extraHeaders.put("Referer", "http://wxpay.wxutil.com");
//////            view.loadUrl(url);
////
////            view.loadUrl("javascript:innerChooseModelImage()");
////
////            return false;
////        }
//
//        @Override
//        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//            QLog.i("onReceivedSslError");
//            handler.proceed(); // 接受所有网站的证书
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
////            tvTitle.setText(view.getTitle());
//            showSuccess();
//        }
//
//    }

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
    public void onDestroy() {
        super.onDestroy();
        webview.setWebViewClient(null);
        webview.setWebChromeClient(null);
        webview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        webview.clearHistory();

        ((ViewGroup) webview.getParent()).removeView(webview);
        webview.destroy();
        webview = null;
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
