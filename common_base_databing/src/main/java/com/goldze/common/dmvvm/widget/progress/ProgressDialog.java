package com.goldze.common.dmvvm.widget.progress;

import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

import com.goldze.common.dmvvm.R;

/**
 * 功能描述：快速提示    等待中Dialog
 * 创建人：小斌
 * 创建时间: 2019/3/30
 **/
public class ProgressDialog extends BaseDialog {

    private final String TAG = this.getClass().getSimpleName();

    private CustomStatusView customStatusView;
    private TextView label;

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
     * 设置FragmentManager
     *
     * @param fragmentManager
     * @return
     */
    public ProgressDialog setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        return this;
    }

    @Override
    public int setUpLayoutId() {
        return R.layout.dialog_loading;
    }

    @Override
    public void convertView(ViewHolder holder, BaseDialog dialog) {
        customStatusView = holder.getView(R.id.view_status);
        label = holder.getView(R.id.label_loding);
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
     * 关闭对话框时的回调
     */
    private void delayedDoing() {
        if (onDismissListener != null)
            onDismissListener.onDismiss();
    }

    /**
     * 完成
     *
     * @param title
     */
    public void onComplete(String title) {
        onComplete(title, 2000);
    }

    /**
     * 完成
     *
     * @param title
     * @param delayMillis
     */
    public void onComplete(String title, int delayMillis) {
        onComplete(title, delayMillis, null);
    }

    /**
     * 完成
     *
     * @param title
     * @param onDismissListener
     */
    public void onComplete(String title, OnDismissListener onDismissListener) {
        onComplete(title, 2000, onDismissListener);
    }

    /**
     * 完成，带回调
     *
     * @param title             标题
     * @param delayMillis       保持显示时长
     * @param onDismissListener 销毁时的回调
     */
    public void onComplete(String title, int delayMillis, OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        complete(title, delayMillis);
    }

    /**
     * 失败
     *
     * @param title
     */
    public void onFail(String title) {
        onFail(title, 2000);
    }

    /**
     * 失败
     *
     * @param title
     * @param delayMillis
     */
    public void onFail(String title, int delayMillis) {
        onFail(title, delayMillis, null);
    }

    /**
     * 失败
     *
     * @param title
     * @param onDismissListener
     */
    public void onFail(String title, OnDismissListener onDismissListener) {
        onFail(title, 2000, onDismissListener);
    }

    /**
     * 失败带回调
     *
     * @param title
     * @param delayMillis
     * @param onDismissListener
     */
    public void onFail(String title, int delayMillis, OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        fail(title, delayMillis);
    }

    /**
     * 加载中
     *
     * @param title
     */
    public void onLoading(String title) {
        this.onDismissListener = null;
        londing(title);
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
        customStatusView.loadSuccess();
        label.setText(title);
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
        customStatusView.loadFailure();
        label.setText(title);
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
        customStatusView.loadLoading();
        label.setText(title);
    }

    public interface OnDismissListener {
        void onDismiss();
    }
}
