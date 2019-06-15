package com.mingpinmall.me.ui.acitivity.order.refundorder;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.utils.log.QLog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityReturnBinding;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.MerchandiseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 退货发货
 */
@Route(path = ARouterConfig.Me.RETURNACTIVITY)
public class ReturnActivity extends AbsLifecycleActivity<ActivityReturnBinding, MeViewModel> {

    @Autowired
    String returnId;

    private List<String> expressIdList;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);
        setTitle("退货发货");

        binding.setClick(this);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();

        registerObserver("MERCHANDISEBEAN_EVENT_KEY",
                Object.class)
                .observe(this, obj -> {
                    if (obj instanceof String) {
                        ToastUtils.showLong(obj.toString());
                        finish();
                        LiveBus.getDefault().postEvent("REFRESH_DATA", true);
                    } else {
                        MerchandiseBean merchandiseBean = (MerchandiseBean) obj;
                        QLog.i(merchandiseBean);
                        List<String> expressList = new ArrayList<>();
                        expressIdList = new ArrayList<>();
                        for (MerchandiseBean.ExpressListBean expressListBean : merchandiseBean.getExpress_list()) {
                            expressList.add(expressListBean.getExpress_name());
                            expressIdList.add(expressListBean.getExpress_id());
                        }
                        ArrayAdapter adapter = new ArrayAdapter(activity, R.layout.item_text1, R.id.text, expressList);
                        binding.spSpinner.setAdapter(adapter);
                        binding.spSpinner.setSelection(0);
                        binding.tvTitle.setText(String.format("发货%s天后，当商家选择未收到则要进行延迟时间操作；如果超过%s天不处理按弃货处理，直接由管理员确认退款。"
                                , merchandiseBean.getReturn_delay()
                                , merchandiseBean.getReturn_confirm()));
                    }

                });
    }

    @Override
    protected void initData() {
        super.initData();
        mViewModel.getMemberReturn(returnId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_return;
    }

    @Override
    protected Object getStateEventKey() {
        return " ";
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btn_submit) {
            String str = binding.edRefundInfo.getText().toString();
            if (TextUtils.isEmpty(str)) {
                ToastUtils.showLong("请填写物流单号");
            } else {
                mViewModel.getMemberReturn(returnId,
                        expressIdList.get(binding.spSpinner.getSelectedItemPosition()),
                        str);
            }
        }
    }
}