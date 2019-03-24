package com.goldze.common.dmvvm.base.mvvm.stateview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldze.common.dmvvm.R;
import com.tqzhang.stateview.stateview.BaseStateControl;

/**
 * @author GuoFeng
 * @date :2019/1/17 15:10
 * @description: 自定义错误加载的样式
 */
public class ErrorState extends BaseStateControl {
    @Override
    protected int onCreateView() {
        return R.layout.stateview_empty_view;
    }

    @Override
    protected void onViewCreate(Context context, View view) {
        TextView errorDesc = view.findViewById(R.id.tv_error_desc);
        ImageView errorIcon = view.findViewById(R.id.iv_error_icon);
        if (view.getTag() != null) {
            if (view.getTag().equals("1")) {
                errorDesc.setText("网络不给力～_~");
                errorIcon.setImageResource(R.drawable.stateview_empty_network);
            } else if (view.getTag().equals("2")) {
                errorDesc.setText("服务器异常");
                errorIcon.setImageResource(R.drawable.stateview_empty_server);
            }

        }
    }

    @Override
    public boolean isVisible() {
        return super.isVisible();
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return false;
    }
}
