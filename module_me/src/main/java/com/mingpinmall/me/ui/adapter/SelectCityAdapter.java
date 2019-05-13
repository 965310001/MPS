package com.mingpinmall.me.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.CityBean;

import java.util.ArrayList;

/**
 * 功能描述：
 * @author 小斌
 * @date 2019/4/12
 **/
public class SelectCityAdapter extends BaseQuickAdapter<CityBean.AreaListBean, BaseViewHolder> {

    public SelectCityAdapter() {
        super(R.layout.item_layout_textview, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, CityBean.AreaListBean item) {
        helper.setText(R.id.tv_label, item.getArea_name());
    }
}
