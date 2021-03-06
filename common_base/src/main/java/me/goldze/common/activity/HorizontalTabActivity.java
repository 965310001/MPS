package me.goldze.common.activity;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.tabbar.EasyIndicator;

import java.util.List;

import me.goldze.common.R;
import me.goldze.common.base.mvvm.base.BaseActivity;
import me.goldze.common.base.mvvm.base.BaseFragment;

public abstract class HorizontalTabActivity extends BaseActivity {

    private EasyIndicator mEasyIndicator;
    private ViewPager mViewPager;
    private ImageView ivBack;
    private TextView tvTitle;
    private RelativeLayout rlTitleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_horizontal_tab;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        mEasyIndicator = findViewById(R.id.easy_indicator);
        mViewPager = findViewById(R.id.view_pager);
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        rlTitleBar = findViewById(R.id.rl_title_bar);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        showSuccess();
        mEasyIndicator.setTabTitles(getTabTitles());
        mEasyIndicator.setViewPager(mViewPager,
                new FragmentAdapter<>(getSupportFragmentManager(),
                        getTabFragments()));
        mViewPager.setOffscreenPageLimit(getTabTitles().length);
        rlTitleBar.setVisibility(View.VISIBLE);
        ivBack.setVisibility(View.VISIBLE);
    }

    /**
     * 根据需求对象Fragment
     *
     * @param position
     */
    protected void onPageSelected(int position) {
        mViewPager.setCurrentItem(position);
        mEasyIndicator.onPageSelected(position);
    }

    protected void setText(String text) {
        tvTitle.setText(text);
    }

    protected void setText(@StringRes int id) {
        tvTitle.setText(id);
    }


    protected abstract String[] getTabTitles();

    protected abstract List<BaseFragment> getTabFragments();

//    @OnClick(R2.id.iv_back)
//    public void onClick() {
//        finish();
//    }
}
