package com.mingpinmall.me.ui.acitivity.property.accountSurplusFragment;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentDefaultRecyclerviewBinding;
import com.mingpinmall.me.ui.adapter.PdcashAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.PdcashBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * 功能描述：余额提现
 * 创建人：小斌
 * 创建时间: 2019/3/28
 **/
public class PdcashFragment extends AbsLifecycleFragment<FragmentDefaultRecyclerviewBinding, MeViewModel> {

    private int pageIndex = 1;
    private boolean isLoadmore = false;
    private PdcashAdapter pdcashAdapter;

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
        pdcashAdapter = new PdcashAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerView.setAdapter(pdcashAdapter);

        binding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isLoadmore = true;
                mViewModel.getPdcashList(pageIndex + 1);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isLoadmore = false;
                lazyLoad();
            }
        });

        pdcashAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //列表点击事件
                PdcashBean.ListBean bean = pdcashAdapter.getItem(position);
                ActivityToActivity.toActivity(ARouterConfig.Me.PDCASHINFORMATIONACTIVITY, "pdcId", bean.getPdc_id());
            }
        });
    }

    @Override
    protected void lazyLoad() {
        mViewModel.getPdcashList(1);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver("PDCASH", "success").observeForever(new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object result) {
                BaseResponse<PdcashBean> data = (BaseResponse<PdcashBean>) result;
                if (isLoadmore) {
                    pageIndex++;
                    binding.refreshLayout.finishLoadMore();
                    pdcashAdapter.addData(data.getData().getList());
                } else {
                    //如果数据有变，则更新预存款余额
                    if (pdcashAdapter.getItemCount() > 0) {
                        if (data.getData().getList().size() > 0) {
                            if (!pdcashAdapter.getItem(0).getPdc_id()
                                    .equals(data.getData().getList().get(0).getPdc_id())) {
                                LiveBus.getDefault().postEvent("REFRESH_PDC", "true");
                            }
                        } else {
                            LiveBus.getDefault().postEvent("REFRESH_PDC", "true");
                        }
                    }
                    pageIndex = 1;
                    binding.refreshLayout.finishRefresh();
                    pdcashAdapter.setNewData(data.getData().getList());
                }
                if (!data.isHasmore()) {
                    binding.refreshLayout.setNoMoreData(true);
                }
            }
        });
        registerObserver("PDCASH", "err", String.class).observeForever(new Observer<String>() {
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
        return "PdcashFragment";
    }
}