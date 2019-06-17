package com.mingpinmall.classz.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.manage.AppManager;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.goldze.common.dmvvm.utils.ResourcesUtils;
import com.goldze.common.dmvvm.utils.TUtil;
import com.goldze.common.dmvvm.utils.log.QLog;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.ItemOfGoodsCommentListBinding;
import com.mingpinmall.classz.databinding.ItemText1Binding;
import com.mingpinmall.classz.databinding.ItemText2Binding;
import com.mingpinmall.classz.databinding.ItemTextBinding;
import com.mingpinmall.classz.databinding.TrecyclerviewBaseBinding;
import com.mingpinmall.classz.ui.vm.bean.GoodsComment;
import com.trecyclerview.TRecyclerView;
import com.trecyclerview.holder.AbsHolder;
import com.trecyclerview.holder.AbsItemHolder;
import com.trecyclerview.listener.OnItemClickListener;

import java.util.List;
import java.util.Map;

public class ItemOfGoodsCommentList extends AbsItemHolder<GoodsComment, ItemOfGoodsCommentList.ViewHolder> {

    private ItemOfGoodsCommentListBinding binding;

    public ItemOfGoodsCommentList(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_of_goods_comment_list;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GoodsComment goodsComment) {
        binding = holder.getBinding();
        binding.setData(goodsComment);
        /*追加内容*/
        String contentAgain = goodsComment.getGeval_content_again();
        binding.llContentAgain.removeAllViews();
        List<String> gevalImage240 = goodsComment.getGeval_image_240();
        if (null != gevalImage240 && gevalImage240.size() > 0) {
            binding.llContentAgain.addView(getTrecyclerview(gevalImage240).getRoot());
        }
        if (!TextUtils.isEmpty(contentAgain)) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            ItemTextBinding text1Binding = DataBindingUtil.bind(View.inflate(mContext,
                    R.layout.item_text, null));
            ((TextView) text1Binding.getRoot().findViewById(R.id.text)).setTextColor(ResourcesUtils.getInstance().getColor(R.color.gray));
            ((TextView) text1Binding.getRoot().findViewById(R.id.text)).setTextSize(14f);
            text1Binding.setData(String.format("%s 追加评价", goodsComment.getGeval_addtime_again_date()));
            text1Binding.getRoot().setLayoutParams(layoutParams);
            binding.llContentAgain.addView(text1Binding.getRoot());

            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            text1Binding = DataBindingUtil.bind(View.inflate(mContext,
                    R.layout.item_text, null));
            ((TextView) text1Binding.getRoot().findViewById(R.id.text)).setTextSize(14f);
            text1Binding.setData(contentAgain);
            text1Binding.getRoot().setLayoutParams(layoutParams);
            binding.llContentAgain.addView(text1Binding.getRoot());

            List<String> imageAgain240 = goodsComment.getGeval_image_again_240();
            if (null != imageAgain240 && imageAgain240.size() > 0) {
                binding.llContentAgain.addView(getTrecyclerview(imageAgain240).getRoot());
            }
        }
        binding.executePendingBindings();
    }

    private TrecyclerviewBaseBinding getTrecyclerview(List<String> imageAgain240) {
        TrecyclerviewBaseBinding trecyclerviewBaseBinding = DataBindingUtil.bind(View.inflate(mContext,
                R.layout.trecyclerview_base, null));
        trecyclerviewBaseBinding.setTag("3");
        trecyclerviewBaseBinding.setLayout(new GridLayoutManager(mContext, 6));
        trecyclerviewBaseBinding.setAdapter(AdapterPool.newInstance().getItemImage(mContext)
                .setOnItemClickListener((view, i, object) -> ImageUtils.loadImages(AppManager.getInstance().currentActivity(), i, imageAgain240))
                .build());
        trecyclerviewBaseBinding.setData(imageAgain240);
        trecyclerviewBaseBinding.getRoot().setPadding(20, 0, 0, 0);
        return trecyclerviewBaseBinding;
    }

    class ViewHolder extends AbsHolder {
        ItemOfGoodsCommentListBinding binding;

        private ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public ItemOfGoodsCommentListBinding getBinding() {
            return binding;
        }
    }
}