package com.mingpinmall.classz.pay;

import com.socks.library.KLog;

/**
 * 微信
 */
public class WeiXinBaoStrategy implements Strategy {

    @Override
    public void pay() {
        KLog.i("微信支付");
    }
}
