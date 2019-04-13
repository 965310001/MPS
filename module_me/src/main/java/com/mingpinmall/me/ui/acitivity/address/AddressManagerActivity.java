package com.mingpinmall.me.ui.acitivity.address;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.bean.AddressDataBean;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.dialog.MaterialDialogUtils;
import com.goldze.common.dmvvm.widget.progress.ProgressDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityAddressmanagerBinding;
import com.mingpinmall.me.ui.adapter.AddressListAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.BaseItemBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/3/29
 **/
@Route(path = ARouterConfig.ADDRESSMANAGERACTIVITY)
public class AddressManagerActivity extends AbsLifecycleActivity<ActivityAddressmanagerBinding, MeViewModel> {

    private AddressListAdapter addressListAdapter;

    private ProgressDialog progressDialog;
    private boolean isGetAddress = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_addressmanager;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        isGetAddress = getIntent().getBooleanExtra("isGetAddress", false);
        setTitle(R.string.title_addressManagerActivity);
        progressDialog = ProgressDialog.initNewDialog(getSupportFragmentManager());
        addressListAdapter = new AddressListAdapter();
        binding.setAdapter(addressListAdapter);
        binding.btnSubmit.setOnClickListener(this);
        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
            }
        });

        addressListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //选择地址
                if (isGetAddress) {
                    AddressDataBean.AddressListBean bean = addressListAdapter.getItem(position);
                    Intent intent = new Intent();
                    intent.putExtra("addressData", bean);
                    setResult(100, intent);
                    finish();
                }
            }
        });
        addressListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                //子Item点击
                AddressDataBean.AddressListBean bean = addressListAdapter.getItem(position);
                if (view.getId() == R.id.tv_edit) {
                    Intent intent = new Intent(AddressManagerActivity.this, EditAddressActivity.class);
                    intent.putExtra("isAdd", false);
                    intent.putExtra("addressData", bean);
                    startActivityForResult(intent, 1);
                } else if (view.getId() == R.id.tv_delete) {
                    deleteAddress(bean.getAddress_id());
                }
            }
        });
    }

    /**
     * 删除地址
     */
    private void deleteAddress(final String addressId) {
        MaterialDialogUtils.showBasicDialog(AddressManagerActivity.this, "删除提示")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        progressDialog.onLoading("");
                        mViewModel.delAddress(addressId);
                    }
                })
                .show();
    }

    @Override
    protected void initData() {
        /*获取数据*/
        mViewModel.getAddressList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 100) {
                //刷新列表
                initData();
            }
        }
    }

    @Override
    protected void dataObserver() {
        registerObserver("GET_ADDRESS_LIST", "success", AddressDataBean.class)
                .observeForever(new Observer<AddressDataBean>() {
                    @Override
                    public void onChanged(@Nullable AddressDataBean result) {
                        //获取成功
                        binding.refreshLayout.finishRefresh();
                        addressListAdapter.setNewData(result.getAddress_list());
                    }
                });
        registerObserver("GET_ADDRESS_LIST", "err")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object o) {
                        //获取失败
                        binding.refreshLayout.finishRefresh(false);
                    }
                });
        registerObserver("DEL_ADDRESS", "success")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        //删成功
                        progressDialog.onComplete("");
                        initData();
                    }
                });
        registerObserver("DEL_ADDRESS", "err")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object o) {
                        //删失败
                        progressDialog.onFail(o.toString());
                        initData();
                    }
                });
    }

    @Override
    protected Object getStateEventKey() {
        return "ADDRESS_ACTIVITY";
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.btn_submit) {
            ActivityToActivity.toActivity(ARouterConfig.EDITADDRESSACTIVITY, "isAdd", true);
        }
    }
}
