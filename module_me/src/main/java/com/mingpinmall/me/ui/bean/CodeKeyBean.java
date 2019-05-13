package com.mingpinmall.me.ui.bean;

import java.io.Serializable;

/**
 * 功能描述：充值卡充值第一步，请求CodeKey
 * @author 小斌
 * @date 2019/4/20
 **/
public class CodeKeyBean implements Serializable {

    private String codekey;

    public String getCodekey() {
        return codekey;
    }

    public void setCodekey(String codekey) {
        this.codekey = codekey;
    }
}
