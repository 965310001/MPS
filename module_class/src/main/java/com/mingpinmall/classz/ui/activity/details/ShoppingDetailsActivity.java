package com.mingpinmall.classz.ui.activity.details;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.mingpinmall.classz.adapter.FragmentPagerAdapter;
import com.mingpinmall.classz.databinding.ActivityShoppingDetailsBinding;
import com.mingpinmall.classz.db.utils.ShoppingCartUtils;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.GoodsDetailInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.mingpinmall.classz.utils.ArgbEvaluator;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品详情
 */
@Route(path = ARouterConfig.home.SHOPPINGDETAILSACTIVITY)
public class ShoppingDetailsActivity extends AbsLifecycleActivity<ActivityShoppingDetailsBinding, ClassifyViewModel> {

    @Autowired
    String id;


    private GoodsInfo mGoodsInfo;
    private GoodsDetailInfo mGoodsDetailInfo;

    private GoodsInfoMainFragment mGoodsInfoMainFragment;
    private GoodsInfoDetailMainFragment mGoodsInfoDetailMainFragment;
    private GoodsCommentFragment mGoodsCommentFragment;

    @Override
    protected boolean isActionBar() {
        return false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        id = intent.getStringExtra("id");
        initData();
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
        return Constants.GOODSDETAIL_EVENT_KEY[1] + id;
    }

    @Override
    protected void initData() {
        super.initData();

        mViewModel.getGoodsDetail(id);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();

        registerObserver(Constants.GOODSDETAIL_EVENT_KEY[0] + id, GoodsDetailInfo.class)
                .observeForever(response -> {
                    if (response.isSuccess()) {
                        mGoodsDetailInfo = response;
                        mGoodsInfo = response.getDatas().getGoods_info();
                        mGoodsInfo.news_spec_list_data = response.getDatas().getNews_spec_list_data();
                        mGoodsInfo.setfavorate(response.getDatas().isIs_favorate());

                        mGoodsInfo.setMember_id(mGoodsDetailInfo.getDatas().getStore_info().getMember_id());

                        if (mGoodsInfoMainFragment == null) {
                            List<HorizontalTabTitle> title = new ArrayList<>();
                            HorizontalTabTitle horizontalTabTitle;
                            for (String s : Arrays.asList("商品", "详情", "评价")) {
                                horizontalTabTitle = new HorizontalTabTitle(s);
                                title.add(horizontalTabTitle);
                            }

                            List<BaseFragment> fragmentList = new ArrayList<>();
                            fragmentList.add(mGoodsInfoMainFragment = GoodsInfoMainFragment.newInstance());
                            fragmentList.add(mGoodsInfoDetailMainFragment = GoodsInfoDetailMainFragment.newInstance());
                            fragmentList.add(mGoodsCommentFragment = GoodsCommentFragment.newInstance());

                            binding.vpContent.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), title, fragmentList));
                            binding.vpContent.setOffscreenPageLimit(3);
                            binding.tabs.setViewPager(binding.vpContent);
                        } else {
                            if (mGoodsInfoMainFragment.isVisible()) {
                                mGoodsInfoMainFragment.update();
                            }
                            if (mGoodsInfoDetailMainFragment.isVisible()) {
                                mGoodsInfoDetailMainFragment.setData();
                            }
                            if (mGoodsCommentFragment.isVisible()) {
                                mGoodsCommentFragment.onRefresh();
                            }
                            if (!TextUtils.isEmpty(SharePreferenceUtil.getKeyValue("SPECIFICATIONPOP"))) {
                                mGoodsInfoMainFragment.updateSpecificationPop();
                            }
                        }
                        if (mGoodsInfo.isVirtual()) {
                            binding.tvBuyNow.setAlpha(1.0f);
                            binding.tvBuyNow.setClickable(true);
                        } else if (mGoodsInfo.isShop()) {
                            binding.tvBuyNow.setAlpha(0.5f);
                            binding.tvBuyNow.setClickable(false);
                        } else {
                            binding.tvBuyNow.setAlpha(1.0f);
                            binding.tvBuyNow.setClickable(true);
                        }
                        setCartNumber();
                        binding.setData(mGoodsInfo);
                    } else {
                        showErrorState();
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
                .observe(this, s -> {
                    id = s.toString();
                    KLog.i(s.toString());
                    initData();
                });

        /*显示对话框 正在加载*/
        registerObserver(Constants.GOODSDETAIL_EVENT_KEY[0] + id + "LOADING", Object.class)
                .observe(this, obj -> {
                    boolean isLoad = (boolean) obj;
                    if (isLoad) {
                        showLoading();
                    } else {
                        /*加载框*/
                        // TODO: 2019/5/13  加载框
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCartNumber();
    }

    public void finish(View view) {
        onClick(view);
    }

    public void goCart(View view) {
        ActivityToActivity.toActivity(ARouterConfig.cart.SHOPCARTACTIVITY);
    }

    public void addCart(View view) {
        KLog.i("添加到购物车");
        if (mGoodsInfoMainFragment.isPopWindowDismiss()) {
            String goodsNum = SharePreferenceUtil.getKeyValue("click_goods_num");
            mGoodsInfo.setNum(TextUtils.isEmpty(goodsNum) ? 1 : Integer.parseInt(goodsNum));
            mViewModel.addCart(id, mGoodsInfo.getNum(), Constants.CART_EVENT_KEY);
        } else {
            mGoodsInfoMainFragment.showBuyPopWindow();
        }
    }

    public void buyNow(View view) {
        /*判断是否是虚拟*/
        if (mGoodsInfoMainFragment.isPopWindowDismiss()) {
            mGoodsInfoMainFragment.popWindowDismiss();
            Map<String, Object> map = new HashMap<>();
            String goodsNum = SharePreferenceUtil.getKeyValue("click_goods_num");
            goodsNum = TextUtils.isEmpty(goodsNum) ? "1" : goodsNum;
            if (mGoodsInfo.isVirtual()) {
                map.put("id", id);
                map.put("quantity", goodsNum);
            } else {
                map.put("cartId", String.format("%s|%s", id, goodsNum));
            }
//            ActivityToActivity.toActivity(ARouterConfig.classify.CONFIRMORDERACTIVITY, map);
            ActivityToActivity.toActivity(ARouterConfig.classify.CONFIRMORDERACTIVITY2, map);
            SharePreferenceUtil.saveKeyValue("click_goods_num", "");
        } else {
            mGoodsInfoMainFragment.showBuyPopWindow();
        }
    }

    public void contactService(View view) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("goodsId", getId());
            GoodsDetailInfo.DatasBean.StoreInfoBean storeInfo = mGoodsDetailInfo.getDatas().getStore_info();
            map.put("tId", storeInfo.getMember_id());
            ActivityToActivity.toActivity(ARouterConfig.classify.CHATACTIVITY, map);
        } catch (Exception e) {
            KLog.i(e.toString());
        }
    }

    // TODO: 2019/4/2 收藏
    public void favorites(View view) {
        if (!SharePreferenceUtil.isLogin()) {
            ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
        } else {
            mGoodsInfoMainFragment.favorites();
        }
    }

    /*领取代金券*/
    public void getReceive(View view) {
        mViewModel.getVoucherFreeex((String) view.getTag(), Constants.VOUCHER[2]);
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

    public void scroll2Shopinfo() {
        binding.vpContent.setCurrentItem(1, false);
    }

    /**
     * 顶部标题栏状态
     */
    private int show = -2;

    /**
     * 修改顶部标题栏状态，过渡背景颜色
     *
     * @param type 0：不透明化标题栏  1：80%不透明  2：全透明
     */
    public void setTitleBarState(int type) {
        if (show == type) {
            return;
        }
        show = type;
        if (show == 2 && binding.vpContent.getCurrentItem() != 0) {
            return;
        }
        int startColor = ((ColorDrawable) binding.rlTop.getBackground()).getColor();
        int endColor;
        if (show < 2 && show >= 0) {
            if (show == 0) {
                //不透明化标题栏
                endColor = Color.parseColor("#FFFFFFFF");
            } else {
                //80%不透明
                endColor = Color.parseColor("#CCFFFFFF");
            }
            binding.tabs.setVisibility(View.VISIBLE);
            binding.tabs.setAlpha(1);
            binding.line.setVisibility(View.VISIBLE);
            binding.line.setAlpha(1);
        } else {
            //全透明
            endColor = Color.parseColor("#00FFFFFF");
            binding.tabs.setVisibility(View.GONE);
            binding.line.setVisibility(View.GONE);
        }
        ValueAnimator animator = ObjectAnimator.ofInt(
                binding.rlTop,
                "backgroundColor",
                startColor,
                endColor
        );//对背景色颜色进行改变，操作的属性为"backgroundColor",此处必须这样写，不能全小写,后面的颜色为在对应颜色间进行渐变
        animator.setDuration(500);
        //如果要颜色渐变必须要ArgbEvaluator，来实现颜色之间的平滑变化，否则会出现颜色不规则跳动
        animator.setEvaluator(new ArgbEvaluator());
        animator.start();
    }

    public String getId() {
        return TextUtils.isEmpty(id) ? "" : id;
    }

    public GoodsDetailInfo getGoodsDetailInfo() {
        return mGoodsDetailInfo;
    }

    protected void setDrawerImage(Bitmap bitmap) {
        mGoodsInfoMainFragment.setDrawerImage(bitmap);
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