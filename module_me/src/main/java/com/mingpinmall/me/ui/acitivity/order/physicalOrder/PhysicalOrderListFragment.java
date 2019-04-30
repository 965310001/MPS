package com.mingpinmall.me.ui.acitivity.order.physicalOrder;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.dialog.TextDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentDefaultRecyclerviewBinding;
import com.mingpinmall.me.ui.adapter.PhysicalOrderListAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.PhysicalOrderBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
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
        View emptyView = View.inflate(activity, R.layout.layout_state_view, null);
        ((AppCompatImageView) emptyView.findViewById(R.id.iv_image)).setImageResource(R.drawable.ic_order_empty_white);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_title)).setText(R.string.text_title_order_empty);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_sub_title)).setText(R.string.text_sub_title_order_empty);
        emptyView.findViewById(R.id.btn_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //切换到首页
                LiveBus.getDefault().postEvent("Main", "tab", 0);
                activity.onBackPressed();
            }
        });
        physicalOrderListAdapter.setEmptyView(emptyView);
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
                final PhysicalOrderBean orderBean = physicalOrderListAdapter.getItem(position);
                if (view.getId() == R.id.order_cancel) {
                    //取消订单
                    TextDialog.showBaseDialog(activity, "取消订单", "确定取消订单？",
                            new TextDialog.SingleButtonCallback() {
                                @Override
                                public void onClick() {
                                    mViewModel.cancelOrder(EVENT_KEY, orderBean.getOrder_id());
                                }
                            })
                            .show();
                } else if (view.getId() == R.id.order_pay) {
                    //立即支付
                } else if (view.getId() == R.id.order_lock) {
                    //退款/退货中...
                } else if (view.getId() == R.id.order_sure) {
                    //确认收货
                    TextDialog.showBaseDialog(activity, "确认收货", "确认已收到订单中商品？",
                            new TextDialog.SingleButtonCallback() {
                                @Override
                                public void onClick() {
                                    //确认
                                    mViewModel.recevieOrder(EVENT_KEY, orderBean.getOrder_id());
                                }
                            })
                            .show();
                } else if (view.getId() == R.id.order_evaluation) {
                    //订单评价
                    ActivityToActivity.toActivity(ARouterConfig.Me.ORDEREVALUATEACTIVITY, "id", orderBean.getOrder_id());
                } else if (view.getId() == R.id.order_evaluation_again) {
                    //追加评价
                } else if (view.getId() == R.id.order_deliver) {
                    //查看物流
                    ActivityToActivity.toActivity(ARouterConfig.Me.ORDERDELIVERYACTIVITY, "order_id", orderBean.getOrder_id());
                } else if (view.getId() == R.id.tv_removeOrder) {
                    //移除订单
                    TextDialog.showBaseDialog(activity, "是否移除订单", "电脑端订单回收站可找回订单！",
                            new TextDialog.SingleButtonCallback() {
                                @Override
                                public void onClick() {
                                    //确认
                                    mViewModel.removeOrder(EVENT_KEY, orderBean.getOrder_id());
                                }
                            })
                            .show();
                } else if (view.getId() == R.id.ll_shopContent) {
                    //点击了 店铺 名字，跳转到店铺
                    ActivityToActivity.toActivity(ARouterConfig.classify.STOREACTIVITY, "storeId", orderBean.getStore_id());
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
        super.dataObserver();
        registerObserver(EVENT_KEY, "REMOVE_ORDER", String.class)
                .observeForever(new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String msg) {
                        //移除订单
                        if (msg.equals("success")) {
                            //成功
                            lazyLoad();
                        } else {
                            //操作失败
                            ToastUtils.showShort(msg);
                        }

                    }
                });
        registerObserver(EVENT_KEY, "RECEVIE_ORDER", String.class)
                .observeForever(new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String msg) {
                        //确认收货
                        if (msg.equals("success")) {
                            //成功
                            lazyLoad();
                        } else {
                            //失败
                            ToastUtils.showShort(msg);
                        }

                    }
                });
        registerObserver(EVENT_KEY, Object.class)
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        if (result instanceof String) {
                            if (isLoadmore) {
                                binding.refreshLayout.finishLoadMore(false);
                            } else {
                                binding.refreshLayout.finishRefresh(false);
                            }
                            ToastUtils.showShort(result.toString());
                        } else {
                            BaseResponse<PhysicalOrderBean> data = (BaseResponse<PhysicalOrderBean>) result;
                            if (!isLoadmore) {
                                pageIndex = 1;
                                binding.refreshLayout.finishRefresh();
                                physicalOrderListAdapter.setNewData(data.getNewdata());
                            } else {
                                pageIndex++;
                                binding.refreshLayout.finishLoadMore();
                                physicalOrderListAdapter.addData(data.getNewdata());
                            }
                            if (!data.isHasmore()) {
                                binding.refreshLayout.setNoMoreData(true);
                            }
                        }
                    }
                });
    }

    @Override
    protected void lazyLoad() {
        mViewModel.getPhysicalOrderList(EVENT_KEY, TYPE, getOrderKey(), 1);
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        lazyLoad();
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
