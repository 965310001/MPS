package com.goldze.common.dmvvm.widget.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.trecyclerview.TRecyclerView;

/**
 * @author GuoFeng
 * @date : 2019/2/15 18:49
 * @description:
 */
public class XRecyclerView extends TRecyclerView {

    private RecyclerView.LayoutManager layoutManager;


    int lastItemPosition;

    //是否向上滑动
    private boolean isSlidingUpward;

    public XRecyclerView(Context context) {
        this(context, null);
    }

    public XRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initView();
    }


    @Override
    public void setLayoutManager(LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    private void initView() {
        if (layoutManager == null) {
            new Exception("必须初始化LayoutManager");
            return;
        }
        /*滑动显示到顶部*/
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }


}
