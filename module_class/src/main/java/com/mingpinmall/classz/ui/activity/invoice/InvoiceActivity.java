package com.mingpinmall.classz.ui.activity.invoice;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.dialog.MaterialDialogUtils;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ResultBean;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.databinding.ActivityInvoiceBinding;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.InvoiceListInfo;
import com.socks.library.KLog;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.listener.OnItemClickListener;
import com.xuexiang.xui.utils.WidgetUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.annotations.Nullable;

/**
 * 管理发票信息
 */
@Route(path = ARouterConfig.classify.INVOICEACTIVITY)
public class InvoiceActivity extends AbsLifecycleActivity<ActivityInvoiceBinding, ClassifyViewModel> implements RadioGroup.OnCheckedChangeListener, OnItemClickListener {

    List<InvoiceListInfo.InvoiceListBean> invoiceList;

    DelegateAdapter adapter;

    String content = "";

    boolean selectInvoiceType = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invoice;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);
        setTitle("管理发票信息");
        setTitlePadding(baseBinding.rlTitleContent);

        binding.setListerer(this);
        binding.setSelectInvoice(true);
        binding.setSelectInvoiceType(selectInvoiceType);

        binding.setContent(content);
    }

    /*RadioGroup的点击*/
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        binding.setSelectInvoice(checkedId == R.id.checkbox);
        binding.setSelectInvoiceType(checkedId == R.id.checkbox_invoice);
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


    /*确定*/
    // TODO: 2019/4/12  获取发票内容
    public void certain(View view) {
        if (!binding.getSelectInvoice()) {/*需要发票提交*/
            Map<String, Object> map = new HashMap<>();
            map.put("inv_title_select", selectInvoiceType ? "person" : "company");/*person*/
            map.put("inv_title", content);
            map.put("inv_content", binding.spinnerSystem.getSelectedItem().toString());
            mViewModel.addInvoice(map, Constants.INVOICECONTENT_KEY[2]);
        } else {
            KLog.i("不需要发票提交");
            ToastUtils.showLong("请选择发票类型");
        }
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.INVOICECONTENT_KEY[0], BaseResponse.class)
                .observeForever(new Observer<BaseResponse>() {
                    @Override
                    public void onChanged(@Nullable BaseResponse response) {
                        if (response.isSuccess()) {
                            BaseResponse<InvoiceListInfo> data = response;
                            KLog.i(data.getData());
                            try {
                                binding.spinnerSystem.setSelection(0);
                                WidgetUtils.initSpinnerStyle(binding.spinnerSystem, data.getData().getInvoice_content_list().toArray(new String[data.getData().getInvoice_content_list().size()]));
                            } catch (Exception e) {

                            }
                        } else {
//                            showErrorState();
                        }
                    }
                });
        /*获取发票列表*/
        registerObserver(Constants.INVOICECONTENT_KEY[3], BaseResponse.class)
                .observeForever(new Observer<BaseResponse>() {
                    @Override
                    public void onChanged(@Nullable BaseResponse response) {
                        if (response.isSuccess()) {
                            BaseResponse<InvoiceListInfo> data = response;
                            KLog.i(data.getData());
                            try {
                                invoiceList = data.getData().getInvoice_list();
                                adapter = AdapterPool.newInstance()
                                        .getInvoiceList(activity)
                                        .setOnItemClickListener(InvoiceActivity.this).build();
                                binding.setAdapter(adapter);
                                binding.setList(invoiceList);
                            } catch (Exception e) {
                                KLog.i(e.toString());
                            }
                        } else {
                            ToastUtils.showLong(response.getMessage());
                        }
                    }
                });
        /*添加发票内容*/
        registerObserver(Constants.INVOICECONTENT_KEY[2], BaseResponse.class)
                .observeForever(new Observer<BaseResponse>() {
                    @Override
                    public void onChanged(@Nullable BaseResponse response) {
                        BaseResponse<InvoiceListInfo> data = response;
                        if (data.isSuccess()) {
                            ToastUtils.showLong("添加成功");
                            KLog.i(data.getData().getInv_id());
                            mViewModel.getInvoiceList(Constants.INVOICECONTENT_KEY[3]);
                        } else {
                            ToastUtils.showLong(data.getMessage());
                        }
                    }
                });
        /*删除发票列表*/
        registerObserver(Constants.INVOICECONTENT_KEY[4], ResultBean.class)
                .observeForever(new Observer<ResultBean>() {
                    @Override
                    public void onChanged(@Nullable ResultBean response) {
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
    }
}