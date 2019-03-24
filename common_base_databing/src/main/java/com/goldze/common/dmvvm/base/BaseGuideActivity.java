package com.goldze.common.dmvvm.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;

import com.goldze.common.dmvvm.R;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.databinding.ActivityBaseGuideBinding;
import com.xuexiang.xui.widget.banner.anim.select.ZoomInEnter;
import com.xuexiang.xui.widget.banner.transform.DepthTransformer;
import com.xuexiang.xui.widget.banner.widget.banner.SimpleGuideBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GuoFeng
 * @date : 2019/1/18 17:33
 * @description: 启动引导页
 */
public abstract class BaseGuideActivity extends BaseActivity<ActivityBaseGuideBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_guide;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        /*设置全屏 WidgetUtils*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initGuideView(getGuidesResIdList(), getSkipClass());
    }

    /*初始化引导页动画*/
    public void initGuideView(List<Integer> guidesResIdList, final Class<?> cls) {
        initGuideView(guidesResIdList, DepthTransformer.class, cls);
    }

    /*获取跳转activity的类*/
    protected abstract Class<? extends Activity> getSkipClass();

    /*获取引导页资源的ID集合*/
    protected abstract List<Integer> getGuidesResIdList();


    private void initGuideView(List<Integer> guidesResIdList, Class<? extends ViewPager.PageTransformer> transformerClass, final Class<?> cls) {
        SimpleGuideBanner sgb = findViewById(R.id.sgb);

        sgb.setIndicatorWidth(6).setIndicatorHeight(6).setIndicatorGap(12)
                .setIndicatorCornerRadius(3.5f)
                .setSelectAnimClass(ZoomInEnter.class)
                .setTransformerClass(transformerClass).barPadding(0, 10, 0, 10)
                .setSource(guidesResIdList).startScroll();

        sgb.setOnJumpClickListener(new SimpleGuideBanner.OnJumpClickListener() {
            @Override
            public void onJumpClick() {
                startActivity(new Intent(BaseGuideActivity.this, cls));
                finish();
            }
        });
    }

    /**
     * 初始化引导页动画
     *
     * @param guidesResIdList  引导图片
     * @param transformerClass 引导图片切换的效果
     */
    public void initGuideView(ArrayList<Integer> guidesResIdList, Class<? extends ViewPager.PageTransformer> transformerClass, SimpleGuideBanner.OnJumpClickListener onJumpClickListener) {
        SimpleGuideBanner sgb = binding.sgb;

        sgb.setIndicatorWidth(6).setIndicatorHeight(6).setIndicatorGap(12)
                .setIndicatorCornerRadius(3.5f)
                .setSelectAnimClass(ZoomInEnter.class)
                .setTransformerClass(transformerClass).barPadding(0, 10, 0, 10)
                .setSource(guidesResIdList).startScroll();

        sgb.setOnJumpClickListener(onJumpClickListener);
    }
}
