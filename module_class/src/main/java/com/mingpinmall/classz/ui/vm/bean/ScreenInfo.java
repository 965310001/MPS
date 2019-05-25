package com.mingpinmall.classz.ui.vm.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 筛选用来传数据
 */
public class ScreenInfo implements Serializable {

    //*排序相关
    private String key = "", order = "";

    public void setOrderKey(String order, String key) {
        this.key = key;
        this.order = order;
    }

    //--------
    private String main_id = "";
    private String gc_id = "";
    private String b_id = "";
    String gc_name = "";

    public String keyword = "";
    private int type = 0;//0：二级搜索  1：一级搜索

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    String gc_Name_2 = "";

    public String getMainName() {
        return gc_name;
    }

    public String getSecondName() {
        return gc_Name_2;
    }

    public String getMainId() {
        return main_id;
    }

    public String getSecondId() {
        return gc_id;
    }

    public void setMainName(String name) {
        gc_name = name;
    }

    public void setSecondName(String name) {
        gc_Name_2 = name;
    }

    public void setMain_id(String id) {
        main_id = id;
    }

    public String getId() {
        switch (type) {
            case 0:
                return b_id;
            case 1:
                return gc_id;
            case 2:
                return gc_id;
            default:
                return gc_id;
        }
    }

    public void setId(String id) {
        gc_id = id;
        b_id = id;
    }

    /**
     * area_id: 商品所在地id
     * price_from:价格区间开始范围
     * price_to: 价格区间结束范围
     * own_mall: 店铺类型（1表示自营）
     * <p>
     * gift: 赠品（1表示选中）
     * groupbuy: 团购（1表示选中）
     * xianshi: 限时折扣（1表示选中）
     * virtual:虚拟（1表示选中）
     * <p>
     * ci: 店铺服务（如：1_2_）
     * attrs:分类属性（如：77_81_93_）
     * gc_id_1:一级分类id
     * gc_id_2:二级分类id
     * gc_id_3:三级分类id(可多选，如：12_13_14_)
     */
    public String areaId = "", priceFrom = "", priceTo = "",
            own_mall = "", gift = "", groupbuy = "",
            xianshi = "", virtual = "", ci = "", attrs = "";
    public String gc_id_1 = "", gc_id_2 = "", gc_id_3 = "";

    public void cloneScreeningDatas(ScreenInfo screenInfo) {
        areaId = screenInfo.areaId;
        priceFrom = screenInfo.priceFrom;
        priceTo = screenInfo.priceTo;
        own_mall = screenInfo.own_mall;
        gift = screenInfo.gift;
        groupbuy = screenInfo.groupbuy;
        xianshi = screenInfo.xianshi;
        virtual = screenInfo.virtual;
        ci = screenInfo.ci;
        attrs = screenInfo.attrs;
        gc_id_1 = screenInfo.gc_id_1;
        gc_id_2 = screenInfo.gc_id_2;
        gc_id_3 = screenInfo.gc_id_3;
    }

    public Map<String, Object> getParams() {
        Map<String, Object> map = new HashMap<>();
        if (type == 1) {
            if (!"".equals(gc_id)) map.put("gc_id", gc_id);
        } else if (type == 0) {
            if (!"".equals(b_id)) map.put("b_id", b_id);
        } else if (type == 2) {
            if (!"".equals(gc_id)) map.put("gc_id_2", gc_id);
        }
        if (!"".equals(keyword)) map.put("keyword", keyword);
        if (!"".equals(areaId)) map.put("area_id", areaId);
        if (!"".equals(priceFrom)) map.put("price_from", priceFrom);
        if (!"".equals(priceTo)) map.put("own_mall", priceTo);
        if (!"".equals(own_mall)) map.put("own_mall", own_mall);
        if (!"".equals(gift)) map.put("gift", gift);
        if (!"".equals(groupbuy)) map.put("groupbuy", groupbuy);
        if (!"".equals(xianshi)) map.put("xianshi", xianshi);
        if (!"".equals(virtual)) map.put("virtual", virtual);
        if (!"".equals(ci)) map.put("ci", ci);
        if (!"".equals(attrs)) map.put("attrs", attrs);
        if (!"".equals(gc_id_1)) map.put("gc_id_1", gc_id_1);
        if (!"".equals(gc_id_2)) map.put("gc_id_2", gc_id_2);
        if (!"".equals(gc_id_3)) map.put("gc_id_3", gc_id_3);
        if (!"".equals(order)) map.put("order", order);
        if (!"".equals(key)) map.put("key", key);
        return map;
    }

}
