package com.mingpinmall.me.ui.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;

/**
 * 功能描述：分页功能
 * @author 小斌
 * @date 2019/4/2
 **/
public class BasePageBean extends BaseBean {

    private boolean hasmore;
    private int page_total;

    public boolean isHasmore() {
        return hasmore;
    }

    public void setHasmore(boolean hasmore) {
        this.hasmore = hasmore;
    }

    public int getPage_total() {
        return page_total;
    }

    public void setPage_total(int page_total) {
        this.page_total = page_total;
    }
}
