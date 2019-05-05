package com.mingpinmall.home.ui.adapter;

import android.support.v7.widget.AppCompatImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.adapter.BaseBannerAdapter;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.home.R;
import com.mingpinmall.home.ui.bean.HomeItemBean;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：首页列表
 * 创建人：小斌
 * 创建时间: 2019/4/3
 **/
public class HomeListAdapter extends BaseMultiItemQuickAdapter<HomeItemBean.DatasBean, BaseViewHolder> {

    private RVBannerPagerClickListener bannerClickListener;

    /**
     * 轮播图 监听
     *
     * @param bannerClickListener
     */
    public void setBannerClickListener(RVBannerPagerClickListener bannerClickListener) {
        this.bannerClickListener = bannerClickListener;
    }

    public HomeListAdapter() {
        super(new ArrayList<HomeItemBean.DatasBean>());
        //轮播图
        addItemType(0, R.layout.view_home_banner);
        //Homes系列
        addItemType(1, R.layout.view_home_item_1);
        addItemType(2, R.layout.view_home_item_2);
        addItemType(3, R.layout.view_home_item_3);
        addItemType(33, R.layout.view_home_item_33);
        addItemType(4, R.layout.view_home_item_4);
        addItemType(5, R.layout.view_home_item_5);
        //导航
        addItemType(6, R.layout.item_base_image);
        //Goods系列
        //商品列表 2span
        addItemType(10, R.layout.view_home_goods);
        //限购 4span
        addItemType(11, R.layout.view_home_goods1);
        //团购 4span
        addItemType(22, R.layout.view_home_goods2_t);
        addItemType(12, R.layout.view_home_goods);
    }

    @Override
    protected void convert(final BaseViewHolder helper, HomeItemBean.DatasBean item) {
        switch (item.getItemType()) {
            case 0:
                /*轮播图*/
                HomeItemBean.DatasBean.AdvListBean advListBean = item.getAdv_list();
                List<String> urls = new ArrayList<>();
                for (HomeItemBean.DatasBean.AdvListBean.ItemBean itemBean : advListBean.getItem()) {
                    urls.add(itemBean.getImage());
                }
                UltraViewPager banner = helper.getView(R.id.view_banner);
                if (banner.getAdapter() != null) {
                    ((HomeTopBannerAdapter) banner.getAdapter()).setData(urls);
                } else {
                    HomeTopBannerAdapter homeTopBannerAdapter = new HomeTopBannerAdapter();
                    homeTopBannerAdapter.setData(urls);
                    homeTopBannerAdapter.setOnPagerClickListener(new BaseBannerAdapter.OnPagerClickListener() {
                        @Override
                        public void OnPagerClick(int position) {
                            bannerClickListener.onItemClick(helper.getAdapterPosition(), position);
                        }
                    });
                    ImageUtils.createBanner(banner, homeTopBannerAdapter);
                }
                break;
            case 1:
                //板块A
                HomeItemBean.DatasBean.Home1Bean datasBean1 = item.getHome1();
                helper.setText(R.id.tv_label, datasBean1.getTitle())
                        .setGone(R.id.tv_label, !datasBean1.getTitle().equals(""))
                        .setGone(R.id.v_0, !datasBean1.getTitle().equals(""));
                ImageUtils.loadImageNoPlaceholder((AppCompatImageView) helper.getView(R.id.iv_0), datasBean1.getImage());
                break;
            case 2:
                //超值购  品牌街  有利可图
                HomeItemBean.DatasBean.Home2Bean datasBean2 = item.getHome2();
                helper.setText(R.id.tv_label, datasBean2.getTitle())
                        .addOnClickListener(R.id.iv_square)
                        .addOnClickListener(R.id.iv_rectangle1)
                        .addOnClickListener(R.id.iv_rectangle2)
                        .setGone(R.id.tv_label, !datasBean2.getTitle().equals(""))
                        .setGone(R.id.v_0, !datasBean2.getTitle().equals(""));
                ImageUtils.loadImageNoPlaceholder((AppCompatImageView) helper.getView(R.id.iv_square), datasBean2.getSquare_image());
                ImageUtils.loadImageNoPlaceholder((AppCompatImageView) helper.getView(R.id.iv_rectangle1), datasBean2.getRectangle1_image());
                ImageUtils.loadImageNoPlaceholder((AppCompatImageView) helper.getView(R.id.iv_rectangle2), datasBean2.getRectangle2_image());
                break;
            case 33:
                //布局c 标题
                HomeItemBean.DatasBean.Home3Bean datasBean3 = item.getHome3();
                helper.setText(R.id.tv_label, datasBean3.getTitle())
                        .setGone(R.id.tv_label, !datasBean3.getTitle().equals(""))
                        .setGone(R.id.v_0, !datasBean3.getTitle().equals(""));
                break;
            case 3:
                //布局c ITEM
                AppCompatImageView imageView = helper.getView(R.id.iv_0);
                ImageUtils.loadImageNoPlaceholder(imageView, item.getImage());
                break;
            case 4:
                //热门活动  特色市场
                HomeItemBean.DatasBean.Home4Bean datasBean4 = item.getHome4();
                helper.setText(R.id.tv_label, datasBean4.getTitle())
                        .addOnClickListener(R.id.iv_square)
                        .addOnClickListener(R.id.iv_rectangle1)
                        .addOnClickListener(R.id.iv_rectangle2)
                        .setGone(R.id.tv_label, !datasBean4.getTitle().equals(""))
                        .setGone(R.id.v_0, !datasBean4.getTitle().equals(""));
                ImageUtils.loadImageNoPlaceholder((AppCompatImageView) helper.getView(R.id.iv_rectangle1), datasBean4.getRectangle1_image());
                ImageUtils.loadImageNoPlaceholder((AppCompatImageView) helper.getView(R.id.iv_rectangle2), datasBean4.getRectangle2_image());
                ImageUtils.loadImageNoPlaceholder((AppCompatImageView) helper.getView(R.id.iv_square), datasBean4.getSquare_image());
                break;
            case 5:
                //手机通讯  自营超市
                HomeItemBean.DatasBean.Home5Bean datasBean5 = item.getHome5();
                helper.setText(R.id.tv_label, datasBean5.getTitle())
                        .setText(R.id.tv_sub_label, datasBean5.getStitle())
                        .addOnClickListener(R.id.iv_square)
                        .addOnClickListener(R.id.iv_rectangle1)
                        .addOnClickListener(R.id.iv_rectangle2)
                        .addOnClickListener(R.id.iv_rectangle3)
                        .setGone(R.id.tv_label, !datasBean5.getTitle().equals(""))
                        .setGone(R.id.tv_sub_label, !datasBean5.getStitle().equals(""));
                ImageUtils.loadImageNoPlaceholder((AppCompatImageView) helper.getView(R.id.iv_square), datasBean5.getSquare_image());
                ImageUtils.loadImageNoPlaceholder((AppCompatImageView) helper.getView(R.id.iv_rectangle1), datasBean5.getRectangle1_image());
                ImageUtils.loadImageNoPlaceholder((AppCompatImageView) helper.getView(R.id.iv_rectangle2), datasBean5.getRectangle2_image());
                ImageUtils.loadImageNoPlaceholder((AppCompatImageView) helper.getView(R.id.iv_rectangle3), datasBean5.getRectangle3_image());
                break;
            case 6:
                //导航
                final AppCompatImageView imageView6 = helper.getView(R.id.image);
                ImageUtils.loadImageNoPlaceholder(imageView6, item.getImage());
                break;
            case 10:
                //商品列表
                HomeItemBean.DatasBean.GoodsBean.ItemBean goodsBean = item.getGoodsItemBean();
                helper.setText(R.id.tv_label, goodsBean.getGoods_name())
                        .setText(R.id.tv_money, goodsBean.getGoods_promotion_price())
                        .setVisible(R.id.tv_tips, false);
                ImageUtils.loadImageNoPlaceholder((AppCompatImageView) helper.getView(R.id.iv_image), goodsBean.getGoods_image());
                break;
            case 11:
                //限购
                HomeItemBean.DatasBean.Goods1Bean goods1Bean = item.getGoods1();
                UltraViewPager banner2 = helper.getView(R.id.view_banner);
                if (banner2.getAdapter() != null) {
                    ((HomeSecondsBannerAdapter) banner2.getAdapter()).setData(goods1Bean.getItem());
                } else {
                    HomeSecondsBannerAdapter homeSecondsBannerAdapter = new HomeSecondsBannerAdapter();
                    homeSecondsBannerAdapter.setData(goods1Bean.getItem());
                    homeSecondsBannerAdapter.setOnPagerClickListener(new BaseBannerAdapter.OnPagerClickListener() {
                        @Override
                        public void OnPagerClick(int position) {
                            bannerClickListener.onItemClick(helper.getAdapterPosition(), position);
                        }
                    });
                    ImageUtils.createBanner(banner2, homeSecondsBannerAdapter);
                }
                break;
            case 12:
                //团购
                HomeItemBean.DatasBean.Goods2Bean.Goods2BeanItem goods2Bean = item.getGoods2ItemBean();
                helper.setText(R.id.tv_label, goods2Bean.getGoods_name())
                        .setText(R.id.tv_money, goods2Bean.getGoods_promotion_price())
                        .setVisible(R.id.tv_tips, true);
                ImageUtils.loadImageNoPlaceholder((AppCompatImageView) helper.getView(R.id.iv_image), goods2Bean.getGoods_image());
                break;
            case 22:
                //团购
                helper.setText(R.id.tv_label, item.getLabel())
                        .setText(R.id.tv_sub_label, item.getSubLabel());
                break;
            default:
                break;
        }
    }
}
