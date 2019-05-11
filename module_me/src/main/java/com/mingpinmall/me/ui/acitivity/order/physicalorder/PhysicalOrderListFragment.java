package com.mingpinmall.me.ui.acitivity.order.physicalorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.bean.NewDatasResponse;
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
import com.mingpinmall.me.ui.constants.Constants;

import static android.app.Activity.RESULT_OK;
import static com.goldze.common.dmvvm.constants.ARouterConfig.SUCCESS;

/**
 * 功能描述：全部实物订单页面
 * @author 小斌
 * @date 2019/3/26
 */
public class PhysicalOrderListFragment extends AbsLifecycleFragment<FragmentDefaultRecyclerviewBinding, MeViewModel> {

    private int pageIndex = 1;
    private String eventType = "";
    private String eventKey = "ALL_PHYSICAL";
    private PhysicalOrderListAdapter physicalOrderListAdapter;
    private boolean isLoadmore = false;

    public PhysicalOrderListFragment() {
    }

    public static PhysicalOrderListFragment newInstance(String type, String eventKey) {
        Bundle bundle = new Bundle();
        bundle.putString("eventType", type);
        bundle.putString("eventKey", eventKey);

        PhysicalOrderListFragment fragment = new PhysicalOrderListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        eventType = args.getString("eventType");
        eventKey = args.getString("eventKey");
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
        emptyView.findViewById(R.id.btn_action).setOnClickListener(v -> {
            //切换到首页
            LiveBus.getDefault().postEvent("Main", "tab", 0);
            activity.onBackPressed();
        });
        physicalOrderListAdapter.setEmptyView(emptyView);
        binding.recyclerView.setAdapter(physicalOrderListAdapter);

        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            isLoadmore = false;
            mViewModel.getPhysicalOrderList(eventKey, eventType, getOrderKey(), 1);
        });
        physicalOrderListAdapter.setOnLoadMoreListener(() -> {
            isLoadmore = true;
            mViewModel.getPhysicalOrderList(eventKey, eventType, getOrderKey(), (pageIndex + 1));
        }, binding.recyclerView);

        setListItemClickListener();
    }

    private void setListItemClickListener() {
        physicalOrderListAdapter.setOnItemClickListener((adapter, view, position) -> {
            //item点击事件
            PhysicalOrderBean orderBean = physicalOrderListAdapter.getItem(position);
            Intent intent = new Intent(activity, PhysicalOrderInformationActivity.class);
            intent.putExtra("orderId", orderBean.getOrder_id());
            startActivityForResult(intent, 1);
        });
        physicalOrderListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            //子控件点击事件
            final PhysicalOrderBean orderBean = physicalOrderListAdapter.getItem(position);
            if (view.getId() == R.id.order_buyer_cancel) {
                //取消订单
                TextDialog.showBaseDialog(activity, "取消订单", "确定取消订单？",
                        () -> mViewModel.cancelOrder(eventKey, orderBean.getOrder_id()))
                        .show();
            } else if (view.getId() == R.id.order_refund_cancel) {
                //订单退款
                ActivityToActivity.toActivity(ARouterConfig.Me.ORDERREFUNDACTIVITY, "id", orderBean.getOrder_id());
            } else if (view.getId() == R.id.order_pay) {
                //立即支付
            } else if (view.getId() == R.id.order_lock) {
                //退款/退货中... tips
            } else if (view.getId() == R.id.order_sure) {
                //确认收货
                TextDialog.showBaseDialog(activity, "确认收货", "确认已收到订单中商品？",
                        () -> {
                            //确认
                            mViewModel.recevieOrder(eventKey, orderBean.getOrder_id());
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
                        () -> {
                            //确认
                            mViewModel.removeOrder(eventKey, orderBean.getOrder_id());
                        })
                        .show();
            } else if (view.getId() == R.id.ll_shopContent) {
                //点击了 店铺 名字，跳转到店铺
                ActivityToActivity.toActivity(ARouterConfig.classify.STOREACTIVITY, "storeId", orderBean.getStore_id());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                lazyLoad();
            }
        }
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        //这个是点击搜索时，当前显示的页面根据搜索条件更新数据
        registerObserver("SEARCH", "ORDER", String.class).observeForever(s -> {
            if (this.isVisibleToUser()) {
                lazyLoad();
            }
        });
        registerObserver(eventKey, Constants.RECEVIE_ORDER, String.class)
                .observeForever(msg -> {
                    //移除订单
                    if (msg.equals(SUCCESS)) {
                        //成功
                        ToastUtils.showShort("移除订单");
                        lazyLoad();
                    } else {
                        //操作失败
                        ToastUtils.showShort(msg);
                    }

                });
        registerObserver(eventKey, Constants.RECEVIE_ORDER, String.class)
                .observeForever(msg -> {
                    //确认收货
                    if (msg.equals(SUCCESS)) {
                        //成功
                        ToastUtils.showShort("已确认收货");
                        lazyLoad();
                    } else {
                        //失败
                        ToastUtils.showShort(msg);
                    }

                });
        registerObserver(eventKey, Object.class)
                .observeForever(result -> {
                    if (result instanceof String) {
                        if (isLoadmore) {
                            physicalOrderListAdapter.loadMoreFail();
                        } else {
                            binding.refreshLayout.finishRefresh(false);
                        }
                        ToastUtils.showShort(result.toString());
                    } else {
                        NewDatasResponse<PhysicalOrderBean> data = (NewDatasResponse<PhysicalOrderBean>) result;
                        if (!isLoadmore) {
                            pageIndex = 1;
                            binding.refreshLayout.finishRefresh();
                            physicalOrderListAdapter.setNewData(data.getData());
                        } else {
                            pageIndex++;
                            physicalOrderListAdapter.loadMoreComplete();
                            physicalOrderListAdapter.addData(data.getData());
                        }
                        if (!data.isHasmore()) {
                            physicalOrderListAdapter.loadMoreEnd();
                        }
                    }
                });
    }

    @Override
    protected void lazyLoad() {
        mViewModel.getPhysicalOrderList(eventKey, eventType, getOrderKey(), 1);
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
