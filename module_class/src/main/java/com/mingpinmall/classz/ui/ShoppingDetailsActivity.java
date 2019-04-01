package com.mingpinmall.classz.ui;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.BuildConfig;
import com.goldze.common.dmvvm.base.bean.HorizontalTabTitle;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.http.rx.RxSubscriber;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ShoppingCartUtils;
import com.mingpinmall.classz.adapter.FragmentPagerAdapter;
import com.mingpinmall.classz.constants.Constants;
import com.mingpinmall.classz.databinding.ActivityShoppingDetailsBinding;
import com.mingpinmall.classz.ui.vm.ClassifyViewModel;
import com.mingpinmall.classz.ui.vm.bean.ClassificationBean;
import com.mingpinmall.classz.ui.vm.bean.GoodsDetailInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsListInfo;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * 商品详情
 */
@Route(path = ARouterConfig.home.SHOPPINGDETAILSACTIVITY)
public class ShoppingDetailsActivity extends AbsLifecycleActivity<ActivityShoppingDetailsBinding, ClassifyViewModel> {

    @Autowired
    String id;

//    private GoodsInfoMainFragment goodsInfoMainFragment;

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
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.GOODSDETAIL_EVENT_KEY_STATE;
    }

    @Override
    protected void initData() {
        super.initData();
        /*getGoodsInfo();*/
        mViewModel.getGoodsDetail(id);
        setCartNumber();
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();

        LiveBus.getDefault()
                .subscribe(Constants.GOODSDETAIL_EVENT_KEY, GoodsDetailInfo.class)
                .observeForever(new Observer<GoodsDetailInfo>() {
                    @Override
                    public void onChanged(@Nullable GoodsDetailInfo response) {
                        if (response.isSuccess()) {
                            List<HorizontalTabTitle> title = new ArrayList<>();
                            HorizontalTabTitle horizontalTabTitle = new HorizontalTabTitle("商品");
                            title.add(horizontalTabTitle);
                            horizontalTabTitle = new HorizontalTabTitle("详情");
                            title.add(horizontalTabTitle);
                            horizontalTabTitle = new HorizontalTabTitle("评价");
                            title.add(horizontalTabTitle);
                            List<BaseFragment> fragmentList = new ArrayList<>();

                            fragmentList.add(GoodsInfoMainFragment.newInstance(id, response));
                            fragmentList.add(GoodsInfoDetailMainFragment.newInstance(id));
                            fragmentList.add(GoodsCommentFragment.newInstance(id));
                            binding.vpContent.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), title, fragmentList));
                            binding.vpContent.setOffscreenPageLimit(3);
                            binding.pstsTabs.setViewPager(binding.vpContent);
                        } else {
                            showErrorState();
                        }
                    }
                });

    }

//    private void getGoodsInfo() {
//        showLoadingState();
////        ApiRepo.getProduct(Long.parseLong(id)).subscribeWith(new RxSubscriber<GoodsListInfo>() {
////            @Override
////            public void onSuccess(GoodsListInfo data) {
////                showSuccess();
////                goodsInfo = data.getData();
////                List<HorizontalTabTitle> title = new ArrayList<>();
////                HorizontalTabTitle horizontalTabTitle = new HorizontalTabTitle("商品");
////                title.add(horizontalTabTitle);
////
////                horizontalTabTitle = new HorizontalTabTitle("详情");
////                title.add(horizontalTabTitle);
////
////                horizontalTabTitle = new HorizontalTabTitle("评价");
////                title.add(horizontalTabTitle);
////
////                List<BaseFragment> fragmentList = new ArrayList<>();
////                fragmentList.add(goodsInfoMainFragment = GoodsInfoMainFragment.newInstance(id, goodsInfo));
////                fragmentList.add(GoodsInfoDetailMainFragment.newInstance(data.getData()));
////                fragmentList.add(GoodsCommentFragment.newInstance(String.valueOf(goodsInfo.getGoodsId())));
////
////                vpContent.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), title, fragmentList));
////                vpContent.setOffscreenPageLimit(3);
////                pstsTabs.setViewPager(vpContent);
////
////                /*判断是否可以购买*/
////                isBuy();
////            }
////
////            @Override
////            public void onFailure(String msg) {
////                KLog.i(msg);
////                showErrorState();
////            }
////        });
//
//    }
    /*是否可以购买*/
//    private void isBuy() {
//        if (!isGo()) {
//            for (TextView textView : textViews) {
//                textView.setSelected(true);
//                textView.setClickable(true);
//            }
//        }
//    }

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
        binding.tvTitle.setVisibility(scrollToBottom ? VISIBLE : GONE);
        binding.pstsTabs.setVisibility(scrollToBottom ? GONE : VISIBLE);

        if (scrollToBottom) {
            binding.tvTitle.setVisibility(VISIBLE);
            binding.pstsTabs.setVisibility(GONE);
        } else {
            binding.tvTitle.setVisibility(GONE);
            binding.pstsTabs.setVisibility(VISIBLE);
        }
    }

//    @OnClick({R.id.ll_back, R.id.rl_cart, R.id.tv_add_cart, R.id.tv_buy_now})
//    public void onClick(View view) {
//        int i = view.getId();
//        if (i == R.id.ll_back) {
//            finish();
//        } else if (i == R.id.rl_cart) {
//            //去购物车
//            ActivityToActivity.toActivity(ARouterConfig.Shopping.SHOPPINGACTIVITY);
//        } else if (i == R.id.tv_add_cart) {
//            //加入购物车
//            if (goodsInfo != null) {
//                if (isGo()) {
//                    addCart(goodsInfo.getGoodsId(), 1);
//                } else {
//                    ToastUtils.showShort("库存不足，请联系客服~");
//                }
//            } else {
//                ToastUtils.showLong("没有正经的商品信息~");
//            }
//        } else if (i == R.id.tv_buy_now) {
//            if (isGo()) {
//                //立即购买
//                JsonArray jsonArray = new JsonArray();
//                JsonArray empArray = new JsonArray();
//                JsonObject jsonObject = new JsonObject();
//                jsonObject.addProperty("goods_id", goodsInfo.getGoodsId());
//                jsonObject.addProperty("num", goodsInfoMainFragment.getGoodsCount());/**/
//                jsonObject.add("property", empArray);
//                jsonArray.add(jsonObject);
//
//                Map<String, Object> map = new HashMap<>();
//                map.put("goodsInfo", goodsInfo);
//                map.put("ids", jsonArray.toString());/*订单id*/
//                map.put("orderProduct", jsonArray.toString());
//                map.put("isBuy", true);
//                map.put("goodsId", String.valueOf(goodsInfo.getGoodsId()));
//                map.put("num", goodsInfoMainFragment.getGoodsCount());
//                ActivityToActivity.toActivity(ARouterConfig.classify.ORDERCONFIRMATIONACTIVITY, map);
//            } else {
//                ToastUtils.showShort("库存不足，请联系客服~");
//            }
//        }
//
//    }

//    boolean isGo() {
//        return goodsInfo.getNum() > 0;
//    }

    private void addCart(long id, int amount) {
//        ApiRepo.addCart(String.valueOf(id), amount).subscribeWith(new RxSubscriber<GoodsListInfo>() {
//
//            @Override
//            public void onSuccess(GoodsListInfo response) {
//                KLog.i(response.getErrorMsg() + response.getError_desc());
//                if (!response.isSuccess()) {
//                    ToastUtils.showLong(response.getErrorMsg());
//                } else {
//                    goodsInfo = response.getCartGoods();
////                    goodsInfo.setNum(goodsInfoMainFragment.getGoodsCount());
//                    ShoppingCartUtils.addCartGoods(goodsInfo);
//                    setCartNumber();
//                    KLog.i("添加购物车成功");
//                }
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                KLog.i(msg);
//                ToastUtils.showLong(msg);
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                KLog.i(t.getMessage());
//                ToastUtils.showLong("请稍后再试");
//            }
//        });

    }

    private void getCartNumber() {
//        ApiRepo.getCartNum().subscribeWith(new RxSubscriber<UserInfo>() {
//
//            @Override
//            public void onSuccess(UserInfo response) {
//                KLog.i(response.getErrorMsg() + response.getError_desc());
//                if (!response.isSuccess()) {
//                    ToastUtils.showLong(response.getErrorMsg());
//                } else {
//                    setCartNumber(Integer.parseInt(response.getQuantity()));
//                }
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                KLog.i(msg);
//                ToastUtils.showLong(msg);
//                setCartNumber(0);
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                KLog.i(t.getMessage());
//                ToastUtils.showLong("请稍后再试");
//                setCartNumber(0);
//            }
//        });

    }

    /**
     * 设置购物车数量
     */
    private void setCartNumber() {
//        UserInfo data = (UserInfo) SharePreferenceUtil.getUser(UserInfo.class);
//        setCartNumber(null != data ? ShoppingCartUtils.getCartCount() : 0);
    }

    private void setCartNumber(int count) {
//        if (count > 1) {
//            binding.tvCount.setVisibility(View.VISIBLE);
//            tvCount.setText(String.valueOf(count));
//        } else {
//            tvCount.setVisibility(View.GONE);
//        }
    }

    /**
     * 切换Fragment
     *
     * @param position position
     */
    public void setCurrentFragment(int position) {
        binding.vpContent.setCurrentItem(position);
    }


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_shopping_details);
//    }
}
