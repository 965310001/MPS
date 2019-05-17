package com.mingpinmall.classz.ui.activity.details;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.text.HtmlCompat;
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
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.HtmlFromUtils;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.StatusBarUtils;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.SlideLayout;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ResultBean;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.adapter.GoodsCommentAdapter;
import com.mingpinmall.classz.databinding.FragmentGoodsInfoMainBinding;
import com.mingpinmall.classz.databinding.ItemGoodsDescBinding;
import com.mingpinmall.classz.db.utils.ShoppingCartUtils;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.GoodsComment;
import com.mingpinmall.classz.ui.vm.bean.GoodsDetailInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.mingpinmall.classz.widget.GoodsSpecificationPop;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.socks.library.KLog;

import java.util.Arrays;
import java.util.List;

import static android.support.v4.text.HtmlCompat.FROM_HTML_MODE_COMPACT;

public class GoodsInfoMainFragment extends AbsLifecycleFragment<FragmentGoodsInfoMainBinding, ClassifyViewModel> implements SlideLayout.OnSlideDetailsListener {
    /**
     * 当前商品详情数据页的索引分别是图文详情、规格参数
     */
    private GoodsInfo goodsInfo;
    private GoodsDetailInfo goodsDetailInfo;
    private ShoppingDetailsActivity shoppingDetailsActivity;
    private GoodsSpecificationPop specificationPop;
    private int bannerHight = 0;

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

    public boolean trans = true;

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        bannerHight = ScreenUtil.getScreenWidth(activity) / 2;
        Log.d("屏幕宽度的一半", "initView: " + bannerHight);
        binding.svSwitch.setOnSlideDetailsListener(this);

        goodsDetailInfo = ((ShoppingDetailsActivity) activity).getGoodsDetailInfo();
        goodsInfo = goodsDetailInfo.getDatas().getGoods_info();
        KLog.i(goodsInfo + "=====");
        setGoodsInfo();

        //滑动监听
        binding.svGoodsInfo.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    Log.d(TAG, "initView: " + scrollY + " , " + bannerHight);
                    if (!isVisible()) {
                        return;
                    }
                    if (scrollY > bannerHight) {
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

        binding.lsiGoodsSpecification.setmOnLSettingItemClick(isChecked -> {
            if (null == specificationPop) {
                specificationPop = GoodsSpecificationPop.getInstance(getContext());
            }
            specificationPop.setGoodsInfo(goodsInfo);
            specificationPop.show(binding.getRoot());
            SharePreferenceUtil.saveKeyValue("SPECIFICATIONPOP", "SPECIFICATIONPOP");
        });
        binding.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        /*满级送*/
        GoodsDetailInfo.DatasBean.MansongInfoBean mansong_info = goodsDetailInfo.getDatas().getMansong_info();
        try {
            if (mansong_info != null && mansong_info.getRules() != null && mansong_info.getRules().size() > 0) {
                TextView textView;
                for (final GoodsDetailInfo.DatasBean.MansongInfoBean.RulesBean rule : mansong_info.getRules()) {
                    textView = new TextView(activity);
                    //fromHtml(String source, int flags)
                    //flags表示：
                    //
                    //FROM_HTML_MODE_COMPACT：html块元素之间使用一个换行符分隔
                    //FROM_HTML_MODE_LEGACY：html块元素之间使用两个换行符分隔
                    String content = String.format("单笔订单满<font color='#000000'>%s</font>元,立减<font color='#000000'>%s</font>元",
                            rule.getPrice(), rule.getDiscount());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        textView.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        textView.setText(Html.fromHtml(content));
                    }
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
            /*赠品*/
            if (goodsDetailInfo.getDatas().getGift_array() != null && goodsDetailInfo.getDatas().getGift_array().size() > 0) {
                SpannableString spannableString;
                TextView textView;
                for (final GoodsDetailInfo.DatasBean.GiftArrayBean giftArrayBean : goodsDetailInfo.getDatas().getGift_array()) {
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
                            //设置下划线
                            ds.setUnderlineText(true);
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
                binding.tvPromotion.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            KLog.i(e.toString());
        }

    }

    protected void update() {
        goodsDetailInfo = ((ShoppingDetailsActivity) activity).getGoodsDetailInfo();
        goodsInfo = goodsDetailInfo.getDatas().getGoods_info();
        KLog.i(goodsInfo + "=====");
        setGoodsInfo();
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

    /**
     * 设置商品头图 轮播
     */
    private void setGoodsHeadImg() {
        if (goodsDetailInfo != null) {
            /*优化*/
            String goods_image = goodsDetailInfo.getDatas().getGoods_image();
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
                        //点击事件
                    });


        }
    }

    /**
     * 设置商品信息
     */
    private void setGoodsInfo() {
        setGoodsHeadImg();
        if (goodsInfo != null) {
            binding.ivLike.setBackgroundResource(!goodsInfo.isfavorate() ? R.drawable.ic_me_favorite : R.drawable.ic_me_favorite_red);
            GoodsDetailInfo.DatasBean.StoreInfoBean storeInfo = goodsDetailInfo.getDatas().getStore_info();
            binding.lsiItem.setLeftText(storeInfo.getStore_name());
            binding.lsiItem.setmOnLSettingItemClick(isChecked -> {
                // TODO: 2019/4/2 品牌网自营
                KLog.i("点击");
                ActivityToActivity.toActivity(ARouterConfig.classify.STOREACTIVITY, "storeId", goodsDetailInfo.getDatas().getStore_info().getStore_id());
            });
            /*描述*/
            showDesc(storeInfo);
            if (null != goodsDetailInfo.getDatas().getGoods_evaluate_info()) {
                binding.lsiComment.setLeftText(String.format("用户点评(%s)", goodsDetailInfo.getDatas().getGoods_evaluate_info().getAll()));
            }
            commentList(goodsDetailInfo.getDatas().getGoods_eval_list());

            binding.lsiComment.setmOnLSettingItemClick(isChecked -> shoppingDetailsActivity.setCurrentFragment(2));
            /*店铺推荐*/
            if (null != goodsDetailInfo.getDatas() && null != goodsDetailInfo.getDatas().getGoods_commend_list()) {
                binding.setList(goodsDetailInfo.getDatas().getGoods_commend_list());
                binding.setAdapter(AdapterPool.newInstance().getRecommend(getContext()).build());
                binding.setLayout(new GridLayoutManager(getContext(), 4));
            }
            // TODO: 2019/4/1  全国 有货 免运费
            KLog.i(goodsDetailInfo.getDatas().getGoods_hair_info().content + " " +
                    goodsDetailInfo.getDatas().getGoods_hair_info().if_store_cn +
                    goodsDetailInfo.getDatas().getGoods_hair_info().area_name);
            binding.setData(goodsInfo);
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
//        TextView tvEmptyComment = binding.getRoot().findViewById(R.id.tv_empty_comment);
//        RecyclerView recyclerView = binding.getRoot().findViewById(R.id.recycle_view);
        RecyclerView recyclerView = binding.recycleView;
        recyclerView.setNestedScrollingEnabled(false);
        if (null != commentList && commentList.size() > 0) {
//            tvEmptyComment.setVisibility(View.GONE);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL));
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new GoodsCommentAdapter(getContext(), commentList));
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