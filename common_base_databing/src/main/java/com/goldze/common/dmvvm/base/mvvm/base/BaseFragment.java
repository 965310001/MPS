package com.goldze.common.dmvvm.base.mvvm.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goldze.common.dmvvm.ILoadManager;
import com.goldze.common.dmvvm.base.mvvm.stateview.ErrorState;
import com.goldze.common.dmvvm.base.mvvm.stateview.LoadingState;
import com.goldze.common.dmvvm.utils.FragmentUserVisibleController;
import com.goldze.common.dmvvm.utils.StatusBarUtils;
import com.socks.library.KLog;
import com.tqzhang.stateview.core.LoadManager;
import com.tqzhang.stateview.stateview.BaseStateControl;

/**
 * @author GuoFeng
 * @date :2019/1/17 14:57
 * @description: 基类Fragment
 * <p>
 * 修订：徐小斌
 * 最后修订时间：2019/4/18
 * 全部修订内容：
 * 1.加入FragmentUserVisibleController处理fragment可见和不可见时在某些情况下不能正常触发回调的问题。
 * 2.加入StatusBarUtils沉浸式状态栏控制方法：setDarkMode(boolean darkMode)  setTitlePadding(View view)
 */
public abstract class BaseFragment<VD extends ViewDataBinding> extends Fragment implements ILoadManager,
        FragmentUserVisibleController.UserVisibleCallback {

    private View rootView;

    protected FragmentActivity activity;

    private LoadManager loadManager;

    protected boolean mIsFirstVisible = true;

    protected VD binding;

    private FragmentUserVisibleController userVisibleController;

    {
        userVisibleController = new FragmentUserVisibleController(this, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        if (null == rootView) {
            binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);
            rootView = binding.getRoot();
            View contentLayout = rootView.findViewById(getContentResId());
            loadManager = new LoadManager.Builder()
                    .setViewParams(contentLayout == null ? rootView : contentLayout)
                    .setListener(new BaseStateControl.OnRefreshListener() {
                        @Override
                        public void onRefresh(View v) {
                            onStateRefresh();
                        }
                    })
                    .build();
            showSuccess();
            initView(state);
        }
        return rootView;
    }

    /* IOS式状态栏沉浸 */

    /**
     * 设置顶部标题栏内部控件不超出内容区域（不覆盖到状态栏区域，用于实现IOS式真沉浸式状态栏）
     *
     * @param view
     */
    protected void setTitlePadding(View view) {
        StatusBarUtils.setPaddingSmart(activity, view);
    }

    /**
     * 设置状态栏字体颜色
     *
     * @param darkMode true 黑色，建议在浅色背景色使用，例如白色背景色。  false 白色，建议在深色背景色时使用，例如蓝色，黑色，红色。
     */
    protected void setDarkMode(boolean darkMode) {
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).setDarkMode(darkMode);
        } else {
            StatusBarUtils.immersive(activity, darkMode);
        }
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        boolean isVis = isHidden() || getUserVisibleHint();
//        if (isVis && mIsFirstVisible) {
//            lazyLoad();
//            mIsFirstVisible = false;
//        }
    }

    protected abstract int getLayoutResId();

    protected abstract int getContentResId();

    /*初始化views*/
    public abstract void initView(Bundle state);

    protected void onStateRefresh() {
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        userVisibleController.onHiddenChanged(hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        userVisibleController.setUserVisibleHint(isVisibleToUser);
    }

    /*数据懒加载*/
    protected void lazyLoad() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (FragmentActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.activity = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.activity = null;
        if (null != binding) {
            binding.unbind();
        }
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T getViewById(int id) {
        return (T) rootView.findViewById(id);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userVisibleController.activityCreated();
    }

    @Override
    public boolean isWaitingShowToUser() {
        return userVisibleController.isWaitingShowToUser();
    }

    @Override
    public void setWaitingShowToUser(boolean waitingShowToUser) {
        userVisibleController.setWaitingShowToUser(waitingShowToUser);
    }

    @Override
    public boolean isVisibleToUser() {
        return userVisibleController.isVisibleToUser();
    }

    @Override
    public void callSuperSetUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onVisibleToUserChanged(boolean isVisibleToUser, boolean invokeInResumeOrPause) {
        Log.i(this.getClass().getSimpleName(), "This Fragment is visible to user --->> " + isVisibleToUser);
        if (isVisibleToUser) {
            // 对用户可见
            if (mIsFirstVisible && isResumed()) {
                lazyLoad();
                mIsFirstVisible = false;
            }
            onVisible();
        } else {
            // 对用户不可见
            onInVisible();
        }
    }

    /**
     * 对用户可见时触发该方法。如果只想在对用户可见时才加载数据，在子类中重写该方法
     */
    protected void onVisible() {
    }

    /**
     * 对用户不可见时触发该方法
     */
    protected void onInVisible() {
    }

    @Override
    public void onResume() {
        super.onResume();
        userVisibleController.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        userVisibleController.pause();
    }

    protected void reGetData() {
        if (mIsFirstVisible) {
            return;
        }
        //重设为需要获取数据，在fg恢复可见或本来就处于可见状态时的时候会触发lazyLoad()。
        lazyLoad();
    }
}
