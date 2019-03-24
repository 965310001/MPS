package com.goldze.common.dmvvm;

import com.tqzhang.stateview.stateview.BaseStateControl;

/**
 * @author GuoFeng
 * @date : 2019/2/16 16:00
 * @description: LoadManager的封装
 */
public interface ILoadManager {
    void showSuccess();

    void showErrorState();

    void showErrorState(Class<? extends BaseStateControl> stateView);


    void showLoadingState();

    void showLoadingState(Class<? extends BaseStateControl> stateView);


    void showStateView(Class<? extends BaseStateControl> stateView);

    void showStateView(Class<? extends BaseStateControl> stateView, Object tag);
}
