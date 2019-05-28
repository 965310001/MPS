package com.mingpinmall.me.ui.acitivity.collection;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.dialog.TextDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentDefaultRecyclerviewBinding;
import com.mingpinmall.me.ui.adapter.ShopsCollectionAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.ShopsCollectionBean;
import com.mingpinmall.me.ui.constants.Constants;

import static com.goldze.common.dmvvm.constants.ARouterConfig.SUCCESS;

/**
 * 功能描述：店铺收藏页面
 * @author 小斌
 * @date 2019/3/26
 **/
public class ShopsCollectionFragment extends AbsLifecycleFragment<FragmentDefaultRecyclerviewBinding, MeViewModel> {

    private int pageIndex = 1;
    private boolean isLoadmore = false;
    private ShopsCollectionAdapter collectionAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_default_recyclerview;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        collectionAdapter = new ShopsCollectionAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        View emptyView = View.inflate(activity, R.layout.layout_state_view, null);
        ((AppCompatImageView) emptyView.findViewById(R.id.iv_image)).setImageResource(R.drawable.ic_me_store);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_title)).setText(R.string.text_title_collect_empty);
        ((AppCompatTextView) emptyView.findViewById(R.id.tv_sub_title)).setText(R.string.text_sub_title_collect_empty);
        emptyView.findViewById(R.id.btn_action).setOnClickListener(v -> {
            //前往店铺街
            ActivityToActivity.toActivity(ARouterConfig.home.SHOPSTREETACTIVITY);
        });
        collectionAdapter.setEmptyView(emptyView);
        binding.recyclerView.setAdapter(collectionAdapter);

        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            isLoadmore = false;
            mViewModel.getShopsCollectList(1);
        });
        collectionAdapter.setOnLoadMoreListener(()-> {
            isLoadmore = true;
            mViewModel.getShopsCollectList(pageIndex + 1);
        }, binding.recyclerView);
        collectionAdapter.setOnItemClickListener((adapter, view, position) -> {
            //点击事件，跳转到店铺
            ShopsCollectionBean.FavoritesListBean bean = collectionAdapter.getItem(position);
            ActivityToActivity.toActivity(ARouterConfig.classify.STOREACTIVITY, "storeId", bean.getStore_id());
        });
        collectionAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            //子控件点击事件
            final ShopsCollectionBean.FavoritesListBean itemData = collectionAdapter.getItem(position);
            if (view.getId() == R.id.iv_delete) {
                //删除这条收藏
                TextDialog.showBaseDialog(activity, "取消收藏", "确定取消收藏吗？", () -> mViewModel.deleShopsCollect(itemData.getStore_id())).show();
            }
        });
    }

    @Override
    protected void dataObserver() {
        registerObserver(Constants.DEL_SHOP_COLLECT, String.class).observeForever(msg -> {
            if (msg.equals(SUCCESS)) {
                lazyLoad();
            } else {
                ToastUtils.showShort(msg);
            }
        });
        registerObserver(Constants.SHOPS_COLLECT_LIST, Object.class).observeForever(result -> {
            if (result instanceof String) {
                ToastUtils.showShort(result.toString());
                if (isLoadmore) {
                    collectionAdapter.loadMoreFail();
                } else {
                    binding.refreshLayout.finishRefresh(false);
                }
            } else {
                BaseResponse<ShopsCollectionBean> data = (BaseResponse<ShopsCollectionBean>) result;
                if (isLoadmore) {
                    pageIndex++;
                    collectionAdapter.loadMoreComplete();
                    collectionAdapter.addData(data.getData().getFavorites_list());
                } else {
                    pageIndex = 1;
                    binding.refreshLayout.finishRefresh();
                    collectionAdapter.setNewData(data.getData().getFavorites_list());
                }
                if (!data.isHasmore()) {
                    collectionAdapter.loadMoreEnd();
                }
            }
        });
    }

    @Override
    protected void lazyLoad() {
        mViewModel.getShopsCollectList(1);
    }

    @Override
    protected Object getStateEventKey() {
        return "SHOPS_COLLECTION";
    }
}
