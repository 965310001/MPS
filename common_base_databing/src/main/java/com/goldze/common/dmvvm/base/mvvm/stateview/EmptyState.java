package com.goldze.common.dmvvm.base.mvvm.stateview;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

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
        AppCompatImageView ivImage = view.findViewById(R.id.iv_image);
        AppCompatTextView tvTitle = view.findViewById(R.id.tv_title), tvSubTitle = view.findViewById(R.id.tv_sub_title);

        AppCompatButton btnAction = view.findViewById(R.id.btn_action);

        Object tag = view.getTag();
        if (tag != null) {
            String title = "", subTitle = "";
            if ("1".equals(tag) || "2".equals(tag)) {
                ivImage.setImageResource(R.drawable.ic_svg_cearch);
                ivImage.setColorFilter(Color.WHITE);
                ivImage.setPadding(10, 10, 10, 10);
                subTitle = "收藏店铺经常来逛一逛";
                btnAction.setVisibility(View.GONE);
            }
            if ("1".equals(tag)) {/*商品上新*/
                title = "商铺最近没有新品上架";
            } else if ("2".equals(tag)) {/*店铺活动*/
                title = "商铺最近没有促销活动";
            } else if ("3".equals(tag)) {
                title = "没有找到任何相关信息";
                subTitle = "搜索其它商品名称或筛选项...";
                btnAction.setText("查看全部商品");
                btnAction.setVisibility(View.VISIBLE);
            } else {
                ivImage.setImageResource(R.drawable.ic_order_empty_white);
                tvTitle.setText("没有找到任何相关信息");
                tvSubTitle.setText("选择或搜索其他商品分类/名称...");
                btnAction.setVisibility(View.GONE);
            }
            tvTitle.setText(title);
            tvSubTitle.setText(subTitle);
        }
    }
}
