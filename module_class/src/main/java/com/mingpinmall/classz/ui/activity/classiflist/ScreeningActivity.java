package com.mingpinmall.classz.ui.activity.classiflist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseViewHolder;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 小斌
 * @data 2019/5/24
 **/
@Route(path = ARouterConfig.classify.SCREENINGACTIVITY)
public class ScreeningActivity extends AbsLifecycleActivity<ActivityScreeningBinding, ClassifyViewModel> {

    ScreenInfo screenInfo;

    private boolean isNone = false;

    /**
     * 包含二级分类和其他隐藏的数据
     */
    private ScreeningBean mainData;
    /**
     * 包含二级分类和其他隐藏的数据
     */
    private ScreeningClassBean class2Data;
    /**
     * 仅三级分类的数据
     */
    private ScreeningClassBean class3Data;
    /**
     * 适配器 适用人群 风格 材质
     */
    private ScreeningAdapter screeningAdapter;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        screenInfo = (ScreenInfo) getIntent().getSerializableExtra("screenInfo");
        if (screenInfo == null) {
            screenInfo = new ScreenInfo();
        }
        setTitle("商品筛选");
        findViewById(R.id.tv_right).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.tv_right)).setText("重置");
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        initAdapter();
        setListener();
    }

    protected void initAdapter() {
        /*类型*/
        screeningAdapter = new ScreeningAdapter();
        binding.recyclerView.setAdapter(screeningAdapter);

        binding.slGoodType.setAdapter(new StackLabelAdapter<ScreeningBean.GoodsTypeBean>((data, position) -> data.getName()));
        binding.slShopType.setAdapter(new StackLabelAdapter<ScreeningBean.StoreTypeBean>((data, position) -> data.getName()));
        binding.slShopServer.setAdapter(new StackLabelAdapter<ScreeningBean.ContractListBean>((data, position) -> data.getName()));
        binding.slClassOne.setAdapter(new StackLabelAdapter<ScreeningBean.GcListBean>((data, position) -> data.getGc_name()));
        for (StackLabel stackLabel : Arrays.asList(binding.slClassSecond, binding.slClassThree)) {
            stackLabel.setAdapter(new StackLabelAdapter<ScreeningClassBean.GcListChildBean>((data, position) -> data.getGc_name()));
        }
    }

    protected void setListener() {
        binding.slClassOne.setOnLabelClickListener((index, v, s) -> {
            clearLabels(2);
            if (synchroLock(binding.slClassOne.getSelectedIndexs().size() > 0)) {
                mViewModel.getScreeningClassInfo(Constants.SCREENING_KEY[1], mainData.getGc_list().get(index).getGc_id());
            } else {
                if (isNone) {
                    isNone = false;
                    binding.slClassOne.setData(mainData.getGc_list());
                }
                clearLabels(1);
            }
        });

        binding.slClassSecond.setOnLabelClickListener((index, v, s) -> {
            clearLabels(3);
            if (synchroLock(binding.slClassSecond.getSelectedIndexs().size() > 0)) {
                mViewModel.getScreeningClassInfo(Constants.SCREENING_KEY[2], class2Data.getGc_list_child().get(index).getGc_id());
            } else {
                binding.slClassSecond.setData(class2Data.getGc_list_child());
            }
        });

//        binding.slClassThree.setOnLabelClickListener((index, v, s) -> {
//        });
        binding.btnOk.setOnClickListener(v -> {
            click2Result();
        });
        findViewById(R.id.tv_right).setOnClickListener(v -> clearLabels(0));
    }

    /**
     * 保存筛选信息返回
     */
    private void click2Result() {
        ScreenInfo screenInfo = new ScreenInfo();
        //价格区间
        screenInfo.priceFrom = binding.etPriceFrom.getText().toString().trim();
        screenInfo.priceTo = binding.etPriceTo.getText().toString().trim();
        /*
         * gift: 赠品（1表示选中）
         * groupbuy: 团购（1表示选中）
         * xianshi: 限时折扣（1表示选中）
         * virtual:虚拟（1表示选中）
         **/
        for (Integer index : binding.slGoodType.getSelectIndexArray()) {
            switch (mainData.getGoods_type().get(index).getId()) {
                case "gift":
                    screenInfo.gift = "1";
                    break;
                case "groupbuy":
                    screenInfo.groupbuy = "1";
                    break;
                case "xianshi":
                    screenInfo.xianshi = "1";
                    break;
                case "virtual":
                    screenInfo.virtual = "1";
                    break;
                default:
                    break;
            }
        }
        //店铺类型
        for (Integer index : binding.slShopType.getSelectIndexArray()) {
            if ("own_mall".equals(mainData.getStore_type().get(index).getId())) {
                screenInfo.own_mall = "1";
            }
        }
        //店铺服务
        for (Integer index : binding.slShopServer.getSelectIndexArray()) {
            screenInfo.ci = screenInfo.ci +
                    mainData.getContract_list().get(index).getId() + "_";
        }
        //一级分类
        for (Integer index : binding.slClassOne.getSelectIndexArray()) {
            screenInfo.gc_id_1 = mainData.getGc_list().get(index).getGc_id();
        }
        //二级分类
        for (Integer index : binding.slClassSecond.getSelectIndexArray()) {
            screenInfo.gc_id_2 = class2Data.getGc_list_child().get(index).getGc_id();
        }
        //三级分类  多选
        for (Integer index : binding.slClassThree.getSelectIndexArray()) {
            screenInfo.gc_id_3 = screenInfo.gc_id_3 +
                    class3Data.getGc_list_child().get(index).getGc_id() + "_";
        }
        //动态属性
        List<ScreeningClassBean.GoodsAttrListBean> data = screeningAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            ScreeningClassBean.GoodsAttrListBean item = data.get(i);
            BaseViewHolder helper = (BaseViewHolder) binding.recyclerView.findViewHolderForAdapterPosition(i);
            if (null!=helper) {
                StackLabel stackLabel = helper.getView(R.id.stl_view);
                for (Integer index : stackLabel.getSelectIndexArray()) {
                    screenInfo.attrs = screenInfo.attrs +
                            item.getValue().get(index).getAttr_value_id() + "_";
                }
            }
        }
        int areaIndex = binding.spinnerSystem.getSelectedItemPosition() - 1;
        screenInfo.areaId = areaIndex < 0 ? "" : mainData.getArea_list().get(areaIndex).getArea_id();
        Intent intent = new Intent();
        intent.putExtra("datas", screenInfo);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 重置条件，清除数据。
     *
     * @param i 3清除三级分类  2清除二级分类和三级分类  1重置一级分类为未选择，清除二级和三级分类  0重置所有
     */
    private void clearLabels(int i) {
        if (i <= 3) {
            binding.slClassThree.setData(new ArrayList<>());
            binding.llThree.setVisibility(View.GONE);
            if (i <= 2) {
                binding.slClassSecond.setData(new ArrayList<>());
                binding.llSecond.setVisibility(View.GONE);
                if (i <= 1) {
                    screeningAdapter.setNewData(new ArrayList<>());
                    if (i <= 0) {
                        for (EditText editText : Arrays.asList(binding.etPriceFrom, binding.etPriceTo)) {
                            editText.setText("");
                        }
                        for (StackLabel stackLabel : Arrays.asList(binding.slClassOne, binding.slGoodType, binding.slShopType, binding.slShopServer)) {
                            stackLabel.notifyDataSetChanged();
                        }
                        binding.spinnerSystem.setSelection(0);
                    }
                }
            }
        }
    }

    /**
     * 请求网络时，禁止点击
     *
     * @param lock
     */
    private boolean synchroLock(boolean lock) {
        binding.slClassOne.setEnabled(!lock);
        binding.slClassSecond.setEnabled(!lock);
        binding.slClassThree.setEnabled(!lock);
        return lock;
    }

    @Override
    protected void initData() {
        super.initData();
        mViewModel.getScreeningInfo(Constants.SCREENING_KEY[0]);
    }

    /**
     * 初始化通用数据
     */
    private void formatMainDatas() {
        List<String> areaList = new ArrayList<>();
        areaList.add("不限");
        //开始~
        int areaIndex = 0;
        ScreeningBean.AreaListBean areaListBean;
        for (int i = 0; i < mainData.getArea_list().size(); i++) {
            areaListBean = mainData.getArea_list().get(i);
            areaList.add(areaListBean.getArea_name());
            if (!screenInfo.areaId.isEmpty() && areaListBean.getArea_id().equals(screenInfo.areaId)) {
                areaIndex = i + 1;
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
                R.layout.item_text1,
                R.id.text, areaList
        );
        binding.spinnerSystem.setAdapter(adapter);
        //地区已选择
        binding.spinnerSystem.setSelection(areaIndex);

        //如果已填写价格区间
        if (!"".equals(screenInfo.priceFrom)) {
            binding.etPriceFrom.setText(screenInfo.priceFrom);
        }
        if (!"".equals(screenInfo.priceTo)) {
            binding.etPriceTo.setText(screenInfo.priceTo);
        }

        binding.slGoodType.setData(mainData.getGoods_type());
        //商品类型已选择
        List<Integer> goodTypeSelected = new ArrayList<>();
        for (int i = 0; i < mainData.getGoods_type().size(); i++) {
            ScreeningBean.GoodsTypeBean goodsTypeBean = mainData.getGoods_type().get(i);
            switch (goodsTypeBean.getId()) {
                case "gift":
                    if ("1".equals(screenInfo.gift)) {
                        goodTypeSelected.add(i);
                    }
                    break;
                case "groupbuy":
                    if ("1".equals(screenInfo.groupbuy)) {
                        goodTypeSelected.add(i);
                    }
                    break;
                case "xianshi":
                    if ("1".equals(screenInfo.xianshi)) {
                        goodTypeSelected.add(i);
                    }
                    break;
                case "virtual":
                    if ("1".equals(screenInfo.virtual)) {
                        goodTypeSelected.add(i);
                    }
                    break;
                default:
                    break;
            }
        }
        binding.slGoodType.setSelect(goodTypeSelected);

        binding.slShopType.setData(mainData.getStore_type());
        //店铺类型已选择
        if (!screenInfo.own_mall.isEmpty()) {
            List<Integer> shopTypeSelected = new ArrayList<>();
            ScreeningBean.StoreTypeBean storeTypeBean;
            for (int i = 0; i < mainData.getStore_type().size(); i++) {
                storeTypeBean = mainData.getStore_type().get(i);
                if ("own_mall".equals(storeTypeBean.getId()) && "1".equals(screenInfo.own_mall)) {
                    shopTypeSelected.add(i);
                }
            }
            binding.slShopType.setSelect(shopTypeSelected);
        }

        binding.slShopServer.setData(mainData.getContract_list());
        //店铺服务已选择
        if (!screenInfo.ci.isEmpty()) {
            String[] cis = new String[]{screenInfo.ci};
            if (screenInfo.ci.contains("_")) {
                cis = screenInfo.ci.split("_");
            }
            List<Integer> shopServerSelected = new ArrayList<>();
            ScreeningBean.ContractListBean storeTypeBean;
            for (int i = 0; i < mainData.getContract_list().size(); i++) {
                storeTypeBean = mainData.getContract_list().get(i);
                for (String ciStr : cis) {
                    if (ciStr.equals(storeTypeBean.getId())) {
                        shopServerSelected.add(i);
                    }
                }
            }
            binding.slShopServer.setSelect(shopServerSelected);
        }

        //一级分类，二级分类等已选择
        if (screenInfo != null && screenInfo.getType() == 2) {
            isNone = true;
            List<ScreeningBean.GcListBean> datas = new ArrayList<>();
            for (ScreeningBean.GcListBean item : mainData.getGc_list()) {
                if (item.getGc_id().equals(screenInfo.getMainId())) {
                    datas.add(item);
                    break;
                }
            }
            binding.slClassOne.setData(datas);
            binding.slClassOne.setSelect(0);

            binding.llSecond.setVisibility(View.VISIBLE);
            List<ScreeningClassBean.GcListChildBean> datas2 = new ArrayList<>();
            ScreeningClassBean.GcListChildBean none2 = new ScreeningClassBean.GcListChildBean();
            none2.setGc_name(screenInfo.getSecondName());
            none2.setGc_id(screenInfo.getSecondId());
            datas2.add(none2);
            binding.slClassSecond.setData(datas2);
            binding.slClassSecond.setSelect(0);

            mViewModel.getScreeningClassInfo(Constants.SCREENING_KEY[1], screenInfo.getMainId());
            mViewModel.getScreeningClassInfo(Constants.SCREENING_KEY[2], screenInfo.getSecondId());
        } else {
            binding.slClassOne.setData(mainData.getGc_list());
        }
    }

    /**
     * 加载二级，三级分类 数据
     *
     * @param data
     */
    private void formatClassDatas(List<ScreeningClassBean.GoodsAttrListBean> data) {
        if (data.size() > 0) {
            for (ScreeningClassBean.GoodsAttrListBean item : data) {
                if (item.getValue().size() > 0) {
                    screeningAdapter.addData(item);
                }
            }
        }
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.SCREENING_KEY[0], Object.class).observeForever(result -> {
            synchroLock(false);
            if (result instanceof ScreeningBean) {
                //成功获取筛选信息
                mainData = (ScreeningBean) result;
                formatMainDatas();
            } else {
                ToastUtils.showShort("获取失败");
            }
        });
        registerObserver(Constants.SCREENING_KEY[1], Object.class).observeForever(result -> {
            synchroLock(false);
            if (result instanceof ScreeningClassBean) {
                //成功获取 选择一级分类 后的数据 ：获取二级分类列表以及 适用人群、风格、材质列表
                class2Data = (ScreeningClassBean) result;
                if (class2Data.getGoods_attr_list() != null) {
                    formatClassDatas(class2Data.getGoods_attr_list());
                }
                if (isNone) {
                    return;
                }
                binding.slClassSecond.setData(class2Data.getGc_list_child());
                binding.llSecond.setVisibility(binding.slClassSecond.getAdapter().getItemCount() > 0 ? View.VISIBLE : View.GONE);
            } else {
                ToastUtils.showShort("获取失败");
            }
        });
        registerObserver(Constants.SCREENING_KEY[2], Object.class).observeForever(result -> {
            synchroLock(false);
            if (result instanceof ScreeningClassBean) {
                //成功获取 选择二级分类 后的数据 ：获取三级分类列表
                class3Data = (ScreeningClassBean) result;
                binding.slClassThree.setData(class3Data.getGc_list_child());
                binding.llThree.setVisibility(binding.slClassThree.getAdapter().getItemCount() > 0 ? View.VISIBLE : View.GONE);
                formatClassDatas(class3Data.getGoods_attr_list());
            } else {
                ToastUtils.showShort("获取失败");
            }
        });
        registerObserver(Constants.SCREENING_KEY[3], Object.class).observeForever(result -> {
            synchroLock(false);
            if (result instanceof ScreeningClassBean) {
                //三级分类点击后 的数据
                screeningAdapter.notifyDataSetChanged();
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
