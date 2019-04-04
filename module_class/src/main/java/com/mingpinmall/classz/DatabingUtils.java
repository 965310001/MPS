package com.mingpinmall.classz;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.leon.lib.settingview.LSettingItem;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.ui.vm.bean.ClassificationRighitBean;
import com.socks.library.KLog;
import com.trecyclerview.TRecyclerView;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.listener.OnItemClickListener;

import java.util.List;


public class DatabingUtils {

    @BindingAdapter({"src"})
    public static void imageLoader(ImageView imageView, String url) {
        if (".gif".endsWith(url)) {
            KLog.i("gif");
            ImageUtils.loadImageAsGIF(imageView, url);
        } else {
            ImageUtils.loadImage(imageView, url);
        }
    }

    /*LSettingItem 的绑定*/
    @BindingAdapter({"lefttext"})
    public static void setLeftText(LSettingItem settingItem, final ClassificationRighitBean.DatasBean.ClassListBean data) {
        if (null != data && !TextUtils.isEmpty(data.getGc_name())) {
            settingItem.setLeftText(data.getGc_name());
        }
        settingItem.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                KLog.i(data.getGc_name() + " " + data.getGc_id());
                ActivityToActivity.toActivity(ARouterConfig.classify.PRODUCTSACTIVITY, "id", String.valueOf(data.getGc_id()));
            }
        });
    }

    @BindingAdapter("items")
    public static void setChild(TRecyclerView rv, List data) {
        DelegateAdapter adapter = (DelegateAdapter) rv.getAdapter();
        if (null == adapter) {
            KLog.i(rv.getTag());
            Context context = rv.getContext();
            switch (Integer.parseInt(rv.getTag().toString())) {
                case 0:
                    adapter = AdapterPool.newInstance().getRightAdapter(context).build();
                    GridLayoutManager layout = new GridLayoutManager(context, 3);
                    rv.setLayoutManager(layout);
                    break;
                case 1:
                    /*左边*/
                    adapter = AdapterPool.newInstance().getLeftAdapter(context)
                            .build();
                    rv.addItemDecoration(new DividerItemDecoration(context,
                            DividerItemDecoration.VERTICAL));
                    layout = new GridLayoutManager(context, 1);
                    rv.setLayoutManager(layout);
                    break;
                default:
                    KLog.i("必须个TRecyclerView 设置TAG");
                    break;
            }
            rv.setAdapter(adapter);
        }
        if (null != data && data.size() > 0) {
            adapter.setDatas(data);
        }
        adapter.notifyDataSetChanged();
    }

//    @BindingAdapter("bind:onItemClickListener")
//    public static void setOnItemClickListener(TRecyclerView rv, OnItemClickListener listener) {
//        DelegateAdapter.Builder adapter = (DelegateAdapter.Builder) rv.getAdapter();
//        KLog.i(rv.getTag());
//        if (null != adapter) {
////            Context context = rv.getContext();
////            switch (Integer.parseInt(rv.getTag().toString())) {
////                case 0:
////                    adapter = AdapterPool.newInstance().getRightAdapter(context).build();
////                    GridLayoutManager layout = new GridLayoutManager(context, 3);
////                    rv.setLayoutManager(layout);
////                    break;
////                case 1:
////                    /*左边*/
////                    adapter = AdapterPool.newInstance().getLeftAdapter(context)
////                            .setOnItemClickListener((OnItemClickListener) context)
////                            .build();
////                    rv.addItemDecoration(new DividerItemDecoration(context,
////                            DividerItemDecoration.VERTICAL));
////                    layout = new GridLayoutManager(context, 1);
////                    rv.setLayoutManager(layout);
////                    break;
////                default:
////                    KLog.i("必须个TRecyclerView 设置TAG");
////                    break;
////            }
////            rv.setAdapter(adapter);
//        }
//        if (null != data && data.size() > 0) {
//            adapter.setDatas(data);
//        }
//        adapter.notifyDataSetChanged();
//    }


}
