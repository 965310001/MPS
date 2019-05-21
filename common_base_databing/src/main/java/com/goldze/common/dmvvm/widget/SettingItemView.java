package com.goldze.common.dmvvm.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.goldze.common.dmvvm.R;

/**
 * 功能描述：
 *
 * @author 小斌
 * @date 2019/3/25
 **/
public class SettingItemView extends FrameLayout {

    public enum ThemeMode {
        //默认
        Default,
        //没有左侧图标
        NoLeftImage,
        //没有左侧图标和主标题下侧描述
        NoLeftImage_NoDescription,
        //没有左侧图标，没有右侧更多图标
        NoLeftImage_NoMoreIcon,
        //没左侧图标，没更多图标，没下侧描述
        NoLeftImage_NoMoreIcon_NoDescription,
        //没更多图标
        NoMoreIcon,
        //没更多图标和下侧描述
        NoMoreIcon_NoDescription,
        //没下侧描述
        NoDescription,
    }

    /*控件*/
    /**
     * 主标题
     */
    private AppCompatTextView tv_label;
    /**
     * 副标题
     */
    private AppCompatTextView tv_label2;
    /**
     * 小红点
     */
    private AppCompatTextView tv_redPoint;
    /**
     * 描述
     */
    private AppCompatTextView tv_description;
    /**
     * 左侧图标
     */
    private AppCompatImageView iv_icon;
    /**
     * 右侧箭头
     */
    private AppCompatImageView iv_more;

    /**
     * 是否小图标模式
     */
    private boolean isSmallIcon = false;

    /**
     * 主标题内容
     */
    private String title;

    /**
     * 副标题内容
     */
    private String subTitle;

    /**
     * 主标题下方描述内容
     */
    private String description;

    /**
     * 左边图标资源
     */
    private @DrawableRes
    int drawable;

    /**
     * 样式，各种控件的隐藏和显示
     */
    private int theme = -1;

    public SettingItemView(Context context) {
        super(context);
        initView();
        setThemeMode(ThemeMode.NoDescription);
    }

    public SettingItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        initAttrs(attrs);
    }

    public SettingItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initAttrs(attrs);
    }

    private void initAttrs(@Nullable AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SettingItemView);
        isSmallIcon = a.getBoolean(R.styleable.SettingItemView_smallIcon, false);
        title = a.getString(R.styleable.SettingItemView_title);
        subTitle = a.getString(R.styleable.SettingItemView_subTitle);
        drawable = a.getResourceId(R.styleable.SettingItemView_image, 0);
        description = a.getString(R.styleable.SettingItemView_description);
        theme = a.getInt(R.styleable.SettingItemView_mode, -1);
        a.recycle();
        setSmallIconMode(isSmallIcon);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setTitle(title);
        setSubTitle(subTitle);
        setDescription(description);
        setImageIcon(drawable);
        if (theme < 0 || theme > 7) {
            theme = 0;
        }
        setThemeMode();
    }

    /**
     * 设置主题样式
     *
     * @param themeMode
     * @return
     */
    public SettingItemView setThemeMode(ThemeMode themeMode) {
        switch (themeMode) {
            case Default:
                theme = 0;
                break;
            case NoLeftImage:
                theme = 1;
                break;
            case NoLeftImage_NoDescription:
                theme = 2;
                break;
            case NoLeftImage_NoMoreIcon:
                theme = 3;
                break;
            case NoLeftImage_NoMoreIcon_NoDescription:
                theme = 4;
                break;
            case NoMoreIcon:
                theme = 5;
                break;
            case NoMoreIcon_NoDescription:
                theme = 6;
                break;
            case NoDescription:
                theme = 7;
                break;
            default:
                break;
        }
        setThemeMode();
        return this;
    }

    /***
     * 初始化主题样式
     */
    private void setThemeMode() {
        switch (theme) {
            case 0:
                //默认全有
                showLeftIcon(true);
                showDescription(true);
                showMoreIcon(true);
                break;
            case 1:
                //NoLeftImage 无左边图片
                showLeftIcon(false);
                showDescription(true);
                showMoreIcon(true);
                break;
            case 2:
                //NoLeftImage_NoDescription 无左边图片，无描述
                showLeftIcon(false);
                showDescription(false);
                showMoreIcon(true);
                break;
            case 3:
                //NoLeftImage_NoMoreIcon 无左边图片，无右边箭头
                showLeftIcon(false);
                showDescription(true);
                showMoreIcon(false);
                break;
            case 4:
                //NoLeftImage_NoMoreIcon_NoDescription 无左边图片，无右边箭头，无描述
                showLeftIcon(false);
                showDescription(false);
                showMoreIcon(false);
                break;
            case 5:
                //NoMoreIcon 无右边箭头
                showLeftIcon(true);
                showDescription(true);
                showMoreIcon(false);
                break;
            case 6:
                //NoMoreIcon_NoDescription //无右边箭头和描述
                showLeftIcon(true);
                showDescription(false);
                showMoreIcon(false);
                break;
            case 7:
                //NoDescription //无描述
                showLeftIcon(true);
                showDescription(false);
                showMoreIcon(true);
                break;
            default:
                break;
        }
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_setting_item, this, true);

        tv_label = findViewById(R.id.tv_label);
        tv_label2 = findViewById(R.id.tv_sublabel);
        tv_redPoint = findViewById(R.id.tv_redPoint);
        tv_description = findViewById(R.id.tv_description);

        iv_icon = findViewById(R.id.iv_icon);
        iv_more = findViewById(R.id.iv_more);
        tv_redPoint.setVisibility(View.GONE);
    }

    /**
     * 是否使用小图标，小图标模式将会将图标和标题栏对齐。 默认模式下是item上下居中
     *
     * @param isSmall 是否使用小图标
     */
    @BindingAdapter("smallIcon")
    public static void setSmallIconMode(SettingItemView settingItemView, boolean isSmall) {
        settingItemView.setSmallIconMode(isSmall);
    }

    public void setSmallIconMode(boolean isSmall) {
        this.isSmallIcon = isSmall;
        ConstraintSet set = new ConstraintSet();
        ConstraintLayout constraintLayout = findViewById(R.id.content_layout);
        set.clone(constraintLayout);
        set.connect(
                iv_icon.getId(),
                ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID,
                ConstraintSet.LEFT
        );
        set.connect(
                iv_icon.getId(),
                ConstraintSet.END,
                tv_label.getId(),
                ConstraintSet.START
        );
        set.connect(
                iv_icon.getId(),
                ConstraintSet.BOTTOM,
                isSmall ? tv_label.getId() : ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM
        );
        set.connect(
                iv_icon.getId(),
                ConstraintSet.TOP,
                isSmall ? tv_label.getId() : ConstraintSet.PARENT_ID,
                ConstraintSet.TOP
        );
        set.applyTo(constraintLayout);
    }

    /**
     * 设置标题TextView四周图标
     *
     * @param drawable    图标，建议不超过  20dp * 20dp  尺寸大小
     * @param orientation 方向 0 左   1 上   2 右   3 下
     * @return 返回这个标题TextView
     */
    public AppCompatTextView setTitleIcon(@DrawableRes int drawable, int orientation) {
        return setTitleIcon(ContextCompat.getDrawable(getContext(), drawable), orientation);
    }

    /**
     * 设置标题TextView四周图标
     *
     * @param drawable    图标，建议不超过  20dp * 20dp  尺寸大小
     * @param orientation 方向 0 左   1 上   2 右   3 下
     * @return 返回这个标题TextView
     */
    public AppCompatTextView setTitleIcon(Drawable drawable, int orientation) {
        tv_label.setCompoundDrawables(
                orientation == 0 ? drawable : null,
                orientation == 1 ? drawable : null,
                orientation == 2 ? drawable : null,
                orientation == 3 ? drawable : null
        );
        return tv_label;
    }

    /**
     * 获得左边图标ImageView
     *
     * @return 左边图标ImageView
     */
    public AppCompatImageView getIconImageView() {
        return iv_icon;
    }

    /**
     * 是否显示左边图标ImageView
     *
     * @param show
     * @return
     */
    public SettingItemView showLeftIcon(boolean show) {
        iv_icon.setVisibility(show ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 是否显示标题下方的描述TextView
     *
     * @param show
     * @return
     */
    public SettingItemView showDescription(boolean show) {
        tv_description.setVisibility(show ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 是否显示右边的箭头ImageView
     *
     * @param show
     * @return
     */
    @BindingAdapter("showMoreIcon")
    public static SettingItemView showMoreIcon(SettingItemView settingItemView, boolean show) {
        return settingItemView.showMoreIcon(show);
    }

    public SettingItemView showMoreIcon(boolean show) {
        iv_more.setVisibility(show ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 设置左边图标ImageView的图片
     *
     * @param drawable 图片
     * @return
     */
    public SettingItemView setImageIcon(Drawable drawable) {
        iv_icon.setImageDrawable(drawable);
        return this;
    }

    /**
     * 设置左边图标ImageView的图片
     *
     * @param bitmap 图片
     * @return
     */
    public SettingItemView setImageIcon(Bitmap bitmap) {
        iv_icon.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 设置左边图标ImageView的图片
     *
     * @param drawable 图片
     * @return
     */
    public SettingItemView setImageIcon(@DrawableRes int drawable) {
        iv_icon.setImageResource(drawable);
        return this;
    }

    /**
     * 设置标题文字内容
     *
     * @param titleId 内容资源id
     * @return
     */
    public SettingItemView setTitle(@StringRes int titleId) {
        tv_label.setText(titleId);
        return this;
    }

    /**
     * 设置标题文字内容
     *
     * @param title 内容字符串
     * @return
     */
    @BindingAdapter("title")
    public static SettingItemView setTitle(SettingItemView settingItemView, String title) {
        return settingItemView.setTitle(title);
    }

    public SettingItemView setTitle(String title) {
        tv_label.setText(title);
        return this;
    }

    /**
     * 设置标题下方描述文字内容
     *
     * @param titleId 内容资源id
     * @return
     */
    public SettingItemView setDescription(@StringRes int titleId) {
        tv_description.setText(titleId);
        return this;
    }

    /**
     * 设置标题下方描述文字内容
     *
     * @param title 内容字符串
     * @return
     */
    @BindingAdapter("description")
    public static SettingItemView setDescription(SettingItemView settingItemView, String title) {
        return settingItemView.setDescription(title);
    }

    public SettingItemView setDescription(String title) {
        tv_description.setText(title);
        return this;
    }

    /**
     * 设置右边箭头左侧 副标题 文字内容
     *
     * @param titleId
     * @return
     */
    public SettingItemView setSubTitle(@StringRes int titleId) {
        tv_label2.setText(titleId);
        return this;
    }

    /**
     * 设置右边箭头左侧 副标题 文字内容
     *
     * @param title
     * @return
     */
    @BindingAdapter("subTitle")
    public static void setSubTitle(SettingItemView settingItemView, String title) {
        settingItemView.setSubTitle(title);
    }

    public SettingItemView setSubTitle(String title) {
        tv_label2.setText(title);
        return this;
    }

    /**
     * 显示右边 红点数值，为0则不显示，超过99则显示为 99+
     *
     * @param count 数值
     */
    public void showRedPoint(int count) {
        String info = "0";
        if (count > 0 && count < 99) {
            info = String.valueOf(count);
        } else if (count > 99) {
            info = "99+";
        } else {
            tv_redPoint.setVisibility(View.GONE);
            return;
        }
        tv_redPoint.setVisibility(View.VISIBLE);
        tv_redPoint.setText(info);
    }

    /**
     * 隐藏右侧小红点
     */
    public void hideRedPoint() {
        tv_redPoint.setVisibility(View.GONE);
    }

}
