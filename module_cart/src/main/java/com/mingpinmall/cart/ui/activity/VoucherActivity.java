package com.mingpinmall.cart.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.databinding.ActivityBaseRefreshRecyclerviewBinding;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.dialog.TextDialog;
import com.goldze.common.dmvvm.widget.loading.CustomProgressDialog;
import com.mingpinmall.cart.R;
import com.mingpinmall.cart.ui.adapter.VoucherListAdapter;
import com.mingpinmall.cart.ui.api.CartViewModel;
import com.mingpinmall.cart.ui.bean.AllVoucherBean;
import com.mingpinmall.cart.ui.constants.Constants;

/**
 * 代金券领取（全店铺）
 *
 * @author 小斌
 * @data 2019/5/29
 **/
@Route(path = ARouterConfig.cart.VOUCHERACTIVITY)
public class VoucherActivity extends AbsLifecycleActivity<ActivityBaseRefreshRecyclerviewBinding, CartViewModel> {

    private int pageIndex = 1;
    private boolean isLoadmore = false;

    private VoucherListAdapter listAdapter;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTitle("领取代金券");
        listAdapter = new VoucherListAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerView.setAdapter(listAdapter);
        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> initData());
        listAdapter.setOnLoadMoreListener(() -> {
            isLoadmore = true;
            mViewModel.getAllVoucherList(pageIndex + 1, Constants.VOUCHER_ALL);
        }, binding.recyclerView);

        listAdapter.setOnItemClickListener((adapter, view, position) -> {
            //领取代金券
            CustomProgressDialog.show(activity, "正在领取...");
            AllVoucherBean.VoucherListBean data = listAdapter.getItem(position);
            mViewModel.getVoucherFreeex(data.getVoucher_t_id(), Constants.VOUCHER_ALL[1]);
        });
    }

    @Override
    protected void initData() {
        super.initData();
        isLoadmore = false;
        mViewModel.getAllVoucherList(1, Constants.VOUCHER_ALL[0]);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.VOUCHER_ALL[0], Object.class).observeForever(result -> {
            if (result instanceof String) {
                if (isLoadmore) {
                    binding.refreshLayout.finishRefresh(false);
                } else {
                    listAdapter.loadMoreFail();
                }
                ToastUtils.showShort(result.toString());
                return;
            }
            BaseResponse<AllVoucherBean> voucherBean = (BaseResponse<AllVoucherBean>) result;
            if (voucherBean.isSuccess()) {
                if (isLoadmore) {
                    pageIndex++;
                    listAdapter.addData(voucherBean.getData().getVoucher_list());
                    listAdapter.loadMoreComplete();
                } else {
                    pageIndex = 1;
                    binding.refreshLayout.finishRefresh();
                    listAdapter.setNewData(voucherBean.getData().getVoucher_list());
                }
                if (!voucherBean.isHasmore()) {
                    listAdapter.loadMoreEnd();
                }
            } else {
                if (isLoadmore) {
                    binding.refreshLayout.finishRefresh(false);
                } else {
                    listAdapter.loadMoreFail();
                }
                ToastUtils.showShort(voucherBean.getMessage());
            }
        });
        registerObserver(Constants.VOUCHER_ALL[1], String.class).observeForever(result -> {
            CustomProgressDialog.stop();
            TextDialog.showBaseDialog(activity,
                    ARouterConfig.SUCCESS.equals(result) ? "" : "领取失败",
                    ARouterConfig.SUCCESS.equals(result) ? "领取成功" : result
            ).show();
        });
    }

    @Override
    protected Object getStateEventKey() {
        return "VoucherActivity";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_refresh_recyclerview;
    }
}
