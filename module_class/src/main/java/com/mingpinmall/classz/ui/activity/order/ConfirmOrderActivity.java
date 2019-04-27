package com.mingpinmall.classz.ui.activity.order;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.bean.AddressDataBean;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.dialog.MaterialDialogUtils;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.databinding.ActivityConfirmOrderBinding;
import com.mingpinmall.classz.ui.activity.classiflist.ProductsActivity;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.BuyStepInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.mingpinmall.classz.ui.vm.bean.OrderInfo;
import com.mingpinmall.classz.widget.CustomPopWindow;
import com.mingpinmall.classz.widget.PayFragment;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提交订单
 */
@Route(path = ARouterConfig.classify.CONFIRMORDERACTIVITY, extras = ARouterConfig.LOGIN_NEEDED)
public class ConfirmOrderActivity extends
        AbsLifecycleActivity<ActivityConfirmOrderBinding, ClassifyViewModel> implements MaterialDialog.ListCallbackSingleChoice {

    @Autowired
    String id;

    @Autowired
    String cartId;

    /*地址id*/
    String addressId;

    @Autowired
    String ifcart;/*是否是购物车*/

    private String mVatHash, mOffpayHash, mOffpayHashBatch;

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
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.CONFIRMORDER_KEY[1];
    }

    @Override
    protected void initData() {
        super.initData();
        KLog.i(cartId);

        /*测试购物车*/
//        ifcart="1";
//        cartId="80|1";
        mViewModel.getOrderInfo(cartId, addressId, ifcart, Constants.CONFIRMORDER_KEY[0]);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();

        registerObserver(Constants.CONFIRMORDER_KEY[0], BaseResponse.class)
                .observeForever(new Observer<BaseResponse>() {
                    @Override
                    public void onChanged(@Nullable BaseResponse response) {
                        BaseResponse<OrderInfo> data = response;
                        if (data.isData()) {
                            KLog.i(data.getData().toString());
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
                                    mVatHash = "X9eeBM0WGaBRx2g48WiFjzrgjDYks2ilgy9";
                                    mOffpayHash = addressApi.getOffpay_hash();
                                    mOffpayHashBatch = addressApi.getOffpay_hash_batch();
                                    binding.setData(goods_list);
                                    binding.setAdapter(AdapterPool.newInstance().getConfirmOrder(activity)
                                            .build());
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
                    }
                });

        /*支付sn的返回*/
        registerObserver(Constants.CONFIRMORDER_KEY[2], BaseResponse.class)
                .observe(this, new Observer<BaseResponse>() {
                    @Override
                    public void onChanged(@Nullable BaseResponse response) {
                        BaseResponse<BuyStepInfo> data = response;
                        if (data.isSuccess()) {
                            KLog.i(data.getData().getPay_sn() + " " + data.getData().getPayment_code());

                        } else {
                            ToastUtils.showLong(data.getMessage());
                        }
                    }
                });
    }

    private MaterialDialog.Builder paymentDialog;

    public void merchant(View view) {
        // TODO: 2019/4/11 商家界面
    }

    public void paymentMethod(View view) {
        // TODO: 2019/4/11 支付方式
        KLog.i("支付方式");
        paymentDialog = MaterialDialogUtils.showSingleListDialog(this, "支付方式",
                Arrays.asList("在线支付", "支付宝", "微信"), this);
        paymentDialog.show();
    }


    /*阿里*/
    public void aLiPay(View view) {
        KLog.i("阿里支付");
    }

    /*微信*/
    public void weixinPay(View view) {
        KLog.i("微信支付");
    }

    public void invoiceInfo(View view) {
        KLog.i("发票信息");
        // TODO: 2019/4/11 发票信息
        ActivityToActivity.toActivity(ARouterConfig.classify.INVOICEACTIVITY);
    }

    public void sublimit(View view) {
        // TODO: 2019/4/11 提交订单
        String payment = binding.getPayment();
        KLog.i("提交订单" + binding.getContent() + payment);
        Map<String, Object> map = new HashMap<>();

//        ifcart:
//        cart_id:109928|1
//        address_id:57
//        vat_hash:D7Fa4RsS8Boz3DJM-bVpN9eMK_NPpuebIpz
//        offpay_hash:eRjFMuKHYDQ9fY6WhgDEl_pnWItk0roztp2iY_6
//        offpay_hash_batch:4x_fbpW7f4kce54vJDxrpBiXf4sbEzv
//        pay_name:online
//        invoice_id:187
//        voucher:undefined|7|undefined
//        pd_pay:0
//        password:
//        fcode:
//        rcb_pay:0
//        rpt:
//        pay_message:7|15013070796,

        if (!TextUtils.isEmpty(ifcart)) {
            map.put("ifcart", ifcart);
        }
        map.put("cart_id", cartId);
        map.put("address_id", addressId);
        map.put("vat_hash", mVatHash);
        map.put("offpay_hash", mOffpayHash);
        map.put("offpay_hash_batch", mOffpayHashBatch);
        map.put("pay_name", "online");
        map.put("invoice_id", "online");
        map.put("rpt", "");
        map.put("pay_message", " binding.getContent()");
//        mViewModel.getBuyStep2(map, Constants.CONFIRMORDER_KEY[2]);

        new PayFragment.Builder(activity)
                .build().createPop().showAsDropDown(binding.getRoot(), Gravity.BOTTOM, 0, 0);


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
            AddressDataBean.AddressListBean data = (AddressDataBean.AddressListBean) intent.getSerializableExtra("addressData");
            binding.setAddress(data);
            addressId = data.getAddress_id();
        }

    }
}