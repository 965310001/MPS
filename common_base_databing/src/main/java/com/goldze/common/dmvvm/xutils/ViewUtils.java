package com.goldze.common.dmvvm.xutils;

import android.app.Activity;
import android.util.Log;

import com.goldze.common.dmvvm.utils.AssetsUtils;
import com.google.gson.Gson;
import com.socks.library.KLog;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 解析
 */
@Aspect
public class ViewUtils {

    private final String POINT_CUT_DOUBLE_CLICK = "execution(@com.goldze.common.dmvvm.xutils * *(..))";

    public static void inject(Activity activity) {
        BindString bindString;
        BindArray bindArray;
        BindAssets bindAssets;
        BindIntArray bindIntArray;

        /*绑定View*/
        Class<? extends Activity> clazz = activity.getClass();
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (null != contentView) {
            try {
                Method getLayoutId = clazz.getMethod("getLayoutId");
                getLayoutId.setAccessible(true);
                LayoutIdInvocationHandler layoutIdInvocationHandler
                        = new LayoutIdInvocationHandler(getLayoutId, contentView.value());
                Object proxy = Proxy.newProxyInstance(clazz.getClassLoader(),
                        getLayoutId.getClass().getInterfaces(),
                        layoutIdInvocationHandler);
//                getLayoutId.invoke(activity);
//                getLayoutId.getReturnType()

                getLayoutId.invoke(proxy);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /*绑定String*/
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            field.setAccessible(true);
            /*绑定String*/
            bindString = field.getAnnotation(BindString.class);
            if (null != bindString) {
                try {
                    field.set(activity, activity.getResources().getString(bindString.value()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            /*绑定Array*/
            bindArray = field.getAnnotation(BindArray.class);
            if (null != bindArray) {
                try {
                    field.set(activity, activity.getResources().getStringArray(bindArray.value()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            /*解析Assets*/
            bindAssets = field.getAnnotation(BindAssets.class);
            if (null != bindAssets) {
                try {
                    field.set(activity, new Gson().fromJson(AssetsUtils.getStringFromAssert(activity.getApplication(), bindAssets.value()), bindAssets.clazz()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            /*解析int 数组*/
            bindIntArray = field.getAnnotation(BindIntArray.class);
            if (null != bindIntArray) {
                try {
                    field.set(activity, activity.getResources().getIntArray(bindIntArray.value()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }


    }

    /*代理*/
    static class LayoutIdInvocationHandler implements InvocationHandler {

        private int value;
//        private Method method;

        public LayoutIdInvocationHandler(Method method, int value) {
            this.value = value;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            KLog.i(value + "");
            return value;
        }
    }

    //切入点
    @Pointcut(POINT_CUT_DOUBLE_CLICK)
    public void doubleClick() {
    }

    //advice通知
    @Around("doubleClick()")
    public int execute(ProceedingJoinPoint joinPoint) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            ContentView annotation = signature.getMethod()
                    .getAnnotation(ContentView.class);
            if (annotation != null) {
                //处理自己的逻辑
                Log.e("KKKk", "点击拦截");
                int proceed = (int) joinPoint.proceed();
                proceed=annotation.value();
                return proceed;
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return 0;
    }

}
