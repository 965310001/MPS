package com.mingpinmall.classz.ui.vm.bean;

import android.databinding.Bindable;

import com.goldze.common.dmvvm.base.bean.BaseBean;
import com.mingpinmall.classz.BR;

/**
 * 聊天信息
 */
public class ChatMessageInfo extends BaseBean {

    public String name;
    public String msg;
    public boolean isMe;/*是否是自己*/
    public String addTime;
    public String icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
        notifyPropertyChanged(BR.msg);
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    @Bindable
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
        notifyPropertyChanged(BR.icon);
    }
}
