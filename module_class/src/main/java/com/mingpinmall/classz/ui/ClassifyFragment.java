package com.mingpinmall.classz.ui;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.databinding.FragmentClassifyBinding;
import com.mingpinmall.classz.ui.vm.ClassifyViewModel;
import com.mingpinmall.classz.ui.vm.bean.ClassificationBean;
import com.trecyclerview.TRecyclerView;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.adapter.ItemData;

/**
 * 分类
 */
public class ClassifyFragment extends AbsLifecycleFragment<FragmentClassifyBinding, ClassifyViewModel> {

    TRecyclerView rvLeftRecyclerView, rvRightRecyclerView;
    private DelegateAdapter leftAdapter, rightAdapter;

    private final ItemData leftData = new ItemData();

    private final ItemData rightData = new ItemData();

    public ClassifyFragment() {
    }

    public static ClassifyFragment newInstance() {
        return new ClassifyFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_classify;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }


    @Override
    public void initView(Bundle state) {
        super.initView(state);
        leftAdapter = AdapterPool.newInstance().getLeftAdapter(getActivity())
//                .setOnItemClickListener(this)
                .build();

//        rightAdapter = AdapterPool.newInstance().getRightAdapter(getActivity())
//                .setOnItemClickListener(this)
//                .build();

        leftAdapter.setDatas(leftData);
//        rightAdapter.setDatas(rightData);

        rvLeftRecyclerView = binding.rvLeft;
        rvRightRecyclerView = binding.rvRight;

        rvLeftRecyclerView.setAdapter(leftAdapter);
//        rvRightRecyclerView.setAdapter(rightAdapter);

        rvLeftRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvRightRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mViewModel.getLeft();
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();

        LiveBus.getDefault()
                .subscribe(Constants.EVENT_KEY_CLASSIFY_MORE, ClassificationBean.class)
                .observeForever(new Observer<ClassificationBean>() {
                    @Override
                    public void onChanged(@Nullable ClassificationBean response) {
                        leftAdapter.setDatas(response.getDatas().getClass_list());

                        mViewModel.getRight(response.getDatas().getClass_list().get(0).getGc_id());
                    }
                });
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.EVENT_KEY_CLASSIFY_MORE;
    }

}
