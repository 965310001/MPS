package com.goldze.common.dmvvm.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldze.common.dmvvm.R;
import com.goldze.common.dmvvm.databinding.LayoutCountClickViewBinding;
import com.goldze.common.dmvvm.utils.PxUtils;


/**
 * @author GuoFeng
 * @date : 2019/1/24 11:05
 * @description: 加减Viw
 */
public class CountClickView extends LinearLayout implements View.OnClickListener {

    public static final int MIN_COUNT = 0;
    public static final int INIT_COUNT = 1;
    public static final int MAX_COUNT = 10000;

    LayoutCountClickViewBinding binding;

    TextView tvCount;
    ImageView ivPlus;
    ImageView ivMinus;
    LinearLayout llMinus;
    LinearLayout llPlus;

    private Context mContext;
    private int maxCount = MAX_COUNT;
    private int minCount = MIN_COUNT;

    //控件资源
    private int minusCan = R.drawable.input_minus_default;
    private int minusNot = R.drawable.input_minus_disabled;
    private int addCan = R.drawable.input_add_default;
    private int addNot = R.drawable.input_add_disabled;
    private boolean input = false;//是否支持如输入 默认不支持

    public CountClickView(Context context) {
        this(context, null);

    }

    public CountClickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private OnClickAfterListener afterClickListener = null;


    private void init(Context context) {
        this.mContext = context;
        this.setBackgroundResource(android.R.color.transparent);
//        ButterKnife.bind(this, View.inflate(context, R.layout.layout_count_click_view, this));

        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_count_click_view, null, false);

        tvCount = binding.tvCount;
        ivPlus = binding.ivPlus;
        ivMinus = binding.ivMinus;
        llMinus = binding.llMinus;
        llPlus = binding.llPlus;

//        tvCount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//                if (input) {
//                    new AppDialog(mContext, DialogType.COUNT)
//                            .setTitle("修改数量")
//                            .setRightButton(new AppDialog.OnButtonClickListener() {
//                                @Override
//                                public void onClick(String val) {
//                                    tvCount.setText(val);
//                                    if (afterClickListener != null) {
//                                        afterClickListener.onAfter(getCount());
//                                        if (getCount() == getMinCount()) {
//                                            afterClickListener.onMin();
//                                        }
//                                    }
//                                    judgeTheViews(Integer.valueOf(val));
//                                }
//                            })
//                            .setNumber(minCount, maxCount, getCount())
//                            .show();
//                }
//            }
//        });

        ivPlus.setOnClickListener(this);
        ivMinus.setOnClickListener(this);

        judgeTheViews(getCount());
        addView(binding.getRoot());
    }

    public int getCount() {
        String text = tvCount.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            return INIT_COUNT;
        }
        return Integer.valueOf(text);
    }

    //    @OnClick({R2.id.iv_plus, R2.id.iv_minus})
    @Override
    public void onClick(View v) {
        int count = Integer.valueOf(tvCount.getText().toString().trim());

        if (R.id.iv_plus == v.getId()) {
            if (count < getMaxCount())
                tvCount.setText(String.valueOf(++count));
        }
        if (R.id.iv_minus == v.getId()) {
            if (count > getMinCount())
                tvCount.setText(String.valueOf(--count));
        }

        judgeTheViews(count);

        if (afterClickListener != null) {
            afterClickListener.onAfter(getCount());
            if (getCount() == getMinCount()) {
                afterClickListener.onMin();
            }
        }
    }

    private void judgeTheViews(int count) {
        if (count <= getMinCount()) {
            ivMinus.setImageResource(minusNot);
        } else {
            ivMinus.setImageResource(minusCan);
        }
        if (count >= getMaxCount()) {
            ivPlus.setImageResource(addNot);
        } else {
            ivPlus.setImageResource(addCan);
        }
    }

    /**
     * 设置 按钮父类的大小
     */
    public void setBtnParentSize(int width, int height) {
        llMinus.setLayoutParams(new LayoutParams(PxUtils.dp2px(mContext, width),
                PxUtils.dp2px(mContext, height)));
        llPlus.setLayoutParams(new LayoutParams(PxUtils.dp2px(mContext, width),
                PxUtils.dp2px(mContext, height)));
        //如果设置了该处大小 则需要更新中间View的高度
        LayoutParams layoutParams = (LayoutParams) tvCount.getLayoutParams();
        layoutParams.height = PxUtils.dp2px(mContext, height);
    }

    /**
     * 设置 按钮父类背景
     */
    public void setBtnParentBg(int bgColor) {
        llMinus.setBackgroundColor(getResources().getColor(bgColor));
        llPlus.setBackgroundColor(getResources().getColor(bgColor));
    }


    /**
     * 设置加减按钮大小
     */
    public void setBtnSize(int width, int height) {
        ivPlus.setLayoutParams(new LayoutParams(PxUtils.dp2px(mContext, width),
                PxUtils.dp2px(mContext, height)));
        ivMinus.setLayoutParams(new LayoutParams(PxUtils.dp2px(mContext, width),
                PxUtils.dp2px(mContext, height)));
    }

    /**
     * 设置加减控件的资源文件
     */
    public void setButtonRes(int minusCan, int minusNot, int addCan, int addNot) {
        this.minusCan = minusCan;
        this.minusNot = minusNot;
        this.addCan = addCan;
        this.addNot = addNot;
        //给按钮设置默认值
        ivMinus.setImageResource(minusCan);
        ivPlus.setImageResource(addCan);
    }

    /***
     * 设置中间View一些属性
     * @param bgColor 背景色
     * @param textColor 字体颜色  使用默认颜色 给0 即可
     * @param marginLeft marginLeft
     * @param marginRight marginRight
     */
    public void setCountViewAttr(int bgColor, int textColor, int marginLeft, int marginRight) {
        LayoutParams layoutParams = (LayoutParams) tvCount.getLayoutParams();
        layoutParams.setMargins(PxUtils.dp2px(mContext, marginLeft), 0, PxUtils.dp2px(mContext, marginRight), 0);
        tvCount.setBackgroundColor(getResources().getColor(bgColor));
        if (textColor != 0) {
            tvCount.setTextColor(getResources().getColor(textColor));
        }
    }


    /**
     * 是否支出输入
     */
    public void setInput(boolean input) {
        this.input = input;
    }

    public void setCurrCount(int count) {
        tvCount.setText(String.valueOf(count));
        judgeTheViews(count);
    }

    public void setMaxCount(int maxCount) {
        if (maxCount < getMinCount()) {
            maxCount = INIT_COUNT;
        }

        this.maxCount = maxCount;
        judgeTheViews(getCount());
    }

    public void setMinCount(int minCount) {
        if (minCount > getMaxCount()) {
            minCount = INIT_COUNT;
        }
        this.minCount = minCount;
        judgeTheViews(getCount());
    }

    private int getMaxCount() {
        return maxCount < MAX_COUNT ? maxCount : MAX_COUNT;
    }

    private int getMinCount() {
        return minCount > MIN_COUNT ? minCount : MIN_COUNT;
    }

    public interface OnClickAfterListener {

        void onAfter(int value);

        void onMin();
    }

    public void setAfterClickListener(OnClickAfterListener afterClickListener) {
        this.afterClickListener = afterClickListener;
    }

}
