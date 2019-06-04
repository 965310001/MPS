package com.mingpinmall.me.ui.acitivity.order.physicalorder;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.PermissionsUtils;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.dialog.TextDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityPhysicalOrderInformationBinding;
import com.mingpinmall.me.ui.adapter.PhysicalOrderInformationListAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.OrderDeliverBean;
import com.mingpinmall.me.ui.bean.OrderInformationBean;
import com.mingpinmall.me.ui.constants.Constants;
import com.scwang.smartrefresh.layout.constant.RefreshState;


import java.util.HashMap;
import java.util.Map;

import static com.goldze.common.dmvvm.constants.ARouterConfig.SUCCESS;

/**
 * 功能描述：订单详情
 *
 * @author 小斌
 * @date 2019/4/16
 **/
@Route(path = ARouterConfig.Me.PHYSICALORDERINFORMATIONACTIVITY)
public class PhysicalOrderInformationActivity extends AbsLifecycleActivity<ActivityPhysicalOrderInformationBinding, MeViewModel> {

    private String orderId, mStoreId;
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

        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            initData();
            binding.refreshLayout.finishRefresh();
        });
        binding.llShopContent.setOnClickListener(this);
        binding.flCall.setOnClickListener(this);
        binding.flService.setOnClickListener(this);

        listAdapter.setOnItemClickListener((adapter, view, position) -> {
            //商品点击事件
            OrderInformationBean.OrderInfoBean.GoodsListBean data = listAdapter.getItem(position);
            ActivityToActivity.toActivity(ARouterConfig.home.SHOPPINGDETAILSACTIVITY, "id", data.getGoods_id());
        });
        listAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            //退款和退货按钮点击事件
            OrderInformationBean.OrderInfoBean.GoodsListBean data = listAdapter.getItem(position);
            if (view.getId() == R.id.tv_refund) {
                //商品退款
                ARouter.getInstance().build(ARouterConfig.Me.APPLYREFUNDACTIVITY)
                        .withString("id", orderId)
                        .withString("goods_id", data.getRec_id())
                        .navigation(activity, 1);
            } else if (view.getId() == R.id.tv_return) {
                //商品退货
                ARouter.getInstance().build(ARouterConfig.Me.APPLYRETURNACTIVITY)
                        .withString("id", orderId)
                        .withString("goods_id", data.getRec_id())
                        .navigation(activity, 1);
            }
        });
    }

    @Override
    protected void dataObserver() {
        registerObserver(Constants.PHYSICAL_ORDER_INFORMATION, Object.class).observeForever(result -> {
            if (result instanceof OrderInformationBean) {
                OrderInformationBean resultData = (OrderInformationBean) result;
                data = resultData.getOrder_info();
                mStoreId = data.getStore_id();
                binding.setData(data);
                showDataInfo();
            } else {
                ToastUtils.showShort(result.toString());
            }
        });
        registerObserver("INFORMATION_CANCEL_ORDER", Constants.CANCEL_ORDER, String.class)
                .observeForever(msg -> {
                    if (SUCCESS.equals(msg)) {
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        ToastUtils.showShort(msg);
                    }
                });
        registerObserver("INFORMATION_RECEVIE_ORDER", Constants.RECEVIE_ORDER, String.class)
                .observeForever(msg -> {
                    if (msg.equals(SUCCESS)) {
                        ToastUtils.showShort("已确认收货");
                        initData();
                    } else {
                        ToastUtils.showShort(msg);
                    }
                });
        registerObserver(Constants.ORDER_DELIVER_INFORMATION, Object.class)
                .observeForever(result -> {
                    if (result instanceof OrderDeliverBean) {
                        OrderDeliverBean data = (OrderDeliverBean) result;
                        binding.setDeliverData(data);
                    } else {
                        ToastUtils.showShort(result.toString());
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
        /*店铺，店铺商品列表*/
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
        binding.tvYouhui.setText(data.getPromotion().size() > 0 ? data.getPromotion().get(0).get(1) : "");
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
            tvBtn.setId(R.id.order_buyer_cancel);
            tvBtn.setText("取消订单");
        }
        if (data.isIf_refund_cancel()) {
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

        binding.clBottom.setVisibility(buttonContent.getChildCount() == 0
                && binding.tvTips.getVisibility() != View.VISIBLE
                ? View.GONE
                : View.VISIBLE);

        //物流信息是否存在
        if (data.isIf_deliver()) {
            mViewModel.getOrderDeliverInformation(data.getOrder_id());
            binding.clExpressInformation.setOnClickListener(this);
        }
    }

    @Override
    protected void initData() {
        mViewModel.getOrderInformation(orderId);
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.order_buyer_cancel) {
            //取消订单
            TextDialog.showBaseDialog(activity, "提示", "确定取消订单？",
                    () -> mViewModel.cancelOrder("INFORMATION_CANCEL_ORDER", data.getOrder_id()))
                    .show();
        } else if (viewId == R.id.order_refund_cancel) {
            //订单退款
            ARouter.getInstance().build(ARouterConfig.Me.ORDERREFUNDACTIVITY)
                    .withString("id", data.getOrder_id())
                    .navigation(activity, 1);
        } else if (viewId == R.id.order_sure) {
            //确认收货
            TextDialog.showBaseDialog(activity, "确认收货", "确认已收到订单中商品？",
                    () -> mViewModel.recevieOrder("INFORMATION_RECEVIE_ORDER", data.getOrder_id()))
                    .show();
        } else if (viewId == R.id.order_evaluation) {
            //订单评价
            ARouter.getInstance().build(ARouterConfig.Me.ORDEREVALUATEACTIVITY)
                    .withString("id", data.getOrder_id())
                    .navigation(activity, 1);
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
            if (PermissionsUtils.checkPermissions(activity, Manifest.permission.CALL_PHONE)) {
                callPhone(data.getStore_phone());
            }
        } else if (viewId == R.id.fl_service) {
            // 联系客服
            Map<String, Object> map = new HashMap<>(2);
            map.put("goodsId", "");
            map.put("tId", mStoreId);
            ActivityToActivity.toActivity(ARouterConfig.classify.CHATACTIVITY, map);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                initData();
            }
        }
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
