package com.mingpinmall.me.ui.acitivity.address;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.bean.AddressDataBean;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.dialog.TextDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityAddressmanagerBinding;
import com.mingpinmall.me.ui.adapter.AddressListAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.constants.Constants;
import com.socks.library.KLog;

import static com.goldze.common.dmvvm.constants.ARouterConfig.SUCCESS;

/**
 * 功能描述：
 *
 * @author 小斌
 * @date 2019/3/29
 **/
@Route(path = ARouterConfig.Me.ADDRESSMANAGERACTIVITY)
public class AddressManagerActivity extends AbsLifecycleActivity<ActivityAddressmanagerBinding, MeViewModel> {

    private AddressListAdapter addressListAdapter;

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
        addressListAdapter = new AddressListAdapter();
        binding.setAdapter(addressListAdapter);
        binding.btnSubmit.setOnClickListener(this);
        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> initData());

        addressListAdapter.setOnItemClickListener((adapter, view, position) -> {
            //选择地址
            if (isGetAddress) {
                AddressDataBean.AddressListBean bean = addressListAdapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra("addressData", bean);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        addressListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            //子Item点击
            AddressDataBean.AddressListBean bean = addressListAdapter.getItem(position);
            if (view.getId() == R.id.tv_edit) {
                ARouter.getInstance().build(ARouterConfig.Me.EDITADDRESSACTIVITY)
                        .withBoolean("isAdd", false)
                        .withSerializable("addressData", bean)
                        .navigation(activity, 1);
            } else if (view.getId() == R.id.tv_delete) {
                deleteAddress(bean.getAddress_id());
            }
        });
    }

    /**
     * 删除地址
     */
    private void deleteAddress(final String addressId) {
        TextDialog.showBaseDialog(activity, "删除提示", "确定要删除这个地址吗？",
                () -> mViewModel.delAddress(addressId))
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
            if (resultCode == RESULT_OK) {
                //刷新列表
                initData();
            }
        }
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.ADDRESS_LIST, Object.class).observeForever(result -> {
            if (result instanceof AddressDataBean) {
                AddressDataBean data = (AddressDataBean) result;
                //获取成功
                binding.refreshLayout.finishRefresh();
                try {
                    addressListAdapter.setNewData(data.getAddress_list());
                } catch (Exception e) {
                    KLog.i(e.toString());
                }
            } else {
                //获取失败
                binding.refreshLayout.finishRefresh(false);
            }
        });
        registerObserver(Constants.DEL_ADDRESS, String.class).observeForever(msg -> {
            if (msg.equals(SUCCESS)) {
                //删成功
                initData();
            } else {
                //删失败
                ToastUtils.showShort(msg);
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
            ARouter.getInstance().build(ARouterConfig.Me.EDITADDRESSACTIVITY)
                    .withBoolean("isAdd", true)
                    .navigation(activity, 1);
        }
    }
}
