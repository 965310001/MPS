package com.mingpinmall.classz.ui.activity.details;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.HtmlFromUtils;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.SlideLayout;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ResultBean;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.adapter.GoodsCommentAdapter;
import com.mingpinmall.classz.databinding.FragmentGoodsInfoMainBinding;
import com.mingpinmall.classz.databinding.ItemGoodsDescBinding;
import com.mingpinmall.classz.databinding.ItemTextBinding;
import com.mingpinmall.classz.db.utils.ShoppingCartUtils;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.GoodsComment;
import com.mingpinmall.classz.ui.vm.bean.GoodsDetailInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.mingpinmall.classz.widget.GoodsSpecificationPop;
import com.mingpinmall.classz.widget.XBottomSheet;
import com.socks.library.KLog;

import java.util.Arrays;
import java.util.List;

public class GoodsInfoMainFragment extends AbsLifecycleFragment<FragmentGoodsInfoMainBinding, ClassifyViewModel> implements SlideLayout.OnSlideDetailsListener {
    /**
     * 当前商品详情数据页的索引分别是图文详情、规格参数
     */
    private GoodsInfo goodsInfo;
    private GoodsDetailInfo goodsDetailInfo;
    private ShoppingDetailsActivity shoppingDetailsActivity;
    private GoodsSpecificationPop specificationPop;
    private int bannerHeight = 0;

    private GoodsDetailInfo.DatasBean dataBean;

    private XBottomSheet xBottomSheet;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        shoppingDetailsActivity = (ShoppingDetailsActivity) context;
    }

    public GoodsInfoMainFragment() {
    }

    public static GoodsInfoMainFragment newInstance() {
        return new GoodsInfoMainFragment();
    }

    public void setDrawerFragment(Fragment fragment) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_fragment, fragment)
                .commitNowAllowingStateLoss();
    }

    protected void setDrawerImage(Bitmap bitmap) {
        binding.ivDrawerImage.setImageBitmap(bitmap);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_goods_info_main;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    public boolean trans = true;

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        bannerHeight = ScreenUtil.getScreenWidth(activity) / 2;
        setTitlePadding(binding.rlEmpty);

        setGoodsDetailInfo();
        setListener();
    }

    private void setGoodsDetailInfo() {
        goodsDetailInfo = ((ShoppingDetailsActivity) activity).getGoodsDetailInfo();
        if (null != goodsDetailInfo) {
            dataBean = goodsDetailInfo.getDatas();
            if (null != dataBean) {
                goodsInfo = dataBean.getGoods_info();
                setGoodsInfo();
                setMansongInfo();
            }
        } else {
            ToastUtils.showLong("服务器异常，请稍后再试");
        }
    }


    /**
     * 设置商品信息
     */
    private void setGoodsInfo() {
        setGoodsHeadImg();
        if (goodsInfo != null) {
            binding.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            binding.ivLike.setBackgroundResource(!goodsInfo.isfavorate() ? R.drawable.ic_me_favorite : R.drawable.ic_me_favorite_red);
            GoodsDetailInfo.DatasBean.StoreInfoBean storeInfo = dataBean.getStore_info();
            binding.lsiItem.setLeftText(storeInfo.getStore_name());
            /*描述*/
            showDesc(storeInfo);
            if (null != dataBean.getGoods_evaluate_info()) {
                binding.lsiComment.setLeftText(String.format("用户点评(%s)", dataBean.getGoods_evaluate_info().getAll()));
            }

            commentList(dataBean.getGoods_eval_list());

            /*店铺推荐*/
            if (null != dataBean && null != dataBean.getGoods_commend_list()) {
                binding.setList(dataBean.getGoods_commend_list());
                binding.setAdapter(AdapterPool.newInstance().getRecommend(getContext()).build());
                binding.setLayout(new GridLayoutManager(getContext(), 4));
            }
            // TODO: 2019/4/1  全国 有货 免运费
            KLog.i(dataBean.getGoods_hair_info().content + " " +
                    dataBean.getGoods_hair_info().if_store_cn +
                    dataBean.getGoods_hair_info().area_name);
            binding.setData(goodsInfo);

            List<GoodsInfo.NewsContractlistBean> newsContractlist = goodsInfo.getNews_contractlist();
            if (null != newsContractlist) {
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(String.format("由“%s”销售和发货，并享受售后服务\n", dataBean.getStore_info().getStore_name()));
                    for (GoodsInfo.NewsContractlistBean contractlistBean : newsContractlist) {
//                    stringBuilder.append(String.format("[%s] <color='#000000'>%s</color> ", contractlistBean.getCti_icon_url(),
                        stringBuilder.append(String.format("[%s] %s ", contractlistBean.getCti_icon_url(),
                                contractlistBean.getCti_name()));
                    }
                    HtmlFromUtils.setImageFromNetWork(activity, binding.tvService,
                            stringBuilder.toString(), false);
                    binding.llService.setVisibility(View.VISIBLE);
                    KLog.i(stringBuilder.toString());
                    if (null == newsContractlist || newsContractlist.size() == 0) {
                        binding.llService.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    KLog.i(e.toString());
                }
            } else {
                binding.llService.setVisibility(View.GONE);
            }

            ItemTextBinding textBinding;
            int size = goodsInfo.news_goods_spec_name.size();
            binding.llGoodsSpecification.removeAllViews();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    textBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.item_text, null, false);
                    textBinding.text.setTextSize(14f);
                    textBinding.text.setPadding(4, 4, 4, 4);
                    textBinding.text.setTextColor(getResources().getColor(R.color.gray));
                    textBinding.text.setBackgroundResource(R.drawable.shape_bg_solid_attr);
                    textBinding.setData(String.format("%s %s", goodsInfo.news_goods_spec_name.get(i), goodsInfo.news_goods_spec.get(i)));
                    binding.llGoodsSpecification.addView(textBinding.getRoot());
                }
            } else {
                textBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.item_text, null, false);
                textBinding.text.setTextSize(14f);
                textBinding.text.setPadding(8, 6, 8, 6);
                textBinding.text.setTextColor(getResources().getColor(R.color.black));
                textBinding.text.setBackgroundResource(R.drawable.shape_bg_solid_attr);
                textBinding.setData("默认");
                binding.llGoodsSpecification.addView(textBinding.getRoot());
            }
        }
//        KLog.i(dataBean.isVoucher() + "==");
        binding.setIsVoucher(dataBean.isVoucher());
    }

    /**
     * 设置商品头图 轮播
     */
    private void setGoodsHeadImg() {
        String goods_image = dataBean.getGoods_image();
        if (!TextUtils.isEmpty(goods_image)) {
            List<String> list;
            if (goods_image.contains(",")) {
                goodsInfo.setGoods_image_url(goods_image.split(",")[0]);
                list = Arrays.asList(goods_image.split(","));
            } else {
                goodsInfo.setGoods_image_url(goods_image);
                list = Arrays.asList(goods_image);
            }
            ImageUtils.loadBanner(binding.vpItemGoodsImg,
                    list,
                    position -> {
                    });
        }
    }

    private void setMansongInfo() {
        /*满级送*/
        try {
            if (null != dataBean) {
                GoodsDetailInfo.DatasBean.MansongInfoBean mansongInfo = dataBean.getMansong_info();
                if (mansongInfo != null && mansongInfo.getRules() != null && mansongInfo.getRules().size() > 0) {
                    TextView textView;
                    for (final GoodsDetailInfo.DatasBean.MansongInfoBean.RulesBean rule : mansongInfo.getRules()) {
                        textView = new TextView(activity);
                        textView.setText("");
                        textView.setText(Html.fromHtml(String.format("单笔订单满<font color='#000000'>%s</font>元,立减<font color='#000000'>%s</font>元",
                                rule.getPrice(), rule.getDiscount())));
                        if (!TextUtils.isEmpty(rule.getGoods_image_url())) {
                            HtmlFromUtils.setImageFromNetWork(textView.getContext(), textView,
                                    String.format(" 送礼品：[%s]", rule.getGoods_image_url()), true);
                        }
                        binding.llManjisong.addView(textView);
                        binding.tvPromotion.setVisibility(View.VISIBLE);
                    }
                } else {
                    binding.llManjisong.setVisibility(View.GONE);
                    binding.tvPromotion.setVisibility(View.GONE);
                }

                List<GoodsDetailInfo.DatasBean.GiftArrayBean> giftArray = dataBean.getGift_array();
                if (giftArray != null && giftArray.size() > 0) {
                    SpannableString spannableString;
                    TextView textView;
                    for (final GoodsDetailInfo.DatasBean.GiftArrayBean giftArrayBean : dataBean.getGift_array()) {
                        textView = new TextView(activity);
                        spannableString = new SpannableString(String.format("%s x %s", giftArrayBean.getGift_goodsname(),
                                giftArrayBean.getGift_amount()));
                        ClickableSpan clickableSpan = new ClickableSpan() {
                            @Override
                            public void onClick(@NonNull View widget) {
                                LiveBus.getDefault().postEvent("GOODSSPECIFICATIONPOP_VAL", "GOODSSPECIFICATIONPOP_VAL",
                                        giftArrayBean.getGift_goodsid());
                            }

                            @Override
                            public void updateDrawState(@NonNull TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setColor(Color.GRAY);
                                ds.setUnderlineText(true);      //设置下划线
                            }
                        };
                        spannableString.setSpan(clickableSpan, 0, giftArrayBean.getGift_goodsname().length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                        textView.setText(spannableString);
                        textView.setMovementMethod(LinkMovementMethod.getInstance());
                        binding.llZengping.addView(textView);
                        binding.tvPromotion.setVisibility(View.VISIBLE);
                    }
                } else {
                    binding.llZengping.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            KLog.i(e.toString());
            ToastUtils.showLong(e.toString());
        }
    }

    private void setListener() {
        binding.svSwitch.setOnSlideDetailsListener(this);

        binding.llGoodsSpecification.setOnClickListener(v -> showBuyPopWindow());

        //滑动监听
        binding.svGoodsInfo.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    Log.d(TAG, "initView: " + scrollY + " , " + bannerHeight);
                    if (!isVisible()) {
                        return;
                    }
                    if (scrollY > bannerHeight) {
                        if (trans) {
                            ((ShoppingDetailsActivity) activity).setTitleBarState(1);
                        }
                        trans = false;
                    } else {
                        if (!trans) {
                            ((ShoppingDetailsActivity) activity).setTitleBarState(2);
                        }
                        trans = true;
                    }
                });
        /*领取代金券*/
        binding.llVoucher.setOnClickListener(v -> {
            // TODO: 2019/4/29 测试
            if (null != dataBean.getVoucher()) {
                if (null == xBottomSheet) {
                    xBottomSheet = new XBottomSheet.BottomListSheetBuilder(activity)
                            .setItemData(dataBean.getVoucher())
                            .setAdapter(AdapterPool.newInstance()
                                    .getVoucherInfoAdapter(activity)
                                    .build())
                            .setLayoutManager(new LinearLayoutManager(activity))
                            .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                                dialog.dismiss();
                                ToastUtils.showLong("Item " + (position + 1));
                            })
                            .build();
                }
                xBottomSheet.show();
            } else {
                ToastUtils.showLong("暂时没有代金券");
            }
        });
        binding.lsiComment.setmOnLSettingItemClick(isChecked -> shoppingDetailsActivity.setCurrentFragment(2));

        binding.lsiItem.setmOnLSettingItemClick(isChecked -> {
            // TODO: 2019/4/2 品牌网自营
            KLog.i("点击");
            ActivityToActivity.toActivity(ARouterConfig.classify.STOREACTIVITY, "storeId", dataBean.getStore_info().getStore_id());
        });

    }

    protected void update() {
        setGoodsDetailInfo();
    }

    protected void updateSpecificationPop() {
        if (null == goodsInfo) {
            goodsDetailInfo = ((ShoppingDetailsActivity) activity).getGoodsDetailInfo();
            if (null != goodsDetailInfo) {
                dataBean = goodsDetailInfo.getDatas();
                if (null != dataBean) {
                    goodsInfo = dataBean.getGoods_info();
                }
            }
        }
        specificationPop.setGoodsInfo(goodsInfo);
        specificationPop.loadData();
    }

    protected void favorites() {
        mViewModel.favorites(((ShoppingDetailsActivity) activity).getId(), goodsInfo.isfavorate(),
                Constants.FAVORITES);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();

        // TODO: 2019/4/2 收藏
        registerObserver(Constants.FAVORITES, ResultBean.class)
                .observeForever(response -> {
                    KLog.i(response.isSuccess() + " " + response.getError());
                    if (response.isSuccess()) {
                        /*ToastUtils.showLong(goodsInfo.isfavorate() ? "取消收藏成功" : "添加收藏成功");*/
                        if (null == goodsInfo) {
                            goodsDetailInfo = ((ShoppingDetailsActivity) activity).getGoodsDetailInfo();
                            if (null != goodsDetailInfo) {
                                dataBean = goodsDetailInfo.getDatas();
                                if (null != dataBean) {
                                    goodsInfo = dataBean.getGoods_info();
                                }
                            }
                        }
                        binding.ivLike.setBackgroundResource(goodsInfo.isfavorate() ? R.drawable.ic_me_favorite : R.drawable.ic_me_favorite_red);
                        goodsInfo.setfavorate(!goodsInfo.isfavorate());
                        binding.executePendingBindings();
                    } else {
                        ToastUtils.showLong(response.getError());
                    }
                });

        /*添加购物车*/
        registerObserver(Constants.CART_EVENT_KEY, ResultBean.class)
                .observeForever(response -> {
                    KLog.i(response.isSuccess() + " " + response.getError());
                    if (response.isSuccess()) {
                        /*String goodsNum = SharePreferenceUtil.getKeyValue("click_goods_num");*/
//                        goodsInfo.setNum(Integer.valueOf(goodsNum));
                        ShoppingCartUtils.addCartGoods(goodsInfo);
                        SharePreferenceUtil.saveKeyValue("click_goods_num", "");
                        shoppingDetailsActivity.setCartNumber();
                        ToastUtils.showLong("添加购物车成功");
                        LiveBus.getDefault().postEvent("SHOP_CART_REFRESH", true);
                        popWindowDismiss();
                    } else {
                        if (!response.isLogin()) {
                            ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
                            ToastUtils.showLong(response.getError());
                        } else {
                            ToastUtils.showLong(response.getError());
                        }
                    }
                    KLog.i(response);
                });

        registerObserver(Constants.VOUCHER[2], ResultBean.class)
                .observe(this, response -> {
                    xBottomSheet.dismiss();
                    if (response.isSuccess()) {
                        ToastUtils.showLong("领取成功");
                    } else {
                        ToastUtils.showLong(response.getError());
                    }
                });

    }

    /*关闭 dismiss*/
    public void popWindowDismiss() {
        if (null != specificationPop && specificationPop.isShowing()) {
            specificationPop.dismiss();
        }
    }

    public boolean isPopWindowDismiss() {
        if (null == specificationPop) {
            return false;
        }
        return specificationPop.isShowing();
    }

    public void showBuyPopWindow() {
        if (null == specificationPop) {
            specificationPop = GoodsSpecificationPop.getInstance(getContext());
        }
        specificationPop.setGoodsInfo(goodsInfo);
        specificationPop.show(binding.getRoot());
        SharePreferenceUtil.saveKeyValue("SPECIFICATIONPOP", "SPECIFICATIONPOP");
    }

    @Override
    protected Object getStateEventKey() {
        return "";
    }

    @Override
    public void onStateChanged(SlideLayout.Status status) {
        if (shoppingDetailsActivity != null) {
            shoppingDetailsActivity.scroll2Shopinfo();
            binding.svGoodsInfo.smoothScrollTo(0, 0);
            binding.svSwitch.smoothClose(true);
        }
    }

    private void showDesc(GoodsDetailInfo.DatasBean.StoreInfoBean storeInfo) {
        List<GoodsDetailInfo.DatasBean.StoreInfoBean.NewStoreCreditBean> list = storeInfo.getStore_credit().getList();
        if (null != storeInfo.getStore_credit() && list != null && !storeInfo.getIs_own_mall().equals("1")) {
            if (binding.llAddDesc.getChildCount() > 0) {
                binding.llAddDesc.removeAllViews();
            }
            binding.setIsOwnMall(!storeInfo.getIs_own_mall().equals("1"));
            ItemGoodsDescBinding itemGoodsDescBinding;
            for (GoodsDetailInfo.DatasBean.StoreInfoBean.NewStoreCreditBean data : list) {
                itemGoodsDescBinding = DataBindingUtil.bind(LayoutInflater.from(activity).inflate(R.layout.item_goods_desc, null));
                itemGoodsDescBinding.setText(data.text);
                itemGoodsDescBinding.setCredit(data.credit);
                itemGoodsDescBinding.setPercentText(data.percent_text);
                itemGoodsDescBinding.executePendingBindings();
                binding.llAddDesc.addView(itemGoodsDescBinding.getRoot());
            }
        } else {
            binding.llAddDesc.setVisibility(View.GONE);
        }
    }

    private void commentList(List<GoodsComment> commentList) {
        RecyclerView recyclerView = binding.recycleView;
        recyclerView.setNestedScrollingEnabled(false);
        if (null != commentList && commentList.size() > 0) {
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL));
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new GoodsCommentAdapter(getContext(), commentList));
        } else {
            recyclerView.setVisibility(View.GONE);
        }
    }
}