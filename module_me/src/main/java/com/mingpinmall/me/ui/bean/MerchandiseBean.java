package com.mingpinmall.me.ui.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;

import java.util.List;

public class MerchandiseBean extends BaseBean {

    /**
     * return_id : 68
     * return_delay : 5
     * return_confirm : 7
     * express_list : [{"express_id":"1","express_name":"安信达"},{"express_id":"2","express_name":"包裹平邮"},{"express_id":"3","express_name":"CCES"},{"express_id":"4","express_name":"传喜物流"},{"express_id":"7","express_name":"德邦物流"},{"express_id":"6","express_name":"大田物流"},{"express_id":"5","express_name":"DHL快递"},{"express_id":"8","express_name":"EMS"},{"express_id":"9","express_name":"EMS国际"},{"express_id":"12","express_name":"凡客如风达"},{"express_id":"11","express_name":"FedEx(国际)"},{"express_id":"10","express_name":"飞康达"},{"express_id":"13","express_name":"港中能达"},{"express_id":"14","express_name":"挂号信"},{"express_id":"15","express_name":"共速达"},{"express_id":"17","express_name":"华宇物流"},{"express_id":"16","express_name":"汇通快递"},{"express_id":"18","express_name":"佳吉快运"},{"express_id":"19","express_name":"佳怡物流"},{"express_id":"20","express_name":"急先达"},{"express_id":"21","express_name":"快捷速递"},{"express_id":"24","express_name":"联昊通"},{"express_id":"23","express_name":"联邦快递"},{"express_id":"22","express_name":"龙邦快递"},{"express_id":"25","express_name":"全一快递"},{"express_id":"26","express_name":"全峰快递"},{"express_id":"27","express_name":"全日通"},{"express_id":"28","express_name":"申通快递"},{"express_id":"29","express_name":"顺丰快递"},{"express_id":"30","express_name":"速尔快递"},{"express_id":"33","express_name":"天地华宇"},{"express_id":"32","express_name":"天天快递"},{"express_id":"31","express_name":"TNT快递"},{"express_id":"34","express_name":"UPS快递"},{"express_id":"35","express_name":"USPS"},{"express_id":"39","express_name":"新蛋物流"},{"express_id":"38","express_name":"希伊艾斯"},{"express_id":"37","express_name":"信丰物流"},{"express_id":"36","express_name":"新邦物流"},{"express_id":"43","express_name":"优速快递"},{"express_id":"42","express_name":"邮政包裹"},{"express_id":"41","express_name":"韵达快递"},{"express_id":"40","express_name":"圆通快递"},{"express_id":"44","express_name":"中通快递"},{"express_id":"45","express_name":"中铁快运"},{"express_id":"46","express_name":"宅急送"},{"express_id":"47","express_name":"中邮物流"}]
     */
    private int return_id;
    private int return_delay;
    private int return_confirm;
    private List<ExpressListBean> express_list;

    public int getReturn_id() {
        return return_id;
    }

    public void setReturn_id(int return_id) {
        this.return_id = return_id;
    }

    public int getReturn_delay() {
        return return_delay;
    }

    public void setReturn_delay(int return_delay) {
        this.return_delay = return_delay;
    }

    public int getReturn_confirm() {
        return return_confirm;
    }

    public void setReturn_confirm(int return_confirm) {
        this.return_confirm = return_confirm;
    }

    public List<ExpressListBean> getExpress_list() {
        return express_list;
    }

    public void setExpress_list(List<ExpressListBean> express_list) {
        this.express_list = express_list;
    }

    public static class ExpressListBean {
        /**
         * express_id : 1
         * express_name : 安信达
         */

        private String express_id;
        private String express_name;

        public String getExpress_id() {
            return express_id;
        }

        public void setExpress_id(String express_id) {
            this.express_id = express_id;
        }

        public String getExpress_name() {
            return express_name;
        }

        public void setExpress_name(String express_name) {
            this.express_name = express_name;
        }
    }
}
