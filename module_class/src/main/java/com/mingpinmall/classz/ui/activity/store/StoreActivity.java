package com.mingpinmall.classz.ui.activity.store;

import android.arch.lifecycle.Observer;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ResourcesUtils;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.dialog.MaterialDialogUtils;
import com.heima.tabview.library.TabView;
import com.heima.tabview.library.TabViewChild;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ResultBean;
import com.mingpinmall.classz.databinding.ActivityInvoiceBinding;
import com.mingpinmall.classz.databinding.ActivityStoreBinding;
import com.mingpinmall.classz.ui.activity.classify.ClassifyFragment;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.InvoiceListInfo;
import com.socks.library.KLog;
import com.xuexiang.xui.utils.WidgetUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.annotations.Nullable;

/**
 * 管理发票信息
 */
@Route(path = ARouterConfig.classify.STOREACTIVITY)
public class StoreActivity extends
        AbsLifecycleActivity<ActivityStoreBinding, ClassifyViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store;
    }

    @Override
    protected boolean isActionBar() {
        return false;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);
        setTitlePadding(binding.clTitleBar);

        List<TabViewChild> tabViewChildList = new ArrayList<>();

        BaseFragment[] fragmentList = {
                ClassifyFragment.newInstance(),
                ClassifyFragment.newInstance(),
                ClassifyFragment.newInstance(),
                ClassifyFragment.newInstance()};

        TypedArray tabIcon = ResourcesUtils.getInstance().obtainTypedArray(R.array.tab_icon);
        TypedArray tabIconDef = ResourcesUtils.getInstance().obtainTypedArray(R.array.tab_icon_def);
        String[] tabName = ResourcesUtils.getInstance().getStringArray(R.array.tab_name);

        TabViewChild tabViewChild;
        for (int i = 0; i < tabIcon.length(); i++) {
            tabViewChild = new TabViewChild(tabIcon.getResourceId(i, 0),
                    tabIconDef.getResourceId(i, 0),
                    tabName[i],
                    fragmentList[i]);
            tabViewChildList.add(tabViewChild);
        }
        binding.tabView.setTabViewChild(tabViewChildList, getSupportFragmentManager());
        binding.tabView.setOnTabChildClickListener(new TabView.OnTabChildClickListener() {
            @Override
            public void onTabChildClick(int i, ImageView imageView, TextView textView) {

            }
        });
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.INVOICECONTENT_KEY[1];
    }

    @Override
    protected void initData() {
        super.initData();
    }


    @Override
    protected void dataObserver() {
        super.dataObserver();
//        registerObserver(Constants.INVOICECONTENT_KEY[0], BaseResponse.class)
//                .observeForever(new Observer<BaseResponse>() {
//                    @Override
//                    public void onChanged(@Nullable BaseResponse response) {
//                        if (response.isSuccess()) {
//                            BaseResponse<InvoiceListInfo> data = response;
//                            KLog.i(data.getData());
//                            try {
//                                WidgetUtils.initSpinnerStyle(binding.spinnerSystem, data.getData().getInvoice_content_list().toArray(new String[data.getData().getInvoice_content_list().size()]));
//                            } catch (Exception e) {
//
//                            }
//                        } else {
////                            showErrorState();
//                        }
//                    }
//                });
//        /*获取发票列表*/
//        registerObserver(Constants.INVOICECONTENT_KEY[3], BaseResponse.class)
//                .observeForever(new Observer<BaseResponse>() {
//                    @Override
//                    public void onChanged(@Nullable BaseResponse response) {
//                        if (response.isSuccess()) {
//                            BaseResponse<InvoiceListInfo> data = response;
//                            KLog.i(data.getData());
//                            try {
//                                invoiceList = data.getData().getInvoice_list();
//                                binding.setList(invoiceList);
//                            } catch (Exception e) {
//                                KLog.i(e.toString());
//                            }
//                        } else {
//                            ToastUtils.showLong(response.getMessage());
//                        }
//                    }
//                });
//        /*获取发票内容*/
//        registerObserver(Constants.INVOICECONTENT_KEY[2], BaseResponse.class)
//                .observeForever(new Observer<BaseResponse>() {
//                    @Override
//                    public void onChanged(@Nullable BaseResponse response) {
//                        if (response.isSuccess()) {
//                            BaseResponse<InvoiceListInfo> data = response;
//                            KLog.i(data.getData().getInv_id());
//
//                            mViewModel.getInvoiceList(Constants.INVOICECONTENT_KEY[3]);
//                        } else {
//                            showErrorState();
//                        }
//                    }
//                });
//        /*删除发票列表*/
//        registerObserver(Constants.INVOICECONTENT_KEY[4], ResultBean.class)
//                .observeForever(new Observer<ResultBean>() {
//                    @Override
//                    public void onChanged(@Nullable ResultBean response) {
//                        if (response.isSuccess()) {
//                            if (!TextUtils.isEmpty(tag)) {
//                                if (null != invoiceList && invoiceList.size() > 0) {
//                                    for (InvoiceListInfo.InvoiceListBean data : invoiceList) {
//                                        if (tag.equals(data.getInv_idX())) {
//                                            if (invoiceList.remove(data)) {
//                                                binding.setList(invoiceList);
//                                            }
//                                            break;
//                                        }
//                                    }
//                                }
//                            }
//                        } else {
//                            ToastUtils.showLong(response.getError());
//                        }
//                    }
//                });
    }

    public void finish(View view) {
        finish();
    }


}