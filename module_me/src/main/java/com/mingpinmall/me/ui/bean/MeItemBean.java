package com.mingpinmall.me.ui.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.goldze.common.dmvvm.base.bean.BaseBean;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/3/25
 **/
public class MeItemBean extends BaseBean implements MultiItemEntity {

    /**
     * 功能码，用于判断这个按钮的功能
     */
    private int funCode = 0;
    private int itemType = 0;
    private String title;
    private String title2;
    private int point = 0;
    private int drawable;
    private String iconUrl;
    private boolean runAnime = true;

    public boolean isRunAnime() {
        return runAnime;
    }

    public void setRunAnime(boolean runAnime) {
        this.runAnime = runAnime;
    }

    private String[] subLabel;
    private int[] subImage;
    private int[] colors;
    private int[] subCorner;

    public int[] getSubCorner() {
        return subCorner;
    }

    public void setSubCorner(int[] subCorner) {
        this.subCorner = subCorner;
    }

    public int[] getSubImage() {
        return subImage;
    }

    public void setSubImage(int[] subImage) {
        this.subImage = subImage;
    }

    public int[] getColors() {
        return colors;
    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }

    public String[] getSubLabel() {
        return subLabel;
    }

    public void setSubLabel(String[] subLabel) {
        this.subLabel = subLabel;
    }

    public int getFunCode() {
        return funCode;
    }

    public void setFunCode(int funCode) {
        this.funCode = funCode;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title;
    }

    public String getTitle2() {
        return title2 == null ? "" : title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2 == null ? "" : title2;
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

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
