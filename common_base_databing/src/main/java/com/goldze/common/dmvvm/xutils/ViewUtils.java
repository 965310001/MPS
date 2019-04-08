package com.goldze.common.dmvvm.xutils;

import android.app.Activity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 解析
 */
public class ViewUtils {

    public static void inject(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (null != contentView) {
            try {
                Method getLayoutId = clazz.getMethod("getLayoutId", null);
                getLayoutId.setAccessible(true);
                LayoutIdInvocationHandler layoutIdInvocationHandler = new LayoutIdInvocationHandler(getLayoutId, activity);
                Object proxy = Proxy.newProxyInstance(clazz.getClassLoader(),
                        new Class[]{getLayoutId.getClass()}, layoutIdInvocationHandler);
                getLayoutId.invoke(activity, proxy);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /*代理*/
    static class LayoutIdInvocationHandler implements InvocationHandler {

        private int value;
        private Method method;
        private Object receiver;

        public LayoutIdInvocationHandler() {
        }

        public LayoutIdInvocationHandler(Method method, Object receiver) {
            this.method = method;
            this.receiver = receiver;
        }

        public LayoutIdInvocationHandler(Method method, Object receiver, int value) {
            this.method = method;
            this.receiver = receiver;
            this.value = value;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//            this.method.invoke(receiver, args);
//            return proxy;
            return value;
        }
    }
}
