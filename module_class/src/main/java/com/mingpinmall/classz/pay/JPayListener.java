package com.mingpinmall.classz.pay;

/**
 * 支付回调
 */
public interface JPayListener {
    //支付成功
    void onPaySuccess();

    //支付失败
    void onPayError(int error_code, String message);

    //支付取消
    void onPayCancel();

    //银联支付结果回调
    void onUUPay(String dataOrg, String sign, String mode);
}