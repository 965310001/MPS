package com.mingpinmall.classz.ui.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mingpinmall.classz.ui.vm.bean.ChatMessageInfo;
import com.mingpinmall.classz.ui.vm.bean.MsgInfo;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * SockerIo
 */
public class SocketIoService extends Service {

    private LocalBroadcastManager mLocalBroadcastManager;
    private WeakReference<Socket> mSocket;

    /**
     * aidl通讯回调
     */
    private IBackService.Stub iBackService = new IBackService.Stub() {

        private String mSavatar;
        private String mSname;
        private String mSid;
        private String mAvatar;
        private String mUname;
        private String mUid;
        private String mUrl;

        /**
         * 收到内容发送消息
         * @param message 需要发送到服务器的消息
         * @return
         * @throws RemoteException
         */
        @Override
        public boolean sendMessage(String message) throws RemoteException {
            return sendMsg(message);
        }

        @Override
        public String getUrl() throws RemoteException {
            return mUrl;
        }

        @Override
        public void setUrl(String url) throws RemoteException {
            this.mUrl = url;
        }

        @Override
        public void setMemberInfo(String uId, String uName, String avatar, String sId, String sName, String sAvatar) throws RemoteException {
            this.mUid = uId;
            this.mUname = uName;
            this.mAvatar = avatar;
            this.mSid = sId;
            this.mSname = sName;
            this.mSavatar = sAvatar;
        }

        @Override
        public String getUid() throws RemoteException {
            return mUid;
        }

        @Override
        public String getUname() throws RemoteException {
            return mUname;
        }

        @Override
        public String getAvatar() throws RemoteException {
            return mAvatar;
        }

        @Override
        public String getSid() throws RemoteException {
            return mSid;
        }

        @Override
        public String getSname() throws RemoteException {
            return mSname;
        }

        @Override
        public String getsAvatar() throws RemoteException {
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

    public boolean sendMsg(final String msg) {
        if (null == mSocket || null == mSocket.get()) {
            return false;
        }
//        final Socket soc = mSocket.get();
//        if (!soc.isClosed() && !soc.isOutputShutdown()) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        OutputStream os = soc.getOutputStream();
//                        String message = msg + "\r\n";
//                        os.write(message.getBytes());
//                        os.flush();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//            sendTime = System.currentTimeMillis();//每次发送成数据，就改一下最后成功发送的时间，节省心跳间隔时间
       /* } else {
            return false;
        }
        return true;*/

//        mSocket.get().emit("", msg);
        return true;
    }

    private void initSocket() {//初始化Socket
        if (null == mSocket || null == mSocket.get()) {
            IO.Options opts = new IO.Options();
            opts.path = "/socket.io";
            opts.reconnection = false;
            opts.sslContext = SSLSocket.genSSLSocketFactory();
            try {
                mSocket = new WeakReference<>(IO.socket(iBackService.getUrl(), opts));
            } catch (URISyntaxException e) {
                KLog.i(e.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mSocket.get().on(Socket.EVENT_CONNECT, onConnect)
//                    .on(Socket.EVENT_DISCONNECT, onDisconnect)
//                    .on("del_msg", onDelMsg())
                    .on("get_msg", onGetMsg);
            mSocket.get().connect();
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
                        member.put("s_id", iBackService.getSid());
                        member.put("s_name", iBackService.getSname());
                        member.put("s_avatar", iBackService.getsAvatar());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.i("isConnected", "JSON连接:" + e.toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    mSocket.get().emit("update_user", member);
                }
            };


    private final Emitter.Listener onGetMsg = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            for (final Object arg : args) {
                String str = arg.toString();
                if (!TextUtils.isEmpty(str) && !"{}".equals(str)) {
                    try {
                        KLog.i(arg.toString());
                        Gson gson = new Gson();
                        List<MsgInfo.MsgBean> msgBeans = gson.fromJson(arg.toString(), new TypeToken<List<MsgInfo.MsgBean>>() {
                        }.getType());
                        Intent intent = new Intent("com.broadcast.test");
                        intent.putExtra("msg", (Serializable) msgBeans);
                        mLocalBroadcastManager.sendBroadcast(intent);
                        /*LiveBus.getDefault().postEvent("", msgBeans);*/

//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Gson gson = new Gson();
//                                List<MsgInfo.MsgBean> msgBeans = gson.fromJson(arg.toString(), new TypeToken<List<MsgInfo.MsgBean>>() {
//                                }.getType());
//                                ChatMessageInfo info;
//                                for (MsgInfo.MsgBean msgBean : msgBeans) {
//                                    info = resultMsg(new ChatMessageInfo(), msgBean);
//                                    info.msg = msgBean.getT_msg();
//                                    itemData.add(info);
//                                }
//                                binding.getAdapter().notifyDataSetChanged();
//                                mRecyclerView.scrollToPosition(itemData.size() - 1);
//                                KLog.i(msgBeans.size() + "条数" + msgBeans.get(0).getF_id());
//
//                                if (null != msgBeans) {
//                                    if (msgBeans.size() > 1) {
//                                        mSocket.emit("del_msg", String.valueOf(msgBeans.size() - 1), msgBeans.get(0).getF_id());
//                                    } else if (msgBeans.size() == 1) {
//                                        mSocket.emit("del_msg", "1", msgBeans.get(0).getF_id());
//                                    }
//                                }
//                            }
//                        });
                    } catch (Exception e) {
                        KLog.i(e.toString());
                    }
                }
            }
        }
    };


    /**
     * 心跳机制判断出socket已经断开后，就销毁连接方便重新创建连接
     *
     * @param mSocket
     */
    private void releaseLastSocket(WeakReference<Socket> mSocket) {
//        try {
//            if (null != mSocket) {
//                Socket sk = mSocket.get();
//                if (!sk.isClosed()) {
//                    sk.close();
//                }
//                sk = null;
//                mSocket = null;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    class InitSocketThread extends Thread {
        @Override
        public void run() {
            super.run();
            initSocket();
        }
    }

    // Thread to read content from Socket
//    class ReadThread extends Thread {
//        private WeakReference<Socket> mWeakSocket;
//        private boolean isStart = true;
//
//        public ReadThread(Socket socket) {
//            mWeakSocket = new WeakReference<Socket>(socket);
//        }
//
//        public void release() {
//            isStart = false;
//            releaseLastSocket(mWeakSocket);
//        }
//
//        @Override
//        public void run() {
//            super.run();
//            Socket socket = mWeakSocket.get();
//            if (null != socket) {
//                try {
//                    InputStream is = socket.getInputStream();
//                    byte[] buffer = new byte[1024 * 4];
//                    int length = 0;
//                    while (!socket.isClosed() && !socket.isInputShutdown()
//                            && isStart && ((length = is.read(buffer)) != -1)) {
//                        if (length > 0) {
//                            String message = new String(Arrays.copyOf(buffer,
//                                    length)).trim();
//                            Log.e(TAG, message);
//                            //收到服务器过来的消息，就通过Broadcast发送出去
//                            if (message.equals("ok")) {//处理心跳回复
//                                Intent intent = new Intent(HEART_BEAT_ACTION);
//                                mLocalBroadcastManager.sendBroadcast(intent);
//                            } else {
//                                //其他消息回复
//                                Intent intent = new Intent(MESSAGE_ACTION);
//                                intent.putExtra("message", message);
//                                mLocalBroadcastManager.sendBroadcast(intent);
//                            }
//                        }
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mHandler.removeCallbacks(heartBeatRunnable);
//        mReadThread.release();
        releaseLastSocket(mSocket);
    }
}