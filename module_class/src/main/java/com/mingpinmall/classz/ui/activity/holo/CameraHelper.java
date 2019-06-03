package com.mingpinmall.classz.ui.activity.holo;


import android.app.Activity;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.hardware.Camera;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;


import com.goldze.common.dmvvm.utils.log.QLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 小斌
 * @data 2019/6/3
 **/
public class CameraHelper implements Camera.PreviewCallback {

    private Camera mCamera;                   //Camera对象
    private Camera.Parameters mParameters;    //Camera对象的参数
    private SurfaceView mSurfaceView;         //用于预览的SurfaceView对象
    SurfaceHolder mSurfaceHolder;             //SurfaceHolder对象

    private Activity mActivity;
    private CallBack mCallBack;   //自定义的回调
    private boolean enableFaceDetect = false;//是否开启人脸检测

    int mCameraFacing = Camera.CameraInfo.CAMERA_FACING_FRONT;  //摄像头方向
    int mDisplayOrientation = 0;    //预览旋转的角度

    private int picWidth = 2160;        //保存图片的宽
    private int picHeight = 3840;       //保存图片的高

    public CameraHelper(Activity activity, SurfaceView surfaceView) {
        this.mSurfaceView = surfaceView;
        this.mActivity = activity;
        mSurfaceHolder = mSurfaceView.getHolder();
        init();
    }

    public CameraHelper(Activity activity, SurfaceView surfaceView, CallBack callBack) {
        this.mSurfaceView = surfaceView;
        this.mActivity = activity;
        this.mCallBack = callBack;
        mSurfaceHolder = mSurfaceView.getHolder();
        init();
    }

    private void init() {
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (mCamera == null) {
                    openCamera(mCameraFacing);
                }
                startPreview();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                releaseCamera();
            }

        });
    }

    /**
     * 拍照
     */
    public void takePic() {
        if (mCamera != null) {
            mCamera.takePicture(() -> {
            }, null, (data, camera) -> {
                mCamera.startPreview();
                if (mCallBack != null) {
                    mCallBack.onTakePic(data);
                }
            });
        }
    }

    //打开相机 默认打开前置相机 Camera.CameraInfo.CAMERA_FACING_FRONT
    private boolean openCamera(int cameraFacing) {
        boolean supportCameraFacing = supportCameraFacing(cameraFacing);
        if (supportCameraFacing) {
            try {
                mCamera = Camera.open(cameraFacing);
                initParameters(mCamera);
                mCamera.setPreviewCallback(this);
            } catch (Exception e) {
                e.printStackTrace();
                toast("打开相机失败!");
                return false;
            }
        }
        return supportCameraFacing;
    }

    //配置相机参数
    private void initParameters(Camera camera) {
        try {
            mParameters = camera.getParameters();
            mParameters.setPreviewFormat(ImageFormat.NV21);

            //获取与指定宽高相等或最接近的尺寸
            //设置预览尺寸
            Camera.Size bestPreviewSize = getBestSize(mSurfaceView.getWidth(), mSurfaceView.getHeight(), mParameters.getSupportedPreviewSizes());
            if (bestPreviewSize != null) {
                mParameters.setPreviewSize(bestPreviewSize.width, bestPreviewSize.height);
            }

            //设置保存图片尺寸
            Camera.Size bestPicSize = getBestSize(picWidth, picHeight, mParameters.getSupportedPreviewSizes());
            if (bestPicSize != null) {
                mParameters.setPictureSize(bestPicSize.width, bestPicSize.height);
            }

            //对焦模式
            if (isSupportFocus(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            }

            camera.setParameters(mParameters);
        } catch (Exception e) {
            e.printStackTrace();
            toast("相机初始化失败!");
        }
    }

    //开始预览
    public void startPreview() {
        if (mCamera != null) {
            try {
                mCamera.setPreviewDisplay(mSurfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            setCameraDisplayOrientation(mActivity);
            mCamera.startPreview();
            if (enableFaceDetect) {
                startFaceDetect(true);
            }
        }
    }

    /**
     * 开启人脸检测
     */
    public void startFaceDetect(boolean enable) {
        enableFaceDetect = enable;
        if (mCamera != null) {
            if (enableFaceDetect) {
                mCamera.startFaceDetection();
                mCamera.setFaceDetectionListener((faces, camera) -> {
                    mCallBack.onFaceDetect(transForm(faces));
                    QLog.i("检测到 " + faces.length + " 张人脸");
                });
            } else {
                mCamera.stopFaceDetection();
                mCamera.setFaceDetectionListener(null);
            }
        }
    }

    //判断是否支持某一对焦模式
    private boolean isSupportFocus(String focusMode) {
        boolean autoFocus = false;
        List<String> listFocusMode = mParameters.getSupportedFocusModes();
        for (String mode : listFocusMode) {
            if (mode == focusMode) {
                autoFocus = true;
            }
            QLog.i("相机支持的对焦模式：" + mode);
        }
        return autoFocus;
    }

    /**
     * 切换摄像头
     */
    public void exchangeCamera() {
        releaseCamera();
        if (mCameraFacing == Camera.CameraInfo.CAMERA_FACING_BACK) {
            mCameraFacing = Camera.CameraInfo.CAMERA_FACING_FRONT;
        } else {
            mCameraFacing = Camera.CameraInfo.CAMERA_FACING_BACK;
        }
        openCamera(mCameraFacing);
        startPreview();
    }

    //释放相机
    public void releaseCamera() {
        if (mCamera != null) {
            mCamera.stopFaceDetection();
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    //获取与指定宽高相等或最接近的尺寸
    private Camera.Size getBestSize(int targetWidth, int targetHeight, List<Camera.Size> sizeList) {
        Camera.Size bestSize = null;
        double targetRatio = ((double) targetHeight / targetWidth);  //目标大小的宽高比
        double minDiff = targetRatio;

        for (Camera.Size size : sizeList) {
            double supportedRatio = ((double) size.width / size.height);
            QLog.i("系统支持的尺寸 : " + size.width + " * " + size.height + " , 比例:" + supportedRatio);
        }

        for (Camera.Size size : sizeList) {
            if (size.width == targetHeight && size.height == targetWidth) {
                bestSize = size;
                break;
            }
            double supportedRatio = (size.width / size.height);
            if (Math.abs(supportedRatio - targetRatio) < minDiff) {
                minDiff = Math.abs(supportedRatio - targetRatio);
                bestSize = size;
            }
        }
        QLog.i("目标尺寸 ：" + targetWidth + " * " + targetHeight + "，比例:" + targetRatio);
        QLog.i("最优尺寸 ：" + bestSize.height + " * " + bestSize.width);
        mCallBack.onPostBestSize(bestSize);
        return bestSize;
    }

    //设置预览旋转的角度
    private void setCameraDisplayOrientation(Activity activity) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(mCameraFacing, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();

        int screenDegree = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                screenDegree = 0;
                break;
            case Surface.ROTATION_90:
                screenDegree = 90;
                break;
            case Surface.ROTATION_180:
                screenDegree = 180;
                break;
            case Surface.ROTATION_270:
                screenDegree = 270;
                break;
        }
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            mDisplayOrientation = (info.orientation + screenDegree) % 360;
            mDisplayOrientation = (360 - mDisplayOrientation) % 360;     // compensate the mirror
        } else {
            mDisplayOrientation = (info.orientation - screenDegree + 360) % 360;
        }
        mCamera.setDisplayOrientation(mDisplayOrientation);

        QLog.i("屏幕的旋转角度 : " + rotation);
        QLog.i("setDisplayOrientation(result) : " + mDisplayOrientation);
    }

    //判断是否支持某个相机
    private boolean supportCameraFacing(int cameraFacing) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
            Camera.getCameraInfo(i, info);
            if (info.facing == cameraFacing) return true;
        }
        return false;
    }

    //将相机中用于表示人脸矩形的坐标转换成UI页面的坐标
    private ArrayList<RectF> transForm(Camera.Face[] faces) {
        Matrix matrix = new Matrix();
        // Need mirror for front camera.
        boolean mirror = (mCameraFacing == Camera.CameraInfo.CAMERA_FACING_FRONT);
        matrix.setScale(mirror ? -1f : 1f, 1f);
        // This is the value for android.hardware.Camera.setDisplayOrientation.
        matrix.postRotate((float) mDisplayOrientation);
        // Camera driver coordinates range from (-1000, -1000) to (1000, 1000).
        // UI coordinates range from (0, 0) to (width, height).
        matrix.postScale(mSurfaceView.getWidth() / 2000f, mSurfaceView.getHeight() / 2000f);
        matrix.postTranslate(mSurfaceView.getWidth() / 2f, mSurfaceView.getHeight() / 2f);

        ArrayList<RectF> rectList = new ArrayList<>();
        for (Camera.Face face :
                faces) {
            RectF srcRect = new RectF(face.rect);
            RectF dstRect = new RectF(0f, 0f, 0f, 0f);
            matrix.mapRect(dstRect, srcRect);
            rectList.add(dstRect);
        }
        return rectList;
    }


    private void toast(String msg) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }

    public Camera getCamera() {
        return mCamera;
    }

    public void addCallBack(CallBack callBack) {
        this.mCallBack = callBack;
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        mCallBack.onPreviewFrame(data);
    }

    public interface CallBack {
        void onPostBestSize(Camera.Size size);

        void onPreviewFrame(byte[] data);

        void onTakePic(byte[] data);

        void onFaceDetect(ArrayList<RectF> faces);
    }
}
