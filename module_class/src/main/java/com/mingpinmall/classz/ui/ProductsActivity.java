package com.mingpinmall.classz.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.activity.HorizontalTabActivity;
import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.DisplayUtil;
import com.goldze.common.dmvvm.utils.Utils;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.constants.Constants;
import com.socks.library.KLog;
import com.xuexiang.xui.widget.tabbar.EasyIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

/**
 * 商品分类list
 */
@Route(path = ARouterConfig.classify.PRODUCTSACTIVITY)
public class ProductsActivity extends HorizontalTabActivity {

    @Autowired
    String id;

    @Override
    protected String[] getTabTitles() {
        return Constants.PRODUCTS_TITLE;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);

        mEasyIndicator.setOnTabClickListener(new EasyIndicator.onTabClickListener() {
            @Override
            public void onTabClick(String title, int position) {
                if (position == 0) {
                    showPopupWindow();
                }
            }
        });
    }

    PopupWindow popupWindow;

    void showPopupWindow() {
        if (null == popupWindow) {
            View contentView = LayoutInflater.from(this).inflate(R.layout.recyclerview_base, null,
                    false);
            final RecyclerView recyclerView = contentView.findViewById(R.id.recycler_view);
            initRecyclerView(recyclerView);
            popupWindow = new PopupWindow(contentView,
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
            popupWindow.setTouchable(true);
            bgAlpha(0.95f);
            popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            popupWindow.getContentView().setAlpha(0.2f);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    //popupwindow消失时使背景不透明
                    bgAlpha(1f);
                }
            });
        }
        popupWindow.showAsDropDown(mEasyIndicator, 0, (int) mEasyIndicator.getY());

//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.alpha = 0.5f;
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        getWindow().setAttributes(lp);、
//        contentView.setAlpha(0.15f);
////        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_list,null);
//        //处理popWindow 显示内容
////        handleListView(contentView);
//        //创建并显示popWindow
//        CustomPopWindow mListPopWindow = new CustomPopWindow.PopupWindowBuilder(contentView.getContext())
//                .setView(contentView).enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
//                .setBgDarkAlpha(0.7f) // 控制亮度
//                .size(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)//显示大小
//                .create()
//                .showAsDropDown(mEasyIndicator);
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
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KLog.i(list.get(position));
                        /*执行网络请求*/
                        popupWindow.dismiss();
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

    private void bgAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }


    @Override
    protected List<BaseFragment> getTabFragments() {
        List<BaseFragment> list = new ArrayList<>();
        int length = Constants.PRODUCTS_TITLE.length;
        KLog.i(id);
        for (int i = 0; i < length; i++) {
            list.add(ProductsItemFragment.newInstance(id, i + ""));
        }
        return list;
    }
}