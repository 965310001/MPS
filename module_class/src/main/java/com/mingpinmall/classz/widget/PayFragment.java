package com.mingpinmall.classz.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.FragmentPayBinding;
import com.socks.library.KLog;
import com.xuexiang.xui.widget.popupwindow.PopWindow;

import java.util.List;

import static com.xuexiang.xui.utils.ResUtils.getResources;

/**
 * @author GuoFeng
 * @date :2019/4/4 16:11
 * @description: 提交订单PopWindow
 */
public class PayFragment extends PopupWindow {

    public PayFragment(Context context, View view) {
        //这里可以修改popupwindow的宽高
        super(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

        setContentView(view);
        initViews();
    }

    private void initViews() {
        setAnimationStyle(R.style.AnimSheetBottom);
        setFocusable(true);
        setOutsideTouchable(true);
    }

    public static class Builder {

        private PayFragment payFragment;
        private Context context;

        private FrameLayout frameLayout;
        //背景颜色
        private int colorBg = Color.parseColor("#F8F8F8");
        private int titleTextColor = Color.parseColor("#333333");//标题字体颜色
        private int tabBgDrawable = R.drawable.item_lable_bg_shape;//选项背景颜色

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setColorBg(int color) {
            colorBg = context.getResources().getColor(color);
            return this;
        }

        public Builder setTitleTextColor(int titleTextColor) {
            this.titleTextColor = titleTextColor;
            return this;
        }

        public Builder setTabBgDrawable(int tabBgDrawable) {
            this.tabBgDrawable = tabBgDrawable;
            return this;
        }

        public Builder build() {
            frameLayout = new FrameLayout(context);
            View view = LayoutInflater.from(context).inflate(R.layout.fragment_pay, null);
            view.setBackgroundColor(colorBg);
            FragmentPayBinding binding = DataBindingUtil.bind(view);
            frameLayout.addView(binding.getRoot());
            return this;
        }

        public PayFragment createPop() {
            payFragment = new PayFragment(context, frameLayout);
            return payFragment;
        }
    }
}