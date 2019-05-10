package com.mingpinmall.me.ui.acitivity.address;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.widget.SpacesItemDecoration;
import com.goldze.common.dmvvm.widget.progress.ProgressDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivitySelectCityBinding;
import com.mingpinmall.me.ui.adapter.SelectCityAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.CityBean;
import com.mingpinmall.me.ui.constants.Constants;

import java.util.List;

/**
 * 功能描述：选择地区三级联动
 * @author 小斌
 * @date 2019/4/12
 **/
@Route(path = ARouterConfig.Me.SELECTCITYACTIVITY)
public class SelectCityActivity extends AbsLifecycleActivity<ActivitySelectCityBinding, MeViewModel> {

    private SelectCityAdapter selectCityAdapter;
    private ProgressDialog progressDialog;
    /**
     * 几级地区
     */
    private int level = 1;
    private List<CityBean.AreaListBean> dataOneList;
    private List<CityBean.AreaListBean> dataTwoList;
    /**
     * 一级选择结果
     */
    private String addressOne = "";
    /**
     * 二级选择结果
     */
    private String addressTwo = "";
    /**
     * 二级选择的ID
     */
    private String cityId;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTitle("选择地区");
        progressDialog = ProgressDialog.initNewDialog(getSupportFragmentManager());
        selectCityAdapter = new SelectCityAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        binding.recyclerView.setAdapter(selectCityAdapter);

        selectCityAdapter.setOnItemClickListener((adapter, view, position) -> {
            //item点击事件
            CityBean.AreaListBean bean = selectCityAdapter.getItem(position);
            if (level == 1) {
                addressOne = bean.getArea_name();
            } else if (level == 2) {
                addressTwo = bean.getArea_name();
                cityId = bean.getArea_id();
            } else if (level >= 3) {
                Intent intent = new Intent();
                intent.putExtra("address", addressOne + " " + addressTwo + " " + bean.getArea_name());
                intent.putExtra("cityId", cityId);
                intent.putExtra("areaId", bean.getArea_id());
                setResult(RESULT_OK, intent);
                finish();
                return;
            }
            mViewModel.getCityList(bean.getArea_id());
            level++;
        });
        binding.clOne.setOnClickListener(this);
        binding.clThree.setOnClickListener(this);
        binding.clTwo.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mViewModel.getCityList("0");
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.cl_one) {
            //一级地区
            if (level > 1) {
                level = 1;
                setLevelButton(1);
                if (dataOneList != null) {
                    selectCityAdapter.setNewData(dataOneList);
                } else {
                    mViewModel.getCityList("0");
                }
            }
        } else if (viewId == R.id.cl_two) {
            //二级地区
            if (level > 2) {
                level = 2;
                setLevelButton(2);
                if (dataTwoList != null) {
                    selectCityAdapter.setNewData(dataTwoList);
                } else {
                    level = 1;
                    setLevelButton(1);
                    mViewModel.getCityList("0");
                }
            }
        }
    }

    /**
     * 设置对应等级地区的各种状态
     */
    private void setLevelButton(int level) {
        binding.tvLabelOne.setTextColor(ContextCompat.getColor(this, level == 1 ? R.color.app_theme_d61619 : R.color.dark));
        binding.tvLabelTwo.setTextColor(ContextCompat.getColor(this, level == 2 ? R.color.app_theme_d61619 : R.color.dark));
        binding.tvLabelThree.setTextColor(ContextCompat.getColor(this, level == 3 ? R.color.app_theme_d61619 : R.color.dark));

        binding.lineOne.setBackgroundColor(ContextCompat.getColor(this, level == 1 ? R.color.app_theme_d61619 : R.color.transparent));
        binding.lineTwo.setBackgroundColor(ContextCompat.getColor(this, level == 2 ? R.color.app_theme_d61619 : R.color.transparent));
        binding.lineThree.setBackgroundColor(ContextCompat.getColor(this, level == 3 ? R.color.app_theme_d61619 : R.color.transparent));
    }

    @Override
    protected void dataObserver() {
        registerObserver(Constants.CITY_LIST, Object.class).observeForever(result -> {
            if (result instanceof CityBean) {
                CityBean cityBean = (CityBean) result;
                //获取成功
                if (cityBean.getArea_list().size() == 0) {
                    Intent intent = new Intent();
                    intent.putExtra("address", addressOne + addressTwo);
                    intent.putExtra("cityId", cityId);
                    intent.putExtra("areaId", cityId);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    setLevelButton(level);
                    selectCityAdapter.setNewData(cityBean.getArea_list());
                    if (level == 1 && dataOneList == null) {
                        dataOneList = cityBean.getArea_list();
                    } else if (level == 2 && dataTwoList == null) {
                        dataTwoList = cityBean.getArea_list();
                    }
                }
            } else {
                //获取失败
                progressDialog.onFail(result.toString());
                level--;
            }
        });
    }

    @Override
    protected Object getStateEventKey() {
        return "SelectCityActivity";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_city;
    }

}
