package com.mingpinmall.home.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.just.agentweb.AgentWeb;
import com.mingpinmall.home.R;
import com.mingpinmall.home.databinding.FragmentTeacherBinding;

/**
 * 功能描述：名师预约
 * 创建人：小斌
 * 创建时间: 2019/4/27
 **/
public class TeacherFragment extends BaseFragment<FragmentTeacherBinding> {

    private AgentWeb mAgentWeb;

    public TeacherFragment() {
    }

    public static TeacherFragment newInstance() {
        return new TeacherFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_teacher;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {
        getViewById(R.id.rl_title_bar).setVisibility(View.VISIBLE);
        ((TextView) getViewById(R.id.tv_title)).setText("名师预约");
        //在这里设置沉浸式状态栏
        setTitlePadding(getViewById(R.id.rl_title_content));
        //并且设置状态栏字体颜色为黑色
        setDarkMode(true);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(binding.llContent, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go("http://c85of8h57pslid5r.mikecrm.com/D1HWDMm");
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        setDarkMode(true);
    }

    @Override
    protected void onInVisible() {
        super.onInVisible();
    }

    @Override
    public void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroyView();
    }

}
