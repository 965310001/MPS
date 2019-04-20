package com.mingpinmall.me.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Jason_周 on 2019/4/20.
 */
public class CouponDisplayView extends View {

    private Paint mPaint;
    private Paint mPaint2;
    /**
     * 圆间距
     */
    private float gap = 8;
    /**
     * 半径
     */
    private float radius = 20;
    /**
     * 圆数量
     */
    private int circleNum;

    private float remain;

    int width, hight;

    Bitmap bitmap1;
    Bitmap bitmap2;

    public CouponDisplayView(Context context) {
        super(context);
    }


    public CouponDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CouponDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(30);

        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2.setDither(true);
        mPaint2.setColor(Color.RED);
        mPaint2.setStyle(Paint.Style.FILL);
        mPaint2.setTextSize(30);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (remain == 0) {
            remain = (int) (h - gap) % (2 * radius + gap);
        }
        circleNum = (int) ((h - gap) / (2 * radius + gap));
        this.width = w;
        this.hight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, width / 2, hight, mPaint);
        canvas.drawRect(width / 2, 0, width, hight, mPaint2);

        mPaint.setColor(Color.WHITE);

        for (int i = 0; i < circleNum; i++) {
            float y = gap + radius + remain / 2 + ((gap + radius * 2) * i);
            canvas.drawCircle(0, y, radius, mPaint);
            canvas.drawCircle(width, y, radius, mPaint);
        }

        if (bitmap1 != null) {
            canvas.drawBitmap(bitmap1, width / 2 - bitmap1.getWidth() / 2, hight / 2 - bitmap1.getHeight() / 2, mPaint);
            canvas.drawText("我是蔡徐坤粉丝", width / 2, (hight / 2 + (bitmap1.getHeight() * 2)), mPaint);
        }

//        for (int i = 0; i < circleNum; i++) {
//            float x = gap + radius + remain / 2 + ((gap + radius * 2) * i);
//            canvas.drawCircle(x, 0, radius, mPaint);
//            canvas.drawCircle(x, getHeight(), radius, mPaint);
//        }

    }
}
