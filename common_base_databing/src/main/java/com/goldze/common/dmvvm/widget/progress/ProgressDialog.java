package com.goldze.common.dmvvm.widget.progress;


import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.StringDef;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chenenyu.router.annotation.InjectParam;
import com.goldze.common.dmvvm.R;
import com.goldze.common.dmvvm.utils.TUtil;
import com.trecyclerview.progressindicator.indicator.BallClipRotatePulseIndicator;
import com.trecyclerview.progressindicator.indicator.BaseIndicatorController;
import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;
import com.wang.avi.indicators.BallClipRotateIndicator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/3/30
 **/
public class ProgressDialog extends BaseDialog {

    private final String TAG = this.getClass().getSimpleName();

    public enum Theme {
        ColourFul, WhiteBG, BlackBG
    }


    private Theme theme = Theme.ColourFul;
    private AVLoadingIndicatorView animeViewLoding;
    private TextView labelLoding;
    private LinearLayout loding;
    private AppCompatImageView animeViewComplete;
    private String animeType = "BallClipRotatePulseIndicator";
    private int animeColor;

    private TextView labelComplete;
    private LinearLayout complete;
    private RelativeLayout content_layout;
    private boolean isInit = false;
    private FragmentManager fragmentManager;

    private Handler handler = new Handler();

    private OnDismissListener onDismissListener = null;

    private Commad commads = new Commad();

    static class Commad {
        CommadType type = CommadType.LODING;
        String message = "";
        int delayMillis = 0;

        enum CommadType {
            LODING, SUCCESS, FAIL, NONE
        }
    }

    public static ProgressDialog initNewDialog(FragmentManager fragmentManager) {
        return new ProgressDialog().setFragmentManager(fragmentManager);
    }

    /**
     * 设置主体样式
     *
     * @param theme
     */
    public ProgressDialog setTheme(Theme theme) {
        this.theme = theme;
        if (isInit) {
            initTheme("default");
        }
        return this;
    }

    /**
     * 设置FragmentManager
     *
     * @param fragmentManager
     * @return
     */
    public ProgressDialog setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        return this;
    }

    /* github开源地址：https://github.com/81813780/AVLoadingIndicatorView 可查看所有效果 */
    @StringDef({"BallPulseIndicator", "BallGridPulseIndicator", "BallClipRotateIndicator", "BallClipRotatePulseIndicator",
            "SquareSpinIndicator", "BallClipRotateMultipleIndicator", "BallPulseRiseIndicator", "BallRotateIndicator",
            "CubeTransitionIndicator", "BallZigZagIndicator", "BallZigZagDeflectIndicator", "BallTrianglePathIndicator",
            "BallScaleIndicator", "LineScaleIndicator", "LineScalePartyIndicator", "BallScaleMultipleIndicator",
            "BallPulseSyncIndicator", "BallBeatIndicator", "LineScalePulseOutIndicator", "LineScalePulseOutRapidIndicator",
            "BallScaleRippleIndicator", "BallScaleRippleMultipleIndicator", "BallSpinFadeLoaderIndicator", "LineSpinFadeLoaderIndicator",
            "TriangleSkewSpinIndicator", "PacmanIndicator", "BallGridBeatIndicator", "SemiCircleSpinIndicator",
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimeType {
    }

    /**
     * 设置动画样式
     *
     * @param animeType
     */
    public ProgressDialog setAnimeType(@AnimeType String animeType) {
        this.animeType = animeType;
        if (isInit) {
            animeViewLoding.setIndicator(animeType);
        }
        return this;
    }

    /**
     * 设置动画样式颜色
     *
     * @param color
     */
    public ProgressDialog setAnimeColor(@ColorRes int color) {
        this.animeColor = color;
        if (isInit) {
            animeViewLoding.setIndicatorColor(animeColor);
        }
        return this;
    }

    @StringDef({"default", "loading", "complete", "fail"})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SetTheme {
    }

    private void initTheme(@SetTheme String type) {
        int colorBlue = Color.parseColor("#0091FF");
        int colorBlack = Color.parseColor("#000000");
        int colorWhite = Color.parseColor("#FFFFFF");
        int colorRed = Color.parseColor("#B10000");

        animeViewLoding.setIndicator(animeType);
        switch (theme) {
            case ColourFul:
                content_layout.setBackgroundResource(R.drawable.bg_loading_dialog_white);
                animeViewLoding.setIndicatorColor(colorBlue);
                labelLoding.setTextColor(colorBlue);
                labelComplete.setTextColor(type.equals("complete") ? colorBlue : colorRed);
                animeViewComplete.setImageResource(type.equals("complete") ? R.drawable.ic_complete_blue : R.drawable.ic_fail_red);
                break;
            case WhiteBG:
                content_layout.setBackgroundResource(R.drawable.bg_loading_dialog_white);
                animeViewLoding.setIndicatorColor(colorBlack);
                labelLoding.setTextColor(colorBlack);
                labelComplete.setTextColor(colorBlack);
                animeViewComplete.setImageResource(type.equals("complete") ? R.drawable.ic_complete_black : R.drawable.ic_fail_black);
                break;
            case BlackBG:
                content_layout.setBackgroundResource(R.drawable.bg_loading_dialog);
                animeViewLoding.setIndicatorColor(colorWhite);
                labelLoding.setTextColor(colorWhite);
                labelComplete.setTextColor(colorWhite);
                animeViewComplete.setImageResource(type.equals("complete") ? R.drawable.ic_complete : R.drawable.ic_fail);
                break;
        }
    }

    @Override
    public int setUpLayoutId() {
        return R.layout.dialog_loading;
    }

    @Override
    public void convertView(ViewHolder holder, BaseDialog dialog) {
        content_layout = holder.getView(R.id.content_layout);
        animeViewLoding = holder.getView(R.id.animeView_loding);
        animeViewLoding.setIndicator(animeType);
        labelLoding = holder.getView(R.id.label_loding);
        loding = holder.getView(R.id.loding);
        animeViewComplete = holder.getView(R.id.animeView_complete);
        labelComplete = holder.getView(R.id.label_complete);
        complete = holder.getView(R.id.complete);
        isInit = true;
        switch (commads.type) {
            case LODING:
                onLoading(commads.message);
                break;
            case FAIL:
                onFail(commads.message, commads.delayMillis);
                break;
            case SUCCESS:
                onComplete(commads.message, commads.delayMillis);
                break;
            case NONE:
                break;
        }
        commads.type = Commad.CommadType.NONE;
    }

    private Runnable runnableForLoading = new Runnable() {
        @Override
        public void run() {
            delayedDoing();
            dismissAllowingStateLoss();
        }
    };

    /**
     * 完成
     *
     * @param title
     * @param delayMillis
     */
    public ProgressDialog onComplete(String title, int delayMillis) {
        complete(title, delayMillis);
        return this;
    }

    /**
     * 停止动画
     */
    private void stopAnim() {
        animeViewLoding.smoothToHide();
    }

    /**
     * 关闭对话框时的回调
     */
    private void delayedDoing() {
        if (onDismissListener != null)
            onDismissListener.onDismiss();
    }

    /**
     * 完成，带回调
     *
     * @param title             标题
     * @param delayMillis       保持显示时长
     * @param onDismissListener 销毁时的回调
     */
    public ProgressDialog onComplete(String title, int delayMillis, OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        complete(title, delayMillis);
        return this;
    }

    /**
     * 失败带回调
     *
     * @param title
     * @param delayMillis
     * @param onDismissListener
     */
    public ProgressDialog onFail(String title, int delayMillis, OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        fail(title, delayMillis);
        return this;
    }

    /**
     * 加载中
     *
     * @param title
     */
    public ProgressDialog onLoading(String title) {
        this.onDismissListener = null;
        londing(title);
        return this;
    }

    /**
     * 失败
     *
     * @param title
     * @param delayMillis
     */
    public ProgressDialog onFail(String title, int delayMillis) {
        this.onDismissListener = null;
        fail(title, delayMillis);
        return this;
    }

    /**
     * 完成  执行
     *
     * @param title
     * @param delayMillis
     */
    private void complete(String title, int delayMillis) {
        if (getDialog() == null) {
            show(fragmentManager, TAG);
        }
        if (!isInit) {
            commads.delayMillis = delayMillis;
            commads.message = title;
            commads.type = Commad.CommadType.SUCCESS;
            return;
        }
        initTheme("complete");
        stopAnim();
        loding.setVisibility(View.GONE);
        complete.setVisibility(View.VISIBLE);
        labelComplete.setText(title);
        handler.postDelayed(runnableForLoading, delayMillis);
    }

    /**
     * 失败  执行
     *
     * @param title
     * @param delayMillis
     */
    private void fail(String title, int delayMillis) {
        if (getDialog() == null) {
            show(fragmentManager, TAG);
        }
        if (!isInit) {
            commads.delayMillis = delayMillis;
            commads.message = title;
            commads.type = Commad.CommadType.FAIL;
            return;
        }
        initTheme("fail");
        stopAnim();
        loding.setVisibility(View.GONE);
        complete.setVisibility(View.VISIBLE);
        labelComplete.setText(title);
        handler.postDelayed(runnableForLoading, delayMillis);
    }

    /**
     * 加载中  执行
     *
     * @param title
     */
    private void londing(String title) {
        if (getDialog() == null) {
            show(fragmentManager, TAG);
        }
        if (!isInit) {
            commads.delayMillis = 0;
            commads.message = title;
            commads.type = Commad.CommadType.LODING;
            return;
        }
        initTheme("loading");
        animeViewLoding.smoothToShow();
        loding.setVisibility(View.VISIBLE);
        complete.setVisibility(View.GONE);
        labelLoding.setText(title);
    }

    public interface OnDismissListener {
        void onDismiss();
    }
}
