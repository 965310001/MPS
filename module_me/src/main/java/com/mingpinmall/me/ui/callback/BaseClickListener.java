package com.mingpinmall.me.ui.callback;

import android.view.View;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/3/25
 **/
public interface BaseClickListener {

    interface OnItemClickListener {
        void onItemclick(View view, int position);
    }

    interface OnItemLongClickListener {
        boolean onLongItemclick(View view, int position);
    }
}
