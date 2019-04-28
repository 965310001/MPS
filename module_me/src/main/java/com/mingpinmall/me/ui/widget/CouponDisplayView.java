package com.mingpinmall.me.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.mingpinmall.me.R;

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
    private float radius = 14;
    /**
     * 圆数量
     */
    private int circleNum;

    private float remain;

    int width, hight;

    public CouponDisplayView(Context context) {
        super(context);
        initView();
    }


    public CouponDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public CouponDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(30);

        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2.setDither(true);
        mPaint2.setColor(Color.parseColor("#ed5564"));
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

    public void setState(boolean state) {
        mPaint2.setColor(state ? Color.parseColor("#ed5564") : Color.parseColor("#aab2bd"));
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.parseColor("#FFFFFFFF"));
        canvas.drawRect(0, 0, width / 8 * 5, hight, mPaint);
        canvas.drawRect(width / 8 * 5, 0, width, hight, mPaint2);

        mPaint.setColor(Color.parseColor("#FFF3F3F3"));
        for (int i = 0; i < circleNum; i++) {
            float y = gap + radius + remain / 2 + ((gap + radius * 2) * i);
            canvas.drawCircle(0, y, radius, mPaint);
            canvas.drawCircle(width, y, radius, mPaint);
        }
    }
}
