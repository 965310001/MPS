package com.goldze.common.dmvvm.base.mvvm;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.base.mvvm.stateview.ErrorState;
import com.goldze.common.dmvvm.base.mvvm.stateview.StateConstants;
import com.goldze.common.dmvvm.utils.TUtil;
import com.tqzhang.stateview.stateview.BaseStateControl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GuoFeng
 * @date :2019/1/17 17:30
 * @description:
 */
public abstract class AbsLifecycleActivity<VD extends ViewDataBinding, T extends AbsViewModel> extends BaseActivity<VD> {

    protected T mViewModel;
    protected Object mStateEventKey;
    protected String mStateEventTag;
    private List<Object> eventKeys = new ArrayList<>();

    public AbsLifecycleActivity() {
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mViewModel = VMProviders(this, (Class<T>) TUtil.getInstance(this, 1));
//        dataObserver();
        if (null != mViewModel) {
            dataObserver();
            mStateEventKey = getStateEventKey();
            mStateEventTag = getStateEventTag();
            eventKeys.add(new StringBuilder((String) mStateEventKey).append(mStateEventTag).toString());
            LiveBus.getDefault().subscribe(mStateEventKey, mStateEventTag).observe(this, observer);
        }
    }

    /**
     * ViewPager +fragment tag
     *
     * @return
     */
    protected String getStateEventTag() {
        return "";
    }

    /**
     * get state page event key
     *
     * @return
     */
    protected abstract Object getStateEventKey();

    protected <T extends ViewModel> T VMProviders(AppCompatActivity fragment, @NonNull Class modelClass) {
        return (T) ViewModelProviders.of(fragment).get(modelClass);

    }

    protected void dataObserver() {

    }

    protected <T> MutableLiveData<T> registerObserver(Object eventKey, Class<T> tClass) {

        return registerObserver(eventKey, null, tClass);
    }

    protected <T> MutableLiveData<T> registerObserver(Object eventKey, String tag) {

        return registerObserver(eventKey, tag, null);
    }

    protected <T> MutableLiveData<T> registerObserver(Object eventKey, String tag, Class<T> tClass) {
        String event;
        if (TextUtils.isEmpty(tag)) {
            event = (String) eventKey;
        } else {
            event = eventKey + tag;
        }
        eventKeys.add(event);
        if (tClass == null) {
            return LiveBus.getDefault().subscribe(eventKey, tag);
        }
        return LiveBus.getDefault().subscribe(eventKey, tag, tClass);
    }

    @Override
    protected void onStateRefresh() {
        showLoading();
        initData();
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

    protected Observer observer = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String state) {
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
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (eventKeys != null && eventKeys.size() > 0) {
            for (int i = 0; i < eventKeys.size(); i++) {
                LiveBus.getDefault().clear(eventKeys.get(i));
            }
        }
    }
}