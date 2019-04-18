package com.mingpinmall.me.ui.acitivity.order.RefundOrder;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentDefaultRecyclerviewBinding;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.RefundBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * 功能描述：退款单列表
 * 创建人：小斌
 * 创建时间: 2019/3/26
 **/
public class RefunMoneyFragment extends AbsLifecycleFragment<FragmentDefaultRecyclerviewBinding, MeViewModel> {

    private int pageIndex = 1;
    private boolean isLoadmore = false;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_default_recyclerview;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        binding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isLoadmore = true;
                mViewModel.getRefundList(pageIndex + 1);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isLoadmore = false;
                lazyLoad();
            }
        });
    }

    @Override
    protected void lazyLoad() {
        mViewModel.getRefundList(1);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver("REFUN_MONEY", "success")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        BaseResponse<RefundBean> refundBean = (BaseResponse<RefundBean>) result;
                        if (!refundBean.isHasmore()) {
                            binding.refreshLayout.finishLoadMoreWithNoMoreData();
                        }
                        if (isLoadmore) {
                            pageIndex++;
                            binding.refreshLayout.finishLoadMore();
                        } else {
                            pageIndex = 1;
                            binding.refreshLayout.finishRefresh();
                        }
                    }
                });
        registerObserver("REFUN_MONEY", "err", String.class)
                .observeForever(new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String msg) {

                    }
                });
    }

    @Override
    protected Object getStateEventKey() {
        return "RefunMoneyFragment";
    }
}
