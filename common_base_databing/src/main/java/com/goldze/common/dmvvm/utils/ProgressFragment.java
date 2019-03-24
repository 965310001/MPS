package com.goldze.common.dmvvm.utils;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goldze.common.dmvvm.R;

import java.util.Random;


/**
 * 进度条
 */
public class ProgressFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setCancelable(false);
        return inflater.inflate(R.layout.stateview_loading_view, container);
    }


    public static ProgressFragment show(FragmentManager manager) {
        Random random = new Random();
        ProgressFragment progressFragment = new ProgressFragment();
        progressFragment.show(manager, String.valueOf(random.nextInt(Integer.MAX_VALUE)));
        return progressFragment;
    }


}
