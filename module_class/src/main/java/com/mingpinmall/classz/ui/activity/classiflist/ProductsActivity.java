package com.mingpinmall.classz.ui.activity.classiflist;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.goldze.common.dmvvm.base.mvvm.base.BaseRecyclerActivity;
import com.goldze.common.dmvvm.base.mvvm.stateview.EmptyState;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.databinding.ItemTabsegmentBinding;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.ClassGoodsBean;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsListInfo;
import com.mingpinmall.classz.ui.vm.bean.ScreenInfo;
import com.mingpinmall.classz.widget.CustomPopWindow;
import com.mingpinmall.classz.widget.FilterTab;
import com.trecyclerview.adapter.DelegateAdapter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * 商品分类list
 */
@Route(path = ARouterConfig.classify.PRODUCTSACTIVITY)
public class ProductsActivity extends BaseRecyclerActivity<ClassifyViewModel>
        implements CustomPopWindow.Builder.OnCustomPopWindowClickListener {
    @Autowired
    String gcId = "";
    @Autowired
    String gcName = "";

    private ScreenInfo screenInfo;

    /**
     * 0：默认搜索
     * 1：特殊搜索
     * 2：二级搜索
     */
    @Autowired
    int type = 0;

    @Autowired
    String keyword = "";

    private View currentView;
    private FilterTab filterTab0;
    private PopupWindow customPopWindow;
    private ImageView imageView;
    private String picPath = "";

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        keyword = intent.getStringExtra("keyword");
        Log.i(TAG, "onNewIntent: " + keyword);
        screenInfo.keyword = keyword;
        edSearch.setText("".equals(keyword) ? "请输入搜索内容" : keyword);
        onRefresh();
    }

    @Override
    protected boolean isItemDecoration() {
        return false;
    }

    @Override
    protected AdapterType getDefaultAdapterType() {
        return AdapterType.Grid;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        screenInfo = new ScreenInfo();
        ARouter.getInstance().inject(this);
        screenInfo.keyword = keyword == null ? "" : keyword;
        screenInfo.setType(type);
        screenInfo.setId(gcId);
        if (type == 2) {
            screenInfo.setSecondName(gcName);
        }
        super.initViews(savedInstanceState);
        clSearch.setVisibility(View.VISIBLE);
        edSearch.setFocusable(false);
        edSearch.setFocusableInTouchMode(false);
        edSearch.setInputType(InputType.TYPE_NULL);
        edSearch.setCursorVisible(false);
        edSearch.setTextIsSelectable(false);
        edSearch.setOnClickListener(this);
        edSearch.setText("".equals(keyword) ? "请输入搜索内容" : keyword);
        ivSearch.setVisibility(View.GONE);
        tvTitle.setVisibility(View.GONE);

        ItemTabsegmentBinding itemTabsegmentBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_tabsegment, null, false);
        binding.llRecyclerview.addView(itemTabsegmentBinding.getRoot(), 0);
        //分割线
        View view = new View(activity);
        view.setBackgroundResource(R.color.line_color);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.px2dip(activity, 1));
        binding.llRecyclerview.addView(view, 1, layoutParams);

        filterTab0 = itemTabsegmentBinding.filterTab0;
        imageView = itemTabsegmentBinding.filterTab4;
        imageView.setOnClickListener(this);
        imageView.setImageResource(getAdapterType() != AdapterType.Grid ? R.drawable.icon_list_32 : R.drawable.icon_grid_32);

        filterTab0.setFilterTabSelected(true);
        currentView = filterTab0;

        for (FilterTab tab :
                Arrays.asList(itemTabsegmentBinding.filterTab0,
                        itemTabsegmentBinding.filterTab1,
                        itemTabsegmentBinding.filterTab2,
                        itemTabsegmentBinding.filterTab3)) {
            tab.setOnClickListener(this);
        }

        if (type == 2) {
            //子分类进来的
            mViewModel.getGcParentId(gcId);
        }
    }

    @Override
    protected void onRefreshSuccess(Collection<?> collection) {
        oldItems.clear();
        if (!picPath.isEmpty()) {
            oldItems.add(picPath);
        }
        oldItems.addAll(collection);
        mRecyclerView.refreshComplete(oldItems, !isLoadMore);
        isRefresh = false;
    }

    @Override
    protected DelegateAdapter createLinearAdapter() {
        return AdapterPool.newInstance()
                .getProductsAdapter(this)
                .build();
    }

    @Override
    protected DelegateAdapter createGridAdapter() {
        return AdapterPool.newInstance()
                .getProductsGridAdapter(this)
                .build();
    }

    @Override
    protected DelegateAdapter createStaggerAdapter() {
        return null;
    }

    @Override
    protected GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
        return new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if (gridAdapter.getItems().get(i) instanceof GoodsInfo) {
                    return 1;
                }
                return 2;
            }
        };
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.PRODUCTS_EVENT_KEY[0], GoodsListInfo.class)
                .observe(this, response -> {
                    picPath = response.getDatas().getBrand_bgpic();
                    setData(response.getDatas().getGoods_list());
                    if (response.getDatas().getGoods_list().size() == 0) {
                        showError(EmptyState.class);
                    }
                });
        registerObserver(Constants.PRODUCTS_EVENT_KEY[2], Object.class)
                .observe(this, response -> {
                    if (response instanceof ClassGoodsBean) {
                        ClassGoodsBean data = (ClassGoodsBean) response;
                        screenInfo.setMain_id(data.getGc_parent_id());
                        screenInfo.setMainName(data.getGc_name());
                    } else {
                        ToastUtils.showShort(response.toString());
                    }
                });
    }

    /**
     * 获取更多数据
     */
    @Override
    protected void getRemoteData() {
        super.getRemoteData();
        Map<String, Object> params = screenInfo.getParams();
        mViewModel.getShappingList(params, page);
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.PRODUCTS_EVENT_KEY[1];
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (v instanceof FilterTab) {
            if (v != currentView) {
                ((FilterTab) v).setFilterTabSelected(!v.isSelected());
                currentView = v;
            }
            if (i == R.id.filter_tab0) {
                if (null == customPopWindow) {
                    customPopWindow =
                            new CustomPopWindow.Builder(ProductsActivity.this)
                                    .setDataSource(Arrays.asList("综合排序", "价格从高到低", "价格从低到高", "人气排序"))
                                    .setListener(this)
                                    .setColorBg(R.color.white).build().createPop();
                }
                customPopWindow.showAsDropDown(filterTab0);
            } else if (i == R.id.filter_tab1) {
                screenInfo.setOrderKey("1", "2");
                onRefresh();
            } else if (i == R.id.filter_tab3) {
                Intent intent = new Intent();
                intent.putExtra("screenInfo", screenInfo);
                intent.setClass(activity, ScreeningActivity.class);
                startActivityForResult(intent, 1);
            }
        } else if (i == R.id.ed_search) {
            ActivityToActivity.toActivity(ARouterConfig.home.SEARCHACTIVITY);
        } else if (i == R.id.filter_tab4) {
            changerManager(getAdapterType() != AdapterType.Grid ? AdapterType.Grid : AdapterType.Linear);
            imageView.setImageResource(getAdapterType() != AdapterType.Grid ? R.drawable.icon_list_32 : R.drawable.icon_grid_32);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //筛选
                ScreenInfo newDatas = (ScreenInfo) data.getSerializableExtra("datas");
                screenInfo.cloneScreeningDatas(newDatas);
                onRefresh();
            }
        }
    }

    @Override
    public void onClick(PopupWindow dialog, View itemView, int position, String content) {
        dialog.dismiss();
        filterTab0.setText(content);
        switch (position) {
            case 0:
                /*综合排序*/
                screenInfo.setOrderKey("", "");
                break;
            case 1:
                /*价格从高到低*/
                screenInfo.setOrderKey("2", "3");
                break;
            case 2:
                /*价格从低到高*/
                screenInfo.setOrderKey("1", "3");
                break;
            case 3:
                /*人气排序*/
                screenInfo.setOrderKey("2", "2");
                break;
            default:
                break;
        }
        onRefresh();
    }
}