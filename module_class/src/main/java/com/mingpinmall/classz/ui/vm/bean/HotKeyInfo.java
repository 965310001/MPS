package com.mingpinmall.classz.ui.vm.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;

import java.util.List;

/**
 * @author GuoFeng
 * @date : 2019/4/3 18:40
 * @description: 热门搜索、历史搜索记录
 */
public class HotKeyInfo extends BaseBean {

    private List<String> list;/*热门搜索*/
    private List<String> his_list;/*历史搜索记录*/

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public List<String> getHis_list() {
        return his_list;
    }

    public void setHis_list(List<String> his_list) {
        this.his_list = his_list;
    }

}