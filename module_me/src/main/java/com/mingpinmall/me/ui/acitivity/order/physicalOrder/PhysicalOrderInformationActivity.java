package com.mingpinmall.me.ui.acitivity.order.physicalOrder;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.dialog.MaterialDialogUtils;
import com.goldze.common.dmvvm.widget.dialog.TextDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityPhysicalOrderInformationBinding;
import com.mingpinmall.me.ui.adapter.PhysicalOrderInformationListAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.OrderDeliverBean;
import com.mingpinmall.me.ui.bean.OrderInformationBean;
import com.mingpinmall.me.ui.bean.PhysicalOrderBean;

/**
 * 功能描述：订单详情
 * 创建人：小斌
 * 创建时间: 2019/4/16
 **/
@Route(path = ARouterConfig.Me.PHYSICALORDERINFORMATIONACTIVITY)
public class PhysicalOrderInformationActivity extends AbsLifecycleActivity<ActivityPhysicalOrderInformationBinding, MeViewModel> {

    private String orderId;
    private PhysicalOrderInformationListAdapter listAdapter;
    private OrderInformationBean.OrderInfoBean data;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        orderId = getIntent().getStringExtra("orderId");
        setTitle(R.string.title_OrderInformation);
        listAdapter = new PhysicalOrderInformationListAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(listAdapter);

        binding.llShopContent.setOnClickListener(this);
        binding.flCall.setOnClickListener(this);
        binding.flService.setOnClickListener(this);
        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //商品点击事件
                OrderInformationBean.OrderInfoBean.GoodsListBean data = listAdapter.getItem(position);
                ActivityToActivity.toActivity(ARouterConfig.home.SHOPPINGDETAILSACTIVITY, "id", data.getGoods_id());
            }
        });
    }

    @Override
    protected void dataObserver() {
        registerObserver("PHYSICAL_ORDER_INFORMATION", Object.class)
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        if (result instanceof OrderInformationBean) {
                            OrderInformationBean resultData = (OrderInformationBean) result;
                            data = resultData.getOrder_info();
                            showDataInfo();
                        } else {
                            ToastUtils.showShort(result.toString());
                        }
                    }
                });
        registerObserver("INFORMATION_CANCEL_ORDER", "REMOVE_ORDER", String.class)
                .observeForever(new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String msg) {
                        if (msg.equals("success")) {
                            setResult(100);
                            finish();
                        } else {
                            ToastUtils.showShort(msg);
                        }
                    }
                });
        registerObserver("INFORMATION_RECEVIE_ORDER", "RECEVIE_ORDER", String.class)
                .observeForever(new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String msg) {
                        if (msg.equals("success")) {
                            initData();
                        } else {
                            ToastUtils.showShort(msg);
                        }
                    }
                });
        registerObserver("ORDER_DELIVER_INFORMATION", Object.class)
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        if (result instanceof OrderDeliverBean) {
                            OrderDeliverBean data = (OrderDeliverBean) result;
                            binding.setDeliverData(data);
                        } else {
                            ToastUtils.showShort(result.toString());
                        }
                    }
                });
    }

    /**
     * 填充内容
     */
    private void showDataInfo() {
        if (data == null) {
            return;
        }
        /*交易状态*/
        binding.tvSublabel.setText(data.getState_desc());
        binding.tvDescription.setText(data.getOrder_tips());
        binding.tvDescription.setVisibility(data.getOrder_tips().isEmpty() ? View.GONE : View.VISIBLE);
        /*收货人 收货地址*/
        binding.sivItem2.setTitle("收货人：" + data.getReciver_name() + "  " + data.getReciver_phone())
                .setDescription("收货地址：" + data.getReciver_addr());
        /*买家留言*/
        binding.sivItem3.setVisibility(!data.getOrder_message().isEmpty() ? View.VISIBLE : View.GONE);
        binding.sivItem3.setDescription(data.getOrder_message());
        /*发票信息*/
        binding.sivItem4.setVisibility(!data.getInvoice().isEmpty() ? View.VISIBLE : View.GONE);
        binding.sivItem4.setDescription(data.getInvoice());
        /*付款方式*/
        binding.sivItem5.setDescription(data.getPayment_name());

        /*店铺，店铺商品列表*/
        binding.tvStoreName.setText(data.getStore_name());
        listAdapter.setNewData(data.getGoods_list());

        /*赠品*/
        if (data.getZengpin_list() != null) {
            binding.llGifts.setVisibility(View.VISIBLE);
            binding.llGifts.removeAllViews();
            for (int i = 0; i < data.getZengpin_list().size(); i++) {
                OrderInformationBean.OrderInfoBean.ZengpinListBean giftListBean = data.getZengpin_list().get(i);
                View view = View.inflate(activity, R.layout.item_tips_textview_14sp, null);
                TextView textView = view.findViewById(R.id.tv_label);
                textView.setText(giftListBean.getGoods_name() + "    x" + giftListBean.getGoods_num());
                binding.llGifts.addView(view);
                if (i < data.getZengpin_list().size() - 1) {
                    View line = new View(activity);
                    line.setBackgroundResource(R.color.line_color);
                    binding.llGifts.addView(line, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                }
            }
        } else {
            binding.llGifts.setVisibility(View.GONE);
        }

        /*优惠，运费，实付款*/
        binding.llYouhui.setVisibility(!data.getPromotion().isEmpty() ? View.VISIBLE : View.GONE);
        binding.tvYouhui.setText(data.getPromotion().size() > 0 ? data.getPromotion().get(0).get(1) : "");
        binding.tvYunfei.setText(data.getShipping_fee());
        binding.tvPayMoney.setText(data.getReal_pay_amount());

        /*订单编号*/
        binding.tvOrderNum.setText(String.format("%s%s", getString(R.string.text_phsi_10), data.getOrder_sn()));
        /*创建时间*/
        binding.tvOrderCreateTime.setText(String.format("%s%s", getString(R.string.text_phsi_11), data.getAdd_time()));
        /*付款时间*/
        binding.tvOrderPayTime.setVisibility(!data.getPayment_time().isEmpty() ? View.VISIBLE : View.GONE);
        binding.tvOrderPayTime.setText(String.format("%s%s", getString(R.string.text_phsi_12), data.getPayment_time()));
        /*发货时间*/
        binding.tvOrderSendTime.setVisibility(!data.getShipping_time().isEmpty() ? View.VISIBLE : View.GONE);
        binding.tvOrderSendTime.setText(String.format("%s%s", getString(R.string.text_phsi_13), data.getShipping_time()));

        /*最下面的按钮*/
        LinearLayout buttonContent = binding.llButtonContent;
        buttonContent.removeAllViews();
        AppCompatTextView tvBtn = null;
        if (data.isIf_evaluation_again()) {
            //追加评价
            tvBtn = (AppCompatTextView) View.inflate(activity, R.layout.button_layout_border_red, null);
            tvBtn.setId(R.id.order_evaluation_again);
            tvBtn.setText("追加评价");
        }
        if (data.isIf_evaluation()) {
            //评价订单
            tvBtn = (AppCompatTextView) View.inflate(activity, R.layout.button_layout_border_red, null);
            tvBtn.setId(R.id.order_evaluation);
            tvBtn.setText("评价订单");
        }
        if (data.isIf_receive()) {
            //确认收货
            tvBtn = (AppCompatTextView) View.inflate(activity, R.layout.button_layout_border_red, null);
            tvBtn.setId(R.id.order_sure);
            tvBtn.setText("确认收货");
        }
        if (data.isIf_buyer_cancel()) {
            //取消订单
            tvBtn = (AppCompatTextView) View.inflate(activity, R.layout.button_layout_default, null);
            tvBtn.setId(R.id.order_cancel);
            tvBtn.setText("取消订单");
        }
        if (data.isIf_refund_cancel()) {
            //订单退款
            tvBtn = (AppCompatTextView) View.inflate(activity, R.layout.button_layout_default, null);
            tvBtn.setId(R.id.order_lock);
            tvBtn.setText("订单退款");
        }

        if (tvBtn != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = ScreenUtil.dip2px(activity, 6);
            buttonContent.addView(tvBtn);
            tvBtn.setLayoutParams(params);
            tvBtn.setOnClickListener(this);
        }
        binding.tvTips.setVisibility(data.isIf_lock() ? View.VISIBLE : View.GONE);

        //物流信息是否存在
        if (data.isIf_deliver()) {
            mViewModel.getOrderDeliverInformation(data.getOrder_id());
            binding.clExpressInformation.setOnClickListener(this);
        }
        binding.clExpressInformation.setVisibility(data.isIf_deliver() ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void initData() {
        //getOrderInformation
        mViewModel.getOrderInformation(orderId);
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.bt_cancelOrder) {
            //取消订单
            if (data == null) {
                return;
            }
            TextDialog.showBaseDialog(activity, "提示", "确定取消订单？",
                    new TextDialog.SingleButtonCallback() {
                        @Override
                        public void onClick() {
                            mViewModel.cancelOrder("INFORMATION_CANCEL_ORDER", data.getOrder_id());
                        }
                    })
                    .show();
        } else if (viewId == R.id.order_pay) {
            //立即支付
        } else if (viewId == R.id.order_lock) {
            //订单退款
        } else if (viewId == R.id.order_sure) {
            //确认收货
            TextDialog.showBaseDialog(activity, "确认收货", "确认已收到订单中商品？",
                    new TextDialog.SingleButtonCallback() {
                        @Override
                        public void onClick() {
                            mViewModel.recevieOrder("INFORMATION_RECEVIE_ORDER", data.getOrder_id());
                        }
                    })
                    .show();
        } else if (viewId == R.id.order_evaluation) {
            //订单评价
            ActivityToActivity.toActivity(ARouterConfig.Me.ORDEREVALUATEACTIVITY, "id", data.getOrder_id());
        } else if (viewId == R.id.order_evaluation_again) {
            //追加评价
        } else if (viewId == R.id.cl_expressInformation) {
            //查看物流
            ActivityToActivity.toActivity(ARouterConfig.Me.ORDERDELIVERYACTIVITY, "order_id", data.getOrder_id());
        } else if (viewId == R.id.ll_shopContent) {
            //点击店铺
            if (data != null) {
                ActivityToActivity.toActivity(ARouterConfig.classify.STOREACTIVITY, "storeId", data.getStore_id());
            }
        } else if (viewId == R.id.fl_call) {
            //拨打电话
            if (data == null || data.getStore_phone().isEmpty()) {
                return;
            }
            callPhone(data.getStore_phone());
        } else if (viewId == R.id.fl_service) {
            //联系客服
        }

    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    private void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    protected Object getStateEventKey() {
        return "PhysicalOrderInformationActivity";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_physical_order_information;
    }
}
