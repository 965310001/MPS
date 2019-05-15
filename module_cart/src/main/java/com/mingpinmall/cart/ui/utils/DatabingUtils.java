package com.mingpinmall.cart.ui.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import com.goldze.common.dmvvm.utils.HtmlFromUtils;
import com.socks.library.KLog;
import com.trecyclerview.TRecyclerView;
import com.trecyclerview.adapter.DelegateAdapter;

import java.util.List;

/**
 * @author 小斌
 * @data 2019/5/14
 **/
public class DatabingUtils {
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
//                                KLog.i("网络图片" + source);
//                            }
//                        });
//                    } else {
//                        try {
//                            d[0] = Utils.getApplication().getResources().getDrawable(Integer.parseInt(source));
//                            d[0].setBounds(0, 0, d[0].getIntrinsicWidth(), d[0].getIntrinsicHeight());
//                            return d[0];
//                        } catch (Exception e) {
//                            KLog.i(e.toString());
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
            /*KLog.i(image + "内容");*/
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
//                    adapter = AdapterPool.newInstance().getRightAdapter(context).build();
//                    layout = new GridLayoutManager(context, 3);
                    break;
//                case 3:
////                    final InvoiceListInfo.InvoiceListBean[] bean = {null};
////                    adapter = AdapterPool.newInstance().getInvoiceList(context)
////                            .setOnItemClickListener(new OnItemClickListener() {
////                                @Override
////                                public void onItemClick(View view, int i, Object o) {
////                                    if (null != bean[0]) {
////                                        bean[0].setChecked(false);
////                                    }
////                                    bean[0] = (InvoiceListInfo.InvoiceListBean) o;
////                                    bean[0].setChecked(true);
////                                }
////                            }).build();
////                    rv.addItemDecoration(new DividerItemDecoration(context,
////                            DividerItemDecoration.VERTICAL));
////                    layout = new LinearLayoutManager(context);
//                    break;
//                case 4:
//////                    adapter = AdapterPool.newInstance().getRecommend(context).build();
//////                    rv.addItemDecoration(new DividerItemDecoration(context,
//////                            DividerItemDecoration.VERTICAL));
//////                    layout = new GridLayoutManager(context, 4);
//                    break;
                default:
                    KLog.i("必须个TRecyclerView 设置TAG");
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

}
