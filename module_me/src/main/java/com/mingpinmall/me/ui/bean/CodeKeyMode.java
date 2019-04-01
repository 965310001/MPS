package com.mingpinmall.me.ui.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/3/30
 **/
public class CodeKeyMode extends BaseBean {

    private int code;
    private DatasBean datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {

        private String codekey;

        public String getCodekey() {
            return codekey;
        }

        public void setCodekey(String codekey) {
            this.codekey = codekey;
        }
    }
}
