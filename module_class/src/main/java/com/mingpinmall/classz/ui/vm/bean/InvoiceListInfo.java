package com.mingpinmall.classz.ui.vm.bean;

import android.databinding.Bindable;

import com.goldze.common.dmvvm.base.bean.BaseBean;
import com.google.gson.annotations.SerializedName;
import com.mingpinmall.classz.BR;

import java.util.List;

/**
 *
 */
public class InvoiceListInfo extends BaseBean {

    private List<String> invoice_content_list;
    /*获取发票列表*/
    private List<InvoiceListBean> invoice_list;
    /*确定发票内容*/
    private int inv_id;

    public List<String> getInvoice_content_list() {
        return invoice_content_list;
    }

    public void setInvoice_content_list(List<String> invoice_content_list) {
        this.invoice_content_list = invoice_content_list;
    }

    public int getInv_id() {
        return inv_id;
    }

    public void setInv_id(int inv_id) {
        this.inv_id = inv_id;
    }

    @Bindable
    public List<InvoiceListBean> getInvoice_list() {
        return invoice_list;
    }

    public void setInvoice_list(List<InvoiceListBean> invoice_list) {
        this.invoice_list = invoice_list;
        notifyPropertyChanged(BR.invoice_list);
    }

    public static class InvoiceListBean extends BaseBean {
        /**
         * inv_id : 45
         * inv_title :
         * inv_content : 明细
         */

        @SerializedName("inv_id")
        private String inv_idX;
        private String inv_title;
        private String inv_content;
        public boolean checked;

        @Bindable
        public String getInv_idX() {
            return inv_idX;
        }

        public void setInv_idX(String inv_idX) {
            this.inv_idX = inv_idX;
            notifyPropertyChanged(BR.inv_idX);
        }

        public String getInv_title() {
            return inv_title;
        }

        public void setInv_title(String inv_title) {
            this.inv_title = inv_title;
        }

        public String getInv_content() {
            return inv_content;
        }

        public void setInv_content(String inv_content) {
            this.inv_content = inv_content;
        }

        @Bindable
        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
            notifyPropertyChanged(BR.checked);
        }
    }
}
