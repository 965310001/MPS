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
import com.mingpinmall.classz.constants.Constants;
import com.socks.library.KLog;

import java.util.List;

/**
 * @author GuoFeng
 * @date : 2019/3/31 14:43
 * @description: 自定义PopupWindow
 */
public class CustomPopWindow extends PopupWindow {

    private int colorBg = Color.parseColor("#F8F8F8");
    private int titleTextColor = Color.parseColor("#333333");//标题字体颜色

    public CustomPopWindow() {
    }

    public CustomPopWindow(Context context, View view) {
        //这里可以修改popupwindow的宽高
        super(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(view);
        initViews();
    }

    private void initViews() {
        setAnimationStyle(R.style.popwin_anim_style);
        //setBackgroundDrawable(new ColorDrawable(0x00000000));
        setFocusable(true);
        setOutsideTouchable(true);
    }

    public static class Builder {
        private Context context;
        private List<String> listData;
        private int columnCount;
        //        private GridLayout rootGridLayout;
        private LinearLayout contextll;
        //背景颜色
        private int colorBg = Color.parseColor("#F8F8F8");
        private int titleTextSize = 14;//SP
        private int tabTextSize = 14;//SP
        private int titleTextColor = Color.parseColor("#333333");//标题字体颜色
        private int tabTextColor = R.color.fit_item_textcolor;//选项字体颜色
        private int tabBgDrawable = R.drawable.item_lable_bg_shape;//选项背景颜色
        //当前加载的行数
        private int row = -1;
        private CustomPopWindow mCustomPopWindow;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置数据源
         *
         * @return
         */
        public CustomPopWindow.Builder setDataSource(List<String> listData) {
            this.listData = listData;
            return this;
        }

        public CustomPopWindow.Builder setColumnCount(int columnCount) {
            this.columnCount = columnCount;
            return this;
        }

        public CustomPopWindow.Builder setColorBg(int color) {
            colorBg = context.getResources().getColor(color);
            return this;
        }

        public CustomPopWindow.Builder setTitleTextSize(int titleTextSize) {
            this.titleTextSize = titleTextSize;
            return this;
        }

        public CustomPopWindow.Builder setTabTextSize(int tabTextSize) {
            this.tabTextSize = tabTextSize;
            return this;
        }

        public CustomPopWindow.Builder setTitleTextColor(int titleTextColor) {
            this.titleTextColor = titleTextColor;
            return this;
        }

        public CustomPopWindow.Builder setTabTextColor(int tabTextColor) {
            this.tabTextColor = tabTextColor;
            return this;
        }

        public CustomPopWindow.Builder setTabBgDrawable(int tabBgDrawable) {
            this.tabBgDrawable = tabBgDrawable;
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
                public void onBindViewHolder(@NonNull RecyclerHolder holder, final int position) {
                    holder.textView.setText(listData.get(position));
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            KLog.i(listData.get(position));
                            LiveBus.getDefault().postEvent(Constants.CUSTOMPOPWINDOW_KEY[0], listData.get(position));
                            mCustomPopWindow.dismiss();

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