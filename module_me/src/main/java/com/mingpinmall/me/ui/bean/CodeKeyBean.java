package com.mingpinmall.me.ui.bean;

import java.io.Serializable;

/**
 * 功能描述：充值卡充值第一步，请求CodeKey
 * 创建人：小斌
 * 创建时间: 2019/4/20
 **/
public class CodeKeyBean implements Serializable {

    /**
     * codekey : c7ce828b
     */

    private String codekey;

    public String getCodekey() {
        return codekey;
    }

    public void setCodekey(String codekey) {
        this.codekey = codekey;
    }
}
