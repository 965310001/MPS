package com.mingpinmall.classz.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Observable;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingpinmall.classz.R;

/**
 * 自定义bar
 */

public class FilterTab extends RelativeLayout {

    //旋转动画执行时间
    private static final long DURATION_ROTATE = 200;
    private TextView textFilter;
    private AppCompatImageView imgArrow;

    private TabSelectedObervable tabSelectedObervable = new TabSelectedObervable();


    public interface OnTabSelectedChangeListener {
        void onChange(FilterTab filterTab, boolean selected);
    }

    private class TabSelectedObervable extends Observable<OnTabSelectedChangeListener> {

        public void notifyTabChanged(FilterTab filterTab, boolean selected) {
            synchronized (mObservers) {
                for (int i = mObservers.size() - 1; i >= 0; i--) {
                    mObservers.get(i).onChange(filterTab, selected);
                }
            }
        }
    }

    public FilterTab(Context context) {
        this(context, null);
    }

    public FilterTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.filter_tab, this);
        textFilter = relativeLayout.findViewById(R.id.filter_text);
        imgArrow = relativeLayout.findViewById(R.id.filter_img);

        //设置筛选条件
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FilterTab);
        String filterText = typedArray.getString(R.styleable.FilterTab_filterText);
        boolean isShow = typedArray.getBoolean(R.styleable.FilterTab_isImage, true);
        if (!TextUtils.isEmpty(filterText)) {
            textFilter.setText(filterText);
        }
        if (!isShow) {
            imgArrow.setVisibility(GONE);
        }
        typedArray.recycle();
    }


    /**
     * 设置筛选标签当前筛选条件
     *
     * @param charSequence
     */
    public void setText(CharSequence charSequence) {
        textFilter.setText(charSequence);
    }


    /**
     * 设置选中状态
     *
     * @param selected
     */
    public void setFilterTabSelected(boolean selected) {
        boolean selectedState = isSelected();
        if (selectedState && selected) {//去除无效的状态
            return;
        }
        //设置切换选中状态
        setSelected(selected);
        //改变箭头方向
        rotateArrow(selected);

        //通知观察者选中状态改变
        tabSelectedObervable.notifyTabChanged(this, selected);

    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        textFilter.setTextColor(getResources().getColor(selected ? R.color.shallow_red : R.color.gray));
    }

    /**
     * 旋转箭头：选中true，箭头向上，取消：false,箭头向下
     *
     * @param up
     */
    private void rotateArrow(boolean up) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(imgArrow, "rotation", up ? 0f : 180f, up ? 180f : 360f);
        rotation.setInterpolator(new LinearOutSlowInInterpolator());
        rotation.setDuration(DURATION_ROTATE);
        rotation.start();
    }

    /**
     * 添加状态改变的监听
     *
     * @param listener
     */
    public void addTabSelectedChangeListener(OnTabSelectedChangeListener listener) {
        tabSelectedObervable.registerObserver(listener);
    }

    /**
     * 移除状态改变的监听
     *
     * @param listener
     */
    public void removeTabSelectedChangeListener(OnTabSelectedChangeListener listener) {
        tabSelectedObervable.unregisterObserver(listener);
    }


}
