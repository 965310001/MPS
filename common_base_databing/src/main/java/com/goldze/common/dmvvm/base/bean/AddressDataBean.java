package com.goldze.common.dmvvm.base.bean;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：
 *
 * @author 小斌
 * @date 2019/4/12
 **/
public class AddressDataBean implements Serializable {

    private List<AddressListBean> address_list;

    public List<AddressListBean> getAddress_list() {
        return address_list;
    }

    public void setAddress_list(List<AddressListBean> address_list) {
        this.address_list = address_list;
    }

    public static class AddressListBean extends BaseBean {

        private String address_id;
        private String member_id;
        private String true_name;
        private String area_id;
        private String city_id;
        private String area_info;
        private String address;
        private Object tel_phone;
        private String mob_phone;
        private String is_default;
        private String dlyp_id;

        public String getAddress_id() {
            return address_id;
        }

        public void setAddress_id(String address_id) {
            this.address_id = address_id;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getTrue_name() {
            if (TextUtils.isEmpty(true_name)) {
                return "";
            }
            return true_name;
        }

        public void setTrue_name(String true_name) {
            this.true_name = true_name;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getArea_info() {
            if (TextUtils.isEmpty(area_info)) {
                return "";
            }
            return area_info;
        }

        public void setArea_info(String area_info) {
            this.area_info = area_info;
        }

        public String getAddress() {
            if (TextUtils.isEmpty(address)) {
                return "";
            }
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Object getTel_phone() {
            return tel_phone;
        }

        public void setTel_phone(Object tel_phone) {
            this.tel_phone = tel_phone;
        }

        public String getMob_phone() {
            if (TextUtils.isEmpty(mob_phone)) {
                return "";
            }
            return mob_phone;
        }

        public void setMob_phone(String mob_phone) {
            this.mob_phone = mob_phone;
        }

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }

        public String getDlyp_id() {
            return dlyp_id;
        }

        public void setDlyp_id(String dlyp_id) {
            this.dlyp_id = dlyp_id;
        }

        /*收货人*/
        public String getUserName() {
            return "收货人:".concat(getTrue_name().concat(" ").concat(getMob_phone()));
        }

        public boolean isEmpty() {
            return TextUtils.isEmpty(getTrue_name()) && (TextUtils.isEmpty(getArea_info()) || TextUtils.isEmpty(getAddress()));
        }
    }
}
