package com.mingpinmall.classz.ui.activity.chat;

import android.arch.lifecycle.Observer;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.goldze.common.dmvvm.BuildConfig;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ResourcesUtils;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.databinding.ActivityChatBinding;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.service.SSLSocket;
import com.mingpinmall.classz.ui.vm.bean.ChatEmojiInfo;
import com.mingpinmall.classz.ui.vm.bean.ChatMessageInfo;
import com.mingpinmall.classz.ui.vm.bean.MsgInfo;
import com.mingpinmall.classz.ui.vm.bean.MsgListInfo;
import com.mingpinmall.classz.utils.FaceConversionUtil;
import com.socks.library.KLog;
import com.trecyclerview.adapter.ItemData;
import com.trecyclerview.listener.OnItemClickListener;
import com.xuexiang.xui.utils.ResUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 商品分类list
 */
@Route(path = ARouterConfig.classify.CHATACTIVITY)
public class ChatActivity extends AbsLifecycleActivity<ActivityChatBinding, ClassifyViewModel> implements OnItemClickListener {

    @Autowired
    String goodsId;

    @Autowired
    String tId;

    private final ItemData itemData = new ItemData();

    private String meIcon, mOtherIcon, tName, msg, mFid;

    private RecyclerView mRecyclerView;

    private String mServerUrl;

    private MsgListInfo.MemberInfoBean memberInfo;

    private static volatile Socket mSocket;

    @Override
    protected boolean isActionBar() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);

        mRecyclerView = binding.getRoot().findViewById(R.id.recycler_view);

        setTitlePadding(binding.rlTitleContent);
        setDarkMode(true);
        binding.ivBack.setOnClickListener(this);

        binding.setAdapter(AdapterPool.newInstance()
                .getChatAdapter(this)
                .build());

        binding.setSmileImg(String.format("%s/wap/images/smile_b.png", BuildConfig.APP_URL));
        binding.setAddImg(String.format("%s/wap/images/picture_add.png", BuildConfig.APP_URL));
        binding.setMsgLogB(String.format("%s/wap/images/msg_log_b.png", BuildConfig.APP_URL));

        binding.etMsg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideTrvBottom(true);
            }
        });

        /*Intent intent = new Intent(getApplicationContext(), SocketService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
        startService(intent);*/
    }

    /*IBackService mIBackService;
    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //连接后拿到 Binder，转换成 AIDL，在不同进程会返回个代理
            mIBackService = IBackService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIBackService = null;
        }
    };*/

    @Override
    protected void initData() {
        super.initData();
        mViewModel.getNodeInfo(goodsId, tId, Constants.CHAT[2]);

        binding.setEmojiAdapter(AdapterPool.newInstance()
                .getEmojiAdapter(this)
                .setOnItemClickListener(this)
                .build());

        initChatEmoji();
    }

    private void initChatEmoji() {
        List<ChatEmojiInfo> list = new ArrayList<>();
        String[] stringArray = ResUtils.getStringArray(R.array.semoj);
        ChatEmojiInfo chatEmojiInfo;
        TypedArray intArray = ResourcesUtils.getInstance().obtainTypedArray(R.array.iemoj);
        int length = stringArray.length;
        for (int i = 0; i < length; i++) {
            chatEmojiInfo = new ChatEmojiInfo(intArray.getResourceId(i, 0), stringArray[i]);
            list.add(chatEmojiInfo);
        }
        binding.setEmojiList(list);
        binding.setLayout(new GridLayoutManager(this, 8));
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.CHAT[2] + "Success", MsgListInfo.class)
                .observe(this, new Observer<MsgListInfo>() {
                    @Override
                    public void onChanged(@Nullable MsgListInfo response) {
                        MsgListInfo.UserInfoBean mUserInfo = response.getUser_info();
                        mFid = response.getMember_info().getMember_id();
                        if (null != mUserInfo) {
                            tId = mUserInfo.getMember_id();
                            tName = mUserInfo.getMember_name();
                            meIcon = mUserInfo.getMember_avatar();
                            mOtherIcon = mUserInfo.getStore_avatar();
                            binding.tvTitle.setText(mUserInfo.getStore_name());
                            itemData.add(response.getChat_goods());
                            binding.setList(itemData);
                            memberInfo = response.getMember_info();
                            mServerUrl = response.getNode_site_url();
                            if (null == mSocket) {
                                KLog.i("mSocket");
                                connectSocket();
                            } else {
                                KLog.i("mSocket 不为null");
                            }
                            connectSocket();
                        } else {
                            showErrorState();
                            KLog.i("服务员异常");
                        }
                    }
                });
        /*历史纪录*/
        registerObserver(Constants.CHAT[3] + "Success", MsgListInfo.class)
                .observe(this, new Observer<MsgListInfo>() {
                    @Override
                    public void onChanged(@Nullable MsgListInfo response) {
                        List<MsgInfo.MsgBean> list = response.getList();
                        if (null != list && list.size() > 0) {
                            ChatMessageInfo info;
                            itemData.clear();
                            Collections.reverse(list);
                            for (MsgInfo.MsgBean msgBean : list) {
                                info = resultMsg(new ChatMessageInfo(), msgBean);
                                info.msg = msgBean.getT_msg();
                                itemData.add(info);
                            }
                            binding.getAdapter().notifyDataSetChanged();
                            mRecyclerView.scrollToPosition(itemData.size() - 1);
                        } else {
                            KLog.i("服务器异常");
                        }
                    }
                });
        /*发送聊天信息*/
        registerObserver(Constants.CHAT[0] + "Success", MsgInfo.class)
                .observe(this, new Observer<MsgInfo>() {
                    @Override
                    public void onChanged(@Nullable MsgInfo response) {
                        binding.etMsg.setText("");
                        MsgInfo.MsgBean msg = response.getMsg();
                        if (null != msg) {
                            ChatMessageInfo info = resultMsg(new ChatMessageInfo(), msg);
                            info.msg = msg.getT_msg();
                            itemData.add(info);
                            binding.getAdapter().notifyItemInserted(itemData.size() - 1);
                            mRecyclerView.scrollToPosition(itemData.size() - 1);
                        } else {
                            KLog.i("1111");
                        }
                    }
                });

        registerObserver(Constants.CHAT[0] + "Error", String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String response) {
                        ToastUtils.showLong(response);
                    }
                });
    }

    private synchronized void connectSocket() {
        if (null == mSocket) {
            IO.Options opts = new IO.Options();
            opts.path = "/socket.io";
            opts.reconnection = false;
            opts.sslContext = SSLSocket.genSSLSocketFactory();
            try {
                /*KLog.i(mServerUrl + memberInfo);*/
                mSocket = IO.socket(mServerUrl, opts);
            } catch (URISyntaxException e) {
                KLog.i(e.toString());
            }
            mSocket.on(Socket.EVENT_CONNECT, onConnect)
                    .on(Socket.EVENT_DISCONNECT, onDisconnect)
//                .on("del_msg", onDelMsg())
                    .on("get_msg", onGetMsg);
        }
        mSocket.connect();
    }

    /*设置消息已读*/
    private Emitter.Listener onDelMsg(final String maxId, final String fId) {
        return new Emitter.Listener() {
            @Override
            public void call(Object... args) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
                JSONObject member = new JSONObject();
                try {
                    member.put("max_id", maxId);
                    member.put("f_id", fId);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("isConnected", "JSON连接:" + e.toString());
                }
                Log.i("isConnected", "消息已读");
                mSocket.emit("del_msg", member);
//                    }
//                });
            }
        };
    }


    private Emitter.Listener onGetMsg = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            for (final Object arg : args) {
                Log.i("接受消息", arg.toString());
                String str = arg.toString();
                if (!TextUtils.isEmpty(str) && !"{}".equals(str)) {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                List<MsgInfo.MsgBean> msgBeans = gson.fromJson(arg.toString(), new TypeToken<List<MsgInfo.MsgBean>>() {
                                }.getType());
                                ChatMessageInfo info;
                                for (MsgInfo.MsgBean msgBean : msgBeans) {
                                    info = resultMsg(new ChatMessageInfo(), msgBean);
                                    info.msg = msgBean.getT_msg();
                                    itemData.add(info);
                                }
                                binding.getAdapter().notifyDataSetChanged();
                                mRecyclerView.scrollToPosition(itemData.size() - 1);
                                KLog.i(msgBeans.size() + "条数" + msgBeans.get(0).getF_id());

                                if (null != msgBeans) {
                                    if (msgBeans.size() > 1) {
                                        onDelMsg(String.valueOf(msgBeans.size() - 1), msgBeans.get(0).getF_id());
                                    } else if (msgBeans.size() == 0 || msgBeans.size() == 1) {
                                        onDelMsg("1", msgBeans.get(0).getF_id());
                                    }
                                }
                            }
                        });
                    } catch (Exception e) {
                        KLog.i(e.toString());
                    }
                }
            }
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            KLog.i("断开连接");

            /*更新后台*/
            /*mSocket.connect();*/
        }
    };

    private Emitter.Listener onConnect =
            new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject member = new JSONObject();
                            try {
                                member.put("u_id", memberInfo.getMember_id());
                                member.put("u_name", memberInfo.getMember_name());
                                member.put("avatar", memberInfo.getMember_avatar());
                                member.put("s_id", memberInfo.getStore_id());
                                member.put("s_name", memberInfo.getStore_name());
                                member.put("s_avatar", memberInfo.getStore_avatar());
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.i("isConnected", "JSON连接:" + e.toString());
                            }
                            mSocket.emit("update_user", member);
                        }
                    });
                }
            };

    private ChatMessageInfo resultMsg(ChatMessageInfo info, MsgInfo.MsgBean msgBean) {
        if (msgBean.isMe(tId)) {/*是自己*/
            info.setIcon(meIcon);
            info.setMe(true);
        } else {
            info.setIcon(mOtherIcon);
        }
        info.msg = msgBean.getT_msg();
        return info;
    }

    /*发送信息*/
    public void sendMsgClick(View view) {
        msg = binding.etMsg.getText().toString();
        if (TextUtils.isEmpty(msg)) {
            ToastUtils.showLong("请输入内容");
        } else {
            mViewModel.sendMsg(goodsId, mFid, tId, tName, msg, Constants.CHAT[0]);
        }
    }

    /*历史纪录*/
    public void getChatLog(View view) {
        mViewModel.getChatLog(tId, "30", Constants.CHAT[3]);
        hideTrvBottom(true);
    }

    /*选择笑脸*/
    public void smileImgClick(View view) {
        hideTrvBottom(false);
    }

    /*选择图片*/
    public void addImgClick(View view) {
        hideTrvBottom(true);
    }

    /**
     * @param isHide true：一直隐藏   false:隐藏或显示
     */
    private void hideTrvBottom(boolean isHide) {
        if (!isHide) isHide = binding.trvBoottom.getVisibility() != View.GONE;
        binding.trvBoottom.setVisibility(isHide ? View.GONE : View.VISIBLE);
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.CHAT[1];
    }

    @Override
    public void onItemClick(View view, int i, Object object) {
        if (object instanceof ChatEmojiInfo) {
            ChatEmojiInfo chatEmojiInfo = (ChatEmojiInfo) object;
            binding.etMsg.getText().append(FaceConversionUtil.addFace(this, chatEmojiInfo.getId(), chatEmojiInfo.getCharacter()));
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!super.dispatchTouchEvent(ev)) {
            hideTrvBottom(true);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSocket = null;
    }
}