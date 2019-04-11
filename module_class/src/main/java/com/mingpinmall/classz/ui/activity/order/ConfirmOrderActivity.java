package com.mingpinmall.classz.ui.activity.order;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.dialog.MaterialDialogUtils;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.ActivityConfirmOrderBinding;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.mingpinmall.classz.ui.vm.bean.OrderInfo;
import com.socks.library.KLog;

import java.util.Arrays;
import java.util.List;

/**
 * 提交订单
 */
@Route(path = ARouterConfig.classify.CONFIRMORDERACTIVITY)
public class ConfirmOrderActivity extends
        AbsLifecycleActivity<ActivityConfirmOrderBinding, ClassifyViewModel> implements MaterialDialog.ListCallbackSingleChoice {

    @Autowired
    String id;

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
    protected void onStateRefresh() {
        super.onStateRefresh();
        initData();
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.CONFIRMORDER_KEY[1];
    }

    @Override
    protected void initData() {
        super.initData();

        mViewModel.getOrderInfo("109938|1,109936|1", Constants.CONFIRMORDER_KEY[0]);
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
                                binding.setAddress(data.getData().getAddress_info());
                                binding.setTotal(data.getData().getOrder_amount());
                                List<GoodsInfo> goods_list = data.getData().getStore_cart_list().get_$10().getGoods_list();
                                String name = "";
                                for (GoodsInfo goodsInfo : goods_list) {
                                    if (!name.equals(goodsInfo.getStore_name())) {
                                        name = goodsInfo.getStore_name();
                                        goodsInfo.setStoreName(true);
                                    }
                                }
                                binding.setData(goods_list);
                                binding.setPayment("在线付款");
                                binding.setInvoice("不需要发票");
                                binding.executePendingBindings();
                            } else {
                                ToastUtils.showLong(data.getMessage());
                            }
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

    public void invoiceInfo(View view) {
        KLog.i("发票信息");
        // TODO: 2019/4/11 发票信息
    }

    public void sublimit(View view) {
        // TODO: 2019/4/11 提交订单
        KLog.i("提交订单" + binding.getContent() + binding.getPayment());
    }

    public void selectAddress(View view) {
        // TODO: 2019/4/11 选择收获地址
        KLog.i("选择收获地址");
    }

    @Override
    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
        KLog.i("支付方式" + text);
        binding.setPayment(text.toString());
        return true;
    }
}