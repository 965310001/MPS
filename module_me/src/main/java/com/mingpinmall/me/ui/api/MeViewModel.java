package com.mingpinmall.me.ui.api;

import android.app.Application;
import android.support.annotation.NonNull;

import com.goldze.common.dmvvm.base.mvvm.AbsViewModel;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/1
 **/
public class MeViewModel extends AbsViewModel<MeRepository> {

    public MeViewModel(@NonNull Application application) {
        super(application);
    }

    public void getUserInfo() {
        mRepository.getUserInfo();
    }

    /**
     * 获取支付密码状态，是否设置
     */
    public void getPayPwdInfo() {
        mRepository.getPayPwdInfo();
    }

    /**
     * 获取手机号是否验证状态
     */
    public void getPhoneInfo() {
        mRepository.getPhoneInfo();
    }

    /**
     * 发送用户反馈
     * @param feedback
     */
    public void sendFeedBack(String feedback) {
        mRepository.sendFeedBack(feedback);
    }

    /**
     * 获取店铺收藏列表
     * @param
     */
    public void getShopsCollectList(int curpage) {
        mRepository.getShopsCollectList(curpage);
    }

    /**
     * 获取商品收藏列表
     * @param
     */
    public void getProductCollectList(int curpage) {
        mRepository.getProductCollectList(curpage);
    }
}
