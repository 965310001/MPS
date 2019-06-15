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
import com.mingpinmall.me.ui.adapter.ReturnOrderListAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.ReturnBean;
import com.mingpinmall.me.ui.constants.Constants;

/**
 * 功能描述：退货单列表
 * *@author 小斌
 *
 * @date 2019/3/26
 **/
public class ReturnFragment extends AbsLifecycleFragment<FragmentDefaultRecyclerviewBinding, MeViewModel> {

    private int pageIndex = 1;
    private boolean isLoadmore = false;
    private ReturnOrderListAdapter listAdapter;

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        listAdapter = new ReturnOrderListAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        View emptyView = View.inflate(activity, R.layout.layout_state_view, null);
        ((AppCompatImageView) emptyView.findViewById(R.id.iv_image)).setImageResource(R.drawable.ic_refund);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_title)).setText(R.string.text_title_return_empty);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_sub_title)).setText(R.string.text_sub_title_return_empty);
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
            mViewModel.getReturnList(pageIndex + 1);
        }, binding.recyclerView);

        listAdapter.setOnItemClickListener((adapter, view, position) -> {
            //item点击
            ReturnBean.ReturnListBean data = listAdapter.getItem(position);
            ActivityToActivity.toActivity(ARouterConfig.Me.RETRUNORDERINFORMATION, "returnId", data.getRefund_id());
        });
        listAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            //item子控件点击
            ReturnBean.ReturnListBean data = listAdapter.getItem(position);
            if (view.getId() == R.id.ll_shopContent) {
                //店铺
                ActivityToActivity.toActivity(ARouterConfig.classify.STOREACTIVITY, "storeId", data.getStore_id());
            } else if (view.getId() == R.id.bt_refundInformation) {
                //查看详情
                ActivityToActivity.toActivity(ARouterConfig.Me.RETRUNORDERINFORMATION, "returnId", data.getRefund_id());
            } else if (view.getId() == R.id.bt_return) {
                ActivityToActivity.toActivity(ARouterConfig.Me.RETURNACTIVITY, "returnId", data.getRefund_id());
            }
        });
    }

    @Override
    protected void lazyLoad() {
        mViewModel.getReturnList(1);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.RETURN, Object.class).observeForever(result -> {
            if (result instanceof String) {
                ToastUtils.showShort(result.toString());
                if (isLoadmore) {
                    listAdapter.loadMoreFail();
                } else {
                    binding.refreshLayout.finishRefresh(false);
                }
            } else {
                BaseResponse<ReturnBean> refundBean = (BaseResponse<ReturnBean>) result;
                if (isLoadmore) {
                    pageIndex++;
                    listAdapter.loadMoreComplete();
                    listAdapter.addData(refundBean.getData().getReturn_list());
                } else {
                    pageIndex = 1;
                    binding.refreshLayout.finishRefresh();
                    listAdapter.setNewData(refundBean.getData().getReturn_list());
                }
                if (!refundBean.isHasmore()) {
                    listAdapter.loadMoreEnd();
                }
            }
        });

        registerObserver("REFRESH_DATA", Boolean.class).observe(this, aBoolean -> lazyLoad());
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
        return "ReturnFragment";
    }
}
