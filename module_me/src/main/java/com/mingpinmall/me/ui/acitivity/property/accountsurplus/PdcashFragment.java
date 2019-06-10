package com.mingpinmall.me.ui.acitivity.property.accountsurplus;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentDefaultRecyclerviewBinding;
import com.mingpinmall.me.ui.adapter.PdcashAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.PdcashBean;
import com.mingpinmall.me.ui.constants.Constants;

/**
 * 功能描述：余额提现
 * @author 小斌
 * @date 2019/3/28
 **/
public class PdcashFragment extends AbsLifecycleFragment<FragmentDefaultRecyclerviewBinding, MeViewModel> {

    private int pageIndex = 1;
    private boolean isLoadmore = false;
    private PdcashAdapter pdcashAdapter;

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
        pdcashAdapter = new PdcashAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        View emptyView = View.inflate(activity, R.layout.layout_state_view, null);
        ((AppCompatImageView) emptyView.findViewById(R.id.iv_image)).setImageResource(R.drawable.ic_mcc_06_w);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_title)).setText(R.string.text_title_pdcash_empty);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_sub_title)).setText(R.string.text_sub_title_log_empty);
        emptyView.findViewById(R.id.btn_action).setVisibility(View.GONE);
        pdcashAdapter.setEmptyView(emptyView);
        binding.recyclerView.setAdapter(pdcashAdapter);

        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            isLoadmore = false;
            lazyLoad();
        });
        pdcashAdapter.setOnLoadMoreListener(() -> {
            isLoadmore = true;
            mViewModel.getPdcashList(pageIndex + 1);
        }, binding.recyclerView);

        pdcashAdapter.setOnItemClickListener((adapter, view, position) -> {
            //列表点击事件
            PdcashBean.ListBean bean = pdcashAdapter.getItem(position);
            ActivityToActivity.toActivity(ARouterConfig.Me.PDCASHINFORMATIONACTIVITY, "pdcId", bean.getPdc_id());
        });
    }

    @Override
    protected void lazyLoad() {
        mViewModel.getPdcashList(1);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.PDCASH, Object.class).observeForever(result -> {
            if (result instanceof String) {
                ToastUtils.showShort(result.toString());
                if (isLoadmore) {
                    pdcashAdapter.loadMoreFail();
                } else {
                    binding.refreshLayout.finishRefresh(false);
                }
            } else {
                BaseResponse<PdcashBean> data = (BaseResponse<PdcashBean>) result;
                if (isLoadmore) {
                    pageIndex++;
                    pdcashAdapter.loadMoreComplete();
                    pdcashAdapter.addData(data.getData().getList());
                } else {
                    //如果数据有变，则更新预存款余额
                    if (pdcashAdapter.getData().size() > 0) {
                        if (data.getData().getList().size() > 0) {
                            if (!pdcashAdapter.getItem(0).getPdc_id()
                                    .equals(data.getData().getList().get(0).getPdc_id())) {
                                LiveBus.getDefault().postEvent("REFRESH_PDC", "true");
                            }
                        } else {
                            LiveBus.getDefault().postEvent("REFRESH_PDC", "true");
                        }
                    }
                    pageIndex = 1;
                    binding.refreshLayout.finishRefresh();
                    pdcashAdapter.setNewData(data.getData().getList());
                }
                if (!data.isHasmore()) {
                    pdcashAdapter.loadMoreEnd();
                }
            }
        });
    }

    @Override
    protected Object getStateEventKey() {
        return "PdcashFragment";
    }
}
