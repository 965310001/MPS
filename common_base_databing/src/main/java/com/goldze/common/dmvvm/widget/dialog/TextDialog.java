package com.goldze.common.dmvvm.widget.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldze.common.dmvvm.R;

/**
 * Created by qq321 on 2017/5/18 0018.
 */

public class TextDialog extends AppCompatDialog implements View.OnClickListener {

    private TextView btn_complete, btn_cancel, label, content;
    private Context context;
    private Drawable drawable;
    private LinearLayout btnContent;

    private SingleButtonCallback onCompleteClick;
    private SingleButtonCallback onCancelClick;

    public static TextDialog newDialog(Context context) {
        return new TextDialog(context);
    }

    /**
     * 只监听确定按钮
     * @param context
     * @param label
     * @param msg
     * @param onPositiveListener
     * @return
     */
    public static TextDialog showBaseDialog(Context context, String label, String msg, SingleButtonCallback onPositiveListener) {
        TextDialog textDialog = new TextDialog(context);
        textDialog.setLabel(label).setMessage(msg).onPositive(onPositiveListener);
        return textDialog;
    }

    /**
     * 监听双按钮
     * @param context
     * @param label
     * @param msg
     * @param onPositiveListener
     * @param onNegativeListener
     * @return
     */
    public static TextDialog showBaseDialog(Context context, String label, String msg, SingleButtonCallback onPositiveListener, SingleButtonCallback onNegativeListener) {
        TextDialog textDialog = new TextDialog(context);
        textDialog.setLabel(label).setMessage(msg).onPositive(onPositiveListener).onNegative(onNegativeListener);
        return textDialog;
    }

    public TextDialog(Context context) {
        super(context, R.style.dialogTheme);
        this.context = context;
        init();
    }

    public TextDialog setLabel(@Nullable CharSequence content) {
        this.label.setText(content);
        return this;
    }

    public TextDialog setLabel(@StringRes int titleId) {
        this.label.setText(titleId);
        return this;
    }

    public TextDialog setMessage(@Nullable CharSequence content) {
        this.content.setText(content);
        return this;
    }

    public TextDialog setMessage(@StringRes int titleId) {
        this.content.setText(titleId);
        return this;
    }

    public TextDialog setCanceledWhenOutside(boolean canceled) {
        this.setCanceledOnTouchOutside(canceled);
        return this;
    }

    @Nullable
    @Override
    public Window getWindow() {
        return super.getWindow();
    }

    /**
     * 确定按钮点击监听
     * @param onCompleteClick
     * @return
     */
    public TextDialog onPositive(SingleButtonCallback onCompleteClick) {
        this.onCompleteClick = onCompleteClick;
        return this;
    }
    public TextDialog setOnDismiss(@Nullable OnDismissListener listener) {
        this.setOnDismissListener(listener);
        return this;
    }

    /**
     * 取消按钮点击监听
     * @param onCancelClick
     * @return
     */
    public TextDialog onNegative(SingleButtonCallback onCancelClick) {
        this.onCancelClick = onCancelClick;
        return this;
    }

    public TextDialog setCompleteText(String text) {
        btn_complete.setText(text);
        return this;
    }

    public TextDialog setCompleteText(int text) {
        btn_complete.setText(text);
        return this;
    }

    public TextDialog setCancelText(String text) {
        btn_cancel.setText(text);
        return this;
    }

    public TextDialog setCancelText(int text) {
        btn_cancel.setText(text);
        return this;
    }

    public TextDialog setOnceButtonMode() {
        btn_cancel.setVisibility(View.GONE);
        drawable = ContextCompat.getDrawable(context, R.drawable.button_dialog_white);
        btn_complete.setBackground(drawable);
        btnContent.removeViewAt(1);
        return this;
    }

    private void init() {
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_showtext);
        getWindow().setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画
        btn_complete = findViewById(R.id.btn_complete);
        btn_cancel = findViewById(R.id.btn_cancel);
        label = findViewById(R.id.label);
        content = findViewById(R.id.content);
        btnContent = findViewById(R.id.btnContent);
        btn_complete.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_complete) {
            if (onCompleteClick != null) {
                onCompleteClick.onClick();
            }
        } else if (i == R.id.btn_cancel) {
            if (onCancelClick != null) {
                onCancelClick.onClick();
            }
        }
        dismiss();
    }

    public interface SingleButtonCallback {
        void onClick();
    }

    @Override
    public void dismiss() {
        drawable = null;
        super.dismiss();
    }

}
