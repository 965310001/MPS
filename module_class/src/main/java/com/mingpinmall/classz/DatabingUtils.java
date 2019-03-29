package com.mingpinmall.classz;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.goldze.common.dmvvm.utils.ImageUtils;


public class DatabingUtils {

    @BindingAdapter({"android:src"})
    public static void imageLoader(ImageView imageView, String url) {
        ImageUtils.loadImage(imageView, url);
    }

//    @BindingAdapter("bind:items")
//    public static void setChild(TRecyclerView rv, List items) {
//        if (rv.getAdapter() == null) {
//            DelegateAdapter leftAdapter = AdapterPool.newInstance().getProductsAdapter(rv.getContext())
////                    .setOnItemClickListener(this)
//                    .build();
//            leftAdapter.setDatas(items);
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rv.getContext());
//            rv.setLayoutManager(linearLayoutManager);
//            rv.setAdapter(leftAdapter);
//        }
//        rv.loadMoreComplete(items, true);
//    }

}
