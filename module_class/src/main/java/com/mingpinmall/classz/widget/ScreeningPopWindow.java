package com.mingpinmall.classz.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.goldze.common.dmvvm.base.event.LiveBus;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.adapter.CustomDefaultFlowTagAdapter;
import com.mingpinmall.classz.databinding.FragmentScreeningBinding;
import com.mingpinmall.classz.ui.vm.bean.ScreenInfo;
import com.mingpinmall.classz.utils.AssetsData;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author GuoFeng
 * @date :2019/4/4 16:11
 * @description: 筛选PopWindow
 */
public class ScreeningPopWindow extends PopupWindow {

    public ScreeningPopWindow() {
    }

    public ScreeningPopWindow(Context context, View view) {
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
        private LinearLayout contextll;
        private Object eventKey;

        private ScreeningPopWindowClickListener listener;

        public interface ScreeningPopWindowClickListener {
            void onClick(PopupWindow dialog, ScreenInfo screenInfo);
        }

        public ScreeningPopWindow.Builder setListener(ScreeningPopWindowClickListener listener) {
            this.listener = listener;
            return this;
        }


        //背景颜色
        private int colorBg = Color.parseColor("#F8F8F8");

        private ScreeningPopWindow mScreeningPopWindow;

        public Builder(Context context) {
            this.context = context;
        }

        public ScreeningPopWindow.Builder setColorBg(int color) {
            colorBg = context.getResources().getColor(color);
            return this;
        }


        public ScreeningPopWindow.Builder build() {
            newItemLayout();
            return this;
        }

        public ScreeningPopWindow.Builder setEventKey(Object eventKey) {
            this.eventKey = eventKey;
            return this;
        }

        private void newItemLayout() {
            contextll = new LinearLayout(context);
            contextll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            contextll.setOnClickListener(v -> {
                if (mScreeningPopWindow != null) {
                    mScreeningPopWindow.dismiss();
                }
            });
            final FragmentScreeningBinding bind = DataBindingUtil
                    .bind(LayoutInflater.from(context)
                            .inflate(R.layout.fragment_screening, null));
            View contentView = bind.getRoot();
            bind.contentLayout.setBackgroundColor(colorBg);
//            bind.msAddressSelect.setItems(ResUtils.getStringArray(R.array.tags_values_type));
//            WidgetUtils.initSpinnerStyle(bind.spinnerSystem, ResUtils.getStringArray(R.array.tags_values_type));
//            bind.spinnerSystem.getSelectedItem().toString();
//            KLog.i(bind.spinnerSystem.getSelectedItem().toString());
            WidgetUtils.initSpinnerStyle(bind.spinnerSystem, AssetsData.getAreaListInfos());
            /*类型*/
            CustomDefaultFlowTagAdapter adapter = new CustomDefaultFlowTagAdapter(context);
            bind.ftlShopType.setAdapter(adapter);
            adapter = new CustomDefaultFlowTagAdapter(context);
            bind.ftlShopServer.setAdapter(adapter);
            adapter = new CustomDefaultFlowTagAdapter(context);
            bind.ftlGoodType.setAdapter(adapter);

            bind.ftlGoodType.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
            bind.ftlGoodType.setItems(context.getResources().getStringArray(R.array.tags_values_server));
            bind.ftlShopType.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
            bind.ftlShopType.setItems("平台自营");
            bind.ftlShopServer.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
            bind.ftlShopServer.setItems(context.getResources().getStringArray(R.array.tags_values_type));

            /*点击事件*/
            bind.btnReset.setOnClickListener(v -> {
                for (EditText editText : Arrays.asList(bind.etPriceFrom, bind.etPriceTo))
                    editText.setText("");
                for (FlowTagLayout flowTagLayout : Arrays.asList(bind.ftlGoodType, bind.ftlShopType, bind.ftlShopServer))
                    flowTagLayout.getAdapter().notifyDataSetChanged();
                bind.spinnerSystem.setSelection(0);
            });
            bind.btnOk.setOnClickListener(v -> {
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
                if (null != eventKey) {
                    LiveBus.getDefault().postEvent(eventKey, screenInfo);
                } else if (null != listener) {
                    listener.onClick(mScreeningPopWindow, screenInfo);
                } else {
                    new NullPointerException("请设置eventKey");
                }
                mScreeningPopWindow.dismiss();
            });
            /*titlebar*/
            View ivBack = bind.getRoot().findViewById(R.id.iv_back);
            ivBack.setVisibility(View.VISIBLE);
            ivBack.setOnClickListener(v -> mScreeningPopWindow.dismiss());

            bind.getRoot().findViewById(R.id.rl_title_bar).setVisibility(View.VISIBLE);
            TextView tvTitle = bind.getRoot().findViewById(R.id.tv_title);
            tvTitle.setText("筛选");
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            contextll.addView(contentView, lp);
        }

        public ScreeningPopWindow createPop() {
            mScreeningPopWindow = new ScreeningPopWindow(context, contextll);
            return mScreeningPopWindow;
        }

    }


}
