package com.goldze.common.dmvvm.utils.itemdecoration;//package me.goldze.common.utils.itemdecoration;
//
//import android.graphics.Canvas;
//import android.support.annotation.NonNull;
//import android.support.v4.view.ViewCompat;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//
//import me.goldze.common.utils.PxUtils;
//import me.goldze.common.utils.Utils;
//
///**
// * @author GuoFeng
// * @date : 2019/1/25 16:57
// * @description:
// */
//public class HorizontalLineItemDecoration extends RecyclerView.ItemDecoration {
//
//    @Override
//    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//        super.onDrawOver(c, parent, state);
//
//    }
//
//    /**
//     * 画线
//     */
//    @Override
//    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
//        super.onDraw(canvas, parent, state);
//        canvas.save();
//        final int leftWithMargin = PxUtils.dp2px(Utils.getApplication(),56)
//        final int right = parent.getWidth();
//
//        final int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            final View child = parent.getChildAt(i);
//            int adapterPosition = parent.getChildAdapterPosition(child);
////            left = (adapterPosition == lastPosition) ?  0 : leftWithMargin;
//            parent.getDecoratedBoundsWithMargins(child, mBounds);
////            final int bottom = mBounds.bottom + Math.round(ViewCompat.getTranslationY(child));
////            final int top = bottom - mDivider.getIntrinsicHeight();
////            mDivider.setBounds(left, top, right, bottom);
////            mDivider.draw(canvas);
//        }
//        canvas.restore();
//    }
//
//    /**
//     * 画竖直分割线
//     */
//    private void drawVertical(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View child = parent.getChildAt(i);
//            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
//            int left = child.getRight() + params.rightMargin;
//            int top = child.getTop() - params.topMargin;
//            int right = left + mDivider.getIntrinsicWidth();
//            int bottom = child.getBottom() + params.bottomMargin;
//            mDivider.setBounds(left, top, right, bottom);
//            mDivider.draw(c);
//        }
//    }
//
//    /**
//     * 画水平分割线
//     */
//    private void drawHorizontal(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View child = parent.getChildAt(i);
//            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
//            int left = child.getLeft() - params.leftMargin;
//            int top = child.getBottom() + params.bottomMargin;
//            int right = child.getRight() + params.rightMargin;
//            int bottom = top + mDivider.getIntrinsicHeight();
//            mDivider.setBounds(left, top, right, bottom);
//            mDivider.draw(c);
//        }
//    }
//
//
//}
