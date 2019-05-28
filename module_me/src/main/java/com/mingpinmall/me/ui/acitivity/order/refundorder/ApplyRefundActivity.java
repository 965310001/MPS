package com.mingpinmall.me.ui.acitivity.order.refundorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.goldze.common.dmvvm.base.bean.UserBean;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.goldze.common.dmvvm.utils.SelectPhotosTools;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.loading.CustomProgressDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityRefundApplyShopsBinding;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.ShopsApplyRefundBean;
import com.mingpinmall.me.ui.constants.Constants;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinnerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.goldze.common.dmvvm.constants.ARouterConfig.SUCCESS;

/**
 * 功能描述：商品退款申请
 * *@author 小斌
 * @date 2019/5/9
 **/
@Route(path = ARouterConfig.Me.APPLYREFUNDACTIVITY)
public class ApplyRefundActivity extends AbsLifecycleActivity<ActivityRefundApplyShopsBinding, MeViewModel> {

    @Autowired
    String id;
    @Autowired
    String goods_id;

    private SelectPhotosTools photosTools;
    private ShopsApplyRefundBean data;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);
        setTitle(R.string.title_ApplyRefundActivity);

        photosTools = new SelectPhotosTools(activity, binding.rvImages);
        photosTools.setMaxSize(3);

        binding.spSpinner.setAdapter(new MaterialSpinnerAdapter<ShopsApplyRefundBean.ReasonListBean>(activity, new ArrayList<>()));

        binding.tvStoreName.setOnClickListener(v -> {
            //店铺名字
            if (data != null) {
                ActivityToActivity.toActivity(ARouterConfig.classify.STOREACTIVITY, "storeId", data.getOrder().getStore_id());
            }
        });
        binding.clGoods.setOnClickListener(v -> {
            //商品详情
            if (data != null) {
                ActivityToActivity.toActivity(ARouterConfig.home.SHOPPINGDETAILSACTIVITY, "id", data.getGoods().getGoods_id());
            }
        });
        binding.btnSubmit.setOnClickListener(v -> {
            CustomProgressDialog.show(activity);
            Map<String, RequestBody> params = new HashMap<>();
            params.put("key", RequestBody.create(MediaType.parse("text/plain"), ((UserBean) SharePreferenceUtil.getUser(UserBean.class)).getKey()));
            params.put("order_id", RequestBody.create(MediaType.parse("text/plain"), id));
            params.put("order_goods_id", RequestBody.create(MediaType.parse("text/plain"), goods_id));
            params.put("reason_id", RequestBody.create(MediaType.parse("text/plain"), ((ShopsApplyRefundBean.ReasonListBean) binding.spSpinner.getSelectedItem()).getReason_id()));
            params.put("refund_amount", RequestBody.create(MediaType.parse("text/plain"), binding.edRefundMoney.getText().toString()));
            params.put("refund_type", RequestBody.create(MediaType.parse("text/plain"), "1"));
            params.put("buyer_message", RequestBody.create(MediaType.parse("text/plain"), binding.edRefundInfo.getText().toString()));
            params.put("goods_num", RequestBody.create(MediaType.parse("text/plain"), "0"));

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

            mViewModel.postRefund(params);
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mViewModel.getRefundShopsInfo(id, goods_id);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.REFUND_SHOPS_INFO, Object.class).observeForever(result -> {
            if (result instanceof ShopsApplyRefundBean) {
                data = (ShopsApplyRefundBean) result;
                binding.setData(data);
                binding.tvMaxRefundMoney.setText(String.format("¥%s", data.getGoods().getGoods_pay_price()));
                binding.spSpinner.setItems(data.getReason_list());
                ImageUtils.loadImageCorners(binding.ivImage,
                        ScreenUtil.dip2px(activity, 4), data.getGoods().getGoods_img_360());
            } else {
                ToastUtils.showShort(result.toString());
            }
        });
        registerObserver(Constants.POST_REFUND, String.class).observeForever(result -> {
            if (result.equals(SUCCESS)) {
                ToastUtils.showShort("提交成功");
                CustomProgressDialog.stop();
                ActivityToActivity.toActivity(ARouterConfig.Me.REFUNDACTIVITY, "pageIndex", 0);
                setResult(RESULT_OK);
                finish();
            } else {
                CustomProgressDialog.stop();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        photosTools.onResultCall(requestCode, resultCode, data, null);
    }

    @Override
    protected Object getStateEventKey() {
        return "ApplyRefundActivity";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refund_apply_shops;
    }
}
