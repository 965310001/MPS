package com.mingpinmall.shopping;

import android.view.View;
import android.widget.Toast;

public class MyClick {

    public void onSaveClick(View view){
        Toast.makeText(view.getContext(), "you clicked the view", Toast.LENGTH_LONG).show();
    }
}
