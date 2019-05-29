package com.mingpinmall.me.ui.acitivity.property.storepacket;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

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
import com.mingpinmall.me.ui.constants.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：已领取的店铺红包列表
 *
 * @author 小斌
 * @date 2019/4/20
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
        View emptyView = View.inflate(activity, R.layout.layout_state_view, null);
        ((AppCompatImageView) emptyView.findViewById(R.id.iv_image)).setImageResource(R.drawable.ic_mcc_09_w);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_title)).setText(R.string.text_title_packet_empty);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_sub_title)).setText(R.string.text_sub_title_packet_empty);
        emptyView.findViewById(R.id.btn_action).setVisibility(View.GONE);
        listAdapter.setEmptyView(emptyView);
        binding.recyclerView.setAdapter(listAdapter);

        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            isLoadmore = false;
            lazyLoad();
        });
        listAdapter.setOnLoadMoreListener(() -> {
            isLoadmore = true;
            mViewModel.getPacketList(pageIndex + 1);
        }, binding.recyclerView);

        listAdapter.setOnItemClickListener((adapter, view, position) -> {
            //列表点击事件
            ActivityToActivity.toActivity(ARouterConfig.home.SHOPSTREETACTIVITY);
        });
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver("REFRESH_COUPON", String.class).observeForever(s -> lazyLoad());
        registerObserver(Constants.PACKET_LIST, Object.class).observeForever(result -> {
            if (result instanceof String) {
                ToastUtils.showShort(result.toString());
                if (isLoadmore) {
                    listAdapter.loadMoreFail();
                } else {
                    binding.refreshLayout.finishRefresh(false);
                }
            } else {
                formatData((BaseResponse<PacketListBean>) result);
            }
        });
    }

    private void formatData(BaseResponse<PacketListBean> data) {
        if (isLoadmore) {
            listAdapter.loadMoreComplete();
            pageIndex++;
            int typeCount = 0;
            for (int i = 0; i < listAdapter.getItemCount(); i++) {
                if (!TextUtils.equals("1", listAdapter.getItem(i).getRpacket_state())) {
                    typeCount = i;
                    break;
                }
            }
            for (PacketListBean.RedpacketListBean item : data.getData().getRedpacket_list()) {
                if (TextUtils.equals("1", item.getRpacket_state())) {
                    listAdapter.addData(typeCount, item);
                    typeCount++;
                } else {
                    listAdapter.addData(item);
                }
            }
        } else {
            binding.refreshLayout.finishRefresh();
            pageIndex = 1;
            if (data.getData().getRedpacket_list() == null || data.getData().getRedpacket_list().isEmpty()) {
                listAdapter.setNewData(new ArrayList<>());
            } else {
                List<PacketListBean.RedpacketListBean> dataList = new ArrayList<>();
                for (PacketListBean.RedpacketListBean item : data.getData().getRedpacket_list()) {
                    if (TextUtils.equals("1", item.getRpacket_state())) {
                        dataList.add(item);
                    }
                }
                PacketListBean.RedpacketListBean spaceBean = new PacketListBean.RedpacketListBean();
                spaceBean.setRpacket_state("0");
                spaceBean.setRpacket_state_text("已过期的红包");
                dataList.add(spaceBean);
                for (PacketListBean.RedpacketListBean item : data.getData().getRedpacket_list()) {
                    if (!TextUtils.equals("1", item.getRpacket_state())) {
                        dataList.add(item);
                    }
                }
                listAdapter.setNewData(dataList);
            }
        }
        if (!data.isHasmore()) {
            listAdapter.loadMoreEnd();
        }
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
