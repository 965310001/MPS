package com.mingpinmall.classz;

import android.databinding.BindingAdapter;
import android.support.v7.widget.GridLayoutManager;
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

    @BindingAdapter({"android:src"})
    public static void imageLoader(ImageView imageView, String url) {
        ImageUtils.loadImage(imageView, url);
    }

    /*LSettingItem 的绑定*/
    @BindingAdapter({"leon:lefttext"})
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

//    @BindingAdapter({"leon:urlid"})
//    public static void goTo(LSettingItem settingItem, final String id) {
//        settingItem.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
//            @Override
//            public void click(boolean isChecked) {
//                String[] strings = id.split("-");
//                ActivityToActivity.toActivity(strings[0], "id", strings[1]);
//            }
//        });
//    }

//    @BindingAdapter({"leon:lefttext"})
//    public static void setLeftText(LSettingItem settingItem, String data) {
//        if (null != data) {
//            settingItem.setLeftText(data);
//        }
////        settingItem.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
////            @Override
////            public void click(boolean isChecked) {
////                KLog.i(data.getGc_name() + " " + data.getGc_id());
////                ActivityToActivity.toActivity(ARouterConfig.classify.PRODUCTSACTIVITY, "id", String.valueOf(data.getGc_id()));
////            }
////        });
//    }

    @BindingAdapter("bind:items")
    public static void setChild(TRecyclerView rv, List data) {
        DelegateAdapter adapter = (DelegateAdapter) rv.getAdapter();
        KLog.i(rv.getTag());
        if (null == adapter) {
            switch (Integer.parseInt(rv.getTag().toString())) {
                case 0:
                    adapter = AdapterPool.newInstance().getRightAdapter1(rv.getContext()).build();
                    GridLayoutManager layout = new GridLayoutManager(rv.getContext(), 3);
                    rv.setLayoutManager(layout);
                    rv.setAdapter(adapter);
                    break;
                case 1:
                    break;
                default:
                    KLog.i("必须个TRecyclerView 设置TAG");
                    break;
            }
        }
        adapter.setDatas(data);
        adapter.notifyDataSetChanged();
    }


}
