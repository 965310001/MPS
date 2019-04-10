package com.mingpinmall.classz.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.MarketPopGoodsSpecificationBinding;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;

/**
 * 商品规格Pop
 */
public class GoodsSpecificationPop extends PopupWindow {

//    private String imgUrl = "https://img14.360buyimg.com/n0/jfs/t1/1867/31/11716/401006/5bd072f8E6db292ab/f3610e2e816ade0f.jpg";
//    private String[] colors = new String[]{"黑色", "宝石蓝", "翡翠绿", "红色", "藏青色", "胡杨黄"};
//    private String[] versions = new String[]{"6GB+128G", "8GB+128GB", "8GB+256GB", "10GB+256GB"};
//    private String[] buyWays = new String[]{"官网套餐", "碎屏保险服务", "故宫特别版", "贴膜套装"};

    private Context context;
    @SuppressLint("StaticFieldLeak")
    private static GoodsSpecificationPop specificationPop;
    private GoodsInfo goodsInfo;

//    @BindView(R2.id.fl_color)
//    FlowLayout flColor;//颜色
//    @BindView(R2.id.fl_version)
//    FlowLayout flVersion;//版本
//    @BindView(R2.id.fl_buy_way)
//    FlowLayout flBuyWay;//购买方式
//    @BindView(R2.id.iv_goods_img)
//    ImageView imageView;

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
    }

    private void loadData() {
//        ImageUtils.loadImage(bind.ivGoodsImg, goodsInfo.getGoods_image_url());

        bind.ccvClick.setMaxCount(Integer.parseInt(goodsInfo.getGoods_storage()));
        bind.ccvClick.setMinCount(1);

        bind.setData(goodsInfo);
        bind.executePendingBindings();
//        flColor.removeAllViews();
//        //加载颜色
//        for (String color : colors) {
//            TextView textView = new TextView(context);
//            textView.setText(color);
//            textView.setBackgroundResource(R.drawable.market_bg_of_ellipse_button_select);
////            textView.setTextColor(R.color.market_goods_button_text_color);
//            textView.setTextColor(Color.BLACK);
//            ViewGroup.MarginLayoutParams mlp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            mlp.setMargins(0, PxUtils.dp2px(5), PxUtils.dp2px(5), 0);
//            textView.setLayoutParams(mlp);
//            textView.setPadding(PxUtils.dp2px(10), PxUtils.dp2px(4), PxUtils.dp2px(10), PxUtils.dp2px(4));
//            textView.setGravity(Gravity.CENTER);
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    v.setSelected(!v.isSelected());
//                }
//            });
//
//            flColor.addView(textView);
//        }

    }

}