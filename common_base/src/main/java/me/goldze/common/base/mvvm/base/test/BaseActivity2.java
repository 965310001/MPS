//package me.goldze.common.base.mvvm.base.test;
//
//import android.content.pm.ActivityInfo;
//import android.content.res.Configuration;
//import android.content.res.Resources;
//import android.database.DatabaseUtils;
//import android.databinding.DataBindingUtil;
//import android.databinding.ViewDataBinding;
//import android.os.Bundle;
//import android.support.annotation.ColorRes;
//import android.support.annotation.LayoutRes;
//import android.support.annotation.Nullable;
//import android.support.v4.app.FragmentActivity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.gyf.barlibrary.ImmersionBar;
//
//import com.tqzhang.stateview.core.LoadManager;
//import com.tqzhang.stateview.stateview.BaseStateControl;
//import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
//
//import org.w3c.dom.Text;
//
//import butterknife.ButterKnife;
//import butterknife.Unbinder;
//import me.goldze.common.ILoadManager;
//import me.goldze.common.R;
//import me.goldze.common.base.mvvm.stateview.ErrorState;
//import me.goldze.common.base.mvvm.stateview.LoadingState;
//import me.goldze.common.databinding.ActivityBase2Binding;
//import me.goldze.common.utils.DateUtils;
//
///**
// * @author GuoFeng
// * @date :2019/1/16 14:40
// * @description: 基类Activity
// */
//public abstract class BaseActivity2<VD extends ViewDataBinding> extends FragmentActivity implements View.OnClickListener, ILoadManager {
//
//    private LoadManager loadManager;
//
//    private ImageView ivBack, ivSearch;
//    private MaterialEditText edSearch;
//    private TextView tvTitle, tvRight;
//    private RelativeLayout rlTitleBar;
//
////    private Unbinder unBinder;
//    private ImmersionBar mImmersionBar;
//    private ViewGroup contentLayout;
//
//    protected VD binding;
//
//    private ActivityBase2Binding baseBinding;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        /*竖屏*/
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        super.onCreate(savedInstanceState);
//        /*android软键盘挡住输入框问题*/
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//
//        initViewDataBinding();
//        initButterKnife();
//
//        initLoadManager();
//
//        if (isActionBar()) {
//            ivBack = findViewById(R.id.iv_back);
//            tvTitle = findViewById(R.id.tv_title);
//            rlTitleBar = findViewById(R.id.rl_title_bar);
//            tvRight = findViewById(R.id.tv_right);
//            ivSearch = findViewById(R.id.iv_search);
//            edSearch = findViewById(R.id.ed_search);
//            rlTitleBar.setVisibility(View.VISIBLE);
//
//            setTitle(getTitle());
//            ivBack.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    finish();
//                }
//            });
//            ivBack.setVisibility(isBack() ? View.VISIBLE : View.GONE);
//        }
//
//        /*沉浸式状态栏*/
//        initImmersionBar(R.color.colorPrimaryDark);
//
//        initViews(savedInstanceState);
//        initData();
//    }
//
//    /**
//     * ButterKnife 注入
//     */
//    private void initButterKnife() {
////        unBinder = ButterKnife.bind(this);
//    }
//
//    /**
//     * ButterKnife unbind
//     */
//    private void unbindButterKnife() {
////        if (unBinder != null) {
////            unBinder.unbind();
////        }
//    }
//
//    /**
//     * DataBing 注入绑定
//     */
//    private void initViewDataBinding() {
//        baseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_base2, null, false);
//        binding = DataBindingUtil.inflate(LayoutInflater.from(this), getLayoutId(), null, false);
//        contentLayout = baseBinding.flContent;
//        contentLayout.addView(binding.getRoot());
//        setContentView(baseBinding.getRoot());
//    }
//
//    /**
//     * 初始化LoadManager
//     */
//    private void initLoadManager() {
//        loadManager = new LoadManager.Builder().setViewParams(contentLayout)
//                .setListener(new BaseStateControl.OnRefreshListener() {
//                    @Override
//                    public void onRefresh(View v) {
//                        onStateRefresh();
//                    }
//                }).build();
//        showSuccess();
//    }
//
//    /************************************************** LoadManager start *****************************************************/
//    /*加载数据成功*/
//    @Override
//    public void showSuccess() {
//        loadManager.showSuccess();
//    }
//
//    /*加载数据失败*/
//    @Override
//    public void showErrorState() {
//        showErrorState(ErrorState.class);
//    }
//
//    @Override
//    public void showErrorState(Class<? extends BaseStateControl> stateView) {
//        loadManager.showStateView(stateView);
//    }
//
//    /*正在加载数据*/
//    @Override
//    public void showLoadingState() {
//        showLoadingState(LoadingState.class);
//    }
//
//    @Override
//    public void showLoadingState(Class<? extends BaseStateControl> stateView) {
//        loadManager.showStateView(stateView);
//    }
//
//    @Override
//    public void showStateView(Class<? extends BaseStateControl> stateView) {
//
//    }
//
//    @Override
//    public void showStateView(Class<? extends BaseStateControl> stateView, Object tag) {
//        loadManager.showStateView(stateView, tag);
//    }
//
//    /************************************************** LoadManager end *****************************************************/
//
//    /*字体适配*/
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        if (newConfig.fontScale != -1) getResources();
//        super.onConfigurationChanged(newConfig);
//    }
//
//    @Override
//    public Resources getResources() {
//        Resources res = super.getResources();
//        if (res.getConfiguration().fontScale != 1) {//非默认值
//            Configuration newConfig = new Configuration();
//            newConfig.setToDefaults();//设置默认
//            res.updateConfiguration(newConfig, res.getDisplayMetrics());
//        }
//        return res;
//    }
//
//    /*默认是显示的*/
//    protected boolean isBack() {
//        return true;
//    }
//
//    @Override
//    public void setTitle(int titleId) {
//        super.setTitle(titleId);
//        tvTitle.setText(titleId);
//    }
//
//    @Override
//    public void setTitle(CharSequence title) {
//        super.setTitle(title);
//        tvTitle.setText(title);
//    }
//
//    /*沉浸栏颜色*/
//    protected void initImmersionBar(@ColorRes int color) {
//        mImmersionBar = ImmersionBar.with(this);
//        if (color != 0) {
//            mImmersionBar.statusBarColor(color);
//        }
//        mImmersionBar.init();
//    }
//
//    /*是否需要ActionBar*/
//    protected boolean isActionBar() {
//        return true;
//    }
//
//    /*初始化数据*/
//    protected void initData() {
//    }
//
//    /*设置布局layout*/
//    protected abstract @LayoutRes
//    int getLayoutId();
//
//    protected void onStateRefresh() {
//    }
//
//    /*初始化views*/
//    protected abstract void initViews(Bundle savedInstanceState);
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        unbindButterKnife();
//        //必须调用该方法，防止内存泄漏
//        if (null != mImmersionBar) {
//            mImmersionBar.destroy();
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.iv_back) {
//            finish();
//        }
//    }
//}