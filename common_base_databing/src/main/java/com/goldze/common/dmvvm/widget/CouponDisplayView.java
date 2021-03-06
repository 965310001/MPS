package com.goldze.common.dmvvm.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.goldze.common.dmvvm.R;

/**
 * Created by Jason_周 on 2019/4/20.
 */
public class CouponDisplayView extends View {

    private Paint mPaint;
    private Paint mPaint0;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private boolean leftRedMode = false;
    /**
     * 圆间距
     */
    private float gap = 11f;
    /**
     * 半径
     */
    private float radius = 14;
    /**
     * 圆数量
     */
    private int circleNum;

    private float remain;

    private int rightColor = Color.parseColor("#ed5564");
    int width, hight;

    public CouponDisplayView(Context context) {
        super(context);
        initPaint();
    }

    public CouponDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initPaint();
    }

    public CouponDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initPaint();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CouponDisplayView);
        leftRedMode = a.getBoolean(R.styleable.CouponDisplayView_leftRedMode, false);
    }

    public void setLeftRedMode(boolean leftRedMode) {
        this.leftRedMode = leftRedMode;
        invalidate();
    }

    private void initPaint() {
        radius = ScreenUtil.dip2px(getContext(), 4);
        //边缘锯齿画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mPaint.setStyle(Paint.Style.FILL);

        mPaint0 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint0.setDither(true);
        mPaint0.setColor(leftRedMode ? rightColor : Color.WHITE);
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
        mCanvas.drawRect(0, 0, radius, hight, mPaint0);
        mPaint0.setColor(leftRedMode ? Color.WHITE : rightColor);
        mCanvas.drawRect(width - radius, 0, width, hight, mPaint0);
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
     *
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
