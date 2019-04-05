package com.mingpinmall.classz.ui.activity.details;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.goldze.common.dmvvm.adapter.BannerImgAdapter;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.widget.CountClickView;
import com.goldze.common.dmvvm.widget.SlideLayout;
import com.leon.lib.settingview.LSettingItem;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ResultBean;
import com.mingpinmall.classz.adapter.GoodsCommentAdapter;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.databinding.FragmentGoodsInfoMainBinding;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.vm.adapter.RecommendGoodsInfoAdapter;
import com.mingpinmall.classz.ui.vm.bean.GoodsComment;
import com.mingpinmall.classz.ui.vm.bean.GoodsDetailInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.socks.library.KLog;

import java.util.Arrays;
import java.util.List;


public class GoodsInfoMainFragment extends AbsLifecycleFragment<FragmentGoodsInfoMainBinding, ClassifyViewModel>
        implements SlideLayout.OnSlideDetailsListener {

    String id;

    //    @BindView(R.id.vp_item_goods_img)
    ConvenientBanner vpItemGoodsImg;
    //    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    //    @BindView(R.id.tv_ensure)
    TextView tvEnsure;
    //    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    //    @BindView(R.id.tv_old_price)
//    TextView tvOldPrice;
    //    @BindView(R.id.ccv_click)
    CountClickView ccvClick;
    //    @BindView(R.id.tv_comment_count)
    TextView tvCommentCount;/*用于点评*/
    LinearLayout llComment;/*用于点评*/
    //    @BindView(R.id.tv_praise_rate)
    TextView tvPraiseRate;
    //    @BindView(R.id.tv_empty_comment)
    TextView tvEmptyComment;
    //    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;//评论
    //    @BindView(R.id.sv_switch)
    SlideLayout svSwitch;
    //    @BindView(R.id.iv_icon)
    ImageView ivIcon;

    RecyclerView recyclerViewRecommend;

//    ConvenientBanner<List<GoodsInfo>> vpRecommend; //推荐位置


    /**
     * 当前商品详情数据页的索引分别是图文详情、规格参数
     */
    GoodsInfo goodsInfo;
    GoodsDetailInfo goodsDetailInfo;
    private ShoppingDetailsActivity shoppingDetailsActivity;
//    private GoodsCommentAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        shoppingDetailsActivity = (ShoppingDetailsActivity) context;
    }

    public GoodsInfoMainFragment() {
    }

    public static GoodsInfoMainFragment newInstance(String id, GoodsDetailInfo goodsDetailInfo) {
        GoodsInfoMainFragment fragment = new GoodsInfoMainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putSerializable("goodsInfo", goodsDetailInfo);
        fragment.setArguments(bundle);
        return fragment;
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
        showSuccess();

        vpItemGoodsImg = binding.vpItemGoodsImg;
        tvGoodsName = binding.tvGoodsName;
        tvEnsure = binding.tvEnsure;
        tvGoodsPrice = binding.tvGoodsPrice;
//        tvOldPrice = binding.tvOldPrice;
        ccvClick = binding.ccvClick;
//        ConvenientBanner<List<GoodsInfo>> vpRecommend = binding.vpRecommend;

        tvCommentCount = binding.getRoot().findViewById(R.id.tv_comment_count);
        llComment = binding.getRoot().findViewById(R.id.ll_comment);
        recyclerView = binding.getRoot().findViewById(R.id.recycle_view);
        recyclerViewRecommend = binding.recycleRecommendView.recyclerView;
        tvEmptyComment = binding.getRoot().findViewById(R.id.tv_empty_comment);

        binding.ccvClick.setCurrCount(1);
        binding.ccvClick.setMinCount(1);
//        ccvClick.setMaxCount(goodsInfo.getNum());/*设置最大*/

//        KLog.i(goodsInfo.getNum()+" ===");
        binding.svSwitch.setOnSlideDetailsListener(this);

        id = getArguments().getString("id");
        goodsDetailInfo = (GoodsDetailInfo) getArguments().getSerializable("goodsInfo");
        goodsInfo = goodsDetailInfo.getDatas().getGoods_info();
        KLog.i(goodsInfo);

        //设置文字中间一条横线
//        binding.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        setGoodsInfo();
//        setGoodsHeadImg();
        /*commentList(ApiData.getGoodsCommentList());*/
//        getReviewList();
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.GOODSDETAIL_EVENT_KEY[0], GoodsDetailInfo.class)
                .observeForever(new Observer<GoodsDetailInfo>() {
                    @Override
                    public void onChanged(@Nullable GoodsDetailInfo response) {
////                        goodsInfo = data.getData();
//                        List<HorizontalTabTitle> title = new ArrayList<>();
//                        HorizontalTabTitle horizontalTabTitle = new HorizontalTabTitle("商品");
//                        title.add(horizontalTabTitle);
//
//                        horizontalTabTitle = new HorizontalTabTitle("详情");
//                        title.add(horizontalTabTitle);
//
//                        horizontalTabTitle = new HorizontalTabTitle("评价");
//                        title.add(horizontalTabTitle);
//
//                        List<BaseFragment> fragmentList = new ArrayList<>();
//                        fragmentList.add(goodsInfoMainFragment = GoodsInfoMainFragment.newInstance(id, null));
//                        fragmentList.add(GoodsInfoDetailMainFragment.newInstance(data.getData()));
//                        fragmentList.add(GoodsCommentFragment.newInstance(String.valueOf(goodsInfo.getGoodsId())));
//
//                        binding.vpContent.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), title, fragmentList));
//                        binding.vpContent.setOffscreenPageLimit(3);
//                        binding.pstsTabs.setViewPager(binding.vpContent);
                    }
                });


        // TODO: 2019/4/2 收藏
        registerObserver(Constants.FAVORITES, ResultBean.class)
                .observeForever(new Observer<ResultBean>() {
                    @Override
                    public void onChanged(@Nullable ResultBean response) {
                        KLog.i(response.isSuccess() + " " + response.getError());
                        if (response.isSuccess()) {
                            goodsInfo.setfavorate(!goodsInfo.isfavorate());
                        }
                    }
                });
    }

    @Override
    protected Object getStateEventKey() {
        return "";
    }

    /*商品评价信息*/
//    private void getReviewList() {
////        commentList(ApiData.getGoodsCommentList());
////        ApiRepo.getReviewList(String.valueOf(goodsInfo.getGoodsId()), 1).subscribeWith(new RxSubscriber<ReviewListInfo>() {
////
////            @Override
////            public void onSuccess(ReviewListInfo response) {
//////                KLog.i(response.getErrorMsg() + response.getError_desc());
////////                if (!response.isSuccess()) {
////////                    ToastUtils.showLong(response.getErrorMsg());
////////                } else {
////////                }
////                commentList(response.getData());
////            }
////
////            @Override
////            public void onFailure(String msg) {
////                KLog.i(msg);
////                /*ToastUtils.showLong(msg);*/
////            }
////
////            @Override
////            public void onError(Throwable t) {
////                KLog.i(t.getMessage());
////                /*ToastUtils.showLong("请稍后再试");*/
////            }
////        });
//
//    }

    @Override
    public void onStateChanged(SlideLayout.Status status) {
        if (shoppingDetailsActivity != null) {
            shoppingDetailsActivity.setViewContent(status == SlideLayout.Status.OPEN);
//            KLog.i("上拉查看图文详情");
            getChildFragmentManager().beginTransaction().replace(R.id.fl_fragment,
                    GoodsInfoDetailMainFragment.newInstance(goodsInfo.getGoods_id())).commitAllowingStateLoss();
        }
    }

//    @OnClick({R.id.ll_pull_up, R.id.ll_comment, R.id.iv_like})
//    public void onClick(View v) {
//        if (v.getId() == R.id.ll_pull_up) {//上拉查看图文详情
//            KLog.i("上拉查看图文详情");
//            /*getChildFragmentManager().beginTransaction().replace(R.id.fl_fragment, fragment).commitAllowingStateLoss();*/
//            svSwitch.smoothOpen(true);
//        } else if (v.getId() == R.id.ll_comment) {
//            //查看评论
//            shoppingDetailsActivity.setCurrentFragment(2);
//        } else if (v.getId() == R.id.iv_like) {
//            /*添加收藏*/
//            getUnlike();
//        }
//    }

    private void getUnlike() {
//        ApiRepo.getUnlike(goodsInfo.getGoodsId()).subscribeWith(new RxSubscriber<UserInfo>() {
//            @Override
//            public void onSuccess(UserInfo response) {
//                KLog.i(response.getErrorMsg() + response.getError_desc());
//                if (!response.isSuccess()) {
//                    ToastUtils.showLong(response.getErrorMsg());
//                } else {
//                    /*修改成功*/
//                    /*ToastUtils.showLong("收藏成功");*/
//                }
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                KLog.i(msg);
//            }
//        });

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
        }
    }

    /**
     * 设置商品信息
     */
    private void setGoodsInfo() {
        if (goodsInfo != null) {
            tvGoodsName.setText(goodsInfo.getGoods_name());
            tvGoodsPrice.setText(String.format("¥%s", goodsInfo.getGoods_price()));
//            tvOldPrice.setText(String.format("¥%s", goodsInfo.getGoodsOldPrice()));
//            tvCommentCount.setText("用户点评");

//            tvPraiseRate.setText(String.format("好评率%s", goodsInfo.getPraiseRate()));
//            tvEnsure.setText(String.format("说明:%s", goodsInfo.getGoodsZb()));
            ccvClick.setMaxCount(goodsInfo.getNum());/*设置最大*/
            setGoodsHeadImg();

            GoodsDetailInfo.DatasBean.StoreInfoBean storeInfo = goodsDetailInfo.getDatas().getStore_info();
            binding.lsiItem.setLeftText(storeInfo.getStore_name());
            binding.lsiItem.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
                @Override
                public void click(boolean isChecked) {
                    // TODO: 2019/4/2 品牌网自营
                    KLog.i("点击");
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
                tvCommentCount.setText(String.format("用户点评(%s)", goodsDetailInfo.getDatas().getGoods_evaluate_info().getAll()));
            }
            commentList(goodsDetailInfo.getDatas().getGoods_eval_list());
            llComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shoppingDetailsActivity.setCurrentFragment(2);
                }
            });
            /*店铺评价*/
            if (null != goodsDetailInfo.getDatas() && null != goodsDetailInfo.getDatas().getGoods_commend_list()) {
//                vpRecommend.setPages(new RecommendGoodsAdapter(getContext()), Arrays.asList(goodsDetailInfo.getDatas().getGoods_commend_list()))
//                        .setCanLoop(goodsDetailInfo.getDatas().getGoods_commend_list().size() != 1)
//                        .setPageIndicator(new int[]{R.drawable.shape_item_index_white, R.drawable.shape_item_index_red})
//                        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

                recyclerViewRecommend.addItemDecoration(new DividerItemDecoration(getContext(),
                        DividerItemDecoration.VERTICAL));
                recyclerViewRecommend.setLayoutManager(new GridLayoutManager(getContext(), 4));
                recyclerViewRecommend.setAdapter(new RecommendGoodsInfoAdapter(getContext(),
                        goodsDetailInfo.getDatas().getGoods_commend_list()));
            }


            // TODO: 2019/4/1  全国 有货 免运费
            KLog.i(goodsDetailInfo.getDatas().getGoods_hair_info().content + " " +
                    goodsDetailInfo.getDatas().getGoods_hair_info().if_store_cn +
                    goodsDetailInfo.getDatas().getGoods_hair_info().area_name);
        }

    }

    private void commentList(List<GoodsComment> commentList) {
        if (null != commentList && commentList.size() > 0) {
            tvEmptyComment.setVisibility(View.GONE);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL));
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//            GoodsCommentAdapter adapter = new GoodsCommentAdapter(getContext(), commentList);
            recyclerView.setAdapter(new GoodsCommentAdapter(getContext(), commentList));
        } else {
            tvEmptyComment.setVisibility(View.VISIBLE);
            tvEmptyComment.setText("暂无精彩评论");
        }
    }

//    public void goodsInfo(GoodsInfo goodsInfo) {
//        this.goodsInfo = goodsInfo;
//        setGoodsHeadImg();
//        setGoodsInfo();
//    }

    /**
     * 当前商品数量
     *
     * @return
     */
    public int getGoodsCount() {
        return binding.ccvClick.getCount();
    }


}

