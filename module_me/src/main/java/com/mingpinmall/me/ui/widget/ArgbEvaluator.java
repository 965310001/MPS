package com.mingpinmall.me.ui.widget;

import android.animation.TypeEvaluator;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/3/27
 **/
public class ArgbEvaluator implements TypeEvaluator {
    /**
     *颜色演变核心算法
     * fraction:动画过渡时间因子，决定了动画变化的速率，值为0-1之间
     * startvalue:动画起始颜色
     * endValue:动画结束颜色
     */
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        int startInt = (Integer) startValue;
        /*
         * 起始颜色ARGB颜色通道拆分
         */
        float startA = ((startInt >> 24) & 0xff) / 255.0f;
        float startR = ((startInt >> 16) & 0xff) / 255.0f;
        float startG = ((startInt >>  8) & 0xff) / 255.0f;
        float startB = ( startInt        & 0xff) / 255.0f;
        /*
         * 结束颜色ARGB颜色通道拆分
         */
        int endInt = (Integer) endValue;
        float endA = ((endInt >> 24) & 0xff) / 255.0f;
        float endR = ((endInt >> 16) & 0xff) / 255.0f;
        float endG = ((endInt >>  8) & 0xff) / 255.0f;
        float endB = ( endInt        & 0xff) / 255.0f;


        // convert from sRGB to linear
        startR = (float) Math.pow(startR, 2.2);
        startG = (float) Math.pow(startG, 2.2);
        startB = (float) Math.pow(startB, 2.2);

        endR = (float) Math.pow(endR, 2.2);
        endG = (float) Math.pow(endG, 2.2);
        endB = (float) Math.pow(endB, 2.2);
        /*
         *根据动画时间因子，计算出中间的过渡颜色
         */
        // compute the interpolated color in linear space
        float a = startA + fraction * (endA - startA);
        float r = startR + fraction * (endR - startR);
        float g = startG + fraction * (endG - startG);
        float b = startB + fraction * (endB - startB);

        // convert back to sRGB in the [0..255] range
        a = a * 255.0f;
        r = (float) Math.pow(r, 1.0 / 2.2) * 255.0f;
        g = (float) Math.pow(g, 1.0 / 2.2) * 255.0f;
        b = (float) Math.pow(b, 1.0 / 2.2) * 255.0f;
        /*
         *重新将分离的颜色通道组合返回过渡颜色
         */
        return Math.round(a) << 24 | Math.round(r) << 16 | Math.round(g) << 8 | Math.round(b);
    }
}
