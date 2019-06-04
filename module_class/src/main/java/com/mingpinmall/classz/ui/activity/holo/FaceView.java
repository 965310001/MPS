package com.mingpinmall.classz.ui.activity.holo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;

/**
 * @author 小斌
 * @data 2019/6/3
 **/
public class FaceView extends View {

    Paint mPaint;
    private String mCorlor = "#42ed45";
    private ArrayList<RectF> mFaces;

    public FaceView(Context context) {
        super(context);
        init();
    }

    public FaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor(mCorlor));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f,
                getContext().getResources().getDisplayMetrics()));
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("FaceView", "onDraw: Start");
        if (mFaces != null) {
            Log.i("FaceView", "onDraw: Mid");
            for (RectF face : mFaces) {
                Log.i("FaceView", "onDraw: Size");
                if (bitmap != null) {
                    Log.i("FaceView", "onDraw: Bitmap");
                    float cha = (face.bottom - face.top) / 5;
                    face.top = face.top - cha;
                    face.bottom = face.bottom - cha;
                    canvas.drawBitmap(bitmap, null, face, null);
                } else {
                    Log.i("FaceView", "onDraw: RectF");
                    canvas.drawRect(face, mPaint);
                }
            }
        }
    }

    private Bitmap bitmap;

    public void setImage(Drawable drawable) {
        this.bitmap = drawable2Bitmap(drawable);
    }

    public void setFaces(ArrayList<RectF> faces) {
        this.mFaces = faces;
        invalidate();
    }

    private Matrix mMatrix;

    public void setFaces(Matrix faces) {
        this.mMatrix = faces;
        invalidate();
    }

    private Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }
}
