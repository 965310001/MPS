package com.mingpinmall.classz.ui.vm.bean;

import android.text.TextUtils;

import com.goldze.common.dmvvm.base.bean.BaseBean;
import com.google.gson.annotations.SerializedName;

public class PayMessageInfo extends BaseBean {

    /**
     * return_code : SUCCESS
     * return_msg : OK
     * appid : wxc18a7a67aae81510
     * mch_id : 1414269202
     * device_info : WEB
     * nonce_str : mJg8GFnMVzosg9r5
     * sign : F5AB76CB6BB06FB851D791DC99C5CB8C
     * result_code : FAIL
     * err_code : INVALID_REQUEST
     * err_code_des : 201 商户订单号重复
     * timeStamp : 1557468181
     * package : Sign=WXPay
     */

    private String return_code;
    private String return_msg;
    private String appid;
    private String mch_id;
    private String device_info;
    private String nonce_str;
    private String sign;
    private String result_code;
    private String err_code;
    private String err_code_des;
    private String timeStamp;
    @SerializedName("package")
    private String packageX;
    private String prepay_id;


    /*支付宝*/
    private String param;
    private String notify_url;
    /**
     * pay_code : {"appid":"wxc18a7a67aae81510","noncestr":"1WrtHa07B4bAANgW","package":"Sign=WXPay","prepayid":"wx10144805430370ac6885638a4137594067","partnerid":"1414269202","timestamp":"1557470884","sign":"D197A44D0133A9726C1C246B5D70DD32","nonce_str":"1WrtHa07B4bAANgW","prepay_id":"wx10144805430370ac6885638a4137594067","return_code":"SUCCESS","return_msg":"OK","trade_type":"APP"}
     */

    private PayCodeBean pay_code;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }


    @Override
    public String toString() {
        return "PayMessageInfo{" +
                "return_code='" + return_code + '\'' +
                ", return_msg='" + return_msg + '\'' +
                ", appid='" + appid + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", device_info='" + device_info + '\'' +
                ", nonce_str='" + nonce_str + '\'' +
                ", sign='" + sign + '\'' +
                ", result_code='" + result_code + '\'' +
                ", err_code='" + err_code + '\'' +
                ", err_code_des='" + err_code_des + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", packageX='" + packageX + '\'' +
                ", prepay_id='" + prepay_id + '\'' +
                ", param='" + param + '\'' +
                ", notify_url='" + notify_url + '\'' +
                ", pay_code=" + pay_code +
                '}';
    }

    public boolean isSuccess() {
        return TextUtils.isEmpty(result_code) || result_code.equals("SUCCESS");
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public PayCodeBean getPay_code() {
        return pay_code;
    }

    public void setPay_code(PayCodeBean pay_code) {
        this.pay_code = pay_code;
    }


    public static class PayCodeBean extends BaseBean {
        /**
         * appid : wxc18a7a67aae81510
         * noncestr : 1WrtHa07B4bAANgW
         * package : Sign=WXPay
         * prepayid : wx10144805430370ac6885638a4137594067
         * partnerid : 1414269202
         * timestamp : 1557470884
         * sign : D197A44D0133A9726C1C246B5D70DD32
         * nonce_str : 1WrtHa07B4bAANgW
         * prepay_id : wx10144805430370ac6885638a4137594067
         * return_code : SUCCESS
         * return_msg : OK
         * trade_type : APP
         */

        @SerializedName("appid")
        private String appidX;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String prepayid;
        private String partnerid;
        private String timestamp;
        @SerializedName("sign")
        private String signX;
        @SerializedName("nonce_str")
        private String nonce_strX;
        @SerializedName("prepay_id")
        private String prepay_idX;
        @SerializedName("return_code")
        private String return_codeX;
        @SerializedName("return_msg")
        private String return_msgX;
        private String trade_type;

        public String getAppidX() {
            return appidX;
        }

        public void setAppidX(String appidX) {
            this.appidX = appidX;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSignX() {
            return signX;
        }

        public void setSignX(String signX) {
            this.signX = signX;
        }

        public String getNonce_strX() {
            return nonce_strX;
        }

        public void setNonce_strX(String nonce_strX) {
            this.nonce_strX = nonce_strX;
        }

        public String getPrepay_idX() {
            return prepay_idX;
        }

        public void setPrepay_idX(String prepay_idX) {
            this.prepay_idX = prepay_idX;
        }

        public String getReturn_codeX() {
            return return_codeX;
        }

        public void setReturn_codeX(String return_codeX) {
            this.return_codeX = return_codeX;
        }

        public String getReturn_msgX() {
            return return_msgX;
        }

        public void setReturn_msgX(String return_msgX) {
            this.return_msgX = return_msgX;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }
    }
}
