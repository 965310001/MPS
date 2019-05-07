package com.mingpinmall.me.ui.acitivity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.base.bean.UserBean;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityOrderevaluateBinding;
import com.mingpinmall.me.ui.adapter.OrderEvaluateAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.BaseSelectPhotos;
import com.mingpinmall.me.ui.bean.OrderEvaluateBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 功能描述：订单评价
 * 创建人：小斌
 * 创建时间: 2019/4/30
 **/
@Route(path = ARouterConfig.Me.ORDEREVALUATEACTIVITY)
public class OrderEvaluateActivity extends AbsLifecycleActivity<ActivityOrderevaluateBinding, MeViewModel> {

    @Autowired
    String id;

    private OrderEvaluateAdapter evaluateAdapter;
    private List<OrderEvaluateBean.OrderGoodsBean> data;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        ARouter.getInstance().inject(this);
        setTitle(R.string.title_OrderEvaluateActivity);
        evaluateAdapter = new OrderEvaluateAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerView.setAdapter(evaluateAdapter);
        binding.btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onViewClicked(int viewId) {
        super.onViewClicked(viewId);
        if (viewId == R.id.btn_submit) {
            //提交评价
            Log.i("点击", "onViewClicked: ----------");
            try {
                String key = ((UserBean) SharePreferenceUtil.getUser(UserBean.class)).getKey();
                JSONObject jsonObject = new JSONObject();
                JSONObject jsonObject1 = new JSONObject();
                jsonObject.put("key", key);
                jsonObject.put("order_id", id);

                Map<String, RequestBody> pics = new HashMap<>();
                for (int i = 0; i < evaluateAdapter.getItemCount(); i++) {
                    String recId = data.get(i).getRec_id();
                    BaseViewHolder helper = (BaseViewHolder) binding.recyclerView.findViewHolderForLayoutPosition(i);
                    String comment = ((AppCompatEditText) helper.getView(R.id.ed_content)).getText().toString();

                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("goods_id", data.get(i).getGoods_id());
                    jsonObject2.put("score", data.get(i).getScore());
                    jsonObject2.put("comment", comment);
                    JSONArray picArray = new JSONArray();
                    List<BaseSelectPhotos> filesPath = evaluateAdapter.getTools().get(i).getImagePath();
                    for (int j = 0; j < filesPath.size(); j++) {
                        Log.i("图片路径", filesPath.get(j).getOriginalurl());
                        File file = new File(filesPath.get(j).getOriginalurl());
                        if (!file.exists()) {
                            continue;
                        }
                        String fileHead = j + "_" + recId;
                        String fileName = fileHead + "_" + file.getName();
                        picArray.put(fileHead);
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                        pics.put(fileHead + "\";filename=\"" + fileName, requestBody);
                    }
                    jsonObject2.put("images", picArray);
                    jsonObject1.put(recId, jsonObject2);
                }
                jsonObject.put("goods", jsonObject1);
                String jsonData = jsonObject.toString();
                Log.i("点击1", "JSON内容: " + jsonData);
                mViewModel.sendEvaluate(jsonData, pics);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void initData() {
        super.initData();
        mViewModel.getOrderEvaluate(id);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver("ORDER_EVALUATE_LIST", Object.class).observeForever(result -> {
            //可评价商品列表
            if (result instanceof OrderEvaluateBean) {
                //获取成功
                data = ((OrderEvaluateBean) result).getOrder_goods();
                evaluateAdapter.setNewData(data);
            } else {
                //获取失败
                ToastUtils.showShort(result.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (evaluateAdapter.getCallTool() != null) {
            evaluateAdapter.getCallTool().onResultCall(requestCode, resultCode, data, null);
            evaluateAdapter.removeCallTool();
        }
    }

    @Override
    protected Object getStateEventKey() {
        return "OrderEvaluateActivity";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_orderevaluate;
    }

}
