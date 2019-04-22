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

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.dialog.MaterialDialogUtils;
import com.goldze.common.dmvvm.widget.dialog.TextDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentDefaultRecyclerviewBinding;
import com.mingpinmall.me.ui.adapter.VirtualOrderListAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.VirtualOrderBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * 功能描述：全部虚拟订单页面
 * 创建人：小斌
 * 创建时间: 2019/3/26
 **/
public class VirtualOrderListFragment extends AbsLifecycleFragment<FragmentDefaultRecyclerviewBinding, MeViewModel> {

    private int pageIndex = 1;
    private boolean isLoadmore = false;
    private String TYPE = "";
    private String EVENT_KEY = "ALL_VIRTUAL";
    private VirtualOrderListAdapter virtualOrderListAdapter;

    public VirtualOrderListFragment() {
    }

    public static VirtualOrderListFragment newInstance(String type, String eventKey) {
        Bundle bundle = new Bundle();
        bundle.putString("TYPE", type);
        bundle.putString("EVENT_KEY", eventKey);

        VirtualOrderListFragment fragment = new VirtualOrderListFragment();
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
        virtualOrderListAdapter = new VirtualOrderListAdapter();
        binding.recyclerView.setAdapter(virtualOrderListAdapter);

        binding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isLoadmore = true;
                mViewModel.getVirtualOrderList(EVENT_KEY, TYPE, getOrderKey(), (pageIndex + 1));
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isLoadmore = false;
                mViewModel.getVirtualOrderList(EVENT_KEY, TYPE, getOrderKey(), 1);
            }
        });
        setListItemClickListener();
    }

    private void setListItemClickListener() {
        virtualOrderListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //item点击事件
                VirtualOrderBean.OrderListBean listBean = virtualOrderListAdapter.getItem(position);
                Intent intent = new Intent(activity, VirtualOrderInformationActivity.class);
                intent.putExtra("orderId", listBean.getOrder_id());
                startActivityForResult(intent, 1);
            }
        });
        virtualOrderListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //子控件点击事件
                final VirtualOrderBean.OrderListBean orderListBean = virtualOrderListAdapter.getItem(position);
                if (view.getId() == R.id.bt_cancelOrder) {
                    TextDialog.showBaseDialog(activity, "提示", "确定取消订单？",
                            new TextDialog.SingleButtonCallback() {
                                @Override
                                public void onClick() {
                                    mViewModel.cancelVirtualOrder(EVENT_KEY, orderListBean.getOrder_id());
                                }
                            })
                            .show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 100) {
                lazyLoad();
            }
        }
    }

    @Override
    protected void dataObserver() {
        registerObserver(EVENT_KEY, "REFRESH_ORDER_LIST")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object o) {
                        //操作成功后刷新列表
                        lazyLoad();
                    }
                });
        registerObserver(EVENT_KEY, "DO_SOMETHING_ERR", String.class)
                .observeForever(new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String o) {
                        //操作失败
                        ToastUtils.showShort(o);
                    }
                });
        registerObserver(EVENT_KEY, EVENT_KEY + "success").observeForever(new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object result) {
                BaseResponse<VirtualOrderBean> data = (BaseResponse<VirtualOrderBean>) result;
                if (!isLoadmore) {
                    pageIndex = 1;
                    binding.refreshLayout.finishRefresh();
                    virtualOrderListAdapter.setNewData(data.getData().getOrder_list());
                } else {
                    pageIndex++;
                    binding.refreshLayout.finishLoadMore();
                    virtualOrderListAdapter.addData(data.getData().getOrder_list());
                }
                if (!data.isHasmore()) {
                    binding.refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }
        });
        registerObserver(EVENT_KEY, EVENT_KEY + "err").observeForever(new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object o) {
                if (binding.refreshLayout.getState() == RefreshState.Refreshing) {
                    binding.refreshLayout.finishRefresh(false);
                } else if (binding.refreshLayout.getState() == RefreshState.Loading) {
                    binding.refreshLayout.finishLoadMore(false);
                }
            }
        });
    }

    @Override
    protected void lazyLoad() {
        mViewModel.getVirtualOrderList(EVENT_KEY, TYPE, getOrderKey(), 1);
    }

    private String getOrderKey() {
        AppCompatEditText editText = getActivity().findViewById(R.id.ed_search);
        return editText == null ? "" : editText.getText().toString();
    }

    @Override
    protected Object getStateEventKey() {
        return "VirtualOrderListFragment";
    }
}
