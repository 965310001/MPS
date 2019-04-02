package com.mingpinmall.classz;

import com.goldze.common.dmvvm.base.bean.BaseBean;

public class ResultBean extends BaseBean {

    /**
     * code : 400
     * login : 0
     * datas : {"error":"请登录"}
     */

    private int code;
    private String login;
    private DatasBean datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * error : 请登录
         */

        private String error;

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "code=" + code +
                ", login='" + login + '\'' +
                ", datas=" + datas +
                '}';
    }
}