package com.goldze.common.dmvvm.base.mvvm.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goldze.common.dmvvm.ILoadManager;
import com.goldze.common.dmvvm.R;
import com.goldze.common.dmvvm.base.mvvm.stateview.ErrorState;
import com.goldze.common.dmvvm.base.mvvm.stateview.LoadingState;
import com.goldze.common.dmvvm.databinding.ActivityBaseBinding;
import com.goldze.common.dmvvm.utils.StatusBarUtils;
import com.goldze.common.dmvvm.xutils.ViewUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.tqzhang.stateview.core.LoadManager;
import com.tqzhang.stateview.stateview.BaseStateControl;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.tabbar.TabControlView;

/**
 * @author GuoFeng
 * @date :2019/1/16 14:40
 * @description: 基类Activity
 */
public abstract class BaseActivity<VD extends ViewDataBinding> extends AppCompatActivity implements View.OnClickListener, ILoadManager {

    protected String TAG;

    protected ActivityBaseBinding baseBinding;
    private LoadManager loadManager;
    protected Activity activity;

    protected ImageView ivBack, ivSearch;
    protected AppCompatEditText edSearch;
    protected ConstraintLayout clSearch;
    protected TextView tvTitle, tvRight;
    private RelativeLayout rlTitleBar;
    protected TabControlView tabControlView;

    private ImmersionBar mImmersionBar;
    private ViewGroup contentLayout;
    /**
     * 黑色状态栏字体
     */
    private boolean darkMode = true;

    protected VD binding;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        XUI.initTheme(this);
        StatusBarUtils.immersive(this, darkMode);
        /*竖屏*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        activity = this;
        /*android软键盘挡住输入框问题*/
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ViewUtils.inject(this);
        initViewDataBinding();

        initLoadManager();

        if (isActionBar()) {
            ivBack = findViewById(R.id.iv_back);
            tvTitle = findViewById(R.id.tv_title);
            rlTitleBar = findViewById(R.id.rl_title_bar);
            tvRight = findViewById(R.id.tv_right);
            ivSearch = findViewById(R.id.iv_search);
            clSearch = findViewById(R.id.cl_search);
            edSearch = findViewById(R.id.ed_search);
            tabControlView = findViewById(R.id.tab_control);
            rlTitleBar.setVisibility(View.VISIBLE);

            setTitle(getTitle());
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            ivBack.setVisibility(isBack() ? View.VISIBLE : View.GONE);

            tvTitle.setVisibility(isTabsBar() ? View.GONE : View.VISIBLE);
            tabControlView.setVisibility(isTabsBar() ? View.VISIBLE : View.GONE);

            ivSearch.setOnClickListener(this);
        } else {
            //直接隐藏
            baseBinding.rlTitleContent.setVisibility(View.GONE);
        }

        /*沉浸式状态栏*/
//        initImmersionBar(R.color.colorPrimaryDark);

        initViews(savedInstanceState);
        initData();
    }

    /* IOS式状态栏沉浸 */

    /**
     * 设置顶部标题栏内部控件不超出内容区域（不覆盖到状态栏区域，用于实现IOS式真沉浸式状态栏）
     *
     * @param view
     */
    protected void setTitlePadding(View view) {
        StatusBarUtils.setPaddingSmart(this, view);
    }

    /**
     * 设置状态栏字体颜色
     *
     * @param darkMode true 黑色，建议在浅色背景色使用，例如白色背景色。  false 白色，建议在深色背景色时使用，例如蓝色，黑色，红色。
     */
    protected void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
        StatusBarUtils.immersive(this, darkMode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //每次界面恢复可见时，都重新设置一次。
        setDarkMode(darkMode);
    }

    /**
     * DataBing 注入绑定
     */
    private void initViewDataBinding() {
        baseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), getBaseLayout(), null, false);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), getLayoutId(), null, false);
        contentLayout = baseBinding.flContent;
        contentLayout.addView(binding.getRoot());
        setContentView(baseBinding.getRoot());
    }

    protected int getBaseLayout() {
        return R.layout.activity_base;
    }

    /**
     * 初始化LoadManager
     */
    private void initLoadManager() {
        loadManager = new LoadManager.Builder().setViewParams(contentLayout)
                .setListener(new BaseStateControl.OnRefreshListener() {
                    @Override
                    public void onRefresh(View v) {
                        onStateRefresh();
                    }
                }).build();
        showSuccess();
    }

    /************************************************** LoadManager start *****************************************************/
    /*加载数据成功*/
    @Override
    public void showSuccess() {
        loadManager.showSuccess();
    }

    /*加载数据失败*/
    @Override
    public void showErrorState() {
        showErrorState(ErrorState.class);
    }

    @Override
    public void showErrorState(Class<? extends BaseStateControl> stateView) {
        loadManager.showStateView(stateView);
    }

    /*正在加载数据*/
    @Override
    public void showLoadingState() {
        showLoadingState(LoadingState.class);
    }

    @Override
    public void showLoadingState(Class<? extends BaseStateControl> stateView) {
        loadManager.showStateView(stateView);
    }

    @Override
    public void showStateView(Class<? extends BaseStateControl> stateView) {

    }

    @Override
    public void showStateView(Class<? extends BaseStateControl> stateView, Object tag) {
        loadManager.showStateView(stateView, tag);
    }

    /************************************************** LoadManager end *****************************************************/

    /*字体适配*/
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != -1) {
            getResources();
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {
            //非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    /*默认是显示的*/
    protected boolean isBack() {
        return true;
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        if (isActionBar()) {
            tvTitle.setText(titleId);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (isActionBar()) {
            tvTitle.setText(title);
        }
    }

    /*沉浸栏颜色*/
    protected void initImmersionBar(@ColorRes int color) {
        mImmersionBar = ImmersionBar.with(this);
        if (color != 0) {
            mImmersionBar.statusBarColor(color);
        }
        mImmersionBar.init();
    }

    /*是否需要ActionBar*/
    protected boolean isActionBar() {
        setDarkMode(true);
        return true;
    }

    /*是否是带有分页标签的ActionBar*/
    protected boolean isTabsBar() {
        return false;
    }

    /*初始化数据*/
    protected void initData() {
    }

    /*设置布局layout*/
    protected abstract @LayoutRes
    int getLayoutId();

    protected void onStateRefresh() {
    }

    /*初始化views*/
    protected abstract void initViews(Bundle savedInstanceState);

    @Override
    public void onDestroy() {
        super.onDestroy();
        //必须调用该方法，防止内存泄漏
        if (null != mImmersionBar) {
            mImmersionBar.destroy();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish();
        } else {
            onViewClicked(v.getId());
        }
    }

    public void onViewClicked(@IdRes int viewId) {
    }
}