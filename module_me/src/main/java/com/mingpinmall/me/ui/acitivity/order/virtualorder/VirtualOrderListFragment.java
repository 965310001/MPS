package com.mingpinmall.me.ui.acitivity.order.virtualorder;

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
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.dialog.TextDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentDefaultRecyclerviewBinding;
import com.mingpinmall.me.ui.adapter.VirtualOrderListAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.VirtualOrderBean;
import com.mingpinmall.me.ui.constants.Constants;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import static android.app.Activity.RESULT_OK;
import static com.goldze.common.dmvvm.constants.ARouterConfig.SUCCESS;

/**
 * 功能描述：全部虚拟订单页面
 * @author 小斌
 * @date 2019/3/26
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
        View emptyView = View.inflate(activity, R.layout.layout_state_view, null);
        ((AppCompatImageView) emptyView.findViewById(R.id.iv_image)).setImageResource(R.drawable.ic_order_empty_white);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_title)).setText(R.string.text_title_order_empty);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_sub_title)).setText(R.string.text_sub_title_order_empty);
        emptyView.findViewById(R.id.btn_action).setOnClickListener(v -> {
            //切换到首页
            LiveBus.getDefault().postEvent("Main", "tab", 0);
            activity.onBackPressed();
        });
        virtualOrderListAdapter.setEmptyView(emptyView);
        binding.recyclerView.setAdapter(virtualOrderListAdapter);

        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            isLoadmore = false;
            mViewModel.getVirtualOrderList(EVENT_KEY, TYPE, getOrderKey(), 1);
        });
        virtualOrderListAdapter.setOnLoadMoreListener(() -> {
            isLoadmore = true;
            mViewModel.getVirtualOrderList(EVENT_KEY, TYPE, getOrderKey(), (pageIndex + 1));
        }, binding.recyclerView);
        setListItemClickListener();
    }

    private void setListItemClickListener() {
        virtualOrderListAdapter.setOnItemClickListener((adapter, view, position) -> {
            //item点击事件
            VirtualOrderBean.OrderListBean listBean = virtualOrderListAdapter.getItem(position);
            Intent intent = new Intent(activity, VirtualOrderInformationActivity.class);
            intent.putExtra("orderId", listBean.getOrder_id());
            startActivityForResult(intent, 1);
        });
        virtualOrderListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            //子控件点击事件
            final VirtualOrderBean.OrderListBean orderListBean = virtualOrderListAdapter.getItem(position);
            if (view.getId() == R.id.bt_cancelOrder) {
                TextDialog.showBaseDialog(activity, "提示", "确定取消订单？",
                        () -> mViewModel.cancelVirtualOrder(EVENT_KEY, orderListBean.getOrder_id()))
                        .show();
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
        registerObserver(EVENT_KEY, Constants.RECEVIE_ORDER, String.class).observeForever(msg -> {
            if (msg.equals(SUCCESS)) {
                //操作成功后刷新列表
                lazyLoad();
            } else {
                //操作失败
                ToastUtils.showShort(msg);
            }
        });
        registerObserver(EVENT_KEY, Object.class).observeForever(result -> {
            if (result instanceof String) {
                ToastUtils.showShort(result.toString());
                if (!isLoadmore) {
                    binding.refreshLayout.finishRefresh(false);
                } else {
                    virtualOrderListAdapter.loadMoreFail();
                }
            } else {
                BaseResponse<VirtualOrderBean> data = (BaseResponse<VirtualOrderBean>) result;
                if (!isLoadmore) {
                    pageIndex = 1;
                    binding.refreshLayout.finishRefresh();
                    virtualOrderListAdapter.setNewData(data.getData().getOrder_list());
                } else {
                    pageIndex++;
                    virtualOrderListAdapter.loadMoreComplete();
                    virtualOrderListAdapter.addData(data.getData().getOrder_list());
                }
                if (!data.isHasmore()) {
                    virtualOrderListAdapter.loadMoreEnd();
                }
            }
        });
    }

    @Override
    protected void lazyLoad() {
        mViewModel.getVirtualOrderList(EVENT_KEY, TYPE, getOrderKey(), 1);
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
        return "VirtualOrderListFragment";
    }
}