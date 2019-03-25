package com.mingpinmall.classz.common;


import com.goldze.common.dmvvm.base.BaseApplication;
import com.xuexiang.xui.XUI;

/**
 * @Author: guofeng
 * @CreateDate: 2019/3/22 16:25
 * @Description:
 */
public class ClassApplication extends BaseApplication {

    @Override
    public void run() {
        XUI.init(this);
    }
}
