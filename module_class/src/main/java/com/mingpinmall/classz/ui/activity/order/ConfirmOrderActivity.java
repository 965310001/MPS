package com.mingpinmall.classz.ui.activity.order;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.bean.AddressDataBean;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.utils.log.QLog;
import com.goldze.common.dmvvm.widget.dialog.MaterialDialogUtils;
import com.mingpinmall.apppay.pay.PayLayoutBean;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.databinding.ActivityConfirmOrderBinding;
import com.mingpinmall.apppay.pay.Context;
import com.mingpinmall.apppay.pay.JPayListener;
import com.mingpinmall.apppay.pay.WeiXinBaoStrategy;
import com.mingpinmall.apppay.pay.ZhiFuBaoStrategy;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.BuyStepInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.mingpinmall.classz.ui.vm.bean.InvoiceListInfo;
import com.mingpinmall.classz.ui.vm.bean.OrderInfo;
import com.mingpinmall.classz.ui.vm.bean.PayMessageInfo;
import com.mingpinmall.classz.widget.PayPopupWindow;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车传
 * ifcart="1";
 * cartId="80|1";
 */

/**
 * 提交订单
 */
@Route(path = ARouterConfig.classify.CONFIRMORDERACTIVITY, extras = ARouterConfig.LOGIN_NEEDED)
public class ConfirmOrderActivity extends
        AbsLifecycleActivity<ActivityConfirmOrderBinding, ClassifyViewModel> implements MaterialDialog.ListCallbackSingleChoice, JPayListener {

    @Autowired
    String id;

    @Autowired
    String cartId;

    @Autowired
    String ifcart;/*是否是购物车*/

    /*地址id  是否选择发票*/
    private String addressId, invoice_id = "", mVatHash, mOffpayHash, mOffpayHashBatch;

    private List<PayLayoutBean.PayInfoBean.PaymentListBean> mPaymentList;

    private int mPayFun = -1;

    private PayPopupWindow mPayPopupWindow;

    private MaterialDialog.Builder paymentDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_order;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);
        setTitle("提交订单");
        setTitlePadding(baseBinding.rlTitleContent);
        binding.setContent("");
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.CONFIRMORDER_KEY[1];
    }

    @Override
    protected void initData() {
        super.initData();
        QLog.i(cartId);
        mViewModel.getOrderInfo(cartId, addressId, ifcart, Constants.CONFIRMORDER_KEY[0]);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();

        registerObserver(Constants.CONFIRMORDER_KEY[0], BaseResponse.class)
                .observeForever(response -> {
                    BaseResponse<OrderInfo> data = response;
                    if (data.isData()) {
                        QLog.i(data.getData().toString());
                        if (data.isSuccess()) {
                            try {
                                binding.setAddress(data.getData().getAddress_info());
                                binding.setTotal(data.getData().getOrder_amount());
                                List<GoodsInfo> goods_list = new ArrayList<>();
                                String name = "";
                                for (OrderInfo.StoreCartListBean storeCartListBean : data.getData().getStore_cart_list()) {
                                    for (GoodsInfo goodsInfo : storeCartListBean.getGoods_list()) {
                                        if (!name.equals(storeCartListBean.getStore_name())) {
                                            name = storeCartListBean.getStore_name();
                                            goodsInfo.setStoreName(true);
                                        }
                                        goods_list.add(goodsInfo);
                                    }
                                }
                                addressId = data.getData().getAddress_info()
                                        .getAddress_id();
                                OrderInfo.AddressApiBean addressApi = data.getData().getAddress_api();
                                mVatHash = data.getData().getVat_hash();
                                mOffpayHash = addressApi.getOffpay_hash();
                                mOffpayHashBatch = addressApi.getOffpay_hash_batch();
                                binding.setData(goods_list);
                                binding.setAdapter(AdapterPool.newInstance().getConfirmOrder(activity)
                                        .build());
                                binding.setPayment("在线付款");
                                binding.setInvoice(data.getData().getInv_info().getContent());
                                binding.executePendingBindings();
                            } catch (Exception e) {
                                QLog.i(e.toString());
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
                        if (null == mPayPopupWindow) {
                            mPayPopupWindow = new PayPopupWindow.Builder(activity)
                                    .setData(data.getData())
                                    .build().createPop();
                        }
                        mPayPopupWindow.showAsDropDown(baseBinding.rlTitleContent);

                        BuyStepInfo stepInfo = data.getData();
                        mPaySn = stepInfo.getPay_sn();
                        mPaymentCode = stepInfo.getPayment_code();
                        /*QLog.i(data.getData().getPay_sn());*/
                        PayLayoutBean.PayInfoBean payInfo = stepInfo.getPay_info();
                        if (null != payInfo) {
                            mPaymentList = payInfo.getPayment_list();
                        }
                    } else {
                        ToastUtils.showLong(data.getMessage());
                    }
                });

        registerObserver(Constants.CONFIRMORDER_KEY[3] + "Success", PayMessageInfo.class)
                .observe(this, new Observer<PayMessageInfo>() {
                    @Override
                    public void onChanged(@Nullable PayMessageInfo response) {
                        QLog.i(response);
                        switch (mPayFun) {
                            case 0:
                                aLiPay(response.getParam());
                                break;
                            case 1:
                                weixinPay(response.getPay_code());
                                break;
                        }
                    }
                });
    }

    private void aLiPay(final String param) {
        Context context = new Context(ZhiFuBaoStrategy.getInstance(this));
        Map<String, String> map = new HashMap<>();
        map.put("orderInfo", param);
        context.pay(map, this);
    }

    private void weixinPay(PayMessageInfo.PayCodeBean response) {
        Context context = new Context(WeiXinBaoStrategy.getInstance(this));
        Map<String, String> map = new HashMap<>();
        map.put("appId", response.getAppidX());
        map.put("partnerId", response.getPartnerid());
        map.put("prepayId", response.getPrepayid());
        map.put("nonceStr", response.getNonce_strX());
        map.put("timeStamp", response.getTimestamp());
        map.put("sign", response.getSignX());
        context.pay(map, this);
    }

//    public void merchant(View view) {
//        // TODO: 2019/4/11 商家界面
//    }

    public void paymentMethod(View view) {
        QLog.i("支付方式");
        if (null == paymentDialog) {
            paymentDialog = MaterialDialogUtils.showSingleListDialog(this, "支付方式",
                    Arrays.asList("在线支付", "支付宝", "微信"), this);
        }
        paymentDialog.show();
    }


    /*阿里*/
    public void aLiPay(View view) {
        mPayFun = 0;
        mPaymentCode = getPaymentCode("支付宝app");
    }

    /*微信*/
    public void weixinPay(View view) {
        mPayFun = 1;
        mPaymentCode = getPaymentCode("微信app");
    }

    private String getPaymentCode(String name) {
        if (null != mPaymentList && mPaymentList.size() > 0) {
            for (PayLayoutBean.PayInfoBean.PaymentListBean paymentListBean : mPaymentList) {
                if (paymentListBean.getPayment_name().contains(name)) {
                    return paymentListBean.getPayment_code();
                }
            }
        }
        return "";
    }

    private String mPaySn, mPaymentCode;

    /*确认订单*/
    public void btnOkClick(View view) {
        switch (mPayFun) {
            case 0:
            case 1:
                mViewModel.getPayNew(mPaySn, mPaymentCode, String.valueOf(mPayFun), Constants.CONFIRMORDER_KEY[3]);
                mPayPopupWindow.dismiss();
                break;
            default:
                ToastUtils.showLong("请选择支付方式");
                break;
        }
    }

    public void invoiceInfo(View view) {
        ARouter.getInstance().build(ARouterConfig.classify.INVOICEACTIVITY)
                .navigation(this,
                        400);

    }

    public void sublimit(View view) {
        String payment = binding.getPayment();
        QLog.i("提交订单" + binding.getContent() + payment);
        Map<String, Object> map = new HashMap<>();

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
        map.put("pay_message", binding.getContent());//store_id+binding.getContent()|store_id+binding.getContent()
        mViewModel.getBuyStep2(map, Constants.CONFIRMORDER_KEY[2]);
    }

    public void onFinishClick(View view) {
        if (mPayPopupWindow.isShowing()) {
            mPayPopupWindow.dismiss();
        }
    }

    public void selectAddress(View view) {
        ARouter.getInstance().build(ARouterConfig.Me.ADDRESSMANAGERACTIVITY)
                .withBoolean("isGetAddress", true).navigation(this,
                100);
    }

    @Override
    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
        QLog.i("支付方式" + text);
        binding.setPayment(text.toString());
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == requestCode && requestCode == 100) {/*选择地址*/
            AddressDataBean.AddressListBean data = (AddressDataBean.AddressListBean) intent.getSerializableExtra("addressData");
            binding.setAddress(data);
            addressId = data.getAddress_id();
        } else if (resultCode == requestCode && requestCode == 400) {/*选择发票*/
            InvoiceListInfo.InvoiceListBean bean = (InvoiceListInfo.InvoiceListBean) intent.getSerializableExtra("invoicelistbean");
            invoice_id = bean.getInv_idX();
            binding.setInvoice(String.format("%s %s", bean.getInv_title(), bean.getInv_content()));
        }

    }

    @Override
    public void onPaySuccess() {
        ToastUtils.showLong("支付成功");
    }

    @Override
    public void onPayError(int error_code, String message) {
        ToastUtils.showLong(error_code + " " + message);
    }

    @Override
    public void onPayCancel() {
        QLog.i("支付取消");
        ToastUtils.showLong("支付取消");
    }

    @Override
    public void onUUPay(String dataOrg, String sign, String mode) {

    }
}