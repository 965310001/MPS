package com.goldze.common.dmvvm.base.mvvm.stateview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;

import com.goldze.common.dmvvm.R;
import com.tqzhang.stateview.stateview.BaseStateControl;

/**
 * @author GuoFeng
 * @date : 2019/1/23 10:39
 * @description: 没有数据的样式
 */
public class EmptyState extends BaseStateControl {

    @Override
    protected int onCreateView() {
        return R.layout.layout_state_view;
    }

    @Override
    protected void onViewCreate(Context context, View view) {
        super.onViewCreate(context, view);
        ((ImageView) view.findViewById(R.id.iv_image)).setImageResource(R.drawable.ic_order_empty_white);
        ((AppCompatTextView) view.findViewById(R.id.tv_title)).setText("没有找到任何相关信息");
        ((AppCompatTextView) view.findViewById(R.id.tv_sub_title)).setText("选择或搜索其他商品分类/名称...");
        view.findViewById(R.id.btn_action).setVisibility(View.GONE);
    }

//    @Override
//    public boolean isVisible() {
//        return super.isVisible();
//    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return false;
    }
}
