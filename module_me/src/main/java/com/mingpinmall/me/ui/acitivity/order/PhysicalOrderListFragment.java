package com.mingpinmall.me.ui.acitivity.order;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentDefaultRecyclerviewBinding;
import com.mingpinmall.me.ui.adapter.PhysicalOrderListAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.PhysicalOrderBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * 功能描述：全部实物订单页面
 * 创建人：小斌
 * 创建时间: 2019/3/26
 **/
public class PhysicalOrderListFragment extends AbsLifecycleFragment<FragmentDefaultRecyclerviewBinding, MeViewModel> {

    private int pageIndex = 1;
    private String TYPE = "";
    private String EVENT_KEY = "ALL_PHYSICAL";
    private PhysicalOrderListAdapter physicalOrderListAdapter;
    private boolean isLoadmore = false;

    public PhysicalOrderListFragment() {
    }

    public static PhysicalOrderListFragment newInstance(String type, String eventKey) {
        Bundle bundle = new Bundle();
        bundle.putString("TYPE", type);
        bundle.putString("EVENT_KEY", eventKey);

        PhysicalOrderListFragment fragment = new PhysicalOrderListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        TYPE = args.getString("TYPE");
        EVENT_KEY = args.getString("EVENT_KEY");
        Log.i("网络请求", "setArguments: " + TYPE + " & " + EVENT_KEY);
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
    public void initView(Bundle state) {
        super.initView(state);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        physicalOrderListAdapter = new PhysicalOrderListAdapter();
        binding.recyclerView.setAdapter(physicalOrderListAdapter);

        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isLoadmore = false;
                mViewModel.getPhysicalOrderList(EVENT_KEY, TYPE, getOrderKey(), 1);
            }
        });
        binding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isLoadmore = true;
                mViewModel.getPhysicalOrderList(EVENT_KEY, TYPE, getOrderKey(), (pageIndex + 1));
            }
        });
        setListItemClickListener();
    }

    private void setListItemClickListener() {
        physicalOrderListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //item点击事件
                PhysicalOrderBean orderBean = physicalOrderListAdapter.getItem(position);
                Intent intent = new Intent(activity, PhysicalOrderInformationActivity.class);
                intent.putExtra("orderId", orderBean.getOrder_id());
                startActivityForResult(intent, 1);
            }
        });
        physicalOrderListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //子控件点击事件
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 100) {
                ToastUtils.showShort("dddd");
            }
        }
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(EVENT_KEY, EVENT_KEY + "success")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        Log.i("数据", "onChanged: 进入Success " + EVENT_KEY);
                        BaseResponse<PhysicalOrderBean> data = (BaseResponse<PhysicalOrderBean>) result;
                        if (!data.isHasmore()) {
                            binding.refreshLayout.finishLoadMoreWithNoMoreData();
                        }
                        if (!isLoadmore) {
                            pageIndex = 1;
                            binding.refreshLayout.finishRefresh();
                            physicalOrderListAdapter.setNewData(data.getNewdata());
                        } else {
                            pageIndex++;
                            binding.refreshLayout.finishLoadMore();
                            physicalOrderListAdapter.addData(data.getNewdata());
                        }
                    }
                });
        registerObserver(EVENT_KEY, EVENT_KEY + "err", String.class).observeForever(new Observer<String>() {
            @Override
            public void onChanged(@Nullable String o) {
                Log.i("数据", "onChanged: 进入Err " + EVENT_KEY);
                if (binding.refreshLayout.getState() == RefreshState.Refreshing) {
                    binding.refreshLayout.finishRefresh(false);
                } else if (binding.refreshLayout.getState() == RefreshState.Loading) {
                    binding.refreshLayout.finishLoadMore(false);
                } else {
                    ToastUtils.showShort(o);
                }
            }
        });
    }

    @Override
    protected void lazyLoad() {
        mViewModel.getPhysicalOrderList(EVENT_KEY, TYPE, getOrderKey(), 1);
    }

    private String getOrderKey() {
        AppCompatEditText editText = getActivity().findViewById(R.id.ed_search);
        return editText == null ? "" : editText.getText().toString();
    }

    @Override
    protected Object getStateEventKey() {
        return "PhysicalOrderListFragment";
    }
}
