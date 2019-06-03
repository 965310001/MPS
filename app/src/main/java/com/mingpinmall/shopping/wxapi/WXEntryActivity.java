package com.mingpinmall.shopping.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.utils.log.QLog;
import com.mingpinmall.apppay.pay.WeiXinBaoStrategy;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ui.constants.Constants;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.SubscribeMessage;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelbiz.WXOpenBusinessView;
import com.tencent.mm.opensdk.modelbiz.WXOpenBusinessWebview;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * 微信发送消息
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static String TAG = "MicroMsg.WXEntryActivity";

    private IWXAPI api;
    private MyHandler handler;

    private static class MyHandler extends Handler {
        private final WeakReference<WXEntryActivity> wxEntryActivityWeakReference;

        public MyHandler(WXEntryActivity wxEntryActivity) {
            wxEntryActivityWeakReference = new WeakReference<>(wxEntryActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            int tag = msg.what;
            switch (tag) {
                case Constants.GET_TOKEN: {
                    Bundle data = msg.getData();
                    JSONObject json = null;
                    try {
                        json = new JSONObject(data.getString("result"));
                        String openId, accessToken, refreshToken, scope;
                        openId = json.getString("openid");
                        accessToken = json.getString("access_token");
                        refreshToken = json.getString("refresh_token");
                      /*  scope = json.getString("scope");
                        Intent intent = new Intent(wxEntryActivityWeakReference.get(), SendToWXActivity.class);
                        intent.putExtra("openId", openId);
                        intent.putExtra("accessToken", accessToken);
                        intent.putExtra("refreshToken", refreshToken);
                        intent.putExtra("scope", scope);
                        wxEntryActivityWeakReference.get().startActivity(intent);*/
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, Constants.WEIXIN_APP_ID, false);
        handler = new MyHandler(this);

        try {
            Intent intent = getIntent();
            api.handleIntent(intent, this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (WeiXinBaoStrategy.getInstance(this) != null) {
            WeiXinBaoStrategy.getInstance(this).getWXApi().handleIntent(getIntent(), this);
        } else {
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);

        if (WeiXinBaoStrategy.getInstance(this) != null) {
            WeiXinBaoStrategy.getInstance(this).getWXApi().handleIntent(intent, this);
        }
    }

    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                goToGetMsg();
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                /*goToShowMsg((ShowMessageFromWX.Req) req);*/
                break;
            default:
                break;
        }
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        int result = 0;

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.errcode_success;
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                result = R.string.errcode_unsupported;
                break;
            case ConstantsAPI.COMMAND_PAY_BY_WX:
                QLog.i("微信支付");
                break;
            default:
                result = R.string.errcode_unknown;
                break;
        }

        resp.errStr = getString(result);
        WeiXinBaoStrategy.getInstance(this).onResp(resp.errCode, resp.errStr);


        if (resp.getType() == ConstantsAPI.COMMAND_SUBSCRIBE_MESSAGE) {
            SubscribeMessage.Resp subscribeMsgResp = (SubscribeMessage.Resp) resp;
            String text = String.format("openid=%s\ntemplate_id=%s\nscene=%d\naction=%s\nreserved=%s",
                    subscribeMsgResp.openId, subscribeMsgResp.templateID, subscribeMsgResp.scene, subscribeMsgResp.action, subscribeMsgResp.reserved);

            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }

        if (resp.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
            WXLaunchMiniProgram.Resp launchMiniProgramResp = (WXLaunchMiniProgram.Resp) resp;
            String text = String.format("openid=%s\nextMsg=%s\nerrStr=%s",
                    launchMiniProgramResp.openId, launchMiniProgramResp.extMsg, launchMiniProgramResp.errStr);

            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }

        if (resp.getType() == ConstantsAPI.COMMAND_OPEN_BUSINESS_VIEW) {
            WXOpenBusinessView.Resp launchMiniProgramResp = (WXOpenBusinessView.Resp) resp;
            String text = String.format("openid=%s\nextMsg=%s\nerrStr=%s\nbusinessType=%s",
                    launchMiniProgramResp.openId, launchMiniProgramResp.extMsg, launchMiniProgramResp.errStr, launchMiniProgramResp.businessType);

            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }

        if (resp.getType() == ConstantsAPI.COMMAND_OPEN_BUSINESS_WEBVIEW) {
            WXOpenBusinessWebview.Resp response = (WXOpenBusinessWebview.Resp) resp;
            String text = String.format("businessType=%d\nresultInfo=%s\nret=%d", response.businessType, response.resultInfo, response.errCode);

            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }

        if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
            SendAuth.Resp authResp = (SendAuth.Resp) resp;
            final String code = authResp.code;
/*
            NetworkUtil.sendWxAPI(handler, String.format("https://api.weixin.qq.com/sns/oauth2/access_token?" +
                            "appid=%s&secret=%s&code=%s&grant_type=authorization_code", "wxd930ea5d5a258f4f",
                    "1d6d1d57a3dd063b36d917bc0b44d964", code), NetworkUtil.GET_TOKEN);
*/
        }
        finish();
    }

    private void goToGetMsg() {
       /* Intent intent = new Intent(this, GetFromWXActivity.class);
        intent.putExtras(getIntent());
        startActivity(intent);
        finish();*/
    }

//	private void goToShowMsg(ShowMessageFromWX.Req showReq) {
//		WXMediaMessage wxMsg = showReq.message;
//		WXAppExtendObject obj = (WXAppExtendObject) wxMsg.mediaObject;
//
//		StringBuffer msg = new StringBuffer();
//		msg.append("description: ");
//		msg.append(wxMsg.description);
//		msg.append("\n");
//		msg.append("extInfo: ");
//		msg.append(obj.extInfo);
//		msg.append("\n");
//		msg.append("filePath: ");
//		msg.append(obj.filePath);
//
//		Intent intent = new Intent(this, ShowFromWXActivity.class);
//		intent.putExtra(Constants.ShowMsgActivity.STitle, wxMsg.title);
//		intent.putExtra(Constants.ShowMsgActivity.SMessage, msg.toString());
//		intent.putExtra(Constants.ShowMsgActivity.BAThumbData, wxMsg.thumbData);
//		startActivity(intent);
//		finish();
//	}
}