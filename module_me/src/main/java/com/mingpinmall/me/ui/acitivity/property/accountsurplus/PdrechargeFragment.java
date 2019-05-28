package com.mingpinmall.me.ui.acitivity.property.accountsurplus;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentDefaultRecyclerviewBinding;
import com.mingpinmall.me.ui.adapter.PdrechargeAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.PdrechargeBean;
import com.mingpinmall.me.ui.constants.Constants;

/**
 * 功能描述：充值明细
 * @author 小斌
 * @date 2019/3/28
 **/
public class PdrechargeFragment extends AbsLifecycleFragment<FragmentDefaultRecyclerviewBinding, MeViewModel> {

    private int pageIndex = 1;
    private boolean isLoadmore = false;
    private PdrechargeAdapter pdrechargeAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_default_recyclerview;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        pdrechargeAdapter = new PdrechargeAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        View emptyView = View.inflate(activity, R.layout.layout_state_view, null);
        ((AppCompatImageView) emptyView.findViewById(R.id.iv_image)).setImageResource(R.drawable.ic_mcc_06_w);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_title)).setText(R.string.text_title_charge_empty);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_sub_title)).setText(R.string.text_sub_title_log_empty);
        emptyView.findViewById(R.id.btn_action).setVisibility(View.GONE);
        pdrechargeAdapter.setEmptyView(emptyView);
        binding.recyclerView.setAdapter(pdrechargeAdapter);

        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            isLoadmore = false;
            lazyLoad();
        });
        pdrechargeAdapter.setOnLoadMoreListener(() -> {
            isLoadmore = true;
            mViewModel.getPdreChargeList(pageIndex + 1);
        }, binding.recyclerView);
    }

    @Override
    protected void lazyLoad() {
        mViewModel.getPdreChargeList(1);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.PDRECHARGE, Object.class).observeForever(result -> {
            if (result instanceof String) {
                ToastUtils.showShort(result.toString());
                if (isLoadmore) {
                    pdrechargeAdapter.loadMoreFail();
                } else {
                    binding.refreshLayout.finishRefresh(false);
                }
            } else {
                BaseResponse<PdrechargeBean> data = (BaseResponse<PdrechargeBean>) result;
                if (isLoadmore) {
                    pageIndex++;
                    pdrechargeAdapter.loadMoreComplete();
                    pdrechargeAdapter.addData(data.getData().getList());
                } else {
                    //如果数据有变，则更新预存款余额
                    if (pdrechargeAdapter.getData().size() > 0) {
                        if (data.getData().getList().size() > 0) {
                            if (!pdrechargeAdapter.getItem(0).getPdr_id()
                                    .equals(data.getData().getList().get(0).getPdr_id())) {
                                LiveBus.getDefault().postEvent("REFRESH_PDC", "true");
                            }
                        } else {
                            LiveBus.getDefault().postEvent("REFRESH_PDC", "true");
                        }
                    }
                    pageIndex = 1;
                    binding.refreshLayout.finishRefresh();
                    pdrechargeAdapter.setNewData(data.getData().getList());
                }
                if (!data.isHasmore()) {
                    pdrechargeAdapter.loadMoreEnd();
                }
            }
        });
    }

    @Override
    protected Object getStateEventKey() {
        return "PdrechargeFragment";
    }
}
