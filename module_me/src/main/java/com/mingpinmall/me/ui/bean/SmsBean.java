package com.mingpinmall.me.ui.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/2
 **/
public class SmsBean extends BaseBean {
    private DatasBean datas;

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        private String sms_time;

        public String getSms_time() {
            return sms_time == null ? "" : sms_time;
        }

        public void setSms_time(String sms_time) {
            this.sms_time = sms_time == null ? "" : sms_time;
        }
    }
}
