package com.mingpinmall.home.ui.activity.shopstreet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.stackLabel.StackLabelAdapter;
import com.mingpinmall.home.R;
import com.mingpinmall.home.databinding.ActivityShopclassBinding;
import com.mingpinmall.home.ui.api.HomeViewModel;
import com.mingpinmall.home.ui.bean.ShopClassBean;
import com.mingpinmall.home.ui.constants.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：
 * @author 小斌
 * @date 2019/4/15
 **/
@Route(path = ARouterConfig.home.SHOPCLASSACTIVITY)
public class ShopClassActivity extends AbsLifecycleActivity<ActivityShopclassBinding, HomeViewModel> {

    private List<String> stringList;
    private ShopClassBean shopClassBean;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTitle(R.string.title_ShopClassActivity);
        stringList = new ArrayList<>();
        binding.stackLabelView.setAdapter((StackLabelAdapter.CovertData<String>) (data, position) -> data);
        binding.stackLabelView.setOnLabelClickListener((index, v, s) -> {
            Intent intent = new Intent();
            intent.putExtra("sc_id", index == 0 ? "0" : shopClassBean.getClass_list().get(index - 1).getSc_id());
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    @Override
    protected void dataObserver() {
        registerObserver(Constants.GET_STORE_CLASS, Object.class).observeForever(result -> {
            if (result instanceof ShopClassBean) {
                shopClassBean = (ShopClassBean) result;
                stringList.clear();
                stringList.add("全部");
                for (ShopClassBean.ClassListBean classListBean : shopClassBean.getClass_list()) {
                    stringList.add(classListBean.getSc_name());
                }
                binding.stackLabelView.setData(stringList);
            } else {
                ToastUtils.showShort(result.toString());
            }
        });
    }

    @Override
    protected void initData() {
        mViewModel.getStoreClass();
    }

    @Override
    protected Object getStateEventKey() {
        return "SHOP_CLASS_ACTIVITY";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopclass;
    }
}
