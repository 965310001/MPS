package com.mingpinmall.classz.ui.activity.holo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.dialog.TextDialog;
import com.goldze.common.dmvvm.widget.loading.CustomProgressDialog;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.adapter.HolosAdapter;
import com.mingpinmall.classz.databinding.ActivityHolo3Binding;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.HolosBean;
import com.xiaopo.flying.sticker.BitmapStickerIcon;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.ZoomIconEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author 小斌
 * @data 2019/6/1
 **/
@Route(path = ARouterConfig.classify.HOLO3ACTIVITY)
public class Holo3Activity extends AbsLifecycleActivity<ActivityHolo3Binding, ClassifyViewModel> {

    @Autowired
    String goods_id;

    @Autowired
    String cart_count = "0";//购物车内商品数量

    private HolosAdapter holosAdapter;

    private Camera mCamera;
    private SurfaceHolder surfaceHolder;
    private boolean previewing;

    /**
     * 是否前置摄像机
     */
    private boolean isFront = true;

    private String imagUrl = "";

    int mCurrentCamIndex = 0;
    SurfaceViewCallback surfaceViewCallback;
    /**
     * 只对焦不拍照
     */
    private Camera.AutoFocusCallback myAutoFocusCallback1 = null;
    public static final int only_auto_focus = 110;
    int issuccessfocus = 0;

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case only_auto_focus:
                    if (mCamera != null) {
                        mCamera.autoFocus(myAutoFocusCallback1);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);

        //TODO 测试
        goods_id = "110381";

        holosAdapter = new HolosAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerView.setAdapter(holosAdapter);

        binding.setCount(Integer.parseInt(cart_count));
        setTitlePadding(binding.layoutTop);
        binding.group.setDrawingCacheEnabled(true);
        binding.group.buildDrawingCache();

        BitmapStickerIcon zoomIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_scale_white_18dp),
                BitmapStickerIcon.RIGHT_BOTOM);
        zoomIcon.setIconEvent(new ZoomIconEvent());
        binding.svSticker.setIcons(Arrays.asList(zoomIcon));
        binding.svSticker.setLocked(false);
        binding.svSticker.setConstrained(true);

        surfaceViewCallback = new SurfaceViewCallback();
        surfaceHolder = binding.sfvSurface.getHolder();
        surfaceHolder.addCallback(surfaceViewCallback);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        setListener();
    }

    private void setListener() {
        holosAdapter.setOnItemClickListener((adapter, view, position) -> {
            //选择眼镜试戴
            holosAdapter.setSelIndex(position);
            HolosBean itemBean = holosAdapter.getItem(position);
            addSticker(itemBean.getGoods_image());
        });
        binding.btnAction.setOnClickListener(v -> {
            // TODO 加入购物车
        });
        binding.llCart.setOnClickListener(v -> {
            // 跳转到购物车
            ActivityToActivity.toActivity(ARouterConfig.cart.SHOPCARTACTIVITY);
        });
        binding.ivBack.setOnClickListener(v -> finish());
        binding.ivSwitch.setOnClickListener(v -> {
            // 切换 前置 or 后置相机
            mCamera.stopPreview();
            mCamera.autoFocus(null);
            mCamera.release();
            if (isFront) {
                mCamera = openCamera(false);
                ToastUtils.showShort("切换到后置相机");
            } else {
                mCamera = openCamera(true);
                ToastUtils.showShort("切换到前置相机");
            }
            startPreview(binding.sfvSurface.getHolder(), binding.sfvSurface.getHolder().getSurfaceFrame().width(),
                    binding.sfvSurface.getHolder().getSurfaceFrame().height());
        });
        binding.llTakePicture.setOnClickListener(v -> {
            // 拍照
            if (previewing) {
                CustomProgressDialog.show(activity);
                mCamera.takePicture(shutterCallback, rawPictureCallback, jpegPictureCallback);
            }
        });
        binding.sfvSurface.setOnClickListener(v -> {
            //点击对焦
            if (mCamera != null) {
                mCamera.autoFocus(myAutoFocusCallback1);
            }
        });
        myAutoFocusCallback1 = (success, camera) -> {
            //对焦监听
            if (success) {
                //success表示对焦成功
                issuccessfocus++;
                if (issuccessfocus <= 1) {
                    mHandler.sendEmptyMessage(only_auto_focus);
                }
                Log.i("qtt", "myAutoFocusCallback1: success..." + issuccessfocus);
            } else {
                //if (issuccessfocus == 0) {
                mHandler.sendEmptyMessage(only_auto_focus);
                //}
                Log.i("qtt", "myAutoFocusCallback1: 失败...");
            }
        };
    }

    /**
     * 添加覆盖层
     *
     * @param goods_image
     */
    private void addSticker(String goods_image) {
        Glide.with(activity)
                .load(goods_image)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        if (binding.svSticker.isNoneSticker()) {
                            binding.svSticker.addSticker(new DrawableSticker(resource));
                        } else {
                            binding.svSticker.replace(new DrawableSticker(resource), true);
                        }
                    }
                });
    }

    @Override
    protected void initData() {
        super.initData();
        mViewModel.getHoloImages(goods_id, Constants.HOLO_IMAGES);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.HOLO_IMAGES, Object.class).observeForever(result -> {
            if (result instanceof String) {
                TextDialog.showBaseDialog(activity, "获取错误", result.toString(), dialog -> finish());
            } else {
                List<HolosBean> data = (List<HolosBean>) result;
                Log.d(TAG, "dataObserver: " + data.size());
                holosAdapter.setNewData(data);
                HolosBean item;
                for (int i = 0; i < data.size(); i++) {
                    item = data.get(i);
                    if (item.getGoods_id().equals(goods_id)) {
                        holosAdapter.setSelIndex(i);
                        addSticker(item.getGoods_image());
                        break;
                    }
                }
            }
        });
    }

    Camera.ShutterCallback shutterCallback = () -> {
    };

    Camera.PictureCallback rawPictureCallback = (arg0, arg1) -> {
    };

    Camera.PictureCallback jpegPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bitmapdata, Camera arg1) {
            Log.i("HOLO3", "onPictureTaken: ");
            binding.ivPhoto.setVisibility(View.VISIBLE);

            new Thread(() -> {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
                final Bitmap resultBitmap;
                if (isFront) {
                    Matrix scale = new Matrix();
                    scale.setScale(-1, 1);//水平翻转
                    bitmap = rotaingImageView(270, bitmap);
                    resultBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), scale, true);
                    bitmap.recycle();
                } else {
                    resultBitmap = rotaingImageView(90, bitmap);
                }
                runOnUiThread(() -> {
                    binding.ivPhoto.setImageBitmap(resultBitmap);
                    saveNewPhoto();
                });

            }).start();
        }

        /**
         * 保持合成后的图片
         */
        private void saveNewPhoto() {
            new Thread(() -> {
                Bitmap bitmap = binding.group.getDrawingCache();
                saveImageToGallery(activity, bitmap);
            }).start();
            CustomProgressDialog.stop();
            TextDialog.showBaseDialog(activity, "", "已保存到系统相册").show();
            binding.ivPhoto.setVisibility(View.INVISIBLE);
            binding.ivPhoto.setImageDrawable(null);
            mCamera.startPreview();
        }
    };

    /**
     * 旋转
     *
     * @param angle
     * @param bitmap
     * @return
     */
    private Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
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
        Log.i("HOLO3", "saveImageToGallery: ");
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
        imagUrl = file.getAbsolutePath();
        // 最后通知图库更新
        Uri contentUri = Uri.fromFile(new File(imagUrl));
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri);
        sendBroadcast(mediaScanIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private final class SurfaceViewCallback implements android.view.SurfaceHolder.Callback {
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (previewing) {
                mCamera.stopPreview();
                previewing = false;
            }
            startPreview(holder, width, height);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mCamera = openCamera(true);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
            previewing = false;
        }
    }

    /**
     * 开始预览
     *
     * @param holder
     * @param width
     * @param height
     */
    private void startPreview(SurfaceHolder holder, int width, int height) {
        try {
            mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Camera.Parameters parameters = mCamera.getParameters();
        Camera.Size size = getOptimalSize(parameters.getSupportedPreviewSizes(), width, height);
        parameters.setPreviewSize(size.width, size.height);
        mCamera.setParameters(parameters);
        setCameraDisplayOrientation(activity, mCurrentCamIndex, mCamera);
        mCamera.startPreview();
        previewing = true;
    }

    // 0表示后置，1表示前置
    private int cameraPosition = 1;

    private Camera openCamera(boolean change2Front) {
        int cameraCount = 0;
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();// 得到摄像头的个数
        if (cameraCount < 1) {
            TextDialog.showBaseDialog(activity, "错误", "手机不支持相机功能", dialog -> finish());
            finish();
            return mCamera;
        }
        if (cameraCount == 1 && change2Front) {
            ToastUtils.showShort("不支持切换相机");
            return mCamera;
        } else {
            if (change2Front) {
                //切换到前置相机
                cameraPosition = 1;
                this.isFront = true;
            } else {
                //切换到后置相机
                cameraPosition = 0;
                this.isFront = false;
            }
        }
        Camera.getCameraInfo(cameraPosition, cameraInfo);// 得到每一个摄像头的信息
        cam = Camera.open(cameraPosition);
        return cam;
    }

    // 根据横竖屏自动调节preview方向
    private static void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
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