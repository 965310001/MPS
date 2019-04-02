package com.mingpinmall.me.ui.acitivity.setting;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.progress.ProgressDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityFeedbackBinding;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.BaseIntDatasBean;

/**
 * 功能描述：用户反馈
 * 创建人：小斌
 * 创建时间: 2019/4/2
 **/
@Route(path = ARouterConfig.FeedBackActivity)
public class FeedBackActivity extends AbsLifecycleActivity<ActivityFeedbackBinding, MeViewModel> {

    ProgressDialog progressDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTitle(R.string.title_feedBackActivity);
        progressDialog = ProgressDialog.initNewDialog(getSupportFragmentManager());
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.edContent.getText().length() < 8) {
                    ToastUtils.showShort("内容太少！");
                    return;
                }
                progressDialog.onLoading("");
                mViewModel.sendFeedBack(binding.edContent.getText().toString());
            }
        });
    }

    @Override
    protected Object getStateEventKey() {
        return null;
    }

    @Override
    protected void dataObserver() {
        LiveBus.getDefault().subscribe("SEND_FEEDBACK", BaseIntDatasBean.class).observeForever(new Observer<BaseIntDatasBean>() {
            @Override
            public void onChanged(@Nullable BaseIntDatasBean result) {
                if (result.getCode() == 400) {
                    ToastUtils.showShort("提交失败！");
                } else {
                    progressDialog.onComplete("", new ProgressDialog.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            finish();
                        }
                    });
                }
            }
        });
    }
}
