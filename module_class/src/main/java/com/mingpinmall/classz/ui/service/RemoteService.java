package com.mingpinmall.classz.ui.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class RemoteService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return iBackService;
    }

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

        @Override
        public void doSomething() throws RemoteException {
            Intent localService = new Intent(RemoteService.this,
                    SocketIoService.class);
            startService(localService);
            bindService(new Intent(RemoteService.this,
                            SocketIoService.class), connection,
                    Context.BIND_ABOVE_CLIENT);
        }

    };

    private ServiceConnection connection = new ServiceConnection() {
        /**
         * 在终止后调用,我们在杀死服务的时候就会引起意外终止,就会调用onServiceDisconnected
         * 则我们就得里面启动被杀死的服务,然后进行绑定
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("TAG", "LocalService被杀死了");
            Intent remoteService = new Intent(RemoteService.this,
                    SocketIoService.class);
            startService(remoteService);
            bindService(new Intent(RemoteService.this,
                    SocketIoService.class), connection, Context.BIND_ABOVE_CLIENT);
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("TAG", "LocalService链接成功!");
            try {
                if (iBackService != null)
                    iBackService.doSomething();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        bindService(new Intent(RemoteService.this,
//                        SocketIoService.class), connection,
//                Context.BIND_ABOVE_CLIENT);
        try {
            iBackService.doSomething();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            iBackService.doSomething();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}