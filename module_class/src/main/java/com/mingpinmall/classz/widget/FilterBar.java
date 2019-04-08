package com.mingpinmall.classz.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 *  具体的bar
 */

public class FilterBar extends LinearLayout implements FilterTab.OnTabSelectedChangeListener {
    public FilterBar(Context context) {
        this(context, null);
    }

    public FilterBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        syncFilterTabState();
    }

    private void syncFilterTabState() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof FilterTab) {
                ((FilterTab) child).addTabSelectedChangeListener(FilterBar.this);
            }
        }
    }


    private FilterTab lastSelectedTab;

    @Override
    public void onChange(FilterTab filterTab, boolean selected) {
        if (selected) {
            if (lastSelectedTab != null) {
                lastSelectedTab.setFilterTabSelected(false);
            }
            lastSelectedTab = filterTab;
        }else {
            lastSelectedTab = null;
        }

    }
}
