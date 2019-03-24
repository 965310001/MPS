package com.goldze.common.dmvvm.base.mvvm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.goldze.common.dmvvm.utils.TUtil;

/**
 * @author GuoFeng
 * @date :2019/1/17 16:46
 * @description:
 */
public class AbsViewModel<T extends AbsRepository> extends AndroidViewModel {

    public T mRepository;

    public AbsViewModel(@NonNull Application application) {
        super(application);
        mRepository = TUtil.getNewInstance(this, 0);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mRepository != null) {
            mRepository.unDisposable();
        }
    }
}
