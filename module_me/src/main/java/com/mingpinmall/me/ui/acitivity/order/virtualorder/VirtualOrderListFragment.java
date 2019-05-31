package com.mingpinmall.me.ui.acitivity.order.virtualorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.dialog.TextDialog;
import com.mingpinmall.apppay.UserPaySheet;
import com.mingpinmall.apppay.pay.Context;
import com.mingpinmall.apppay.pay.JPayListener;
import com.mingpinmall.apppay.pay.PayLayoutBean;
import com.mingpinmall.apppay.pay.WeiXinBaoStrategy;
import com.mingpinmall.apppay.pay.ZhiFuBaoStrategy;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentDefaultRecyclerviewBinding;
import com.mingpinmall.me.ui.adapter.VirtualOrderListAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.PayMessageInfo;
import com.mingpinmall.me.ui.bean.VirtualOrderBean;
import com.mingpinmall.me.ui.constants.Constants;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.goldze.common.dmvvm.constants.ARouterConfig.SUCCESS;

/**
 * 功能描述：全部虚拟订单页面
 * @author 小斌
 * @date 2019/3/26
 **/
public class VirtualOrderListFragment extends AbsLifecycleFragment<FragmentDefaultRecyclerviewBinding, MeViewModel> implements JPayListener {

    private int pageIndex = 1;
    private boolean isLoadmore = false;
    private String TYPE = "";
    private String EVENT_KEY = "ALL_VIRTUAL";
    private VirtualOrderListAdapter virtualOrderListAdapter;

    /**
     * -1：未选择
     * 0：充值卡
     * 1：预存款
     * 2：支付宝
     * 3：微信
     */
    private int method = -1;

    private UserPaySheet userPaySheet;

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
            if (view.getId() == R.id.order_buyer_cancel) {
                TextDialog.showBaseDialog(activity, "提示", "确定取消订单？",
                        () -> mViewModel.cancelVirtualOrder(EVENT_KEY, orderListBean.getOrder_id()))
                        .show();
            } else if (view.getId() == R.id.order_pay) {
                // 订单支付
                mViewModel.getPayInfo(orderListBean.getOrder_sn(), EVENT_KEY, Constants.PAY_INFO.toString());
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
        //这个是点击搜索时，当前显示的页面根据搜索条件更新数据
        registerObserver("SEARCH", "ORDER", String.class).observeForever(s -> {
            orderKey = s;
            if (this.isVisibleToUser()) {
                lazyLoad();
            }
        });
        registerObserver(EVENT_KEY, Constants.PAY_INFO.toString(), Object.class).observeForever(result -> {
            if (result instanceof PayLayoutBean) {
                PayLayoutBean data = (PayLayoutBean) result;
                showPaySheet(data.getPay_info());
            }
        });

        registerObserver(EVENT_KEY, Constants.PAY_METHOD, Object.class).observeForever(result -> {
            if (result instanceof PayMessageInfo) {
                PayMessageInfo data = (PayMessageInfo) result;
                switch (method) {
                    case 0:
                        //充值卡支付方式
                        break;
                    case 1:
                        //预存款支付方式
                        break;
                    case 2:
                        //支付宝支付方式
                        KLog.i("支付宝");
                        aLiPay(data.getParam());
                        break;
                    case 3:
                        //微信支付方式
                        weixinPay(data.getPay_code());
                        break;
                    default:
                        break;
                }
            } else {
                userPaySheet.getSheetBuilder().onPayFail(result.toString());
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
        if (userPaySheet != null && userPaySheet.isShowing()) {
            return;
        }
        //每次显示的时候，都取得一次搜索框内容
        LiveBus.getDefault().postEvent("ORDER", "SEARCH", "GET");
    }

    private String orderKey = "";

    private String getOrderKey() {
        return orderKey;
    }

    @Override
    protected Object getStateEventKey() {
        return "VirtualOrderListFragment";
    }

    private void showPaySheet(PayLayoutBean.PayInfoBean payInfo) {
        userPaySheet = new UserPaySheet.PayViewSheetBuilder(activity)
                .setData(payInfo)
                .setmOnPayMethodListener(new UserPaySheet.OnPayMethodListener() {
                    @Override
                    public void onAlipay(UserPaySheet dialog) {
                        //支付宝支付方式
                        dialog.getSheetBuilder().onPaying("");
                        method = 2;
                        mViewModel.getPayInfo2(
                                payInfo.getPay_sn(),
                                "0",
                                "0",
                                "",
                                "alipay_sdk",
                                EVENT_KEY,
                                Constants.PAY_METHOD
                        );
                    }

                    @Override
                    public void onWxpay(UserPaySheet dialog) {
                        //微信支付方式
                        dialog.getSheetBuilder().onPaying("");
                        method = 3;
                        mViewModel.getPayInfo2(
                                payInfo.getPay_sn(),
                                "0",
                                "0",
                                "",
                                "wxpay_sdk",
                                EVENT_KEY,
                                Constants.PAY_METHOD
                        );
                    }

                    @Override
                    public void onPcd(UserPaySheet dialog, int type, String password) {
                        dialog.getSheetBuilder().onPaying("");
                        method = type;
                        mViewModel.getPayInfo2(
                                payInfo.getPay_sn(),
                                type == 0 ? "1" : "0",
                                type == 1 ? "1" : "0",
                                password,
                                "",
                                EVENT_KEY,
                                Constants.PAY_METHOD
                        );
                    }
                }).build();
        userPaySheet.show();
    }

    private void aLiPay(final String param) {
        Context context = new Context(ZhiFuBaoStrategy.getInstance(activity));
        Map<String, String> map = new HashMap<>(1);
        map.put("orderInfo", param);
        context.pay(map, this);
    }

    private void weixinPay(PayMessageInfo.PayCodeBean response) {
        if(response == null) {
            userPaySheet.getSheetBuilder().onPayFail("未知错误");
            return;
        }
        Context context = new Context(WeiXinBaoStrategy.getInstance(activity));
        Map<String, String> map = new HashMap<>(6);
        map.put("appId", response.getAppidX());
        map.put("partnerId", response.getPartnerid());
        map.put("prepayId", response.getPrepayid());
        map.put("nonceStr", response.getNonce_strX());
        map.put("timeStamp", response.getTimestamp());
        map.put("sign", response.getSignX());
        context.pay(map, this);
    }

    @Override
    public void onPaySuccess() {
        //支付成功
        userPaySheet.getSheetBuilder().onPaySuccess("");
    }

    @Override
    public void onPayError(int error_code, String message) {
        //支付失败
        userPaySheet.getSheetBuilder().onPayFail(message);
    }

    @Override
    public void onPayCancel() {
        //取消支付
        userPaySheet.getSheetBuilder().onPayFail("支付已取消");
    }

    @Override
    public void onUUPay(String dataOrg, String sign, String mode) {
        //银联回调
        userPaySheet.getSheetBuilder().onPayFail("");
    }
}
