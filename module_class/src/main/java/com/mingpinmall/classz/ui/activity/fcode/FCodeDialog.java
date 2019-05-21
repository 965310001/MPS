package com.mingpinmall.classz.ui.activity.fcode;

import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.goldze.common.dmvvm.widget.dialog.TextDialog;
import com.goldze.common.dmvvm.widget.progress.BaseDialog;
import com.goldze.common.dmvvm.widget.progress.ViewHolder;
import com.mingpinmall.classz.R;

/**
 * @author 小斌
 * @data 2019/5/21
 **/
public class FCodeDialog extends BaseDialog {

    private OnViewClickListener onViewClickListener;

    public void setOnViewClickListener(OnViewClickListener onViewClickListener) {
        this.onViewClickListener = onViewClickListener;
    }

    @Override
    public int setUpLayoutId() {
        return R.layout.dialog_fcode;
    }

    @Override
    public void convertView(ViewHolder holder, BaseDialog dialog) {
        setOutCancel(false);
        setCancelable(false);
        holder.setOnClickListener(R.id.btn_submit, v -> {
            // 提交验证
            TextDialog.showBaseDialog(getContext(), "F码商品", "验证成功后，该F码将不能再次使用\n\n是否继续?", () -> {
                if (onViewClickListener != null) {
                    onViewClickListener.onClickView(v, ((AppCompatEditText) holder.getView(R.id.ed_fcode)).getText().toString());
                }
            }).show();
        });
        holder.setOnClickListener(R.id.iv_close, v -> {
            // 关闭验证窗口
            TextDialog.showBaseDialog(getContext(), "F码商品", "必须输入F码才可以下单\n是否取消购买该商品?", () -> {
                if (onViewClickListener != null) {
                    onViewClickListener.onClickView(v, "取消购买");
                }
                this.dismiss();
            }).show();
        });
    }

    public interface OnViewClickListener {
        void onClickView(View view, String str);
    }
}
