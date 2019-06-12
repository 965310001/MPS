package com.mingpinmall.shopping.ui;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
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
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.utils.log.QLog;
import com.google.gson.Gson;
import com.mingpinmall.cart.ui.CartFragment;
import com.mingpinmall.classz.ui.activity.classify.ClassifyFragment;
import com.mingpinmall.classz.ui.service.IBackService;
import com.mingpinmall.classz.ui.service.SocketIoBroadcast;
import com.mingpinmall.classz.ui.service.SocketIoService;
import com.mingpinmall.home.ui.HomeFragment;
import com.mingpinmall.home.ui.TeacherFragment;
import com.mingpinmall.me.ui.MeFragment;
import com.mingpinmall.me.ui.bean.MyInfoBean;
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

    private IBackService mIBackService;
    private ServiceConnection mConnection;
    private SocketIoBroadcast mReceiver;

    private void registerBroadcastReceiver() {
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction("com.broadcast.test");
        mReceiver = new SocketIoBroadcast();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mReceiver, mFilter);
    }

    /*销毁 service receiver*/
    private void destroyService() {
        try {
            if (null != mConnection) {
                unbindService(mConnection);
            }
            if (null != mReceiver) {
                LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(mReceiver);
            }
        } catch (Exception e) {
            QLog.i(e.toString());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*destroyService();*/
    }

    @Override
    protected boolean isActionBar() {
        return false;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        if (SharePreferenceUtil.isLogin()) {
            mConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    mIBackService = IBackService.Stub.asInterface(service);
                    try {
                        String userInfo = SharePreferenceUtil.getKeyValue("USER_INFO");
                        QLog.i(userInfo);
                        if (!TextUtils.isEmpty(userInfo)) {
                            MyInfoBean.MemberInfoBean bean = new Gson().fromJson(userInfo, MyInfoBean.MemberInfoBean.class);
                            mIBackService.setUrl("");
                            mIBackService.setMemberInfo(bean.getId(),
                                    bean.getUser_name(), bean.getAvatar(),
                                    "", "", "");
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        QLog.i(e.toString());
                    }
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    mIBackService = null;
                }
            };
            Intent intent = new Intent(getApplicationContext(), SocketIoService.class);
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            bindService(intent, mConnection, BIND_AUTO_CREATE);
            startService(intent);
            registerBroadcastReceiver();
            QLog.i("开始连接");
        }

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

        LiveBus.getDefault().subscribe(ARouterConfig.LOGIN_OUT).observeForever(isLogin -> destroyService());

        // TODO: 2019/4/19 测试
//        ActivityToActivity.toActivity(ARouterConfig.classify.STOREACTIVITY);
//        ActivityToActivity.goShoppingDetails("106911"); //进入聊天  服务 7天退货 品质承诺
//        ActivityToActivity.goShoppingDetails("106911"); //进入聊天  服务 7天退货 品质承诺
//        ActivityToActivity.goShoppingDetails("110387"); //虚拟
//        ActivityToActivity.toActivity(ARouterConfig.home.SEARCHACTIVITY);
//        ActivityToActivity.toActivity(ARouterConfig.home.SEARCHACTIVITY);
//        ActivityToActivity.toActivity(ARouterConfig.classify.HOLOACTIVITY, "url", " ");
//        ActivityToActivity.toActivity(ARouterConfig.home.SHOPSTREETACTIVITY);

        // TODO: 试戴测试 110376
//        Map<String, Object> params = new HashMap<>(2);
//        params.put("goods_id", "110381");
//        params.put("cart_count", "3");
//        ActivityToActivity.toActivity(ARouterConfig.classify.HOLO4ACTIVITY, params);

//        String url = "https://www.feeai.cn/fitting/?shop_id=1097e32594e07daf671d50ad93fca1a9&shop_secret=daa98398d09cf846869c0fd2094d08df&cat_id=7&goods_id=361&version=189";
//        String url = "https://www.feeai.cn/fitting";
//        ActivityToActivity.toActivity(ARouterConfig.SMARTWEBACTIVITY, "url", url);
//        ActivityToActivity.toActivity(ARouterConfig.classify.HOLO2ACTIVITY, "url", url);
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
//                        QLog.i("onComplete" + s.toString());
//                    }
//
//                    @Override
//                    public void onNext(AppInfo result) {
//
//                        QLog.i("----------------------");
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        QLog.i(t.toString());
//                        QLog.i("--------Throwable--------------");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        QLog.i("onComplete");
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
                SharePreferenceUtil.saveBooleanKeyValue("ISSOCKET_DISCONNECT", false);
                if (mExitTime != 0) {
                    mExitTime = 0;
                }
            }
            return true;
        }
        return false;
    }

}