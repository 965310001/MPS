package com.goldze.common.dmvvm.base.mvvm.stateview;

import com.tqzhang.stateview.stateview.BaseStateControl;

/**
 * @author GuoFeng
 * @date : 2019/1/23 10:39
 * @description: 没有数据的样式
 */
public class EmptyState extends BaseStateControl {

    @Override
    protected int onCreateView() {
        return 0;
    }
}
