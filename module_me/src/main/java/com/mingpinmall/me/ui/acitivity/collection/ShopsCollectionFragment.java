package com.mingpinmall.me.ui.acitivity.collection;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentOrderBinding;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.BasePageBean;

/**
 * 功能描述：店铺收藏页面
 * 创建人：小斌
 * 创建时间: 2019/3/26
 **/
public class ShopsCollectionFragment extends AbsLifecycleFragment<FragmentOrderBinding, MeViewModel> {

    private int pageIndex = 1;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_order;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        mViewModel.getShopsCollectList(pageIndex);
    }

    @Override
    protected void dataObserver() {
        LiveBus.getDefault().subscribe("SHOPS_COLLECT_LIST", BasePageBean.class)
                .observeForever(new Observer<BasePageBean>() {
                    @Override
                    public void onChanged(@Nullable BasePageBean basePageBean) {

                    }
                });
    }

    @Override
    protected Object getStateEventKey() {
        return "SHOPS_COLLECTION";
    }
}
