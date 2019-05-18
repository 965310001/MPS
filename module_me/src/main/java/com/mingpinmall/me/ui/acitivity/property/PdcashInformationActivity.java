package com.mingpinmall.me.ui.acitivity.property;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityPdcashInformationBinding;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.PdcashInfoBean;
import com.mingpinmall.me.ui.constants.Constants;

/**
 * 功能描述：提现详情
 * *@author 小斌
 * @date 2019/4/19
 **/
@Route(path = ARouterConfig.Me.PDCASHINFORMATIONACTIVITY)
public class PdcashInformationActivity extends AbsLifecycleActivity<ActivityPdcashInformationBinding, MeViewModel> {

    @Autowired
    String pdcId;

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.PDCASH_INFORMATION, Object.class).observeForever(result -> {
            if (result instanceof PdcashInfoBean) {
                PdcashInfoBean data = (PdcashInfoBean) result;
                binding.setData(data);
            } else {
                ToastUtils.showShort(result.toString());
            }
        });
    }

    @Override
    protected void initData() {
        ARouter.getInstance().inject(this);
        setTitle(R.string.title_PdcashInformationActivity);
        mViewModel.getPdcashList(pdcId);
    }

    @Override
    protected Object getStateEventKey() {
        return "PdcashInformationActivity";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pdcash_information;
    }
}
