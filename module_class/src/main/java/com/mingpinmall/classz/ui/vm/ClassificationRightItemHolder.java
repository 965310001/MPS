package com.mingpinmall.classz.ui.vm;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.leon.lib.settingview.LSettingItem;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.databinding.ClassifyItemOfRighitListBinding;
import com.mingpinmall.classz.ui.vm.bean.ClassificationRighitBean;
import com.socks.library.KLog;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.holder.AbsHolder;
import com.trecyclerview.holder.AbsItemHolder;
import com.trecyclerview.listener.OnItemClickListener;

public class ClassificationRightItemHolder extends AbsItemHolder<ClassificationRighitBean.DatasBean.ClassListBean,
        ClassificationRightItemHolder.ViewHolder> {

    public ClassificationRightItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.classify_item_of_righit_list;
    }

    ClassifyItemOfRighitListBinding binding;

    @Override
    public ViewHolder createViewHolder(View view) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), getLayoutResId(), null, false);
        return new ViewHolder(binding.getRoot());
    }

    GridLayoutManager layout;

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final ClassificationRighitBean.DatasBean.ClassListBean dataBean) {
        binding = DataBindingUtil.getBinding(holder.itemView);
        binding.lsiItem.setLeftText(dataBean.getGc_name());
        binding.lsiItem.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                KLog.i(dataBean.getGc_name() + " " + dataBean.getGc_id());
            }
        });
        DelegateAdapter adapter = AdapterPool.newInstance().getRightAdapter1(mContext)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int postion, Object object) {
                        ClassificationRighitBean.DatasBean.ClassListBean.ChildBean data =
                                dataBean.getChild().get(postion);
                        KLog.i(data.getGc_name() + " " + data.getGc_id());
                        ActivityToActivity.toActivity(ARouterConfig.classify.PRODUCTSACTIVITY, "id", String.valueOf(data.getGc_id()));
                    }
                }).build();
        adapter.setDatas(dataBean.getChild());
        layout = new GridLayoutManager(mContext, 3);
        binding.rvRight.setLayoutManager(new GridLayoutManager(mContext, 3));
        binding.rvRight.setAdapter(adapter);
    }

    static class ViewHolder extends AbsHolder {
        private ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
