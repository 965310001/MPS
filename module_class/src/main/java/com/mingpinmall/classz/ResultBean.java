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
    private String error;
//    public DatasBean datas;

    public boolean isSuccess() {
        return 200 == code;
    }

    /**
     * 是否登录了
     *
     * @return true：没有登录
     */
    public boolean isLogin() {
        return !"0".equals(login);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
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

                '}';
    }
}