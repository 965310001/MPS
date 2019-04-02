package com.mingpinmall.me.ui.acitivity.setting;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.util.DialogUtils;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.dialog.MaterialDialogUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivitySettingBinding;
import com.mingpinmall.me.ui.adapter.SettingAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.BaseCheckBean;
import com.mingpinmall.me.ui.bean.BaseItemBean;
import com.mingpinmall.me.ui.bean.UserBean;
import com.mingpinmall.me.ui.widget.SettingItemView;
import com.xuexiang.xui.widget.dialog.DialogLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/3/25
 **/
@Route(path = ARouterConfig.SETTINGACTIVITY)
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

        mViewModel.getPayPwdInfo();
        mViewModel.getPhoneInfo();
    }

    @Override
    protected Object getStateEventKey() {
        return null;
    }

    @Override
    public void onDestroy() {
        LiveBus.getDefault().clear("USER_PAYPWD_INFO");
        LiveBus.getDefault().clear("USER_PHONE_INFO");
        super.onDestroy();
    }

    @Override
    protected void dataObserver() {
        LiveBus.getDefault().subscribe("USER_PAYPWD_INFO", BaseCheckBean.class)
                .observeForever(new Observer<BaseCheckBean>() {
                    @Override
                    public void onChanged(@Nullable BaseCheckBean result) {
                        settingAdapter.getData().get(2).setSubTitle(result.getDatas().isState() ? "已验证" : "未验证");
                        settingAdapter.notifyDataSetChanged();
                    }
                });
        LiveBus.getDefault().subscribe("USER_PHONE_INFO", BaseCheckBean.class)
                .observeForever(new Observer<BaseCheckBean>() {
                    @Override
                    public void onChanged(@Nullable BaseCheckBean result) {
                        checkPhoneState = result;
                        settingAdapter.getData().get(1).setSubTitle(result.getDatas().isState() ? result.getDatas().getMobile() : "未绑定");
                        settingAdapter.notifyDataSetChanged();
                    }
                });
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
                        ActivityToActivity.toActivity(ARouterConfig.ResetPasswordActivity,
                                "phoneNumber", checkPhoneState != null ?
                                        checkPhoneState.getDatas().getMobile() :
                                        "");
                        break;
                    case 1:
                        //手机验证
                        ActivityToActivity.toActivity(ARouterConfig.ResetPhoneActivity,
                                "phoneNumber", checkPhoneState != null ?
                                        checkPhoneState.getDatas().getMobile() :
                                        "");
                        break;
                    case 2:
                        //支付密码
                        ActivityToActivity.toActivity(ARouterConfig.ResetPayPwdActivity,
                                "phoneNumber", checkPhoneState != null ?
                                        checkPhoneState.getDatas().getMobile() :
                                        "");
                        break;
                    case 4:
                        //用户反馈
                        ActivityToActivity.toActivity(ARouterConfig.FeedBackActivity);
                        break;
                    case 6:
                        //退出登录
                        MaterialDialogUtils.showBasicDialog(SettingActivity.this, getString(R.string.text_isLoginOut))
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        LiveBus.getDefault().postEvent("LOGIN_OUT", true);
                                        SharePreferenceUtil.saveUser(null);
                                        SharePreferenceUtil.saveKeyValue("UserPhone", "");
                                        finish();
                                    }
                                })
                                .show();
                }
            }
        });
    }
}
