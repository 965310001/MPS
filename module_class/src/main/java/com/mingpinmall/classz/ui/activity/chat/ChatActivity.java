package com.mingpinmall.classz.ui.activity.chat;

import android.arch.lifecycle.Observer;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.BuildConfig;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ResourcesUtils;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.databinding.ActivityChatBinding;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.ChatEmojiInfo;
import com.mingpinmall.classz.ui.vm.bean.ChatMessageInfo;
import com.mingpinmall.classz.ui.vm.bean.MsgInfo;
import com.mingpinmall.classz.ui.vm.bean.MsgListInfo;
import com.mingpinmall.classz.utils.FaceConversionUtil;
import com.socks.library.KLog;
import com.trecyclerview.adapter.ItemData;
import com.trecyclerview.listener.OnItemClickListener;
import com.xuexiang.xui.utils.ResUtils;

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

    private ItemData itemData = new ItemData();

    private String meIcon, mOtherIcon, tName, msg;

    private RecyclerView mRecyclerView;

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
    }


    @Override
    protected void initData() {
        super.initData();
        mViewModel.getNodeInfo(goodsId, tId, Constants.CHAT[2]);

        binding.setEmojiAdapter(AdapterPool.newInstance()
                .getEmojiAdapter(this)
                .setOnItemClickListener(this)
                .build());
        List<ChatEmojiInfo> list = new ArrayList<>();
        String[] stringArray = ResUtils.getStringArray(R.array.semoj);
        ChatEmojiInfo emojiInfo;
        TypedArray intArray = ResourcesUtils.getInstance().obtainTypedArray(R.array.iemoj);
        int length = stringArray.length;
        for (int i = 0; i < length; i++) {
            emojiInfo = new ChatEmojiInfo(intArray.getResourceId(i, 0), stringArray[i]);
            list.add(emojiInfo);
        }
        binding.setEmojiList(list);
        binding.setLayout(new GridLayoutManager(this, 8));
    }

    public void sendMsgClick(View view) {
        msg = binding.etMsg.getText().toString();
        if (TextUtils.isEmpty(msg)) {
            ToastUtils.showLong("请输入内容");
        } else {
            mViewModel.sendMsg(goodsId, tId, tName, msg, Constants.CHAT[0]);
        }
    }


    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.CHAT[2] + "Success", MsgListInfo.class)
                .observe(this, new Observer<MsgListInfo>() {
                    @Override
                    public void onChanged(@Nullable MsgListInfo response) {
                        MsgListInfo.UserInfoBean userInfo = response.getUser_info();
                        tId = userInfo.getMember_id();
                        tName = userInfo.getMember_name();
                        meIcon = userInfo.getMember_avatar();
                        mOtherIcon = userInfo.getStore_avatar();
                        binding.tvTitle.setText(userInfo.getStore_name());
                        itemData.add(response.getChat_goods());
                        binding.setList(itemData);
                    }
                });
        /*历史纪录*/
        registerObserver(Constants.CHAT[3] + "Success", MsgListInfo.class)
                .observe(this, new Observer<MsgListInfo>() {
                    @Override
                    public void onChanged(@Nullable MsgListInfo response) {
                        List<MsgInfo.MsgBean> list = response.getList();
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
                    }
                });
        /*发送聊天信息*/
        registerObserver(Constants.CHAT[0] + "Success", MsgInfo.class)
                .observe(this, new Observer<MsgInfo>() {
                    @Override
                    public void onChanged(@Nullable MsgInfo response) {
                        binding.etMsg.setText("");
                        MsgInfo.MsgBean msg = response.getMsg();
                        ChatMessageInfo info = resultMsg(new ChatMessageInfo(), msg);
                        info.msg = msg.getT_msg();
                        itemData.add(info);
                        binding.getAdapter().notifyItemInserted(itemData.size() - 1);
                        mRecyclerView.scrollToPosition(itemData.size() - 1);
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

    private ChatMessageInfo resultMsg(ChatMessageInfo info, MsgInfo.MsgBean msgBean) {
        if (msgBean.isMe(tId)) {/*是自己*/
            info.setIcon(meIcon);
            info.setMe(true);
        } else {
            info.setIcon(mOtherIcon);
        }
        return info;
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
}