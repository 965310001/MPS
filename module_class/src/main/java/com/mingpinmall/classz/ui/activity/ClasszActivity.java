package com.mingpinmall.classz.ui.activity;

import android.os.Bundle;

import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.xutils.BindAssets;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ui.activity.classify.ClassifyFragment;
import com.mingpinmall.classz.ui.vm.bean.AreaListInfo;


//@ContentView(R.layout.brvah_quick_view_load_more)
public class ClasszActivity extends BaseActivity {
//    @BindString(R.string.load_all)
    String title;

//    @BindArray(R.array.fit_age_tabs)
    String[] strings;

    @BindAssets(value = "area.json", clazz = AreaListInfo.class)
    AreaListInfo areaListInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_classz;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("分类");
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, ClassifyFragment.newInstance()).commit();
//        QLog.i(title + " " + strings+areaListInfo);
//        ActivityToActivity.toActivity(ARouterConfig.home.SHOPPINGDETAILSACTIVITY, "id", "109928");
        /*店铺商家*/
        ActivityToActivity.toActivity(ARouterConfig.classify.STOREACTIVITY);
    }

    @Override
    protected boolean isActionBar() {
        return false;
    }

    @Override
    protected boolean isBack() {
        return false;
    }
}