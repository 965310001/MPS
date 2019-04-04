package com.mingpinmall.classz.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.goldze.common.dmvvm.utils.DisplayUtil;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.FragmentScreeningBinding;

/**
 * @author GuoFeng
 * @date :2019/4/4 16:11
 * @description: 筛选PopWindow
 */
public class ScreeningPopWindow extends PopupWindow {
    private Context context;

    public ScreeningPopWindow(Context context) {
        this(context, null);
    }

    public ScreeningPopWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWidth((int) (DisplayUtil.getScreenWidth(context) * 0.8));
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.context = context;
        initView();
    }

    private void initView() {
        setFocusable(true);
        setOutsideTouchable(true);
        FragmentScreeningBinding bind = DataBindingUtil
                .bind(LayoutInflater.from(context)
                        .inflate(R.layout.fragment_screening, null));
        View view = bind.getRoot();

        view.setBackgroundColor(context.getResources().getColor(R.color.color_f8f8f8));
        DrawerLayout drawerLayout = bind.dl;
        drawerLayout.openDrawer(Gravity.END);
        drawerLayout.setScrimColor(Color.TRANSPARENT);


        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

        }
        setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.color_33000000)));

        setContentView(view);

    }


}
