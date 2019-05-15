package com.mingpinmall.apppay.pay;

import java.util.Map;

public class Context {

    private Strategy strategy;

    public Context(Strategy strategy) {
        super();
        this.strategy = strategy;
    }

    public void pay(Map<String, String> map, JPayListener listener) {
        strategy.pay(map, listener);
    }
}
