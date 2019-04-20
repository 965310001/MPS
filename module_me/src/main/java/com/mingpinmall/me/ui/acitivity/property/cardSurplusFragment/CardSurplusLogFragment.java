package com.mingpinmall.me.ui.acitivity.property.cardSurplusFragment;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentCardLogBinding;
import com.mingpinmall.me.ui.adapter.RCardLogAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.RCardBalanceBean;
import com.mingpinmall.me.ui.bean.RCardLogBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * 功能描述：充值卡余额
 * 创建人：小斌
 * 创建时间: 2019/4/13
 **/
public class CardSurplusLogFragment extends AbsLifecycleFragment<FragmentCardLogBinding, MeViewModel> {

    private int pageIndex = 1;
    private boolean isLoadmore = false;
    private RCardLogAdapter rCardLogAdapter;

    public CardSurplusLogFragment() {}

    public static CardSurplusLogFragment newFragment() {
        return new CardSurplusLogFragment();
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        rCardLogAdapter = new RCardLogAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerView.setAdapter(rCardLogAdapter);

        binding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isLoadmore = true;
                mViewModel.getRCBLog(pageIndex + 1);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isLoadmore = false;
                lazyLoad();
            }
        });
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver("REFRESH_RCARD", String.class).observeForever(new Observer<String>() {
            @Override
            public void onChanged(@Nullable String result) {
                lazyLoad();
            }
        });
        registerObserver("RCBALANCE", "success", RCardBalanceBean.class).observeForever(new Observer<RCardBalanceBean>() {
            @Override
            public void onChanged(@Nullable RCardBalanceBean result) {
                binding.tvSurplus.setText(result.getAvailable_rc_balance());
            }
        });
        registerObserver("RCBALANCE", "err", String.class).observeForever(new Observer<String>() {
            @Override
            public void onChanged(@Nullable String msg) {
                ToastUtils.showShort(msg);
            }
        });
        registerObserver("RCCHARGE_LIST", "success").observeForever(new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object result) {
                BaseResponse<RCardLogBean> data = (BaseResponse<RCardLogBean>) result;
                if (!data.isHasmore()) {
                    binding.refreshLayout.finishLoadMoreWithNoMoreData();
                }
                if (isLoadmore) {
                    binding.refreshLayout.finishLoadMore();
                    pageIndex++;
                    rCardLogAdapter.addData(data.getData().getLog_list());
                } else {
                    binding.refreshLayout.finishRefresh();
                    pageIndex = 1;
                    rCardLogAdapter.setNewData(data.getData().getLog_list());
                }
            }
        });
        registerObserver("RCCHARGE_LIST", "err", String.class).observeForever(new Observer<String>() {
            @Override
            public void onChanged(@Nullable String msg) {
                ToastUtils.showShort(msg);
                if (isLoadmore) {
                    binding.refreshLayout.finishLoadMore(false);
                } else {
                    binding.refreshLayout.finishRefresh(false);
                }
            }
        });
    }

    @Override
    protected void lazyLoad() {
        mViewModel.getRCBalance();
        mViewModel.getRCBLog(1);
    }

    @Override
    protected Object getStateEventKey() {
        return "CardSurplusLogFragment";
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_card_log;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }
}
