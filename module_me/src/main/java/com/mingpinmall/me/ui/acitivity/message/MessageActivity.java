package com.mingpinmall.me.ui.acitivity.message;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.dialog.TextDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityMessageBinding;
import com.mingpinmall.me.ui.adapter.MessageListAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.MessageListBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.w3c.dom.Text;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/3/27
 **/
@Route(path = ARouterConfig.Me.MESSAGEACTIVITY)
public class MessageActivity extends AbsLifecycleActivity<ActivityMessageBinding, MeViewModel> {

    private MessageListAdapter listAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTitle(R.string.title_messageActivity);
        listAdapter = new MessageListAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        View emptyView = View.inflate(activity, R.layout.layout_state_view, null);
        ((AppCompatImageView) emptyView.findViewById(R.id.iv_image)).setImageResource(R.drawable.ic_message_white);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_title)).setText(R.string.text_title_msg_empty);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_sub_title)).setText(R.string.text_sub_title_msg_empty);
        emptyView.findViewById(R.id.btn_action).setVisibility(View.GONE);
        listAdapter.setEmptyView(emptyView);
        binding.recyclerView.setAdapter(listAdapter);

        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
            }
        });

        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //item

            }
        });
        listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //子控件
                final MessageListBean.ListBean data = listAdapter.getItem(position);
                if (view.getId() == R.id.iv_delete) {
                    //删除这条消息记录
                    TextDialog.showBaseDialog(activity, "删除消息", "确定删除这条消息吗？", new TextDialog.SingleButtonCallback() {
                        @Override
                        public void onClick() {
                            mViewModel.delMsg(data.getU_id());
                        }
                    }).show();
                }
            }
        });
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver("MESSAGE_LIST", Object.class).observeForever(new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object result) {
                if (result instanceof MessageListBean) {
                    MessageListBean bean = (MessageListBean) result;
                    binding.refreshLayout.finishRefresh();
                    listAdapter.setNewData(bean.getList());
                } else {
                    binding.refreshLayout.finishRefresh(false);
                    ToastUtils.showShort(result.toString());
                }

            }
        });
        registerObserver("DELETE_MESSAGE", String.class).observeForever(new Observer<String>() {
            @Override
            public void onChanged(@Nullable String msg) {
                if (msg.equals("success")) {
                    initData();
                } else {
                    ToastUtils.showShort(msg);
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mViewModel.getMsgList();
    }

    @Override
    protected Object getStateEventKey() {
        return "MessageActivity";
    }
}
