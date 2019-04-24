package com.mingpinmall.classz.ui.vm;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ColorUtil;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.adapter.SearchHistoryAdapter;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.databinding.ActivitySearchBinding;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.vm.bean.HotKeyInfo;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 搜索
 */
@Route(path = ARouterConfig.home.SEARCHACTIVITY)
public class SearchActivity extends AbsLifecycleActivity<ActivitySearchBinding,
        ClassifyViewModel> {

    private List<String> items = new ArrayList<>();

    private SearchHistoryAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        edSearch.setVisibility(View.VISIBLE);
        ivSearch.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.GONE);

        binding.recyclerSearchHistory.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchHistoryAdapter(this, items);
        binding.recyclerSearchHistory.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mViewModel.getHotKeys();
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.SEARCH_EVENT_KEY[0], BaseResponse.class).observe(this, new Observer<BaseResponse>() {
            @Override
            public void onChanged(@Nullable BaseResponse baseResponse) {
                BaseResponse<HotKeyInfo> response = baseResponse;
                if (response.isData()) {
                    HotKeyInfo data = response.getData();
                    if (data.getList() != null) {
                        addHotKey(response.getData().getList());
                    }
                    if (data.getHis_list() != null && data.getHis_list().size() > 0) {
                        items.addAll(data.getHis_list());
                        adapter.notifyDataSetChanged();
                    } else {
                        loadSearchHistory();
                    }
                } else {
                    ToastUtils.showLong(response.getMessage());
                }
            }
        });

        /*点击搜索*/
        registerObserver(Constants.SEARCH_EVENT_KEY[2], String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String content) {
                        edSearch.setText(content);
                        search();
                    }
                });

        /*删除*/
        registerObserver(Constants.SEARCH_EVENT_KEY[3], Integer.class)
                .observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer position) {
                        items.remove((int) position);
                        adapter.notifyDataSetChanged();
                        SharePreferenceUtil.saveSearchList(items);
                    }
                });
    }

    private void loadSearchHistory() {
        items.clear();
        items.addAll(SharePreferenceUtil.getSearchList());
        adapter.notifyDataSetChanged();
    }

    private void addHotKey(List<String> data) {
        Random random = new Random();
        View child;
        TextView textView;
        for (final String entity : data) {
            child = View.inflate(this, R.layout.layout_tag_navi, null);
            textView = child.findViewById(R.id.tv_tag);
            textView.setText(entity);
            textView.setTextColor(ColorUtil.getRandomColor(random));
//            textView.setBackgroundColor(getResources().getColor(R.color.white));
            textView.setBackgroundResource(R.drawable.item_lable_bg_shape_radius);
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edSearch.setText(entity);
                    search();
                }
            });
            binding.layoutFlow.addView(child);
        }
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.SEARCH_EVENT_KEY[1];
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.iv_search || i == R.id.ed_search) {
            KLog.d("搜索");
            search();
        }
    }

    private void search() {
        String content = edSearch.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showLong("搜索内容不能为空");
            return;
        }
        saveSearchKey(content);
        SharePreferenceUtil.saveKeyValue("KEYWORD", content);
        ActivityToActivity.toActivity(ARouterConfig.classify.PRODUCTSACTIVITY, "keyword", content);
//        finish();
    }

    private void saveSearchKey(String searchKey) {
        /***********/
//        items.add(0, searchKey);
//        HashSet<String> hashSet = new HashSet(items);
//        items.clear();
//        items.addAll(hashSet);
//        Collections.reverse(items);
        /***********/
        for (String item : items) {
            if (item.equals(searchKey)) {
                items.remove(searchKey);
                break;
            }
        }
        items.add(0, searchKey);
        adapter.notifyDataSetChanged();
        SharePreferenceUtil.saveSearchList(items);
    }
}