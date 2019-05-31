package com.mingpinmall.shopping.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Window;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baidu.mobstat.StatService;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.PermissionsUtils;
import com.goldze.common.dmvvm.utils.StatusBarUtils;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.shopping.R;
import com.mingpinmall.shopping.databinding.ActivitySplashBinding;

import java.util.Objects;

/**
 * 因为特殊的需求，所以这里没有继承BaseActivity。
 * @author 小斌
 * @data 2019/5/18
 **/
@Route(path = ARouterConfig.SPLASHACTIVITY)
public class SplashActivity extends AppCompatActivity {

    //启动时间间隔为2.5s
//    private final int LOAD_DISPLAY_TIME = 2500;
//    private ActivitySplashBinding binding;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setBackgroundDrawableResource(R.color.white);
        /*竖屏*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        StatusBarUtils.immersive(this, true);
        super.onCreate(savedInstanceState);
        //*****************
        //百度应用统计服务
        StatService.start(this);
        //设置打开Crash日志收集。
        // 仅收集java crash，flag = StatService.JAVA_EXCEPTION_LOG
        // 同时收集java 和 native crash，flag = StatService.EXCEPTION_LOG
        StatService.setOn(this, StatService.EXCEPTION_LOG);
        //设置启动后日志发送延时时间 默认值0，启动时刻立即load数据发送日志
        StatService.setLogSenderDelayed(5);
        //设置应用进入后台再回到前台为同一次启动的最大间隔时间，有效值范围0～600s，例如设置值30s，则应用进入后台后，30s内唤醒为同一次启动
        StatService.setSessionTimeOut(30);
        //正式版本中，为避免影响APP性能，请关闭调试。
        StatService.setDebugOn(true);
        //*****************
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        if (!this.isTaskRoot()) {
            //判断该Activity是不是任务空间的源Activity，“非”也就是说是被系统重新实例化出来
            //如果你就放在launcher Activity中话，这里可以直接return了
            Intent mainIntent = getIntent();
            String action = mainIntent.getAction();
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && Objects.equals(action, Intent.ACTION_MAIN)) {
                finish();
                return; //finish()之后该活动会继续执行后面的代码，你可以logCat验证，加return避免可能的exception
            }
        }
        ActivitySplashBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_splash, null, false);
        setContentView(binding.getRoot());
        new Handler().postDelayed(this::toNextActivity, 2500);
    }

    private void toNextActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //在欢迎页面这，禁止用户按返回键退出app
        return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event);
    }
}
