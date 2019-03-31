package com.mingpinmall.me.ui.acitivity.distribution;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityDistributionBinding;
import com.mingpinmall.me.ui.adapter.BasePagerAdapter;

/**
 * 功能描述：我的分销管理
 * 创建人：小斌
 * 创建时间: 2019/3/29
 **/
@Route(path = ARouterConfig.DISRTIBUTIONACTIVITY)
public class DistributionActivity extends BaseActivity<ActivityDistributionBinding> {

    private DistributionFragment oneFragment;
    private DistributionFragment twoFragment;
    private DistributionFragment threeFragment;

    private BasePagerAdapter pagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_distribution;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle(R.string.title_text_distributionActivity);

        oneFragment = new DistributionFragment();
        twoFragment = new DistributionFragment();
        threeFragment = new DistributionFragment();

        pagerAdapter = new BasePagerAdapter(getSupportFragmentManager(), this);
        pagerAdapter.addFragment(oneFragment, R.string.tabs_text_one);
        pagerAdapter.addFragment(twoFragment, R.string.tabs_text_two);
        pagerAdapter.addFragment(threeFragment, R.string.tabs_text_three);

        binding.viewPager.setAdapter(pagerAdapter);
        binding.tabs.setupWithViewPager(binding.viewPager);

        binding.tvPopularize.setText("推广链接：http://39.108.254.185/wap/tmpl/member/register_invite.html?rec=30545");
        binding.tvPopularize.setOnClickListener(this);
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.tv_popularize) {
            //点击了邀请链接，复制邀请链接到剪切板，或者打开分享窗口
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 将文本内容放到系统剪贴板里。
            cm.setPrimaryClip(ClipData.newPlainText("我的推广链接", binding.tvPopularize.getText().toString()));
            ToastUtils.showShort("复制成功，可以发给朋友们了。");


        }

    }
}
