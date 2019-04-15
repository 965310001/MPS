package com.mingpinmall.classz.ui.activity.store;

import android.arch.lifecycle.Observer;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
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
import com.mingpinmall.classz.adapter.FragmentPagerAdapter;
import com.mingpinmall.classz.databinding.ActivityInvoiceBinding;
import com.mingpinmall.classz.databinding.ActivityStoreBinding;
import com.mingpinmall.classz.ui.activity.classify.ClassifyFragment;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.GoodsListInfo;
import com.mingpinmall.classz.ui.vm.bean.InvoiceListInfo;
import com.mingpinmall.classz.ui.vm.bean.StoreInfo;
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

        getSupportFragmentManager()
                .beginTransaction().replace(R.id.fl_content,
                ClassifyFragment.newInstance()).commit();
    }


    @Override
    protected Object getStateEventKey() {
        return Constants.STORE_GOODS_RANK_KEY[1];
    }

    @Override
    protected void initData() {
        super.initData();

        /*店铺信息*/
        mViewModel.getStoreInfo("7", "",
                Constants.STORE_GOODS_RANK_KEY[2]);

        /*收藏排行*/
        mViewModel.getStoreGoodsRank("7",
                "collectdesc", "3",
                Constants.STORE_GOODS_RANK_KEY[0]);
    }


    @Override
    protected void dataObserver() {
        super.dataObserver();
        /*店铺信息*/
        registerObserver(Constants.INVOICECONTENT_KEY[2], BaseResponse.class)
                .observeForever(new Observer<BaseResponse>() {
                    @Override
                    public void onChanged(@Nullable BaseResponse response) {
                        if (response.isSuccess()) {
                            BaseResponse<StoreInfo> data = response;
                            KLog.i("TAGS",data.getData());
                        } else {
                            ToastUtils.showLong(response.getMessage());
                        }
                    }
                });
        /*收藏排行*/
        registerObserver(Constants.STORE_GOODS_RANK_KEY[0], GoodsListInfo.class)
                .observeForever(new Observer<GoodsListInfo>() {
                    @Override
                    public void onChanged(@Nullable GoodsListInfo response) {
                        KLog.i("TAGS",response.toString());

                    }
                });
//
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