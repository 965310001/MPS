package com.mingpinmall.classz.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

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
import com.goldze.common.dmvvm.utils.log.QLog;
import com.goldze.common.dmvvm.widget.loading.CustomProgressDialog;
import com.mingpinmall.apppay.UserPaySheet;
import com.mingpinmall.apppay.pay.Context;
import com.mingpinmall.apppay.pay.JPayListener;
import com.mingpinmall.apppay.pay.PayLayoutBean;
import com.mingpinmall.apppay.pay.WeiXinBaoStrategy;
import com.mingpinmall.apppay.pay.ZhiFuBaoStrategy;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.adapter.ConfirmOrderAdapter;
import com.mingpinmall.classz.databinding.ActivityConfirmOrder2Binding;
import com.mingpinmall.classz.ui.activity.fcode.FCodeDialog;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.BuyStepInfo;
import com.mingpinmall.classz.ui.vm.bean.ConfirmOrderBean;
import com.mingpinmall.classz.ui.vm.bean.InvoiceListInfo;
import com.mingpinmall.classz.ui.vm.bean.PayMessageInfo;

import java.util.HashMap;
import java.util.List;
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

    private List<ConfirmOrderBean.StoreCartListNewsBean> mStoreCartListNews;
    /**
     * 是否是购物车
     */
    @Autowired
    String ifcart;

    private FCodeDialog fCodeDialog;

    private ConfirmOrderAdapter adapter;

    /*地址id  是否选择发票*/
    private String addressId, invoice_id = "", mVatHash, mOffpayHash, mOffpayHashBatch;

    private String mPySn;

    //紅包Id   private String rpacketTId;private Double rpacketPrice;

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
        setTitle("确认订单");
        setTitlePadding(baseBinding.rlTitleContent);

        adapter = new ConfirmOrderAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            int id = view.getId();
            if (id == R.id.ll_shopContent) {
                QLog.i("商家");
                ConfirmOrderBean.StoreCartListNewsBean newsBean = (ConfirmOrderBean.StoreCartListNewsBean) adapter.getItem(position);
                ActivityToActivity.toActivity(ARouterConfig.classify.STOREACTIVITY, "storeId", newsBean.getStore_id());
            }
        });
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.CONFIRMORDER_KEY[1];
    }

    @Override
    protected void initData() {
        super.initData();
        showLoading();
        mViewModel.getOrderInfo2(cartId, addressId, ifcart, Constants.CONFIRMORDER_KEY[0]);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.CONFIRMORDER_KEY[4], String.class).observeForever(result -> {
            if ("success".equals(result)) {
                //验证成功
                ToastUtils.showShort("验证成功");
                fCodeDialog.dismiss();
            } else {
                //验证失败
                ToastUtils.showShort("该F码似乎是无效的");
            }
        });

        registerObserver(Constants.CONFIRMORDER_KEY[0], BaseResponse.class)
                .observeForever(response -> {
                    BaseResponse<ConfirmOrderBean> data = response;
                    if (data.isData()) {
                        if (data.isSuccess()) {
                            try {
                                ConfirmOrderBean orderBean = data.getData();
                                binding.setAddress(orderBean.getAddress_info());
                                binding.setTotal(orderBean.getOrder_amount());
                                mStoreCartListNews = orderBean.getStore_cart_list_news();
                                for (ConfirmOrderBean.NewStoreFinalTotalListBean newStoreFinalTotalListBean : orderBean.getNew_store_final_total_list()) {
                                    for (ConfirmOrderBean.StoreCartListNewsBean mStoreCartListNew : mStoreCartListNews) {
                                        if (mStoreCartListNew.getStore_id().equals(String.valueOf(newStoreFinalTotalListBean.getKey()))) {
                                            mStoreCartListNew.setStore_goods_total(newStoreFinalTotalListBean.getValue());
                                        }
                                    }
                                }
                                ConfirmOrderBean.JoinStoreInfoBean joinStoreInfo = orderBean.getJoin_store_info();

                                /*折扣*/
                                if (null != joinStoreInfo) {
                                    binding.llDiscount.setVisibility(View.VISIBLE);
                                    binding.tvDiscount.setText(joinStoreInfo.getZk() + "");
                                    TextView tvPrice = binding.tvPrice;
                                    String savePrice = orderBean.getSave_price();
                                    if (!TextUtils.isEmpty(savePrice)) {
                                        if (joinStoreInfo.getJoin_store() == 1) {
                                            tvPrice.setText(Html.fromHtml(String.format("(节省:<font color='#d61619'>%s</font>元)", savePrice)));
                                        } else {
                                            tvPrice.setText(Html.fromHtml(String.format("(节省:<font color='#d61619'>%d</font>元)", savePrice)));
                                        }
                                        tvPrice.setVisibility(View.VISIBLE);
                                    } else {
                                        tvPrice.setVisibility(View.GONE);
                                    }
                                } else {
                                    binding.llDiscount.setVisibility(View.GONE);
                                }
                                adapter.setNewData(mStoreCartListNews);

                                addressId = orderBean.getAddress_info()
                                        .getAddress_id();
                                ConfirmOrderBean.AddressApiBean addressApi = orderBean.getAddress_api();
                                mVatHash = orderBean.getVat_hash();
                                mOffpayHash = addressApi.getOffpay_hash();
                                mOffpayHashBatch = addressApi.getOffpay_hash_batch();
                                binding.setPayment("在线付款");
                                binding.setInvoice(orderBean.getInv_info().getContent());
                                binding.executePendingBindings();

                                ConfirmOrderBean.StoreCartListNewsBean.GoodsListBean goodsListBean
                                        = orderBean.getStore_cart_list_news().get(0).getGoods_list().get(0);
                                if (goodsListBean.isFCode()) {
                                    showFCodeDialog(goodsListBean.getGoods_id());
                                } else {
                                    QLog.i("dataObserver: 不是F码商品");
                                }
                            } catch (Exception e) {
                                QLog.i(e.toString());
                            }
                        } else {
                            binding.setAddress(new AddressDataBean.AddressListBean());
                            if (data.getMessage().contains("地址")) {
                                ARouter.getInstance().build(ARouterConfig.Me.EDITADDRESSACTIVITY)
                                        .withBoolean("isAdd", true)
                                        .navigation(activity, 1);
                            }
                            ToastUtils.showLong(data.getMessage());
                        }
                    }
                });

        /*支付sn的返回*/
        registerObserver(Constants.CONFIRMORDER_KEY[2], BaseResponse.class)
                .observe(this, response -> {
                    CustomProgressDialog.stop();
                    BaseResponse<BuyStepInfo> data = response;
                    if (data.isSuccess()) {
                        mPySn = data.getData().getPay_sn();
                        showPaySheet(data.getData().getPay_info());
                    } else {
                        ToastUtils.showLong(data.getMessage());
                    }
                });

        registerObserver(Constants.CONFIRMORDER_KEY[3], Object.class)
                .observe(this, response -> {
                    if (response instanceof PayMessageInfo) {
                        PayMessageInfo data = (PayMessageInfo) response;
                        QLog.i(response);
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

    /**
     * F 码商品，显示输入F码的弹窗
     */
    private void showFCodeDialog(String goodsId) {
        if (fCodeDialog == null) {
            fCodeDialog = new FCodeDialog();
            fCodeDialog.setOnViewClickListener((view, str) -> {
                if (view.getId() == R.id.iv_close) {
                    // 取消验证
                    finish();
                } else {
                    // 提交验证
                    mViewModel.checkFCode(str, goodsId);
                }
            });
        }
        fCodeDialog.showNow(getSupportFragmentManager(), "ConfirmOrder");
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

//    public void merchant(View view) {
//    }

    public void invoiceInfo(View view) {
        ARouter.getInstance().build(ARouterConfig.classify.INVOICEACTIVITY)
                .navigation(this,
                        400);
    }

    public void sublimit(View view) {
        CustomProgressDialog.show(activity);
        Map<String, Object> map = new HashMap<>(12);
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
        if (!TextUtils.isEmpty(mPySn)) {
            map.put("pay_sn", mPySn);
        }
//        if (!TextUtils.isEmpty(rpacketTId)) {
//            map.put("rpacket_t_id", rpacketTId);
//        }
        if (null != mStoreCartListNews && mStoreCartListNews.size() > 0) {
            ConfirmOrderBean.StoreCartListNewsBean.StoreVoucherInfoBean storeVoucherInfo;
            StringBuilder sVoucher = new StringBuilder();
            for (ConfirmOrderBean.StoreCartListNewsBean mStoreCartListNew : mStoreCartListNews) {
                storeVoucherInfo = mStoreCartListNew.getStore_voucher_info();
                if (null != storeVoucherInfo) {
                    sVoucher.append(String.format("%s|%s|%s,", storeVoucherInfo.getVoucher_t_id(),
                            storeVoucherInfo.getVoucher_store_id(),
                            storeVoucherInfo.getVoucher_price()));
                }
            }
            QLog.i(sVoucher.toString());
            if (sVoucher.length() > 0) {
                map.put("voucher", sVoucher.toString());/*代金卷*/
            }
        }

        BaseViewHolder item;
        ConfirmOrderBean.StoreCartListNewsBean itemData;
        StringBuilder payMsg = new StringBuilder();
        for (int i = 0; i < adapter.getItemCount(); i++) {
            item = (BaseViewHolder) binding.recyclerView.findViewHolderForAdapterPosition(i);
            itemData = adapter.getItem(i);
            payMsg.append(itemData.getStore_id()).append("|").append(((AppCompatEditText) item.getView(R.id.ed_message)).getText().toString()).append(",");
        }
        if (null != payMsg && payMsg.toString().contains(",")) {
            map.put("pay_message", payMsg.substring(0, payMsg.lastIndexOf(",")));
        }
        mViewModel.getBuyStep2(map, Constants.CONFIRMORDER_KEY[2]);
    }

    public void selectAddress(View view) {
        ARouter.getInstance().build(ARouterConfig.Me.ADDRESSMANAGERACTIVITY)
                .withBoolean("isGetAddress", true).navigation(this,
                100);
    }

    @Override
    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence
            text) {
        QLog.i("支付方式" + text);
        binding.setPayment(text.toString());
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 100:
                    AddressDataBean.AddressListBean data = (AddressDataBean.AddressListBean) intent.getSerializableExtra("addressData");
                    binding.setAddress(data);
                    addressId = data.getAddress_id();
                    break;
                case 400:
                    InvoiceListInfo.InvoiceListBean bean = (InvoiceListInfo.InvoiceListBean) intent.getSerializableExtra("invoicelistbean");
                    invoice_id = bean.getInv_idX();
                    binding.setInvoice(String.format("%s %s", bean.getInv_title(), bean.getInv_content()));
                    break;
                case 1:
                    initData();
                    break;
                default:
                    ToastUtils.showLong("是否是传错了");
                    break;
            }
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