package com.mingpinmall.classz.ui.activity.chat;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.bean.BaseSelectPhotos;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ResourcesUtils;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.databinding.ActivityChatBinding;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.service.IBackService;
import com.mingpinmall.classz.ui.service.SocketIoBroadcast;
import com.mingpinmall.classz.ui.service.SocketIoService;
import com.mingpinmall.classz.ui.vm.bean.ChatEmojiInfo;
import com.mingpinmall.classz.ui.vm.bean.ChatMessageInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.mingpinmall.classz.ui.vm.bean.MsgInfo;
import com.mingpinmall.classz.ui.vm.bean.MsgListInfo;
import com.mingpinmall.classz.utils.FaceConversionUtil;
import com.socks.library.KLog;
import com.trecyclerview.adapter.ItemData;
import com.trecyclerview.listener.OnItemClickListener;
import com.xuexiang.xui.utils.ResUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 商品分类list
 */
@Route(path = ARouterConfig.classify.CHATACTIVITY, extras = ARouterConfig.LOGIN_NEEDED)
public class ChatActivity extends AbsLifecycleActivity<ActivityChatBinding, ClassifyViewModel> implements OnItemClickListener {

    @Autowired
    String goodsId;/*商品Id*/

    @Autowired
    String tId;/*店铺Id*/

    private final ItemData itemData = new ItemData();

//    private String meIcon, mOtherIcon, tName, msg, mFid;

    private RecyclerView mRecyclerView;

    private String mServerUrl;//, msg;

    private MsgListInfo.UserInfoBean mUserInfo;

    private MsgListInfo.MemberInfoBean memberInfo;

    private IBackService mIBackService;

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

//        binding.setSmileImg(String.format("%s/wap/images/smile_b.png", BuildConfig.APP_URL));
//        binding.setAddImg(String.format("%s/wap/images/picture_add.png", BuildConfig.APP_URL));
//        binding.setMsgLogB(String.format("%s/wap/images/msg_log_b.png", BuildConfig.APP_URL));

        binding.etMsg.setOnFocusChangeListener((v, hasFocus) -> hideTrvBottom(true));
    }

    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIBackService = IBackService.Stub.asInterface(service);
            try {
                mIBackService.setUrl(mServerUrl);
                mIBackService.setMemberInfo(memberInfo.getMember_id(),
                        memberInfo.getMember_name(), memberInfo.getMember_avatar(),
                        memberInfo.getStore_id(), memberInfo.getStore_name(),
                        memberInfo.getStore_avatar());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIBackService = null;
        }
    };

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
                .observe(this, response -> {
                    mUserInfo = response.getUser_info();
                    if (null != mUserInfo) {
                        tId = mUserInfo.getMember_id();
//                            mFid = response.getMember_info().getMember_id();
//                            tName = mUserInfo.getMember_name();
//                            meIcon = mUserInfo.getMember_avatar();
//                            mOtherIcon = mUserInfo.getStore_avatar();
                        if (!TextUtils.isEmpty(mUserInfo.getStore_name())) {
                            binding.tvTitle.setText(mUserInfo.getStore_name());
                        } else if (!TextUtils.isEmpty(mUserInfo.getMember_name())) {
                            binding.tvTitle.setText(mUserInfo.getMember_name());
                        } else {
                            binding.tvTitle.setText("消息详情");
                        }
                        GoodsInfo goodsInfo = response.getChat_goods();
                        if (null != goodsInfo) {
                            goodsInfo.setGoods_image_url(goodsInfo.getPic());
                            itemData.add(goodsInfo);
                        }
                        binding.setList(itemData);
                        memberInfo = response.getMember_info();
                        mServerUrl = response.getNode_site_url();
                        Intent intent = new Intent(getApplicationContext(), SocketIoService.class);
                        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                        bindService(intent, mConnection, BIND_AUTO_CREATE);
                        startService(intent);
                        registerBroadcastReceiver();
                    } else {
                        showErrorState();
                        KLog.i("服务员异常");
                    }
                });
        /*历史纪录*/
        registerObserver(Constants.CHAT[3] + "Success", MsgListInfo.class)
                .observe(this, response -> {
                    List<MsgInfo.MsgBean> list = response.getList();
                    if (null != list && list.size() > 0) {
                        itemData.clear();
                        binding.getAdapter().notifyDataSetChanged();
                        Collections.reverse(list);
                        KLog.i(list);
//                            for (MsgInfo.MsgBean msgBean : list) {
//                            ChatMessageInfo info;
////                                info = resultMsg(new ChatMessageInfo(), msgBean);
////                                info.msg = msgBean.getT_msg();
////                                itemData.add(info);
//
//                                update(msgBean, false);
//                            }
//                            binding.getAdapter().notifyDataSetChanged();
//                            mRecyclerView.scrollToPosition(itemData.size() - 1);
                        updateList(list);
                        binding.setList(itemData);
                    } else {
                        KLog.i("服务器异常");
                    }
                });
        /*发送聊天信息*/
        registerObserver(Constants.CHAT[0] + "Success", MsgInfo.class)
                .observe(this, response -> {
                    binding.etMsg.setText("");
                    KLog.i("发送聊天信息");
                    update(response.getMsg(), true);
                    /*发送消息*/
                    try {
                        Gson gson = new Gson();
                        mIBackService.sendMessage(gson.toJson(response.getMsg()));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
//                        MsgInfo.MsgBean msg = response.getMsg();
//                        if (null != msg) {
////                            ChatMessageInfo info = resultMsg(new ChatMessageInfo(), msg);
////                            info.msg = msg.getT_msg();
////                            itemData.add(info);
////                            binding.getAdapter().notifyItemInserted(itemData.size() - 1);
////                            mRecyclerView.scrollToPosition(itemData.size() - 1);
//                            update(msg, true);
//                        } else {
//                            KLog.i("1111");
//                        }
                });

        registerObserver(Constants.CHAT[0] + "Error", String.class)
                .observe(this, response -> ToastUtils.showLong(response));

        registerObserver("UPDATE_CHAT_LIST", "UPDATE_CHAT_LIST", Object.class)
                .observe(this, object -> {
                    List<MsgInfo.MsgBean> msgBeans = (List<MsgInfo.MsgBean>) object;
                    updateList(msgBeans);
                    binding.setList(itemData);
                    KLog.i("更新数据");
                    try {
                        if (null != msgBeans) {
                            if (msgBeans.size() > 1) {
                                mIBackService.sendMessage(String.format("%s|%s", String.valueOf(msgBeans.size() - 1), msgBeans.get(0).getF_id()));
                            } else if (msgBeans.size() == 1) {
                                mIBackService.sendMessage(String.format("%s|%s", String.valueOf(msgBeans.size() - 1), msgBeans.get(0).getF_id()));
                            }
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
    }

    private SocketIoBroadcast mReceiver;

    private void registerBroadcastReceiver() {
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction("com.broadcast.test");
        mReceiver = new SocketIoBroadcast();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mReceiver, mFilter);
    }

    private void updateList(List<MsgInfo.MsgBean> msgBeans) {
        if (null != msgBeans && msgBeans.size() > 0) {
            for (MsgInfo.MsgBean msgBean : msgBeans) {
                update(msgBean, false);
            }
            notifyItemInserted(true);
        }
    }

    private void update(MsgInfo.MsgBean msgBean, boolean isUpdate) {
        if (null != msgBean) {
            ChatMessageInfo info = resultMsg(new ChatMessageInfo(), msgBean);
            info.msg = msgBean.getT_msg();
            itemData.add(info);
            notifyItemInserted(isUpdate);
        }
    }

    private void notifyItemInserted(boolean isUpdate) {
        if (isUpdate) {
            binding.setList(itemData);
            binding.getAdapter().notifyItemInserted(itemData.size() - 1);
            mRecyclerView.scrollToPosition(itemData.size() - 1);
        }
    }

    private ChatMessageInfo resultMsg(ChatMessageInfo info, MsgInfo.MsgBean msgBean) {
        if (msgBean.isMe(tId)) {/*是自己*/
            info.setIcon(mUserInfo.getMember_avatar());
            info.setMe(true);
        } else {
            info.setIcon(mUserInfo.getStore_avatar());
        }
        info.msg = msgBean.getT_msg();
        if (!TextUtils.isEmpty(msgBean.getMsg_type())) {
            info.setImage(msgBean.getMsg_type().equals("image"));
        }
        return info;
    }

    /*发送信息*/
    public void sendMsgClick(View view) {
        String msg = binding.etMsg.getText().toString();
        if (TextUtils.isEmpty(msg)) {
            ToastUtils.showLong("请输入内容");
        } else {
            mViewModel.sendMsg(goodsId, memberInfo.getMember_id(), tId,
                    mUserInfo.getMember_name(), msg, Constants.CHAT[0]);
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
        KLog.i("选择图片");
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                .maxSelectNum(1)// 最大图片选择数量 int
//                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(3)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.JPEG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .compress(true)// 是否压缩 true or false
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(true)// 是否显示gif图片 true or false
//                .selectionMedia(selectList)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    BaseSelectPhotos blockPhoto;
                    for (LocalMedia lm : selectList) {
                        blockPhoto = new BaseSelectPhotos();
                        if (lm.isCompressed()) {
                            blockPhoto.setOriginalurl(lm.getCompressPath());
                        } else if (lm.isCut()) {
                            blockPhoto.setOriginalurl(lm.getCutPath());
                        } else {
                            blockPhoto.setOriginalurl(lm.getPath());
                        }
                        KLog.i(blockPhoto.getOriginalurl());
                        File file = new File(blockPhoto.getOriginalurl());
                        if (file.exists()) {
                            mViewModel.picUpload(goodsId, memberInfo.getMember_id(), tId,
                                    mUserInfo.getMember_name(), blockPhoto.getOriginalurl(), Constants.CHAT[0]);
                        } else {
                            ToastUtils.showLong("图片不存在，请稍后再试");
                        }

                    }
                    break;
            }

        }
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
        try {
            if (null != mConnection) {
                unbindService(mConnection);
            }
            if (null != mReceiver) {
                LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(mReceiver);
            }
        } catch (Exception e) {
            KLog.i(e.toString());
        }
    }
}


//    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, final Intent intent) {
//            String action = intent.getAction();
//            KLog.i("=");
//            if (action.equals("com.broadcast.test")) {
//                final String str = intent.getStringExtra("com.broadcast.test.string");
//                if (!TextUtils.isEmpty(str) && !"{}".equals(str)) {
//                    try {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Gson gson = new Gson();
//                                List<MsgInfo.MsgBean> msgBeans = gson.fromJson(str, new TypeToken<List<MsgInfo.MsgBean>>() {
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
////                                        mSocket.emit("del_msg", String.valueOf(msgBeans.size() - 1), msgBeans.get(0).getF_id());
//                                    } else if (msgBeans.size() == 1) {
////                                        mSocket.emit("del_msg", "1", msgBeans.get(0).getF_id());
//                                    }
//                                }
//                            }
//                        });
//                    } catch (Exception e) {
//                        KLog.i(e.toString());
//                    }
//                }
//            }
//        }
//    };

//    private synchronized void connectSocket() {
//        if (null == mSocket) {
//            IO.Options opts = new IO.Options();
//            opts.path = "/socket.io";
//            opts.reconnection = false;
//            opts.sslContext = SSLSocket.genSSLSocketFactory();
//            try {
//                mSocket = IO.socket(mServerUrl, opts);
//            } catch (URISyntaxException e) {
//                KLog.i(e.toString());
//            }
//            mSocket.on(Socket.EVENT_CONNECT, onConnect)
//                    .on(Socket.EVENT_DISCONNECT, onDisconnect)
//                    .on("del_msg", onDelMsg())
//                    .on("get_msg", onGetMsg);
//            mSocket.connect();
//        }
//    }
/*设置消息已读*/
//    private final Emitter.Listener onDelMsg() {
//        return new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                if (args.length == 2) {
//                    JSONObject member = new JSONObject();
//                    try {
//                        member.put("max_id", args[0]);
//                        member.put("f_id", args[1]);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        Log.i("isConnected", "JSON连接:" + e.toString());
//                    }
//                    Log.i("isConnected", "消息已读");
//                    mSocket.emit("del_msg", member);
//                }
//            }
//        };
//    }

//    private final Emitter.Listener onGetMsg = new Emitter.Listener() {
//        @Override
//        public void call(Object... args) {
//            for (final Object arg : args) {
//                Log.i("接受消息", arg.toString());
//                String str = arg.toString();
//                if (!TextUtils.isEmpty(str) && !"{}".equals(str)) {
//                    try {
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
//                    } catch (Exception e) {
//                        KLog.i(e.toString());
//                    }
//                }
//            }
//        }
//    };

//    private final Emitter.Listener onDisconnect = new Emitter.Listener() {
//        @Override
//        public void call(Object... args) {
//            KLog.i("断开连接");
//
//            /*更新后台*/
//            /*mSocket.connect();*/
//        }
//    };

//    private final Emitter.Listener onConnect =
//            new Emitter.Listener() {
//                @Override
//                public void call(Object... args) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            JSONObject member = new JSONObject();
//                            try {
//                                member.put("u_id", memberInfo.getMember_id());
//                                member.put("u_name", memberInfo.getMember_name());
//                                member.put("avatar", memberInfo.getMember_avatar());
//                                member.put("s_id", memberInfo.getStore_id());
//                                member.put("s_name", memberInfo.getStore_name());
//                                member.put("s_avatar", memberInfo.getStore_avatar());
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                                Log.i("isConnected", "JSON连接:" + e.toString());
//                            }
//                            mSocket.emit("update_user", member);
//                        }
//                    });
//                }
//            };