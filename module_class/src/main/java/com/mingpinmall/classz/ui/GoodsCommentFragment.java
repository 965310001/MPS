package com.mingpinmall.classz.ui;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.adapter.GoodsCommentAdapter;
import com.mingpinmall.classz.databinding.FragmentGoodsCommentBinding;
import com.mingpinmall.classz.ui.vm.ClassifyViewModel;
import com.mingpinmall.classz.ui.vm.bean.GoodsComment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情 -商品评价
 */
public class GoodsCommentFragment extends AbsLifecycleFragment<FragmentGoodsCommentBinding, ClassifyViewModel> {

    TextView tvCommentCount;
    TextView tvPraiseRate;
    TextView tvEmptyComment;
    RecyclerView recycleView;
    ImageView ivRight;

    private final List<GoodsComment> commentList = new ArrayList<>();
    private GoodsCommentAdapter adapter;

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

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_goods_comment;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {

        tvCommentCount = binding.tvCommentCount;
        tvPraiseRate = binding.tvPraiseRate;
        tvEmptyComment = binding.tvEmptyComment;
        recycleView = binding.recycleView;
        ivRight = binding.ivRight;

        showSuccess();

        adapter = new GoodsCommentAdapter(getContext(), commentList);
        recycleView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleView.setAdapter(adapter);

        ivRight.setVisibility(View.GONE);
        tvEmptyComment.setVisibility(View.GONE);
        recycleView.setVisibility(View.VISIBLE);
        tvCommentCount.setText("用户点评");
//        tvPraiseRate.setText("好评率97.8%");

        /*commentList(ApiData.getGoodsCommentList());*/


//        commentList((List<GoodsComment>) getArguments().getSerializable("data"));
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
//        registerObserver("",Bep)
    }

    @Override
    protected Object getStateEventKey() {
        return "";
    }

//    private void getReviewList() {
////        commentList(ApiData.getGoodsCommentList());
////        ApiRepo.getReviewList(getArguments().getString("id"), 1).subscribeWith(new RxSubscriber<ReviewListInfo>() {
////
////            @Override
////            public void onSuccess(ReviewListInfo response) {
//////                KLog.i(response.getErrorMsg() + response.getError_desc());
////////                if (!response.isSuccess()) {
////////                    ToastUtils.showLong(response.getErrorMsg());
////////                } else {
////////                }
////                commentList(response.getData());
////            }
////
////            @Override
////            public void onFailure(String msg) {
////                KLog.i(msg);
////                ToastUtils.showLong(msg);
////            }
////
////            @Override
////            public void onError(Throwable t) {
////                KLog.i(t.getMessage());
////                ToastUtils.showLong("请稍后再试");
////            }
////        });
//
//    }


    void commentList(List<GoodsComment> commentList) {
        if (null != commentList && commentList.size() > 0) {
//            tvCommentCount.setText(String.format("用户点评(%d)", commentList.size()));
        }
        this.commentList.addAll(commentList);
        adapter.notifyDataSetChanged();
    }
}

