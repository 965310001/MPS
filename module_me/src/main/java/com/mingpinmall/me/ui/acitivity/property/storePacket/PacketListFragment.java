package com.mingpinmall.me.ui.acitivity.property.storePacket;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.BaseRecyclerviewBinding;
import com.mingpinmall.me.ui.adapter.PacketListAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.PacketListBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：已领取的店铺红包列表
 * 创建人：小斌
 * 创建时间: 2019/4/20
 **/
public class PacketListFragment extends AbsLifecycleFragment<BaseRecyclerviewBinding, MeViewModel> {

    private int pageIndex = 1;
    private boolean isLoadmore = false;
    private PacketListAdapter listAdapter;

    public static PacketListFragment newFragment() {
        return new PacketListFragment();
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        listAdapter = new PacketListAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerView.setAdapter(listAdapter);

        binding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isLoadmore = true;
                mViewModel.getPacketList(pageIndex + 1);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isLoadmore = false;
                lazyLoad();
            }
        });

        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //列表点击事件
                PacketListBean.RedpacketListBean data = listAdapter.getItem(position);
                ActivityToActivity.toActivity(ARouterConfig.home.SHOPSTREETACTIVITY);
            }
        });
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver("REFRESH_COUPON", String.class).observeForever(new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                lazyLoad();
            }
        });
        registerObserver("PACKET_LIST", "success").observeForever(new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object result) {
                BaseResponse<PacketListBean> data = (BaseResponse<PacketListBean>) result;
                if (isLoadmore) {
                    binding.refreshLayout.finishLoadMore();
                    pageIndex++;
                    int typeCount = 0;
                    for (int i = 0; i < listAdapter.getItemCount(); i++) {
                        if (!listAdapter.getItem(i).getRpacket_state().equals("1")) {
                            typeCount = i - 1;
                            break;
                        }
                    }
                    for (PacketListBean.RedpacketListBean item : data.getData().getRedpacket_list()) {
                        if (item.getRpacket_state().equals("1")) {
                            listAdapter.addData(typeCount, item);
                            typeCount++;
                        } else {
                            listAdapter.addData(item);
                        }
                    }
                } else {
                    binding.refreshLayout.finishRefresh();
                    pageIndex = 1;
                    List<PacketListBean.RedpacketListBean> dataList = new ArrayList<>();
                    for (PacketListBean.RedpacketListBean item : data.getData().getRedpacket_list()) {
                        if (item.getRpacket_state().equals("1")) {
                            dataList.add(item);
                        }
                    }
                    PacketListBean.RedpacketListBean spaceBean = new PacketListBean.RedpacketListBean();
                    spaceBean.setRpacket_state("0");
                    spaceBean.setRpacket_state_text("已过期的红包");
                    dataList.add(spaceBean);
                    for (PacketListBean.RedpacketListBean item : data.getData().getRedpacket_list()) {
                        if (!item.getRpacket_state().equals("1")) {
                            dataList.add(item);
                        }
                    }
                    listAdapter.setNewData(dataList);
                }
                if (!data.isHasmore()) {
                    binding.refreshLayout.setNoMoreData(true);
                }
            }
        });
        registerObserver("PACKET_LIST", "err", String.class).observeForever(new Observer<String>() {
            @Override
            public void onChanged(@Nullable String msg) {
                ToastUtils.showShort(msg);
                if (isLoadmore) {
                    binding.refreshLayout.finishLoadMore(false);
                } else {
                    binding.refreshLayout.finishRefresh(false);
                }
            }
        });
    }

    @Override
    protected void lazyLoad() {
        mViewModel.getPacketList(1);
    }

    @Override
    protected Object getStateEventKey() {
        return "CouponListFragment";
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.base_recyclerview;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }
}
