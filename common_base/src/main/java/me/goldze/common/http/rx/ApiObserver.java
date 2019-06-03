package me.goldze.common.http.rx;




import me.goldze.common.base.bean.BaseResponse;
import me.goldze.common.utils.log.QLog;

/**
 * @author GuoFeng
 * @date : 2019/1/23 11:24
 * @description:
 */
public abstract class ApiObserver<T> extends BaseObserver<BaseResponse<T>> {

    private static final String TAG = "Request";

    @Override
    public void callback(BaseResponse<T> response) {
        if (response.isSuccess()) {
            onSuccess(response);
        } else {
            onFailure(response.getErrorCode(), response.getErrorMsg());
        }
    }

    public void onFailure(int code, String msg) {
        QLog.d("code:" + code + "->msg:" + msg);
    }

    public abstract void onSuccess(BaseResponse<T> response);

}
