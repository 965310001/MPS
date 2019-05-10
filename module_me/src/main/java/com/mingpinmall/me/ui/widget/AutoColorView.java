package com.mingpinmall.me.ui.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.Nullable;

/**
 * 功能描述：
 * @author 小斌
 * @date 2019/3/27
 **/
public class AutoColorView extends FrameLayout implements Animator.AnimatorListener {

    private Context context;
    /**
     * 颜色集合
     */
    private List<Integer> colors = new ArrayList<>();
    /**
     * 是否正在循环切换背景色
     */
    private boolean runState = false;
    /**
     * 动画锁，防止上一个动画未执行完，又重新开始了动画
     */
    private boolean runLock = false;
    /**
     * 第几个颜色了
     */
    private int index = 0;
    /**
     * 间隔多少毫秒更换颜色
     */
    private long delayMillis = 1000;

    private final Handler handler = new Handler();

    public AutoColorView(Context context) {
        super(context);
        this.context = context;
    }

    public AutoColorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public AutoColorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    /**
     * 设置前景图
     *
     * @param drawable 前景图
     * @return
     */
    public AutoColorView setImage(@DrawableRes int drawable) {
        return setImage(drawable);
    }

    /**
     * 设置前景图
     *
     * @param drawable 前景图
     * @return
     */
    public AutoColorView setImage(Drawable drawable) {
        this.setForeground(drawable);
        return this;
    }

    public void setColors(List<Integer> colorIds) {
        this.colors.clear();
        this.colors = colorIds;
    }

    public void setColors(int[] colorIds) {
        this.colors.clear();
        for (int colorId :
                colorIds) {
            this.colors.add(colorId);
        }
    }

    /**
     * 设置切换颜色间隔
     *
     * @param delayMillis 间隔 毫秒
     * @return
     */
    public AutoColorView setDelayMillis(@IntRange(from = 0) long delayMillis) {
        this.delayMillis = delayMillis;
        return this;
    }

    /**
     * 添加颜色
     *
     * @param colorId 颜色
     */
    public void addColor(@ColorRes int colorId) {
        this.colors.add(colorId);
    }

    /**
     * 添加颜色
     *
     * @param index 插入到第几个
     * @param colorId 颜色
     */
    public void addColor(int index, @ColorRes int colorId) {
        this.colors.add(index, colorId);
    }

    /**
     * 添加颜色
     *
     * @param colorIds 颜色
     */
    public void addColors(@NonNull List<Integer> colorIds) {
        this.colors.addAll(colorIds);
    }

    public void addColors(@NonNull int[] colorIds) {
        for (int colorId :
                colorIds) {
            this.colors.add(colorId);
        }
    }

    /**
     * 主要代码，更换背景颜色
     */
    private void changeColor() {
        if (colors.size() < 2) {
            if (colors.isEmpty()) {
                runState = false;
                try {
                    throw new Exception("颜色资源为空");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
            this.setBackgroundColor(colors.get(0));
            return;
        }
        int color1 = colors.get(index);
        index++;
        if (index == colors.size()) {
            index = 0;
        }
        int color2 = colors.get(index);
        ValueAnimator animator = ObjectAnimator.ofInt(
                this,
                "backgroundColor",
                ContextCompat.getColor(context, color1),
                ContextCompat.getColor(context, color2)
        );//对背景色颜色进行改变，操作的属性为"backgroundColor",此处必须这样写，不能全小写,后面的颜色为在对应颜色间进行渐变
        animator.setDuration(1500);
        //如果要颜色渐变必须要ArgbEvaluator，来实现颜色之间的平滑变化，否则会出现颜色不规则跳动
        animator.setEvaluator(new ArgbEvaluator());
        animator.start();
        animator.addListener(this);
    }

    /**
     * 是否正在执行动画
     *
     * @return
     */
    public boolean isRunning() {
        return runState;
    }

    /**
     * 开始
     */
    public void start() {
        if (!runState) {
            while (runLock) {
                this.getAnimation().cancel();
            }
            runState = true;
            changeColor();
        }
    }

    /**
     * 暂停
     */
    public void pause() {
        runState = false;
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 销毁
     */
    public void destory() {
        handler.removeCallbacksAndMessages(null);
        colors.clear();
    }

    @Override
    public void onAnimationStart(Animator animation) {
        //开始动画，锁定
        runLock = true;
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        //结束动画，解锁
        runLock = false;
        if (runState) {
            handler.postDelayed(() -> changeColor(), delayMillis);
        }
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        //取消动画，解锁
        runLock = false;
    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
