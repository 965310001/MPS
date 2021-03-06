package me.goldze.common.base.mvvm;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.tqzhang.stateview.stateview.BaseStateControl;

import me.goldze.common.base.mvvm.base.test.BaseActivity;
import me.goldze.common.base.mvvm.stateview.ErrorState;
import me.goldze.common.base.mvvm.stateview.StateConstants;
import me.goldze.common.utils.TUtil;

/**
 * @author GuoFeng
 * @date :2019/1/17 17:30
 * @description:
 */
public abstract class AbsLifecycleActivity2<T extends AbsViewModel> extends BaseActivity {
    protected T mViewModel;

    public AbsLifecycleActivity2() {
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mViewModel = VMProviders(this, TUtil.getInstance(this, 0));
        dataObserver();
    }

    protected <T extends ViewModel> T VMProviders(FragmentActivity fragment, @NonNull Class modelClass) {
        return (T) ViewModelProviders.of(fragment).get(modelClass);

    }

    protected void dataObserver() {

    }

    @Override
    protected void onStateRefresh() {
        showLoading();
    }


    protected void showError(Class<? extends BaseStateControl> stateView, Object tag) {
        showStateView(stateView, tag);
    }

    protected void showError(Class<? extends BaseStateControl> stateView) {
        showError(stateView, null);
    }

//    protected void showSuccess() {
//        showSuccess();
//    }

    protected void showLoading() {
        showLoadingState();
    }

    protected Observer observer = (Observer<String>) state -> {
        if (!TextUtils.isEmpty(state)) {
            if (StateConstants.ERROR_STATE.equals(state)) {
                showError(ErrorState.class, "2");
            } else if (StateConstants.NET_WORK_STATE.equals(state)) {
                showError(ErrorState.class, "1");
            } else if (StateConstants.LOADING_STATE.equals(state)) {
                showLoading();
            } else if (StateConstants.SUCCESS_STATE.equals(state)) {
                showSuccess();
            }
        }
    };
}