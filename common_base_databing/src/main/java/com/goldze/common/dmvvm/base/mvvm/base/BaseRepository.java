package com.goldze.common.dmvvm.base.mvvm.base;

import com.goldze.common.dmvvm.base.bean.UserBean;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsRepository;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;

/**
 *@author GuoFeng
 * @date :2019/1/17 16:42
 * @description:
 */
public class BaseRepository extends AbsRepository {

    protected String getUserKey() {
        UserBean userBean = (UserBean) SharePreferenceUtil.getUser(UserBean.class);
        return userBean == null ? "" : userBean.getKey();
    }

    protected void sendData(Object eventKey, Object t) {
        sendData(eventKey, null, t);
    }

    protected void showPageState(Object eventKey, String state) {
        sendData(eventKey, state);
    }

    protected void showPageState(Object eventKey, String tag, String state) {
        sendData(eventKey, tag, state);
    }

    protected void sendData(Object eventKey, String tag, Object t) {
        LiveBus.getDefault().postEvent(eventKey, tag, t);
    }
}
