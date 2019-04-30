package com.mingpinmall.classz.ui.activity.chat;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.databinding.ActivityChatBinding;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.MsgInfo;
import com.mingpinmall.classz.ui.vm.bean.MsgListInfo;
import com.socks.library.KLog;
import com.trecyclerview.adapter.ItemData;

/**
 * 商品分类list
 */
@Route(path = ARouterConfig.classify.CHATACTIVITY)
public class ChatActivity extends AbsLifecycleActivity<ActivityChatBinding, ClassifyViewModel> {

    @Autowired
    String goodsId;

    @Autowired
    String tId;

    String tName, msg;

    ItemData itemData = new ItemData();

    String meIcon, otherIcon;

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

        setTitlePadding(binding.rlTitleContent);
        setDarkMode(true);
        binding.ivBack.setOnClickListener(this);

        binding.setAdapter(AdapterPool.newInstance()
                .getChatAdapter(this).build());
    }

    @Override
    protected void initData() {
        super.initData();
        mViewModel.getNodeInfo(goodsId, tId, Constants.CHAT[2]);
    }

    public void sendMsgClick(View view) {
        msg = binding.etMsg.getText().toString();
        if (TextUtils.isEmpty(msg)) {
            ToastUtils.showLong("请输入内容");
        } else {
            mViewModel.sendMsg(goodsId, tId, tName, msg, Constants.CHAT[0]);
        }
    }

    public void getChatLog(View view) {
        mViewModel.getChatLog(tId, "30", Constants.CHAT[3]);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.CHAT[2] + "Success", MsgListInfo.class)
                .observe(this, new Observer<MsgListInfo>() {
                    @Override
                    public void onChanged(@Nullable MsgListInfo response) {
                        MsgListInfo.MemberInfoBean memberInfo = response.getMember_info();
                        tName = memberInfo.getMember_name();
                        tId = memberInfo.getMember_id();
                        MsgListInfo.UserInfoBean userInfo = response.getUser_info();
                        meIcon = userInfo.getMember_avatar();
                        otherIcon = userInfo.getStore_avatar();
                        binding.tvTitle.setText(memberInfo.getStore_name());
                        itemData.add(response.getChat_goods());
                        binding.setList(itemData);

                    }
                });
        /*历史纪录*/
        registerObserver(Constants.CHAT[3] + "Success", MsgListInfo.class)
                .observe(this, new Observer<MsgListInfo>() {
                    @Override
                    public void onChanged(@Nullable MsgListInfo response) {
                        MsgListInfo.MemberInfoBean memberInfo = response.getMember_info();
                        tName = memberInfo.getMember_name();
                        tId = memberInfo.getMember_id();
                        binding.tvTitle.setText(memberInfo.getStore_name());

                    }
                });
        /*发送聊天信息*/
        registerObserver(Constants.CHAT[0] + "Success", MsgInfo.class)
                .observe(this, new Observer<MsgInfo>() {
                    @Override
                    public void onChanged(@Nullable MsgInfo response) {
                        KLog.i(response.toString());
                        binding.etMsg.setText("");
                        if (Long.parseLong(response.getMsg().getF_id()) != (response.getMsg().getT_id())) {/*是自己*/
//                            meIcon;
                        } else {
//                            otherIcon;
                        }
                        binding.setList(itemData);
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


    @Override
    protected Object getStateEventKey() {
        return Constants.CHAT[1];
    }


}