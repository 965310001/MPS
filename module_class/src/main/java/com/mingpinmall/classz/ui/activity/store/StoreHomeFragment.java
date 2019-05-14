package com.mingpinmall.classz.ui.activity.store;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.base.BaseListFragment;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.GoodsListInfo;
import com.mingpinmall.classz.ui.vm.bean.StoreInfo;
import com.mingpinmall.classz.ui.vm.bean.TypeInfo;
import com.socks.library.KLog;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.adapter.ItemData;
import com.trecyclerview.listener.OnItemClickListener;

/**
 * 店铺首页
 */
public class StoreHomeFragment extends BaseListFragment<ClassifyViewModel> implements OnItemClickListener {


    public static StoreHomeFragment newInstance() {
        return new StoreHomeFragment();
    }

    @Override
    protected void getRemoteData() {
        super.getRemoteData();

        String storeId = ((StoreActivity) activity).getStoreId();
        mViewModel.getStoreInfo(storeId, "",
                Constants.STORE_GOODS_RANK_KEY[0]);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();

        registerObserver(Constants.STORE_GOODS_RANK_KEY[0], BaseResponse.class)
                .observeForever(response -> {
                    if (response.isSuccess()) {
                        BaseResponse<StoreInfo> data = response;
                        try {
                            ItemData itemData = new ItemData();
                            itemData.add(new TypeInfo("店铺排行榜"));
                            GoodsListInfo goodsListInfo = new GoodsListInfo();
                            goodsListInfo.setCollectdesc_goods_list(data.getData().getCollectdesc_goods_list());
                            goodsListInfo.setSalenumdesc_goods_list(data.getData().getSalenumdesc_goods_list());
                            goodsListInfo.setList(goodsListInfo.getCollectdesc_goods_list());
                            itemData.add(goodsListInfo);
                            itemData.add(new TypeInfo("店主推荐"));
                            itemData.addAll(data.getData().getRec_goods_list());
                            setData(itemData);
                            ((StoreActivity) getActivity()).setStoreInfo(data.getData().getStore_info());
                        } catch (Exception e) {
                            KLog.i(e.toString());
                        }
                    } else {
                        ToastUtils.showLong(response.getMessage());
                    }
                });

        registerObserver("TabLayout", "TabLayout")
                .observe(this, object -> {
                    int index = (int) object;
                    GoodsListInfo goodsListInfo;
                    for (int i = 0; i < adapter.getItems().size(); i++) {
                        if (adapter.getItems().get(i) instanceof GoodsListInfo) {
                            goodsListInfo = (GoodsListInfo) adapter.getItems().get(i);
                            switch (index) {
                                case 0:
                                    goodsListInfo.setList(goodsListInfo.getCollectdesc_goods_list());
                                    break;
                                case 1:
                                    goodsListInfo.setList(goodsListInfo.getSalenumdesc_goods_list());
                                    break;
                            }
                        }
                    }
                });
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.STORE_GOODS_RANK_KEY[1];
    }

    private Object object;

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                object = adapter.getItems().get(i);
                if (object instanceof TypeInfo ||
                        object instanceof GoodsListInfo) {
                    return 2;
                }
                return 1;
            }
        });
        return gridLayoutManager;
    }

    @Override
    protected DelegateAdapter createAdapter() {
        return AdapterPool.newInstance().getStoreHomeAdapter(getActivity())
                .setOnItemClickListener(this)
                .build();
    }

    @Override
    public void onItemClick(View view, int i, Object o) {

    }
}