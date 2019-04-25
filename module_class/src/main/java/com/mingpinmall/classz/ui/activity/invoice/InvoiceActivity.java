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
import com.mingpinmall.classz.databinding.ActivityInvoiceBinding;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.InvoiceListInfo;
import com.mingpinmall.classz.utils.AssetsData;
import com.socks.library.KLog;
import com.xuexiang.xui.utils.WidgetUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.annotations.Nullable;

/**
 * 管理发票信息
 */
@Route(path = ARouterConfig.classify.INVOICEACTIVITY)
public class InvoiceActivity extends AbsLifecycleActivity<ActivityInvoiceBinding, ClassifyViewModel> implements RadioGroup.OnCheckedChangeListener {

    List<InvoiceListInfo.InvoiceListBean> invoiceList;

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
        binding.setSelectInvoiceType(true);
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
            mViewModel.addInvoice(Constants.INVOICECONTENT_KEY[2], map);
        } else {
            KLog.i("不需要发票提交");
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
                                binding.setList(invoiceList);
                            } catch (Exception e) {
                                KLog.i(e.toString());
                            }
                        } else {
                            ToastUtils.showLong(response.getMessage());
                        }
                    }
                });
        /*获取发票内容*/
        registerObserver(Constants.INVOICECONTENT_KEY[2], BaseResponse.class)
                .observeForever(new Observer<BaseResponse>() {
                    @Override
                    public void onChanged(@Nullable BaseResponse response) {
                        if (response.isSuccess()) {
                            BaseResponse<InvoiceListInfo> data = response;
                            KLog.i(data.getData().getInv_id());

                            mViewModel.getInvoiceList(Constants.INVOICECONTENT_KEY[3]);
                        } else {
                            showErrorState();
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
                                    for (InvoiceListInfo.InvoiceListBean data : invoiceList) {
                                        if (tag.equals(data.getInv_idX())) {
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
}