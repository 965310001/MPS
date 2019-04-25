package com.mingpinmall.me.ui.acitivity.property;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
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
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * 功能描述：会员积分
 * 创建人：小斌
 * 创建时间: 2019/3/28
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

        binding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isLoadmore = true;
                mViewModel.getVipPointLog(pageIndex + 1);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isLoadmore = false;
                initData();
            }
        });
    }

    @Override
    protected void initData() {
        mViewModel.getVipPoint();
        mViewModel.getVipPointLog(1);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver("VIP_POINT", "success", VipPointBean.class).observeForever(new Observer<VipPointBean>() {
            @Override
            public void onChanged(@Nullable VipPointBean vipPointBean) {
                binding.tvSurplus.setText(vipPointBean.getPoint());
            }
        });
        registerObserver("VIP_POINT", "err", String.class).observeForever(new Observer<String>() {
            @Override
            public void onChanged(@Nullable String msg) {
                ToastUtils.showShort(msg);
            }
        });
        registerObserver("VIP_POINT_LOG", "success").observeForever(new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object result) {
                //获取到了会员积分记录
                BaseResponse<VipPointListBean> data = (BaseResponse<VipPointListBean>) result;
                if (isLoadmore) {
                    pageIndex++;
                    binding.refreshLayout.finishLoadMore();
                    listAdapter.addData(data.getData().getLog_list());
                } else {
                    pageIndex = 1;
                    binding.refreshLayout.finishRefresh();
                    listAdapter.setNewData(data.getData().getLog_list());
                }
                if (!data.isHasmore()) {
                    binding.refreshLayout.setNoMoreData(true);
                }
            }
        });
        registerObserver("VIP_POINT_LOG", "err", String.class).observeForever(new Observer<String>() {
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
        return "VipIntegralActivity";
    }
}
