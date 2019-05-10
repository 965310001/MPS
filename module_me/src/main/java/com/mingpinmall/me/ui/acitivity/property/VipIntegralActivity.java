package com.mingpinmall.me.ui.acitivity.property;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityVipintergralBinding;
import com.mingpinmall.me.ui.adapter.VipPointListAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.VipPointBean;
import com.mingpinmall.me.ui.bean.VipPointListBean;
import com.mingpinmall.me.ui.constants.Constants;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * 功能描述：会员积分
 * @author 小斌
 * @date 2019/3/28
 **/
@Route(path = ARouterConfig.Me.VIPINTERGRALACTIVITY)
public class VipIntegralActivity extends AbsLifecycleActivity<ActivityVipintergralBinding, MeViewModel> {

    private int pageIndex = 1;
    private boolean isLoadmore = false;
    private VipPointListAdapter listAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vipintergral;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTitle(R.string.title_vipIntegralActivity);
        listAdapter = new VipPointListAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(activity,
                DividerItemDecoration.VERTICAL));
        binding.recyclerView.setAdapter(listAdapter);

        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            isLoadmore = false;
            initData();
        });
        listAdapter.setOnLoadMoreListener(() -> {
            isLoadmore = true;
            mViewModel.getVipPointLog(pageIndex + 1);
        }, binding.recyclerView);
    }

    @Override
    protected void initData() {
        mViewModel.getVipPoint();
        mViewModel.getVipPointLog(1);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.VIP_POINT, Object.class).observeForever(result -> {
            if (result instanceof VipPointBean) {
                VipPointBean vipPointBean = (VipPointBean) result;
                binding.tvSurplus.setText(vipPointBean.getPoint());
            } else {
                ToastUtils.showShort(result.toString());
            }
        });
        registerObserver(Constants.VIP_POINT_LOG, Object.class).observeForever(result -> {
            if (result instanceof String) {
                ToastUtils.showShort(result.toString());
                if (isLoadmore) {
                    listAdapter.loadMoreFail();
                } else {
                    binding.refreshLayout.finishRefresh(false);
                }
            } else {
                //获取到了会员积分记录
                BaseResponse<VipPointListBean> data = (BaseResponse<VipPointListBean>) result;
                if (isLoadmore) {
                    pageIndex++;
                    listAdapter.loadMoreComplete();
                    listAdapter.addData(data.getData().getLog_list());
                } else {
                    pageIndex = 1;
                    binding.refreshLayout.finishRefresh();
                    listAdapter.setNewData(data.getData().getLog_list());
                }
                if (!data.isHasmore()) {
                    listAdapter.loadMoreEnd();
                }
            }
        });
    }

    @Override
    protected Object getStateEventKey() {
        return "VipIntegralActivity";
    }
}
