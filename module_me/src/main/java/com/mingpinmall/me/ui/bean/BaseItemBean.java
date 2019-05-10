package com.mingpinmall.me.ui.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.mingpinmall.me.ui.widget.SettingItemView;

/**
 * 功能描述：
 * @author 小斌
 * @date 2019/3/25
 **/
public class BaseItemBean implements MultiItemEntity {

    private SettingItemView.ThemeMode itemMode;
    private int itemType;
    private String title;
    private String subTitle;
    private String description;
    private int point;
    private int drawable;
    private int littleDrawable;
    private String iconUrl;

    public int getLittleDrawable() {
        return littleDrawable;
    }

    public void setLittleDrawable(int littleDrawable) {
        this.littleDrawable = littleDrawable;
    }

    public SettingItemView.ThemeMode getItemMode() {
        return itemMode;
    }

    public void setItemMode(SettingItemView.ThemeMode itemMode) {
        this.itemMode = itemMode;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title;
    }

    public String getSubTitle() {
        return subTitle == null ? "" : subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle == null ? "" : subTitle;
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    public void setDescription(String description) {
        this.description = description == null ? "" : description;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getIconUrl() {
        return iconUrl == null ? "" : iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl == null ? "" : iconUrl;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
