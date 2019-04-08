package com.mingpinmall.home.ui.activity.qrCode;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.widget.dialog.MaterialDialogUtils;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.mingpinmall.home.R;
import com.mingpinmall.home.databinding.ActivityQrcodeBinding;

import java.util.List;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/4
 **/
public class ScanQRCodeActivity extends BaseActivity<ActivityQrcodeBinding> {

    private CaptureManager capture;

    private BarcodeCallback barcodeCallback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            binding.bvBarcode.pause();
            if (result != null){
                Log.e(getClass().getName(), "获取到的扫描结果是：" + result.getText());

                MaterialDialogUtils.showBasicDialog(ScanQRCodeActivity.this, "这是你要的结果吗？")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                //已获取到需要的结果，进行相应的操作
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                //不是想要的结果，继续扫描
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return binding.bvBarcode.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

}
