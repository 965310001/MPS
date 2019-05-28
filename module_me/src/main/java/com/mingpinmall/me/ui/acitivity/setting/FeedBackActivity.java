package com.mingpinmall.me.ui.acitivity.setting;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.loading.CustomProgressDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityFeedbackBinding;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.constants.Constants;

import static com.goldze.common.dmvvm.constants.ARouterConfig.SUCCESS;

/**
 * 功能描述：用户反馈
 * @author 小斌
 * @date 2019/4/2
 **/
@Route(path = ARouterConfig.Me.FEEDBACKACTIVITY)
public class FeedBackActivity extends AbsLifecycleActivity<ActivityFeedbackBinding, MeViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTitle(R.string.title_feedBackActivity);
        binding.btnSubmit.setOnClickListener(v -> {
            if (binding.edContent.getText().length() < 8) {
                ToastUtils.showShort("内容太少！");
                return;
            }
            CustomProgressDialog.show(activity);
            mViewModel.sendFeedBack(binding.edContent.getText().toString());
        });
    }

    @Override
    protected Object getStateEventKey() {
        return "FEED_BACK";
    }

    @Override
    protected void dataObserver() {
        registerObserver(Constants.SEND_FEEDBACK, String.class).observeForever(msg -> {
            if (msg.equals(SUCCESS)) {
                ToastUtils.showShort("您的宝贵意见我们已经收到");
                CustomProgressDialog.stop();
                finish();
            } else{
                ToastUtils.showShort(msg);
                CustomProgressDialog.stop();
            }
        });
    }
}
