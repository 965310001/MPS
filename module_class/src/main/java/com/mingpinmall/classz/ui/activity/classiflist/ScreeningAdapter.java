package com.mingpinmall.classz.ui.activity.classiflist;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.widget.stackLabel.StackLabel;
import com.goldze.common.dmvvm.widget.stackLabel.StackLabelAdapter;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ui.vm.bean.ScreeningClassBean;

import java.util.ArrayList;

/**
 * @author 小斌
 * @data 2019/5/25
 **/
public class ScreeningAdapter extends BaseQuickAdapter<ScreeningClassBean.GoodsAttrListBean, BaseViewHolder> {
    public ScreeningAdapter() {
        super(R.layout.item_screening, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, ScreeningClassBean.GoodsAttrListBean item) {
        helper.setText(R.id.tv_title, item.getName());
        StackLabel<ScreeningClassBean.GoodsAttrListBean.ValueBean> stackLabel = helper.getView(R.id.stl_view);
        stackLabel.setAdapter((data, position) -> data.getAttr_value_name());
        stackLabel.setData(item.getValue());
    }
}
