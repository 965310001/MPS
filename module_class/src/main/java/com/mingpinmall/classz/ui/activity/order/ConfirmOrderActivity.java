package com.mingpinmall.classz.ui.activity.order;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.bean.HorizontalTabTitle;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ResultBean;
import com.mingpinmall.classz.adapter.FragmentPagerAdapter;
import com.mingpinmall.classz.databinding.ActivityConfirmOrderBinding;
import com.mingpinmall.classz.databinding.ActivityShoppingDetailsBinding;
import com.mingpinmall.classz.db.utils.ShoppingCartUtils;
import com.mingpinmall.classz.ui.activity.details.GoodsCommentFragment;
import com.mingpinmall.classz.ui.activity.details.GoodsInfoDetailMainFragment;
import com.mingpinmall.classz.ui.activity.details.GoodsInfoMainFragment;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.GoodsDetailInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * 提交订单
 */
@Route(path = ARouterConfig.classify.CONFIRMORDERACTIVITY)
public class ConfirmOrderActivity extends
        AbsLifecycleActivity<ActivityConfirmOrderBinding, ClassifyViewModel> {

    @Autowired
    String id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_order;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);
        setTitle("提交订单");
        setTitlePadding(baseBinding.rlTitleContent);
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.CONFIRMORDER_KEY[1];
    }

    @Override
    protected void initData() {
        super.initData();

//        mViewModel.getOrderInfo(id, Constants.CONFIRMORDER_KEY[0]);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();

        registerObserver(Constants.CONFIRMORDER_KEY[0], GoodsDetailInfo.class)
                .observeForever(new Observer<GoodsDetailInfo>() {
                    @Override
                    public void onChanged(@Nullable GoodsDetailInfo response) {
                    }
                });
    }

    public void goCart(View view) {
//        ActivityToActivity.toActivity(ARouterConfig.Shopping.SHOPPINGACTIVITY);
        KLog.i("去购物车");
    }


}