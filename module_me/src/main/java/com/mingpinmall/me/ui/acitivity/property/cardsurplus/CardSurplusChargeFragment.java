package com.mingpinmall.me.ui.acitivity.property.cardsurplus;

import android.os.Bundle;
import android.view.View;

import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.widget.progress.ProgressDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentCardChargeBinding;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.constants.Constants;

import static com.goldze.common.dmvvm.constants.ARouterConfig.SUCCESS;

/**
 * 功能描述：充值卡充值
 * @author 小斌
 * @date 2019/4/13
 **/
public class CardSurplusChargeFragment extends AbsLifecycleFragment<FragmentCardChargeBinding, MeViewModel> {

    private ProgressDialog progressDialog;

    public CardSurplusChargeFragment() {}

    public static CardSurplusChargeFragment newFragment() {
        return new CardSurplusChargeFragment();
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        progressDialog = ProgressDialog.initNewDialog(getChildFragmentManager());
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.edCardNum.length() > 0) {
                    progressDialog.onLoading("");
                    mViewModel.rcCharge(binding.edCardNum.getText().toString());
                }
            }
        });
    }

    @Override
    protected void dataObserver() {
        registerObserver(Constants.RCARD_CHARGE, String.class).observeForever(msg -> {
            if (msg.equals(SUCCESS)) {
                progressDialog.onComplete("", () -> {
                    binding.edCardNum.setText("");
                    LiveBus.getDefault().postEvent("REFRESH_RCARD", "true");
                });
            } else {
                progressDialog.onFail(msg);
            }
        });
    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    protected Object getStateEventKey() {
        return "CardSurplusChargeFragment";
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_card_charge;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }
}
