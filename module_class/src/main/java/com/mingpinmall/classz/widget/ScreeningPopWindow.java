package com.mingpinmall.classz.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.utils.AssetsUtils;
import com.goldze.common.dmvvm.utils.ColorUtil;
import com.goldze.common.dmvvm.utils.DisplayUtil;
import com.goldze.common.dmvvm.utils.Utils;
import com.google.gson.Gson;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.FragmentScreeningBinding;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.AreaListInfo;
import com.mingpinmall.classz.ui.vm.bean.ScreenInfo;
import com.mingpinmall.classz.utils.AssetsData;
import com.socks.library.KLog;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author GuoFeng
 * @date :2019/4/4 16:11
 * @description: 筛选PopWindow
 */
public class ScreeningPopWindow extends PopupWindow {

    public ScreeningPopWindow() {
    }

    public ScreeningPopWindow(Context context, View view) {
        //这里可以修改popupwindow的宽高
        super(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(view);
        initViews();
    }

    private void initViews() {
        setAnimationStyle(R.style.popwinscreening_anim_style);
        setFocusable(true);
        setOutsideTouchable(true);
    }

    public static class Builder {
        private Context context;
        private List<String> listData;
        private int columnCount;
        //        private GridLayout rootGridLayout;
        private LinearLayout contextll;
        //背景颜色
        private int colorBg = Color.parseColor("#F8F8F8");
        private int titleTextSize = 14;//SP
        private int tabTextSize = 14;//SP
        private int titleTextColor = Color.parseColor("#333333");//标题字体颜色
        private int tabTextColor = R.color.fit_item_textcolor;//选项字体颜色
        private int tabBgDrawable = R.drawable.item_lable_bg_shape;//选项背景颜色
        //当前加载的行数
        private int row = -1;
        private ScreeningPopWindow mScreeningPopWindow;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置数据源
         *
         * @return
         */
        public ScreeningPopWindow.Builder setDataSource(List<String> listData) {
            this.listData = listData;
            return this;
        }

        public ScreeningPopWindow.Builder setColumnCount(int columnCount) {
            this.columnCount = columnCount;
            return this;
        }

        public ScreeningPopWindow.Builder setColorBg(int color) {
            colorBg = context.getResources().getColor(color);
            return this;
        }

        public ScreeningPopWindow.Builder setTitleTextSize(int titleTextSize) {
            this.titleTextSize = titleTextSize;
            return this;
        }

        public ScreeningPopWindow.Builder setTabTextSize(int tabTextSize) {
            this.tabTextSize = tabTextSize;
            return this;
        }

        public ScreeningPopWindow.Builder setTitleTextColor(int titleTextColor) {
            this.titleTextColor = titleTextColor;
            return this;
        }

        public ScreeningPopWindow.Builder setTabTextColor(int tabTextColor) {
            this.tabTextColor = tabTextColor;
            return this;
        }

        public ScreeningPopWindow.Builder setTabBgDrawable(int tabBgDrawable) {
            this.tabBgDrawable = tabBgDrawable;
            return this;
        }

        public ScreeningPopWindow.Builder build() {
            newItemLayout();
            return this;
        }

        private void newItemLayout() {
            contextll = new LinearLayout(context);
            contextll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            contextll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mScreeningPopWindow != null) {
                        mScreeningPopWindow.dismiss();
                    }
                }
            });
            final FragmentScreeningBinding bind = DataBindingUtil
                    .bind(LayoutInflater.from(context)
                            .inflate(R.layout.fragment_screening, null));
            View contentView = bind.getRoot();
            bind.contentLayout.setBackgroundColor(colorBg);
//            bind.msAddressSelect.setItems(ResUtils.getStringArray(R.array.tags_values_type));
//            WidgetUtils.initSpinnerStyle(bind.spinnerSystem, ResUtils.getStringArray(R.array.tags_values_type));
            WidgetUtils.initSpinnerStyle(bind.spinnerSystem, AssetsData.getAreaListInfos());
//            bind.spinnerSystem.getSelectedItem().toString();
//            KLog.i(bind.spinnerSystem.getSelectedItem().toString());
            /*类型*/
            bind.ftlGoodType.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
            bind.ftlGoodType.setItems(context.getResources().getStringArray(R.array.tags_values_server));
            bind.ftlShopType.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
            bind.ftlShopType.setItems("平台自营");
            bind.ftlShopServer.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
            bind.ftlShopServer.setItems(context.getResources().getStringArray(R.array.tags_values_type));

            /*点击事件*/
            bind.btnReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (EditText editText : Arrays.asList(bind.etPriceFrom, bind.etPriceTo))
                        editText.setText("");
                    for (FlowTagLayout flowTagLayout : Arrays.asList(bind.ftlGoodType, bind.ftlShopType, bind.ftlShopServer))
                        flowTagLayout.getAdapter().notifyDataSetChanged();
                    bind.spinnerSystem.setSelection(0);
                }
            });
            bind.btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ScreenInfo screenInfo = new ScreenInfo();
                    screenInfo.priceFrom = bind.etPriceFrom.getText().toString().trim();
                    screenInfo.priceTo = bind.etPriceTo.getText().toString().trim();
                    List<String> goodsType = screenInfo.goodsType = new ArrayList<>();
                    goodsType.clear();
                    for (Integer index : bind.ftlGoodType.getSelectedIndexs()) {
                        goodsType.add(context.getResources().getStringArray(R.array.tags_values_server_index)[index]);
                    }
                    for (Integer index : bind.ftlShopType.getSelectedIndexs()) {
                        screenInfo.shoppingType = true;
                    }
                    List<String> shoppingServer = screenInfo.shoppingServer = new ArrayList<>();
//                    shoppingServer.clear();
                    for (Integer index : bind.ftlShopServer.getSelectedIndexs()) {
                        shoppingServer.add(String.valueOf(index));
                    }
                    screenInfo.areaId = AssetsData.getAreaByName(bind.spinnerSystem.getSelectedItem().toString());
                    LiveBus.getDefault().postEvent(Constants.CUSTOMPOPWINDOW_KEY[1], screenInfo);
                    mScreeningPopWindow.dismiss();
                }
            });
            /*titlebar*/
            View ivBack = bind.getRoot().findViewById(R.id.iv_back);
            ivBack.setVisibility(View.VISIBLE);
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mScreeningPopWindow.dismiss();
                }
            });

            bind.getRoot().findViewById(R.id.rl_title_bar).setVisibility(View.VISIBLE);
            TextView tvTitle = bind.getRoot().findViewById(R.id.tv_title);
            tvTitle.setText("筛选");
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            contextll.addView(contentView, lp);
        }


        public ScreeningPopWindow createPop() {
            if (listData == null || listData.size() == 0) {
                try {
                    throw new Exception("没有筛选条件");
                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                return null;
            }
            mScreeningPopWindow = new ScreeningPopWindow(context, contextll);
            return mScreeningPopWindow;
        }

    }


}
