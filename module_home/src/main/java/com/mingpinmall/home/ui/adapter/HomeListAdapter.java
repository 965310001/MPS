package com.mingpinmall.home.ui.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.home.R;
import com.mingpinmall.home.ui.bean.HomeItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/3
 **/
public class HomeListAdapter extends BaseMultiItemQuickAdapter<HomeItemBean.DatasBean, BaseViewHolder> {

    private com.bigkoo.convenientbanner.listener.OnItemClickListener bannerClickListener;

    private HomeListNavAdapter homeListNavAdapter;

    public void setBannerClickListener(com.bigkoo.convenientbanner.listener.OnItemClickListener bannerClickListener) {
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
        addItemType(4, R.layout.view_home_item_4);
        addItemType(5, R.layout.view_home_item_5);
        //导航
        addItemType(6, R.layout.view_home_nav);
        //Goods系列
        addItemType(10, R.layout.view_home_goods);
        addItemType(11, R.layout.view_home_goods1);
        addItemType(12, R.layout.view_home_goods2);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeItemBean.DatasBean item) {
        switch (item.getItemType()) {
            case 0:
                /*轮播图*/
                HomeItemBean.DatasBean.AdvListBean advListBean = item.getAdv_list();
                List<String> urls = new ArrayList<>();
                for (HomeItemBean.DatasBean.AdvListBean.ItemBean itemBean : advListBean.getItem()) {
                    Log.i(TAG, "convert: " + itemBean.getImage());
                    urls.add(itemBean.getImage());
                }
                ImageUtils.loadBanner((ConvenientBanner) helper.getView(R.id.view_banner), urls, bannerClickListener);
                break;
            case 1:
                //板块A
                HomeItemBean.DatasBean.Home1Bean datasBean1 = item.getHome1();
                helper.setText(R.id.tv_label, datasBean1.getTitle());
                ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_0), datasBean1.getImage());
                break;
            case 2:
                //超值购  品牌街  有利可图
                HomeItemBean.DatasBean.Home2Bean datasBean2 = item.getHome2();
                helper.setText(R.id.tv_label, datasBean2.getTitle());
                ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_0), datasBean2.getSquare_image());
                ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_1), datasBean2.getRectangle1_image());
                ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_2), datasBean2.getRectangle2_image());
                break;
            case 3:
                //布局c
                HomeItemBean.DatasBean.Home3Bean datasBean3 = item.getHome3();
                helper.setText(R.id.tv_label, datasBean3.getTitle());
                ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_0), datasBean3.getItem().get(0).getImage());
                ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_1), datasBean3.getItem().get(1).getImage());
                ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_2), datasBean3.getItem().get(2).getImage());
                ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_3), datasBean3.getItem().get(3).getImage());
                break;
            case 4:
                //热门活动  特色市场
                HomeItemBean.DatasBean.Home4Bean datasBean4 = item.getHome4();
                helper.setText(R.id.tv_label, datasBean4.getTitle());
                ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_0), datasBean4.getRectangle1_image());
                ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_1), datasBean4.getRectangle2_image());
                ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_2), datasBean4.getSquare_image());
                break;
            case 5:
                //手机通讯  自营超市
                HomeItemBean.DatasBean.Home5Bean datasBean5 = item.getHome5();
                helper.setText(R.id.tv_label, datasBean5.getTitle());
                helper.setText(R.id.tv_sub_label, datasBean5.getStitle());
                ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_0), datasBean5.getSquare_image());
                ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_1), datasBean5.getRectangle1_image());
                ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_2), datasBean5.getRectangle2_image());
                ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_3), datasBean5.getRectangle3_image());
                break;
            case 6:
                //导航
                HomeItemBean.DatasBean.Home6Bean datasBean6 = item.getHome6();
                homeListNavAdapter = new HomeListNavAdapter();
                RecyclerView recyclerView = helper.getView(R.id.recyclerView);
                recyclerView.setLayoutManager(new GridLayoutManager(helper.itemView.getContext(), 4));
                recyclerView.setHasFixedSize(true);
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setAdapter(homeListNavAdapter);
                homeListNavAdapter.setNewData(datasBean6.getItem());
                break;
            case 10:
                //未知
                break;
            case 11:
                //限购

                break;
            case 12:
                //团购

                break;
            default:
                break;
        }
    }
}
