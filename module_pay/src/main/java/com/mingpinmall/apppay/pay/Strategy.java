package com.mingpinmall.apppay.pay;

import java.util.Map;

public interface Strategy {

    void pay(Map<String, String> map, JPayListener listener);
}
