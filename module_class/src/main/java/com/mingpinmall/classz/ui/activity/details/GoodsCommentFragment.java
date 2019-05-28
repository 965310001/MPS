package com.mingpinmall.classz.ui.activity.details;

import android.os.Bundle;
import android.view.View;

import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.goldze.common.dmvvm.base.mvvm.base.BaseListFragment;
import com.goldze.common.dmvvm.utils.StatusBarUtils;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.GoodsCommentListBean;
import com.socks.library.KLog;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.adapter.ItemData;
import com.trecyclerview.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 商品详情 -商品评价
 */
public class GoodsCommentFragment extends BaseListFragment<ClassifyViewModel> implements OnItemClickListener {

    private String type = "";

    public static GoodsCommentFragment newInstance() {
        return new GoodsCommentFragment();
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        binding.getRoot().setPadding(0, ScreenUtil.dip2px(activity, 44) + StatusBarUtils.getStatusBarHeight(activity), 0, 0);
    }

    @Override
    protected void getRemoteData() {
        super.getRemoteData();

        KLog.i("EVALUATE_EVENT_KEY" + adapter.getItems().size());
        if (itemData.size() == 0) {
            KLog.i("EVALUATE_EVENT_KEY");
            itemData.add(0, new ArrayList(Arrays.asList("全部评价", "好评", "中评", "差评", "订单晒图", "追加评价", String.valueOf(index))));
            setData(itemData);
        }
        mViewModel.getEvaluate(((ShoppingDetailsActivity) activity).getId(), type, String.valueOf(page));
    }

    int index;

    private final ItemData itemData = new ItemData();

    @Override
    protected void dataObserver() {
        super.dataObserver();
        /*评价列表*/
        registerObserver(Constants.EVALUATE_EVENT_KEY[0], GoodsCommentListBean.class)
                .observe(this, response -> {
                    setData(response.getDatas().getGoods_eval_list());
                });
        registerObserver("FLOWTAGLAYOUT_POSTION", Integer.class).observe(this, integer -> {
            if (integer == 0) {
                type = "";
            } else {
                type = String.valueOf(integer);
            }
            index = integer;
            onRefresh();
            KLog.i(type);
        });
    }

    @Override
    public void onRefresh() {
        itemData.clear();
        super.onRefresh();
    }

    @Override
    protected DelegateAdapter createAdapter() {
        return AdapterPool.newInstance()
                .getEvaluate(getActivity())
                .setOnItemClickListener(this)
                .build();
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.EVALUATE_EVENT_KEY[1];
    }

    @Override
    public void onItemClick(View view, int i, Object o) {

    }
}