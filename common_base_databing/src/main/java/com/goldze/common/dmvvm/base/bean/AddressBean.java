package com.goldze.common.dmvvm.base.bean;

/**
 * 地址
 */
public class AddressBean extends BaseBean {
    /**
     * address_id : 5
     * member_id : 16
     * true_name : 123333
     * area_id : 37
     * city_id : 36
     * area_info : 北京	北京市	东城区
     * address : 114645
     * tel_phone :
     * mob_phone : 13869903575
     * is_default : 0
     * dlyp_id : 0
     */

    private String address_id;
    private String member_id;
    private String true_name;
    private String area_id;
    private String city_id;
    private String area_info;
    private String address;
    private String tel_phone;
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
        return area_info;
    }

    public void setArea_info(String area_info) {
        this.area_info = area_info;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel_phone() {
        return tel_phone;
    }

    public void setTel_phone(String tel_phone) {
        this.tel_phone = tel_phone;
    }

    public String getMob_phone() {
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
}