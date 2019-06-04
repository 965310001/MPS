package com.mingpinmall.me.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：订单物流信息
 * *@author 小斌
 * @date 2019/4/27
 **/
public class OrderDeliverBean implements Serializable {

    private String express_name;
    private String shipping_code;
    private List<String> deliver_info;

    public String getExpress_name() {
        return express_name == null ? "" : express_name;
    }

    public void setExpress_name(String express_name) {
        this.express_name = express_name == null ? "" : express_name;
    }

    public String getShipping_code() {
        return shipping_code == null ? "" : shipping_code;
    }

    public void setShipping_code(String shipping_code) {
        this.shipping_code = shipping_code == null ? "" : shipping_code;
    }

    public List<String> getDeliver_info() {
        return deliver_info;
    }

    public void setDeliver_info(List<String> deliver_info) {
        this.deliver_info = deliver_info;
    }
}
