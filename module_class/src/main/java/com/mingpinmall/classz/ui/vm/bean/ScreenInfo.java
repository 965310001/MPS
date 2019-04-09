package com.mingpinmall.classz.ui.vm.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 筛选用来传数据
 */
public class ScreenInfo extends BaseBean {

    public String areaId, priceFrom, priceTo;

    public List<String> goodsType = new ArrayList<>();

    public boolean shoppingType;/*true 店铺类型*/

    public List<String> shoppingServer = new ArrayList<>();

}
