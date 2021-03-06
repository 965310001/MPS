package com.mingpinmall.classz.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.log.QLog;
import com.goldze.common.dmvvm.widget.CountClickView;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.adapter.CustomDefaultFlowTagAdapter;
import com.mingpinmall.classz.databinding.MarketPopGoodsSpecificationBinding;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;

import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;

import java.util.List;

/**
 * 商品规格Pop
 */
public class GoodsSpecificationPop extends PopupWindow implements CountClickView.OnClickAfterListener, View.OnClickListener {

    private final Context mContext;
    private static GoodsSpecificationPop INSTANCE;
    private GoodsInfo mGoodsInfo;
    private MarketPopGoodsSpecificationBinding bind;
    private int[] ints;
    private StringBuilder stringBuilder;

    private GoodsSpecificationPop(Context context) {
        this.mContext = context;
    }

    public static synchronized GoodsSpecificationPop getInstance(Context context) {
        if (INSTANCE == null) INSTANCE = new GoodsSpecificationPop(context);
        return INSTANCE;
    }

    public GoodsSpecificationPop setGoodsInfo(GoodsInfo mGoodsInfo) {
        this.mGoodsInfo = mGoodsInfo;
        return this;
    }

    public void show(View parent) {
        bind = DataBindingUtil.bind(LayoutInflater.from(mContext).inflate(R.layout.market_pop_goods_specification, null));
        if (mGoodsInfo.isVirtual()) {
            bind.tvBuyNow.setAlpha(1.0f);
            bind.tvBuyNow.setClickable(true);
        } else if (mGoodsInfo.isShop()) {
            bind.tvBuyNow.setAlpha(0.5f);
            bind.tvBuyNow.setClickable(false);
        } else {
            bind.tvBuyNow.setAlpha(1.0f);
            bind.tvBuyNow.setClickable(true);
        }
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

        /*添加属性*/
        TextView textView;
        List<String> stringList;
        FlowTagLayout flowLayout;
        CustomDefaultFlowTagAdapter adapter;
        View chideView;
        int dimensionPixelSize = mContext.getResources().getDimensionPixelSize(R.dimen.dp_4);
        int size = mGoodsInfo.news_goods_spec_name.size();
        ints = new int[size];
        for (int index = 0; index < size; index++) {
            chideView = View.inflate(mContext, R.layout.item_text, null);
            chideView.setPadding(dimensionPixelSize, dimensionPixelSize, 0, dimensionPixelSize);
            textView = chideView.findViewById(R.id.text);
            textView.setTextColor(mContext.getResources().getColor(R.color.gray));
            textView.setTextSize(mContext.getResources().getDimensionPixelSize(R.dimen.text_8));
            textView.setText(mGoodsInfo.news_goods_spec_name.get(index));
            bind.llAttar.addView(chideView);
            stringList = mGoodsInfo.news_goods_spec_value.get(index);
            flowLayout = new FlowTagLayout(mContext);
            adapter = new CustomDefaultFlowTagAdapter(mContext);
            flowLayout.setAdapter(adapter);
            flowLayout.setItems(stringList);
            flowLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
            for (int i = 0; i < stringList.size(); i++) {
                if (stringList.get(i).equals(mGoodsInfo.news_goods_spec.get(index))) {
                    flowLayout.setSelectedPositions(i);
                    ints[index] = mGoodsInfo.news_spec_data.get(index).getSpec_value().get(i).getSpe_id();
                }
            }
            bind.llAttar.addView(flowLayout);
            final int finalIndex = index;
            flowLayout.setOnTagSelectListener((parent1, position, selectedList) -> {
                ints[finalIndex] = mGoodsInfo.news_spec_data.get(finalIndex).getSpec_value().get(position).getSpe_id();
                stringBuilder = new StringBuilder();
                for (int i = ints.length - 1; i >= 0; i--) {
                    stringBuilder.append(ints[i]).append("|");
                }
                int i;
                QLog.i(ints.length);
                for (GoodsInfo.NewsSpecListDataBean newsSpecListDataBean : mGoodsInfo.news_spec_list_data) {
                    i = 0;
                    for (int anInt : ints) {
                        if (newsSpecListDataBean.getKey().contains(String.valueOf(anInt))) {
                            ++i;
                        }
                    }
                    if (i == size) {
                        QLog.i(newsSpecListDataBean.getVal());
                        LiveBus.getDefault().postEvent("GOODSSPECIFICATIONPOP_VAL", "GOODSSPECIFICATIONPOP_VAL",
                                newsSpecListDataBean.getVal());
                        break;
                    }
                }
               /* Observable.fromIterable(mGoodsInfo.news_spec_list_data)
                        .filter(newsSpecListDataBean -> stringBuilder.toString().contains(newsSpecListDataBean.getKey()))
                        .subscribe(newsSpecListDataBean -> {
                            QLog.i(newsSpecListDataBean.getVal());
                            LiveBus.getDefault().postEvent("GOODSSPECIFICATIONPOP_VAL", "GOODSSPECIFICATIONPOP_VAL",
                                    newsSpecListDataBean.getVal());
                            QLog.i(newsSpecListDataBean.getVal());
                        });*/
            });
        }

        setCartNumber(SharePreferenceUtil.getIntKeyValue("CART_NUMBER", 0));
    }

    private void setCartNumber(int count) {
        if (SharePreferenceUtil.isLogin() && count > 0) {
            bind.tvCount.setVisibility(View.VISIBLE);
            bind.tvCount.setText(String.valueOf(count));
        } else {
            bind.tvCount.setVisibility(View.GONE);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        SharePreferenceUtil.saveKeyValue("SPECIFICATIONPOP", null);
    }

    public void loadData() {
        bind.setData(mGoodsInfo);
        bind.setListener(this);
        bind.setDismissListener(this);
        bind.executePendingBindings();

        String clickGoodsNum = SharePreferenceUtil.getKeyValue("click_goods_num");
        bind.ccvClick.setCurrCount(TextUtils.isEmpty(clickGoodsNum) ? 1 : Integer.valueOf(clickGoodsNum));

        if (mGoodsInfo.isGroupbuy()) {
            bind.ccvClick.setMaxCount(Integer.valueOf(mGoodsInfo.getUpper_limit()));
        }
        if (mGoodsInfo.isGroupbuy()) {
            bind.tvPromotionType.setVisibility(View.VISIBLE);
            bind.tvPromotionType.setText("团购");
        } else if (mGoodsInfo.getPromotion_type().equals("xianshi")) {
            bind.tvPromotionType.setVisibility(View.VISIBLE);
            bind.tvPromotionType.setText("限时折扣");
        } else {
            bind.tvPromotionType.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAfter(int value) {
        SharePreferenceUtil.saveKeyValue("click_goods_num", String.valueOf(value));
    }

    @Override
    public void onMin() {
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}