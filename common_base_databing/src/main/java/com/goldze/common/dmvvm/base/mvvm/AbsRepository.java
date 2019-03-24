package com.goldze.common.dmvvm.base.mvvm;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author GuoFeng
 * @date :2019/1/17 16:35
 * @description: 订阅关系
 */
public abstract class AbsRepository {

    private CompositeDisposable mCompositeDisposable;

    public AbsRepository() {

    }

    protected void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public void unDisposable() {
        if (mCompositeDisposable != null && mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }
    }

}
