package me.goldze.common.constants;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;



import me.goldze.common.utils.ActivityToActivity;
import me.goldze.common.utils.SharePreferenceUtil;
import me.goldze.common.utils.log.QLog;

/**
 * @author GuoFeng
 * @date : 2019/2/22 10:47
 * @description: 实现拦截登录功能
 */
/*priority: 优先级，越小，优先级越高*/
@Interceptor(priority = 1, name = "LoginInterceptor")
public class LoginInterceptor implements IInterceptor {

    /*private Context mContext;*/

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        /*, extras = 200*/
        QLog.i("LoginInterceptor 开始执行" + postcard.getExtra() + postcard.getPath());
        /*QLog.i(mContext);*/
        //给需要跳转的页面添加值为Constant.LOGIN_NEEDED的extra参数，用来标记是否需要用户先登录才可以访问该页面
        if (postcard.getExtra() == ARouterConfig.LOGIN_NEEDED) {
            boolean isLogin = null != SharePreferenceUtil.getUser(Object.class);
            QLog.i("是否已登录: " + isLogin);
            if (isLogin) {
                callback.onContinue(postcard);
            } else {//没有登录,注意需要传入context
                QLog.i("去登录");
                callback.onInterrupt(null);
                ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
            }
        } else {
            /*没有extra参数时则继续执行，不做拦截*/
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {
    }
}