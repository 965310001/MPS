package com.mingpinmall.me.ui.acitivity.order.virtualorder;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.goldze.common.dmvvm.utils.PermissionsUtils;
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
import com.mingpinmall.me.ui.widget.BottomSheet;

import java.util.HashMap;
import java.util.Map;

import static com.goldze.common.dmvvm.constants.ARouterConfig.SUCCESS;

/**
 * 功能描述：订单详情
 * *@author 小斌
 *
 * @date 2019/4/16
 **/
@Route(path = ARouterConfig.Me.VIRTUALORDERINFORMATIONACTIVITY)
public class VirtualOrderInformationActivity extends AbsLifecycleActivity<ActivityVirtualOrderInformationBinding, MeViewModel> {

    private String orderId;
    private VirtualGoodsCodeAdapter listAdapter;
    private VirtualStoreAddrsAdapter addrsAdapter;
    private VirtualInformationBean.OrderInfoBean data;
    private final String EVENT_KEY_CANCEL = "VR_INFORMATION";
    private BottomSheet bottomSheet;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        orderId = getIntent().getStringExtra("orderId");
        setTitle(R.string.title_OrderInformation);
        setRecyclverView(binding.recyclerView);
        setRecyclverView(binding.recyclerView2);

        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> initData());

        listAdapter = new VirtualGoodsCodeAdapter();
        binding.recyclerView.setAdapter(listAdapter);

        addrsAdapter = new VirtualStoreAddrsAdapter();
        binding.recyclerView2.setAdapter(addrsAdapter);

        binding.btnSendCode.setOnClickListener(this);
        binding.sivGoMaps.setOnClickListener(this);
        binding.clGoods.setOnClickListener(this);

        listAdapter.setOnItemClickListener((adapter, view, position) -> {
            // TODO 虚拟订单 详情 - 店铺 信息列表点击

        });
        addrsAdapter.setOnItemClickListener((adapter, view, position) -> {
            // TODO 虚拟订单 详情 - 店铺地址item点击
            VirtualStoreAddrsBean.AddrListBean data = addrsAdapter.getItem(position);
        });
        addrsAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            //店铺地址item子控件点击
            VirtualStoreAddrsBean.AddrListBean data = addrsAdapter.getItem(position);
            if (view.getId() == R.id.iv_callPhone) {
                //拨打电话
                if (PermissionsUtils.checkPermissions(activity, Manifest.permission.CALL_PHONE)) {
                    callPhone(data.getPhone_info());
                }
            }
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
                binding.refreshLayout.finishRefresh();
            } else {
                ToastUtils.showShort(result.toString());
                binding.refreshLayout.finishRefresh(false);
            }
        });
        registerObserver(Constants.VIRTUAL_ORDER_ADDRS, Object.class).observeForever(result -> {
            if (result instanceof VirtualStoreAddrsBean) {
                VirtualStoreAddrsBean resultData = (VirtualStoreAddrsBean) result;
                addrsAdapter.setNewData(resultData.getAddr_list());
            } else {
                ToastUtils.showShort(result.toString());
            }
            binding.setHasAddress(addrsAdapter.getItemCount() > 0);
        });
        registerObserver(EVENT_KEY_CANCEL, "RECEVIE_ORDER", String.class).observeForever(msg -> {
            if (msg.equals(SUCCESS)) {
                setResult(RESULT_OK);
                finish();
            } else {
                ToastUtils.showShort(msg);
            }
        });
        registerObserver(Constants.SEND_VIRTUALCODE, String.class).observeForever(s -> {
            if ("success".equals(s)) {
                if (bottomSheet.isShowing() && bottomSheet != null) {
                    bottomSheet.dismiss();
                }
                ToastUtils.showShort("成功发送");
            } else {
                if (bottomSheet.isShowing() && bottomSheet != null) {
                    bottomSheet.setFail();
                }
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
        binding.setData(data);
        binding.btnSendCode.setVisibility(data.isIf_resend() ? View.VISIBLE : View.GONE);
        if (data.isIf_resend()) {
            binding.btnSendCode.setOnClickListener(v -> {
                bottomSheet = new BottomSheet.SendCodeSheetBuilder(activity)
                        .setData(data.getBuyer_phone())
                        .setOnSubmitClickListener(phone -> {
                            // 重新发送兑换码
                            mViewModel.sendVirtualCode(phone, orderId);
                        })
                        .build();
                bottomSheet.show();
            });
        }
        listAdapter.setNewData(data.getCode_list());

        /*最下面的按钮*/
        LinearLayout buttonContent = binding.llButtonContent;
        buttonContent.removeAllViews();
        AppCompatTextView tvBtn = null;
        if (data.isIf_evaluation()) {
            //评价订单
            tvBtn = (AppCompatTextView) View.inflate(activity, R.layout.button_layout_border_red, null);
            tvBtn.setId(R.id.order_evaluation);
            tvBtn.setText("评价订单");
        }
        if (data.isIf_cancel()) {
            //取消订单
            tvBtn = (AppCompatTextView) View.inflate(activity, R.layout.button_layout_default, null);
            tvBtn.setId(R.id.order_buyer_cancel);
            tvBtn.setText("取消订单");
        }
        if (data.isIf_refund()) {
            //订单退款
            tvBtn = (AppCompatTextView) View.inflate(activity, R.layout.button_layout_default, null);
            tvBtn.setId(R.id.order_refund_cancel);
            tvBtn.setText("订单退款");
        }
        if (tvBtn != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = ScreenUtil.dip2px(activity, 6);
            buttonContent.addView(tvBtn);
            tvBtn.setLayoutParams(params);
            tvBtn.setOnClickListener(this);
        }

    }

    @Override
    protected void initData() {
        mViewModel.getVitrualOrderInformation(orderId);
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.order_buyer_cancel) {
            //取消订单
            TextDialog.showBaseDialog(activity, "提示", "确定取消订单？",
                    () -> mViewModel.cancelVirtualOrder(EVENT_KEY_CANCEL, data.getOrder_id()))
                    .show();
        } else if (viewId == R.id.order_evaluation) {
            //订单评价
            ARouter.getInstance().build(ARouterConfig.Me.ORDEREVALUATEACTIVITY)
                    .withString("id", data.getOrder_id())
                    .navigation(activity, 1);
        } else if (viewId == R.id.cl_goods) {
            //点击商品 跳转到商品详情
            if (data != null) {
                ActivityToActivity.toActivity(ARouterConfig.home.SHOPPINGDETAILSACTIVITY, "id", data.getGoods_id());
            }
        } else if (viewId == R.id.siv_goMaps) {
            //查看所有商家地址

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
