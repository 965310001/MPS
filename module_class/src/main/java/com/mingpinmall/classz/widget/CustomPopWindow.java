package com.mingpinmall.classz.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.goldze.common.dmvvm.utils.DisplayUtil;
import com.goldze.common.dmvvm.utils.Utils;
import com.mingpinmall.classz.R;
import com.socks.library.KLog;

import java.util.Arrays;
import java.util.List;

/**
 * @author GuoFeng
 * @date : 2019/3/31 14:43
 * @description: 自定义PopupWindow
 */
public class CustomPopWindow extends PopupWindow {

    public CustomPopWindow() {
    }

    public CustomPopWindow(final Context context) {
        super(context);
        View contentView = LayoutInflater.from(context).inflate(R.layout.recyclerview_base, null,
                false);
        final RecyclerView recyclerView = contentView.findViewById(R.id.recycler_view);
        initRecyclerView(recyclerView);
        contentView.setBackgroundColor(Color.WHITE);
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        conentView = inflater.inflate(R.layout.recyclerview_base, null);
        int h = ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();
        int w = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(contentView);
        bgAlpha((Activity) context, 0.85f);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        bgAlpha(0.15f);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(Color.GRAY);
        ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);

        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);

        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                bgAlpha((Activity) context, 0.98f);
                return false;
            }
        });

        setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //popupwindow消失时使背景不透明
                bgAlpha((Activity) context, 1f);
            }
        });

        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimationPreview);
//        LinearLayout addTaskLayout = (LinearLayout) conentView
//                .findViewById(R.id.add_task_layout);
//        LinearLayout teamMemberLayout = (LinearLayout) conentView
//                .findViewById(R.id.team_member_layout);
//        addTaskLayout.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                AddPopWindow.this.dismiss();
//            }
//        });
//        teamMemberLayout.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                AddPopWindow.this.dismiss();
//            }
//        });
    }

    private void bgAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        class RecyclerHolder extends RecyclerView.ViewHolder {
            TextView textView;

            private RecyclerHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.text);
//                textView.setTextColor(getResources().getColor(R.color.black));
                textView.setTextSize(DisplayUtil.px2dp(Utils.getApplication(), 24));
                textView.setPadding(20, 0, 0, 0);
            }
        }

        RecyclerView.Adapter<RecyclerHolder> adapter = new RecyclerView.Adapter<RecyclerHolder>() {
            List<String> list = Arrays.asList("综合排序", "价格从高到低", "价格从低到高", "人气排序");

            @NonNull
            @Override
            public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_text, viewGroup, false);
                return new RecyclerHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerHolder holder, final int position) {
                holder.textView.setText(list.get(position));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KLog.i(list.get(position));
                        /*执行网络请求*/
                        dismiss();
                    }
                });
            }

            @Override
            public int getItemCount() {
                return list.size();
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }


    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent);
        } else {
            this.dismiss();
        }
    }

}
