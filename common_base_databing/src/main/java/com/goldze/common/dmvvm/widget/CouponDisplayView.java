package com.goldze.common.dmvvm.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.bigkoo.convenientbanner.utils.ScreenUtil;

/**
 * Created by Jason_周 on 2019/4/20.
 */
public class CouponDisplayView extends View {

    private Paint mPaint;
    private Paint mPaint0;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    /**
     * 圆间距
     */
    private float gap = 8;
    /**
     * 半径
     */
    private float radius = 14;
    /**
     * 圆数量
     */
    private int circleNum;

    private float remain;

    private int rightColor = Color.WHITE;
    int width, hight;

    public CouponDisplayView(Context context) {
        super(context);
        initPaint();
    }


    public CouponDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    public CouponDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        radius = ScreenUtil.dip2px(getContext(), 14);
        //边缘锯齿画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mPaint.setStyle(Paint.Style.FILL);

        mPaint0 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint0.setDither(true);
        mPaint0.setColor(Color.WHITE);
        mPaint0.setStyle(Paint.Style.FILL);
    }

    private void initView() {
        if (getBackground() == null) {
            //背景未设置情况下，设置为透明背景
            setBackgroundColor(Color.TRANSPARENT);
        }

        // 初始化锯齿遮盖图层
        mBitmap = Bitmap.createBitmap(width, hight, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        // 绘制图层颜色
        mCanvas.drawRect(0, 0, width / 8 * 5, hight, mPaint0);
        mPaint0.setColor(rightColor);
        mCanvas.drawRect(width / 8 * 5, 0, width, hight, mPaint0);
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
        initView();
    }

    /**
     * 有效和无效俩状态设置
     * @param state
     */
    public void setState(boolean state) {
        rightColor = state ? Color.parseColor("#ed5564") : Color.parseColor("#aab2bd");
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制图层
        canvas.drawBitmap(mBitmap, 0, 0, null);
        for (int i = 0; i < circleNum; i++) {
            float y = gap + radius + remain / 2 + ((gap + radius * 2) * i);
            mCanvas.drawCircle(0, y, radius, mPaint);
            mCanvas.drawCircle(width, y, radius, mPaint);
        }
    }
}
