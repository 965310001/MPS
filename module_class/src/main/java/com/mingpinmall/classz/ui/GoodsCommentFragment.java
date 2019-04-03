package com.mingpinmall.classz.ui;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.view.View;

import com.goldze.common.dmvvm.base.mvvm.base.BaseListFragment;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.constants.Constants;
import com.mingpinmall.classz.ui.vm.ClassifyViewModel;
import com.mingpinmall.classz.ui.vm.bean.GoodsCommentListBean;
import com.socks.library.KLog;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.listener.OnItemClickListener;

import android.support.annotation.Nullable;

/**
 * 商品详情 -商品评价
 */
public class GoodsCommentFragment extends BaseListFragment<ClassifyViewModel> implements OnItemClickListener {

//    TextView tvCommentCount;
//    TextView tvPraiseRate;
//    TextView tvEmptyComment;
//    RecyclerView recycleView;
//    ImageView ivRight;
//    private final List<GoodsComment> commentList = new ArrayList<>();
//    private GoodsCommentAdapter adapter;

    String type="";

    public GoodsCommentFragment() {
    }

    public static GoodsCommentFragment newInstance(String id) {
        GoodsCommentFragment fragment = new GoodsCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

//    public static GoodsCommentFragment newInstance(List<GoodsComment> data) {
//        GoodsCommentFragment fragment = new GoodsCommentFragment();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("data", (Serializable) data);
//        fragment.setArguments(bundle);
//        return fragment;
//    }
//    @Override
//    protected int getLayoutResId() {
//        return R.layout.fragment_goods_comment;
//    }
//
//    @Override
//    protected int getContentResId() {
//        return R.id.content_layout;
//    }
//    @Override
//    public void initView(Bundle state) {
//        super.initView(state);
//
////        tvCommentCount = binding.tvCommentCount;
////        tvPraiseRate = binding.tvPraiseRate;
////        tvEmptyComment = binding.tvEmptyComment;
////        recycleView = binding.recycleView;
////        ivRight = binding.ivRight;
////        showSuccess();
////        adapter = new GoodsCommentAdapter(getContext(), commentList);
////        recycleView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
////        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
////        recycleView.setAdapter(adapter);
////
////        ivRight.setVisibility(View.GONE);
////        tvEmptyComment.setVisibility(View.GONE);
////        recycleView.setVisibility(View.VISIBLE);
////        tvCommentCount.setText("用户点评");
////        tvPraiseRate.setText("好评率97.8%");
////        commentList(ApiData.getGoodsCommentList());
////        commentList((List<GoodsComment>) getArguments().getSerializable("data"));
//
//    }

    @Override
    protected void getRemoteData() {
        super.getRemoteData();
        KLog.i("EVALUATE_EVENT_KEY");
        mViewModel.getEvaluate(getArguments().getString("id"), type, String.valueOf(page));
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        /*评价列表*/
        registerObserver(Constants.EVALUATE_EVENT_KEY[0], GoodsCommentListBean.class)
                .observeForever(new Observer<GoodsCommentListBean>() {
                    @Override
                    public void onChanged(@Nullable GoodsCommentListBean response) {
                        KLog.i("EVALUATE_EVENT_KEY");
                        setData(response.getDatas().getGoods_eval_list());
                    }
                });
    }

    @Override
    protected DelegateAdapter createAdapter() {
        return AdapterPool.newInstance()
                .getEvaluate(getActivity())
                .setOnItemClickListener(this)
                .build();
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.EVALUATE_EVENT_KEY[1];
    }

    @Override
    public void onItemClick(View view, int i, Object o) {

    }
}

