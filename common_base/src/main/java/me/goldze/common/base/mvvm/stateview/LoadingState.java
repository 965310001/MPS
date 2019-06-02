package me.goldze.common.base.mvvm.stateview;

import com.tqzhang.stateview.stateview.BaseStateControl;

import me.goldze.common.R;

/**
 * @author GuoFeng
 * @date :2019/1/17 15:09
 * @description: 自定义加载的样式
 */
public class LoadingState extends BaseStateControl {

    @Override
    protected int onCreateView() {
        return R.layout.stateview_loading_view;
    }
}
