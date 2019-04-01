package com.mingpinmall.classz.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.bean.HorizontalTabTitle;
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
import com.mingpinmall.classz.databinding.ActivityShoppingDetailsBinding;
import com.mingpinmall.classz.ui.vm.ClassifyViewModel;
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

    //TODO 测试使用列表造的伪数据表示不同商品做加入购物车操作
    private GoodsInfo goodsInfo;

    private GoodsInfoMainFragment goodsInfoMainFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopping_details;
    }

//    @Override
//    protected void initViews(Bundle savedInstanceState) {
//        ARouter.getInstance().inject(this);
//
//        KLog.i("商品" + id);
//    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
        getGoodsInfo();

        setCartNumber();
    }

    private void getGoodsInfo() {
        showLoadingState();
        ApiRepo.getProduct(Long.parseLong(id)).subscribeWith(new RxSubscriber<GoodsListInfo>() {
            @Override
            public void onSuccess(GoodsListInfo data) {
                showSuccess();
                goodsInfo = data.getData();
                List<HorizontalTabTitle> title = new ArrayList<>();
                HorizontalTabTitle horizontalTabTitle = new HorizontalTabTitle("商品");
                title.add(horizontalTabTitle);

                horizontalTabTitle = new HorizontalTabTitle("详情");
                title.add(horizontalTabTitle);

                horizontalTabTitle = new HorizontalTabTitle("评价");
                title.add(horizontalTabTitle);

                List<BaseFragment> fragmentList = new ArrayList<>();
                fragmentList.add(goodsInfoMainFragment = GoodsInfoMainFragment.newInstance(id, goodsInfo));
                fragmentList.add(GoodsInfoDetailMainFragment.newInstance(data.getData()));
                fragmentList.add(GoodsCommentFragment.newInstance(String.valueOf(goodsInfo.getGoodsId())));

                vpContent.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), title, fragmentList));
                vpContent.setOffscreenPageLimit(3);
                pstsTabs.setViewPager(vpContent);

                /*判断是否可以购买*/
                isBuy();
            }

            @Override
            public void onFailure(String msg) {
                KLog.i(msg);
                showErrorState();
            }
        });

    }

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
        vpContent.setNoScroll(scrollToBottom);
//        tvTitle.setVisibility(scrollToBottom ? VISIBLE : GONE);
//        pstsTabs.setVisibility(scrollToBottom ? GONE : VISIBLE);

        if (scrollToBottom) {
            tvTitle.setVisibility(VISIBLE);
            pstsTabs.setVisibility(GONE);
        } else {
            tvTitle.setVisibility(GONE);
            pstsTabs.setVisibility(VISIBLE);
        }
    }

    @OnClick({R.id.ll_back, R.id.rl_cart, R.id.tv_add_cart, R.id.tv_buy_now})
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.ll_back) {
            finish();
        } else if (i == R.id.rl_cart) {
            //去购物车
            ActivityToActivity.toActivity(ARouterConfig.Shopping.SHOPPINGACTIVITY);
        } else if (i == R.id.tv_add_cart) {
            //加入购物车
            if (goodsInfo != null) {
                if (isGo()) {
                    addCart(goodsInfo.getGoodsId(), 1);
                } else {
                    ToastUtils.showShort("库存不足，请联系客服~");
                }
            } else {
                ToastUtils.showLong("没有正经的商品信息~");
            }
        } else if (i == R.id.tv_buy_now) {
            if (isGo()) {
                //立即购买
                JsonArray jsonArray = new JsonArray();
                JsonArray empArray = new JsonArray();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("goods_id", goodsInfo.getGoodsId());
                jsonObject.addProperty("num", goodsInfoMainFragment.getGoodsCount());/**/
                jsonObject.add("property", empArray);
                jsonArray.add(jsonObject);

                Map<String, Object> map = new HashMap<>();
                map.put("goodsInfo", goodsInfo);
                map.put("ids", jsonArray.toString());/*订单id*/
                map.put("orderProduct", jsonArray.toString());
                map.put("isBuy", true);
                map.put("goodsId", String.valueOf(goodsInfo.getGoodsId()));
                map.put("num", goodsInfoMainFragment.getGoodsCount());
                ActivityToActivity.toActivity(ARouterConfig.classify.ORDERCONFIRMATIONACTIVITY, map);
            } else {
                ToastUtils.showShort("库存不足，请联系客服~");
            }
        }

    }

    boolean isGo() {
        return goodsInfo.getNum() > 0;
    }

    private void addCart(long id, int amount) {
        ApiRepo.addCart(String.valueOf(id), amount).subscribeWith(new RxSubscriber<GoodsListInfo>() {

            @Override
            public void onSuccess(GoodsListInfo response) {
                KLog.i(response.getErrorMsg() + response.getError_desc());
                if (!response.isSuccess()) {
                    ToastUtils.showLong(response.getErrorMsg());
                } else {
                    goodsInfo = response.getCartGoods();
//                    goodsInfo.setNum(goodsInfoMainFragment.getGoodsCount());
                    ShoppingCartUtils.addCartGoods(goodsInfo);
                    setCartNumber();
                    KLog.i("添加购物车成功");
                }
            }

            @Override
            public void onFailure(String msg) {
                KLog.i(msg);
                ToastUtils.showLong(msg);
            }

            @Override
            public void onError(Throwable t) {
                KLog.i(t.getMessage());
                ToastUtils.showLong("请稍后再试");
            }
        });

    }

    private void getCartNumber() {
        ApiRepo.getCartNum().subscribeWith(new RxSubscriber<UserInfo>() {

            @Override
            public void onSuccess(UserInfo response) {
                KLog.i(response.getErrorMsg() + response.getError_desc());
                if (!response.isSuccess()) {
                    ToastUtils.showLong(response.getErrorMsg());
                } else {
                    setCartNumber(Integer.parseInt(response.getQuantity()));
                }
            }

            @Override
            public void onFailure(String msg) {
                KLog.i(msg);
                ToastUtils.showLong(msg);
                setCartNumber(0);
            }

            @Override
            public void onError(Throwable t) {
                KLog.i(t.getMessage());
                ToastUtils.showLong("请稍后再试");
                setCartNumber(0);
            }
        });

    }

    /**
     * 设置购物车数量
     */
    private void setCartNumber() {
        UserInfo data = (UserInfo) SharePreferenceUtil.getUser(UserInfo.class);
        setCartNumber(null != data ? ShoppingCartUtils.getCartCount() : 0);
    }

    private void setCartNumber(int count) {
        if (count > 1) {
            tvCount.setVisibility(View.VISIBLE);
            tvCount.setText(String.valueOf(count));
        } else {
            tvCount.setVisibility(View.GONE);
        }
    }

    /**
     * 切换Fragment
     *
     * @param position position
     */
    public void setCurrentFragment(int position) {
        vpContent.setCurrentItem(position);
    }


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_shopping_details);
//    }
}
