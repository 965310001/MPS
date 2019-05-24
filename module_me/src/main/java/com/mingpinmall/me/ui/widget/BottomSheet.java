package com.mingpinmall.me.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;

import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.LayoutBottomSendcodeBinding;

/**
 * 自定义 底部
 * DialogView
 */
public class BottomSheet extends Dialog {
    // 动画时长
    private final static int mAnimationDuration = 200;
    // 持有 ContentView，为了做动画
    private View mContentView;
    private boolean mIsAnimating = false;
    private SendCodeSheetBuilder sheetBuilder;

    private OnBottomSheetShowListener mOnBottomSheetShowListener;

    public BottomSheet(Context context) {
        super(context, R.style.BottomSheet);
    }

    public void setOnBottomSheetShowListener(OnBottomSheetShowListener onBottomSheetShowListener) {
        mOnBottomSheetShowListener = onBottomSheetShowListener;
    }

    public void setSheetBuilder(SendCodeSheetBuilder sheetBuilder) {
        this.sheetBuilder = sheetBuilder;
    }

    public SendCodeSheetBuilder getSheetBuilder() {
        return sheetBuilder;
    }

    public void setFail() {
        sheetBuilder.bind.btnSubmit.setEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //noinspection ConstantConditions
        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        // 在底部，宽度撑满
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM | Gravity.CENTER;

        int screenWidth = ScreenUtil.getScreenWidth(getContext());
        int screenHeight = ScreenUtil.getScreenHeight(getContext());
        params.width = screenWidth < screenHeight ? screenWidth : screenHeight;
        getWindow().setAttributes(params);
        setCanceledOnTouchOutside(true);
    }

    @Override
    public void setContentView(int layoutResID) {
        mContentView = LayoutInflater.from(getContext()).inflate(layoutResID, null);
        super.setContentView(mContentView);
    }

    @Override
    public void setContentView(@NonNull View view, ViewGroup.LayoutParams params) {
        mContentView = view;
        super.setContentView(view, params);
    }

    public View getContentView() {
        return mContentView;
    }

    @Override
    public void setContentView(@NonNull View view) {
        mContentView = view;
        super.setContentView(view);
    }

    /**
     * BottomSheet升起动画
     */
    private void animateUp() {
        if (mContentView == null) {
            return;
        }
        TranslateAnimation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f
        );
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(translate);
        set.addAnimation(alpha);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(mAnimationDuration);
        set.setFillAfter(true);
        mContentView.startAnimation(set);
    }

    /**
     * BottomSheet降下动画
     */
    private void animateDown() {
        if (mContentView == null) {
            return;
        }
        TranslateAnimation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f
        );
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(translate);
        set.addAnimation(alpha);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(mAnimationDuration);
        set.setFillAfter(true);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mIsAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIsAnimating = false;
                mContentView.post(() -> {
                    // java.lang.IllegalArgumentException: View=com.android.internal.policy.PhoneWindow$DecorView{22dbf5b V.E...... R......D 0,0-1080,1083} not attached to window manager
                    // 在dismiss的时候可能已经detach了，简单try-catch一下
                    try {
                        BottomSheet.super.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mContentView.startAnimation(set);
    }

    @Override
    public void show() {
        super.show();
        animateUp();
        if (mOnBottomSheetShowListener != null) {
            mOnBottomSheetShowListener.onShow();
        }
    }

    @Override
    public void dismiss() {
        if (mIsAnimating) {
            return;
        }
        animateDown();
    }

    public interface OnBottomSheetShowListener {
        void onShow();
    }

    /**
     * 生成自定义View类型的 对话框。
     */
    public static class SendCodeSheetBuilder {

        private Context mContext;

        private BottomSheet mDialog;
        private String data;
        private LayoutBottomSendcodeBinding bind;

        private BottomSheet.OnSubmitClickListener onSubmitClickListener;
        private OnDismissListener mOnBottomDialogDismissListener;

        public SendCodeSheetBuilder(Context context) {
            mContext = context;
        }

        public SendCodeSheetBuilder setOnSubmitClickListener(OnSubmitClickListener onSubmitClickListener) {
            this.onSubmitClickListener = onSubmitClickListener;
            return this;
        }

        public SendCodeSheetBuilder setOnBottomDialogDismissListener(OnDismissListener listener) {
            mOnBottomDialogDismissListener = listener;
            return this;
        }

        public SendCodeSheetBuilder setData(String data) {
            this.data = data;
            return this;
        }

        public BottomSheet build() {
            mDialog = new BottomSheet(mContext);
            mDialog.setSheetBuilder(this);
            View contentView = buildTViews();
            mDialog.setContentView(contentView,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            if (mOnBottomDialogDismissListener != null) {
                mDialog.setOnDismissListener(mOnBottomDialogDismissListener);
            }
            return mDialog;
        }

        private View buildTViews() {
            bind = DataBindingUtil
                    .bind(LayoutInflater.from(mContext)
                            .inflate(R.layout.layout_bottom_sendcode, null));
            View wrapperView = bind.getRoot().getRootView();
            bind.executePendingBindings();

            bind.setData(data);
            bind.btnSubmit.setOnClickListener(v -> {
                String phone = bind.edPhone.getText().toString().trim();
                if ("".equals(phone)) {
                    ToastUtils.showShort("请输入手机号");
                    return;
                }
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(bind.edPhone.getWindowToken(), 0);
                }
                bind.edPhone.clearFocus();
                if (onSubmitClickListener != null) {
                    bind.btnSubmit.setEnabled(false);
                    onSubmitClickListener.onSubmit(phone);
                }
            });
            return wrapperView;
        }

    }

    public interface OnSubmitClickListener {

        /**
         * 手机号
         *
         * @param phone
         */
        void onSubmit(String phone);

    }

}

