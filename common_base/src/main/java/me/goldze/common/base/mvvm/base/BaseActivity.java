package me.goldze.common.base.mvvm.base;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.bugtags.library.Bugtags;
import com.tqzhang.stateview.core.LoadManager;
import com.tqzhang.stateview.stateview.BaseStateControl;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.goldze.common.ILoadManager;
import me.goldze.common.base.mvvm.stateview.ErrorState;
import me.goldze.common.base.mvvm.stateview.LoadingState;

/**
 * @author GuoFeng
 * @date :2019/1/16 14:40
 * @description: 基类Activity
 */
public abstract class BaseActivity extends FragmentActivity implements ILoadManager {

    private LoadManager loadManager;
    private Unbinder unBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        initStatusBar();
        //设置布局内容
        setContentView(getLayoutId());

        unBinder = ButterKnife.bind(this);

        loadManager = new LoadManager.Builder()
                .setViewParams(this)
                .setListener(new BaseStateControl.OnRefreshListener() {
                    @Override
                    public void onRefresh(View v) {
                        onStateRefresh();
                    }
                })
                .build();
        initViews(savedInstanceState);

        initToolBar();

        initData();
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
        if (newConfig.fontScale != -1) getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    /*初始化数据*/
    protected void initData() {

    }

    /*状态栏*/
    protected void initStatusBar() {

    }

    /*初始化toolbar*/
    protected void initToolBar() {

    }

    /*设置布局layout*/
    protected abstract @LayoutRes
    int getLayoutId();

    protected void onStateRefresh() {
    }

    /*初始化views*/
    protected abstract void initViews(Bundle savedInstanceState);


    /*显示进度条*/
    public void showProgressBar() {
    }

    /*隐藏进度条*/
    public void hideProgressBar() {

    }

    /************************************************** Bugtags start *****************************************************/

//    @Override
//    protected void onResume() {
//        super.onResume();
////        Bugtags.onResume(this);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
////        Bugtags.onPause(this);
//    }
//
//    @SuppressLint("RestrictedApi")
//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
////        Bugtags.onDispatchKeyEvent(this, event);
//        return super.dispatchKeyEvent(event);
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        //注：回调 3
//        Bugtags.onDispatchTouchEvent(this, ev);
//        return super.dispatchTouchEvent(ev);
//    }

    /************************************************** Bugtags end *****************************************************/


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unBinder != null) {
            unBinder.unbind();
        }
    }
}