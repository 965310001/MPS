package com.mingpinmall.me.ui.acitivity.property.couponFragment;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.BaseRecyclerviewBinding;
import com.mingpinmall.me.ui.adapter.CouponListAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.CouponListBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：已领取的店铺代金券列表
 * 创建人：小斌
 * 创建时间: 2019/4/20
 **/
public class CouponListFragment extends AbsLifecycleFragment<BaseRecyclerviewBinding, MeViewModel> {

    private int pageIndex = 1;
    private boolean isHasmore = true;
    private boolean isLoadmore = false;
    private CouponListAdapter listAdapter;

    public static CouponListFragment newFragment() {
        return new CouponListFragment();
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        listAdapter = new CouponListAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerView.setAdapter(listAdapter);

        binding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (!isHasmore) {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                    return;
                }
                isLoadmore = true;
                mViewModel.getCouponList(pageIndex + 1);
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
                //列表点击事件
                CouponListBean.VoucherListBean data = listAdapter.getItem(position);
                if (data.getItemType() == 1) {
                    ActivityToActivity.toActivity(ARouterConfig.classify.STOREACTIVITY, "storeId", data.getStore_id());
                }
            }
        });
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver("REFRESH_COUPON", String.class).observeForever(new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                lazyLoad();
            }
        });
        registerObserver("COUPONLISTBEAN", "success").observeForever(new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object result) {
                BaseResponse<CouponListBean> data = (BaseResponse<CouponListBean>) result;
                isHasmore = data.isHasmore();
                if (isLoadmore) {
                    binding.refreshLayout.finishLoadMore();
                    pageIndex++;
                    int typeCount = 0;
                    for (int i = 0; i < listAdapter.getItemCount(); i++) {
                        if (listAdapter.getItem(i).getType() != 1) {
                            typeCount = i - 1;
                            break;
                        }
                    }
                    for (CouponListBean.VoucherListBean item : data.getData().getVoucher_list()) {
                        if (item.getType() == 1) {
                            listAdapter.addData(typeCount, item);
                            typeCount++;
                        } else {
                            listAdapter.addData(item);
                        }
                    }
                } else {
                    binding.refreshLayout.finishRefresh();
                    pageIndex = 1;
                    List<CouponListBean.VoucherListBean> dataList = new ArrayList<>();
                    for (CouponListBean.VoucherListBean item : data.getData().getVoucher_list()) {
                        if (item.getType() == 1) {
                            dataList.add(item);
                        }
                    }
                    CouponListBean.VoucherListBean spaceBean = new CouponListBean.VoucherListBean();
                    spaceBean.setType(0);
                    spaceBean.setVoucher_state_text("已过期的券");
                    dataList.add(spaceBean);
                    for (CouponListBean.VoucherListBean item : data.getData().getVoucher_list()) {
                        if (item.getType() != 1) {
                            dataList.add(item);
                        }
                    }
                    listAdapter.setNewData(dataList);
                }
                if (!isHasmore) {
                    binding.refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }
        });
        registerObserver("COUPONLISTBEAN", "err", String.class).observeForever(new Observer<String>() {
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
        mViewModel.getCouponList(1);
    }

    @Override
    protected Object getStateEventKey() {
        return "CouponListFragment";
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.base_recyclerview;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }
}
