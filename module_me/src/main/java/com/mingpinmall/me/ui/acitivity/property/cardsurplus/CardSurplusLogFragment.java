package com.mingpinmall.me.ui.acitivity.property.cardsurplus;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentCardLogBinding;
import com.mingpinmall.me.ui.adapter.RCardLogAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.RCardBalanceBean;
import com.mingpinmall.me.ui.bean.RCardLogBean;
import com.mingpinmall.me.ui.constants.Constants;

/**
 * 功能描述：充值卡余额
 *
 * @author 小斌
 * @date 2019/4/13
 **/
public class CardSurplusLogFragment extends AbsLifecycleFragment<FragmentCardLogBinding, MeViewModel> {

    private int pageIndex = 1;
    private boolean isLoadmore = false;
    private RCardLogAdapter rCardLogAdapter;

    public CardSurplusLogFragment() {
    }

    public static CardSurplusLogFragment newFragment() {
        return new CardSurplusLogFragment();
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        rCardLogAdapter = new RCardLogAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        View emptyView = View.inflate(activity, R.layout.layout_state_view, null);
        ((AppCompatImageView) emptyView.findViewById(R.id.iv_image)).setImageResource(R.drawable.ic_mcc_07_w);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_title)).setText(R.string.text_title_card_empty);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_sub_title)).setText(R.string.text_sub_title_card_empty);
        emptyView.findViewById(R.id.btn_action).setVisibility(View.GONE);
        rCardLogAdapter.setEmptyView(emptyView);
        binding.recyclerView.setAdapter(rCardLogAdapter);

        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            isLoadmore = false;
            lazyLoad();
        });
        rCardLogAdapter.setOnLoadMoreListener(() -> {
            isLoadmore = true;
            mViewModel.getRCBLog(pageIndex + 1);
        }, binding.recyclerView);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver("REFRESH_RCARD", String.class).observeForever(result -> lazyLoad());
        registerObserver(Constants.RCBALANCE, Object.class).observeForever(result -> {
            if (result instanceof RCardBalanceBean) {
                RCardBalanceBean data = (RCardBalanceBean) result;
                binding.tvSurplus.setText(data.getAvailable_rc_balance());
            } else {
                ToastUtils.showShort(result.toString());
            }
        });
        registerObserver(Constants.RCCHARGE_LIST, Object.class).observeForever(result -> {
            if (result instanceof String) {
                ToastUtils.showShort(result.toString());
                if (isLoadmore) {
                    rCardLogAdapter.loadMoreFail();
                } else {
                    binding.refreshLayout.finishRefresh(false);
                }
            } else {
                BaseResponse<RCardLogBean> data = (BaseResponse<RCardLogBean>) result;
                if (isLoadmore) {
                    rCardLogAdapter.loadMoreComplete();
                    pageIndex++;
                    rCardLogAdapter.addData(data.getData().getLog_list());
                } else {
                    binding.refreshLayout.finishRefresh();
                    pageIndex = 1;
                    rCardLogAdapter.setNewData(data.getData().getLog_list());
                }
                if (!data.isHasmore()) {
                    rCardLogAdapter.loadMoreEnd();
                }
            }
        });
    }

    @Override
    protected void lazyLoad() {
        mViewModel.getRCBalance();
        mViewModel.getRCBLog(1);
    }

    @Override
    protected Object getStateEventKey() {
        return "CardSurplusLogFragment";
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_card_log;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }
}
