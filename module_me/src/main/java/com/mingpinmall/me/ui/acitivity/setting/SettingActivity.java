package com.mingpinmall.me.ui.acitivity.setting;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.widget.dialog.MaterialDialogUtils;
import com.goldze.common.dmvvm.widget.dialog.TextDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivitySettingBinding;
import com.mingpinmall.me.ui.adapter.SettingAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.BaseCheckBean;
import com.mingpinmall.me.ui.bean.BaseItemBean;
import com.mingpinmall.me.ui.constants.Constants;
import com.goldze.common.dmvvm.widget.SettingItemView;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：
 *
 * @author 小斌
 * @date 2019/3/25
 **/
@Route(path = ARouterConfig.Me.SETTINGACTIVITY)
public class SettingActivity extends AbsLifecycleActivity<ActivitySettingBinding, MeViewModel> {

    @Autowired
    String name;

    private BaseCheckBean checkPhoneState;

    SettingAdapter settingAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTitle(getString(R.string.title_settingActivity));
        ARouter.getInstance().inject(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        settingAdapter = new SettingAdapter();
        settingAdapter.bindToRecyclerView(binding.recyclerView);

        initItems();
    }

    @Override
    protected void initData() {
        mViewModel.getPayPwdInfo();
        mViewModel.getPhoneInfo();
    }

    @Override
    protected Object getStateEventKey() {
        return "SETTING_ME";
    }

    @Override
    protected void dataObserver() {
        registerObserver(Constants.USER_PAYPWD_INFO, BaseCheckBean.class).observeForever(result -> {
            settingAdapter.getData().get(2).setSubTitle(result.isState() ? "已验证" : "未验证");
            settingAdapter.notifyDataSetChanged();
        });
        registerObserver(Constants.USER_PHONE_INFO, BaseCheckBean.class).observeForever(result -> {
            checkPhoneState = result;
            settingAdapter.getData().get(1).setSubTitle(result.isState() ? result.getMobile() : "未绑定");
            settingAdapter.notifyDataSetChanged();
        });
        registerObserver(Constants.ERR_USER_INFO, String.class).observeForever(result -> TextDialog.showBaseDialog(activity, "获取内容失败", result, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                onBackPressed();
            }
        }));
        registerObserver(ARouterConfig.REFRESH_DATA, "SettingActivity")
                .observeForever(o -> initData());
    }

    /**
     * 初始化item
     */
    private void initItems() {
        String[] titles = new String[]{
                getString(R.string.set_item_item1),
                getString(R.string.set_item_item2),
                getString(R.string.set_item_item3),
                getString(R.string.set_item_item4),
                getString(R.string.set_item_item5),
        };
        String[] descs = new String[]{
                getString(R.string.set_desc_item1),
                getString(R.string.set_desc_item2),
                getString(R.string.set_desc_item3),
                getString(R.string.set_desc_item4),
                "",
        };
        List<BaseItemBean> itemBeanList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            BaseItemBean itemBean = new BaseItemBean();
            itemBean.setDescription(descs[i]);
            itemBean.setItemMode(SettingItemView.ThemeMode.NoLeftImage);
            itemBean.setTitle(titles[i]);
            itemBean.setItemType(1);
            itemBeanList.add(itemBean);
            if (i == 2 || i == 3 || i == 4) {
                BaseItemBean spaceItem = new BaseItemBean();
                spaceItem.setItemType(0);
                itemBeanList.add(spaceItem);
            }
        }
        itemBeanList.get(2).setSubTitle(getString(R.string.set_subitem_item4));
        itemBeanList.get(6).setItemMode(SettingItemView.ThemeMode.NoLeftImage_NoMoreIcon_NoDescription);
        settingAdapter.setNewData(itemBeanList);

        settingAdapter.setOnItemClickListener((adapter, view, position) -> {
            switch (position) {
                case 0:
                    //修改密码   登录密码
                    if (null != checkPhoneState && !checkPhoneState.isState()) {
                        toBindPhone();
                        break;
                    }
                    Map<String, Object> params = new HashMap<>(3);
                    params.put("type", CheckAuthActivity.RESET_PASSWORD);
                    params.put("phoneNumber", checkPhoneState != null ? checkPhoneState.getMobile() : "");
                    params.put("phoneNumber2", checkPhoneState != null ? checkPhoneState.getMobile_full() : "");
                    ActivityToActivity.toActivity(ARouterConfig.Me.CHECKAUTHACTIVITY, params);
                    break;
                case 1:
                    //手机验证
                    if (null != checkPhoneState && !checkPhoneState.isState()) {
                        ActivityToActivity.toActivity(ARouterConfig.Me.BINDPHONEACTIVITY);
                        break;
                    }
                    Map<String, Object> params1 = new HashMap<>(3);
                    params1.put("type", CheckAuthActivity.RESET_PHONE);
                    params1.put("phoneNumber", checkPhoneState != null ? checkPhoneState.getMobile() : "");
                    params1.put("phoneNumber2", checkPhoneState != null ? checkPhoneState.getMobile_full() : "");
                    ActivityToActivity.toActivity(ARouterConfig.Me.CHECKAUTHACTIVITY, params1);
                    break;
                case 2:
                    //支付密码
                    if (null != checkPhoneState && !checkPhoneState.isState()) {
                        toBindPhone();
                        break;
                    }
                    Map<String, Object> params2 = new HashMap<>(3);
                    params2.put("type", CheckAuthActivity.RESET_PAY_PASSWORD);
                    params2.put("phoneNumber", checkPhoneState != null ? checkPhoneState.getMobile() : "");
                    params2.put("phoneNumber2", checkPhoneState != null ? checkPhoneState.getMobile_full() : "");
                    ActivityToActivity.toActivity(ARouterConfig.Me.CHECKAUTHACTIVITY, params2);
                    break;
                case 4:
                    //用户反馈
                    ActivityToActivity.toActivity(ARouterConfig.Me.FEEDBACKACTIVITY);
                    break;
                case 6:
                    //退出登录
                    TextDialog.showBaseDialog(activity, "登出提示", getString(R.string.text_isLoginOut),
                            () -> {
                                LiveBus.getDefault().postEvent(ARouterConfig.LOGIN_OUT, true);
                                SharePreferenceUtil.saveUser(null);
                                SharePreferenceUtil.saveKeyValue("UserPhone", "");
                                finish();
                            })
                            .show();
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * 前往绑定手机
     */
    private void toBindPhone() {
        MaterialDialogUtils.showBasicDialog(SettingActivity.this, "您还未绑定手机，不能进行此操作。是否前往绑定手机？")
                .onPositive((dialog, which) -> ActivityToActivity.toActivity(ARouterConfig.Me.BINDPHONEACTIVITY))
                .show();
    }
}
