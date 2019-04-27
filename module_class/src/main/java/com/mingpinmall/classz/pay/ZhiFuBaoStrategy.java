package com.mingpinmall.classz.pay;

import com.socks.library.KLog;

/**
 * 支付宝
 */
public class ZhiFuBaoStrategy implements Strategy {

    @Override
    public void pay() {
        KLog.i("支付宝支付");
    }
}
