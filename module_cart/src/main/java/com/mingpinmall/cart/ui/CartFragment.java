package com.mingpinmall.cart.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
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
import com.goldze.common.dmvvm.widget.SmoothCheckBox;
import com.goldze.common.dmvvm.widget.dialog.TextDialog;
import com.mingpinmall.cart.BR;
import com.mingpinmall.cart.DataBindItemViewHolderManager;
import com.mingpinmall.cart.R;
import com.mingpinmall.cart.databinding.FragmentCartBinding;
import com.mingpinmall.cart.ui.adapter.ShopCartAdapter;
import com.mingpinmall.cart.ui.api.CartViewModel;
import com.mingpinmall.cart.ui.bean.AvailableCartBean;
import com.mingpinmall.cart.ui.bean.CartQuantityState;
import com.mingpinmall.cart.ui.bean.ShopCartBean;
import com.mingpinmall.cart.ui.bean.ShopVoucherInfo;
import com.mingpinmall.cart.ui.constants.Constants;
import com.mingpinmall.cart.widget.XBottomSheet;
import com.socks.library.KLog;
import com.trecyclerview.adapter.DelegateAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车
 *
 * @author 小斌
 */
public class CartFragment extends AbsLifecycleFragment<FragmentCartBinding, CartViewModel> {

    private String EVENT_KEY = "";
    private XBottomSheet xBottomSheet;
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
        if (EVENT_KEY.isEmpty()) {
            getViewById(R.id.rl_title_bar).setVisibility(View.VISIBLE);
            ((TextView) getViewById(R.id.tv_title)).setText("购物车");
            ((ImageView) getViewById(R.id.iv_search)).setImageResource(R.drawable.ic_message_black);
            getViewById(R.id.iv_search).setVisibility(View.VISIBLE);
            getViewById(R.id.iv_search).setOnClickListener(v -> {
                //跳转消息页面，未登录则跳转登陆页面
                ActivityToActivity.toActivity(SharePreferenceUtil.isLogin() ? ARouterConfig.Me.MESSAGEACTIVITY : ARouterConfig.LOGINACTIVITY);
            });
            //在这里设置沉浸式状态栏
            setTitlePadding(getViewById(R.id.rl_title_content));
        }
        //并且设置状态栏字体颜色为黑色
        setDarkMode(true);

        View emptyView = View.inflate(activity, R.layout.layout_state_view, null);
        emptyView.findViewById(R.id.btn_action).setOnClickListener(v -> {
            //切换到首页
            LiveBus.getDefault().postEvent("Main", "tab", 0);
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
        binding.clNoLogin.setOnClickListener(v -> {
            //跳转去登陆
            ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
        });
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            if (!SharePreferenceUtil.isLogin()) {
                binding.refreshLayout.finishRefresh(false);
                return;
            }
            lazyLoad();
        });

        binding.tvPayNow.setOnClickListener(v -> {
            // 立即支付
            if (!SharePreferenceUtil.isLogin()) {
                ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
                return;
            }
            StringBuilder cartId = new StringBuilder();
            String gId;
            String gNum;
            for (AvailableCartBean cartBean : shopCartAdapter.getData()) {
                //遍历一次列表
                if (cartBean.getItemType() == 1) {
                    //只处理商品类型的 item
                    if (!cartBean.isCheck()) {
                        //如果没有勾选这个商品，则不添加
                        continue;
                    }
                    gId = cartBean.getGoods().getCart_id();
                    gNum = cartBean.getGoods().getGoods_num();
                    cartId.append(String.format("%s|%s,", gId, TextUtils.isEmpty(gNum) ? "1" : gNum));
                }
            }
            if (cartId.toString().length() > 0) {
                String cartInfo = cartId.toString().contains(",") ?
                        cartId.toString().substring(0, cartId.lastIndexOf(",")) : cartId.toString();
                Log.d("立即购买", "setListener: " + cartInfo);
                Map<String, Object> params = new HashMap<>(2);
                params.put("cartId", cartInfo);
                params.put("ifcart", "1");
                ActivityToActivity.toActivity(ARouterConfig.classify.CONFIRMORDERACTIVITY2, params);
            } else {
                ToastUtils.showLong("请选择商品");
            }
        });
        binding.cbSelectAll.setOnClickListener(v -> {
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
            binding.tvMoney.setText(getMoney(money));
            shopCartAdapter.notifyDataSetChanged();
        });
        /*列表item的点击事件*/
        shopCartAdapter.setOnItemClickListener((adapter, view, position) -> {
            AvailableCartBean data = shopCartAdapter.getItem(position);
            if (data.getItemType() == 1) {
                ActivityToActivity.toActivity(
                        ARouterConfig.home.SHOPPINGDETAILSACTIVITY,
                        "id",
                        data.getGoods().getGoods_id()
                );
            }

        });
        /*列表上子控件的点击事件*/
        shopCartAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            final AvailableCartBean data = shopCartAdapter.getItem(position);
            if (view.getId() == R.id.iv_delete) {
                //移除商品
                TextDialog.showBaseDialog(activity, "移除商品", "确定移除这个商品吗？",
                        () -> mViewModel.deleteGoods(position, data.getGoods().getCart_id())
                ).show();
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
                mViewModel.getVoucherTplList(data.getStore_id(), Constants.CART_VOUCHERTPL);
            }
        });
    }

    /**
     * 增加或者减少单个商品数量
     *
     * @param position 第几个
     * @param quantity 增量
     */
    private void changeGoodsCount(int position, int quantity) {
        AvailableCartBean data = null;
        if (null != shopCartAdapter) {
            data = shopCartAdapter.getItem(position);
        }
        if (null != data) {
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
                binding.tvMoney.setText(getMoney(money));
            }
        }
    }

    /**
     * 切换选中状态，非全选按钮
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
        binding.tvMoney.setText(getMoney(money));
    }

    @Override
    protected void lazyLoad() {
        if (!SharePreferenceUtil.isLogin()) {
            return;
        }
        /*binding.clPayContent.setVisibility(View.VISIBLE);*/
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
        binding.clPayContent.setVisibility(dataList.size() > 0 ? View.VISIBLE : View.GONE);
        shopCartAdapter.setNewData(dataList);
        binding.cbSelectAll.setChecked(checkedSize == goodsSize, false);
        binding.tvMoney.setText(getMoney(money));
    }

    private String getMoney(double mMoney) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(mMoney);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.CART_VOUCHERTPL, Object.class).observeForever(result -> {
            if (result instanceof ShopVoucherInfo) {
                ShopVoucherInfo data = (ShopVoucherInfo) result;
                List<ShopVoucherInfo.VoucherListBean> voucherList = data.getVoucher_list();
                if (null != voucherList) {
                    DataBindItemViewHolderManager manager = new DataBindItemViewHolderManager(activity,
                            R.layout.item_voucher, BR.data);
                    manager.setOnClickListener(this::getReceive);
                    xBottomSheet = new XBottomSheet.BottomListSheetBuilder(activity)
                            .setItemData(voucherList)
                            .setAdapter(new DelegateAdapter.Builder<>()
                                    .bind(ShopVoucherInfo.VoucherListBean.class, manager)
                                    .build())
                            .setLayoutManager(new LinearLayoutManager(activity))
                            .setOnSheetItemClickListener((dialog, itemView, position, tag) -> dialog.dismiss()).build();
                    xBottomSheet.show();
                } else {
                    ToastUtils.showLong("暂时没有代金券");
                }
            } else {
                ToastUtils.showShort(result.toString());
            }

        });
        /*领取代金券*/
        registerObserver(Constants.CART_GETVOUCHERTPL, String.class)
                .observe(this, response -> {
                    xBottomSheet.dismiss();
                    if (ARouterConfig.SUCCESS.equals(response)) {
                        ToastUtils.showLong("领取成功");
                    } else {
                        ToastUtils.showLong(response);
                    }
                });

        LiveBus.getDefault().subscribe(ARouterConfig.LOGIN_SUCCESS).observeForever(isLogin -> {
            KLog.i("登陆成功，刷新数据");
            reGetData();
        });
        LiveBus.getDefault().subscribe(ARouterConfig.LOGIN_OUT).observeForever(isLogin -> {
            KLog.i("退出登录，清除数据");
            if (shopCartAdapter != null) {
                shopCartAdapter.setNewData(new ArrayList<>());
            }
        });
        registerObserver("SHOP_CART_REFRESH", Boolean.class).observeForever(isOk -> {
            Log.i("购物车", "onChanged: 重获数据");
            if (isOk) {
                reGetData();
            }
        });
        registerObserver(EVENT_KEY + Constants.SHOP_CART_LIST, Object.class).observeForever(result -> {
            //获取购物车数据并处理
            if (result instanceof ShopCartBean) {
                formatData((ShopCartBean) result);
                binding.refreshLayout.finishRefresh();
            } else {
                ToastUtils.showShort(result.toString());
                binding.refreshLayout.finishRefresh(false);
            }
        });
        registerObserver(Constants.CART_QUANTITY, CartQuantityState.class).observeForever(result -> {
            //购物车商品数量增加和减少
            if (result.isSuccess()) {
                changeGoodsCount(result.getPosition(), result.getQuantity());
            } else {
                if (TextUtils.equals("参数错误", result.getMsg())) {
                    lazyLoad();
                }
                ToastUtils.showShort(result.getMsg());
            }
        });
        registerObserver(Constants.CART_DELETE, CartQuantityState.class).observeForever(result -> {
            //购物车商品删除
            if (result.isSuccess()) {
                ToastUtils.showShort("删除成功");
            } else {
                ToastUtils.showShort(result.getMsg());
            }
            lazyLoad();
        });
    }

    /*领取代金券*/
    public void getReceive(View view) {
        /*String tId = (String) view.getTag();*/
        mViewModel.getVoucherFreeex((String) view.getTag(), Constants.CART_GETVOUCHERTPL);
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
//        binding.clPayContent.setVisibility(SharePreferenceUtil.isLogin() ? View.VISIBLE : View.GONE);
        if (!SharePreferenceUtil.isLogin() && shopCartAdapter.getItemCount() > 0) {
            shopCartAdapter.setNewData(new ArrayList<>());
        } else {
            binding.clPayContent.setVisibility(shopCartAdapter.getItemCount() > 1 ? View.VISIBLE : View.GONE);
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
