package com.goldze.common.dmvvm.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.goldze.common.dmvvm.R;

/**
 * @author GuoFeng
 * @date :2019/1/15 16:03
 * @description: Dialog方向工具类
 */
public class BaseDialog extends Dialog {

    /*默认背景阴影*/
    final static float DEFAULT_DIMAMOUNT = 0.2f;

    protected Builder builder;

    public BaseDialog(Context context) {
        this(context, 0);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public BaseDialog(Builder builder) {
        this(builder.context, 0);

        this.builder = builder;
        config(builder.layoutResID, DEFAULT_DIMAMOUNT, builder.gravity, builder.animInType,
                builder.width, builder.height,
                builder.canceledOnTouchOutside);
    }

    public static class Builder {
        /**
         * @param layoutResID            布局ID
         * @param dimAmount              背景阴影
         * @param gravity                动画方向
         * @param animInType             动画类型
         * @param width                  宽
         * @param height                 高
         * @param canceledOnTouchOutside Touch外是否关闭Dialog
         * @return
         */
        @NonNull
        Context context;
        @LayoutRes
        int layoutResID;
        float dimAmount = DEFAULT_DIMAMOUNT;
        int gravity = Gravity.CENTER;
        AnimInType animInType;
        int width = WindowManager.LayoutParams.MATCH_PARENT;
        int height = WindowManager.LayoutParams.WRAP_CONTENT;
        boolean canceledOnTouchOutside;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Builder layoutResID(@LayoutRes int layoutResID) {
            this.layoutResID = layoutResID;
            return this;
        }

        public Builder dimAmount(@FloatRange(from = 0, to = 1) float dimAmount) {
            this.dimAmount = dimAmount;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder canceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public Builder gravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder animInType(AnimInType animInType) {
            this.animInType = animInType;
            return this;
        }

        @UiThread
        public BaseDialog build() {
            return new BaseDialog(this);
        }

        @UiThread
        public BaseDialog show() {
            BaseDialog dialog = build();
            dialog.show();
            return dialog;
        }
    }

    /**
     * @param layoutResID            布局ID
     * @param dimAmount              背景阴影
     * @param gravity                动画方向
     * @param animInType             动画类型
     * @param width                  宽
     * @param height                 高
     * @param canceledOnTouchOutside Touch外是否关闭Dialog
     * @return
     */
    public BaseDialog config(@NonNull @LayoutRes int layoutResID, float dimAmount, int gravity, AnimInType animInType,
                             int width, int height, boolean canceledOnTouchOutside) {
        /*setContentView(layoutResID);//填充对话框布局
        setDimAmount(dimAmount);
        getWindow().setGravity(gravity);
        setAnimType(animInType);
        getWindow().setLayout(width, height);
        setCanceledOnTouchOutside(canceledOnTouchOutside ? true : false);*/

        config(View.inflate(getContext(), layoutResID, null), dimAmount, gravity, animInType, width, height, canceledOnTouchOutside);
        return this;
    }

    public BaseDialog config(@NonNull View view, float dimAmount, int gravity, AnimInType animInType,
                             int width, int height, boolean canceledOnTouchOutside) {
        /*setContentView(view);//填充对话框布局
        setDimAmount(dimAmount);
        getWindow().setGravity(gravity);
        setAnimType(animInType);
        getWindow().setLayout(width, height);
        setCanceledOnTouchOutside(canceledOnTouchOutside ? true : false);*/

        config(view, null, dimAmount, gravity, animInType, width, height, canceledOnTouchOutside);
        return this;
    }

    public BaseDialog config(@NonNull View view, @Nullable ViewGroup.LayoutParams params, float dimAmount, int gravity, AnimInType animInType,
                             int width, int height, boolean canceledOnTouchOutside) {
//        setContentView(view, params);//填充对话框布局
        if (null != params) {
            setContentView(view, params);
        } else {
            setContentView(view);
        }
        setDimAmount(dimAmount);
        getWindow().setGravity(gravity);
        setAnimType(animInType);
        getWindow().setLayout(width, height);
        setCanceledOnTouchOutside(canceledOnTouchOutside);
        return this;
    }

    /**
     * 动画类型
     *
     * @param animInType
     * @return
     */
    public BaseDialog setAnimType(AnimInType animInType) {
        int resId = R.style.dialog_zoom;
        switch (animInType) {
            case CENTER:
                resId = R.style.dialog_zoom;
                break;
            case LEFT:
                resId = R.style.dialog_anim_left;
                break;
            case TOP:
                resId = R.style.dialog_anim_top;
                break;
            case RIGHT:
                resId = R.style.dialog_anim_right;
                break;
            case BOTTOM:
                resId = R.style.dialog_anim_bottom;
                break;
        }
        getWindow().setWindowAnimations(resId);
        return this;
    }

    public void setOffset(int x, int y) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.x = x;
        layoutParams.y = y;
    }

    public float getDimAmount() {
        return getWindow().getAttributes().dimAmount;
    }

    /**
     * 设置背景阴影,必须setContentView之后调用才生效
     *
     * @param dimAmount
     * @return
     */
    public BaseDialog setDimAmount(float dimAmount) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = dimAmount;
        return this;
    }

    /**
     * 动画类型
     */
    public enum AnimInType {
        CENTER(0), LEFT(2), TOP(4), RIGHT(3), BOTTOM(1);

        AnimInType(int n) {
            intType = n;
        }

        final int intType;
    }


    // TODO: 2019/1/15 等待封装
//    public BaseDialog config(@LayoutRes int layoutResID, boolean canceledOnTouchOutside) {
//        config(layoutResID, DEFAULT_DIMAMOUNT, Gravity.CENTER, AnimInType.CENTER, WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.WRAP_CONTENT, canceledOnTouchOutside);
//        return this;
//    }

//    public BaseDialog config(@NonNull View view, boolean canceledOnTouchOutside) {
//        config(view, DEFAULT_DIMAMOUNT, Gravity.CENTER, AnimInType.CENTER, WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.WRAP_CONTENT, canceledOnTouchOutside);
//        return this;
//    }

//    public BaseDialog config(@LayoutRes int layoutResID, int gravity, AnimInType animInType, boolean canceledOnTouchOutside) {
//        config(layoutResID, DEFAULT_DIMAMOUNT, gravity, animInType, WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.WRAP_CONTENT, canceledOnTouchOutside);
//        return this;
//    }

//    public BaseDialog config(@NonNull View view, int gravity, AnimInType animInType, boolean canceledOnTouchOutside) {
//        config(view, DEFAULT_DIMAMOUNT, gravity, animInType, WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.WRAP_CONTENT, canceledOnTouchOutside);
//        return this;
//    }

//    public BaseDialog config(@LayoutRes int layoutResID, int gravity, AnimInType animInType,
//                             int width, int height, boolean canceledOnTouchOutside) {
//        config(layoutResID, DEFAULT_DIMAMOUNT, gravity, animInType, width, height, canceledOnTouchOutside);
//        return this;
//    }

//    public BaseDialog config(@NonNull View view, int gravity, AnimInType animInType,
//                             int width, int height, boolean canceledOnTouchOutside) {
//        config(view, DEFAULT_DIMAMOUNT, gravity, animInType, width, height, canceledOnTouchOutside);
//        return this;
//    }
}