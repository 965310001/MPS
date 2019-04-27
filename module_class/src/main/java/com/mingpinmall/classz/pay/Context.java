package com.mingpinmall.classz.pay;

public class Context {

    private Strategy strategy;

    public Context(Strategy strategy) {
        super();
        this.strategy = strategy;
    }

    public void pay() {
        strategy.pay();
    }
}
