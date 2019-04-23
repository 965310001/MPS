package com.mingpinmall.classz.widget;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.widget.CountClickView;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.adapter.CustomDefaultFlowTagAdapter;
import com.mingpinmall.classz.databinding.MarketPopGoodsSpecificationBinding;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.socks.library.KLog;
import com.xuexiang.xui.widget.flowlayout.FlowLayout;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;

import java.util.Arrays;
import java.util.List;

/**
 * 商品规格Pop
 */
public class GoodsSpecificationPop extends PopupWindow implements CountClickView.OnClickAfterListener {

    private Context context;
    @SuppressLint("StaticFieldLeak")
    private static GoodsSpecificationPop specificationPop;
    private GoodsInfo goodsInfo;

    int[] ints = new int[3];
    StringBuilder stringBuilder;

    public GoodsSpecificationPop(Context context) {
        this.context = context;
    }

    public static synchronized GoodsSpecificationPop getInstance(Context context) {
        if (specificationPop == null) {
            specificationPop = new GoodsSpecificationPop(context);
        }
        return specificationPop;
    }

    MarketPopGoodsSpecificationBinding bind;

    public GoodsSpecificationPop setGoodsInfo(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
        return this;
    }


    public void show(View parent) {
        View view = View.inflate(context, R.layout.market_pop_goods_specification, null);
        bind = DataBindingUtil.bind(view);
        setAnimationStyle(R.style.AnimSheetBottom);
        setBackgroundDrawable(new ColorDrawable(0));
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(bind.getRoot());
        showAtLocation(parent, Gravity.CENTER, 0, 0);
        loadData();
        update();

        bind.viewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        bind.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        /*添加属性*/
        TextView textView;
        List<String> stringList;
        FlowTagLayout flowLayout;
        CustomDefaultFlowTagAdapter adapter;
        View chieView;
        for (int index = 0; index < goodsInfo.news_goods_spec_name.size(); index++) {
            chieView = View.inflate(context, R.layout.item_text, null);
            chieView.setPadding(context.getResources().getDimensionPixelSize(R.dimen.dp_4),
                    context.getResources().getDimensionPixelSize(R.dimen.dp_4),
                    0,
                    context.getResources().getDimensionPixelSize(R.dimen.dp_4));
            textView = chieView.findViewById(R.id.text);
            textView.setTextColor(context.getResources().getColor(R.color.gray));
            textView.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.text_8));
            textView.setText(goodsInfo.news_goods_spec_name.get(index));
            bind.llAttar.addView(chieView);
            stringList = goodsInfo.news_goods_spec_value.get(index);
            flowLayout = new FlowTagLayout(context);
            adapter = new CustomDefaultFlowTagAdapter(context);
            flowLayout.setAdapter(adapter);
            flowLayout.setItems(stringList);
            flowLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);

            for (int i = 0; i < stringList.size(); i++) {
                if (stringList.get(i).equals(goodsInfo.news_goods_spec.get(index))) {
                    flowLayout.setSelectedPositions(i);
                    ints[index] = goodsInfo.news_spec_data.get(index).getSpec_value().get(i).getSpe_id();
                }
            }
            bind.llAttar.addView(flowLayout);
            final int finalIndex = index;
            flowLayout.setOnTagSelectListener(new FlowTagLayout.OnTagSelectListener() {
                @Override
                public void onItemSelect(FlowTagLayout parent, int position, List<Integer> selectedList) {
                    ints[finalIndex] = goodsInfo.news_spec_data.get(finalIndex).getSpec_value().get(position).getSpe_id();
                    stringBuilder = new StringBuilder();
                    for (int anInt : ints) {
                        stringBuilder.append(anInt).append("|");
                    }
                    for (GoodsInfo.NewsSpecListDataBean newsSpecListDataBean : goodsInfo.news_spec_list_data) {
                        KLog.i(newsSpecListDataBean.getKey() + "==" + stringBuilder.toString());
                        if (stringBuilder.toString().contains(newsSpecListDataBean.getKey())) {
                            KLog.i(newsSpecListDataBean.getVal());
                            LiveBus.getDefault().postEvent("GOODSSPECIFICATIONPOP_VAL", "GOODSSPECIFICATIONPOP_VAL", newsSpecListDataBean.getVal());
                            break;
                        }
                    }
                }
            });
        }
    }

    private void loadData() {
        bind.setData(goodsInfo);
        bind.setListener(this);
        bind.executePendingBindings();
    }

    @Override
    public void onAfter(int value) {
        KLog.i(value + "");
        SharePreferenceUtil.saveKeyValue("ccvclick_goods_num", String.valueOf(value));
    }

    @Override
    public void onMin() {

    }
}