package com.goldze.common.dmvvm.base.bean;

/**
 * @author GuoFeng
 * @date : 2019/1/21 16:50
 * @description: 水平选项卡Title
 */
public class HorizontalTabTitle<T> extends BaseBean {
    private String title;
    private T data;

    public HorizontalTabTitle(String title) {
        this.title = title;
    }

    public HorizontalTabTitle(String title, T data) {
        this.title = title;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
