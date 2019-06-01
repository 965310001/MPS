package com.mingpinmall.shopping.ui;

import android.Manifest;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.KeyEvent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.databinding.ActivityHomeNavigationBinding;
import com.goldze.common.dmvvm.manage.AppManager;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.PermissionsUtils;
import com.goldze.common.dmvvm.utils.ResourcesUtils;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.cart.ui.CartFragment;
import com.mingpinmall.classz.ui.activity.classify.ClassifyFragment;
import com.mingpinmall.home.ui.HomeFragment;
import com.mingpinmall.home.ui.TeacherFragment;
import com.mingpinmall.me.ui.MeFragment;
import com.mingpinmall.shopping.R;

/**
 * @Author: guofeng
 * @CreateDate:
 * @Description: 主界面
 * <p>
 * 修改：小斌
 * 最后修改日期：2019/4/26
 * 修改内容：
 * 1.更换拓展性更好，性能更佳的BottomBar
 * 2.添加监听，切换首页Fragment
 */
@Route(path = ARouterConfig.MAINACTIVITY)
public class MainActivity extends BaseActivity<ActivityHomeNavigationBinding> {

    private long mExitTime;
    /*标识是否处于活动状态*/
    private boolean isResume = false;
    /*小于0则不作操作，大于等于0则切换fragment*/
    private int index = -1;

    @Override
    protected void onPause() {
        super.onPause();
        isResume = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isResume = true;
        if (index >= 0) {
            binding.bottomBar.setCurrentItem(index);
            index = -1;
        }
    }

    @Override
    protected boolean isActionBar() {
        return false;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setDarkMode(false);
        Class[] fragmentList = new Class[]{
                HomeFragment.class,
                ClassifyFragment.class,
                TeacherFragment.class,
                CartFragment.class,
                MeFragment.class
        };

        TypedArray tabIcon = ResourcesUtils.getInstance().obtainTypedArray(R.array.tab_icon);
        TypedArray tabIconDef = ResourcesUtils.getInstance().obtainTypedArray(R.array.tab_icon_def);
        String[] tabName = ResourcesUtils.getInstance().getStringArray(R.array.tab_name);

        binding.bottomBar.setContainer(R.id.home_content)
                //params[0]：UnSelectTextColor  params[1]：OnSelectTextColor
                .setTitleBeforeAndAfterColor(R.color.dark, R.color.app_theme_d61619);

        for (int i = 0; i < tabIcon.length(); i++) {
            binding.bottomBar.addItem(
                    fragmentList[i],//Fragment
                    tabName[i],//FragmentTitle
                    tabIconDef.getResourceId(i, 0),//UnSelectTabIcon
                    tabIcon.getResourceId(i, 0)//OnSelectTabIcon
            );
        }
        binding.bottomBar.build();

        //检查文件权限
        if (PermissionsUtils.checkPermissions(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE)) {
            checkUpdate();
        } else {
            ToastUtils.showShort("没有权限");
        }

        /*监听切换Fragment*/
        LiveBus.getDefault().subscribe("Main", "tab", Integer.class).observeForever(position -> {
            //处于活动状态则直接切换fragment，否则记录要切换到的fragment，然后等待生命周期onresume调用
            if (isResume) {
                binding.bottomBar.setCurrentItem(position == null ? 0 : position);
            } else {
                index = position == null ? -1 : position;
            }
        });
        // TODO: 2019/4/19 测试
//        ActivityToActivity.toActivity(ARouterConfig.classify.STOREACTIVITY);
//        ActivityToActivity.goShoppingDetails("106911"); //进入聊天  服务 7天退货 品质承诺
//        ActivityToActivity.goShoppingDetails("106911"); //进入聊天  服务 7天退货 品质承诺
//        ActivityToActivity.goShoppingDetails("110351"); //虚拟
//        ActivityToActivity.toActivity(ARouterConfig.home.SEARCHACTIVITY);
//        ActivityToActivity.toActivity(ARouterConfig.home.SEARCHACTIVITY);
//        ActivityToActivity.toActivity(ARouterConfig.classify.HOLOACTIVITY, "url", " ");
//        ActivityToActivity.toActivity(ARouterConfig.classify.STOREACTIVITY, "storeId", "3");
//        ActivityToActivity.toActivity(ARouterConfig.home.SHOPSTREETACTIVITY);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_navigation;
    }

    private void checkUpdate() {
//        RetrofitClient.getInstance().getApiService().checkUpdate()
//                .compose(RxSchedulers.<AppInfo>io_main())
//                .subscribe(new Subscriber<AppInfo>() {
//                    @Override
//                    public void onSubscribe(Subscription s) {
//                        KLog.i("onComplete" + s.toString());
//                    }
//
//                    @Override
//                    public void onNext(AppInfo result) {
//
//                        KLog.i("----------------------");
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        KLog.i(t.toString());
//                        KLog.i("--------Throwable--------------");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        KLog.i("onComplete");
//                    }
//                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                ToastUtils.showLong("双击退出应用");
                mExitTime = System.currentTimeMillis();
            } else {
                AppManager.getInstance().finishAllActivity();
                if (mExitTime != 0) {
                    mExitTime = 0;
                }
            }
            return true;
        }
        return false;
    }

}