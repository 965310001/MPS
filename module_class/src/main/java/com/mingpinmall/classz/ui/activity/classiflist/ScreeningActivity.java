package com.mingpinmall.classz.ui.activity.classiflist;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.stackLabel.StackLabel;
import com.goldze.common.dmvvm.widget.stackLabel.StackLabelAdapter;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.ActivityScreeningBinding;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.ScreenInfo;
import com.mingpinmall.classz.ui.vm.bean.ScreeningBean;
import com.mingpinmall.classz.ui.vm.bean.ScreeningClassBean;
import com.mingpinmall.classz.utils.AssetsData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 小斌
 * @data 2019/5/24
 **/
@Route(path = ARouterConfig.classify.SCREENINGACTIVITY)
public class ScreeningActivity extends AbsLifecycleActivity<ActivityScreeningBinding, ClassifyViewModel> {

    @Autowired
    ScreenInfo screenInfo;

    /**
     * 包含一级分类和通用
     */
    private ScreeningBean mainData;
    private StackLabelAdapter<ScreeningBean.GcListBean> classDataAdapter;
    private StackLabelAdapter<ScreeningClassBean.GcListChildBean> class2DataAdapter, class3DataAdapter;

    private StackLabelAdapter<ScreeningBean.GoodsTypeBean> goodTypeAdapter;
    private StackLabelAdapter<ScreeningBean.StoreTypeBean> shopTypeAdapter;
    private StackLabelAdapter<ScreeningBean.ContractListBean> shopServerAdapter;
    /**
     * 包含二级分类和其他隐藏的
     */
    private ScreeningClassBean classData;
    /**
     * 仅三级分类
     */
    private ScreeningClassBean class3Data;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTitle("商品筛选");

    }

    private void formatDatas() {
        List<String> areaList = new ArrayList<>();
        for (ScreeningBean.AreaListBean item : mainData.getArea_list()) {
            areaList.add(item.getArea_name());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
                R.layout.item_text1,
                R.id.text, areaList
        );
        binding.spinnerSystem.setAdapter(adapter);
        /*类型*/
        goodTypeAdapter = new StackLabelAdapter<ScreeningBean.GoodsTypeBean>() {
            @Override
            public String covert(ScreeningBean.GoodsTypeBean data, int position) {
                return data.getName();
            }
        };
        shopTypeAdapter = new StackLabelAdapter<ScreeningBean.StoreTypeBean>() {
            @Override
            public String covert(ScreeningBean.StoreTypeBean data, int position) {
                return data.getName();
            }
        };
        shopServerAdapter = new StackLabelAdapter<ScreeningBean.ContractListBean>() {
            @Override
            public String covert(ScreeningBean.ContractListBean data, int position) {
                return data.getName();
            }
        };
        classDataAdapter = new StackLabelAdapter<ScreeningBean.GcListBean>() {
            @Override
            public String covert(ScreeningBean.GcListBean data, int position) {
                return data.getGc_name();
            }
        };
        class2DataAdapter = new StackLabelAdapter<ScreeningClassBean.GcListChildBean>() {
            @Override
            public String covert(ScreeningClassBean.GcListChildBean data, int position) {
                return data.getGc_name();
            }
        };
        class3DataAdapter = new StackLabelAdapter<ScreeningClassBean.GcListChildBean>() {
            @Override
            public String covert(ScreeningClassBean.GcListChildBean data, int position) {
                return data.getGc_name();
            }
        };

        binding.slGoodType.setAdapter(goodTypeAdapter);
        goodTypeAdapter.setLabels(mainData.getGoods_type());

        binding.slShopType.setAdapter(shopTypeAdapter);
        shopTypeAdapter.setLabels(mainData.getStore_type());

        binding.slShopServer.setAdapter(shopServerAdapter);
        shopServerAdapter.setLabels(mainData.getContract_list());

        binding.slSlClassOne.setAdapter(classDataAdapter);
        classDataAdapter.setLabels(mainData.getGc_list());


        /*点击事件*/
        binding.btnReset.setOnClickListener(v -> {
            for (EditText editText : Arrays.asList(binding.etPriceFrom, binding.etPriceTo)) {
                editText.setText("");
            }
            for (StackLabel stackLabel : Arrays.asList(binding.slGoodType, binding.slShopType, binding.slShopServer)) {
                stackLabel.notifyDataSetChanged();
            }
            binding.spinnerSystem.setSelection(0);
        });
        binding.btnOk.setOnClickListener(v -> {
            ScreenInfo screenInfo = new ScreenInfo();
            screenInfo.priceFrom = binding.etPriceFrom.getText().toString().trim();
            screenInfo.priceTo = binding.etPriceTo.getText().toString().trim();
            List<String> goodsType = screenInfo.goodsType = new ArrayList<>();
            goodsType.clear();
            for (Integer index : binding.slGoodType.getSelectedIndexs()) {
                goodsType.add(goodTypeAdapter.getItem(index).getName());
            }
            for (Integer index : binding.slShopType.getSelectedIndexs()) {
                screenInfo.shoppingType = true;
            }
            List<String> shoppingServer = screenInfo.shoppingServer = new ArrayList<>();
            for (Integer index : binding.slShopServer.getSelectedIndexs()) {
                shoppingServer.add(shopServerAdapter.getItem(index).getName());
            }
            screenInfo.areaId = AssetsData.getAreaByName(binding.spinnerSystem.getSelectedItem().toString());
//            if (null != eventKey) {
//                LiveBus.getDefault().postEvent(eventKey, screenInfo);
//            } else if (null != listener) {
//                listener.onClick(mScreeningPopWindow, screenInfo);
//            } else {
//                new NullPointerException("请设置eventKey");
//            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mViewModel.getScreeningInfo(Constants.SCREENING_KEY[0]);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.SCREENING_KEY[0], Object.class).observeForever(result -> {
            if (result instanceof ScreeningBean) {
                //成功获取筛选信息
                mainData = (ScreeningBean) result;
                formatDatas();
            } else {
                ToastUtils.showShort("获取失败");
            }
        });
        registerObserver(Constants.SCREENING_KEY[1], Object.class).observeForever(result -> {
            if (result instanceof ScreeningClassBean) {
                //成功获取 选择一级分类 后的数据 ：获取二级分类列表以及 适用人群、风格、材质列表
                classData = (ScreeningClassBean) result;
            } else {
                ToastUtils.showShort("获取失败");
            }
        });
        registerObserver(Constants.SCREENING_KEY[2], Object.class).observeForever(result -> {
            if (result instanceof ScreeningClassBean) {
                //成功获取 选择二级分类 后的数据 ：获取三级分类列表
                class3Data = (ScreeningClassBean) result;

            } else {
                ToastUtils.showShort("获取失败");
            }
        });
    }

    @Override
    protected Object getStateEventKey() {
        return "ScreeningActivity";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_screening;
    }
}
