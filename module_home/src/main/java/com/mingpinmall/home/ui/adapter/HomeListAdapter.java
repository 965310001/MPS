package com.mingpinmall.home.ui.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.home.R;
import com.mingpinmall.home.ui.bean.HomeItemBean;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：首页列表
 *
 * @author 小斌
 * @date 2019/4/3
 **/
public class HomeListAdapter extends BaseMultiItemQuickAdapter<HomeItemBean.DatasBean, BaseViewHolder> {

    private RVBannerPagerClickListener bannerClickListener;

    /**
     * @param bannerClickListener 轮播图点击事件
     */
    public void setBannerClickListener(RVBannerPagerClickListener bannerClickListener) {
        this.bannerClickListener = bannerClickListener;
    }

    public HomeListAdapter() {
        super(new ArrayList<>());
        //轮播图
        addItemType(0, R.layout.view_home_banner);
        //Homes系列
        //单张图片占一整行
        addItemType(1, R.layout.view_home_item_1);
        //左1右2
        addItemType(2, R.layout.view_home_item_2);
        //单张图片占一行的一半
        addItemType(3, R.layout.view_home_item_3);
        //单标题
        addItemType(33, R.layout.view_home_item_33);
        //左2右1
        addItemType(4, R.layout.view_home_item_4);
        //左1右3
        addItemType(5, R.layout.view_home_item_5);
        //单张图片-导航
        addItemType(6, R.layout.item_base_image);
        //Goods系列
        //普通商品列表 一行俩个
        addItemType(10, R.layout.view_home_goods);
        //限购 轮播图
        addItemType(11, R.layout.view_home_goods1);
        //团购商品列表 一行俩个
        addItemType(22, R.layout.view_home_goods2_t);
        //团购商品-标题
        addItemType(12, R.layout.view_home_goods);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeItemBean.DatasBean item) {
        Context context = helper.itemView.getContext();
        switch (item.getItemType()) {
            case 0:
                /*轮播图*/
                initItemType0(helper, item.getAdv_list());
                break;
            case 1:
                //单张图片占一整行
                initItemType1(helper, item.getHome1());
                break;
            case 2:
                //左1右2
                initItemType2(helper, item.getHome2());
                break;
            case 33:
                //单标题
                initItemTitle3(helper, item.getHome3());
                break;
            case 3:
                //单张图片占一行的一半
                ImageUtils.loadImage(helper.getView(R.id.iv_0), item.getImage(),
                        ScreenUtil.dip2px(context, 320), ScreenUtil.dip2px(context, 160));
                break;
            case 4:
                //左2右1
                initItemType4(helper, item.getHome4());
                break;
            case 5:
                //左1右3
                initItemType5(helper, item.getHome5());
                break;
            case 6:
                //单张图片导航
                ImageUtils.loadImage(helper.getView(R.id.image), item.getImage(),
                        ScreenUtil.dip2px(context, 160), ScreenUtil.dip2px(context, 160));
                break;
            case 10:
                //普通商品列表 一行俩个
                initItemType10(helper, item.getGoodsItemBean());
                break;
            case 11:
                //限购 轮播图
                initItemType11(helper, item.getGoods1());
                break;
            case 12:
                //团购商品列表 一行俩个
                initItemType12(helper, item.getGoods2ItemBean());
                break;
            case 22:
                //团购标题
                helper.setText(R.id.tv_label, item.getLabel())
                        .setText(R.id.tv_sub_label, item.getSubLabel());
                break;
            default:
                break;
        }
    }

    private void initItemType12(BaseViewHolder helper, HomeItemBean.DatasBean.Goods2Bean.Goods2BeanItem data) {
        helper.setText(R.id.tv_label, data.getGoods_name())
                .setText(R.id.tv_money, data.getGoods_promotion_price())
                .setVisible(R.id.tv_tips, true);
        ImageUtils.loadImageCorners(helper.getView(R.id.iv_image), data.getGoods_image(),
                ScreenUtil.dip2px(helper.itemView.getContext(), 320),
                ScreenUtil.dip2px(helper.itemView.getContext(), 320),
                ScreenUtil.dip2px(helper.itemView.getContext(), 4)
        );
    }

    private void initItemType11(BaseViewHolder helper, HomeItemBean.DatasBean.Goods1Bean data) {
        UltraViewPager banner2 = helper.getView(R.id.view_banner);
        if (banner2.getAdapter() != null) {
            ((HomeSecondsBannerAdapter) banner2.getAdapter()).setData(data.getItem());
        } else {
            HomeSecondsBannerAdapter homeSecondsBannerAdapter = new HomeSecondsBannerAdapter();
            homeSecondsBannerAdapter.setData(data.getItem());
            homeSecondsBannerAdapter.setOnPagerClickListener(position -> bannerClickListener.onItemClick(helper.getAdapterPosition(), position));
            ImageUtils.createBanner(banner2, homeSecondsBannerAdapter);
        }
    }

    private void initItemType10(BaseViewHolder helper, HomeItemBean.DatasBean.GoodsBean.ItemBean data) {
        helper.setText(R.id.tv_label, data.getGoods_name())
                .setText(R.id.tv_money, data.getGoods_promotion_price())
                .setVisible(R.id.tv_tips, false);
        ImageUtils.loadImageCorners(helper.getView(R.id.iv_image), data.getGoods_image(),
                ScreenUtil.dip2px(helper.itemView.getContext(), 320),
                ScreenUtil.dip2px(helper.itemView.getContext(), 320),
                ScreenUtil.dip2px(helper.itemView.getContext(), 4)
        );
    }

    private void initItemType5(BaseViewHolder helper, HomeItemBean.DatasBean.Home5Bean data) {
        helper.setText(R.id.tv_label, data.getTitle())
                .setText(R.id.tv_sub_label, data.getStitle())
                .addOnClickListener(R.id.iv_square)
                .addOnClickListener(R.id.iv_rectangle1)
                .addOnClickListener(R.id.iv_rectangle2)
                .addOnClickListener(R.id.iv_rectangle3)
                .setGone(R.id.tv_label, !TextUtils.isEmpty(data.getTitle()))
                .setGone(R.id.tv_sub_label, !TextUtils.isEmpty(data.getStitle()));
        ImageUtils.loadImage(helper.getView(R.id.iv_square), data.getSquare_image(),
                ScreenUtil.dip2px(helper.itemView.getContext(), 320),
                ScreenUtil.dip2px(helper.itemView.getContext(), 320)
        );
        ImageUtils.loadImage(helper.getView(R.id.iv_rectangle1), data.getRectangle1_image(),
                ScreenUtil.dip2px(helper.itemView.getContext(), 320),
                ScreenUtil.dip2px(helper.itemView.getContext(), 160)
        );
        ImageUtils.loadImage(helper.getView(R.id.iv_rectangle2), data.getRectangle2_image(),
                ScreenUtil.dip2px(helper.itemView.getContext(), 160),
                ScreenUtil.dip2px(helper.itemView.getContext(), 160)
        );
        ImageUtils.loadImage(helper.getView(R.id.iv_rectangle3), data.getRectangle3_image(),
                ScreenUtil.dip2px(helper.itemView.getContext(), 160),
                ScreenUtil.dip2px(helper.itemView.getContext(), 160)
        );
    }

    private void initItemType4(BaseViewHolder helper, HomeItemBean.DatasBean.Home4Bean data) {
        helper.setText(R.id.tv_label, data.getTitle())
                .addOnClickListener(R.id.iv_square)
                .addOnClickListener(R.id.iv_rectangle1)
                .addOnClickListener(R.id.iv_rectangle2)
                .setGone(R.id.tv_label, !TextUtils.isEmpty(data.getTitle()))
                .setGone(R.id.v_0, !TextUtils.isEmpty(data.getTitle()));
        ImageUtils.loadImage(helper.getView(R.id.iv_rectangle1), data.getRectangle1_image(),
                ScreenUtil.dip2px(helper.itemView.getContext(), 320),
                ScreenUtil.dip2px(helper.itemView.getContext(), 160));
        ImageUtils.loadImage(helper.getView(R.id.iv_rectangle2), data.getRectangle2_image(),
                ScreenUtil.dip2px(helper.itemView.getContext(), 320),
                ScreenUtil.dip2px(helper.itemView.getContext(), 160));
        ImageUtils.loadImage(helper.getView(R.id.iv_square), data.getSquare_image(),
                ScreenUtil.dip2px(helper.itemView.getContext(), 320),
                ScreenUtil.dip2px(helper.itemView.getContext(), 320));
    }

    private void initItemTitle3(BaseViewHolder helper, HomeItemBean.DatasBean.Home3Bean home3) {
        helper.setText(R.id.tv_label, home3.getTitle())
                .setGone(R.id.tv_label, !TextUtils.isEmpty(home3.getTitle()))
                .setGone(R.id.v_0, !TextUtils.isEmpty(home3.getTitle()));
    }

    private void initItemType2(BaseViewHolder helper, HomeItemBean.DatasBean.Home2Bean home2) {
        helper.setText(R.id.tv_label, home2.getTitle())
                .addOnClickListener(R.id.iv_square)
                .addOnClickListener(R.id.iv_rectangle1)
                .addOnClickListener(R.id.iv_rectangle2)
                .setGone(R.id.tv_label, !TextUtils.isEmpty(home2.getTitle()))
                .setGone(R.id.v_0, !TextUtils.isEmpty(home2.getTitle()));
        ImageUtils.loadImage(helper.getView(R.id.iv_square), home2.getSquare_image(),
                ScreenUtil.dip2px(helper.itemView.getContext(), 320),
                ScreenUtil.dip2px(helper.itemView.getContext(), 320));
        ImageUtils.loadImage(helper.getView(R.id.iv_rectangle1), home2.getRectangle1_image(),
                ScreenUtil.dip2px(helper.itemView.getContext(), 320),
                ScreenUtil.dip2px(helper.itemView.getContext(), 160));
        ImageUtils.loadImage(helper.getView(R.id.iv_rectangle2), home2.getRectangle2_image(),
                ScreenUtil.dip2px(helper.itemView.getContext(), 320),
                ScreenUtil.dip2px(helper.itemView.getContext(), 160));
    }

    private void initItemType1(BaseViewHolder helper, HomeItemBean.DatasBean.Home1Bean data) {
        helper.setText(R.id.tv_label, data.getTitle())
                .setGone(R.id.tv_label, !TextUtils.isEmpty(data.getTitle()))
                .setGone(R.id.v_0, !TextUtils.isEmpty(data.getTitle()));
        ImageUtils.loadImage(helper.getView(R.id.iv_0), data.getImage(),
                ScreenUtil.dip2px(helper.itemView.getContext(), 640),
                ScreenUtil.dip2px(helper.itemView.getContext(), 260));
    }

    private void initItemType0(BaseViewHolder helper, HomeItemBean.DatasBean.AdvListBean data) {
        List<String> urls = new ArrayList<>();
        for (HomeItemBean.DatasBean.AdvListBean.ItemBean itemBean : data.getItem()) {
            urls.add(itemBean.getImage());
        }
        UltraViewPager banner = helper.getView(R.id.view_banner);
        if (banner.getAdapter() != null) {
            ((HomeTopBannerAdapter) banner.getAdapter()).setData(urls);
        } else {
            HomeTopBannerAdapter homeTopBannerAdapter = new HomeTopBannerAdapter();
            homeTopBannerAdapter.setData(urls);
            homeTopBannerAdapter.setOnPagerClickListener(position -> bannerClickListener.onItemClick(helper.getAdapterPosition(), position));
            ImageUtils.createBanner(banner, homeTopBannerAdapter);
        }
    }
}
