package com.mingpinmall.me.ui.acitivity.order.virtualorder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.dialog.TextDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityVirtualOrderInformationBinding;
import com.mingpinmall.me.ui.adapter.VirtualGoodsCodeAdapter;
import com.mingpinmall.me.ui.adapter.VirtualStoreAddrsAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.VirtualInformationBean;
import com.mingpinmall.me.ui.bean.VirtualStoreAddrsBean;
import com.mingpinmall.me.ui.constants.Constants;

import static com.goldze.common.dmvvm.constants.ARouterConfig.SUCCESS;

/**
 * 功能描述：订单详情
 * *@author 小斌
 * @date 2019/4/16
 **/
@Route(path = ARouterConfig.Me.VIRTUALORDERINFORMATIONACTIVITY)
public class VirtualOrderInformationActivity extends AbsLifecycleActivity<ActivityVirtualOrderInformationBinding, MeViewModel> {

    private String orderId;
    private VirtualGoodsCodeAdapter listAdapter;
    private VirtualStoreAddrsAdapter addrsAdapter;
    private VirtualInformationBean.OrderInfoBean data;
    private final String EVENT_KEY_CANCEL = "VR_INFORMATION";

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        orderId = getIntent().getStringExtra("orderId");
        setTitle(R.string.title_OrderInformation);
        setRecyclverView(binding.recyclerView);
        setRecyclverView(binding.recyclerView2);

        listAdapter = new VirtualGoodsCodeAdapter();
        binding.recyclerView.setAdapter(listAdapter);

        addrsAdapter = new VirtualStoreAddrsAdapter();
        binding.recyclerView2.setAdapter(addrsAdapter);

        binding.btCancelOrder.setOnClickListener(this);
        binding.btnSendCode.setOnClickListener(this);
        binding.sivGoMaps.setOnClickListener(this);
        binding.clGoods.setOnClickListener(this);
        listAdapter.setOnItemClickListener((adapter, view, position) -> {
            //店铺 信息列表点击

        });
    }

    private void setRecyclverView(RecyclerView recyclverView) {
        recyclverView.setLayoutManager(new LinearLayoutManager(this));
        recyclverView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        recyclverView.setHasFixedSize(true);
        recyclverView.setNestedScrollingEnabled(false);
    }

    @Override
    protected void dataObserver() {
        registerObserver(Constants.VIRTUAL_ORDER_INFORMATION, Object.class).observeForever(result -> {
            if (result instanceof VirtualInformationBean) {
                VirtualInformationBean resultData = (VirtualInformationBean) result;
                data = resultData.getOrder_info();
                mViewModel.getVitrualOrderStoreAddrs(data.getStore_id());
                showDataInfo();
            } else {
                ToastUtils.showShort(result.toString());
            }
        });
        registerObserver(Constants.VIRTUAL_ORDER_ADDRS, Object.class).observeForever(result -> {
            if (result instanceof VirtualStoreAddrsBean) {
                VirtualStoreAddrsBean resultData = (VirtualStoreAddrsBean) result;
                addrsAdapter.setNewData(resultData.getAddr_list());
            } else {
                ToastUtils.showShort(result.toString());
            }
        });
        registerObserver(EVENT_KEY_CANCEL, "RECEVIE_ORDER", String.class).observeForever(msg -> {
            if (msg.equals(SUCCESS)) {
                setResult(RESULT_OK);
                finish();
            } else {
                ToastUtils.showShort(msg);
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
        /*买家手机号*/
        binding.sivItem2.setDescription(data.getBuyer_phone());
        binding.btnSendCode.setVisibility(TextUtils.equals("20", data.getOrder_state()) || TextUtils.equals("40",data.getOrder_state()) ? View.VISIBLE : View.GONE);
        /*买家留言*/
        binding.sivItem3.setDescription(data.getBuyer_msg());
        /*虚拟兑换码 有效期*/
        binding.sivItem4.setDescription(data.getVr_indate());
        listAdapter.setNewData(data.getCode_list());

        /*商品名，价格，数量，商品图片*/
        binding.tvGoodsName.setText(data.getGoods_name());
        binding.tvPayMoney.setText(String.format("¥%s", data.getGoods_price()));
        binding.tvCount.setText(String.format("x%s", data.getGoods_num()));
        ImageUtils.loadImage(binding.ivImage, data.getGoods_image_url());

        /*订单编号*/
        binding.tvOrderNum.setText(String.format("订单编号：%s", data.getOrder_sn()));
        /*创建时间*/
        binding.tvOrderCreateTime.setText(String.format("创建时间：%s", data.getAdd_time()));

    }

    @Override
    protected void initData() {
        mViewModel.getVitrualOrderInformation(orderId);
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.bt_cancelOrder) {
            //取消订单
            if (data != null) {
                TextDialog.showBaseDialog(activity, "提示", "确定取消订单？",
                        () -> mViewModel.cancelVirtualOrder(EVENT_KEY_CANCEL, data.getOrder_id()))
                        .show();
            }
        } else if (viewId == R.id.cl_goods) {
            //点击商品 跳转到商品详情
            if (data != null) {
                ActivityToActivity.toActivity(ARouterConfig.home.SHOPPINGDETAILSACTIVITY, "id", data.getGoods_id());
            }
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
        return R.layout.activity_virtual_order_information;
    }
}