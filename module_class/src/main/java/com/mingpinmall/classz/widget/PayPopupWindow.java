package com.mingpinmall.classz.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.FragmentPayBinding;
import com.mingpinmall.classz.ui.vm.bean.BuyStepInfo;

import static com.xuexiang.xui.utils.ResUtils.getResources;

/**
 * @author GuoFeng
 * @date :2019/4/4 16:11
 * @description: 提交订单PopWindow
 */
public class PayPopupWindow extends PopupWindow {

    public PayPopupWindow(Context context, View view) {
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
        private PayPopupWindow payFragment;
        private Context context;

        private FrameLayout frameLayout;
        //背景颜色
        private int colorBg = R.color.color_33000000;

        private BuyStepInfo mData;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setColorBg(int color) {
            colorBg = color;
            return this;
        }

        public Builder setData(BuyStepInfo data) {
            mData = data;
            return this;
        }

        public Builder build() {
            frameLayout = new FrameLayout(context);
            RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.fragment_pay, null);
            relativeLayout.setBackgroundColor(context.getResources().getColor(colorBg));
            FragmentPayBinding binding = DataBindingUtil.bind(relativeLayout);
            binding.setData(mData);
            binding.executePendingBindings();
            frameLayout.addView(binding.getRoot());
            return this;
        }

        public PayPopupWindow createPop() {
            payFragment = new PayPopupWindow(context, frameLayout);
            payFragment.showAsDropDown(frameLayout.getRootView(), Gravity.BOTTOM, 0, 0);
            return payFragment;
        }
    }
}