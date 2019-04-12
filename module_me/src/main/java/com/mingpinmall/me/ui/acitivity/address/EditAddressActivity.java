package com.mingpinmall.me.ui.acitivity.address;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.bean.AddressDataBean;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.widget.progress.ProgressDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityEditaddressBinding;
import com.mingpinmall.me.ui.api.MeViewModel;

/**
 * 功能描述：编辑 / 新增 收货地址
 * 创建人：小斌
 * 创建时间: 2019/3/29
 **/
@Route(path = ARouterConfig.EDITADDRESSACTIVITY)
public class EditAddressActivity extends AbsLifecycleActivity<ActivityEditaddressBinding, MeViewModel> {

    private ProgressDialog progressDialog;

    /**
     * city_id:市级id   2级
     * area_id:地区id   3级 如果三级没有id，则使用二级的
     */
    private String cityId;
    private String areaId;

    boolean isAdd = true;//是否是新增， false则是编辑模式

    private AddressDataBean.AddressListBean addressData;//收货地址信息，编辑收货地址时用到

    @Override
    protected int getLayoutId() {
        return R.layout.activity_editaddress;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        isAdd = getIntent().getBooleanExtra("isAdd", true);
        setTitle(isAdd ? R.string.title_addAddressActivity : R.string.title_editAddressActivity);
        progressDialog = ProgressDialog.initNewDialog(getSupportFragmentManager());
        binding.tvSelectBlock.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);

        if (!isAdd) {
            addressData = (AddressDataBean.AddressListBean) getIntent().getSerializableExtra("addressData");
            binding.setData(addressData);
            binding.sbDefault.setChecked(addressData.getIs_default().equals("1"));
            cityId = addressData.getCity_id();
            areaId = addressData.getArea_id();
        }
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.tv_selectBlock) {
            startActivityForResult(new Intent(this, SelectCityActivity.class), 1);
        } else if (viewId == R.id.btn_submit) {
            //保存
            submit();
        }
    }

    /**
     * 保存提交
     */
    private void submit() {
        if (binding.edAddress.length() <= 3) {
            progressDialog.onFail("请输入正确的地址");
            return;
        } else if (binding.edName.length() == 0) {
            progressDialog.onFail("请输入收件人姓名");
            return;
        } else if (binding.edPhone.length() < 11) {
            progressDialog.onFail("请输入11位手机号");
            return;
        } else if (binding.tvSelectBlock.length() == 0) {
            progressDialog.onFail("请选择地区");
            return;
        }
        progressDialog.onLoading("");
        if (isAdd) {
            /**
             * int id_default, String name, String city_id, String area_id, String area_info,
             *                            String address, String phone
             */
            mViewModel.addAddress(
                    binding.sbDefault.isChecked() ? 1 : 0,
                    binding.edName.getText().toString(),
                    cityId,
                    areaId,
                    binding.tvSelectBlock.getText().toString(),
                    binding.edAddress.getText().toString(),
                    binding.edPhone.getText().toString()
            );
        } else {
            mViewModel.editAddress(
                    addressData.getAddress_id(),
                    binding.sbDefault.isChecked() ? 1 : 0,
                    binding.edName.getText().toString(),
                    cityId,
                    areaId,
                    binding.tvSelectBlock.getText().toString(),
                    binding.edAddress.getText().toString(),
                    binding.edPhone.getText().toString()
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 100) {
                binding.tvSelectBlock.setText(data.getStringExtra("address"));
                cityId = data.getStringExtra("cityId");
                areaId = data.getStringExtra("areaId");
            }
        }
    }

    @Override
    protected void dataObserver() {
        registerObserver("EDIT_ADDRESS", "success")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object o) {
                        //保存成功
                        progressDialog.onComplete("", new ProgressDialog.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                setResult(100);
                                finish();
                            }
                        });
                    }
                });
        registerObserver("EDIT_ADDRESS", "err")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object o) {
                        //保存失败
                        progressDialog.onFail(o.toString());
                    }
                });
    }

    @Override
    protected Object getStateEventKey() {
        return "EDIT_ADDRESS_ACTIVITY";
    }
}
