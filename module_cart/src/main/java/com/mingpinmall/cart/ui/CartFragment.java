package com.mingpinmall.cart.ui;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.dialog.TextDialog;
import com.mingpinmall.cart.R;
import com.mingpinmall.cart.databinding.FragmentCartBinding;
import com.mingpinmall.cart.ui.adapter.ShopCartAdapter;
import com.mingpinmall.cart.ui.api.CartViewModel;
import com.mingpinmall.cart.ui.bean.AvailableCartBean;
import com.mingpinmall.cart.ui.bean.CartQuantityState;
import com.mingpinmall.cart.ui.bean.ShopCartBean;
import com.goldze.common.dmvvm.widget.SmoothCheckBox;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车
 */
public class CartFragment extends AbsLifecycleFragment<FragmentCartBinding, CartViewModel> {

    private String EVENT_KEY = "";
    private ShopCartAdapter shopCartAdapter;
    private int checkedSize = 0;
    private int goodsSize = 0;

    public CartFragment() {
    }

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    public static CartFragment newInstance(String key) {
        CartFragment cartFragment = new CartFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", key);
        cartFragment.setArguments(bundle);
        return cartFragment;
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        EVENT_KEY = args.getString("key");
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        if (EVENT_KEY.equals("")) {
            getViewById(R.id.rl_title_bar).setVisibility(View.VISIBLE);
            ((TextView) getViewById(R.id.tv_title)).setText("购物车");
            ((ImageView) getViewById(R.id.iv_search)).setImageResource(R.drawable.ic_message_black);
            getViewById(R.id.iv_search).setVisibility(View.VISIBLE);
            getViewById(R.id.iv_search).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转消息页面，未登录则跳转登陆页面
                    ActivityToActivity.toActivity(SharePreferenceUtil.isLogin() ? ARouterConfig.Me.MESSAGEACTIVITY : ARouterConfig.LOGINACTIVITY);
                }
            });
            //在这里设置沉浸式状态栏
            setTitlePadding(getViewById(R.id.rl_title_content));
        }
        //并且设置状态栏字体颜色为黑色
        setDarkMode(true);

        View emptyView = View.inflate(activity, R.layout.layout_state_view, null);
        emptyView.findViewById(R.id.btn_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //切换到首页
                LiveBus.getDefault().postEvent("Main", "tab", 0);
            }
        });
        shopCartAdapter = new ShopCartAdapter();
        shopCartAdapter.setEmptyView(emptyView);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerView.setAdapter(shopCartAdapter);

        binding.refreshLayout.setEnableLoadMore(false);
        setListener();
    }

    /**
     * 设置监听
     */
    private void setListener() {
        binding.clNoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转去登陆
                ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
            }
        });
        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (!SharePreferenceUtil.isLogin()) {
                    binding.refreshLayout.finishRefresh(false);
                    return;
                }
                lazyLoad();
            }
        });

        binding.tvPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //立即支付
//                ActivityToActivity.toActivity(ARouterConfig.cart.SHOPCARTACTIVITY);这个是其他地方需要跳转到购物车，又不想返回首页的时候，跳转这个activity
            }
        });
        binding.cbSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*全选购物车或者全反选购物车*/
                binding.cbSelectAll.toggle();
                boolean isCheck = binding.cbSelectAll.isChecked();
                checkedSize = isCheck ? goodsSize : 0;
                double money = 0.0;
                for (AvailableCartBean item : shopCartAdapter.getData()) {
                    item.setCheck(isCheck);
                    if (item.getItemType() == 1 && isCheck) {
                        double price = Double.parseDouble(item.getGoods().getGoods_price());
                        int count = Integer.parseInt(item.getGoods().getGoods_num());
                        price = price * count;
                        money += isCheck ? price : -price;
                    }
                }
                binding.tvMoney.setText(money + "");
                shopCartAdapter.notifyDataSetChanged();
            }
        });
        /*列表item的点击事件*/
        shopCartAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AvailableCartBean data = shopCartAdapter.getItem(position);
                if (data.getItemType() == 1) {
                    ActivityToActivity.toActivity(
                            ARouterConfig.home.SHOPPINGDETAILSACTIVITY,
                            "id",
                            data.getGoods().getGoods_id()
                    );
                }

            }
        });
        /*列表上子控件的点击事件*/
        shopCartAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                final AvailableCartBean data = shopCartAdapter.getItem(position);
                if (view.getId() == R.id.iv_delete) {
                    //移除商品
                    TextDialog.showBaseDialog(activity, "移除商品", "确定移除这个商品吗？", new TextDialog.SingleButtonCallback() {
                        @Override
                        public void onClick() {
                            mViewModel.deleteGoods(position, data.getGoods().getCart_id());
                        }
                    }).show();
                } else if (view.getId() == R.id.cb_single) {
                    //勾选反勾选
                    ((SmoothCheckBox) view).setChecked(!((SmoothCheckBox) view).isChecked(), true);
                    checkStateChange(data, position);
                } else if (view.getId() == R.id.iv_jian) {
                    //商品减一
                    int goodsNum = Integer.parseInt(data.getGoods().getGoods_num());
                    mViewModel.editCartQuantity(position, data.getGoods().getCart_id(), goodsNum - 1);
                    view.setEnabled(false);
                } else if (view.getId() == R.id.iv_jia) {
                    //商品加一
                    int goodsNum = Integer.parseInt(data.getGoods().getGoods_num());
                    mViewModel.editCartQuantity(position, data.getGoods().getCart_id(), goodsNum + 1);
                    view.setEnabled(false);
                } else if (view.getId() == R.id.ll_listContent || view.getId() == R.id.iv_arrow) {
                    //满即送展开与收起
                    data.setExpanded(!data.isExpanded());
                    shopCartAdapter.notifyItemChanged(position);
                } else if (view.getId() == R.id.tv_coupon) {
                    //领券
                    Log.i("购物车", "onItemChildClick: 领取优惠券");

                }
            }
        });
    }

    /**
     * 增加或者减少单个商品数量
     *
     * @param position
     * @param quantity
     */
    private void changeGoodsCount(int position, int quantity) {
        AvailableCartBean data = shopCartAdapter.getItem(position);
        int count = Integer.parseInt(data.getGoods().getGoods_num());
        boolean isAdd = quantity > count;
        count += quantity - count;
        if (count < 1) {
            count = 1;
        }
        data.getGoods().setGoods_num(String.valueOf(count));
        shopCartAdapter.setData(position, data);
        if (data.isCheck()) {
            //获得当前总价
            double money = Double.parseDouble(binding.tvMoney.getText().toString());
            //获得单价
            double price = Double.parseDouble(data.getGoods().getGoods_price());
            //总价 加上 这次的单价
            money = money + (isAdd ? price : -price);
            binding.tvMoney.setText(String.valueOf(money));
        }
    }

    /**
     * 切换选中状态，非全选按钮
     *
     * @param data
     */
    private void checkStateChange(AvailableCartBean data, int position) {
        boolean isCheck = !data.isCheck();
        data.setCheck(isCheck);
        double money = 0.0;
        if (!binding.tvMoney.getText().toString().isEmpty()) {
            money = Double.parseDouble(binding.tvMoney.getText().toString());
        }
        if (data.getItemType() == 0) {
            //group
            for (int i = position; i < shopCartAdapter.getData().size(); i++) {
                AvailableCartBean itemData = shopCartAdapter.getData().get(i);
                if (itemData.getStore_id().equals(data.getStore_id())) {
                    if (itemData.getItemType() == 1 && itemData.isCheck() != isCheck) {
                        checkedSize = isCheck ? checkedSize + 1 : checkedSize - 1;
                        double price = Double.parseDouble(itemData.getGoods().getGoods_price());
                        int count = Integer.parseInt(itemData.getGoods().getGoods_num());
                        price = price * count;
                        money += isCheck ? price : -price;
                    }
                    itemData.setCheck(isCheck);
                    continue;
                }
                break;
            }
            shopCartAdapter.notifyDataSetChanged();
        } else {
            //child
            String storeId = data.getStore_id();
            checkedSize = isCheck ? checkedSize + 1 : checkedSize - 1;
            for (int i = 0; i < shopCartAdapter.getData().size(); i++) {
                AvailableCartBean itemBean = shopCartAdapter.getData().get(i);
                if (itemBean.getStore_id() == storeId && itemBean.getItemType() == 0) {
                    itemBean.changeCheckedCount(isCheck);
                    shopCartAdapter.notifyItemChanged(i);
                    break;
                }
            }
            double price = Double.parseDouble(data.getGoods().getGoods_price());
            int count = Integer.parseInt(data.getGoods().getGoods_num());
            price = price * count;
            money += data.isCheck() ? price : -price;
        }
        binding.cbSelectAll.setChecked(goodsSize != 0 && checkedSize == goodsSize, false);
        binding.tvMoney.setText(money + "");
    }

    @Override
    protected void lazyLoad() {
        if (!SharePreferenceUtil.isLogin()) {
            return;
        }
        binding.clPayContent.setVisibility(View.VISIBLE);
        mViewModel.getCartList(EVENT_KEY);
    }

    /**
     * 处理数据内容
     *
     * @param data
     */
    private void formatData(ShopCartBean data) {
        double money = 0.00;
        List<AvailableCartBean> dataList = new ArrayList<>();
        goodsSize = 0;
        checkedSize = 0;
        for (ShopCartBean.CartListBean cartListBean : data.getCart_list()) {
            AvailableCartBean item = new AvailableCartBean();
            item.setItemType(0);
            item.setCheck(true);
            item.setChildCount(cartListBean.getGoods().size());
            item.setCheckedCount(cartListBean.getGoods().size());
            item.setStore_id(cartListBean.getStore_id());
            item.setStore_name(cartListBean.getStore_name());
            item.setFree_freight(cartListBean.getFree_freight());
            item.setVoucher(cartListBean.getVoucher());
            item.setMansong(cartListBean.getMansong());
            dataList.add(item);
            for (ShopCartBean.CartListBean.GoodsBean goodsBean : cartListBean.getGoods()) {
                goodsSize++;
                checkedSize++;
                money += Double.parseDouble(goodsBean.getGoods_price()) * Integer.parseInt(goodsBean.getGoods_num());
                AvailableCartBean childItem = new AvailableCartBean();
                childItem.setItemType(1);
                childItem.setCheck(true);
                childItem.setStore_id(cartListBean.getStore_id());
                childItem.setStore_name(cartListBean.getStore_name());
                childItem.setGoods(goodsBean);
                dataList.add(childItem);
            }
            AvailableCartBean itemSpace = new AvailableCartBean();
            itemSpace.setItemType(11);
            itemSpace.setStore_id(cartListBean.getStore_id());
            itemSpace.setStore_name(cartListBean.getStore_name());
            dataList.add(itemSpace);
        }
        shopCartAdapter.setNewData(dataList);
        binding.cbSelectAll.setChecked(checkedSize == goodsSize, false);
        binding.tvMoney.setText(money + "");
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        LiveBus.getDefault().subscribe("LoginSuccess").observeForever(new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object isLogin) {
                KLog.i("登陆成功，刷新数据");
                reGetData();
            }
        });

        LiveBus.getDefault().subscribe("LOGIN_OUT").observeForever(new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object isLogin) {
                KLog.i("退出登录，清除数据");
                shopCartAdapter.setNewData(new ArrayList<AvailableCartBean>());
            }
        });
        /* 如需要刷新购物车列表，调用
         * LiveBus.getDefault().postEvent("SHOP_CART_REFRESH", true);
         */
        registerObserver("SHOP_CART_REFRESH", Boolean.class).observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean o) {
                Log.i("购物车", "onChanged: 重获数据");
                if (o)
                    reGetData();
            }
        });
        registerObserver(EVENT_KEY + "SHOP_CART_LIST", Object.class).
                observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        //获取购物车数据并处理
                        if (result instanceof ShopCartBean) {
                            formatData((ShopCartBean) result);
                            binding.refreshLayout.finishRefresh();
                        } else {
                            ToastUtils.showShort(result.toString());
                            binding.refreshLayout.finishRefresh(false);
                        }
                    }
                });
        registerObserver("CART_QUANTITY", CartQuantityState.class).observeForever(new Observer<CartQuantityState>() {
            @Override
            public void onChanged(@Nullable CartQuantityState result) {
                //购物车商品数量增加和减少
                if (result.isSuccess()) {
                    changeGoodsCount(result.getPosition(), result.getQuantity());
                } else {
                    if (result.getMsg().equals("参数错误")) {
                        lazyLoad();
                    }
                    ToastUtils.showShort(result.getMsg());
                }
            }
        });
        registerObserver("CART_DELETE", CartQuantityState.class).observeForever(new Observer<CartQuantityState>() {
            @Override
            public void onChanged(@Nullable CartQuantityState result) {
                //购物车商品删除
                if (result.isSuccess()) {
                } else {
                    ToastUtils.showShort(result.getMsg());
                }
                lazyLoad();
            }
        });
    }

    @Override
    protected Object getStateEventKey() {
        return "CartFragment";
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        setDarkMode(true);
        binding.clNoLogin.setVisibility(!SharePreferenceUtil.isLogin() ? View.VISIBLE : View.GONE);
        binding.clPayContent.setVisibility(SharePreferenceUtil.isLogin() ? View.VISIBLE : View.GONE);
        if (!SharePreferenceUtil.isLogin() && shopCartAdapter.getItemCount() > 0) {
            shopCartAdapter.setNewData(new ArrayList<AvailableCartBean>());
        }
    }

    @Override
    protected void onInVisible() {
        super.onInVisible();

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_cart;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }


}
