package com.mingpinmall.classz.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.widget.stackLabel.StackLabel;
import com.goldze.common.dmvvm.widget.stackLabel.StackLabelAdapter;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.FragmentScreeningBinding;
import com.mingpinmall.classz.ui.vm.bean.ScreenInfo;
import com.mingpinmall.classz.ui.vm.bean.ScreeningBean;
import com.mingpinmall.classz.utils.AssetsData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getContentView().getContext(), R.color.gray)));
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
//            bind.contentLayout.setBackgroundColor(colorBg);
//            bind.msAddressSelect.setItems(ResUtils.getStringArray(R.array.tags_values_type));
//            WidgetUtils.initSpinnerStyle(bind.spinnerSystem, ResUtils.getStringArray(R.array.tags_values_type));
//            bind.spinnerSystem.getSelectedItem().toString();
//            KLog.i(bind.spinnerSystem.getSelectedItem().toString());
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.item_text1, R.id.text, AssetsData.getAreaListInfos());
            bind.spinnerSystem.setAdapter(adapter);
//            WidgetUtils.initSpinnerStyle(bind.spinnerSystem, AssetsData.getAreaListInfos());
            /*类型*/

            bind.slGoodType.setAdapter((StackLabelAdapter.CovertData<String>) (data, position) -> data);
            bind.slGoodType.setData(Arrays.asList(context.getResources().getStringArray(R.array.tags_values_server)));

            bind.slShopType.setAdapter((StackLabelAdapter.CovertData<String>) (data, position) -> data);
            bind.slShopType.setData(Collections.singletonList("平台自营"));

            bind.slShopServer.setAdapter((StackLabelAdapter.CovertData<String>) (data, position) -> data);
            bind.slShopServer.setData(Arrays.asList(context.getResources().getStringArray(R.array.tags_values_type)));

            /*点击事件*/
            bind.btnReset.setOnClickListener(v -> {
                for (EditText editText : Arrays.asList(bind.etPriceFrom, bind.etPriceTo)) {
                    editText.setText("");
                }
                for (StackLabel stackLabel : Arrays.asList(bind.slGoodType, bind.slShopType, bind.slShopServer)) {
                    stackLabel.notifyDataSetChanged();
                }
                bind.spinnerSystem.setSelection(0);
            });
            bind.btnOk.setOnClickListener(v -> {
                ScreenInfo screenInfo = new ScreenInfo();
                screenInfo.priceFrom = bind.etPriceFrom.getText().toString().trim();
                screenInfo.priceTo = bind.etPriceTo.getText().toString().trim();
//                List<String> goodsType = screenInfo.goodsType = new ArrayList<>();
//                goodsType.clear();
//                for (Integer index : bind.slGoodType.getSelectedIndexs()) {
//                    goodsType.add(context.getResources().getStringArray(R.array.tags_values_server_index)[index]);
//                }
//                for (Integer index : bind.slShopType.getSelectedIndexs()) {
//                    screenInfo.shoppingType = true;
//                }
//                List<String> shoppingServer = screenInfo.shoppingServer = new ArrayList<>();
//                for (Integer index : bind.slShopServer.getSelectedIndexs()) {
//                    shoppingServer.add(String.valueOf(index));
//                }
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
