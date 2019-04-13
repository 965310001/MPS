package com.mingpinmall.shopping.ui;

import android.Manifest;
import android.content.res.TypedArray;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.activity.BottomNavigationActivity;
import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.manage.AppManager;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.PermissionsUtils;
import com.goldze.common.dmvvm.utils.ResourcesUtils;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.heima.tabview.library.TabView;
import com.heima.tabview.library.TabViewChild;
import com.mingpinmall.cart.ui.CartFragment;
import com.mingpinmall.classz.ui.activity.classify.ClassifyFragment;
import com.mingpinmall.home.ui.HomeFragment;
import com.mingpinmall.me.ui.MeFragment;
import com.mingpinmall.shopping.R;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author: guofeng
 * @CreateDate:
 * @Description: 主界面
 */
@Route(path = ARouterConfig.MAINACTIVITY)
public class MainActivity extends BottomNavigationActivity {

    private long mExitTime;

//    @Override
//    protected void initViews(Bundle savedInstanceState) {
//        showSuccess();
//    }

    @Override
    protected boolean isActionBar() {
        return false;
    }

    @Override
    protected void initData() {
        super.initData();

        List<TabViewChild> tabViewChildList = new ArrayList<>();

        BaseFragment[] fragmentList = {HomeFragment.newInstance(), ClassifyFragment.newInstance(),
                CartFragment.newInstance(), MeFragment.newInstance()};

        TypedArray tabIcon = ResourcesUtils.getInstance().obtainTypedArray(R.array.tab_icon);
        TypedArray tabIconDef = ResourcesUtils.getInstance().obtainTypedArray(R.array.tab_icon_def);
        String[] tabName = ResourcesUtils.getInstance().getStringArray(R.array.tab_name);

        TabViewChild tabViewChild;
        for (int i = 0; i < tabIcon.length(); i++) {
            tabViewChild = new TabViewChild(tabIcon.getResourceId(i, 0),
                    tabIconDef.getResourceId(i, 0),
                    tabName[i],
                    fragmentList[i]);
            tabViewChildList.add(tabViewChild);
        }

        initNavBar(tabViewChildList, getSupportFragmentManager(), new TabView.OnTabChildClickListener() {
            @Override
            public void onTabChildClick(int i, ImageView imageView, TextView textView) {
            }
        });

        //检查文件权限
        if (PermissionsUtils.checkPermissions(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            checkUpdate();
        }
        /*测试*/
//        ActivityToActivity.toActivity(ARouterConfig.classify.INVOICEACTIVITY);
        /*测试商品*/
        ActivityToActivity.goShoppingDetails("109928");
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