package com.mingpinmall.classz.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.goldze.common.dmvvm.adapter.BaseRecyclerAdapter;
import com.goldze.common.dmvvm.base.mvvm.base.BaseViewHolder;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.RecyclerviewBaseBinding;
import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.utils.ResUtils;

import java.util.List;

public class MenuPopupWindow extends PopupWindow {

    public MenuPopupWindow(Context context, View view) {
        super(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(view);
        initViews();
    }

    private void initViews() {
        setAnimationStyle(R.style.XUI_Animation_PopDownMenu_Right);
        setFocusable(true);
        setOutsideTouchable(true);
    }

    public static class Builder {
        private MenuPopupWindow menuPopupWindow;
        private Context context;
        private FrameLayout frameLayout;
        private BaseRecyclerAdapter.OnItemClickListener onItemClickListener;
        //背景颜色
        List<AdapterItem> listItems;
        private int colorBg = R.color.color_999;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setColorBg(int color) {
            colorBg = color;
            return this;
        }

        public Builder setOnItemClickListener(BaseRecyclerAdapter.OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
            return this;
        }

        public Builder setItem(List<AdapterItem> listItems) {
            this.listItems = listItems;
            return this;
        }

        public Builder build() {
            frameLayout = new FrameLayout(context);
            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(ResUtils.getDimensionPixelSize(R.dimen.xui_popup_width_phone),
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.recyclerview_base, null);
            relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(ResUtils.getDimensionPixelSize(R.dimen.xui_popup_width_phone),
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            relativeLayout.setBackgroundColor(context.getResources().getColor(colorBg));
            RecyclerviewBaseBinding binding = DataBindingUtil.bind(relativeLayout);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            BaseRecyclerAdapter<AdapterItem> adapter = new BaseRecyclerAdapter<AdapterItem>(context, listItems, R.layout.xui_adapter_listview_simple_item) {
                @Override
                protected void convert(BaseViewHolder holder, AdapterItem adapterItem, int position, List<Object> payloads) {
                    holder.getView(R.id.ll_content)
                            .getRootView().setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));

                    LinearLayout rootView = holder.getView(R.id.ll_content);
                    rootView.setGravity(Gravity.CENTER);

                    holder.setText(R.id.tv_title, adapterItem.getTitle())
                            .setTextColor(R.id.tv_title, context.getResources().getColor(R.color.white))
                            .addOnClickListener(R.id.ll_content)
                            .setImageResource(R.id.iv_icon, adapterItem.getIcon())
                            .setColorFilter(R.id.iv_icon, context.getResources().getColor(R.color.white));
                }
            };
            binding.recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(onItemClickListener);
            binding.executePendingBindings();
            frameLayout.setPadding(0, 0, 20, 0);
            frameLayout.addView(binding.getRoot());
            relativeLayout.setBackgroundResource(R.drawable.bg_popwindow_radius);
            return this;
        }

        public MenuPopupWindow createPop() {
            menuPopupWindow = new MenuPopupWindow(context, frameLayout);
            menuPopupWindow.showAsDropDown(frameLayout.getRootView(), Gravity.END | Gravity.RIGHT, 0, 0);
            return menuPopupWindow;
        }

        public MenuPopupWindow createPop(View view) {
            menuPopupWindow = new MenuPopupWindow(context, frameLayout);

            menuPopupWindow.showAsDropDown(view, Gravity.TOP | Gravity.RIGHT, 0, 0);
            return menuPopupWindow;
        }
    }


}
