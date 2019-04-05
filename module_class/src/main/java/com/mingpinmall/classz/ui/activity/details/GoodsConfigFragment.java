package com.mingpinmall.classz.ui.activity.details;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.FragmentGoodsConfigBinding;

/**
 * 商品详情 - 规格参数Fragment
 */
public class GoodsConfigFragment extends BaseFragment<FragmentGoodsConfigBinding> {

    RecyclerView listView;

    public static GoodsConfigFragment newInstance() {
        return new GoodsConfigFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_goods_config;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {

        listView=binding.hlvConfig;
//        showSuccess();
        listView.setFocusable(false);

//        List<GoodsConfigBean> data = new ArrayList<>();
//        data.add(new GoodsConfigBean("品牌", "小米Mix 3"));
//        data.add(new GoodsConfigBean("型号", "全面屏 小米Mix 3"));
//
//        listView.setLayoutManager(new LinearLayoutManager(getContext()));
//        listView.setAdapter(new GoodsConfigAdapter(getContext(), data, R.layout.item_of_goods_config_list));
    }


}
