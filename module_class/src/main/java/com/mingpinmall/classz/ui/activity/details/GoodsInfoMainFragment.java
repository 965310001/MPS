package com.mingpinmall.classz.ui.activity.details;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.SlideLayout;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ResultBean;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.adapter.CustomDefaultFlowTagAdapter;
import com.mingpinmall.classz.adapter.GoodsCommentAdapter;
import com.mingpinmall.classz.databinding.FragmentGoodsInfoMainBinding;
import com.mingpinmall.classz.databinding.ItemGoodsDescBinding;
import com.mingpinmall.classz.db.utils.ShoppingCartUtils;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.GoodsComment;
import com.mingpinmall.classz.ui.vm.bean.GoodsDetailInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.goldze.common.dmvvm.utils.HtmlFromUtils;
import com.mingpinmall.classz.widget.GoodsSpecificationPop;
import com.mingpinmall.classz.widget.XBottomSheet;
import com.socks.library.KLog;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;

import java.util.ArrayList;
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

    private GoodsDetailInfo.DatasBean datasBean;

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

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_goods_info_main;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);

        setGoodsDetailInfo();
        setListener();

        binding.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void setGoodsDetailInfo() {
        goodsDetailInfo = ((ShoppingDetailsActivity) activity).getGoodsDetailInfo();
        if (null != goodsDetailInfo) {
            datasBean = goodsDetailInfo.getDatas();
            if (null != datasBean) {
                goodsInfo = datasBean.getGoods_info();
                setGoodsInfo();
                setMansongInfo();
            } else {
                KLog.i("网络不好，请稍后再试");
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
            binding.ivLike.setBackgroundResource(!goodsInfo.isfavorate() ? R.drawable.ic_me_favorite : R.drawable.ic_me_favorite_red);
            GoodsDetailInfo.DatasBean.StoreInfoBean storeInfo = datasBean.getStore_info();
            binding.lsiItem.setLeftText(storeInfo.getStore_name());
            /*描述*/
            showDesc(storeInfo);
            if (null != datasBean.getGoods_evaluate_info()) {
                binding.lsiComment.setLeftText(String.format("用户点评(%s)", datasBean.getGoods_evaluate_info().getAll()));
            }

            commentList(datasBean.getGoods_eval_list());

            /*店铺推荐*/
            if (null != datasBean && null != datasBean.getGoods_commend_list()) {
                binding.setList(datasBean.getGoods_commend_list());
                binding.setAdapter(AdapterPool.newInstance().getRecommend(getContext()).build());
                binding.setLayout(new GridLayoutManager(getContext(), 4));
            }
            // TODO: 2019/4/1  全国 有货 免运费
            KLog.i(datasBean.getGoods_hair_info().content + " " +
                    datasBean.getGoods_hair_info().if_store_cn +
                    datasBean.getGoods_hair_info().area_name);
            binding.setData(goodsInfo);
        }
        binding.setIsVoucher(datasBean.isVoucher());
    }

    /**
     * 设置商品头图 轮播
     */
    private void setGoodsHeadImg() {
        String goods_image = datasBean.getGoods_image();
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
            if (null != datasBean) {
                GoodsDetailInfo.DatasBean.MansongInfoBean mansongInfo = datasBean.getMansong_info();
                if (mansongInfo != null && mansongInfo.getRules() != null && mansongInfo.getRules().size() > 0) {
                    TextView textView;
                    for (final GoodsDetailInfo.DatasBean.MansongInfoBean.RulesBean rule : mansongInfo.getRules()) {
                        textView = new TextView(activity);
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
                    KLog.i("TAGTAG");
                    binding.llManjisong.setVisibility(View.GONE);
                    binding.tvPromotion.setVisibility(View.GONE);
                }

                List<GoodsDetailInfo.DatasBean.GiftArrayBean> giftArray = datasBean.getGift_array();
                if (giftArray != null && giftArray.size() > 0) {
                    SpannableString spannableString;
                    TextView textView;
                    for (final GoodsDetailInfo.DatasBean.GiftArrayBean giftArrayBean : datasBean.getGift_array()) {
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
                        KLog.i("TAGTAG");
                    }
                } else {
                    binding.llZengping.setVisibility(View.GONE);
//                    binding.tvPromotion.setVisibility(View.GONE);
//                    KLog.i("TAGTAG");
                }
            }
        } catch (Exception e) {
            KLog.i(e.toString());
            ToastUtils.showLong(e.toString());
        }
    }

    private void setListener() {
        binding.svSwitch.setOnSlideDetailsListener(this);

        binding.lsiGoodsSpecification.setmOnLSettingItemClick(isChecked -> {
            if (null == specificationPop) {
                specificationPop = GoodsSpecificationPop.getInstance(getContext());
            }
            specificationPop.setGoodsInfo(goodsInfo);
            specificationPop.show(binding.getRoot());
            SharePreferenceUtil.saveKeyValue("SPECIFICATIONPOP", "SPECIFICATIONPOP");
        });

        /*领取代金券*/
        binding.llVoucher.setOnClickListener(v -> {
            // TODO: 2019/4/29 测试
            if (null != datasBean.getVoucher()) {
                if (null == xBottomSheet) {
                    xBottomSheet = new XBottomSheet.BottomListSheetBuilder(activity)
                            .setItemData(datasBean.getVoucher())
                            .setItemData(datasBean.getVoucher())
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
            ActivityToActivity.toActivity(ARouterConfig.classify.STOREACTIVITY, "storeId", datasBean.getStore_info().getStore_id());
        });

    }

    protected void update() {
       /* goodsDetailInfo = ((ShoppingDetailsActivity) activity).getGoodsDetailInfo();
        goodsInfo = datasBean.getGoods_info();
        KLog.i(goodsInfo + "=====");
        setGoodsInfo();*/

        setGoodsDetailInfo();
    }

    protected void updateSpecificationPop() {
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
                    if (response.isSuccess()) {
                        ShoppingCartUtils.addCartGoods(goodsInfo);
                        shoppingDetailsActivity.setCartNumber();
                        ToastUtils.showLong("添加购物车成功");
                        LiveBus.getDefault().postEvent("SHOP_CART_REFRESH", true);
                        if (null != specificationPop && specificationPop.isShowing()) {
                            specificationPop.dismiss();
                        }
                    } else {
                        if (response.isLogin()) {
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

    @Override
    protected Object getStateEventKey() {
        return "";
    }

    @Override
    public void onStateChanged(SlideLayout.Status status) {
        if (shoppingDetailsActivity != null) {
            shoppingDetailsActivity.setViewContent(status == SlideLayout.Status.OPEN);
            getChildFragmentManager().beginTransaction().replace(R.id.fl_fragment,
                    GoodsInfoDetailMainFragment.newInstance()).commitAllowingStateLoss();
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
        if (null != commentList && commentList.size() > 0) {
//            tvEmptyComment.setVisibility(View.GONE);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL));
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new GoodsCommentAdapter(getContext(), commentList));
        } else {
            recyclerView.setVisibility(View.GONE);
        }
//        else {
//            tvEmptyComment.setVisibility(View.GONE);
//            tvEmptyComment.setText("暂无精彩评论");
//        }
    }

//    /**
//     * 当前商品数量
//     *
//     * @return
//     */
//    public int getGoodsCount() {
//        return binding.ccvClick.getCount();
//    }
}