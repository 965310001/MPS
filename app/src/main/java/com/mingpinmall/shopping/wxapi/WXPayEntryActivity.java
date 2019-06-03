package com.mingpinmall.shopping.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.goldze.common.dmvvm.utils.log.QLog;
import com.mingpinmall.apppay.pay.WeiXinBaoStrategy;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * 微信支付
 */

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    /*private IWXAPI api;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.ac_wxpay_entry);*/
      /*  api = WXAPIFactory.createWXAPI(this, Constants.WEIXIN_APP_ID);
        api.handleIntent(getIntent(), this);*/

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
     /*   api.handleIntent(intent, this);
        Log.i("TAG", "微信支付onNewIntent");*/

        if (WeiXinBaoStrategy.getInstance(this) != null) {
            WeiXinBaoStrategy.getInstance(this).getWXApi().handleIntent(intent, this);
        }
    }


    @Override
    public void onReq(BaseReq baseReq) {
        QLog.i(baseReq.transaction + baseReq.openId);
    }

    @Override
    public void onResp(BaseResp resp) {
        QLog.i(resp.getType() + " " + resp.errCode + " " + resp.errStr + " " + resp.transaction + " " + resp.openId);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (WeiXinBaoStrategy.getInstance(this) != null) {
                if (resp.errStr != null) {
                    Log.e("weiXinPay", "errStr=" + resp.errStr);
                }
                switch (resp.errCode) {
                    case 0:
                        QLog.i("成功");
                        break;
                    case -1:
                        resp.errStr = String.format("%s", "可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。");
                        break;
                    case -2:
                        resp.errStr = String.format("%s", "您已取消付款");
                        break;
                }
                WeiXinBaoStrategy.getInstance(this).onResp(resp.errCode, resp.errStr);
                /*finish();*/
            }
           /* switch (resp.errCode) {
                case 0:
                    QLog.i("成功");
                    break;
                case -1:
                    QLog.i("可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。\n");
                    Toast.makeText(this, "可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。!", Toast.LENGTH_SHORT).show();
                    break;
                case -2:
                    Toast.makeText(this, "您已取消付款!", Toast.LENGTH_SHORT).show();
                    break;
            }*/
        }
        finish();
    }
}