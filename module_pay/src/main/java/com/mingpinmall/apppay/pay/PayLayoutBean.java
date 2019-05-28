package com.mingpinmall.apppay.pay;

import android.databinding.Bindable;

import com.goldze.common.dmvvm.base.bean.BaseBean;

import java.util.List;

/**
 * @author 小斌
 * @data 2019/5/14
 **/
public class PayLayoutBean extends BaseBean {

    private PayInfoBean pay_info;

    public PayInfoBean getPay_info() {
        return pay_info;
    }

    public void setPay_info(PayInfoBean pay_info) {
        this.pay_info = pay_info;
    }

    public static class PayInfoBean extends BaseBean{

        private String pay_amount;
        private String member_available_pd;
        private String member_available_rcb;
        private boolean member_paypwd;
        private String pay_sn;
        private String payed_amount;
        private List<PaymentListBean> payment_list;

        public String getPay_amount() {
            return pay_amount == null ? "" : pay_amount;
        }

        public void setPay_amount(String pay_amount) {
            this.pay_amount = pay_amount == null ? "" : pay_amount;
        }

        public String getMember_available_pd() {
            return member_available_pd == null ? "" : member_available_pd;
        }

        public void setMember_available_pd(String member_available_pd) {
            this.member_available_pd = member_available_pd == null ? "" : member_available_pd;
        }

        public String getMember_available_rcb() {
            return member_available_rcb == null ? "" : member_available_rcb;
        }

        public void setMember_available_rcb(String member_available_rcb) {
            this.member_available_rcb = member_available_rcb == null ? "" : member_available_rcb;
        }

        public boolean isMember_paypwd() {
            return member_paypwd;
        }

        public void setMember_paypwd(boolean member_paypwd) {
            this.member_paypwd = member_paypwd;
        }

        public String getPay_sn() {
            return pay_sn == null ? "" : pay_sn;
        }

        public void setPay_sn(String pay_sn) {
            this.pay_sn = pay_sn == null ? "" : pay_sn;
        }

        public String getPayed_amount() {
            return payed_amount == null ? "" : payed_amount;
        }

        public void setPayed_amount(String payed_amount) {
            this.payed_amount = payed_amount == null ? "" : payed_amount;
        }

        public List<PaymentListBean> getPayment_list() {
            return payment_list;
        }

        public void setPayment_list(List<PaymentListBean> payment_list) {
            this.payment_list = payment_list;
        }

        @Bindable
        public String getPayAmout() {
            return String.format("本次交易需在线支付<font size='3' color='#ED5564'><b>%s</b></font>元", pay_amount);
        }

        /*是否显示微信*/
        @Bindable
        public boolean isWeiXin() {
            for (PaymentListBean paymentListBean : getPayment_list()) {
                if (paymentListBean.getPayment_name().contains("微信")) {
                    return true;
                }
            }
            return false;
        }

        /*是否显示支付宝*/
        @Bindable
        public boolean isAliPay() {
            for (PaymentListBean paymentListBean : getPayment_list()) {
                if (paymentListBean.getPayment_name().contains("支付宝")) {
                    return true;
                }
            }
            return false;
        }

        public static class PaymentListBean {

            private String payment_code;
            private String payment_name;

            public String getPayment_code() {
                return payment_code == null ? "" : payment_code;
            }

            public void setPayment_code(String payment_code) {
                this.payment_code = payment_code == null ? "" : payment_code;
            }

            public String getPayment_name() {
                return payment_name == null ? "" : payment_name;
            }

            public void setPayment_name(String payment_name) {
                this.payment_name = payment_name == null ? "" : payment_name;
            }
        }
    }
}
