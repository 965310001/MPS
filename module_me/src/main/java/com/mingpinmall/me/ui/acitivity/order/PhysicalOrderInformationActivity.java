package com.mingpinmall.me.ui.acitivity.order;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Route;
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
import com.mingpinmall.me.ui.bean.OrderInformationBean;

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

        binding.btCancelOrder.setOnClickListener(this);
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
        registerObserver("ORDER_INFORMATION", "success", OrderInformationBean.class)
                .observeForever(new Observer<OrderInformationBean>() {
                    @Override
                    public void onChanged(@Nullable OrderInformationBean orderInformationBean) {
                        data = orderInformationBean.getOrder_info();
                        showDataInfo();
                    }
                });
        registerObserver("ORDER_INFORMATION", "err", String.class)
                .observeForever(new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        ToastUtils.showShort(s);
                    }
                });
        registerObserver("PL_INFORMATION", "REFRESH_ORDER_LIST", OrderInformationBean.class)
                .observeForever(new Observer<OrderInformationBean>() {
                    @Override
                    public void onChanged(@Nullable OrderInformationBean orderInformationBean) {
                        setResult(100);
                        finish();
                    }
                });
        registerObserver("PL_INFORMATION", "DO_SOMETHING_ERR", String.class)
                .observeForever(new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        ToastUtils.showShort(s);
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
        binding.sivItem3.setDescription(data.getOrder_message());
        /*发票信息*/
        binding.sivItem4.setDescription(data.getInvoice());
        /*付款方式*/
        binding.sivItem5.setDescription(data.getPayment_name());

        /*店铺，店铺商品列表*/
        binding.tvStoreName.setText(data.getStore_name());
        listAdapter.setNewData(data.getGoods_list());

        /*优惠，运费，实付款*/
        binding.tvYouhui.setText(data.getPromotion().size() > 0 ? data.getPromotion().get(0).get(1) : "");
        binding.tvYunfei.setText(data.getShipping_fee());
        binding.tvPayMoney.setText(data.getReal_pay_amount());

        /*订单编号*/
        binding.tvOrderNum.setText("订单编号：" + data.getOrder_sn());
        /*创建时间*/
        binding.tvOrderCreateTime.setText("创建时间：" + data.getAdd_time());

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

                            mViewModel.cancelOrder("INFORMATION", data.getOrder_id());
                        }
                    })
                    .show();
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
