package com.mingpinmall.me.ui.acitivity.order.RefundOrder;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.bean.UserBean;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.SelectPhotosTools;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.progress.ProgressDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityRefundApplyOrderBinding;
import com.mingpinmall.me.ui.adapter.OrderRefundListAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.OrderApplyRefundBean;
import com.mingpinmall.me.ui.constants.Constants;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 功能描述：订单退款申请
 * 创建人：小斌
 * 创建时间: 2019/5/9
 **/
@Route(path = ARouterConfig.Me.ORDERREFUNDACTIVITY)
public class OrderRefundActivity extends AbsLifecycleActivity<ActivityRefundApplyOrderBinding, MeViewModel> {

    @Autowired
    String id;

    private SelectPhotosTools photosTools;
    private OrderRefundListAdapter listAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);
        setTitle(R.string.title_OrderRefundActivity);

        photosTools = new SelectPhotosTools(activity, binding.rvImages);
        photosTools.setMaxSize(3);

        listAdapter = new OrderRefundListAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerView.setAdapter(listAdapter);

        binding.btnSubmit.setOnClickListener(v -> {
            if (progressDialog == null) progressDialog = ProgressDialog.initNewDialog(getSupportFragmentManager());
            progressDialog.onLoading("");
            Map<String, RequestBody> params = new HashMap<>();
            params.put("key", RequestBody.create(MediaType.parse("text/plain"), ((UserBean) SharePreferenceUtil.getUser(UserBean.class)).getKey()));
            params.put("order_id", RequestBody.create(MediaType.parse("text/plain"), id));
            params.put("buyer_message", RequestBody.create(MediaType.parse("text/plain"), binding.edRefundInfo.getText().toString()));
            String path;
            File file;
            RequestBody requestBody;
            for (int i = 0; i < photosTools.getImagePath().size(); i++) {
                path = photosTools.getImagePath().get(i).getOriginalurl();
                file = new File(path);
                if (!file.exists()) {
                    continue;
                }
                requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                params.put("file" + i + "\"; filename=\"" + file.getName(), requestBody);
            }

            mViewModel.postOrderRefund(params);
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mViewModel.getRefundOrderInfo(id);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.REFUND_ORDER_INFO, Object.class).observeForever(result -> {
            if (result instanceof OrderApplyRefundBean) {
                OrderApplyRefundBean data = (OrderApplyRefundBean) result;
                formatData(data);
            }
        });
        registerObserver(Constants.POST_REFUND_ALL, String.class).observeForever(result -> {
            if (result.equals("success")) {
                progressDialog.onComplete("");
                ActivityToActivity.toActivity(ARouterConfig.Me.REFUNDACTIVITY);
                setResult(100);
                finish();
            } else {
                ToastUtils.showShort(result);
            }
        });
    }

    private void formatData(OrderApplyRefundBean data) {
        listAdapter.setNewData(data.getGoods_list());

        binding.tvStoreName.setText(data.getOrder().getStore_name());
        /*赠品*/
        if (data.getGift_list() != null || data.getGift_list().size() > 0) {
            binding.llGifts.setVisibility(View.VISIBLE);
            binding.llGifts.removeAllViews();
            for (int i = 0; i < data.getGift_list().size(); i++) {
                OrderApplyRefundBean.GiftListBean giftListBean = data.getGift_list().get(i);
                View view = View.inflate(activity, R.layout.item_tips_textview_14sp, null);
                TextView textView = view.findViewById(R.id.tv_label);
                textView.setText(giftListBean.getGoods_name() + "    x" + giftListBean.getGoods_num());
                binding.llGifts.addView(view);
                if (i < data.getGift_list().size() - 1) {
                    View line = new View(activity);
                    line.setBackgroundResource(R.color.line_color);
                    binding.llGifts.addView(line, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                }
            }
        } else {
            binding.llGifts.setVisibility(View.GONE);
        }
    }

    @Override
    protected Object getStateEventKey() {
        return "OrderRefundActivity";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refund_apply_order;
    }
}
