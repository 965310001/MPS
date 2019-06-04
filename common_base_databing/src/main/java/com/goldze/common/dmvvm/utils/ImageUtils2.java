package com.goldze.common.dmvvm.utils;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.goldze.common.dmvvm.R;
import com.goldze.common.dmvvm.adapter.BannerImgAdapter;
import com.goldze.common.dmvvm.adapter.BaseBannerAdapter;
import com.goldze.common.dmvvm.manage.BlurTransformation;
import com.goldze.common.dmvvm.utils.log.QLog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.tmall.ultraviewpager.UltraViewPager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.goldze.common.dmvvm.utils.ResourcesUtils.getResources;

/**
 * @author GuoFeng
 * @date :2019/1/16 18:05
 * @description: 图片工具类
 */
public class ImageUtils2 {

    /**
     * 图片类型
     */
    public enum ImageType {
        CIRCLE,
        CENTER,
        FITCENTER,
        CIRCLECROPTRANSFORM
    }

    /**
     * 加载多张图片
     *
     * @param activity
     * @param images
     */
    public static void loadImages(Activity activity, int position, List<String> images) {
        if (null != images && images.size() > 0) {
            List<LocalMedia> selectList = new ArrayList<>();
            LocalMedia localMedia;
            for (String image : images) {
                localMedia = new LocalMedia();
                localMedia.setPath(image);
                selectList.add(localMedia);
            }
            PictureSelector.create(activity).themeStyle(R.style.picture_default_style).openExternalPreview(position, selectList);
        }
    }

    /**
     * 加载网络、本地图片
     *
     * @param imageView
     * @param object    url，资源Id
     */
    public static void loadImage(ImageView imageView, Object object) {
        loadImage(imageView, object, null);
    }

    public static void loadImage(ImageView imageView, Object object, ImageType imageType) {
        RequestManager requestManager = Glide.with(Utils.getApplication());
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_loading_image)
                .error(new ColorDrawable(Color.WHITE))
                .fallback(new ColorDrawable(Color.RED));
        if (null != imageType) {
            switch (imageType) {
                case CIRCLE:
                    requestOptions.circleCrop();
                    break;
                case CENTER:
                    requestOptions.centerCrop();
                    break;
                case FITCENTER:
                    requestOptions.fitCenter();
                    break;
                case CIRCLECROPTRANSFORM:
                    requestOptions.circleCropTransform();
                    break;
            }
        }
        try {
            if (object instanceof String) {
                String url = object.toString();
                if (TextUtils.isEmpty(url)) {
                    return;
                }
                if (url.endsWith(".gif") || url.endsWith(".GIF")) {
                    requestManager.asGif()
                            .load(url)
                            .apply(requestOptions)
                            .into(imageView);
                } else if (url.startsWith("http")) {
                    requestManager.load(url)
                            .apply(requestOptions)
                            .into(imageView);
                }
            } else if (object instanceof Long) {
                requestManager.asGif()
                        .load(Long.valueOf(object.toString()))
                        .apply(requestOptions)
                        .into(imageView);
            } else if (object instanceof Integer) {
                requestManager.asGif()
                        .load(Integer.valueOf(object.toString()))
                        .apply(requestOptions)
                        .into(imageView);
            } else {
                QLog.i("请输入正确的图片地址");
                return;
            }
        } catch (Exception e) {
            QLog.i(e.toString());
        }
    }

    public static int overrideHight = 0;
    public static int overrideWidth = 0;

    public static int getOverrideHight(Context context) {
        if (overrideHight == 0) {
            overrideHight = getOverrideWidth(context) / 640 * 260;
        }
        return overrideHight;
    }

    public static int getOverrideWidth(Context context) {
        if (overrideWidth == 0) {
            overrideWidth = ScreenUtil.getScreenWidth(context);
        }
        return overrideWidth;
    }

    /**
     * 加载网络图片
     *
     * @param url       url
     * @param imageView imageView
     * @param imageView transformation 转换器
     */
    public static void loadImage(ImageView imageView, String url, int width, int hight) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions()
                        .fitCenter()
                        .placeholder(R.drawable.ic_loading_image)
                        .override(width, hight)
                        .error(new ColorDrawable(Color.WHITE)))
                .into(imageView);
    }

    /**
     * 加载网络图片,首页专用
     *
     * @param url       url
     * @param imageView imageView
     * @param imageView transformation 转换器
     */
    public static void loadHomeListImage(ImageView imageView, String url, int width, int hight) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions()
                        .fitCenter()
//                        .override(width, hight)
                        .error(new ColorDrawable(Color.WHITE)))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }

    /**
     * 加载网络图片,带圆角
     *
     * @param url       url
     * @param imageView imageView
     * @param width     width
     * @param hight     hight
     */
    public static void loadImageCorners(ImageView imageView, String url, int width, int hight, int radius) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions().transform(new GlideRoundTransform(radius))
                        .fitCenter()
                        .placeholder(R.drawable.ic_loading_image)
                        .override(width, hight)
                        .error(new ColorDrawable(Color.WHITE)))
                .into(imageView);
    }

    /**
     * 加载网络图片
     *
     * @param url       url
     * @param radius    圆角半径
     * @param imageView imageView
     */
    public static void loadImageCorners(ImageView imageView, int radius, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions().transform(new GlideRoundTransform(radius))
                        .placeholder(R.drawable.ic_loading_image)
                        .override(300, 300)
                        .error(new ColorDrawable(Color.WHITE)))
                .into(imageView);
    }


    /**
     * 加载圆形
     *
     * @param url       url
     * @param imageView imageView
     */
    public static void loadImageCircle(ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(Utils.getApplication())
                .load(url)
                .apply(new RequestOptions().circleCrop()
                        .placeholder(R.drawable.ic_loading_image)
                        .error(new ColorDrawable(Color.TRANSPARENT))
                        .fallback(new ColorDrawable(Color.RED)))
                .into(imageView);
    }

    /**
     * 加载高斯模糊图
     *
     * @param imageView imageView
     * @param url       url
     */
    public static void loadImageBlur(ImageView imageView, String url) {
        Glide.with(imageView)
                .load(url)
                .apply(RequestOptions.bitmapTransform(
                        new BlurTransformation(Utils.getApplication())))
                .into(imageView);
    }

    /**
     * 加载只有一张图的Banner
     *
     * @param banner   banner
     * @param imgUrl   imgUrl
     * @param listener listener
     */
    public static void loadBanner(ConvenientBanner banner, List<String> imgUrl, OnItemClickListener listener) {
        banner.setPages(new BannerImgAdapter(), imgUrl)
                .setPageIndicator(new int[]{R.drawable.shape_item_index_white, R.drawable.shape_item_index_red})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .setOnItemClickListener(listener)
                .startTurning();
        banner.setCanLoop(true);
    }

    /**
     * 通用轮播图
     *
     * @param ultraViewPager 必须是UltraViewPager
     * @param adapter        适配器
     */
    public static void createBanner(UltraViewPager ultraViewPager, BaseBannerAdapter adapter) {
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        ultraViewPager.setAdapter(adapter);
        //内置indicator初始化
        ultraViewPager.initIndicator();
        //设置indicator样式
        ultraViewPager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(Color.GREEN)
                .setNormalColor(Color.GRAY)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics()));
        //设置indicator对齐方式
        ultraViewPager.getIndicator().setGravity(Gravity.RIGHT | Gravity.BOTTOM);
        ultraViewPager.getIndicator().setMargin(0, 0, 16, 16);
        //构造indicator,绑定到UltraViewPager
        ultraViewPager.getIndicator().build();
        //设定页面循环播放
        ultraViewPager.setInfiniteLoop(true);
        //设定页面自动切换
        ultraViewPager.setAutoScroll(3500);
    }

    /**
     * 压缩图片
     *
     * @param filePath 图片地址
     * @return File
     */
    @Nullable
    public static File compressImage(String filePath) {
        if (!FileUtils.isExistExternalStore()) {
            return null;
        }
        int quality = 100;
        Bitmap bm = getSmallBitmap(filePath);//获取一定尺寸的图片
        int degree = readPictureDegree(filePath);//获取相片拍摄角度
        if (degree != 0) {//旋转照片角度，防止头像横着显示
            bm = rotateBitmap(bm, degree);
        }
        File outputFile = null;
        try {
            outputFile = new File(FileUtils.getTempPath(), "temp_" + DateUtils.getCurrentTimeStamp() + ".jpg");
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
            } else {
                outputFile.delete();
            }
            FileOutputStream out = new FileOutputStream(outputFile);
            bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bm.recycle();
        }
        return outputFile;
    }

    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 获取照片角度
     *
     * @param path
     * @return
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转照片
     *
     * @param bitmap
     * @param degress
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

    /**
     * 根据链接返回图片
     *
     * @param url
     */
    public static void loadImage(Context context, String url, SimpleTarget simpleTarget) {
        Glide.with(Utils.getApplication())
                .asDrawable()
                .load(url)
                .into(simpleTarget);
    }
}