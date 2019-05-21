package com.mingpinmall.classz.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.base.bean.AddressDataBean;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.apppay.UserPaySheet;
import com.mingpinmall.apppay.pay.Context;
import com.mingpinmall.apppay.pay.JPayListener;
import com.mingpinmall.apppay.pay.PayLayoutBean;
import com.mingpinmall.apppay.pay.WeiXinBaoStrategy;
import com.mingpinmall.apppay.pay.ZhiFuBaoStrategy;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.adapter.ConfirmOrderAdapter;
import com.mingpinmall.classz.databinding.ActivityConfirmOrder2Binding;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.BuyStepInfo;
import com.mingpinmall.classz.ui.vm.bean.ConfirmOrderBean;
import com.mingpinmall.classz.ui.vm.bean.InvoiceListInfo;
import com.mingpinmall.classz.ui.vm.bean.PayMessageInfo;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

/**
 * 提交订单
 *
 * @author 小斌
 */
@Route(path = ARouterConfig.classify.CONFIRMORDERACTIVITY2, extras = ARouterConfig.LOGIN_NEEDED)
public class ConfirmOrderActivity2 extends AbsLifecycleActivity<ActivityConfirmOrder2Binding, ClassifyViewModel>
        implements MaterialDialog.ListCallbackSingleChoice, JPayListener {

    @Autowired
    String id;

    @Autowired
    String cartId;

    /**
     * 是否是购物车
     */
    @Autowired
    String ifcart;

    private ConfirmOrderAdapter adapter;

    /*地址id  是否选择发票*/
    private String addressId, invoice_id = "", mVatHash, mOffpayHash, mOffpayHashBatch;

    /**
     * 支付窗口
     */
    private UserPaySheet userPaySheet;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_order2;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);
        setTitle("提交订单");
        setTitlePadding(baseBinding.rlTitleContent);

        adapter = new ConfirmOrderAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerView.setAdapter(adapter);

    }

    @Override
    protected Object getStateEventKey() {
        return Constants.CONFIRMORDER_KEY[1];
    }

    @Override
    protected void initData() {
        super.initData();
        KLog.i(cartId);
        mViewModel.getOrderInfo2(cartId, addressId, ifcart, Constants.CONFIRMORDER_KEY[0]);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();

        registerObserver(Constants.CONFIRMORDER_KEY[0], BaseResponse.class)
                .observeForever(response -> {
                    BaseResponse<ConfirmOrderBean> data = response;
                    if (data.isData()) {
                        /*KLog.i(data.getData().toString());*/
                        if (data.isSuccess()) {
                            try {
                                binding.setAddress(data.getData().getAddress_info());
                                binding.setTotal(data.getData().getOrder_amount());
                                adapter.setNewData(data.getData().getStore_cart_list_news());
                                addressId = data.getData().getAddress_info()
                                        .getAddress_id();
                                ConfirmOrderBean.AddressApiBean addressApi = data.getData().getAddress_api();
                                mVatHash = data.getData().getVat_hash();
                                mOffpayHash = addressApi.getOffpay_hash();
                                mOffpayHashBatch = addressApi.getOffpay_hash_batch();
                                binding.setPayment("在线付款");
                                binding.setInvoice(data.getData().getInv_info().getContent());
                                binding.executePendingBindings();
                            } catch (Exception e) {
                                KLog.i(e.toString());
                            }
                        } else {
                            ToastUtils.showLong(data.getMessage());
                        }
                    }
                });

        /*支付sn的返回*/
        registerObserver(Constants.CONFIRMORDER_KEY[2], BaseResponse.class)
                .observe(this, response -> {
                    BaseResponse<BuyStepInfo> data = response;
                    if (data.isSuccess()) {
                        showPaySheet(data.getData().getPay_info());
                    } else {
                        ToastUtils.showLong(data.getMessage());
                    }
                });

        registerObserver(Constants.CONFIRMORDER_KEY[3], Object.class)
                .observe(this, response -> {
                    if (response instanceof PayMessageInfo) {
                        PayMessageInfo data = (PayMessageInfo) response;
                        KLog.i(response);
                        switch (method) {
                            case 0:
                                break;
                            case 1:
                                break;
                            case 2:
                                aLiPay(data.getParam());
                                break;
                            case 3:
                                weixinPay(data.getPay_code());
                                break;
                            default:
                                break;
                        }
                    } else {
                        userPaySheet.getSheetBuilder().onPayFail(response.toString());
                    }
                });

    }

    private void aLiPay(final String param) {
        Context context = new Context(ZhiFuBaoStrategy.getInstance(this));
        Map<String, String> map = new HashMap<>(1);
        map.put("orderInfo", param);
        context.pay(map, this);
    }

    private void weixinPay(PayMessageInfo.PayCodeBean response) {
        Context context = new Context(WeiXinBaoStrategy.getInstance(this));
        Map<String, String> map = new HashMap<>(6);
        map.put("appId", response.getAppidX());
        map.put("partnerId", response.getPartnerid());
        map.put("prepayId", response.getPrepayid());
        map.put("nonceStr", response.getNonce_strX());
        map.put("timeStamp", response.getTimestamp());
        map.put("sign", response.getSignX());
        context.pay(map, this);
    }

    public void merchant(View view) {
        // TODO: 2019/4/11 商家界面
    }

    public void invoiceInfo(View view) {
        ARouter.getInstance().build(ARouterConfig.classify.INVOICEACTIVITY)
                .navigation(this,
                        400);

    }

    public void sublimit(View view) {
        Map<String, Object> map = new HashMap<>(10);
        if (!TextUtils.isEmpty(ifcart)) {
            map.put("ifcart", ifcart);
        }
        map.put("cart_id", cartId);
        map.put("address_id", addressId);
        map.put("vat_hash", mVatHash);
        map.put("offpay_hash", mOffpayHash);
        map.put("offpay_hash_batch", mOffpayHashBatch);
        map.put("pay_name", "online");
        map.put("invoice_id", invoice_id);
        map.put("rpt", "");
        BaseViewHolder item;
        ConfirmOrderBean.StoreCartListNewsBean itemData;
        StringBuilder payMsg = new StringBuilder();
        for (int i = 0; i < adapter.getItemCount(); i++) {
            item = (BaseViewHolder) binding.recyclerView.findViewHolderForAdapterPosition(i);
            itemData = adapter.getItem(i);
            payMsg.append(itemData.getStore_id()).append("|").append(((AppCompatEditText) item.getView(R.id.ed_message)).getText().toString()).append(",");
        }
        map.put("pay_message", payMsg.substring(0, payMsg.lastIndexOf(",")));
        mViewModel.getBuyStep2(map, Constants.CONFIRMORDER_KEY[2]);
    }

    public void selectAddress(View view) {
        ARouter.getInstance().build(ARouterConfig.Me.ADDRESSMANAGERACTIVITY)
                .withBoolean("isGetAddress", true).navigation(this,
                100);
    }

    @Override
    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
        KLog.i("支付方式" + text);
        binding.setPayment(text.toString());
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == requestCode && requestCode == 100) {
            /*选择地址*/
            AddressDataBean.AddressListBean data = (AddressDataBean.AddressListBean) intent.getSerializableExtra("addressData");
            binding.setAddress(data);
            addressId = data.getAddress_id();
        } else if (resultCode == requestCode && requestCode == 400) {
            /*选择发票*/
            InvoiceListInfo.InvoiceListBean bean = (InvoiceListInfo.InvoiceListBean) intent.getSerializableExtra("invoicelistbean");
            invoice_id = bean.getInv_idX();
            binding.setInvoice(String.format("%s %s", bean.getInv_title(), bean.getInv_content()));
        }

    }

    @Override
    public void onPaySuccess() {
        userPaySheet.getSheetBuilder().onPaySuccess("");
        ActivityToActivity.toActivity(ARouterConfig.Me.ORDERACTIVITY);
        finish();
    }

    @Override
    public void onPayError(int error_code, String message) {
        userPaySheet.getSheetBuilder().onPayFail(message);
        /*finish();*/
    }

    @Override
    public void onPayCancel() {
        userPaySheet.getSheetBuilder().onPayFail("支付已取消");
        /*finish();*/
    }

    @Override
    public void onUUPay(String dataOrg, String sign, String mode) {
        userPaySheet.getSheetBuilder().onPayFail("");
        /*finish();*/
    }

    /**
     * -1：未选择
     * 0：充值卡
     * 1：预存款
     * 2：支付宝
     * 3：微信
     */
    private int method = -1;

    private void showPaySheet(PayLayoutBean.PayInfoBean payInfo) {
        userPaySheet = new UserPaySheet.PayViewSheetBuilder(activity)
                .setData(payInfo)
                .setmOnPayMethodListener(new UserPaySheet.OnPayMethodListener() {
                    @Override
                    public void onAlipay(UserPaySheet dialog) {
                        //支付宝支付方式
                        dialog.getSheetBuilder().onPaying("");
                        method = 2;
                        mViewModel.getPayInfoNew(
                                payInfo.getPay_sn(),
                                "0",
                                "0",
                                "",
                                "alipay_sdk",
                                Constants.CONFIRMORDER_KEY[3]
                        );
                    }

                    @Override
                    public void onWxpay(UserPaySheet dialog) {
                        //微信支付方式
                        dialog.getSheetBuilder().onPaying("");
                        method = 3;
                        mViewModel.getPayInfoNew(
                                payInfo.getPay_sn(),
                                "0",
                                "0",
                                "",
                                "wxpay_sdk",
                                Constants.CONFIRMORDER_KEY[3]
                        );
                    }

                    @Override
                    public void onPcd(UserPaySheet dialog, int type, String password) {
                        dialog.getSheetBuilder().onPaying("");
                        method = type;
                        mViewModel.getPayInfoNew(
                                payInfo.getPay_sn(),
                                type == 0 ? "1" : "0",
                                type == 1 ? "1" : "0",
                                password,
                                "",
                                Constants.CONFIRMORDER_KEY[3]
                        );
                    }
                }).build();
        userPaySheet.show();
    }
}