package com.mingpinmall.me.ui.acitivity.property.accountSurplusFragment;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentDefaultRecyclerviewBinding;
import com.mingpinmall.me.ui.adapter.PdrechargeAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.PdrechargeBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * 功能描述：充值明细
 * 创建人：小斌
 * 创建时间: 2019/3/28
 **/
public class PdrechargeFragment extends AbsLifecycleFragment<FragmentDefaultRecyclerviewBinding, MeViewModel> {

    private int pageIndex = 1;
    private boolean isLoadmore = false;
    private PdrechargeAdapter pdrechargeAdapter;

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
        pdrechargeAdapter = new PdrechargeAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerView.setAdapter(pdrechargeAdapter);

        binding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isLoadmore = true;
                mViewModel.getPdreChargeList(pageIndex + 1);
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
        mViewModel.getPdreChargeList(1);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver("PDRECHARGE", "success").observeForever(new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object result) {
                BaseResponse<PdrechargeBean> data = (BaseResponse<PdrechargeBean>) result;
                if (isLoadmore) {
                    pageIndex++;
                    binding.refreshLayout.finishLoadMore();
                    pdrechargeAdapter.addData(data.getData().getList());
                } else {
                    //如果数据有变，则更新预存款余额
                    if (pdrechargeAdapter.getItemCount() > 0) {
                        if (data.getData().getList().size() > 0) {
                            if (!pdrechargeAdapter.getItem(0).getPdr_id()
                                    .equals(data.getData().getList().get(0).getPdr_id())) {
                                LiveBus.getDefault().postEvent("REFRESH_PDC", "true");
                            }
                        } else {
                            LiveBus.getDefault().postEvent("REFRESH_PDC", "true");
                        }
                    }
                    pageIndex = 1;
                    binding.refreshLayout.finishRefresh();
                    pdrechargeAdapter.setNewData(data.getData().getList());
                }
                if (!data.isHasmore()) {
                    binding.refreshLayout.setNoMoreData(true);
                }
            }
        });
        registerObserver("PDRECHARGE", "err", String.class).observeForever(new Observer<String>() {
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
    protected Object getStateEventKey() {
        return "PdrechargeFragment";
    }
}