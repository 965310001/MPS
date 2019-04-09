package com.mingpinmall.classz.utils;

import android.text.TextUtils;

import com.goldze.common.dmvvm.utils.AssetsUtils;
import com.goldze.common.dmvvm.utils.Utils;
import com.google.gson.Gson;
import com.mingpinmall.classz.ui.vm.bean.AreaListInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 读取Assets数据
 */
public class AssetsData {
    /*获取省*/
    public static AreaListInfo getAreaListInfo() {
        String path = "area.json";
        return new Gson().fromJson(AssetsUtils.getStringFromAssert(Utils.getApplication(),
                path), AreaListInfo.class);
    }

    /*获取省String*/
    public static String[] getAreaListInfos() {
        AreaListInfo areaListInfo = getAreaListInfo();
        List<String> list = new ArrayList<>();
        list.add("不限");
        for (AreaListInfo.AreaListBean areaListBean : areaListInfo.getArea_list()) {
            list.add(areaListBean.areaName);
        }
        return list.toArray(new String[list.size()]);
    }

    /*根据省获取Id*/
    public static String getAreaByName(String name) {
        if (!TextUtils.isEmpty(name)) {
            for (AreaListInfo.AreaListBean areaListBean : getAreaListInfo().getArea_list()) {
                if (name.equals(areaListBean.areaName)) {
                    return areaListBean.areaId;
                }
            }
        }
        return "";
    }
}
