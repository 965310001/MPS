package com.mingpinmall.classz.ui.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.utils.log.QLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mingpinmall.classz.ui.vm.bean.MsgInfo;


import java.util.List;

/**
 * 聊天消息处理
 */
public class SocketIoBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("com.broadcast.test")) {
            final String str = intent.getStringExtra("com.broadcast.test.string");
            if (!TextUtils.isEmpty(str) && !"{}".equals(str)) {
                try {
                    Gson gson = new Gson();
                    List<MsgInfo.MsgBean> msgBeans = gson.fromJson(str, new TypeToken<List<MsgInfo.MsgBean>>() {
                    }.getType());

                    LiveBus.getDefault().postEvent("UPDATE_CHAT_LIST", "UPDATE_CHAT_LIST", msgBeans);
                } catch (Exception e) {
                    QLog.i(e.toString());
                }
            }
        }

    }

}