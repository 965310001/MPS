package com.mingpinmall.me.ui.bean;

import java.io.Serializable;

/**
 * 功能描述：充值卡余额
 * @author 小斌
 * @date 2019/4/20
 **/
public class RCardBalanceBean implements Serializable {

    /**
     * available_rc_balance : 3000.00
     */

    private String available_rc_balance;

    public String getAvailable_rc_balance() {
        return available_rc_balance;
    }

    public void setAvailable_rc_balance(String available_rc_balance) {
        this.available_rc_balance = available_rc_balance;
    }
}
