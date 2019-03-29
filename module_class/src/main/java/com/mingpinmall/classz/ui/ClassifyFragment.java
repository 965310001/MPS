package com.mingpinmall.classz.ui;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.DataBindViewHolderManager;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.mingpinmall.classz.BR;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.databinding.FragmentClassifyBinding;
import com.mingpinmall.classz.ui.vm.ClassifyViewModel;
import com.mingpinmall.classz.ui.vm.bean.ClassificationBean;
import com.mingpinmall.classz.ui.vm.bean.ClassificationRighitBean;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.trecyclerview.TRecyclerView;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.adapter.ItemData;
import com.trecyclerview.listener.OnItemClickListener;

/**
 * 分类
 */
public class ClassifyFragment extends AbsLifecycleFragment<FragmentClassifyBinding, ClassifyViewModel> implements OnItemClickListener {

    private TRecyclerView rvLeftRecyclerView, rvRightRecyclerView;
    private DelegateAdapter leftAdapter, rightAdapter;

    private final ItemData leftData = new ItemData();

    private final ItemData rightData = new ItemData();

    /***************/
//    private RecyclerView rvLeft, rvRight;
//    private BaseItemAdapter leftBaseAdapter, rightBaseAdapter;

    /***************/


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
                .setOnItemClickListener(this)
                .build();

        rightAdapter = AdapterPool.newInstance().getRightAdapter(getActivity())
                .build();

        leftAdapter.setDatas(leftData);
        rightAdapter.setDatas(rightData);
        rvLeftRecyclerView = binding.trvLeft;
        rvRightRecyclerView = binding.trvRight;

        rvLeftRecyclerView.setAdapter(leftAdapter);
        rvRightRecyclerView.setAdapter(rightAdapter);

        rvLeftRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvLeftRecyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvRightRecyclerView.setLayoutManager(linearLayoutManager);

        /*******************************/
//        rvLeft = binding.rvLeft;
//        rvRight = binding.rvRight;
//        linearLayoutManager = new LinearLayoutManager(getActivity());
//        rvLeft.setLayoutManager(linearLayoutManager);
//        linearLayoutManager = new LinearLayoutManager(getActivity());
//        rvRight.setLayoutManager(linearLayoutManager);
//        rvLeft.addItemDecoration(new DividerItemDecoration(getContext(),
//                DividerItemDecoration.VERTICAL));
//        rvRight.addItemDecoration(new DividerItemDecoration(getContext(),
//                DividerItemDecoration.VERTICAL));
//        leftBaseAdapter = new BaseItemAdapter();
//        rightBaseAdapter = new BaseItemAdapter();
//        rvLeft.setAdapter(leftBaseAdapter);
//        rvRight.setAdapter(rightBaseAdapter);
//
//        leftBaseAdapter.register(ClassificationBean.DatasBean.ClassListBean.class,
//                new DataBindViewHolderManager(R.layout.classify_item_of_list, BR.data));

//        rightBaseAdapter.register(ClassificationRighitBean.DatasBean.ClassListBean.class,
//                new DataBindViewHolderManager(R.layout.item_products, BR.data));

        /*******************************/

        mViewModel.getLeft();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        lazyLoad();
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();

        LiveBus.getDefault()
                .subscribe(Constants.EVENT_KEY_CLASSIFY_MORE, ClassificationBean.class)
                .observeForever(new Observer<ClassificationBean>() {
                    @Override
                    public void onChanged(@Nullable ClassificationBean response) {
                        leftData.clear();
                        leftData.addAll(response.getDatas().getClass_list());
                        leftAdapter.notifyDataSetChanged();
                        data = response.getDatas().getClass_list().get(0);
                        data.setSelect(true);
                        mViewModel.getRight(data.getGc_id());

                        /**************************/
//                        leftBaseAdapter.addDataItems(response.getDatas().getClass_list());
                        /**************************/
                    }
                });

        LiveBus.getDefault()
                .subscribe(Constants.EVENT_KEY_CLASSIFY_MORE_RIGHT, ClassificationRighitBean.class)
                .observeForever(new Observer<ClassificationRighitBean>() {
                    @Override
                    public void onChanged(@Nullable ClassificationRighitBean response) {
                        rightData.clear();
                        rightData.addAll(response.getDatas().getClass_list());
                        rightAdapter.notifyDataSetChanged();
                        /**************************/
                        /**************************/
                    }
                });
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.EVENT_KEY_CLASSIFY_MORE_STATE;
    }

    int leftPostion = 0;
    ClassificationBean.DatasBean.ClassListBean data;

    @Override
    public void onItemClick(View view, int postion, Object object) {
        if (object instanceof ClassificationBean.DatasBean.ClassListBean) {
            if (leftPostion != postion) {
                data.setSelect(false);
                leftAdapter.notifyItemChanged(leftPostion);
                data = (ClassificationBean.DatasBean.ClassListBean) object;

                data.setSelect(true);
                mViewModel.getRight(String.valueOf(leftAdapter.getItemId(postion)));
                leftPostion = postion;
                leftAdapter.notifyItemChanged(leftPostion);
            }
        }
    }
}