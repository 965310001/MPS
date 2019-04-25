package com.mingpinmall.classz.ui.activity.details;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.goldze.common.dmvvm.adapter.BannerImgAdapter;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.SlideLayout;
import com.leon.lib.settingview.LSettingItem;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ResultBean;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.adapter.GoodsCommentAdapter;
import com.mingpinmall.classz.db.utils.ShoppingCartUtils;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.databinding.FragmentGoodsInfoMainBinding;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.vm.bean.GoodsComment;
import com.mingpinmall.classz.ui.vm.bean.GoodsDetailInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.mingpinmall.classz.widget.GoodsSpecificationPop;
import com.socks.library.KLog;
import com.xuexiang.xui.widget.banner.widget.banner.base.ImageLoader;

import java.util.Arrays;
import java.util.List;

public class GoodsInfoMainFragment extends AbsLifecycleFragment<FragmentGoodsInfoMainBinding, ClassifyViewModel> implements SlideLayout.OnSlideDetailsListener {
    /**
     * 当前商品详情数据页的索引分别是图文详情、规格参数
     */
    GoodsInfo goodsInfo;
    GoodsDetailInfo goodsDetailInfo;
    private ShoppingDetailsActivity shoppingDetailsActivity;
    private GoodsSpecificationPop specificationPop;

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

    TextView textView;

    @Override
    public void initView(Bundle state) {
        super.initView(state);

        binding.svSwitch.setOnSlideDetailsListener(this);
        goodsDetailInfo = ((ShoppingDetailsActivity) activity).getGoodsDetailInfo();
        goodsInfo = goodsDetailInfo.getDatas().getGoods_info();
        KLog.i(goodsInfo + "=====");
        setGoodsInfo();
        binding.lsiGoodsSpecification.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                if (null == specificationPop) {
                    specificationPop = GoodsSpecificationPop.getInstance(getContext());
                }
                specificationPop.setGoodsInfo(goodsInfo);
                specificationPop.show(binding.getRoot());
                SharePreferenceUtil.saveKeyValue("SPECIFICATIONPOP", "SPECIFICATIONPOP");
            }
        });
        binding.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        /*满级送*/
        GoodsDetailInfo.DatasBean.MansongInfoBean mansong_info = goodsDetailInfo.getDatas().getMansong_info();
        try {

            if (mansong_info != null) {
                for (GoodsDetailInfo.DatasBean.MansongInfoBean.RulesBean rule : mansong_info.getRules()) {
                    textView = new TextView(activity);
                    textView.setText(Html.fromHtml(String.format("单笔订单满<font color='#000000'>%s</font>元,立减<font color='#000000'>%s</font>元",
                            rule.getPrice(), rule.getDiscount())));
                    if (!TextUtils.isEmpty(rule.getGoods_image_url())) {
                        /*网络图片*/
                        Html.ImageGetter imgGetter = new Html.ImageGetter() {
                            @Override
                            public Drawable getDrawable(String source) {
                                KLog.i(source);
                                final LevelListDrawable drawable = new LevelListDrawable();
//                                Glide.with(getContext()).load(source).into(new SimpleTarget<Bitmap>() {
//                                    @Override
//                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                                        if (resource != null) {
//                                            BitmapDrawable bitmapDrawable = new BitmapDrawable(resource);
//                                            drawable.addLevel(1, 1, bitmapDrawable);
//                                            drawable.setBounds(0, 0, resource.getWidth(), resource.getHeight());
//                                            drawable.setLevel(1);
//                                            textView.invalidate();
////                                            finalTextView.setText(textView.getText());
//                                            textView.setText(textView.getText());
//                                        }
//                                    }
//                                });
                                return drawable;
                            }
                        };
                        textView.append(Html.fromHtml(String.format(" 送礼品：<img src='%s'/>", rule.getGoods_image_url()), imgGetter, null));
                    }
                    binding.llManjisong.addView(textView);
                }
            }
            /*赠品*/
            SpannableString spannableString;
            if (goodsDetailInfo.getDatas().getGift_array() != null) {
                for (final GoodsDetailInfo.DatasBean.GiftArrayBean giftArrayBean : goodsDetailInfo.getDatas().getGift_array()) {
                    textView = new TextView(activity);
                    spannableString = new SpannableString(String.format("%s x %s", giftArrayBean.getGift_goodsname(),
                            giftArrayBean.getGift_amount()));
//                    underlineSpan = new UnderlineSpan();
//                    spannableString.setSpan(underlineSpan, 0, giftArrayBean.getGift_goodsname().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.BLACK);
//                    spannableString.setSpan(foregroundColorSpan, 0, giftArrayBean.getGift_goodsname().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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
                }
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
                .observeForever(new Observer<ResultBean>() {
                    @Override
                    public void onChanged(@Nullable ResultBean response) {
                        KLog.i(response.isSuccess() + " " + response.getError());
                        if (response.isSuccess()) {
                            ToastUtils.showLong(goodsInfo.isfavorate() ? "取消收藏成功" : "添加收藏成功");
                            goodsInfo.setfavorate(!goodsInfo.isfavorate());
                        } else {
                            ToastUtils.showLong(response.getError());
                        }
                    }
                });

        /*添加购物车*/
        registerObserver(Constants.CART_EVENT_KEY, ResultBean.class)
                .observeForever(new Observer<ResultBean>() {
                    @Override
                    public void onChanged(@Nullable ResultBean response) {
                        if (response.isSuccess()) {
                            ShoppingCartUtils.addCartGoods(goodsInfo);
                            shoppingDetailsActivity.setCartNumber();
                            ToastUtils.showLong("添加购物车成功");
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

    /**
     * 设置商品头图 轮播
     */
    private void setGoodsHeadImg() {
        if (goodsInfo != null) {
            binding.vpItemGoodsImg.setPages(new BannerImgAdapter() {
                @Override
                public ImageView getImageView() {
                    ImageView imageView = new ImageView(getContext());
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
                    return imageView;
                }
            }, Arrays.asList(goodsDetailInfo.getDatas().getGoods_image().split(",")))
//                    .setPageIndicator(new int[]{R.drawable.market_banner_index_white, R.drawable.market_shape_round_red})
                    .setPageIndicator(new int[]{R.drawable.shape_item_index_white, R.drawable.shape_item_index_red})
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

            goodsInfo.setGoods_image_url(goodsDetailInfo.getDatas().getGoods_image().split(",").length > 0
                    ? goodsDetailInfo.getDatas().getGoods_image().split(",")[0] : "");
        }
    }

    /**
     * 设置商品信息
     */
    private void setGoodsInfo() {
        if (goodsInfo != null) {
            setGoodsHeadImg();
            GoodsDetailInfo.DatasBean.StoreInfoBean storeInfo = goodsDetailInfo.getDatas().getStore_info();
            binding.lsiItem.setLeftText(storeInfo.getStore_name());
            binding.lsiItem.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
                @Override
                public void click(boolean isChecked) {
                    // TODO: 2019/4/2 品牌网自营
                    KLog.i("点击");
                    ActivityToActivity.toActivity(ARouterConfig.classify.STOREACTIVITY, "storeId", goodsDetailInfo.getDatas().getStore_info().getStore_id());
                }
            });
            if (null != storeInfo.getNew_storeCredit()) {
                String content = "";
                for (GoodsDetailInfo.DatasBean.StoreInfoBean.NewStoreCreditBean data : storeInfo.getNew_storeCredit()) {
                    content = content.concat(data.text + " " + data.credit + " " + data.percent_text);
                }
                binding.tvDesc.setText(content);
            }
            if (null != goodsDetailInfo.getDatas().getGoods_evaluate_info()) {
                ((TextView) binding.getRoot().findViewById(R.id.tv_comment_count)).setText(String.format("用户点评(%s)", goodsDetailInfo.getDatas().getGoods_evaluate_info().getAll()));
            }
            commentList(goodsDetailInfo.getDatas().getGoods_eval_list());
            binding.getRoot().findViewById(R.id.ll_comment).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shoppingDetailsActivity.setCurrentFragment(2);
                }
            });
            /*店铺推荐*/
            if (null != goodsDetailInfo.getDatas() && null != goodsDetailInfo.getDatas().getGoods_commend_list()) {
//                RecyclerView recyclerViewRecommend = binding.recycleRecommendView.recyclerView;
//                recyclerViewRecommend.addItemDecoration(new DividerItemDecoration(getContext(),
//                        DividerItemDecoration.VERTICAL));
//                recyclerViewRecommend.setLayoutManager(new GridLayoutManager(getContext(), 4));
//                recyclerViewRecommend.setAdapter(new RecommendGoodsInfoAdapter(getContext(),
//                        goodsDetailInfo.getDatas().getGoods_commend_list()));
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

    private void commentList(List<GoodsComment> commentList) {
        TextView tvEmptyComment = binding.getRoot().findViewById(R.id.tv_empty_comment);
        RecyclerView recyclerView = binding.getRoot().findViewById(R.id.recycle_view);
        if (null != commentList && commentList.size() > 0) {
            tvEmptyComment.setVisibility(View.GONE);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL));
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new GoodsCommentAdapter(getContext(), commentList));
        } else {
            tvEmptyComment.setVisibility(View.VISIBLE);
            tvEmptyComment.setText("暂无精彩评论");
        }
    }

    /**
     * 当前商品数量
     *
     * @return
     */
    public int getGoodsCount() {
        return binding.ccvClick.getCount();
    }
}