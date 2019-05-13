package com.mingpinmall.me.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：
 * *@author 小斌
 * @date 2019/4/18
 **/
public class VirtualStoreAddrsBean implements Serializable {

    private List<AddrListBean> addr_list;

    public List<AddrListBean> getAddr_list() {
        return addr_list;
    }

    public void setAddr_list(List<AddrListBean> addr_list) {
        this.addr_list = addr_list;
    }

    public static class AddrListBean {

        private int key;
        private String map_id;
        private String name_info;
        private String address_info;
        private String phone_info;
        private String bus_info;
        private String city;
        private String district;
        private String lng;
        private String lat;
        private double gcjlng;
        private double gcjlat;
        private String distance;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getMap_id() {
            return map_id;
        }

        public void setMap_id(String map_id) {
            this.map_id = map_id;
        }

        public String getName_info() {
            return name_info;
        }

        public void setName_info(String name_info) {
            this.name_info = name_info;
        }

        public String getAddress_info() {
            return address_info;
        }

        public void setAddress_info(String address_info) {
            this.address_info = address_info;
        }

        public String getPhone_info() {
            return phone_info;
        }

        public void setPhone_info(String phone_info) {
            this.phone_info = phone_info;
        }

        public String getBus_info() {
            return bus_info;
        }

        public void setBus_info(String bus_info) {
            this.bus_info = bus_info;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public double getGcjlng() {
            return gcjlng;
        }

        public void setGcjlng(double gcjlng) {
            this.gcjlng = gcjlng;
        }

        public double getGcjlat() {
            return gcjlat;
        }

        public void setGcjlat(double gcjlat) {
            this.gcjlat = gcjlat;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }
    }
}
