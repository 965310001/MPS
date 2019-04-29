package com.mingpinmall.me.ui.acitivity.distribution;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityReducecashBinding;
import com.mingpinmall.me.ui.acitivity.distribution.reduceCash.ActionOneFragment;
import com.mingpinmall.me.ui.acitivity.distribution.reduceCash.ActionTwoFragment;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.ReduceCashBean;

/**
 * 功能描述：我的推广码
 * 创建人：小斌
 * 创建时间: 2019/4/29
 **/
@Route(path = ARouterConfig.Me.REDUCECASHACTIVITY)
public class ReduceCashActivity extends AbsLifecycleActivity<ActivityReducecashBinding, MeViewModel> {

    private Fragment currentFrament;
    private ActionOneFragment oneFragment;
    private ActionTwoFragment twoFragment;

    @Override
    protected void initViews(Bundle savedInstanceState) {

        super.initViews(savedInstanceState);
        setTitle(R.string.title_ReduceCashActivity);
        oneFragment = ActionOneFragment.getInstance();
        twoFragment = ActionTwoFragment.getInstance();
        switchFragment(oneFragment);
        binding.tvAction1.setOnClickListener(this);
        binding.tvAction2.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mViewModel.getReduceCash();
    }

    private void switchFragment(Fragment newFragmeent) {
        if (newFragmeent != currentFrament) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (!newFragmeent.isAdded()) { // 判断是否被add过
                // 隐藏当前的fragment，将 下一个fragment 添加进去
                if (currentFrament == null) {
                    transaction.add(R.id.fl_view, newFragmeent).commitNowAllowingStateLoss();
                } else {
                    transaction.hide(currentFrament).add(R.id.fl_view, newFragmeent).commitNowAllowingStateLoss();
                }
            } else {
                // 隐藏当前的fragment，显示下一个fragment
                transaction.hide(currentFrament).show(newFragmeent).commitNowAllowingStateLoss();
            }
            currentFrament = newFragmeent;
        }
    }

    @Override
    public void onViewClicked(int viewId) {
        super.onViewClicked(viewId);
        if (viewId == R.id.tv_action1) {
            //切换到邀请码
            switchFragment(oneFragment);
            binding.tvAction1.setBackgroundResource(R.drawable.shape_red_soild_left_2dp);
            binding.tvAction2.setBackgroundResource(R.drawable.shape_red_stroke_right_2dp);
            binding.tvAction1.setTextColor(ContextCompat.getColor(activity, R.color.white));
            binding.tvAction2.setTextColor(ContextCompat.getColor(activity, R.color.black));
        } else if (viewId == R.id.tv_action2) {
            //切换到提现申请
            switchFragment(twoFragment);
            binding.tvAction1.setBackgroundResource(R.drawable.shape_red_stroke_left_2dp);
            binding.tvAction2.setBackgroundResource(R.drawable.shape_red_soild_right_2dp);
            binding.tvAction1.setTextColor(ContextCompat.getColor(activity, R.color.black));
            binding.tvAction2.setTextColor(ContextCompat.getColor(activity, R.color.white));
        }
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver("REDUCE_CASH", Object.class).observeForever(new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object result) {
                if (result instanceof ReduceCashBean) {
                    ReduceCashBean data = (ReduceCashBean) result;
                    binding.setData(data);
                } else {
                    ToastUtils.showShort(result.toString());
                }
            }
        });
    }

    @Override
    protected Object getStateEventKey() {
        return "ReduceCashActivity";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reducecash;
    }
}
