package com.mingpinmall.classz.ui.vm.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;

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
}
