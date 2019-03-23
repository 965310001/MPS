package com.mingpinmall.shopping;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mingpinmall.shopping.bean.UserBean;
import com.mingpinmall.shopping.databinding.ActivityMain2Binding;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMain2Binding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_main2);
        binding.setClick(new MyClick());
        binding.tvName.setText("name");
    }
}
