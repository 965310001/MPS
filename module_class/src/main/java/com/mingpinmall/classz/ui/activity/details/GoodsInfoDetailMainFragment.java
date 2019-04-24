package com.mingpinmall.classz.ui.activity.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goldze.common.dmvvm.BuildConfig;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.utils.DisplayUtil;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.FragmentGoodsInfoDetailMainBinding;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情 -图文详情 主Fragment
 * 顶部为商品详情 商品规格 2个Tba 点击可切换下方Fragment的父 Fragment
 */
public class GoodsInfoDetailMainFragment extends AbsLifecycleFragment<FragmentGoodsInfoDetailMainBinding, ClassifyViewModel>
        implements View.OnClickListener {

    View vTabCursor;
    private int nowIndex;
    private float fromX;
    private List<TextView> tabTextList;
    private GoodsInfoWebFragment goodsDetailWebFragment;
    private GoodsConfigFragment goodsConfigFragment;
    private Fragment currentFragment;//当前显示的Fragment
    private FragmentManager fragmentManager;

    public GoodsInfoDetailMainFragment() {
    }

    public static GoodsInfoDetailMainFragment newInstance() {
        GoodsInfoDetailMainFragment fragment = new GoodsInfoDetailMainFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_goods_info_detail_main;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {

        TextView tvGoodsDetail = binding.tvGoodsDetail;
        TextView tvGoodsConfig = binding.tvGoodsConfig;
        vTabCursor = binding.vTabCursor;

        tvGoodsDetail.setOnClickListener(this);
        tvGoodsConfig.setOnClickListener(this);

        showSuccess();
        tabTextList = new ArrayList<>();
        tabTextList.add(tvGoodsDetail);
        tabTextList.add(tvGoodsConfig);

        setData();
    }

    @Override
    protected Object getStateEventKey() {
        return "";
    }

    /**
     * 商品信息Fragment页获取完数据执行
     */
    public void setData() {
        goodsDetailWebFragment = GoodsInfoWebFragment.newInstance(getUrl());
        currentFragment = goodsDetailWebFragment;
        goodsConfigFragment = new GoodsConfigFragment();
        fragmentManager = getChildFragmentManager();
        //默认显示商品详情tab
        fragmentManager.beginTransaction().replace(R.id.fl_goods_content, currentFragment).commitAllowingStateLoss();
    }

    private String getUrl() {
        return BuildConfig.APP_URL + "/mo_bile/index.php?app=goods&wwi=goods_body&goods_id=" + ((ShoppingDetailsActivity) activity).getId();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_goods_detail) {//商品详情tab
            switchFragment(currentFragment, goodsDetailWebFragment);
            nowIndex = 0;
            currentFragment = goodsDetailWebFragment;
            scrollCursor();
        } else if (i == R.id.tv_goods_config) {//规格参数tab
            switchFragment(currentFragment, goodsConfigFragment);
            nowIndex = 1;
            currentFragment = goodsConfigFragment;
            scrollCursor();
        }
    }

    /**
     * 滑动游标
     */
    private void scrollCursor() {
        TranslateAnimation anim = new TranslateAnimation(fromX, nowIndex * vTabCursor.getWidth(), 0, 0);
        anim.setFillAfter(true);//设置动画结束时停在动画结束的位置
        anim.setDuration(50);
        //保存动画结束时游标的位置,作为下次滑动的起点
        fromX = nowIndex * vTabCursor.getWidth();
        vTabCursor.startAnimation(anim);

        //设置Tab切换颜色
        for (int i = 0; i < tabTextList.size(); i++) {
            tabTextList.get(i).setTextColor(i == nowIndex ? getResources().getColor(R.color.red) : getResources().getColor(R.color.black));
        }
    }

    /**
     * 切换Fragment
     */
    private void switchFragment(Fragment fromFragment, Fragment toFragment) {
        if (currentFragment != toFragment) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (!toFragment.isAdded()) {    // 先判断是否被add过
                fragmentTransaction.hide(fromFragment).add(R.id.fl_goods_content, toFragment).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到activity中
            } else {
                fragmentTransaction.hide(fromFragment).show(toFragment).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
            }
        }
    }
}