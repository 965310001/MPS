package com.mingpinmall.classz;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;

import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.goldze.common.dmvvm.widget.MultipleItemView;
import com.leon.lib.settingview.LSettingItem;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.ui.vm.bean.ClassificationRighitBean;
import com.mingpinmall.classz.ui.vm.bean.InvoiceListInfo;
import com.socks.library.KLog;
import com.trecyclerview.TRecyclerView;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.listener.OnItemClickListener;

import java.util.List;


public class DatabingUtils {

    @BindingAdapter({"src"})
    public static void imageLoader(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            if (".gif".endsWith(url)) {
                ImageUtils.loadImageAsGIF(imageView, url);
            } else {
                ImageUtils.loadImage(imageView, url);
            }
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

    @BindingAdapter({"pleftText"})
    public static void pleftText(MultipleItemView settingItem, final String data) {
        if (null != data && !TextUtils.isEmpty(data)) {
            settingItem.setLeftText(data);
        }
        settingItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KLog.i("商家界面");
            }
        });
    }

    @BindingAdapter({"prightText"})
    public static void prightText(MultipleItemView settingItem, final String data) {
        if (null != data && !TextUtils.isEmpty(data)) {
            settingItem.setRightText(data);
        }
    }

    @BindingAdapter("titles")
    public static void setTitles(TabLayout tabLayout,
                                 String titles) {
        KLog.i(titles);
        if (!TextUtils.isEmpty(titles)) {
            String[] strings = titles.split(";");
            for (String string : strings) {
                tabLayout.addTab(tabLayout.newTab().setText(string));
            }
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    LiveBus.getDefault().postEvent("TabLayout", "TabLayout", tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
    }

    @BindingAdapter("items")
    public static void setChild(TRecyclerView rv, List data) {
        DelegateAdapter adapter = (DelegateAdapter) rv.getAdapter();
        if (null == adapter) {
//            KLog.i(rv.getTag());
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
                case 2:
                    adapter = AdapterPool.newInstance().getConfirmOrder(context)
                            .build();
                    rv.addItemDecoration(new DividerItemDecoration(context,
                            DividerItemDecoration.VERTICAL));
                    layout = new GridLayoutManager(context, 1);
                    rv.setLayoutManager(layout);
                    break;
                case 3:
                    final InvoiceListInfo.InvoiceListBean[] bean = {null};
                    adapter = AdapterPool.newInstance().getInvoiceList(context)
                            .setOnItemClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int i, Object o) {
                                    if (null != bean[0]) {
                                        bean[0].setChecked(false);
                                    }
                                    bean[0] = (InvoiceListInfo.InvoiceListBean) o;
                                    bean[0].setChecked(true);
                                }
                            }).build();
                    rv.addItemDecoration(new DividerItemDecoration(context,
                            DividerItemDecoration.VERTICAL));
                    layout = new GridLayoutManager(context, 1);
                    rv.setLayoutManager(layout);
                    break;
                case 4:
                    adapter = AdapterPool.newInstance().getRecommend(context).build();
                    rv.addItemDecoration(new DividerItemDecoration(context,
                            DividerItemDecoration.VERTICAL));
                    layout = new GridLayoutManager(context, 4);
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

}
