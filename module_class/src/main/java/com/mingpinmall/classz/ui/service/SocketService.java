//package com.mingpinmall.classz.ui.service;
//
//import android.app.Service;
//import android.content.Intent;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.RemoteException;
//import android.support.v4.content.LocalBroadcastManager;
//import android.util.Log;
//
//
//import com.goldze.common.dmvvm.utils.log.QLog;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.lang.ref.WeakReference;
//import java.net.Socket;
//import java.net.UnknownHostException;
//import java.util.Arrays;
//
///**
// * 聊天的Service
// */
//public class SocketService extends Service {
//
//    public static final String HOST = "https://www.mingpinmall.cn";
//    public static final int PORT = 8091;
//
//    private static final String TAG = "danxx";
//    /**
//     * 心跳频率
//     */
//    private static final long HEART_BEAT_RATE = 3 * 1000;
//
//    /**
//     * 服务器消息回复广播
//     */
//    public static final String MESSAGE_ACTION = "message_ACTION";
//    /**
//     * 服务器心跳回复广播
//     */
//    public static final String HEART_BEAT_ACTION = "heart_beat_ACTION";
//    /**
//     * 读线程
//     */
//    private ReadThread mReadThread;
//
//    private LocalBroadcastManager mLocalBroadcastManager;
//    /***/
//    private WeakReference<Socket> mSocket;
//
//    // For heart Beat
//    private Handler mHandler = new Handler();
//    /**
//     * 心跳任务，不断重复调用自己
//     */
//    private Runnable heartBeatRunnable = new Runnable() {
//
//        @Override
//        public void run() {
//            if (System.currentTimeMillis() - sendTime >= HEART_BEAT_RATE) {
//                boolean isSuccess = sendMsg("HeartBeat");//就发送一个\r\n过去 如果发送失败，就重新初始化一个socket
//                if (!isSuccess) {
//                    mHandler.removeCallbacks(heartBeatRunnable);
//                    mReadThread.release();
//                    releaseLastSocket(mSocket);
//                    new InitSocketThread().start();
//                }
//            }
//            mHandler.postDelayed(this, HEART_BEAT_RATE);
//        }
//    };
//
//    private long sendTime = 0L;
//    /**
//     * aidl通讯回调
//     */
//    private IBackService.Stub iBackService = new IBackService.Stub() {
//        /**
//         * 收到内容发送消息
//         * @param message 需要发送到服务器的消息
//         * @return
//         * @throws RemoteException
//         */
//        @Override
//        public boolean sendMessage(String message) throws RemoteException {
//            return sendMsg(message);
//        }
//
//        @Override
//        public String getUrl() throws RemoteException {
//            return null;
//        }
//
//        @Override
//        public void setUrl(String url) throws RemoteException {
//
//        }
//
//        @Override
//        public void setMemberInfo(String uId, String uName, String avatar, String sId, String sName, String sAvatar) throws RemoteException {
//
//        }
//
//        @Override
//        public String getUid() throws RemoteException {
//            return null;
//        }
//
//        @Override
//        public String getUname() throws RemoteException {
//            return null;
//        }
//
//        @Override
//        public String getAvatar() throws RemoteException {
//            return null;
//        }
//
//        @Override
//        public String getSid() throws RemoteException {
//            return null;
//        }
//
//        @Override
//        public String getSname() throws RemoteException {
//            return null;
//        }
//
//        @Override
//        public String getsAvatar() throws RemoteException {
//            return null;
//        }
//
//        @Override
//        public void doSomething() throws RemoteException {
//
//        }
//    };
//
//    @Override
//    public IBinder onBind(Intent arg0) {
//        return iBackService;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        new InitSocketThread().start();
//        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
//
//    }
//
//    public boolean sendMsg(final String msg) {
//        if (null == mSocket || null == mSocket.get()) {
//            return false;
//        }
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
//        } else {
//            return false;
//        }
//        return true;
//    }
//
//    private void initSocket() {//初始化Socket
//        try {
//            Socket so = new Socket(HOST, PORT);
//            mSocket = new WeakReference<>(so);
//            mReadThread = new ReadThread(so);
//            mReadThread.start();
//            mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//初始化成功后，就准备发送心跳包
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//            QLog.i(e.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//            QLog.i(e.toString());
//        }
//    }
//
//    /**
//     * 心跳机制判断出socket已经断开后，就销毁连接方便重新创建连接
//     *
//     * @param mSocket
//     */
//    private void releaseLastSocket(WeakReference<Socket> mSocket) {
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
//    }
//
//    class InitSocketThread extends Thread {
//        @Override
//        public void run() {
//            super.run();
//            initSocket();
//        }
//    }
//
//    // Thread to read content from Socket
//    class ReadThread extends Thread {
//        private WeakReference<Socket> mWeakSocket;
//        private boolean isStart = true;
//
//        public ReadThread(Socket socket) {
//            mWeakSocket = new WeakReference<>(socket);
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
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mHandler.removeCallbacks(heartBeatRunnable);
//        mReadThread.release();
//        releaseLastSocket(mSocket);
//    }
//}