package com.mingpinmall.classz.ui.activity.details;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.BuildConfig;
import com.goldze.common.dmvvm.base.bean.HorizontalTabTitle;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.utils.log.QLog;
import com.goldze.common.dmvvm.widget.dialog.TextDialog;
import com.mingpinmall.apppay.pay.JPayListener;
import com.mingpinmall.apppay.pay.WeiXinBaoStrategy;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.adapter.FragmentPagerAdapter;
import com.mingpinmall.classz.databinding.ActivityShoppingDetailsBinding;
import com.mingpinmall.classz.db.utils.ShoppingCartUtils;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.CartCountInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsDetailInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.mingpinmall.classz.utils.ArgbEvaluator;
import com.mingpinmall.classz.widget.MenuPopupWindow;
import com.mingpinmall.classz.widget.XBottomSheet;

import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;
import com.xuexiang.xui.widget.popupwindow.popup.XUISimplePopup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mingpinmall.classz.widget.XBottomSheet.BottomGridSheetBuilder.FIRST_LINE;

/**
 * 商品详情
 */
@Route(path = ARouterConfig.home.SHOPPINGDETAILSACTIVITY)
public class ShoppingDetailsActivity extends AbsLifecycleActivity<ActivityShoppingDetailsBinding, ClassifyViewModel> implements JPayListener {

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
    protected int getLayoutId() {
        return R.layout.activity_shopping_details;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);
        setTitlePadding(binding.rlTop);
        addListener();
    }

    private void addListener() {
        //ViewPager的滑动监听，改变顶部tabbar状态的。
        binding.vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                if (i == 0) {
                    int alpha;
                    if (!mGoodsInfoMainFragment.trans) {
                        //80%不透明
                        alpha = (int) (v * (255 - 204)) + 204;
                        binding.tabs.setVisibility(View.VISIBLE);
                        binding.line.setVisibility(View.VISIBLE);
                    } else {
                        alpha = (int) (v * 255);
                        binding.tabs.setVisibility(v > 0.1 ? View.VISIBLE : View.GONE);
                        binding.line.setVisibility(v > 0.1 ? View.VISIBLE : View.GONE);
                        binding.tabs.setAlpha(v);
                        binding.line.setAlpha(v);
                    }
                    binding.rlTop.setBackgroundColor(Color.argb(alpha, 255, 255, 255));
                }
            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    if (mGoodsInfoMainFragment.trans) {
                        binding.tabs.setVisibility(View.GONE);
                        binding.line.setVisibility(View.GONE);
                    }
                } else {
                    binding.tabs.setVisibility(View.VISIBLE);
                    binding.line.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.GOODSDETAIL_EVENT_KEY[1] + id;
    }

    @Override
    protected void initData() {
        super.initData();
        SharePreferenceUtil.saveKeyValue("SHOPPINGDETAILSACTIVITY_ID", id);
        SharePreferenceUtil.saveKeyValue("click_goods_num", "1");
        mViewModel.getGoodsDetail(id);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();

        registerObserver(Constants.GOODSDETAIL_EVENT_KEY[0] + id, GoodsDetailInfo.class)
                .observeForever(response -> {
                    /*QLog.i(response.isSuccess() + " 刷新数据");*/
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
                        /*QLog.i("刷新数据");*/
                    } else {
                        showErrorState();
                    }
                });

        /*商品规格*/
        registerObserver("GOODSSPECIFICATIONPOP_VAL", "GOODSSPECIFICATIONPOP_VAL")
                .observe(this, s -> {
                    id = s.toString();
                    QLog.i(s.toString() + " ===");
                    registerObserver();
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

        /*获取购物车数量*/
        registerObserver(Constants.GOODSDETAIL_EVENT_KEY[2], CartCountInfo.class)
                .observe(this, obj -> {
                    setCartNumber(obj.getCart_count());
                    /*购物车的数量*/
                    SharePreferenceUtil.saveIntKeyValue("CART_NUMBER", obj.getCart_count());
                });

        registerObserver(Constants.GOODSDETAIL_EVENT_KEY[2] + "Error", String.class)
                .observe(this, s -> setCartNumber(SharePreferenceUtil.isLogin() ? ShoppingCartUtils.getCartCount() : 0));
    }

    void registerObserver() {
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCartNumber();
//        mViewModel.getMemberCart(Constants.GOODSDETAIL_EVENT_KEY[2]);
    }

    public void finish(View view) {
        onClick(view);
    }

    XUISimplePopup mMenuPopup;

    /*三个点*/
    public void points(View view) {
        QLog.i("三个点");
        if (null == mMenuPopup) {
            mMenuPopup = new XUISimplePopup(this, new AdapterItem[]{
                    new AdapterItem("搜  索", R.drawable.search),
                    new AdapterItem("购物车", R.drawable.icon_cart),
                    new AdapterItem("消  息", R.drawable.ic_message)})
                    .create((adapter, item, position) -> {
                        /*ToastUtils.showLong(item.getTitle().toString());*/
                        switch (item.getTitle().toString()) {
                            case "搜  索":
                                ActivityToActivity.toActivity(ARouterConfig.home.SEARCHACTIVITY);
                                break;
                            case "购物车":
                                ActivityToActivity.toActivity(SharePreferenceUtil.isLogin() ? ARouterConfig.cart.SHOPCARTACTIVITY : ARouterConfig.LOGINACTIVITY);
                                break;
                            case "消  息":
                                ActivityToActivity.toActivity(SharePreferenceUtil.isLogin() ? ARouterConfig.Me.MESSAGEACTIVITY : ARouterConfig.LOGINACTIVITY);
                                break;
                        }
                    });
        }
//        mMenuPopup.showDown(view);

        if (null == menuPopupWindow) {
            List<AdapterItem> items = Arrays.asList(
                    new AdapterItem("搜   索", R.drawable.search),
                    new AdapterItem("购物车", R.drawable.icon_cart),
                    new AdapterItem("消   息", R.drawable.ic_message));
            menuPopupWindow = new MenuPopupWindow
                    .Builder(this)
                    .setItem(items)
                    .setOnItemClickListener((v, position) -> {
                        menuPopupWindow.dismiss();
                        switch (position) {
                            case 0:
                                ActivityToActivity.toActivity(ARouterConfig.home.SEARCHACTIVITY);
                                break;
                            case 1:
                                ActivityToActivity.toActivity(SharePreferenceUtil.isLogin() ? ARouterConfig.cart.SHOPCARTACTIVITY : ARouterConfig.LOGINACTIVITY);
                                break;
                            case 2:
                                ActivityToActivity.toActivity(SharePreferenceUtil.isLogin() ? ARouterConfig.Me.MESSAGEACTIVITY : ARouterConfig.LOGINACTIVITY);
                                break;
                        }
                    })
                    .build().createPop(view);
        }
        menuPopupWindow.showAsDropDown(view);
    }

    MenuPopupWindow menuPopupWindow;

    /*分享*/
    public void share(View view) {
        QLog.i(mGoodsInfo.getGoods_image_url() + " " + mGoodsInfo.getGoods_jingle());

        XBottomSheet bottomSheet = new XBottomSheet.BottomGridSheetBuilder(activity)
                .addItem(R.drawable.icon_wx_logo, "微信", "微信", FIRST_LINE)
                .addItem(R.drawable.icon_moments, "朋友圈", "朋友圈", FIRST_LINE)
                .addItem(R.drawable.icon_collection, "收藏", "收藏", BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.drawable.icon_copy, "复制", "复制", BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .setOnSheetItemClickListener((dialog, itemView) -> {
                    QLog.i("TAG" + itemView.getTag());
                    WeiXinBaoStrategy weiXinBaoStrategy = WeiXinBaoStrategy.getInstance(this);
                    Map<String, String> map = new HashMap<>();
                    String url = String.format("%s/wap/tmpl/product_detail.html?goods_id=%s", BuildConfig.APP_URL, mGoodsInfo.getGoods_id());
                    if (null != mGoodsInfo) {
                        map.put("url", url);
                        map.put("title", mGoodsInfo.getGoods_name());
                        map.put("description", mGoodsInfo.getGoods_jingle());
                        map.put("imageurl", mGoodsInfo.getGoods_image_url());
                    }
                    switch (itemView.getTag().toString()) {
                        case "微信":
                            weiXinBaoStrategy.wechatShare("wxc18a7a67aae81510", 0, map, this);
                            break;
                        case "朋友圈":
                            weiXinBaoStrategy.wechatShare("wxc18a7a67aae81510", 1, map, this);
                            break;
                        case "复制":
                            copy(url);
                            break;
                        case "收藏":
                            weiXinBaoStrategy.wechatShare("wxc18a7a67aae81510", 2, map, this);
                            break;
                    }
                    dialog.dismiss();
                })
                .build();
        bottomSheet.show();
    }

    private void copy(String url) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(url);
        Toast.makeText(this, "复制成功，可以发给朋友们了。", Toast.LENGTH_LONG).show();
    }

    public void goCart(View view) {
        ActivityToActivity.toActivity(ARouterConfig.cart.SHOPCARTACTIVITY);
    }

    public void addCart(View view) {
        /*QLog.i("添加到购物车");*/
        id = SharePreferenceUtil.getKeyValue("SHOPPINGDETAILSACTIVITY_ID");
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
        id = SharePreferenceUtil.getKeyValue("SHOPPINGDETAILSACTIVITY_ID");
        QLog.i(id + " ==");
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
            QLog.i(e.toString());
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

    /*试戴*/
    public void goHolo(View view) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("goods_id", mGoodsInfo.getGoods_id());
        params.put("cart_count", binding.tvCount.getText().toString());

//        ActivityToActivity.toActivity(ARouterConfig.classify.HOLO4ACTIVITY, params);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            TextDialog.showBaseDialog(activity, "", "您的手机系统版本过低，不支持试戴功能").show();
            return;
        }
        ActivityToActivity.toActivity(ARouterConfig.classify.HOLO2ACTIVITY, "url", mGoodsInfo.getTryon_url());
//        ActivityToActivity.toActivity(ARouterConfig.classify.HOLOACTIVITY, "url", mGoodsInfo.getTryon_url());
    }

    /*领取代金券*/
    public void getReceive(View view) {
        mViewModel.getVoucherFreeex((String) view.getTag(), Constants.VOUCHER[2]);
    }

    /**
     * 设置购物车数量
     */
    public void setCartNumber() {
        if (SharePreferenceUtil.isLogin()) {
            mViewModel.getMemberCart(Constants.GOODSDETAIL_EVENT_KEY[2]);
        }
    }

    private void setCartNumber(int count) {
        if (count > 0) {
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
        if (null != mGoodsInfoMainFragment) mGoodsInfoMainFragment.setDrawerImage(bitmap);
    }

    /**
     * 切换Fragment
     *
     * @param position position
     */
    public void setCurrentFragment(int position) {
        binding.vpContent.setCurrentItem(position);
    }

    @Override
    public void onPaySuccess() {
        QLog.i("成功");
    }

    @Override
    public void onPayError(int error_code, String message) {
        /*QLog.i(error_code + "==" + message);*/
        ToastUtils.showLong(message);
    }

    @Override
    public void onPayCancel() {
        QLog.i("取消");
    }

    @Override
    public void onUUPay(String dataOrg, String sign, String mode) {

    }
}