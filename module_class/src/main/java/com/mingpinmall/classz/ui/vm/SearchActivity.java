package com.mingpinmall.classz.ui.vm;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.constants.Constants;
import com.mingpinmall.classz.databinding.ActivitySearchBinding;
import com.mingpinmall.classz.ui.vm.bean.HotKeyInfo;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索
 */
@Route(path = ARouterConfig.home.SEARCHACTIVITY)
public class SearchActivity extends AbsLifecycleActivity<ActivitySearchBinding,
        ClassifyViewModel> {

    private List<String> items = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        edSearch.setVisibility(View.VISIBLE);
        ivSearch.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.GONE);
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
            }
        });
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
        SharePreferenceUtil.saveKeyValue("KEYWORD", content);
        ActivityToActivity.toActivity(ARouterConfig.classify.PRODUCTSACTIVITY, "keyword", content);
        finish();
        saveSearchKey(content);
    }

    private void saveSearchKey(String searchKey) {
        boolean exit = false;
        for (String item : items) {
            if (item.equals(searchKey)) {
                exit = true;
                break;
            }
        }
        if (exit) {
            items.remove(searchKey);
        }
        items.add(0, searchKey);
//        adapter.notifyDataSetChanged();
        SharePreferenceUtil.saveSearchList(items);
    }
}
