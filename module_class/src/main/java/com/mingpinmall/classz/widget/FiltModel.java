package com.mingpinmall.classz.widget;

import java.util.List;

/**
 * @author GuoFeng
 * @date :2019/4/4 15:01
 * @description:
 */
public class FiltModel {
    private int type;//筛选类型
    private String typeName = "";//筛选类型的名字
    private TableMode tab;//当前选择
    private List<TableMode> tabs;//选项列表

    public static class TableMode {
        public int id;
        public String name = "";
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public TableMode getTab() {
        return tab;
    }

    public void setTab(TableMode tab) {
        this.tab = tab;
    }

    public List<TableMode> getTabs() {
        return tabs;
    }

    public void setTabs(List<TableMode> tabs) {
        this.tabs = tabs;
    }
}
