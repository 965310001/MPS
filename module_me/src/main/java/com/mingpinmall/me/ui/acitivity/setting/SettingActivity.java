package com.mingpinmall.me.ui.acitivity.setting;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
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
import com.goldze.common.dmvvm.base.bean.UserBean;
import com.mingpinmall.me.ui.constants.Constants;
import com.mingpinmall.me.ui.widget.SettingItemView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/3/25
 **/
@Route(path = ARouterConfig.Me.SETTINGACTIVITY)
public class SettingActivity extends AbsLifecycleActivity<ActivitySettingBinding, MeViewModel> {

    @Autowired
    String name;

    private UserBean userBean;
    private BaseCheckBean checkPhoneState;


    SettingAdapter settingAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        userBean = (UserBean) SharePreferenceUtil.getUser(UserBean.class);
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
        registerObserver(Constants.Err_USER_INFO, String.class).observeForever(result -> TextDialog.showBaseDialog(activity, "获取内容失败", result, new DialogInterface.OnDismissListener() {
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

        settingAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        //修改密码   登录密码
                        if (!checkPhoneState.isState()) {
                            toBindPhone();
                            break;
                        }
                        Map params = new HashMap<>();
                        params.put("type", CheckAuthActivity.RESET_PASSWORD);
                        params.put("phoneNumber", checkPhoneState != null ? checkPhoneState.getMobile() : "");
                        params.put("phoneNumber2", checkPhoneState != null ? checkPhoneState.getMobile_full() : "");
                        ActivityToActivity.toActivity(ARouterConfig.Me.CheckAuthActivity, params);
                        break;
                    case 1:
                        //手机验证
                        if (!checkPhoneState.isState()) {
                            ActivityToActivity.toActivity(ARouterConfig.Me.BindPhoneActivity);
                            break;
                        }
                        Map params1 = new HashMap<>();
                        params1.put("type", CheckAuthActivity.RESET_PHONE);
                        params1.put("phoneNumber", checkPhoneState != null ? checkPhoneState.getMobile() : "");
                        params1.put("phoneNumber2", checkPhoneState != null ? checkPhoneState.getMobile_full() : "");
                        ActivityToActivity.toActivity(ARouterConfig.Me.CheckAuthActivity, params1);
                        break;
                    case 2:
                        //支付密码
                        if (!checkPhoneState.isState()) {
                            toBindPhone();
                            break;
                        }
                        Map params2 = new HashMap<>();
                        params2.put("type", CheckAuthActivity.RESET_PAY_PASSWORD);
                        params2.put("phoneNumber", checkPhoneState != null ? checkPhoneState.getMobile() : "");
                        params2.put("phoneNumber2", checkPhoneState != null ? checkPhoneState.getMobile_full() : "");
                        ActivityToActivity.toActivity(ARouterConfig.Me.CheckAuthActivity, params2);
                        break;
                    case 4:
                        //用户反馈
                        ActivityToActivity.toActivity(ARouterConfig.Me.FeedBackActivity);
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
                }
            }
        });
    }

    /**
     * 前往绑定手机
     */
    private void toBindPhone() {
        MaterialDialogUtils.showBasicDialog(SettingActivity.this, "您还未绑定手机，不能进行此操作。是否前往绑定手机？")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ActivityToActivity.toActivity(ARouterConfig.Me.BindPhoneActivity);
                    }
                })
                .show();
    }
}
