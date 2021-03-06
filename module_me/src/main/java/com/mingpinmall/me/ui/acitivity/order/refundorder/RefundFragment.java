package com.mingpinmall.me.ui.acitivity.order.refundorder;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentDefaultRecyclerviewBinding;
import com.mingpinmall.me.ui.adapter.RefundOrderListAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.RefundBean;
import com.mingpinmall.me.ui.constants.Constants;

/**
 * 功能描述：退款单列表
 * *@author 小斌
 * @date 2019/3/26
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
        View emptyView = View.inflate(activity, R.layout.layout_state_view, null);
        ((AppCompatImageView) emptyView.findViewById(R.id.iv_image)).setImageResource(R.drawable.ic_refund);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_title)).setText(R.string.text_title_refund_empty);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_sub_title)).setText(R.string.text_sub_title_refund_empty);
        emptyView.findViewById(R.id.btn_action).setVisibility(View.GONE);
        listAdapter.setEmptyView(emptyView);
        binding.recyclerView.setAdapter(listAdapter);

        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            isLoadmore = false;
            lazyLoad();
        });
        listAdapter.setOnLoadMoreListener(() -> {
            isLoadmore = true;
            mViewModel.getRefundList(pageIndex + 1);
        }, binding.recyclerView);
        listAdapter.setOnItemClickListener((adapter, view, position) -> {
            // item点击
            RefundBean.RefundListBean data = listAdapter.getItem(position);
            ActivityToActivity.toActivity(ARouterConfig.Me.REFUNDORDERINFORMATION, "refundId", data.getRefund_id());
        });
        listAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            //item子控件点击
            RefundBean.RefundListBean data = listAdapter.getItem(position);
            if (view.getId() == R.id.ll_shopContent) {
                //店铺
                ActivityToActivity.toActivity(ARouterConfig.classify.STOREACTIVITY, "storeId", data.getStore_id());
            } else if (view.getId() == R.id.bt_refundInformation){
                //查看详情
                ActivityToActivity.toActivity(ARouterConfig.Me.REFUNDORDERINFORMATION, "refundId", data.getRefund_id());
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
        registerObserver(Constants.REFUND, Object.class).observeForever(result -> {
            if (result instanceof String) {
                ToastUtils.showShort(result.toString());
                if (isLoadmore) {
                    listAdapter.loadMoreFail();
                } else {
                    binding.refreshLayout.finishRefresh(false);
                }
            } else {
                BaseResponse<RefundBean> refundBean = (BaseResponse<RefundBean>) result;
                if (isLoadmore) {
                    pageIndex++;
                    listAdapter.loadMoreComplete();
                    listAdapter.addData(refundBean.getData().getRefund_list());
                } else {
                    pageIndex = 1;
                    binding.refreshLayout.finishRefresh();
                    listAdapter.setNewData(refundBean.getData().getRefund_list());
                }
                if (!refundBean.isHasmore()) {
                    listAdapter.loadMoreEnd();
                }
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
