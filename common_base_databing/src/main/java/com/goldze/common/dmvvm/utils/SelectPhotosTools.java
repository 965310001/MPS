package com.goldze.common.dmvvm.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ScrollView;

import com.goldze.common.dmvvm.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.goldze.common.dmvvm.adapter.SelectPhotoImageAdapter;
import com.goldze.common.dmvvm.base.bean.BaseSelectPhotos;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * 宫格展示图片 需要搭配RecyclerView食用
 * 使用方法：初始化时传入用于显示图片的recyclerView，然后在onActivityResult里调用：
 * onResultCall(requestCode, resultCode, data, scrollView);方法
 * scrollView参数可为空，此参数用于scrollView嵌套recyclerView的情况；
 * Created by qq321 on 2017/5/26 0026.
 */
public class SelectPhotosTools {

    private WeakReference<Object> context;
    private RecyclerView imageRecycler;
    private SelectPhotoImageAdapter imageAdapter;
    private OnCall onCallListener;

    //本地图片
    private List<BaseSelectPhotos> imagePath = new ArrayList<>();
    private List<LocalMedia> selectList = new ArrayList<>();
    //网络图片
    private List<BaseSelectPhotos> imageUrl = new ArrayList<>();
    private List<BaseSelectPhotos> deleteImageUrl = new ArrayList<>();

    private int maxSize = 9;//最大图片数量
    private int spanCount = 3;//横向排列几张

    public SelectPhotosTools(Activity activity, RecyclerView recyclerView) {
        this.context = new WeakReference<>(activity);
        this.imageRecycler = recyclerView;
        init();
    }

    public SelectPhotosTools(Fragment fragment, RecyclerView recyclerView) {
        this.context = new WeakReference<>(fragment);
        this.imageRecycler = recyclerView;
        init();
    }

    public void setOnDataChangeListener(SelectPhotoImageAdapter.ImageDataChangeListener onDataChangeListener) {
        imageAdapter.setOnDataChangeListener(onDataChangeListener);
    }

    public void setOnCallListener(OnCall onCallListener) {
        this.onCallListener = onCallListener;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        imageAdapter.setMaxSize(maxSize);
        imageAdapter.notifyDataSetChanged();
    }

    public int getSpanCount() {
        return spanCount;
    }

    public void setSpanCount(int spanCount) {
        this.spanCount = spanCount;
        initRV();
    }

    public SelectPhotoImageAdapter getImageAdapter() {
        return imageAdapter;
    }

    public void setImageUrl(List<BaseSelectPhotos> imageUrl) {
        this.imageUrl = imageUrl;
        imageAdapter.putPhotoPath(imageUrl);
    }

    public List<BaseSelectPhotos> getImagePath() {
        return imagePath;
    }

    public List<BaseSelectPhotos> getDeleteImageUrl() {
        return deleteImageUrl;
    }

    public void reSetRecyclerView(RecyclerView recyclerView) {
        this.imageRecycler = recyclerView;
        initRV();
    }

    public interface OnCall {
        void onCall(SelectPhotosTools photosTools);
    }

    public void init() {
        //初始化带删除监听的适配器
        imageAdapter = new SelectPhotoImageAdapter(pos -> {
            if (pos < imageUrl.size()) {
                deleteImageUrl.add(imageUrl.get(pos));
                imageUrl.remove(pos);
            } else {
                imagePath.remove(pos - imageUrl.size());
                selectList.remove(pos - imageUrl.size());
            }
        });
        //初始化
        initRV();
        imageRecycler.setNestedScrollingEnabled(false);
        imageRecycler.setAdapter(imageAdapter);
        //点击事件  点击打开选择图片或预览图片
        imageAdapter.setOpenListener((view, position) -> {
            PictureSelector selector;
            if (context.get() instanceof Activity) {
                selector = PictureSelector.create((Activity) context.get());
            } else if (context.get() instanceof Fragment) {
                selector = PictureSelector.create((Fragment) context.get());
            } else {
                Log.i("SelectPhotosTools", "init:没有传入activity或者fragment");
                return;
            }
            if (position + 1 == imageAdapter.getItemCount() && imageAdapter.getPhotoPath().size() < maxSize) {
                if (onCallListener != null) {
                    onCallListener.onCall(this);
                }
                selector.openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .maxSelectNum(maxSize - imageUrl.size())// 最大图片选择数量 int
                        .minSelectNum(1)// 最小选择数量 int
                        .imageSpanCount(3)// 每行显示个数 int
                        .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .previewImage(true)// 是否可预览图片 true or false
                        .isCamera(true)// 是否显示拍照按钮 true or false
                        .imageFormat(PictureMimeType.JPEG)// 拍照保存图片格式后缀,默认jpeg
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                        .compress(true)// 是否压缩 true or false
                        .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                        .isGif(true)// 是否显示gif图片 true or false
                        .selectionMedia(selectList)// 是否传入已选图片 List<LocalMedia> list
                        .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
//                      更多API：
//                            .previewVideo()// 是否可预览视频 true or false
//                            .enablePreviewAudio() // 是否可播放音频 true or false
//                            .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
//                            .enableCrop(true)// 是否裁剪 true or false
//                            .glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                            .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                            .compressSavePath(getPath())//压缩图片保存地址
//                            .freeStyleCropEnabled()// 裁剪框是否可拖拽 true or false
//                            .circleDimmedLayer()// 是否圆形裁剪 true or false
//                            .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
//                            .showCropGrid()// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
//                            .openClickSound()// 是否开启点击声音 true or false
//                            .cropCompressQuality()// 裁剪压缩质量 默认90 int
//                            .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                            .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
//                            .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
//                            .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
//                            .videoQuality()// 视频录制质量 0 or 1 int
//                            .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
//                            .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
//                            .recordVideoSecond()//视频秒数录制 默认60s int
            } else {
                //点击预览图片，这里暂时使用PictureSelector库自带的预览功能
                selector.themeStyle(R.style.picture_default_style).openExternalPreview(position, selectList);
                //下面是自定义预览界面
//                ArrayList<String> picUrls = new ArrayList<>();
//                for (BaseSelectPhotos item: Objects.requireNonNull(imageAdapter.getPhotoPath())) {
//                    picUrls.add(item.getOriginalurl());
//                }
//                Intent intent = new Intent(context, ImageWatchActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putStringArrayList("photoList", picUrls);
//                intent.putExtras(bundle);
//                intent.putExtra("page", position);
//                context.startActivity(intent);
            }
        });
    }

    private void initRV() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContxt(), spanCount);
        imageRecycler.setLayoutManager(layoutManager);
    }

    public void onResultCall(int requestCode, int resultCode, Intent data, ScrollView scrollView) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    imageAdapter.removePhotoPath(imagePath);
                    imagePath.clear();
                    BaseSelectPhotos blockPhoto;
                    for (LocalMedia lm : selectList) {
                        blockPhoto = new BaseSelectPhotos();
                        if (lm.isCompressed()) {
                            blockPhoto.setOriginalurl(lm.getCompressPath());
                        } else if (lm.isCut()) {
                            blockPhoto.setOriginalurl(lm.getCutPath());
                        } else {
                            blockPhoto.setOriginalurl(lm.getPath());
                        }
                        imagePath.add(blockPhoto);
                    }
                    imageAdapter.addPhotoPath(imagePath);
                    break;
                default:
                    break;
            }
        }
        if (scrollView != null) {
            scrollView.smoothScrollTo(0, scrollView.getBottom());
        }

    }

    private Context getContxt() {
        return context.get() instanceof Activity ?
                (Activity) context.get() :
                ((Fragment) context.get()).getContext();
    }

    public void onDestroy() {
        imageAdapter = null;
        imagePath.clear();
        imageUrl.clear();
        deleteImageUrl.clear();
        PictureFileUtils.deleteCacheDirFile(getContxt());
    }

}
