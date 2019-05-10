package com.mingpinmall.home.ui.activity.qrcode;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;

import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.widget.dialog.TextDialog;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.mingpinmall.home.R;
import com.mingpinmall.home.databinding.ActivityQrcodeBinding;

import java.util.List;

/**
 * 功能描述：二维码扫描处理
 * @author 小斌
 * @date 2019/4/4
 **/
public class ScanQrCodeActivity extends BaseActivity<ActivityQrcodeBinding> {

    private CaptureManager capture;

    private BarcodeCallback barcodeCallback = new BarcodeCallback() {
        @Override
        public void barcodeResult(final BarcodeResult result) {
            binding.bvBarcode.pause();
            if (result != null) {
                Log.e(getClass().getName(), "获取到的扫描结果是：" + result.getText());
                String content = result.getText();
                if (content.contains("?")) {
                    String[] strs = content.split("\\?");
                    for (String str : strs) {
                        if (str.contains("goods_id")) {
                            String goods_id = str.replace("goods_id=", " ").trim();
                            //跳转到商品详情
                            if (str.contains("&")) {
                                goods_id = goods_id.split("&")[0];
                            }
                            Log.i("二维码扫描", "商品ID: " + goods_id);
                            ActivityToActivity.toActivity(ARouterConfig.home.SHOPPINGDETAILSACTIVITY, "id", goods_id);
                            return;
                        }
                    }
                }

                TextDialog.showBaseDialog(activity, "复制到剪切板", result.getText(),
                        new TextDialog.SingleButtonCallback() {
                            @Override
                            public void onClick() {
                                ClipboardManager myClipboard;
                                myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                                ClipData myClip;
                                myClip = ClipData.newPlainText("text", result.getText());
                                myClipboard.setPrimaryClip(myClip);
                            }
                        })
                        .setOnDismiss(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                binding.bvBarcode.resume();
                            }
                        })
                        .show();
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qrcode;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("扫一扫");
        capture = new CaptureManager(this, binding.bvBarcode);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();
        binding.bvBarcode.decodeContinuous(barcodeCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return binding.bvBarcode.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

}
