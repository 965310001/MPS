package com.mingpinmall.classz.ui.activity.holo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.ActivityHolo3Binding;
import com.mingpinmall.classz.ui.activity.holo.tools.BitmapUtil;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.socks.library.KLog;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author 小斌
 * @data 2019/6/1
 **/
@Route(path = ARouterConfig.classify.HOLO3ACTIVITY)
public class Holo3Activity extends AbsLifecycleActivity<ActivityHolo3Binding, ClassifyViewModel> {

    private Camera mCamera;
    private SurfaceHolder surfaceHolder;
    private boolean previewing;

    private String imagUrl = "";

    int mCurrentCamIndex = 0;
    SurfaceViewCallback surfaceViewCallback;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTitlePadding(binding.layoutTop);
        binding.group.setDrawingCacheEnabled(true);
        binding.group.buildDrawingCache();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test_yanjing);
        binding.svSticker.setWaterMark(bitmap);

        surfaceViewCallback = new SurfaceViewCallback();
        surfaceHolder = binding.sfvSurface.getHolder();
        surfaceHolder.addCallback(surfaceViewCallback);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        binding.btnAction.setOnClickListener(v -> {
            // TODO 加入购物车
        });
        binding.ivCart.setOnClickListener(v -> {
            // TODO 跳转到购物车
        });
        binding.ivBack.setOnClickListener(v -> finish());
        binding.ivSwitch.setOnClickListener(v -> {
            // TODO 切换 前置 or 后置相机
        });
        binding.ivCamera.setOnClickListener(v -> {
            // 拍照
            if (previewing) {
                mCamera.takePicture(shutterCallback, rawPictureCallback, jpegPictureCallback);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
    }

    Camera.ShutterCallback shutterCallback = () -> {
    };

    Camera.PictureCallback rawPictureCallback = (arg0, arg1) -> {

    };

    Camera.PictureCallback jpegPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] arg0, Camera arg1) {
            save(arg0);
        }

        /**
         * 保存图片
         *
         * @param data
         */
        private void save(byte[] data) {
            String fileName = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM).toString()
                    + File.separator + System.currentTimeMillis() + ".jpg";
            File file = new File(fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }
            try {
                BufferedOutputStream bos = new BufferedOutputStream(
                        new FileOutputStream(file));
                bos.write(data);
                mCamera.stopPreview();
                previewing = false;
                bos.flush();
                bos.close();
                scanFileToPhotoAlbum(file.getAbsolutePath());
                configPhoto(file);
                saveNewPhoto();
            } catch (Exception e) {
            }
            mCamera.startPreview();
            previewing = true;
        }

        /**
         * 保持合成后的图片
         */
        private void saveNewPhoto() {
//            Bitmap bitmap = group.getDrawingCache();
//            iv_show.setImageBitmap(bitmap);
//            saveImageToGallery(MainActivity.this, bitmap);
//            iv_show.setVisibility(View.INVISIBLE);
//            to();
        }

        /**
         * 配置照片
         *
         * @param file
         */
        private void configPhoto(File file) {
            Bitmap imagbitmap = null;
            setCameraDisplayOrientation(activity, mCurrentCamIndex, mCamera);
            if (cameraPosition == 1) {
                imagbitmap = BitmapUtil.convert(BitmapUtil.rotaingImageView(
                        270, BitmapFactory.decodeFile(file.getAbsolutePath())),
                        0);
            } else {
                imagbitmap = BitmapUtil.decodeSampledBitmapFromResource(file.getAbsolutePath(), 200, 300);
                imagbitmap = BitmapUtil.rotaingImageView(90, imagbitmap);
            }
//            iv_show.setImageBitmap(imagbitmap);
//            iv_show.setVisibility(View.VISIBLE);
        }
    };

    public void scanFileToPhotoAlbum(String path) {
        MediaScannerConnection.scanFile(activity,
                new String[]{path}, null,
                (path1, uri) -> {
                });
    }

    private final class SurfaceViewCallback implements android.view.SurfaceHolder.Callback {
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (previewing) {
                mCamera.stopPreview();
                previewing = false;
            }
            try {
                mCamera.setPreviewDisplay(holder);
                Camera.Parameters parameters = mCamera.getParameters();
                Camera.Size size = getOptimalSize(parameters.getSupportedPreviewSizes(), width, height);
                parameters.setPreviewSize(size.width, size.height);
                mCamera.startPreview();
                previewing = true;
                setCameraDisplayOrientation(activity,
                        mCurrentCamIndex, mCamera);
            } catch (Exception e) {
            }
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mCamera = openFrontFacingCameraGingerbread();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
            previewing = false;
        }
    }

    // 0表示后置，1表示前置
    private int cameraPosition = 1;

    private Camera openBackFacingCameraGingerbread() {
        int cameraCount = 0;
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();// 得到摄像头的个数
        if (cameraCount > 1) {
            cameraPosition = 1;
        } else {
            cameraPosition = 0;
        }
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, cameraInfo);// 得到每一个摄像头的信息
            if (cameraPosition == 1) {
                // 现在是后置，变更为前置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    cam = Camera.open(i);
                    break;
                }
            } else {
                // 现在是前置， 变更为后置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    cam = Camera.open(i);
                    break;
                }
            }

        }

        return cam;
    }

    private Camera openFrontFacingCameraGingerbread() {
        int cameraCount = 0;
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();// 得到摄像头的个数
        if (cameraCount > 1) {
            cameraPosition = 1;
        } else {
            cameraPosition = 0;
        }
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, cameraInfo);// 得到每一个摄像头的信息
            if (cameraPosition == 1) {
                // 现在是后置，变更为前置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    cam = Camera.open(i);
                    break;
                }
            } else {
                // 现在是前置， 变更为后置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    cam = Camera.open(i);
                    break;
                }
            }

        }

        return cam;
    }

    // 根据横竖屏自动调节preview方向
    private static void setCameraDisplayOrientation(Activity activity,
                                                    int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();

        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
            default:
                break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        } else {

            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);

    }

    /**
     * 保存合成后的图片
     *
     * @param context
     * @param bmp
     */
    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String fileName = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM).toString()
                + File.separator + System.currentTimeMillis() + ".jpg";
        File file = new File(fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse(file.getAbsolutePath())));
        imagUrl = file.getAbsolutePath();
    }

    private static Camera.Size getOptimalSize(@NonNull List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) h / w;
        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) {
                continue;
            }
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }

        return optimalSize;
    }

    @Override
    protected Object getStateEventKey() {
        return "Holo3";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_holo3;
    }

    @Override
    protected boolean isActionBar() {
        return false;
    }
}
