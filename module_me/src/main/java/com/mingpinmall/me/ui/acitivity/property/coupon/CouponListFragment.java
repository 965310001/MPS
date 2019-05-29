package com.mingpinmall.me.ui.acitivity.property.coupon;

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
import com.mingpinmall.me.databinding.BaseRecyclerviewBinding;
import com.mingpinmall.me.ui.adapter.CouponListAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.CouponListBean;
import com.mingpinmall.me.ui.constants.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：已领取的店铺代金券列表
 * @author 小斌
 * @date 2019/4/20
 **/
public class CouponListFragment extends AbsLifecycleFragment<BaseRecyclerviewBinding, MeViewModel> {

    private int pageIndex = 1;
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
        View emptyView = View.inflate(activity, R.layout.layout_state_view, null);
        ((AppCompatImageView) emptyView.findViewById(R.id.iv_image)).setImageResource(R.drawable.ic_mcc_08_w);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_title)).setText(R.string.text_title_coupon_empty);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_sub_title)).setText(R.string.text_sub_title_coupon_empty);
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
            mViewModel.getCouponList(pageIndex + 1);
        }, binding.recyclerView);

        listAdapter.setOnItemClickListener((adapter, view, position) -> {
            //列表点击事件
            CouponListBean.VoucherListBean data = listAdapter.getItem(position);
            if (data.getItemType() == 1) {
                ActivityToActivity.toActivity(ARouterConfig.classify.STOREACTIVITY, "storeId", data.getStore_id());
            }
        });
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver("REFRESH_COUPON", String.class).observeForever(s -> lazyLoad());
        registerObserver(Constants.COUPONLISTBEAN, Object.class).observeForever(result -> {
            if (result instanceof String) {
                ToastUtils.showShort(result.toString());
                if (isLoadmore) {
                    listAdapter.loadMoreFail();
                } else {
                    binding.refreshLayout.finishRefresh(false);
                }
            }else {
                formatData((BaseResponse<CouponListBean>) result);
            }
        });
    }

    private void formatData(BaseResponse<CouponListBean> data) {
        if (isLoadmore) {
            listAdapter.loadMoreComplete();
            pageIndex++;
            int typeCount = listAdapter.getItemCount() - 1;
            for (int i = 0; i < listAdapter.getItemCount(); i++) {
                if (listAdapter.getItem(i).getType() != 1) {
                    typeCount = i;
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
            if (data.getData().getVoucher_list().isEmpty() || data.getData().getVoucher_list() == null) {
                listAdapter.setNewData(new ArrayList<>());
            } else {
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
        }
        if (!data.isHasmore()) {
            listAdapter.loadMoreEnd();
        }
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
