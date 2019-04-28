package com.mingpinmall.me.ui.acitivity.property.accountSurplusFragment;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentDefaultRecyclerviewBinding;
import com.mingpinmall.me.ui.adapter.PredepositLogAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.PredepoitLogBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * 功能描述：账户余额
 * 创建人：小斌
 * 创建时间: 2019/3/28
 **/
public class PredepositLogFragment extends AbsLifecycleFragment<FragmentDefaultRecyclerviewBinding, MeViewModel> {

    private int pageIndex = 1;
    private boolean isLoadmore = false;
    private PredepositLogAdapter listAdapter;

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
        listAdapter = new PredepositLogAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        View emptyView = View.inflate(activity, R.layout.layout_state_view, null);
        ((AppCompatImageView) emptyView.findViewById(R.id.iv_image)).setImageResource(R.drawable.ic_mcc_06_w);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_title)).setText(R.string.text_title_pdcash_empty);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_sub_title)).setText(R.string.text_sub_title_log_empty);
        emptyView.findViewById(R.id.btn_action).setVisibility(View.GONE);
        listAdapter.setEmptyView(emptyView);
        binding.recyclerView.setAdapter(listAdapter);

        binding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isLoadmore = true;
                mViewModel.getPredepoitLog(pageIndex + 1);
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
        mViewModel.getPredepoitLog(1);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver("PREDPOSITLOG", Object.class).observeForever(new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object result) {
                if (result instanceof String) {
                    ToastUtils.showShort(result.toString());
                    if (isLoadmore)
                        binding.refreshLayout.finishLoadMore(false);
                    else
                        binding.refreshLayout.finishRefresh(false);

                } else {
                    BaseResponse<PredepoitLogBean> data = (BaseResponse<PredepoitLogBean>) result;
                    if (isLoadmore) {
                        pageIndex++;
                        binding.refreshLayout.finishLoadMore();
                        listAdapter.addData(data.getData().getList());
                    } else {
                        //如果数据有变，则更新预存款余额
                        if (listAdapter.getData().size() > 0) {
                            if (data.getData().getList().size() > 0) {
                                if (!listAdapter.getItem(0).getLg_id()
                                        .equals(data.getData().getList().get(0).getLg_id())) {
                                    LiveBus.getDefault().postEvent("REFRESH_PDC", "true");
                                }
                            } else {
                                LiveBus.getDefault().postEvent("REFRESH_PDC", "true");
                            }
                        }
                        pageIndex = 1;
                        binding.refreshLayout.finishRefresh();
                        listAdapter.setNewData(data.getData().getList());
                    }
                    if (!data.isHasmore()) {
                        binding.refreshLayout.setNoMoreData(true);
                    }
                }
            }
        });
    }

    @Override
    protected Object getStateEventKey() {
        return "PredepositLogFragment";
    }
}
