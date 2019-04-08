package com.mingpinmall.me.ui.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;
import com.goldze.common.dmvvm.base.bean.BaseResponse;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/2
 **/
public class SmsBean {

    private String sms_time;

    public String getSms_time() {
        return sms_time == null ? "" : sms_time;
    }

    public void setSms_time(String sms_time) {
        this.sms_time = sms_time == null ? "" : sms_time;
    }
}
