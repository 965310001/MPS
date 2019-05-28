package com.mingpinmall.me.ui.acitivity.address;

import android.content.DialogInterface;
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
import com.goldze.common.dmvvm.widget.loading.CustomProgressDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityEditaddressBinding;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.constants.Constants;

import static com.goldze.common.dmvvm.constants.ARouterConfig.SUCCESS;

/**
 * 功能描述：编辑 / 新增 收货地址
 *
 * @author 小斌
 * @date 2019/3/29
 **/
@Route(path = ARouterConfig.Me.EDITADDRESSACTIVITY)
public class EditAddressActivity extends AbsLifecycleActivity<ActivityEditaddressBinding, MeViewModel> {

    /**
     * city_id:市级id   2级
     * area_id:地区id   3级 如果三级没有id，则使用二级的
     */
    private String cityId;
    private String areaId;

    /**
     * 是否是新增， false则是编辑模式
     */
    boolean isAdd = true;

    private AddressDataBean.AddressListBean addressData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_editaddress;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        isAdd = getIntent().getBooleanExtra("isAdd", true);
        setTitle(isAdd ? R.string.title_addAddressActivity : R.string.title_editAddressActivity);
        binding.tvSelectBlock.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);

        if (!isAdd) {
            addressData = (AddressDataBean.AddressListBean) getIntent().getSerializableExtra("addressData");
            binding.setData(addressData);
            binding.sbDefault.setChecked("1".equals(addressData.getIs_default()));
            cityId = addressData.getCity_id();
            areaId = addressData.getArea_id();
        }
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.tv_selectBlock) {
            ARouter.getInstance().build(ARouterConfig.Me.SELECTCITYACTIVITY).navigation(activity, 1);
        } else if (viewId == R.id.btn_submit) {
            //保存
            submit();
        }
    }

    /**
     * 保存提交
     */
    private void submit() {
        CustomProgressDialog.stop();
        String label = "";
        if (binding.edAddress.length() <= 3) {
            label = "请输入正确的地址";
        } else if (binding.edName.length() == 0) {
            label = "请输入收件人姓名";
        } else if (binding.edPhone.length() < 11) {
            label = "请输入11位手机号";
        } else if (binding.tvSelectBlock.length() == 0) {
            label = "请选择地区";
        }
        if ("".equals(label)) {
            TextDialog.showBaseDialog(activity, label, "", dialog -> {
            });
            return;
        }
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
            if (resultCode == RESULT_OK) {
                binding.tvSelectBlock.setText(data.getStringExtra("address"));
                cityId = data.getStringExtra("cityId");
                areaId = data.getStringExtra("areaId");
            }
        }
    }

    @Override
    protected void dataObserver() {
        registerObserver(Constants.EDIT_ADDRESS, String.class).observeForever(msg -> {
            if (msg.equals(SUCCESS)) {
                //保存成功
                ToastUtils.showShort("保存成功");
                CustomProgressDialog.stop();
                setResult(RESULT_OK);
                finish();
            } else {
                //保存失败
                ToastUtils.showShort(msg);
                CustomProgressDialog.stop();
            }
        });
    }

    @Override
    protected Object getStateEventKey() {
        return "EDIT_ADDRESS_ACTIVITY";
    }
}
