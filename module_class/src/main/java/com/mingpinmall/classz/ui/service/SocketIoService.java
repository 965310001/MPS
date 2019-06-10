package com.mingpinmall.classz.ui.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.log.QLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * SocketIo
 */
public class SocketIoService extends Service {

    private LocalBroadcastManager mLocalBroadcastManager;
    private WeakReference<Socket> mSocket;

    private final IBackService.Stub iBackService = new IBackService.Stub() {

        private String mSavatar;
        private String mSname;
        private String mSid;
        private String mAvatar;
        private String mName;
        private String mUid;
        private String mUrl;

        /**
         * 收到内容发送消息
         * @param message 需要发送到服务器的消息
         */
        @Override
        public boolean sendMessage(String message) {
            if (!TextUtils.isEmpty(message) && message.contains("|")) {
                QLog.i("已经阅读信息");
                String[] strings = message.split("|");
                mSocket.get().emit("delMsg", strings[0], strings[1]);
            } else {
                QLog.i(message);
                mSocket.get().emit("send_msg", message);
            }
            return false;
        }

        @Override
        public String getUrl() {
            return mUrl;
        }

        @Override
        public void setUrl(String url) {
            this.mUrl = url;
        }

        @Override
        public void setMemberInfo(String uId, String uName, String avatar, String sId, String sName, String sAvatar) {
            this.mUid = uId;
            this.mName = uName;
            this.mAvatar = avatar;
            this.mSid = sId;
            this.mSname = sName;
            this.mSavatar = sAvatar;
        }

        @Override
        public String getUid() {
            return mUid;
        }

        @Override
        public String getUname() {
            return mName;
        }

        @Override
        public String getAvatar() {
            return mAvatar;
        }

        @Override
        public String getSid() {
            return mSid;
        }

        @Override
        public String getSname() {
            return mSname;
        }

        @Override
        public String getsAvatar() {
            return mSavatar;
        }
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return iBackService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new InitSocketThread().start();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    private void initSocket() {//初始化Socket
        if (null == mSocket || null == mSocket.get()) {
            IO.Options opts = new IO.Options();
            opts.path = "/socket.io";
            opts.reconnection = false;
            opts.sslContext = SSLSocket.genSSLSocketFactory();

            try {
                if (null == iBackService.getUrl()) {
                    QLog.i("iBackService.getUrl() is null");
                    iBackService.setUrl("https://www.mingpinmall.cn:8091");
                }
                mSocket = new WeakReference<>(IO.socket(iBackService.getUrl(), opts));
                mSocket.get().on(Socket.EVENT_CONNECT, onConnect)
                        .on(Socket.EVENT_DISCONNECT, onDisconnect)
                        .on("delMsg", onDelMsg)
                        .on("get_msg", onGetMsg);
                mSocket.get().connect();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    QLog.i(e.toString() + iBackService.getUrl());
                } catch (Exception e1) {
                    QLog.i(e.toString());
                }
            }
        }
    }

    private final Emitter.Listener onConnect =
            new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject member = new JSONObject();
                    try {
                        member.put("u_id", iBackService.getUid());
                        member.put("u_name", iBackService.getUname());
                        member.put("avatar", iBackService.getAvatar());

                        if (!TextUtils.isEmpty(iBackService.getSid())) {
                            member.put("s_id", iBackService.getSid());
                        }
                        if (!TextUtils.isEmpty(iBackService.getSname())) {
                            member.put("s_name", iBackService.getSname());
                        }
                        if (!TextUtils.isEmpty(iBackService.getsAvatar())) {
                            member.put("s_avatar", iBackService.getsAvatar());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        QLog.i("JSON连接:" + e.toString());
                    }
                    mSocket.get().emit("update_user", member);

                    QLog.i("连接成功");
                    SharePreferenceUtil.saveBooleanKeyValue("ISSOCKET_DISCONNECT", true);
                }
            };

    private final Emitter.Listener onDisconnect = args -> {
        QLog.i("断开连接");
        SharePreferenceUtil.saveBooleanKeyValue("ISSOCKET_DISCONNECT", false);
        /*更新后台*/
//            mSocket.get().emit("", "");
    };

    private final Emitter.Listener onGetMsg = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            for (final Object arg : args) {
                String str = arg.toString();
                if (!TextUtils.isEmpty(str) && !"{}".equals(str)) {
                    try {
                        new Thread(() -> {
                            QLog.i(arg.toString());
//                            Gson gson = new Gson();
//                            List<MsgInfo.MsgBean> msgBeans = gson.fromJson(arg.toString(), new TypeToken<List<MsgInfo.MsgBean>>() {
//                            }.getType());
                            /*intent.putExtra("com.broadcast.test", (Serializable) msgBeans);*/
                            Intent intent = new Intent("com.broadcast.test");
                            intent.putExtra("com.broadcast.test.string", arg.toString());
                            sendBroadcast(intent);
                            mLocalBroadcastManager.sendBroadcast(intent);
                        }).start();
                    } catch (Exception e) {
                        QLog.i(e.toString());
                    }
                }
            }
        }
    };

    private Emitter.Listener onDelMsg = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            if (args.length == 2) {
                JSONObject member = new JSONObject();
                try {
                    member.put("max_id", args[0]);
                    member.put("f_id", args[1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("isConnected", "JSON连接:" + e.toString());
                }
                Log.i("isConnected", "消息已读");
                mSocket.get().emit("del_msg", member);
            }
        }
    };

    class InitSocketThread extends Thread {
        @Override
        public void run() {
            super.run();
            initSocket();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disconnect();
    }

    private void disconnect() {
        mSocket.get().disconnect();
        mSocket.get().off("get_msg");
        mSocket.get().off("");
    }
}