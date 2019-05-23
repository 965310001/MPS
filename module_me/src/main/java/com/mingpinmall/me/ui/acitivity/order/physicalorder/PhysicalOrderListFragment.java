package com.mingpinmall.me.ui.acitivity.order.physicalorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.goldze.common.dmvvm.base.bean.NewDatasResponse;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
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
import com.mingpinmall.me.ui.adapter.PhysicalOrderListAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.PayMessageInfo;
import com.mingpinmall.me.ui.bean.PhysicalOrderBean;
import com.mingpinmall.me.ui.constants.Constants;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.goldze.common.dmvvm.constants.ARouterConfig.SUCCESS;

/**
 * 功能描述：全部实物订单页面
 *
 * @author 小斌
 * @date 2019/3/26
 */
public class PhysicalOrderListFragment extends AbsLifecycleFragment<FragmentDefaultRecyclerviewBinding, MeViewModel> implements JPayListener {

    private int pageIndex = 1;
    private String eventType = "";
    private String eventKey = "ALL_PHYSICAL";
    private PhysicalOrderListAdapter physicalOrderListAdapter;
    private boolean isLoadmore = false;
    /**
     * -1：未选择
     * 0：充值卡
     * 1：预存款
     * 2：支付宝
     * 3：微信
     */
    private int method = -1;

    private UserPaySheet userPaySheet;

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
                mViewModel.getPayInfo(orderBean.getPay_sn(), eventKey, Constants.PAY_INFO.toString());
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
                // TODO 追加评价

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
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(eventKey, Constants.PAY_INFO.toString(), Object.class).observeForever(result -> {
            if (result instanceof PayLayoutBean) {
                PayLayoutBean data = (PayLayoutBean) result;
                showPaySheet(data.getPay_info());
            }
        });
        registerObserver(eventKey, Constants.PAY_METHOD, Object.class).observeForever(result -> {
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
        //这个是点击搜索时，当前显示的页面根据搜索条件更新数据
        registerObserver("SEARCH", "ORDER", String.class).observeForever(s -> {
            if (this.isVisibleToUser()) {
                lazyLoad();
            }
        });
        registerObserver(eventKey, Constants.REMOVE_ORDER, String.class)
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
        registerObserver(eventKey, Constants.CANCEL_ORDER, String.class)
                .observeForever(msg -> {
                    //移除订单
                    if (msg.equals(SUCCESS)) {
                        //成功
                        ToastUtils.showShort("订单已取消");
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
                                eventKey,
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
                                eventKey,
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
                                eventKey,
                                Constants.PAY_METHOD
                        );
                    }
                }).build();
        userPaySheet.show();
    }

    @Override
    protected void lazyLoad() {
        mViewModel.getPhysicalOrderList(eventKey, eventType, getOrderKey(), 1);
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        if (userPaySheet != null && userPaySheet.isShowing()) {
            return;
        }
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
