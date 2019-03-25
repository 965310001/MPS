package com.mingpinmall.classz.ui.vm;

import android.app.Application;
import android.support.annotation.NonNull;

import com.goldze.common.dmvvm.base.mvvm.AbsViewModel;

public class ClassifyViewModel extends AbsViewModel<ClassifyRepository> {

    public ClassifyViewModel(@NonNull Application application) {
        super(application);
    }

    public void getLeft() {
        mRepository.getLeft();
    }

    public void getRight(String gcId) {
        mRepository.getRight(gcId);
    }


}
