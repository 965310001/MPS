package com.mingpinmall.classz.ui.vm.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;
import com.mingpinmall.apppay.pay.PayLayoutBean;

/**
 * 提交订单
 */
public class BuyStepInfo extends BaseBean {

    /**
     * pay_sn : 160609697604704016
     * payment_code : online
     */

    private String pay_sn;
    private String payment_code;
    private PayLayoutBean.PayInfoBean pay_info;

    public String getPay_sn() {
        return pay_sn;
    }

    public void setPay_sn(String pay_sn) {
        this.pay_sn = pay_sn;
    }

    public String getPayment_code() {
        return payment_code;
    }

    public void setPayment_code(String payment_code) {
        this.payment_code = payment_code;
    }

    public PayLayoutBean.PayInfoBean getPay_info() {
        return pay_info;
    }

    public void setPay_info(PayLayoutBean.PayInfoBean pay_info) {
        this.pay_info = pay_info;
    }

}
