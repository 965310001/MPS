package com.mingpinmall.classz.widget;

import android.content.Context;
import android.graphics.Color;

import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.goldze.common.dmvvm.base.event.LiveBus;

import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ui.constants.Constants;
import com.socks.library.KLog;

import java.util.List;

/**
 * @author GuoFeng
 * @date : 2019/3/31 14:43
 * @description: 自定义PopupWindow
 */
public class CustomPopWindow extends PopupWindow {

    public CustomPopWindow() {
    }

    public CustomPopWindow(Context context, View view) {
        super(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(view);
        initViews();
    }

    private void initViews() {
        setAnimationStyle(R.style.popwin_anim_style);
        setFocusable(true);
        setOutsideTouchable(true);
    }

    public static class Builder {
        private Context context;
        private List<String> listData;
        private LinearLayout contextll;
        //背景颜色
        private int colorBg = Color.parseColor("#F8F8F8");

        private CustomPopWindow mCustomPopWindow;

        private OnCustomPopWindowClickListener listener;

        public interface OnCustomPopWindowClickListener {
            void onClick(PopupWindow dialog, View itemView, int position, String content);
        }

        public CustomPopWindow.Builder setListener(OnCustomPopWindowClickListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder(Context context) {
            this.context = context;
        }

        public CustomPopWindow.Builder setDataSource(List<String> listData) {
            this.listData = listData;
            return this;
        }

        public CustomPopWindow.Builder setColorBg(int color) {
            colorBg = context.getResources().getColor(color);
            return this;
        }

        public CustomPopWindow.Builder build() {
            newItemLayout();
            return this;
        }

        private void initRecyclerView(RecyclerView recyclerView) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            class RecyclerHolder extends RecyclerView.ViewHolder {
                TextView textView;

                private RecyclerHolder(View itemView) {
                    super(itemView);
                    textView = itemView.findViewById(R.id.text);
                    textView.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.text_8));
                }
            }

            RecyclerView.Adapter<RecyclerHolder> adapter = new RecyclerView.Adapter<RecyclerHolder>() {

                @NonNull
                @Override
                public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_text, viewGroup, false);
                    return new RecyclerHolder(view);
                }

                @Override
                public void onBindViewHolder(@NonNull final RecyclerHolder holder, final int position) {
                    holder.textView.setText(listData.get(position));
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (null != listener) {
                                listener.onClick(mCustomPopWindow, holder.itemView, position, listData.get(position));
                            }else{
                                KLog.i("你还没有初始化 listener");
                            }
//                            KLog.i(listData.get(position));
//                            LiveBus.getDefault().postEvent(Constants.CUSTOMPOPWINDOW_KEY[0], listData.get(position));
//                            mCustomPopWindow.dismiss();
                        }
                    });
                }

                @Override
                public int getItemCount() {
                    return listData.size();
                }
            };
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        }

        private void newItemLayout() {
            contextll = new LinearLayout(context);
            contextll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            contextll.setBackgroundColor(context.getResources().getColor(R.color.color_33000000));
            contextll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCustomPopWindow != null) {
                        mCustomPopWindow.dismiss();
                    }
                }
            });
            View contentView = LayoutInflater.from(context).inflate(R.layout.recyclerview_base, null,
                    false);
            contentView.setBackgroundColor(colorBg);
            RecyclerView recyclerView = contentView.findViewById(R.id.recycler_view);
            initRecyclerView(recyclerView);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            contextll.addView(contentView, lp);
        }

        public CustomPopWindow createPop() {
            if (listData == null || listData.size() == 0) {
                try {
                    throw new Exception("没有筛选条件");
                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                return null;
            }
            mCustomPopWindow = new CustomPopWindow(context, contextll);
            return mCustomPopWindow;
        }

    }
}