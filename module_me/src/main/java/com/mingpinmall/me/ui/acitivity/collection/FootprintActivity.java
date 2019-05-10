package com.mingpinmall.me.ui.acitivity.collection;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.dialog.TextDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityFootprintBinding;
import com.mingpinmall.me.ui.adapter.FootprintListAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.FootprintBean;
import com.mingpinmall.me.ui.constants.Constants;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import static com.goldze.common.dmvvm.constants.ARouterConfig.SUCCESS;

/**
 * 功能描述：我的足迹
 * @author 小斌
 * @date 2019/3/27
 **/
@Route(path = ARouterConfig.Me.FOOTPRINTACTIVITY)
public class FootprintActivity extends AbsLifecycleActivity<ActivityFootprintBinding, MeViewModel> {

    private boolean isLoadmore = false;
    private int pageIndex = 1;
    private FootprintListAdapter footprintListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_footprint;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTitle(getString(R.string.title_footprintActivity));
        tvRight.setText(getString(R.string.text_clearn));
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setOnClickListener(this);

        footprintListAdapter = new FootprintListAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        View emptyView = View.inflate(activity, R.layout.layout_state_view, null);
        ((AppCompatImageView) emptyView.findViewById(R.id.iv_image)).setImageResource(R.drawable.ic_me_goods_browse);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_title)).setText(R.string.text_title_footprint_empty);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_sub_title)).setText(R.string.text_sub_title_footprint_empty);
        emptyView.findViewById(R.id.btn_action).setOnClickListener(v -> {
            //前往首页
            LiveBus.getDefault().postEvent("Main", "tab", 0);
            activity.onBackPressed();
        });
        footprintListAdapter.setEmptyView(emptyView);
        binding.recyclerView.setAdapter(footprintListAdapter);

        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            isLoadmore = false;
            mViewModel.getMyFootprint(1);
        });
        footprintListAdapter.setOnLoadMoreListener(() -> {
            isLoadmore = true;
            mViewModel.getMyFootprint(pageIndex + 1);
        }, binding.recyclerView);

        footprintListAdapter.setOnItemClickListener((adapter, view, position) -> {
            //item点击事件
            FootprintBean.GoodsbrowseListBean bean = footprintListAdapter.getItem(position);
            ActivityToActivity.toActivity(ARouterConfig.home.SHOPPINGDETAILSACTIVITY, "id", bean.getGoods_id());
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mViewModel.getMyFootprint(1);
    }

    @Override
    protected void dataObserver() {
        registerObserver(Constants.GET_FOOTPRINT, Object.class).observeForever(result -> {
            if (result instanceof String) {
                ToastUtils.showShort(result.toString());
                //获取失败
                if (isLoadmore) {
                    footprintListAdapter.loadMoreFail();
                } else {
                    binding.refreshLayout.finishRefresh(false);
                }
            } else {
                BaseResponse<FootprintBean> data = (BaseResponse<FootprintBean>) result;
                if (isLoadmore) {
                    pageIndex++;
                    footprintListAdapter.loadMoreComplete();
                    footprintListAdapter.addData(data.getData().getGoodsbrowse_list());
                } else {
                    pageIndex = 1;
                    binding.refreshLayout.finishRefresh();
                    footprintListAdapter.setNewData(data.getData().getGoodsbrowse_list());
                }
                if (!data.isHasmore()) {
                    footprintListAdapter.loadMoreEnd();
                }
            }
        });
        registerObserver(Constants.CLEAR_FOOTPRINT, String.class).observeForever(msg -> {
            if (msg.equals(SUCCESS)) {
                //清空成功
                initData();
            } else {
                //清空失败
                ToastUtils.showShort(msg);
            }
        });
    }

    @Override
    protected Object getStateEventKey() {
        return "FOOT_PRINT_ACTIVTY";
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.tv_right) {
            //清空记录
            TextDialog.showBaseDialog(activity, "清空足迹", "将会清空最近浏览记录", new TextDialog.SingleButtonCallback() {
                @Override
                public void onClick() {
                    mViewModel.clearnMyFootprint();
                }
            }).show();
        }
    }
}
