package com.mingpinmall.me.ui.acitivity.order.RefundOrder;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentDefaultRecyclerviewBinding;
import com.mingpinmall.me.ui.adapter.RefundOrderListAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.RefundBean;
import com.mingpinmall.me.ui.bean.ReturnBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * 功能描述：退款单列表
 * 创建人：小斌
 * 创建时间: 2019/3/26
 **/
public class RefundFragment extends AbsLifecycleFragment<FragmentDefaultRecyclerviewBinding, MeViewModel> {

    private int pageIndex = 1;
    private boolean isLoadmore = false;
    private RefundOrderListAdapter listAdapter;

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        listAdapter = new RefundOrderListAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerView.setAdapter(listAdapter);

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
        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //item点击
                RefundBean.RefundListBean data = listAdapter.getItem(position);

            }
        });
        listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //item子控件点击
                RefundBean.RefundListBean data = listAdapter.getItem(position);
                if (view.getId() == R.id.ll_shopContent) {
                    //店铺
                    ActivityToActivity.toActivity(ARouterConfig.classify.STOREACTIVITY, "storeId", data.getStore_id());
                } else if (view.getId() == R.id.bt_refundInformation){
                    //查看详情
                    ActivityToActivity.toActivity(ARouterConfig.Me.REFUNDORDERINFORMATION, "refundId", data.getRefund_id());
                }
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
        registerObserver("REFUND", "success")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        BaseResponse<RefundBean> refundBean = (BaseResponse<RefundBean>) result;
                        if (isLoadmore) {
                            pageIndex++;
                            binding.refreshLayout.finishLoadMore();
                            listAdapter.addData(refundBean.getData().getRefund_list());
                        } else {
                            pageIndex = 1;
                            binding.refreshLayout.finishRefresh();
                            listAdapter.setNewData(refundBean.getData().getRefund_list());
                        }
                        if (!refundBean.isHasmore()) {
                            binding.refreshLayout.setNoMoreData(true);
                        }
                    }
                });
        registerObserver("REFUND", "err", String.class)
                .observeForever(new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String msg) {

                    }
                });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_default_recyclerview;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    protected Object getStateEventKey() {
        return "RefundFragment";
    }
}
