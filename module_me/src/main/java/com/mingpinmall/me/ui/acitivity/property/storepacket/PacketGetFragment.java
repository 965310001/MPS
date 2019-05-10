package com.mingpinmall.me.ui.acitivity.property.storepacket;

import android.os.Bundle;

import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.widget.progress.ProgressDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentPacketGetBinding;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.constants.Constants;

import static com.goldze.common.dmvvm.constants.ARouterConfig.SUCCESS;

/**
 * 功能描述：获取店铺红包
 * @author 小斌
 * @date 2019/4/20
 **/
public class PacketGetFragment extends AbsLifecycleFragment<FragmentPacketGetBinding, MeViewModel> {

    private ProgressDialog progressDialog;

    public static PacketGetFragment newFragment() {
        return new PacketGetFragment();
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        progressDialog = ProgressDialog.initNewDialog(getChildFragmentManager());
        binding.btnSubmit.setOnClickListener(v -> {
            if (binding.edCardNum.length() > 0) {
                progressDialog.onLoading("");
                mViewModel.packetCharge(binding.edCardNum.getText().toString().trim());
            }
        });
    }

    @Override
    protected void dataObserver() {
        registerObserver(Constants.PACKET_CHARGE, String.class).observeForever(msg -> {
            if (msg.equals(SUCCESS)) {
                progressDialog.onComplete("", () -> {
                    binding.edCardNum.setText("");
                    LiveBus.getDefault().postEvent("REFRESH_PACKET", "true");
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
        return "CouponGetFragment";
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_packet_get;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }
}
