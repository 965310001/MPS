package com.goldze.common.dmvvm.widget.progress;


import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldze.common.dmvvm.R;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/3/30
 **/
public class ProgressDialog extends BaseDialog {

    private AVLoadingIndicatorView animeViewLoding;
    private TextView labelLoding;
    private LinearLayout loding;
    private AppCompatImageView animeViewComplete;

    private TextView labelComplete;
    private LinearLayout complete;
    private boolean isInit = false;

    private Handler handler = new Handler();

    private boolean stop = false;
    private OnFinish onFinish = null;

    private Commad commads = new Commad();

    static class Commad {
        CommadType type = CommadType.LODING;
        String message = "";
        int delayMillis = 0;

        enum CommadType {
            LODING, SUCCESS, FAIL, NONE
        }
    }

    @Override
    public int setUpLayoutId() {
        return R.layout.dialog_loading;
    }

    @Override
    public void convertView(ViewHolder holder, BaseDialog dialog) {
        animeViewLoding = holder.getView(R.id.animeView_loding);
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
            dismiss();
            delayedDoing();
        }
    };

    private Runnable runnableForFail = new Runnable() {
        @Override
        public void run() {
            if (stop) {
                stop = false;
                onFail("等待超时", 1500);
            }
        }
    };

    /**
     * 完成
     *
     * @param title
     * @param delayMillis
     */
    public ProgressDialog onComplete(String title, int delayMillis) {
        this.onFinish = onFinish;
        complete(title, delayMillis);
        return this;
    }

    private void stopAnim() {
        animeViewLoding.smoothToHide();
    }

    private void delayedDoing() {
        onFinish.onFinish();
    }

    /**
     * 完成，带回调
     *
     * @param title       标题
     * @param delayMillis 保持显示时长
     * @param onFinish    销毁时的回调
     */
    public ProgressDialog onComplete(String title, int delayMillis, OnFinish onFinish) {
        this.onFinish = onFinish;
        stop = false;
        complete(title, delayMillis);
        return this;
    }

    /**
     * 失败带回调
     *
     * @param title
     * @param delayMillis
     * @param onFinish
     */
    public ProgressDialog onFail(String title, int delayMillis, OnFinish onFinish) {
        this.onFinish = onFinish;
        fail(title, delayMillis);
        return this;
    }

    /**
     * 加载中
     *
     * @param title
     */
    public ProgressDialog onLoading(String title) {
        this.onFinish = null;
        londing(title);
        return this;
    }

    /**
     * 失败
     *
     * @param title
     * @param delayMillis
     */
    public ProgressDialog onFail(String title, int delayMillis)
    {
        this.onFinish = null;
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
        if (!isInit) {
            commads.delayMillis = delayMillis;
            commads.message = title;
            commads.type = Commad.CommadType.SUCCESS;
            return;
        }
        stop = false;
        stopAnim();
        loding.setVisibility(View.GONE);
        complete.setVisibility(View.VISIBLE);
         animeViewComplete.setImageResource(R.drawable.ic_complete);
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
        if (!isInit) {
            commads.delayMillis = delayMillis;
            commads.message = title;
            commads.type = Commad.CommadType.FAIL;
            return;
        }
        stop = false;
        stopAnim();
        loding.setVisibility(View.GONE);
        complete.setVisibility(View.VISIBLE);
        animeViewComplete.setImageResource(R.drawable.ic_fail);
        labelComplete.setText(title);
        handler.postDelayed(runnableForLoading, delayMillis);
    }

    /**
     * 加载中  执行
     *
     * @param title
     */
    private void londing(String title) {
        if (!isInit) {
            commads.delayMillis = 0;
            commads.message = title;
            commads.type = Commad.CommadType.LODING;
            return;
        }
        animeViewLoding.smoothToShow();
        loding.setVisibility(View.VISIBLE);
        complete.setVisibility(View.GONE);
        labelLoding.setText(title);
    }

    interface OnFinish {
        void onFinish();
    }
}
