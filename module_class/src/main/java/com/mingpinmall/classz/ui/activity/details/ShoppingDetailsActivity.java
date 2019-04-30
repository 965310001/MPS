package com.mingpinmall.classz.ui.activity.details;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.db.utils.ShoppingCartUtils;
import com.mingpinmall.classz.adapter.FragmentPagerAdapter;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.databinding.ActivityShoppingDetailsBinding;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.vm.bean.GoodsDetailInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 商品详情
 */
@Route(path = ARouterConfig.home.SHOPPINGDETAILSACTIVITY)
public class ShoppingDetailsActivity extends AbsLifecycleActivity<ActivityShoppingDetailsBinding, ClassifyViewModel> {

    @Autowired
    String id;

    private GoodsInfo goodsInfo;
    private GoodsDetailInfo goodsDetailInfo;

    private GoodsInfoMainFragment goodsInfoMainFragment;
    private GoodsInfoDetailMainFragment goodsInfoDetailMainFragment;
    private GoodsCommentFragment goodsCommentFragment;

    @Override
    protected boolean isActionBar() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopping_details;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);
        setTitlePadding(binding.rlTop);
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.GOODSDETAIL_EVENT_KEY[1];
    }

    @Override
    protected void initData() {
        super.initData();

        mViewModel.getGoodsDetail(id);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();

        registerObserver(Constants.GOODSDETAIL_EVENT_KEY[0], GoodsDetailInfo.class)
                .observeForever(new Observer<GoodsDetailInfo>() {
                    @Override
                    public void onChanged(@Nullable GoodsDetailInfo response) {
                        if (response.isSuccess()) {
                            goodsDetailInfo = response;
                            goodsInfo = response.getDatas().getGoods_info();
                            goodsInfo.news_spec_list_data = response.getDatas().getNews_spec_list_data();
                            goodsInfo.setfavorate(response.getDatas().isIs_favorate());

                            goodsInfo.setMember_id(goodsDetailInfo.getDatas().getStore_info().getMember_id());

                            if (goodsInfoMainFragment == null) {
                                List<HorizontalTabTitle> title = new ArrayList<>();
                                HorizontalTabTitle horizontalTabTitle;
                                for (String s : Arrays.asList("商品", "详情", "评价")) {
                                    horizontalTabTitle = new HorizontalTabTitle(s);
                                    title.add(horizontalTabTitle);
                                }
                                List<BaseFragment> fragmentList = new ArrayList<>();
                                fragmentList.add(goodsInfoMainFragment = GoodsInfoMainFragment.newInstance());
                                fragmentList.add(goodsInfoDetailMainFragment = GoodsInfoDetailMainFragment.newInstance());
                                fragmentList.add(goodsCommentFragment = GoodsCommentFragment.newInstance());

                                binding.vpContent.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), title, fragmentList));
                                binding.vpContent.setOffscreenPageLimit(3);
                                binding.pstsTabs.setViewPager(binding.vpContent);
                            } else {
                                if (goodsInfoMainFragment.isVisible()) {
                                    goodsInfoMainFragment.update();
                                }
                                if (goodsInfoDetailMainFragment.isVisible()) {
                                    goodsInfoDetailMainFragment.setData();
                                }
                                if (goodsCommentFragment.isVisible()) {
                                    goodsCommentFragment.onRefresh();
                                }
                                if (!TextUtils.isEmpty(SharePreferenceUtil.getKeyValue("SPECIFICATIONPOP"))) {
                                    goodsInfoMainFragment.updateSpecificationPop();
                                }
                            }
                            setCartNumber();
                            binding.setData(goodsInfo);
                        } else {
                            showErrorState();
                        }
                    }
                });

        /*添加到购物车*/
//        registerObserver(Constants.CART_EVENT_KEY, ResultBean.class)
//                .observeForever(new Observer<ResultBean>() {
//                    @Override
//                    public void onChanged(@Nullable ResultBean response) {
//                        /*if (!response.isSuccess() || response.isLogin()) {
//                            ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
//                            ToastUtils.showLong(response.getError());
//                        } else {
//                            ShoppingCartUtils.addCartGoods(goodsInfo);
//                            setCartNumber();
//                            ToastUtils.showLong("添加购物车成功");
//                        }*/
//                        if (response.isSuccess()) {
//                            ShoppingCartUtils.addCartGoods(goodsInfo);
//                            setCartNumber();
//                            ToastUtils.showLong("添加购物车成功");
//                        } else {
//                            if (response.isLogin()) {
//                                ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
//                                ToastUtils.showLong(response.getError());
//                            } else {
//                                ToastUtils.showLong(response.getError());
//                            }
//                        }
//                        KLog.i(response);
//                    }
//                });

        /*商品规格*/
        registerObserver("GOODSSPECIFICATIONPOP_VAL", "GOODSSPECIFICATIONPOP_VAL")
                .observe(this, new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object s) {
                        id = s.toString();
                        KLog.i(s.toString());
                        initData();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCartNumber();
    }

    /**
     * 设置内容
     */
    public void setViewContent(boolean scrollToBottom) {
        // true:图文详情  false:商品详情
        binding.vpContent.setNoScroll(scrollToBottom);
        binding.tvTitle.setVisibility(scrollToBottom ? View.VISIBLE : View.GONE);
        binding.pstsTabs.setVisibility(scrollToBottom ? View.GONE : View.VISIBLE);
    }

    public void finish(View view) {
        onClick(view);
    }

    public void goCart(View view) {
//        ActivityToActivity.toActivity(ARouterConfig.Shopping.SHOPPINGACTIVITY);
        KLog.i("去购物车");
    }

    public void addCart(View view) {
        KLog.i("添加到购物车");
        mViewModel.addCart(id, 1, Constants.CART_EVENT_KEY);
        goodsInfo.setNum(1);
    }

    public void buyNow(View view) {
        KLog.i("立即购买");

        String goodsNum = SharePreferenceUtil.getKeyValue("ccvclick_goods_num");
        ActivityToActivity.toActivity(ARouterConfig.classify.CONFIRMORDERACTIVITY, "cartId",
                String.format("%s|%s", id, TextUtils.isEmpty(goodsNum) ? "1" : goodsNum));
        SharePreferenceUtil.saveKeyValue("ccvclick_goods_num", "");
    }

    public void contactService(View view) {
        KLog.i("联系客服");
    }

    // TODO: 2019/4/2 收藏
    public void favorites(View view) {
        if (!SharePreferenceUtil.isLogin()) {
            ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
        } else {
            goodsInfoMainFragment.favorites();
        }
    }

    /**
     * 设置购物车数量
     */
    public void setCartNumber() {
        setCartNumber(SharePreferenceUtil.isLogin() ? ShoppingCartUtils.getCartCount() : 0);
    }

    private void setCartNumber(int count) {
        if (count > 1) {
            binding.tvCount.setVisibility(View.VISIBLE);
            binding.tvCount.setText(String.valueOf(count));
        } else {
            binding.tvCount.setVisibility(View.GONE);
        }
    }

    public String getId() {
        return TextUtils.isEmpty(id) ? "" : id;
    }

    public GoodsDetailInfo getGoodsDetailInfo() {
        return goodsDetailInfo;
    }

    /**
     * 切换Fragment
     *
     * @param position position
     */
    public void setCurrentFragment(int position) {
        binding.vpContent.setCurrentItem(position);
    }
}