package com.mingpinmall.me.ui.acitivity.property.cardsurplus;

import android.os.Bundle;

import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.loading.CustomProgressDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentCardChargeBinding;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.constants.Constants;

import static com.goldze.common.dmvvm.constants.ARouterConfig.SUCCESS;

/**
 * 功能描述：充值卡充值
 *
 * @author 小斌
 * @date 2019/4/13
 **/
public class CardSurplusChargeFragment extends AbsLifecycleFragment<FragmentCardChargeBinding, MeViewModel> {

    public CardSurplusChargeFragment() {
    }

    public static CardSurplusChargeFragment newFragment() {
        return new CardSurplusChargeFragment();
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        binding.btnSubmit.setOnClickListener(v -> {
            if (binding.edCardNum.length() > 0) {
                CustomProgressDialog.show(activity);
                mViewModel.rcCharge(binding.edCardNum.getText().toString());
            }
        });
    }

    @Override
    protected void dataObserver() {
        registerObserver(Constants.RCARD_CHARGE, String.class).observeForever(msg -> {
            if (msg.equals(SUCCESS)) {
                ToastUtils.showShort("充值成功");
                CustomProgressDialog.stop();
                binding.edCardNum.setText("");
                LiveBus.getDefault().postEvent("REFRESH_RCARD", "true");
            } else {
                ToastUtils.showShort(msg);
                CustomProgressDialog.stop();
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
