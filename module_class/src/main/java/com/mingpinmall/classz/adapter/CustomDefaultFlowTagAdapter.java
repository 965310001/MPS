package com.mingpinmall.classz.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.mingpinmall.classz.R;
import com.xuexiang.xui.widget.flowlayout.DefaultFlowTagAdapter;

/**
 * 重写XUI自定义的布局
 */
public class CustomDefaultFlowTagAdapter extends DefaultFlowTagAdapter {

    public CustomDefaultFlowTagAdapter(Context context) {
        super(context);
    }

    TextView textView;

    @Override
    protected TextView newViewHolder(View convertView) {
        textView = super.newViewHolder(convertView);
        textView.setBackgroundResource(R.drawable.default_flow_tag_bg_rect_round_red);
        textView.setTextColor(getContext().getResources().getColorStateList(R.color.fit_item_textcolor));
        return textView;
    }
}
