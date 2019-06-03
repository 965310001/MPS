package com.mingpinmall.classz.ui.activity.holo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 小斌
 * @data 2019/6/1
 **/
@Route(path = ARouterConfig.classify.HOLO4ACTIVITY)
public class Holo4Activity extends AbsLifecycleActivity<ActivityHolo3Binding, ClassifyViewModel> {

    @Autowired
    String goods_id;

    @Autowired
    String cart_count = "0";//购物车内商品数量

    private CameraHelper cameraHelper;
    private HolosAdapter holosAdapter;

    private String imagUrl;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);

        cameraHelper = new CameraHelper(activity, binding.sfvSurface, callBack);
        //开启人脸识别
//        cameraHelper.startFaceDetect();

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
            cameraHelper.exchangeCamera();
        });
        binding.llTakePicture.setOnClickListener(v -> {
            // 拍照
            CustomProgressDialog.show(activity);
            cameraHelper.takePic();
        });
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
                        //添加到 人脸检测 绘制
                        binding.faceView.setImage(ContextCompat.getDrawable(activity, R.drawable.test_yanjing));
//                        binding.faceView.setImage(resource);
                        //添加贴图
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

    CameraHelper.CallBack callBack = new CameraHelper.CallBack() {
        @Override
        public void onPostBestSize(Camera.Size size) {
            //最佳预览大小
            ConstraintLayout constraintLayout = (ConstraintLayout) binding.group.getParent();
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            constraintSet.setDimensionRatio(R.id.group, size.height + ":" + size.width);
            constraintLayout.setConstraintSet(constraintSet);
        }

        @Override
        public void onPreviewFrame(byte[] data) {
            //预览
        }

        @Override
        public void onTakePic(byte[] data) {
            Log.i("HOLO4", "拍照");
            binding.ivPhoto.setVisibility(View.VISIBLE);
            new Thread(() -> {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                final Bitmap resultBitmap;
                if (cameraHelper.mCameraFacing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
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

        @Override
        public void onFaceDetect(ArrayList<RectF> faces) {
            //人脸检测
            Log.i("FaceView", "onFaceDetect: 人脸检测");
            binding.faceView.setFaces(faces);
            if (faces != null && faces.size() > 0) {
                RectF rectF = faces.get(0);
                Matrix matrix = new Matrix();
                matrix.mapRect(rectF);
            }
        }
    };

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
    }

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

    @Override
    protected Object getStateEventKey() {
        return "Holo4";
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
