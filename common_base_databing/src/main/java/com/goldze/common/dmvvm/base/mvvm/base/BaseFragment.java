package com.goldze.common.dmvvm.base.mvvm.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goldze.common.dmvvm.ILoadManager;
import com.goldze.common.dmvvm.base.mvvm.stateview.ErrorState;
import com.goldze.common.dmvvm.base.mvvm.stateview.LoadingState;
import com.tqzhang.stateview.core.LoadManager;
import com.tqzhang.stateview.stateview.BaseStateControl;

/**
 * @author GuoFeng
 * @date :2019/1/17 14:57
 * @description: 基类Fragment
 */
public abstract class BaseFragment<VD extends ViewDataBinding> extends Fragment implements ILoadManager {

    private View rootView;

    protected FragmentActivity activity;

    private LoadManager loadManager;

    protected boolean mIsFirstVisible = true;

    protected VD binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
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
        return rootView;
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
        boolean isVis = isHidden() || getUserVisibleHint();
        if (isVis && mIsFirstVisible) {
            lazyLoad();
            mIsFirstVisible = false;
        }
    }

    protected abstract int getLayoutResId();

    protected abstract int getContentResId();

    /*初始化views*/
    public abstract void initView(Bundle state);

    protected void onStateRefresh() {
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    /*当界面可见时的操作*/
    protected void onVisible() {
        if (mIsFirstVisible && isResumed()) {
            lazyLoad();
            mIsFirstVisible = false;
        }
    }

    /*数据懒加载*/
    protected void lazyLoad() {
    }

    /*当界面不可见时的操作*/
    protected void onInVisible() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.activity = null;
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

    @SuppressWarnings("unchecked")
    protected <T extends View> T getViewById(int id) {
        return (T) rootView.findViewById(id);
    }

}
