package com.mingpinmall.classz;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.goldze.common.dmvvm.utils.ImageUtils2;
import com.goldze.common.dmvvm.utils.log.QLog;
import com.goldze.common.dmvvm.widget.CountClickView;
import com.goldze.common.dmvvm.widget.MultipleItemView;
import com.leon.lib.settingview.LSettingItem;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.adapter.CustomDefaultFlowTagAdapter;
import com.mingpinmall.classz.ui.vm.bean.ClassificationRighitBean;
import com.goldze.common.dmvvm.utils.HtmlFromUtils;

import com.trecyclerview.TRecyclerView;
import com.trecyclerview.adapter.DelegateAdapter;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;


public class DatabingUtils {

    @BindingAdapter(value = {"image", "imageStyle"}, requireAll = false)
    public static void imageLoader(ImageView imageView, String url, String imageStyle) {
        if (!TextUtils.isEmpty(url)) {
            if (!TextUtils.isEmpty(imageStyle)) {
                switch (imageStyle) {
                    case "circle":
//                        if (url.endsWith(".gif")) {
//                            ImageUtils.loadImageCircleAsGif(imageView, url);
//                        } else {
//                            ImageUtils.loadImage(imageView, url);
//                        }
//                        QLog.i("加载circle 图片");
                        ImageUtils2.loadImage(imageView, url, ImageUtils2.ImageType.CIRCLE);
                        break;
                    case "local":
                        QLog.i("加载本地图片 图片");
//                        ImageUtils.loadImage(imageView, Integer.parseInt(url));

                        ImageUtils2.loadImage(imageView, Integer.parseInt(url));
                        break;
                    default:
//                        ImageUtils.loadImage(imageView, url);


                        ImageUtils2.loadImage(imageView, url);
                        break;
                }
            } else if (url.endsWith(".gif")) {
//                ImageUtils.loadImageAsGIF(imageView, url);
                QLog.i("gif 图片");
                ImageUtils2.loadImage(imageView, url);
            } else {
//                ImageUtils.loadImage(imageView, url);
                ImageUtils2.loadImage(imageView, url);
            }
        }

    }

    @BindingAdapter(value = {"sqImage", "sqSpan"}, requireAll = false)
    public static void squareImageLoader(ImageView imageView, String url, String sqSpan) {
        if (!TextUtils.isEmpty(url)) {
            if (!TextUtils.isEmpty(sqSpan)) {
                int width = 640 / Integer.parseInt(sqSpan);
                ImageUtils.loadImage(imageView, url, width, width);
            } else {
                ImageUtils.loadImage(imageView, url, 320, 320);
            }
        }

    }

    /*LSettingItem 的绑定*/
    @BindingAdapter({"lefttext"})
    public static void setLeftText(LSettingItem settingItem, final ClassificationRighitBean.DatasBean.ClassListBean data) {
        if (null != data && !TextUtils.isEmpty(data.getGc_name())) {
            settingItem.setLeftText(data.getGc_name());
        }
        settingItem.setmOnLSettingItemClick(isChecked -> {
            QLog.i(data.getGc_name() + " " + data.getGc_id());
            ActivityToActivity.toActivity(ARouterConfig.classify.PRODUCTSACTIVITY, "id", String.valueOf(data.getGc_id()));
        });
    }

    @BindingAdapter({"pleftText"})
    public static void pleftText(MultipleItemView settingItem, final String data) {
        if (null != data && !TextUtils.isEmpty(data)) {
            settingItem.setLeftText(data);
        }
        settingItem.setOnClickListener(v -> QLog.i("商家界面"));
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
        QLog.i(titles);
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

    @BindingAdapter(value = {"maxcount", "mincount", "currcount", "listener"}, requireAll = false)
    public static void setMaxCount(CountClickView ccvClick, String max, String min, String currcount, CountClickView.OnClickAfterListener listener) {
        try {
            if (TextUtils.isEmpty(min)) {
                min = "1";
            }
            if (TextUtils.isEmpty(max)) {
                max = "1";
            }
            if (TextUtils.isEmpty(currcount)) {
                currcount = "1";
            }
            ccvClick.setMinCount(Integer.valueOf(min));
            ccvClick.setMaxCount(Integer.valueOf(max));
            ccvClick.setCurrCount(Integer.valueOf(currcount));

            if (null != listener) {
                ccvClick.setAfterClickListener(listener);
            }
        } catch (Exception e) {
            QLog.i(e.toString() + "最好两个都设置");
        }

    }

    /*设置 星星数量*/
    @BindingAdapter(value = {"rating"}, requireAll = false)
    public static void setRating(MaterialRatingBar ratingBar, String rating) {
        if (null != ratingBar) {
            ratingBar.setRating(Float.parseFloat(rating));
        }
    }

    /*设置 FlowTagLayout*/
    @BindingAdapter(value = {"list", "eventkey"}, requireAll = false)
    public static void setFlowTagLayout(FlowTagLayout flowTagLayout, List list, Object eventkey) {
        if (null != flowTagLayout && null != list && list.size() > 0) {
            /*int index = Integer.parseInt((String) list.get(list.size() - 1));*/
//            if (flowTagLayout.getAdapter() == null) {
            CustomDefaultFlowTagAdapter adapter = new CustomDefaultFlowTagAdapter(flowTagLayout.getContext());
            flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
            flowTagLayout.setAdapter(adapter);
            /*list.remove(list.get(list.size() - 1));*/
            flowTagLayout.setItems(list.subList(0, list.size() - 1));

            /*QLog.i(Integer.valueOf(list.get(list.size() - 1).toString()) + "");*/
//            QLog.i(Integer.valueOf((Integer) list.get(list.size() - 1))+"");
            flowTagLayout.setOnTagSelectListener((parent, position, selectedList) -> {
                if (null != eventkey) {
                    LiveBus.getDefault().postEvent(eventkey, position);
                }
            });
            flowTagLayout.setSelectedPositions(Integer.valueOf(list.get(list.size() - 1).toString()));
//            }
        }
    }


    /**
     * HTML的解析
     *
     * @param textView
     * @param content
     * @param image
     */
    @BindingAdapter(value = {"html", "imageHtml"}, requireAll = false)
    public static void setHtml(final TextView textView, String content, String image) {
//        final Drawable[] d = new Drawable[1];
        if (!TextUtils.isEmpty(image)) {
//            Html.ImageGetter imgGetter = new Html.ImageGetter() {
//                @Override
//                public Drawable getDrawable(final String source) {
//                    if (source.endsWith(".jpg") || source.endsWith(".png")) {
//                        ImageUtils.loadImage(textView.getContext(), source, new SimpleTarget<Drawable>() {
//                            @Override
//                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//                                d[0] = resource;
//                                textView.invalidate();
//                                QLog.i("网络图片" + source);
//                            }
//                        });
//                    } else {
//                        try {
//                            d[0] = Utils.getApplication().getResources().getDrawable(Integer.parseInt(source));
//                            d[0].setBounds(0, 0, d[0].getIntrinsicWidth(), d[0].getIntrinsicHeight());
//                            return d[0];
//                        } catch (Exception e) {
//                            QLog.i(e.toString());
//                            return null;
//                        }
//
//                    }
//                    return d[0];
//                }
//            };
//            String source = FaceConversionUtil.dealExpression(image);
//            textView.setText(Html.fromHtml(source, imgGetter, null));
//            content="[http://hao.qudao.com/upload/article/20160120/82935299371453253610.jpg]";
            /*QLog.i(image + "内容");*/
            if (!TextUtils.isEmpty(image) &&
                    (image.endsWith(".jpg") || image.endsWith(".png"))) {
                image = String.format("[%s]", image);
            }
            HtmlFromUtils.setImageFromNetWork(textView.getContext(), textView, image, false);
        } else if (!TextUtils.isEmpty(content)) {
            textView.setText(Html.fromHtml(content));
        }
    }

    /*封装*/
    @BindingAdapter(value = {"items", "adapter", "layout", "itemdecoration"}, requireAll = false)
    public static void setChild(TRecyclerView rv,
                                List data,
                                DelegateAdapter adapter,
                                RecyclerView.LayoutManager layout,
                                boolean isDecoration) {/*默认是显示 true:不显示*/
        if (null == rv.getAdapter()) {
            Context context = rv.getContext();
            if (null == rv.getLayoutManager()) {
                if (layout == null) {
                    layout = new LinearLayoutManager(context);
                }
            } else {
                layout = rv.getLayoutManager();
            }
            switch (Integer.parseInt(rv.getTag().toString())) {
                case 0:
                    adapter = AdapterPool.newInstance().getRightAdapter(context).build();
                    layout = new GridLayoutManager(context, 3);
                    break;

             /*   case 3:
                    adapter = AdapterPool.newInstance().getEvaluate(context).build();
                    layout = new GridLayoutManager(context, 3);
                    break;*/

                default:
                    /*QLog.i("必须个TRecyclerView 设置TAG");*/
                    break;
            }
            if (!isDecoration) {
                rv.addItemDecoration(new DividerItemDecoration(context,
                        DividerItemDecoration.VERTICAL));
            }
            rv.setLayoutManager(layout);
        } else {
            adapter = (DelegateAdapter) rv.getAdapter();
        }
        if (null != adapter) {
            rv.setAdapter(adapter);
            if (null != data && data.size() > 0) {
                adapter.setDatas(data);
            }
            adapter.notifyDataSetChanged();
        }
    }

//    @BindingAdapter(value = {"items", "adapter", "layout", "isdecoration"}, requireAll = false)
//    public static void setChild(TRecyclerView rv,
//                                List data,
//                                DelegateAdapter adapter,
//                                RecyclerView.LayoutManager layout,
//                                boolean isDecoration) {/*默认是false 是需要*/
//        adapter = (DelegateAdapter) rv.getAdapter();
//        if (null == adapter) {
//            Context context = rv.getContext();
//            if (null == layout) {
//                layout = new GridLayoutManager(context, 1);
//            }
////            if (!isDecoration) {
////                rv.addItemDecoration(new DividerItemDecoration(context,
////                        DividerItemDecoration.VERTICAL));
////            }
//            switch (Integer.parseInt(rv.getTag().toString())) {
//                case 0:
//                    adapter = AdapterPool.newInstance().getRightAdapter(context).build();
//                    layout = new GridLayoutManager(context, 3);
//                    break;
//                case 1:
//                    QLog.i("左边");
//                    /*左边*/
//                    adapter = AdapterPool.newInstance().getLeftAdapter(context)
//                            .build();
//                    rv.addItemDecoration(new DividerItemDecoration(context,
//                            DividerItemDecoration.VERTICAL));
//                    layout = new GridLayoutManager(context, 1);
//
//                    if (null == layout) {
//                        layout = new LinearLayoutManager(context);
//                    }
//                    rv.addItemDecoration(new DividerItemDecoration(context,
//                            DividerItemDecoration.VERTICAL));
//                    break;
//                case 2:
//                    adapter = AdapterPool.newInstance().getConfirmOrder(context)
//                            .build();
//                    rv.addItemDecoration(new DividerItemDecoration(context,
//                            DividerItemDecoration.VERTICAL));
//                    layout = new GridLayoutManager(context, 1);
//                    break;
//                case 3:
//                    final InvoiceListInfo.InvoiceListBean[] bean = {null};
//                    adapter = AdapterPool.newInstance().getInvoiceList(context)
//                            .setOnItemClickListener(new OnItemClickListener() {
//                                @Override
//                                public void onItemClick(View view, int i, Object o) {
//                                    if (null != bean[0]) {
//                                        bean[0].setChecked(false);
//                                    }
//                                    bean[0] = (InvoiceListInfo.InvoiceListBean) o;
//                                    bean[0].setChecked(true);
//                                }
//                            }).build();
//                    rv.addItemDecoration(new DividerItemDecoration(context,
//                            DividerItemDecoration.VERTICAL));
//                    layout = new GridLayoutManager(context, 1);
//                    break;
//                case 4:
//                    adapter = AdapterPool.newInstance().getRecommend(context).build();
//                    rv.addItemDecoration(new DividerItemDecoration(context,
//                            DividerItemDecoration.VERTICAL));
//                    layout = new GridLayoutManager(context, 4);
//                    break;
//                default:
//                    QLog.i("必须个TRecyclerView 设置TAG");
//                    break;
//            }
//            rv.setLayoutManager(layout);
//            rv.setAdapter(adapter);
//        }
//        if (null != data && data.size() > 0) {
//            adapter.setDatas(data);
//        }
//        adapter.notifyDataSetChanged();
//    }


}
