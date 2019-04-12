package com.mingpinmall.classz.ui.activity.invoice;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.ActivityShoppingDetailsBinding;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;

/**
 * 管理发票信息
 */
@Route(path = ARouterConfig.classify.INVOICEACTIVITY)
public class InvoiceActivity extends AbsLifecycleActivity<ActivityShoppingDetailsBinding, ClassifyViewModel> {

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
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.GOODSDETAIL_EVENT_KEY[0];
    }

    @Override
    protected void initData() {
        super.initData();

//        mViewModel.getGoodsDetail(id);
    }

    /*确定*/
    public void certain(View view) {

    }


    @Override
    protected void dataObserver() {
        super.dataObserver();
//        registerObserver(Constants.GOODSDETAIL_EVENT_KEY[0], GoodsDetailInfo.class)
//                .observeForever(new Observer<GoodsDetailInfo>() {
//                    @Override
//                    public void onChanged(@Nullable GoodsDetailInfo response) {
//                        if (response.isSuccess()) {
//                            List<HorizontalTabTitle> title = new ArrayList<>();
//                            HorizontalTabTitle horizontalTabTitle;
//                            for (String s : Arrays.asList("商品", "详情", "评价")) {
//                                horizontalTabTitle = new HorizontalTabTitle(s);
//                                title.add(horizontalTabTitle);
//                            }
//                            List<BaseFragment> fragmentList = new ArrayList<>();
//                            response.getDatas().getGoods_info().setfavorate(response.getDatas().isIs_favorate());
//                            fragmentList.add(GoodsInfoMainFragment.newInstance(id, response));
//                            fragmentList.add(GoodsInfoDetailMainFragment.newInstance(id));
//                            fragmentList.add(GoodsCommentFragment.newInstance(id));
//                            binding.vpContent.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), title, fragmentList));
//                            binding.vpContent.setOffscreenPageLimit(3);
//                            binding.pstsTabs.setViewPager(binding.vpContent);
//                            goodsInfo = response.getDatas().getGoods_info();
//                            is_favorate = response.getDatas().isIs_favorate();
//                            setCartNumber();
//                        } else {
//                            showErrorState();
//                        }
//                    }
//                });


    }

}