package com.mingpinmall.apppay.pay;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.goldze.common.dmvvm.utils.log.QLog;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * 微信
 */
public class WeiXinBaoStrategy implements Strategy {

    private static WeiXinBaoStrategy mWeiXinPay;
    private Context mContext;
    private IWXAPI mIWXAPI;
    private JPayListener mJPayListener;

    //未安装微信或微信版本过低
    public static final int WEIXIN_VERSION_LOW = 0x001;
    //支付参数异常
    public static final int PAY_PARAMETERS_ERROE = 0x002;
    //支付失败
    public static final int PAY_ERROR = 0x003;

    private WeiXinBaoStrategy(Context mContext) {
        this.mContext = mContext;
    }

    public static WeiXinBaoStrategy getInstance(Activity context) {
        if (mWeiXinPay == null) {
            synchronized (WeiXinBaoStrategy.class) {
                if (mWeiXinPay == null) {
                    mWeiXinPay = new WeiXinBaoStrategy(context);
                }
            }
        }
        return mWeiXinPay;
    }

    @Override
    public void pay(Map<String, String> map, JPayListener listener) {
        this.mJPayListener = listener;
        try {
            startWXPay(map.get("appId"),
                    map.get("partnerId"),
                    map.get("prepayId"),
                    map.get("nonceStr"),
                    map.get("timeStamp"),
                    map.get("sign"), mJPayListener);
        } catch (Exception e) {
            Log.i("TAG", e.toString());
        }
    }

    /**
     * 初始化微信支付接口
     *
     * @param appId
     */
    public void init(String appId) {
        mIWXAPI = WXAPIFactory.createWXAPI(mContext, appId);
        mIWXAPI.registerApp(appId);
    }

    /**
     * 获取微信接口
     *
     * @return
     */
    public IWXAPI getWXApi() {
        return mIWXAPI;
    }

    /**
     * 调起支付
     *
     * @param appId
     * @param partnerId
     * @param prepayId
     * @param nonceStr
     * @param timeStamp
     * @param sign
     */
    private void startWXPay(String appId, String partnerId, String prepayId,
                            String nonceStr, String timeStamp, String sign, JPayListener listener) {
        mJPayListener = listener;
        init(appId);

        if (!checkWx()) {
            if (listener != null) {
                listener.onPayError(WEIXIN_VERSION_LOW, "未安装微信或者微信版本过低");
            }
            return;
        }
        PayReq request = new PayReq();
        request.appId = appId;
        request.partnerId = partnerId;
        request.prepayId = prepayId;
        request.packageValue = "Sign=WXPay";
        request.nonceStr = nonceStr;
        request.timeStamp = timeStamp;
        request.sign = sign;
        mIWXAPI.sendReq(request);
    }

    /**
     * 微信分享
     *
     * @param appId
     * @param flag  0:分享到微信好友，1：分享到微信朋友圈 2:收藏
     */
    public void wechatShare(String appId, int flag, Map<String, String> map, JPayListener listener) {
        init(appId);
        if (!checkWx()) {
            if (listener != null) {
                listener.onPayError(WEIXIN_VERSION_LOW, "未安装微信或者微信版本过低");
            }
            return;
        }
        if (null != map) {
            try {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        WXWebpageObject webpageObject = new WXWebpageObject();
                        webpageObject.webpageUrl = map.get("url");
                        WXMediaMessage msg = new WXMediaMessage(webpageObject);
                        msg.title = TextUtils.isEmpty(map.get("title")) ? "" : map.get("title");
                        msg.description = TextUtils.isEmpty(map.get("description")) ? "" : map.get("description");
                        String imageurl = TextUtils.isEmpty(map.get("imageurl")) ? "" : map.get("imageurl");
                        //这里替换一张自己工程里的图片资源
                        try {
                            Bitmap thumb = BitmapFactory.decodeStream(new URL(imageurl).openStream());
                            thumb = Bitmap.createScaledBitmap(thumb, 120, 150, true);
                            msg.setThumbImage(thumb);
                            thumb.recycle();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        SendMessageToWX.Req req = new SendMessageToWX.Req();
                        req.transaction = String.valueOf(System.currentTimeMillis());
                        req.message = msg;
                        switch (flag) {
                            case 0:
                                req.scene = SendMessageToWX.Req.WXSceneSession;
                                break;
                            case 1:
                                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                                break;
                            case 2:
                                req.scene = SendMessageToWX.Req.WXSceneFavorite;
                                break;
                            default:
                                req.scene = SendMessageToWX.Req.WXSceneSession;
                                break;
                        }
                        mIWXAPI.sendReq(req);
                    }
                }.start();
            } catch (Exception e) {
                Log.i("TAG", e.toString());
            }
        }
    }

    //检测微信客户端是否支持微信支付
    private boolean checkWx() {
        try {
            return isWeixinAvilible() && mIWXAPI.isWXAppInstalled() && mIWXAPI.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        } catch (Exception e) {
            QLog.i(e.toString());
        }
        return false;
    }

    /**
     * 响应支付回调
     *
     * @param error_code
     * @param message
     */
    public void onResp(int error_code, String message) {
        if (mJPayListener == null) {
            return;
        }
        if (error_code == 0) {
            //支付成功
            mJPayListener.onPaySuccess();
        } else if (error_code == -1) {
            //支付异常
            mJPayListener.onPayError(PAY_ERROR, message);
        } else if (error_code == -2) {
            //支付取消
            mJPayListener.onPayCancel();
        }

        mJPayListener = null;
    }

    /**
     * 判断微信是否安装
     *
     * @return
     */
    private boolean isWeixinAvilible() {
        return appIsAvilible("com.tencent.mm");
    }

    /**
     * 判断app是否安装
     *
     * @param packageName
     * @return
     */
    private boolean appIsAvilible(String packageName) {
        final PackageManager packageManager = mContext.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
