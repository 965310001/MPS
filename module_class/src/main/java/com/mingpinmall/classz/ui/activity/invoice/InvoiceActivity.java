package com.mingpinmall.classz.ui.activity.invoice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.utils.log.QLog;
import com.goldze.common.dmvvm.widget.dialog.MaterialDialogUtils;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ResultBean;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.databinding.ActivityInvoiceBinding;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.InvoiceListInfo;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.listener.OnItemClickListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理发票信息
 */
@Route(path = ARouterConfig.classify.INVOICEACTIVITY)
public class InvoiceActivity extends AbsLifecycleActivity<ActivityInvoiceBinding, ClassifyViewModel> implements RadioGroup.OnCheckedChangeListener, OnItemClickListener {

    List<InvoiceListInfo.InvoiceListBean> invoiceList;

    DelegateAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invoice;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        /*XUI.initTheme(activity);*/
        super.initViews(savedInstanceState);
        setTitle("管理发票信息");
        setTitlePadding(baseBinding.rlTitleContent);

        binding.setListener(this);
        binding.setSelectInvoice(true);
        binding.setSelectInvoiceType(true);
        binding.setContent("");
        binding.setName("");
        binding.setIdcard("");
        binding.setNumber("");
        binding.executePendingBindings();
    }

    /*RadioGroup的点击*/
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        binding.setSelectInvoice(checkedId == R.id.checkbox);
        binding.setSelectInvoiceType(binding.rgSelectType.getCheckedRadioButtonId() == R.id.checkbox_invoice
                || checkedId == R.id.checkbox_invoice);
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.INVOICECONTENT_KEY[1];
    }

    @Override
    protected void initData() {
        super.initData();
        mViewModel.getInvoiceContentList(Constants.INVOICECONTENT_KEY[0]);
        mViewModel.getInvoiceList(Constants.INVOICECONTENT_KEY[3]);
    }

    public void certain(View view) {
        if (!binding.getSelectInvoice()) {/*需要发票提交*/
            Map<String, Object> map = new HashMap<>();
            map.put("inv_title_select", binding.getSelectInvoiceType() ? "person" : "company");/*person*/
            map.put("inv_title", binding.getContent());
            map.put("inv_content", binding.spinnerSystem.getSelectedItem().toString());
            map.put("inv_username", binding.getName());
            map.put("inv_idcode", binding.getIdcard());
            map.put("inv_jgcode", binding.getNumber());
            mViewModel.addInvoice(map, Constants.INVOICECONTENT_KEY[2]);
        } else {
            QLog.i("不需要发票提交");
            ToastUtils.showLong("请选择发票类型");
        }
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.INVOICECONTENT_KEY[0], BaseResponse.class)
                .observeForever(response -> {
                    if (response.isSuccess()) {
                        BaseResponse<InvoiceListInfo> data = response;
                        QLog.i(data.getData());
                        try {
                           /* binding.spinnerSystem.setSelection(0);
                            WidgetUtils.initSpinnerStyle(binding.spinnerSystem, data.getData().getInvoice_content_list().toArray(
                                    new String[data.getData().getInvoice_content_list().size()]));*/

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
                                    R.layout.item_text1,
                                    R.id.text, data.getData().getInvoice_content_list()
                            );
                            binding.spinnerSystem.setAdapter(adapter);
                        } catch (Exception e) {
                            QLog.i(e.toString());
                        }
                    }
                });
        /*获取发票列表*/
        registerObserver(Constants.INVOICECONTENT_KEY[3], BaseResponse.class)
                .observeForever(response -> {
                    if (response.isSuccess()) {
                        BaseResponse<InvoiceListInfo> data = response;
                        QLog.i(data.getData());
                        try {
                            invoiceList = data.getData().getInvoice_list();
                            adapter = AdapterPool.newInstance()
                                    .getInvoiceList(activity)
                                    .setOnItemClickListener(InvoiceActivity.this).build();
                            binding.setAdapter(adapter);
                            binding.setList(invoiceList);
                        } catch (Exception e) {
                            QLog.i(e.toString());
                        }
                    } else {
                        ToastUtils.showLong(response.getMessage());
                    }
                });
        /*添加发票内容*/
        registerObserver(Constants.INVOICECONTENT_KEY[2], BaseResponse.class)
                .observeForever(response -> {
                    BaseResponse<InvoiceListInfo> data = response;
                    if (data.isSuccess()) {
                        ToastUtils.showLong("添加成功");
                        QLog.i(data.getData().getInv_id());
                        mViewModel.getInvoiceList(Constants.INVOICECONTENT_KEY[3]);
                    } else {
                        ToastUtils.showLong(data.getMessage());
                    }
                });
        /*删除发票列表*/
        registerObserver(Constants.INVOICECONTENT_KEY[4], ResultBean.class)
                .observeForever(response -> {
                    if (response.isSuccess()) {
                        if (!TextUtils.isEmpty(tag)) {
                            if (null != invoiceList && invoiceList.size() > 0) {
                                String invIdX;
                                for (InvoiceListInfo.InvoiceListBean data : invoiceList) {
                                    invIdX = data.getInv_idX();
                                    if (tag.equals(invIdX)) {
                                        if (invoiceList.remove(data)) {
                                            binding.setList(invoiceList);
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        ToastUtils.showLong(response.getError());
                    }
                });
    }

    String tag;

    public void del(final View v) {
        MaterialDialogUtils.showBasicDialogNoTitle(this, "你确定要删除吗")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        tag = v.getTag().toString();
                        mViewModel.invoiceDel(Constants.INVOICECONTENT_KEY[4], tag);
                    }
                }).show();
    }

    InvoiceListInfo.InvoiceListBean bean;
    int index = 0;

    @Override
    public void onItemClick(View view, int index, Object object) {
        if (null != bean) {
            bean.setChecked(false);
        }
        this.index = index;
        bean = (InvoiceListInfo.InvoiceListBean) object;
        bean.setChecked(true);


        Intent intent = new Intent();
        intent.putExtra("invoicelistbean", bean);
        setResult(400, intent);
        finish();
    }
}