package com.mingpinmall.me.ui.acitivity.address;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityAddressmanagerBinding;
import com.mingpinmall.me.ui.adapter.AddressListAdapter;
import com.mingpinmall.me.ui.bean.BaseItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/3/29
 **/
@Route(path = ARouterConfig.ADDRESSMANAGERACTIVITY)
public class AddressManagerActivity extends BaseActivity<ActivityAddressmanagerBinding> {

    private AddressListAdapter addressListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_addressmanager;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle(R.string.title_addressManagerActivity);
        addressListAdapter = new AddressListAdapter();
        binding.setAdapter(addressListAdapter);
        binding.btnSubmit.setOnClickListener(this);

        List<BaseItemBean> data = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            data.add(new BaseItemBean());
        }
        addressListAdapter.setNewData(data);

        addressListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_edit) {
//                    ToastUtils.showShort("点击了编辑");
                    ActivityToActivity.toActivity(ARouterConfig.EDITADDRESSACTIVITY, "isAdd", false);
                } else if (view.getId() == R.id.tv_delete) {
                    ToastUtils.showShort("点击了删除");
                }
            }
        });
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.btn_submit) {
//            ToastUtils.showShort("点击了新增");
            ActivityToActivity.toActivity(ARouterConfig.EDITADDRESSACTIVITY, "isAdd", true);
        }
    }
}
