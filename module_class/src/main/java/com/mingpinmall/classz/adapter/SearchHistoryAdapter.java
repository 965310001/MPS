//package com.mingpinmall.classz.adapter;
//
//
//import android.content.Context;
//import android.support.annotation.Nullable;
//
//import com.goldze.common.dmvvm.adapter.BaseRecyclerAdapter;
//import com.goldze.common.dmvvm.base.event.LiveBus;
//import com.goldze.common.dmvvm.base.mvvm.base.BaseViewHolder;
//import com.mingpinmall.classz.R;
//import com.mingpinmall.classz.ui.constants.Constants;
//import com.socks.library.KLog;
//
//import java.util.List;
//
///**
// * 搜索历史Adapter
// */
//public class SearchHistoryAdapter extends BaseRecyclerAdapter<String> {
//
//    public SearchHistoryAdapter(Context context, @Nullable List<String> list) {
//        super(context, list, R.layout.item_search_history);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder holder, final String item, final int position, List<Object> payloads) {
//        holder.setText(R.id.tv_item, item);
//
//        holder.itemView.setOnClickListener(v -> {
//            LiveBus.getDefault().postEvent(Constants.SEARCH_EVENT_KEY[2], item);
//            KLog.i("setOnClickListener");
//        });
//
//        holder.getView(R.id.img_delete).setOnClickListener(v -> {
//            LiveBus.getDefault().postEvent(Constants.SEARCH_EVENT_KEY[3], position);
//
//            KLog.i("img_delete");
//        });
//
//
//    }
//}
