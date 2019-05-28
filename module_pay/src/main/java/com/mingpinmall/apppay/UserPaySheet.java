package com.mingpinmall.apppay;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
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

import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.apppay.pay.PayLayoutBean;
import com.xiaobin.apppay.R;
import com.xiaobin.apppay.databinding.LayoutBottomPayOrderBinding;
import com.xuexiang.xui.utils.Utils;

/**
 * 自定义 底部 支付 DialogView
 */
public class UserPaySheet extends Dialog {
    // 动画时长
    private final static int mAnimationDuration = 200;
    // 持有 ContentView，为了做动画
    private View mContentView;
    private boolean mIsAnimating = false;
    private PayViewSheetBuilder sheetBuilder;

    private OnBottomSheetShowListener mOnBottomSheetShowListener;

    public UserPaySheet(Context context) {
        super(context, R.style.BottomSheet);
    }

    public void setOnBottomSheetShowListener(OnBottomSheetShowListener onBottomSheetShowListener) {
        mOnBottomSheetShowListener = onBottomSheetShowListener;
    }

    public void setSheetBuilder(PayViewSheetBuilder sheetBuilder) {
        this.sheetBuilder = sheetBuilder;
    }

    public PayViewSheetBuilder getSheetBuilder() {
        return sheetBuilder;
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

        int screenWidth = Utils.getScreenWidth(getContext());
        int screenHeight = Utils.getScreenHeight(getContext());
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
                        UserPaySheet.super.dismiss();
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
    public static class PayViewSheetBuilder {

        private Context mContext;

        private UserPaySheet mDialog;
        private PayLayoutBean.PayInfoBean data;
        private LayoutBottomPayOrderBinding bind;
        /**
         * -1：未选择
         * 0：充值卡
         * 1：预存款
         * 2：支付宝
         * 3：微信
         */
        private int metodType = 3;

        private UserPaySheet.OnPayMethodListener mOnPayMethodListener;
        private OnDismissListener mOnBottomDialogDismissListener;

        public PayViewSheetBuilder(Context context) {
            mContext = context;
        }

        public PayViewSheetBuilder setmOnPayMethodListener(OnPayMethodListener mOnPayMethodListener) {
            this.mOnPayMethodListener = mOnPayMethodListener;
            return this;
        }

        public PayViewSheetBuilder setOnBottomDialogDismissListener(OnDismissListener listener) {
            mOnBottomDialogDismissListener = listener;
            return this;
        }

        public PayViewSheetBuilder setData(PayLayoutBean.PayInfoBean data) {
            this.data = data;
            return this;
        }

        /**
         * 支付中
         */
        public void onPaying(String label) {
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
            bind.ivClose.setVisibility(View.GONE);
            bind.flStateContent.setVisibility(View.VISIBLE);
            bind.pbLoading.setVisibility(View.VISIBLE);
            bind.ivState.setVisibility(View.GONE);
            bind.tvState.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            bind.tvState.setText(TextUtils.isEmpty(label) ? "等待支付结果" : label);
        }

        /**
         * 支付失败
         */
        public void onPayFail(String label) {
            bind.flStateContent.setVisibility(View.VISIBLE);
            bind.pbLoading.setVisibility(View.GONE);
            bind.ivState.setImageResource(R.drawable.ic_alert);
            bind.ivState.setVisibility(View.VISIBLE);
            bind.tvState.setTextColor(ContextCompat.getColor(mContext, R.color.load_failure));
            bind.tvState.setText(TextUtils.isEmpty(label) ? "支付失败" : label);
            new Handler().postDelayed(() -> {
                bind.flStateContent.setVisibility(View.GONE);
                bind.ivClose.setVisibility(View.VISIBLE);
                mDialog.setCancelable(true);
                mDialog.setCanceledOnTouchOutside(true);
            }, 2000);
        }

        /**
         * 支付成功
         */
        public void onPaySuccess(String label) {
            bind.flStateContent.setVisibility(View.VISIBLE);
            bind.pbLoading.setVisibility(View.GONE);
            bind.ivState.setImageResource(R.drawable.ic_success);
            bind.ivState.setVisibility(View.VISIBLE);
            bind.tvState.setTextColor(ContextCompat.getColor(mContext, R.color.load_success));
            bind.tvState.setText(TextUtils.isEmpty(label) ? "支付成功" : label);
            new Handler().postDelayed(() -> mDialog.dismiss(), 2000);
        }

        public UserPaySheet build() {
            mDialog = new UserPaySheet(mContext);
            mDialog.setSheetBuilder(this);
            View contentView = buildTViews();
            mDialog.setContentView(contentView,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            if (mOnBottomDialogDismissListener != null) {
                mDialog.setOnDismissListener(mOnBottomDialogDismissListener);
            }
            return mDialog;
        }

        private void onIVClick(View iv) {
            iv.setAlpha((float) 1);
            if (iv == bind.llAlipay) {
                metodType = 2;
                bind.rgRadio.check(bind.rbAlipay.getId());
            } else {
                metodType = 3;
                bind.rgRadio.check(bind.rbWeixin.getId());
            }
        }

        private View buildTViews() {
            bind = DataBindingUtil
                    .bind(LayoutInflater.from(mContext)
                            .inflate(R.layout.layout_bottom_pay_order, null));
            View wrapperView = bind.getRoot().getRootView();
            bind.executePendingBindings();

            bind.flStateContent.setOnClickListener(v -> {
            });
            bind.ivClose.setOnClickListener(v -> mDialog.dismiss());
            bind.setData(data);

            /**
             * 需支付金额
             */
            double pay_amount = Double.parseDouble(data.getPay_amount());
            /**
             * 预存款余额
             */
            double pd = Double.parseDouble(data.getMember_available_pd());
            /**
             * 充值卡余额
             */
            double rcb = Double.parseDouble(data.getMember_available_rcb());
            if (pd < pay_amount) {
                //预存款余额 不够支付
                bind.tvTipsPd.setVisibility(View.VISIBLE);
                bind.flPayMoney.setEnabled(false);
                bind.scbPmoney.setEnabled(false);
                bind.scbPmoney.setChecked(false);
            } else {
//                bind.flPayMoney.setVisibility(View.VISIBLE);
//                bind.flPayMoney.setOnClickListener(v -> bind.scbPmoney.toggle());
//                bind.scbPmoney.setOnCheckedChangeListener((checkBox, isChecked) -> {
//                    if (isChecked) {
//                        metodType = 1;
//                        if (bind.scbPcard.isChecked()) {
//                            bind.scbPcard.setChecked(false, bind.scbPcard.isChecked());
//                        }
//                        bind.llPsd.setVisibility(View.VISIBLE);
//                    } else {
//                        if (!bind.scbPcard.isChecked()) {
//                            bind.llPsd.setVisibility(View.GONE);
//                            bind.edPassword.setText("");
//                            if (metodType < 2) {
//                                metodType = -1;
//                            }
//                        }
//                    }
//                    bind.ivWechat.setAlpha((float) 0.5);
//                    bind.ivAlipay.setAlpha((float) 0.5);
//                });
            }
            if (rcb < pay_amount) {
                //充值卡余额 不够支付
                bind.tvTipsPcd.setVisibility(View.VISIBLE);
                bind.flPayCard.setEnabled(false);
                bind.scbPcard.setEnabled(false);
                bind.scbPcard.setChecked(false);
            } else {
//                bind.flPayCard.setVisibility(View.VISIBLE);
//                bind.flPayCard.setOnClickListener(v -> bind.scbPcard.toggle());
//                bind.scbPcard.setOnCheckedChangeListener((checkBox, isChecked) -> {
//                    if (isChecked) {
//                        metodType = 0;
//                        if (bind.scbPmoney.isChecked()) {
//                            bind.scbPmoney.setChecked(false, bind.scbPmoney.isChecked());
//                        }
//                        bind.llPsd.setVisibility(View.VISIBLE);
//                    } else {
//                        if (!bind.scbPmoney.isChecked()) {
//                            bind.llPsd.setVisibility(View.GONE);
//                            bind.edPassword.setText("");
//                            if (metodType < 2) {
//                                metodType = -1;
//                            }
//                        }
//                    }
//                    bind.ivWechat.setAlpha((float) 0.5);
//                    bind.ivAlipay.setAlpha((float) 0.5);
//                });
            }
            // 支付宝支付 和 微信支付 按钮
            for (PayLayoutBean.PayInfoBean.PaymentListBean item :
                    data.getPayment_list()) {
                if (item.getPayment_code().equals("alipay_sdk")) {
                    bind.llAlipay.setVisibility(View.VISIBLE);
                    bind.llAlipay.setOnClickListener(this::onIVClick);
                } else if (item.getPayment_code().equals("wxpay_sdk")) {
                    bind.llWeixin.setVisibility(View.VISIBLE);
                    bind.llWeixin.setOnClickListener(this::onIVClick);
                }
            }
            bind.btnSubmit.setOnClickListener(v -> {
                if (metodType == -1) {
                    ToastUtils.showShort("请选择支付方式");
                    return;
                }
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(bind.edPassword.getWindowToken(), 0);
                }
                bind.edPassword.clearFocus();
                if (mOnPayMethodListener != null) {
                    switch (metodType) {
                        case 0:
                        case 1:
                            mOnPayMethodListener.onPcd(mDialog, metodType, bind.edPassword.getText().toString());
                            break;
                        case 2:
                            mOnPayMethodListener.onAlipay(mDialog);
                            break;
                        case 3:
                            mOnPayMethodListener.onWxpay(mDialog);
                            break;
                        default:
                            break;
                    }
                }
            });
            return wrapperView;
        }

    }

    public interface OnPayMethodListener {

        /**
         * 支付宝支付
         *
         * @param dialog
         */
        void onAlipay(UserPaySheet dialog);

        /**
         * 微信支付
         *
         * @param dialog
         */
        void onWxpay(UserPaySheet dialog);

        /**
         * 预存款和充值卡支付方式
         *
         * @param dialog
         * @param type     0：充值卡  1： 预存款
         * @param password 支付密码
         */
        void onPcd(UserPaySheet dialog, int type, String password);
    }

}

