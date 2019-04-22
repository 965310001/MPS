package com.mingpinmall.me.ui.acitivity.order.RefundOrder;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentDefaultRecyclerviewBinding;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.RefundBean;
import com.mingpinmall.me.ui.bean.ReturnBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * 功能描述：退货单列表
 * 创建人：小斌
 * 创建时间: 2019/3/26
 **/
public class ReturnFragment extends AbsLifecycleFragment<FragmentDefaultRecyclerviewBinding, MeViewModel> {

    private int pageIndex = 1;
    private boolean isLoadmore = false;

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        binding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isLoadmore = true;
                mViewModel.getReturnList(pageIndex + 1);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isLoadmore = false;
                lazyLoad();
            }
        });
//        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                //item点击
//                RefundBean.RefundListBean data = listAdapter.getItem(position);
//
//            }
//        });
//        listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                //item子控件点击
//                RefundBean.RefundListBean data = listAdapter.getItem(position);
//                if (view.getId() == R.id.ll_shopContent) {
//                    //店铺
//                    ActivityToActivity.toActivity(ARouterConfig.classify.STOREACTIVITY, "storeId", data.getStore_id());
//                } else if (view.getId() == R.id.bt_refundInformation){
//                    //查看详情 TODO 查看详情页面
//
//                }
//            }
//        });
    }

    @Override
    protected void lazyLoad() {
        mViewModel.getReturnList(1);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver("RETURN", "success")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        BaseResponse<ReturnBean> refundBean = (BaseResponse<ReturnBean>) result;
                        if (isLoadmore) {
                            pageIndex++;
                            binding.refreshLayout.finishLoadMore();
                        } else {
                            pageIndex = 1;
                            binding.refreshLayout.finishRefresh();
                        }
                        if (!refundBean.isHasmore()) {
                            binding.refreshLayout.finishLoadMoreWithNoMoreData();
                        }
                    }
                });
        registerObserver("RETURN", "err", String.class)
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
        return "ReturnFragment";
    }
}
