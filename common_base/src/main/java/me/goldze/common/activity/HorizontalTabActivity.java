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

import butterknife.BindView;
import butterknife.OnClick;
import me.goldze.common.R;
import me.goldze.common.R2;
import me.goldze.common.base.mvvm.base.BaseActivity;
import me.goldze.common.base.mvvm.base.BaseFragment;

public abstract class HorizontalTabActivity extends BaseActivity {

    @BindView(R2.id.easy_indicator)
    EasyIndicator mEasyIndicator;

    @BindView(R2.id.view_pager)
    ViewPager mViewPager;

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.rl_title_bar)
    RelativeLayout rlTitleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_horizontal_tab;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
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

    @OnClick(R2.id.iv_back)
    public void onClick() {
        finish();
    }
}
